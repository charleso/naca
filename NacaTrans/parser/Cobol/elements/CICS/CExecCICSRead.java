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
import lexer.CReservedKeyword;
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
import semantic.CICS.CEntityCICSRead;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecCICSRead extends CCobolElement
{

	/**
	 * @param line
	 */
	public CExecCICSRead(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis( CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityCICSRead Read = factory.NewEntityCICSRead(getLine(), CEntityCICSRead.CEntityCICSReadMode.NORMAL);
		parent.AddChild(Read);
		CDataEntity filename = m_FileName.GetDataEntity(getLine(), factory);
		if (m_ReadType == CCobolKeywordList.FILE)
		{
			Read.ReadFile(filename);
		}
		else if (m_ReadType == CCobolKeywordList.DATASET)
		{
			Read.ReadDataSet(filename);
		}
		else
		{
			Transcoder.logError(getLine(), "Error in semantic analysis of EXEC CICS READ") ;
			return null ;
		}

		if (m_DataInto != null)
		{
			CDataEntity edata = m_DataInto.GetDataReference(getLine(), factory);
			CDataEntity edatalen = null ;
			if (m_DataLength != null)
			{
				edatalen = m_DataLength.GetDataEntity(getLine(), factory);
			}
			Read.SetDataInto(edata, edatalen);
		}
		if (m_RecIDField != null)
		{
			CDataEntity edata = m_RecIDField.GetDataReference(getLine(), factory);
			Read.SetRecIDField(edata);
		}
		if (m_KeyLength != null)
		{
			Read.SetKeyLength(m_KeyLength.GetDataEntity(getLine(), factory));
		}
		if (m_bEqual)
		{
			Read.SetEqual() ;
		}
		return Read ;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.READ)
		{
			tok = GetNext();
		}
		
		boolean bDone = false ;
		while (!bDone)
		{
			tok = GetCurrentToken() ;
			if (tok.GetKeyword() == CCobolKeywordList.FILE && m_ReadType == null)
			{
				m_ReadType = CCobolKeywordList.FILE ;
				tok = GetNext() ;
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{ 
					tok = GetNext();
					m_FileName = ReadTerminal();
					tok= GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext();
					}
				}
			}
			else if (tok.GetKeyword() == CCobolKeywordList.DATASET && m_ReadType == null)
			{
				m_ReadType = CCobolKeywordList.DATASET ;
				tok = GetNext();
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{ 
					tok = GetNext();
					m_FileName = ReadTerminal();
					tok= GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext();
					}
				}
			}
			else if (tok.GetKeyword() == CCobolKeywordList.INTO)
			{
				tok = GetNext();
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{ 
					tok = GetNext();
					m_DataInto = ReadIdentifier() ;
					tok= GetCurrentToken() ;
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
					tok = GetNext();
					m_DataLength = ReadTerminal() ;
					tok= GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext();
					}
				}
			}		
			else if (tok.GetValue().equals("RIDFLD"))
			{
				tok = GetNext();
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{ 
					tok = GetNext();
					m_RecIDField = ReadIdentifier() ;
					tok= GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext();
					}
				}
			}
			else if (tok.GetValue().equals("KEYLENGTH"))
			{
				tok = GetNext();
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{ 
					tok = GetNext();
					m_KeyLength = ReadTerminal() ;
					tok= GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext();
					}
				}
			}
			else if (tok.GetKeyword() == CCobolKeywordList.EQUAL)
			{
				m_bEqual = true ;
				tok = GetNext() ;
			}		
			else if (tok.GetKeyword() == CCobolKeywordList.UPDATE)
			{
				m_bUpdate = true ;
				tok = GetNext() ;
			}		
			else 
			{
				bDone = true ;
			}
		}
				
		if (tok.GetKeyword() != CCobolKeywordList.END_EXEC)
		{
			Transcoder.logError(tok.getLine(), "Error while parsing EXEC CICS READ");
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
		Element eWr = root.createElement("ExecCICSRead") ;
		Element e ;
		if (m_ReadType == CCobolKeywordList.FILE)
		{
			e = root.createElement("File");
		}
		else if (m_ReadType == CCobolKeywordList.DATASET)
		{
			e = root.createElement("Dataset");
		}
		else
		{
			return null ;
		}
		eWr.appendChild(e);
		m_FileName.ExportTo(e, root);
		
		if (m_DataInto != null)
		{
			Element eFrom = root.createElement("Into");
			m_DataInto.ExportTo(eFrom, root);
			eWr.appendChild(eFrom);
		}
		if (m_DataLength != null)
		{
			Element eFrom = root.createElement("Length");
			m_DataLength.ExportTo(eFrom, root);
			eWr.appendChild(eFrom);
		}
		if (m_RecIDField != null)
		{
			Element eFrom = root.createElement("RecIDField");
			m_RecIDField.ExportTo(eFrom, root);
			eWr.appendChild(eFrom);
		}
		if (m_KeyLength != null)
		{
			Element eFrom = root.createElement("KeyLength");
			m_KeyLength.ExportTo(eFrom, root);
			eWr.appendChild(eFrom);
		}
		if (m_bEqual)
		{
			eWr.setAttribute("Equal", "true");
		}
		return eWr;
	}

	protected CReservedKeyword m_ReadType = null ;
	protected CTerminal m_FileName = null ; 
	protected CIdentifier m_DataInto = null ;
	protected CIdentifier m_RecIDField = null ; 
	protected CTerminal m_KeyLength = null ;
	protected CTerminal m_DataLength = null ;
	protected boolean m_bEqual = false ;
	protected boolean m_bUpdate = false ;

}
