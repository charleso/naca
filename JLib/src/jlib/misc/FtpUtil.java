/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.misc;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPListParseEngine;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtil
{
	private FTPClient m_ftp = new FTPClient();

	public boolean connect(String url, String user, String password, String siteCommand)
	{
		try
		{
			m_ftp.connect(url);
			int reply = m_ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply))
			{
				disconnect();
				return false;
			}
			if (m_ftp.login(user, password))
			{
				reply = m_ftp.getReplyCode();
				if (!FTPReply.isPositiveCompletion(reply))
				{
					disconnect();
					return false;
				}
				if (siteCommand != null && !siteCommand.equals("")) 
				{
					String[] siteCommandSplit = siteCommand.split(";");
					for (int i=0; i < siteCommandSplit.length; i++) 
					{
						m_ftp.sendSiteCommand(siteCommandSplit[i]);
						reply = m_ftp.getReplyCode();
						if(!FTPReply.isPositiveCompletion(reply)) 
						{
							disconnect();
							return false;
						}
					}
				}
			}
			else
			{
				disconnect();
				return false;
			}
		}
		catch (Exception ex)
		{
			disconnect();
			return false;
		}
		return true;
	}

	public boolean changeWorkingDirectory(String pathname)
	{
		try
		{
			while (!m_ftp.printWorkingDirectory().equals("''")
					&& !m_ftp.printWorkingDirectory().equals("/"))
			{
				m_ftp.changeToParentDirectory();
			}
			m_ftp.changeWorkingDirectory(pathname);
			int reply = m_ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply))
			{
				return false;
			}
		}
		catch (Exception ex)
		{
			return false;
		}
		return true;
	}
	
	public boolean makeDirectory(String directory)
	{
		try
		{
			m_ftp.makeDirectory(directory);
			
			int reply = m_ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply))
			{
				return false;
			}
		}
		catch (Exception ex)
		{
			return false;
		}
		return true;
	}

	public boolean storeFile(String localFile, String remoteFile, boolean binary)
	{
		return storeFile(localFile, remoteFile, binary, null, 0);
	}
	public boolean storeFile(String localFile, String remoteFile, boolean binary, String format, int length)
	{
		try
		{
			InputStream is = new FileInputStream(localFile);
			if (binary)
			{
				HostFileInputStream his = new HostFileInputStream(is, format, length);
				is = his;
			}
			boolean isOk = storeFile(is, remoteFile);
	    	is.close();
	    	return isOk;
		}
		catch (Exception ex)
		{
			return false;
		}
	}
	public boolean storeFile(InputStream localFile, String remoteFile) 
	{
		try 
		{
			m_ftp.storeFile(remoteFile, localFile);
			int reply = m_ftp.getReplyCode();
			if(!FTPReply.isPositiveCompletion(reply)) 
			{
				return false;
			}
	    }
		catch (Exception ex) 
		{
	    	return false;
	    }
		return true;
	}

	public boolean retrieveFile(String localFile, String remoteFile, boolean binary)
	{
		return retrieveFile(localFile, remoteFile, binary, null, false);
	}
	public boolean retrieveFile(String localFile, String remoteFile, boolean binary, String format, boolean header)
	{
		try
		{
			OutputStream os = new FileOutputStream(localFile);
			if (binary)
			{
				HostFileOuputStream hof = new HostFileOuputStream(os, format, header);
				os = hof;
			}
			boolean isOk = retrieveFile(os, remoteFile);
			os.close();
			return isOk;
		}
		catch (Exception ex)
		{
			return false;
		}
	}
	public boolean retrieveFile(OutputStream localFile, String remoteFile) 
	{
		try 
		{
			m_ftp.retrieveFile(remoteFile, localFile);
			int reply = m_ftp.getReplyCode();
			if(!FTPReply.isPositiveCompletion(reply)) 
			{
				return false;
			}
		} 
		catch (Exception ex) 
		{
			return false;
		}
		return true;
	}
	
	public boolean deleteFile(String pathname)
	{
		try 
		{
			m_ftp.deleteFile(pathname);
			int reply = m_ftp.getReplyCode();
			if(!FTPReply.isPositiveCompletion(reply)) 
			{
				return false;
			}
		} 
		catch (Exception ex) 
		{
			return false;
		}
		return true;
	}

	public boolean setFileTransferMode(int mode)
	{
		try
		{
			m_ftp.setFileTransferMode(mode);
		}
		catch (Exception ex)
		{
			return false;
		}
		return true;
	}

	public boolean setFileType(int type)
	{
		try
		{
			m_ftp.setFileType(type);
		}
		catch (Exception ex)
		{
			return false;
		}
		return true;
	}
	
	public FTPFile[] listFiles(String entry)
	{
		FTPFile[] files = null;
		try 
		{
			FTPListParseEngine engine = m_ftp.initiateListParsing(entry);
			files = engine.getFiles();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			return null;
		}
		return files;
	}
	public FTPFile[] listFiles(String className, String entry)
	{
		FTPFile[] files = null;
		try 
		{
			FTPListParseEngine engine = m_ftp.initiateListParsing(className, entry);
			files = engine.getFiles();
		}
		catch (Exception ex) 
		{
			ex.printStackTrace();
			return null;
		}
		return files;
	}

	public boolean disconnect()
	{
		try
		{
			if (m_ftp.isConnected())
			{
				m_ftp.disconnect();
			}
		}
		catch (Exception ex)
		{
			return false;
		}
		return true;
	}
}