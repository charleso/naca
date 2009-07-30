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
import semantic.Verbs.CEntityAccept.AcceptMode;
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
		if (m_From == AcceptMode.FROM_VARIABLE) 
		{
			CDataEntity eSource = m_Source.GetDataReference(getLine(), factory) ;
			eAcc.AcceptFromVariable(eVar, eSource) ;
		}
		else if (m_From != null)
		{
			eAcc.AcceptFrom(m_From, eVar) ;
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
				m_From = AcceptMode.FROM_DATE ;
			}
			else if (tok.GetKeyword() == CCobolKeywordList.DAY)
			{
				tok = GetNext() ;
				m_From = AcceptMode.FROM_DAY ;
			}
			else if (tok.GetKeyword() == CCobolKeywordList.DAY_OF_WEEK)
			{
				tok = GetNext() ;
				m_From = AcceptMode.FROM_DAYOFWEEK ;
			}
			else if (tok.GetKeyword() == CCobolKeywordList.TIME)
			{
				tok = GetNext() ;
				m_From = AcceptMode.FROM_TIME ;
			}
			else if (tok.GetKeyword() == CCobolKeywordList.CONSOLE)
			{
				tok = GetNext() ;
				m_From = AcceptMode.FROM_INPUT ;
			}
			else if (tok.GetKeyword() == CCobolKeywordList.ENVIRONMENT_VALUE)
			{
				tok = GetNext() ;
				m_From = AcceptMode.FROM_ENVIRONMENT_VALUE ;
			}
			else
			{
				m_Source = ReadIdentifier();
				if (m_Source != null)
				{
					m_From = AcceptMode.FROM_VARIABLE ;
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
			m_From = AcceptMode.FROM_INPUT ;
		}
		return true ;
	}
	protected Element ExportCustom(Document root)
	{
		Element eAcc = root.createElement("Accept");
		if (m_From == AcceptMode.FROM_DATE)
		{
			Element eFrom = root.createElement("FromDate");
			eAcc.appendChild(eFrom);
		}
		else if (m_From == AcceptMode.FROM_DAY)
		{
			Element eFrom = root.createElement("FromDay");
			eAcc.appendChild(eFrom);
		}
		else if (m_From == AcceptMode.FROM_DAYOFWEEK)
		{
			Element eFrom = root.createElement("FromDayOfWeek");
			eAcc.appendChild(eFrom);
		}
		else if (m_From == AcceptMode.FROM_INPUT)
		{
			Element eFrom = root.createElement("FromInput");
			eAcc.appendChild(eFrom);
		}
		else if (m_From == AcceptMode.FROM_TIME)
		{
			Element eFrom = root.createElement("FromTime");
			eAcc.appendChild(eFrom);
		}
		else if (m_From == AcceptMode.FROM_VARIABLE)
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
	protected AcceptMode m_From ;
	
}
