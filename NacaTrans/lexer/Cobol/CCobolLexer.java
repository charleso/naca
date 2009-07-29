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
 * Created on Jul 15, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package lexer.Cobol;


import lexer.CBaseLexer;
import lexer.CBaseToken;


/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class CCobolLexer extends CBaseLexer
{

	public CCobolLexer()
	{
		super(6, 66, CCobolKeywordList.List, CCobolConstantList.List) ;
	}

	public CCobolLexer(int ignored, int utils)
	{
		super(ignored, utils, CCobolKeywordList.List, CCobolConstantList.List) ;
	}

	@Override
	protected boolean IsCommentMarker(char current, boolean isNewLine)
	{
		if (isNewLine && m_nCurrentPositionInLine == 0)
		{
			if (current == '*')
			{
				return true ;
			}
			else  if (current == '/')
			{
				return true ;
			}
		}
		return false;
	}

	/**
	 * @see lexer.CBaseLexer#handleSpecialCharacter(char)
	 */
	@Override
	protected CBaseToken handleSpecialCharacter(char current)
	{
		if (current == 'x' || current == 'X')
		{
			if (m_arrCurrentLine[m_nCurrentPositionInLine+1] == '\'')
			{ // there is a HEXA string
				m_nCurrentPositionInLine += 2 ; // consume X and '
				CBaseToken tok = ReadHexaString();
				return tok ;
			}
		}
		return null ;
	}

	

}
