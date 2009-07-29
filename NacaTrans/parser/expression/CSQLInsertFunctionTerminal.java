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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CIdentifier;

import semantic.CBaseEntityFactory;
import semantic.CDataEntity;
import utils.modificationsReporter.Reporter;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CSQLInsertFunctionTerminal extends CTerminal
{
	public CSQLInsertFunctionTerminal(CIdentifier id, String format)
	{
		m_id = id;
		m_csFormat = format;
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
		return null;
	}
	
	CIdentifier m_id = null;
	String m_csFormat = "";
	/* (non-Javadoc)
	 * @see parser.expression.CTerminal#ExportTo(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void ExportTo(Element e, Document root)
	{
		e.setAttribute("CSQLInsertFunctionTerminal", m_id.GetName()) ;		
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
		Reporter.Add("Modif_PJ", "NewEntityInsertSQLFunction");
		return factory.NewEntityInsertSQLFunction(m_id, m_csFormat);
	}
	public String toString()
	{
		return m_id.GetName();
	}

	public boolean IsNumber()
	{
		return false ;
	}
}
