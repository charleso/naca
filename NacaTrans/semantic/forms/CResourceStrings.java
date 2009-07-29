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
 * Created on 11 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.forms;

import java.util.Hashtable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.sun.org.apache.xml.internal.utils.StringToStringTable;
import com.sun.org.apache.xml.internal.utils.StringVector;


/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CResourceStrings
{
	public static String LANG_FRENCH = "FR" ;
	public static String LANG_GERMAN = "DE" ;
	public static String LANG_ITALIAN = "IT" ;
	public static String LANG_ENGLISH = "EN" ;
	public static String getOfficialLanguageCode(String lang)
	{
		lang = lang.trim() ;
		if (lang.endsWith("F"))
		{
			return LANG_FRENCH;
		}
		else if (lang.endsWith("D"))
		{
			return LANG_GERMAN;
		}
		else if (lang.endsWith("I"))
		{
			return LANG_ITALIAN;
		}
		else if (lang.endsWith("G"))
		{
			return LANG_ENGLISH;
		}
		else
		{
			return lang;
		}
	}
	
	protected StringVector m_arrLangId = new StringVector() ;
	protected class CLocalizedText
	{
		public String m_csId = "" ;
		public StringToStringTable m_TextTable = new StringToStringTable();
		public int m_length =0  ; 
	}
	public CResourceStrings(int nbLines, int nbCols)
	{
		m_nbCols = nbCols ;
		m_nbLines = nbLines ;
		m_arrLines = new CLocalizedText[m_nbLines+1][];
		m_tabTexts = new Hashtable<String, CLocalizedText>() ;
	}
	public void SetResourceText(int line, int col, String text, String langID, int length)
	{
		String csLang = getOfficialLanguageCode(langID) ; 
		if (!m_arrLangId.contains(csLang))
		{
			m_arrLangId.addElement(csLang) ;
		}
		CLocalizedText lText = GetResourceAt(line, col) ;
		lText.m_length = length ;
		lText.m_TextTable.put(csLang, text) ;
	}
	public void SetResourceText(int line, int col, String text, String langID, String id, int length)
	{
		String csLang = getOfficialLanguageCode(langID) ; 
		if (!m_arrLangId.contains(csLang))
		{
			m_arrLangId.addElement(csLang) ;
		}
		CLocalizedText lText = GetResourceAt(line, col) ;
		lText.m_length = length ;
		lText.m_TextTable.put(csLang, text) ;
		if (!id.equals(""))
		{
			lText.m_csId = id ;
			m_tabTexts.put(id, lText) ;
		}
	}
	protected CLocalizedText GetResourceAt(int line, int col)
	{
		CLocalizedText lText = null ;
		if (m_arrLines[line] == null)
		{
			m_arrLines[line] = new CLocalizedText[m_nbCols+1] ;
		}
		lText = m_arrLines[line][col] ;
		if (lText == null)
		{
			lText = new CLocalizedText() ;
			m_arrLines[line][col] = lText ;
		} 
		return lText ;
	}
	public abstract Element Export(Element parent, Document root);
	
	public String CreateName(String radical)
	{
		return radical + "_LABEL_" + m_lastIndex++;
	}
	protected int m_lastIndex = 0;
	protected int m_nbLines = 0 ;
	protected int m_nbCols = 0 ;
	protected CLocalizedText[][] m_arrLines = null ;
	protected Hashtable<String, CLocalizedText> m_tabTexts = null ;

	public Node ExportResource(String name, Document doc)
	{
		CLocalizedText res = m_tabTexts.get(name) ;
		if (res == null)
		{
			return null;
		}
		Element eText = doc.createElement("texts");
		int n = res.m_TextTable.getLength() ;
		for (int i=0; i<n; i+=2)
		{
			String id = res.m_TextTable.elementAt(i) ;
			String text = res.m_TextTable.elementAt(i+1) ;
			Element e = doc.createElement("text");
			e.setAttribute("lang", id) ;
			eText.appendChild(e);
			e.appendChild(doc.createTextNode(text));
		}
		return eText ;
	}
	public abstract void FormatResource(String name) ;
	/**
	 * @param m_initialValue
	 * @return
	 */
	public abstract String ExportForField(String m_initialValue, String display) ; 
	
	public String ExportAllLangId()
	{
		String cs = "" ;
		for (int i=0; i<m_arrLangId.size(); i++)
		{
			if (i>0)
			{
				cs += ";" ;
			}
			cs += m_arrLangId.elementAt(i) ;
		}
		return cs ;
	}
	/**
	 * @param posLine
	 * @param posCol
	 * @return
	 */
	public boolean isExistingField(int line, int col, int length)
	{
		if (line > m_nbLines || col > m_nbCols)
		{
			return false ;
		}
		CLocalizedText lText = null ;
		if (m_arrLines[line] == null)
		{
			return  false ;
		}
		lText = m_arrLines[line][col] ;
		if (lText == null)
		{
			return false ;
		} 
		if (lText.m_length != length)
		{
			return false ;
		}
		return true ;
	}
}
