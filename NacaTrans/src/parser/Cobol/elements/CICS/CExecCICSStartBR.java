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
import semantic.CICS.CEntityCICSStartBrowse;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecCICSStartBR extends CCobolElement
{

	/**
	 * @param line
	 */
	public CExecCICSStartBR(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityCICSStartBrowse eSt = factory.NewEntityCICSStartBrowse(getLine()) ;
		parent.AddChild(eSt);
		if (m_DataSet != null)
		{
			eSt.BrowseDataSet(m_DataSet.GetDataEntity(getLine(), factory)); 
		}
		if (m_KeyLength != null)
		{
			eSt.SetKeyLength(m_KeyLength.GetDataEntity(getLine(), factory)); 
		}
		if (m_RecIDField != null)
		{
			eSt.SetRecIDField(m_RecIDField.GetDataReference(getLine(), factory)); 
		}
		if (m_bGTEQ)
		{
			eSt.SetGTEQ() ;
		}
		return eSt ;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.STARTBR)
		{
			tok = GetNext();
		}
		
		boolean bDone = false ;
		while (!bDone)
		{
			tok = GetCurrentToken() ;
			if (tok.GetKeyword() == CCobolKeywordList.DATASET)
			{
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext() ;
					m_DataSet = ReadTerminal() ;
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext() ;
					}
				}
			}
			else if (tok.GetKeyword() == CCobolKeywordList.KEYLENGTH)
			{
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext() ;
					m_KeyLength = ReadTerminal() ;
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext() ;
					}
				}
			}
			else if (tok.GetValue().equals("RIDFLD"))
			{
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext() ;
					m_RecIDField = ReadIdentifier() ;
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext() ;
					}
				}
			}
			else if (tok.GetValue().equals("GTEQ"))
			{
				m_bGTEQ = true ;
				tok = GetNext() ;
			}
			else 
			{
				bDone = true ;
			}
		}
		
		if (tok.GetKeyword() != CCobolKeywordList.END_EXEC)
		{
			Transcoder.logError(getLine(), "Error while parsing EXEC CICS STARBR");
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
		Element eCICS = root.createElement("ExecCICSStartBrowse") ;
		if (m_DataSet != null)
		{
			Element e = root.createElement("DataSet") ;
			eCICS.appendChild(e) ;
			m_DataSet.ExportTo(e, root); 
		}
		if (m_RecIDField != null)
		{
			Element e = root.createElement("RecIdField") ;
			eCICS.appendChild(e) ;
			m_RecIDField.ExportTo(e, root); 
		}
		if (m_bGTEQ)
		{
			eCICS.setAttribute("GTEQ", "true") ;
		}
		return eCICS;
	}


	protected CTerminal m_DataSet = null ;
	protected CTerminal m_KeyLength = null ;
	protected CIdentifier m_RecIDField = null ;
	protected boolean m_bGTEQ = false ;
}
