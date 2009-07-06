/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */


package generate.fpacjava;

import generate.CBaseLanguageExporter;
import semantic.expression.CEntityAddress;
import utils.CObjectCatalog;

public class CFPacJavaAddress extends CEntityAddress
{

	public CFPacJavaAddress(CObjectCatalog cat, CBaseLanguageExporter out, String address)
	{
		super(cat, out, address);
	}

	@Override
	public String ExportReference(int nLine)
	{
		return m_csAddress ;
	}
	
	public String toString()
	{
		return "@" + m_csAddress ;
	}

}
