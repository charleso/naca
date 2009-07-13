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
import semantic.Verbs.CEntityParseString;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaParseString extends CEntityParseString
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaParseString(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}
	protected void DoExport()
	{
		String cs = "" ;
		if (m_lstChildren.size()>0)
		{
			cs = "if (" ;
		}
		cs += "unstring(" + m_Variable.ExportReference(getLine()) + ")";
		WriteWord(cs);
		cs = "" ;
		for (int i=0; i<m_arrDelimitersSingle.size(); i++)
		{
			CDataEntity e = m_arrDelimitersSingle.get(i);
			cs = ".delimitedBy(" + e.ExportReference(getLine()) + ")";
			WriteWord(cs);
		}
		for (int i=0; i<m_arrDelimitersMulti.size(); i++)
		{
			CDataEntity e = m_arrDelimitersMulti.get(i);
			cs = ".delimitedByAll(" + e.ExportReference(getLine())+")" ;
			WriteWord(cs);
		}
		if (m_WithPointer != null)
		{
			cs = ".withPointer(" + m_WithPointer.ExportReference(getLine())+")" ;
			WriteWord(cs);
		}
		if (m_Tallying != null)
		{
			cs = ".tallying(" + m_Tallying.ExportReference(getLine())+")" ;
			WriteWord(cs);
		}
		for (int i=0; i<m_arrDestinations.size(); i++)
		{
			CDataEntity[] entities = m_arrDestinations.get(i);
			CDataEntity eTo = entities[0];
			CDataEntity eDelimiterIn = entities[1];
			CDataEntity eCountIn = entities[2];
			String csTo = ".to(" + eTo.ExportReference(getLine());
			if (eDelimiterIn != null || eCountIn != null)
			{
				csTo += ", ";
				if (eDelimiterIn == null)
					csTo += "null";
				else
					csTo += eDelimiterIn.ExportReference(getLine());
				csTo += ", ";
				if (eCountIn == null)
					csTo += "null";
				else
					csTo += eCountIn.ExportReference(getLine());
			}
			csTo += ")";
			WriteWord(csTo);
		}		
		if (m_lstChildren.size()>0)
		{
			WriteWord(".failed()) {") ;
			WriteEOL() ;
			StartOutputBloc();
			ExportChildren();
			EndOutputBloc() ;
			WriteLine("}");
		}
		else
		{
			WriteWord(" ;");
			WriteEOL();
		}
	}

}
