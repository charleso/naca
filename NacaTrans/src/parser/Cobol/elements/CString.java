/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 13 août 2004
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
import semantic.CDataEntity;
import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.Verbs.CEntityStringConcat;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CString extends CCobolElement
{

	protected class CStringConcatItem
	{
		CStringConcatItem(CTerminal id, CTerminal t)
		{
			m_Value = id ;
			m_Until = t ;
		} 
		CTerminal m_Value = null ;
		CTerminal m_Until = null ; // if null => DELIMITED BY SIZE
	} 
	/**
	 * @param line
	 */
	public CString(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityStringConcat eConcat = factory.NewEntityStringConcat(getLine());
		parent.AddChild(eConcat);

		CDataEntity eVariable = m_Variable.GetDataReference(getLine(), factory);
		eVariable.RegisterWritingAction(eConcat) ;
		if (m_DestIndexStart != null)
		{
			CDataEntity eStart = m_DestIndexStart.GetDataEntity(getLine(), factory) ;
			eConcat.SetVariable(eVariable, eStart);
			eStart.RegisterReadingAction(eConcat) ;
		}
		else
		{
			eConcat.SetVariable(eVariable);
		}

		for (int i =0; i<m_arrConcatItems.size(); i++)
		{
			CStringConcatItem item = m_arrConcatItems.get(i);
			CDataEntity eItem = item.m_Value.GetDataEntity(getLine(), factory);
			if (item.m_Until != null)
			{
				CDataEntity eUntil = item.m_Until.GetDataEntity(getLine(), factory);
				if (eUntil == null && !item.m_Until.IsReference() && (item.m_Until.GetValue().equals("SPACES") || item.m_Until.GetValue().equals("SPACE")))
				{
					char [] arr = {' '} ;
					eUntil = factory.NewEntityString(arr);
				}
				eConcat.AddItem(eItem, eUntil) ;
			}
			else
			{
				eConcat.AddItem(eItem) ;
			}
			eItem.RegisterReadingAction(eConcat) ;
		}
		
		if (m_Bloc != null)
		{
			CBaseLanguageEntity e = m_Bloc.DoSemanticAnalysis(eConcat, factory) ;
			eConcat.AddChildSpecial(e);
		}
		return eConcat;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken() ;
		if (tok.GetKeyword() != CCobolKeywordList.STRING)
		{
			return false ;
		}
		CGlobalEntityCounter.GetInstance().CountCobolVerb(tok.GetKeyword().m_Name) ;
		tok = GetNext();
		boolean bDone = false ;
		Vector<CTerminal> arrTerms = new Vector<CTerminal>() ;  // array used to save read terminals before reading the 'delimited by' statement
		// TXT-LIB-AA TXT-A DELIMITED BY '*'   <=> TXT-LIB-AA DELIMITED BY '*' TXT-A  DELIMITED BY '*' 	
		while (!bDone)
		{
			tok = GetCurrentToken(); 
			if (tok.GetType() == CTokenType.IDENTIFIER || tok.GetType() == CTokenType.STRING || tok.GetType() == CTokenType.CONSTANT)
			{
				CTerminal id = ReadTerminal() ;
				SkipComma();
				tok = GetCurrentToken();
				if (tok.GetKeyword() == CCobolKeywordList.DELIMITED)
				{
					tok = GetNext() ;
					if (tok.GetKeyword() == CCobolKeywordList.BY)
					{
						tok = GetNext() ;
					}
					if (tok.GetKeyword() == CCobolKeywordList.SIZE)
					{
						for (CTerminal idsav : arrTerms)
						{
							m_arrConcatItems.add(new CStringConcatItem(idsav, null));	
						}
						m_arrConcatItems.add(new CStringConcatItem(id, null));	
						GetNext(); 
					}
					else 
					{
						CTerminal term = ReadTerminal() ;
						for (CTerminal idsav : arrTerms)
						{
							m_arrConcatItems.add(new CStringConcatItem(idsav, term));	
						}
						m_arrConcatItems.add(new CStringConcatItem(id, term));	
					}
					arrTerms.clear() ;
				}
				else
				{
					arrTerms.add(id) ;
//					m_arrConcatItems.add(new CStringConcatItem(id, null));	
				}
				SkipComma();
			}
			else if (tok.GetKeyword() == CCobolKeywordList.INTO)
			{
				GetNext();
				m_Variable = ReadIdentifier();
				tok = GetCurrentToken();
				if (tok.GetKeyword()== CCobolKeywordList.WITH)
				{
					tok = GetNext();
				}
				if (tok.GetKeyword() == CCobolKeywordList.POINTER)
				{
					tok = GetNext();
					m_DestIndexStart = ReadTerminal() ;
				}
				bDone = true ;
			}
			else
			{
				Transcoder.logError(getLine(), "Unexpecting token : " + tok.GetValue()) ;
				return false; 
			}
		}
		tok = GetCurrentToken();
		if (tok.GetKeyword() == CCobolKeywordList.ON)
		{
			tok = GetNext();
			if (tok.GetKeyword() == CCobolKeywordList.OVERFLOW)
			{
				GetNext();
				m_Bloc = new CGenericBloc("OnOverflow", GetCurrentToken().getLine()) ;
				if (!Parse(m_Bloc))
				{
					Transcoder.logError(getLine(), "Failure while parsing THEN bloc") ;
					return false ;
				}		
				tok = GetCurrentToken();
			}
		}
		if (tok.GetKeyword() == CCobolKeywordList.END_STRING)
		{
			GetNext();
		}
		if (m_Variable != null)
		{
			return true;
		}
		else
		{
			return false ;
		}
	}

	private void SkipComma()
	{
		if (GetCurrentToken().GetType() == CTokenType.COMMA)
		{
			GetNext();
		}
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element eST = root.createElement("StringConcat") ;
		Element eInto = root.createElement("Into") ;
		eST.appendChild(eInto);
		if (m_Variable != null)
		{
			m_Variable.ExportTo(eInto, root);
		}
		for (int i =0; i<m_arrConcatItems.size(); i++)
		{
			CStringConcatItem item = m_arrConcatItems.get(i) ;
			Element eItem = root.createElement("Item") ;
			eST.appendChild(eItem);
			item.m_Value.ExportTo(eItem, root) ;
			if (item.m_Until != null)
			{
				Element eUntil = root.createElement("DelimitedBy") ;
				eItem.appendChild(eUntil);
				item.m_Until.ExportTo(eUntil, root) ;
			}  
		}
		return eST;
	}

	protected CIdentifier m_Variable = null ;
	protected Vector<CStringConcatItem> m_arrConcatItems = new Vector<CStringConcatItem>() ;
	protected CBlocElement m_Bloc = null ;
	protected CTerminal m_DestIndexStart = null ;
}
