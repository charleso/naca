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
import semantic.Verbs.CEntityAccept;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CAccept extends CCobolElement
{
	/**
	 * @param line
	 */
	public CAccept(int line)
	{
		super(line);
	}
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityAccept eAcc = factory.NewEntityAccept(getLine()) ;
		parent.AddChild(eAcc) ;
		CDataEntity eVar = m_Variable.GetDataReference(getLine(), factory) ;
		if (m_bFromDate)
		{
			eAcc.AcceptFromDate(eVar) ;
		}
		else if (m_bFromDay)
		{
			eAcc.AcceptFromDay(eVar) ;
		}
		else if (m_bFromDayOfWeek)
		{
			eAcc.AcceptFromDayOfWeek(eVar) ;
		}
		else if (m_bFromInput)
		{
			eAcc.AcceptFromInput(eVar) ;
		}
		else  if (m_bFromTime)
		{
			eAcc.AcceptFromTime(eVar) ;
		}
		else if (m_bFromVariable) 
		{
			CDataEntity eSource = m_Source.GetDataReference(getLine(), factory) ;
			eAcc.AcceptFromVariable(eVar, eSource) ;
		}
		else 
		{
			Transcoder.logError(getLine(), "Unmanaged situation with ACCEPT") ;
			return null ;
		}
		return eAcc;
	}
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() != CCobolKeywordList.ACCEPT)
		{
			return false ;
		} 
		CGlobalEntityCounter.GetInstance().CountCobolVerb(tok.GetKeyword().m_Name) ;
	
		tok = GetNext() ;
		m_Variable = ReadIdentifier();
		
		tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.FROM)
		{
			tok = GetNext() ;
			if (tok.GetKeyword() == CCobolKeywordList.DATE)
			{
				tok = GetNext() ;
				m_bFromInput = false ;
				m_bFromVariable = false ;
				m_bFromDate = true ;
				m_bFromTime = false ;
				m_bFromDay = false ;
				m_bFromDayOfWeek = false ;
			}
			else if (tok.GetKeyword() == CCobolKeywordList.DAY)
			{
				tok = GetNext() ;
				m_bFromInput = false ;
				m_bFromVariable = false ;
				m_bFromDate = false ;
				m_bFromTime = false ;
				m_bFromDay = true ;
				m_bFromDayOfWeek = false ;
			}
			else if (tok.GetKeyword() == CCobolKeywordList.DAY_OF_WEEK)
			{
				tok = GetNext() ;
				m_bFromInput = false ;
				m_bFromVariable = false ;
				m_bFromDate = false ;
				m_bFromTime = false ;
				m_bFromDay = false ;
				m_bFromDayOfWeek = true ;
			}
			else if (tok.GetKeyword() == CCobolKeywordList.TIME)
			{
				tok = GetNext() ;
				m_bFromInput = false ;
				m_bFromVariable = false ;
				m_bFromDate = false ;
				m_bFromTime = true ;
				m_bFromDay = false ;
				m_bFromDayOfWeek = false ;
			}
			else if (tok.GetKeyword() == CCobolKeywordList.CONSOLE)
			{
				tok = GetNext() ;
				m_bFromInput = true ;
				m_bFromVariable = false ;
				m_bFromDate = false ;
				m_bFromTime = false ;
				m_bFromDay = false ;
				m_bFromDayOfWeek = false ;
			}
			else
			{
				m_Source = ReadIdentifier();
				if (m_Source != null)
				{
					m_bFromInput = false ;
					m_bFromVariable = true ;
					m_bFromDate = false ;
					m_bFromTime = false ;
					m_bFromDay = false ;
					m_bFromDayOfWeek = false ;
				}
				else
				{
					Transcoder.logError(getLine(), "Unexpecting situation");
					return false ;
				}
			}
		}
		else
		{
			m_bFromInput = true ;
			m_bFromVariable = false ;
			m_bFromDate = false ;
			m_bFromTime = false ;
			m_bFromDay = false ;
			m_bFromDayOfWeek = false ;
		}
		return true ;
	}
	protected Element ExportCustom(Document root)
	{
		Element eAcc = root.createElement("Accept");
		if (m_bFromDate)
		{
			Element eFrom = root.createElement("FromDate");
			eAcc.appendChild(eFrom);
		}
		else if (m_bFromDay)
		{
			Element eFrom = root.createElement("FromDay");
			eAcc.appendChild(eFrom);
		}
		else if (m_bFromDayOfWeek)
		{
			Element eFrom = root.createElement("FromDayOfWeek");
			eAcc.appendChild(eFrom);
		}
		else if (m_bFromInput)
		{
			Element eFrom = root.createElement("FromInput");
			eAcc.appendChild(eFrom);
		}
		else if (m_bFromTime)
		{
			Element eFrom = root.createElement("FromTime");
			eAcc.appendChild(eFrom);
		}
		else if (m_bFromVariable)
		{
			Element eFrom = root.createElement("From");
			eAcc.appendChild(eFrom);
			m_Source.ExportTo(eFrom, root) ;
		}
		Element eTo = root.createElement("To");
		m_Variable.ExportTo(eTo, root);
		eAcc.appendChild(eTo);
		return eAcc;
	}
	
	protected CIdentifier m_Variable = null ; 
	protected CIdentifier m_Source = null;
	protected boolean m_bFromInput = false ;
	protected boolean m_bFromVariable = false ;
	protected boolean m_bFromDate = false ;
	protected boolean m_bFromTime = false ;
	protected boolean m_bFromDay = false ;
	protected boolean m_bFromDayOfWeek = false ;
}
