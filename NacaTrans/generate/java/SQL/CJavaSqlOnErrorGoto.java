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
	public CJavaSqlOnErrorGoto(int l, CObjectCatalog cat, CBaseLanguageExporter out, String Reference, boolean OnWarning)
	{
		super(l, cat, out, Reference, OnWarning);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseSemanticEntity#DoExport()
	 */
	protected void DoExport()
	{
		if (m_bOnWarning)
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
		else
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
	}
}
