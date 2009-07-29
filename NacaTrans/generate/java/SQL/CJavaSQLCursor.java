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
/*
 * Created on Oct 12, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.SQL;

import generate.CBaseLanguageExporter;
import semantic.SQL.CEntitySQLCursor;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaSQLCursor extends CEntitySQLCursor
{
	/**
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CJavaSQLCursor(String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(name, cat, out);
	}

	public CDataEntityType GetDataType()
	{
		return null;
	}

	public String ExportReference(int nLine)
	{
		String csCurName = GetName();
		// PJReady starts: support for conflict betwen curosr and copy names
		boolean bConflict = m_ProgramCatalog.HasExternalReferenceWithName(csCurName);
		if(bConflict)
		{
			int n = 0;
			while(bConflict && n < 99999)	// Not infinite loop...
			{
				csCurName = csCurName + "$" + n; 
				bConflict = m_ProgramCatalog.HasExternalReferenceWithName(csCurName);
				n++;				
			}
		}
		// PJReady ends
		String csValue = FormatIdentifier(csCurName);
		return csValue;
	}
	protected void DoExport()
	{
		// unused
	}
	public boolean isValNeeded()
	{
		return false;
	}

}
