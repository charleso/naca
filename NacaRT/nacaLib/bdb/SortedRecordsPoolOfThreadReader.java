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

import jlib.threads.BasePooledThreadFactory;
import jlib.threads.PoolOfThreads;
import jlib.threads.ThreadPoolRequest;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
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
