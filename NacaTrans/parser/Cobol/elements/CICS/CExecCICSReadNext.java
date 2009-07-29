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
public class CExecCICSReadNext extends CCobolElement
{

	/**
	 * @param line
	 */
	public CExecCICSReadNext(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityCICSRead Read = factory.NewEntityCICSRead(getLine(), CEntityCICSRead.CEntityCICSReadMode.NEXT);
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
			Transcoder.logError(getLine(), "Error in semantic analysis of EXEC CICS READNEXT") ;
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
		return Read ;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.READNEXT)
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
			else if (tok.GetKeyword() == CCobolKeywordList.LENGTH)
			{
				tok = GetNext();
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{ 
					tok = GetNext();
					m_Length = ReadTerminal() ;
					tok= GetCurrentToken() ;
					if (tok.GetType() == CTokenType.RIGHT_BRACKET)
					{
						tok = GetNext();
					}
				}
			}		
			else if (tok.GetKeyword() == CCobolKeywordList.KEYLENGTH)
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
		Element eWr = root.createElement("ExecCICSReadNext") ;
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
		if (m_RecIDField != null)
		{
			Element eFrom = root.createElement("RecIDField");
			m_RecIDField.ExportTo(eFrom, root);
			eWr.appendChild(eFrom);
		}
		return eWr;
	}

	protected CReservedKeyword m_ReadType = null ;
	protected CTerminal m_FileName = null ; 
	protected CIdentifier m_DataInto = null ;
	protected CIdentifier m_RecIDField = null ; 
	protected CTerminal m_DataLength = null ;
	protected CTerminal m_Length = null ;
	protected CTerminal m_KeyLength = null ;

}
