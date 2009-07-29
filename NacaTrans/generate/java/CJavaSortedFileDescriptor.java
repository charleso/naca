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
package generate.java;

import generate.CBaseLanguageExporter;
import semantic.CBaseLanguageEntity;
import semantic.CEntitySortedFileDescriptor;
import utils.CObjectCatalog;

public class CJavaSortedFileDescriptor extends CEntitySortedFileDescriptor
{

	public CJavaSortedFileDescriptor(int line, String name, CObjectCatalog cat, CBaseLanguageExporter out)
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
		}
		//String cs = "SORT " + FormatIdentifier(GetDisplayName()) + " = sortDefine(\""+file+"\").record(" ;
		//cs +=  FormatIdentifier(GetRecordName()) + ");" ;
		String cs = "SortDescriptor " + FormatIdentifier(GetDisplayName()) + " = declare.sort() ;" ;
		WriteLine(cs) ;
		
		CBaseLanguageEntity child = m_lstChildren.getFirst() ; 
		DoExport(child);
	}

}
