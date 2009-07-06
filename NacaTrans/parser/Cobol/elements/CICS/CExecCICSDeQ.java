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

import parser.CIdentifier;
import parser.Cobol.CCobolElement;
import parser.expression.CTerminal;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.CICS.CEntityCICSDeQ;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecCICSDeQ extends CCobolElement
{

	/**
	 * @param line
	 */
	public CExecCICSDeQ(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityCICSDeQ eCICS = factory.NewEntityCICSDeQ(getLine()) ;
		parent.AddChild(eCICS);
		CDataEntity eRes = m_Resource.GetDataReference(getLine(), factory) ;
		CDataEntity eLen = null ;
		if (m_Lengh != null)
		{
			eLen = m_Lengh.GetDataEntity(getLine(), factory);
		}
		eCICS.SetResource(eRes, eLen);
		return eCICS ;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.DEQ)
		{
			tok = GetNext();
		}
		
		if (tok.GetKeyword() == CCobolKeywordList.RESOURCE)
		{
			tok = GetNext() ;
			if (tok.GetType() == CTokenType.LEFT_BRACKET)
			{
				tok = GetNext() ;
				m_Resource = ReadIdentifier() ;
				tok = GetCurrentToken() ;
				if (tok.GetType() == CTokenType.RIGHT_BRACKET)
				{
					tok = GetNext() ;
				}
			}
		}
		
		if (tok.GetKeyword() == CCobolKeywordList.LENGTH)
		{
			tok = GetNext() ;
			if (tok.GetType() == CTokenType.LEFT_BRACKET)
			{
				tok = GetNext() ;
				m_Lengh = ReadTerminal() ;
				tok = GetCurrentToken() ;
				if (tok.GetType() == CTokenType.RIGHT_BRACKET)
				{
					tok = GetNext() ;
				}
			}
		}
		
		if (tok.GetKeyword() != CCobolKeywordList.END_EXEC)
		{
			Transcoder.logError(getLine(), "Error while parsing EXEC CICS DEQ");
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
		Element e = root.createElement("ExecCICSDeQ") ;
		Element eRes = root.createElement("Resource") ;
		e.appendChild(eRes);
		m_Resource.ExportTo(eRes, root);
		if (m_Lengh != null)
		{
			Element eLen = root.createElement("Length") ;
			e.appendChild(eLen);
			m_Lengh.ExportTo(eLen, root);
		}
		return e;
	}

	protected CIdentifier m_Resource = null ; 
	protected CTerminal m_Lengh = null ;
}
