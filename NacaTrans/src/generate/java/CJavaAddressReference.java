/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Aug 25, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.CEntityAddressReference;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaAddressReference extends CEntityAddressReference
{
	/**
	 * @param l
	 * @param cat
	 * @param out
	 * @param ref
	 */
	public CJavaAddressReference(CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity ref)
	{
		super(cat, out, ref);
	}
	public String ExportReference(int nLine)
	{
		String out = "addressOf(" ;
		if (m_Reference == null)
		{
			out += "[UNDEFINED]" ;
		}
		else
		{
			out += m_Reference.ExportReference(getLine());
		}
		out += ")" ;
		return out ;
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
	protected void DoExport()
	{
		// unused
	}
	public boolean isValNeeded()
	{
		return false;
	}

}
