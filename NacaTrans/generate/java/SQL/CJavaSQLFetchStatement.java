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
 * Created on 19 août 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.SQL;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.SQL.CEntitySQLCursor;
import semantic.SQL.CEntitySQLFetchStatement;
import utils.CObjectCatalog;

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaSQLFetchStatement extends CEntitySQLFetchStatement
{
	public CJavaSQLFetchStatement(int line, CObjectCatalog cat, CBaseLanguageExporter out, CEntitySQLCursor cur)
	{
		super(line, cat, out, cur);
	}
   
	protected void DoExport()
	{
		String s = "cursorFetch(" + m_Cursor.ExportReference(getLine()) + ")";
		WriteWord(s);
		
		int i=0;
		
		CDataEntity eVariableStatement = m_Cursor.getVariableStatement();
		if(eVariableStatement == null)
		{
			for(; i<m_arrInto.size(); i++)
			{
				if(i != 0)
					WriteEOL();
				CDataEntity e = m_arrInto.elementAt(i);
				String cs = e.ExportReference(getLine());
				CDataEntity eInd = m_arrIndicators.elementAt(i) ;
				if (eInd != null)
					cs += ", " + eInd.ExportReference(getLine()) ;
				WriteWord(".into(" + cs + ")");
			}
			if(m_arrIgnoredInto != null && m_arrIgnoredInto.size() > 0)
			{
				for(int j=0; j<m_arrIgnoredInto.size(); j++, i++)
				{
					if(j != 0)
						WriteEOL();
					CDataEntity e = m_arrIgnoredInto.elementAt(j);
					String cs = e.ExportReference(getLine());
					CDataEntity eInd = m_arrIgnoredIndicators.elementAt(j) ;
					if (eInd != null)
						cs += ", " + eInd.ExportReference(getLine()) ;
					WriteWord(".ignoredInto(" + cs + ")");
				}
			}
			if(m_missingFetchVariable != null && m_missingFetchVariable.getValue() > 0)
			{
				if(i != 0)
					WriteEOL();
				WriteWord(".missingFetchVariables(" + m_missingFetchVariable.getValue() + ")");
			}
		}
		else	// Variable statement cursor, all ; Not using .ignoredInto
		{
			for(; i<m_arrInto.size(); i++)
			{
				if(i != 0)
					WriteEOL();
				CDataEntity e = m_arrInto.elementAt(i);
				String cs = e.ExportReference(getLine());
				CDataEntity eInd = m_arrIndicators.elementAt(i) ;
				if (eInd != null)
					cs += ", " + eInd.ExportReference(getLine()) ;
				WriteWord(".into(" + cs + ")");				
			}
			if(m_arrIgnoredInto != null && m_arrIgnoredInto.size() > 0)
			{
				for(int j=0; j<m_arrIgnoredInto.size(); j++, i++)
				{
					if(j != 0)
						WriteEOL();
					CDataEntity e = m_arrIgnoredInto.elementAt(j);
					String cs = e.ExportReference(getLine());
					CDataEntity eInd = m_arrIgnoredIndicators.elementAt(j) ;
					if (eInd != null)
						cs += ", " + eInd.ExportReference(getLine()) ;
					WriteWord(".into(" + cs + ")");
				}
			}
		}
		String csSQLErrorWarningStatement = m_ProgramCatalog.getSQLWarningErrorStatement();
		if(csSQLErrorWarningStatement != null)
		{
			WriteEOL();
			WriteWord(csSQLErrorWarningStatement);
		}
		WriteWord(" ;") ;
		WriteEOL();		
	}	
}
