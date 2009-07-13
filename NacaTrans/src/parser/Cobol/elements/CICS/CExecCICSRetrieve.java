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
import semantic.CDataEntity;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CICS.CEntityCICSRetrieve;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecCICSRetrieve extends CCobolElement
{

	/**
	 * @param line
	 */
	public CExecCICSRetrieve(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityCICSRetrieve eRetr = factory.NewEntityCICSRetreive(getLine(), m_bPointer);
		parent.AddChild(eRetr);
		CDataEntity ref = m_DataReference.GetDataReference(getLine(), factory);
		CDataEntity len = null;
		if (m_DataLength != null)
		{
			len = m_DataLength.GetDataReference(getLine(), factory);
			len.RegisterReadingAction(eRetr) ;
		}
		eRetr.SetRetrieve(ref, len);
		return eRetr ;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.RETRIEVE)
		{
			tok = GetNext();
		}
		
		if (tok.GetKeyword() == CCobolKeywordList.INTO)
		{
			m_bPointer = false ;
		}
		else if (tok.GetKeyword() == CCobolKeywordList.SET)
		{
			m_bPointer = true ;
		}
		else
		{
			Transcoder.logError(getLine(), "Unexpecting situation");
			return false ;
		}
		tok = GetNext() ;
		if (tok.GetType() == CTokenType.LEFT_BRACKET)
		{
			tok  = GetNext() ;
			m_DataReference = ReadIdentifier() ;
			tok = GetCurrentToken() ;
			if (tok.GetType() == CTokenType.RIGHT_BRACKET)
			{
				tok = GetNext();
			}
		}
		
		if (tok.GetKeyword() == CCobolKeywordList.LENGTH)
		{
			tok = GetNext() ;
			if (tok.GetType() == CTokenType.LEFT_BRACKET)
			{
				tok  = GetNext() ;
				m_DataLength = ReadIdentifier() ;
				tok = GetCurrentToken() ;
				if (tok.GetType() == CTokenType.RIGHT_BRACKET)
				{
					tok = GetNext();
				}
			}
		}
		if (tok.GetKeyword() != CCobolKeywordList.END_EXEC)
		{
			Transcoder.logError(getLine(), "Error while parsing EXEC CICS RETRIEVE");
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
		Element e = root.createElement("ExecCICSRetrieve") ;
		Element eData ;
		if (m_bPointer)
		{
			eData = root.createElement("SetPointer");
		}
		else
		{
			eData = root.createElement("IntoData");
		}
		e.appendChild(eData);
		m_DataReference.ExportTo(eData, root);
		if (m_DataLength != null)
		{
			Element eL = root.createElement("Length");
			eData.appendChild(eL);
			m_DataLength.ExportTo(eL, root);
		}
		return e;
	}
	
	protected CIdentifier m_DataReference = null ;
	protected CIdentifier m_DataLength = null ;
	protected boolean m_bPointer = false ;
}
