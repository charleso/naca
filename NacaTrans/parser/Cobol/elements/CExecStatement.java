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
/*
 * Created on Jul 16, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.Cobol.CCobolElement;
import semantic.CBaseLanguageEntity;
import semantic.CBaseEntityFactory;
import semantic.Verbs.CEntityExec;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecStatement extends CCobolElement
{
	/**
	 * @param line
	 */
	public CExecStatement(int line) {
		super(line);
	}
	/* (non-Javadoc)
	 * @see parser.CLanguageElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tokNext = GetCurrentToken() ;
		if (tokNext.GetKeyword()!=CCobolKeywordList.EXEC)
		{
			tokNext = GetNext();
		}
		if(tokNext.GetKeyword() != null)	// PJD added
			CGlobalEntityCounter.GetInstance().CountCobolVerb(tokNext.GetKeyword().m_Name) ;
		boolean bDone = false ;
		while (!bDone)
		{
			tokNext = GetCurrentToken() ;
			if (tokNext.GetType()==CTokenType.KEYWORD && tokNext.GetKeyword()==CCobolKeywordList.EXEC)
			{
				CCobolElement eExec = new CExecStatement(tokNext.getLine()) ;
				AddChild(eExec) ;
				if (!Parse(eExec))
				{
					Transcoder.logError(getLine(), "Failure while parsing EXEC Statement") ;
					return false ;
				}
			}
			else if (tokNext.GetType()==CTokenType.KEYWORD && tokNext.GetKeyword()==CCobolKeywordList.END_EXEC)
			{
				GetNext();
				bDone = true ;
			}
			else
			{
				m_csSentence += tokNext.GetDisplay() ;
				GetNext();
			}
			setLine(tokNext.getLine());
		}
//		m_Logger.info("(" +getLine()+ ") EXEC CICS "+m_csSentence);
		return true;
	}
	/* (non-Javadoc)
	 * @see parser.CLanguageElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element eExec = root.createElement("Exec") ;
		eExec.setAttribute("Sentence", m_csSentence);
		return eExec;
	}
	String m_csSentence = "" ;
	/* (non-Javadoc)
	 * @see parser.CBaseElement#DoCustomSemanticAnalysis(semantic.CBaseSemanticEntity, semantic.CBaseSemanticEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityExec e = factory.NewEntityExec(getLine(), m_csSentence);
		parent.AddChild(e) ;
		return e ;
	}
	
}
