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
 * Created on 7 sept. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements.CICS;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.Cobol.CCobolElement;
import parser.expression.CTerminal;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CICS.CEntityCICSDelay;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecCICSDelay extends CCobolElement
{

	/**
	 * @param line
	 */
	public CExecCICSDelay(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityCICSDelay eCICS = factory.NewEntityCICSDelay(getLine()) ;
		parent.AddChild(eCICS);
		if (m_Interval != null)
		{
			eCICS.SetInterval(m_Interval.GetDataEntity(getLine(), factory)) ;
		}
		else if (m_Seconds != null)
		{
			eCICS.SetSeconds(m_Seconds.GetDataEntity(getLine(), factory));
		}
		return eCICS ;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.DELAY)
		{
			tok = GetNext();
		}
		
		if (tok.GetValue().equals("INTERVAL"))
		{
			tok = GetNext() ;
			if (tok.GetType() == CTokenType.LEFT_BRACKET)
			{
				tok = GetNext() ;
				m_Interval = ReadTerminal() ;
				tok = GetCurrentToken() ;
				if (tok.GetType() == CTokenType.RIGHT_BRACKET)
				{
					tok = GetNext() ;
				}
			}
		}
		
		if (tok.GetKeyword() == CCobolKeywordList.FOR)
		{
			tok = GetNext() ;
			if (tok.GetKeyword() == CCobolKeywordList.SECONDS)
			{
				tok = GetNext();
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok =GetNext();
					m_Seconds = ReadTerminal() ;
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext();
					} 
				}
			}
		}
				
		if (tok.GetKeyword() != CCobolKeywordList.END_EXEC)
		{
			Transcoder.logError(getLine(), "Error while parsing EXEC CICS DELAY");
			return false ;
		}
		StepNext();
		return true ;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element e = root.createElement("ExecCICSDelay") ;
		if (m_Interval != null)
		{
			Element eI = root.createElement("Interval") ;
			e.appendChild(eI);
			m_Interval.ExportTo(eI, root);
		}
		if (m_Seconds != null)
		{
			Element eI = root.createElement("Seconds") ;
			e.appendChild(eI);
			m_Seconds.ExportTo(eI, root);
		}
		return e;
	}

	protected CTerminal m_Interval = null ;
	protected CTerminal m_Seconds = null ;
}
