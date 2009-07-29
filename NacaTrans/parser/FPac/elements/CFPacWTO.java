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

import jlib.misc.NumberParser;
import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.FPac.CFPacKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.FPac.CFPacElement;
import parser.expression.CTerminal;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.CSubStringAttributReference;
import semantic.CDataEntity.CDataEntityType;
import semantic.Verbs.CEntityConvertReference;
import semantic.Verbs.CEntityDisplay;
import semantic.expression.CBaseEntityExpression;
import utils.Transcoder;
import utils.FPacTranscoder.OperandDescription;

public class CFPacWTO extends CFPacElement
{

	private CTerminal m_termToDisplay;
	private CTerminal m_termLength ;

	public CFPacWTO(int line)
	{
		super(line);
	}

	@Override
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if  (tok.GetKeyword() == CFPacKeywordList.WTO)
			tok = GetNext() ;
		
		if (tok.GetType() == CTokenType.MINUS)
		{
			tok = GetNext() ;
			CTerminal term = ReadTerminal() ;
			m_termToDisplay = term ;
			tok = GetCurrentToken() ;
			if (tok.GetType() == CTokenType.COMMA)
			{
				tok = GetNext() ;
				m_termLength = ReadTerminal() ;
			}
		}
		else
		{
			Transcoder.logError(tok.getLine(), "Expecting '-' instead of "+tok.toString()) ;
			return false ;
		}
		return true ;
	}

	@Override
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityDisplay disp = factory.NewEntityDisplay(getLine(), true) ;
		CDataEntity e = m_termToDisplay.GetDataEntity(getLine(), factory) ;
		CDataEntity toDisp = null ;
		if (e.GetDataType() == CDataEntityType.ADDRESS)
		{
			if (m_termLength != null)
			{
				CDataEntity len = m_termLength.GetDataEntity(getLine(), factory) ;
				CBaseEntityExpression explen = factory.NewEntityExprTerminal(len) ;
				CBaseEntityExpression expadd = factory.NewEntityExprTerminal(e) ;
				
				String add = e.GetConstantValue() ;
				int nadd = NumberParser.getAsInt(add) ;
				CDataEntity buffer = null ;
				if (nadd < 5000)
				{ //file buffer 
					buffer = OperandDescription.getDefaultInputFileBuffer(factory.m_ProgramCatalog) ;
				}
				else
				{ // working
					buffer = factory.m_ProgramCatalog.GetDataEntity("WORKING", "") ;
				}
				CEntityConvertReference conv = factory.NewEntityConvert(getLine()) ;
				conv.convertToAlphaNum(buffer) ;
				CSubStringAttributReference substr = factory.NewEntitySubString(getLine()) ;
				substr.SetReference(conv, expadd, explen) ;
				toDisp = substr ;
			}
		}
		else
		{
			toDisp = e ;
		}
		disp.AddItemToDisplay(toDisp) ;
		toDisp.RegisterReadingAction(disp) ;
		parent.AddChild(disp) ;
		return disp ;
	}

	@Override
	protected Element ExportCustom(Document root)
	{
		Element eAdd = root.createElement("WriteToOuput") ;
		Element e = root.createElement("Data") ;
		m_termToDisplay.ExportTo(e, root) ;
		eAdd.appendChild(e) ;
		return eAdd ;
	}

}
