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
package utils.FPacTranscoder;

import java.util.ListIterator;

import jlib.misc.NumberParser;
import parser.expression.CExpression;
import semantic.CBaseEntityFactory;
import semantic.CDataEntity;
import semantic.CEntityFileBuffer;
import semantic.CDataEntity.CDataEntityType;
import semantic.expression.CBaseEntityExpression;
import semantic.expression.CEntityExprTerminal;
import semantic.expression.CEntityNumber;
import semantic.expression.CEntityString;
import utils.Transcoder;
import utils.CObjectCatalog;
import utils.FPacTranscoder.notifs.NotifGetDefaultInputFile;
import utils.FPacTranscoder.notifs.NotifGetDefaultOutputFile;

public class OperandDescription
{
	public CDataEntity m_eObject = null ;
	public CBaseEntityExpression m_expStart = null ; 
	public CBaseEntityExpression m_expLength = null ;
	public boolean m_bHexaNoPacked = false;
	
	public static OperandDescription FindFirstDataEntity(ListIterator<CExpression> iter, CBaseEntityFactory factory)
	{
		return FindFirstDataEntity(iter, factory, false) ;
	}
	public static OperandDescription FindFirstDataEntity(ListIterator<CExpression> iter, CBaseEntityFactory factory, boolean bFromOutput)
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
			else if (term.GetDataType() == CDataEntityType.STRING)
			{
				String val = term.GetConstantValue() ;
				CEntityString string = factory.NewEntityString(val) ;
				OperandDescription desc = new OperandDescription() ;
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
					CDataEntity buffer ;
					if (bFromOutput)
						buffer = getDefaultOutputFileBuffer(factory.m_ProgramCatalog) ;
					else
						buffer = getDefaultInputFileBuffer(factory.m_ProgramCatalog) ;
					OperandDescription desc = new OperandDescription() ;
					desc.m_eObject = buffer ;
					desc.m_expStart = term ;
					desc.m_expLength = null  ;
					return desc ;
				}
				else
				{ // working
					CDataEntity working = factory.m_ProgramCatalog.GetDataEntity("WORKING", "") ;
					OperandDescription desc = new OperandDescription() ;
					desc.m_eObject = working ;
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
						desc.m_eObject = var ;
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
			CExpression op = exp.GetFirstCalculOperand() ;
			CBaseEntityExpression term = exp.AnalyseExpression(factory) ;
			if (op.IsConstant())
			{
				String val = op.GetConstantValue() ;
				int add = NumberParser.getAsInt(val) ;
				if (add < 5000)
				{ //file buffer 
					CDataEntity buffer ;
					if (bFromOutput)
						buffer = getDefaultOutputFileBuffer(factory.m_ProgramCatalog) ;
					else
						buffer = getDefaultInputFileBuffer(factory.m_ProgramCatalog) ;
					OperandDescription desc = new OperandDescription() ;
					desc.m_eObject = buffer ;
					desc.m_expStart = term ;
					desc.m_expLength = null  ;
					return desc ;
				}
				else
				{ // working
					CDataEntity working = factory.m_ProgramCatalog.GetDataEntity("WORKING", "") ;
					OperandDescription desc = new OperandDescription() ;
					desc.m_eObject = working ;
					desc.m_expStart = term ;
					desc.m_expLength = null  ;
					return desc ;
				}
			}
			else
			{
				return null ;
			}
		}
	}

	public static OperandDescription FindSecondDataEntity(ListIterator<CExpression> iter, CBaseEntityFactory factory)
	{
		return FindSecondDataEntity(iter, factory, false) ;
	}

	public static OperandDescription FindSecondDataEntity(ListIterator<CExpression> iter, CBaseEntityFactory factory, boolean toInput)
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
				if (iter.hasNext())
				{
					CExpression explen = iter.next()  ;
					termlen = explen.AnalyseExpression(factory) ;
					if (termlen == null)
					{
						Transcoder.logError(exp.getLine(), "Unanalysed expression : "+explen.toString()) ; 
						return null ;
					}
					else if (termlen.GetDataType() == CDataEntityType.STRING)
					{
//						iter.previous() ;
//						String format = termlen.GetConstantValue() ;
//						termlen = factory.NewEntityExprTerminal(factory.NewEntityNumber(format.length())) ;
					}
					else if (termlen.GetDataType() != CDataEntityType.ADDRESS)
					{
						Transcoder.logError(exp.getLine(), "Unexpecting expression : "+termlen.toString()) ; 
					}
				}
				OperandDescription desc = new OperandDescription() ;
				if (add < 5000)
				{ //file buffer 
					CDataEntity buffer ;
					if (toInput)
						buffer = getDefaultInputFileBuffer(factory.m_ProgramCatalog) ;
					else 
						buffer = getDefaultOutputFileBuffer(factory.m_ProgramCatalog) ;
					desc.m_eObject = buffer ;
					desc.m_expStart = term ;
					desc.m_expLength = termlen  ;
				}
				else
				{ // working
					CDataEntity working = factory.m_ProgramCatalog.GetDataEntity("WORKING", "") ;
					desc.m_eObject = working ;
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
					if (iter.hasNext())
					{
						CExpression explen = iter.next()  ;
						CBaseEntityExpression termlen = explen.AnalyseExpression(factory) ;
						if (termlen.GetDataType() == CDataEntityType.ADDRESS)
						{
							OperandDescription desc = new OperandDescription() ;
							desc.m_eObject = var ;
							desc.m_expStart = termstart ;
							desc.m_expLength = termlen  ;
							return desc ;
						}
					}
					else
					{
						OperandDescription desc = new OperandDescription() ;
						desc.m_eObject = var ;
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
				desc.m_expLength = null ;
				if (iter.hasNext())
				{
					CExpression explen = iter.next()  ;
					CBaseEntityExpression termlen = explen.AnalyseExpression(factory) ;
					desc.m_expLength = termlen ;
				}
				return desc ;
			}
			return null ;
		}
		else
		{
			CExpression op = exp.GetFirstCalculOperand() ;
			CBaseEntityExpression term = exp.AnalyseExpression(factory) ;
			if (op.IsConstant())
			{
				String val = op.GetConstantValue() ;
				int add = NumberParser.getAsInt(val) ;
				CBaseEntityExpression termlen = null ;
				if (iter.hasNext())
				{
					CExpression explen = iter.next()  ;
					termlen = explen.AnalyseExpression(factory) ;
					if (termlen.GetDataType() != CDataEntityType.ADDRESS)
					{
						Transcoder.logError(exp.getLine(), "Unexpecting expression : "+termlen.toString()) ; 
					}
				}
				if (add < 5000)
				{ //file buffer 
					CDataEntity buffer = getDefaultOutputFileBuffer(factory.m_ProgramCatalog) ;
					OperandDescription desc = new OperandDescription() ;
					desc.m_eObject = buffer ;
					desc.m_expStart = term ;
					desc.m_expLength = termlen  ;
					return desc ;
				}
				else
				{ // working
					CDataEntity working = factory.m_ProgramCatalog.GetDataEntity("WORKING", "") ;
					OperandDescription desc = new OperandDescription() ;
					desc.m_eObject = working ;
					desc.m_expStart = term ;
					desc.m_expLength = termlen  ;
					return desc ;
				}
			}
			else
			{
				return null ;
			}
		}
	}

	public static CEntityFileBuffer getDefaultInputFileBuffer(CObjectCatalog catalog)
	{
		NotifGetDefaultInputFile notif = new NotifGetDefaultInputFile() ;
		catalog.SendNotifRequest(notif) ;
		return notif.fileBuffer ;
	}

	public static CEntityFileBuffer getDefaultOutputFileBuffer(CObjectCatalog catalog)
	{
		NotifGetDefaultOutputFile notif = new NotifGetDefaultOutputFile() ;
		catalog.SendNotifRequest(notif) ;
		return notif.fileBuffer ;
	}
	
}