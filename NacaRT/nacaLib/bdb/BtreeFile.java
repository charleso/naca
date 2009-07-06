/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.bdb;

import jlib.misc.LineRead;
import jlib.misc.LittleEndingUnsignBinaryBufferStorage;
import jlib.threads.PoolOfThreads;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.exceptions.AbortSessionException;

import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: BtreeFile.java,v 1.24 2007/06/22 15:57:35 u930bm Exp $
 */
public class BtreeFile
{
	private Database m_bdb = null;
	DatabaseEntry m_key = null;
	DatabaseEntry m_data = null;
	private BtreeKeyDescription m_keyDescription = null;
	public static final int MAX_RECORD_LENGTH = 32768;
	private Cursor m_cursor = null;
	private int m_nNbthreadsSort = 0;
	private PoolOfThreads m_threadsPoolWriter = null;
	private SortedRecordsPoolOfThreadReader m_threadsPoolReader = null;
	private int m_nNbMaxRequestAsyncSortPending = 0;
	private MultiThreadedSortAddItemCache m_multiThreadedSortItemCache = null;
			
	BtreeFile(Database bdb)	//, boolean bCanSortMultiThreads)
	{
		m_bdb = bdb;
		m_key = new DatabaseEntry();
		m_data = new DatabaseEntry();
//		if(bCanSortMultiThreads)
//		{
			m_nNbthreadsSort = BaseResourceManager.getNbThreadsSort();
			if(m_nNbthreadsSort > 0)
			{
				m_nNbMaxRequestAsyncSortPending = BaseResourceManager.getNbMaxRequestAsyncSortPending();
				BtreePooledThreadWriterFactory btreeThreadFactory = new BtreePooledThreadWriterFactory();
				
				m_threadsPoolWriter = new PoolOfThreads(btreeThreadFactory, m_nNbthreadsSort, m_nNbMaxRequestAsyncSortPending);
				m_threadsPoolWriter.startAllThreads();
			}
			m_multiThreadedSortItemCache = new MultiThreadedSortAddItemCache();
		//}
	}
	
	public void setKeyDescription(BtreeKeyDescription keyDescription)
	{
		m_keyDescription = keyDescription;
		m_keyDescription.prepare();
	}

	public boolean internalSortInsertWithRecordIndexAtEnd(byte tbyData[], int nSourceOffset, int nTotalLength, int nNbRecordRead, boolean bVariableLength)  
	{
		if(m_nNbthreadsSort == 0)	// No thread for sorting
		{
			byte tbyKey[] = m_keyDescription.fillKeyBuffer(tbyData, 0, nNbRecordRead, bVariableLength);
	
			//LittleEndingUnsignBinaryBufferStorage.writeInt(tbyKey, nNbRecordRead, m_keyDescription.m_nKeyLength-4);	// Intel format
			
			m_data.setData(tbyData, 0, nTotalLength);
			m_key.setData(tbyKey);
			try
			{
				m_bdb.put(null, m_key, m_data);
				return true;
			}
			catch (DatabaseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		else // Multi threads for sorting
		{
			// Insert real record
			MultiThreadedSortAddItem item = m_multiThreadedSortItemCache.getUsusedItem();
			if(item == null)
				item = new MultiThreadedSortAddItem(this, tbyData, nSourceOffset, nTotalLength, nNbRecordRead, bVariableLength);
			else
				item.fill(this, tbyData, nSourceOffset, nTotalLength, nNbRecordRead, bVariableLength);
			m_threadsPoolWriter.enqueue(item);
			return true;
		}
	}
	
	public void asyncAddItemToSort(byte tbyData[], int nTotalLength, int nNbRecordRead, boolean bVariableLength)
	{
		// if only 1 dedicated thread is used for adding an item to sort 
		m_data.setData(tbyData, 0, nTotalLength);
		byte tbyKey[] = m_keyDescription.fillNewKeyBuffer(tbyData, nNbRecordRead, bVariableLength);
		m_key.setData(tbyKey);
		try
		{
			m_bdb.put(null, m_key, m_data);
			//unlock 
		}
		catch (DatabaseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		unlock
	}
	
	public void asyncAddItemToSortByMultiThreads(MultiThreadedSortAddItem multiThreadedSortItem, byte tbyData[])
	{
		// if more than 1 dedicated thread are used for adding an item to sort
				
		DatabaseEntry data = new DatabaseEntry();
		data.setData(tbyData, 0, multiThreadedSortItem.m_nTotalLength);
		byte tbyKey[] = m_keyDescription.fillNewKeyBuffer(tbyData, multiThreadedSortItem.m_nNbRecordRead, multiThreadedSortItem.m_bVariableLength);
		
		//Dumper.dump("Record read="+multiThreadedSortItem.m_nNbRecordRead);
		//Dumper.dump(tbyKey);
		
		DatabaseEntry key = new DatabaseEntry();
		key.setData(tbyKey);
		try
		{
			m_bdb.put(null, key, data);
		}
		catch (DatabaseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		m_multiThreadedSortItemCache.disposeItemForReuse(multiThreadedSortItem);
	}

	public boolean externalSortInsertWithRecordIndexAtEnd(Environment env, LineRead lineRead, int nNbRecordRead, boolean bFileInEbcdic, boolean bFileInVariableLength)  
	{
		byte tbyData[] = lineRead.getBuffer();
		int nOffset = lineRead.getOffset();
		int nTotalLength = lineRead.getTotalLength();
		
		if(m_nNbthreadsSort == 0)	// No thread for sorting
		{
			byte tbyKey[] = m_keyDescription.fillKeyBufferExceptRecordId(lineRead, bFileInVariableLength);	//, bFileInEbcdic);

			LittleEndingUnsignBinaryBufferStorage.writeInt(tbyKey, nNbRecordRead, m_keyDescription.m_nKeyLength-4);	// Write record id at the end of the key

			m_data.setData(tbyData, nOffset, nTotalLength);
			m_key.setData(tbyKey);
			//Dumper.dump("Record read="+nNbRecordRead);
			//Dumper.dump(tbyKey);
			try
			{
				m_bdb.put(null, m_key, m_data);
				return true;
			}
			catch (DatabaseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		else
		{
			MultiThreadedSortAddItem item = m_multiThreadedSortItemCache.getUsusedItem();
			if(item == null)
				item = new MultiThreadedSortAddItem(this, tbyData, nOffset, nTotalLength, nNbRecordRead, bFileInVariableLength);
			else
				item.fill(this, tbyData, nOffset, nTotalLength, nNbRecordRead, bFileInVariableLength);
			//MultiThreadedSortAddItem item = new MultiThreadedSortAddItem(this, tbyData, nOffset, nTotalLength, nNbRecordRead, bFileInVariableLength);
			m_threadsPoolWriter.enqueue(item);
			return true;
		}
	}
	
	public boolean tryLaunchAsyncSortReader()
	{
		if(m_threadsPoolWriter != null)	// We are using a pool of threads for adding items for sorting; wait until all items have been completly added
		{
			Exception expThrownByPooledThread = m_threadsPoolWriter.stop();
			if (expThrownByPooledThread != null)    // One of the threads has crashed
			{
				throw new RuntimeException(expThrownByPooledThread);
			}
			
			m_threadsPoolWriter = null;
			
			// Create a thread pool reader
			BtreePooledThreadReaderFactory btreePooledThreadReaderFactory = new BtreePooledThreadReaderFactory(this);
			
			m_threadsPoolReader = new SortedRecordsPoolOfThreadReader(btreePooledThreadReaderFactory, m_nNbMaxRequestAsyncSortPending);
			m_threadsPoolReader.startAllThreads();
			
			return true;
		}
		return false; 
	}
	
	public byte [] syncGetFirst()
	{	
		try
		{
			m_cursor = m_bdb.openCursor(null, null);
			OperationStatus status = m_cursor.getFirst(m_key, m_data, LockMode.DEFAULT);
			if(status == OperationStatus.SUCCESS)
			{
				m_nNbRecordExported = 1;
				byte tDataWithHeader[] = getDataRead();
				return tDataWithHeader;
			}
		}
		catch (DatabaseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public byte [] syncGetNext()
	{
		try
		{
			if(m_cursor != null)
			{
				OperationStatus status = m_cursor.getNext(m_key, m_data, LockMode.DEFAULT);
				if(status == OperationStatus.SUCCESS)
				{
					m_nNbRecordExported++;
					byte tDataWithHeader[] = getDataRead();
					return tDataWithHeader;
				}
			}				
		}
		catch (DatabaseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public byte [] getDataRead()
	{
		return m_data.getData();
	}
	
	public byte [] getKeyRead()
	{
		return m_key.getData();
	}

	void close()
	{
		if(m_cursor != null)
		{
			try
			{
				m_cursor.close();
				m_cursor = null;
			}
			catch (DatabaseException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		if(m_bdb != null)
		{
			try
			{
				m_bdb.close();
			}
			catch (DatabaseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			m_bdb = null;
		}
	}

	byte[] getData()
	{
		if(m_data != null)
			return m_data.getData();
		return null;
	}
	
	byte[] getKey()
	{
		if(m_key != null)
			return m_key.getData();
		return null;
	}
		
	public byte[] getNextSortedRecord()
	{
		if(m_nNbRecordExported == 0)
			tryLaunchAsyncSortReader();
		
		if(m_threadsPoolReader == null)
		{
			if(m_nNbRecordExported == 0)
				return syncGetFirst();
			else
				return syncGetNext();
		}
		return m_threadsPoolReader.getNextSortedRecord();
	}
	
	private int m_nNbRecordExported = 0;
}
