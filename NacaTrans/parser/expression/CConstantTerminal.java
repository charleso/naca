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
 * Created on Jul 27, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.expression;

import lexer.Cobol.CCobolConstantList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.Cobol.elements.SQL.SQLSetDateTimeType;
import semantic.CDataEntity;
import semantic.CBaseEntityFactory;
import semantic.expression.CEntityConstant;
import utils.modificationsReporter.Reporter;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CConstantTerminal extends CTerminal
{
	public CConstantTerminal(String val)
	{
		m_csValue = val ;
	}
	/* (non-Javadoc)
	 * @see parser.condition.CConditionalTerminal#Export()
	 */
//	public String GetType()
//	{
//		return "Constant" ;
//	}
	public String GetValue()
	{
		return m_csValue;
	}
	String m_csValue = "" ;
	/* (non-Javadoc)
	 * @see parser.expression.CTerminal#ExportTo(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void ExportTo(Element e, Document root)
	{
		e.setAttribute("Constant", m_csValue) ;		
	}
	/* (non-Javadoc)
	 * @see parser.expression.CTerminal#IsReference()
	 */
	public boolean IsReference()
	{
		return false;
	}
	
	public boolean IsOne()
	{
		return false;
	}
	
	public boolean IsMinusOne()
	{
		return false;
	}


	
	/* (non-Javadoc)
	 * @see parser.expression.CTerminal#ExportTo(semantic.CBaseExporter)
	 */
//	public void ExportTo(CBaseLanguageExporter e)
//	{
//		e.WriteWord(m_csValue.toUpperCase()) ;
//	}
	/* (non-Javadoc)
	 * @see parser.expression.CTerminal#GetDataEntity(semantic.CBaseEntityFactory)
	 */
	public CDataEntity GetDataEntity(int nLine, CBaseEntityFactory factory)
	{
		if (m_csValue.equals("ZERO") || m_csValue.equals("ZEROS") || m_csValue.equals("ZEROES"))
		{
			return factory.NewEntityNumber("0");
		}
		else if (m_csValue.equals("SPACE") || m_csValue.equals("SPACES"))
		{
			return factory.NewEntityString(" ");
		}
		/*	// PJD Commented
		else if (m_csValue.equals("CURRENT TIMESTAMP"))
		{
			return factory.NewEntityNumber(m_csValue);
		}
		*/
		else if (m_csValue.equals("CURRENT_TIMESTAMP") || m_csValue.equals("CURRENT TIMESTAMP"))	// PJD New code for CURRENT_TIMESTAMP
		{
			Reporter.Add("Modif_PJ", "Constant CURRENT_TIMESTAMP | CURRENT TIMESTAMP");
			return factory.NewEntityCurrentTimeStampSQLFunction(m_csValue);
			//return factory.NewEntityString("CURRENT_TIMESTAMPXXX");
		}
		else if (m_csValue.equals("CURRENT_DATE") || m_csValue.equals("CURRENT DATE"))	// PJD New code for CURRENT_DATE
		{
			Reporter.Add("Modif_PJ", "Constant CURRENT_DATE | CURRENT DATE");
			return factory.NewEntityCurrentDateSQLFunction(m_csValue);
			//return factory.NewEntityCurrentDate();
		}
		else if (m_csValue.equals("DEFAULT"))	// PJD New code for DEFAULT
		{
			Reporter.Add("Modif_PJ", "Constant DEFAULT");
			return factory.NewEntityNamedSQLFunction(m_csValue);
		}
		else if (m_csValue.equals("SQL_NULL"))	// PJD New code for SQL_NULL
		{
			Reporter.Add("Modif_PJ", "Constant SQL_NULL");
			return factory.NewEntitySQLNull();
		}
		else if (m_csValue.equals("LOW-VALUE"))	// PJD Added PJ
		{
			Reporter.Add("Modif_PJ", "Constant LOW_VALUE");
			return factory.NewEntityConstant(CEntityConstant.Value.LOW_VALUE);
		}
		else if (m_csValue.equals("HIGH-VALUE"))	// PJD Added PJ
		{
			return factory.NewEntityConstant(CEntityConstant.Value.HIGH_VALUE);
		}

		else if (m_csValue.equals(CCobolConstantList.QUOTE.m_Name) || m_csValue.equals(CCobolConstantList.QUOTES.m_Name))
		{
			char[] b = {'"'} ;
			return factory.NewEntityString(b);
		}
		return null ;
//		CBaseTranscoder.ms_logger.error("ERROR : missing Special test for constant "+m_csValue);
//		return factory.NewEntityString(m_csValue);
	}
	public String toString()
	{
		return m_csValue ;
	}

	public boolean IsNumber()
	{
		return false ;
	}
}
