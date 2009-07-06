/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 19 août 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements.SQL;

import lexer.*;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;

import semantic.SQL.CEntitySQLCloseStatement;
import semantic.SQL.CEntitySQLCursor;

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecSQLClose extends CBaseExecSQLAction
{
	public CExecSQLClose(int l)
	{
		super(l);
	}

	public Element ExportCustom(Document root)
	{
		Element e = root.createElement("SQLCloseCursor") ;
		e.setAttribute("Name", m_csCursorName);
		return e;
	}

	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntitySQLCursor cur = factory.m_ProgramCatalog.GetSQLCursor(m_csCursorName) ;
		if (cur != null)
		{
			CEntitySQLCloseStatement eSQL = factory.NewEntitySQLCloseStatement(getLine(), cur) ;
			parent.AddChild(eSQL) ;
			return eSQL;
		}
		return null ;
	}
	
	protected boolean DoParsing()
	{
		// Parse until reaching END-EXEC.
		boolean bDone = false ;
		
		while (!bDone)
		{
			CBaseToken tok = GetCurrentToken() ;
			if (tok.GetType() == CTokenType.IDENTIFIER)
			{
				m_csCursorName = new String(tok.GetValue());
			}
			if (tok.GetKeyword() == CCobolKeywordList.END_EXEC)
			{
				bDone = true ;
				break;
			}
			GetNext();
		}	
		return true ; 	
	}
	
	private String m_csCursorName = null;


}
