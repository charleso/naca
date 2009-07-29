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
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.CICS.CEntityCICSSendMap;
import utils.CGlobalEntityCounter;
import utils.Transcoder;
import utils.modificationsReporter.Reporter;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecCICSSend extends CCobolElement
{

	protected static class CCICSSendType
	{
		protected CCICSSendType(String cs)
		{
			m_Name = cs ;
		}
		public String m_Name = "" ;
		public static CCICSSendType MAP = new CCICSSendType("MAP");
		public static CCICSSendType SEND = new CCICSSendType("SEND");
		public static CCICSSendType PAGE = new CCICSSendType("PAGE");
		public static CCICSSendType CONTROL = new CCICSSendType("CONTROL");
	}
	/**
	 * @param line
	 */
	public CExecCICSSend(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		if (m_SendType == CCICSSendType.MAP)
		{
			CEntityCICSSendMap send = factory.NewEntityCICSSendMap(getLine());
			parent.AddChild(send);
			factory.m_ProgramCatalog.RegisterMapSend(send) ;
			
			CDataEntity name = m_MapName.GetDataEntity(getLine(), factory);
			send.SetName(name);
			name.RegisterReadingAction(send);
			
			if (m_MapSetName != null)
			{
				CDataEntity msname = m_MapSetName.GetDataEntity(getLine(), factory);
				send.SetMapSet(msname);
				msname.RegisterReadingAction(send);
			}
			if (m_MapFrom != null)
			{
				CDataEntity from = m_MapFrom.GetDataReference(getLine(), factory) ;
				from.RegisterReadingAction(send);
				CDataEntity len = null ;
				if (m_MapLength != null)
				{
					len = m_MapLength.GetDataEntity(getLine(), factory);
					len.RegisterReadingAction(send);
				}
				send.SetDataFrom(from, len, m_bMapDataOnly);
			}
			send.SetAccum(m_bMapAccum);
			send.SetAlarm(m_bMapAlarm);
			send.SetErase(m_bMapErase);
			send.SetFreeKB(m_bMapFreeKB);
			send.SetPaging(m_bMapPaging);
			send.SetWait(m_bMapWait);
			if (m_bMapCursor)
			{
				if (m_MapCursorValue != null)
				{
					CDataEntity cur = m_MapCursorValue.GetDataEntity(getLine(), factory); 
					send.SetCursor(cur) ;
					cur.RegisterReadingAction(send) ;
				}
				else
				{
					send.SetCursor(null);
				}
			}
			
			return send ;
		}
		else
		{
			Transcoder.logError(getLine(), "No Semantic Analysis for EXEC CICS SEND") ;
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.SEND)
		{
			tok = GetNext();
		}
		
		boolean bRet = true ;
		if (tok.GetValue().equals("MAP")) // MAP can't be defiend as keyword....
		{
			CGlobalEntityCounter.GetInstance().CountCICSCommandOptions("SEND", "MAP") ;
			bRet = ParseSendMap();
		}
		else if (tok.GetKeyword() == CCobolKeywordList.CONTROL)
		{
			CGlobalEntityCounter.GetInstance().CountCICSCommandOptions("SEND", "CONTROL") ;
			bRet = ParseSendControl();
		}
		else if (tok.GetKeyword() == CCobolKeywordList.FROM)
		{
			CGlobalEntityCounter.GetInstance().CountCICSCommandOptions("SEND", "SEND") ;
			bRet = ParseSend();
		}
		else if (tok.GetKeyword() == CCobolKeywordList.LENGTH)	// PJD Added
		{
			Reporter.Add("Modif_PJ", "CExecCICSSend LENGTH");
			CGlobalEntityCounter.GetInstance().CountCICSCommandOptions("SEND", "LENGTH") ;
			bRet = ParseSendLength();
		}
		else if (tok.GetKeyword() == CCobolKeywordList.PAGE)
		{
			CGlobalEntityCounter.GetInstance().CountCICSCommandOptions("SEND", "PAGE") ;
			bRet = ParseSendPage();
		}
		else
		{
			Transcoder.logError(getLine(), "Unparsed EXEC CICS SEND statement : "+tok.GetValue());
			String cs = "" ;
			tok = GetCurrentToken() ;
			while (tok.GetKeyword() != CCobolKeywordList.END_EXEC)
			{
				cs += tok.GetDisplay() + " " ;
				tok = GetNext() ;
			}		
			GetNext() ;
			return true ;
		}
		
		tok = GetCurrentToken() ;
		if (!bRet || tok.GetKeyword() != CCobolKeywordList.END_EXEC)
		{
			Transcoder.logError(getLine(), "Error while parsing EXEC CICS SEND");
			return false ;
		}
		StepNext() ;
		return true ;
	}
	
	
	protected boolean ParseSendControl()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.CONTROL)
		{
			tok = GetNext() ;
		}
		m_SendType = CCICSSendType.CONTROL ;
		boolean bDone = false ;
		while (!bDone)
		{
			tok = GetCurrentToken() ;
			if (tok.GetKeyword() == CCobolKeywordList.ERASE)
			{
				tok = GetNext()	;
				m_bControlErase = true ;
			}
			else if (tok.GetKeyword() == CCobolKeywordList.FREEKB)
			{
				tok = GetNext();
				m_bControlFreeKB = true ;
			}
			else
			{
				bDone = true ;
			}
		}
		return true ;
	}

	protected boolean ParseSendPage()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.PAGE)
		{
			tok = GetNext() ;
		}
		m_SendType = CCICSSendType.PAGE ;
		boolean bDone = false ;
		while (!bDone)
		{
			tok = GetCurrentToken() ;
			if (tok.GetValue().equals("RETAIN"))
			{
				tok = GetNext() ;
				m_bPageRetain = true ;
			}
			else
			{
				bDone = true ;
			}
		}
		return true ;
	}
	
	protected boolean ParseSendLength()	// PJD Added
	{
		return ParseSend();
	}
	 
	protected boolean ParseSend()
	{
		m_SendType = CCICSSendType.SEND ;
		boolean bDone = false ;
		while (!bDone)
		{
			CBaseToken tok = GetCurrentToken() ;
			if (tok.GetKeyword() == CCobolKeywordList.FROM)
			{
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext() ;
					m_SendFrom = ReadIdentifier() ;
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext() ;
					}
				}
			}
			else if (tok.GetKeyword() == CCobolKeywordList.LENGTH)
			{
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext() ;
					m_SendLength = ReadTerminal() ;
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext() ;
					}
				}
			}
			else if (tok.GetValue().equals("CONVID"))
			{
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext() ;
					m_SendConvID = ReadTerminal() ;
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext() ;
					}
				}
			}
			else if (tok.GetKeyword() == CCobolKeywordList.ERASE)
			{
				tok = GetNext() ;
				m_bSendErase = true ; 
			}
			else if (tok.GetKeyword() == CCobolKeywordList.WAIT)
			{
				tok = GetNext() ;
				m_bSendWait = true ; 
			}
			else 
			{
				bDone = true ;
			}
		
		}
		return true ;
	}
	
	protected boolean ParseSendMap()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetValue().equals("MAP"))
		{
			tok = GetNext();
		} 
		//CGlobalEntityCounter.GetInstance().CountCICSCommand("SEND_MAP") ;
		m_SendType = CCICSSendType.MAP ;
		if (tok.GetType() == CTokenType.LEFT_BRACKET)
		{
			tok = GetNext();
			m_MapName = ReadTerminal() ;
			tok = GetCurrentToken() ;
			if (tok.GetType() == CTokenType.RIGHT_BRACKET)
			{
				tok = GetNext();
			}
		}
		
		boolean bDone = false ;
		while (!bDone)
		{
			tok  = GetCurrentToken() ;
			if (tok.GetValue().equals("MAPSET"))
			{
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext();
					m_MapSetName = ReadTerminal() ;
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext();
					}
				}
			}
			else if (tok.GetKeyword() == CCobolKeywordList.FROM)
			{
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext();
					m_MapFrom = ReadIdentifier() ;
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext();
					}
				}
			}
			else if (tok.GetKeyword() == CCobolKeywordList.LENGTH)
			{
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext();
					m_MapLength = ReadTerminal() ;
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext();
					}
				}
			}
			else if (tok.GetKeyword() == CCobolKeywordList.FREEKB)
			{
				tok = GetNext() ;
				m_bMapFreeKB = true ; 
			}
			else if (tok.GetValue().equals("ACCUM"))
			{
				tok = GetNext() ;
				m_bMapAccum = true ; 
			}
			else if (tok.GetValue().equals("PAGING"))
			{
				tok = GetNext() ;
				m_bMapPaging = true ; 
			}
			else if (tok.GetKeyword() == CCobolKeywordList.ALARM)
			{
				tok = GetNext() ;
				m_bMapAlarm = true ; 
			}
			else if (tok.GetKeyword() == CCobolKeywordList.WAIT)
			{
				tok = GetNext() ;
				m_bMapWait = true ; 
			}
			else if (tok.GetKeyword() == CCobolKeywordList.ERASE)
			{
				tok = GetNext() ;
				m_bMapErase = true ; 
			}
			else if (tok.GetKeyword() == CCobolKeywordList.CURSOR)
			{
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext();
					m_MapCursorValue = ReadTerminal();
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext() ;
					}
				}
				m_bMapCursor = true ; 
			}
			else if (tok.GetKeyword() == CCobolKeywordList.DATAONLY)
			{
				tok = GetNext() ;
				m_bMapDataOnly = true ; 
			}
			else
			{
				bDone = true ;
			}
		}
		return true ;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		if (m_SendType == CCICSSendType.MAP)
		{
			Element e = root.createElement("ExecCICSSendMap") ;
			Element eName = root.createElement("MapName");
			e.appendChild(eName);
			m_MapName.ExportTo(eName, root) ;
			
			if (m_MapSetName != null)
			{
				Element eMS = root.createElement("MapSet");
				e.appendChild(eMS);
				m_MapSetName.ExportTo(eMS, root) ;
			}
			if (m_MapFrom != null)
			{
				Element eMS = root.createElement("From");
				e.appendChild(eMS);
				m_MapFrom.ExportTo(eMS, root) ;
			}
			if (m_MapLength != null)
			{
				Element eMS = root.createElement("Length");
				e.appendChild(eMS);
				m_MapLength.ExportTo(eMS, root) ;
			}
			if (m_bMapFreeKB)
			{
				e.setAttribute("FreeKB", "true") ;
			}
			if (m_bMapAccum)
			{
				e.setAttribute("Accum", "true") ;
			}
			if (m_bMapPaging)
			{
				e.setAttribute("Paging", "true") ;
			}
			if (m_bMapDataOnly)
			{
				e.setAttribute("DataOnly", "true") ;
			}
			if (m_bMapWait)
			{
				e.setAttribute("Wait", "true") ;
			}
			if (m_bMapAlarm)
			{
				e.setAttribute("Alarm", "true") ;
			}
			if (m_bMapErase)
			{
				e.setAttribute("Erase", "true") ;
			}
			if (m_bMapCursor)
			{
				if (m_MapCursorValue != null)
				{
					Element eCurs = root.createElement("Cursor");
					e.appendChild(eCurs) ;
					m_MapCursorValue.ExportTo(eCurs, root);
				}
				else
				{
					e.setAttribute("Cursor", "true") ;
				}
			}
			return e;
		}
		else if (m_SendType == CCICSSendType.SEND)
		{
			Element e = root.createElement("ExecCICSSend") ;
			if (m_SendFrom != null)
			{
				Element efrom = root.createElement("From") ;
				e.appendChild(efrom);
				m_SendFrom.ExportTo(efrom, root) ;
			}
			if (m_SendConvID != null)
			{
				Element eConv = root.createElement("ConvID");
				e.appendChild(eConv) ;
				m_SendConvID.ExportTo(eConv, root);
			}
			if (m_SendLength != null)
			{
				Element eConv = root.createElement("Length");
				e.appendChild(eConv) ;
				m_SendLength.ExportTo(eConv, root);
			}
			if (m_bSendErase)
			{
				e.setAttribute("Erase", "true");
			}
			if (m_bSendWait)
			{
				e.setAttribute("Wait", "true");
			}
			return e;
		}
		else if (m_SendType == CCICSSendType.PAGE)
		{
			Element e = root.createElement("ExecCICSSendPage") ;
			if (m_bPageRetain)
			{
				e.setAttribute("Retain", "true");
			}
			return e;
		}
		else if (m_SendType == CCICSSendType.CONTROL)
		{
			Element e = root.createElement("ExecCICSSendControl") ;
			if (m_bControlErase)
			{
				e.setAttribute("Erase", "true");
			}
			if (m_bControlFreeKB)
			{
				e.setAttribute("FreeKB", "true");
			}
			return e;
		}
		else
		{
			Element e = root.createElement("ExecCICSSend") ;
			return e;
		}
	}

	protected CCICSSendType m_SendType = null ;

	// SEND
	protected CIdentifier m_SendFrom = null ;
	protected CTerminal m_SendConvID = null ;
	protected boolean m_bSendErase = false ;
	protected CTerminal m_SendLength = null ;
	protected boolean m_bSendWait = false ;
	
	// SEND MAP
	protected CTerminal m_MapName = null ;
	protected CTerminal m_MapSetName = null ;
	protected CIdentifier m_MapFrom = null ;
	protected CTerminal m_MapCursorValue = null ;
	protected boolean m_bMapFreeKB= false ;
	protected boolean m_bMapDataOnly = false ;
	protected boolean m_bMapCursor = false ;
	protected boolean m_bMapErase = false ;
	protected boolean m_bMapAlarm = false ; 
	protected boolean m_bMapWait = false ; 
	protected boolean m_bMapAccum = false ;
	protected boolean m_bMapPaging = false ;
	protected CTerminal m_MapLength = null ;
	
	// SEND PAGE
	protected boolean m_bPageRetain  = false ;
	
	// SEND CONTROL
	protected boolean m_bControlErase = false ;
	protected boolean m_bControlFreeKB = false ;
}
