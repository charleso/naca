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
package generate.java.verbs;

import parser.expression.CTerminal;
import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.Verbs.CEntityWriteFile;
import utils.CObjectCatalog;

public class CJavaWriteFile extends CEntityWriteFile
{

	public CJavaWriteFile(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	@Override
	protected void DoExport()
	{
		String cs = "";
		String csFile = "[UnknownReference]" ;
		if (m_eFileDescriptor != null)
			csFile = m_eFileDescriptor.ExportReference(getLine()) ;
		
		String csArg = null;
		if (m_eDataFrom != null)
			csArg = csFile + ", " + m_eDataFrom.ExportReference(getLine());
				 
		if(m_bPage)	// Page Positionning
		{
			if(m_bBefore)
			{
				if (m_eDataFrom != null)
					cs = "writeFromBeforePagePositionning(" + csArg + ") ;";
				else
					cs = "writeBeforePagePositionning(" + csFile + ") ;";
			}
			else	// After
			{
				if (m_eDataFrom != null)
					cs = "writeFromAfterPagePositionning(" + csArg + ") ;";
				else
					cs = "writeAfterPagePositionning(" + csFile + ") ;";
			}
			WriteLine(cs) ;
		}
		else if(m_eNbLinesPositioning != null)	// Line positionning
		{
			if(m_bBefore)
			{
				if (m_eDataFrom != null)
					cs = "writeFromBeforeLinePositionning(" + csArg + ", " + m_eNbLinesPositioning.ExportReference(getLine()) + ") ;";
				else
					cs = "writeBeforeLinePositionning(" + csFile + ", " + m_eNbLinesPositioning.ExportReference(getLine()) + ") ;";
			}
			else	// after
			{
				if (m_eDataFrom != null)
					cs = "writeFromAfterLinePositionning(" + csArg + ", " + m_eNbLinesPositioning.ExportReference(getLine()) + ") ;";
				else
					cs = "writeAfterLinePositionning(" + csFile + ", " + m_eNbLinesPositioning.ExportReference(getLine()) + ") ;";
			}
			WriteLine(cs) ;
		}
		else	// No positioning
		{
			if (m_eDataFrom != null)
				cs = "writeFrom(" + csArg + ") ;";
			else
				cs = "write(" + csFile + ") ;";
			WriteLine(cs) ;
		}
	}

}
	