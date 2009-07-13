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
import semantic.CICS.CEntityCICSAddress;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecCICSAddress extends CCobolElement
{

	/**
	 * @param line
	 */
	public CExecCICSAddress(int line)
	{
		super(line);
	}
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityCICSAddress eCICS = factory.NewEntityCICSAddress(getLine());
		parent.AddChild(eCICS);
		if (m_RefCWA != null)
		{ 
			CDataEntity e = m_RefCWA.GetDataReference(getLine(), factory);
			eCICS.SetRefForCWA(e) ;
		}
		if (m_RefTCTUA != null)
		{ 
			CDataEntity e = m_RefTCTUA.GetDataReference(getLine(), factory);
			eCICS.SetRefForTCTUA(e) ;
		}
		if (m_RefTWA != null)
		{ 
			CDataEntity e = m_RefTWA.GetDataReference(getLine(), factory);
			eCICS.SetRefForTWA(e) ;
		}
		return eCICS;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.ADDRESS)
		{
			tok = GetNext();
		}
		
		boolean bDone = false ;
		while (!bDone)
		{
			tok = GetCurrentToken() ;
			if (tok.GetValue().equals("TCTUA"))
			{
				CGlobalEntityCounter.GetInstance().CountCICSCommandOptions("ADDRESS", "TCTUA") ;
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext();
					m_RefTCTUA = ReadIdentifier() ;
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext() ;
					} 
				}
			}
			else if (tok.GetValue().equals("TWA"))
			{
				CGlobalEntityCounter.GetInstance().CountCICSCommandOptions("ADDRESS", "TWA") ;
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext();
					m_RefTWA = ReadIdentifier() ;
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext() ;
					} 
				}
			}
			else if (tok.GetValue().equals("CWA"))
			{
				CGlobalEntityCounter.GetInstance().CountCICSCommandOptions("ADDRESS", "CWA") ;
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext();
					m_RefCWA = ReadIdentifier() ;
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext() ;
					} 
				}
			}
			else 
			{
				bDone = true ;
			}
		}
		
		if (tok.GetKeyword() != CCobolKeywordList.END_EXEC)
		{
			Transcoder.logError(getLine(), "Error while parsing EXEC CICS ADDRESS");
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
		Element eAdd = root.createElement("ExecCICSAddress") ;
		if (m_RefTCTUA != null)
		{
			Element e = root.createElement("TCTUA");
			eAdd.appendChild(e);
			m_RefTCTUA.ExportTo(e, root);
		}
		if (m_RefTWA != null)
		{
			Element e = root.createElement("TWA");
			eAdd.appendChild(e);
			m_RefTWA.ExportTo(e, root);
		}
		if (m_RefCWA != null)
		{
			Element e = root.createElement("CWA");
			eAdd.appendChild(e);
			m_RefCWA.ExportTo(e, root);
		}
		return eAdd;
	}

	protected CIdentifier m_RefTCTUA = null ;
	protected CIdentifier m_RefTWA = null ;
	protected CIdentifier m_RefCWA = null ;
}
