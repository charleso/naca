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
public class SortedRecordReq extends ThreadPoolRequest
{
	private byte m_tbyData[] = null;
	
	SortedRecordReq(byte tbyData[])
	{
		super(false);
		
		int nLength = tbyData.length;
		m_tbyData = new byte[nLength];
		for(int n=0; n<nLength; n++)
		{
			m_tbyData[n] = tbyData[n];
		}
	}
	
	public void execute()
	{
		// unused here
	}
	
	byte[] getData()
	{
		return m_tbyData;
	}
}