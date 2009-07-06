/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 10 févr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package generate.java.expressions;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.expression.CEntityConcat;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CJavaConcat extends CEntityConcat
{

	/**
	 * @param cat
	 * @param out
	 * @param data1
	 * @param data2
	 */
	public CJavaConcat(CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity data1, CDataEntity data2)
	{
		super(cat, out, data1, data2);
	}

	/* (non-Javadoc)
	 * @see semantic.CDataEntity#ExportReference(getLine())
	 */
	public String ExportReference(int nLine)
	{
		String cs = "concat(" + m_Reference.ExportReference(getLine()) + ", " + m_dataRef2.ExportReference(getLine()) + ")" ;
		return cs ;
	}
	public boolean isValNeeded()
	{
		return false;
	}


}
