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
 * Created on 15 sept. 2004
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
import semantic.SQL.CEntitySQLExecute;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecSQLExecute extends CBaseExecSQLAction
{
	public CExecSQLExecute(int l)
	{
		super(l);
	}
	public Element ExportCustom(Document root)
	{
		Element eExe = root.createElement("SQLExecute");
		eExe.setAttribute("Var", m_idVar.GetName());
		return eExe;
	}
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntitySQLExecute exec = factory.NewEntitySQLExecute(getLine()) ;
		CDataEntity var = m_idVar.GetDataReference(getLine(), factory) ;
		exec.setVar(var) ;
		parent.AddChild(exec) ;
		return exec ;
	}
	protected boolean DoParsing()
	{
		CBaseToken tok = GetNext() ;
		
		if (tok.GetKeyword() == CCobolKeywordList.IMMEDIATE)
		{
			tok = GetNext();
		}
		if (tok.GetType() == CTokenType.COLON)
		{
			tok = GetNext();
			m_idVar = ReadIdentifier();
		}
		return true ;
	}
	
	protected CIdentifier m_idVar = null ;
}
