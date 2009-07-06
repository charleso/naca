/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.fpacPrgEnv;

import nacaLib.tempCache.CStr;
import nacaLib.tempCache.TempCacheLocator;
import nacaLib.varEx.Var;
import nacaLib.varEx.VarBase;
import nacaLib.varEx.VarBuffer;
import nacaLib.varEx.VarBufferPos;
import nacaLib.varEx.VarType;
import nacaLib.varEx.VarTypeEnum;

public class VarFPacAlphaNum extends Var
{
	public VarFPacAlphaNum(DeclareTypeFPacAlphaNum declareTypeFPacAlphaNum, VarBuffer varBuffer, int nPosition)
	{
		super(declareTypeFPacAlphaNum);
		m_bufferPos = new VarBufferPos(varBuffer, nPosition);
		m_varDef.setTotalSize(m_varDef.getSingleItemRequiredStorageSize());
	}
	
	public VarFPacAlphaNum(DeclareTypeFPacAlphaNum declareTypeFPacAlphaNum)
	{
		super(declareTypeFPacAlphaNum);
	}
		
	protected VarFPacAlphaNum()
	{
		super();
	}
	
	protected VarBase allocCopy()
	{
		VarFPacAlphaNum v = new VarFPacAlphaNum();
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
		return doConvertEbcdicToUnicode(tBytes);
	}
		
	public VarFPacLengthUndef createVarFPacUndef(FPacVarManager fpacVarManager, VarBuffer varBuffer, int nAbsolutePosition)
	{
		return new VarFPacAlphaNumLengthUndef(fpacVarManager, varBuffer, nAbsolutePosition);
	}
	
	public VarType getVarType()
	{
		return VarType.VarFpacAlphaNum; 
	}
}
