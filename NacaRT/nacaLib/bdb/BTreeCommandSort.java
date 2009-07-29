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
/**
 * 
 */
package nacaLib.bdb;

import jlib.log.Log;
import jlib.misc.AsciiEbcdicConverter;
import jlib.misc.DataFileLineReader;
import jlib.misc.DataFileWrite;
import jlib.misc.FileSystem;
import jlib.misc.LineRead;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.tempCache.TempCacheLocator;
import nacaLib.varEx.FileDescriptor;

import com.sleepycat.je.Environment;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class BTreeCommandSort
{
	private String m_csTempDir = null;
	
	//private String m_csFileIn = null;
	//private boolean m_bFileInEbcdic = false;
	
	private String m_csFileOut = null;
	
	private BTreeEnv m_btreeEnv = null;
	private BtreeKeyDescription m_keyDescription = null;
	
	private DataFileWrite m_dataFileKeyOut = null;
	//private boolean m_bCanSortMultiThreads = false;
	
	public BTreeCommandSort()
	{
		//m_bCanSortMultiThreads = bCanSortMultiThreads;
	}
	
	public void setTempDir(String csTempDir)
	{
		m_csTempDir = FileSystem.normalizePath(csTempDir);
		FileSystem.createPath(m_csTempDir);		
	}
	
//	public void setPhysicalInFileName(String csFileIn, boolean bFileInEbcdic)
//	{
//		m_csFileIn = csFileIn;
//		m_bFileInEbcdic = bFileInEbcdic;
//	}

	public void setPhysicalOutFile(String csFileOut)
	{
		m_csFileOut = csFileOut;
	}
	
	public void setFileExportKey(DataFileWrite dataFileKeyOut)
	{
		m_dataFileKeyOut = dataFileKeyOut;
	}
	
	public void setExportKeyFileOut(String csExportKeyFileOut)
	{
		if(csExportKeyFileOut != null)
		{
			m_dataFileKeyOut = new DataFileWrite(csExportKeyFileOut, false);
			boolean bOutKeyOpened = m_dataFileKeyOut.open();
			if(!bOutKeyOpened)
			{
				m_dataFileKeyOut = null;
				Log.logImportant("Cannot create output key file " + csExportKeyFileOut);
			}
		}
	}
	
	public void set(String csTempDir, String csFileOut, String csKeys)
	{
		setTempDir(csTempDir);
		if(csFileOut != null)
			setPhysicalOutFile(csFileOut);
		setKeyDescription(csKeys);	
	}
	
	public void setKeyDescription(String csKeys)
	{
		m_keyDescription = new BtreeKeyDescription();
		m_keyDescription.set(csKeys, true);
		TempCacheLocator.getTLSTempCache().setBtreeKeyDescription(m_keyDescription);
	}
	
	public void setKeyDescription(BtreeKeyDescription keyDescription)
	{
		m_keyDescription = keyDescription;
		TempCacheLocator.getTLSTempCache().setBtreeKeyDescription(m_keyDescription);
	}
	
	public boolean execute(int nBufferChunkReadAHead, FileDescriptor fileSortIn, FileDescriptor fileSortOut)
	{
		String csFileIn = fileSortIn.getPhysicalName();
		if(fileSortIn.getRecordLengthDefinition() == null)
		{
			fileSortOut.getPhysicalName();
			fileSortIn.inheritSettings(fileSortOut);
		}
		
		boolean bFileInEbcdic = fileSortIn.isEbcdic();
		m_keyDescription.setFileInEncoding(bFileInEbcdic);
		
		String csBtreeDir = getTempFileName();
		BtreeFile btreeFile = createAndOpenTempBtrieveFile(csBtreeDir);
		if(btreeFile == null)
		{
			throw new RuntimeException("Cannot create btreefile");
		}
		else
		{
			btreeFile.setKeyDescription(m_keyDescription);
			int nNbRecordRead = importInFile(btreeFile, fileSortIn, nBufferChunkReadAHead, true);
			if(nNbRecordRead >= 0)
				exportToOutFile(btreeFile, false, false);
			closeAndDelete(btreeFile, csBtreeDir);
			if(nNbRecordRead < 0)
				return false;			
		}
		return true;
	}
	
	public String getTempFileName()
	{
		if(m_csTempDir == null)
			m_csTempDir = "./";
		String csTempFile = m_csTempDir + FileSystem.getTempFileName();
		return csTempFile;		
	}
	
	public BtreeFile createAndOpenTempBtrieveFile(String csBtreeDir)
	{
		csBtreeDir = FileSystem.normalizePath(csBtreeDir);
		FileSystem.createPath(csBtreeDir);

		if(connectBtreeEngine(csBtreeDir))
		{
			BtreeFile btreeFile = m_btreeEnv.createBtreeFile("Btree");	//, m_bCanSortMultiThreads);	
			return btreeFile; 
		}
		
		return null;			
	}
	
	public void closeAndDelete(BtreeFile btreeFile, String csBtreeDir)
	{
		if(btreeFile != null)
			btreeFile.close();
		
		if(m_btreeEnv != null)
			m_btreeEnv.close();
		
		if(csBtreeDir != null)
			FileSystem.DeleteDirAndContent(csBtreeDir);
	}
	
	private boolean connectBtreeEngine(String csDir)
	{
		if(m_btreeEnv == null)
		{
			m_btreeEnv = new BTreeEnv();
			boolean b = m_btreeEnv.initEngine(csDir);
			return b;
		}
		return true;
	}
	
	public int importInFile(BtreeFile btreeFile, FileDescriptor fileSortIn, int nBufferChunkReadAHead, boolean bExternalSort)
	{
		int nNbRecordRead = 0;
		String csFileIn = fileSortIn.getPhysicalName();
		DataFileLineReader dataFileIn = new DataFileLineReader(csFileIn, nBufferChunkReadAHead, 0);
		boolean bInOpened = dataFileIn.open();
		if(bInOpened)
		{
			fileSortIn.tryAutoDetermineRecordLengthIfRequired(dataFileIn);
			
			boolean bFileInVariableLength = fileSortIn.hasVarVariableLengthMarker();
			boolean  b = true;
			boolean bFileInEbcdic = fileSortIn.isEbcdic();
			LineRead lineRead = fileSortIn.readALine(dataFileIn, null);
			Environment env = m_btreeEnv.getEnv();
			while(lineRead != null && b == true)
			{
				b = btreeFile.externalSortInsertWithRecordIndexAtEnd(env, lineRead, nNbRecordRead, bFileInEbcdic, bFileInVariableLength);
				nNbRecordRead++;
				lineRead = fileSortIn.readALine(dataFileIn, lineRead);
			}
			
			dataFileIn.close();
			Log.logNormal("" + nNbRecordRead + " records imported into btree file from " + csFileIn);
		}
		else
		{
			Log.logCritical("Could not open file " + csFileIn);
			return -1;
		}
		return nNbRecordRead;
	}
//	
//	public int importInFile(BtreeFile btreeFile, int nBufferChunkReadAHead)
//	{
//		int nNbRecordRead = 0;
//		DataFileLineReader dataFileIn = new DataFileLineReader(m_csFileIn, nBufferChunkReadAHead, 0);
//		boolean bInOpened = dataFileIn.open();
//		if(bInOpened)
//		{
//			boolean b = true;
//			LineRead lineRead = dataFileIn.readNextUnixLine();
//			while(lineRead != null && b == true)
//			{
////				if(m_bFileInEbcdic)
////					AsciiEbcdicConverter.swapByteEbcdicToAscii(lineRead.getBuffer(), lineRead.getOffset(), lineRead.getTotalLength());
//				
//				//String cs = lineRead.getChunkAsString();
//				
//				b = btreeFile.externalSortInsertWithRecordIndexAtEnd(m_btreeEnv.getEnv(), lineRead, nNbRecordRead, m_bFileInEbcdic);
//				lineRead = dataFileIn.readNextUnixLine();
//				nNbRecordRead++;
//			}
//			dataFileIn.close();
//		}		
//		Log.logCritical("" + nNbRecordRead + " records imported into btree file from " + m_csFileIn);
//	
//		return nNbRecordRead;
//	}
	
	public int exportToOutFile(BtreeFile btreeFile, boolean bMustSwapByteEncodingOnOutput, boolean bToEbcdic)
	{
		int nNbRecordWrite = 0;
		boolean bMustWriteFileHeader = false;
		//boolean bMustWriteFileHeader = BaseResourceManager.getMustWriteFileHeader();
		DataFileWrite dataFileOut = new DataFileWrite(m_csFileOut, bMustWriteFileHeader);
		boolean bOutOpened = dataFileOut.open();
		if(bOutOpened)
		{
			if(btreeFile != null)	// We have a sorted file to write on output
			{
				//btreeFile.tryLaunchAsyncSortReader();
				//byte tBytesData[] = btreeFile.syncGetFirst();
				
				byte tBytesData[] = btreeFile.getNextSortedRecord();
				while(tBytesData != null)
				{
					if(m_dataFileKeyOut != null)	// Must export key file
					{
						byte tbyKey[] = m_keyDescription.fillKeyBuffer(tBytesData, 0, nNbRecordWrite, false);
						m_dataFileKeyOut.writeWithEOL(tbyKey, tbyKey.length-4);
					}
					int nRecordLengthWithoutHeader = tBytesData.length;
					if(bMustSwapByteEncodingOnOutput)
					{
						if(bToEbcdic)
							AsciiEbcdicConverter.swapByteAsciiToEbcdic(tBytesData, 0, nRecordLengthWithoutHeader);
						else
							AsciiEbcdicConverter.swapByteEbcdicToAscii(tBytesData, 0, nRecordLengthWithoutHeader);						
					}
					dataFileOut.write(tBytesData, 0, nRecordLengthWithoutHeader);
					dataFileOut.writeEndOfRecordMarker();
					
					//tBytesData = btreeFile.syncGetNext();
					tBytesData = btreeFile.getNextSortedRecord();
					
					nNbRecordWrite++;
				}
				if(m_dataFileKeyOut != null)
				{
					m_dataFileKeyOut.close();
					
					// Check key out file 
					//boolean b = Dumper.isFileRecordsOrdered(m_dataFileKeyOut.getName(), true);
					m_dataFileKeyOut = null;
				}
			}
			dataFileOut.close();
		}
		Log.logNormal("" + nNbRecordWrite + " records exported from btree file into " + m_csFileOut);
		return nNbRecordWrite;
	}
}
