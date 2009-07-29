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
import semantic.CDataEntity.CDataEntityType;
import semantic.Verbs.CEntityAssignWithAccessor;
import utils.CObjectCatalog;

public class CFPacJavaAssignWithAccessor extends CEntityAssignWithAccessor
{

	public CFPacJavaAssignWithAccessor(int line, CObjectCatalog cat,
					CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	@Override
	protected void DoExport()
	{
		String val = "" ;
		if (m_Value != null)
		{
			val = m_Value.ExportReference(getLine()) ;
		}
		if (m_Value.GetDataType() == CDataEntityType.VAR && m_Reference.GetDataType() == CDataEntityType.NUMERIC_VAR)
		{
			val += ".getInt()" ;
		}
		String out = m_Reference.ExportWriteAccessorTo(val) ;
		if (out == null)
		{
			WriteLine("");
		}
		else
		{
			WriteLine(out);
		}
	}

}
