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
 * Created on 5 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.verbs;

import generate.CBaseLanguageExporter;
import generate.java.expressions.CJavaCondEquals;
import semantic.Verbs.CEntityCaseSearchAll;
import utils.CObjectCatalog;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCaseSearchAll extends CEntityCaseSearchAll
{

	/**
	 * @param cat
	 * @param out
	 */
	public CJavaCaseSearchAll(int l, CObjectCatalog cat, CBaseLanguageExporter out, int endline)
	{
		super(l, cat, out, endline);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	protected void DoExport()
	{
		//String cs = "		CompareResult result = compare(un_Mot_Dou.getAt(ind_Mot_Douteux), searchedValue);
		if (m_Condition == null)
		{
			Transcoder.logError(getLine(), "SEVERE ERROR: No condition provided for SEARCH ALL: Statement is omitted");
			return ;
		}
		
		if (!(m_Condition  instanceof CJavaCondEquals))
		{
			Transcoder.logError(getLine(), "SEVERE ERROR: NacaTrans doesn't support yet multiple conditions for SEARCH ALL: Statement is omitted");
			return ;
		}
		
		WriteLine("CompareResult result = " + m_Condition.Export() + " ;") ;
		WriteWord("if(result.isEqual()) {") ;
		WriteEOL() ;
		StartOutputBloc();
		ExportChildren();
		EndOutputBloc();
		WriteLine("}", m_nEndBlocLine) ;		
		
	}

}
