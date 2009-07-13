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
import semantic.Verbs.CEntitySortReturn;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CReturn extends CCobolElement
{

	/**
	 * @param line
	 */
	public CReturn(int line)
	{
		super(line);
	}
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntitySortReturn eRet = factory.NewEntitySortReturn(getLine()) ;
		parent.AddChild(eRet) ;
		
		CEntityFileDescriptor eRef = factory.m_ProgramCatalog.getFileDescriptor(m_SortFile.GetName()) ;
		if (m_DataRef != null)
		{
			CDataEntity into = m_DataRef.GetDataReference(getLine(), factory) ;
			eRet.setDataReference(eRef, into) ;
		}
		else
		{
			eRet.setDataReference(eRef) ;
		}
		
		if (m_AtEndBloc != null)
		{
			CBaseLanguageEntity le = m_AtEndBloc.DoSemanticAnalysis(eRet, factory) ;
			eRet.SetAtEndBloc(le) ;
		}
		if (m_NotAtEndBloc != null)
		{
			CBaseLanguageEntity le = m_NotAtEndBloc.DoSemanticAnalysis(eRet, factory) ;
			eRet.SetNotAtEndBloc(le) ;
		}
		
		return eRet ;
	}
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() != CCobolKeywordList.RETURN)
		{
			return false ;
		}
		CGlobalEntityCounter.GetInstance().CountCobolVerb(tok.GetKeyword().m_Name) ;
		
		tok = GetNext() ;
		m_SortFile = ReadIdentifier();
		
		tok = GetCurrentToken();
		if (tok.GetKeyword() == CCobolKeywordList.INTO)
		{
			tok = GetNext();
			m_DataRef = ReadIdentifier();
		}
		
		tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.AT)
		{
			tok = GetNext() ;
			if (tok.GetKeyword() == CCobolKeywordList.END)
			{
				tok = GetNext() ;
				m_AtEndBloc = new CGenericBloc("AtEnd", getLine()) ;
				if (!Parse(m_AtEndBloc))
				{
					return false ;
				}
				tok = GetCurrentToken();	
			}
			else
			{
				Transcoder.logError(tok.getLine(), "Unexpecting situation");
				return false ;
			}
		}
		
		if (tok.GetKeyword() == CCobolKeywordList.NOT)
		{
			if (tok.GetKeyword() == CCobolKeywordList.AT)
			{
				tok = GetNext() ;
				if (tok.GetKeyword() == CCobolKeywordList.END)
				{
					tok = GetNext() ;
					m_NotAtEndBloc = new CGenericBloc("NotAtEnd", getLine()) ;
					if (!Parse(m_NotAtEndBloc))
					{
						return false ;
					}
					tok = GetCurrentToken();	
				}
				else
				{
					Transcoder.logError(tok.getLine(), "Unexpecting situation");
					return false ;
				}
			}
			else
			{
				Transcoder.logError(tok.getLine(), "Unexpecting situation");
				return false ;
			}
		}
		
		tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.END_RETURN)
		{
			tok = GetNext() ;
		}
		return true;
	}
	protected Element ExportCustom(Document root)
	{
		Element eReturn = root.createElement("Return") ;
		Element eRecord = root.createElement("Record");
		eReturn.appendChild(eRecord);
		m_SortFile.ExportTo(eRecord, root);
		
		if (m_DataRef != null)
		{
			Element e = root.createElement("Into");
			m_DataRef.ExportTo(e, root);
			eReturn.appendChild(e);
		}
		
		if (m_AtEndBloc != null)
		{
			Element e = m_AtEndBloc.Export(root);
			eReturn.appendChild(e);
		} 
		if (m_NotAtEndBloc != null)
		{
			Element e = m_NotAtEndBloc.Export(root);
			eReturn.appendChild(e);
		} 
		return eReturn;
	}
	
	protected CIdentifier m_SortFile = null ;
	protected CIdentifier m_DataRef = null ;
	protected CGenericBloc m_AtEndBloc = null ;
	protected CGenericBloc m_NotAtEndBloc = null ;
}
