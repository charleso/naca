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

import java.util.ListIterator;
import java.util.Vector;

import jlib.misc.NumberParser;

import lexer.CReservedKeyword;

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
import semantic.Verbs.CEntityConvertReference;
import semantic.expression.CBaseEntityExpression;
import utils.FPacTranscoder.OperandDescription;

/**
 * @author S. Charton
 * @version $Id$
 */
public class CFPacConvert extends CFPacElement
{
	private Vector<CExpression> m_arrTerms ;
	private CReservedKeyword m_command ;
	
	/**
	 * @param line
	 * @param command 
	 * @param arrTerms 
	 */
	public CFPacConvert(int line, Vector<CExpression> arrTerms, CReservedKeyword command)
	{
		super(line);
		m_command = command ;
		m_arrTerms = arrTerms ;
	}

	/**
	 * @see parser.CBaseElement#ExportCustom(org.w3c.dom.Document)
	 */
	@Override
	protected Element ExportCustom(Document root)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see parser.FPac.CFPacElement#DoParsing()
	 */
	@Override
	protected boolean DoParsing()
	{
		return true;
	}

	/**
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	@Override
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		ListIterator<CExpression> iter = m_arrTerms.listIterator() ;
		OperandDescription desc1 = OperandDescription.FindFirstDataEntity(iter, factory) ;
		if (desc1.m_expStart != null)
		{
			CEntityConvertReference conv = factory.NewEntityConvert(getLine());
			conv.convertToAlphaNum(desc1.m_eObject) ;
			desc1.m_eObject = conv ;
		}

		OperandDescription desc2 = FindSecondOperand(iter, factory) ; 
		
		if (desc1.m_expLength == null && iter.hasNext())
		{
			CExpression exp = iter.next() ;
			CBaseEntityExpression term = exp.AnalyseExpression(factory) ;
			desc1.m_expLength = term ;
		}
		if (desc2.m_expLength == null)
		{
			if (iter.hasNext())
			{
				CExpression exp = iter.next() ;
				CBaseEntityExpression term = exp.AnalyseExpression(factory) ;
				desc2.m_expLength = term ;
			}
			else
			{
				desc2.m_expLength = desc1.m_expLength ;
			}
		}

		CDataEntity var1= null, var2 = null ;
		if (desc1.m_expStart != null)
		{
			CSubStringAttributReference e1 = factory.NewEntitySubString(getLine()) ;
			e1.SetReference(desc1.m_eObject, desc1.m_expStart, desc1.m_expLength) ;
			var1 = e1;
		}
		else
		{
			var1 = desc1.m_eObject ;
		}
		if (desc2.m_expStart != null)
		{
			CSubStringAttributReference e2 = factory.NewEntitySubString(getLine()) ;
			e2.SetReference(desc2.m_eObject, desc2.m_expStart, desc2.m_expLength) ;
			var2 = e2 ;
		}
		else
		{
			var2 = desc2.m_eObject ;
		}

		CEntityAssign conv = factory.NewEntityAssign(getLine()) ;
		var2.RegisterWritingAction(conv) ;
		var1.RegisterReadingAction(conv) ;
		conv.AddRefTo(var2) ;
		conv.SetValue(var1) ;
		parent.AddChild(conv) ;
		
		return conv ;
	}

	/**
	 * @param iter
	 * @param factory
	 * @return
	 */
	private OperandDescription FindSecondOperand(ListIterator<CExpression> iter, CBaseEntityFactory factory)
	{
		CExpression exp2 = iter.next() ;
		CBaseEntityExpression term2 = exp2.AnalyseExpression(factory) ;
		if (term2.GetDataType() == CDataEntityType.ADDRESS)
		{
			String cs = term2.GetConstantValue() ;
			int add = NumberParser.getAsInt(cs) ;
			OperandDescription desc = new OperandDescription() ;
			if (add < 5000)
			{ //file buffer 
				CDataEntity buffer = OperandDescription.getDefaultOutputFileBuffer(factory.m_ProgramCatalog) ;
				CEntityConvertReference conv = factory.NewEntityConvert(getLine()) ;
				conv.convertToPacked(buffer) ;
				desc.m_eObject = conv ;
				desc.m_expStart = term2 ;
				desc.m_expLength = null ;
			}
			else
			{ // working
				CDataEntity working = factory.m_ProgramCatalog.GetDataEntity("WORKING", "") ;
				CEntityConvertReference conv = factory.NewEntityConvert(getLine()) ;
				conv.convertToPacked(working) ;
				desc.m_eObject = conv ;
				desc.m_expStart = term2 ;
				desc.m_expLength = null ;
			}
			return desc ;
		}
		else if (term2.GetDataType() == CDataEntityType.VAR)
		{
			CDataEntity var = term2.GetSingleOperator() ;
			CExpression expstart = iter.next()  ;
			CBaseEntityExpression termstart = expstart.AnalyseExpression(factory) ;
			if (termstart.GetDataType() == CDataEntityType.ADDRESS)
			{
				OperandDescription desc = new OperandDescription() ;
				desc.m_eObject = var ;
				desc.m_expStart = termstart ;
				desc.m_expLength = null  ;
				return desc ;
			}
		}
		return null ;
	}

}
