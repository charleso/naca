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
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CEntityBloc;
import semantic.CEntityCondition;
import semantic.CEntityFileBuffer;
import semantic.expression.CEntityIsFileEOF;
import utils.Transcoder;
import utils.FPacTranscoder.OperandDescription;

/**
 * @author S. Charton
 * @version $Id: CFPacAt.java,v 1.4 2007/06/28 16:33:58 u930bm Exp $
 */
public class CFPacAt extends CFPacElement
{

	/**
	 * @param line
	 */
	public CFPacAt(int line)
	{
		super(line);
	}

	/**
	 * @see parser.FPac.CFPacElement#DoParsing()
	 */
	@Override
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CFPacKeywordList.AT)
		{
			tok=GetNext() ;
		}
		
		if (tok.GetType() == CTokenType.MINUS)
		{
			tok = GetNext() ;
		}
		else
		{
			Transcoder.logError(getLine(), "Expecting '-' after AT") ;
			return false  ;
		}
		
		if (tok.GetKeyword() == CFPacKeywordList.EOF) 
		{
			tok = GetNext() ;
			m_atEofBloc = new CFPacCodeBloc(getLine(), "") ;
			if (!Parse(m_atEofBloc))
			{
				return false ;
			}
			tok  = GetCurrentToken() ;
			if (tok.GetKeyword() == CFPacKeywordList.ATEND)
			{
				tok = GetNext() ;
			}
		}
		return true ;
	}
	
	private CFPacCodeBloc m_atEofBloc = null ; 

	
	/**
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	@Override
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityCondition cond = factory.NewEntityCondition(getLine()) ;
		
		if (m_atEofBloc != null)
		{
			CEntityFileBuffer fb = OperandDescription.getDefaultInputFileBuffer(factory.m_ProgramCatalog) ;
			parent.AddChild(cond) ;
			
			CEntityIsFileEOF eof = factory.NewEntityIsFileEOF(fb.GetFileDescriptor()) ;
			CEntityBloc le = (CEntityBloc)m_atEofBloc.DoSemanticAnalysis(cond, factory) ;
			
			cond.SetCondition(eof, le, null) ;
		}
		parent.AddChild(cond) ;
		return cond;
	}

	/**
	 * @see parser.CBaseElement#ExportCustom(org.w3c.dom.Document)
	 */
	@Override
	protected Element ExportCustom(Document root)
	{
		Element e = root.createElement("AtEnd") ;
		if (m_atEofBloc != null)
		{
			Element bloc = m_atEofBloc.Export(root) ;
			e.appendChild(bloc) ;			
		}
		return e; 
	}

}
