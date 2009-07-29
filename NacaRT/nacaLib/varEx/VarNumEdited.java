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
 * Created on 1 avr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

import nacaLib.tempCache.*;

/**
 * @author PJD
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class VarNumEdited extends Var
{
	VarNumEdited(DeclareTypeNumEdited declareTypeNumEdited)
	{
		super(declareTypeNumEdited);
	}
	
	protected VarNumEdited()
	{
		super();
	}
	
	protected String getAsLoggableString()
	{
		//return m_varDef.getRawStringIncludingHeader(m_bufferPos);
		CStr cstr = m_bufferPos.getOwnCStr(m_varDef.getLength());
		String cs = cstr.getAsString();
		//cstr.resetManagerCache();
		return cs;
	}
	
	public boolean hasType(VarTypeEnum e)
	{
		if(e == VarTypeEnum.TypeEditedNum)
			return true;
		return false;
	}
	
	protected VarBase allocCopy()
	{
		VarNumEdited v = new VarNumEdited();
		return v;
	}
	
	String getDisplayableString()
	{
		return "tutu";
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
	

	protected byte[] convertUnicodeToEbcdic(char[] tChars)
	{
		return doConvertUnicodeToEbcdic(tChars);
	}
	
	protected char[] convertEbcdicToUnicode(byte[] tBytes)
	{
		char[] tChars = new char[tBytes.length];
		for(int n=0; n<tBytes.length; n++)
		{
			tChars[n] = (char)tBytes[n];
		}
		return tChars;
	}
	
	public VarType getVarType()
	{
		return VarType.VarNumEdited;
	}

}

	
