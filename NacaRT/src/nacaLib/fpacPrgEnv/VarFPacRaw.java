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

public class VarFPacRaw extends Var
{
	public VarFPacRaw(DeclareTypeFPacRaw declareTypeFPacRaw, VarBuffer varBuffer, int nPosition)
	{
		super(declareTypeFPacRaw);
		m_bufferPos = new VarBufferPos(varBuffer, nPosition);
		m_varDef.setTotalSize(m_varDef.getSingleItemRequiredStorageSize());
	}
	
	public VarFPacRaw(DeclareTypeFPacRaw declareTypeFPacRaw)
	{
		super(declareTypeFPacRaw);
	}
		
	protected VarFPacRaw()
	{
		super();
	}
	
	public void copy(VarFPacRaw varSource)
	{
		int nNbCharToCopy = Math.min(varSource.getLength(), getLength());
		// m_bufferPos.copyBytes(m_bufferPos.m_nAbsolutePosition, nNbCharToCopy, varSource.getBuffer().m_nAbsolutePosition, varSource.getBuffer());
		m_bufferPos.copy(nNbCharToCopy, varSource.getBuffer());
	}
	
	protected VarBase allocCopy()
	{
		VarFPacRaw v = new VarFPacRaw();
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
		return VarType.VarFPacVarRaw; 
	}
}

	