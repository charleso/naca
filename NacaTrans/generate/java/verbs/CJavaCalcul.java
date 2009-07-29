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
 * Created on 3 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.verbs;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.Verbs.CEntityCalcul;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCalcul extends CEntityCalcul
{

	/**
	 * @param cat
	 * @param out
	 */
	public CJavaCalcul(int l, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, cat, out);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseSemanticEntity#DoExport()
	 */
	protected void DoExport()
	{
		String csOperation = "compute" ;
		if (m_arrRoundedDestinations.size() > 0)
		{
			csOperation += "Rounded";
		}
		csOperation += "(";
		String csExpression = m_Expression.Export() ;
		
		boolean bOnError = false ;
		if (m_OnErrorBloc != null)
		{
			WriteWord("if (") ;
			bOnError = true ;
		}
		boolean bFound = false ;
		for (int i=0; i<m_arrDestinations.size();i++)
		{
			CDataEntity destination = m_arrDestinations.get(i);
			String line = "" ;
			if (bFound && bOnError)
			{
				line = " || " ;
			}
			else
			{
				bFound = true ;
			}
			String csDest = destination.ExportReference(getLine());
			if (csDest.equals("getSQLCode()"))
			{
				line += "resetSQLCode(" + csExpression + ")" ;
			}
			else
			{
				line += csOperation + csExpression + ", " + csDest + ")" ;
			}
			if (bOnError)
			{
				line += ".isError()" ;
				WriteWord(line);
			}
			else 
			{
				line += "; " ;
				WriteLine(line) ;
			}
		}
		for (int i=0; i<m_arrRoundedDestinations.size();i++)
		{
			CDataEntity destination = m_arrRoundedDestinations.get(i);
			String line = "" ;
			if (bFound && bOnError)
			{
				line = " || " ;
			}
			else
			{
				bFound = true ;
			}
			String csDest = destination.ExportReference(getLine());
			if (csDest.equals("getSQLCode()"))
			{
				line += "resetSQLCode(" + csExpression + ")" ;
			}
			else
			{
				line += csOperation + csExpression + ", " + csDest + ")" ;
			}
			if (bOnError)
			{
				line += ".isError()" ;
				WriteWord(line);
			}
			else 
			{
				line += "; " ;
				WriteLine(line) ;
			}
		}
		if (bOnError)
		{
			WriteWord(") {") ;
			WriteEOL() ;
			DoExport(m_OnErrorBloc) ;
			WriteLine("}");
		}
	}

}
