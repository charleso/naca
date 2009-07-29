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
import generate.SQLDumper;
import semantic.CDataEntity;
import semantic.SQL.CEntitySQLCursorSelectStatement;
import utils.CObjectCatalog;
import utils.Transcoder;

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
		exportExtractedSQL();
		
		boolean bBloc = false ;
		String s = "cursorOpen(" + m_Cursor.ExportReference(getLine()) + ", " ;
		WriteWord(s) ;
		WriteLongString(m_csStatement.trim()) ;
		WriteWord(")");
		
		boolean bOracle = Transcoder.isOracleTarget();
		if(!bOracle)
		{
			if (m_bWithHold)
			{
				WriteEOL() ;
				StartOutputBloc() ;
				bBloc = true ;
				WriteWord(".setHoldability(true)") ;
				WriteEOL() ;
			}
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
		if(bOracle)
		{
			if (m_bWithHold)
			{
				WriteEOL() ;
				StartOutputBloc() ;
				bBloc = true ;
				WriteWord(".setHoldability(true)") ;
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
	
	private void exportExtractedSQL()
	{
		SQLDumper sqlDumper = getSQLDumper();
		if(sqlDumper == null)
			return ;
		
		sqlDumper.startStatement(getLine());
		sqlDumper.incNbCursorOpen();
		
		String csCurName = m_Cursor.ExportReference(getLine());
		sqlDumper.registerCursorName(csCurName);
		
		boolean bBloc = false ;
		sqlDumper.append("try") ;
		sqlDumper.appendLineFeed();
		sqlDumper.append("{") ;
		sqlDumper.appendLineFeed();
		String s = "\tm_cur = cursorOpen(" + m_Cursor.ExportReference(getLine()) + ", " ;
		sqlDumper.append(s) ;
		sqlDumper.append("\"" + m_csStatement.trim() + "\"") ;
		sqlDumper.append(")");
//		if (m_bWithHold)
//		{
//			sqlDumper.appendLineFeed();
//			sqlDumper.append("\t.setHoldability(true)") ;
//			sqlDumper.appendLineFeed();
//		}
		sqlDumper.setNbParam(m_arrParameters.size());
		for(int i=0; i<m_arrParameters.size(); i++)
		{
			CDataEntity e = m_arrParameters.elementAt(i) ;
			if (e != null)
			{
				sqlDumper.appendLineFeed();
				sqlDumper.append("\t.param("+ (i+1) + ", " + "varParam" + i + ")");
			}
		}
		sqlDumper.append(" ;") ;
		sqlDumper.appendLineFeed();		
		sqlDumper.append("\thandleSqlStatus(m_cur);");
		sqlDumper.appendLineFeed();
		sqlDumper.append("}") ;
		sqlDumper.appendLineFeed();
		sqlDumper.append("catch(Exception e)") ;
		sqlDumper.appendLineFeed();
		sqlDumper.append("{") ;
		sqlDumper.appendLineFeed();
		sqlDumper.append("\thandleExceptionCursor(e);");
		sqlDumper.appendLineFeed();
		sqlDumper.append("}") ;
		
	}	
}
