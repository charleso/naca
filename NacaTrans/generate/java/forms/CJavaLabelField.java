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
 * Created on Aug 17, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.forms;

import generate.CBaseLanguageExporter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import semantic.forms.CEntityResourceField;
import semantic.forms.CResourceStrings;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaLabelField extends CEntityResourceField
{
	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param lexp
	 */
	public CJavaLabelField(int l, CObjectCatalog cat, CBaseLanguageExporter lexp)
	{
		super(l, "", cat, lexp);
	}
	public boolean IsEntryField()
	{
		return false;
	}
	public Element DoXMLExport(Document doc, CResourceStrings res)
	{
		Element ef ;
		if (m_Mode == FieldMode.TITLE)
		{
			ef = doc.createElement("title") ;
		}
		else if (m_Mode == FieldMode.HIDDEN)
		{
			return null ;
		}
		else if (m_Mode == FieldMode.ACTIVE_CHOICE)
		{
			ef = doc.createElement("label") ;
			ef.setAttribute("type", "activeChoice") ;
			ef.setAttribute("activeChoiceValue", m_csActiveChoiceValue);
			ef.setAttribute("activeChoiceTarget", m_csActiveChoiceTarget);
			ef.setAttribute("activeChoiceSubmit", m_bActiveChoiceSubmit?"true":"false");
		}
		else if (m_Mode == FieldMode.LINKED_ACTIVE_CHOICE)
		{
			ef = doc.createElement("label") ;
			ef.setAttribute("type", "linkedActiveChoice") ;
			ef.setAttribute("activeChoiceLink", FormatIdentifier(m_csActiveChoiceValue));
			ef.setAttribute("activeChoiceTarget", m_csActiveChoiceTarget);
			ef.setAttribute("activeChoiceSubmit", m_bActiveChoiceSubmit?"true":"false");
		}
		else
		{
			ef = doc.createElement("label") ;
		}
		ef.setAttribute("length", String.valueOf(m_nLength)) ;
		ef.setAttribute("line", String.valueOf(m_nPosLine)) ;
		ef.setAttribute("col", String.valueOf(m_nPosCol)) ;
		if (!m_csInitialValue.equals(""))
		{
//			res.GetResource;
			//ef.setAttribute("InitialValue", m_initialValue) ;
			ef.appendChild(res.ExportResource(m_csInitialValue, doc)) ;
		}
		if (!m_csDisplayName.equals(""))
		{
			ef.setAttribute("name", m_csDisplayName);
		}
//		if (!GetName().equals(""))
//		{
//			ef.setAttribute("Name", GetName()) ;
//		}
		if (!m_csColor.equals(""))
		{
			ef.setAttribute("color", m_csColor.toLowerCase());
		}
		if (!m_csHighLight.equals(""))
		{
			ef.setAttribute("highlighting", m_csHighLight.toLowerCase());
		}
		if (!m_csBrightness.equals(""))
		{
			ef.setAttribute("brightness", m_csBrightness.toLowerCase());
		}
//		if (!m_Protection.equals(""))
//		{
//			ef.setAttribute("Protection", m_Protection);
//		}
//		if (!m_FillValue.equals(""))
//		{
//			ef.setAttribute("FillValue", m_FillValue);
//		}
//		if (!m_Justify.equals(""))
//		{
//			ef.setAttribute("Justify", m_Justify);
//		}
//		for (int i=0; i<m_arrAttrib.size(); i++)
//		{
//			String cs = m_arrAttrib.elementAt(i);
//			Element e = doc.createElement("Attribute");
//			ef.appendChild(e);
//			e.setAttribute("Value", cs);			
//		}
//		for (int i=0; i<m_arrJustify.size(); i++)
//		{
//			String cs = m_arrJustify.elementAt(i);
//			Element e = doc.createElement("Justify");
//			ef.appendChild(e) ;
//			e.setAttribute("Value", cs);			
//		}
		return ef ;
	}
	public CDataEntityType GetDataType()
	{
		return CDataEntityType.FIELD ;
	}
	public String ExportReference(int nLine)
	{
		// unsued
		return "" ;
	}
	public String ExportWriteAccessorTo(String value)
	{
		// unused
		return "" ;
	}
	public boolean isValNeeded()
	{
		return false;
	}

	protected void DoExport()
	{
		// unused
	}
	/* (non-Javadoc)
	 * @see semantic.CBaseExternalEntity#GetTypeDecl()
	 */
	public String GetTypeDecl()
	{
		return "" ; // unsued
	}
}
