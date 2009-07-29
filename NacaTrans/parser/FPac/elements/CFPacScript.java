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

import generate.CJavaFPacEntityFactory;
import generate.fpacjava.CFPacJavaClass;
import lexer.CBaseToken;
import lexer.FPac.CFPacKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.FPac.CFPacElement;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CEntityClass;
import utils.Transcoder;

public class CFPacScript extends CFPacElement 
{

	private String m_csName = "" ;
	private Vector<CFPacSubr> m_arrSubr;

	public CFPacScript(int line)
	{
		super(line);
	}
	
	@Override
	protected Element ExportCustom(Document root)
	{
		Element e = root.createElement("Script");
		return e ;
	}

	@Override
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityClass cl = factory.NewEntityClass(getLine(), m_csName) ;
		
		return cl ;
	}

	protected boolean DoParsing() 
	{
		CBaseToken tok = GetCurrentToken() ; // read comments before parsing declaration zone
		CFPacDeclarationZone zone = new CFPacDeclarationZone(0) ;
		if (!Parse(zone))
		{
			return false;
		}
		AddChild(zone) ;
		
		tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CFPacKeywordList.FIRST)
		{
			CFPacCodeBloc firstBloc = new CFPacCodeBloc(tok.getLine(), "First") ;
			tok = GetNext() ;
			if (!Parse(firstBloc))
			{
				return false ;
			}
			AddChild(firstBloc) ;
		}
		else
		{
			CFPacCodeBloc firstBloc = new CFPacCodeBloc(0, "First") ;
			AddChild(firstBloc) ;
		}
		
		tok = GetCurrentToken() ;
		int nNormalLine = 0;
		if (tok.GetKeyword() == CFPacKeywordList.NORMAL)
		{
			nNormalLine = tok.getLine() ;
			tok = GetNext() ;
		}
		CFPacCoreCodeBloc normBloc = new CFPacCoreCodeBloc(nNormalLine, "Normal") ;
		if (!Parse(normBloc))
		{
			return false ;
		}
		AddChild(normBloc) ;
	
		tok = GetCurrentToken() ;
		CFPacCodeBloc lastBloc ;
		if (tok.GetKeyword() == CFPacKeywordList.LAST)
		{
			lastBloc = new CFPacCodeBloc(tok.getLine(), "Last") ;
			tok = GetNext() ;
			if (!Parse(lastBloc))
			{
				return false ;
			}
			AddChild(lastBloc) ;
		}		
		else
		{
			lastBloc = new CFPacCodeBloc(0, "Last") ;
			AddChild(lastBloc) ;
		}

		tok = GetCurrentToken() ;
		while (tok != null && tok.GetKeyword() == CFPacKeywordList.SUBR)
		{
			if (m_arrSubr == null)
				m_arrSubr = new Vector<CFPacSubr>() ;
			CFPacSubr subr = new  CFPacSubr(tok.getLine()) ;
			if (!Parse(subr))
			{
				return false ;
			}
			AddChild(subr) ;
			tok = GetCurrentToken() ;
		}

		tok = GetCurrentToken() ;
		if (tok == null)
		{
			return true ;
		}
		else if (tok.GetKeyword() == CFPacKeywordList.END)
		{
			lastBloc.SetEndLine(tok.getLine()) ;
			StepNext() ;
			return true ;
		}
		else
		{
			Transcoder.logError(tok.getLine(), "Token not parsed : "+tok.toString()) ;
			return false ;
		}
	}

	public CFPacJavaClass DoSemanticAnalysis(CJavaFPacEntityFactory factory)
	{
		return (CFPacJavaClass)DoSemanticAnalysis(null, factory) ;
	}

	public void setName(String name)
	{
		m_csName = name ;
	};
	
}
