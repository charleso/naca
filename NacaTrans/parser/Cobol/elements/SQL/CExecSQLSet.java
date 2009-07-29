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
/**
 * 
 */
package parser.Cobol.elements.SQL;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.Cobol.CCobolParser;
import parser.expression.CTerminal;

import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.SQL.CEntitySQLRollBack;
import semantic.SQL.CEntitySQLSet;
import semantic.Verbs.CEntityDivide;
import utils.Transcoder;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */


public class CExecSQLSet extends CBaseExecSQLAction
{
	private CTerminal m_terminal = null;
	private SQLSetDateTimeType m_sqlSetType = null;
	/**
	 * @param l
	 */
	public CExecSQLSet(int l)
	{
		super(l);
	}
	
	public Element ExportCustom(Document root)
	{
		Element e = root.createElement("SQLSet") ;
		if(m_sqlSetType == SQLSetDateTimeType.Date)
		{
			e.setAttribute("CurrentDate", "true");
			Element eInto = root.createElement("Into") ;
			e.appendChild(eInto);
			m_terminal.ExportTo(eInto, root);
		}
		else if(m_sqlSetType == SQLSetDateTimeType.Time)
		{
			e.setAttribute("CurrentTime", "true");
			Element eInto = root.createElement("Into") ;
			e.appendChild(eInto);
			m_terminal.ExportTo(eInto, root);
		}
		else if(m_sqlSetType == SQLSetDateTimeType.TimeStamp)
		{
			e.setAttribute("CurrentTimeStamp", "true");
			Element eInto = root.createElement("Into") ;
			e.appendChild(eInto);
			m_terminal.ExportTo(eInto, root);
		}
		return e ;
	}
	
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		if(m_sqlSetType != null)
		{
			CEntitySQLSet eSQLSet = factory.NewEntitySQLSet(getLine());
			CDataEntity entityVar = m_terminal.GetDataEntity(getLine(), factory);
			entityVar.RegisterWritingAction(eSQLSet);
			parent.AddChild(eSQLSet) ;
			eSQLSet.SetTerminal(entityVar);
			eSQLSet.SetSQLSetDateTimeType(m_sqlSetType);
			return eSQLSet;
		}
		return null;
	}
//	
//	
//	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
//	{
//		CEntityDivide eDivide = factory.NewEntityDivide(getLine());
//		parent.AddChild(eDivide);
//		CDataEntity eWhat = m_DivideWhat.GetDataEntity(getLine(), factory);
//		eWhat.RegisterReadingAction(eDivide) ;
//		CDataEntity eBy = m_DivideBy.GetDataEntity(getLine(), factory);
//		eBy.RegisterReadingAction(eDivide) ;
//		if (m_Result != null)
//		{
//			CDataEntity eResult = m_Result.GetDataReference(getLine(), factory) ;
//			eResult.RegisterWritingAction(eDivide) ;
//			eDivide.SetDivide(eWhat, eBy, eResult,m_bIsRounded);
//		}
//		else
//		{
//			eDivide.SetDivide(eWhat, eBy, m_bIsRounded);
//		}
//		
//		if (m_Remainder != null)
//		{
//			CDataEntity eRem = m_Remainder.GetDataReference(getLine(), factory);
//			eRem.RegisterWritingAction(eDivide) ;
//			eDivide.SetRemainder(eRem);
//		}
//		return eDivide;
//	}
	
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if(tok.getLine() == 478)
		{
			int gg= 0 ;
		}
		if (tok.GetValue().equals("SET"))
		{
			tok = GetNext();
			if (tok.GetType() == CTokenType.COLON)
			{
				tok = GetNext();
				if (tok.GetType() == CTokenType.IDENTIFIER)
				{
					m_terminal = ReadTerminal();
					tok = GetNext();
					if (tok.GetKeyword() == CCobolKeywordList.CURRENT__DATE)		// // CURRENT_DATE
					{
						m_sqlSetType = SQLSetDateTimeType.Date;
						tok = GetNext();
						if (tok.GetKeyword() == CCobolKeywordList.END_EXEC)
						{
							return true;
						}
					}
					else if (tok.GetKeyword() == CCobolKeywordList.CURRENT__TIME)	// CURRENT_TIME
					{
						m_sqlSetType = SQLSetDateTimeType.Time;
						tok = GetNext();
						if (tok.GetKeyword() == CCobolKeywordList.END_EXEC)
						{
							return true;
						}
					}
					else if (tok.GetKeyword() == CCobolKeywordList.CURRENT__TIMESTAMP) // CURRENT_TIMESTAMP
					{
						m_sqlSetType = SQLSetDateTimeType.TimeStamp;
						tok = GetNext();
						if (tok.GetKeyword() == CCobolKeywordList.END_EXEC)
						{
							return true;
						}
					}
					else if (tok.GetKeyword() == CCobolKeywordList.CURRENT)	// CURRENT DATE|TIME|TIMESTAMP
					{
						tok = GetNext();	
						if (tok.GetKeyword() == CCobolKeywordList.DATE)	// CURRENT DATE
						{
							m_sqlSetType = SQLSetDateTimeType.Date;
							tok = GetNext();
							if (tok.GetKeyword() == CCobolKeywordList.END_EXEC)
							{
								return true;
							}
						}
						else if (tok.GetKeyword() == CCobolKeywordList.TIME)	// CURRENT TIME
						{
							m_sqlSetType = SQLSetDateTimeType.Time;
							tok = GetNext();
							if (tok.GetKeyword() == CCobolKeywordList.END_EXEC)
							{
								return true;
							}
						}
						else if (tok.GetKeyword() == CCobolKeywordList.TIMESTAMP)	// CURRENT TIMESTAMP
						{
							m_sqlSetType = SQLSetDateTimeType.TimeStamp;
							tok = GetNext();
							if (tok.GetKeyword() == CCobolKeywordList.END_EXEC)
							{
								return true;
							}
						}
					}					
				}
			}
		}
		Transcoder.logError(tok.getLine(), "Could not lex token " + tok.GetDisplay());
		while(tok.GetKeyword() != CCobolKeywordList.END_EXEC)
		{
			tok = GetNext();
			Transcoder.logError(tok.getLine(), "Could not lex token " + tok.GetDisplay());
		}
		return false;
	}
}