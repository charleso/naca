/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 13 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements;

import lexer.CBaseToken;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CIdentifier;
import parser.Cobol.CCobolElement;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.Verbs.CEntityReturn;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CStop extends CCobolElement
{

	/**
	 * @param line
	 */
	public CStop(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		if (m_Ref == null)
		{
			CEntityReturn eStop = factory.NewEntityReturn(getLine());
			eStop.SetStopProgram() ;
			parent.AddChild(eStop);
			return eStop ;
		}
		else
		{
			Transcoder.logError(getLine(), "No semantic analysis for STOP ID"); 
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken();
		if (tok.GetKeyword() != CCobolKeywordList.STOP)
		{
			return false ;
		}
		tok = GetNext();
		if (tok.GetKeyword() == CCobolKeywordList.RUN)
		{
			GetNext();
			CGlobalEntityCounter.GetInstance().CountCobolVerb("STOP_RUN") ;
			m_Ref = null;
		}
		else 
		{
			m_Ref = ReadIdentifier();
			CGlobalEntityCounter.GetInstance().CountCobolVerb("STOP_INPUT") ;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		if (m_Ref == null)
		{
			return root.createElement("StopRun");
		}
		else
		{
			Element e = root.createElement("StopInput");
			m_Ref.ExportTo(e, root);
			return e ;
		}
	}
	
	protected CIdentifier m_Ref = null ; // if NULL => STOP RUN ;
}
