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
import semantic.Verbs.CEntityCase;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCase extends CEntityCase
{

	/**
	 * @param cat
	 * @param out
	 */
	public CJavaCase(int l, CObjectCatalog cat, CBaseLanguageExporter out, int endline)
	{
		super(l, cat, out, endline);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	protected void DoExport()
	{
		if (m_Condition != null)
		{ // if m_Condition == null => OTHER Statement
			WriteWord("if (" + m_Condition.Export() + ")") ;
		}
		WriteLine("{") ;
		WriteEOL() ;
		StartOutputBloc();
		ExportChildren();
		EndOutputBloc();
		WriteLine("}", m_nEndBlocLine) ;		
		
	}

}
