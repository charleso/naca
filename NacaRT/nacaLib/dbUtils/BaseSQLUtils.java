/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.dbUtils;

import java.util.ArrayList;

import nacaLib.basePrgEnv.BaseSession;
import jlib.log.Log;
import jlib.sql.ColValue;
import jlib.sql.ColValueGeneric;
import jlib.sql.DbConnectionBase;
import jlib.sql.DbPreparedStatement;
import jlib.sql.SQLTypeOperation;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: BaseSQLUtils.java,v 1.11 2007/05/23 07:39:58 u930bm Exp $
 */
public abstract class BaseSQLUtils
{
	DbConnectionBase m_dbConnection = null;
	private BaseSession m_session = null; 
	
	BaseSQLUtils(BaseSession session, DbConnectionBase dbConnection)
	{
		m_dbConnection = dbConnection;
		m_session = session;
	}
	
	BaseSession getSession()
	{
		return m_session; 
	}
	
	int executeSQLClause(String csClause)
	{
		SQLTypeOperation typeOperation = SQLTypeOperation.determineOperationType(csClause, false);	// cursor clause not supported
		
		// Remove ending ';' as it is not supported by UDB
		if(csClause.endsWith(";"))
			csClause = csClause.substring(0, csClause.length()-1);
		
		if(typeOperation.executeWithStatement())
		{	
			csClause = SQLTypeOperation.addEnvironmentPrefix(m_dbConnection.getEnvironmentPrefix(), csClause, typeOperation, "");
			DbPreparedStatement stmt = m_dbConnection.prepareStatement(csClause, 0, false);
			if(stmt != null)
			{
				int n = stmt.execute(typeOperation);
				return n;
			}
		}
		else
		{
			int n = m_dbConnection.executeOperation(typeOperation);
			return n;
		}
		return -1;
	}
	
	boolean executeSQLClause(String csClause, ArrayList<ColValueGeneric> arrColValues, int nRecordId)
	{
		SQLTypeOperation typeOperation = SQLTypeOperation.determineOperationType(csClause, false);	// cursor clause not supported
		
		// Remove ending ';' as it is not supported by UDB
		if(csClause.endsWith(";"))
			csClause = csClause.substring(0, csClause.length()-1);
		
		int nStatus = -1;
		if(typeOperation.executeWithStatement())
		{
			csClause = SQLTypeOperation.addEnvironmentPrefix(m_dbConnection.getEnvironmentPrefix(), csClause, typeOperation, "");
			DbPreparedStatement stmt = m_dbConnection.prepareStatement(csClause, 0, false);
			
			boolean b = true;			
			int nNbParam = arrColValues.size();
			for(int nParam=0; nParam<nNbParam && b; nParam++)
			{
				ColValue colValue = arrColValues.get(nParam);
				if(colValue != null)
					b = stmt.setColParam(nParam, colValue);
			}
			if(b && stmt != null)
			{
				nStatus = stmt.execute(typeOperation);
			}
		}
		else
		{
			nStatus = m_dbConnection.executeOperation(typeOperation);
		}
		
		if(nStatus < 0)
		{
			String cs = makeLogText(csClause, arrColValues, nRecordId);
			Log.logCritical("SQL Error : " + cs);
			return false;
		}
		return true;		
	}
	
	boolean executeSQLInsertClause(SQLTypeOperation typeOperation, String csClause, ArrayList<ColValueGeneric> arrColValues, int nRecordId)
	{
		int nStatus = -1;

		DbPreparedStatement stmt = m_dbConnection.prepareStatement(csClause, 0, false);
		
		boolean b = true;			
		int nNbParam = arrColValues.size();
		for(int nParam=0; nParam<nNbParam && b; nParam++)
		{
			ColValue colValue = arrColValues.get(nParam);
			if(colValue != null)
				b = stmt.setColParam(nParam, colValue);
		}
		if(b && stmt != null)
		{
			nStatus = stmt.execute(typeOperation);
		}

		
		if(nStatus < 0)
		{
			String cs = makeLogText(csClause, arrColValues, nRecordId);
			Log.logCritical("SQL Error : " + cs);
			return false;
		}
		return true;		
	}
	
	private String makeLogText(String csClause, ArrayList<ColValueGeneric> arrColValues, int nRecordId)
	{
		nRecordId++;	// 1 based
		String cs = "Record number=" + nRecordId + "\r\n";
		cs += "Clause="+csClause + "\r\n";
		cs += "Columns:\r\n";
		for(int n=0; n<arrColValues.size(); n++)
		{
			ColValue colValue = arrColValues.get(n);
			cs += colValue.toString() + "\r\n";
		}
		return cs;
	}
	
	protected String makeLogText(String csClause, String csErrorsText, int nRecordId)
	{
		nRecordId++;	// 1 based
		String cs = "Record number=" + nRecordId + "\r\n";
		cs += "Clause="+csClause + "\r\n";
		cs += csErrorsText;
		return cs;
	}
	
	abstract int executeStatement(String csClause);
}
