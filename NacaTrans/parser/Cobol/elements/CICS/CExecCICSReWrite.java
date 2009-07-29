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
import semantic.CICS.CEntityCICSReWrite;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CExecCICSReWrite extends CCobolElement
{

	/**
	 * @param line
	 */
	public CExecCICSReWrite(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityCICSReWrite write = factory.NewEntityCICSReWrite(getLine());
		parent.AddChild(write);
		CDataEntity filename = m_FileName.GetDataEntity(getLine(), factory);
		if (m_WriteType == CCobolKeywordList.FILE)
		{
			write.WriteFile(filename);
		}
		else if (m_WriteType == CCobolKeywordList.DATASET)
		{
			write.WriteDataSet(filename);
		}
		else
		{
			Transcoder.logError(getLine(), "Error in semantic analysis of EXEC CICS WRITE") ;
			return null ;
		}

		if (m_DataFrom != null)
		{
			CDataEntity edata = m_DataFrom.GetDataReference(getLine(), factory);
			CDataEntity eLen = null ;
			if (m_DataLength != null)
			{
				eLen = m_DataLength.GetDataEntity(getLine(), factory);
			}
			write.SetDataFrom(edata, eLen);
		}
		return write ;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.REWRITE)
		{
			tok = GetNext();
		}
		
		boolean bDone = false ;
		while (!bDone)
		{
			tok = GetCurrentToken() ;
			if (tok.GetKeyword() == CCobolKeywordList.FILE && m_WriteType == null)
			{
				m_WriteType = CCobolKeywordList.FILE ;
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
			else if (tok.GetKeyword() == CCobolKeywordList.DATASET && m_WriteType == null)
			{
				m_WriteType = CCobolKeywordList.DATASET ;
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
			else if (tok.GetKeyword() == CCobolKeywordList.FROM)
			{
				tok = GetNext();
				if (tok.GetType() == CTokenType.LEFT_BRACKET)
				{ 
					tok = GetNext();
					m_DataFrom = ReadIdentifier() ;
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
			else 
			{
				bDone = true ;
			}
		}
				
		if (tok.GetKeyword() != CCobolKeywordList.END_EXEC)
		{
			Transcoder.logError(tok.getLine(), "Error while parsing EXEC CICS REWRITE");
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
		Element eWr = root.createElement("ExecCICSReWrite") ;
		Element e ;
		if (m_WriteType == CCobolKeywordList.FILE)
		{
			e = root.createElement("File");
		}
		else if (m_WriteType == CCobolKeywordList.DATASET)
		{
			e = root.createElement("Dataset");
		}
		else
		{
			return null ;
		}
		eWr.appendChild(e);
		m_FileName.ExportTo(e, root);
		
		if (m_DataFrom != null)
		{
			Element eFrom = root.createElement("From");
			m_DataFrom.ExportTo(eFrom, root);
			eWr.appendChild(eFrom);
			if (m_DataLength != null)
			{
				Element eLen = root.createElement("Length");
				eFrom.appendChild(eLen);
				m_DataLength.ExportTo(eLen, root);
			}
		}
		return eWr;
	}

	protected CReservedKeyword m_WriteType = null ;
	protected CTerminal m_FileName = null ; 
	protected CIdentifier m_DataFrom = null ;
	protected CTerminal m_DataLength = null ;
}
