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
 * Created on 13 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.Cobol.elements;

import jlib.misc.NumberParser;
import lexer.CBaseToken;
import lexer.CTokenType;
import lexer.Cobol.CCobolKeywordList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.org.apache.xml.internal.utils.StringVector;

import parser.Cobol.CCobolElement;
import semantic.CBaseEntityFactory;
import semantic.CBaseExternalEntity;
import semantic.CBaseLanguageEntity;
import semantic.CEntityInline;
import utils.CGlobalEntityCounter;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CCopyRec extends CCobolElement
{

	/**
	 * @param line
	 */
	public CCopyRec(int line)
	{
		super(line);
	}

	/* (non-Javadoc)
	 * @see parser.CLanguageElement#DoCustomSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	protected CBaseLanguageEntity DoCustomSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CGlobalEntityCounter.GetInstance().RegisterCopy(parent.GetProgramName(), m_Identifier) ;

		String csRenamePattern = "" ;
		int nReplaceLevel = 0 ;
		if (m_arrOption.size()>0)
		{
			for (int i=0; i<m_arrOption.size(); i++)
			{
				String cs1 = m_arrOption.elementAt(i);
				int n1 = NumberParser.getAsInt(cs1) ;
				if (n1 > 0)
				{
					nReplaceLevel = n1 ;
				}
				else
				{
					csRenamePattern = cs1 ;
				}
			}
		}
		
		CBaseExternalEntity e = factory.m_ProgramCatalog.GetExternalDataReference(m_Identifier, csRenamePattern, factory) ;
		if (e == null)
		{
			CGlobalEntityCounter.GetInstance().RegisterMissingCopy(parent.GetProgramName(), m_Identifier) ;
			return null ;
		}

		CBaseLanguageEntity ent = parent ;
		if (nReplaceLevel > 0)
		{
			e.ReplaceLevel(1, nReplaceLevel) ;
			ent = parent.FindLastEntityAvailableForLevel(nReplaceLevel);
		}

		CEntityInline eil = factory.NewEntityInline(getLine(), e) ;
		e.InitDependences(factory) ; 
		//parent.AddChild(eil) ;
		ent.AddChild(eil) ;
		if (ent != null)
		{
			e.SetParent(ent);
		}
		return eil ;

	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#Parse(lexer.CTokenList)
	 */
	protected boolean DoParsing()
	{
		CBaseToken tok = GetCurrentToken();
		if (tok.GetKeyword()!= CCobolKeywordList.COPYREC)
		{
			return false;
		}
		CGlobalEntityCounter.GetInstance().CountCobolVerb(tok.GetKeyword().m_Name) ;

		int line = tok.getLine() ; 
		tok = GetNext();
		if (tok.GetType() == CTokenType.IDENTIFIER)
		{
			m_Identifier = tok.GetValue();
		}
		else
		{
			Transcoder.logError(tok.getLine(), "Expecting identifier");
			return false ;
		}
		tok = GetNext() ;
		while (tok.GetType() != CTokenType.DOT && !tok.IsKeyword())
		{
			if (tok.GetType() == CTokenType.LEFT_BRACKET)
			{
				tok = GetNext() ;
				while (tok.GetType() != CTokenType.RIGHT_BRACKET)
				{
					tok = GetNext();
				}
				tok = GetNext();
			}
			else if (tok.GetType() == CTokenType.COMMA)
			{
				tok = GetNext();
			}
			else if (tok.GetType() == CTokenType.NUMBER && tok.GetValue().equals("01"))
			{
				break ;
			}
			else if ((tok.GetType() == CTokenType.NUMBER || tok.GetType() == CTokenType.IDENTIFIER) && tok.getLine() == line)
			{
				m_arrOption.addElement(tok.GetValue());
				tok = GetNext() ;
			}
//			else if (tok.GetType() == CTokenType.COMMENT)
//			{
//				ParseComment();
//				tok=GetCurrentToken();
//			}
			else
			{
				break ;
			}
			
		}
		if (tok.GetType() == CTokenType.DOT)
		{
			GetNext() ;
		}
		return ParseContent() ;
	}
	
	protected boolean ParseContent()
	{
		boolean bDone = false ;
		while (!bDone)
		{
			CBaseToken tokEntry = GetCurrentToken();
			if (tokEntry.GetType()==CTokenType.NUMBER)
			{
				int level = tokEntry.GetIntValue();
				if (level > 1)
				{
					CCobolElement eEntry = new CWorkingEntry(tokEntry.getLine()) ;
					if (!Parse(eEntry))
					{
						Transcoder.logError(getLine(), "Error while parsing wotking entry") ;
						return false ;
					}
					AddChild(eEntry) ;
				}
				else
				{
					bDone = true ; // this entry is a top-level entry
				}
			}
			else
			{
				bDone = true ;	// this token is not parsed by this function, go back to caller
			}
		}
		return true ;
	}

	/* (non-Javadoc)
	 * @see parser.CBaseElement#ExportCustom(org.w3c.dom.Document)
	 */
	protected Element ExportCustom(Document root)
	{
		Element eCopy = root.createElement("CopyRec");
		eCopy.setAttribute("Reference", m_Identifier) ;
		for (int i=0; i<m_arrOption.size(); i++)
		{
			String cs = "Option"+i ;
			String val = m_arrOption.elementAt(i);
			eCopy.setAttribute(cs, val);		
		}
		return eCopy;
	}
	
	protected String m_Identifier = ""  ;
	protected StringVector m_arrOption = new StringVector() ; 

}
