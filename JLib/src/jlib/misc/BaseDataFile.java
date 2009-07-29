/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.misc;


/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public abstract class BaseDataFile
{
	protected String m_csName = null;
	private boolean m_bEOF = false;
	
	protected BaseDataFile()
	{
		
	}
	
	public static boolean isNullFile(String csFilePhysicalName)
	{
		if(StringUtil.isEmpty(csFilePhysicalName))
			return true;
		if(csFilePhysicalName.equalsIgnoreCase("wrk/nullfile"))
			return true;
		if(csFilePhysicalName.toUpperCase().indexOf("NULLFILE") >= 0)
			return true;
		return false;
	}
	
	
	public void setName(String csName)
	{
		m_csName = csName;
	}
	
	public String getName()
	{
		return m_csName;
	}
	
	public boolean isEOF()
	{
		return m_bEOF;
	}
	
	public void setEOF(boolean b)
	{
		m_bEOF = b;
	}
	
	//public abstract boolean open();
	public abstract boolean open(LogicalFileDescriptor logicalFileDescriptor);

	public abstract boolean flush();
	public abstract boolean close();
	
	public abstract boolean isOpen();
	
	public abstract void writeEndOfRecordMarker();
	public abstract void writeWithEOL(byte[] tBytes, int nSize);
	public abstract void writeWithEOL(LineRead lineRead);
	
	public abstract void writeWithOptionalEOL(byte[] tBytes, int nSize, boolean bEndsCRLF, boolean bEndsLF);
	public abstract void writeWithOptionalEOLMFCobol(byte[] tBytes, int nSize, boolean bEndsCRLF, boolean bEndsLF);
	
	
	public abstract void write(char c);
	public abstract void write(byte[] tBytes);
	public abstract void write(byte[] tBytes, int nOffset, int nLength);
	public abstract void writeRecord(String cs);
	
	public abstract LineRead readVariableLengthLine(boolean bTryReadNextLF, boolean bHeaderIsInt, LineRead lineOut);	// Read a variable length line (length is given in record header 4 bytes)
	public abstract LineRead readNextUnixLine();
	public abstract LineRead readNextUnixLineMFCobol();
	public abstract LineRead readNextLineCRLFTerminated();
	
	public abstract LineRead readBuffer(int nLineLength, boolean bTryReadNextLF);
	public abstract LineRead readBufferOptionalEOL(int nLineLength, boolean bTryReadNextCRLF, boolean bTryReadNextLF);
	public abstract byte[] read(int nSize);
	public abstract boolean readEndOfLineMarker();
	public abstract boolean savePosition(int nMaxReadAheadSize);
	public abstract boolean returnAtSavedPosition();
	
	public abstract byte[] getByteBuffer(int nSize);
	//public abstract byte[] getAlternateByteBuffer(int nSize);
	
	public abstract void rewrite(byte[] tBytes, int nOffset, int nLength);
	public abstract void rewriteWithEOL(byte[] tbyDest, int nSize);
	public abstract void rewriteWithOptionalEOL(byte[] tbyDest, int nSize, boolean bEndsCRLF, boolean bEndsLF);
	//public abstract long getFileSize();

	
	public abstract boolean isReadable();
	public abstract boolean isWritable();
	public abstract boolean isUpdateable();
	
	public abstract long getFileCurrentPosition();
	public abstract boolean setFileCurrentPosition(long lCurrentPosition);
	
	
	public long getLastPosition()
	{
		return m_lLastPosition;
	}
	
	public void setLastPosition(long l)
	{
		m_lLastPosition = l;
	}
	
	private long m_lLastPosition = 0;
	
	
	public String unbufferedReadAheadLine(int nMaxReadAheadSize)
	{
		String cs = null;
		if(savePosition(nMaxReadAheadSize))
		{		
			byte[] tBytes = read(nMaxReadAheadSize);
			if (tBytes != null)
			{	
				for(int nPos=0; nPos<tBytes.length && nPos < nMaxReadAheadSize; nPos++)
				{
					if(tBytes[nPos] == FileEndOfLine.LF)
					{
						cs = new String(tBytes, 0, nPos);
						break;
					}
				}
				if(returnAtSavedPosition())
					return cs;			
			}
		}
		return null;
	}
	
	public int skipFileHeader(String cs)
	{
		// Reread the header, to set current position just after header
		int nHeaderLength = cs.length() + 1;	// Skip header trailing LF
		read(nHeaderLength);
		return nHeaderLength;
	}
}