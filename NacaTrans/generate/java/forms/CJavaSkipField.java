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
import semantic.forms.CEntitySkipFields;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaSkipField extends CEntitySkipFields
{
	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 * @param nbFields
	 */
	public CJavaSkipField(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out, int nbFields, String level)
	{
		super(l, name, cat, out, nbFields, level);
	}

	public String ExportReference(int nLine)
	{
		return FormatIdentifier(GetName()) ;
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
		String cs = "Edit " + FormatIdentifier(GetName()) + " = declare.level("+Integer.parseInt(m_csLevel)+").editSkip(" + m_nbFields + ") ;" ;
		WriteLine(cs);
		
		StartOutputBloc();
		ExportChildren();
		EndOutputBloc() ;
	}
}
