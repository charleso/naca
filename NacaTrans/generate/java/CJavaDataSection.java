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
 * Created on Aug 26, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java;

import generate.CBaseLanguageExporter;
import semantic.CEntityDataSection;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaDataSection extends CEntityDataSection
{
	/**
	 * @param line
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CJavaDataSection(int line, String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, name, cat, out);
	}
	protected void DoExport()
	{
		boolean bMustExportTally = false;
		String name = GetName() ;
		String type = "" ;
		if (name.equals("WorkingStorageSection"))
		{
			type = "workingStorageSection" ;
			bMustExportTally = m_ProgramCatalog.getAndResetTallyUsage();
		}
		else if (name.equals("LinkageSection"))
		{
			type = "linkageSection";
		}
		else if (name.equals("FileSection"))
		{
			type = "fileSection";
		}
		else if (name.equals("VariableSection"))
		{
			type = "variableSection";
		}
		else
		{
			ExportChildren() ;
			return ;
		}
		String line = "DataSection " + FormatIdentifier(GetName()) + " = declare." + type + "() ;" ;
		WriteLine(line);
//		StartOutputBloc() ;
		ExportChildren() ;
		
		if(bMustExportTally)
		{
			line = "Var tally = declare.level(77).pic9(5).comp().var();	// NacaTrans generated for tally support";
			WriteLine(line);
		}
//		EndOutputBloc();
	}
}
