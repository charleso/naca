/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 20 août 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements.SQL;

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CSQLTableColDescriptor
{
	public CSQLTableColDescriptor()
	{
	}
	
	public void SetName(String csName)
	{
		m_csName = csName;
	}
	
	public String GetName()
	{
		return m_csName;
	}
	
	void SetLength(int n)
	{
		m_nLength = n;
		m_bLengthSet = true;
	}	

	void SetDecimal(int n)
	{
		m_nDecimal = n;
		m_bDecimalSet = true;
	}
	
	public boolean HasSize()
	{
		return m_bLengthSet;
	}
	
	public String GetSizes()
	{
		if(m_bLengthSet)
		{
			if(m_bDecimalSet)
				return String.valueOf(m_nLength) + ", " + String.valueOf(m_nDecimal);
			return String.valueOf(m_nLength);
		}
		return "";
	}
	
	void SetType(String csType)
	{
		m_csType = csType;
	}
	
	public String GetType()
	{
		return m_csType;
	} 
	
	void SetNull(boolean b)
	{
		m_bNull = b;
	}		
	
	public boolean IsNull()
	{
		return m_bNull;
	}
	
	private int m_nLength = 0;
	private int m_nDecimal = 0;
	private boolean m_bDecimalSet = false;
	private boolean m_bLengthSet = false;
	private String m_csType = "";
	private String m_csName = "";
	private boolean m_bNull = false;

}
