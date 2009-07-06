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

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.FPac.CFPacElement;
import parser.expression.CExpression;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.CSubStringAttributReference;
import semantic.CDataEntity.CDataEntityType;
import semantic.Verbs.CEntityAssign;
import semantic.Verbs.CEntityAssignWithAccessor;
import semantic.Verbs.CEntityConvertReference;
import semantic.expression.CBaseEntityExpression;
import utils.Transcoder;
import utils.FPacTranscoder.OperandDescription;

public class CFPacMove extends CFPacElement
{
	private Vector<CExpression> m_arrTerms ;
	private boolean m_bMoveToInput = false ;
	private boolean m_bMovefromOutput = false ;
	private boolean m_bMovePacked = false ;
	private boolean m_bUnpack = false ;
	
	public CFPacMove(int line, Vector<CExpression> arrTerms)
	{
		super(line);
		m_arrTerms = arrTerms ;
	}

	@Override
	protected boolean DoParsing()
	{
		return true;
	}

	@Override
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		if (m_arrTerms.size() == 1)
		{
			CExpression e = m_arrTerms.get(0) ;
			CBaseEntityExpression exp = e.AnalyseExpression(factory) ;
			CExpression op = e.GetFirstCalculOperand();
			if (op.IsReference())
			{
				CDataEntity var = op.GetReference(factory) ;
				if (var != null)
				{
					CEntityAssign ass = factory.NewEntityAssign(getLine()) ;
					ass.AddRefTo(var) ;
					var.RegisterWritingAction(ass) ;
					ass.SetValue(exp) ;
					exp.RegisterReadingAction(ass) ;
					parent.AddChild(ass) ;
					return ass ;					
				}
			}
			else if (op.IsConstant())
			{
			}
			return null ;
		}
		
		ListIterator<CExpression> iter = m_arrTerms.listIterator() ;

		OperandDescription op1 = OperandDescription.FindFirstDataEntity(iter, factory, m_bMovefromOutput) ;
		if (op1 == null || op1.m_eObject == null)
		{
			Transcoder.logError(getLine(), "Unexpecting entity") ;
			return null ;
		}
		
		OperandDescription op2 = OperandDescription.FindSecondDataEntity(iter, factory, m_bMoveToInput) ;
		if (op2 == null || op2.m_eObject == null)
		{
			Transcoder.logError(getLine(), "Unexpecting entity") ;
			return null ;
		}
		
		boolean bSpecialPacked = false;
		if (op1.m_expStart != null && op1.m_expLength == null && op2.m_expLength != null)
		{
			if (op2.m_expLength.GetDataType() == CDataEntityType.ADDRESS || op2.m_expLength.GetDataType() == CDataEntityType.NUMBER)
			{
				op1.m_expLength = op2.m_expLength ;
			}
			else
			{
				String cs = op2.m_expLength.GetConstantValue() ;
				if (!cs.equals(""))
				{
					int start = NumberParser.getAsInt(op1.m_expStart.GetConstantValue()) ; 
					if (start >= 6000 && start < 7000 && 
								(op2.m_expStart == null || NumberParser.getAsInt(op2.m_expStart.GetConstantValue()) < 6000 
												|| NumberParser.getAsInt(op2.m_expStart.GetConstantValue()) >= 7000))
					{ // in packed fields, number are stored 2 per byte 
						op1.m_expLength = factory.NewEntityExprTerminal(factory.NewEntityNumber(cs.length() / 2)) ;
						bSpecialPacked = true;
					}
					else
					{
						op1.m_expLength = factory.NewEntityExprTerminal(factory.NewEntityNumber(cs.length())) ;
					}
				}
			}
		}
		if (op2.m_expStart != null && op2.m_expLength == null && op1.m_expLength != null)
		{
			op2.m_expLength = op1.m_expLength ;
		}
		if (op2.m_expLength == null && op1.m_expLength == null)
		{
			if (op2.m_expStart != null)
			{
				int start2 = NumberParser.getAsInt(op2.m_expStart.GetConstantValue()) ;
				if (m_bMovePacked || (start2>=6000 && start2<7000))
					op2.m_expLength = factory.NewEntityExprTerminal(factory.NewEntityNumber(8)) ;
			}
			if (op1.m_expStart != null)
			{
				int start1 = NumberParser.getAsInt(op1.m_expStart.GetConstantValue()) ;
				if ((m_bMovePacked || (start1>=6000 && start1<7000)))
					op1.m_expLength = factory.NewEntityExprTerminal(factory.NewEntityNumber(8)) ;
			}
		}
		
		// manage type conversion
		if (op1.m_expStart != null)
		{
			CEntityConvertReference conv = factory.NewEntityConvert(getLine()) ;
			if (m_bMovePacked || bSpecialPacked)
				conv.convertToPacked(op1.m_eObject) ;
			else
				conv.convertToAlphaNum(op1.m_eObject) ;
			op1.m_eObject = conv ;
		}
		if (op2.m_expStart != null)
		{
			CEntityConvertReference conv = factory.NewEntityConvert(getLine()) ;
			if (m_bMovePacked)
				conv.convertToPacked(op2.m_eObject) ;
			else
				conv.convertToAlphaNum(op2.m_eObject) ;
			op2.m_eObject = conv ;
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
		// build instructions
		if (var2.HasAccessors())
		{
			CEntityAssignWithAccessor ass = factory.NewEntityAssignWithAccessor(getLine()) ;
			parent.AddChild(ass) ;
			var2.RegisterWritingAction(ass) ;
			var1.RegisterReadingAction(ass) ;
			ass.SetRefTo(var2) ;
			ass.SetValue(var1) ;
			return ass ;
		}
//		else if (m_bMovePacked)
//		{
//			CEntityAssignSpecial ass = factory.NewEntityAssignSpecial(getLine()) ;
//			ass.setDestination(var2) ;
//			var2.RegisterWritingAction(ass);
//			ass.setSource(var1) ;
//			var1.RegisterReadingAction(ass) ;
//			ass.setArithmeticAssign(true) ;
//			parent.AddChild(ass) ;
//			
//			return ass ;
//		}
		else
		{
			CEntityAssign ass = factory.NewEntityAssign(getLine()) ;
			var2.RegisterWritingAction(ass) ;
			var1.RegisterReadingAction(ass) ;
			ass.AddRefTo(var2) ;
			ass.SetValue(var1) ;
			parent.AddChild(ass) ;
			
			return ass ;
		}
	}


	@Override
	protected Element ExportCustom(Document root)
	{
		Element eAdd = root.createElement("Move") ;
		for (CExpression t : m_arrTerms)
		{
			Element e = root.createElement("Term") ;
			e.appendChild(t.Export(root)) ;
			eAdd.appendChild(e) ;
		}
		return eAdd ;
	}

	/**
	 * 
	 */
	public void moveToInput()
	{
		m_bMoveToInput  = true ;
	}

	/**
	 * 
	 */
	public void moveFromOutput()
	{
		m_bMovefromOutput  = true ;
	}

	/**
	 * 
	 */
	public void movePacked()
	{
		this.m_bMovePacked  = true ;
	}

	/**
	 * 
	 */
	public void unpack()
	{
		this.m_bUnpack  = true ; 
	}

}
