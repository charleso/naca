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

import java.util.Vector;

import jlib.misc.NumberParser;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.FPac.CFPacKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.FPac.CFPacElement;
import parser.expression.CAddressTerminal;
import parser.expression.CStringTerminal;
import parser.expression.CTerminal;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.CSubStringAttributReference;
import semantic.CDataEntity.CDataEntityType;
import semantic.Verbs.CEntityCallProgram;
import semantic.Verbs.CEntityConvertReference;
import semantic.Verbs.CEntityRoutineEmulation;
import semantic.Verbs.CEntityRoutineEmulationCall;
import utils.CGlobalEntityCounter;
import utils.Transcoder;
import utils.FPacTranscoder.OperandDescription;

public class CFPacCall extends CFPacElement
{

	public CFPacCall(int line)
	{
		super(line);
	}

	@Override
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CFPacKeywordList.CALL)
		{
			tok = GetNext() ;
		}
		
		if  (tok.GetType() == CTokenType.MINUS)
		{
			tok = GetNext() ;
			if (tok.GetType() == CTokenType.IDENTIFIER)
			{
				String cs = tok.GetValue() ;
				m_idCalled = new CStringTerminal(cs) ;
				tok = GetNext();
				while (tok.GetType() == CTokenType.COMMA)
				{
					tok = GetNext() ;
					if (tok.GetType() == CTokenType.NUMBER)
					{
						m_arrTermParam.add(new CAddressTerminal(tok.GetValue())) ;
						tok = GetNext() ;
					}
					else
					{
						Transcoder.logError(tok.getLine(), "4. Unparsed token : "+tok.toString()) ;
						return false ;
					}
				}
			}
			else
			{
				Transcoder.logError(tok.getLine(), "Expecting IDENTIFIER after CALL-") ;
				return false ;
			}
		}
		else
		{
			Transcoder.logError(tok.getLine(), "Expecting '-' after CALL") ;
			return false ;
		}
		return true ;
	}
	
	protected CTerminal m_idCalled = null ;
	protected Vector<CTerminal> m_arrTermParam = new Vector<CTerminal>() ;

	@Override
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		String prg = m_idCalled.GetValue() ;
		
		boolean bCheck = true ;
		CEntityRoutineEmulation emul = factory.m_ProgramCatalog.getRoutineEmulation(prg) ;
		if (emul != null)
		{
			CEntityRoutineEmulationCall call = emul.NewCall(getLine(), factory) ;
			for (CTerminal term : m_arrTermParam) 
			{
				CDataEntity param = term.GetDataEntity(getLine(), factory) ;
				if (param.GetDataType() == CDataEntityType.ADDRESS)
				{
					int add = NumberParser.getAsInt(param.GetConstantValue()) ;
					CDataEntity buffer = null ;
					if (add < 5000)
					{ //file buffer 
						buffer = OperandDescription.getDefaultInputFileBuffer(factory.m_ProgramCatalog) ;
					}
					else
					{ // working
						buffer = factory.m_ProgramCatalog.GetDataEntity("WORKING", "") ;
					}
					CEntityConvertReference conv = factory.NewEntityConvert(getLine()) ;
					conv.convertToAlphaNum(buffer) ;
					CSubStringAttributReference ss = factory.NewEntitySubString(0) ;
					ss.SetReference(conv, factory.NewEntityExprTerminal(param), null) ;
					call.AddParameter(ss) ;
					ss.RegisterReadingAction(call);
				}
				else
				{
					call.AddParameter(param) ;
					param.RegisterReadingAction(call);
				}
			}
			parent.AddChild(call);
			return call ;
		}
		else
		{
			if (!factory.m_ProgramCatalog.CheckProgramReference(prg, false, m_arrTermParam.size(), true))
			{
				Transcoder.logError(getLine(), "Missing sub program : "+prg) ;
				CGlobalEntityCounter.GetInstance().RegisterMissingSubProgram(parent.GetProgramName(), prg) ;
				bCheck = false ;
			}
			else
			{
				//Transcoder.info("Referenced program found : "+prg) ;
				bCheck = true ;
			}
		}

		CDataEntity ref = m_idCalled.GetDataEntity(getLine(), factory) ;
		CEntityCallProgram call = factory.NewEntityCallProgram(getLine(), ref) ;
		call.setChecked(bCheck) ;
		for (CTerminal term : m_arrTermParam) 
		{
			CDataEntity param = term.GetDataEntity(getLine(), factory) ;
			if (param.GetDataType() == CDataEntityType.ADDRESS)
			{
				int add = NumberParser.getAsInt(param.GetConstantValue()) ;
				if (add < 5000)
				{ //file buffer 
					CDataEntity buffer = OperandDescription.getDefaultInputFileBuffer(factory.m_ProgramCatalog) ;
					CSubStringAttributReference ss = factory.NewEntitySubString(0) ;
					ss.SetReference(buffer, factory.NewEntityExprTerminal(param), null) ;
					call.SetParameterByRef(ss) ;
				}
				else
				{ // working
					CDataEntity working = factory.m_ProgramCatalog.GetDataEntity("WORKING", "") ;
					CSubStringAttributReference ss = factory.NewEntitySubString(0) ;
					ss.SetReference(working, factory.NewEntityExprTerminal(param), null) ;
					call.SetParameterByRef(ss) ;
				}
			}
			else
			{
				call.SetParameterByValue(param) ;
			}
		}
		parent.AddChild(call) ;
		return call ;
	}

	@Override
	protected Element ExportCustom(Document root)
	{
		Element e = root.createElement("Call") ;
		Element eId = root.createElement("Id") ;
		e.appendChild(eId) ;
		m_idCalled.ExportTo(eId, root) ;
		for (CTerminal term : m_arrTermParam)
		{
			Element eP = root.createElement("Param") ;
			e.appendChild(eP) ;
			term.ExportTo(eP, root) ;
		}
		return e ;
	}

}
