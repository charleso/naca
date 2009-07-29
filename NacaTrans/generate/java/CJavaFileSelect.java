/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package generate.java;

import generate.CBaseLanguageExporter;
import semantic.CEntityFileSelect;
import utils.CObjectCatalog;

public class CJavaFileSelect extends CEntityFileSelect
{
	public CJavaFileSelect(String csFileName, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(csFileName, cat, out);
	}

	@Override
	protected void DoExport()
	{
		if (m_FileSelect != null && m_eFileStatus != null)
		{
			String file = m_FileSelect.GetFileName() ;
			int npos = file.lastIndexOf('-') ;
			if (npos > 0)
			{
				file = file.substring(npos+1) ;
			}
			String csFileName = FormatIdentifier(GetDisplayName());
		
			String csFileStatusName = m_eFileStatus.ExportReference(0);
			
			WriteWord("FileSelectStatus ");
			WriteWord(csFileName + "$FSStatus");
			WriteWord(" = ");
			WriteWord("declare.fileSelectStatus(");
			WriteWord(csFileName);
			WriteWord(", ") ;
			WriteWord(csFileStatusName);
			WriteWord(")");
		
			if(m_eOrganizationMode != null)
			{
				if(m_eOrganizationMode == OrganizationMode.INDEXED)
					WriteWord(".organization(FileOrganization.Indexed)");
				if(m_eOrganizationMode == OrganizationMode.SEQUENTIAL)
					WriteWord(".organization(FileOrganization.Sequential)");
			}
			WriteWord(" ;");
		
			WriteEOL();
		}
	}

}
