/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
// InitialValue.java

package nacaLib.varEx;


public class CInitialValue
{
	public CInitialValue(int n, boolean bFill)
	{
		m_genericValue = new GenericValueInt(n); 
		m_bFill = bFill;
	}
	
	public CInitialValue(double d, boolean bFill)
	{
		m_genericValue = new GenericValueDouble(d); 
		m_bFill = bFill;
	}
	
	public CInitialValue(String csValue, boolean bFill)
	{
		m_genericValue = new GenericValueString(csValue); 
		m_bFill = bFill;
	}
	

	public CInitialValue(char c, boolean bFill)
	{
		m_genericValue = new GenericValueChar(c);
		m_bFill = bFill;
	}
	
//	public void apply()
//	{
//		if(m_var != null)
//		{
//			if(!m_bFill)
//			{
//				if(m_sValue != null)
//					m_var.set(m_sValue);
//				else 
//					m_var.set(m_c);
//			}
//			else
//			{
//				if(m_sValue != null)
//					m_var.m_VarManager.setAndFillWithType(m_sValue);
//				else 
//					m_var.m_VarManager.setAndFillWithType(m_c);
//			}
//		}
//	}
	
	public String toString()
	{
		return "GenericValue="+m_genericValue.getAsString() + " m_bFill="+m_bFill;
	}
	
	GenericValue m_genericValue;
	boolean m_bFill = false;
}

