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
 * Created on 14 janv. 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.sqlSupport;

import jlib.log.Log;
import jlib.misc.ArrayFixDyn;
import jlib.misc.NumberParser;
import jlib.misc.StringUtil;
import jlib.xml.Tag;
import jlib.xml.TagCursor;

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
	
	public static SQLCodeValue SQL_DUPLICATE_INDEX_KEY = new SQLCodeValue(-803);
	public static SQLCodeValue SQL_MORE_THAN_ONE_ROW = new SQLCodeValue(-811); 		// PJ
	public static SQLCodeValue SQL_OK = new SQLCodeValue(0); 						// PJ
	public static SQLCodeValue SQL_ERROR = new SQLCodeValue(-1); 
	public static SQLCodeValue SQL_NOT_FOUND = new SQLCodeValue(100);				// PJ
	public static SQLCodeValue SQL_CURSOR_NOT_OPEN = new SQLCodeValue(-501);
	public static SQLCodeValue SQL_CURSOR_ALREADY_OPENED = new SQLCodeValue(-502);
	public static SQLCodeValue SQL_VALUE_NULL = new SQLCodeValue(-305);				// PJ
	public static SQLCodeValue SQL_MINUS_1405  = new SQLCodeValue(-1405);				// PJ
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
	
	public static void fillDbCodes(Tag tagDbSQLCodes)
	{
		TagCursor cur = new TagCursor(); 
		Tag tag = tagDbSQLCodes.getFirstChild(cur, "Code");
		while(tag != null)
		{
			String csName = tag.getVal("Name");
			String csValues = tag.getVal("Values");
			
			if(csName.equalsIgnoreCase("SQL_DUPLICATE_INDEX_KEY"))
				fillCode(SQL_DUPLICATE_INDEX_KEY, csValues);

			else if(csName.equalsIgnoreCase("SQL_MORE_THAN_ONE_ROW"))
				fillCode(SQL_MORE_THAN_ONE_ROW, csValues);
			
			else if(csName.equalsIgnoreCase("SQL_OK"))
				fillCode(SQL_OK, csValues);
			
			else if(csName.equalsIgnoreCase("SQL_ERROR"))
				fillCode(SQL_ERROR, csValues);
			
			else if(csName.equalsIgnoreCase("SQL_NOT_FOUND"))
				fillCode(SQL_NOT_FOUND, csValues);
			
			else if(csName.equalsIgnoreCase("SQL_CURSOR_NOT_OPEN"))
				fillCode(SQL_CURSOR_NOT_OPEN, csValues);
			
			else if(csName.equalsIgnoreCase("SQL_CURSOR_ALREADY_OPENED"))
				fillCode(SQL_CURSOR_ALREADY_OPENED, csValues);
			
			else if(csName.equalsIgnoreCase("SQL_VALUE_NULL"))
				fillCode(SQL_VALUE_NULL, csValues);
			else
				Log.logImportant("SQL Code not recognized: "+csName);
			
			tag = tagDbSQLCodes.getNextChild(cur);
		}		
	}
	
	private static void fillCode(SQLCodeValue sqlCodeValue, String csValues)
	{
		sqlCodeValue.resetCodes();
		doFillCode(sqlCodeValue, csValues);
	}
	
	private static void doFillCode(SQLCodeValue sqlCodeValue, String csValues)
	{	
		int nPos = csValues.indexOf(',');
		if(nPos >= 0)
		{
			String csLeft = csValues.substring(0, nPos);
			String csRight = csValues.substring(nPos+1);
			assignCode(sqlCodeValue, csLeft);
			doFillCode(sqlCodeValue, csRight);
		}
		else
			assignCode(sqlCodeValue, csValues);
	}
	
	private static void assignCode(SQLCodeValue sqlCodeValue, String csValue)
	{
		sqlCodeValue.set(NumberParser.getAsInt(csValue));
	}

	public static SQLCodeValue Select(int n)
	{
		if(SQL_OK.isCode(n))
			return SQL_OK ;
		if(SQL_NOT_FOUND.isCode(n))
			return SQL_NOT_FOUND ;
		if(SQL_MORE_THAN_ONE_ROW.isCode(n))
			return SQL_MORE_THAN_ONE_ROW ;
		if(SQL_DUPLICATE_INDEX_KEY.isCode(n))
			return SQL_DUPLICATE_INDEX_KEY ;
		if(SQL_CURSOR_NOT_OPEN.isCode(n))
			return  SQL_CURSOR_NOT_OPEN;
		if(SQL_CURSOR_ALREADY_OPENED.isCode(n))
			return SQL_CURSOR_ALREADY_OPENED;
		if(SQL_VALUE_NULL.isCode(n))
			return SQL_VALUE_NULL;	
		return SQL_ERROR ;
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

