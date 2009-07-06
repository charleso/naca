/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 8 sept. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements.SQL;

import lexer.CBaseToken;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.SQL.CEntitySQLCommit;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecSQLCommit extends CBaseExecSQLAction
{

	/**
	 * @param l
	 */
	public CExecSQLCommit(int l)
	{
		super(l);
	}
	public Element ExportCustom(Document root)
	{
		Element e = root.createElement("SQLCommit") ;
		return e ;
	}
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntitySQLCommit eRB = factory.NewEntitySQLCommit(getLine());
		parent.AddChild(eRB) ;
		return eRB;
	}
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.COMMIT)
		{
			tok = GetNext();
		}
		
		if (tok.GetKeyword() == CCobolKeywordList.WORK)
		{
			tok = GetNext();
		}
		return true ;
	}

}
