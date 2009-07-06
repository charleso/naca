/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package parser.FPac.elements;

import java.util.ListIterator;
import java.util.Vector;

import jlib.misc.NumberParser;
import lexer.CReservedKeyword;
import lexer.FPac.CFPacKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.FPac.CFPacElement;
import parser.expression.CExpression;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.CSubStringAttributReference;
import semantic.CDataEntity.CDataEntityType;
import semantic.Verbs.CEntityAddTo;
import semantic.Verbs.CEntityAssign;
import semantic.Verbs.CEntityConvertReference;
import semantic.Verbs.CEntityDivide;
import semantic.Verbs.CEntityMultiply;
import semantic.Verbs.CEntitySubtractTo;
import semantic.expression.CBaseEntityExpression;
import semantic.expression.CEntityExprTerminal;
import semantic.expression.CEntityNumber;
import utils.NacaTransAssertException;
import utils.Transcoder;
import utils.FPacTranscoder.OperandDescription;

public class CFPacArithmeticOperation extends CFPacElement
{
	private Vector<CExpression> m_arrExp ;
	private CReservedKeyword m_command ;
	
	public CFPacArithmeticOperation(int line, Vector<CExpression> arrTerms, CReservedKeyword command)
	{
		super(line);
		m_arrExp = arrTerms ;
		m_command = command ;
	}

	@Override
	protected boolean DoParsing()
	{
		return true;
	}

	@Override
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		boolean bFirstPacked = false, bSecondPacked = false ;
		if (m_command == CFPacKeywordList.A || m_command == CFPacKeywordList.S || m_command == CFPacKeywordList.M || m_command == CFPacKeywordList.D)
		{
			bFirstPacked = true ;
			bSecondPacked = true ;
		}
		else if (m_command == CFPacKeywordList.U)
		{
			bFirstPacked = true ;
		}

		ListIterator<CExpression> iter = m_arrExp.listIterator() ;

		OperandDescription op1 = findFirstDataEntity(iter, factory, bFirstPacked) ;
		if (op1 == null || op1.m_eObject == null)
		{
			Transcoder.logError(getLine(), "Unexpecting entity") ;
			return null ;
		}
		
		OperandDescription op2 = findSecondDataEntity(iter, factory, bSecondPacked) ;
		if (op2 == null || op2.m_eObject == null)
		{
			Transcoder.logError(getLine(), "Unexpecting entity.") ;
			return null ;
		}
		// manage length and start
		if (op1.m_expStart != null && op1.m_expLength == null)
		{
			if (iter.hasNext())
			{
				CExpression explen = iter.next()  ;
				CBaseEntityExpression termlen = explen.AnalyseExpression(factory) ;
				op1.m_expLength = termlen ;
			}
			else if (op2.m_expLength != null)
			{
				if (op2.m_expLength.GetDataType() == CDataEntityType.NUMBER)
				{
					op1.m_expLength = op2.m_expLength ;
				}
				else
				{
					String cs = op2.m_expLength.GetConstantValue() ;
					if (!cs.equals(""))
					{
						op1.m_expLength = factory.NewEntityExprTerminal(factory.NewEntityNumber(cs.length())) ;
					}
				}
			}
		}
		if (op2.m_expStart != null && op2.m_expLength == null)
		{
			if (iter.hasNext())
			{
				CExpression explen = iter.next()  ;
				CBaseEntityExpression termlen = explen.AnalyseExpression(factory) ;
				op2.m_expLength = termlen ;
			}
			else if (op1.m_expLength != null)
			{
				op2.m_expLength = op1.m_expLength ;
			}
		}
		if (op2.m_expLength == null && op1.m_expLength == null)
		{
			if (op2.m_expStart != null)
			{
				int start2 = NumberParser.getAsInt(op2.m_expStart.GetConstantValue()) ;
				if (start2>=6000 && start2<7000)
					op2.m_expLength = factory.NewEntityExprTerminal(factory.NewEntityNumber(8)) ;
			}
			if (op1.m_expStart != null)
			{
				int start1 = NumberParser.getAsInt(op1.m_expStart.GetConstantValue()) ;
				if (start1>=6000 && start1<7000)
					op1.m_expLength = factory.NewEntityExprTerminal(factory.NewEntityNumber(8)) ;
			}
		}
		
		// build variables
		CDataEntity var1= null, var2 = null ;
		if (op1.m_expStart != null)
		{
			CSubStringAttributReference e1 = factory.NewEntitySubString(getLine()) ;
			e1.SetReference(op1.m_eObject, op1.m_expStart, op1.m_expLength) ;
			var1 = e1;
		}
		else
		{
			var1 = op1.m_eObject ;
		}
		if (op2.m_expStart != null)
		{
			CSubStringAttributReference e2 = factory.NewEntitySubString(getLine()) ;
			e2.SetReference(op2.m_eObject, op2.m_expStart, op2.m_expLength) ;
			var2 = e2 ;
		}
		else
		{
			var2 = op2.m_eObject ;
		}
		
		if (m_command == CFPacKeywordList.A)
		{
			CEntityAddTo add = factory.NewEntityAddTo(getLine()) ;
			var1.RegisterReadingAction(add) ;
			var2.RegisterWritingAction(add) ;
			add.SetAddValue(var1) ;
			add.SetAddDest(var2) ;
			parent.AddChild(add) ;
			return add ;
		}
		else if (m_command == CFPacKeywordList.U)
		{
			CEntityAssign add = factory.NewEntityAssign(getLine()) ;
			var2.RegisterWritingAction(add) ;
			var1.RegisterReadingAction(add) ;
			add.AddRefTo(var2) ;
			add.SetValue(var1) ;
			parent.AddChild(add) ;
			return add ;
		}
		else if (m_command == CFPacKeywordList.M)
		{
			CEntityMultiply mult = factory.NewEntityMultiply(getLine()) ;
			var2.RegisterWritingAction(mult) ;
			var1.RegisterReadingAction(mult) ;
			mult.SetMultiply(var1, var2, false) ;
			parent.AddChild(mult) ;
			return mult ;
		}
		else if (m_command == CFPacKeywordList.D)
		{
			CEntityDivide divide = factory.NewEntityDivide(getLine()) ;
			var2.RegisterWritingAction(divide) ;
			var1.RegisterReadingAction(divide) ;
			divide.SetDivide(var2, var1, false) ;
			parent.AddChild(divide) ;
			return divide ;
		}
		else if (m_command == CFPacKeywordList.S)
		{
			CEntitySubtractTo subtract = factory.NewEntitySubtractTo(getLine()) ;
			var1.RegisterReadingAction(subtract) ;
			var2.RegisterWritingAction(subtract) ;
			subtract.SetSubstract(var2, var1, var2);
			parent.AddChild(subtract) ;
			return subtract ;
		}
		else
		{
			throw new NacaTransAssertException("Arithmetic operation not managed : "+m_command) ;
		}
	}

	/**
	 * @param iter
	 * @param factory
	 * @param secondPacked 
	 * @return
	 */
	private OperandDescription findSecondDataEntity(ListIterator<CExpression> iter, CBaseEntityFactory factory, boolean secondPacked)
	{
		CExpression exp = iter.next()  ;
		if (exp.IsConstant() || exp.IsReference())
		{
			CBaseEntityExpression term = exp.AnalyseExpression(factory) ;
			if (term.GetDataType() == CDataEntityType.ADDRESS)
			{
				String val = term.GetConstantValue() ;
				int add = NumberParser.getAsInt(val) ;
				
				CBaseEntityExpression termlen = null ;
				OperandDescription desc = new OperandDescription() ;
				if (add < 5000)
				{ //file buffer 
					CDataEntity buffer = OperandDescription.getDefaultOutputFileBuffer(factory.m_ProgramCatalog) ;
					CEntityConvertReference conv = factory.NewEntityConvert(getLine()) ;
					if (secondPacked)
						conv.convertToPacked(buffer) ;
					else
						conv.convertToAlphaNum(buffer) ;
					desc.m_eObject = conv ;
					desc.m_expStart = term ;
					desc.m_expLength = termlen  ;
				}
				else
				{ // working
					CDataEntity working = factory.m_ProgramCatalog.GetDataEntity("WORKING", "") ;
					CEntityConvertReference conv = factory.NewEntityConvert(getLine()) ;
					if (secondPacked)
						conv.convertToPacked(working) ;
					else
						conv.convertToAlphaNum(working) ;
					desc.m_eObject = conv ;
					desc.m_expStart = term ;
					desc.m_expLength = termlen  ;
				}
				return desc ;
			}
			else if (term.GetDataType() == CDataEntityType.VAR)
			{
				CDataEntity var = term.GetSingleOperator() ;
				CExpression expstart = iter.next()  ;
				CBaseEntityExpression termstart = expstart.AnalyseExpression(factory) ;
				if (termstart.GetDataType() == CDataEntityType.ADDRESS)
				{
					OperandDescription desc = new OperandDescription() ;
					CEntityConvertReference conv = factory.NewEntityConvert(getLine()) ;
					if (secondPacked)
						conv.convertToPacked(var) ;
					else
						conv.convertToAlphaNum(var) ;
					desc.m_eObject = conv ;
					desc.m_expStart = termstart ;
					desc.m_expLength = null  ;
					return desc ;
				}
			}
			else if (term.GetDataType() == CDataEntityType.NUMERIC_VAR)
			{
				CDataEntity var = term.GetSingleOperator() ;
				OperandDescription desc = new OperandDescription() ;
				desc.m_eObject = var ;
				desc.m_expStart = null ;
				desc.m_expLength = null ;
				return desc ;
			}
			return null ;
		}
		else
		{
			{
				return null ;
			}
		}
	}

	/**
	 * @param iter
	 * @param factory
	 * @param firstPacked 
	 * @return
	 */
	private OperandDescription findFirstDataEntity(ListIterator<CExpression> iter, CBaseEntityFactory factory, boolean firstPacked)
	{
		CExpression exp = iter.next()  ;
		if (exp.IsConstant() || exp.IsReference())
		{
			CEntityExprTerminal term = (CEntityExprTerminal)exp.AnalyseExpression(factory) ;
			if (term.GetDataType() == CDataEntityType.NUMBER)
			{
				String val = term.GetConstantValue() ;
				CEntityNumber number = factory.NewEntityNumber(val) ;
				OperandDescription desc = new OperandDescription() ;
				desc.m_eObject = number ;
				desc.m_expStart = null ;
				desc.m_expLength = null ; //factory.NewEntityExprTerminal(factory.NewEntityNumber(val.length()))  ;
				return desc ;
			}
			else if (term.GetDataType() == CDataEntityType.ADDRESS)
			{
				String val = term.GetConstantValue() ;
				int add = NumberParser.getAsInt(val) ;
				
				if (add < 5000)
				{ //file buffer 
					CDataEntity buffer = OperandDescription.getDefaultInputFileBuffer(factory.m_ProgramCatalog) ;
					OperandDescription desc = new OperandDescription() ;
					CEntityConvertReference conv = factory.NewEntityConvert(getLine()) ;
					if (firstPacked)
						conv.convertToPacked(buffer) ;
					else
						conv.convertToAlphaNum(buffer) ;
					desc.m_eObject = conv ;
					desc.m_expStart = term ;
					desc.m_expLength = null  ;
					return desc ;
				}
				else
				{ // working
					CDataEntity working = factory.m_ProgramCatalog.GetDataEntity("WORKING", "") ;
					OperandDescription desc = new OperandDescription() ;
					CEntityConvertReference conv = factory.NewEntityConvert(getLine()) ;
					if (firstPacked)
						conv.convertToPacked(working) ;
					else
						conv.convertToAlphaNum(working) ;
					desc.m_eObject = conv ;
					desc.m_expStart = term ;
					desc.m_expLength = null  ;
					return desc ;
				}
			}
			else if (term.GetDataType() == CDataEntityType.VAR)
			{
				CDataEntity var = term.GetSingleOperator() ;
				CExpression expstart = iter.next()  ;
				CBaseEntityExpression termstart = expstart.AnalyseExpression(factory) ;
				if (termstart.GetDataType() == CDataEntityType.ADDRESS)
				{
					String vallen = termstart.GetConstantValue() ;
					if (!vallen.equals(""))
					{
						OperandDescription desc = new OperandDescription() ;
						CEntityConvertReference conv = factory.NewEntityConvert(getLine()) ;
						if (firstPacked)
							conv.convertToPacked(var) ;
						else
							conv.convertToAlphaNum(var) ;
						desc.m_eObject = conv ;
						desc.m_expStart = termstart ;
						desc.m_expLength = null  ;
						return desc ;
					}
				}
			}
			else if (term.GetDataType() == CDataEntityType.NUMERIC_VAR)
			{
				CDataEntity var = term.GetSingleOperator() ;
				OperandDescription desc = new OperandDescription() ;
				desc.m_eObject = var ;
				desc.m_expStart = null ;
				desc.m_expLength = null  ;
				return desc ;
			}
			return null ;
		}
		else
		{
//			CExpression op = exp.GetFirstCalculOperand() ;
//			CBaseEntityExpression term = exp.AnalyseExpression(factory) ;
//			if (op.IsConstant())
//			{
//				String val = op.GetConstantValue() ;
//				int add = NumberParser.getAsInt(val) ;
//				if (add < 5000)
//				{ //file buffer 
//					CDataEntity buffer ;
//					if (bFromOutput)
//						buffer = getDefaultOutputFileBuffer(factory.m_ProgramCatalog) ;
//					else
//						buffer = getDefaultInputFileBuffer(factory.m_ProgramCatalog) ;
//					OperandDescription desc = new OperandDescription() ;
//					desc.m_eObject = buffer ;
//					desc.m_expStart = term ;
//					desc.m_expLength = null  ;
//					return desc ;
//				}
//				else
//				{ // working
//					CDataEntity working = factory.m_ProgramCatalog.GetDataEntity("WORKING", "") ;
//					OperandDescription desc = new OperandDescription() ;
//					desc.m_eObject = working ;
//					desc.m_expStart = term ;
//					desc.m_expLength = null  ;
//					return desc ;
//				}
//			}
//			else
			{
				return null ;
			}
		}
	}

	@Override
	protected Element ExportCustom(Document root)
	{
		Element eAdd = root.createElement("Add") ;
		for (CExpression t : m_arrExp)
		{
			Element e = root.createElement("Exp") ;
			e.appendChild(t.Export(root)) ;
			eAdd.appendChild(e) ;
		}
		return eAdd ;
	}

}
