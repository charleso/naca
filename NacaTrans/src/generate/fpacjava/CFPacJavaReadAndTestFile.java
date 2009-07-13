/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.fpacjava;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.expression.CEntityFunctionCall;
import utils.CObjectCatalog;

public class CFPacJavaReadAndTestFile extends CEntityFunctionCall
{

	public CFPacJavaReadAndTestFile(CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity data)
	{
		super(cat, out, data);
	}

	@Override
	public String ExportReference(int nLine)
	{
		String cs = m_Reference.ExportReference(getLine()) + ".read().atEnd()" ;
		return cs ;
	}

	@Override
	public boolean isValNeeded()
	{
		return false;
	}

}
