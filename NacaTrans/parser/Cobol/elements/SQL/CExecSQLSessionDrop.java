/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package parser.Cobol.elements.SQL;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.Cobol.CCobolElement;
import parser.Cobol.elements.CExecStatement;

import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.SQL.CEntitySQLSessionDrop;

public class CExecSQLSessionDrop extends CBaseExecSQLAction
{
	public CExecSQLSessionDrop(int line)
	{
		super(line);
	}
	public Element ExportCustom(Document root)
	{
		Element eExe = root.createElement("SQLSessionDrop");
		eExe.setAttribute("sql", m_csSql);
		return eExe;
	}
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntitySQLSessionDrop session = factory.NewEntitySQLSessionDrop(getLine()) ;
		session.setSql(m_csSql);
		parent.AddChild(session);
		return session;
	}
	protected boolean DoParsing()
	{
		CBaseToken tok;
		
		m_csSql = "DROP";
		boolean bDone = false ;
		while (!bDone)
		{
			tok = GetNext() ;
			if (tok.GetType()==CTokenType.DOT)
			{
				m_csSql += tok.GetType().GetSourceValue();
				tok = GetNext();
				m_csSql += tok.GetValue();
			}
			else if (tok.GetType()==CTokenType.KEYWORD && tok.GetKeyword()==CCobolKeywordList.END_EXEC)
			{
				bDone = true ;
			}
			else
			{
				m_csSql += " " + tok.GetValue();
			}
		}
		return true ;
	}
	
	protected String m_csSql = null ;
}
