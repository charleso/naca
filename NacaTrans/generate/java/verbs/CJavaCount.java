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
 * Created on 6 oct. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.verbs;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.Verbs.CEntityCount;
import utils.CObjectCatalog;
import utils.modificationsReporter.Reporter;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCount extends CEntityCount
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaCount(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	protected void DoExport()
	{
		if(m_bFunctionReverse)
		{
			Reporter.Add("Modif_PJ", "inspectReverseTallying");
			WriteWord("inspectTallying(functionReverse(" + m_Variable.ExportReference(getLine()) + "))");
			//WriteWord("inspectReverseTallying(" + m_Variable.ExportReference(getLine()) + ")");
		}
		else
			WriteWord("inspectTallying(" + m_Variable.ExportReference(getLine()) + ")");
		for (int i =0; i<m_arrCountAllToken.size();i++)
		{
			CDataEntity eTok = m_arrCountAllToken.get(i);
			CDataEntity eVar = m_arrCountVariableAll.get(i);
			WriteWord(".countAll(" + eTok.ExportReference(getLine()) + ", " + eVar.ExportReference(getLine()) + ")");
		}
		for (int i =0; i<m_arrCountLeadingToken.size();i++)
		{
			CDataEntity eTok = m_arrCountLeadingToken.get(i);
			CDataEntity eVar = m_arrCountVariableLeading.get(i);
			String cs = eTok.ExportReference(getLine());
			if(cs.equals("0"))
				cs = "\"0\"";
			WriteWord(".countLeading(" + cs + ", " + eVar.ExportReference(getLine()) + ")");
		}
		for (int i =0; i<m_arrCountAfterToken.size();i++)
		{
			CDataEntity eTok = m_arrCountAfterToken.get(i);
			CDataEntity eVar = m_arrCountVariableAfterToken.get(i);
			WriteWord(".countCharsAfter(" + eTok.ExportReference(getLine()) + ", " + eVar.ExportReference(getLine()) + ")");
		}
		for (int i =0; i<m_arrCountBeforeToken.size();i++)
		{
			CDataEntity eTok = m_arrCountBeforeToken.get(i);
			CDataEntity eVar = m_arrCountVariableBeforeToken.get(i);
			WriteWord(".countCharsBefore(" + eTok.ExportReference(getLine()) + ", " + eVar.ExportReference(getLine()) + ")");
		}
		WriteWord(";");
		WriteEOL();		
	}

}
