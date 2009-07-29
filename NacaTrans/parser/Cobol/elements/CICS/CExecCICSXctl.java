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
import semantic.CICS.CEntityCICSXctl;
import utils.CGlobalEntityCounter;
import utils.Transcoder;
import utils.modificationsReporter.Reporter;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecCICSXctl extends CCobolElement
{

	/**
	 * @param line
	 */
	public CExecCICSXctl(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		boolean bChecked = false ;
		if (!m_Program.IsReference())
		{ // reference is a constant string : 'PRGM'
			String prg = m_Program.GetValue() ; 
			if (!factory.m_ProgramCatalog.CheckProgramReference(prg, true, 0, false))
			{
				//m_Logger.error("ERROR line "+getLine()+" : Missing referenced program : "+prg) ;
				CGlobalEntityCounter.GetInstance().CountCICSCommandOptions("Missed EXEC CICS XCTL", prg) ;
				bChecked = false ;
			}
			else
			{
				//m_Logger.info("Referenced program found : "+prg) ;
				bChecked = true ;
			}
		}
		else
		{
			//m_Logger.warn("Call use a variable to identify program") ;
		}
		CEntityCICSXctl eCICS = factory.NewEntityCICSXctl(getLine());
		parent.AddChild(eCICS);
		CDataEntity ePrgm = m_Program.GetDataEntity(getLine(), factory) ; 
		eCICS.SetProgramName(ePrgm, bChecked) ;
		
		if (m_CommArea != null)
		{
			CDataEntity eCommArea = m_CommArea.GetDataReference(getLine(), factory) ;
			eCommArea.RegisterReadingAction(eCICS);
			CDataEntity eCALength = null ;
			if (m_CommAreaLength != null)
			{
				eCALength = m_CommAreaLength.GetDataEntity(getLine(), factory);
				eCALength.RegisterReadingAction(eCICS);
			}
			eCICS.SetCommArea(eCommArea, eCALength);
		}
		return eCICS;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.XCTL)
		{
			tok = GetNext() ;
		}
		
		if (tok.GetKeyword()== CCobolKeywordList.PROGRAM)
		{
			tok = GetNext();
			if (tok.GetType() == CTokenType.LEFT_BRACKET)
			{
				tok = GetNext();
				m_Program = ReadTerminal() ;
				//CGlobalEntityCounter.GetInstance().CountCICSCommandOptions("XCTL", m_Program.GetValue()) ;
				tok = GetCurrentToken() ;
				if (tok.GetType() == CTokenType.RIGHT_BRACKET)
				{
					tok = GetNext() ;
				}
			}
		}

		if (tok.GetKeyword() == CCobolKeywordList.COMMAREA)
		{
			tok = GetNext();
			if (tok.GetType() == CTokenType.LEFT_BRACKET)
			{
				tok = GetNext() ;
				m_CommArea = ReadIdentifier();
				tok = GetCurrentToken() ;
				if (tok.GetType() == CTokenType.RIGHT_BRACKET)
				{
					tok = GetNext();
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
		}
		else if (tok.GetKeyword() == CCobolKeywordList.LENGTH)	// PJD Added
		{
			Reporter.Add("Modif_PJ", "CExecCICSXctl LENGTH");
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
			if (tok.GetKeyword() == CCobolKeywordList.COMMAREA)
			{
				tok = GetNext();
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext() ;
					m_CommArea = ReadIdentifier();
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
			Transcoder.logError(getLine(), "Error while parsing EXEC CICS XCTL");
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
		Element e = root.createElement("ExecCICSXCTL") ;
		e.setAttribute("Program", m_Program.GetValue()) ;
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
		}
		return e;
	}

	protected CTerminal m_Program = null ;
	protected CIdentifier m_CommArea = null ;
	protected CTerminal m_CommAreaLength = null ;
}
