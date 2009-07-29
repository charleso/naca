/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.sqlSupport;


public class SQLCodeValue
{
	private int m_nNbCodes = 0;
	private int m_tnCode[] = new int [5];
	
	SQLCodeValue(int n)
	{
		m_tnCode[0] = n;
		m_nNbCodes = 1;
	}
	
	public void resetCodes()
	{
		m_nNbCodes = 0;
	}
	
	public boolean set(int n)
	{
		if(m_nNbCodes < 5)
		{
			m_tnCode[m_nNbCodes++] = n;
			return true;
		}
		return false;
	}
	
	public boolean isCode(int nCode)
	{
		if(m_nNbCodes == 1)
			if(m_tnCode[0] == nCode)
				return true;
		
		if(m_nNbCodes == 2)
			if(m_tnCode[0] == nCode || m_tnCode[1] == nCode)
				return true;

		for(int n=0; n<m_nNbCodes; n++)
			if(m_tnCode[n] == nCode)
				return true;
		return false;
	}
	
	public int getMainCode()
	{
		return m_tnCode[0];
	}
	
	public int getCode(int nIndex)
	{
		return m_tnCode[nIndex];
	}
}
