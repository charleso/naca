/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package parser.FPac.elements;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.FPac.CFPacKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.FPac.CFPacElement;
import parser.expression.CExpression;
import parser.expression.CTerminal;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.Verbs.CEntityLoopIter;
import semantic.Verbs.CEntityLoopWhile;
import semantic.expression.CBaseEntityCondition;
import semantic.expression.CEntityCondCompare;
import utils.Transcoder;

public class CFPacDoLoop extends CFPacElement
{

	private CExpression m_expUntil;
	private CExpression m_expWhile ;
	private CFPacCodeBloc m_DoBloc;
	private CTerminal m_termNbLoops;

	public CFPacDoLoop(int line)
	{
		super(line);
	}

	@Override
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CFPacKeywordList.DO)
		{
			tok = GetNext() ;
		}
		
		if (tok.GetType() == CTokenType.MINUS)
		{ 
			tok = GetNext() ;
			if (tok.GetKeyword() == CFPacKeywordList.UNTIL)
			{
				tok = GetNext() ;
				m_expUntil = ReadCondition() ;
				if (m_expUntil == null)
				{
					return false ;
				}
				
				m_DoBloc = new CFPacCodeBloc(tok.getLine(), "") ;
				if (!Parse(m_DoBloc))
				{
					return false ;
				}
			}
			else if (tok.GetKeyword() == CFPacKeywordList.WHILE)
			{
				tok = GetNext() ;
				m_expWhile = ReadCondition() ;
				if (m_expWhile == null)
				{
					return false ;
				}
				
				m_DoBloc = new CFPacCodeBloc(tok.getLine(), "") ;
				if (!Parse(m_DoBloc))
				{
					return false ;
				}
			}
			else
			{
				m_termNbLoops = ReadTerminal() ;
				if (m_termNbLoops == null)
				{
					Transcoder.logError(tok.getLine(), "Expecting 'UNTIL' after DO- instead of token : "+tok.toString()) ;
					return false ;
				}
				m_DoBloc = new CFPacCodeBloc(tok.getLine(), "") ;
				if (!Parse(m_DoBloc))
				{
					return false ;
				}
			}
		}
		else
		{
			Transcoder.logError(tok.getLine(), "Expecting '-' after DO instead of token : "+tok.toString()) ;
			return false ;
		}
		
		tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CFPacKeywordList.DOEND)
		{
			tok = GetNext() ;
		}
		else
		{
			Transcoder.logError(tok.getLine(), "Expecting 'DOEND' after DO LOOP instead of token : "+tok.toString()) ;
			return false ;
		}
		return true ;
	}

	@Override
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		if (m_expUntil != null)
		{
			CBaseEntityCondition condUntil = m_expUntil.AnalyseCondition(factory);
			if (condUntil != null)
			{
				CEntityLoopWhile loop = factory.NewEntityLoopWhile(getLine()) ;
				loop.SetUntilCondition(condUntil) ;
				CBaseLanguageEntity bloc = m_DoBloc.DoSemanticAnalysis(loop, factory) ;
				parent.AddChild(loop) ;
				return loop ;
			}
		}
		else if (m_expWhile != null)
		{
			CBaseEntityCondition condWhile = m_expWhile.AnalyseCondition(factory);
			if (condWhile != null)
			{
				CEntityLoopWhile loop = factory.NewEntityLoopWhile(getLine()) ;
				loop.SetWhileCondition(condWhile) ;
				CBaseLanguageEntity bloc = m_DoBloc.DoSemanticAnalysis(loop, factory) ;
				parent.AddChild(loop) ;
				return loop ;
			}
		}
		else if (m_termNbLoops != null)
		{
			CDataEntity nbLoops = m_termNbLoops.GetDataEntity(getLine(), factory) ;
			CEntityLoopIter iter = factory.NewEntityLoopIter(getLine()) ;
			CBaseLanguageEntity bloc = m_DoBloc.DoSemanticAnalysis(iter, factory) ;
			CDataEntity index = factory.m_ProgramCatalog.GetDataEntity("INDEX", "") ;
			iter.SetLoopIterInc(index, factory.NewEntityNumber(0)) ;
			CEntityCondCompare comp = factory.NewEntityCondCompare() ;
			comp.SetLessThan(factory.NewEntityExprTerminal(index), factory.NewEntityExprTerminal(nbLoops)) ;
			iter.SetWhileCondition(comp, true) ;
			parent.AddChild(iter) ;
			return iter ;
		}
		return null;
	}

	@Override
	protected Element ExportCustom(Document root)
	{
		Element e = root.createElement("Do") ;
		if (m_expUntil != null)
		{
			Element eUntil = root.createElement("Until") ;
			e.appendChild(eUntil) ;
			eUntil.appendChild(m_expUntil.Export(root)) ;
		}
		if (m_expWhile != null)
		{
			Element eUntil = root.createElement("While") ;
			e.appendChild(eUntil) ;
			eUntil.appendChild(m_expWhile.Export(root)) ;
		}
		e.appendChild(m_DoBloc.Export(root)) ;
		return e ;
	}

}
