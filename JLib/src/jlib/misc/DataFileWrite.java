/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.misc;

import java.io.*;
import java.nio.channels.*;

public class DataFileWrite extends BaseDataFileBuffered
{
	private BufferedOutputStream m_out = null;
	private FileLock m_outLock = null;
	private boolean m_bMustWriteFileHeader = false;
	
	public DataFileWrite(String csName, boolean bMustWriteFileHeader)
	{
		m_csName = csName;
		m_bMustWriteFileHeader = bMustWriteFileHeader;
	}
		
	public boolean open(String csName)
	{
		setName(csName);
		return open(false);
	}

	public boolean open()
	{
		return open(false);
	}
	
	public boolean openInAppend(String csName)
	{
		setName(csName);
		return open(true);
	}

	public boolean openInAppend(LogicalFileDescriptor logicalFileDescriptor)
	{
		return open(true, logicalFileDescriptor);
	}
	
	public boolean open(boolean bAppend)
	{
		try
		{
			FileOutputStream fileOutput = new FileOutputStream(getName(), bAppend);
			m_out = new BufferedOutputStream(new DataOutputStream(fileOutput));
			FileChannel outChannel = fileOutput.getChannel();
			try
			{
				m_outLock = outChannel.lock();
			}
			catch(IOException e)
			{
				e.printStackTrace();
				return false;	
			}
			return true;
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} 
		return false;
	}
	
	public boolean open(LogicalFileDescriptor logicalFileDescriptor)
	{
		return open(false, logicalFileDescriptor);
	}
	
	private boolean open(boolean bAppend, LogicalFileDescriptor logicalFileDescriptor)
	{
		boolean bOpened = open(bAppend);
		if(bOpened && logicalFileDescriptor != null)
		{
			if(bAppend)	// append something to the file: Read it's header as the file must already exists
				logicalFileDescriptor.readFileHeader(this);
			else 	// Create a new file
			{
				if(m_bMustWriteFileHeader)	// open the file in output not append with writing file header 
					logicalFileDescriptor.writeFileHeader(this);
			}				
		}
		return bOpened;
	}

	public boolean close()
	{
		try
		{
			if(m_out != null)
			{
				if(m_outLock != null)
				{
					m_outLock.release();
					m_outLock = null;
				}
				m_out.close();
				m_out = null;
				return true;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean flush()
	{
		try
		{
			if(m_out != null)
			{
				m_out.flush();
				return true;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public boolean isOpen()
	{
		if(m_out != null)
			return true;
		return false;
	}
	
	public String toString()
	{
		String cs = m_csName + " (";
		if(isOpen())
		{
			cs += "Open";
			if(m_out != null)
				cs += " Write";
		}
		else
		{
			cs += "Close";
		}
		cs += ")";
		return cs;
	}
	
	public void write(byte[] tBytes, int nOffset, int nLength)
	{
		if(tBytes != null)
		{
			if(m_out != null)
			{
				try
				{
					m_out.write(tBytes, nOffset, nLength);
				}
				catch (IOException e)
				{
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
	}	
	
	public void writeRecord(String cs)
	{
		int nLg = cs.length();
		if(m_out != null)
		{
			try
			{
				m_out.write(cs.getBytes(), 0, nLg);
				m_out.write((char)FileEndOfLine.LF);
			}
			catch (IOException e)
			{
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	public void write(byte[] tBytes)
	{
		if(tBytes != null)
		{
			if(m_out != null)
			{
				try
				{
					m_out.write(tBytes, 0, tBytes.length);
				}
				catch (IOException e)
				{
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	public void writeWithEOL(byte[] tBytes, int nSize)
	{
		if(tBytes != null)
		{
			if(m_out != null)
			{
				try
				{
					if(nSize+1 < tBytes.length)
					{
						tBytes[nSize] = FileEndOfLine.LF;
						m_out.write(tBytes, 0, nSize+1);
					}
					else
					{
						m_out.write(tBytes, 0, nSize);
						m_out.write(FileEndOfLine.LF);
					}
				}
				catch (IOException e)
				{
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	public void writeWithEOL(LineRead lineRead)
	{
		if(m_out != null)
		{
			try
			{
				m_out.write(lineRead.getBuffer(), lineRead.getOffset(), lineRead.getTotalLength());
				m_out.write(FileEndOfLine.LF);
			}
			catch (IOException e)
			{
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
	
	public void writeEndOfRecordMarker()
	{
		if(m_out != null)
		{
			try
			{
				m_out.write(FileEndOfLine.LF);
			}
			catch (IOException e)
			{
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
	
	public boolean readEndOfLineMarker()
	{
		return false;
	}
		
	public byte[] read(int nSize)
	{
		return null;
	}
	
	public int getUnixRecordLength()
	{
		return 0;
	}
	
	public int readUnixLine(byte tBytes[], int nMaxLineSize)
	{
		return 0;
	}
	
	public int readUnixLine(byte tBytes[], int nOffset, int nMaxLineSize)
	{
		return 0;
	}
	
	public int readChunk(byte tBytes[], int nNbBytes)
	{
		return 0;
	}
	
	public int readChunk(byte tBytes[], int nOffset, int nNbBytes)
	{
		return 0;
	}
	
	public byte[] readWholeFileAsArray()
	{
		return null;
	}
	
	public LineRead readNextUnixLine()
	{
		return null;
	}

	public LineRead readBuffer(int nLength, boolean bTryReadNextLF)
	{
		return null;
	}
	
	public void rewrite(byte[] tBytes, int nOffset, int nLength)
	{
		//write(tBytes, nOffset, nLength);
	}
	
	public void rewriteWithEOL(byte[] tbyDest, int nSize)
	{
		//rewriteWithEOL(tbyDest, nSize);
	}

	public boolean isReadable()
	{
		return false;
	}
	
	public boolean isWritable()
	{
		return true;
	}
	
	public boolean isUpdateable()
	{
		return false;
	}
	
	public long getFileCurrentPosition()
	{
		return -1;
	}
	
	public boolean setFileCurrentPosition(long lCurrentPosition)
	{
		return false;
	}
	
	public boolean savePosition(int nMaxReadAheadSize)
	{
		return false;
	}
	
	public boolean returnAtSavedPosition()
	{
		return false;		
	}
	
	public String unbufferedReadAheadLine(int nMaxReadAheadSize)
	{
		return null;
	}
	
	public LineRead readVariableLengthLine(boolean bTryReadNextLF, boolean bHeaderIsInt, LineRead lineOut)	// Read a vairable length line (length is given in record header 4 bytes)
	{
		return null;
	}
}
