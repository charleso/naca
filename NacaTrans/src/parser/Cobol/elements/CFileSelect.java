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
import lexer.CTokenType;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


import parser.CIdentifier;
import parser.Cobol.CCobolElement;
import parser.expression.CStringTerminal;
import parser.expression.CTerminal;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CEntityFileSelect;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CFileSelect extends CCobolElement
{
	/**
	 * @param line
	 */
	public CFileSelect(int line)
	{
		super(line);
	}
	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityFileSelect eFS = factory.NewEntityFileSelect(m_FileReference.GetName()) ;
		eFS.setFileName(m_FileName.GetDataEntity(getLine(), factory)) ;
		
		if (m_bAccessModeDynamic)
		{
			eFS.setAccessMode(CEntityFileSelect.AccessMode.DYNAMIC) ;
		}
		else  if (m_bAccessModeRandom)
		{
			eFS.setAccessMode(CEntityFileSelect.AccessMode.RANDOM) ;
		}
		else if (m_bAccessModeSequential)
		{
			eFS.setAccessMode(CEntityFileSelect.AccessMode.SEQUENTIAL) ;
		}
		
		if (m_bOrganizationIndexed)
		{
			eFS.setOrganizationMode(CEntityFileSelect.OrganizationMode.INDEXED) ;
		}
		else if (m_bOrganizationSequential)
		{
			eFS.setOrganizationMode(CEntityFileSelect.OrganizationMode.INDEXED) ;
		}
		
		if (m_FileStatus != null)
		{
			eFS.setFileStatus(m_FileStatus.GetDataReference(getLine(), factory));
		}
		if (m_RecordKey != null)
		{
			Transcoder.logWarn(getLine(), "No semantic analysis for FileSelect / Record Key");
		}
		return eFS ;
	}
	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() != CCobolKeywordList.SELECT)
		{
			return false ;
		}
		CGlobalEntityCounter.GetInstance().CountCobolVerb(tok.GetKeyword().m_Name) ;
		
		// file local identifier
		tok = GetNext();
		m_FileReference = ReadIdentifier();
		if (m_FileReference == null)
		{
			Transcoder.logError(tok.getLine(), "Expecting identifier");
			return false ;
		}
		
		tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.ASSIGN)
		{
			tok = GetNext() ;
			if (tok.GetKeyword()== CCobolKeywordList.TO)
			{
				tok = GetNext() ;
			}
		}
		else
		{
			Transcoder.logError(tok.getLine(), "Expecting ASSIGN");
			return false ;
		}
		if (tok.GetKeyword() == CCobolKeywordList.DISK)
		{
			tok = GetNext() ;
		}
		if (tok.GetType() == CTokenType.DOT)
		{
			m_FileName = new CStringTerminal(m_FileReference.GetName());
			GetNext();
			return true;
		}
		
		// file name in computer file system
		m_FileName = ReadTerminal();
		if (m_FileName == null)
		{
			Transcoder.logError(tok.getLine(), "Expecting identifier");
			return false ;
		}
		
		boolean bDone = false ;
		while (!bDone)
		{
			tok = GetCurrentToken() ;
			if (tok.GetType() == CTokenType.DOT)
			{
				bDone = true ;
				GetNext() ;
			}
			else if (tok.GetKeyword() == CCobolKeywordList.RECORD)
			{
				tok = GetNext();
				if (tok.GetKeyword() == CCobolKeywordList.KEY)
				{
					tok = GetNext();
				}
				if (tok.GetKeyword() == CCobolKeywordList.IS)
				{
					tok = GetNext();
				}
				m_RecordKey = ReadIdentifier();
			}
			else if (tok.GetKeyword() == CCobolKeywordList.FILE)
			{
				tok = GetNext();
				if (tok.GetKeyword() == CCobolKeywordList.STATUS)
				{
					tok = GetNext();
				}
				if (tok.GetKeyword() == CCobolKeywordList.IS)
				{
					tok = GetNext();
				}
				m_FileStatus = ReadIdentifier();
			}
			else if (tok.GetKeyword() == CCobolKeywordList.ACCESS)
			{
				tok = GetNext() ;
				if (tok.GetKeyword() == CCobolKeywordList.MODE)
				{
					tok = GetNext();
				}
				if (tok.GetKeyword() == CCobolKeywordList.IS)
				{
					tok = GetNext();
				}
				if (tok.GetKeyword() == CCobolKeywordList.DYNAMIC)
				{
					m_bAccessModeDynamic = true ;
					m_bAccessModeRandom = false ;
					m_bAccessModeSequential = false ;
					GetNext();
				}
				else if (tok.GetKeyword() == CCobolKeywordList.SEQUENTIAL)
				{
					m_bAccessModeDynamic = false ;
					m_bAccessModeRandom = false ;
					m_bAccessModeSequential = true ;
					GetNext();
				}
				else if (tok.GetKeyword() == CCobolKeywordList.RANDOM)
				{
					m_bAccessModeDynamic = false ;
					m_bAccessModeRandom = true ;
					m_bAccessModeSequential = false ;
					GetNext();
				}
				else
				{
					Transcoder.logError(tok.getLine(), "Unexpecting token");
					return false ;
				}
			}
			else if (tok.GetKeyword() == CCobolKeywordList.ORGANIZATION)
			{
				tok = GetNext() ;
				if (tok.GetKeyword() == CCobolKeywordList.IS)
				{
					tok = GetNext() ;
				}
				if (tok.GetKeyword() == CCobolKeywordList.LINE)
				{
					tok = GetNext() ;
				}
				if (tok.GetKeyword() == CCobolKeywordList.SEQUENTIAL)
				{
					GetNext() ;
					m_bOrganizationSequential = true ;
					m_bOrganizationIndexed = false ;
				}
				else if (tok.GetKeyword() == CCobolKeywordList.INDEXED)
				{
					GetNext() ;
					m_bOrganizationSequential = false ;
					m_bOrganizationIndexed = true ;
				}
				else
				{
					Transcoder.logError(getLine(), "Error parsing SELECT");
					return false ;
				}
			}
			else
			{
				Transcoder.logError(getLine(), "Error parsing SELECT");
				return false ;
			}
		}
		return true ;
	}
	/* (non-Javadoc)
	 * @see parser.CBaseElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element eFile = root.createElement("FileSelect");
		
		Element eName = root.createElement("FileName");
		m_FileName.ExportTo(eName, root);
		eFile.appendChild(eName);
		
		Element eRef = root.createElement("Reference");
		eFile.appendChild(eRef);
		m_FileReference.ExportTo(eRef, root);
		
		if (m_RecordKey != null)
		{
			Element eKey = root.createElement("RecordKey");
			eFile.appendChild(eKey);
			m_RecordKey.ExportTo(eKey, root);
		}
		
		if (m_FileStatus != null)
		{
			Element eSt = root.createElement("FileStatus");
			eFile.appendChild(eSt);
			m_FileStatus.ExportTo(eSt, root);
		}
		
		if (m_bOrganizationIndexed)
		{
			eFile.setAttribute("Organization", "Indexed");
		}
		else if (m_bOrganizationSequential)
		{
			eFile.setAttribute("Organization", "Sequential");
		}
		
		if (m_bAccessModeDynamic)
		{
			eFile.setAttribute("AccessMode", "Dynamic");
		}
		else if (m_bAccessModeRandom)
		{
			eFile.setAttribute("AccessMode", "Random");
		}
		else if (m_bAccessModeSequential)
		{
			eFile.setAttribute("AccessMode", "Sequential");
		} 
		return eFile;
	}
	
	protected CIdentifier m_FileReference = null ;
	protected CTerminal m_FileName = null ;
	protected CIdentifier m_RecordKey = null ;
	protected CIdentifier m_FileStatus = null ;
	protected boolean m_bOrganizationSequential = false ;
	protected boolean m_bOrganizationIndexed  = false ;
	protected boolean m_bAccessModeRandom = false ;
	protected boolean m_bAccessModeSequential = false ;
	protected boolean m_bAccessModeDynamic = false ;
}
