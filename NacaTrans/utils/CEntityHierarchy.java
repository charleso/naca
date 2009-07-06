/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 7 oct. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package utils;

import java.util.Vector;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CEntityHierarchy
{

	public CEntityHierarchy()
	{
	}
	
//	public CEntityHierarchy(CEntityHierarchy hier)
//	{
//		if (hier != null)
//		{
//			Concat(hier) ;
//		}
//	}	
	protected Vector<String> m_arrAscendants = new Vector<String>() ;

	public void AddLevel(String level)
	{
		m_arrAscendants.addElement(level) ;
	}
	
	public boolean CheckAscendant(String name)
	{
		if (name.contains(";"))
		{
			String [] arr = name.split(";") ;
			return CheckAscendants(arr) ;
		}
		return m_arrAscendants.contains(name) && !m_arrAscendants.elementAt(m_arrAscendants.size()-1).equals(name);
	}	
	public boolean CheckAscendants(String[] arr)
	{
		boolean b = true ;
		for (String cs : arr)
		{
			b &= CheckAscendant(cs) ;
		}
		return b ;
	}	
	
	public String FindGoodName(CEntityHierarchy tab[], String curName, int index)
	{
		String radical = curName ;
		int n = curName.indexOf('$') ;
		if (n>0)
		{
			radical = curName.substring(0, n) ;
		}
		String goodName = "" ;
		for (int i=0; i<m_arrAscendants.size()-1 ; i++) // hierarchy starts with object's name
		{
			boolean bFound = false ;
			String asc = m_arrAscendants.elementAt(i);
			for (int j=0; j<tab.length && !bFound ; j++)
			{
				bFound |= tab[j].CheckAscendant(asc);
			}
			if (!bFound)
			{
				goodName = asc ;
				break ;
			}
		}
		if (goodName.equals(""))
		{
			return radical+"$"+index ;
		}
		return radical+"$"+goodName ;
	}
	
	public CEntityHierarchy Concat(CEntityHierarchy hier)
	{
		for (int i=0; i<hier.m_arrAscendants.size(); i++)
		{
			m_arrAscendants.addElement(hier.m_arrAscendants.elementAt(i));
		}		
		return this ;
	}
}
