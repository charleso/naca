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
package lexer.FPac;

import lexer.CBaseLexer;
import lexer.CBaseToken;

public class CFPacLexer extends CBaseLexer
{

	public CFPacLexer()
	{
		super(0, 72, CFPacKeywordList._List_, CFPacConstantList._List_);
	}

	
	protected String ReadWord()
	{
		String val = new String() ;
		val += m_cCurrent ;
		try 
		{
			m_nCurrentPositionInLine ++ ;
			while (m_nCurrentPositionInLine < m_nCurrentLineLength)
			{
				m_cCurrent = m_arrCurrentLine[m_nCurrentPositionInLine] ;
				if (m_cCurrent >= 'a' && m_cCurrent <= 'z')
				{
					m_cCurrent = Character.toUpperCase(m_cCurrent) ;
				}
				if ( (m_cCurrent >= 'A' && m_cCurrent <= 'Z') || (m_cCurrent >= '0' && m_cCurrent <= '9') )
				{
					val += m_cCurrent ;
				}
				else
				{
					break ;
				}
				m_nCurrentPositionInLine ++ ;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return val ;
	}


	@Override
	protected boolean IsCommentMarker(char current, boolean isNewLine)
	{
		if (current == '*')
		{
			return true ;
		}
		else if (isNewLine && current=='$')
		{
			return true ;
		}
		return false;
	}


	/**
	 * @see lexer.CBaseLexer#handleSpecialCharacter(char)
	 */
	@Override
	protected CBaseToken handleSpecialCharacter(char current)
	{
		return null;
	}

	
}
