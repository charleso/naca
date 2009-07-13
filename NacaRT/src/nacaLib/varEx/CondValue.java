/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 26 nov. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author PJD
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.varEx;

//import nacaLib.program.Var;

public class CondValue
{
	CondValue(String sMin, String sMax)
	{
		m_sMin = sMin;
		m_sMax = sMax;
		m_bInterval = true;
		m_constant = null;
	}

	CondValue(String sValue)
	{
		m_sMin = sValue;
		m_bInterval = false;
		m_constant = null;
	}
	
	CondValue(CobolConstantBase constant)
	{
		m_constant = constant;
		m_bInterval = false;
	}
	
	public boolean is(Var v)
	{
		if(m_constant != null)
			return v.is(m_constant);
		else
		{
			if(m_bInterval)
			{
				if(v.compareTo(ComparisonMode.Unicode, m_sMin) >= 0 && v.compareTo(ComparisonMode.Unicode, m_sMax) <= 0)
					return true;
			}
			if(v.equals(m_sMin))
				return true;
		}
		return false;		  
	}
	
	public String getMin()
	{
		if(m_constant == null)			
			return m_sMin;
		return null;
	}
	
	public String toString()
	{
		if(m_constant != null)		
			return m_constant.getSTCheckValue();		
		if(m_bInterval)
			return "[" + m_sMin + "," + m_sMax + "]";
		return m_sMin;
	}
	
	private CobolConstantBase m_constant = null;
	private String m_sMin = null; 
	private String m_sMax = null;
	private boolean m_bInterval = false;
}