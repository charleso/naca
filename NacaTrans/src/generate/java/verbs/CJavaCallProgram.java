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
import semantic.CDataEntity;
import semantic.Verbs.CEntityCallProgram;
import utils.CObjectCatalog;
import utils.CobolNameUtil;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCallProgram extends CEntityCallProgram
{

	/**
	 * @param cat
	 * @param out
	 */
	public CJavaCallProgram(int l, CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity ref)
	{
		super(l, cat, out, ref);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	protected void DoExport()
	{
		if (m_OnErrorBloc != null)
		{
			WriteWord("if (") ;
		}
		String name = m_Reference.ExportReference(getLine());
		if (name.startsWith("\"") && m_bChecked)
		{
			name = name.substring(1, name.length()-1);
			name = CobolNameUtil.fixJavaName(name);
			name += ".class";
		}
		WriteWord("call(" +  name + ")") ;
		if (m_arrParameters.size()>0)
		{
			for (int i=0; i<m_arrParameters.size(); i++)
			{
				CCallParameter p = m_arrParameters.get(i) ;
				if (!p.m_Reference.ignore())
				{
					String cs = "" ;
					if (p.m_Methode == CCallParameterMethode.BY_REFERENCE)
					{
						cs = ".using(";
					}
					else if (p.m_Methode == CCallParameterMethode.LENGTH_OF)
					{
						cs = ".usingLengthOf(";
					}
					else if (p.m_Methode == CCallParameterMethode.BY_VALUE)
					{
						cs = ".usingValue(";
					}
					else if (p.m_Methode == CCallParameterMethode.BY_CONTENT)
					{
						cs = ".usingContent(";
					}
					else  
					{
						cs = ".using(";
					}			
					if (p.m_Reference != null)
					{
						cs += p.m_Reference.ExportReference(getLine());
					}
					else
					{
						cs += "[UNDEFINED]";
					}
					WriteWord(cs + ")");
					
				}
			}
		}
		if (m_OnErrorBloc != null)
		{
			WriteWord(".executeCallSafe()") ;
			WriteWord(") {") ;
			WriteEOL() ;
			DoExport(m_OnErrorBloc) ;
			WriteLine("}");
			return;
		}
		WriteWord(".executeCall() ;");
		WriteEOL();
	}

}
