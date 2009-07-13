/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.fpacjava;

import generate.CBaseLanguageExporter;
import semantic.CEntityEnvironmentVariable;
import utils.CObjectCatalog;

public class CFPacJavaEnvironmentVariable extends CEntityEnvironmentVariable
{

	public CFPacJavaEnvironmentVariable(int l, String name, CObjectCatalog cat,
					CBaseLanguageExporter out, String accessor, String writer,
					boolean bNumericVar)
	{
		super(l, name, cat, out, accessor, writer, bNumericVar);
	}

	@Override
	public String ExportReference(int nLine)
	{
		return m_csAccessor ;
	}

	@Override
	public boolean HasAccessors()
	{
		return m_csWriteAccessor != null  && !m_csWriteAccessor.equals("");
	}

	@Override
	public String ExportWriteAccessorTo(String value)
	{
		if (m_csWriteAccessor.endsWith("("))
		{
			return m_csWriteAccessor + value + ") ;" ;
		}
		else
		{
			return m_csWriteAccessor + value + " ;" ;
		}
	}

	@Override
	public boolean isValNeeded()
	{
		return false;
	}

	@Override
	protected void DoExport()
	{
		// not used
	}
	
	public String toString()
	{
		return m_csAccessor ;
	}

}
