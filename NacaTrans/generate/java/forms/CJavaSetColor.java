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
import semantic.forms.CEntitySetColor;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaSetColor extends CEntitySetColor
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaSetColor(int line, CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity field)
	{
		super(line, cat, out, field);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	protected void DoExport()
	{
		String cs = "moveColor(";
		if (m_Color != null)
		{
			cs += "MapFieldAttrColor." + m_Color.m_text + ", " ;
		}
		else if (m_ColorVariable != null)
		{
			cs += m_ColorVariable.ExportReference(getLine()) + ", " ;
		}
		else
		{
			cs += "MapFieldAttrColor.NEUTRAL, " ;
		}
		cs += m_Field.ExportReference(getLine())+ ") ;" ;
		WriteLine(cs) ; 
	}

}
