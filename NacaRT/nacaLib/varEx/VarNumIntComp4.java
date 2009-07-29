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

import jlib.misc.AsciiEbcdicConverter;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class VarNumIntComp4 extends VarNum
{
	VarNumIntComp4(DeclareType9 declareType9)
	{
		super(declareType9);
	}
	
	protected VarNumIntComp4()
	{
		super();
	}
	
	protected VarBase allocCopy()
	{
		VarNumIntComp4 v = new VarNumIntComp4();
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
		return ebcdicToUnicodeInBinaryFormat(tBytes);
	}
	
	public VarType getVarType()
	{
		return VarType.VarNumIntComp4;
	}
}
