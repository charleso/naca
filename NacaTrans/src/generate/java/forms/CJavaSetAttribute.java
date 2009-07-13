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

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.forms.CEntitySetAttribute;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaSetAttribute extends CEntitySetAttribute
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 * @param field
	 */
	public CJavaSetAttribute(int line, CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity field)
	{
		super(line, cat, out, field);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	protected void DoExport()
	{
		if (m_AttributeValue != null)
		{
			WriteLine("moveAttribute("+m_AttributeValue.ExportReference(getLine()) + ", " + m_RefField.ExportReference(getLine()) + ") ;") ;
			return  ;
		}
		// else
		if (m_bAutoSkip)
		{
			WriteLine("moveAttribute(MapFieldAttrProtection.AUTOSKIP, " + m_RefField.ExportReference(getLine()) + ") ;") ;
		}
		else if (m_bNumeric)
		{
			WriteLine("moveAttribute(MapFieldAttrProtection.NUMERIC, " + m_RefField.ExportReference(getLine()) + ") ;") ;
		}
		else if (m_bProtected)
		{
			WriteLine("moveAttribute(MapFieldAttrProtection.PROTECTED, " + m_RefField.ExportReference(getLine()) + ") ;") ;
		}
		else if (m_bUnProtected)
		{
			WriteLine("moveAttribute(MapFieldAttrProtection.UNPROTECTED, " + m_RefField.ExportReference(getLine()) + ") ;") ;
		}
		if (m_bBright)
		{
			WriteLine("moveAttribute(MapFieldAttrIntensity.BRIGHT, " + m_RefField.ExportReference(getLine()) + ") ;") ;
		}
		else if (m_bDark)
		{
			WriteLine("moveAttribute(MapFieldAttrIntensity.DARK, " + m_RefField.ExportReference(getLine()) + ") ;") ;
		}
		else if (m_bNormal)
		{
			WriteLine("moveAttribute(MapFieldAttrIntensity.NORMAL, " + m_RefField.ExportReference(getLine()) + ") ;") ;
		}
		if (m_bModified)
		{
			WriteLine("moveAttribute(MapFieldAttrModified.MODIFIED, " + m_RefField.ExportReference(getLine()) + ") ;") ;
		}
		else if (m_bUnmodified)
		{
			WriteLine("moveAttribute(MapFieldAttrModified.UNMODIFIED, " + m_RefField.ExportReference(getLine()) + ") ;") ;
		}
	}

}
