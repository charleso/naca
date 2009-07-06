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
package lexer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Vector;

import jlib.misc.AsciiEbcdicConverter;

import lexer.CBaseToken;
import lexer.CConstantList;
import lexer.CKeywordList;
import lexer.CReservedConstant;
import lexer.CReservedKeyword;
import lexer.CTokenConstant;
import lexer.CTokenGeneric;
import lexer.CTokenIdentifier;
import lexer.CTokenKeyword;
import lexer.CTokenList;
import lexer.CTokenNumber;
import lexer.CTokenString;
import lexer.CTokenType;
import lexer.CTokenUnrecognized;

import utils.CGlobalEntityCounter;
import utils.COriginalLisiting;
import utils.NacaTransAssertException;


/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public abstract class CBaseLexer
{
	protected char m_cCurrent = 0 ;
	protected int m_nCurrentPositionInLine = 0;
	protected int m_nCurrentLineLength = 0;
	protected char[] m_arrCurrentLine = null ;
	private int m_nbCharsIgnoredAtBegining = 0 ;
	private int m_nbCharsUtils = 80 ;
	//protected boolean m_bHandleLabel = true ;
	protected COriginalLisiting m_prgmListing = null ;
	private CKeywordList m_lstKW ;
	private CConstantList m_lstCste ;

	public CBaseLexer(int ignored, int utils, CKeywordList lstKW, CConstantList lstCste)
	{
		m_nbCharsIgnoredAtBegining = ignored ;
		m_nbCharsUtils = utils ;
		m_lstKW = lstKW ;
		m_lstCste = lstCste ;
	}
	
	protected boolean ReadLineEnd(InputStream buffer)
	{
		int nReadChar = m_nCurrentLineLength + m_nbCharsIgnoredAtBegining +1; // +1 counts the \n character
		int nLineChar = m_nCurrentLineLength ;
		m_arrCurrentLine[nLineChar] = '\n' ;
		nLineChar ++ ;
		try
		{
			char b = 0 ;
			int nReadNextLine = 0 ; 
			int nbStringMarks = 0 ;
			char[] nextline = new char[m_nbCharsUtils+m_nbCharsIgnoredAtBegining] ;
			Arrays.fill(nextline, '\0');
			while (b != '\n' && buffer.available()>0)
			{
				b = (char)buffer.read() ;
				nextline[nReadNextLine] = b ;
				nReadNextLine ++ ;
				if (b == '\'')
				{
					nbStringMarks ++ ;
				}
//				nReadChar ++ ;
//				if (nReadChar <= m_nbCharsUtils+m_nbCharsIgnoredAtBegining
//						&& nReadChar > m_nbCharsIgnoredAtBegining)
//				{
//					m_arrCurrentLine[nLineChar] = b ;
//					nLineChar ++ ;
//				}
			}
			if (nbStringMarks == 0)
			{ // the next line contains string data, but the string is not ending on this next line, but on the line after
				throw new NacaTransAssertException("String lexing case not implemented") ;
			}
			else if (nbStringMarks == 1)
			{ // the string is ended on the next line, and the \n character is part of the string.
				for (int i=0; i<nReadNextLine ; i++)
				{
					char c = nextline[i] ;
					nReadChar ++;
					if (nReadChar <= m_nbCharsUtils+m_nbCharsIgnoredAtBegining
						&& nReadChar > m_nbCharsIgnoredAtBegining)
					{
						m_arrCurrentLine[nLineChar] = c ;
						nLineChar ++ ;
					}
				}
			}
			else  if (nbStringMarks % 2 == 0)
			{ // the string is ended at the end of this line, the next line is a whole line, the \n char marks the en of the line, 
				// but the string is not finnished yet, the next line contains the end of the string
				char mark = nextline[m_nbCharsIgnoredAtBegining] ;
				if (mark == '-')
				{
					String cs = new String(nextline) ;
					m_prgmListing.RegisterNewOriginalLine(cs.trim()) ;
					m_line ++ ;
					int n1 = cs.indexOf('\'')  ;
//					int n2 = cs.lastIndexOf('\'') ;
					int n2 = cs.lastIndexOf('\n')-1 ;
					char[] newline = new char[m_nbCharsUtils + (n2 - n1)] ;
					for (int i=0; i<m_nCurrentLineLength; i++)
					{
						char c = m_arrCurrentLine[i] ;
						newline[i] = c ;
					}
					for (int i=0; i<m_nbCharsUtils-m_nCurrentLineLength; i++)
					{
						newline[m_nCurrentLineLength+i] = ' ' ;
					}
					for (int i=0; i<n2 - n1; i++)
					{
						char c = nextline[n1 + 1 + i] ;
						newline[m_nbCharsUtils + i] = c ;
					}
					m_nCurrentLineLength = m_nbCharsUtils + (n2 - n1) ;
					m_arrCurrentLine = newline ;
					return true ;
				}
				else
				{ // other cases not handleled
					throw new NacaTransAssertException("String lexing case not implemented") ;
				}
			}
			else
			{ // other cases not handleled
				throw new NacaTransAssertException("String lexing case not implemented") ;
			}
			
			if (nReadChar>m_nbCharsIgnoredAtBegining+1)
			{
				if (m_arrCurrentLine[nLineChar-1] == '\n')
				{
					if (m_arrCurrentLine[nLineChar-2] == '\r')
					{
						m_nCurrentLineLength = nLineChar -2; // don't count \r\n
					}
					else
					{
						m_nCurrentLineLength = nLineChar -1 ; 
					}
				}
				else
				{
					m_nCurrentLineLength = nLineChar ; 
				}
				String csCurrentLine = new String(m_arrCurrentLine, 0, m_nCurrentLineLength);
				//csCurrentLine = csCurrentLine.trim();
				m_prgmListing.ReplaceCurrentOriginalLine(csCurrentLine);
				if (m_nCurrentLineLength<0)
				{
					m_nCurrentLineLength =0 ;
				}
				return true ;
			}
			else
			{
				return false ;
			}
		}
		catch (IOException e)
		{
			return false ;
		}
		catch (IndexOutOfBoundsException e)
		{
			return false ;
		}
	}
	
	protected boolean ReadLine(InputStream buffer)
	{
		int nReadChar = 0 ;
		try
		{
			m_arrCurrentLine = new char[m_nbCharsUtils] ;
			char b = 0 ;
			int nLineChar = 0;
			char cc = '\0' ;
			while (b != '\n' && buffer.available()>0)
			{
				b = (char)buffer.read() ;
				nReadChar ++ ;
				cc = b ;
				if (nReadChar <= m_nbCharsUtils+m_nbCharsIgnoredAtBegining
					&& nReadChar > m_nbCharsIgnoredAtBegining)
				{
					m_arrCurrentLine[nLineChar] = b ;
					nLineChar ++ ;
				}
			}
			if (nReadChar>m_nbCharsIgnoredAtBegining+1)
			{
				if (m_arrCurrentLine[nLineChar-1] == '\n')
				{
					if (m_arrCurrentLine[nLineChar-2] == '\r')
					{
						m_nCurrentLineLength = nLineChar -2; // don't count \r\n
					}
					else
					{
						m_nCurrentLineLength = nLineChar -1 ; 
					}
				}
				else
				{
					m_nCurrentLineLength = nLineChar ; 
				}
				String csCurrentLine = new String(m_arrCurrentLine, 0, m_nCurrentLineLength);
				//csCurrentLine = csCurrentLine.trim();
				m_prgmListing.RegisterNewOriginalLine(csCurrentLine);
				if (m_nCurrentLineLength<0)
				{
					m_nCurrentLineLength =0 ;
				}
				m_line ++ ;
				m_nCurrentPositionInLine = 0 ; 
				return true ;
			}
			else
			{
				if (buffer.available() == 0)
				{
					return false ;
				}
				m_prgmListing.RegisterNewOriginalLine("");
				m_line ++ ;
				return ReadLine(buffer) ;
			}
		}
		catch (IOException e)
		{
			return false ;
		}
		catch (IndexOutOfBoundsException e)
		{
			return false ;
		}
	}
	
	public boolean StartLexer(String input, COriginalLisiting prgmCatalog)
	{
		if (input == null || input.equals(""))
		{
			return false ;
		}
		m_arrCurrentLine = input.toCharArray() ;
		m_nCurrentLineLength = input.length() ;
		try 
		{
			DoLine(null) ;
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage()) ;
			System.out.println(e.getStackTrace()) ;
			return false ;
		}
		return true ;
	}
	
	public boolean StartLexer(InputStream buffer, COriginalLisiting prgmCatalog)
	{
		m_prgmListing = prgmCatalog ;
		if (buffer == null)
		{
			return false ;
		}
		try 
		{
			while (ReadLine(buffer))
			{
				DoLine(buffer) ;
			}
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage()) ;
			System.out.println(e.getStackTrace()) ;
		}
		if (isIgnoreOriginalListing())
		{
			m_prgmListing.Clear() ;
		}
		//DoCount(nbLines, nbLinesComment, nbLinesCode);
		return true ;
	}
	private boolean ignoreOriginalListing = false ;
	
	private void DoLine(InputStream buffer)
	{
		//CBaseTranscoder.ms_logger.info("Lexing line "+getLine()) ;
		boolean bIsNewLine = true ; // this flag is true if lexer is at the begining of a line
		while (m_nCurrentPositionInLine < m_nCurrentLineLength)
		{
			m_cCurrent = m_arrCurrentLine[m_nCurrentPositionInLine] ;
			CBaseToken tok = null ;
			if (IsCommentMarker(m_cCurrent, bIsNewLine))
			{
				tok = ReadComment(buffer);
			}
			else
			{
				tok = handleSpecialCharacter(m_cCurrent) ;
				if (tok == null)
				{
					switch (m_cCurrent)
					{
						case '0': case '1': case '2': case '3': case '4': 
						case '5': case '6': case '7': case '8': case '9':
							tok = ReadNumber() ;
							tok.m_bIsNewLine = bIsNewLine ;
							break;
						case '.':
							if (m_nCurrentPositionInLine<m_nCurrentLineLength-1)
							{
								if (m_arrCurrentLine[m_nCurrentPositionInLine+1] >= '0' && m_arrCurrentLine[m_nCurrentPositionInLine+1] <= '9')
								{ // dotted-decimal number
									tok = ReadNumber();
									break ;
								}
							}
							tok = new CTokenGeneric(CTokenType.DOT, getLine(), bIsNewLine);
							m_nCurrentPositionInLine ++ ;
							break;
						case ',':
							tok = new CTokenGeneric(CTokenType.COMMA, getLine(), bIsNewLine);
							m_nCurrentPositionInLine ++ ;
							break;
						case ';':
							tok = new CTokenGeneric(CTokenType.SEMI_COLON, getLine(), bIsNewLine);
							m_nCurrentPositionInLine ++ ;
							break;
						case ':': 
							tok = new CTokenGeneric(CTokenType.COLON, getLine(), bIsNewLine);
							m_nCurrentPositionInLine ++ ;
							break;
						case '!': 
							tok = new CTokenGeneric(CTokenType.EXCLAMATION, getLine(), bIsNewLine);
							m_nCurrentPositionInLine ++ ;
							break;
						case '[': 
							tok = new CTokenGeneric(CTokenType.LEFT_SQUARE_BRACKET, getLine(), bIsNewLine);
							m_nCurrentPositionInLine ++ ;
							break;
						case ']': 
							tok = new CTokenGeneric(CTokenType.RIGHT_SQUARE_BRACKET, getLine(), bIsNewLine);
							m_nCurrentPositionInLine ++ ;
							break;
						case '(': 
							tok = new CTokenGeneric(CTokenType.LEFT_BRACKET, getLine(), bIsNewLine);
							m_nCurrentPositionInLine ++ ;
							break;
						case '^': 
							tok = new CTokenGeneric(CTokenType.CIRCUMFLEX, getLine(), bIsNewLine);
							m_nCurrentPositionInLine ++ ;
							break;
						case ')': 
							tok = new CTokenGeneric(CTokenType.RIGHT_BRACKET, getLine(), bIsNewLine);
							m_nCurrentPositionInLine ++ ;
							break;
						case '=': 
							tok = new CTokenGeneric(CTokenType.EQUALS, getLine(), bIsNewLine);
							m_nCurrentPositionInLine ++ ;
							break;
						case '-': 
							if (m_nCurrentPositionInLine > 0)
							{ // ignore '-' at the begining
								tok = new CTokenGeneric(CTokenType.MINUS, getLine(), bIsNewLine);
							}
							else
							{
								tok = new CTokenGeneric(CTokenType.WHITESPACE, getLine(), bIsNewLine);
							}
							m_nCurrentPositionInLine ++ ;
							break;
						case '+': 
							tok = new CTokenGeneric(CTokenType.PLUS, getLine(), bIsNewLine);
							m_nCurrentPositionInLine ++ ;
							break;
						case '*':
							tok = new CTokenGeneric(CTokenType.STAR, getLine(), bIsNewLine);
							m_nCurrentPositionInLine ++ ;
							break;
						case '/':
							tok = new CTokenGeneric(CTokenType.SLASH, getLine(), bIsNewLine);
							m_nCurrentPositionInLine ++ ;
							break;
						case ' ': 
						case '\t': 
						case '\r':
						case '\n': 
							tok = ReadWhiteSpace(buffer) ;
							break;
						case '"':
						case '\'':
							tok = ReadString(buffer);
							break;
						case '>':
							tok = ReadGreaterThan() ;
							break;
						case '<':
							tok = ReadLessThan() ;
							break;
						case 0:
							m_nCurrentPositionInLine = m_nCurrentLineLength; // end of line
							continue;
						default:
							if (m_cCurrent >= 'a' && m_cCurrent <= 'z')
								m_cCurrent = Character.toUpperCase(m_cCurrent);
							if (m_cCurrent >= 'A' && m_cCurrent <= 'Z')
							{
								int pos = m_nCurrentPositionInLine ;
								String word = ReadWord();
								CReservedKeyword kw = m_lstKW.GetKeyword(word);
								if (kw != null)
								{
									tok = new CTokenKeyword(kw, getLine(), bIsNewLine) ;
								}
								else 
								{
									CReservedConstant cste = m_lstCste.GetConstant(word) ;
									if (cste != null)
									{
										tok = new CTokenConstant(cste, getLine(), bIsNewLine) ;
									}
									else
									{
										if (pos <= 1)
										{
											m_lstTokens.Add(new CTokenGeneric(CTokenType.END_OF_BLOCK, getLine(), false));
										}
										tok = new CTokenIdentifier(word, getLine(), bIsNewLine) ;
									}
								}
							}
					}
				}
			}
			if (tok != null)
			{
				if (!tok.IsWhiteSpace())
				{
					if (tok.m_bIsNewLine)
					{
						m_nbLines ++ ;
						if (tok.GetType() == CTokenType.COMMENTS)
						{
							m_nbCommentLines ++ ;
						}
						else
						{
							m_nbCodeLines ++ ;
						}
					} 
					m_lstTokens.Add(tok);
					bIsNewLine = false ;
				}
				if (tok.GetType() == CTokenType.NEWLINE)
				{
					bIsNewLine = true ;
				}
			}
			else
			{
				tok = new CTokenUnrecognized(m_cCurrent, getLine(), bIsNewLine) ;
				m_lstTokens.Add(tok);
				m_nCurrentPositionInLine ++ ;
			}
		}
	}
	
	/**
	 * @param current
	 * @return
	 */
	protected abstract CBaseToken handleSpecialCharacter(char current) ;

	protected abstract boolean IsCommentMarker(char current, boolean isNewLine) ;

	protected int m_nbLines = 0 ;
	protected int m_nbCommentLines = 0 ;
	protected int m_nbCodeLines = 0;
	public void DoCount()
	{
		CGlobalEntityCounter ec = CGlobalEntityCounter.GetInstance();
		ec.CountLines(m_nbLines, m_nbCommentLines, m_nbCodeLines);
	}
	
	public String Export()
	{
		return m_lstTokens.toString() ;
	}
	
	protected CBaseToken ReadHexaString()
	{
		Vector<Character> arr = new Vector<Character>() ;
		while (m_nCurrentPositionInLine < m_nCurrentLineLength && m_arrCurrentLine[m_nCurrentPositionInLine] != '\'')
		{
			String digit = "0x" ;
			m_cCurrent = m_arrCurrentLine[m_nCurrentPositionInLine] ;
			if ((m_cCurrent >= '0' && m_cCurrent <= '9') || (m_cCurrent >= 'A' && m_cCurrent <= 'F'))
			{
				digit += m_cCurrent;
			}
			else if (m_cCurrent != '\'')
			{
				// unexpected character
				return null ;
			}
			m_nCurrentPositionInLine ++ ;
			m_cCurrent = m_arrCurrentLine[m_nCurrentPositionInLine] ;
			if ((m_cCurrent >= '0' && m_cCurrent <= '9') || (m_cCurrent >= 'A' && m_cCurrent <= 'F'))
			{
				digit += m_cCurrent;
			}
			else if (m_cCurrent != '\'')
			{
				// unexpected character
				return null ;
			}
			m_nCurrentPositionInLine ++ ;
			int nVal = Integer.decode(digit).intValue() ;
			char cVal = (char)nVal ;
			cVal = AsciiEbcdicConverter.getAsciiChar(cVal);			
			/*if(nVal < 0 || nVal > 255)
			{
				System.out.println("nValEbcdic to convert in ascii : Wrong ebcdic value="+nVal);
			}
			int nAscii = AsciiEbcdicConverter.getAsAscii(nVal);
			if(nAscii < 0 || nAscii > 255)
			{
				System.out.println("nAscii converted from ebcdic: Wrong ascii value="+nAscii + " from ebcdic value="+nVal);
			}
			char cVal = (char)nAscii;*/
			Character b = new Character(cVal);
			arr.add(b);
		}
		m_nCurrentPositionInLine ++ ;
		char[] res = new char[arr.size()];
		for (int i=0; i<arr.size(); i++)
		{
			Character c = arr.get(i) ;
			res[i] = c.charValue() ;
		}
		return new CTokenString(res, getLine(), false);
	}
	
	protected CBaseToken ReadGreaterThan()
	{
		try
		{
			m_nCurrentPositionInLine ++ ;
			m_cCurrent = m_arrCurrentLine[m_nCurrentPositionInLine] ;
			if (m_cCurrent == '=')
			{
				m_nCurrentPositionInLine ++ ;
				return new CTokenGeneric(CTokenType.GREATER_OR_EQUALS, getLine(), false) ; 
			}
			else
			{
				return new CTokenGeneric(CTokenType.GREATER_THAN, getLine(), false) ; 
			}
		}
		catch (Exception e)
		{
			return null ;
		}
	}

	protected CBaseToken ReadComment(InputStream buffer)
	{
		String val = new String() ;
		try 
		{
			m_nCurrentPositionInLine ++ ;
			while (m_nCurrentPositionInLine < m_nCurrentLineLength)
			{
				val += m_arrCurrentLine[m_nCurrentPositionInLine] ;
				m_nCurrentPositionInLine ++ ;
			}
		}
		catch (Exception e)
		{
		}
		CBaseToken tok = new CTokenComment(val, getLine(), true);
		return tok ;
	}
	
	protected CBaseToken ReadLessThan()
	{
		try
		{
			m_nCurrentPositionInLine ++ ;
			m_cCurrent = m_arrCurrentLine[m_nCurrentPositionInLine] ;
			if (m_cCurrent == '=')
			{
				m_nCurrentPositionInLine ++ ;
				return new CTokenGeneric(CTokenType.LESS_OR_EQUALS, getLine(), false) ; 
			}
			else
			{
				return new CTokenGeneric(CTokenType.LESS_THAN, getLine(), false) ; 
			}
		}
		catch (Exception e)
		{
			return null ;
		}
	}
	
	protected CBaseToken ReadNumber()
	{
		String val = new String() ;
		val += m_cCurrent ;
		boolean bDoted = false ;
		try 
		{
			m_nCurrentPositionInLine ++ ;
			while (m_nCurrentPositionInLine < m_nCurrentLineLength)
			{
				m_cCurrent = m_arrCurrentLine[m_nCurrentPositionInLine] ;
				if (m_cCurrent >= '0' && m_cCurrent <= '9')
				{
					val += m_cCurrent ;
				}
				else if (m_cCurrent == '.' && !bDoted && m_arrCurrentLine[m_nCurrentPositionInLine+1]>='0' && m_arrCurrentLine[m_nCurrentPositionInLine+1]<='9')
				{
					if (val.equals(""))
					{
						val = "0" ;
					}
					val += m_cCurrent ;
					bDoted = true ;
				}
				else if ((m_cCurrent >= 'a' && m_cCurrent <= 'z') || (m_cCurrent >= 'A' && m_cCurrent <= 'Z') || m_cCurrent == '-')
				{
					String cs = ReadWord() ;
					val = val + cs ; 
//					if (bDoted)
//					{
//						String todo = null;
//						todo.charAt(0) ; // to do : string is like 'a.b' 
//					}
					CBaseToken tok = new CTokenIdentifier(val, getLine(), false);
					return tok ;
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
		}

		CBaseToken tok = new CTokenNumber(val, getLine(), false);
		return tok ;
	}
	
	protected CBaseToken ReadString(InputStream buffer)
	{
		Vector<Character> val = new Vector<Character>() ;
		char delimit = m_cCurrent ;
		m_nCurrentPositionInLine ++ ; // '\''
		boolean bDone = false ;
		while (!bDone)
		{
			while (!bDone && m_nCurrentPositionInLine <= m_nCurrentLineLength)
			{
				if (!bDone && m_nCurrentPositionInLine == m_nCurrentLineLength && m_nCurrentLineLength < m_nbCharsUtils)
				{	// current line has \n in a string, and is to be finished on the next line
					if (ReadLineEnd(buffer))
						continue ;
					else
						return null ;
				}
				else if (m_nCurrentPositionInLine == m_nCurrentLineLength)
				{
					break ;
				}
				m_cCurrent = m_arrCurrentLine[m_nCurrentPositionInLine];
				Character b = new Character(m_cCurrent) ;
//				if (m_cCurrent == '*' && m_nCurrentPositionInLine == m_nCurrentLineLength-1)
//				{	// the string continues on next line.
//					if (!ReadLine(buffer))
//					{
//						return null ;
//					}
//					continue ;  
//				}
				if (m_cCurrent != delimit && m_cCurrent != '\n' && m_cCurrent != '\r')
				{
					val.add(b) ;
				}
				else if (m_cCurrent == delimit && m_nCurrentPositionInLine==m_nCurrentLineLength-1)
				{
					bDone = true ;
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
				else if (m_cCurrent == '\n' || m_cCurrent == '\r')
				{
					val.add(b) ;
				}
				else
				{
//					m_nCurrentPositionInLine ++ ;
//					break ;
				}
				m_nCurrentPositionInLine ++ ;
			}
			if (!bDone)
			{
				if (!ReadLine(buffer))
				{
					return null ;
				}
				m_cCurrent = m_arrCurrentLine[m_nCurrentPositionInLine];
				if (m_cCurrent != '-')
				{
					bDone = true ; 
				}
				else
				{
					m_nCurrentPositionInLine ++ ;
					m_cCurrent = m_arrCurrentLine[m_nCurrentPositionInLine];
					// first read until next '
					while (m_cCurrent != delimit && m_nCurrentPositionInLine<m_nCurrentLineLength)
					{
						m_nCurrentPositionInLine ++ ;
						m_cCurrent = m_arrCurrentLine[m_nCurrentPositionInLine];
					}
					if (m_cCurrent == delimit)
					{
						m_nCurrentPositionInLine ++ ;
					}
					else
					{
						bDone = true ;
					}
				}
			}
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
	
	protected CBaseToken ReadWhiteSpace(InputStream buffer)
	{
		boolean bIsNewline = false ;
		boolean bFound = false ;
		int nbNewLine = 0; 
		while (!bFound)
		{
			if (m_cCurrent == '\n' || m_cCurrent == '\r')
			{
				bIsNewline = true ;
			}
			else if (m_cCurrent == ' ' || m_cCurrent == '\t')
			{
				 //nothing
			}
			else
			{
				bFound = true ;
			}

			if (!bFound)
			{
				m_nCurrentPositionInLine ++ ;
				if (m_nCurrentPositionInLine == m_nCurrentLineLength || bIsNewline)
				{
					if (!ReadLine(buffer))
					{
						bFound = true ;
					}
					else
					{
						bIsNewline = false ;
						nbNewLine ++ ;
						m_nCurrentPositionInLine = 0 ;
						m_cCurrent = m_arrCurrentLine[m_nCurrentPositionInLine] ;
					}
				}
				else
				{				
					m_cCurrent = m_arrCurrentLine[m_nCurrentPositionInLine] ;
				}
			}
		}

		if (nbNewLine == 0)
		{
			CBaseToken tok = new CTokenGeneric(CTokenType.WHITESPACE, getLine(), false);
			return tok ;
		}
		else
		{
			CBaseToken tok = new CTokenGeneric(CTokenType.NEWLINE, getLine(), true);
			//m_line += nbNewLine ;
			return tok ;
		}
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
				else if (m_cCurrent == '-' || m_cCurrent == '_' || m_cCurrent == '#')  // maybe other characters are allowed
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
		}
		return val ;
	}
	
	public CTokenList GetTokenList()
	{
		return m_lstTokens ;
	}
	protected CTokenList m_lstTokens = new CTokenList() ;
	private int m_line = 0 ;
	
	protected int getLine()
	{
		return m_line;
	}

	/**
	 * @return Returns the ignoreOriginalListing.
	 */
	public boolean isIgnoreOriginalListing()
	{
		return ignoreOriginalListing;
	}

	/**
	 * @param ignoreOriginalListing The ignoreOriginalListing to set.
	 */
	public void setIgnoreOriginalListing(boolean ignoreOriginalListing)
	{
		this.ignoreOriginalListing = ignoreOriginalListing;
	}

}
