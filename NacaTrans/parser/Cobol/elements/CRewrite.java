/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 8 sept. 2004
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
import semantic.Verbs.CEntityRewriteFile;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CRewrite extends CCobolElement
{

	/**
	 * @param line
	 */
	public CRewrite(int line)
	{
		super(line);
	}
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityRewriteFile eWrite = factory.NewEntityRewriteFile(getLine()) ;
		parent.AddChild(eWrite) ;
		
		CEntityFileDescriptor eFD = factory.m_ProgramCatalog.getFileDescriptor(m_FileDesc.GetName()) ;
		if (eFD != null)
		{
			CDataEntity eData = null ;
			if (m_DataRef != null)
			{
				eData = m_DataRef.GetDataReference(getLine(), factory) ;
			}
			eWrite.setFileDescriptor(eFD, eData) ;
		}
		else
		{
			Transcoder.logError(getLine(), "File descriptor not found : " + m_FileDesc.GetName());
		}
		return eWrite ;
	}
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() != CCobolKeywordList.REWRITE)
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
			m_DataRef = ReadIdentifier();
			tok = GetCurrentToken() ;
		}
		
		if (tok.GetKeyword() == CCobolKeywordList.INVALID)
		{
			tok = GetNext();
			if (tok.GetKeyword() == CCobolKeywordList.KEY)
			{
				tok = GetNext();
			}
			m_OnInvalidKey = new CGenericBloc("OnInvalidKey",  tok.getLine());
			if (!Parse(m_OnInvalidKey))
			{
				return false ;
			}
		}
		return true;
	}
	protected Element ExportCustom(Document root)
	{
		Element eRW = root.createElement("ReWrite");
		Element eRecord = root.createElement("File");
		eRW.appendChild(eRecord);
		m_FileDesc.ExportTo(eRecord, root);
		
		if (m_DataRef != null)
		{
			Element e = root.createElement("From");
			m_DataRef.ExportTo(e, root);
			eRW.appendChild(e);
		}
		return eRW;
	}

	protected CIdentifier m_FileDesc = null ;
	protected CIdentifier m_DataRef = null ;
	protected CGenericBloc m_OnInvalidKey = null ;
}
