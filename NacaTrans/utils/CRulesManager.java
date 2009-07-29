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
 * Created on 2 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package utils;

import java.util.Hashtable;
import java.util.Vector;

import jlib.misc.StringUtil;
import jlib.xml.*;

/**
 * @author sly
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CRulesManager
{
	protected static CRulesManager ms_Instance = null ;

	public CRulesManager()
	{
	}
	public static CRulesManager getInstance()
	{
		if (ms_Instance == null)
		{
			ms_Instance = new CRulesManager() ;
		}
		return ms_Instance ;
	}
	
	public void LoadRulesFile(String csFilePath)
	{
		m_tagRules = Tag.createFromFile(csFilePath);
		LoadRules() ;
	}
	/**
	 * 
	 */
	private void LoadRules()
	{
		if (m_tagRules != null)
		{
			TagCursor cur = new TagCursor() ;
			Tag tagCat = m_tagRules.getFirstChild(cur, "category") ;
			while  (tagCat != null)
			{
				String id = tagCat.getVal("id");
				if (id != null && !id.equals(""))
				{
					Vector<Tag> lstRules = tagCat.getChilds("rule") ;
					if (lstRules.size()>0)
					{
						m_tabCategories.put(id, lstRules);
					}
				}
				tagCat = m_tagRules.getNextChild(cur) ;
			}
			
			cur = new TagCursor() ;
			Tag tagRule = m_tagRules.getFirstChild(cur, "rule") ;
			while  (tagRule != null)
			{
				String id = tagRule.getVal("id");
				if (id != null && !id.equals(""))
				{
					Vector<Tag> lstRules = new Vector<Tag>() ;
					lstRules.add(tagRule) ;
					m_tabCategories.put(id, lstRules);
				}
				tagRule = m_tagRules.getNextChild(cur) ;
			}
		}
		
	}
	protected Tag m_tagRules = null ;
	protected Hashtable<String, Vector<Tag>> m_tabCategories = new Hashtable<String, Vector<Tag>>() ;
	
	public int getNbRules(String category)
	{
		Vector<Tag> lst = m_tabCategories.get(category);
		if (lst != null)
		{
			return lst.size() ;
		}
		return 0 ;
	}
	public Tag getRule(String category, int index)
	{
		Vector<Tag> lst = m_tabCategories.get(category);
		if (lst != null && index < lst.size())
		{
			return lst.get(index) ;
		}
		return null ;
	}
	public Tag getRule(String category)
	{
		Vector<Tag> lst = m_tabCategories.get(category);
		if (lst != null)
		{
			return lst.get(0) ;
		}
		return null ;
	}
	
	public boolean isToInline(String csCopyFileName)
	{
		int nNbCopyToInline = getNbRules("inlinedCopy");
		for(int n=0; n<nNbCopyToInline; n++)
		{
			Tag tag = getRule("inlinedCopy", n);
			if(tag != null)
			{
				String csName = tag.getVal("copyName");
				if(csName.equalsIgnoreCase(csCopyFileName))
					return true;	// The copy file csCopyFileName must be inlined
			}
		}
		return false;			// The copy file csCopyFileName must not be inlined
	}
}
