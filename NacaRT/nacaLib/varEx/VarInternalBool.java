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
 * Created on 28 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

import jlib.misc.AsciiEbcdicConverter;

/**
 * @author U930CV
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class VarInternalBool extends Var
{
	private boolean m_bValue = false ;
	/**
	 * @param declareTypeBase
	 */
	public VarInternalBool()
	{
		super(null);
		m_varDef = new VarDefInternalBool(this);
		m_varTypeId = m_varDef.getTypeId();
		m_bValue = false;
	}

	/* (non-Javadoc)
	 * @see nacaLib.varEx.Var#allocCopy()
	 */
	protected VarBase allocCopy()
	{
		VarInternalBool v = new VarInternalBool();
		return v;
	}

	/* (non-Javadoc)
	 * @see nacaLib.varEx.VarBase#getAsLoggableString()
	 */
	protected String getAsLoggableString()
	{
		if (m_bValue)
		{
			return "true" ;
		}
		else
		{
			return "false" ;
		}
	}

	/* (non-Javadoc)
	 * @see nacaLib.varEx.VarBase#hasType(nacaLib.varEx.VarTypeEnum)
	 */
	public boolean hasType(VarTypeEnum e)
	{
		return false;
	}

	public void set(int n)
	{
		if(n == 0)
			m_bValue = false;
		else
			m_bValue = true;
	}

	public void set(long l)
	{
		if(l == 0)
			m_bValue = false;
		else
			m_bValue = true;
	}

	public void set(String cs)
	{
		if(cs.equalsIgnoreCase("false") || cs.equalsIgnoreCase("0")) 
			m_bValue = false;
		else
			m_bValue = true;
	}

	public void set(boolean b)
	{
		m_bValue = b ;
	}

	public boolean compareTo(boolean b)
	{
		return (m_bValue && b) || (!m_bValue && !b);
	}
	
	public boolean getBool()
	{
		return m_bValue;
	}
	
	public int getInt()
	{
		if(m_bValue)
			return 1;
		return 0;
	}
		
	public long getLong()
	{
		if(m_bValue)
			return 1L;
		return 0L;
	}
	
	public double getDouble()
	{
		if(m_bValue)
			return 1.0;
		return 0.0;
	}
	
	public String getString()
	{
		if(m_bValue)
			return "1";
		return "0";
	}
	
	public String toString()
	{
		if(m_bValue)
			return "true";
		return "false";
	}
	
	public int compareTo(int nValue)
	{
		int nVarValue = getInt();
		return nVarValue - nValue;
	}
	
	
	public int compareTo(double dValue)
	{
		double dVarValue = getDouble();
		double d = dVarValue - dValue;
		if(d < -0.00001)	//Consider epsilon precision at 10 e-5 
			return -1;
		else if(d > 0.00001)	//Consider epsilon precision at 10 e-5
			return 1;
		return 0;			
	} 
	

	protected byte[] convertUnicodeToEbcdic(char [] tChars)
	{
		return AsciiEbcdicConverter.noConvertUnicodeToEbcdic(tChars);
	}
	
	protected char[] convertEbcdicToUnicode(byte[] tBytes)
	{
		return AsciiEbcdicConverter.noConvertEbcdicToUnicode(tBytes);
	}
	

	public VarType getVarType()
	{
		return VarType.VarInternalBool;
	}
}
