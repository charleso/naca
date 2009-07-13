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
import semantic.CICS.CEntityCICSWriteQ;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecCICSWriteQ extends CCobolElement
{

	/**
	 * @param line
	 */
	public CExecCICSWriteQ(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityCICSWriteQ eWQ = factory.NewEntityCICSWriteQ(getLine(), m_bPersistant);
		parent.AddChild(eWQ);
		
		eWQ.SetName(m_QueueName.GetDataEntity(getLine(), factory)) ;
		if (m_DataRef != null)
		{
			CDataEntity len = null ;
			if (m_Length != null)
			{
				len = m_Length.GetDataEntity(getLine(), factory);
				len.RegisterWritingAction(eWQ); 
			}
			CDataEntity data = m_DataRef.GetDataReference(getLine(), factory) ;
			data.RegisterWritingAction(eWQ); 
			eWQ.SetDataRef(data, len);
		}
		if (m_Item != null)
		{
			CDataEntity e = m_Item.GetDataEntity(getLine(), factory) ;
			eWQ.WriteItem(e);
			e.RegisterReadingAction(eWQ) ;
		}
		if (m_NumItem != null)
		{
			CDataEntity e = m_NumItem.GetDataEntity(getLine(), factory) ;
			eWQ.WriteNumItem(e);
			e.RegisterReadingAction(eWQ) ;
		}
		if (m_bAuxiliary)
		{
			eWQ.SetAuxiliary() ;
		}
		else if (m_bMain)
		{
			eWQ.SetMain() ;
		}
		if (m_bRewrite)
		{
			eWQ.SetRewrite() ;
		}
		return eWQ ;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.WRITEQ)
		{
			tok = GetNext();
		}
		
		if (tok.GetValue().equals("TD"))
		{
			CGlobalEntityCounter.GetInstance().CountCICSCommandOptions("WRITEQ", "TD") ;
			tok = GetNext(); 
			m_bPersistant = true ;
		}
		else if (tok.GetValue().equals("TS"))
		{
			CGlobalEntityCounter.GetInstance().CountCICSCommandOptions("WRITEQ", "TS") ;
			tok = GetNext(); 
			m_bPersistant = false ;
		}
		else
		{
			CGlobalEntityCounter.GetInstance().CountCICSCommandOptions("WRITEQ", "Unknown") ;
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
			else if (tok.GetKeyword() == CCobolKeywordList.FROM)
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
			else if (tok.GetValue().equals("SYSID"))
			{
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{
					tok = GetNext();
					m_SysID = ReadTerminal() ;
					tok = GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext();
					}
				}
			}
			else if (tok.GetValue().equals("MAIN"))
			{
				tok = GetNext() ;
				m_bMain = true ;
			}
			else if (tok.GetValue().equals("AUXILIARY"))
			{
				tok = GetNext() ;
				m_bAuxiliary = true ;
			}
			else if (tok.GetValue().equals("REWRITE"))
			{
				tok = GetNext() ;
				m_bRewrite = true ;
			}
			else if (tok.GetValue().equals("NUMITEM"))
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
			Transcoder.logError(getLine(), "Error whle parsing EXEC CICS WRITEQ");
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
		Element eWrite = root.createElement("ExecCICSWriteQ") ;
		if (m_bPersistant)
		{
			eWrite.setAttribute("Persistant", "true") ;
		}
		else
		{
			eWrite.setAttribute("Persistant", "false") ;
		}
		if (m_bMain)
		{
			eWrite.setAttribute("Main", "true") ;
		}
		if (m_bAuxiliary)
		{
			eWrite.setAttribute("Auxiliary", "true") ;
		}
		if (m_bRewrite)
		{
			eWrite.setAttribute("Rewrite", "true") ;
		}
		if (m_QueueName != null)
		{
			Element e = root.createElement("QueueName");
			eWrite.appendChild(e);
			m_QueueName.ExportTo(e, root) ;
		}
		if (m_SysID != null)
		{
			Element e = root.createElement("SysID");
			eWrite.appendChild(e);
			m_SysID.ExportTo(e, root) ;
		}
		if (m_DataRef != null)
		{
			Element e = root.createElement("From");
			eWrite.appendChild(e);
			m_DataRef.ExportTo(e, root) ;
		}
		if (m_NumItem != null)
		{
			Element e = root.createElement("NumItem");
			eWrite.appendChild(e);
			m_NumItem.ExportTo(e, root) ;
		}
		if (m_Item != null)
		{
			Element e = root.createElement("Item");
			eWrite.appendChild(e);
			m_Item.ExportTo(e, root) ;
		}
		if (m_Length != null)
		{
			Element e = root.createElement("Length");
			eWrite.appendChild(e);
			m_Length.ExportTo(e, root) ;
		}
		return eWrite;
	}

	protected boolean m_bPersistant = false ;
	protected CTerminal m_QueueName = null ;
	protected CIdentifier m_DataRef = null ;
	protected CTerminal m_Length = null ;
	protected CTerminal m_Item = null ;
	protected CTerminal m_NumItem = null ;
	protected boolean m_bMain = false ;
	protected boolean m_bRewrite = false ;
	protected boolean m_bAuxiliary = false ;
	protected CTerminal m_SysID = null ;
}
