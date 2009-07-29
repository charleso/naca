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
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.CEntityFileDescriptor;
import semantic.Verbs.CEntityReadFile;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CRead extends CCobolElement
{
	/**
	 * @param line
	 */
	public CRead(int line)
	{
		super(line);
	}
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityReadFile eRead = factory.NewEntityReadFile(getLine()) ;
		parent.AddChild(eRead) ;
		
		CEntityFileDescriptor eFD = factory.m_ProgramCatalog.getFileDescriptor(m_FileDescriptor.GetName()) ;
		if (eFD != null)
		{
			CDataEntity eData = null ;
			if (m_DataInto != null)
			{
				eData = m_DataInto.GetDataReference(getLine(), factory) ;
			}
			eRead.setFileDescriptor(eFD, eData) ;
			if (m_AtEndBloc != null) 
			{
				CBaseLanguageEntity eBloc = m_AtEndBloc.DoSemanticAnalysis(eRead, factory) ;
				eRead.SetAtEndBloc(eBloc) ;
			}
			if (m_NotAtEndBloc != null) 
			{
				CBaseLanguageEntity eBloc = m_NotAtEndBloc.DoSemanticAnalysis(eRead, factory) ;
				eRead.SetNotAtEndBloc(eBloc) ;
			}
		}
		else
		{
			Transcoder.logError(getLine(), "File descriptor not found : " + m_FileDescriptor.GetName());
		}
//		if  (m_bReadNextRecord)
//		{
//			m_Logger.error("No semantic analysis for ReadFile/ ReadNextRecord ");
//		}
		if (m_bReadPreviousRecord)
		{
			Transcoder.logError(getLine(), "No semantic analysis for ReadFile/ ReadPreviousRecord");
		}
		if (m_Key != null)
		{
			Transcoder.logError(getLine(), "No semantic analysis for ReadFile/ KEY");
		}
		if (m_InvalidKeyBloc != null)
		{
			Transcoder.logError(getLine(), "No semantic analysis for ReadFile/ InvalidKeyBloc");
		}
		if (m_NotInvalidKeyBloc != null)
		{
			Transcoder.logError(getLine(), "No semantic analysis for ReadFile/ NotInvalidKeyBloc");
		}
		return eRead;
	}
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() != CCobolKeywordList.READ)
		{
			return false ;
		}
		CGlobalEntityCounter.GetInstance().CountCobolVerb(tok.GetKeyword().m_Name) ;
		
		tok = GetNext() ;
		m_FileDescriptor = ReadIdentifier();
		
		tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.NEXT)
		{
			m_bReadNextRecord = true ;
			m_bReadPreviousRecord = false ;
			tok = GetNext() ;
			if (tok.GetKeyword() == CCobolKeywordList.RECORD)
			{
				tok = GetNext();
			}
		}
		else if (tok.GetKeyword() == CCobolKeywordList.PREVIOUS)
		{
			m_bReadNextRecord = false ;
			m_bReadPreviousRecord = true ;
			tok = GetNext() ;
			if (tok.GetKeyword() == CCobolKeywordList.RECORD)
			{
				tok = GetNext();
			}
		}
		if (tok.GetKeyword() == CCobolKeywordList.INTO)
		{
			tok = GetNext() ;
			m_DataInto = ReadIdentifier();
			tok = GetCurrentToken() ;
		}
		
		if (tok.GetKeyword() == CCobolKeywordList.KEY)
		{
			tok = GetNext();
			if (tok.GetKeyword() == CCobolKeywordList.IS)
			{
				tok = GetNext();
			}
			m_Key = ReadIdentifier();
			tok = GetCurrentToken();
		}
		
		if (tok.GetKeyword() == CCobolKeywordList.AT)
		{
			tok = GetNext() ;
			if (tok.GetKeyword() == CCobolKeywordList.END)
			{
				tok = GetNext() ;
				m_AtEndBloc = new CGenericBloc("AtEnd", tok.getLine()) ;
				if (!Parse(m_AtEndBloc))
				{
					return false ;
				}
			}
		}
		tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.NOT)
		{
			tok = GetNext();
			if (tok.GetKeyword() == CCobolKeywordList.AT)
			{
				tok = GetNext() ;
				if (tok.GetKeyword() == CCobolKeywordList.END)
				{
					tok = GetNext() ;
					m_NotAtEndBloc = new CGenericBloc("NotAtEnd", tok.getLine()) ;
					if (!Parse(m_NotAtEndBloc))
					{
						return false ;
					}
				}
			}
		}

		tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.INVALID)
		{
			tok = GetNext() ;
			if (tok.GetKeyword() == CCobolKeywordList.KEY)
			{
				tok = GetNext() ;
			}
			m_InvalidKeyBloc = new CGenericBloc("InvalidKey", tok.getLine()) ;
			if (!Parse(m_InvalidKeyBloc))
			{
				return false ;
			}
		}
		tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.NOT)
		{
			tok = GetNext();
			if (tok.GetKeyword() == CCobolKeywordList.INVALID)
			{
				tok = GetNext() ;
				if (tok.GetKeyword() == CCobolKeywordList.KEY)
				{
					tok = GetNext() ;
				}
				m_NotInvalidKeyBloc = new CGenericBloc("NotInvalidKey", tok.getLine()) ;
				if (!Parse(m_NotInvalidKeyBloc))
				{
					return false ;
				}
			}
		}
		
		tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.END_READ)
		{
			GetNext();
		}
		return true;
	}
	protected Element ExportCustom(Document root)
	{
		Element eRead = root.createElement("Read");
		String cs = "File" ;
//		if (m_bReadNextRecord)
//		{
//			cs = "NextRecord" ;
//		}
		if (m_bReadPreviousRecord)
		{
			cs = "PreviousRecord" ;
		}
		Element eFile = root.createElement(cs);
		eRead.appendChild(eFile);
		m_FileDescriptor.ExportTo(eFile, root);
		
		if (m_DataInto != null)
		{
			Element eTo = root.createElement("Into");
			eRead.appendChild(eTo);
			m_DataInto.ExportTo(eTo, root);
		}
		
		if (m_Key != null)
		{
			Element eKey = root.createElement("Key");
			eRead.appendChild(eKey);
			m_Key.ExportTo(eKey, root);
		}
		
		if (m_AtEndBloc != null)
		{
			Element e = m_AtEndBloc.Export(root);
			eRead.appendChild(e);
		} 
		if (m_NotAtEndBloc != null)
		{
			Element e = m_NotAtEndBloc.Export(root);
			eRead.appendChild(e);
		} 
		if (m_InvalidKeyBloc != null)
		{
			Element e = m_InvalidKeyBloc.Export(root);
			eRead.appendChild(e);
		} 
		if (m_NotInvalidKeyBloc != null)
		{
			Element e = m_NotInvalidKeyBloc.Export(root);
			eRead.appendChild(e);
		} 
		return eRead;
	}
	
	protected CIdentifier m_FileDescriptor = null ;
	protected CIdentifier m_DataInto = null ; 
	protected CIdentifier m_Key = null ;
	protected CGenericBloc m_AtEndBloc = null ;
	protected CGenericBloc m_NotAtEndBloc = null ;
	protected CGenericBloc m_InvalidKeyBloc = null ;
	protected CGenericBloc m_NotInvalidKeyBloc = null ;
	protected boolean m_bReadNextRecord = false ;
	protected boolean m_bReadPreviousRecord = false ;

}
