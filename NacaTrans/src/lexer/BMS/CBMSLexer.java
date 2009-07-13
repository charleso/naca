/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Sep 27, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package lexer.BMS;

import java.io.InputStream;
import java.util.Vector;

import lexer.CBaseLexer;
import lexer.CBaseToken;
import lexer.CTokenComment;
import lexer.CTokenString;



/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CBMSLexer extends CBaseLexer
{
	public CBMSLexer()
	{
		super(0, 72, CBMSKeywordList.List, CBMSConstantList.List);
		setIgnoreOriginalListing(true) ;
	}

	protected CBaseToken ReadString(InputStream buffer)
	{
		Vector<Character> val = new Vector<Character>() ;
		char delimit = m_cCurrent ;
		m_nCurrentPositionInLine ++ ; // '\''
		boolean bDone = false ;
		while (!bDone && m_nCurrentPositionInLine < m_nCurrentLineLength)
		{
			m_cCurrent = m_arrCurrentLine[m_nCurrentPositionInLine];
			Character b = new Character(m_cCurrent) ;
			if (m_cCurrent == '*' && m_nCurrentPositionInLine == m_nCurrentLineLength-1)
			{	// the string continues on next line.
				if (!ReadLine(buffer))
				{
					return null ;
				}
				m_nCurrentPositionInLine = 15 ;
				continue ;  
			}
			if (m_cCurrent != delimit && m_cCurrent != '\n' && m_cCurrent != '\r')
			{
				val.add(b) ;
			}
			else if (m_cCurrent == delimit && m_nCurrentPositionInLine==m_nCurrentLineLength-1)
			{
				bDone = true ;
			}
			else if (m_cCurrent == delimit && m_nCurrentPositionInLine==m_nCurrentLineLength-2 
					&& m_arrCurrentLine[m_nCurrentPositionInLine+1]=='*')
			{ // maybe the string continues on the next line : it depends on the first character on the next line.
				if (!ReadLine(buffer))
				{
					return null ;
				}
				m_nCurrentPositionInLine = 15 ;
				if (m_arrCurrentLine[m_nCurrentPositionInLine] == delimit)
				{
					m_nCurrentPositionInLine ++ ; // in this case current position += 2
					val.add(b) ;
				}
				else
				{
					bDone = true ;
				}
				continue ;  
			}
			else if (m_cCurrent == delimit && m_arrCurrentLine[m_nCurrentPositionInLine+1]==delimit)
			{
				m_nCurrentPositionInLine ++ ; // in this case current position += 2
				val.add(b) ;
			}
			else if (m_cCurrent == delimit)
			{
				bDone = true ;
			}
			else
			{
//					m_nCurrentPositionInLine ++ ;
//					break ;
			}
			m_nCurrentPositionInLine ++ ;
		}
		char[] res = new char[val.size()];
		for (int i=0; i<val.size(); i++)
		{
			Character b = val.get(i) ;
			res[i] = b.charValue() ;
		}
		CBaseToken tok = new CTokenString(res, getLine(), false);
		return tok ;
	}
	
	protected void DoCount(int nbLines, int nbLinesComment, int nbLinesCode)
	{
		 /// nothing
	}
	
	protected CBaseToken ReadComment(InputStream buffer)
	{
		String val = new String() ;
		try 
		{
			m_nCurrentPositionInLine ++ ;
			boolean bDone = false;
			while (!bDone)
			{
				while (m_nCurrentPositionInLine < m_nCurrentLineLength)
				{
					val += m_arrCurrentLine[m_nCurrentPositionInLine] ;
					m_nCurrentPositionInLine ++ ;
				}
				char c = m_arrCurrentLine[m_nCurrentLineLength-1] ;
				if (c == '*')
				{
					ReadLine(buffer) ;
				}
				else
				{
					bDone = true ;
				}
			}
		}
		catch (Exception e)
		{
		}
		CBaseToken tok = new CTokenComment(val, getLine(), true);
		return tok ;
	}

	@Override
	protected boolean IsCommentMarker(char current, boolean isNewLine)
	{
		if (isNewLine && m_nCurrentPositionInLine <= 12)
		{
			if (current == '*' || current == '/')
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
		return null;
	}


	
}
