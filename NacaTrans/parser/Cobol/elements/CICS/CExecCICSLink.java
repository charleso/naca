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

import parser.CIdentifier;
import parser.Cobol.CCobolElement;
import parser.expression.CTerminal;
import semantic.CDataEntity;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CICS.CEntityCICSLink;
import semantic.Verbs.CEntityRoutineEmulation;
import semantic.Verbs.CEntityRoutineEmulationCall;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecCICSLink extends CCobolElement
{

	/**
	 * @param line
	 */
	public CExecCICSLink(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		boolean bChecked = false ;
		if (!m_ProgramName.IsReference())
		{ // reference is a constant string : 'PRGM'
			String prg = m_ProgramName.GetValue() ; 
			CEntityRoutineEmulation emul = factory.m_ProgramCatalog.getRoutineEmulation(prg) ;
			if (emul != null)
			{
				CEntityRoutineEmulationCall call = emul.NewCall(getLine(), factory) ;
				if (m_CommArea != null)
				{
					CDataEntity eCommArea = m_CommArea.GetDataReference(getLine(), factory) ;
					eCommArea.RegisterReadingAction(call) ;
					call.AddParameter(eCommArea) ;
				}
				parent.AddChild(call);
				return call ;
			}
			else
			{
				if (!factory.m_ProgramCatalog.CheckProgramReference(prg, true, 0, true))
				{
					//m_Logger.error("ERROR line "+getLine()+" : Missing referenced program : "+prg) ;
					CGlobalEntityCounter.GetInstance().CountCICSCommandOptions("Missed EXEC CICS LINK", prg) ;
					bChecked = false ;
				}
				else
				{
					bChecked = true ;
					//m_Logger.info("Referenced program found : "+prg) ;
				}
			}
		}
		else
		{
			//m_Logger.warn("Call use a variable to identify program") ;
		}
		CEntityCICSLink eCICS = factory.NewEntityCICSLink(getLine());
		parent.AddChild(eCICS);
		CDataEntity ePrgm = m_ProgramName.GetDataEntity(getLine(), factory) ; 
		eCICS.SetProgramName(ePrgm, bChecked) ;
		
		if (m_CommArea != null)
		{
			CDataEntity eCommArea = m_CommArea.GetDataReference(getLine(), factory) ;
			eCommArea.RegisterReadingAction(eCICS) ;
			CDataEntity eCALength = null ;
			CDataEntity eCADataLength = null ;
			if (m_CommAreaDataLength != null)
			{
				eCADataLength = m_CommAreaDataLength.GetDataEntity(getLine(), factory) ;
				eCADataLength.RegisterReadingAction(eCICS) ;
			}
			if (m_CommAreaLength != null)
			{
				eCALength = m_CommAreaLength.GetDataEntity(getLine(), factory);
				eCALength.RegisterReadingAction(eCICS) ;
			}
			eCICS.SetCommArea(eCommArea, eCALength, eCADataLength);
		}
		return eCICS;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.LINK)
		{
			tok = GetNext() ;
		}
		if (tok.GetKeyword() != CCobolKeywordList.PROGRAM)
		{
			return false ; 
		}
		tok = GetNext() ;
		if (tok.GetType() == CTokenType.LEFT_BRACKET)
		{
			tok = GetNext() ;
			m_ProgramName = ReadTerminal() ;
			//CGlobalEntityCounter.GetInstance().CountCICSCommandOptions("LINK", m_ProgramName.GetValue()) ;
			tok = GetNext() ;
			if (tok.GetType() == CTokenType.RIGHT_BRACKET)
			{
				tok = GetNext();
			}
		}
		
		if (tok.GetKeyword() == CCobolKeywordList.COMMAREA)
		{
			tok = GetNext();
			if (tok.GetType() == CTokenType.LEFT_BRACKET)
			{
				tok = GetNext() ;
				if (tok.GetType()== CTokenType.IDENTIFIER)
				{
					m_CommArea = ReadIdentifier();
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext();
					}
				}
			}
			if (tok.GetKeyword() == CCobolKeywordList.LENGTH)
			{
				tok = GetNext();
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext() ;
					m_CommAreaLength = ReadTerminal();
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext();
					}
				}
			}
			if (tok.GetKeyword() == CCobolKeywordList.DATALENGTH)
			{
				tok = GetNext();
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext() ;
					m_CommAreaLength = ReadTerminal();
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
			Transcoder.logError(getLine(), "Error while parsing EXEC CICS LINK");
			return false ;
		}
		StepNext() ;
		return true ;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element e = root.createElement("ExecCICSLink") ;
		Element ePrg = root.createElement("Program");
		m_ProgramName.ExportTo(ePrg, root) ;
		e.appendChild(ePrg) ;
		if (m_CommArea != null)
		{
			Element eCA = root.createElement("CommArea");
			e.appendChild(eCA);
			m_CommArea.ExportTo(eCA, root) ;
			if (m_CommAreaLength != null)
			{
				Element eL = root.createElement("Length");
				eCA.appendChild(eL) ;
				m_CommAreaLength.ExportTo(eL, root);
			}
			if (m_CommAreaDataLength != null)
			{
				Element eL = root.createElement("DataLength");
				eCA.appendChild(eL) ;
				m_CommAreaDataLength.ExportTo(eL, root);
			}
		}
		
		return e;
	}


	protected CTerminal m_ProgramName = null ;
	protected CIdentifier m_CommArea = null ;
	protected CTerminal m_CommAreaLength = null ;
	protected CTerminal m_CommAreaDataLength = null ;
	
}
