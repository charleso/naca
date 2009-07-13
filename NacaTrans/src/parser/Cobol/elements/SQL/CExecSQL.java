/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 4 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements.SQL;

import java.util.Vector;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.Cobol.CCobolElement;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.Verbs.CEntityExec;
import semantic.expression.CEntityConcat;
import semantic.expression.CEntityDigits;
import utils.CGlobalEntityCounter;
import utils.NacaTransAssertException;
import utils.Transcoder;

import com.sun.org.apache.xml.internal.utils.StringVector;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecSQL extends CCobolElement
{

	/**
	 * @param line
	 */
	public CExecSQL(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tokSQL = GetCurrentToken() ;
		if (tokSQL.GetKeyword() != CCobolKeywordList.SQL)
		{
			Transcoder.logError(getLine(), "Expecting 'SQL' keyword");
			return false ;
		}
		
		CBaseToken tokAction = GetNext() ;
		if (tokAction.GetKeyword() == CCobolKeywordList.INCLUDE)
		{ // EXEC SQL INCLUDE ref END-EXC
			CBaseToken tokRef = GetNext() ;
			if (tokRef.GetType() != CTokenType.IDENTIFIER)
			{
				Transcoder.logError(getLine(), "Expecting IDENTIFIER as include reference, instead of " + tokRef.GetValue()) ;
				return false ;
			}
			CExecSQLInclude include = new CExecSQLInclude(getLine(), tokRef.GetValue());
			Parse(include) ; 
			m_action = include ;
			CBaseToken tokNext = GetCurrentToken() ;
			if (tokNext.GetKeyword() == CCobolKeywordList.END_EXEC)
			{
				tokNext = GetNext() ;
				if (tokNext.GetType() == CTokenType.DOT)
				{
					tokNext = GetNext() ;
				}
				if (tokNext.GetType() == CTokenType.NUMBER && Integer.parseInt(tokNext.GetValue())>1)
				{
					return include.ParseContent() ;
				}
				return true ;
			}
		}
		else if (tokAction.GetKeyword() == CCobolKeywordList.DECLARE)
		{ 
			boolean bInto = false;
			String csLastIndentifier = "";

			CBaseToken tokNext = GetNext();
			if (tokNext.GetValue().equals("GLOBAL"))
			{
				CExecSQLSessionDeclare ExecSQLSessionDeclare = new CExecSQLSessionDeclare(getLine());
				Parse(ExecSQLSessionDeclare);
				m_action = ExecSQLSessionDeclare;
			}
			else
			{	
				while (tokNext.GetKeyword() != CCobolKeywordList.END_EXEC)
				{
					CBaseToken tok = GetCurrentToken() ;
				
					if(tok.GetType() == CTokenType.IDENTIFIER || tok.GetType() == CTokenType.STRING)
					{
						csLastIndentifier = tok.GetValue();
					}
					tok = GetNext() ;
					if(tok.GetKeyword() == CCobolKeywordList.TABLE)
					{
						CGlobalEntityCounter.GetInstance().CountSQLCommand("DECLARE_TABLE");
						tokNext = GetNext();
						if(tokNext.GetType() == CTokenType.LEFT_BRACKET)
						{
							tokNext = GetNext();	// Skip the (
							CExecSQLDeclareTable ExecSQLDeclareTable = new CExecSQLDeclareTable(tok.getLine());
							ExecSQLDeclareTable.SetTableName(csLastIndentifier);
							Parse(ExecSQLDeclareTable);
							m_action = ExecSQLDeclareTable;
						}
					}
					if(tok.GetKeyword() == CCobolKeywordList.CURSOR)
					{
						CGlobalEntityCounter.GetInstance().CountSQLCommand("DECLARE_CURSOR");
						boolean bWithHold = false ;
						tokNext = GetNext();
						if(tokNext.GetKeyword() == CCobolKeywordList.WITH)
						{
							tokNext = GetNext();
							if(tokNext.GetKeyword() == CCobolKeywordList.HOLD)
							{
								tokNext = GetNext() ;
								bWithHold = true ;
							}					
						}
						if(tokNext.GetKeyword() == CCobolKeywordList.FOR)
						{
							tokNext = GetNext();	// Skip the FOR
							// Cursor for select
							if (tokNext.GetKeyword() == CCobolKeywordList.SELECT)
							{
								CExecSQLSelect ExecSQLSelect = new CExecSQLSelect(tok.getLine());
								ExecSQLSelect.SetCursorName(csLastIndentifier, bWithHold);
								Parse(ExecSQLSelect);
								m_action = ExecSQLSelect;
							}
							else if (tokNext.GetType() == CTokenType.IDENTIFIER)
							{
								CExecSQLVariableCursor cursor = new CExecSQLVariableCursor(tok.getLine(), csLastIndentifier);
								Parse(cursor) ;
								m_action = cursor  ;
							}
						}
					}
					tokNext = GetCurrentToken() ;
				}
			}
			tokNext = GetNext() ;
			if (tokNext == null)
			{
				return true ;
			}
			if (tokNext.GetType() == CTokenType.DOT)
			{
				StepNext();
			}
			return true ;
			/*
			// EXEC SQL DECLARE TABLE		--> this statement is ignored
			CBaseToken tokNext = GetNext();
			while (tokNext.GetKeyword() != CCobolKeywordList.END_EXEC)
			{
				tokNext = GetNext() ;
			}
			*/
		}
		else if (tokAction.GetKeyword() == CCobolKeywordList.OPEN)
		{
			CExecSQLOpen ExecSQLOpen = new CExecSQLOpen(getLine());
			Parse(ExecSQLOpen);
			m_action = ExecSQLOpen;
		}
		else if (tokAction.GetValue().equals("FETCH"))
		{
			CExecSQLFetch ExecSQLFetch = new CExecSQLFetch(getLine());
			Parse(ExecSQLFetch);
			m_action = ExecSQLFetch;
		}
		else if (tokAction.GetKeyword() == CCobolKeywordList.CLOSE)
		{
			CExecSQLClose ExecSQLClose = new CExecSQLClose(getLine());
			Parse(ExecSQLClose);
			m_action = ExecSQLClose;
		}		
		else if (tokAction.GetKeyword() == CCobolKeywordList.CALL)
		{
			CExecSQLCall ExecSQLCall = new CExecSQLCall(getLine());
			Parse(ExecSQLCall);
			m_action = ExecSQLCall;
		}		
		else if (tokAction.GetValue().equals("ROLLBACK"))
		{
			CExecSQLRollBack ExecSQLRollback = new CExecSQLRollBack(getLine());
			Parse(ExecSQLRollback);
			m_action = ExecSQLRollback;
		}		
		else if (tokAction.GetKeyword() == CCobolKeywordList.PREPARE)
		{
			CExecSQLPrepare ExecSQLPrepare = new CExecSQLPrepare(getLine());
			Parse(ExecSQLPrepare);
			m_action = ExecSQLPrepare;
		}		
		else if (tokAction.GetKeyword() == CCobolKeywordList.COMMIT)
		{
			CExecSQLCommit ExecSQLCommit = new CExecSQLCommit(getLine());
			Parse(ExecSQLCommit);
			m_action = ExecSQLCommit;
		}		
		else if (tokAction.GetKeyword() == CCobolKeywordList.EXECUTE)
		{
			CExecSQLExecute ExecSQLExecute = new CExecSQLExecute(getLine());
			Parse(ExecSQLExecute);
			m_action = ExecSQLExecute ;
		}		
		else if (tokAction.GetKeyword() == CCobolKeywordList.LOCK)
		{
			CExecSQLLock ExecSQLLock = new CExecSQLLock(getLine());
			Parse(ExecSQLLock);
			m_action = ExecSQLLock;
		}
		else if (tokAction.GetKeyword() == CCobolKeywordList.DROP)
		{
			CExecSQLSessionDrop ExecSQLSessionDrop = new CExecSQLSessionDrop(getLine());
			Parse(ExecSQLSessionDrop);
			m_action = ExecSQLSessionDrop;
		}
		else if (tokAction.GetKeyword() == CCobolKeywordList.WHENEVER)
		{ // EXEC SQL WHENEVER SQLERROR GO TO identifier END-EXEC
			CBaseToken tokNext = GetNext();
			boolean m_bOnWarning = false ;
			if (tokNext.GetKeyword() == CCobolKeywordList.SQLERROR)
			{
				m_bOnWarning = false ;
			}
			else if (tokNext.GetKeyword() == CCobolKeywordList.SQLWARNING)
			{
				m_bOnWarning = true ;
			}
			else
			{
				Transcoder.logError(getLine(), "Expecting 'SQLERROR' keyword");
				return false ;
			}
			tokNext = GetNext() ;
			if (tokNext.GetKeyword() == CCobolKeywordList.CONTINUE)
			{
				tokNext = GetNext() ;
				if (m_bOnWarning)
				{
					m_action = new CExecSQLOnWarningGoto(getLine(), "") ;
				}
				else
				{
					m_action = new CExecSQLOnErrorGoto(getLine(), "") ;
				}
			}
			else 
			{
				if (tokNext.GetKeyword() != CCobolKeywordList.GOTO)
				{
					if (tokNext.GetKeyword() != CCobolKeywordList.GO)
					{
						Transcoder.logError(getLine(), "Expecting 'GOTO' keyword");
						return false ;
					} 
					tokNext = GetNext() ;
					if (tokNext.GetKeyword() != CCobolKeywordList.TO)
					{
						Transcoder.logError(getLine(), "Expecting 'GOTO' keyword");
						return false ;
					}
				}
				tokNext = GetNext() ;
				if (tokNext.GetType() != CTokenType.IDENTIFIER)
				{
					Transcoder.logError(getLine(), "Expecting IDENTIFIER as GOTO reference, instead of " + tokNext.GetValue()) ;
					return false ;
				}
				String ref = tokNext.GetValue() ;
				if (!m_bOnWarning)
				{
					m_action = new CExecSQLOnErrorGoto(getLine(), ref) ;
				}
				else
				{
					m_action = new CExecSQLOnWarningGoto(getLine(), ref) ;
				}
				tokNext = GetNext() ;
			}
			if (tokNext.GetKeyword() != CCobolKeywordList.END_EXEC)
			{
				Transcoder.logError(getLine(), "Expecting END-EXEC instead of " + tokNext.GetValue()) ;
				return false ;
			}
			CGlobalEntityCounter.GetInstance().CountSQLCommand(tokAction.GetValue());
			tokNext = GetNext() ;
			if (tokNext == null)
			{
				return true ;
			}
			if (tokNext.GetType() == CTokenType.DOT)
			{
				StepNext();
			}
			return true ;
		}
		else if (tokAction.GetKeyword() == CCobolKeywordList.SELECT)
		{
			CExecSQLSelect selectaction = new CExecSQLSelect(getLine()) ;
			m_action = selectaction ;
			Parse(selectaction) ;
		}
		else if(tokAction.GetKeyword() == CCobolKeywordList.DELETE)
		{
			CExecSQLDelete selectaction = new CExecSQLDelete(getLine()) ;
			m_action = selectaction ;
			Parse(selectaction) ;
		}
		else if(tokAction.GetKeyword() == CCobolKeywordList.UPDATE)
		{
			CExecSQLUpdate selectaction = new CExecSQLUpdate(getLine()) ;
			m_action = selectaction ;
			Parse(selectaction) ;
		}
		
		else if (tokAction.GetKeyword() == CCobolKeywordList.INSERT)
		{
			CExecSQLInsert selectaction = new CExecSQLInsert(getLine()) ;
			m_action = selectaction ;
			Parse(selectaction) ;
		}
		else
		{
			Transcoder.logError(getLine(), "Unexpecting token in EXEC SQL statement : " + tokAction.GetValue());
			m_csUnparsedStatement = tokAction.GetValue() ;
			CBaseToken tok = GetNext() ;
			while (tok.GetKeyword() != CCobolKeywordList.END_EXEC)
			{
				m_csUnparsedStatement += " " + tok.GetValue() ;
				tok = GetNext() ;
			}
		}
		CBaseToken tokNext = GetCurrentToken() ;
//		while (tokNext.GetType() == CTokenType.COMMENT)
//		{
//			ParseComment();
//			tokNext = GetCurrentToken() ;
//		}
		if (tokNext.GetKeyword() != CCobolKeywordList.END_EXEC)
		{
			Transcoder.logError(getLine(), "Expecting END-EXEC instead of " + tokNext.GetValue()) ;
			return false ;
		}
		CGlobalEntityCounter.GetInstance().CountSQLCommand(tokAction.GetValue());
		tokNext = GetNext() ;
		if (tokNext == null)
		{
			return true ;
		}
//		if (tokNext.GetType() == CTokenType.DOT)
//		{
//			StepNext();
//		}
	 
		return true;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#DoCustomSemanticAnalysis(semantic.CBaseSemanticEntity, semantic.CBaseSemanticEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		if (m_action != null)
		{
			return m_action.DoSemanticAnalysis(parent, factory) ;
		}
		else if (!m_csUnparsedStatement.equals(""))
		{
			CEntityExec e = factory.NewEntityExec(getLine(), m_csUnparsedStatement);
			parent.AddChild(e) ;
			return e ;
		}
		else
		{
			return null ;
		}
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		if (m_action != null)
		{
			return m_action.Export(root) ;
		}
		else
		{
			Element e = root.createElement("SQL");
			e.setAttribute("Statement", m_csUnparsedStatement) ;
			return e ;
		}
	}
	protected CBaseExecSQLAction m_action = null ;
	protected String m_csUnparsedStatement = "" ;
	
	public static String CheckConcat(String clause, Vector<CDataEntity> arrParams, CBaseEntityFactory factory)
	{
		String out = "" ;
		clause = clause.replaceAll("]]", "!!") ;
		clause = clause.replaceAll("! !", "!!") ;
		clause = clause.replaceAll(" !! ", "!!") ;
		clause = clause.replaceAll(" CONCAT ", "!!") ;
		int nPos = clause.indexOf("!!");
		int nLastEdit = 0 ;
		boolean bOnlyParameter = false ;
		while (nPos >0)
		{
			// find first argument
			int nbPar = 0;
			int i = nPos-1 ;
			boolean bFound = false ;
			int nStep = 0;  // 0 = start , 1 = par, 2 = word
			while (!bFound && i>=0)
			{
				char c = clause.charAt(i);
				if ((Character.isLetter(c) || Character.isDigit(c) || c=='.' || c=='_') 
					&& (nStep == 0 || nStep == 2))
				{
					nStep = 2 ;
					i -- ;
				}
				else if (c=='#' && (nStep == 0 || nStep == 2)) 
				{
					nStep = 2 ;
					bOnlyParameter = true ;
					i -- ;
				}
				else if (c=='#' && nStep == 1)
				{
					i -- ;
					bOnlyParameter = true ;
				}
				else if ((Character.isLetter(c) || Character.isDigit(c) || c=='.' || c=='_' || c==',' || c==' ') 
					&& (nStep == 1))
				{
					i -- ;
				}
				else if (c == ')' && (nStep == 0 || nStep ==1))
				{
					nbPar ++ ;
					nStep = 1 ;
					i -- ;
				} 
				else if (c == '(' && nStep == 1)
				{
					nbPar -- ;
					if (nbPar == 0)
					{
						nStep = 2 ; // the parenthesis are closed, now, find the function word
					}
					i -- ;
				}
				else if (c == '\'')
				{
					i -- ;
					while (i>=0 && clause.charAt(i) != '\'')
					{
						i -- ;
					}
					if (i>0)
						i-- ;
				}
				else 
				{
					bFound = true ;
				}
			}
			
			String fst = clause.substring(i+1, nPos);
			StringVector arrId = new StringVector() ;
			arrId.addElement(fst) ;
			if (i>nLastEdit)
			{
				out += clause.substring(nLastEdit, i+1);
			}
			
			// find second argument
			boolean hasMoreArguments = true ;
			String concat = "" ;
			while (hasMoreArguments)
			{
				boolean isParameter = false ;
				nbPar = 0;
				i = nPos+2 ;
				bFound = false ;
				nStep = 0;  // 0 = start , 1 = par, 2 = word
				while (!bFound && i<clause.length())
				{
					char c = clause.charAt(i);
					if ((Character.isLetter(c) || Character.isDigit(c) || c=='.' || c=='_') 
						&& (nStep == 0 || nStep == 2))
					{
						nStep = 2 ;
						i ++ ;
					}
					else if (c=='#' && (nStep == 0 || nStep == 2)) 
					{
						nStep = 2 ;
						isParameter = true ;
						i ++ ;
					}
					else if (Character.isWhitespace(c) && (nStep == 0))
					{
						i ++ ;
						nPos ++ ;
					}
					else if (c == '\'' && nStep == 0)
					{
						i ++ ;
						c = clause.charAt(i);
						while (c != '\'')
						{
							i ++ ;
							c = clause.charAt(i);
						}
						i++ ;
						bFound = true ;
					}
					else if ((Character.isLetter(c) || Character.isDigit(c) || c=='.' || c=='_' || c==',' || c==' ' || c=='\'') 
						&& (nStep == 1))
					{
						i ++ ;
					}
					else if (c=='#' && nStep == 1)
					{
						i ++ ;
						isParameter = true ;
					}
					else if (c == '(')
					{
						nbPar ++ ;
						nStep = 1 ;
						i ++ ;
					} 
					else if (c == ')' && nStep == 1)
					{
						nbPar -- ;
						i ++ ;
						if (nbPar == 0)
						{
							bFound = true ; 
						}
					}
					else 
					{
						bFound = true ;
					}
				}
				String end = clause.substring(nPos+2, i);
				arrId.addElement(end) ;
				nLastEdit = i ;

				bOnlyParameter &= isParameter ;

				concat = "CONCAT(" + fst + ", " + end + ")" ;
				String space = "" ;
				while (nLastEdit<clause.length() && Character.isWhitespace(clause.charAt(nLastEdit)))
				{
					space += clause.charAt(nLastEdit) ;
					nLastEdit ++ ;
				}
				if (clause.startsWith("!!", nLastEdit))
				{ // more than one '!!' in single statement
					fst = concat ;
					nPos = nLastEdit ;
				}
				else
				{
					if (space.equals(""))
					{
						space = " " ;
					}
					concat += space ;
					hasMoreArguments = false ;
				}
				
			}
			if (bOnlyParameter)
			{
				//String firstId = arrId.elementAt(0).substring(1) ;
				//concat = "" + '#' + firstId + " ";
				int id = 0 ; //Integer.parseInt(firstId) ;
				CDataEntity el = null ; //(CDataEntity)arrParams.get(id-1) ;
				for (int j=0; j<arrId.size(); j++)
				{
					String nextId = arrId.elementAt(j) ;
					if (nextId.startsWith("#"))
					{
						nextId = nextId.substring(1) ;
						int nid = Integer.parseInt(nextId) ;
						CDataEntity nel = arrParams.get(nid-1) ;
						arrParams.set(nid-1, null) ;
						if (id == 0)
						{
							id = nid ;
							el = nel ;
						}
						else
						{
							CEntityConcat eConcat = factory.NewEntityConcat(el, nel) ;
							el = eConcat ;
						}
					}
					else
					{
						int p1 = nextId.indexOf('(') ;
						int p2 = nextId.indexOf(')') ;
						if (p1 > 0 && p2 > p1)
						{
							String ids = nextId.substring(p1+1, p2) ; 
							if (ids.startsWith("#"))
							{
								ids = ids.substring(1) ;
							}
							else
							{
								throw new NacaTransAssertException("Unmanaging situation with concat parameter : "+nextId) ;
							}
							String fun = nextId.substring(0, p1) ;
							int nid = Integer.parseInt(ids) ;
							CDataEntity nel = arrParams.get(nid-1) ;
							CDataEntity func = null ;
							if (fun.equalsIgnoreCase("DIGITS"))
							{
								CEntityDigits dig = factory.NewEntityDigits(nel) ;
								func = dig ;
							}
							else
							{
								throw new NacaTransAssertException("Unmanaged function : "+fun) ;
							}
							arrParams.set(nid-1, null) ;
							if (id == 0)
							{
								id = nid ;
								el = func ;
							}
							else
							{
								CEntityConcat eConcat = factory.NewEntityConcat(el, func) ;
								el = eConcat ;
							}
						}
						else
						{
							throw new NacaTransAssertException("unmanaged situation with concat parameter : "+nextId) ;
						}
					}
				}
				concat = "" + '#' + id + " " ;
				arrParams.set(id-1, el) ;
			}
			out += concat ;
			nPos = clause.indexOf("!!", nLastEdit);
		}
		if (nLastEdit < clause.length())
		{
			out += clause.substring(nLastEdit);
		}
		return out ;
	}
	
}
