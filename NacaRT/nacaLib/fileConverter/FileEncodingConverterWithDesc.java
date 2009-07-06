/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.fileConverter;

import jlib.misc.DataFileLineReader;
import jlib.misc.LineRead;
import jlib.misc.LittleEndingSignBinaryBufferStorage;
import jlib.misc.NumberParser;
import nacaLib.varEx.FileDescriptor;
import nacaLib.varEx.VarDefEncodingConvertibleManager;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: FileEncodingConverterWithDesc.java,v 1.11 2007/06/09 12:04:22 u930bm Exp $
 */
public class FileEncodingConverterWithDesc extends FileEncodingConverter
{
	private String m_csDesc = null;
	private VarDefEncodingConvertibleManager m_encodingManager = null;  
	
	public FileEncodingConverterWithDesc(FileDescriptor fileIn, FileDescriptor fileOut)
	{
		super(fileIn, fileOut);
	}
		
	public boolean execute(String csDesc)
	{
		m_fileIn.getPhysicalName();
		m_fileOut.getPhysicalName();
		if (m_fileIn.isEbcdic() != m_fileOut.isEbcdic() || m_bHost)
		{
			return convert(csDesc);
		}
		else
		{
			return copyFile();
		}
	}
	
	private boolean convert(String csDesc)
	{
		if (!csDesc.equals(""))
			fillDesc(csDesc);
		
		boolean bEbcdicIn = m_fileIn.isEbcdic();
		boolean bEbcdicOut = m_fileOut.isEbcdic();

		// Read all record form source into structure
		String csFileIn = m_fileIn.getPhysicalName();
		DataFileLineReader dataFileIn = new DataFileLineReader(csFileIn, 65536, 0);
		boolean bInOpened = dataFileIn.open();
		if(bInOpened)
		{
			m_fileOut.openOutput();
			boolean bVariableLength = m_fileIn.isVariableLength();
			if (m_bHost)
			{
				if (m_bHeaderEbcdic)
				{
					byte[] tbyHeaderEbcdic = new String("<FileHeader Version=\"1\" Encoding=\"ebcdic\"/>").getBytes();
					m_fileOut.write(tbyHeaderEbcdic, 0, tbyHeaderEbcdic.length, true);
				}
				
				if (m_nLengthRecord == 0)
				{
					byte[] tbyHeader4 = new byte[4];
					if (m_bVariable4)
					{	
						LineRead lineRead = dataFileIn.readBuffer(4, false);
						while (lineRead != null)
						{
							int i1 = lineRead.getBuffer()[lineRead.getOffset()];
							if (i1 < 0) i1 = 256 + i1;
							int i2 = lineRead.getBuffer()[lineRead.getOffset() + 1];
							if (i2 < 0) i2 = 256 + i2;
							int nCurrentRecordLength = (i1 * 256) + i2 - 4;
							
							if (bVariableLength)
							{
								LittleEndingSignBinaryBufferStorage.writeInt(tbyHeader4, nCurrentRecordLength, 0);
								m_fileOut.write(tbyHeader4, 0, tbyHeader4.length, false);
							}
							
							lineRead = dataFileIn.readBuffer(nCurrentRecordLength, false);
							if (bEbcdicIn && !bEbcdicOut)
								m_encodingManager.getConvertedBytesEbcdicToAscii(lineRead);
							else if (!bEbcdicIn && bEbcdicOut)
								m_encodingManager.getConvertedBytesAsciiToEbcdic(lineRead);
							
							m_fileOut.write(lineRead.getBuffer(), lineRead.getOffset(), lineRead.getBodyLength(), true);
							lineRead = dataFileIn.readBuffer(4, false);
						}
					}
					else
					{
						LineRead lineRead = dataFileIn.readBuffer(3, false);
						while (lineRead != null)
						{
							int i1 = lineRead.getBuffer()[lineRead.getOffset() + 1];
							int i2 = lineRead.getBuffer()[lineRead.getOffset() + 2];
							int nCurrentRecordLength = i1 * 256 + i2;
							
							if (bVariableLength)
							{
								LittleEndingSignBinaryBufferStorage.writeInt(tbyHeader4, nCurrentRecordLength, 0);
								m_fileOut.write(tbyHeader4, 0, tbyHeader4.length, false);
							}
							
							lineRead = dataFileIn.readBuffer(nCurrentRecordLength, false);
							if (bEbcdicIn && !bEbcdicOut)
								m_encodingManager.getConvertedBytesEbcdicToAscii(lineRead);
							else if (!bEbcdicIn && bEbcdicOut)
								m_encodingManager.getConvertedBytesAsciiToEbcdic(lineRead);
							
							m_fileOut.write(lineRead.getBuffer(), lineRead.getOffset(), lineRead.getBodyLength(), true);
							lineRead = dataFileIn.readBuffer(3, false);
						}
					}
				}
				else
				{
					LineRead lineRead = dataFileIn.readBuffer(m_nLengthRecord, false);
					while (lineRead != null)
					{	
						if (bEbcdicIn && !bEbcdicOut)
							m_encodingManager.getConvertedBytesEbcdicToAscii(lineRead);
						else if (!bEbcdicIn && bEbcdicOut)
							m_encodingManager.getConvertedBytesAsciiToEbcdic(lineRead);
						m_fileOut.write(lineRead.getBuffer(), lineRead.getOffset(), lineRead.getBodyLength(), true);
						lineRead = dataFileIn.readBuffer(m_nLengthRecord, false);
					}
				}
			}
			else
			{
				LineRead lineRead = m_fileIn.readALine(dataFileIn, null);
				while(lineRead != null)
				{
					if(bVariableLength)
						lineRead.shiftOffset(4);	// Skip record header
	
					if (bEbcdicIn && !bEbcdicOut)
						m_encodingManager.getConvertedBytesEbcdicToAscii(lineRead);
					else if (!bEbcdicIn && bEbcdicOut)
						m_encodingManager.getConvertedBytesAsciiToEbcdic(lineRead);
	
					if(bVariableLength)
						lineRead.shiftOffset(-4);
	
					m_fileOut.writeFrom(lineRead);
					lineRead = m_fileIn.readALine(dataFileIn, lineRead);
				}
			}
			m_fileOut.close();
		}
		
		return true;
	}
	
	private void fillDesc(String csDesc)
	{
		m_csDesc = csDesc;
		m_encodingManager = new VarDefEncodingConvertibleManager();
		
		while(m_csDesc != null)
		{
			int nPosition = getChunkAsInt()-1;
			int nLength = getChunkAsInt();
			String csType = getChunk();
						
			if(csType.equalsIgnoreCase("CH"))
				m_encodingManager.add(nPosition, nLength);
			else if(csType.equalsIgnoreCase("CHB"))
				m_encodingManager.add(nPosition, nLength, true);
			else if(csType.equalsIgnoreCase("PRINT"))
				m_encodingManager.add(nPosition, nLength, false, true);
			else if(csType.equalsIgnoreCase("Comp0"))
				m_encodingManager.add(nPosition, nLength);
			else if(csType.equalsIgnoreCase("Comp0Signed"))
				m_encodingManager.add(nPosition, nLength-1);
		}
		
		m_encodingManager.compress();
	}
	
	private int getChunkAsInt()
	{
		String cs = getChunk();
		return NumberParser.getAsInt(cs);
	}
		
	private String getChunk()
	{
		String cs = null;
		int nIndex = m_csDesc.indexOf(',');
		if(nIndex == -1)
		{
			cs = m_csDesc;
			cs = cs.trim();
			m_csDesc = null;
		}
		else
		{
			cs = m_csDesc.substring(0, nIndex);
			cs = cs.trim();
			m_csDesc = m_csDesc.substring(nIndex+1);			
		}
		return cs;
	}
}
