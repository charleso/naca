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
import semantic.expression.CEntityNumber;
import utils.CObjectCatalog;

public class CFPacJavaNumber extends CEntityNumber
{

	public CFPacJavaNumber(CObjectCatalog cat, CBaseLanguageExporter out, String number)
	{
		super(cat, out, number);
	}

	@Override
	public String ExportReference(int nLine)
	{
		if (m_csValue.startsWith("0x"))
		{
			return "hexa(\""+m_csValue.substring(2)+"\")" ;
		}
		else
		{
			return m_csValue ;
		}
	}

	@Override
	public boolean isValNeeded()
	{
		return false;
	}

	@Override
	public String GetConstantValue()
	{
		return m_csValue ;
	}
	
	public String toString()
	{
		return ExportReference(getLine()) ;
	}

}
