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
import semantic.SQL.CEntitySQLLock;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecSQLLock extends CBaseExecSQLAction
{
	/**
	 * 
	 */
	public CExecSQLLock(int line)
	{
		super(line);
	}
	public Element ExportCustom(Document root)
	{
		Element eExe = root.createElement("SQLLock");
		eExe.setAttribute("Table", m_idTable.GetName());
		return eExe;
	}
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntitySQLLock lock = factory.NewEntitySQLLock(getLine()) ;
		lock.setTable(m_idTable.GetName()) ;
		parent.AddChild(lock) ;
		return lock ;
	}
	protected boolean DoParsing()
	{
		CBaseToken tok = GetNext() ;
		if (tok.GetKeyword() == CCobolKeywordList.TABLE)
		{
			tok = GetNext();
		}
		if (tok.GetType() == CTokenType.IDENTIFIER)
		{
			m_idTable = new CIdentifier(tok.GetValue()) ;
			tok = GetNext() ;
		}
		else
		{
			return false ;
		}
		if (tok.GetKeyword() == CCobolKeywordList.IN)
		{
			tok = GetNext() ;
		}
		if (tok.GetKeyword() == CCobolKeywordList.EXCLUSIVE)
		{
			tok = GetNext() ;
		}
		if (tok.GetKeyword() == CCobolKeywordList.MODE)
		{
			tok = GetNext() ;
		}
		return true ;
	}
	
	protected CIdentifier m_idTable = null ;
}
