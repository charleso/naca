/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package parser.FPac;

import java.util.Iterator;
import java.util.Vector;

import jlib.misc.NumberParser;

import lexer.CReservedKeyword;
import lexer.FPac.CFPacKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.expression.CDefaultConditionManager;
import parser.expression.CExpression;
import semantic.CBaseEntityFactory;
import semantic.CDataEntity;
import semantic.CSubStringAttributReference;
import semantic.CDataEntity.CDataEntityType;
import semantic.Verbs.CEntityConvertReference;
import semantic.expression.CBaseEntityCondition;
import semantic.expression.CBaseEntityExpression;
import semantic.expression.CEntityCondCompare;
import semantic.expression.CEntityCondEquals;
import semantic.expression.CEntityCondIsConstant;
import semantic.expression.CEntityCondIsKindOf;
import semantic.expression.CEntityExprTerminal;
import semantic.expression.CEntityNumber;
import semantic.expression.CEntityString;
import utils.CObjectCatalog;
import utils.Transcoder;
import utils.FPacTranscoder.OperandDescription;
import utils.FPacTranscoder.notifs.NotifGetDefaultInputFile;
import utils.FPacTranscoder.notifs.NotifGetDefaultOutputFile;

public class CFPacGenericExpression extends CExpression
{

	private CReservedKeyword m_keyword;
	private Vector<CExpression> m_arrLeftTerms = new Vector<CExpression>() ;
	private Vector<CExpression> m_arrRightTerms = new Vector<CExpression>() ;

	public CFPacGenericExpression(int line)
	{
		super(line);
	}

	@Override
	public CBaseEntityExpression AnalyseExpression(CBaseEntityFactory factory)
	{
		return null ;
	}

	private OperandDescription FindOperand(Vector<CExpression> arrTerms, CBaseEntityFactory factory)
	{
		Iterator<CExpression> iter = arrTerms.iterator() ;
		CExpression exp = iter.next() ;
		OperandDescription desc = new OperandDescription() ;
		if (exp.IsConstant() || exp.IsReference())
		{
			CEntityExprTerminal term = (CEntityExprTerminal)exp.AnalyseExpression(factory) ;
			if (term.GetDataType() == CDataEntityType.NUMBER)
			{
				String val = term.GetConstantValue() ;
				CEntityNumber number = factory.NewEntityNumber(val) ;
				desc.m_eObject = number ;
				desc.m_expStart = null ;
				desc.m_expLength = null ;
				if (val.startsWith("0x"))
				{
					desc.m_expLength = factory.NewEntityExprTerminal(factory.NewEntityNumber((val.length()-2)/2)) ;
					if (!isPackedHexa(val.substring(2)))
						desc.m_bHexaNoPacked = true;
				}
				else
				{
					desc.m_expLength = factory.NewEntityExprTerminal(factory.NewEntityNumber(8)) ;
				}
				return desc ;
			}
			else if (term.GetDataType() == CDataEntityType.STRING)
			{
				String val = term.GetConstantValue() ;
				CEntityString string = factory.NewEntityString(val) ;
				desc.m_eObject = string ;
				desc.m_expStart = null ;
				desc.m_expLength = factory.NewEntityExprTerminal(factory.NewEntityNumber(val.length()))  ;
				return desc ;
			}
			else if (term.GetDataType() == CDataEntityType.ADDRESS)
			{
				String val = term.GetConstantValue() ;
				int add = NumberParser.getAsInt(val) ;
				
				if (add < 5000)
				{ //file buffer 
					CDataEntity buffer = getDefaultInputFileBuffer(factory.m_ProgramCatalog) ;
					desc.m_eObject = buffer ;
					desc.m_expStart = term ;
				}
				else
				{ // working
					CDataEntity working = factory.m_ProgramCatalog.GetDataEntity("WORKING", "") ;
					desc.m_eObject = working ;
					desc.m_expStart = term ;
				}
			}
			else if (term.GetDataType() == CDataEntityType.NUMERIC_VAR)
			{
				CDataEntity var = term.GetSingleOperator() ;
				desc.m_eObject = var ;
				desc.m_expStart = null ;
			}
			else if (term.GetDataType() == CDataEntityType.VAR)
			{
				CDataEntity var = term.GetSingleOperator() ;
				if (iter.hasNext())
				{
					CExpression expstart = iter.next()  ;
					CBaseEntityExpression termstart = expstart.AnalyseExpression(factory) ;
					if (termstart.GetDataType() == CDataEntityType.ADDRESS)
					{
						desc.m_eObject = var ;
						desc.m_expStart = termstart ;
					}
				}
				else
				{
					desc.m_eObject = var ;
					desc.m_expStart = null ;
				}
			}
			else
			{
				Transcoder.logError(getLine(), "Unexpecting expression : " + exp.toString()) ;
				return null ;
			}
		}
		else
		{
			CExpression op = exp.GetFirstCalculOperand() ;
			CBaseEntityExpression term = exp.AnalyseExpression(factory) ;
			if (op.IsConstant())
			{
				String val = op.GetConstantValue() ;
				int add = NumberParser.getAsInt(val) ;
				if (add < 5000)
				{ //file buffer
					CDataEntity buffer = getDefaultInputFileBuffer(factory.m_ProgramCatalog) ;
					desc.m_eObject = buffer ;
					desc.m_expStart = term ;
					/*CDataEntity buffer = getDefaultOutputFileBuffer(factory.m_ProgramCatalog) ;
					desc.m_eObject = buffer ;
					desc.m_expStart = term ;*/
				}
				else
				{ // working
					CDataEntity working = factory.m_ProgramCatalog.GetDataEntity("WORKING", "") ;
					desc.m_eObject = working ;
					desc.m_expStart = term ;
				}
			}
			else
			{
				Transcoder.logError(getLine(), "Unexpecting expression : " + exp.toString()) ;
				return null ;
			}
		}
		
		if (iter.hasNext())
		{
			exp = iter.next() ;
			CEntityExprTerminal term = (CEntityExprTerminal)exp.AnalyseExpression(factory) ;
			if (term.GetDataType() == CDataEntityType.ADDRESS)
			{
				desc.m_expLength = term ;
			}
			else
			{
				Transcoder.logError(getLine(), "Unexpecting expression :  " + exp.toString()) ;
				return null ;
			}
		}
		return desc;
	}
	
	private boolean isPackedHexa(String cs)	// Autodermine if the cs value is a packed one or a string described in hexadecimal codes
	{
		for(int n=0; n<cs.length()-1; n++)
		{
			char c = cs.charAt(n);
			if(c < '0' || c > '9')
				return false;
		}
		char c = cs.charAt(cs.length()-1);
		if(c == 'D' || c == 'C')
			return true;
		return false;
	}
	
	private void manageDataTypeDependingOnOperationType(OperandDescription desc1, OperandDescription desc2, CBaseEntityFactory factory) {
		CDataEntity firstObject = desc1.m_eObject ;
		if (desc1.m_expStart != null)
		{
			CEntityConvertReference conv = factory.NewEntityConvert(getLine());
			if (m_keyword == CFPacKeywordList.LE
					|| m_keyword == CFPacKeywordList.LT
					|| m_keyword == CFPacKeywordList.GE
					|| m_keyword == CFPacKeywordList.GT)
			{
				if (desc2.m_eObject.GetDataType() == CDataEntityType.STRING)
				{
					conv.convertToAlphaNum(desc1.m_eObject) ;
				}
				else if (desc2.m_eObject.GetDataType() == CDataEntityType.VAR)
				{
					conv.convertToAlphaNum(desc1.m_eObject) ;
				}
				else
				{
					if (desc2.m_bHexaNoPacked)
						conv.convertToAlphaNum(desc1.m_eObject) ;
					else
						conv.convertToPacked(desc1.m_eObject) ;
				}
			}
			else if (m_keyword == CFPacKeywordList.EQ || m_keyword == CFPacKeywordList.NE)
			{
				if (desc2.m_eObject.GetDataType() == CDataEntityType.NUMBER)
				{
					if (desc2.m_bHexaNoPacked)
						conv.convertToAlphaNum(desc1.m_eObject) ;
					else
						conv.convertToPacked(desc1.m_eObject) ;
				}
				else
				{
					conv.convertToAlphaNum(desc1.m_eObject) ;
				}
			}
			else
			{
				conv.convertToAlphaNum(desc1.m_eObject) ;
			}
			desc1.m_eObject = conv ;
		}
		if (desc2.m_expStart != null)
		{
			CEntityConvertReference conv = factory.NewEntityConvert(getLine());
			if (m_keyword == CFPacKeywordList.LE
					|| m_keyword == CFPacKeywordList.LT
					|| m_keyword == CFPacKeywordList.GE
					|| m_keyword == CFPacKeywordList.GT)
			{
				if (firstObject.GetDataType() == CDataEntityType.STRING)
				{
					conv.convertToAlphaNum(desc2.m_eObject) ;
				}
				else if (firstObject.GetDataType() == CDataEntityType.VAR)
				{
					conv.convertToAlphaNum(desc2.m_eObject) ;
				}
				else
				{
					conv.convertToPacked(desc2.m_eObject) ;
				}
			}
			else if (m_keyword == CFPacKeywordList.EQ || m_keyword == CFPacKeywordList.NE)
			{
				if (firstObject.GetDataType() == CDataEntityType.NUMBER)
				{
					conv.convertToPacked(desc2.m_eObject) ;
				}
				else
				{
					conv.convertToAlphaNum(desc2.m_eObject) ;
				}
			}
			else
			{
				conv.convertToAlphaNum(desc2.m_eObject) ;
			}
			desc2.m_eObject = conv ;
		}
	}

	private CDataEntity getDefaultInputFileBuffer(CObjectCatalog catalog)
	{
		NotifGetDefaultInputFile notif = new NotifGetDefaultInputFile() ;
		catalog.SendNotifRequest(notif) ;
		return notif.fileBuffer ;
	}

	private CDataEntity getDefaultOutputFileBuffer(CObjectCatalog catalog)
	{
		NotifGetDefaultOutputFile notif = new NotifGetDefaultOutputFile() ;
		catalog.SendNotifRequest(notif) ;
		return notif.fileBuffer ;
	}

	@Override
	public CBaseEntityCondition AnalyseCondition(CBaseEntityFactory factory, CDefaultConditionManager masterCond)
	{
		//  analyse operands
		OperandDescription desc1 = FindOperand(m_arrLeftTerms, factory) ;
		if (m_arrRightTerms == null || m_arrRightTerms.isEmpty())
		{
			return AnalyseSingleOperand(desc1, factory) ;
		}
		OperandDescription desc2 = FindOperand(m_arrRightTerms, factory) ;
		if (desc1 == null || desc2 == null)
		{
			return null ;
		}

		// manage buffer type
		manageDataTypeDependingOnOperationType(desc1, desc2, factory) ;
		
		// build data entities 
		CDataEntity e1, e2 ;
		if (desc2.m_expLength != null && desc1.m_expLength == null && desc1.m_expStart != null)
		{
			desc1.m_expLength = desc2.m_expLength ;
		}
		if (desc1.m_expLength != null && desc2.m_expLength == null && desc2.m_expStart != null)
		{
			desc2.m_expLength = desc1.m_expLength ;
		}
		if (desc1.m_expStart != null)
		{
			CSubStringAttributReference ss = factory.NewEntitySubString(getLine()) ;
			ss.SetReference(desc1.m_eObject, desc1.m_expStart, desc1.m_expLength) ;
			e1 = ss ;
		}
		else
		{
			e1 = desc1.m_eObject ;
		}
		if (desc2.m_expStart != null)
		{
			CSubStringAttributReference ss = factory.NewEntitySubString(getLine()) ;
			ss.SetReference(desc2.m_eObject, desc2.m_expStart, desc2.m_expLength) ;
			e2 = ss ;
		}
		else
		{
			e2 = desc2.m_eObject ;
		}
		CBaseEntityExpression exp1 = factory.NewEntityExprTerminal(e1) ;
		CBaseEntityExpression exp2 = factory.NewEntityExprTerminal(e2) ;
		
		// analyse keywords
		CBaseEntityCondition condition = null ;
		if (m_keyword == CFPacKeywordList.EQ)
		{
			CEntityCondEquals cond = factory.NewEntityCondEquals() ;
			cond.SetEqualCondition(exp1, exp2) ;
			condition = cond ;
		}
		else if (m_keyword == CFPacKeywordList.NE)
		{
			CEntityCondEquals cond = factory.NewEntityCondEquals() ;
			cond.SetDifferentCondition(exp1, exp2) ;
			condition = cond ;
		}
		else if (m_keyword == CFPacKeywordList.LE)
		{
			CEntityCondCompare cond = factory.NewEntityCondCompare() ;
			cond.SetLessOrEqualThan(exp1, exp2) ;
			condition = cond ;
		}
		else if (m_keyword == CFPacKeywordList.LT)
		{
			CEntityCondCompare cond = factory.NewEntityCondCompare() ;
			cond.SetLessThan(exp1, exp2) ;
			condition = cond ;
		}
		else if (m_keyword == CFPacKeywordList.GE)
		{
			CEntityCondCompare cond = factory.NewEntityCondCompare() ;
			cond.SetGreaterOrEqualsThan(exp1, exp2) ;
			condition = cond ;
		}
		else if (m_keyword == CFPacKeywordList.GT)
		{
			CEntityCondCompare cond = factory.NewEntityCondCompare() ;
			cond.SetGreaterThan(exp1, exp2) ;
			condition = cond ;
		}
		else
		{
			Transcoder.logError(getLine(), "Unexpecting keyword :  " + m_keyword.toString()) ;
			return null ;
		}
		exp1.RegisterVarTesting(condition) ;
		exp2.RegisterValueAccess(condition) ;
		return condition ;
	}

	/**
	 * @param desc1
	 * @param factory
	 * @return
	 */
	private CBaseEntityCondition AnalyseSingleOperand(OperandDescription desc1, CBaseEntityFactory factory)
	{
		CDataEntity e1 ;
		if (desc1.m_expStart != null)
		{
			CEntityConvertReference conv = factory.NewEntityConvert(getLine());
			conv.convertToAlphaNum(desc1.m_eObject) ;
			CSubStringAttributReference ss = factory.NewEntitySubString(getLine()) ;
			ss.SetReference(conv, desc1.m_expStart, desc1.m_expLength) ;
			e1 = ss ;
		}
		else
		{
			e1 = desc1.m_eObject ;
		}
		if (m_keyword == CFPacKeywordList.NUMERIC)
		{
			if (desc1.m_expLength == null)
			{
				desc1.m_expLength = factory.NewEntityExprTerminal(factory.NewEntityNumber(1)) ;
				CEntityConvertReference conv = factory.NewEntityConvert(getLine());
				conv.convertToAlphaNum(desc1.m_eObject) ;
				CSubStringAttributReference ss = factory.NewEntitySubString(getLine()) ;
				ss.SetReference(conv, desc1.m_expStart, desc1.m_expLength) ;
				e1 = ss;
			}
			CEntityCondIsKindOf eCond = factory.NewEntityCondIsKindOf() ;
			eCond.SetIsNumeric(e1);
			e1.RegisterVarTesting(eCond) ;
			return eCond;
		}
		else if (m_keyword == CFPacKeywordList.SPACE)
		{
			CEntityCondIsConstant eCond = factory.NewEntityCondIsConstant() ;
			eCond.SetIsSpace(e1);
			e1.RegisterVarTesting(eCond) ;
			return eCond;
		}
		else
		{
			Transcoder.logError(getLine(), "Unexpecting keyword : " + m_keyword.toString()) ;
			return null ;
		}
	}

	protected boolean CheckMembersBeforeExport()
	{
		return true;
	}
	
	@Override
	public Element DoExport(Document root)
	{
		Element eExp = root.createElement("Expression") ;
		eExp.setAttribute("Type", m_keyword.m_Name) ;
		Element eLeft = root.createElement("LeftTerms") ;
		eExp.appendChild(eLeft) ;
		for (CExpression exp : m_arrLeftTerms) 
		{
			CheckMembersBeforeExport();
			eLeft.appendChild(exp.DoExport(root)) ;
		}
		Element eRight = root.createElement("RightTerms") ;
		eExp.appendChild(eRight) ;
		for (CExpression exp : m_arrRightTerms) 
		{
			eRight.appendChild(exp.DoExport(root)) ;
		}
		return eExp ;
	}

	@Override
	public CExpression GetFirstConditionOperand()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CExpression GetSimilarExpression(CExpression operand)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean IsBinaryCondition()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public void AddTerm(CExpression exp)
	{
		if (m_keyword == null)
		{
			m_arrLeftTerms.add(exp) ;
		}
		else
		{
			m_arrRightTerms.add(exp);
		}
		
	}

	public void SetKeyword(CReservedKeyword keyword)
	{
		m_keyword = keyword ;
	}

	@Override
	public CExpression GetFirstCalculOperand()
	{
		return null;
	}

}
