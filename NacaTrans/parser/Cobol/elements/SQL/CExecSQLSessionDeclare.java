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
package parser.Cobol.elements.SQL;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.SQL.CEntitySQLSessionDeclare;

public class CExecSQLSessionDeclare extends CBaseExecSQLAction
{
	public CExecSQLSessionDeclare(int line)
	{
		super(line);
	}
	public Element ExportCustom(Document root)
	{
		Element eExe = root.createElement("SQLSessionDeclare");
		eExe.setAttribute("sql", m_csSql);
		return eExe;
	}
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntitySQLSessionDeclare session = factory.NewEntitySQLSessionDeclare(getLine()) ;
		session.setSql(m_csSql);
		parent.AddChild(session);
		return session;
	}
	protected boolean DoParsing()
	{
		CBaseToken tok;
		
		m_csSql = "DECLARE GLOBAL";
		boolean bDone = false ;
		while (!bDone)
		{
			tok = GetNext() ;
			if (tok.GetType()==CTokenType.DOT)
			{
				m_csSql += tok.GetType().GetSourceValue();
				tok = GetNext();
				m_csSql += tok.GetValue();
				m_csSql += " ";
			}
			else if (tok.GetType()==CTokenType.LEFT_BRACKET)
			{
				m_csSql += tok.GetType().GetSourceValue();
				tok = GetNext();
				m_csSql += tok.GetValue();
			}
			else if (tok.GetType()==CTokenType.RIGHT_BRACKET)
			{
				m_csSql += tok.GetType().GetSourceValue();				
			}
			else if (tok.GetType()==CTokenType.COMMA)
			{
				m_csSql += tok.GetType().GetSourceValue();
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
