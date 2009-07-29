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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import jlib.log.Log;

public class DataFileReadWrite extends BaseDataFileBuffered
{
	private RandomAccessFile m_rw = null;
	private byte m_t1Byte[] = new byte[1];
	private LineRead m_lineRead = null;
	private final static int ms_nMaxRecordLength = 65536;
	private long m_lSavedPosition = -1;
	private byte m_tc[] = new byte [ms_nMaxRecordLength * 2];
	
	public DataFileReadWrite()
	{
	}
	
	public DataFileReadWrite(String csName)
	{
		m_csName = csName;
	}
		
//	public boolean open(String csName)
//	{
//		setName(csName);
//		return open();
//	}

	private boolean doOpen()
	{
		try
		{
			FileIOAccounting.startFileIO(FileIOAccountingType.Open);
			m_rw = new RandomAccessFile(getName(), "rw");
			FileIOAccounting.endFileIO();
			m_lineRead = new LineRead();
			initLineRead();
			return true;
		}
		catch (FileNotFoundException e)
		{
			FileIOAccounting.endFileIO();
			e.printStackTrace();
		} 
		return false;
	}
	
	public boolean open(LogicalFileDescriptor logicalFileDescriptor)
	{
		boolean bOpened = doOpen();
		if(bOpened && logicalFileDescriptor != null)
		{
			logicalFileDescriptor.readFileHeader(this);
		}
		return bOpened;
	}
	
	private void initLineRead()
	{
		m_lineRead.resetAndGaranteeBufferStorage(ms_nMaxRecordLength, ms_nMaxRecordLength);
	}

	public boolean close()
	{
		try
		{
			if(m_rw != null)
			{
				FileIOAccounting.startFileIO(FileIOAccountingType.Close);
				m_rw.close();
				FileIOAccounting.endFileIO();
				m_rw = null;
				return true;
			}
		}
		catch (IOException e)
		{
			FileIOAccounting.endFileIO();
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean flush()
	{
		try
		{
			if(m_rw != null)
			{		
				FileIOAccounting.startFileIO(FileIOAccountingType.Flush);
				m_rw.getFD().sync();
				FileIOAccounting.endFileIO();
				return true;
			}
		}
		catch (IOException e)
		{
			FileIOAccounting.endFileIO();
			e.printStackTrace();
		}
		return false;
	}

	public boolean isOpen()
	{
		if(m_rw != null)
			return true;
		return false;
	}
	
	public String toString()
	{
		String cs = m_csName + " (";
		if(isOpen())
		{
			cs += "Open RW";
		}
		else
		{
			cs += "Close";
		}
		cs += ")";
		return cs;
	}
	
	public void write(char c)
	{
		try
		{
			FileIOAccounting.startFileIO(FileIOAccountingType.Write);
			m_rw.write(c);
			FileIOAccounting.endFileIO();
		}
		catch (IOException e)
		{
			FileIOAccounting.endFileIO();
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public void write(byte[] tBytes, int nOffset, int nLength)
	{
		if(tBytes != null)
		{
			if(m_rw != null)
			{
				try
				{
					FileIOAccounting.startFileIO(FileIOAccountingType.Write);
					m_rw.write(tBytes, nOffset, nLength);
					FileIOAccounting.endFileIO();
				}
				catch (IOException e)
				{
					FileIOAccounting.endFileIO();
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
	}	
	
	public void writeRecord(String cs)
	{
		int nLg = cs.length();
		if(m_rw != null)
		{
			try
			{
				FileIOAccounting.startFileIO(FileIOAccountingType.Write);
				m_rw.write(cs.getBytes(), 0, nLg);
				m_rw.write((char)FileEndOfLine.LF);
				FileIOAccounting.endFileIO();
			}
			catch (IOException e)
			{	
				FileIOAccounting.endFileIO();
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}

	public void write(byte[] tBytes)
	{
		if(tBytes != null)
		{
			if(m_rw != null)
			{
				try
				{
					FileIOAccounting.startFileIO(FileIOAccountingType.Write);
					m_rw.write(tBytes, 0, tBytes.length);
					FileIOAccounting.endFileIO();
				}
				catch (IOException e)
				{
					FileIOAccounting.endFileIO();
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
			if(m_rw != null)
			{
				try
				{
					if(nSize+1 < tBytes.length)
					{
						tBytes[nSize] = FileEndOfLine.LF;
						FileIOAccounting.startFileIO(FileIOAccountingType.Write);
						m_rw.write(tBytes, 0, nSize+1);
						FileIOAccounting.endFileIO();
					}
					else
					{
						FileIOAccounting.startFileIO(FileIOAccountingType.Write);
						m_rw.write(tBytes, 0, nSize);
						m_rw.write(FileEndOfLine.LF);
						FileIOAccounting.endFileIO();
					}
				}
				catch (IOException e)
				{
					FileIOAccounting.endFileIO();
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	public void writeWithOptionalEOL(byte[] tBytes, int nSize, boolean bEndsCRLF, boolean bEndsLF)
	{
		if(tBytes != null)
		{
			if(m_rw != null)
			{
				try
				{
					if(bEndsCRLF)
					{
						if(nSize+2 < tBytes.length)
						{
							tBytes[nSize] = FileEndOfLine.CR;
							tBytes[nSize+1] = FileEndOfLine.LF;
							FileIOAccounting.startFileIO(FileIOAccountingType.Write);
							m_rw.write(tBytes, 0, nSize+2);
							FileIOAccounting.endFileIO();
						}
						else
						{
							FileIOAccounting.startFileIO(FileIOAccountingType.Write);
							m_rw.write(tBytes, 0, nSize);
							m_rw.write(FileEndOfLine.CR);
							m_rw.write(FileEndOfLine.LF);
							FileIOAccounting.endFileIO();
						}
					}
					else if(bEndsLF)
					{
						if(nSize+1 < tBytes.length)
						{
							tBytes[nSize] = FileEndOfLine.LF;
							FileIOAccounting.startFileIO(FileIOAccountingType.Write);
							m_rw.write(tBytes, 0, nSize+1);
							FileIOAccounting.endFileIO();
						}
						else
						{
							FileIOAccounting.startFileIO(FileIOAccountingType.Write);
							m_rw.write(tBytes, 0, nSize);
							m_rw.write(FileEndOfLine.LF);
							FileIOAccounting.endFileIO();
						}
					}
					else
					{
						FileIOAccounting.startFileIO(FileIOAccountingType.Write);
						m_rw.write(tBytes, 0, nSize);
						FileIOAccounting.endFileIO();
					}
				}
				catch (IOException e)
				{
					FileIOAccounting.endFileIO();
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	public void writeWithOptionalEOLMFCobolNotOptimized(byte[] tBytes, int nSize, boolean bEndsWithCRLF, boolean bEndsWithLF)
	{
		if(tBytes != null)
		{
			if(m_rw != null)
			{
				try
				{
					// Ignore trailing spaces
					int nPos = nSize-1;
					byte c = tBytes[nPos];
					while(c == 32 && nPos > 0)
					{
						nPos--;
						c = tBytes[nPos];
					}
					
					// Write chars, prefixing non disppalyable codes by 0x00
					FileIOAccounting.startFileIO(FileIOAccountingType.Write);
					for(int n=0; n<=nPos; n++)
					{
						c = tBytes[n];
						if(c >= 0 && c < 32)
							m_rw.write(0);
						m_rw.write(c);
					}
									
					// Add record terminaison
					if(bEndsWithCRLF)
					{
						m_rw.write(FileEndOfLine.CR);
						m_rw.write(FileEndOfLine.LF);
					}
					else if(bEndsWithLF)
					{
						m_rw.write(FileEndOfLine.LF);
					}
					FileIOAccounting.endFileIO();
				}
				catch (IOException e)
				{
					FileIOAccounting.endFileIO();
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	public void writeWithOptionalEOLMFCobol(byte[] tBytes, int nSize, boolean bEndsWithCRLF, boolean bEndsWithLF)
	{
		if(tBytes != null)
		{
			if(m_rw != null)
			{
				
				// Ignore trailing spaces
				int nPosEnd = nSize-1;
				byte c = tBytes[nPosEnd];
				while(c == 32 && nPosEnd > 0)
				{
					nPosEnd--;
					c = tBytes[nPosEnd];
				}
				
				int nPosDest=0;
				// Write chars, prefixing non displayable codes by 0x00 
				for(int nPosSource=0; nPosSource<=nPosEnd; nPosSource++)
				{
					c = tBytes[nPosSource];
					if(c >= 0 && c < 32)
						m_tc[nPosDest++] = 0;
					m_tc[nPosDest++] = c;
				}
				
				// Add record terminaison
				if(bEndsWithCRLF)
				{
					m_tc[nPosDest++] = FileEndOfLine.CR;
					m_tc[nPosDest++] = FileEndOfLine.LF;
				}
				else if(bEndsWithLF)
					m_tc[nPosDest++] = FileEndOfLine.LF;
				
				try
				{
					FileIOAccounting.startFileIO(FileIOAccountingType.Write);
					m_rw.write(m_tc, 0, nPosDest);
					FileIOAccounting.endFileIO();
				}
				catch (IOException e)
				{
					FileIOAccounting.endFileIO();
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
	}

	
	public void writeWithEOL(LineRead lineRead)
	{
		if(m_rw != null)
		{
			try
			{
				FileIOAccounting.startFileIO(FileIOAccountingType.Write);
				m_rw.write(lineRead.getBuffer(), lineRead.getOffset(), lineRead.getTotalLength());
				m_rw.write(FileEndOfLine.LF);
				FileIOAccounting.endFileIO();
			}
			catch (IOException e)
			{
				FileIOAccounting.endFileIO();
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
	
	public void writeEndOfRecordMarker()
	{
		if(m_rw != null)
		{
			try
			{
				FileIOAccounting.startFileIO(FileIOAccountingType.Write);
				m_rw.write(FileEndOfLine.LF);
				FileIOAccounting.endFileIO();
			}
			catch (IOException e)
			{
				FileIOAccounting.endFileIO();
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
	
	public boolean readEndOfLineMarker()
	{
		getFileCurrentPosition();
		int nByte = 0;
		if(m_rw != null)
		{
			try
			{
				FileIOAccounting.startFileIO(FileIOAccountingType.Read);
				nByte = m_rw.read();
				FileIOAccounting.endFileIO();
				if(nByte == -1)
				{
					setEOF(true);
					return false;
				}
				if(nByte == FileEndOfLine.LF)
				{
					setEOF(false);
					return true;	// Found EOL
				}
			}
			catch (IOException e)
			{
				FileIOAccounting.endFileIO();
				setEOF(true);
				return false;
			}
		}
		setEOF(true);
		return false;
	}
		
	public byte[] read(int nSize)
	{
		//getFileCurrentPosition();
		if(m_rw != null)
		{
			try
			{
				byte byteBuffer[] = getByteBuffer(nSize);
				FileIOAccounting.startFileIO(FileIOAccountingType.Read);
				int nNBytesRead = m_rw.read(byteBuffer, 0, nSize);
				FileIOAccounting.endFileIO();
				if(nNBytesRead == -1)
					setEOF(true);
				return byteBuffer;
			}
			catch (IOException e)
			{
				FileIOAccounting.endFileIO();
				e.printStackTrace();
			}			
		}
		return null;
	}
	
//	public int getUnixRecordLength()
//	{
//		getCurrentPosition();
//		int n = 0;
//		byte[] tVal = new byte[1];
//		if(m_rw != null)
//		{
//			try
//			{	
//				while(tVal[0] != FileEndOfLine.LF)
//				{
//					int nNBytesRead = m_rw.read(tVal, 0, 1);
//					if(nNBytesRead == -1)
//					{
//						setEOF(true);
//						return n;
//					}
//					n++;
//				}
//				return n;
//			}
//			catch (IOException e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}			
//		}
//		return n;
//	}
	
	private int readUnixLine(byte tBytes[], int nMaxLineSize)
	{
		if(m_rw != null)
		{
			try
			{
				int n = 0;
				m_t1Byte[0] = 0;
				FileIOAccounting.startFileIO(FileIOAccountingType.Read);
				while(m_t1Byte[0] != FileEndOfLine.LF)
				{
					int nNBytesRead = m_rw.read(m_t1Byte, 0, 1);
					if(nNBytesRead != -1)
						tBytes[n++] = m_t1Byte[0];
					else
					{
						FileIOAccounting.endFileIO();
						setEOF(true);						
						return n;
					}
				}
				FileIOAccounting.endFileIO();
				return n - 1;
			}
			catch (IOException e)
			{
				FileIOAccounting.endFileIO();
				e.printStackTrace();
				setEOF(true);
				return -1;
			}			
		}
		return 0;
	}
	
	private int readUnixLineMFCobol(byte tBytes[], int nMaxLineSize)
	{
		if(m_rw != null)
		{
			try
			{
				int n = 0;
				m_t1Byte[0] = 0;
				FileIOAccounting.startFileIO(FileIOAccountingType.Read);
				while(true)
				{
					int nNBytesRead = m_rw.read(m_t1Byte, 0, 1);
					if(nNBytesRead == -1)
					{
						FileIOAccounting.endFileIO();
						setEOF(true);
						return n;
					}
					if(m_t1Byte[0] != 0)
					{
						if(m_t1Byte[0] == FileEndOfLine.LF)
						{
							FileIOAccounting.endFileIO();						
							return n;
						}
						tBytes[n++] = m_t1Byte[0];
					}
					else	// Read prefix 00; ignore it
					{
						nNBytesRead = m_rw.read(m_t1Byte, 0, 1);
						if(nNBytesRead == -1)
						{
							FileIOAccounting.endFileIO();
							Log.logCritical("File format error: could not read byte following leading 00");
							setEOF(true);	// Should never happen
							return n;
						}
						tBytes[n++] = m_t1Byte[0];
					}
				}
			}
			catch (IOException e)
			{
				FileIOAccounting.endFileIO();
				e.printStackTrace();
				setEOF(true);
				return -1;
			}			
		}
		return 0;
	}
	
	private int readCRLFLine(byte tBytes[], int nMaxLineSize)
	{
		if(m_rw != null)
		{
			try
			{
				int n = 0;
				m_t1Byte[0] = 0;
				boolean bFound = false;
				FileIOAccounting.startFileIO(FileIOAccountingType.Read);
				while(!bFound)	//m_t1Byte[0] != FileEndOfLine.LF)
				{
					if(m_t1Byte[0] == FileEndOfLine.CR)	// Last char is a CR
					{							
						int nNBytesRead = m_rw.read(m_t1Byte, 0, 1);						
						if(nNBytesRead != -1)
							tBytes[n++] = m_t1Byte[0];
						else
						{
							FileIOAccounting.endFileIO();
							setEOF(true);
							return n;
						}
						if(m_t1Byte[0] == FileEndOfLine.LF)
							bFound = true;
					}
					else
					{
						int nNBytesRead = m_rw.read(m_t1Byte, 0, 1);
						if(nNBytesRead != -1)
							tBytes[n++] = m_t1Byte[0];
						else
						{
							FileIOAccounting.endFileIO();
							setEOF(true);
							return n;
						}
					}
				}
				FileIOAccounting.endFileIO();
				return n - 1;
			}
			catch (IOException e)
			{
				FileIOAccounting.endFileIO();
				e.printStackTrace();
				setEOF(true);
				return -1;
			}			
		}
		return 0;
	}
	
//	public int readUnixLine(byte tBytes[], int nOffset, int nMaxLineSize)
//	{
//		getCurrentPosition();
//		int n = nOffset;
//		byte[] tVal = new byte[1];
//		if(m_rw != null)
//		{
//			try
//			{	
//				while(tVal[0] != FileEndOfLine.LF)
//				{
//					int nNBytesRead = m_rw.read(tVal, 0, 1);
//					if(nNBytesRead != -1)
//						tBytes[n++] = tVal[0];
//					else
//					{
//						setEOF(true);
//						return n;
//					}
//				}
//				return n;
//			}
//			catch (IOException e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}			
//		}
//		return n;
//	}
	
	public int readChunk(byte tBytes[], int nNbBytes)
	{
		getFileCurrentPosition();
		int n = -1;
		if(m_rw != null && !isEOF())
		{
			try
			{	
				FileIOAccounting.startFileIO(FileIOAccountingType.Read);
				int nNBytesRead = m_rw.read(tBytes, 0, nNbBytes);
				FileIOAccounting.endFileIO();
				if(nNBytesRead == -1)
					setEOF(true);
				return nNBytesRead;
			}
			catch (IOException e)
			{
				FileIOAccounting.endFileIO();
				e.printStackTrace();
			}			
		}
		return n;
	}
	
	public int readChunk(byte tBytes[], int nOffset, int nNbBytes)
	{
		getFileCurrentPosition();
		int n = -1;
		if(m_rw != null && !isEOF())
		{
			try
			{	
				FileIOAccounting.startFileIO(FileIOAccountingType.Read);
				int nNBytesRead = m_rw.read(tBytes, nOffset, nNbBytes);
				FileIOAccounting.endFileIO();
				if(nNBytesRead == -1)
					setEOF(true);
				return nNBytesRead;
			}
			catch (IOException e)
			{
				FileIOAccounting.endFileIO();
				e.printStackTrace();
			}			
		}
		return n;
	}
	
	public LineRead readNextUnixLine()
	{
		if(m_rw == null)
			return null;

		long lLastPosition = getFileCurrentPosition();
		setLastPosition(lLastPosition);
		
		initLineRead();
			
		int nDataLength = readUnixLine(m_lineRead.getBuffer(), ms_nMaxRecordLength);
		if(nDataLength > 0)
		{
			m_lineRead.setDataLengthStartingAt0(nDataLength);
			return m_lineRead;
		}
		return null;
	}
	
	public LineRead readNextUnixLineMFCobol()
	{
		if(m_rw == null)
			return null;

		long lLastPosition = getFileCurrentPosition();
		setLastPosition(lLastPosition);
		
		initLineRead();
			
		int nDataLength = readUnixLineMFCobol(m_lineRead.getBuffer(), ms_nMaxRecordLength);
		if(nDataLength > 0)
		{
			m_lineRead.setDataLengthStartingAt0(nDataLength);
			return m_lineRead;
		}
		return null;
	}
	
	public LineRead readNextLineCRLFTerminated()
	{
		if(m_rw == null)
			return null;

		long lLastPosition = getFileCurrentPosition();
		setLastPosition(lLastPosition);
		
		initLineRead();
			
		int nDataLength = readCRLFLine(m_lineRead.getBuffer(), ms_nMaxRecordLength);
		if(nDataLength > 0)
		{
			m_lineRead.setDataLengthStartingAt0(nDataLength);
			return m_lineRead;
		}
		return null;
	}	

	
	public LineRead readBuffer(int nLength, boolean bTryReadNextLF)
	{		
		if(m_rw != null)
		{
			long lLastPosition = getFileCurrentPosition();
			setLastPosition(lLastPosition);
			
			int nFullLength = nLength;
			if(bTryReadNextLF)
				nFullLength++;
	
			initLineRead();
			int nLengthRead = readChunk(m_lineRead.getBuffer(), 0, nFullLength);
			if(nLengthRead >= 0)
			{
				m_lineRead.setDataLengthStartingAt0(nLengthRead);
				if(bTryReadNextLF)
					m_lineRead.manageTrailingLF();
				return m_lineRead;
			}
		}
		return null;
	}
	
	public LineRead readBufferOptionalEOL(int nLength, boolean bTryReadNextCRLF, boolean bTryReadNextLF)
	{
		if(m_rw != null)
		{
			long lLastPosition = getFileCurrentPosition();
			setLastPosition(lLastPosition);
			
			int nFullLength = nLength;
			if(bTryReadNextCRLF)
				nFullLength += 2;
			else if(bTryReadNextLF)
				nFullLength++;
	
			initLineRead();
			int nLengthRead = readChunk(m_lineRead.getBuffer(), 0, nFullLength);
			if(nLengthRead >= 0)
			{
				m_lineRead.setDataLengthStartingAt0(nLengthRead);
				if(bTryReadNextLF)
					m_lineRead.manageTrailingCRLF();
				else if(bTryReadNextLF)
					m_lineRead.manageTrailingLF();
				return m_lineRead;
			}
		}
		return null;	
	}


	
	public void rewrite(byte[] tBytes, int nOffset, int nLength)
	{
		long lLastPosition = getLastPosition();
		setFileCurrentPosition(lLastPosition);
		write(tBytes, nOffset, nLength);
	}
	
	public void rewriteWithEOL(byte[] tbyDest, int nSize)
	{
		long lLastPosition = getLastPosition();
		setFileCurrentPosition(lLastPosition);
		writeWithEOL(tbyDest, nSize);
	}
	
	public void rewriteWithOptionalEOL(byte[] tbyDest, int nSize, boolean bEndsCRLF, boolean bEndsLF)
	{
		long lLastPosition = getLastPosition();
		setFileCurrentPosition(lLastPosition);
		writeWithOptionalEOL(tbyDest, nSize, bEndsCRLF, bEndsLF);		
	}

	
	public boolean isReadable()
	{
		return true;
	}
	
	public boolean isWritable()
	{
		return true;
	}
	
	public boolean isUpdateable()
	{
		return true;
	}

	
	public long getFileCurrentPosition()
	{
		try
		{
			FileIOAccounting.startFileIO(FileIOAccountingType.Position);
			long lPos = m_rw.getFilePointer();
			FileIOAccounting.endFileIO();
			return lPos; 
		}
		catch (IOException e)
		{
			FileIOAccounting.endFileIO();
			return -1;
		}
	}

	public boolean setFileCurrentPosition(long lCurrentPosition)
	{
		try
		{
			FileIOAccounting.startFileIO(FileIOAccountingType.Position);
			m_rw.seek(lCurrentPosition);
			FileIOAccounting.endFileIO();
			return true;
		}
		catch (IOException e)
		{
			FileIOAccounting.endFileIO();
		}
		return false;
	}
	
	public boolean savePosition(int nMaxReadAheadSize)
	{
		m_lSavedPosition = getFileCurrentPosition();
		if(m_lSavedPosition >= 0)
			return true;
		return false;
	}
	
	public boolean returnAtSavedPosition()
	{
		if(m_lSavedPosition >= 0)
			return setFileCurrentPosition(m_lSavedPosition);
		return false;
	}
	
	public LineRead readVariableLengthLine(boolean bTryReadNextLF, boolean bHeaderIsInt, LineRead lineOut)	// Read a vairable length line (length is given in record header 4 bytes)
	{
		LineRead recordHeader = readBuffer(4, false);
		if(recordHeader != null)
		{
			int nLength = 0;
			if(bHeaderIsInt)
				nLength = recordHeader.getAsLittleEndingUnsignBinaryInt();
			else
				nLength = recordHeader.getAsLittleEndingUnsignBinaryShort();
			
			if(lineOut == null)
				lineOut = new LineRead();
			lineOut.resetAndGaranteeBufferStorage(4 + nLength + 1, 4 + nLength + 1);
			lineOut.append(recordHeader);
	
			LineRead recordBody = readBuffer(nLength, bTryReadNextLF);
			lineOut.append(recordBody);
			
			return lineOut;
		}
		return null;
	}
	
	public boolean setFileLength(long lFileLength)
	{
		try
		{
			FileIOAccounting.startFileIO(FileIOAccountingType.Position);
			m_rw.setLength(lFileLength);
			FileIOAccounting.endFileIO();
			return true;
		}
		catch (IOException e)
		{
			FileIOAccounting.endFileIO();
		}
		return false;
	}
	
	public boolean shinkFileAtCurrentPosition()
	{
		try
		{
			FileIOAccounting.startFileIO(FileIOAccountingType.Position);
			long lPos = m_rw.getFilePointer();				
			m_rw.setLength(lPos);
			FileIOAccounting.endFileIO();
			return true;
		}
		catch (IOException e)
		{
			FileIOAccounting.endFileIO();
		}
		return false;
	}
}
