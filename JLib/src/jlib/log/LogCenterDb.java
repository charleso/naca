/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.log;

import java.sql.ResultSet;
import java.sql.SQLException;

import jlib.sql.*;
import jlib.xml.*;

/*
DB Table on MySQL
// Table Header
CREATE TABLE 'logheader' (
  'Id' int(10) unsigned NOT NULL auto_increment,
  'Ins_Date' timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  'Type' int(10) unsigned NOT NULL default '0',
  'File' varchar(255) character set latin1 collate latin1_bin default NULL,
  'Line' int(10) unsigned NOT NULL default '0',
  'Thread' varchar(45) NOT NULL default '',
  'Method' varchar(255) NOT NULL default '',
  'StartTime' int(10) unsigned NOT NULL default '0',
  'EventName' varchar(255) NOT NULL default '',
  'Message' text NOT NULL,
  PRIMARY KEY  ('Id')
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

// Table Details
CREATE TABLE 'logdetails' (
  'Id' int(10) unsigned NOT NULL default '0',
  'DetailId' int(10) unsigned NOT NULL auto_increment,
  'Name' varchar(255) NOT NULL default '',
  'Value' text NOT NULL,
  PRIMARY KEY  ('DetailId')
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
*/

public class LogCenterDb extends LogCenter
{
	public LogCenterDb(LogCenterLoader logCenterLoader)
	{
		super(logCenterLoader);
	}
	
//	private boolean normalizeAppend(String cs)
//	{
//		if(cs.equalsIgnoreCase("false"))
//			return false;
//		else if(cs.equalsIgnoreCase("0"))
//			return false;
//		return true;		
//	}
	
	private String m_csMasterTable = null;
	private String m_csDetailsTable = null;
	private String m_csDbUser = null;
	private String m_csDbPassword = null;
	private String m_csDbUrl = null;
	private String m_csDbProvider = null;
	private boolean m_bUseSequence = false;
	DbConnectionManager m_manager = null;
	DbConnectionBase m_dbConnection = null;
		
	public void loadSpecificsEntries(Tag tagLogCenter)	// Special values for file appenders
	{
		m_csDbUser = tagLogCenter.getVal("DbUser");
		m_csDbPassword = tagLogCenter.getVal("DbPassword");
		m_csDbUrl = tagLogCenter.getVal("DbUrl");
		m_csDbProvider = tagLogCenter.getVal("DbProvider");
		m_csMasterTable = tagLogCenter.getVal("MasterTable");
		m_csDetailsTable = tagLogCenter.getVal("DetailsTable");
	}
	
	boolean open()
	{
		boolean b = false;
		int nTime_Ms = 1000 * 60 * 10;		// 10 minutes
		m_manager = new DbConnectionManager();
		if(m_csDbProvider.equalsIgnoreCase("MySql"))
		{
			b = m_manager.initMySql(m_csDbUrl, m_csDbUser, m_csDbPassword, null, 2, nTime_Ms, -1, 0);
			m_bUseSequence = false;
		}
		else if(m_csDbProvider.equalsIgnoreCase("Oracle"))
		{
			b = m_manager.initOracle(m_csDbUrl, m_csDbUser, m_csDbPassword, null, 2, nTime_Ms, -1, 0);
			m_bUseSequence = false;
		}
		else if(m_csDbProvider.equalsIgnoreCase("SqlServer"))
		{
			b = m_manager.initSqlServer(m_csDbUrl, m_csDbUser, m_csDbPassword, null, 2, nTime_Ms, -1, 0);
			m_bUseSequence = false;
		}
		else if(m_csDbProvider.equalsIgnoreCase("DB2"))
		{
			b = m_manager.initDB2(m_csDbUrl, m_csDbUser, m_csDbPassword, null, 2, nTime_Ms, -1, 0);
			m_bUseSequence = false;
		}
		else
		{
			b = m_manager.initDriverClass(m_csDbUrl, m_csDbUser, m_csDbPassword, null, m_csDbProvider, 2, nTime_Ms, -1, 0);
			m_bUseSequence = false;
		}
			
		return b;
	}
	
	boolean closeLogCenter()
	{
		return true;
	}
	
	void preSendOutput()
	{
		try
		{
			m_dbConnection = m_manager.getConnection("LogStatement", null, true);
		}
		catch (DbConnectionException e)
		{
			e.printStackTrace();
			m_dbConnection = null;
		}	
	}

	void sendOutput(LogParams logParam)
	{	
		// Insert header
		if(m_dbConnection != null)
		{
			String cs;
			
			if(!m_bUseSequence)
				cs = "Insert into " + m_csMasterTable + " (" +
					"Log_Type, File_Name, Line, Thread, Method, Start_Time, Event_Name, Message) Values (" +
					"?,    ?,    ?,    ?,      ?,      ?,         ?,         ?)";
			else
				cs = "Insert into " + m_csMasterTable + " (" +
					"Log_Type, File_Name, Line, Thread, Method, Start_Time, Event_Name, Message, Id) Values (" +
					"?,    		?,    		?,    ?,      ?,      ?,         ?,         ?, 		 SEQ_LOG_ID.nextval)";
				 
			int nCol = 0;
			DbPreparedStatement stInsertHeader = m_dbConnection.prepareStatement(cs, 0, false);
			stInsertHeader.setColParam(nCol++, logParam.getType());
			stInsertHeader.setColParam(nCol++, logParam.getFile());
			stInsertHeader.setColParam(nCol++, logParam.getLine());
			stInsertHeader.setColParam(nCol++, logParam.getThreadName());
			stInsertHeader.setColParam(nCol++, logParam.getMethod());
			stInsertHeader.setColParam(nCol++, logParam.getStartTime());
			stInsertHeader.setColParam(nCol++, logParam.getEventName());
			stInsertHeader.setColParam(nCol++, logParam.getMessage());
			
			
			int n0 = stInsertHeader.executeInsert();
			
			long lLastId = 0;
			cs = "SELECT Id FROM " + m_csMasterTable + " order by Id desc";
			DbPreparedStatement stSelectLastId = m_dbConnection.prepareStatement(cs, 0, false);
			ResultSet resultSet = stSelectLastId.executeSelect();
			if(resultSet != null)
			{
				try
				{
					resultSet.next();
					lLastId = resultSet.getLong(1);
				} 
				catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//resultSet.close();	// TBD
			}
			
			if(!m_bUseSequence)
				cs = "Insert into " + m_csDetailsTable + " (Id, Name, Value) Values (?,  ?,    ?)";
			else
				cs = "Insert into " + m_csDetailsTable + "(Id, Name, Value, Detail_Id) Values (?, ?, ?, SEQ_LOGDETAIL_ID.nextval)";
			
			int nNbMembers = logParam.getNbParamInfoMember();
			
			DbPreparedStatement insDetails = m_dbConnection.prepareStatement(cs, 0, false);
			for(int nMember=0; nMember<nNbMembers; nMember++)
			{
				LogInfoMember member = logParam.getParamInfoMember(nMember);
				if(member != null)
				{					 
					nCol = 0;
					
					insDetails.setColParam(nCol++, lLastId);
					insDetails.setColParam(nCol++, member.getName());
					insDetails.setColParam(nCol++, member.getValue());
						
					insDetails.executeInsert();
				}			
			}
		}
	}
	
	void postSendOutput()
	{
		if(m_dbConnection != null)
		{
			m_dbConnection.commit();
			m_dbConnection.returnConnectionToPool();
		}
	}
	
	public String getType()
	{
		return "LogCenterDb";
	}
}