/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Jan 10, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.forms;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import generate.CBaseLanguageExporter;
import semantic.forms.CEntityResourceField;
import semantic.forms.CEntityResourceFieldArray;
import semantic.forms.CResourceStrings;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaFieldArray extends CEntityResourceFieldArray
{
	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param lexp
	 */
	public CJavaFieldArray(int l, String name, CObjectCatalog cat, CBaseLanguageExporter lexp)
	{
		super(l, name, cat, lexp);
	}

	public String GetTypeDecl()
	{
		return "";
	}
	/* (non-Javadoc)
	 * @see semantic.CDataEntity#ExportReference(getLine())
	 */
	public String ExportReference(int nLine)
	{
		// nothing
		return "" ;
	}
	/* (non-Javadoc)
	 * @see semantic.CDataEntity#ExportWriteAccessorTo()
	 */
	public String ExportWriteAccessorTo(String value)
	{
		// unused
		return "" ;
	}
	public boolean isValNeeded()
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	protected void DoExport()
	{
		// nothing
		ExportChildren() ;
	}


	/* (non-Javadoc)
	 * @see semantic.forms.CEntityResourceField#DoXMLExport(org.w3c.dom.Document, semantic.forms.CResourceStrings)
	 */
	public Element DoXMLExport(Document doc, CResourceStrings res)
	{
		Element eArray = doc.createElement("array");
		eArray.setAttribute("nbCol", String.valueOf(m_NbColumns)) ;
		eArray.setAttribute("nbItems", String.valueOf(m_NbItems)) ;
		eArray.setAttribute("vert", String.valueOf(m_bVerticalFilling)) ;
		eArray.setAttribute("line", String.valueOf(m_nPosLine)) ;
		eArray.setAttribute("col", String.valueOf(m_nPosCol)) ;
		
		Element eItem = doc.createElement("item") ;
		eArray.appendChild(eItem);
		ListIterator iter = m_lstChildren.listIterator() ;
		try
		{
			CEntityResourceField field = (CEntityResourceField)iter.next() ;
			while (field != null)
			{
				Element e = field.DoXMLExport(doc, res) ;
				if (e != null)
				{
					eItem.appendChild(e) ;
				}
				field = (CEntityResourceField)iter.next() ;
			}
		}
		catch (NoSuchElementException e)
		{
		}
		return eArray ;
	}
}
