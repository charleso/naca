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
import semantic.Verbs.CEntityContinue;
import utils.CObjectCatalog;

public class CFPacJavaContinue extends CEntityContinue
{

	public CFPacJavaContinue(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	@Override
	protected void DoExport()
	{
//		NotifHasExplicitFileGet notif = new NotifHasExplicitFileGet() ;
//		m_ProgramCatalog.SendNotifRequest(notif) ;
//		if (!notif.hasExplicitFileGet)
//		{
//			WriteLine("continue ;") ;
//		}
//		else
//		{
		WriteLine("return NORMAL ;") ;
//		}
	}
	
	public boolean hasExplicitGetOut()
	{
		return true ;
	}
}
