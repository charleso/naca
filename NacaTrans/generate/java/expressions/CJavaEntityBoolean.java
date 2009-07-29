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
 * Created on 18 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.expressions;

import jlib.misc.NumberParser;
import generate.CBaseLanguageExporter;
import semantic.expression.CEntityBoolean;
import semantic.expression.CEntityNumber;
import utils.CObjectCatalog;
import utils.modificationsReporter.Reporter;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaEntityBoolean extends CEntityBoolean
{

	/**
	 * @param l
	 * @param cat
	 * @param out
	 * @param number
	 */
	public CJavaEntityBoolean(CObjectCatalog cat, CBaseLanguageExporter out, boolean bValue)
	{
		super(cat, out, bValue);
		if(bValue)
		{
			int gg  =0 ;
		}
	}
	public String ExportReference(int nLine)
	{
		if(m_bValue)
			return "true";
		return "false";
	}
	public String GetConstantValue()
	{
		if(m_bValue)
			return "true";
		return "false";
	} 	 
	
	public boolean isValNeeded()
	{
		return true;
	}

}
