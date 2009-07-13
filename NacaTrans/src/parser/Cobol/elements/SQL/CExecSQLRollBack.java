/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Sep 7, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements.SQL;

import lexer.CBaseToken;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.SQL.CEntitySQLRollBack;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecSQLRollBack extends CBaseExecSQLAction
{
	/**
	 * @param l
	 */
	public CExecSQLRollBack(int l)
	{
		super(l);
	}
	public Element ExportCustom(Document root)
	{
		Element e = root.createElement("SQLRollBack") ;
		return e ;
	}
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntitySQLRollBack eRB = factory.NewEntitySQLRollBack(getLine());
		parent.AddChild(eRB) ;
		return eRB;
	}
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetValue().equals("ROLLBACK"))
		{
			tok = GetNext();
		}
		return true ;
	}
}
