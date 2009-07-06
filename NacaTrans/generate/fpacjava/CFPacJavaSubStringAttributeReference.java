/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.fpacjava;

import generate.CBaseLanguageExporter;
import semantic.CSubStringAttributReference;
import utils.CObjectCatalog;

public class CFPacJavaSubStringAttributeReference extends
				CSubStringAttributReference
{

	public CFPacJavaSubStringAttributeReference(int l, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, cat, out);
	}

	@Override
	public CDataEntityType GetDataType()
	{
		return m_Reference.GetDataType() ;
	}

	@Override
	public String ExportReference(int nLine)
	{
		if (m_Reference.HasAccessors())
		{
			String cs = m_Reference.ExportReference(getLine()) ;
			if (!cs.contains("("))
				cs += "(" ;
			else
				cs += ", " ;
			cs += m_Start.ExportReference(getLine()) ;
			if (m_Length != null)
			{
				cs += ", " + m_Length.ExportReference(getLine()) ;
			}
			cs += ")" ;
			return cs ;
		}
		else
		{
			String cs = "buffer(";
			cs += m_Reference.ExportReference(getLine()) + ", " ;
			cs += m_Start.ExportReference(getLine()) ;
			if (m_Length != null)
			{
				cs += ", " + m_Length.ExportReference(getLine()) ;
			}
			cs += ")" ;
			return cs ;
		}
	}

	@Override
	public boolean HasAccessors()
	{
		return false;
	}

	@Override
	public String ExportWriteAccessorTo(String value)
	{
		String cs = "move("+value+", "+m_Reference.ExportReference(getLine())+"("+m_Start.ExportReference(getLine())+", "+m_Length.ExportReference(getLine())+")) ;" ;
		return cs ;
	}

	@Override
	public boolean isValNeeded()
	{
		return false;
	}

	@Override
	protected void DoExport()
	{
		// unused

	}

}
