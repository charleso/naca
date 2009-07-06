/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 28 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package generate.java.verbs;


import generate.CBaseLanguageExporter;
import semantic.Verbs.CEntitySearch;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CJavaSearch extends CEntitySearch
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaSearch(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	protected void DoExport()
	{
		WriteWord("for (move(false, "+FormatIdentifier("Search-Found")+"); ") ;
		WriteWord("isNot("+FormatIdentifier("Search-Found")+") && isLessOrEqual(" + m_eIndex.ExportReference(getLine()) + 
							", getNbOccurs(" + m_eVariable.ExportReference(getLine()) + ")); ") ;
		WriteWord("inc(1, " + m_eIndex.ExportReference(getLine()) + ")) {" ) ;
		WriteEOL() ;
		StartOutputBloc() ;
		ExportChildren() ;
		EndOutputBloc() ;
		WriteLine("}") ;
		if (m_blocElse != null)
		{
			WriteLine("if (isNot("+FormatIdentifier("Search-Found")+")) {") ;
			StartOutputBloc() ;
			DoExport(m_blocElse) ;
			EndOutputBloc() ;
			WriteLine("}") ;
		}
	}

}
