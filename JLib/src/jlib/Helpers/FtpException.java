/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.Helpers;
//******************************************************************************
//**                 Describes exceptions thrown by the FTP server.           **
//******************************************************************************
/**
 * Exceptions thrown by the {@link FtpPassiveClient} class when the server
 * returns an error code.
 */
public class FtpException extends Exception {
//******************************************************************************
//**                                 Class properties.                        **
//******************************************************************************
	private static final long serialVersionUID = -8333814073500135218L;

/**
 * Returns the standard exception message.
 * The standard exception message is composed by all available data specified to the constructor:
 * <pre>
 *  [Specific Message]: Error code [XXXX] while executing command '[YYYY]' returned '[ZZZZ]'
 * </pre>
 */
	public String getMessage() {
		StringBuilder message=new StringBuilder(_specificMessage);
		if (_errorCode>0) {
			message.append("Error code ");
			message.append(_errorCode);
		}
		if (_command!=null) {
			message.append(" while executing '");
			message.append(_command);
			message.append("'");
		}
		if (_response!=null) {
			message.append(", server returned '");
			message.append(_response);
			message.append("'.");
		}
		return message.toString();
	}

/**
 * Contains the specific message (the 'message' parameter specified to the constructor).
 */
	private String _specificMessage;
/**
 * Returns the specific message (the 'message' parameter specified to the constructor).
 * @return The specific message (the 'message' parameter specified to the constructor).
 */
	public String getSpecificMessage() {
		return _specificMessage;
	}

/**
 * Contains the command that produced the error.
 */
	private String _command;
	public String getCommand() {
		return _command;
	}

/**
 * Contains (if available) the error code returned by the server.
 */
	private int _errorCode;
/**
 * Returns (if available) the error code returned by the server.
 * Ftp error codes are:
 * <ul>
 * 	<li><b>110</b>:  Restart marker reply. In this case, the text is exact and not left to the particular implementation; it must read:      MARK yyyy = mmmm Where yyyy is User-process data stream marker, and mmmm server's equivalent marker (note the spaces between markers and "=").</li>
 * 	<li><b>120</b>:  Service ready in nnn minutes.</li>
 * 	<li><b>125</b>:  Data connection already open; transfer starting.</li>
 * 	<li><b>150</b>:  File status okay; about to open data connection.</li>
 * 	<li><b>200</b>:  Command okay.</li>
 * 	<li><b>202</b>:  Command not implemented, superfluous at this site.</li>
 * 	<li><b>211</b>:  System status, or system help reply.</li>
 * 	<li><b>212</b>:  Directory status.</li>
 * 	<li><b>213</b>:  File status.</li>
 * 	<li><b>214</b>:  Help message. On how to use the server or the meaning of a particular non-standard command.  This reply is useful only to the human user.</li>
 * 	<li><b>215</b>:  NAME system type. Where NAME is an official system name from the list in the Assigned Numbers document.</li>
 * 	<li><b>220</b>:  Service ready for new user.</li>
 * 	<li><b>221</b>:  Service closing control connection. Logged out if appropriate.</li>
 * 	<li><b>225</b>:  Data connection open; no transfer in progress.</li>
 * 	<li><b>226</b>:  Closing data connection. Requested file action successful (for example, file transfer or file abort).</li>
 * 	<li><b>227</b>:  Entering Passive Mode (h1,h2,h3,h4,p1,p2).</li>
 * 	<li><b>230</b>:  User logged in, proceed.</li>
 * 	<li><b>250</b>:  Requested file action okay, completed.</li>
 * 	<li><b>257</b>:  "PATHNAME" created.</li>
 * 	<li><b>331</b>:  User name okay, need password.</li>
 * 	<li><b>332</b>:  Need account for login.</li>
 * 	<li><b>350</b>:  Requested file action pending further information.</li>
 * 	<li><b>421</b>:  Service not available, closing control connection. This may be a reply to any command if the service knows it must shut down.</li>
 * 	<li><b>425</b>:  Can't open data connection.</li>
 * 	<li><b>426</b>:  Connection closed; transfer aborted.</li>
 * 	<li><b>450</b>:  Requested file action not taken. File unavailable (e.g., file busy).</li>
 * 	<li><b>451</b>:  Requested action aborted: local error in processing.</li>
 * 	<li><b>452</b>:  Requested action not taken. Insufficient storage space in system.</li>
 * 	<li><b>500</b>:  Syntax error, command unrecognized. This may include errors such as command line too long.</li>
 * 	<li><b>501</b>:  Syntax error in parameters or arguments.</li>
 * 	<li><b>502</b>:  Command not implemented.</li>
 * 	<li><b>503</b>:  Bad sequence of commands.</li>
 * 	<li><b>504</b>:  Command not implemented for that parameter.</li>
 * 	<li><b>530</b>:  Not logged in.</li>
 * 	<li><b>532</b>:  Need account for storing files.</li>
 * 	<li><b>550</b>:  Requested action not taken. File unavailable (e.g., file not found, no access).</li>
 * 	<li><b>551</b>:  Requested action aborted: page type unknown.</li>
 * 	<li><b>552</b>:  Requested file action aborted. Exceeded storage allocation (for current directory or dataset).</li>
 * 	<li><b>553</b>:  Requested action not taken. File name not allowed.
 * </ul>
 * @return (if available) the error code returned by the server.
 */
	public int getErrorCode() {
		return _errorCode;
	}

/**
 * Contains the response returned by the server.
 * The response starts with 3 numbers specifying the error code.
 */
	private String _response;
/**
 * Returns (if available) the complete server response containing the error.
 * @return (if available) the complete server response containing the error.
 */
	public String getResponse() {
		return _response;
	}

//******************************************************************************
//**                      Class initialization.                               **
//******************************************************************************
	private void initialize(String message,String response,String command) {
		_specificMessage=message;
		_response=response;
		_command=command;

//........ Tries to extract the error code from the response ...................
		if (response!=null) {
			int n,nn;
			char c;
			nn=response.length();
			for(n=0;n<nn;n++) {
				c=response.charAt(n);
				if (c<'0' || c>'9')
					break;
			}
			if (n>0)
				_errorCode=Integer.parseInt(response.substring(0,n));
		}		
	}

//******************************************************************************
//**                                  Class constructors.                     **
//******************************************************************************
/**
 * Creates a FTP exception.
 * @param message Specific message describing the circumstances when the error has been received. 
 */
	public FtpException(String message) {
		super(message);
		initialize(message,null,null);
	}

/**
 * Creates a FTP exception.
 * @param message Specific message describing the circumstances when the error has been received.
 * @param response The response containing the error returned by the FTP server. 
 */
	public FtpException(String message, String response) {
		super(message);
		initialize(message,response,null);
	}

/**
 * Creates a FTP exception.
 * @param message Specific message describing the circumstances when the error has been received.
 * @param response The response containing the error returned by the FTP server.
 * @param command The command that produced the error. 
 */
	public FtpException(String message, String response, String command) {
		super(message);
		initialize(message,response,command);
	}
}
