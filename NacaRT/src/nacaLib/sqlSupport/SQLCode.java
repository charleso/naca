/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 14 janv. 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.sqlSupport;

import jlib.misc.ArrayFixDyn;
import jlib.xml.Tag;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SQLCode
{
//	info link : http://www.caliberdt.com/tips/sqlcode.htm
	private static int ms_tnFlagIsKillerSQLCode[] = null;
	//private static int ms_tBits[] = null;
	
	public static int SQL_DUPLICATE_INDEX_KEY = -803;
	public static int SQL_MORE_THAN_ONE_ROW = -811;
	public static int SQL_OK = 0 ; 
	public static int SQL_ERROR = -1 ; 
	public static int SQL_NOT_FOUND = 100 ;
	public static int SQL_CURSOR_NOT_OPEN = -501 ;
	public static int SQL_CURSOR_ALREADY_OPENED = -502 ;
	public static int SQL_VALUE_NULL = -305;
	private static final int NB_MAXI_SQLCODE = 100000;	// Maxi SQLCode
	
	
	public static void init()
	{
		int nMax = NB_MAXI_SQLCODE / 32;
		ms_tnFlagIsKillerSQLCode = new int[nMax];
		for(int n=0; n<nMax; n++)
		{
			ms_tnFlagIsKillerSQLCode[n] = 0;
		}
		
//		ms_tBits = new int[32];
//		int nVal = 1;
//		for(int n=0; n<32; n++)
//		{
//			ms_tBits[n] = nVal;
//			nVal <<= 1;
//		}
	}

	public static int Select(int i)
	{
		switch (i)
		{
			case 0 :
				return SQL_OK ;
			case 100 :
				return SQL_NOT_FOUND ;
			case -811 :
				return SQL_MORE_THAN_ONE_ROW ;
			case -803 :
				return SQL_DUPLICATE_INDEX_KEY ;
			case -501 :
				return SQL_CURSOR_NOT_OPEN ;
			case -502 :
				return SQL_CURSOR_ALREADY_OPENED;
			case -305 :
				return SQL_VALUE_NULL;	
			default :
				return SQL_ERROR ;
		}
	}
	
	// @see http://publib.boulder.ibm.com/infocenter/db2help/index.jsp?topic=/com.ibm.db2.udb.doc/ad/c0004672.htm
	public static boolean isError(int n)
	{
		/* 
		if(n == 0 || n == 100 || n == -811 || n == -803)
			return false;
		return true;
		*/
		//if(n < 0 && n != SQL_MORE_THAN_ONE_ROW)
		if(n < 0)	
			return true;
		return false;
	}
	
	public static boolean isNotFound(int n)
	{
		if(n == 100)
			return true;
		return false;
	}

	public static boolean isWarning(int n)
	{
		if(n > 0 && n != 100)
			return true;
		return false;
	}
	
	public static boolean isNormal(int n)
	{
		if(n == 0)
			return true;
		return false;
	}
	
	public static void fillConnectionKillerSQLCodes(Tag tagConnectionKillerSQLCodes)
	{
		Tag tagEntry = tagConnectionKillerSQLCodes.getEnumChild("ConnectionKillerSQLCode");
		while(tagEntry != null)
		{
			int nCodeId = tagEntry.getValAsInt("Value");
			
			if(nCodeId < 0)	// Only negative code are supported between [0, 1000
			{
				nCodeId = -nCodeId;
				if(nCodeId <= NB_MAXI_SQLCODE)
				{
					setBitForCodeId(nCodeId);
				}					
			}
			tagEntry = tagConnectionKillerSQLCodes.getEnumChild("ConnectionKillerSQLCode");
		}
	}
	
	public static void setBitForCodeId(int nCodeId)
	{
		int nIntIndex = nCodeId / 32;
		int nBitIndex = nCodeId % 32; 
		int nBitValue = 1 << nBitIndex;
		ms_tnFlagIsKillerSQLCode[nIntIndex] |= nBitValue;
	}
	
	public static boolean isConnectionKillerSQLCode(int nCodeId)
	{
//		if(n == 100)
//			return true;	// PJD to remove
		if(nCodeId < 0)
			nCodeId = -nCodeId;
		if(nCodeId <= NB_MAXI_SQLCODE)
		{
			int nIntFlags = ms_tnFlagIsKillerSQLCode[nCodeId >> 5];
			int nBitValue = 1 << (nCodeId & 0x1f);	//% 32;	// Keep 
//			int nBitValue = ms_tBits[nBitIndex];
			return ((nIntFlags & nBitValue) != 0);
		}
		return false;
	}
}

