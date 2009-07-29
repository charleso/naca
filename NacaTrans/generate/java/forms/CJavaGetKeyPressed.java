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
 * Created on Oct 18, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.forms;

import generate.CBaseLanguageExporter;
import semantic.forms.CEntityGetKeyPressed;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaGetKeyPressed extends CEntityGetKeyPressed
{
	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CJavaGetKeyPressed(String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(name, cat, out);
	}

	public String ExportReference(int nLine)
	{
		return "getKeyPressed()";
	}

	public String ExportWriteAccessorTo(String value)
	{
		return "setKeyPressed("+value+") ;";
	}
	public boolean isValNeeded()
	{
		return false;
	}

}
