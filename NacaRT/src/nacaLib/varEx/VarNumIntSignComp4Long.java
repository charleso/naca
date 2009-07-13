/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.varEx;

import jlib.misc.AsciiEbcdicConverter;


/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class VarNumIntSignComp4Long extends VarNum
{
	protected VarNumIntSignComp4Long(DeclareType9 declareType9)
	{
		super(declareType9);
	}
	
	protected VarNumIntSignComp4Long()
	{
		super();
	}
	
	protected VarBase allocCopy()
	{
		VarNumIntSignComp4 v = new VarNumIntSignComp4();
		return v;
	}
	
	public boolean DEBUGisStorageAscii()
	{
		return false;
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
		long nVarValue = getLong();
		long l = nVarValue - nValue;
		if(l < 0)
			return -1;
		else if(l == 0)
			return 0;
		return 1;
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
		return ebcdicToUnicodeInBinaryFormat(tBytes);
	}
	
	public VarType getVarType()
	{
		return VarType.VarNumIntSignComp4Long;
	}
}
