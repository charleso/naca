/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import jlib.sql.DbConnectionBase;
import jlib.sql.DbConnectionException;
import jlib.sql.DbConnectionManager;
import jlib.sql.DbPreparedStatement;
import jlib.xml.Tag;
/**
 * Log center saving events into a flat database.
 * This log center is used to keep record of applications execution cycle:
 * <ul>
 * 	<li>The application starts.</li>
 * 	<li>Eventually the application launches another application to perform some
 * 	additional task.</li>
 * 	<li>The application processes elements of information.</li>
 * 	<li>Eventually the application founds an error in some of the information 
 * 	elements to process. Then it skips the current element to process the next
 * 	one.</li>
 * 	<li>Eventually, the error is so critical that the application has to
 * 	abort prematurely.</li>
 * 	<li>Application reports the amount of processed information elements.</li>
 * 	<li>Application finishes.</li>
 * </ul>
 * Events logged through this class are thereafter visible with the
 * COP/Log interface (http://c930cop.consultas.ch/LOG).
 * For the COP/LOG interface to be able to group events by execution cycles, and to
 * keep track of applications launching child applications, some identifiers are needed.
 * <ul>
 * 	<li><i>Process</i> is the internal application name. The term "Application Name"
 * 	has to be used in its most general sense.</li>
 * 	<li><i>RuntimeId</i> is a unique identifier retrieved when the application starts,
 * 	and kept during all the application execution cycle. It is used to stamp all
 * 	events raised by the application.</li>
 * 	<li><i>RunId</i> is a unique identifier retrieved when the application starts,
 * 	much like <i>RuntimeId</i>. The difference is that, if the application launches
 * 	a child application, it will pass the <i>RunId</i> identifier. It is used to stamp all
 * 	events raised by the application, plus all events raised by the child applications.</li>
 * </ul>
 * The COP/Log interface produces two types of reports:
 * <ul>
 * 	<li>Execution reports, that show when an application starts, stops, how
 * 	many information elements it has processed, how many exceptions has been raised,
 * 	how much time it has been running, etc.</li>
 * 	<li>Commercial reports, that show how many elements of a particular client, product,
 * 	brand, source, etc. has been processed in general, or by a particular application.</li>
 * </ul>
 * For that second kind of report, an additional identifier is needed to specify the
 * client, product, brand, source, etc. This additional identifier is the <i>Product</i>.<p/>
 * 
 * This is a typical <i>LogCenterDbFlat</i> configuration:
 * <pre>
 * </pre>
 * This configuration predefines the default <i>Process</i> name, and the default <i>Product</i> 
 * name. That means that any {@link LogEvent} not specifying 
 * the {@link LogEvent#getProcess} or the {@link LogEvent#getProduct}, will be assigned to
 * the default ones if the event is accepted by the log center.  
 */

public class LogCenterDbFlat extends LogCenter
{
	public LogCenterDbFlat(LogCenterLoader logCenterLoader)
	{
		super(logCenterLoader);
	}
	
	private String m_csTable = null;
	private String m_csTableRunId = null;
	private String m_csLogEventDefinitionTable = null;
	private String m_csDbUser = null;
	private String m_csDbPassword = null;
	private String m_csDbUrl = null;
	private String m_csDbProvider = null;
	private String m_csMachine = null;
	private String m_csRunMode = null;
	private Hashtable<Integer, Boolean> m_hashDefinedLogEvent = new Hashtable<Integer, Boolean> ();
	
	DbConnectionManager m_manager = null;
	DbConnectionBase m_dbConnection = null;
		
	public void loadSpecificsEntries(Tag tagLogCenter)	// Special values for file appenders
	{
		m_csDbUser = tagLogCenter.getVal("DbUser");
		m_csDbPassword = tagLogCenter.getVal("DbPassword");
		m_csDbUrl = tagLogCenter.getVal("DbUrl");
		m_csDbProvider = tagLogCenter.getVal("DbProvider");
		m_csTable = tagLogCenter.getVal("Table");
		m_csTableRunId = tagLogCenter.getVal("TableRunId");
		m_csMachine = tagLogCenter.getVal("Machine");
		m_csProcess = tagLogCenter.getVal("Process");
		m_csRunMode = tagLogCenter.getVal("RunMode");
		m_csLogEventDefinitionTable = tagLogCenter.getVal("LogEventDefinitionTable");
	}
	
	boolean open()
	{
		boolean b = false;
		int nTime_Ms = 1000 * 60 * 10;		// 10 minutes
		m_manager = new DbConnectionManager();

		if(m_csDbProvider.equalsIgnoreCase("MySql"))
		{
			b = m_manager.initMySql(m_csDbUrl, m_csDbUser, m_csDbPassword, null, 8, nTime_Ms, -1, 0);
		}
		else if(m_csDbProvider.equalsIgnoreCase("Oracle"))
		{
			b = m_manager.initOracle(m_csDbUrl, m_csDbUser, m_csDbPassword, null, 8, nTime_Ms, -1, 0);
		}
		else if(m_csDbProvider.equalsIgnoreCase("SqlServer"))
		{
			b = m_manager.initSqlServer(m_csDbUrl, m_csDbUser, m_csDbPassword, null, 8, nTime_Ms, -1, 0);
		}
		else if(m_csDbProvider.equalsIgnoreCase("DB2"))
		{
			b = m_manager.initDB2(m_csDbUrl, m_csDbUser, m_csDbPassword, null, 8, nTime_Ms, -1, 0);
		}
		else
		{
			b = m_manager.initDriverClass(m_csDbUrl, m_csDbUser, m_csDbPassword, null, m_csDbProvider, 8, nTime_Ms, -1, 0);
		}
		
		try
		{
			m_dbConnection = m_manager.getConnection("LogStatement", null, true);
		}
		catch (DbConnectionException e)
		{
			e.printStackTrace();
		}
		if(m_dbConnection != null)
			loadDefinedLogEvent();

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
			// TODO Auto-generated catch block
			e.printStackTrace();
			m_dbConnection = null;
		}	
	}

	void sendOutput(LogParams logParam)
	{		
		if(m_dbConnection != null)
		{
			int nEventId = logParam.getEventId();
			manageLogEventDefinition(logParam, nEventId);

			String csRunId=logParam.m_csRunId;
			if (csRunId==null)
				csRunId=getRunId();

			String csRuntimeId=logParam.m_csRuntimeId;
			if (csRuntimeId==null)
				csRuntimeId=getRuntimeId();

			String csProduct=logParam.m_logEvent.getProduct();
			if (csProduct==null)
				csProduct=getProduct();

			String csProcess=logParam.m_logEvent.getProcess();
			if (csProcess==null)
				csProcess=getProcess();

			String csParamNames = "";
			String csParamQuestions = "";
			int nNbParam = logParam.getNbParamInfoMember();
			for(int n=0; n<nNbParam; n++)
			{
				csParamNames += ", Parameter_Value" + n;
				csParamQuestions += ", ?";
			}
			
			String cs = "Insert into " + m_csTable +
				"(Machine, Process, Run_Mode, Ins_Date, Event_Message, Log_Type, File_Name, Line, Thread, Method, Start_Time, Event_Id, Run_Id, Product, Runtime" + csParamNames +  
				") values (" +  
				" ?,       ?,       ?,        ?,        ?,             ?,        ?,         ?,    ?,      ?,      ?,          ?,        ?,      ?,       ?" + csParamQuestions + 
				")";   
			int nCol = 0;
			DbPreparedStatement stInsert = m_dbConnection.prepareStatement(cs, 0, false);
			stInsert.setColParam(nCol++, m_csMachine);
			stInsert.setColParam(nCol++, csProcess);
			stInsert.setColParam(nCol++, m_csRunMode);
			stInsert.setColParam(nCol++, logParam.getDisplayTimestamp());
			stInsert.setColParam(nCol++, logParam.getMessage());
			stInsert.setColParam(nCol++, logParam.getType());
			stInsert.setColParam(nCol++, logParam.getFile());
			stInsert.setColParam(nCol++, logParam.getLine());
			stInsert.setColParam(nCol++, logParam.getThreadName());
			stInsert.setColParam(nCol++, logParam.getMethod());
			stInsert.setColParam(nCol++, logParam.getStartTime());
			stInsert.setColParam(nCol++, nEventId);
			stInsert.setColParam(nCol++, csRunId);
			stInsert.setColParam(nCol++, csProduct);
			stInsert.setColParam(nCol++, csRuntimeId);
	//		Runtime rt = Runtime.getRuntime();
	//		String csRuntime = rt.toString();
	//		stInsert.setColParam(nCol++, csRuntime);
			for(int n=0; n<nNbParam; n++)
			{
				String csParam = logParam.getItemValue(n);
				stInsert.setColParam(nCol++, csParam);
			}
			
			int n0 = stInsert.executeInsert();
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
	
	private void loadDefinedLogEvent()
	{
		String cs = "Select Event_Id from " + m_csLogEventDefinitionTable; 
		DbPreparedStatement st = m_dbConnection.prepareStatement(cs, 0, false);
		ResultSet rs = st.executeSelect();
		if(rs != null)
		{
			boolean bNext;
			try
			{
				bNext = rs.next();
				while(bNext)
				{
					Integer i = rs.getInt("Event_Id");
					m_hashDefinedLogEvent.put(i, new Boolean(true));
					bNext = rs.next();
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private synchronized void manageLogEventDefinition(LogParams logParam, int nEventId)
	{
		Boolean b = m_hashDefinedLogEvent.get(nEventId);
		if(b == null)	// Log event not defined yet
		{
			boolean bInserted = addLogEventDefinition(nEventId, logParam);
			if(bInserted)
			{
				m_hashDefinedLogEvent.put(nEventId, new Boolean(true));
			}
		}
	}
	
	private boolean addLogEventDefinition(int nEventId, LogParams logParam)
	{
		String csParamNames = "";
		String csParamQuestions = "";
		int nNbParam = logParam.getNbParamInfoMember();
		for(int n=0; n<nNbParam; n++)
		{
			csParamNames += ", Parameter_Name" + n;
			csParamQuestions += ", ?";
		}
		
		String cs = "Insert into " + m_csLogEventDefinitionTable +
			"(Event_Name, Event_Id, Short_Event_Name" + csParamNames +    
			") values (" +  
			"?,           ?,         ?" + csParamQuestions + ")";  
			   
		int nCol = 0;
		DbPreparedStatement stInsert = m_dbConnection.prepareStatement(cs, 0, false);
		stInsert.setColParam(nCol++, logParam.getEventName());
		stInsert.setColParam(nCol++, nEventId);
		stInsert.setColParam(nCol++, logParam.getShortEventName());
		for(int n=0; n<nNbParam; n++)
		{
			LogInfoMember info = logParam.getParamInfoMember(n);
			stInsert.setColParam(nCol++, info.getName());
		}
		
		int n = stInsert.executeInsert();
		if(n == 1)
			return true;
		return false;
	}
/**
 * Returns the current <i>RunId</i> identifier.
 * If it is not set (or has been set to <i>null</i>), creates a new
 * unique identifier.
 */
	public String getRunId() {
		if (m_csRunId==null) 
		{
			m_csRunId=generateIdentifier();
		}
		return m_csRunId;
	}

/**
 * Returns the current <i>RuntimeId</i> identifier.
 * If it is not set (or has been set to <i>null</i>), creates a new
 * unique identifier.
 */
	public String getRuntimeId() {
		if (m_csRuntimeId==null) 
		{
			m_csRuntimeId=generateIdentifier();
		}
		return m_csRuntimeId;
	}

	synchronized private String generateIdentifier()
	{
		String csOut = "0";
		DbConnectionBase dbConnection;
		try 
		{
			dbConnection=m_manager.getConnection("LogStatement", null, true);
		}
		catch (DbConnectionException e) 
		{
			e.printStackTrace();
			return csOut;
		}
	
		String cs = "Select RunId from " + m_csTableRunId + " where channel=''";	
		DbPreparedStatement stSelect = dbConnection.prepareStatement(cs, 0, false);
		ResultSet rs = stSelect.executeSelect();
		if (rs==null) {
			try {
				System.out.println(dbConnection.getDbConnection().getWarnings().getMessage());
			} catch (SQLException s) {
				System.out.println(s.getMessage());
			}
		}
		if(rs != null)
		{
			boolean bNext;
			try
			{
				bNext = rs.next();
				if(bNext)
				{
					int nRunId = rs.getInt("RunId");
					nRunId++;
//					stSelect.close();
//					String csUpdate = "update " + m_csTableRunId + " set RunId=" + nRunId + " where channel='"+csOrganisation+"'";		
					String csUpdate = "update " + m_csTableRunId + " set RunId=" + nRunId + " where channel=''";		
					DbPreparedStatement stUpdate = dbConnection.prepareStatement(csUpdate, 0, false);
					int n = stUpdate.executeUpdate();
					csOut = String.valueOf(nRunId);
					stUpdate.close();
				}
				else	// 1st record
				{
//					stSelect.close();
					String csInsert = "Insert into " + m_csTableRunId + " (Channel, RunId) values (?, ?)";
					DbPreparedStatement stInsert = dbConnection.prepareStatement(csInsert, 0, false);
//					stInsert.setColParam(0, csOrganisation);
					stInsert.setColParam(0, "");
					stInsert.setColParam(1, 1);
					int n = stInsert.executeInsert();
					csOut = "1";
					stInsert.close();
				}
			}
			catch (SQLException e)
			{
				stSelect.close();
			}
		}
		dbConnection.commit();
		dbConnection.returnConnectionToPool();
		return csOut;
	}
	
	public String getType()
	{
		return "LogCenterDbFlat";
	}
}

