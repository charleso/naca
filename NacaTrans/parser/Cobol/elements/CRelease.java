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
import semantic.Verbs.CEntitySortRelease;
import utils.CGlobalEntityCounter;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CRelease extends CCobolElement
{

	/**
	 * @param line
	 */
	public CRelease(int line)
	{
		super(line);
	}
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntitySortRelease eRel = factory.NewEntitySortRelease(getLine()) ;
		parent.AddChild(eRel) ;
		
		CDataEntity e = m_SortFile.GetDataReference(getLine(), factory) ;
		if (m_DataRef != null)
		{
			CDataEntity eFrom = m_DataRef.GetDataReference(getLine(), factory) ;
			eRel.setDataReference(e, eFrom) ;
		}
		else
		{
			eRel.setDataReference(e) ;
			
		}
		
		return eRel;
	}
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() != CCobolKeywordList.RELEASE)
		{
			return false ;
		}
		CGlobalEntityCounter.GetInstance().CountCobolVerb(tok.GetKeyword().m_Name) ;
		
		tok = GetNext() ;
		m_SortFile = ReadIdentifier();
		
		tok = GetCurrentToken();
		if (tok.GetKeyword() == CCobolKeywordList.FROM)
		{
			tok = GetNext();
			m_DataRef = ReadIdentifier();
		}
		return true;
	}
	protected Element ExportCustom(Document root)
	{
		Element eRelease = root.createElement("Release");
		
		Element eRecord = root.createElement("Record");
		eRelease.appendChild(eRecord);
		m_SortFile.ExportTo(eRecord, root);
		
		if (m_DataRef != null)
		{
			Element e = root.createElement("From");
			m_DataRef.ExportTo(e, root);
			eRelease.appendChild(e);
		}
		return eRelease;
	}

	protected CIdentifier m_SortFile = null ;
	protected CIdentifier m_DataRef = null ;
}
