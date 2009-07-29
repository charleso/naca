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
 * Created on 10 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package utils;

import java.util.Vector;

/**
 * @author sly
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CEntityRenamer
{
	protected abstract class CBaseRenameRule
	{
		public CBaseRenameRule(String mask)
		{
			m_csMask = mask ;
		}
		protected String m_csMask ;
		public String getMask() 
		{
			return m_csMask ;
		}
		public abstract String doRename(String name) ;
		public abstract void init(String param) ;
	}
	protected class CTruncRule extends CBaseRenameRule
	{
		public CTruncRule(String cs)
		{
			super(cs);
		}
		public String doRename(String cs)
		{
			String out = cs.substring(0, cs.length()-m_nParam) ;
			return out ;
		}
		public void init(String param)
		{
			m_nParam = Integer.parseInt(param);
		}
		protected int m_nParam = 0 ;
	}
	protected class CBypassRule extends CBaseRenameRule
	{
		public CBypassRule(String cs)
		{
			super(cs);
		}
		public String doRename(String cs)
		{
			return cs ;
		}
		public void init(String param)
		{
		}
	}
	protected class CRenameRule extends CBaseRenameRule
	{
		public CRenameRule(String cs)
		{
			super(cs);
		}
		public String doRename(String cs)
		{
			return m_csParam;
		}
		public void init(String param)
		{
			m_csParam = param;
		}
		protected String m_csParam = "";
	}
	/**
	 * 
	 */
	public CEntityRenamer()
	{
	}
	
	public void AddRule(String mask, String action, String param)
	{
		CBaseRenameRule rule = null ;
		if (action.equalsIgnoreCase("trunc"))
		{
			rule = new CTruncRule(mask) ;
		}
		else if (action.equalsIgnoreCase("nothing"))
		{
			rule = new CBypassRule(mask) ;
		}
		else if (action.equalsIgnoreCase("rename"))
		{
			rule = new CRenameRule(mask) ;
		}
		rule.init(param) ;
		m_arrRules.add(rule) ;
	}
	public String FindAndApplyRule(String name)
	{
		for (int i=0; i<m_arrRules.size(); i++)
		{
			CBaseRenameRule rule = m_arrRules.get(i) ;
			String m = rule.getMask() ;
			if (name.matches(m))
			{
				String out = rule.doRename(name);
				Transcoder.logWarn(0, "Rules manager: Replacing Subprogram " + name + " by " + out);
				return out ;
			}
		}
		return null ;
	}
	protected Vector<CBaseRenameRule> m_arrRules = new Vector<CBaseRenameRule>();
}
