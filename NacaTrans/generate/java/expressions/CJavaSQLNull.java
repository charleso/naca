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
/**
 * 
 */
package generate.java.expressions;

import generate.CBaseLanguageExporter;
import semantic.expression.CEntitySQLNull;
import utils.CObjectCatalog;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class CJavaSQLNull extends CEntitySQLNull
{
	public CJavaSQLNull(CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(cat, out);
	}
	
	public String ExportReference(int nLine)
	{
		return "NULL";
	}
	
	public boolean isValNeeded()
	{
		return false;
	}
}