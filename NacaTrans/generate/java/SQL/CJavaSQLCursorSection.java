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
import semantic.CDataEntity;
import semantic.CEntitySQLCursorSection;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaSQLCursorSection extends CEntitySQLCursorSection
{
	/**
	 * @param cat
	 * @param out
	 */
	public CJavaSQLCursorSection(CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(cat, out);
	}

	protected void DoExport()
	{
		String line = "DataSection " + FormatIdentifier(GetName()) + " = declare.cursorSection() ;" ;
		WriteLine(line);
		WriteLine("");
		StartOutputBloc() ;
		for (int i=0; i<m_arrCursors.size(); i++)
		{
			CDataEntity cur = (CDataEntity)m_arrCursors.elementAt(i);
			String cs = "SQLCursor "+ cur.ExportReference(getLine()) + " = declare.cursor() ; " ;
			WriteLine(cs);
			WriteLine("");
		}
		EndOutputBloc();
	}
}
