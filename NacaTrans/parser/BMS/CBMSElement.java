/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 30 juil. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.BMS;

import jlib.xml.Tag;
import jlib.xml.TagCursor;
import lexer.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CBaseElement;

import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.forms.CResourceStrings;
import utils.Transcoder;


/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CBMSElement extends CBaseElement
{
	public enum EBMSElementType
	{
		ARRAY,
		MAPSET,
		MAP,
		FIELD,
		GROUP ;
	} 
	public abstract EBMSElementType GetType() ;
	
	
	//public abstract CBaseResourceEntity DoSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory) ;
	
	protected CBMSElement(String name, int line)
	{
		super(line) ;
		m_Name = name ;
	}

	public String getName()
	{
		return m_Name ;
	}
	
	protected void setName(String cs)
	{
		m_Name = cs;
	}

	public void AddElement(CBMSElement e)
	{
		AddChild(e) ;
	}

	protected abstract Element DoExportCustom(Document root) ;
	protected Element ExportCustom(Document root)
	{
		Element e = DoExportCustom(root);
		e.setAttribute("Name", m_Name) ;
		return e ;
	}

	protected abstract boolean InterpretKeyword(CReservedKeyword kw, CTokenList lstTokens) ;
	
	protected boolean DoParsing()
	{
		boolean bDone = false ;
		while (!bDone)
		{
			CBaseToken tokMapSet = m_lstTokens.GetCurrentToken() ;
			if (tokMapSet.GetType() == CTokenType.KEYWORD)
			{
				CBaseToken tokNext = m_lstTokens.GetNext() ;
				if (tokNext.GetType() != CTokenType.EQUALS)
				{
					Transcoder.logError(getLine(), "Expecting EQUALS after " + tokMapSet.GetValue());
					return false ;
				}
				m_lstTokens.GetNext() ;
				if (!InterpretKeyword(tokMapSet.GetKeyword(), m_lstTokens))
				{
					Transcoder.logError(getLine(), "Problem parsing keyword : " + tokMapSet.GetValue());
					return false ;
				}
				tokNext = m_lstTokens.GetCurrentToken() ;
				if (tokNext.GetType() == CTokenType.COMMA)
				{
					m_lstTokens.GetNext() ;
				}
				else
				{
					bDone = true ;
				}					
			}
			else if(tokMapSet.GetType() == CTokenType.STAR)
			{
				m_lstTokens.GetNext() ;
			}
			else if(tokMapSet.GetType() == CTokenType.IDENTIFIER)
			{
				bDone = true ;
			}
			else if(tokMapSet.GetType() == CTokenType.COMMENTS)
			{
				String com = tokMapSet.GetValue().trim() ;
				if (com.startsWith("'") && com.endsWith("'"))
				{
					bDone = true ;
				}
				else
				{
					ParseComment() ;
				}
			}
			else
			{
				Transcoder.logError(getLine(), "Unrecognized token : " + tokMapSet.GetValue());
				m_lstTokens.GetNext() ;
			}  
		} 
		return true ;
	}

	protected String m_Name = "" ;

	/* (non-Javadoc)
	 * @see parser.CBaseElement#DoSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	public CBaseLanguageEntity DoSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		return null;
	}
	
	public abstract CResourceStrings GetResourceStrings() ;
	public abstract void SetResourceStrings(CResourceStrings res) ;
	
	// Used form XML resource loading and export as .res and .java
	public abstract CBMSElement parseXMLResource(Tag tagCurrent);
	public abstract CBMSElement loadTagParameters(Tag tagCurrent);
}
