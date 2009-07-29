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
package generate.fpacjava;

import generate.CBaseLanguageExporter;
import semantic.CEntityProcedureSection;
import semantic.Verbs.CEntityGoto;
import utils.CObjectCatalog;

public class CFPacJavaGoto extends CEntityGoto
{

	public CFPacJavaGoto(int line, CObjectCatalog cat, CBaseLanguageExporter out, String ref, CEntityProcedureSection sectionContainer)
	{
		super(line, cat, out, ref, sectionContainer);
	}

	@Override
	protected void DoExport()
	{
		WriteLine("return " + m_Reference.getProcedureName() + " ;") ;
	}

}
