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
 * Created on 30 nov. 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.mapSupport;

public class MapFieldFlag
{
	public MapFieldFlag()
	{
	}
	
	public MapFieldFlag duplicate()
	{
		MapFieldFlag copy = new MapFieldFlag();
		copy.m_csValue = m_csValue;
		return copy;
	}
	
	public void set(String cs)
	{
		m_csValue = cs;
	}

	public void set(char c)
	{
		if (c == 0)
		{
			m_csValue = null ;
		}
		else
		{
			m_csValue = new String(Character.toString(c));
		}
	}
	
	public String get()
	{
		if (m_csValue != null)
		{
			return m_csValue;
		}
		else
		{
			return "" ;
		}
	}
	
	public boolean isFlag(String cs)
	{
		if (m_csValue == null)
		{
			return false ;
		}
		return m_csValue.equals(cs);
	}
	
	public char getEncodedValue()
	{
		if(m_csValue!=null && m_csValue.length() >= 1)
			return m_csValue.charAt(0);
		return 0; 
	}

	public void setEncodedValue(char cEncodedValue)
	{
		set(cEncodedValue);
	}
	
	private String m_csValue = null;

	/**
	 * @return
	 */
	public boolean isSet()
	{
		return m_csValue != null ;
	}

	/**
	 * 
	 */
	public void reset()
	{
		m_csValue = null ;		
	}
}
