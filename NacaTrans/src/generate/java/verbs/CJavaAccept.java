/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.java.verbs;

import generate.CBaseLanguageExporter;
import semantic.Verbs.CEntityAccept;
import utils.CObjectCatalog;

public class CJavaAccept extends CEntityAccept
{

	public CJavaAccept(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	@Override
	protected void DoExport()
	{
		String cs = "" ;
		switch (m_eMode)
		{
			case FROM_DATE:
				cs = "getDateBatch()" ;
				break;
			case FROM_DAY:
				cs = "getDayBatch()" ;
				break ;
			case FROM_DAYOFWEEK:
				cs = "getDayOfWeekBatch()" ;
				break ;
			case FROM_INPUT:
				cs = "getInputBatch()" ;
				break ;
			case FROM_TIME:
				cs = "getTimeBatch()" ;
				break ;
			case FROM_VARIABLE:
				cs = m_eSource.ExportReference(getLine()) ;
				break ;
		}
		WriteLine("move(" + cs + ", " + m_eVariable.ExportReference(getLine()) + ");") ;
	}

}
