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

import jlib.misc.AsciiEbcdicConverter;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class VarNumIntSignComp0 extends VarNum
{
	VarNumIntSignComp0(DeclareType9 declareType9)
	{
		super(declareType9);
	}
	
	protected VarNumIntSignComp0()
	{
		super();
	}
	
	protected VarBase allocCopy()
	{
		VarNumIntSignComp0 v = new VarNumIntSignComp0();
		return v;
	}
	
	public int compareTo(ComparisonMode mode, String csValue)
	{
		double dValue = 0;
		try
		{
			dValue = new Double(csValue).doubleValue();
		}
		catch (Exception ex)
		{	
		}
		return compareTo(dValue);
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
		byte tByte[]  = doConvertUnicodeToEbcdic(tChars);
		// Do not convert sign byte, as it has no ebcdic encoding value
		int nPosSign = tChars.length - 1;
		tByte[nPosSign] = (byte)tChars[nPosSign];
		return tByte;
	}
	
	protected char[] convertEbcdicToUnicode(byte[] tBytes)
	{
		char t [] = AsciiEbcdicConverter.convertEbcdicToUnicode(tBytes);
		int nLength = t.length;
		if(nLength > 0)
		{
			byte byt = tBytes[nLength-1];
			int n = byt;
			if(byt < 0)
				n += 256;
			t[nLength-1] = (char)n;
		}
		return t;
	}
	
	public VarType getVarType()
	{
		return VarType.VarNumIntSignComp0;
	}
}
