/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 6 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.verbs;

import generate.CBaseLanguageExporter;

import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.xpath.internal.operations.Gte;

import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import semantic.CEntityProcedureSection;
import semantic.CProcedureReference;
import utils.CObjectCatalog;

public class CJavaGotoDepending extends CBaseActionEntity
{
	private final List<CProcedureReference> refs = new ArrayList<CProcedureReference>();
	private CDataEntity depending;

	public CJavaGotoDepending(int line, CObjectCatalog cat, CBaseLanguageExporter out, List<String> refs, CDataEntity depending, CEntityProcedureSection section)
	{
		super(line, cat, out);
		String sec = "";
		if (section != null)
		{
			sec = section.GetName();
		}
		for (String ref : refs)
		{
			this.refs.add(new CProcedureReference(ref, sec, cat));
		}
		this.depending = depending;
	}

	@Override
	public void Clear()
	{
		super.Clear();
		refs.clear();
		depending = null;
	}

	protected void DoExport()
	{
		StringBuilder line = new StringBuilder("goTo(new CJMapRunnable[]{");
		for (CProcedureReference ref : refs)
		{
			line.append(ref.getProcedure().ExportReference(getLine()));
			line.append(",");
		}
		line.append("},").append(depending.ExportReference(getLine())).append(") ;");
		WriteLine(line.toString());
	}
}
