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
 * Created on 20 août 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.SQL;

import semantic.SQL.CEntitySQLDeclareTable;
import utils.CObjectCatalog;
import generate.CBaseLanguageExporter;

import java.util.ArrayList;

import parser.Cobol.elements.SQL.CSQLTableColDescriptor;

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaSQLDeclareTable extends CEntitySQLDeclareTable
{
	public CJavaSQLDeclareTable(int nLine, CObjectCatalog cat, CBaseLanguageExporter out, String csTableName, String csViewName, ArrayList<CSQLTableColDescriptor> arrTableColDescription)
	{
		super(nLine, cat, out, csTableName, csViewName, arrTableColDescription);
	}
	
	protected void DoExport()
	{
//		WriteLine("{");
//		StartOutputBloc();
//		String s = "sqlDeclareTable(\"" + m_csTableName.trim() + "\");";
//		WriteLine(s);
//		for(int i=0; i<m_arrTableColDescription.size(); i++)
//		{
//			CSQLTableColDescriptor desc = (CSQLTableColDescriptor)m_arrTableColDescription.get(i);
//			String sCol = "addCol(\"" + desc.GetName() + "\")";
//			sCol += ".type(\"" + desc.GetType() + "\")";
//			if(desc.HasSize())
//				sCol += ".size(" + desc.GetSizes() + ")";
//			if(desc.IsNull())
//				sCol += ".null();";
//			else
//				sCol += ".notNull();";
//			WriteLine(sCol);
//		}
//		EndOutputBloc();
//		WriteLine("}");
	}	
}

