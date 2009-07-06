/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Creat/d on 15 oct. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.varEx;

import jlib.misc.AsciiEbcdicConverter;
import nacaLib.tempCache.CStr;
import nacaLib.tempCache.TempCacheLocator;

public class MapRedefine extends Var
{
	public MapRedefine(DeclareTypeMapRedefine declareTypeMapRedefine)
	{
		super(declareTypeMapRedefine);
		m_formRedefineOrigin = declareTypeMapRedefine.m_formRedefineOrigin;
	}
	
	protected MapRedefine()
	{
		super();
	}
	
	protected VarBase allocCopy()
	{
		MapRedefine v = new MapRedefine();
		return v;
	}

	/* (non-Javadoc)
	 * @see nacaLib.varEx.VarBase#getAsLoggableString()
	 */
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
		return false;
	}

	public void encodeToVar(Var varDest)
	{
		m_varDef.m_varDefFormRedefineOrigin.encodeToVar(m_bufferPos, varDest);
	}
	
	public void decodeFromVar(Var varSource)
	{
		m_varDef.m_varDefFormRedefineOrigin.decodeFromVar(m_bufferPos, varSource);
	}
	
	public InternalCharBuffer encodeToCharBuffer()
	{
		int nDestLength = m_varDef.getBodyLength() + m_varDef.getHeaderLength();
		VarDefForm varDefFormOrigin = m_varDef.m_varDefFormRedefineOrigin;
		return varDefFormOrigin.encodeToCharBuffer(nDestLength);
	}
	
	public void decodeFromCharBuffer(InternalCharBuffer charBufferSource)
	{
		VarDefForm varDefFormOrigin = m_varDef.m_varDefFormRedefineOrigin;
		varDefFormOrigin.decodeFromCharBuffer(m_bufferPos, charBufferSource);
	}
	
	public String getStringIncludingHeader()
	{
		CStr cstr = m_bufferPos.getOwnCStr(m_varDef.getLength());
		String cs = cstr.getAsString();
		//cstr.resetManagerCache();
		return cs;
		//return m_varDef.getRawStringIncludingHeader(m_bufferPos);
	}
	
	public void initialize()
	{
		if(m_formRedefineOrigin != null)
		{
			InitializeCache initializeCache = getProgramManager().getOrCreateInitializeCache(getVarDef());
			m_formRedefineOrigin.initialize(initializeCache);
			// Was before optimizations: m_formRedefineOrigin.initialize();
		}
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
		return AsciiEbcdicConverter.noConvertUnicodeToEbcdic(tChars);
	}
	
	protected char[] convertEbcdicToUnicode(byte[] tBytes)
	{
		return AsciiEbcdicConverter.noConvertEbcdicToUnicode(tBytes);
	}
	
	public VarType getVarType()
	{
		return VarType.VarMapRedefine;
	}
	
	Form m_formRedefineOrigin = null;
}
