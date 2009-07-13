/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 18 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.forms;

import org.w3c.dom.Element;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.forms.CEntityFormRedefine;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaFormRedefine extends CEntityFormRedefine
{

	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 * @param level
	 * @param form
	 */
	public CJavaFormRedefine(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity form, boolean bSaveMap)
	{
		super(l, name, cat, out, form, bSaveMap);
	}
	public String ExportReference(int nLine)
	{
		return FormatIdentifier(GetName());
	}
	public boolean HasAccessors()
	{
		return false;
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
//		if (!m_bSaveMap)
//		{
			String cs = "MapRedefine " + FormatIdentifier(GetName()) + " = declare.level(1).redefinesMap(" ;
			cs += m_eForm.ExportReference(getLine()) ;
			WriteLine(cs + ") ;");
			StartOutputBloc();
			ExportChildren();
			EndOutputBloc();
//		} 
	}

	public Element DoXMLExport()
	{
		return null; // unused
	}
	public String GetTypeDecl()
	{
		return ""; // unused
	}

}
