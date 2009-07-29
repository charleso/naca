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

import jlib.threads.ThreadPoolRequest;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class MultiThreadedSortAddItem extends ThreadPoolRequest
{
	private BtreeFile m_btreeFile = null;
	private byte m_tbyData[] = null;
	int m_nTotalLength;
	int m_nNbRecordRead;
	boolean m_bVariableLength;
	
	MultiThreadedSortAddItem(BtreeFile btreeFile, byte tbyData[], int nSourceOffset, int nTotalLength, int nNbRecordRead, boolean bVariableLength)
	{
		super(false);
		
		m_btreeFile = btreeFile;
		m_tbyData = new byte[nTotalLength];
		for(int n=0; n<nTotalLength; n++)
		{
			m_tbyData[n] = tbyData[nSourceOffset++];
		}
		m_nTotalLength = nTotalLength;
		m_nNbRecordRead = nNbRecordRead;
		m_bVariableLength = bVariableLength;
	}
	
	void fill(BtreeFile btreeFile, byte tbyData[], int nSourceOffset, int nTotalLength, int nNbRecordRead, boolean bVariableLength)
	{
		m_btreeFile = btreeFile;

		if(m_tbyData.length < nTotalLength)
			m_tbyData = new byte[nTotalLength];
		
		for(int n=0; n<nTotalLength; n++)
			m_tbyData[n] = tbyData[nSourceOffset++];
		
		m_nTotalLength = nTotalLength;
		m_nNbRecordRead = nNbRecordRead;
		m_bVariableLength = bVariableLength;
	}
	
	public void execute()
	{
		m_btreeFile.asyncAddItemToSortByMultiThreads(this, m_tbyData);
	}
}
