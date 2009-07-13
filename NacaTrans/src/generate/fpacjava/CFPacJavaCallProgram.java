/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.fpacjava;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.Verbs.CEntityCallProgram;
import utils.CObjectCatalog;

/**
 * @author S. Charton
 * @version $Id: CFPacJavaCallProgram.java,v 1.2 2007/06/28 06:19:46 u930bm Exp $
 */
public class CFPacJavaCallProgram extends CEntityCallProgram
{

	/**
	 * @param l
	 * @param cat
	 * @param out
	 * @param Reference
	 */
	public CFPacJavaCallProgram(int l, CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity Reference)
	{
		super(l, cat, out, Reference);
	}

	/**
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	@Override
	protected void DoExport()
	{
		String name = m_Reference.ExportReference(getLine());
		if (name.startsWith("\""))
		{
			name = name.subSequence(1, name.length()-1) + ".class";	
		}
		if (m_bChecked)
		{
			WriteWord("call(" +  name + ")") ;
		}
		else
		{
			WriteWord("call(\"" +  name + "\")") ;
		}
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
		WriteWord(".executeCall() ;");
		WriteEOL();
	}

}
