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
 * Created on Sep 29, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.expressions;

import generate.CBaseLanguageExporter;
import semantic.expression.CEntityCurrentDate;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCurrentDate extends CEntityCurrentDate
{
	/**
	 * @param cat
	 * @param out
	 */
	public CJavaCurrentDate(CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(cat, out);
	}
	public String ExportReference(int nLine)
	{
		return "currentDate()";
	}
	public boolean isValNeeded()
	{
		return false;
	}

}
