/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.java;

import generate.CBaseLanguageExporter;
import semantic.CEntityFileDescriptor;
import utils.CObjectCatalog;

public class CJavaFileDescriptor extends CEntityFileDescriptor
{

	public CJavaFileDescriptor(int line, String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, name, cat, out);
	}

	@Override
	protected void DoExport()
	{
		String file = "" ;
		if (m_FileSelect != null)
		{
			file = m_FileSelect.GetFileName() ;
			int npos = file.lastIndexOf('-') ;
			if (npos > 0)
			{
				file = file.substring(npos+1) ;
			}
		}
		String cs = "FileDescriptor " + FormatIdentifier(GetDisplayName()) + " = declare.file(\""+file+"\")";
		WriteWord(cs) ;
//		if (m_RecSizeDependingOn != null)
//		{
//			WriteWord(".lengthDependingOn("+m_RecSizeDependingOn.ExportReference(getLine())+")") ;
//		}
		WriteWord(" ;") ;
		WriteEOL() ;
		ExportChildren() ;
//		CBaseLanguageEntity child = m_lstChildren.getFirst() ; 
//		DoExport(child);
		
	}

}
