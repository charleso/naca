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
 * Created on Jul 19, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements;

import lexer.*;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CThenBloc extends CBlocElement
{
	/**
	 * @param line
	 */
	public CThenBloc(int line) {
		super(line);
	}

	protected boolean DoParsing(CFlag fCheckForNextSentence)
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetType()== CTokenType.KEYWORD && tok.GetKeyword() == CCobolKeywordList.THEN)
		{
			GetNext();
		} 
		if (!super.DoParsing())
		{
			return false ;
		}
		if (fCheckForNextSentence != null)
		{
			fCheckForNextSentence.Set(m_fCheckForNextSentence.ISSet()) ;
		}
		return true ;
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element eThen = root.createElement("Then") ;
		return eThen ;
	}

	/* (non-Javadoc)
	 * @see parser.elements.CBlocElement#isTopLevelBloc()
	 */
	protected boolean isTopLevelBloc()
	{
		return false;
	}
}
