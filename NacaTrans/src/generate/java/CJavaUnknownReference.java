/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Oct 28, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java;

import generate.CBaseLanguageExporter;
import semantic.CEntityUnknownReference;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaUnknownReference extends CEntityUnknownReference
{
	public CJavaUnknownReference(int nLine, String csName, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(nLine, csName, cat, out);
	}

	public String ExportReference(int nLine)
	{
		return "[UnknownObject]";
	}
	public boolean HasAccessors()
	{
		return false;
	}
	public String ExportWriteAccessorTo(String value)
	{
		return null;
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
