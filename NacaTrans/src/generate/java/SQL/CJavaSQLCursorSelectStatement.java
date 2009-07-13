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
import semantic.SQL.CEntitySQLCursorSelectStatement;
import utils.CObjectCatalog;

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaSQLCursorSelectStatement extends CEntitySQLCursorSelectStatement
{
	public CJavaSQLCursorSelectStatement(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}
	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	protected void DoExport()
	{
		boolean bBloc = false ;
		String s = "cursorOpen(" + m_Cursor.ExportReference(getLine()) + ", " ;
		WriteWord(s) ;
		WriteLongString(m_csStatement.trim()) ;
		WriteWord(")");
		if (m_bWithHold)
		{
			WriteEOL() ;
			StartOutputBloc() ;
			bBloc = true ;
			WriteWord(".setHoldability(true)") ;
			WriteEOL() ;
		}
		for(int i=0; i<m_arrParameters.size(); i++)
		{
			CDataEntity e = m_arrParameters.elementAt(i) ;
			if (e != null)
			{
				WriteEOL() ;
				if (!bBloc)
				{
					StartOutputBloc() ;
					bBloc = true ;
				}
				WriteWord(".param("+ (i+1) + ", " + e.ExportReference(getLine()) + ")");
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
		if (bBloc)
		{
			EndOutputBloc() ;
		}
	}	

}
