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
import semantic.CICS.CEntityCICSReadQ;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecCICSReadQ extends CCobolElement
{

	/**
	 * @param line
	 */
	public CExecCICSReadQ(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityCICSReadQ eRQ = factory.NewEntityCICSReadQ(getLine(), m_bPersistant);
		parent.AddChild(eRQ);
		
		eRQ.SetName(m_QueueName.GetDataEntity(getLine(), factory)) ;
		if (m_DataRef != null)
		{
			CDataEntity len = null ;
			if (m_Length != null)
			{
				len = m_Length.GetDataEntity(getLine(), factory);
				len.RegisterWritingAction(eRQ); 
			}
			CDataEntity data = m_DataRef.GetDataReference(getLine(), factory) ;
			data.RegisterWritingAction(eRQ); 
			eRQ.SetDataRef(data, len);
		}
		if (m_bNext)
		{
			eRQ.ReadNext() ;
		}
		else if (m_Item != null)
		{
			CDataEntity e = m_Item.GetDataEntity(getLine(), factory) ;
			e.RegisterReadingAction(eRQ) ;
			eRQ.ReadItem(e);
		}
		if (m_NumItem != null)
		{
			CDataEntity e = m_NumItem.GetDataEntity(getLine(), factory) ;
			eRQ.ReadNumItem(e);
			e.RegisterReadingAction(eRQ) ;
		}
		return eRQ ;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.READQ)
		{
			tok = GetNext();
		}
		
		if (tok.GetValue().equals("TD"))
		{
			CGlobalEntityCounter.GetInstance().CountCICSCommandOptions("READQ", "TD") ;
			tok = GetNext(); 
			m_bPersistant = true ;
		}
		else if (tok.GetValue().equals("TS"))
		{
			CGlobalEntityCounter.GetInstance().CountCICSCommandOptions("READQ", "TS") ;
			tok = GetNext(); 
			m_bPersistant = false ;
		}
		else
		{
			CGlobalEntityCounter.GetInstance().CountCICSCommandOptions("READQ", "Unknown") ;
			m_bPersistant = false ;
		}
		
		boolean bDone = false ;
		while (!bDone)
		{
			if (tok.GetValue().equals("QUEUE"))
			{
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext();
					m_QueueName = ReadTerminal();
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext();
					}
				}
			}
			else if (tok.GetKeyword() == CCobolKeywordList.INTO)
			{
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext();
					m_DataRef = ReadIdentifier();
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
					m_Length = ReadTerminal() ;
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext();
					}
				}
			}
			else if (tok.GetKeyword() == CCobolKeywordList.NEXT)
			{
				tok = GetNext() ;
				m_bNext = true ;
			}
			else if (tok.GetValue().equals("ITEM"))
			{
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext();
					m_Item = ReadTerminal() ;
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext();
					}
				}
			}
			else if (tok.GetValue().equals("NUMITEMS"))
			{
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext();
					m_NumItem = ReadTerminal() ;
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
			Transcoder.logError(getLine(), "Error while parsing EXEC CICS READQ");
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
		Element eRead = root.createElement("ExecCICSReadQ") ;
		if (m_bPersistant)
		{
			eRead.setAttribute("Persistant", "true") ;
		}
		else
		{
			eRead.setAttribute("Persistant", "false") ;
		}
		if (m_bNext)
		{
			eRead.setAttribute("Next", "true") ;
		}
		if (m_QueueName != null)
		{
			Element e = root.createElement("QueueName");
			eRead.appendChild(e);
			m_QueueName.ExportTo(e, root) ;
		}
		if (m_DataRef != null)
		{
			Element e = root.createElement("Into");
			eRead.appendChild(e);
			m_DataRef.ExportTo(e, root) ;
		}
		if (m_NumItem != null)
		{
			Element e = root.createElement("NumItems");
			eRead.appendChild(e);
			m_NumItem.ExportTo(e, root) ;
		}
		if (m_Item != null)
		{
			Element e = root.createElement("Item");
			eRead.appendChild(e);
			m_Item.ExportTo(e, root) ;
		}
		if (m_Length != null)
		{
			Element e = root.createElement("Length");
			eRead.appendChild(e);
			m_Length.ExportTo(e, root) ;
		}
		return eRead;
	}
	
	protected boolean m_bPersistant = false ;
	protected CTerminal m_QueueName = null ;
	protected CIdentifier m_DataRef  = null ;
	protected CTerminal m_NumItem = null ;
	protected CTerminal m_Item = null ;
	protected CTerminal m_Length = null ;
	protected boolean m_bNext = false ;
}
 