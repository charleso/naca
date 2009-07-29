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
/*
 * Created on 3 févr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.CESM;

import java.util.ArrayList;

import nacaLib.varEx.InternalCharBuffer;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CESMTempStorageColl
{
	CESMTempStorageColl()
	{
		m_arr = new ArrayList<InternalCharBuffer>();
	}
	
	int add(InternalCharBuffer data)
	{
		m_arr.add(data);
		return m_arr.size(); 
	}
	
	boolean set(int item, InternalCharBuffer bufItem)
	{
		if (item >=0 && item < m_arr.size())
		{
			m_arr.set(item, bufItem);
			return true;
		}
		return false;
	}

	InternalCharBuffer getNextItem()
	{
		if(m_nLastItemRead+1 < m_arr.size())
		{
			m_nLastItemRead++;
			InternalCharBuffer item = m_arr.get(m_nLastItemRead);
			return item;
		}
		return null;
	}
	
	InternalCharBuffer getIndexedTempQueue(int nIndex)
	{
		m_nLastItemRead = nIndex-1;
		if(m_nLastItemRead>=0 && m_nLastItemRead < m_arr.size())
		{
			InternalCharBuffer item = m_arr.get(m_nLastItemRead);
			return item;
		}
		return null;
	}
	
	int getNbItems()
	{
		return m_arr.size();
	}
	
	
	
	
	private ArrayList<InternalCharBuffer> m_arr = null;
	private int m_nLastItemRead = -1;
}
