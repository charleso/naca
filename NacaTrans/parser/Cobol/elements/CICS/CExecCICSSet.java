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
import semantic.CICS.CEntityCICSSetTDQueue;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecCICSSet extends CCobolElement
{

	/**
	 * @param line
	 */
	public CExecCICSSet(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		if (m_TDQueue != null)
		{
			CEntityCICSSetTDQueue eCICS = factory.NewEntityCICSSetTDQueue(getLine()) ;
			parent.AddChild(eCICS);
			eCICS.SetQueue(m_TDQueue.GetDataEntity(getLine(), factory));
			if (m_bTDQueueOpen)
			{
				eCICS.SetOpen(true);
			}
			else if (m_bTDQueueClosed)
			{
				eCICS.SetOpen(false);
			}
			return eCICS ;
		}
		else if (m_DataSet != null)
		{
			Transcoder.logError(getLine(), "No Semantic Analysis for EXEC CICS SET DATASET") ;
			return null ;
		}
		else
		{
			Transcoder.logError(getLine(), "No Semantic Analysis for EXEC CICS SET") ;
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.SET)
		{
			tok = GetNext();
		}

		boolean bDone = false ;
		while (!bDone)
		{
			tok = GetCurrentToken() ;
			if (tok.GetValue().equals("TDQUEUE"))
			{
				CGlobalEntityCounter.GetInstance().CountCICSCommandOptions("SET", "TDQUEUE");
				tok = GetNext();
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext() ;
					m_TDQueue = ReadTerminal() ;
					tok = GetCurrentToken();
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext();
					}
				}
				if (tok.GetKeyword() == CCobolKeywordList.OPEN)
				{
					m_bTDQueueClosed = false ;
					m_bTDQueueOpen = true ;
					tok = GetNext();
				}
				else if (tok.GetKeyword() == CCobolKeywordList.CLOSED)
				{
					m_bTDQueueClosed = true ;
					m_bTDQueueOpen = false ;
					tok = GetNext();
				}
				else
				{
					Transcoder.logError(getLine(), "Error while parsing EXEC CICS SET TDQUEUE");
				}				
			}
			else if (tok.GetKeyword() == CCobolKeywordList.DATASET)
			{
				CGlobalEntityCounter.GetInstance().CountCICSCommandOptions("SET", "DATASET");
				tok = GetNext();
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext() ;
					m_DataSet = ReadTerminal() ;
					tok = GetCurrentToken();
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext();
					}
				}
				if (tok.GetKeyword() == CCobolKeywordList.OPEN)
				{
					m_bDataSetClosed = false ;
					m_bDataSetOpen = true ;
					m_bDataSetEnabled = false ;
					tok = GetNext();
				}
				else if (tok.GetKeyword() == CCobolKeywordList.CLOSED)
				{
					m_bDataSetClosed = true ;
					m_bDataSetOpen = false ;
					m_bDataSetEnabled = false ;
					tok = GetNext();
				}
				else if (tok.GetKeyword() == CCobolKeywordList.ENABLED)
				{
					m_bDataSetClosed = false ;
					m_bDataSetOpen = false ;
					m_bDataSetEnabled = true ;
					tok = GetNext();
				}
				else
				{
					Transcoder.logError(getLine(), "Error while parsing EXEC CICS SET DATASET");
				}				
			}
			else if (tok.GetValue().equals("TERMINAL"))
			{
				tok = GetNext();
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext() ;
					m_Terminal = ReadTerminal() ;
					tok = GetCurrentToken();
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext();
					}
				}
				if (tok.GetKeyword() == CCobolKeywordList.UCTRAN)
				{
					m_bTerminalUpperCase = true ;
					tok = GetNext();
				}
				else if (tok.GetKeyword() == CCobolKeywordList.NOUCTRAN)
				{
					m_bTerminalUpperCase = false ;
					tok = GetNext();
				}
				else
				{
					Transcoder.logError(getLine(), "Error whle parsing EXEC CICS SET TERMINAL");
				}				
			}
			else
			{
				bDone = true ;
			}
		}

		if (tok.GetKeyword() != CCobolKeywordList.END_EXEC)
		{
			Transcoder.logError(getLine(), "Error while parsing EXEC CICS SET");
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
		if (m_TDQueue != null)
		{
			Element e = root.createElement("ExecCICSSetTDQueue") ;
			m_TDQueue.ExportTo(e, root) ;
			if (m_bTDQueueClosed)
			{
				e.setAttribute("Option", "Closed");
			}
			else if (m_bTDQueueOpen)
			{
				e.setAttribute("Option", "Open");
			}
			return e;
		}
		else
		{
			Element e = root.createElement("ExecCICSSet") ;
			return e;
		}
	}
	
	// DataSet
	protected CTerminal m_DataSet = null ;
	protected boolean m_bDataSetOpen = false ; 
	protected boolean m_bDataSetClosed = false ; 
	protected boolean m_bDataSetEnabled = false ; 

	// Terminal
	protected CTerminal m_Terminal = null ;
	protected boolean m_bTerminalUpperCase = false ;
	
	// TDQueues	
	protected CTerminal m_TDQueue = null ;
	protected boolean m_bTDQueueClosed = false ;
	protected boolean m_bTDQueueOpen = false ;

}
