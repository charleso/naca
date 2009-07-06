/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 12 oct. 04
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
package nacaLib.varEx;

import jlib.misc.AsciiEbcdicConverter;
import jlib.misc.NumberParser;

public class VarInternalInt extends Var
{
	public VarInternalInt()
	{
		super(null);
		m_varDef = new VarDefInternalInt(this);
		m_varTypeId = m_varDef.getTypeId();
		m_n = 0;
	}
	
	protected VarBase allocCopy()
	{
		VarInternalInt v = new VarInternalInt();
		return v;
	}
	
	public void set(int n)
	{
		m_n = n;
	}
	
	public void set(String cs)
	{
		m_n = NumberParser.getAsInt(cs);
	}
	
	public void set(char c)
	{
		m_n = NumberParser.getAsInt(c);
	}
	
//	public void set(GenericValue gv)
//	{
//		//m_n = gv.getInt();
//	}

	protected String getAsLoggableString()
	{
		return String.valueOf(m_n);
	}
	
	public boolean hasType(VarTypeEnum e)
	{
		if(e == VarTypeEnum.Type9)
			return true;
		return false;
	}

	
//	public String getString()
//	{
//		return String.valueOf(m_n); 
//	}
	
//	public String getDottedSignedString()
//	{
//		return String.valueOf(m_n);
//	}
	
	public int getInt()
	{
		return m_n;
	}
	
	public double getDouble()
	{
		return m_n;
	} 

//	VarNumberChunk getNumberChunks()
//	{
//		VarNumberChunk chunk = new VarNumberChunk() ;
//		if(m_n < 0)
//			chunk.setNegative(true);
//		else
//			chunk.setNegative(false);
//		chunk.m_sAbsInt = String.valueOf(Math.abs(m_n));
//		chunk.m_sDec = "" ;
//		return chunk ;
//	}
	
	public String toString()
	{
		return "InternalVar: " + String.valueOf(m_n);
	}
	
	int getSingleItemRequiredStorageSize()
	{
		return 0;
	}

	int m_n;
	
	public void transferTo(Var varDest)
	{
		varDest.set(m_n);
	}
	public String getSTCheckValue()
	{
		return "VarInternalInt("+m_n+")" ;
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
		return VarType.VarInternalInt;
	}
}
