/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Sep 7, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements;

import lexer.CBaseToken;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CIdentifier;
import parser.Cobol.CCobolElement;
import parser.expression.CNumberTerminal;
import parser.expression.CTerminal;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.CEntityFileDescriptor;
import semantic.Verbs.CEntityWriteFile;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CWrite extends CCobolElement
{
	/**
	 * @param line
	 */
	public CWrite(int line)
	{
		super(line);
	}
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityWriteFile eWrite = factory.NewEntityWriteFile(getLine()) ;
		parent.AddChild(eWrite) ;
		
		CEntityFileDescriptor eFD = factory.m_ProgramCatalog.getFileDescriptor(m_FileDesc.GetName()) ;
		if (eFD != null)
		{
			CDataEntity eData = null ;
			if (m_DataFrom != null)
			{
				eData = m_DataFrom.GetDataReference(getLine(), factory) ;
			}
			eWrite.setFileDescriptor(eFD, eData) ;
		}
		else
		{
			Transcoder.logError(getLine(), "File descriptor not found : " + m_FileDesc.GetName());
		}
		if  (m_bWriteAfterPositioning)
		{
			eWrite.SetAfter(m_NbLinesPositioning.GetDataEntity(getLine(), factory));
		}
		if (m_bWriteBeforePositioning)
		{
			Transcoder.logError(getLine(), "No semantic analysis for WriteFile/ WriteBeforePositioning");
		}
		if (m_blocInvalidKey != null)
		{
			Transcoder.logError(getLine(), "No semantic analysis for WriteFile/ InvalidKeyBloc");
		}
		return eWrite;
	}
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ; 
		if (tok.GetKeyword() != CCobolKeywordList.WRITE)
		{
			return false ;
		}
		CGlobalEntityCounter.GetInstance().CountCobolVerb(tok.GetKeyword().m_Name) ;
		
		tok = GetNext() ;
		m_FileDesc = ReadIdentifier();
		
		tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.FROM)
		{
			tok = GetNext() ;
			m_DataFrom = ReadIdentifier();
		}
		
		tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.AFTER || tok.GetKeyword() == CCobolKeywordList.BEFORE)
		{
			if (tok.GetKeyword() == CCobolKeywordList.AFTER)
			{
				m_bWriteAfterPositioning = true ;
			}
			else
			{
				m_bWriteBeforePositioning = true ;
			}
			tok = GetNext() ;
			if (tok.GetKeyword() == CCobolKeywordList.ADVANCING || tok.GetKeyword() == CCobolKeywordList.POSITIONING)
			{
				tok = GetNext();
			}
			if (tok.GetKeyword() == CCobolKeywordList.PAGE)
			{
				GetNext() ;
				m_NbLinesPositioning = new CNumberTerminal("-1") ;
			}
			else
			{
				CTerminal term = ReadTerminal();
				if (term != null)
				{
					m_NbLinesPositioning = term ;
				}
				else
				{
					Transcoder.logError(tok.getLine(), "Unexpecting situation");
					return false ;
				}
				tok = GetCurrentToken() ;
				if (tok.GetKeyword() == CCobolKeywordList.LINE || tok.GetKeyword() == CCobolKeywordList.LINES)
				{
					GetNext() ;
				}
			}
		}

		tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.INVALID)
		{
			tok = GetNext() ;
			if (tok.GetKeyword()== CCobolKeywordList.KEY)
			{
				tok = GetNext();
			}
			m_blocInvalidKey = new CGenericBloc("InvalidKey", getLine());
			if (!Parse(m_blocInvalidKey))
			{
				return false ;
			}
		}
	
		tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.END_WRITE)
		{
			GetNext() ;
		}
		return true ;
	}
	protected Element ExportCustom(Document root)
	{
		Element eWr = root.createElement("Write");
		
		Element eFile = root.createElement("File");
		eWr.appendChild(eFile);
		m_FileDesc.ExportTo(eFile, root);
		
		if (m_DataFrom != null)
		{
			Element e = root.createElement("DataFrom");
			eWr.appendChild(e);
			m_DataFrom.ExportTo(e, root);
		}
		
		if (m_NbLinesPositioning != null)
		{
			String cs = "" ;
			if (m_bWriteAfterPositioning)
			{
				cs = "WriteAfterPositioning";
			}
			else if (m_bWriteBeforePositioning)
			{
				cs = "WriteBeforePositioning";
			}
			Element ePos = root.createElement(cs);
			eWr.appendChild(ePos);
			if (m_NbLinesPositioning.GetValue().equals("-1"))
			{
				Element e = root.createElement("Page");
				ePos.appendChild(e);
			}
			else
			{
				Element e = root.createElement("Lines");
				m_NbLinesPositioning.ExportTo(e, root);
				ePos.appendChild(e);
			}
		}
		
		if (m_blocInvalidKey != null)
		{
			Element e = m_blocInvalidKey.Export(root);
			eWr.appendChild(e);	
		}
		return eWr;
	}
	
	protected CIdentifier m_FileDesc = null ;
	protected CIdentifier m_DataFrom = null ;
	protected boolean m_bWriteAfterPositioning = false ;
	protected boolean m_bWriteBeforePositioning = false ;
	protected CTerminal m_NbLinesPositioning = null ; // -1 means PAGE
	protected CGenericBloc m_blocInvalidKey = null ;
}
