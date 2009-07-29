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
 * Created on Sep 14, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements.SQL;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CIdentifier;

import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.SQL.CEntitySQLCursor;
import utils.Transcoder;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecSQLPrepare extends CBaseExecSQLAction
{
	/**
	 * @param l
	 */
	public CExecSQLPrepare(int l)
	{
		super(l);
	}
	public Element ExportCustom(Document root)
	{
		Element eExe = root.createElement("SQLPrepare");
		eExe.setAttribute("Table", m_idStatement.GetName());
		eExe.setAttribute("From", m_idFrom.GetName());
		return eExe;
	}
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CDataEntity var = m_idFrom.GetDataReference(getLine(), factory) ;
		CEntitySQLCursor cur = factory.m_ProgramCatalog.GetSQLCursor(m_idStatement.GetName()) ;
		if (cur != null)
		{
			cur.setVariableStatement(var) ;
		}
		else
		{
			Transcoder.logError(getLine(), "Cursor can't be found : "+m_idStatement.GetName())  ;
		}
		return null ;
	}
	protected boolean DoParsing()
	{
		CBaseToken tok = GetNext() ;
		m_idStatement = ReadIdentifier();
		tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.FROM)
		{
			tok = GetNext() ;
			if (tok.GetType() == CTokenType.COLON)
			{
				tok = GetNext() ;
				m_idFrom = ReadIdentifier();
			}
		}
		return true ;
	}
	
	protected CIdentifier m_idStatement = null ;
	protected CIdentifier m_idFrom = null ;
}
