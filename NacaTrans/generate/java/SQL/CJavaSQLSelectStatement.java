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
 * Created on 18 août 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.SQL;

import java.util.Vector;

import generate.CBaseLanguageExporter;
import generate.SQLDumper;
import semantic.CDataEntity;
import semantic.SQL.CEntitySQLSelectStatement;
import utils.CObjectCatalog;
import utils.Transcoder;

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaSQLSelectStatement extends CEntitySQLSelectStatement
{
	/**
	 * @param line
	 * @param cat
	 * @param out
	 * @param st
	 */
	public CJavaSQLSelectStatement(int line, CObjectCatalog cat, CBaseLanguageExporter out, String csStatement, Vector<CDataEntity> arrParameters, Vector<CDataEntity> arrInto, Vector<CDataEntity> arrInd)
	{
		super(line, cat, out, csStatement, arrParameters, arrInto, arrInd);
	}
	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	protected void DoExport()
	{
		exportExtractedSQL();
		
		boolean bBloc = false ;
		WriteWord("sql(") ;
		WriteLongString(m_csStatement.trim()) ;
		WriteWord(")");
		for(int i=0; i<m_arrInto.size(); i++)
		{
			WriteEOL();
			if (!bBloc)
			{
				StartOutputBloc() ;
				bBloc = true ;
			}
			CDataEntity cs = m_arrInto.elementAt(i);
			String out = ".into(" + cs.ExportReference(getLine()) ;
			if (i<m_arrInd.size())
			{
				CDataEntity e = m_arrInd.get(i) ;
				if (e != null)
				{
					out += ", "+e.ExportReference(getLine()) ;
				}
			}
			WriteWord(out + ")");
		}
		for(int i=0; i<m_arrParameters.size(); i++)
		{
			CDataEntity cs = m_arrParameters.elementAt(i);
			if (cs != null)
			{
				WriteEOL();
				if (!bBloc)
				{
					StartOutputBloc() ;
					bBloc = true ;
				}
				WriteWord(".param("+ (i+1) + ", " + cs.ExportReference(getLine()) + ")");
			}
		}
		String csSQLErrorWarningStatement = m_ProgramCatalog.getSQLWarningErrorStatement();
		if(csSQLErrorWarningStatement != null)
		{
			WriteEOL();
			WriteWord(csSQLErrorWarningStatement);
		}
		WriteWord(" ;") ;
		WriteEOL() ;
		if (bBloc)
		{
			EndOutputBloc() ;
		}
	}
	
	protected void exportExtractedSQL()
	{
		SQLDumper sqlDumper = getSQLDumper();
		if(sqlDumper == null)
			return ;
			
		sqlDumper.startStatement(getLine());
		sqlDumper.incNbSelect();
		
		boolean bBloc = false ;
		sqlDumper.append("m_sql = sql(\"") ;
		sqlDumper.append(m_csStatement.trim()) ;
		sqlDumper.append("\")");
		String csVar = "varInto";
		
		sqlDumper.setNbInto(m_arrInto.size());
		sqlDumper.setNbParam(m_arrParameters.size());
		for(int i=0; i<m_arrInto.size(); i++)
		{
			String out = ".into(" + csVar + i  + ")";
			sqlDumper.appendLineFeed();
			sqlDumper.append("\t");			
			sqlDumper.append(out);
		}
		csVar = "varParam";
		for(int i=0; i<m_arrParameters.size(); i++)
		{
			CDataEntity cs = m_arrParameters.elementAt(i);
			if (cs != null)
			{
				sqlDumper.appendLineFeed();
				sqlDumper.append("\t");
				sqlDumper.append(".param("+ (i+1) + ", " + csVar + i + ")");
			}
		}
		sqlDumper.append(" ;") ;
		sqlDumper.append("\n\t\thandleSqlStatus();") ;
		sqlDumper.append("\n") ;
	}	
}
