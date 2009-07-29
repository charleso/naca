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
package parser.FPac.elements;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import jlib.misc.NumberParser;
import lexer.CBaseToken;
import lexer.CReservedKeyword;
import lexer.CTokenType;
import lexer.FPac.CFPacKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CIdentifier;
import parser.FPac.CFPacElement;
import parser.expression.CAddressTerminal;
import parser.expression.CExpression;
import parser.expression.CIdentifierTerminal;
import parser.expression.CNumberTerminal;
import parser.expression.CStringTerminal;
import parser.expression.CSumExpression;
import parser.expression.CTermExpression;
import parser.expression.CTerminal;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CEntityBloc;
import semantic.CEntityCondition;
import semantic.CEntityFileDescriptor;
import semantic.CEntityProcedure;
import semantic.Verbs.CEntityCloseFile;
import semantic.Verbs.CEntityContinue;
import semantic.Verbs.CEntityOpenFile;
import semantic.Verbs.CEntityReturn;
import semantic.Verbs.CEntityWriteFile;
import semantic.expression.CEntityCondIsBoolean;
import semantic.expression.CEntityFunctionCall;
import utils.Transcoder;
import utils.FPacTranscoder.notifs.NotifGetAllFilesNotClosed;
import utils.FPacTranscoder.notifs.NotifGetAllFilesNotOpen;
import utils.FPacTranscoder.notifs.NotifGetDefaultInputFile;
import utils.FPacTranscoder.notifs.NotifGetDefaultOutputFile;

public class CFPacCodeBloc extends CFPacElement
{
	protected int m_nEndLine = 0 ;
	protected String m_csName = "" ;
	public CFPacCodeBloc(int line, String csName)
	{
		super(line);
		m_csName = csName ;
	}

	@Override
	protected boolean DoParsing()
	{
		boolean bDone = false ;
		while  (!bDone)
		{
			CBaseToken tok = GetCurrentToken() ;
			if  (tok == null)
			{
				bDone = true ;
			}
			else if (tok.GetKeyword() == CFPacKeywordList.P ||
							tok.GetKeyword() == CFPacKeywordList.X ||
							tok.GetKeyword() == CFPacKeywordList.C ||
							tok.GetType() == CTokenType.NUMBER ||
							tok.GetType() == CTokenType.IDENTIFIER)
			{
				if (!ParseInstructionLine())
				{
					return false ;
				}
			}
			else if (tok.GetType() == CTokenType.KEYWORD)
			{
				if (!ParseKeyWord(tok))
				{
					if (tok == GetCurrentToken())
					{
						bDone = true ;
					}
					else
						return false ;
				}
			}
			else
			{
				bDone = true ;
			}
		}
		return true ;
	}

	private boolean ParseInstructionLine()
	{
		Vector<CExpression> arrTerms = new Vector<CExpression>() ;
		CReservedKeyword kCommand = null ;
		int nLine = GetCurrentToken().getLine() ;
		boolean bOk = true ;
		while (bOk)
		{
			CBaseToken tok = GetCurrentToken() ;
			if  (tok.GetKeyword() == CFPacKeywordList.P)
			{
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.STRING)
				{
					String cs = tok.GetValue() ;
					CTerminal term = new CNumberTerminal(cs);
					arrTerms.add(new CTermExpression(tok.getLine(), term)) ;
					tok = GetNext() ;
				}
				else if (tok.GetType() == CTokenType.COMMA)
				{
					kCommand = CFPacKeywordList.P ;
				}
				else
				{
					Transcoder.logError(tok.getLine(), "Expecting 'STRING' after 'P'") ;
					return false ;
				}
			}
			else if (tok.GetKeyword() == CFPacKeywordList.C)
			{
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.STRING)
				{
					String cs = tok.GetValue() ;
					CTerminal term = new CStringTerminal(cs);
					arrTerms.add(new CTermExpression(tok.getLine(), term)) ;
				}
				else
				{
					Transcoder.logError(tok.getLine(), "Expecting 'STRING' after 'C'") ;
					return false ;
				}
				tok = GetNext() ;
			}
			else if (tok.GetType() == CTokenType.STRING)
			{
				String cs = tok.GetValue() ;
				CTerminal term = new CStringTerminal(cs);
				arrTerms.add(new CTermExpression(tok.getLine(), term)) ;
				tok = GetNext() ;
			}
			else if (tok.GetKeyword() == CFPacKeywordList.E)
			{
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.STRING)
				{
					String cs = tok.GetValue() ;
					CTerminal term = new CStringTerminal(cs);
					arrTerms.add(new CTermExpression(tok.getLine(), term)) ;
				}
				else
				{
					Transcoder.logError(tok.getLine(), "Expecting 'STRING' after 'E'") ;
					return false ;
				}
				tok = GetNext() ;
			}
			else if (tok.GetKeyword() == CFPacKeywordList.X)
			{
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.STRING)
				{
					String cs = tok.GetValue() ;
					CTerminal term = new CNumberTerminal("0x" + cs);
					arrTerms.add(new CTermExpression(tok.getLine(), term)) ;
				}
				else
				{
					Transcoder.logError(tok.getLine(), "Expecting 'STRING' after 'X'") ;
					return false ;
				}
				tok = GetNext() ;
			}
			else if (tok.GetKeyword() == CFPacKeywordList.U)
			{
				kCommand = tok.GetKeyword() ;
				tok = GetNext() ;
			}
			else if (tok.GetKeyword() == CFPacKeywordList.A)
			{
				kCommand = tok.GetKeyword() ;
				tok = GetNext() ;
			}
			else if (tok.GetKeyword() == CFPacKeywordList.M)
			{
				kCommand = tok.GetKeyword() ;
				tok = GetNext() ;
			}
			else if (tok.GetKeyword() == CFPacKeywordList.D)
			{
				kCommand = tok.GetKeyword() ;
				tok = GetNext() ;
			}
			else if (tok.GetKeyword() == CFPacKeywordList.ZA)
			{
				kCommand = tok.GetKeyword() ;
				tok = GetNext() ;
			}
			else if (tok.GetKeyword() == CFPacKeywordList.CB)
			{
				kCommand = tok.GetKeyword() ;
				tok = GetNext() ;
			}
			else if (tok.GetKeyword() == CFPacKeywordList.CD)
			{
				kCommand = tok.GetKeyword() ;
				tok = GetNext() ;
			}
			else if (tok.GetKeyword() == CFPacKeywordList.II)
			{
				kCommand = tok.GetKeyword() ;
				tok = GetNext() ;
			}
			else if (tok.GetKeyword() == CFPacKeywordList.OO)
			{
				kCommand = tok.GetKeyword() ;
				tok = GetNext() ;
			}
			else if (tok.GetKeyword() == CFPacKeywordList.S)
			{
				kCommand = tok.GetKeyword() ;
				tok = GetNext() ;
			}
			else if (tok.GetType() == CTokenType.NUMBER)
			{
				CTerminal term = new CAddressTerminal(tok.GetValue()) ;
				CExpression exp = new CTermExpression(tok.getLine(), term) ;
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.PLUS)
				{
					tok = GetNext() ;
					CExpression exp2 = ReadTerminalExpression() ;
					if (exp2 == null)
					{
						Transcoder.logError(tok.getLine(), "Expecting expression after '+'") ;
						return false ;
					}
					CSumExpression sum = new CSumExpression(tok.getLine(), exp, exp2, CSumExpression.CSumType.ADD) ;
					arrTerms.add(sum) ;
				}
				else
				{
					arrTerms.add(exp) ;
				}
			}
			else if (tok.GetType() == CTokenType.IDENTIFIER)
			{
				CBaseToken tokid = tok ;
				CIdentifier id = new CIdentifier(tok.GetValue()) ;
				tok = GetNext() ;
				if  (tok.GetType() == CTokenType.MINUS)
				{
					tok = GetNext() ;
					if (tok.GetType() == CTokenType.NUMBER)
					{
						CExpression exp = new CTermExpression(0, new CAddressTerminal(tok.GetValue())) ;
						//id.SetSubStringReference(exp, null) ;
						CTerminal term = new CIdentifierTerminal(id) ;
						arrTerms.add(new CTermExpression(tok.getLine(), term)) ;
						arrTerms.add(exp) ;
						tok = GetNext() ;
					}
					else
					{
						Transcoder.logError(tok.getLine(), "Expecting number instead of token : "+tok.toString()) ;
						return false ;
					}
				}
				else if (tok.GetType() == CTokenType.EQUALS)
				{
					tok = GetNext() ;
					//CExpression exp = ReadExpression() ;
					CExpression exp = null ;
					if (tok.GetType() == CTokenType.NUMBER)
					{
						exp = new CTermExpression(tok.getLine(), new CNumberTerminal(tok.GetValue())) ;
					}
					else if (tok.GetType() == CTokenType.IDENTIFIER)
					{
						exp = new CTermExpression(tok.getLine(), new CIdentifierTerminal(new CIdentifier(tok.GetValue()))) ;
					}
					else
					{
						Transcoder.logError(tok.getLine(), "Expecting expression after '+'") ;
						return false ;
					}
					CFPacAssign ass = new CFPacAssign(tok.getLine(), id, exp) ;
					AddChild(ass) ;
					tok = GetNext() ;
					return true ;
				}
				else if (tok.GetType() == CTokenType.PLUS)
				{
					tok = GetNext() ;
					int line = tok.getLine() ;
					CTerminal term = ReadTerminal() ;
					if (term == null)
					{
						Transcoder.logError(tok.getLine(), "Expecting terminal after '+'") ;
						return false ;
					}
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.COMMA)
					{
						CSumExpression sum = new CSumExpression(tok.getLine(), 
										new CTermExpression(tok.getLine(), new CIdentifierTerminal(id)), 
										new CTermExpression(tok.getLine(), term), CSumExpression.CSumType.ADD) ;
						arrTerms.add(sum) ;
					}
					else
					{
						CFPacInc inc = new CFPacInc(line) ;
						if (!term.IsReference() && NumberParser.getAsInt(term.GetValue())>0)
						{
							term = new CNumberTerminal(term.GetValue()) ;
						}
						inc.Increments(id, term) ;
						AddChild(inc) ;
						return true ;
					}
				}
				else if (tok.GetType() == CTokenType.COMMA)
				{
					CTerminal term = new CIdentifierTerminal(id) ;
					arrTerms.add(new CTermExpression(tok.getLine(), term)) ;
				}
				else
				{
					CTerminal term = new CIdentifierTerminal(id) ;
					arrTerms.add(new CTermExpression(tok.getLine(), term)) ;
				}
			}
		
			tok = GetCurrentToken() ;
			if (tok.GetType() == CTokenType.COMMA)
			{
				tok = GetNext();
			}
			else
			{
				bOk = false ;
			}
		}
		
		if (kCommand == CFPacKeywordList.A)
		{
			CFPacArithmeticOperation add = new CFPacArithmeticOperation(nLine, arrTerms, kCommand) ;
			if (!Parse(add))
			{
				return false ;
			}
			AddChild(add) ;
		}
		else if (kCommand == CFPacKeywordList.P)
		{
			CFPacConvert conv = new CFPacConvert(nLine, arrTerms, kCommand) ;
			if (!Parse(conv))
			{
				return false ;
			}
			AddChild(conv) ;
		}
		else if (kCommand == CFPacKeywordList.II)
		{
			CFPacMove ass = new CFPacMove(nLine, arrTerms) ;
			ass.moveToInput();
			AddChild(ass) ;
		}
		else if (kCommand == CFPacKeywordList.OO)
		{
			CFPacMove ass = new CFPacMove(nLine, arrTerms) ;
			ass.moveFromOutput();
			AddChild(ass) ;
		}
		else if (kCommand == CFPacKeywordList.ZA)
		{
			CFPacMove ass = new CFPacMove(nLine, arrTerms) ;
//			Transcoder.warn("WARNING : ZA command not managed yet") ;
			ass.movePacked();
			AddChild(ass) ;
		}
		else if (kCommand == CFPacKeywordList.M)
		{
			CFPacArithmeticOperation add = new CFPacArithmeticOperation(nLine, arrTerms, kCommand) ;
			if (!Parse(add))
			{
				return false ;
			}
			AddChild(add) ;
		}
		else if (kCommand == CFPacKeywordList.S)
		{
			CFPacArithmeticOperation add = new CFPacArithmeticOperation(nLine, arrTerms, kCommand) ;
			if (!Parse(add))
			{
				return false ;
			}
			AddChild(add) ;
		}
		else if (kCommand == CFPacKeywordList.D)
		{
			CFPacArithmeticOperation add = new CFPacArithmeticOperation(nLine, arrTerms, kCommand) ;
			if (!Parse(add))
			{
				return false ;
			}
			AddChild(add) ;
		}
		else if (kCommand == CFPacKeywordList.CB)
		{
			CFPacMove ass = new CFPacMove(nLine, arrTerms) ;
			Transcoder.logWarn(nLine, "CB command not managed yet") ;
			AddChild(ass) ;
		}
		else if (kCommand == CFPacKeywordList.CD)
		{
			CFPacMove ass = new CFPacMove(nLine, arrTerms) ;
			Transcoder.logWarn(nLine, "CD command not managed yet") ;
			AddChild(ass) ;
		}
		else if (kCommand == CFPacKeywordList.U)
		{
			CFPacArithmeticOperation add = new CFPacArithmeticOperation(nLine, arrTerms, kCommand) ;
			if (!Parse(add))
			{
				return false ;
			}
			AddChild(add) ;
		}
		else
		{
			CFPacMove ass = new CFPacMove(nLine, arrTerms) ;
			if (!Parse(ass)) 
			{
				return false ;
			}
			AddChild(ass) ;
		}
		return true ;
		
	}

	private boolean ParseKeyWord(CBaseToken tok)
	{
		CFPacElement el = null;
		if (tok.GetKeyword() == CFPacKeywordList.OPEN)
		{
			CFPacOpen open = new CFPacOpen(tok.getLine()) ;
			el = open; 
		}
		else if (tok.GetKeyword() == CFPacKeywordList.IF)
		{
			CFPacCondition open = new CFPacCondition(tok.getLine()) ;
			el = open; 
		}
		else if (tok.GetKeyword() == CFPacKeywordList.CLOSE)
		{
			CFPacClose open = new CFPacClose(tok.getLine()) ;
			el = open; 
		}
		else if (tok.GetKeyword() == CFPacKeywordList.TO)
		{
			CFPacTo open = new CFPacTo(tok.getLine()) ;
			el = open; 
		}
		else if (tok.GetKeyword() == CFPacKeywordList.FROM)
		{
			CFPacFrom open = new CFPacFrom(tok.getLine()) ;
			el = open; 
		}
		else if (tok.GetKeyword() == CFPacKeywordList.GET)
		{
			CFPacGet open = new CFPacGet(tok.getLine()) ;
			el = open; 
		}
		else if (tok.GetKeyword() == CFPacKeywordList.PUT)
		{
			CFPacPut open = new CFPacPut(tok.getLine()) ;
			el = open; 
		}
		else if (tok.GetKeyword() == CFPacKeywordList.GOEND || tok.GetKeyword() == CFPacKeywordList.GOABEND)
		{
			CFPacGoEnd open = new CFPacGoEnd(tok.getLine()) ;
			el = open; 
		}
		else if (tok.GetKeyword() == CFPacKeywordList.GOLAST)
		{
			CFPacGoLast open = new CFPacGoLast(tok.getLine()) ;
			el = open; 
		}
		else if (tok.GetKeyword() == CFPacKeywordList.GOBACK)
		{
			CFPacGoback open = new CFPacGoback(tok.getLine()) ;
			el = open; 
		}
		else if (tok.GetKeyword() == CFPacKeywordList.WTO)
		{
			CFPacWTO wto = new CFPacWTO(tok.getLine()) ;
			el = wto; 
		}
		else if (tok.GetKeyword() == CFPacKeywordList.CALL)
		{
			CFPacCall call = new CFPacCall(tok.getLine()) ;
			el = call; 
		}
		else if (tok.GetKeyword() == CFPacKeywordList.DO)
		{
			CFPacDoLoop doloop = new CFPacDoLoop(tok.getLine()) ;
			el = doloop; 
		}
		else if (tok.GetKeyword() == CFPacKeywordList.AT)
		{
			CFPacAt doloop = new CFPacAt(tok.getLine()) ;
			el = doloop; 
		}
		else if (tok.GetKeyword() == CFPacKeywordList.DOSUBR)
		{
			CFPacDoSubr doSubr = new CFPacDoSubr(tok.getLine()) ;
			el = doSubr; 
		}
		else if (tok.GetKeyword() == CFPacKeywordList.DOQUIT)
		{
			CFPacDoQuit doSubr = new CFPacDoQuit(tok.getLine()) ;
			el = doSubr; 
		}
		else
		{
			//Transcoder.logError("Line "+tok.getLine()+" : unexpected token "+tok.GetDisplay()) ;
			// managed case : if  false -> keyword not recognized, end of bloc parsing
			return false ;
		}
		if (!Parse(el))
			return false ;
		AddChild(el) ;
		return true ;
	}

	@Override
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		if (m_csName.equals(""))
		{
			CEntityBloc e = factory.NewEntityBloc(getLine()) ;
			if (parent != null)
				parent.AddChild(e) ;
			e.SetEndLine(m_nEndLine) ;
			return e ;
		}
		else
		{
			CEntityProcedure e = factory.NewEntityProcedure(getLine(), m_csName, null) ;
			if (parent != null)
				parent.AddChild(e) ;
			e.SetEndLine(m_nEndLine) ;
			
			if (m_csName.equalsIgnoreCase("NORMAL"))
			{
				DoSemanticAnalysisForChildren(e, factory) ;
				m_bAnalysisDoneForChildren = true;
				
				NotifGetDefaultInputFile notif = new NotifGetDefaultInputFile() ;
				factory.m_ProgramCatalog.SendNotifRequest(notif) ;
				if (notif.fileBuffer != null)
				{
					if (notif.fileBuffer.GetFileDescriptor().GetName().endsWith("F"))  // "IPF" or "IPF1"
					{
						CEntityCondition test = factory.NewEntityCondition(getLine()) ;
						CEntityFunctionCall call = factory.NewEntityFunctionCall("ReadAndTestFile", notif.fileBuffer) ;
						CEntityCondIsBoolean bool = factory.NewEntityCondIsBoolean() ;
						bool.setIsTrue(call) ;
						CEntityBloc thenBloc = factory.NewEntityBloc(0) ;
						CEntityReturn ret = factory.NewEntityReturn(0) ;
						thenBloc.AddChild(ret) ;
						test.SetCondition(bool, thenBloc, null) ;				
						e.AddChild(test, null);
						
					}
				}

				if (!e.hasExplicitGetOut())
				{
					NotifGetDefaultOutputFile notifOutput = new NotifGetDefaultOutputFile() ;
					factory.m_ProgramCatalog.SendNotifRequest(notifOutput) ;
					if (notifOutput.fileBuffer != null)
					{
						if (notifOutput.fileBuffer.GetFileDescriptor().GetName().endsWith("F"))  // "OPF" or "OPF1"
						{
							CEntityWriteFile write = factory.NewEntityWriteFile(0) ;
							write.setFileDescriptor(notifOutput.fileBuffer.GetFileDescriptor(), null) ;
							e.AddChild(write) ;
						}
					}
					if (notif.fileBuffer != null)
					{
						if (notif.fileBuffer.GetFileDescriptor().GetName().endsWith("F"))  // "IPF" or "IPF1"
						{
							CEntityContinue cont = factory.NewEntityContinue(0) ;
							e.AddChild(cont) ;
						}
					}
				}
			}
			else if (m_csName.equalsIgnoreCase("FIRST"))
			{
				DoSemanticAnalysisForChildren(e, factory) ;
				m_bAnalysisDoneForChildren = true;
				
				NotifGetAllFilesNotOpen notif = new NotifGetAllFilesNotOpen() ;
				factory.m_ProgramCatalog.SendNotifRequest(notif) ;
				Collections.sort(notif.m_arrFiles, new Comparator<CEntityFileDescriptor>() {
					public int compare(CEntityFileDescriptor o1, CEntityFileDescriptor o2)
					{
						return o1.GetName().compareTo(o2.GetName());
					}
				});
				for (CEntityFileDescriptor desc : notif.m_arrFiles)
				{
					CEntityOpenFile open = factory.NewEntityOpenFile(0) ;
					open.setFileDescriptor(desc, null) ;
					e.AddChild(open, null) ;
				}
			}
			else if (m_csName.equalsIgnoreCase("LAST"))
			{
				DoSemanticAnalysisForChildren(e, factory) ;
				m_bAnalysisDoneForChildren = true;
				
				NotifGetAllFilesNotClosed notif = new NotifGetAllFilesNotClosed() ;
				factory.m_ProgramCatalog.SendNotifRequest(notif) ;
				Collections.sort(notif.m_arrFiles, new Comparator<CEntityFileDescriptor>() {
					public int compare(CEntityFileDescriptor o1, CEntityFileDescriptor o2)
					{
						return o2.GetName().compareTo(o1.GetName());
					}
				});
				for (CEntityFileDescriptor desc : notif.m_arrFiles)
				{
					CEntityCloseFile close = factory.NewEntityCloseFile(0) ;
					close.setFileDescriptor(desc) ;
					e.AddChild(close) ;
				}
			}
			return e ;
		}
	}

	@Override
	protected Element ExportCustom(Document root)
	{
		String cs = "Bloc" ;
		if (!m_csName.equals(""))
			cs = m_csName ;
		Element eAdd = root.createElement(cs) ;
		return eAdd;
	}

	/**
	 * @param line
	 */
	public void SetEndLine(int line)
	{
		m_nEndLine = line ;
	}

}
