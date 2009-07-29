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
 * Created on 28 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package generate.java.verbs;

import generate.CBaseLanguageExporter;

import java.util.ArrayList;

import semantic.CDataEntity;
import semantic.CEntityIndex;
import semantic.CEntityStructure;
import semantic.COrderedEntityStructure;
import semantic.Verbs.CEntitySearch;
import utils.CObjectCatalog;
import utils.modificationsReporter.Reporter;

/**
 * @author U930CV TODO To change the template for this generated type comment go
 *         to Window - Preferences - Java - Code Style - Code Templates
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	protected void DoExport()
	{
		if (m_bAll)
		{
			Reporter.Add("Modif_PJ", "SearchAll");
			//searchAll(poste_Profparul3).executeWith(index, poste_Profparul3, run);
			String cs = "searchAll(" + m_eVariable.ExportReference(getLine());
			
			CDataEntity eTableSizeDepending = m_eVariable.getTableSizeDepending();
			if(eTableSizeDepending != null)
			{
				cs += ", ";
				cs += eTableSizeDepending.ExportReference(getLine());
			}
			cs += ")";
			cs += ".indexedBy(";
			CEntityIndex index = m_eVariable.getOccursIndex();
			cs += index.ExportReference(getLine());
			cs += ")";
			WriteWord(cs);
			
			ArrayList<COrderedEntityStructure> arrOrderedTableSortKeys = m_eVariable.getArrTableSortKeys();
			if(arrOrderedTableSortKeys != null)
			{
				for(int n=0; n<arrOrderedTableSortKeys.size(); n++)
				{
					COrderedEntityStructure eOrderedStruct = arrOrderedTableSortKeys.get(n);
					CEntityStructure eStruct = eOrderedStruct.getStruct();
					boolean bAscending = eOrderedStruct.getAscending();
					if(bAscending)
						cs = ".keyAsc(";
					else
						cs = ".keyDesc(";
					cs += eStruct.ExportReference(getLine());
					cs += ")";
					WriteLine(cs);
				}
			}
			WriteEOL();
			cs = ".when(new CallbackSearch(this){public CompareResult run(){";
			WriteLine(cs);
			StartOutputBloc();
			ExportChildren();
			EndOutputBloc();
			WriteLine("}});");
		}
		else	// Search
		{
			WriteWord("for (move(false, "+FormatIdentifier("Search-Found")+"); ") ;
			WriteWord("isNot(" + FormatIdentifier("Search-Found") + ") && isLessOrEqual(" + m_eIndex.ExportReference(getLine()) + ", getNbOccurs(" + m_eVariable.ExportReference(getLine()) + ")); ");
			WriteWord("inc(1, " + m_eIndex.ExportReference(getLine()) + ")) {");
			WriteEOL();
			StartOutputBloc();
			ExportChildren();
			EndOutputBloc();
			WriteLine("}");
			if (m_blocElse != null)
			{
				WriteLine("if (isNot(" + FormatIdentifier("Search-Found") + ")) {");
				StartOutputBloc();
				DoExport(m_blocElse);
				EndOutputBloc();
				WriteLine("}");
			}
		}
	}

}
