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
package generate.java.forms;

import java.util.Enumeration;
import java.util.NoSuchElementException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import semantic.forms.CResourceStrings;

import com.sun.org.apache.xml.internal.utils.StringToStringTable;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaResourceStrings extends CResourceStrings
{

	/**
	 * @param nbLines
	 * @param nbCols
	 */
	public CJavaResourceStrings(int nbLines, int nbCols)
	{
		super(nbLines, nbCols);
	}

	/* (non-Javadoc)
	 * @see semantic.forms.CResourceStrings#Export(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public Element Export(Element parent, Document root)
	{
		Element eRoot = root.createElement("Strings") ;
		parent.appendChild(eRoot);
		Enumeration enumere = m_tabTexts.elements() ;
		CLocalizedText lText = null ;
		try
		{
			lText = (CLocalizedText)enumere.nextElement() ;
			while (lText != null)
			{
				Element eString = root.createElement("String");
				eRoot.appendChild(eString) ;
				eString.setAttribute("Name", lText.m_csId) ;
				for (int i=0; i<lText.m_TextTable.getLength(); i += 2)
				{
					Element eText = root.createElement("LocalizedText") ;
					eString.appendChild(eText);
					String cs = lText.m_TextTable.elementAt(i) ;
					eText.setAttribute("LangID", cs);
					String text = lText.m_TextTable.elementAt(i+1) ;
					eText.setAttribute("Text", text) ;
				}
				lText = (CLocalizedText)enumere.nextElement() ;
			}
		}
		catch (NoSuchElementException e)
		{
			e.printStackTrace();
			lText = null ;
		}
		return eRoot ;
	}
	
	public String ExportForField(String initialValue, String display)
	{
		CLocalizedText ltext = m_tabTexts.get(initialValue) ;
		String out = "LocalizedString " + display + " = declare.localizedString()" ;
		for (int i=0; i<ltext.m_TextTable.getLength(); i += 2)
		{
			String cs = ltext.m_TextTable.elementAt(i) ;
			cs = "LanguageCode."+cs ;
			String text = ltext.m_TextTable.elementAt(i+1) ;
			out += ".text("+cs+", \"" + text + "\")" ;
		}
		out += ";" ;
		return out ;
	} 

	public void FormatResource(String name)
	{
		CLocalizedText res = m_tabTexts.get(name) ;
		if (res == null)
		{
			return ;
		}
		StringToStringTable tab = new StringToStringTable() ;
		int n = res.m_TextTable.getLength() ;
		for (int i=0; i<n; i+=2)
		{
			String id = res.m_TextTable.elementAt(i) ;
			String text = res.m_TextTable.elementAt(i+1) ;
			text = text.trim() ;			
			tab.put(id, text) ;
		}
		res.m_TextTable = tab ;
//		CLocalizedText res = m_tabTexts.get(name) ;
//		if (res == null)
//		{
//			return ;
//		}
//		StringToStringTable tab = new StringToStringTable() ;
//		int n = res.m_TextTable.getLength() ;
//		for (int i=0; i<n; i+=2)
//		{
//			String id = res.m_TextTable.elementAt(i) ;
//			String text = res.m_TextTable.elementAt(i+1) ;
//			text = text.toLowerCase().trim() ;
//			String out = "" ;
//			int npos = 0 ;
//			while (npos>=0 && npos<text.length())
//			{
//				out += Character.toUpperCase(text.charAt(npos)) ;
//				int nsp = text.indexOf(' ', npos) ;
//				if (nsp == -1)
//				{
//					out += text.substring(npos+1) ;
//					npos = -1 ;
//				}
//				else
//				{
//					out += text.substring(npos+1, nsp+1) ;
//					npos = nsp+1 ;
//				}
//			}
//			tab.put(id, out) ;
//		}
//		res.m_TextTable = tab ;
	}
}
