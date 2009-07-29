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
 * Created on 1 sept. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.verbs;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.Verbs.CEntityStringConcat;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaStringConcat extends CEntityStringConcat
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaStringConcat(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}
	protected void DoExport()
	{
		String cs = "" ;
		boolean bOnError = m_lstChildren.size() > 0 ; 
		if (bOnError)
		{
			cs = "if (";
		}
		for (int i=0; i<m_arrItems.size(); i++)
		{
			CDataEntity eItem = m_arrItems.get(i);
			CDataEntity eUntil = m_arrItemsDelimiters.get(i);
			if (eUntil != null)
			{
				cs += "concatDelimitedBy(" + eItem.ExportReference(getLine()) + ", " + eUntil.ExportReference(getLine()) + ").";
			}
			else
			{
				cs += "concat(" + eItem.ExportReference(getLine()) + ")." ;
			}
			WriteWord(cs);
			cs ="" ;
		}
		cs = "" ;
		if (m_eStartIndex != null)
		{
			cs += "withPointer(" + m_eStartIndex.ExportReference(getLine()) + ")." ;
		}
		cs += "into(" + m_eVariable.ExportReference(getLine()) + ")";
		WriteWord(cs);

		if (bOnError)
		{
			WriteWord(".failed()) {");
			WriteEOL() ;
			StartOutputBloc();
			ExportChildren();
			EndOutputBloc();
			WriteLine("}");			
		}
		else
		{
			WriteWord(" ;");
			WriteEOL();
		}
	}

}
