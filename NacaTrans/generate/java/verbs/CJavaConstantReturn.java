/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaTrans - Naca Transcoder v1.2.0.beta.1
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.java.verbs;

import generate.CBaseLanguageExporter;
import semantic.Verbs.CEntityConstantReturn;
import utils.CObjectCatalog;

public class CJavaConstantReturn extends CEntityConstantReturn
{	
	/**
	 * @param cat
	 * @param out
	 */
	public CJavaConstantReturn(int l, CObjectCatalog cat, CBaseLanguageExporter out, String cs)
	{
		super(l, cat, out, cs);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	protected void DoExport()
	{
		WriteLine("return " + m_csConstant + " ;") ;
	}

}