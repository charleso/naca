/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package parser.Cobol.elements.SQL;

import lexer.CBaseToken;
import lexer.CTokenType;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.SQL.CEntitySQLCursor;
import utils.Transcoder;

/**
 * @author S. Charton
 * @version $Id: CExecSQLVariableCursor.java,v 1.4 2007/10/30 16:09:13 u930bm Exp $
 */
public class CExecSQLVariableCursor extends CBaseExecSQLAction
{
	protected String m_csStatementName ;
	protected String m_csCursorName ;
	/**
	 * @see parser.CBaseElement#DoParsing()
	 */
	@Override
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetType() == CTokenType.IDENTIFIER)
		{
			m_csStatementName = tok.GetValue()  ;
			tok = GetNext() ;
		}
		else
		{
			Transcoder.logError(getLine(), "Expecting identifier for DECLARE CURSOR") ;
			return false ;
		}
		return true ;
	}

	/**
	 * @param l
	 */
	public CExecSQLVariableCursor(int l, String curName)
	{
		super(l);
		m_csCursorName = curName ;
	}

	/**
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	@Override
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntitySQLCursor cur = factory.m_ProgramCatalog.GetSQLCursor(m_csCursorName) ;
		if (cur == null)
		{
			cur = factory.NewEntitySQLCursor(m_csCursorName);
		}
		else
		{
			Transcoder.logError(getLine(), "Cursor already defined : " + m_csCursorName);
		}
		factory.m_ProgramCatalog.RegisterSQLCursor(m_csStatementName, cur) ;
		return null ;
		
	}

	/**
	 * @see parser.CBaseElement#ExportCustom(org.w3c.dom.Document)
	 */
	@Override
	protected Element ExportCustom(Document root)
	{
		Element e = root.createElement("DeclareVariableCursor") ;
		e.setAttribute("Name", m_csStatementName) ;
		return e ;
	}

}
