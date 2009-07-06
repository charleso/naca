/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 12 août 2004
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
import parser.expression.CTerminal;
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CEntityAddressReference;
import semantic.CEntityMoveReference;
import semantic.Verbs.CEntityAddTo;
import semantic.Verbs.CEntityAssign;
import semantic.Verbs.CEntitySubtractTo;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CSet extends CCobolElement
{

	/**
	 * @param line
	 */
	public CSet(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		if (m_AddressOfFrom != null && m_AddressOfTo != null)
		{
			CDataEntity eFrom = m_AddressOfFrom.GetDataReference(getLine(), factory);
			CDataEntity eTo = m_AddressOfTo.GetDataReference(getLine(), factory);
			CEntityAddressReference eAddFrom = factory.NewEntityAddressReference(eFrom);
			CEntityAddressReference eAddTo = factory.NewEntityAddressReference(eTo);
			CEntityMoveReference eMove = factory.NewEntityMoveReference(getLine()) ;
			eMove.SetMoveReference(eAddFrom, eAddTo) ;
			parent.AddChild(eMove);
			return eMove;
		}
		else if (m_arrIdTo.size()>0)
		{
			//CBaseDataEntity eTo = m_IdTo.GetDataReference(factory);
			if (m_ValFrom != null)
			{
				if (m_ValFrom.IsReference())
				{
					CDataEntity eFrom = m_ValFrom.GetDataEntity(getLine(), factory) ;
					CEntityAssign eAssign = factory.NewEntityAssign(getLine());
					eAssign.SetValue(eFrom);
					for (int i=0; i<m_arrIdTo.size(); i++)
					{
						CIdentifier idTo = m_arrIdTo.get(i);
						CDataEntity eTo = idTo.GetDataReference(getLine(), factory);
						eAssign.AddRefTo(eTo);
					}
					parent.AddChild(eAssign);
					return eAssign ;
				}
				else
				{
					for (int i=0; i<m_arrIdTo.size(); i++)
					{
						CIdentifier idTo = m_arrIdTo.get(i);
						CDataEntity eTo = idTo.GetDataReference(getLine(), factory);
						CBaseActionEntity eAssign = eTo.GetSpecialAssignment(m_ValFrom, factory, i) ;
						if (eAssign == null)
						{
							CDataEntity eVal = m_ValFrom.GetDataEntity(getLine(), factory);
							CEntityAssign eAssgn = factory.NewEntityAssign(getLine());
							eAssgn.SetValue(eVal);
							eAssgn.AddRefTo(eTo);
							parent.AddChild(eAssgn) ;
						}
						else
						{
							parent.AddChild(eAssign) ;
						}
					}
					return parent; 
				}
			}
			else if (m_DownByValue != null)
			{
				for (int i=0; i<m_arrIdTo.size(); i++)
				{
					CIdentifier idTo = m_arrIdTo.get(i);
					CDataEntity eTo = idTo.GetDataReference(getLine(), factory);
					CEntitySubtractTo eSub = factory.NewEntitySubtractTo(getLine());
					CDataEntity val = m_DownByValue.GetDataEntity(getLine(), factory);
					eSub.SetSubstract(eTo, val, null) ;
					parent.AddChild(eSub);
				}
				return null ;
			}
			else if (m_UpByValue != null)
			{
				for (int i=0; i<m_arrIdTo.size(); i++)
				{
					CIdentifier idTo = m_arrIdTo.get(i);
					CDataEntity eTo = idTo.GetDataReference(getLine(), factory);
					CEntityAddTo eAdd = factory.NewEntityAddTo(getLine());
					CDataEntity val = m_UpByValue.GetDataEntity(getLine(), factory);
					eAdd.SetAddValue(val) ;
					eAdd.SetAddDest(eTo) ;
					parent.AddChild(eAdd);
				}
				return null ;
			}
		}
		Transcoder.logError(getLine(), "Unexpecting situation");
		return null;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() != CCobolKeywordList.SET)
		{
			return false ;
		}
		CGlobalEntityCounter.GetInstance().CountCobolVerb(tok.GetKeyword().m_Name) ;
		tok = GetNext(); 
		if (tok.GetKeyword() == CCobolKeywordList.ADDRESS)
		{
			tok = GetNext() ;
			if (tok.GetKeyword() != CCobolKeywordList.OF)
			{
				Transcoder.logError(tok.getLine(), "Expecting OF");
				return false ;
			}
			tok = GetNext() ;
			m_AddressOfTo = ReadIdentifier() ;
		}
		else
		{
			while (tok.GetType() == CTokenType.IDENTIFIER)
			{
				CIdentifier idTo = ReadIdentifier() ;
				if (idTo != null)
				{
					m_arrIdTo.add(idTo);
				}
				else
				{
					break ;
				}
			}
		}		
		
		tok = GetCurrentToken() ;
		if (tok.GetKeyword() == CCobolKeywordList.TO)
		{
			tok = GetNext(); 
			if (tok.GetKeyword() == CCobolKeywordList.ADDRESS)
			{
				tok = GetNext() ;
				if (tok.GetKeyword() != CCobolKeywordList.OF)
				{
					Transcoder.logError(tok.getLine(), "Expecting OF");
					return false ;
				}
				tok = GetNext() ;
				m_AddressOfFrom = ReadIdentifier() ;
			}
			else
			{
				m_ValFrom = ReadTerminal();
			}
		}
		else if (tok.GetKeyword() == CCobolKeywordList.DOWN)
		{
			tok = GetNext() ;
			if (tok.GetKeyword() == CCobolKeywordList.BY)
			{
				tok = GetNext();
			}
			m_DownByValue = ReadTerminal();
		}
		else if (tok.GetKeyword() == CCobolKeywordList.UP)
		{
			tok = GetNext() ;
			if (tok.GetKeyword() == CCobolKeywordList.BY)
			{
				tok = GetNext();
			}
			m_UpByValue = ReadTerminal();
		}
		else
		{
			Transcoder.logError(tok.getLine(), "Expecting TO");
			return false ;
		}					
		return true;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element eSet = root.createElement("Set") ;
		if (m_arrIdTo.size()>0)
		{
			for (int i=0; i<m_arrIdTo.size();i++)
			{
				Element eTo = root.createElement("Variable")  ;
				eSet.appendChild(eTo);
				CIdentifier idTo = m_arrIdTo.get(i); 
				idTo.ExportTo(eTo, root) ;
			}
		}
		else if (m_AddressOfTo != null)
		{
			Element eTo = root.createElement("Variable")  ;
			eSet.appendChild(eTo);
			Element eAdd = root.createElement("AddressOf");
			eTo.appendChild(eAdd) ;
			m_AddressOfTo.ExportTo(eAdd, root) ;
		}
		if (m_DownByValue == null && m_UpByValue == null)
		{
			Element eFrom = root.createElement("From") ;
			eSet.appendChild(eFrom) ;
			if (m_ValFrom != null)
			{
				m_ValFrom.ExportTo(eFrom, root) ;
			}
			else if (m_AddressOfFrom != null)
			{
				Element eAdd = root.createElement("AddressOf");
				eFrom.appendChild(eAdd) ;
				m_AddressOfFrom.ExportTo(eAdd, root) ;
			}
		}
		else if (m_DownByValue != null && m_UpByValue == null)
		{
			Element e = root.createElement("DownByValue");
			eSet.appendChild(e);
			m_DownByValue.ExportTo(e, root) ;
		}
		else if (m_DownByValue == null && m_UpByValue != null)
		{
			Element e = root.createElement("UpByValue");
			eSet.appendChild(e);
			m_UpByValue.ExportTo(e, root) ;
		}
		return eSet ;
	}

	protected CIdentifier m_AddressOfFrom = null ;
	protected CIdentifier m_AddressOfTo = null ;
	protected CTerminal m_ValFrom = null ;
	protected Vector<CIdentifier> m_arrIdTo = new Vector<CIdentifier>() ;
	protected CTerminal m_DownByValue = null ;
	protected CTerminal m_UpByValue = null ;
}
