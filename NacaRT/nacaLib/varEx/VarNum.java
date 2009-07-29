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
 * Created on 18 mars 2005
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
public abstract class VarNum extends Var
{
	VarNum(DeclareType9 declareType9)
	{
		super(declareType9);
	}
	
	protected VarNum()
	{
		super();
	}
	
//	public void set(char c)
//	{
//		int n = c - '0';
//	}
	
	protected String getAsLoggableString()
	{
		//return m_varDef.getRawStringIncludingHeader(m_bufferPos);
		CStr cstr = m_bufferPos.getOwnCStr(m_varDef.getLength());
		String cs = cstr.getAsString();
		//cstr.resetManagerCache();;
		return cs;
	}
	
	public boolean hasType(VarTypeEnum e)
	{
		if(e == VarTypeEnum.Type9)
			return true;
		return false;
	}
	
	static protected char[] ebcdicToUnicodeInBinaryFormat(byte[] tBytes)
	{
		int nLength = tBytes.length;
		char tc[]  = new char[nLength];
		//if(nLength <= 4)	// 32 bits chunks are high weight - low eight
		{		
			for(int n=0; n<nLength; n++)
				tc[n] = byteToChar(tBytes[n]);
		}
//		else if(nLength == 8)	// for long, host is 32 bit low weight / 32 bit high weight
//		{
//			for(int n=0; n<4; n++)
//			{
//				tc[n+4] = byteToChar(tBytes[n]);
//				tc[n] = byteToChar(tBytes[n+4]);
//			}
//		}
		return tc;
	}

	static private char byteToChar(byte b)
	{
		if(b < 0)
			return (char) (b + 256);
		return (char) b;
	}
	

	//abstract void setInt(int n);
//	public void set(String cs)
//	{
//		NumberContainer numberContainer = new NumberContainer(cs);
//		// set(numberContainer);
//	}
}
