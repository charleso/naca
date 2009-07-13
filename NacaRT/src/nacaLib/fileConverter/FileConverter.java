/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.fileConverter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import jlib.log.Log;
import jlib.misc.AsciiEbcdicConverter;
import jlib.misc.DataFileLineReader;
import jlib.misc.DataFileWrite;
import jlib.misc.FileEndOfLine;
import jlib.misc.FileSystem;
import jlib.misc.LineRead;
import jlib.misc.LittleEndingSignBinaryBufferStorage;
import jlib.misc.LittleEndingUnsignBinaryBufferStorage;
import jlib.misc.LogicalFileDescriptor;
import nacaLib.varEx.FileDescriptor;

public class FileConverter
{
	private static final byte   AFP_ASCII_5A  			=   (byte)0x5D; // 5A
	private static final byte[] AFP_ASCII_SFI 			= { (byte)0x4C, (byte)0xD3, (byte)0xBA }; // D3EE9B
	private static final byte[] AFP_ASCII_PAGEFORMAT 	= { (byte)0x4C, (byte)0xBF, (byte)0xAD }; // D3ABCA
	private static final byte[] AFP_ASCII_COPYGROUP 	= { (byte)0x4C, (byte)0xBF, (byte)0xF6 }; // D3ABCC
	private static final byte[] AFP_ASCII_SEGMENT 		= { (byte)0x4C, (byte)0xAE, (byte)0x5E }; // D3AF5F	
	
	private static final byte   AFP_EBCDIC_5A  			=   (byte)0x5A;
//	private static final byte[] AFP_EBCDIC_SFI 			= { (byte)0xD3, (byte)0xEE, (byte)0x9B };
//	private static final byte[] AFP_EBCDIC_BOC 			= { (byte)0xD3, (byte)0xA8, (byte)0x92 };
//	private static final byte[] AFP_EBCDIC_OCD 			= { (byte)0xD3, (byte)0xEE, (byte)0x92 };
//	private static final byte[] AFP_EBCDIC_EOC 			= { (byte)0xD3, (byte)0xA9, (byte)0x92 };
//	private static final byte[] AFP_EBCDIC_IOB 			= { (byte)0xD3, (byte)0xAF, (byte)0xC3 };
	
	private FileDescriptor m_file = null;
	private boolean m_bList = false;
	private boolean m_bSuppressVariableLength = true;
	private boolean m_bAddVariableLength = false;
	private boolean m_bKeepLineFeed = false;
	private String m_csLineFeedReplace = "";
	private int m_nLengthRecord = 0;
	private String m_csPaddingHex = "";
	private byte m_bytePadding = 0;
	private boolean m_bConvertInEbcdic = false;
	private boolean m_bConvertInEbcdicAFP = false;
	private boolean m_bConvertInEbcdicAFPInfoPrint = false;
	private boolean m_bConvertInAscii = false;
	private boolean m_bKeepOutputFile = false;
	private boolean m_bAppendEOF = false;
	private int m_nLine = 0;
	
	private byte[] m_tbyHeader2 = new byte[2];
	private byte[] m_tbyHeader4 = new byte[4];
	
	DataFileWrite m_fileOutput;

	public FileConverter(FileDescriptor file)
	{
		m_file = file;
	}
		
	public boolean execute(String csParameter)
	{
		if (csParameter != null && !csParameter.equals(""))
		{
			String csParameterUpper = csParameter.toUpperCase();
			if (csParameterUpper.indexOf("LIST") != -1)
			{
				m_bList = true;
			}
			if (csParameterUpper.indexOf("KEEPVARIABLELENGTH") != -1)
			{
				m_bSuppressVariableLength = false;
			}
			if (csParameterUpper.indexOf("ADDVARIABLELENGTH") != -1)
			{
				m_bSuppressVariableLength = false;
				m_bAddVariableLength = true;
				m_bKeepLineFeed = true;
				m_csLineFeedReplace = "\n";
			}
			if (csParameterUpper.indexOf("REPLACELINEFEED={") != -1)
			{
				m_bKeepLineFeed = false;
				int nPos = csParameterUpper.indexOf("REPLACELINEFEED={") + 17;
				int nPosEnd = csParameterUpper.indexOf("}", nPos);
				m_csLineFeedReplace = csParameter.substring(nPos, nPosEnd);
			}
			else if (csParameterUpper.indexOf("KEEPLINEFEED") != -1)
			{
				m_bKeepLineFeed = true;
				m_csLineFeedReplace = "\n";
			}
			if (csParameterUpper.indexOf("RECORDLENGTH={") != -1)
			{
				int nPos = csParameterUpper.indexOf("RECORDLENGTH={") + 14;
				int nPosEnd = csParameterUpper.indexOf("}", nPos);
				m_nLengthRecord = new Integer(csParameter.substring(nPos, nPosEnd)).intValue();
				if (csParameterUpper.indexOf("PADDINGHEX={") != -1)
				{
					nPos = csParameterUpper.indexOf("PADDINGHEX={") + 12;
					nPosEnd = csParameterUpper.indexOf("}", nPos);
					m_csPaddingHex = csParameter.substring(nPos, nPosEnd);
					String csDigit = "0x" + m_csPaddingHex.charAt(0) + m_csPaddingHex.charAt(1);
					int nVal = Integer.decode(csDigit).intValue();
					m_bytePadding = (byte)nVal;
				}
			}
			if (csParameterUpper.indexOf("CONVERTINEBCDICAFPINFOPRINT") != -1)
			{
				m_bConvertInEbcdicAFPInfoPrint = true;
			}
			else if (csParameterUpper.indexOf("CONVERTINEBCDICAFP") != -1)
			{
				m_bConvertInEbcdicAFP = true;
			}
			else if (csParameterUpper.indexOf("CONVERTINEBCDIC") != -1)
			{
				m_bConvertInEbcdic = true;
			}
			else if (csParameterUpper.indexOf("CONVERTINASCII") != -1)
			{
				m_bConvertInAscii = true;
			}
			if (csParameterUpper.indexOf("KEEPOUTPUTFILE") != -1)
			{
				m_bKeepOutputFile = true;
			}
			if (csParameterUpper.indexOf("APPENDEOF") != -1)
			{
				m_bAppendEOF = true;
			}
		}
		
		if (m_bAddVariableLength)
			System.out.println("FileConverter: Add variable length");
		else
			if (m_bSuppressVariableLength)
				System.out.println("FileConverter: Suppress variable length");
			else
				System.out.println("FileConverter: Keep variable length");
		
		if (!m_bKeepLineFeed)
			System.out.println("FileConverter: Replace line feed by : \"" + m_csLineFeedReplace + "\"");		
		if (m_nLengthRecord != 0)
		{	
			System.out.println("FileConverter: Length record : " + m_nLengthRecord);
			if (m_bytePadding == 0)
				System.out.println("FileConverter: Padding with low-value");
			else
				System.out.println("FileConverter: Padding with hex(" + m_csPaddingHex + ")");
		}
		if (m_bConvertInEbcdicAFP)
			System.out.println("FileConverter: Convert in ebcdic for AFP file");
		if (m_bConvertInEbcdicAFPInfoPrint)
			System.out.println("FileConverter: Convert in ebcdic for AFP file InfoPrint Manager");
		if (m_bAppendEOF)
			System.out.println("FileConverter: Add character End Of File");
		
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
					convert(csFile);
				}
				dataFileList.close();
			}
		}
		else
		{
			String csFile = m_file.getPhysicalName();
			convert(csFile);
		}

		return true;
	}
	
	private boolean convert(String csFile)
	{
		if (m_bAppendEOF)
		{
			try
			{
				File fileOut = new File(csFile);
				int length = (int)fileOut.length();
				if (length != 0)
				{
					byte[] tbyFileOut = FileSystem.getBytesFromFile(fileOut);
					if (tbyFileOut[length - 1] != FileEndOfLine.LF)
					{
						BufferedWriter out = new BufferedWriter(new FileWriter(fileOut, true));
						out.write(FileEndOfLine.LF);
				        out.close();
				        System.out.println("FileConverter: Character End Of File added");
					}
					else
					{
						System.out.println("FileConverter: Character End Of File already exists");
					}
				}
			}
			catch (IOException e)
			{
			}
		}
		DataFileLineReader dataFileIn = new DataFileLineReader(csFile, 65536, 0);
		LogicalFileDescriptor logicalFileDescriptor = new LogicalFileDescriptor("", csFile);
		if(logicalFileDescriptor != null)
		{
			boolean bInOpened = dataFileIn.open(logicalFileDescriptor);
			if(bInOpened)
			{
				if (m_bAddVariableLength)
				{
					fileOutputOpen(csFile);
					LineRead lineRead = dataFileIn.readNextUnixLine();
					if (lineRead != null)
					{
						if (lineRead.getAsLittleEndingUnsignBinaryInt() == lineRead.getBodyLength() - 4)
						{
							dataFileIn.close();
							System.out.println("FileConverter: File " + csFile + " already converted");
							return false;
						}
						if (m_nLengthRecord != 0)
							LittleEndingSignBinaryBufferStorage.writeInt(m_tbyHeader4, m_nLengthRecord, 0);
						
						while (lineRead != null)
						{
							if (m_nLengthRecord == 0)
								LittleEndingSignBinaryBufferStorage.writeInt(m_tbyHeader4, lineRead.getBodyLength(), 0);
							m_fileOutput.write(m_tbyHeader4);
							convertNext(dataFileIn, lineRead);
							lineRead = dataFileIn.readNextUnixLine();
						}
					}
					dataFileIn.close();
					fileOutputClose(csFile);
					return true;
				}
				else
				{
					if (!logicalFileDescriptor.isLengthInfoDefined())
					{
						logicalFileDescriptor.tryAutoDetermineRecordLength(dataFileIn);
					}
					fileOutputOpen(csFile);
					if (logicalFileDescriptor.isVariableLength())
					{
						byte[] tbyHeader = new byte[4];
						if (m_nLengthRecord != 0)
							LittleEndingSignBinaryBufferStorage.writeInt(tbyHeader, m_nLengthRecord, 0);
						LineRead lineHeader = dataFileIn.readBuffer(4, false);
						while (lineHeader != null)
						{
							int nLengthExcludingHeader = lineHeader.getAsLittleEndingUnsignBinaryInt();							
							if (!m_bSuppressVariableLength)
							{
								if (m_nLengthRecord == 0)
									m_fileOutput.write(lineHeader.getBuffer(), 0, lineHeader.getBodyLength());
								else
									m_fileOutput.write(tbyHeader);
							}
							LineRead lineRead = dataFileIn.readBuffer(nLengthExcludingHeader, true);
							convertNext(dataFileIn, lineRead);
							lineHeader = dataFileIn.readBuffer(4, false);
						}
					}
					else
					{
						if (logicalFileDescriptor.getRecordLengthDefinition() != null)
						{	
							int iLength = logicalFileDescriptor.getRecordLengthDefinition().getRecordLength();
							LineRead lineRead = dataFileIn.readBuffer(iLength, true);
							while (lineRead != null)
							{
								convertNext(dataFileIn, lineRead);
								lineRead = dataFileIn.readBuffer(iLength, true);
							}
						}
					}
					dataFileIn.close();
					fileOutputClose(csFile);
					return true;
				}
			}
		}
		
		return false;
	}
	
	private void convertNext(DataFileLineReader dataFileIn, LineRead lineRead)
	{
		m_nLine++;
		byte[] arrByteValue = lineRead.getBufferCopy();
		int nLengthLine = lineRead.getBodyLength();

		if (m_bConvertInEbcdicAFP || m_bConvertInEbcdicAFPInfoPrint)
		{
			if (nLengthLine > 6 && arrByteValue[0] == AFP_ASCII_5A)
			{
				if (isSpecialAfp(arrByteValue, AFP_ASCII_COPYGROUP) || isSpecialAfp(arrByteValue, AFP_ASCII_PAGEFORMAT))
				{
					AsciiEbcdicConverter.swapByteAsciiToEbcdic(arrByteValue, 0, 1);
					AsciiEbcdicConverter.swapByteAsciiToEbcdic(arrByteValue, 3, 3);					
					AsciiEbcdicConverter.swapByteAsciiToEbcdic(arrByteValue, 9, 8);
					if (nLengthLine > 17)
					{
						if (m_bConvertInEbcdicAFP)
						{
							if (nLengthLine > 19)
							{
								arrByteValue[17] = arrByteValue[nLengthLine - 2];
								arrByteValue[18] = arrByteValue[nLengthLine - 1];
								nLengthLine = 19;
							}	
							AsciiEbcdicConverter.swapByteAsciiToEbcdic(arrByteValue, 17, nLengthLine - 17);
						}
						else
						{
							nLengthLine = 17;
						}
					}
					int nLengthAFP = LittleEndingUnsignBinaryBufferStorage.readShort(arrByteValue, 1);
					if (nLengthAFP != 16)
					{
						LittleEndingUnsignBinaryBufferStorage.writeUnsignedShort(arrByteValue, 16, 1);
						Log.logDebug("FileConverter: Change the record x'5a' length copygroup/pageformat at line " + m_nLine + " for document " + dataFileIn.getName());
					}
				}
				else if (isSpecialAfp(arrByteValue, AFP_ASCII_SEGMENT))
				{
					
					AsciiEbcdicConverter.swapByteAsciiToEbcdic(arrByteValue, 0, 1);
					AsciiEbcdicConverter.swapByteAsciiToEbcdic(arrByteValue, 3, 3);
					AsciiEbcdicConverter.swapByteAsciiToEbcdic(arrByteValue, 9, 8);
					if (nLengthLine > 23)
					{
						if (m_bConvertInEbcdicAFP)
						{
							if (nLengthLine > 25)
							{
								arrByteValue[23] = arrByteValue[nLengthLine - 2];
								arrByteValue[24] = arrByteValue[nLengthLine - 1];
								nLengthLine = 25;
							}
							AsciiEbcdicConverter.swapByteAsciiToEbcdic(arrByteValue, 23, nLengthLine - 23);
						}
						else
						{
							nLengthLine = 23;
						}
					}
					int nLengthAFPSegment = LittleEndingUnsignBinaryBufferStorage.readShort(arrByteValue, 1);
					if (nLengthAFPSegment != 22)
					{
						LittleEndingUnsignBinaryBufferStorage.writeUnsignedShort(arrByteValue, 22, 1);
						Log.logDebug("FileConverter: Change the record x'5a' length segment at 22 in line " + m_nLine + " for document " + dataFileIn.getName());
					}
				}
				else if (isSpecialAfp(arrByteValue, AFP_ASCII_SFI))
				{
					AsciiEbcdicConverter.swapByteAsciiToEbcdic(arrByteValue, 0, 1);
					AsciiEbcdicConverter.swapByteAsciiToEbcdic(arrByteValue, 3, 3);
					AsciiEbcdicConverter.swapByteAsciiToEbcdic(arrByteValue, 9, nLengthLine - 9);
				}
				else
				{
					Log.logCritical("FileConverter: No transformation ebcdic for the record x'5a' in line " + m_nLine + " for document " + dataFileIn.getName());
				}
			}
			else if (nLengthLine > 6 && arrByteValue[0] == AFP_EBCDIC_5A)
			{
//				if (isSpecialAfp(arrByteValue, AFP_EBCDIC_SFI) ||
//					isSpecialAfp(arrByteValue, AFP_EBCDIC_BOC) ||
//					isSpecialAfp(arrByteValue, AFP_EBCDIC_OCD) ||
//					isSpecialAfp(arrByteValue, AFP_EBCDIC_EOC) ||
//					isSpecialAfp(arrByteValue, AFP_EBCDIC_IOB))
//				{
					// Pas convertir le record Formattage texte des routines PSF --> déjà en ebcdic
					if (m_bConvertInEbcdicAFP)
					{
						// Convertir les 2 derniers bytes pour a2p
						AsciiEbcdicConverter.swapByteAsciiToEbcdic(arrByteValue, nLengthLine - 2, 2);
					}
//				}
//				else
//				{
//					AsciiEbcdicConverter.swapByteAsciiToEbcdic(arrByteValue, 0, nLengthLine);
//				}
			}
			else if (nLengthLine > 4 && arrByteValue[0] == '#' &&
								    arrByteValue[1] == '3' &&
								    arrByteValue[2] == '0' &&
								    arrByteValue[3] == '0' &&
								    arrByteValue[4] == '#')
			{
				// Pas convertir le record #300# pour InfoPrint
			}
			else
			{
				AsciiEbcdicConverter.swapByteAsciiToEbcdic(arrByteValue, 0, nLengthLine);
			}
		}
		else if (m_bConvertInEbcdic)
		{
			AsciiEbcdicConverter.swapByteAsciiToEbcdic(arrByteValue, 0, nLengthLine);
		}
		else if (m_bConvertInAscii)
		{
			AsciiEbcdicConverter.swapByteEbcdicToAscii(arrByteValue, 0, nLengthLine);
		}
		
		if (m_bConvertInEbcdicAFPInfoPrint)
		{
			// Ajouter la longueur sur 2 bytes du record
			LittleEndingSignBinaryBufferStorage.writeShort(m_tbyHeader2, (short)nLengthLine, 0);
			m_fileOutput.write(m_tbyHeader2);
		}

		if (m_nLengthRecord == 0)
		{	
			m_fileOutput.write(arrByteValue, 0, nLengthLine);
		}	
		else
		{
			if (nLengthLine >= m_nLengthRecord)
			{
				m_fileOutput.write(arrByteValue, 0, m_nLengthRecord);
			}
			else
			{
				m_fileOutput.write(arrByteValue, 0, nLengthLine);
				byte[] tbyFill = new byte[m_nLengthRecord - nLengthLine];
				for (int i=0 ; i < tbyFill.length; i++)
					tbyFill[i] = m_bytePadding;
				m_fileOutput.write(tbyFill);
			}
		}

		if (!m_csLineFeedReplace.equals(""))
		{
			m_fileOutput.write(m_csLineFeedReplace.getBytes(), 0, m_csLineFeedReplace.length());
		}
	}
	
	private boolean isSpecialAfp(byte[] arrByteValue, byte[] arrToCheck)
	{
		if (arrByteValue[3] == arrToCheck[0] && arrByteValue[4] == arrToCheck[1] && arrByteValue[5] == arrToCheck[2])
			return true;
		else
			return false;
	}

	private void fileOutputOpen(String csFile)
	{
		m_fileOutput = new DataFileWrite(csFile + ".conv", false);
		m_fileOutput.open();
	}
	
	private void fileOutputClose(String csFile)
	{
		m_fileOutput.close();
		if (m_bKeepOutputFile)
		{
			System.out.println("FileConverter: File " + csFile + " converted in file " + csFile + ".conv");
		}
		else
		{	
			FileSystem.moveOrCopy(csFile + ".conv", csFile);
			System.out.println("FileConverter: File " + csFile + " converted");
		}
	}
}