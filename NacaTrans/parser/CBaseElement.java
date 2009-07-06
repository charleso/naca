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
package parser;

import java.util.*;

import org.apache.log4j.Logger;
import org.w3c.dom.*;

import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import utils.Transcoder;

import lexer.*;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CBaseElement
{
	private int m_line = 0 ;
	
	public int getLine()
	{
		return m_line;
	}
	
	public void setLine(int n)
	{
		m_line = n;
		Transcoder.setLine(m_line);
	}
	
	protected CTokenList m_lstTokens = null ;
	protected boolean DoParsing() 
	{
		return false ;
	};
	protected boolean DoParsing(CFlag f) 
	{
		return false ;
	};
	protected CBaseToken GetNext()
	{
		CBaseToken tok = m_lstTokens.GetNext() ;
		while (tok != null && tok.GetType() == CTokenType.COMMENTS)
		{
			ParseComment() ;
			tok = m_lstTokens.GetCurrentToken() ;
		}
		return tok ;
	}
	protected void StepNext()
	{
		m_lstTokens.GetNext() ;
	}
	protected CBaseToken GetCurrentToken()
	{
		CBaseToken tok = m_lstTokens.GetCurrentToken() ;
		while (tok != null && tok.GetType() == CTokenType.COMMENTS)
		{
			ParseComment() ;
			tok = m_lstTokens.GetCurrentToken() ;
		}
		return tok ;
	}		
	public boolean Parse(CTokenList lst, CGlobalCommentContainer container)
	{
		m_lstTokens = lst ;
		m_Container = container ;
		return DoParsing();
	}
	public boolean Parse(CTokenList lst, CGlobalCommentContainer container, CFlag f)
	{
		m_lstTokens = lst ;
		m_Container = container ;
		return DoParsing(f);
	}
	protected boolean Parse(CBaseElement e, CFlag f)
	{
		return e.Parse(m_lstTokens, m_Container, f) ;
	}
	protected boolean Parse(CBaseElement e)
	{
		return e.Parse(m_lstTokens, m_Container) ;
	}
	protected boolean ParseComment()
	{
		return m_Container.ParseComment(m_lstTokens);
	}
	
	private CGlobalCommentContainer m_Container = null ;
	
	//protected Logger m_Logger = Transcoder.ms_logger ;

	public abstract CBaseLanguageEntity DoSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory) ;
	
	public CBaseElement(int line)
	{
		setLine(line);
	};
	
	protected void AddChild(CBaseElement el)
	{
		m_children.add(el) ;
	}
	protected LinkedList<CBaseElement> m_children = new LinkedList<CBaseElement>() ;
	

	protected abstract Element ExportCustom(Document root);
	private boolean m_bExportDoneForChildren = false ;
	public final Element Export(Document rootdoc)
	{
		Element e = ExportCustom(rootdoc) ;
		if (e == null)
		{
			e = rootdoc.createElement("UnknownElement") ;
		}
		e.setAttribute("Line", String.valueOf(getLine()));
		ExportChildren(rootdoc, e) ;
		return e ;
	}
	protected void ExportChildren(Document root, Element parent)
	{
		if (!m_bExportDoneForChildren)
		{
			ListIterator<CBaseElement> i = m_children.listIterator() ;
			try
			{
				CBaseElement le = i.next() ;
				while (le != null)
				{
					Element e = le.Export(root);
					if (e != null)
					{
						parent.appendChild(e);
					}
					le = i.next() ;
				}
			}
			catch (NoSuchElementException e)
			{
				//System.out.println(e.toString());
			}
			m_bExportDoneForChildren = true;
		}
	}

	protected class CFlag
	{
		public CFlag()
		{
		}
		public void Set()
		{
			m_bFlag = true ;
		}
		public void UnSet()
		{
			m_bFlag = false ;
		}
		public void Set(boolean b)
		{
			m_bFlag = b ;
		}
		public boolean ISSet()
		{
			return m_bFlag ;
		}
		protected boolean m_bFlag = false ;
	}
	public void Clear()
	{
		m_lstTokens = null ;
		m_Container = null ;
		
		ListIterator<CBaseElement> i = m_children.listIterator() ;
		try
		{
			CBaseElement le = i.next() ;
			while (le != null)
			{
				le.Clear();
				le = i.next() ;
			}
		}
		catch (NoSuchElementException e)
		{
			//System.out.println(e.toString());
		}
	}
}
