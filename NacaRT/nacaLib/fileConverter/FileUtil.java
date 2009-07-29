/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.fileConverter;

import java.nio.ByteBuffer;

import jlib.misc.DataFileLineReader;
import jlib.misc.DataFileWrite;
import jlib.misc.FileSystem;
import jlib.misc.LineRead;
import jlib.misc.LittleEndingSignBinaryBufferStorage;
import jlib.misc.LogicalFileDescriptor;
import nacaLib.varEx.FileDescriptor;

public class FileUtil
{
	private FileDescriptor m_file = null;
	
	private int m_nSequencer = 0;
	private int m_nCommandNext = -1;
	
	private boolean m_bCount = false;
	private boolean m_bReplace = false;
	private boolean m_bDelete = false;
	private boolean m_bExtract = false;
	
	private boolean m_bList = false;
	
	private int m_nLineBegin = 0;
	private int m_nLineEnd = 0;
	private int m_nLast = 0;
	private int m_nColBegin = 0;
	private int m_nColEnd = 0;
	private String m_csValue = null;
	private String m_csValueHex = null;
	private String m_csValueNew = null;
	private String m_csValueHexNew = null;
	private ByteBuffer[] m_arrByteValue;
	private byte[] m_arrByteValueNew;	
	
	private boolean m_bKeepOutputFile = false;
	private boolean m_bDebug = false;
	
	private int m_nLine = 0;
	private int m_nLineCount = 0;
	
	DataFileWrite m_fileOutput;

	public FileUtil(FileDescriptor file)
	{
		m_file = file;
	}
		
	public boolean execute(String csParameter)
	{
		String csParameterUpper = csParameter.toUpperCase();
		if (csParameterUpper.indexOf("LIST") != -1)
		{
			m_bList = true;
		}		
		if (csParameterUpper.indexOf("KEEPOUTPUTFILE") != -1)
		{
			m_bKeepOutputFile = true;
		}		
		if (csParameterUpper.indexOf("DEBUG") != -1)
		{
			m_bDebug = true;
		}

		int nCount = csParameterUpper.indexOf("COUNT");
		int nReplace = csParameterUpper.indexOf("REPLACE");
		int nDelete = csParameterUpper.indexOf("DELETE");
		int nExtract = csParameterUpper.indexOf("EXTRACT");
		
		if (nReplace == -1 && nDelete == -1 && nExtract == - 1)
		{
			if (nCount == -1)
			{
				System.out.println("FileUtil: No commands found");
				return false;
			}
		}
		else if (nCount != -1)
		{
			System.out.println("FileUtil: Command count and others not compatible");
			return false;
		}
		
		int nPosStart = -1;
		while (true)
		{
			m_bCount = false;
			m_bReplace = false;
			m_bDelete = false;
			m_bExtract = false;
			
			m_nLineBegin = 0;
			m_nLineEnd = 0;
			m_nLast = 0;
			m_nColBegin = 0;
			m_nColEnd = 0;
			m_csValue = null;
			m_csValueHex = null;
			m_csValueNew = null;
			m_csValueHexNew = null;
			m_arrByteValue = null;
			m_arrByteValueNew = null;
			
			if (nCount != -1 && (nReplace == -1 || nCount < nReplace) && (nDelete == -1 || nCount < nDelete) && (nExtract == -1 || nCount < nExtract))
			{
				m_bCount = true;
				nPosStart = nCount + 1;
			}
			else if (nReplace != -1 && (nCount == -1 || nReplace < nCount) && (nDelete == -1 || nReplace < nDelete) && (nExtract == -1 || nReplace < nExtract))
			{
				m_bReplace = true;
				nPosStart = nReplace + 1;
			}
			else if (nDelete != -1 && (nCount == -1 || nDelete < nCount) && (nReplace == -1 || nDelete < nReplace) && (nExtract == -1 || nDelete < nExtract))
			{
				m_bDelete = true;
				nPosStart = nDelete + 1;
			}
			else
			{
				m_bExtract = true;
				nPosStart = nExtract + 1;
			}
			
			nReplace = csParameterUpper.indexOf("REPLACE", nPosStart);
			nDelete = csParameterUpper.indexOf("DELETE", nPosStart);
			nExtract = csParameterUpper.indexOf("EXTRACT", nPosStart);
			
			if (nReplace != -1 && (nDelete == -1 || nReplace < nDelete) && (nExtract == -1 || nReplace < nExtract))
				m_nCommandNext = nReplace;
			else if (nDelete != -1 && (nReplace == -1 || nDelete < nReplace) && (nExtract == -1 || nDelete < nExtract))
				m_nCommandNext = nDelete;
			else if (nExtract != -1 && (nReplace == -1 || nExtract < nReplace) && (nDelete == -1 || nExtract < nDelete))
				m_nCommandNext = nExtract;
			else
				m_nCommandNext = -1;
			
			String csParameterPart = csParameter;
			if (m_nCommandNext == -1)
				csParameterPart = csParameterPart.substring(nPosStart);
			else
				csParameterPart = csParameterPart.substring(nPosStart, m_nCommandNext);
			String csParameterPartUpper = csParameterPart.toUpperCase();
			
			if (csParameterPartUpper.indexOf("VALUE=") != -1)
			{
				int nPos = csParameterPartUpper.indexOf("VALUE=") + 6;
				int nPosEnd = csParameterPartUpper.indexOf(",", nPos);
				if (nPosEnd == -1)
					m_csValue = csParameterPart.substring(nPos);
				else
					m_csValue = csParameterPart.substring(nPos, nPosEnd);
				String values[] = m_csValue.split("#or#");
				m_arrByteValue = new ByteBuffer[values.length];
				for (int i = 0; i < values.length; i++)
				{
					m_arrByteValue[i] = ByteBuffer.wrap(values[i].getBytes());
				}	
			}
			if (csParameterPartUpper.indexOf("VALUEHEX=") != -1)
			{
				int nPos = csParameterPartUpper.indexOf("VALUEHEX=") + 9;
				int nPosEnd = csParameterPartUpper.indexOf(",", nPos);
				if (nPosEnd == -1)
					m_csValueHex = csParameterPart.substring(nPos);
				else
					m_csValueHex = csParameterPart.substring(nPos, nPosEnd);
				String values[] = m_csValueHex.split("#or#");
				m_arrByteValue = new ByteBuffer[values.length];
				for (int i = 0; i < values.length; i++)
				{
					m_arrByteValue[i] = ByteBuffer.wrap(hexToBytes(values[i]));
				}
			}
			if (csParameterPartUpper.indexOf("VALUENEW=") != -1)
			{
				int nPos = csParameterPartUpper.indexOf("VALUENEW=") + 9;
				int nPosEnd = csParameterPartUpper.indexOf(",", nPos);
				if (nPosEnd == -1)
					m_csValueNew = csParameterPart.substring(nPos);
				else
					m_csValueNew = csParameterPart.substring(nPos, nPosEnd);
				m_arrByteValueNew = m_csValueNew.getBytes();
			}
			if (csParameterPartUpper.indexOf("VALUEHEXNEW=") != -1)
			{
				int nPos = csParameterPartUpper.indexOf("VALUEHEXNEW=") + 12;
				int nPosEnd = csParameterPartUpper.indexOf(",", nPos);
				if (nPosEnd == -1)
					m_csValueHexNew = csParameterPart.substring(nPos);
				else
					m_csValueHexNew = csParameterPart.substring(nPos, nPosEnd);
				m_arrByteValueNew = hexToBytes(m_csValueHexNew);
			}
			
			if (csParameterPartUpper.indexOf("LINE=") != -1)
			{
				int nPos = csParameterPartUpper.indexOf("LINE=") + 5;
				int nPosEnd = csParameterPartUpper.indexOf(",", nPos);
				if (nPosEnd == -1)
					m_nLineBegin = new Integer(csParameterPart.substring(nPos)).intValue();
				else
					m_nLineBegin = new Integer(csParameterPart.substring(nPos, nPosEnd)).intValue();
			}		
			if (csParameterPartUpper.indexOf("LINEEND=") != -1)
			{
				int nPos = csParameterPartUpper.indexOf("LINEEND=") + 8;
				int nPosEnd = csParameterPartUpper.indexOf(",", nPos);
				if (nPosEnd == -1)
					m_nLineEnd = new Integer(csParameterPart.substring(nPos)).intValue();
				else
					m_nLineEnd = new Integer(csParameterPart.substring(nPos, nPosEnd)).intValue();
			}

			if (csParameterPartUpper.indexOf("FIRST=") != -1)
			{
				int nPos = csParameterPartUpper.indexOf("FIRST=") + 6;
				int nPosEnd = csParameterPartUpper.indexOf(",", nPos);
				int nFirst = 0;
				if (nPosEnd == -1)
					nFirst = new Integer(csParameterPart.substring(nPos)).intValue();
				else
					nFirst = new Integer(csParameterPart.substring(nPos, nPosEnd)).intValue();
				m_nLineBegin = 0;
				m_nLineEnd = nFirst - 1;
			}
			if (csParameterPartUpper.indexOf("LAST=") != -1)
			{
				int nPos = csParameterPartUpper.indexOf("LAST=") + 5;
				int nPosEnd = csParameterPartUpper.indexOf(",", nPos);
				if (nPosEnd == -1)
					m_nLast = new Integer(csParameterPart.substring(nPos)).intValue();
				else
					m_nLast = new Integer(csParameterPart.substring(nPos, nPosEnd)).intValue();
			}

			if (csParameterPartUpper.indexOf("COL=") != -1)
			{
				int nPos = csParameterPartUpper.indexOf("COL=") + 4;
				int nPosEnd = csParameterPartUpper.indexOf(",", nPos);
				if (nPosEnd == -1)
					m_nColBegin = new Integer(csParameterPart.substring(nPos)).intValue();
				else
					m_nColBegin = new Integer(csParameterPart.substring(nPos, nPosEnd)).intValue();
			}			
			if (csParameterPartUpper.indexOf("COLEND=") != -1)
			{
				int nPos = csParameterPartUpper.indexOf("COLEND=") + 7;
				int nPosEnd = csParameterPartUpper.indexOf(",", nPos);
				if (nPosEnd == -1)
					m_nColEnd = new Integer(csParameterPart.substring(nPos)).intValue();
				else
					m_nColEnd = new Integer(csParameterPart.substring(nPos, nPosEnd)).intValue();
			}
			
			if (m_bReplace)
			{
				if (m_csValue != null || m_csValueNew != null)
				{
					if (m_csValue == null)
					{
						System.out.println("FileUtil: Replace all chars by \"" + m_csValueNew + "\"");
					}
					else
					{
						System.out.println("FileUtil: Replace \"" + m_csValue + "\" by \"" + m_csValueNew + "\"");
					}
				}
				else if (m_csValueHex != null || m_csValueHexNew != null)
				{
					if (m_csValueHex == null)
					{
						System.out.println("FileUtil: Replace all chars by hex \"" + m_csValueHexNew + "\"");
					}
					else
					{
						System.out.println("FileUtil: Replace hex \"" + m_csValueHex + "\" by \"" + m_csValueHexNew + "\"");
					}
				}
				else
				{
					// error
				}
			}
			else if (m_bDelete)
			{
				if (m_csValue != null)
					System.out.println("FileUtil: Delete when record contains \"" + m_csValue + "\"");
				else if (m_csValueHex != null)
					System.out.println("FileUtil: Delete when record contains hex \"" + m_csValueHex + "\"");
				else
					System.out.println("FileUtil: Delete");
			}
			else if (m_bExtract)
			{
				if (m_csValue != null)
					System.out.println("FileUtil: Extract when record contains \"" + m_csValue + "\"");
				else if (m_csValueHex != null)
					System.out.println("FileUtil: Extract when record contains hex \"" + m_csValueHex + "\"");
				else
					System.out.println("FileUtil: Extract");
			}
			else if (m_bCount)
			{
				System.out.println("FileUtil: Count");
			}
			else
			{
				System.out.println("FileUtil: No treatment");
				return false;
			}

			if (m_nLineBegin != 0 || m_nLineEnd != 0)
			{
				if (m_nLineBegin == 0)
					System.out.println("FileUtil: From begin of file to line " + m_nLineEnd);
				else if (m_nLineEnd == 0)
					System.out.println("FileUtil: From line " + m_nLineBegin + " to end of file");
				else
					System.out.println("FileUtil: From line " + m_nLineBegin + " to line " + m_nLineEnd);
			}
			if (m_nColBegin != 0 || m_nColEnd != 0)
			{
				if (m_nColBegin == 0)
					System.out.println("FileUtil: From begin of record to column " + m_nColEnd);
				else if (m_nColEnd == 0)
					System.out.println("FileUtil: From column " + m_nColBegin + " to end of record");
				else
					System.out.println("FileUtil: From column " + m_nColBegin + " to column " + m_nColEnd);
			}
			
			try
			{
				if (m_bList)
				{
					String csFileList = m_file.getPhysicalName();
					DataFileLineReader dataFileList = new DataFileLineReader(csFileList, 65536, 0);
					boolean bConvOpened = dataFileList.open();
					if(bConvOpened)
					{
						LineRead lineRead;
						while((lineRead = m_file.readALine(dataFileList, null)) != null)
						{
							String csFile = lineRead.getChunkAsString().trim();
							util(csFile);
						}
						dataFileList.close();
					}
				}
				else
				{
					String csFile = m_file.getPhysicalName();
					util(csFile);
				}
			}
			catch(Exception ex)
			{
				System.out.println("FileUtil: Error in line " + m_nLine);
				throw new RuntimeException(ex);
			}
			
			if (m_nCommandNext == -1)
				break;
			
			m_nSequencer++;
		}
		
		if (m_bList)
		{
			String csFileList = m_file.getPhysicalName();
			DataFileLineReader dataFileList = new DataFileLineReader(csFileList, 65536, 0);
			boolean bConvOpened = dataFileList.open();
			if(bConvOpened)
			{
				LineRead lineRead;
				while((lineRead = m_file.readALine(dataFileList, null)) != null)
				{
					String csFile = lineRead.getChunkAsString().trim();
					fileEnd(csFile);
				}
				dataFileList.close();
			}
		}
		else
		{
			String csFile = m_file.getPhysicalName();
			fileEnd(csFile);
		}

		return true;
	}

	private boolean util(String csFile)
	{
		m_nLine = 0;
		String csFileIn = csFile;
		if (m_nSequencer != 0)
			csFileIn += ".util." + (m_nSequencer - 1);
		DataFileLineReader dataFileIn = new DataFileLineReader(csFileIn, 65536, 0);
		LogicalFileDescriptor logicalFileDescriptor = new LogicalFileDescriptor("", csFileIn);
		if(logicalFileDescriptor != null)
		{
			boolean bInOpened = dataFileIn.open(logicalFileDescriptor);
			if(bInOpened)
			{	
				if (!logicalFileDescriptor.isLengthInfoDefined())
				{
					logicalFileDescriptor.tryAutoDetermineRecordLength(dataFileIn);
				}
				if (!m_bCount)
					fileOutputOpen(csFile);
				if (logicalFileDescriptor.isVariableLength())
				{
					if (m_nLast != 0)
					{
						LineRead lineHeader = dataFileIn.readBuffer(4, false);
						while (lineHeader != null)
						{
							m_nLine++;
							int nLengthExcludingHeader = lineHeader.getAsLittleEndingUnsignBinaryInt();
							LineRead lineRead = dataFileIn.readBuffer(nLengthExcludingHeader, true);
							lineHeader = dataFileIn.readBuffer(4, false);
						}
						m_nLineBegin = m_nLine - m_nLast + 1;
						if (m_nLineBegin < 0)
							m_nLineBegin = 0;
						m_nLineEnd = m_nLine;
						m_nLine = 0;
						dataFileIn.close();
						dataFileIn.open(logicalFileDescriptor);
					}
					byte[] tbyHeader = new byte[4];
					LineRead lineHeader = dataFileIn.readBuffer(4, false);
					while (lineHeader != null)
					{
						int nLengthExcludingHeader = lineHeader.getAsLittleEndingUnsignBinaryInt();
						LittleEndingSignBinaryBufferStorage.writeInt(tbyHeader, nLengthExcludingHeader, 0);
						LineRead lineRead = dataFileIn.readBuffer(nLengthExcludingHeader, true);
						if (utilNext(dataFileIn, lineRead, tbyHeader))
							break;
						lineHeader = dataFileIn.readBuffer(4, false);
					}
				}
				else
				{
					if (logicalFileDescriptor.getRecordLengthDefinition() != null)
					{	
						int iLength = logicalFileDescriptor.getRecordLengthDefinition().getRecordLength();
						if (m_nLast != 0)
						{
							LineRead lineRead = dataFileIn.readBuffer(iLength, true);
							while (lineRead != null)
							{
								m_nLine++;
								lineRead = dataFileIn.readBuffer(iLength, true);
							}
							m_nLineBegin = m_nLine - m_nLast + 1;
							if (m_nLineBegin < 0)
								m_nLineBegin = 0;
							m_nLineEnd = m_nLine;
							m_nLine = 0;
							dataFileIn.close();
							dataFileIn.open(logicalFileDescriptor);
						}
						
						LineRead lineRead = dataFileIn.readBuffer(iLength, true);
						while (lineRead != null)
						{
							if (utilNext(dataFileIn, lineRead, null))
								break;
							lineRead = dataFileIn.readBuffer(iLength, true);
						}
					}
				}
				dataFileIn.close();
				if (m_bCount)
					System.out.println("FileUtil: Number of lines " + m_nLineCount);
				else
					fileOutputClose(csFile);
				return true;
			}
		}
		
		return false;
	}

	private byte[] hexToBytes(String csHex)
	{
		byte[] arrByteValue = new byte[csHex.length()/2];
		
		for (int i=0, j=0; i < csHex.length(); j++)
		{
			String csDigit = "0x" + csHex.charAt(i++) + csHex.charAt(i++);
			int nVal = Integer.decode(csDigit).intValue();
			arrByteValue[j] = (byte)nVal;
		}
		
		return arrByteValue;
	}

	private boolean utilNext(DataFileLineReader dataFileIn, LineRead lineRead, byte[] tbyHeader)
	{
		boolean bStop = false;
		m_nLine++;
		byte[] arrByteData = lineRead.getBufferCopy();
		int nLengthLine = lineRead.getBodyLength();
		
		boolean bWrite = false;
		
		if (m_bReplace)
		{
			bWrite = true;
			if (m_nLine >= m_nLineBegin && (m_nLineEnd == 0 || m_nLine <= m_nLineEnd))
			{
				if (replaceValue(arrByteData, nLengthLine))
					if (m_bDebug)
						System.out.println("FileUtil: Line " + m_nLine + " replaced");
			}
		}
		else if (m_bDelete)
		{
			if (m_nLine >= m_nLineBegin && (m_nLineEnd == 0 || m_nLine <= m_nLineEnd))
			{
				if (m_arrByteValue != null)
				{	
					if (!existsValue(arrByteData, nLengthLine))
						bWrite = true;
				}
			}
			else
			{
				bWrite = true;
			}
			if (m_bDebug && !bWrite)
				System.out.println("FileUtil: Line " + m_nLine + " deleted");
		}
		else if (m_bExtract)
		{
			if (m_nLine >= m_nLineBegin && (m_nLineEnd == 0 || m_nLine <= m_nLineEnd))
			{
				if (m_arrByteValue == null)
					bWrite = true;
				else
					if (existsValue(arrByteData, nLengthLine))
						bWrite = true;
			}
			else
			{
				if (m_nLine >= m_nLineBegin)
					bStop = true;
			}
			if (m_bDebug && bWrite)
				System.out.println("FileUtil: Line " + m_nLine + " extracted");
		}
		else if (m_bCount)
		{
			if (m_nLine >= m_nLineBegin && (m_nLineEnd == 0 || m_nLine <= m_nLineEnd))
			{
				if (m_arrByteValue == null)
					bWrite = true;
				else
					if (existsValue(arrByteData, nLengthLine))
						bWrite = true;
			}
			else
			{
				if (m_nLine >= m_nLineBegin)
					bStop = true;
			}
			if (bWrite)
			{
				m_nLineCount++;
				bWrite = false;
				if (m_bDebug)
					System.out.println("FileUtil: Line " + m_nLine + " counted");
			}	
		}
		
		if (bWrite)
		{
			if (tbyHeader != null)				
				m_fileOutput.write(tbyHeader);
			m_fileOutput.write(arrByteData, 0, nLengthLine);
			m_fileOutput.writeEndOfRecordMarker();
		}
		return bStop;
	}

	private boolean replaceValue(byte[] arrByteData, int nLengthLine)
	{
		boolean bReplaced = false;
		int nBegin = 0;
		if (m_nColBegin != 0)
			nBegin = m_nColBegin - 1;
		int nEnd = nLengthLine;
		if (m_nColEnd != 0 && m_nColEnd < nEnd)
			nEnd = m_nColEnd;		
		for ( ;nBegin < nEnd; )
		{
			boolean bEqual = checkValue(arrByteData, nBegin);
			if (bEqual)
			{
				bReplaced = true;
				for (int i=0; i < m_arrByteValueNew.length; i++)
				{
					arrByteData[nBegin + i] = m_arrByteValueNew[i];
				}
				nBegin += m_arrByteValueNew.length;
			}
			else
			{
				nBegin++;
			}
		}
		return bReplaced;
	}
	
	private boolean existsValue(byte[] arrByteData, int nLengthLine)
	{
		int nBegin = 0;
		if (m_nColBegin != 0)
			nBegin = m_nColBegin - 1;
		int nEnd = nLengthLine;
		if (m_nColEnd != 0 && m_nColEnd < nEnd)
			nEnd = m_nColEnd;		
		for ( ;nBegin < nEnd; nBegin++)
		{
			boolean bEqual = checkValue(arrByteData, nBegin);
			if (bEqual)
				return true;			
		}
		return false;
	}
	
	private boolean checkValue(byte[] arrByteData, int nBegin)
	{
		if (m_arrByteValue == null) return true;
		
		for (int i=0; i < m_arrByteValue.length; i++)
		{
			boolean bEqual = true;
			byte[] bytes = m_arrByteValue[i].array();
			if (nBegin + bytes.length > arrByteData.length)
			{
				bEqual = false;
			}
			else
			{
				for (int j=0; j < bytes.length; j++)
				{
					if (arrByteData[nBegin + j] != bytes[j])
					{
						bEqual = false;
						break;
					}
				}
			}
			if (bEqual)
				return true;
		}		
		return false;
	}

	private void fileOutputOpen(String csFile)
	{
		if (m_nCommandNext == -1)
			m_fileOutput = new DataFileWrite(csFile + ".util", false);
		else	
			m_fileOutput = new DataFileWrite(csFile + ".util." + m_nSequencer, false);
		m_fileOutput.open();
	}
	
	private void fileOutputClose(String csFile)
	{
		m_fileOutput.close();
		if (m_nSequencer != 0)
		{
			csFile += ".util." + (m_nSequencer - 1);
			FileSystem.delete(csFile);
		}
	}
	
	private void fileEnd(String csFile)
	{
		if (m_bKeepOutputFile)
		{	
			System.out.println("FileUtil: File " + csFile + " treated in file " + csFile + ".util");
		}	
		else
		{
			FileSystem.moveOrCopy(csFile + ".util", csFile);
			System.out.println("FileUtil: File " + csFile + " treated");
		}
	}
}