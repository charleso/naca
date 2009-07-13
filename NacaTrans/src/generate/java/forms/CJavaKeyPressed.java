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
import semantic.forms.CEntityKeyPressed;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaKeyPressed extends CEntityKeyPressed
{
	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CJavaKeyPressed(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out, String publicName)
	{
		super(l, name, cat, out);
		m_csPublicName = publicName ;
	}
	protected String m_csPublicName = "" ;

	public String ExportReference(int nLine)
	{
		return "KeyPressed."+m_csPublicName ;	
	}

	public boolean HasAccessors()
	{
		// unused
		return false;
	}

	public String ExportWriteAccessorTo(String value)
	{
		// unused
		return null;
	}
	public boolean isValNeeded()
	{
		return false;
	}

}
