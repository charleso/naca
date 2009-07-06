/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 5 août 2004
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
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaField extends CEntityResourceField
{

	/**
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CJavaField(int l, String name, CObjectCatalog cat, CBaseLanguageExporter lexp)
	{
		super(l, name, cat, lexp);
	}
	public String ExportReference(int nLine)
	{
		String cs = "" ;
		if (m_Of != null)
		{
			cs += m_Of.ExportReference(getLine());
			cs += ".";
		}
		cs += FormatIdentifier(GetName()) ;
		return cs ;
	}
	protected void DoExport()
	{
		String fieldname = FormatIdentifier(GetName()) ;
//		String length = eField.getAttribute("Length");
//		String col = eField.getAttribute("Col");
//		String line = eField.getAttribute("Line");
		if (!fieldname.equals(""))
		{
			String displayName = FormatIdentifier(m_csDisplayName) ;
			if (displayName.equals(""))
			{
				displayName = fieldname ;
			} 
			String cs = "Edit " + fieldname + " = declare.edit(\""+displayName+"\", "+m_nLength+")" ;
//			if (m_occurs > 0)
//			{
//				cs += ".occurs("+ m_occurs +")" ;
//			}
			if (!m_csInitialValue.equals(""))
			{
				String display = FormatIdentifier(m_csInitialValue) ;
				String res = m_ResourceStrings.ExportForField(m_csInitialValue, display) ;
				WriteLine(res) ;
				cs += ".initialValue("+display+")" ;
			}
//			if (!m_csColor.equals(""))
//			{
//				cs += ".color(MapFieldAttrColor."+ m_csColor+")" ;
//			}
//			if (!m_csHighLight.equals(""))
//			{
//				cs += ".highLighting(MapFieldAttrHighlighting."+ m_csHighLight+")" ;
//			}
//			if (!m_csBrightness.equals(""))
//			{
//				cs += ".intensity(MapFieldAttrIntensity."+ m_csBrightness+")" ;
//			}
//			if (!m_csProtection.equals(""))
//			{
//				cs += ".protection(MapFieldAttrProtection."+ m_csProtection+")" ;
//			}
			if (!m_csFillValue.equals(""))
			{
				cs += ".justifyFill(MapFieldAttrFill."+ m_csFillValue+")" ;
			}
			if (m_bRightJustified)
			{
				cs += ".justifyRight()" ;
			}
//			if (m_bCursor)
//			{
//				cs += ".setCursor(true)" ;
//			}
//			if (m_bModified)
//			{
//				cs += ".setModified()" ;
//			}
			if (!m_csDevelopableFlagMark.equals(""))
			{
				cs += ".setDevelopableMark(\"" + m_csDevelopableFlagMark + "\")" ;
			}
			if (!m_csFormat.equals(""))
			{
				cs += ".format(\"" + m_csFormat + "\")" ;
			}
			cs += ".edit() ;" ;
			WriteLine(cs);
		}
		if (m_lstChildren.size()> 0)
		{
			StartOutputBloc() ;
			ExportChildren();
			EndOutputBloc() ;
		}
	}

	public Element DoXMLExport(Document doc, CResourceStrings res)
	{
		Element ef;
		if (m_Mode == FieldMode.TITLE)
		{
			ef = doc.createElement("title") ;
		}
		else if (m_Mode == FieldMode.SWITCH)
		{
			ef = doc.createElement("switch") ;
			ef.setAttribute("linkedvalue", FormatIdentifier(GetDisplayName())) ;
			ef.setAttribute("name", FormatIdentifier(GetDisplayName())) ;
			ef.setAttribute("length", String.valueOf(m_nLength)) ;
			for  (int i=0; i<m_arrSwitchCaseElement.size(); i++)
			{
				CSwitchCaseElement el = m_arrSwitchCaseElement.get(i) ;
				Element eCase ;
				if (el.m_val != null)
				{
					eCase = doc.createElement("case") ;
					ef.appendChild(eCase) ;
					eCase.setAttribute("value", el.m_val) ;
				}
				else if (el.m_protection != null)
				{
					eCase = doc.createElement("case") ;
					ef.appendChild(eCase) ;
					eCase.setAttribute("protection", el.m_protection) ;
				}
				else
				{
					eCase = doc.createElement("default") ;
					ef.appendChild(eCase) ;
				}
				Element etag = (Element)doc.importNode(el.m_tag, true) ;
				eCase.appendChild(etag) ;
			}
			return ef ;
		}
		else
		{
			ef = doc.createElement("edit") ;
		}
		
		// PJD Other params
		//ef.setAttribute("sourceline", "" + getLine()) ;
		//ef.setAttribute("original_name", "" + GetName()) ;
		
		//ef.setAttribute("", "" + getLine()) ;
		
		if (!m_csInitialValue.equals(""))
		{
//			res.GetResource;
			//ef.setAttribute("InitialValue", m_initialValue) ;
			ef.appendChild(res.ExportResource(m_csInitialValue, doc)) ;
		}

		
		if (m_Mode == FieldMode.CHECKBOX)
		{
			ef.setAttribute("type", "checkbox") ;
			ef.setAttribute("valueOn", m_csCheckBoxValueOn);
			ef.setAttribute("valueOff", m_csCheckBoxValueOff);
		}
		else if (m_Mode == FieldMode.ACTIVE_CHOICE)
		{
			ef.setAttribute("type", "activeChoice") ;
			ef.setAttribute("activeChoiceValue", m_csActiveChoiceValue);
			ef.setAttribute("activeChoiceTarget", m_csActiveChoiceTarget);
			ef.setAttribute("activeChoiceSubmit", m_bActiveChoiceSubmit?"true":"false");
		}
		else if (m_Mode == FieldMode.LINKED_ACTIVE_CHOICE)
		{
			ef.setAttribute("type", "linkedActiveChoice") ;
			ef.setAttribute("activeChoiceLink", FormatIdentifier(m_csActiveChoiceValue));
			ef.setAttribute("activeChoiceTarget", m_csActiveChoiceTarget);
			ef.setAttribute("activeChoiceSubmit", m_bActiveChoiceSubmit?"true":"false");
		}
		if (m_Mode == FieldMode.HIDDEN)
		{
			ef.setAttribute("type", "hidden") ;
			ef.setAttribute("length", "0") ;
			ef.setAttribute("line", "0") ;
			ef.setAttribute("col", "0") ;
		}
		else
		{
			ef.setAttribute("length", String.valueOf(m_nLength)) ;
			ef.setAttribute("line", String.valueOf(m_nPosLine)) ;
			ef.setAttribute("col", String.valueOf(m_nPosCol)) ;
		}
		ef.setAttribute("linkedvalue", FormatIdentifier(GetDisplayName())) ;
		if (!GetName().equals(""))
		{
			String csName = FormatIdentifier(GetDisplayName());
			ef.setAttribute("name", csName) ;
			String csNameCopy = FormatIdentifier(GetName());
			if (!csNameCopy.equals(csName))
				ef.setAttribute("namecopy", FormatIdentifier(GetName())) ;
		}
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
			ef.setAttribute("intensity", m_csBrightness.toLowerCase());
		}
		if (!m_csProtection.equals(""))
		{
			ef.setAttribute("protection", m_csProtection.toLowerCase());
		}
		if (m_bCursor)
		{
			ef.setAttribute("cursor", "true") ;
		}
		if (m_bModified)
		{
			ef.setAttribute("modified", "true") ;
		}
		if (m_bReplayMutable)
		{
			ef.setAttribute("replayMutable", "true") ;
		}
		if (m_bRightJustified)
			ef.setAttribute("justify", "right") ;
		else
			ef.setAttribute("justify", "left") ;
		ef.setAttribute("fill", m_csFillValue.toLowerCase()) ;
		return ef ;
	}
	public boolean IsNeedDeclarationInClass()
	{
		return false ;
	}
	public String ExportWriteAccessorTo(String value)
	{
		// unsued		
		return "" ;
	}
	public boolean isValNeeded()
	{
		return true;
	}

	public CDataEntityType GetDataType()
	{
		return CDataEntityType.FIELD ;
	}
	public boolean IsEntryField()
	{
		return true;
	}
	/* (non-Javadoc)
	 * @see semantic.CBaseExternalEntity#GetTypeDecl()
	 */
	public String GetTypeDecl()
	{
		return "" ; // unused
	}

}
