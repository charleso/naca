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

import java.util.Comparator;

import nacaLib.tempCache.TempCacheLocator;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class BtreeKeyComparator implements Comparator
{
	private BtreeKeyDescription m_keyDescription = null;	
    
	public BtreeKeyComparator() 
    {
    }

    public int compare(Object d1, Object d2) 
    {
    	int n = doCompare(d1, d2);
    	return n;
    }
    
    private synchronized int doCompare(Object d1, Object d2)
    {
    	if(m_keyDescription == null)
    		m_keyDescription = TempCacheLocator.getTLSTempCache().getBtreeKeyDescription();
		
    	int n = m_keyDescription.compare(d1, d2);
		return n;
    }
}
