/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.Helpers;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.BindException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import jlib.exception.ProgrammingException;
import jlib.exception.TechnicalException;

/**
 * A FTP client operating in passive mode.
 * This class allows to:
 * <ul>
 * 	<li>Connect to a FTP server</li>
 * 	<li>Navigate through the folder structure.</li>
 * 	<li>Retrieve the list of files and folders in a folder</li>
 * 	<li>Check for the existence of a particular file or folder</li>
 * 	<li>Check for the size of a particular file.</li>
 * 	<li>Upload or download files from and to the server.</li>
 * 	<li>Open InputStream and OutputStream to files on the server (This
 * 		allows "on the fly" operations).</li>
 * </ul>
 * 
 * @author Jean-Michel Gonet
 */
public class FtpPassiveClient {

//*************************************************************************
//**                         Class properties.                           **
//*************************************************************************

//***************************** Command socket ****************************
/**
 * Command socket.
 * All orders to the FTP server are transmitted through this sockets. Also
 * the responses from the FTP server are returned through it.
 * The command socket is initialized by {@link connectToFTPServer}, and is
 * cleared by {@link disconnect}.
 */
	private Socket _commandSocket;

//************** Streams to read and write to the command socket **********
/**
 * Stream for reading from the command socket.
 * This property is initialized by {@link connectToFTPServer}.
 */
	private BufferedReader _commandInput;

/**
 * Stream for writing to the command socket.
 * This property is initialized by {@link connectToFTPServer}.
 */
	private OutputStream _commandOutput;

//******************************* Data socket ****************************
/**
 * Data socket.
 * This socket has to be initialized before sending orders that need data
 * transference: LIST, STOR, RETR.
 * This property can be accessed for reading and write files by calling
 * methods {@link getInputStream} and {@link getOutputStream}.
 */
	private Socket _dataSocket;

//*************************** Last executed command ***********************
/**
 * Last command executed by {@link #executeFTPCommand}.
 * Used to build up the FTP exceptions.
 */
	private String _lastCommand;

//*************************************************************************
//**                     Connect to a FTP server.                        **
//*************************************************************************
/**
 * Connects to a FTP server using the port number 21. 
 * @param url Address or IP to the FTP server.
 * @param user User name (warning, the user name and password are
 * transmitted unencrypted in the FTP protocol).
 * @param password Password for the specified user name (warning, the user
 * name and password are transmitted unecrypted in the FTP protocol).
 */
	public void connectToFTPServer(String host,String user,String password) throws FtpException,UnknownHostException {
		connectToFTPServer(host,user,password,21);
	}

/**
 * Connects to a FTP server using the specified port number (the default
 * port number for FTP protocol is 21). 
 * @param host Address or IP to the FTP server.
 * @param user User name (warning, the user name and password are
 * transmitted unencrypted in the FTP protocol).
 * @param password Password for the specified user name (warning, the user
 * name and password are transmitted unecrypted in the FTP protocol).
 * @param port The port number.
 * @exception FtpException When the FTP returns an error. Error codes can be
 * any of:
 * <ul>
 * 	<li><b>120</b>:  Service ready in nnn minutes.</li>
 * 	<li><b>202</b>:  Command not implemented, superfluous at this site.</li>
 * 	<li><b>220</b>:  Service ready for new user.</li>
 * 	<li><b>230</b>:  User logged in, proceed.</li>
 * 	<li><b>331</b>:  User name okay, need password.</li>
 * 	<li><b>332</b>:  Need account for login.</li>
 * 	<li><b>421</b>:  Service not available, closing control connection. This may be a reply to any command if the service knows it must shut down.</li>
 * 	<li><b>500</b>:  Syntax error, command unrecognized. This may include errors such as command line too long.</li>
 * 	<li><b>501</b>:  Syntax error in parameters or arguments.</li>
 * 	<li><b>503</b>:  Bad sequence of commands.</li>
 * 	<li><b>530</b>:  Not logged in.</li>
 * </ul>
 * @exception Exception If unexpected errors or communication failure.
 */
	public void connectToFTPServer(String host,String user,String password,int port) throws FtpException, UnknownHostException {
		String response;            // Contains the response from the FTP server.
		try {
//*********************** Connects to the FTP server ***********************
			_commandSocket=new Socket(host,port);
			_commandInput=new BufferedReader(new InputStreamReader(_commandSocket.getInputStream()));
			_commandOutput=_commandSocket.getOutputStream();

			response=_commandInput.readLine();
			if (response.charAt(0)!='2')
				throw new FtpException("Server returned error message at connecting to '"+host+"':'"+port+"'",response);
	
//*************************** Logs the user in *****************************		
			response=executeFTPCommand("USER "+user);
			do {
				if (response.charAt(0)=='5' || response.charAt(0)=='4' || response.charAt(0)=='1')
					throw new FtpException("Server returned error message at login user '"+user+"' to host '"+host+"':'"+port+"'",response,_lastCommand);
				if (response.startsWith("230") || response.startsWith("202"))
					return;    // Codes 230 and 202: no password is needed.
				if (response.startsWith("33"))
					break;     // A response starting by 33X means that authentication is needed.
				response=_commandInput.readLine();
			} while (response!=null);

//************************** Sends the user password ************************
			response=executeFTPCommand("PASS "+password);
			do {
				char c=response.charAt(0);
				if (c=='5')
					throw new FtpException("Server returned error message at sending password '"+password+"' for authenticating user '"+user+"' to host '"+host+"':'"+port+"'",response,_lastCommand);
				if (c=='2')
					return;    // A response starting by 2XX means that the password is accepted or no needed.
				response=_commandInput.readLine();
			} while (response!=null);			
		}
//************************** Exception management ***************************
		catch (FtpException e) {
			throw e;
		}
		catch (UnknownHostException e) {
			throw e;
		}
		catch (IOException e) {
			throw new TechnicalException(TechnicalException.IO_ERROR,"while connecting to ('"+host+"','"+user+"','*******',"+port+")-->"+e.getMessage(),e);
		}
	}

//*************************************************************************
//**            Checks if the command socket is still connected.         **
//*************************************************************************
/**
 * Checks if the command socket is still connected.
 * @return <i>true</i> if the command socket is still connected.
 */
	public boolean isConnected() throws Exception {
		try {
			if (_commandSocket.isBound())
				if (_commandSocket.isConnected())
					if (!_commandSocket.isClosed())
						if (!_commandSocket.isInputShutdown())
							if (!_commandSocket.isOutputShutdown())
								return true;
			return false;
		}
		catch (Exception e) {
			throw new Exception(ParseError.parseError("FtpPassiveClient.isConnected",e));
		}
	}

//*************************************************************************
//**               Disconnects from the current FTP server.              **
//*************************************************************************
/**
 * Disconnects from the current FTP server.
 * It is part of the NETiquette to disconnect from FTP servers.
 * If the instance is not currently connected to any FTP server, the method
 * does nothing.<p/>
 */
	public void disconnect() throws IOException {
		executeFTPCommand("QUIT");
		_commandSocket.close();
		_commandOutput.close();
		_commandInput.close();
	}

//*************************************************************************
//**         Changes the Working Directory.                              **
//*************************************************************************
/**
 * Changes the current working directory.
 * @param remoteFolder The name of the new working directory. To specify an
 * absolute path, start with '/'.
 * @exception FtpException if the FTP server returns an error. Error codes
 * can be any of:
 * <ul>
 * 	<li><b>500</b>:  Syntax error, command unrecognized. This may include errors such as command line too long.</li>
 * 	<li><b>501</b>:  Syntax error in parameters or arguments.</li>
 * 	<li><b>502</b>:  Command not implemented.</li>
 * 	<li><b>530</b>:  Not logged in.</li>
 * 	<li><b>550</b>:  Requested action not taken. File unavailable (e.g., file not found, no access).</li>
 * 	<li><b>421</b>:  Service not available, closing control connection. This may be a reply to any command if the service knows it must shut down.</li>
 * </ul>
 * @exception Exception If unexpected errors or communication failure.
 */
	public void changeCurrentDirectory(String remoteFolder) throws FtpException {
		String response;
		try {
//******************* Sends the command to the server ***********************
			response=executeFTPCommand("CWD "+remoteFolder);

//******************** Waits for the "230" answer ***************************
			do {
				char c=response.charAt(0);
				if (c=='5' || c=='4')
					throw new FtpException("Server returned error message when changing working directory to '"+remoteFolder+"'",response,_lastCommand);
				if (c=='2')
					return;    // A response starting by 2XX means that the password is accepted.
				response=_commandInput.readLine();
			} while (response!=null);
		}

//************************ Exception management *****************************
		catch (FtpException e) {
			throw e;
		}
		catch (Exception e) {
			throw new ProgrammingException(ProgrammingException.FTP_CHANGEDIRECTORY,"Error while changing to directory '"+remoteFolder+"'",e);
		}
	}

//***************************************************************************
//**     Retrieves the list of files and folders of the current folder.    **
//***************************************************************************
/**
 * Retrieves the list of files and folders of the current folder.
 * To change the current folder, use {@link #changeCurrentDirectory}.
 * @param remoteFolder The name of the folder to list the contents from. 
 * To specify an absolute path, start with '/'.
 * @return An array of strings, each element containing the simple file name (without its path).
 * @exception FtpException When the FTP returns an error. Error codes can be
 * any of:
 * <ul>
 * 	<li><b>125</b>:  Data connection already open; transfer starting.</li>
 * 	<li><b>150</b>:  File status okay; about to open data connection.</li>
 * 	<li><b>226</b>:  Closing data connection. Requested file action successful (for example, file transfer or file abort).</li>
 * 	<li><b>250</b>:  Requested file action okay, completed.</li>
 * 	<li><b>421</b>:  Service not available, closing control connection. This may be a reply to any command if the service knows it must shut down.</li>
 * 	<li><b>425</b>:  Can't open data connection.</li>
 * 	<li><b>426</b>:  Connection closed; transfer aborted.</li>
 * 	<li><b>450</b>:  Requested file action not taken. File unavailable (e.g., file busy).</li>
 * 	<li><b>451</b>:  Requested action aborted: local error in processing.</li>
 * 	<li><b>500</b>:  Syntax error, command unrecognized. This may include errors such as command line too long.</li>
 * 	<li><b>501</b>:  Syntax error in parameters or arguments.</li>
 * 	<li><b>502</b>:  Command not implemented.</li>
 * 	<li><b>530</b>:  Not logged in.</li>
 * </ul>
 * @exception Exception If unexpected errors or communication failure.
 */
	public String[] getFilesList(String remoteFolder) throws FtpException {
		StringBuilder fileList;
		Socket dataSocket;
		BufferedReader dataStream;
		String command;
		String response;
		String fileName;
		int n1,n2;
		try {
//**************************** Initialization ********************************
			dataSocket=openDataSocket();

//................ Builds the apropriate FTP command .........................
			if (remoteFolder==null) remoteFolder="";
			if (remoteFolder.length()>0)
				command="NLST "+remoteFolder;
			else
				command="NLST";

//*********************** Retrieves the list of files ************************
			executeFTPCommand(command);

//............... Reads the file list until data socket is closed ............
			fileList=new StringBuilder();
			dataStream=new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
			while((response=dataStream.readLine())!=null) {
				if (fileList.length()>0)
					fileList.append("?");
				n1=response.lastIndexOf("/");
				n2=response.lastIndexOf("\\");
				if (n2>n1) n1=n2;
				fileName=response.substring(n1+1);
				fileList.append(fileName);
			}
			dataSocket.close();

//.......... Reads responses until reaching a success or an error ............
// Success reponse for a NLST command is:
// 226 Transfer complete.
			for(;;) {
				response=_commandInput.readLine();
				char c=response.charAt(0);
				if (c=='2')
					break;
				if (c=='4' || c=='5')
					throw new FtpException("Error reading content of folder '"+remoteFolder+"'",response,_lastCommand);
			}

//****************** Builds the array of files ******************************
			if (fileList.length()>0)
				return fileList.toString().split("\\?");
			else
				return new String[0];
		}
//**************************** Exception management *************************
		catch (FtpException e) {
			throw e;
		}
		catch (IOException e) {
			throw new TechnicalException(TechnicalException.IO_ERROR,e.getMessage(),e);
		}
	}

//***************************************************************************
//**                Checks the existence of a file.                        **
//***************************************************************************
/**
 * Checks the existence of a file.
 * @param remoteFileName Remote file on the FTP server. This path
 * is relative to the <code>Current Working Directory</code> (see
 * {@link changeWorkingDirectory} and {@link getCurrentWorkingDirectoy}.
 * @return <code>true</code> when the file exists.
 * @exception FtpException When the FTP returns an error. Error codes can be
 * any of:
 * <ul>
 * 	<li><b>213</b>:  File status.</li>
 * 	<li><b>500</b>:  Syntax error, command unrecognized. This may include errors such as command line too long.</li>
 * 	<li><b>501</b>:  Syntax error in parameters or arguments.</li>
 * 	<li><b>530</b>:  Not logged in.</li>
 * 	<li><b>550</b>:  Requested action not taken. File unavailable (e.g., file not found, no access).</li>
 * </ul>
 * @exception Exception If unexpected errors or communication failure.
 */
	public boolean fileExists(String remoteFileName) throws Exception, FtpException {
		String response;
		try {
//********************* Tries to check the file size ************************
			response=executeFTPCommand("SIZE "+remoteFileName);

//*********************** Checks the server response ************************
			while(!response.startsWith("213")) {
//................ When the file doesn't exist ..............................
				if (response.startsWith("550"))
					return false;

//................. Other errors are not normal .............................
				if (response.startsWith("5")) 
					throw new FtpException("Server returned an error while checking the file size",response,_lastCommand);

//................... Retrieves a new response ..............................
				response=_commandInput.readLine();
			}

//................ When the file exists .....................................
			return true;
		}
//*************************** Exception management **************************
		catch (FtpException e) {
			throw e;
		}
		catch(Exception e) {
			throw new Exception(ParseError.parseError("FtpPassiveClient.fileExists('"+remoteFileName+"')",e));
		}
	}

//*************************************************************************
//**                     Returns the size of a file.                     **
//*************************************************************************
/**
 * Returns the size of a file on the FTP server.
 * @param remoteFileName Remote file on the FTP server. This path
 * is relative to the <code>Current Working Directory</code> (see
 * {@link changeWorkingDirectory} and {@link getCurrentWorkingDirectoy}.
 * @return The file size in bytes.
 * @exception FtpException When the FTP returns an error. Error codes can be
 * any of:
 * <ul>
 * 	<li><b>213</b>:  File status.</li>
 * 	<li><b>500</b>:  Syntax error, command unrecognized. This may include errors such as command line too long.</li>
 * 	<li><b>501</b>:  Syntax error in parameters or arguments.</li>
 * 	<li><b>530</b>:  Not logged in.</li>
 * 	<li><b>550</b>:  Requested action not taken. File unavailable (e.g., file not found, no access).</li>
 * </ul>
 * @exception Exception If unexpected errors or communication failure.
 */
	public int fileSize(String remoteFileName) throws FtpException, Exception {
		String response;
		int n;
		try {
//********************* Asks for the file size *****************************
			response=executeFTPCommand("SIZE "+remoteFileName);

//*********************** Checks the server response ************************
			while(!response.startsWith("213")) {
				if (response.startsWith("5")) 
					throw new FtpException("Server returned an error while retrieving  the file size",response,_lastCommand);

//................... Retrieves a new response ..............................
				response=_commandInput.readLine();
			}

//**************** Process the server response *****************************
			n=response.lastIndexOf(" ");
			return Integer.parseInt(response.substring(n+1));
		}

//*************************** Exception management **************************
		catch (FtpException e) {
			throw e;
		}
		catch(Exception e) {
			throw new Exception(ParseError.parseError("FtpPassiveClient.fileSize('"+remoteFileName+"')",e));
		}
	}

//*************************************************************************
//**                 Returns the file date.                              **
//*************************************************************************
/**
 * Returns the modified date of a file on the FTP server.
 * @param remoteFileName Remote file on the FTP server. This path
 * is relative to the <code>Current Working Directory</code> (see
 * {@link changeWorkingDirectory} and {@link getCurrentWorkingDirectoy}.
 * @return The file date.
 * @exception FtpException When the FTP returns an error. Error codes can be
 * any of:
 * <ul>
 * 	<li><b>213</b>:  File status.</li>
 * 	<li><b>500</b>:  Syntax error, command unrecognized. This may include errors such as command line too long.</li>
 * 	<li><b>501</b>:  Syntax error in parameters or arguments.</li>
 * 	<li><b>530</b>:  Not logged in.</li>
 * 	<li><b>550</b>:  Requested action not taken. File unavailable (e.g., file not found, no access).</li>
 * </ul>
 * @exception Exception If unexpected errors or communication failure.
 */
	public Date fileDate(String remoteFileName) throws FtpException,Exception {
		String response;
		int year,month,day,hour,minute;
		GregorianCalendar gc;
		try {
//********************* Asks for the file date ******************************
			response=executeFTPCommand("MDTM "+remoteFileName);

//*********************** Checks the server response ************************
			while(!response.startsWith("213")) {
				if (response.startsWith("5")) 
					throw new FtpException("Server returned an error while retrieving the file date: "+response);

//................... Retrieves a new response ..............................
				response=_commandInput.readLine();
			}

//**************** Process the server response ******************************
			response=response.substring(4);

// Puts together the bits of information:
			year=Integer.parseInt(response.substring(0,4));
			month=Integer.parseInt(response.substring(4,6));
			day=Integer.parseInt(response.substring(6,8));
			hour=Integer.parseInt(response.substring(8,10));
			minute=Integer.parseInt(response.substring(10,12));
			
// Builds a calendar with the retrieved data:
			gc=new GregorianCalendar();
			gc.set(year,month-1+Calendar.JANUARY,day,hour,minute,0);

//************************ Returns the file date ****************************
			return gc.getTime();
		}

//*************************** Exception management **************************
		catch (FtpException e) {
			throw e;
		}
		catch(Exception e) {
			throw new Exception(ParseError.parseError("FtpPassiveClient.fileDate('"+remoteFileName+"'",e));
		}
	}

//*************************************************************************
//**           Deletes a file on the FTP server.                         **
//*************************************************************************
/**
 * Deletes a file on the FTP server.
 * @param remoteFileName Remote file on the FTP server. This path
 * is relative to the <code>Current Working Directory</code> (see
 * {@link changeWorkingDirectory} and {@link getCurrentWorkingDirectoy}.
 * @exception FtpException When the FTP returns an error. Error codes can be
 * any of:
 * <ul>
 * 	<li><b>250</b>:  Requested file action okay, completed.</li>
 * 	<li><b>421</b>:  Service not available, closing control connection. This may be a reply to any command if the service knows it must shut down.</li>
 * 	<li><b>450</b>:  Requested file action not taken. File unavailable (e.g., file busy).</li>
 * 	<li><b>550</b>:  Requested action not taken. File unavailable (e.g., file not found, no access).</li>
 * 	<li><b>500</b>:  Syntax error, command unrecognized. This may include errors such as command line too long.</li>
 * 	<li><b>501</b>:  Syntax error in parameters or arguments.</li>
 * 	<li><b>502</b>:  Command not implemented.</li>
 * 	<li><b>530</b>:  Not logged in.</li>
 * </ul>
 * @exception Exception If unexpected errors or communication failure.
 */
	public void deleteFile(String remoteFileName) throws FtpException, Exception {
		String response;
		try {
//********************* Sends the command to the FTP server ***************
			response=executeFTPCommand("DELE "+remoteFileName);

//****************** Checks the server response ***************************
			while(!response.startsWith("250")) {
//............ We admit "file doesn't exist" as a correct response ........
				if (response.startsWith("550"))
					return;

//.............. We raise exceptions on other errors ......................
				if (response.startsWith("5") || response.startsWith("4"))
					throw new FtpException("Server returned an error while deleting a file",response,_lastCommand);

//................... Retrieves a new response ..............................
				response=_commandInput.readLine();
			}			
		}

//*************************** Exception management **************************
		catch (FtpException e) {
			throw e;
		}
		catch(Exception e) {
			throw new Exception(ParseError.parseError("FtpPassiveClient.deleteFile('"+remoteFileName+"')",e));
		}
	}

//*************************************************************************
//**                   Creates a new folder.                             **
//*************************************************************************
/**
 * Creates a new folder on the FTP server.
 * @param newRemoteFolderName Remote file on the FTP server. This path
 * is relative to the <code>Current Working Directory</code> (see
 * {@link changeWorkingDirectory} and {@link getCurrentWorkingDirectoy}.
 * @exception FtpException When the FTP returns an error. Error codes can be
 * any of:
 * <ul>
 * 	<li><b>500</b>: Syntax error, command unrecognized. This may include errors such as command line too long.</li>
 * 	<li><b>501</b>: Syntax error in parameters or arguments.</li>
 * 	<li><b>502</b>: Command not implemented.</li>
 * 	<li><b>421</b>: Service not available, closing control connection.</li>
 * 	<li><b>530</b>: Not logged in.</li>
 * 	<li><b>550</b>: Requested action not taken. File unavailable (e.g., file not found, no access).</li>
 * </ul>
 * @exception Exception If unexpected errors or communication failure.
 */
	public void createFolder(String newRemoteFolderName) throws FtpException, Exception {
		String response;
		try {
//********************* Sends the command to the FTP server ***************
			response=executeFTPCommand("MKD "+newRemoteFolderName);

//****************** Checks the server response ***************************
			while(!response.startsWith("257")) {

//.............. We raise exceptions on other errors ......................
				if (response.startsWith("5") || response.startsWith("4"))
					throw new FtpException("Server returned an error while creating a file",response,_lastCommand);

//................... Retrieves a new response ..............................
				response=_commandInput.readLine();
			}			
		}

//*************************** Exception management **************************
		catch (FtpException e) {
			throw e;
		}
		catch(Exception e) {
			throw new Exception(ParseError.parseError("FtpPassiveClient.deleteFile('"+newRemoteFolderName+"')",e));
		}
	}


//*************************************************************************
//**  Opens an Output Stream to write data to a file on the FTP server.  **
//*************************************************************************
/**
 * Opens an Output Stream to write data in a file on the FTP server.
 * The data sent to the stream will be stored on the FTP server in the
 * specified file. When all data is sent through the stream, simply close
 * it.
 * @param remoteFileName Remote file on the FTP server. This path
 * is relative to the <code>Current Working Directory</code> (see
 * {@link changeWorkingDirectory} and {@link getCurrentWorkingDirectoy}.
 * @return An output stream, ready to accept data.
 * @exception FtpException When the FTP returns an error. Error codes can be
 * any of:
 * <ul>
 * 	<li><b>227</b>:  Entering Passive Mode (h1,h2,h3,h4,p1,p2).</li>
 * 	<li><b>500</b>:  Syntax error, command unrecognized. This may include errors such as command line too long.</li>
 * 	<li><b>501</b>:  Syntax error in parameters or arguments.</li>
 * 	<li><b>502</b>:  Command not implemented.</li>
 * 	<li><b>421</b>:  Service not available, closing control connection. This may be a reply to any command if the service knows it must shut down.</li>
 * 	<li><b>530</b>:  Not logged in.</li>
 * </ul>
 * @exception Exception If unexpected errors or communication failure.
 * @deprecated Use {@link #putFile} instead.
 */
	public OutputStream openOutputStream(String remoteFileName) throws FtpException {
		try {
			openDataSocket();
			try {
				return _dataSocket.getOutputStream();
			} catch (IOException e) {
				throw new TechnicalException(TechnicalException.IO_ERROR,e.getMessage(),e);
			}
		}
		catch (FtpException e) {
			throw e;
		}
	}

//**************************************************************************
//**   Opens an Input Stream to read data from a file on the FTP server.  **
//**************************************************************************
/**
 * Opens an Input Stream to read data from a file on the FTP server.
 * The data read from this stream is directly obtained from the file on the
 * FTP server. When all data is read, the stream reports an normal End Of File.
 * @param remoteFileName Remote file on the FTP server. This path
 * is relative to the <code>Current Working Directory</code> (see
 * {@link changeWorkingDirectory} and {@link getCurrentWorkingDirectoy}.
 * @return An input stream, ready to read data from.
 * @deprecated use {@link #getFile} instead.
 */
	public InputStream openInputStream(String remoteFileName) throws FtpException, Exception {
		try {
			openDataSocket();
			return _dataSocket.getInputStream();
		}
		catch (FtpException e) {
			throw e;
		}
		catch (Exception e) {
			throw new Exception(ParseError.parseError("FtpPassiveClient.openInputStream('"+remoteFileName+"')",e));
		}
	}

//*************************************************************************
//**                   Downloads a file from the FTP server.             **
//*************************************************************************
/**
 * Downloads a file from the FTP server.
 * @param remoteFileName Remote file on the FTP server. This path
 * is relative to the <code>Current Working Directory</code> (see
 * {@link changeWorkingDirectory} and {@link getCurrentWorkingDirectoy}.
 * @param localFileName Local file where to store the file retrieved from
 * the FTP server.
 * @return The file size.
 * @exception FtpException When the FTP returns an error. Error codes can be
 * any of:
 * <ul>
 * 	<li><b>110</b>:  Restart marker reply. In this case, the text is exact and not left to the particular implementation; it must read:      MARK yyyy = mmmm Where yyyy is User-process data stream marker, and mmmm server's equivalent marker (note the spaces between markers and "=").</li>
 * 	<li><b>125</b>:  Data connection already open; transfer starting.</li>
 * 	<li><b>150</b>:  File status okay; about to open data connection.</li>
 * 	<li><b>226</b>:  Closing data connection. Requested file action successful (for example, file transfer or file abort).</li>
 * 	<li><b>250</b>:  Requested file action okay, completed.</li>
 * 	<li><b>421</b>:  Service not available, closing control connection. This may be a reply to any command if the service knows it must shut down.</li>
 * 	<li><b>425</b>:  Can't open data connection.</li>
 * 	<li><b>426</b>:  Connection closed; transfer aborted.</li>
 * 	<li><b>451</b>:  Requested action aborted: local error in processing.</li>
 * 	<li><b>450</b>:  Requested file action not taken. File unavailable (e.g., file busy).</li>
 * 	<li><b>500</b>:  Syntax error, command unrecognized. This may include errors such as command line too long.</li>
 * 	<li><b>501</b>:  Syntax error in parameters or arguments.</li>
 * 	<li><b>530</b>:  Not logged in.</li>
 * 	<li><b>550</b>:  Requested action not taken. File unavailable (e.g., file not found, no access).</li>
 * </ul>
 * @exception Exception If unexpected errors or communication failure.
 */
	public int getFile(String remoteFileName,File localFileName) throws FtpException, Exception {
		String response;               // To check responses from server.
		int size;                      // The file size;
		Socket data;                   // A data socket to retrieve the file content.
		InputStream is;                // The input stream from the data socket.
		FileOutputStream fos;          // To save the file content on the local filesystem.
		byte buffer[]=new byte[1024];  // A buffer for data transmission.
		int packageSize;
		int totalBytes;
		try {
			if (localFileName==null)
				throw new Exception("'localFileName' cannot be null.");
			if (remoteFileName==null) remoteFileName="";
			if (remoteFileName.length()==0)
				throw new Exception("Specified 'remoteFileName' cannot be emtpy or null.");

//************************* Initialization *******************************
			size=fileSize(remoteFileName);
			data=openDataSocket();

//............... Sends the command to the server .........................
			response=executeFTPCommand("RETR "+remoteFileName);

			while(!response.startsWith("1")) {
				if (response.startsWith("4") || response.startsWith("5"))
					throw new FtpException("Error retrieving file '"+remoteFileName+"'",response,_lastCommand);
				if (response.startsWith("2"))
					throw new FtpException("Server returned 'OK' for file '"+remoteFileName+"', but file wasn't retrieved yet.",response,_lastCommand);
				response=_commandInput.readLine();
			}

//******************** Retrieves the file contents ***********************
			totalBytes=0;
			is=data.getInputStream();
			fos=new FileOutputStream(localFileName);
			while(totalBytes<size) {
				packageSize=is.read(buffer);
				fos.write(buffer,0,packageSize);
				totalBytes+=packageSize;
			}
			fos.close();

// Closes the data socket:
			data.close();

//******************** Retrieves some server blattering ******************
			do {
				response=_commandInput.readLine();
				if (response.startsWith("4") || response.startsWith("5"))
					throw new FtpException("Error after retrieving "+totalBytes+" bytes of the file '"+remoteFileName+"'.",response,_lastCommand);
			} while (!response.startsWith("226") && !response.startsWith("250"));

//********************** Returns the file size ***************************
			return size;
		}
//****************************** Exception management ********************
		catch (FtpException e) {
			throw e;
		}
		catch (Exception e) {
			String sLocalFileName="null";
			if (localFileName!=null) sLocalFileName=localFileName.getAbsolutePath();
			throw new Exception(ParseError.parseError("FtpPassiveClient.getFile('"+remoteFileName+"','"+sLocalFileName+"')",e));
		}
	}

//*************************************************************************
//**                   Uploads a file to the FTP server.                 **
//*************************************************************************
/**
 * Uploads a file to the FTP server.
 * @param localFileName Local file to upload on the FTP server.
 * @param remoteFileName Remote file on the FTP server. This path
 * is relative to the <code>Current Working Directory</code> (see
 * {@link changeWorkingDirectory} and {@link getCurrentWorkingDirectoy}.
 * @exception FtpException When the FTP returns an error. Error codes can be
 * any of:
 * <ul>
 * 	<li><b>110</b>:  Restart marker reply. In this case, the text is exact and not left to the particular implementation; it must read:      MARK yyyy = mmmm Where yyyy is User-process data stream marker, and mmmm server's equivalent marker (note the spaces between markers and "=").</li>
 * 	<li><b>125</b>:  Data connection already open; transfer starting.</li>
 * 	<li><b>150</b>:  File status okay; about to open data connection.</li>
 * 	<li><b>226</b>:  Closing data connection. Requested file action successful (for example, file transfer or file abort).</li>
 * 	<li><b>250</b>:  Requested file action okay, completed.</li>
 * 	<li><b>421</b>:  Service not available, closing control connection. This may be a reply to any command if the service knows it must shut down.</li>
 * 	<li><b>425</b>:  Can't open data connection.</li>
 * 	<li><b>426</b>:  Connection closed; transfer aborted.</li>
 * 	<li><b>450</b>:  Requested file action not taken. File unavailable (e.g., file busy).</li>
 * 	<li><b>451</b>:  Requested action aborted: local error in processing.</li>
 * 	<li><b>452</b>:  Requested action not taken. Insufficient storage space in system.</li>
 * 	<li><b>500</b>:  Syntax error, command unrecognized. This may include errors such as command line too long.</li>
 * 	<li><b>501</b>:  Syntax error in parameters or arguments.</li>
 * 	<li><b>530</b>:  Not logged in.</li>
 * 	<li><b>532</b>:  Need account for storing files.</li>
 * 	<li><b>551</b>:  Requested action aborted: page type unknown.</li>
 * 	<li><b>552</b>:  Requested file action aborted. Exceeded storage allocation (for current directory or dataset).</li>
 * 	<li><b>553</b>:  Requested action not taken. File name not allowed.
 * </ul>
 * @exception Exception If unexpected errors or communication failure.
 */
	public void putFile(String localFileName,String remoteFileName) throws FileNotFoundException,FtpException {
		putFile(localFileName,remoteFileName,true);
	}

/**
 * Uploads a file to the FTP server.
 * @param localFileName Local file to upload on the FTP server.
 * @param remoteFileName Remote file on the FTP server. This path
 * is relative to the <code>Current Working Directory</code> (see
 * {@link changeWorkingDirectory} and {@link getCurrentWorkingDirectoy}.
 * @param binary If <code>true</code>, binary mode is asked to the server before 
 * starting the file transfer.
 * @exception FtpException When the FTP returns an error. Error codes can be
 * any of:
 * <ul>
 * 	<li><b>110</b>:  Restart marker reply. In this case, the text is exact and not left to the particular implementation; it must read:      MARK yyyy = mmmm Where yyyy is User-process data stream marker, and mmmm server's equivalent marker (note the spaces between markers and "=").</li>
 * 	<li><b>125</b>:  Data connection already open; transfer starting.</li>
 * 	<li><b>150</b>:  File status okay; about to open data connection.</li>
 * 	<li><b>226</b>:  Closing data connection. Requested file action successful (for example, file transfer or file abort).</li>
 * 	<li><b>250</b>:  Requested file action okay, completed.</li>
 * 	<li><b>421</b>:  Service not available, closing control connection. This may be a reply to any command if the service knows it must shut down.</li>
 * 	<li><b>425</b>:  Can't open data connection.</li>
 * 	<li><b>426</b>:  Connection closed; transfer aborted.</li>
 * 	<li><b>450</b>:  Requested file action not taken. File unavailable (e.g., file busy).</li>
 * 	<li><b>451</b>:  Requested action aborted: local error in processing.</li>
 * 	<li><b>452</b>:  Requested action not taken. Insufficient storage space in system.</li>
 * 	<li><b>500</b>:  Syntax error, command unrecognized. This may include errors such as command line too long.</li>
 * 	<li><b>501</b>:  Syntax error in parameters or arguments.</li>
 * 	<li><b>530</b>:  Not logged in.</li>
 * 	<li><b>532</b>:  Need account for storing files.</li>
 * 	<li><b>551</b>:  Requested action aborted: page type unknown.</li>
 * 	<li><b>552</b>:  Requested file action aborted. Exceeded storage allocation (for current directory or dataset).</li>
 * 	<li><b>553</b>:  Requested action not taken. File name not allowed.
 * </ul>
 * @exception Exception If unexpected errors or communication failure.
 */
	public void putFile(String localFileName,String remoteFileName,boolean binary) throws FileNotFoundException, FtpException {
		FileInputStream fis=new FileInputStream(localFileName);
		putFile(fis,remoteFileName,binary);
	}
	
/**
 * Uploads a file to the FTP server.
 * @param is An input stream opened on the local data.
 * @param remoteFileName Remote file on the FTP server. This path
 * is relative to the <code>Current Working Directory</code> (see
 * {@link changeWorkingDirectory} and {@link getCurrentWorkingDirectoy}.
 * @exception FtpException When the FTP returns an error. Error codes can be
 * any of:
 * <ul>
 * 	<li><b>110</b>:  Restart marker reply. In this case, the text is exact and not left to the particular implementation; it must read:      MARK yyyy = mmmm Where yyyy is User-process data stream marker, and mmmm server's equivalent marker (note the spaces between markers and "=").</li>
 * 	<li><b>125</b>:  Data connection already open; transfer starting.</li>
 * 	<li><b>150</b>:  File status okay; about to open data connection.</li>
 * 	<li><b>226</b>:  Closing data connection. Requested file action successful (for example, file transfer or file abort).</li>
 * 	<li><b>250</b>:  Requested file action okay, completed.</li>
 * 	<li><b>421</b>:  Service not available, closing control connection. This may be a reply to any command if the service knows it must shut down.</li>
 * 	<li><b>425</b>:  Can't open data connection.</li>
 * 	<li><b>426</b>:  Connection closed; transfer aborted.</li>
 * 	<li><b>450</b>:  Requested file action not taken. File unavailable (e.g., file busy).</li>
 * 	<li><b>451</b>:  Requested action aborted: local error in processing.</li>
 * 	<li><b>452</b>:  Requested action not taken. Insufficient storage space in system.</li>
 * 	<li><b>500</b>:  Syntax error, command unrecognized. This may include errors such as command line too long.</li>
 * 	<li><b>501</b>:  Syntax error in parameters or arguments.</li>
 * 	<li><b>530</b>:  Not logged in.</li>
 * 	<li><b>532</b>:  Need account for storing files.</li>
 * 	<li><b>551</b>:  Requested action aborted: page type unknown.</li>
 * 	<li><b>552</b>:  Requested file action aborted. Exceeded storage allocation (for current directory or dataset).</li>
 * 	<li><b>553</b>:  Requested action not taken. File name not allowed.
 * </ul>
 * @exception Exception If unexpected errors or communication failure.
 */
	public void putFile(InputStream is,String remoteFileName) throws FtpException {
		putFile(is,remoteFileName,true);
	}

/**
 * Uploads a file to the FTP server.
 * @param is An input stream opened on the local data.
 * @param remoteFileName Remote file on the FTP server. This path
 * is relative to the <code>Current Working Directory</code> (see
 * {@link changeWorkingDirectory} and {@link getCurrentWorkingDirectoy}.
 * @param binary If <code>true</code>, binary mode is asked to the server before 
 * starting the file transfer.
 * @exception FtpException When the FTP returns an error. Error codes can be
 * any of:
 * <ul>
 * 	<li><b>110</b>:  Restart marker reply. In this case, the text is exact and not left to the particular implementation; it must read:      MARK yyyy = mmmm Where yyyy is User-process data stream marker, and mmmm server's equivalent marker (note the spaces between markers and "=").</li>
 * 	<li><b>125</b>:  Data connection already open; transfer starting.</li>
 * 	<li><b>150</b>:  File status okay; about to open data connection.</li>
 * 	<li><b>226</b>:  Closing data connection. Requested file action successful (for example, file transfer or file abort).</li>
 * 	<li><b>250</b>:  Requested file action okay, completed.</li>
 * 	<li><b>421</b>:  Service not available, closing control connection. This may be a reply to any command if the service knows it must shut down.</li>
 * 	<li><b>425</b>:  Can't open data connection.</li>
 * 	<li><b>426</b>:  Connection closed; transfer aborted.</li>
 * 	<li><b>450</b>:  Requested file action not taken. File unavailable (e.g., file busy).</li>
 * 	<li><b>451</b>:  Requested action aborted: local error in processing.</li>
 * 	<li><b>452</b>:  Requested action not taken. Insufficient storage space in system.</li>
 * 	<li><b>500</b>:  Syntax error, command unrecognized. This may include errors such as command line too long.</li>
 * 	<li><b>501</b>:  Syntax error in parameters or arguments.</li>
 * 	<li><b>530</b>:  Not logged in.</li>
 * 	<li><b>532</b>:  Need account for storing files.</li>
 * 	<li><b>551</b>:  Requested action aborted: page type unknown.</li>
 * 	<li><b>552</b>:  Requested file action aborted. Exceeded storage allocation (for current directory or dataset).</li>
 * 	<li><b>553</b>:  Requested action not taken. File name not allowed.
 * </ul>
 * @exception Exception If unexpected errors or communication failure.
 */
	public void putFile(InputStream is,String remoteFileName,boolean binary) throws FtpException {
		String response;               // To check responses from server.
		Socket data;                   // A data socket to retrieve the file content.
		OutputStream os;               // The input stream from the data socket.
		byte buffer[]=new byte[1024];  // A buffer for data transmission.
		int packageSize;               // Size of one package (file is sent in packages of variable size).
		int totalSize;                 // Total number of bytes sent.
		try {
//************************* Initialization *******************************
			data=openDataSocket();

//.................... Sets the transfer type to binary ...................
			if (binary)
				setTransferType(typeIMAGE);

//............... Sends the command to the server .........................
			response=executeFTPCommand("STOR "+remoteFileName);

			while(!response.startsWith("1")) {
				if (response.startsWith("4") || response.startsWith("5"))
					throw new FtpException("Error sending file '"+remoteFileName+"'",response,_lastCommand);
				if (response.startsWith("2"))
					throw new FtpException("Server returned 'OK' for file '"+remoteFileName+"', but file wasn't sent yet.",response,_lastCommand);
				response=_commandInput.readLine();
			}
			
//******************** Retrieves the file contents ***********************
			totalSize=0;
			os=data.getOutputStream();
			
			while((packageSize=is.read(buffer))>=0) {
				os.write(buffer,0,packageSize);
				totalSize+=packageSize;
			}
			os.close();
			data.close();
			is.close();

//******************** Retrieves some server blattering ******************
			do {
				response=_commandInput.readLine();
				if (response.startsWith("4") || response.startsWith("5"))
					throw new FtpException("Error after sending "+totalSize+" bytes of the file '"+remoteFileName+"'.",response,_lastCommand);
			} while (!response.startsWith("226") && !response.startsWith("250"));
		}

//******************** Exception management ******************************
		catch (FtpException e) {
			if (is!=null)
				try {
					is.close();
				} catch (IOException ee) {
					
				}
			throw e;
		}
		catch (IOException e) {
			throw new TechnicalException(TechnicalException.IO_ERROR,"While sending a file to '"+remoteFileName+"': "+e.getMessage(),e);
		}
	}	
	

	

//*************************************************************************
//**               Sends an order through the command socket.            **
//*************************************************************************
/**
 * Sends a command to the FTP server.
 * The command is sent through the command socket. A '\r\n' is placed at
 * the end of the command.
 * Here is a list of FTP commands:
 *	<li>ABOR: abort a file transfer</li>
 *	<li>CWD: change working directory</li>
 *	<li>CDUP: CWD to the parent of the current directory</li>
 *	<li>DELE: delete a remote file</li>
 *	<li>LIST: list remote files (*)</li>
 *	<li>MDTM: return the modification time of a file</li>
 *	<li>MKD: make a remote directory</li>
 *	<li>NLST: name list of remote directory</li>
 *	<li>PASS: send password </li>
 *	<li>PASV: enter passive mode</li>
 *	<li>PORT: open a data port</li>
 *	<li>PWD: print working directory </li>
 *	<li>QUIT: terminate the connection</li>
 *	<li>RETR: retrieve a remote file (*)</li>
 *	<li>RMD: remove a remote directory</li>
 *	<li>RNFR: rename from</li>
 *	<li>RNTO: rename to</li>
 *	<li>SITE: site-specific commands</li>
 *	<li>SIZE: return the size of a file</li>
 *	<li>STOR: store a file on the remote host (*)</li>
 *	<li>TYPE: set transfer type</li>
 *	<li>USER: send username</li>
 * (*) Means that the response to the command is sent to the data socket. A data
 * socket can be opened with the openFTPPassiveConnection method.
 * @param command The command to send to the FTP server.
 */
	private String executeFTPCommand(String command) throws IOException {
		final StringBuffer newLine = new StringBuffer("\r\n");
		StringBuffer commandLine = new StringBuffer(command);
		_lastCommand=command;

		PrintStream ps = new PrintStream(_commandOutput);
		commandLine.append(newLine);
		ps.print(commandLine);
		if(ps.checkError())
			throw new IOException();
		/*
		int n,nn;
		_lastCommand=command;
		nn=command.length();
		for(n=0;n<nn;n++) {
			byte b=(byte)command.charAt(n);
			_commandOutput.write(b);
		}
		_commandOutput.write('\n');
		*/
		return _commandInput.readLine();
	}

//*************************************************************************
//**                    Sets the transfer type                           **
//*************************************************************************

/** 
 * To specify a binary transfer type.
 * The data are sent as contiguous bits which, for transfer,
 * are packed into the 8-bit transfer bytes.  The receiving site must store 
 * the data as contiguous bits.  The structure of the storage system might 
 * necessitate the padding of the file (or of each record, for a record-structured file) 
 * to some convenient boundary (byte, word or block).  This padding, which must be all 
 * zeros, may occur only at the end of the file (or at the end of each record) and 
 * there must be a way of identifying the padding bits so that they may be
 * stripped off if the file is retrieved.  The padding transformation should be well 
 * publicized to enable a user to process a file at the storage site.<p/>
 * Image type is intended for the efficient storage and retrieval of files and for the 
 * transfer of binary data.  It is recommended that this type be accepted by 
 * all FTP implementations.
 * See {@link #setTransferType}. 
 */
	public final String typeIMAGE="I";

/** 
 * To specify a text transfer type.
 * This is the default type and must be accepted by all FTP
 * implementations.  It is intended primarily for the transfer
 * of text files, except when both hosts would find the EBCDIC type 
 * more convenient.<p/>
 * The sender converts the data from an internal character
 * representation to the standard 8-bit NVT-ASCII
 * representation (see the Telnet specification).  The receiver
 * will convert the data from the standard form to his own
 * internal form.<p/>
 * In accordance with the NVT standard, the [CRLF] sequence
 * should be used where necessary to denote the end of a line
 * of text.  (See the discussion of file structure at the end
 * of the Section on Data Representation and Storage.)<p/>
 * Using the standard NVT-ASCII representation means that data
 * must be interpreted as 8-bit bytes. <p/>
 * See {@link #setTransferType}.
 */
	public final String typeASCII="A";

/**
 * To specify an EBCDIC transfer type.
 * This type is intended for efficient transfer between hosts
 * which use EBCDIC for their internal character
 * representation.<p/>
 * For transmission, the data are represented as 8-bit EBCDIC
 * characters.  The character code is the only difference
 * between the functional specifications of EBCDIC and ASCII
 * types.<p/>
 * End-of-line (as opposed to end-of-record--see the discussion
 * of structure) will probably be rarely used with EBCDIC type
 * for purposes of denoting structure, but where it is
 * necessary the [NL] character should be used.<p/>
 * See {@link #setTransferType}
 */
	public final String typeEBCDIC="E";

/** 
 * To specify a LOCAL transfer type.
 * The data is transferred in logical bytes of the size
 * specified by the obligatory second parameter, Byte size.
 * The value of Byte size must be a decimal integer; there is
 * no default value.  The logical byte size is not necessarily
 * the same as the transfer byte size.  If there is a
 * difference in byte sizes, then the logical bytes should be
 * packed contiguously, disregarding transfer byte boundaries
 * and with any necessary padding at the end.<p/>
 * See {@link #setTransferType}
 */
	public final String typeLOCAL="L";

/**
 * Sets the transfer type.
 * @param type Specifies the transfert type. Available types are {@link #typeIMAGE}, 
 * {@link #typeASCII}, {@link #typeEBCDIC} and {@link #typeLOCAL}.
 * @exception FtpException When the FTP returns an error. Error codes can be
 * any of:
 * <ul>
 * 	<li><b>500</b>:  Syntax error, command unrecognized. This may include errors such as command line too long.</li>
 * 	<li><b>501</b>:  Syntax error in parameters or arguments.</li>
 * 	<li><b>504</b>:  Command not implemented for that parameter.</li>
 * 	<li><b>421</b>:  Service not available, closing control connection. This may be a reply to any command if the service knows it must shut down.</li>
 * 	<li><b>530</b>:  Not logged in.</li>
 * </ul>
 * @exception Exception If unexpected errors or communication failure.
 */
	public void setTransferType(String type) throws FtpException {
		String response;
		try {
			response=executeFTPCommand("TYPE "+type);
	
			while(!response.startsWith("2")) {
				if (response.startsWith("4") || response.startsWith("5"))
					throw new FtpException("Error setting transfer type to '"+type+"'",response,_lastCommand);
				response=_commandInput.readLine();
			}
		} catch (IOException e) {
			throw new TechnicalException(TechnicalException.IO_ERROR,e.getMessage(),e);
		}
	}
	
//*************************************************************************
//**                    Opens a data connection.                         **
//*************************************************************************
// Possible error codes:
// 227:  Entering Passive Mode (h1,h2,h3,h4,p1,p2).</li>
// 500:  Syntax error, command unrecognized. This may include errors such as command line too long.</li>
// 501:  Syntax error in parameters or arguments.</li>
// 502:  Command not implemented.</li>
// 421:  Service not available, closing control connection. This may be a reply to any command if the service knows it must shut down.</li>
// 530:  Not logged in.</li>
	private Socket openDataSocket() throws FtpException {
		String response;           // Contains server response to commands.
		String numbers[];            // Contains the 6 numbers returned by the FTP server.
		String ip;                 // The ip adress to open the passive connection.        
		int port;               // The port number to open the passive connection.
		int n1,n2;                 // Auxiliary to compute the port number.

//********************** Asks for a passive connection ********************
		for(;;) {
			try {
				response=executeFTPCommand("PASV");
			} catch (IOException e) {
				throw new TechnicalException(TechnicalException.IO_ERROR,e.getMessage(),e);
			}
			while (!response.startsWith("227")) {
				if (response.startsWith("5") || response.startsWith("4"))
					throw new FtpException("Error trying to open a data socket (PASV command)",response,_lastCommand);
				try {
					response=_commandInput.readLine();
				} catch (IOException e) {
					throw new TechnicalException(TechnicalException.IO_ERROR,e.getMessage(),e);
				}
			}

//****************** Reads the server response ***************************
// The response is something like: 
//    "227 Entering Passive Mode (195,13,58,68,183,29)"
// The first 4 numbers compose the IP address.
// The last 2 numbers contain the port number.
			n1=response.indexOf("(");
			n2=response.indexOf(")",n1);
			response=response.substring(n1+1,n2);
			numbers=response.split(",");

//................... Builds the IP address ..............................
			ip=numbers[0]+"."+numbers[1]+"."+numbers[2]+"."+numbers[3];

//................... Computes the port number ...........................
			n1=Integer.parseInt(numbers[4]);
			n2=Integer.parseInt(numbers[5]);
			port=n1*256+n2;

//**************** Establish the passive connection **********************
// Tries to establish the passive connection: the IP may be already
// in use in the local system (by any other network application).
			try {
				_dataSocket=new Socket(ip,port);
				break;
			} catch (BindException e) {
				System.err.println("Could not open port "+port+" on host "+ip+": "+e.getMessage()+". Trying another one.");
			} catch (IOException e) {
				throw new ProgrammingException(ProgrammingException.IO_ERROR,e.getMessage(),e);
			}
		}
		return _dataSocket;
	}
}
