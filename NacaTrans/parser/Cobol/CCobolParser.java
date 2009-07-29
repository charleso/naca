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
package parser.Cobol;

import lexer.CTokenList;
import parser.CParser;
import parser.Cobol.elements.CProgram;

public class CCobolParser extends CParser<CProgram>
{


	@Override
	protected boolean DoParsing(CTokenList lstTokens)
	{
		CProgram p = new CProgram(lstTokens.GetCurrentToken().getLine()) ;
		m_eRoot = p ;
		ms_bCommaIsDecimalPoint = false ;
		boolean bParsed = p.Parse(lstTokens, m_CommentContainer) ; 
		return bParsed ;
	}

	public static boolean ms_bCommaIsDecimalPoint = false ;

	
}
