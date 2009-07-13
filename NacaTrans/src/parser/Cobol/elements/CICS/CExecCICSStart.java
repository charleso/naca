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
import semantic.CDataEntity;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CICS.CEntityCICSStart;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecCICSStart extends CCobolElement
{

	/**
	 * @param line
	 */
	public CExecCICSStart(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CDataEntity TID ;
		boolean bChecked = false ;
		if (m_TransID.IsReference())
		{
			TID = m_TransID.GetDataEntity(getLine(), factory);
			factory.m_ProgramCatalog.RegisterVariableTransID(TID) ;
		}
		else
		{
			String transID = m_TransID.GetValue() ;
			String programID = factory.m_ProgramCatalog.GetProgramForTransaction(transID);
			if (programID.equals(""))
			{
				TID = m_TransID.GetDataEntity(getLine(), factory);
				factory.m_ProgramCatalog.RegisterVariableTransID(TID) ;
			}
			else
			{
				TID = factory.NewEntityString(programID) ;
				if (factory.m_ProgramCatalog.CheckProgramReference(programID, true, 0, false))
				{
					bChecked = true ;
				}
			}
		}
		CEntityCICSStart start = factory.NewEntityCICSStart(getLine(), TID);
		TID.RegisterReadingAction(start) ;
		parent.AddChild(start);
				
		start.setVerified(bChecked) ;
		if (m_Interval != null)
		{
			CDataEntity inter = m_Interval.GetDataEntity(getLine(), factory); 
			start.SetInterval(inter) ;
		}
		else if (m_Time != null)
		{
			CDataEntity inter = m_Time.GetDataEntity(getLine(), factory); 
			start.SetTime(inter) ;
		}
		
		if (m_From != null)
		{
			CDataEntity dataFrom = m_From.GetDataEntity(getLine(), factory);
			CDataEntity dataLength = null ;
			if (m_Length != null)
			{
				dataLength = m_Length.GetDataEntity(getLine(), factory);
			}
			start.SetDataFrom(dataFrom, dataLength);
		}
		
		if (m_TermID != null)
		{
			CDataEntity term = m_TermID.GetDataEntity(getLine(), factory);
			start.SetTermID(term);
		}
		
		if (m_SysID != null)
		{
			CDataEntity sys = m_SysID.GetDataEntity(getLine(), factory);
			start.SetSysID(sys);
		}
		return start ;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.START)
		{
			tok = GetNext();
		}
		
		boolean bDone = false ;
		while (!bDone)
		{
			tok = GetCurrentToken() ;
			if (tok.GetKeyword() == CCobolKeywordList.TRANSID)
			{
				tok = GetNext();
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext() ;
					m_TransID = ReadTerminal() ;
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext();
					}
				}
			}
			else if (tok.GetValue().equals("TERMID"))
			{
				tok = GetNext();
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext() ;
					m_TermID = ReadTerminal() ;
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext();
					}
				}
			}
			else if (tok.GetValue().equals("SYSID"))
			{
				tok = GetNext();
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext() ;
					m_SysID = ReadTerminal() ;
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext();
					}
				}
			}
			else if (tok.GetValue().equals("INTERVAL"))
			{
				tok = GetNext();
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext() ;
					m_Interval = ReadTerminal() ;
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext();
					}
				}
			}
			else if (tok.GetKeyword() == CCobolKeywordList.FROM)
			{
				tok = GetNext();
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext() ;
					m_From = ReadTerminal() ;
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext();
					}
				}
			}
			else if (tok.GetKeyword() == CCobolKeywordList.TIME)
			{
				tok = GetNext();
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext() ;
					m_Time = ReadTerminal() ;
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext();
					}
				}
			}
			else if (tok.GetKeyword() == CCobolKeywordList.LENGTH)
			{
				tok = GetNext();
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext() ;
					m_Length = ReadTerminal() ;
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext();
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
			Transcoder.logError(getLine(), "Error while parsing EXEC CICS START");
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
		Element eSt = root.createElement("ExecCICSStart") ;
		if (m_TransID != null)
		{
			Element e = root.createElement("TransID");
			eSt.appendChild(e) ;
			m_TransID.ExportTo(e, root);
		}
		if (m_SysID != null)
		{
			Element e = root.createElement("SysID");
			eSt.appendChild(e) ;
			m_SysID.ExportTo(e, root);
		}
		if (m_TermID != null)
		{
			Element e = root.createElement("TermID");
			eSt.appendChild(e) ;
			m_TermID.ExportTo(e, root);
		}
		if (m_Interval != null)
		{
			Element e = root.createElement("Interval");
			eSt.appendChild(e) ;
			m_Interval.ExportTo(e, root);
		}
		if (m_From != null)
		{
			Element e = root.createElement("From");
			eSt.appendChild(e) ;
			m_From.ExportTo(e, root);
		}
		if (m_Length != null)
		{
			Element e = root.createElement("Length");
			eSt.appendChild(e) ;
			m_Length.ExportTo(e, root);
		}
		if (m_Time != null)
		{
			Element e = root.createElement("Time");
			eSt.appendChild(e) ;
			m_Time.ExportTo(e, root);
		}
		return eSt;
	}
	
	protected CTerminal m_TransID = null ;
	protected CTerminal m_Length = null ;
	protected CTerminal m_From = null ;
	protected CTerminal m_TermID = null ;
	protected CTerminal m_SysID = null ;
	protected CTerminal m_Interval = null ; 
	protected CTerminal m_Time = null ;
}
