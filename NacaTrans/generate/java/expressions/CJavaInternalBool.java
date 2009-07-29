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
 * Created on 7 juil. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package generate.java.expressions;

import generate.CBaseLanguageExporter;
import semantic.expression.CEntityInternalBool;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CJavaInternalBool extends CEntityInternalBool
{

	/**
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CJavaInternalBool(String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(name, cat, out);
	}

	/* (non-Javadoc)
	 * @see semantic.CDataEntity#ExportReference(getLine())
	 */
	public String ExportReference(int nLine)
	{
		return FormatIdentifier(GetName()) ;
	}
	public boolean isValNeeded()
	{
		return true;
	}


	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	protected void DoExport()
	{
		WriteLine("Var " + FormatIdentifier(GetName()) + " = declare.bool() ;") ;
	}

}
