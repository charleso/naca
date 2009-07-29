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

import java.util.Vector;

import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CIdentifier;
import parser.Cobol.CCobolElement;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CEntityFileDescriptor;
import semantic.Verbs.CEntityCloseFile;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CClose extends CCobolElement
{
	/**
	 * @param line
	 */
	public CClose(int line)
	{
		super(line);
	}
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		for (int i=0; i<m_arrFileDesc.size();i++)
		{
			CIdentifier id = m_arrFileDesc.get(i) ;
			CEntityFileDescriptor fd = factory.m_ProgramCatalog.getFileDescriptor(id.GetName()) ;
			if (fd != null)
			{
				CEntityCloseFile eClose = factory.NewEntityCloseFile(getLine()) ;
				parent.AddChild(eClose) ;
				eClose.setFileDescriptor(fd) ;
			}
			else
			{
				Transcoder.logError(getLine(), "File descriptor not found : " + id.GetName()) ;
			}
		}
		return parent;
	}
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() != CCobolKeywordList.CLOSE)
		{
			return false ;
		}
		CGlobalEntityCounter.GetInstance().CountCobolVerb(tok.GetKeyword().m_Name) ;
		
		tok = GetNext();
		CIdentifier id = ReadIdentifier();
		m_arrFileDesc.add(id) ;
		tok = GetCurrentToken() ;
		while (tok.GetType() == CTokenType.COMMA || tok.GetType() == CTokenType.IDENTIFIER)
		{
			if (tok.GetType() == CTokenType.COMMA)
			{
				tok = GetNext() ;
			}
			else
			{
				id = ReadIdentifier() ;
				if (id != null)
				{
					m_arrFileDesc.add(id) ;
					tok = GetCurrentToken() ;
				}
			}
		}
		return true ;
	}
	protected Element ExportCustom(Document root)
	{
		Element eClose = root.createElement("Close");
		for (int i = 0; i<m_arrFileDesc.size();i++)
		{
			CIdentifier id = m_arrFileDesc.get(i);
			Element eFile = root.createElement("File");
			eClose.appendChild(eFile);
			id.ExportTo(eFile, root) ;
		}
		return eClose;
	}
	
	protected Vector<CIdentifier> m_arrFileDesc = new Vector<CIdentifier>() ;
}
