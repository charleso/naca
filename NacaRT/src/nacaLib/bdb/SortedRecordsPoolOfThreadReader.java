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

import jlib.threads.BasePooledThreadFactory;
import jlib.threads.PoolOfThreads;
import jlib.threads.ThreadPoolRequest;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: SortedRecordsPoolOfThreadReader.java,v 1.1 2006/11/29 09:31:30 u930di Exp $
 */
public class SortedRecordsPoolOfThreadReader extends PoolOfThreads
{
	public SortedRecordsPoolOfThreadReader(BasePooledThreadFactory pooledThreadFactory, int nNbMaxRequestAsyncSortPending)
	{
		super(pooledThreadFactory, 1, nNbMaxRequestAsyncSortPending);
	}
	
	byte [] getNextSortedRecord()
	{
		ThreadPoolRequest req = dequeue();
		if(req.getTerminaisonRequest())
			return null;
		SortedRecordReq sortedRecordReq = (SortedRecordReq) req;
		return sortedRecordReq.getData();
	}
}
