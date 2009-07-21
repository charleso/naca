/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 17 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

import nacaLib.tempCache.CStr;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class VarAlphaNum extends Var
{
	VarAlphaNum(DeclareTypeX declareTypeX)
	{
		super(declareTypeX);
	}
		
	protected VarAlphaNum()
	{
		super();
	}
	
	protected VarBase allocCopy()
	{
		VarAlphaNum v = new VarAlphaNum();
		return v;
	}
	

	protected String getAsLoggableString()
	{
		CStr cstr = m_bufferPos.getOwnCStr(m_varDef.getLength());		
		String cs = cstr.getAsString();
		//cstr.resetManagerCache();
		return cs;
	}	
	
	public boolean hasType(VarTypeEnum e)
	{
		if(e == VarTypeEnum.TypeX)
			return true;
		return false;
	}
	
	public int compareTo(int nValue)
	{
		int nVarValue;
		if (getString().trim().equals(""))
			nVarValue = -1;
		else
			nVarValue = getInt();
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
		return doConvertEbcdicToUnicode(tBytes);
	}
	
	
	public VarType getVarType()
	{
		return VarType.VarAlphaNum;
	}
	
	@Override
	public VarAndEdit subString(int start, int length) {
		VarAlphaNum num = new VarAlphaNum();
		VarDefBuffer def = m_varDef.allocCopy();
		start--;
		def.m_nTotalSize = Math.min(m_varDef.m_nTotalSize, length - start);
		num.m_varDef = def;
		num.m_bufferPos = new VarBufferPos(m_bufferPos, m_varDef.m_nDefaultAbsolutePosition + start);
		return num;
	}
}


