/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.mapSupport;

import jlib.misc.MapStringByString;

/*
 * Created on 13 oct. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LocalizedString
{
	public LocalizedString()
	{
	}
	/*
	LocalizedString(String cs)
	{
		m_csName = cs ;
	}
		*/
	public String getTextForLanguage(String csLangId)
	{
		String cs = m_tabTexts.get(csLangId.trim());
		return cs;
	}
	
	public LocalizedString text(String csId, String text)
	{
		m_tabTexts.put(csId, text); 		
		return this ;
	}

	//protected String m_csName = "" ;
	protected MapStringByString m_tabTexts = new MapStringByString() ;
}
