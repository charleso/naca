/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Jul 19, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CExceptionBloc extends CBlocElement
{
	public CExceptionBloc(int line) {
		super(line);
	}

	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetType()== CTokenType.KEYWORD && tok.GetKeyword() == CCobolKeywordList.EXCEPTION)
		{
			GetNext();
		} 
		return super.DoParsing();
	}

	protected Element ExportCustom(Document root)
	{
		return root.createElement("Exception") ;
	}

	protected boolean isTopLevelBloc()
	{
		return false;
	}
}
