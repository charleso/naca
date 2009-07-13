/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 2 févr. 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.misc;

import java.util.Hashtable;

import jlib.misc.MapStringByString;
import jlib.xml.Tag;

import nacaLib.base.CJMapObject;

/**
 * @author SLY
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CLocalizedTextManager extends CJMapObject
{
	private CLocalizedTextManager()
	{
	}
	
	protected static CLocalizedTextManager ms_instance = null ;
	public static CLocalizedTextManager getInstance()
	{
		if (ms_instance == null)
		{
			ms_instance = new CLocalizedTextManager() ;
		}
		return ms_instance ;
	}
	
	public void Init(Tag tagRoot)
	{
		Tag tagLanguages = tagRoot.getEnumChild("languages");
		while(tagLanguages != null)
		{
			Tag tagCode = tagLanguages.getEnumChild("code");
			while(tagCode != null)
			{
				String csId = tagCode.getVal("id");
				String csLang = tagCode.getVal("lang");
				m_tabLanguageCodes.put(csId, csLang);
				
				tagCode = tagLanguages.getEnumChild();
			}
			tagLanguages = tagRoot.getEnumChild();
		}

		Tag tagItem = tagRoot.getEnumChild("item");
		while(tagItem != null)
		{
			String csId = tagItem.getVal("id");
			MapStringByString table = new MapStringByString() ;
			m_tabLocalizedTexts.put(csId, table) ;

			Tag tagText = tagItem.getEnumChild("text");
			while(tagText != null)
			{
				String lang = tagText.getVal("lang");
				String text = tagText.getNodeVal();
				table.put(lang, text) ;
				
				tagText = tagItem.getEnumChild();
			}
			tagItem = tagRoot.getEnumChild();
		}
	}
	
	public String getLocalizedString(String id, String code)
	{
		String lang = m_tabLanguageCodes.get(code);
		if (lang == null)
		{
			lang = code ;
		}
		
		MapStringByString table = m_tabLocalizedTexts.get(id);
		if (table != null)
		{
			String text = table.get(lang) ;
			if (text != null)
			{
				return text ;
			}
		}
		return id ;
	}

	protected Hashtable<String, String> m_tabLanguageCodes = new Hashtable<String, String>() ;
	protected Hashtable<String, MapStringByString> m_tabLocalizedTexts = new Hashtable<String, MapStringByString>() ;
	
}
