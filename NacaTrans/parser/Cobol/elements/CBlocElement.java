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

import parser.*;
import parser.Cobol.CCobolElement;
import parser.Cobol.elements.CICS.*;
import parser.Cobol.elements.SQL.*;
import parser.expression.CIdentifierTerminal;
import parser.expression.CTerminal;
import semantic.CBaseLanguageEntity;
import semantic.CBaseEntityFactory;
import semantic.CEntityBloc;
import utils.CGlobalEntityCounter;
import utils.Transcoder;
import utils.modificationsReporter.Reporter;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CBlocElement extends CCommentContainer
{
	/**
	 * @param line
	 */
	public CBlocElement(int line) {
		super(line);
	}

	protected boolean DoParsing()
	{
		boolean bDone = false ;
		while (!bDone)
		{
			CBaseToken tokVerb = GetCurrentToken() ;
			if (tokVerb == null)
			{
				return true ;
			}
			if(tokVerb.getLine() == 381)
			{
				int gg = 0;
			}
			if (tokVerb.GetType()==CTokenType.KEYWORD)
			{
				if (!ParseVerb())
				{
					Transcoder.logError(getLine(), "Failure while parsing keyword '" + tokVerb.GetValue() + "'") ;
				}
				if (tokVerb == GetCurrentToken())
				{	// this keyword has not been parsed
					m_nEndLine = tokVerb.getLine()-1 ;
					bDone = true;
				}
			}
			else if (tokVerb.GetType() == CTokenType.SEMI_COLON)
			{
				GetNext();
			}
			else
			{
				m_nEndLine = tokVerb.getLine()-1 ;
				bDone = true ; // this token can't be parsed by this function
			}
		}
		return true;
	}
	
	
	protected CCobolElement GetCICSVerb()
	{
		CCobolElement e = null ;
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.ADDRESS)
		{
			e = new CExecCICSAddress(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.LINK)
		{
			e = new CExecCICSLink(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.XCTL)
		{
			e = new CExecCICSXctl(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.IGNORE)
		{
			e = new CExecCICSIgnore(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.RETRIEVE)
		{
			e = new CExecCICSRetrieve(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.RECEIVE)
		{
			e = new CExecCICSReceive(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.ASKTIME)
		{
			e = new CExecCICSAskTime(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.BIF)
		{
			e = new CExecCICSBif(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.READQ)
		{
			e = new CExecCICSReadQ(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.RETURN)
		{
			e = new CExecCICSReturn(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.HANDLE)
		{
			e = new CExecCICSHandle(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.ENQ)
		{
			e = new CExecCICSEnq(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.ASSIGN)
		{
			e = new CExecCICSAssign(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.SEND)
		{
			e = new CExecCICSSend(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.SYNCPOINT)
		{
			e = new CExecCICSSyncPoint(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.WRITE)
		{
			e = new CExecCICSWrite(tok.getLine());
		}
		else if (tok.GetType() == CTokenType.IDENTIFIER && tok.GetValue().equals("ABEND"))
		{
			e = new CExecCICSAbend(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.DELETEQ)
		{
			e = new CExecCICSDeleteQ(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.WRITEQ)
		{
			e = new CExecCICSWriteQ(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.DEQ)
		{
			e = new CExecCICSDeQ(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.INQUIRE)
		{
			e = new CExecCICSInquire(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.READ)
		{
			e = new CExecCICSRead(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.START)
		{
			e = new CExecCICSStart(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.SET)
		{
			e = new CExecCICSSet(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.DELAY)
		{
			e = new CExecCICSDelay(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.REWRITE)
		{
			e = new CExecCICSReWrite(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.GETMAIN)
		{
			e = new CExecCICSGetMain(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.READNEXT)
		{
			e = new CExecCICSReadNext(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.STARTBR)
		{
			e = new CExecCICSStartBR(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.ENDBR)
		{
			e = new CExecCICSEndBR(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.DELETE)
		{
			e = new CExecCICSDelete(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.UNLOCK)
		{
			e = new CExecCICSUnLock(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.READPREV)
		{
			e = new CExecCICSReadPrev(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.ALLOCATE)
		{
			e = new CExecCICSAllocate(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.CONNECT)
		{
			e = new CExecCICSConnect(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.FREE)
		{
			e = new CExecCICSFree(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.CONVERSE)
		{
			e = new CExecCICSConverse(tok.getLine());
		}
		else if (tok.GetKeyword() == CCobolKeywordList.ENABLE)
		{
			e = new CExecCICSEnable(tok.getLine());
		}
//		else if (tok.GetKeyword() == CCobolKeywordList.ADDRESS)
//		{
//		}
		else
		{
			Transcoder.logError(tok.getLine(), "Unrecognized CICS command : " + tok.GetValue());
			e = new CExecStatement(tok.getLine()) ;
		}  
		CGlobalEntityCounter.GetInstance().CountCICSCommand(tok.GetValue()) ;
		return e ;
	}
			
	protected int m_nRewriteLine = 0; 
	protected boolean ParseVerb()
	{
		CBaseToken tokVerb = GetCurrentToken() ;
		if(tokVerb.getLine() == 174)
		{
			int gg = 0;
		}
		if (m_fCheckForNextSentence.ISSet())
		{
			if (tokVerb.GetKeyword() == CCobolKeywordList.END_IF ||
				tokVerb.GetKeyword() == CCobolKeywordList.ELSE  ||
				tokVerb.GetKeyword() == CCobolKeywordList.END_PERFORM  ||
				tokVerb.GetKeyword() == CCobolKeywordList.END_EVALUATE  ||
				tokVerb.GetType() == CTokenType.DOT)
			{
				return true ;
			}
			else if (!isTopLevelBloc())
			{
				Transcoder.logWarn(tokVerb.getLine(), "Unrecommanded usage of NEXT SENTENCE, using nextSentence");
				m_nRewriteLine = tokVerb.getLine() ;
			}
		}
		if (tokVerb.GetKeyword()==CCobolKeywordList.EXEC)
		{
			CBaseToken tokType = GetNext() ;
			CCobolElement eExec = null ;
			if (tokType.GetKeyword() == CCobolKeywordList.SQL)
			{
				eExec = new CExecSQL(tokVerb.getLine()) ;
			}
			else if (tokType.GetKeyword() == CCobolKeywordList.CICS)
			{
				GetNext();
				eExec = GetCICSVerb();
				if (eExec == null)
				{
					return true ; // ignore EXEC CICS that are not parsed
				}
			}
			else
			{
				eExec = new CExecStatement(tokVerb.getLine()) ;
			}
			AddChild(eExec) ;
			return Parse(eExec) ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.MOVE)
		{
			if(tokVerb.getLine()==363)
			{
				int gg = 0;
			}
			CCobolElement eExec = new CMove(tokVerb.getLine()) ;
			AddChild(eExec) ;
			return Parse(eExec) ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.EXIT)
		{
			CCobolElement eExec = new CExit(tokVerb.getLine()) ;
			AddChild(eExec) ;
			return Parse(eExec) ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.EJECT)
		{
			GetNext() ;
			return true ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.SKIP3)
		{
			GetNext() ;
			return true ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.SKIP2)
		{
			GetNext() ;
			return true ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.SKIP1)
		{
			GetNext() ;
			return true ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.START)
		{
			CCobolElement eExec = new CStart(tokVerb.getLine()) ;
			AddChild(eExec) ;
			return Parse(eExec) ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.ON)
		{
			CCobolElement eExec = new COnElse(tokVerb.getLine()) ;
			AddChild(eExec) ;
			return Parse(eExec) ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.DELETE)
		{
			CCobolElement eExec = new CDelete(tokVerb.getLine()) ;
			AddChild(eExec) ;
			return Parse(eExec) ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.SEARCH)
		{
			CCobolElement eExec = new CSearch(tokVerb.getLine()) ;
			AddChild(eExec) ;
			return Parse(eExec) ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.DISPLAY)
		{
			CCobolElement eExec = new CDisplay(tokVerb.getLine()) ;
			AddChild(eExec) ;
			return Parse(eExec) ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.WRITE)
		{
			CCobolElement eExec = new CWrite(tokVerb.getLine()) ;
			AddChild(eExec) ;
			return Parse(eExec) ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.CLOSE)
		{
			CCobolElement eExec = new CClose(tokVerb.getLine()) ;
			AddChild(eExec) ;
			return Parse(eExec) ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.OPEN)
		{
			CCobolElement eExec = new COpen(tokVerb.getLine()) ;
			AddChild(eExec) ;
			return Parse(eExec) ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.ACCEPT)
		{
			CCobolElement eExec = new CAccept(tokVerb.getLine()) ;
			AddChild(eExec) ;
			return Parse(eExec) ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.TRANSFORM)
		{
			CCobolElement eExec = new CTransform(tokVerb.getLine()) ;
			AddChild(eExec) ;
			return Parse(eExec) ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.SORT)
		{
			CCobolElement eExec = new CSort(tokVerb.getLine()) ;
			AddChild(eExec) ;
			return Parse(eExec) ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.RELEASE)
		{
			CCobolElement eExec = new CRelease(tokVerb.getLine()) ;
			AddChild(eExec) ;
			return Parse(eExec) ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.RETURN)
		{
			CCobolElement eExec = new CReturn(tokVerb.getLine()) ;
			AddChild(eExec) ;
			return Parse(eExec) ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.READ)
		{
			CCobolElement eExec = new CRead(tokVerb.getLine()) ;
			AddChild(eExec) ;
			return Parse(eExec) ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.REWRITE)
		{
			CCobolElement eExec = new CRewrite(tokVerb.getLine()) ;
			AddChild(eExec) ;
			return Parse(eExec) ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.STOP)
		{
			CCobolElement eExec = new CStop(tokVerb.getLine()) ;
			AddChild(eExec) ;
			return Parse(eExec) ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.SUBTRACT)
		{
			CCobolElement eExec = new CSubtract(tokVerb.getLine()) ;
			AddChild(eExec) ;
			return Parse(eExec) ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.MULTIPLY)
		{
			CCobolElement eExec = new CMultiply(tokVerb.getLine()) ;
			AddChild(eExec) ;
			return Parse(eExec) ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.DIVIDE)
		{
			CCobolElement eExec = new CDivide(tokVerb.getLine()) ;
			AddChild(eExec) ;
			return Parse(eExec) ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.STRING)
		{
			CCobolElement eExec = new CString(tokVerb.getLine()) ;
			AddChild(eExec) ;
			return Parse(eExec) ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.UNSTRING)
		{
			CCobolElement eExec = new CUnstring(tokVerb.getLine()) ;
			AddChild(eExec) ;
			return Parse(eExec) ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.INSPECT)
		{
			CCobolElement eExec = new CInspect(tokVerb.getLine()) ;
			AddChild(eExec) ;
			return Parse(eExec) ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.IF)
		{
			CCobolElement eIf = new CIfStatement(tokVerb.getLine()) ;
			AddChild(eIf) ;
			CFlag f = new CFlag() ;
			if (!Parse(eIf, f))
			{
				return false ;
			}
			if (f.ISSet())
			{
				m_fCheckForNextSentence.Set() ;
			}
			return true ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.PERFORM)
		{ // PERFOM verb has many usages
			return ParsePerfom();
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.EVALUATE)
		{
			CCobolElement eVerb = new CEvaluate(tokVerb.getLine()) ;
			AddChild(eVerb) ;
			return Parse(eVerb);
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.CALL)
		{
			CCobolElement eVerb = new CCall(tokVerb.getLine()) ;
			AddChild(eVerb) ;
			return Parse(eVerb);
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.INITIALIZE)
		{
			CCobolElement eVerb = new CInitialize(tokVerb.getLine()) ;
			AddChild(eVerb) ;
			return Parse(eVerb);
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.COMPUTE)
		{
			CCobolElement eVerb = new CCompute(tokVerb.getLine()) ;
			AddChild(eVerb) ;
			return Parse(eVerb);
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.GOBACK)
		{
			CCobolElement eVerb = new CGoBack(tokVerb.getLine()) ;
			AddChild(eVerb) ;
			return Parse(eVerb);
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.NEXT)
		{
			CCobolElement eVerb = new CNextSentence(tokVerb.getLine()) ;
			AddChild(eVerb) ;
			return Parse(eVerb, m_fCheckForNextSentence) ;
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.CONTINUE)
		{
			CCobolElement eVerb = new CContinue(tokVerb.getLine()) ;
			AddChild(eVerb) ;
			return Parse(eVerb);
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.ADD)
		{
			CCobolElement eVerb = new CAdd(tokVerb.getLine()) ;
			AddChild(eVerb) ;
			return Parse(eVerb);
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.GOTO)
		{
			CCobolElement eVerb = new CGoto(tokVerb.getLine()) ;
			AddChild(eVerb) ;
			return Parse(eVerb);
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.SET)
		{
			CCobolElement eVerb = new CSet(tokVerb.getLine()) ;
			AddChild(eVerb) ;
			return Parse(eVerb);
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.GO)
		{
			CCobolElement eVerb = new CGoto(tokVerb.getLine()) ;
			AddChild(eVerb) ;
			return Parse(eVerb);
		}
		else if (tokVerb.GetKeyword()==CCobolKeywordList.ELSE ||
				tokVerb.GetKeyword()==CCobolKeywordList.END_IF ||
				tokVerb.GetKeyword()==CCobolKeywordList.NOT ||
				tokVerb.GetKeyword()==CCobolKeywordList.END_EVALUATE ||
				tokVerb.GetKeyword()==CCobolKeywordList.END_RETURN ||
				tokVerb.GetKeyword()==CCobolKeywordList.END_DIVIDE ||
				tokVerb.GetKeyword()==CCobolKeywordList.END_STRING ||
				tokVerb.GetKeyword()==CCobolKeywordList.END_WRITE ||
				tokVerb.GetKeyword()==CCobolKeywordList.END_READ ||
				tokVerb.GetKeyword()==CCobolKeywordList.END_UNSTRING ||
				tokVerb.GetKeyword()==CCobolKeywordList.WHEN ||
				tokVerb.GetType()==CTokenType.END_OF_BLOCK ||
				tokVerb.GetKeyword()==CCobolKeywordList.END_PERFORM ||
				tokVerb.GetKeyword()==CCobolKeywordList.END_SEARCH ||
				tokVerb.GetKeyword()==CCobolKeywordList.END_COMPUTE ||
				tokVerb.GetKeyword()==CCobolKeywordList.END_CALL)
		{	// this keyword is the end of bloc
			//m_Logger.info("INFO Line " +getLine()+ " : " + "Keyword is end-of-bloc : '" + tokVerb.GetValue() + "'") ;
			return true ;
		}
		else if(tokVerb.GetKeyword()==CCobolKeywordList.LENGTH)
		{
			CCobolElement eVerb = new CLength(tokVerb.getLine()) ;
			AddChild(eVerb) ;
			return Parse(eVerb);
		}
		else if(tokVerb.GetKeyword()==CCobolKeywordList.COPY)	// PJD COPY Including code (PG POC)
		{
			Reporter.Add("Modif_PJ", "COPY Including code");
			String cs = tokVerb.GetValue();
			CBaseToken tokDrop = GetNext();
			Transcoder.logWarn(tokVerb.getLine(), "COPY Including code detected; Currently ignored; File="+tokDrop.GetDisplay());
			while (tokDrop != null && !tokDrop.m_bIsNewLine)
			{
				//Transcoder.logWarn(tokDrop.getLine(), "Keyword not parsed : '" + tokDrop.GetValue() + "'") ;
				tokDrop = GetNext();
				if(tokDrop == null)
				{
					int gg  = 0;
				}
			}
			return true;
		}
		else
		{
			// PJD Maybe an identifier has the name of a reserved keyword
			String cs = tokVerb.GetValue();
			Transcoder.logWarn(tokVerb.getLine(), "Keyword not parsed : '" + tokVerb.GetValue() + "'") ;
			CBaseToken tokDrop = GetNext();
			while (!tokDrop.m_bIsNewLine)
			{
				Transcoder.logWarn(tokDrop.getLine(), "Keyword not parsed : '" + tokDrop.GetValue() + "'") ;
				tokDrop = GetNext();
			}
			return true;
		}
	}
	
	/**
	 * @return
	 */
	protected abstract boolean isTopLevelBloc() ;

	protected boolean ParsePerfom()
	{
		CBaseToken tokId = GetNext() ;
		CIdentifier identifier = null ;
		if (tokId.GetType() == CTokenType.IDENTIFIER)
		{
			identifier = ReadIdentifier();
		}

		CIdentifier identifierThru = null ;
		CBaseToken tokKW = GetCurrentToken() ;
		if (tokKW.GetKeyword() == CCobolKeywordList.THRU)
		{
			CBaseToken tok = GetNext() ;
			identifierThru = ReadIdentifier(); 
			tokKW = GetCurrentToken();
		}
		
		// PERFORM usage depends on following keyword
		boolean bTestBefore = true ; // default value
		if (tokKW.GetKeyword() == CCobolKeywordList.WITH)
		{
			tokKW =GetNext();
		}
		if (tokKW.GetKeyword() == CCobolKeywordList.TEST)
		{
			tokKW =GetNext();
			if (tokKW.GetKeyword() == CCobolKeywordList.BEFORE)
			{
				tokKW = GetNext();
				bTestBefore = true ;
			}
			else if (tokKW.GetKeyword() == CCobolKeywordList.AFTER)
			{
				tokKW = GetNext();
				bTestBefore = false ;
			}
			else
			{
				Transcoder.logError(tokKW.getLine(), "Unexpecting token : " + tokKW.GetValue());
				return false ; 
			}
		}
		if (tokKW.GetKeyword() == CCobolKeywordList.UNTIL)
		{
			CCobolElement ePerform = new CPerformUntil(identifier, identifierThru, tokKW.getLine(), bTestBefore);
			AddChild(ePerform);
			return Parse(ePerform) ;
		}
		else if (tokKW.GetKeyword() == CCobolKeywordList.VARYING)
		{
			CCobolElement ePerform = new CPerformVarying(identifier, identifierThru, tokKW.getLine(), bTestBefore);
			AddChild(ePerform);
			return Parse(ePerform) ;
		}
		else if (tokKW.GetKeyword() == CCobolKeywordList.TIMES)
		{
			GetNext();
			CTerminal term = new CIdentifierTerminal(identifier) ;
			CCobolElement ePerform = new CPerform(term, tokKW.getLine());
			identifier = null;
			AddChild(ePerform);
			return Parse(ePerform) ;
		}
		else if (tokKW.GetType() == CTokenType.NUMBER)
		{
			CTerminal term = ReadTerminal();
			CBaseToken tok = GetCurrentToken() ;
			if (tok.GetKeyword() == CCobolKeywordList.TIMES)
			{
				GetNext() ;
				CCobolElement eVerb = new CPerform(identifier, identifierThru, term, tokKW.getLine());
				AddChild(eVerb) ;
				return Parse(eVerb) ;
			}
			else
			{
				Transcoder.logError(tokKW.getLine(), "Unexpecting situation in PERFORM") ;
				return false ;
			} 			
		}
		else if (identifier != null)
		{
			CCobolElement eVerb = new CPerform(identifier, identifierThru, tokId.getLine()) ;
			AddChild(eVerb) ;
			return Parse(eVerb) ;
		}
		else
		{
			Transcoder.logError(tokKW.getLine(), "Unexpecting situation in PERFORM") ;
			return false ;
		}
	}
	
	/* (non-Javadoc)
	 * @see parser.CBaseElement#DoCustomSemanticAnalysis(semantic.CBaseSemanticEntity, semantic.CBaseSemanticEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityBloc eBloc = factory.NewEntityBloc(getLine()) ;
		eBloc.SetEndLine(m_nEndLine) ;
		eBloc.SetParent(parent);
		if (m_nRewriteLine != 0)
		{
			CGlobalEntityCounter.GetInstance().RegisterProgramToRewrite(parent.GetProgramName(), m_nRewriteLine, "NEXT SENTENCE") ;
		}
//		if (parent != null)
//		{
//			parent.AddChild(eBloc);
//		}
		return eBloc;
	}

//	public boolean Parse(CTokenList lstTokens)
//	{
//		return Parse(lstTokens, this);
//	}
//	protected boolean Parse(CLanguageElement e, CFlag f)
//	{
//		return e.Parse(m_lstTokens, m_con, f) ;
//	}
	protected CFlag m_fCheckForNextSentence = new CFlag();
	protected int m_nEndLine = 0 ;
	public void SetEndLine(int n)
	{
		m_nEndLine = n ;
	}

}
