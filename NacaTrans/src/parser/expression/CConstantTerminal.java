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

import semantic.CDataEntity;
import semantic.CBaseEntityFactory;

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
		else if (m_csValue.equals("CURRENT TIMESTAMP"))
		{
			return factory.NewEntityNumber(m_csValue);
		}
		else if (m_csValue.equals(CCobolConstantList.QUOTE.m_Name) || m_csValue.equals(CCobolConstantList.QUOTES.m_Name))
		{
			char[] b = {'"'} ;
			return factory.NewEntityString(b);
		}
		else if (m_csValue.equals(CCobolConstantList.LOW_VALUE.m_Name) || m_csValue.equals(CCobolConstantList.LOW_VALUES.m_Name))
		{
			return factory.NewEntityString(new char[] { 0 });
		}
		else if (m_csValue.equals(CCobolConstantList.HIGH_VALUE.m_Name) || m_csValue.equals(CCobolConstantList.HIGH_VALUES.m_Name))
		{
			return factory.NewEntityString(new char[] { 255 });
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
