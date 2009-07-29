/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 20 août 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements.SQL;

import jlib.misc.IntegerRef;
import utils.DCLGenConverter.DCLGenConverterTarget;

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CSQLTableColDescriptor
{
	public CSQLTableColDescriptor()
	{
	}
	
	public void SetName(String csName)
	{
		m_csName = csName;
	}
	
	public String GetName()
	{
		return m_csName;
	}
	
//	public boolean hasName(String csColName)
//	{
//		if(m_csName.equalsIgnoreCase(csColName))
//			return true;
//		return false;
//	}
	
	void SetLength(int n)
	{
		m_nLength = n;
		m_bLengthSet = true;
	}	

	void SetDecimal(int n)
	{
		m_nDecimal = n;
		m_bDecimalSet = true;
	}
	
	public boolean HasSize()
	{
		return m_bLengthSet;
	}
	
	public String GetSizes()
	{
		if(m_bLengthSet)
		{
			if(m_bDecimalSet)
				return String.valueOf(m_nLength) + ", " + String.valueOf(m_nDecimal);
			return String.valueOf(m_nLength);
		}
		return "";
	}
	
	void SetType(String csType)
	{
		m_csType = csType;
	}
	
	public String GetType()
	{
		return m_csType;
	} 
	
	void SetNull(boolean b)
	{
		m_bNull = b;
	}
	
	void SetNotNull(boolean b)
	{
		m_bNotNull = b;
	}
	
	public boolean IsNull()
	{
		return m_bNull;
	}
	
	public boolean IsNotNull()
	{
		return m_bNotNull;
	}

	
	public void prepareExport(DCLGenConverterTarget target, IntegerRef nNameWidth, IntegerRef nTypeWidth)
	{
		if(target.isDB2())
			prepareExportDB2(nNameWidth, nTypeWidth);
		if(target.isOracle())
			prepareExportOracle(nNameWidth, nTypeWidth);
		
	}
	
	private void prepareExportOracle(IntegerRef nNameWidth, IntegerRef nTypeWidth)
	{
		String cs = GetName();
		if(cs.length() > nNameWidth.get())
			nNameWidth.set(cs.length());
		
		cs = GetCreateTypeOracle();
		if(cs.length() > nTypeWidth.get())
			nTypeWidth.set(cs.length());
	}
	
	private void prepareExportDB2(IntegerRef nNameWidth, IntegerRef nTypeWidth)
	{
		String cs = GetName();
		if(cs.length() > nNameWidth.get())
			nNameWidth.set(cs.length());
		
		cs = GetCreateTypeDB2();
		if(cs.length() > nTypeWidth.get())
			nTypeWidth.set(cs.length());
	}
		
	public StringBuilder getColumnCreationOrder(DCLGenConverterTarget target, int nNameWidth, int nTypeWidth, boolean bSetComma, boolean bAddOriginalType)
	{
		if(target.isDB2())
			return getColumnCreationOrderDB2(nNameWidth, nTypeWidth, bSetComma);
		if(target.isOracle())			
			return getColumnCreationOrderOracle(nNameWidth, nTypeWidth, bSetComma, bAddOriginalType);
		return null;
	}
	
	private StringBuilder getColumnCreationOrderOracle(int nNameWidth, int nTypeWidth, boolean bSetComma, boolean bAddOriginalType)
	{
		StringBuilder sb = new StringBuilder(GetName());
		appendSpaces(sb, nNameWidth+1);
		
		sb.append(GetCreateTypeOracle());
		appendSpaces(sb, nNameWidth+nTypeWidth+2);
		
		if(IsNull())
		{
			sb.append("     NULL");
			appendSpaces(sb, 4);
		}
		else if(IsNotNull())
			sb.append(" NOT NULL");
		else
			sb.append("         ");
			
		
		if(bSetComma)
			sb.append(",");
		
		if(bAddOriginalType)
		{
			sb.append(" -- Original DB2 type: ");
			sb.append(GetCreateTypeDB2());
		}
		
		return sb;
	}
	
	private StringBuilder getColumnCreationOrderDB2(int nNameWidth, int nTypeWidth, boolean bSetComma)
	{
		StringBuilder sb = new StringBuilder(GetName());
		appendSpaces(sb, nNameWidth+1);
		
		sb.append(GetCreateTypeDB2());
		appendSpaces(sb, nNameWidth+nTypeWidth+2);
		
		if(IsNull())
			sb.append("      NULL");
		else if(IsNotNull())
			sb.append("  NOT NULL");
		else
			sb.append("          ");
		if(bSetComma)
			sb.append(",");
		
		return sb;
	}
	
	private void appendSpaces(StringBuilder sb, int nSize)
	{
		while(sb.length() < nSize)
			sb.append(" ");
	}
		
	private String GetCreateTypeOracle()
	{
		String cs = getCorrespondingColumnTypeOracle(GetType());	

		if(m_bLengthSet)
		{
			cs += " (";
			if(m_bDecimalSet)
				cs += String.valueOf(m_nLength) + ", " + String.valueOf(m_nDecimal);
			else 
				cs += String.valueOf(m_nLength);
			cs += ")";
		}
		return cs;
	}
	
	private String GetCreateTypeDB2()
	{
		String cs = GetType();	

		if(m_bLengthSet)
		{
			cs += " (";
			if(m_bDecimalSet)
				cs += String.valueOf(m_nLength) + ", " + String.valueOf(m_nDecimal);
			else 
				cs += String.valueOf(m_nLength);
			cs += ")";
		}
		return cs;
	}
	
	private String getCorrespondingColumnTypeOracle(String csType)
	{
		if(csType.equalsIgnoreCase("CHAR"))
			return "VARCHAR2";
		return csType;
	}
	
	private int m_nLength = 0;
	private int m_nDecimal = 0;
	private boolean m_bDecimalSet = false;
	private boolean m_bLengthSet = false;
	private String m_csType = "";
	private String m_csName = "";
	private boolean m_bNull = false;
	private boolean m_bNotNull = false;
}
