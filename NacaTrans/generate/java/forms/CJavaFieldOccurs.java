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
 * Created on 22 oct. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.forms;

import generate.CBaseLanguageExporter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import semantic.forms.CEntityFieldOccurs;
import semantic.forms.CResourceStrings;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaFieldOccurs extends CEntityFieldOccurs
{
	public CJavaFieldOccurs(int l, String name, CObjectCatalog cat, CBaseLanguageExporter lexp)
	{
		super(l, name, cat, lexp);
	}

	public Element DoXMLExport(Document doc, CResourceStrings res)
	{
		return null;
	}

	public String ExportReference(int nLine)
	{
		return FormatIdentifier(GetName());
	}

	public String ExportWriteAccessorTo(String value)
	{
		return null;
	}
	public boolean isValNeeded()
	{
		return false;
	}


	protected void DoExport()
	{
		String name = FormatIdentifier(GetName());
		String cs = "Edit " + name + " = declare.level("+Integer.parseInt(m_csLevel)+").editOccurs(" + m_Occurs.ExportReference(getLine()) + ", \"" + name + "\") ;" ;
		WriteLine(cs) ;
		StartOutputBloc();
		ExportChildren();
		EndOutputBloc() ;
	}

}
