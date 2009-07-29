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
 * Created on 4 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.SQL;

import generate.CBaseLanguageExporter;
import semantic.SQL.CEntitySqlOnErrorGoto;
import utils.CObjectCatalog;
import utils.modificationsReporter.Reporter;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaSqlOnErrorGoto extends CEntitySqlOnErrorGoto
{

	/**
	 * @param cat
	 * @param out
	 * @param Reference
	 */
	public CJavaSqlOnErrorGoto(int l, CObjectCatalog cat, CBaseLanguageExporter out, String Reference, SQLErrorType errorType) // PJD: Generalized SQLErrorType errorType
	{
		super(l, cat, out, Reference, errorType);
		Reporter.Add("Modif_PJ", "CJavaSqlOnErrorGoto ctor");
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseSemanticEntity#DoExport()
	 */
	protected void DoExport()
	{
		//if (m_bOnWarning)
		Reporter.Add("Modif_PJ", "CJavaSqlOnErrorGoto generation generalized");
		if(m_errorType == SQLErrorType.OnWarningGoto)
		{
			if (m_csRef.equals(""))
			{
				// WriteLine("// onSQLWarningContinue() ;") ;
				m_ProgramCatalog.registerSQLWarningContinue(null);
			}
			else
			{
				// WriteLine("// onSQLWarningGoto(" + FormatIdentifier(m_csRef) + ") ;") ;
				m_ProgramCatalog.registerSQLWarningGoto(FormatIdentifier(m_csRef));
			}
		}
		else if(m_errorType == SQLErrorType.OnErrorGoto)
		{
			if (m_csRef.equals(""))
			{
				// WriteLine("// onSQLErrorContinue() ;") ;
				m_ProgramCatalog.RegisterSQLErrorContinue(null);
			}
			else
			{
				// WriteLine("// onSQLErrorGoto(" + FormatIdentifier(m_csRef) + ") ;") ;
				m_ProgramCatalog.registerSQLErrorGoto(FormatIdentifier(m_csRef));
			}
		}
		else if(m_errorType == SQLErrorType.OnNotFoundGoto)	// PJD Added
		{
			Reporter.Add("Modif_PJ", "CJavaSqlOnErrorGoto added SQLErrorType.OnNotFoundGoto");
			if (m_csRef.equals(""))
			{
				m_ProgramCatalog.RegisterSQLNotFoundContinue(null);
			}
			else
			{
				m_ProgramCatalog.registerSQLNotFoundGoto(FormatIdentifier(m_csRef));
			}
		}
	}
}
