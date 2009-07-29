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
package nacaLib.varEx;

import jlib.log.Log;
import jlib.misc.DataFileWrite;
import jlib.misc.LittleEndingSignBinaryBufferStorage;
import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.bdb.BTreeCommandSort;
import nacaLib.bdb.BtreeFile;
import nacaLib.bdb.BtreeKeyDescription;
import nacaLib.program.*;

public class SortCommand
{
	private SortDescriptor m_sortDescriptorDeclared = null;
	
	public SortCommand(BaseProgramManager programManager, SortDescriptor sortDescriptorDeclared)
	{
		m_programManager = programManager;
		m_sortDescriptorDeclared = sortDescriptorDeclared;
	}
	
	public SortCommand exportKey(String csExportKeyFile)
	{
		m_dataFileKeyOut = new DataFileWrite(csExportKeyFile, false);
		boolean bOutKeyOpened = m_dataFileKeyOut.open();
		if(!bOutKeyOpened)
		{
			m_dataFileKeyOut = null;
			Log.logImportant("Cannot create output key file " + csExportKeyFile);
		}
		return this;
	}
	
	public SortCommand ascKey(Var var)
	{
		SortKeySegmentDefinition keySegment = new SortKeySegmentDefinition(var, true);
		m_btreeKeyDescription.addSegmentDefinition(keySegment);
		return this;
	}

	public SortCommand descKey(Var var)
	{
		SortKeySegmentDefinition keySegment = new SortKeySegmentDefinition(var, false);
		m_btreeKeyDescription.addSegmentDefinition(keySegment);
		return this;
	}
	
	public SortCommand using(FileDescriptor fileDescIn)
	{
		m_fileDescIn = fileDescIn;
		return this;
	}

	public SortCommand giving(FileDescriptor fileDescOut)
	{
		m_fileDescOut = fileDescOut;
		return this;
	}

	public SortCommand usingInput(Paragraph paraInputMin, Paragraph paraInputMax)
	{
		m_paraInputMin = paraInputMin;
		m_paraInputMax = paraInputMax;
		m_sectionInput = null;
		return this;
	}
	
	public SortCommand usingInput(Paragraph paraInput)
	{
		m_paraInputMin = paraInput;
		m_paraInputMax = null;
		m_sectionInput = null;
		return this;
	}
	
	public SortCommand usingInput(Section section)
	{
		m_paraInputMin = null;
		m_paraInputMax = null;
		m_sectionInput = section;
		return this;
	}
	
	
	public SortCommand usingOutput(Paragraph paraOutputMin, Paragraph paraOutputMax)
	{
		m_paraOutputMin = paraOutputMin;
		m_paraOutputMax = paraOutputMax;
		m_sectionOutput = null;
		return this;
	}

	public SortCommand usingOutput(Paragraph paraOutput)
	{
		m_paraOutputMin = paraOutput;
		m_paraOutputMax = paraOutput;
		m_sectionOutput = null;
		return this;
	}
	
	public SortCommand usingOutput(Section secOutput)
	{
		m_paraOutputMin = null;
		m_paraOutputMax = null;
		m_sectionOutput = secOutput;
		return this;
	}
	
	public void exec()
	{
		m_nNbRecordImported = 0;
		
		m_btreeKeyDescription.addRecordIdKeySegment();
				
		m_btreeCommandSort = new BTreeCommandSort();
		m_btreeCommandSort.setTempDir(BaseResourceManager.getTempDir());
		
		boolean bInputIsFile = false;
		boolean bEbcdicIn = false;
		// Input
		if(m_fileDescIn != null)	// read form input file
		{
			//String csFileNameIn = m_fileDescIn.getPhysicalName();
			//bEbcdicIn = m_fileDescIn.isEbcdic();
			bInputIsFile = true;
			
			m_btreeKeyDescription.setFileInEncoding(bEbcdicIn);

			//m_btreeCommandSort.setPhysicalInFileName(csFileNameIn, bEbcdicIn);
			m_btreeCommandSort.setKeyDescription(m_btreeKeyDescription);
			
			m_csBtrieveFileName = m_btreeCommandSort.getTempFileName();
			m_btreeFile = m_btreeCommandSort.createAndOpenTempBtrieveFile(m_csBtrieveFileName);
			if (m_btreeFile == null)
			{
				throw new RuntimeException("Cannot create btreefile");
			}
			else
			{
				m_btreeFile.setKeyDescription(m_btreeKeyDescription);
				m_nNbRecordImported = m_btreeCommandSort.importInFile(m_btreeFile, m_fileDescIn, BaseResourceManager.getFileLineReaderBufferSize(), false);
			}
		}
		else if(m_sectionInput != null)	// Read from section
		{
			m_btreeKeyDescription.setFileInEncoding(false);	// Source = code: Always ascii
			
			SortParagHandler sortParagHandler = new SortParagHandler(this);  
			m_programManager.setCurrentSortCommand(sortParagHandler);
			m_programManager.perform(m_sectionInput);
			m_programManager.setCurrentSortCommand(null);
		}
		else if(m_paraInputMax != null)	// Read from interval of paragraph code
		{
			m_btreeKeyDescription.setFileInEncoding(false);	// Source = code: Always ascii
			
			SortParagHandler sortParagHandler = new SortParagHandler(this);  
			m_programManager.setCurrentSortCommand(sortParagHandler);
			m_programManager.performThrough(m_paraInputMin, m_paraInputMax);
			m_programManager.setCurrentSortCommand(null);
		}
		else	// Read from paragraph code
		{
			m_btreeKeyDescription.setFileInEncoding(false);	// Source = code: Always ascii
			
			SortParagHandler sortParagHandler = new SortParagHandler(this);  
			m_programManager.setCurrentSortCommand(sortParagHandler);
			m_programManager.perform(m_paraInputMin);
			m_programManager.setCurrentSortCommand(null);
		}

		// Output
		if(m_fileDescOut != null)	// Output to file
		{
			String csFileNameOut = m_fileDescOut.getPhysicalName();
			boolean bEbcdicOut = m_fileDescOut.isEbcdic();
			boolean bMustSwapByteEncodingOnOutput = false;
			if(bInputIsFile && bEbcdicOut != bEbcdicIn)
				bMustSwapByteEncodingOnOutput = true;
			m_btreeCommandSort.setPhysicalOutFile(csFileNameOut);
			m_btreeCommandSort.setFileExportKey(m_dataFileKeyOut);
			m_btreeCommandSort.exportToOutFile(m_btreeFile, bMustSwapByteEncodingOnOutput, bEbcdicOut);
		}
		else if(m_sectionOutput != null)	// Output to section
		{
			SortParagHandler sortParagHandler = new SortParagHandler(this);  
			m_programManager.setCurrentSortCommand(sortParagHandler);
			m_programManager.perform(m_sectionOutput);
			m_programManager.setCurrentSortCommand(null);
		}
		else	// Output to interval of paragraphs
		{
			SortParagHandler sortParagHandler = new SortParagHandler(this);  
			m_programManager.setCurrentSortCommand(sortParagHandler);
			m_programManager.performThrough(m_paraOutputMin, m_paraOutputMax);
			m_programManager.setCurrentSortCommand(null);
		}
		
		m_btreeCommandSort.closeAndDelete(m_btreeFile, m_csBtrieveFileName);
		
		if(m_dataFileKeyOut != null)	// Must export key file
			m_dataFileKeyOut.close();
	}
	
	protected void release(Var varRecord)	// A record is given by a paragraph for btrieve importation
	{
		int nTotalLength = 0;

		boolean bVariableLength = false;
		if(m_sortDescriptorDeclared != null)
		{
			bVariableLength = m_sortDescriptorDeclared.hasVarVariableLengthMarker();	// The sort descriptor has a variable length marker: The data is of variable length
			nTotalLength = m_sortDescriptorDeclared.getRecordLength(varRecord);
			if(bVariableLength)
				nTotalLength += 4;	//Reserve space for record header; it will be stored in the data to sort
		}
		else	
			nTotalLength = varRecord.getLength();
		
		if(m_nNbRecordImported == 0)
		{
			m_btreeCommandSort.setKeyDescription(m_btreeKeyDescription);
			m_csBtrieveFileName = m_btreeCommandSort.getTempFileName();	
			m_btreeFile = m_btreeCommandSort.createAndOpenTempBtrieveFile(m_csBtrieveFileName);
			if (m_btreeFile == null)
			{
				throw new RuntimeException("Cannot create btreefile");
			}
			else
			{
				m_btreeFile.setKeyDescription(m_btreeKeyDescription);
			}
		}
		if(m_btreeFile != null)
		{				
			checkBytebuffer(nTotalLength);
			
//			if(debugCheckSpecialBytes(m_tBytesDataRelease, nTotalLength))
//			{
//				int nDebugf = 0;
//			}
			
			if(!bVariableLength)
				varRecord.exportToByteArray(m_tBytesDataRelease, nTotalLength);
			else
			{
				LittleEndingSignBinaryBufferStorage.writeInt(m_tBytesDataRelease, 0, nTotalLength-4);					
				varRecord.exportToByteArray(m_tBytesDataRelease, 4, nTotalLength-4);
			}
				
			boolean b = m_btreeFile.internalSortInsertWithRecordIndexAtEnd(m_tBytesDataRelease, 0, nTotalLength, m_nNbRecordImported, bVariableLength);
			m_nNbRecordImported++;
		}		
	}
	
	// To remove
//	private boolean debugCheckSpecialBytes(byte tSource[], int nSourceLength)
//	{
//		for(int n=0; n<nSourceLength-4; n++)
//		{
//			if(tSource[n] == 0x10 && tSource[n+1] == 0x2d && tSource[n+2] == 0x26 && tSource[n+3] == 0x0c)
//			{
//				return true;
//			}			
//		}
//		return false;
//	}
	
	private void checkBytebuffer(int nLength)
	{
		if(m_tBytesDataRelease == null || m_tBytesDataRelease.length < nLength)
			m_tBytesDataRelease = new byte[nLength];
	}
	
	protected RecordDescriptorAtEnd returnSort(SortDescriptor sortDescriptor)
	{
		if(m_btreeFile != null)
		{
			byte tDataWithHeader[] = m_btreeFile.getNextSortedRecord();

			if(tDataWithHeader != null)
			{
				if(m_dataFileKeyOut != null)	// Must export key file; Not usable in multithread mode
				{
					byte tBytesKey[] = m_btreeFile.getKeyRead();
					m_dataFileKeyOut.writeWithEOL(tBytesKey, tBytesKey.length);
				}
				
				sortDescriptor.fillRecord(tDataWithHeader);
				return RecordDescriptorAtEnd.NotEnd;
			}
		}
		return RecordDescriptorAtEnd.End;
	}
	
	private BaseProgramManager m_programManager = null;
	private FileDescriptor m_fileDescIn = null;
	private FileDescriptor m_fileDescOut = null;
	private BtreeKeyDescription m_btreeKeyDescription = new BtreeKeyDescription(); 
	private Section m_sectionInput = null;
	private Paragraph m_paraInputMin = null;
	private Paragraph m_paraInputMax = null;
	private Section m_sectionOutput = null;
	private Paragraph m_paraOutputMin = null;
	private Paragraph m_paraOutputMax = null;
	
	private BTreeCommandSort m_btreeCommandSort = null;
	private BtreeFile m_btreeFile = null;

	private int m_nNbRecordImported = 0;
	private String m_csBtrieveFileName = null;
	private byte[] m_tBytesDataRelease = null;
	private DataFileWrite m_dataFileKeyOut = null;
}
