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
 * Created on 17 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

import nacaLib.tempCache.*;


/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class VarGroup extends Var
{
	public VarGroup(DeclareTypeG declareTypeG)
	{
		super(declareTypeG);
	}
	
	protected VarGroup()
	{
		super();
	}
	
	protected VarBase allocCopy()
	{
		VarGroup v = new VarGroup();
		return v;
	}

	/* (non-Javadoc)
	 * @see nacaLib.varEx.VarBase#getAsLoggableString()
	 */
	protected String getAsLoggableString()
	{
		// return m_varDef.getRawStringIncludingHeader(m_bufferPos);
		CStr cstr = m_bufferPos.getOwnCStr(m_varDef.getLength());
		String cs = cstr.getAsString();
		//cstr.resetManagerCache();
		return cs;
	}
	
	public boolean hasType(VarTypeEnum e)
	{
		if(e == VarTypeEnum.TypeGroup)
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
	

	protected byte[] convertUnicodeToEbcdic(char [] tChars)
	{
		return doConvertUnicodeToEbcdic(tChars);
	}
	
	protected char[] convertEbcdicToUnicode(byte[] tBytes)
	{
		return doConvertEbcdicToUnicode(tBytes);
	}


	public VarType getVarType()
	{
		return VarType.VarGroup;
	}	
	
	public void initialize(InitializeCache initializeCache)
	{
		if(initializeCache != null && initializeCache.isFilled())	// initializeCache may be null 
		{
			initializeCache.applyItems(m_bufferPos, m_bufferPos.m_nAbsolutePosition);
			//m_varDef.initializeUsingCache(m_bufferPos, initializeCache);
		}
		else	
		{
			TempCache tempCache = TempCacheLocator.getTLSTempCache();
			InitializeManager initializeManagerManager = tempCache.getInitializeManagerNone();
			
			int nOffset = getInitializeReplacingOffset(tempCache);
			m_varDef.initializeItemAndChildren(m_bufferPos, initializeManagerManager, nOffset, initializeCache);
			
			if(initializeCache != null)
				initializeCache.setFilledAndcompress(m_bufferPos.m_nAbsolutePosition);
		}
	}
}


