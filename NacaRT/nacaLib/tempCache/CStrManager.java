/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.tempCache;

import java.util.ArrayList;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: CStrManager.java,v 1.3 2007/01/09 15:54:48 u930di Exp $
 */
public  class CStrManager
{
	public CStr getMapped()
	{
		if(m_nIndexCStrMapped < m_arrCStrMapped.size())
		{
			CStr cs = m_arrCStrMapped.get(m_nIndexCStrMapped);
			//cs.set(null, 0, 0);	// Erase previous buffer, as we are mapped
			m_nIndexCStrMapped++;
			return cs;			
		}
		else
		{
			CStr cs = new CStr();
			m_arrCStrMapped.add(cs);
			m_nIndexCStrMapped = m_arrCStrMapped.size();
			return cs;
		}		
	}
	
	public CStr getReusable()
	{
		if(m_nIndexCStrReusable < m_arrCStrReusable.size())
		{
			CStr cs = m_arrCStrReusable.get(m_nIndexCStrReusable);
			m_nIndexCStrReusable++;
			return cs;			
		}
		else
		{
			CStr cs = new CStr();
			m_arrCStrReusable.add(cs);
			m_nIndexCStrReusable = m_arrCStrReusable.size();
			return cs;
		}		
	}
	
	public CStrNumber getNumber()
	{
		if(m_nIndexCStrNumber < m_arrCStrNumber.size())
		{
			CStrNumber csNum = m_arrCStrNumber.get(m_nIndexCStrNumber);
			m_nIndexCStrNumber++;
			return csNum;			
		}
		else
		{
			CStrNumber csNum = new CStrNumber();
			m_arrCStrNumber.add(csNum);
			m_nIndexCStrNumber = m_arrCStrNumber.size();
			return csNum;
		}			
	}
	
	public CStrString getString()
	{
		if(m_nIndexCStrString < m_arrCStrString.size())
		{
			CStrString cs = m_arrCStrString.get(m_nIndexCStrString);
			m_nIndexCStrString++;
			return cs;			
		}
		else
		{
			CStrString cs = new CStrString();
			m_arrCStrString.add(cs);
			m_nIndexCStrString = m_arrCStrString.size();
			return cs;
		}
	}
	
	private ArrayList<CStr> m_arrCStrMapped = new ArrayList<CStr>();
	private ArrayList<CStr> m_arrCStrReusable = new ArrayList<CStr>();
	private ArrayList<CStrNumber> m_arrCStrNumber = new ArrayList<CStrNumber>();	
	private ArrayList<CStrString> m_arrCStrString = new ArrayList<CStrString>();
	

	private int m_nIndexCStrMapped = 0;
	private int m_nIndexCStrReusable = 0;
	private int m_nIndexCStrNumber = 0;
	private int m_nIndexCStrString = 0;
	
	public void reset()
	{
		m_nIndexCStrMapped = 0;
		m_nIndexCStrReusable = 0;
		m_nIndexCStrNumber = 0;
		m_nIndexCStrString = 0;
	}
	
	public void rewindCStrMapped(int n)
	{
		m_nIndexCStrMapped -= n;
	}
}
