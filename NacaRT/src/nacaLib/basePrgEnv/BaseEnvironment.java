/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.basePrgEnv;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import jlib.log.Log;
import jlib.misc.CurrentDateInfo;
import jlib.misc.JVMReturnCodeManager;
import jlib.misc.ThreadSafeCounter;
import jlib.misc.Time_ms;
import jlib.sql.DbConnectionBase;
import jlib.sql.DbConnectionException;
import jlib.sql.DbConnectionManagerBase;
import jlib.xml.Tag;
import nacaLib.CESM.CESMQueueManager;
import nacaLib.CESM.CESMReturnCode;
import nacaLib.CESM.CESMStartData;
import nacaLib.accounting.AccountingRecordProgram;
import nacaLib.accounting.AccountingRecordTrans;
import nacaLib.accounting.CriteriaEndRunMain;
import nacaLib.base.CJMapObject;
import nacaLib.exceptions.AbortSessionException;
import nacaLib.misc.CCommarea;
import nacaLib.misc.KeyPressed;
import nacaLib.sqlSupport.SQLConnection;
import nacaLib.tempCache.TempCache;
import nacaLib.tempCache.TempCacheLocator;
import nacaLib.varEx.Var;

import org.w3c.dom.Document;

public abstract class BaseEnvironment extends CJMapObject implements SessionEnvironmentRequester
{
	private DbConnectionBase m_SQLConnection = null;
	private DbConnectionManagerBase m_connectionManager = null;
	protected String m_csCurrentTransaction = "" ;
	private String m_csTransaction1stProgram = "";
	private BaseSession m_baseSession = null; 
	private Integer m_iEnvId = null; 
	private static ThreadSafeCounter ms_id = new ThreadSafeCounter();
	private CurrentDateInfo m_creationDateInfo = null;
	private int m_nSumTransactionsExecTime_ms = 0;
	private int m_nNbTransactionsExecuted = 0;
	private boolean m_bInitialConnectDb = true;	// true if db conection is established before lauchin 1st program
	private FileManager m_fileManager = null;
	private boolean m_bExternalConnection = false;
	private boolean m_bSimulateRealEnvironment = false;
	
	public BaseEnvironment(BaseSession baseSession, DbConnectionManagerBase connectionManager, BaseResourceManager baseResourceManager)
	{	
		m_baseSession = baseSession;
		m_connectionManager = connectionManager ;
		m_QueueManager = new CESMQueueManager(this);
		
		m_accountingRecordManager = new AccountingRecordTrans(baseResourceManager);
		m_iEnvId = ms_id.inc();
		m_creationDateInfo = new CurrentDateInfo();
		m_bSimulateRealEnvironment = baseResourceManager.getSimulateRealEnvironment();
	}
	
	public DbConnectionManagerBase getDbConnectionManager()
	{
		return m_connectionManager;
	}
	
	public CurrentDateInfo getCreationDateInfo()
	{
		return m_creationDateInfo;
	}
	
	public Integer getEnvId()
	{
		return m_iEnvId;
	}
	
	public BaseSession getBaseSession()
	{
		return m_baseSession;
	}
	
	public void resetSession()
	{
		m_baseSession = null;
	}
	
	public Document getLastScreenXMLData()
	{
		if(m_baseSession != null)
			return m_baseSession.getLastScreenXMLData();
		return null;
	}
	
	public void setCurrentTransaction(String csTransactionID, String csProgramID)
	{
		m_csCurrentTransaction = csTransactionID ;
		m_csTransaction1stProgram = csProgramID ;
	}

	public String getCurrentTransaction()
	{
		return m_csCurrentTransaction ;
	}
	
	public void DEBUGremoveDBConnection()
	{
		m_SQLConnection = null; 
	}
	
	public void fillEnvConnectionWithAllocatedConnection(Connection spConnection, String csPrefId, String csEnv, boolean bUseCachedStatements)
	{
		m_SQLConnection = new SQLConnection(spConnection, csPrefId, csEnv, bUseCachedStatements, false, null);
	}
	
	public DbConnectionBase getNewSQLConnection()
	{
		if(m_connectionManager != null)
		{
			try
			{
				DbConnectionBase newSQLConnection = m_connectionManager.getNewConnection(m_csTransaction1stProgram, BaseResourceManager.getUseStatementCache()) ;
				return newSQLConnection;
			}
			catch (DbConnectionException e)
			{
				Log.logImportant("Db connection error: "+e.toString());
			}
		}		
		return null;
	}
	
	public DbConnectionBase getSQLConnection()
	{
		if(m_SQLConnection == null && m_connectionManager != null)
		{
			try
			{
				m_SQLConnection = m_connectionManager.getConnection(m_csTransaction1stProgram, m_csProgramParent, BaseResourceManager.getUseStatementCache()) ;
			}
			catch (DbConnectionException e)
			{
				Log.logImportant("Db connection error: "+e.toString());

				//JVMReturnCodeManager.exitJVM(8);	// No connection provided: Do not exit as it kills tomcat !
			}
		}		
		return m_SQLConnection ;
	}	

	public boolean abortTransWhenInvalidDbConnection()
	{
		if(!hasSQLConnection())
		{
			endRunTransaction(CriteriaEndRunMain.Abort);
			JVMReturnCodeManager.exitJVM(8);	// No connection provided
			return false;
		}
		return true;
	}
	
	public boolean hasSQLConnection()
	{
		if(m_SQLConnection == null)
			return false;
		return true;
	}
	
	public void releaseSQLConnection()
	{
		if(!m_bExternalConnection)	// Release only internal connection
		{
			if (m_SQLConnection != null)
			{
				if(m_connectionManager != null)
					m_connectionManager.returnConnection(m_SQLConnection);
			}
		}
		else
			m_bExternalConnection = false;	// Not an external connection (reset status for next reuse of the environment)

		// The environment has no knowledge anymore of the connection 
		m_SQLConnection = null;
	}
	
	private void getTempCacheFromStack()
	{
		m_TempCache = TempCacheLocator.setTempCache();
	}
	
	public void returnTempCacheToStack()
	{
		if (m_TempCache != null)
		{
			TempCacheLocator.relaseTempCache();
			m_TempCache = null;
		}
	}
	
	private TempCache m_TempCache = null;
	
	/**
	 * 
	 */
	public SQLException commitSQL()
	{
		if(!m_bExternalConnection)
		{
			if (m_SQLConnection != null)
			{
				return m_SQLConnection.commitWithException() ;
			}
		}
		return null;
	}

	/**
	 * 
	 */
	public SQLException rollbackSQL()
	{
		if(!m_bExternalConnection)
		{
			if (m_SQLConnection != null)
			{
				return m_SQLConnection.rollBackWithException() ;
			}
		}
		return null;
	}
	
	public String getNextProgramToLoad()
	{
		return m_csNextProgramToLoad ;
	}
	
	public void setNextProgramToLoad(String csProgramId)
	{
		m_csNextProgramToLoad = csProgramId.trim() ;
		m_csProgramParent = null;
	}
	
	public void setNextProgramToLoad(String csProgramId, String csProgramParent)
	{
		m_csNextProgramToLoad = csProgramId.trim() ;
		m_csProgramParent = csProgramParent;
	}

	protected void deQueueProgram()
	{
			if(!m_qPrograms.isEmpty())
				m_csNextProgramToLoad = (String)m_qPrograms.remove();
			else
				m_csNextProgramToLoad = "";
		m_Commarea = null ;

	}
	
	public void doEnqueueProgram(String csProg)
	{
		m_qPrograms.add(csProg);
	}
	
	private String m_csNextProgramToLoad = "" ;
	private String m_csProgramParent = null;
	private Queue m_qPrograms = new SynchronousQueue() ;
	
	private CCommarea m_Commarea = null ;

	public CCommarea getCommarea()
	{
		return m_Commarea ;
	}

	public void setCommarea(CCommarea commarea)
	{
		m_Commarea = commarea ;
	}
	
	public void resetNewTransaction()
	{
		doResetNewTransaction();
	}

	protected void doResetNewTransaction()
	{
		resetDateTime() ;
		setNextProgramToLoad("");
	}
	
	private Date m_StartTime = new Date() ;
	
	public void resetDateTime()
	{
		m_StartTime = new Date() ;
	}
	
	public String getTime()
	{
		SimpleDateFormat formater = new SimpleDateFormat("'0'HHmmss");
		String cs = formater.format(m_StartTime);
		return cs ;
	}
	
	public String getDate()
	{
		SimpleDateFormat formater  ;
		//Calendar cal = Calendar.getInstance() ;
		formater = new SimpleDateFormat("'01'yyDDD");
		String cs = formater.format(m_StartTime);
		return cs ;
	}
	
	public boolean hasOutput()
	{
		return false;
	}
	
	public void RegisterOutput()
	{
	}

	private Tag m_tagConfig = null ;
	

	public void Init(Tag tagCESMConfig)
	{
		configInit(tagCESMConfig);
	}
	
	protected void configInit(Tag tagCESMConfig)
	{
		if(tagCESMConfig != null)
			m_tagConfig = tagCESMConfig.getChild("Config") ;
	}
	
	public String getLanguageCode()
	{
		return "";
	}
	
	public boolean isLinux()
	{
		String linux = getConfigOption("StartBatchLinux");
		return Boolean.parseBoolean(linux);
	}
	/**
	 * @param string
	 * @return
	 */
	public String getConfigOption(String string)
	{
		if (m_tagConfig != null)
		{
			return m_tagConfig.getVal(string);
		}
		return "";
	}
	
	public String getUserLanguageId()
	{
		return "";
	}
	
	public String getProfitCenter()
	{
		return "";
	}
	
	public String getCmpSession()
	{
		return "";
	}
	
	public String getUserId()
	{
		return "";
	}
	
	public String getUserLdapId()
	{
		return "";
	}
	

	public abstract BaseCESMManager createCESMManager();
	public abstract BaseSession getSession();
	
	
	protected String m_csLastCommandCode = "" ;
	public String getLastCommandCode()
	{
		return m_csLastCommandCode ;
	}

	public void setLastCommandCode(String string)
	{
		m_csLastCommandCode = string ;		
	}
	
	
	private CESMReturnCode m_LastCommandReturnCode = CESMReturnCode.NORMAL ;
	public CESMReturnCode getLastCommandReturnCode()
	{
		return m_LastCommandReturnCode ;
	}	
	
	public void setCommandReturnCode(CESMReturnCode cs)
	{
		m_LastCommandReturnCode = cs ;
	}

	
	private CESMQueueManager m_QueueManager = null;
	public CESMQueueManager getQueueManager()
	{
		return m_QueueManager;
	}

	public String getTerminalID()
	{
		return m_csTermID ;
	}
	protected String m_csTermID = "" ;
	
	
	private Queue m_qData = new SynchronousQueue() ;
	
	public void enqueueProgram(String csTransID, CESMStartData data)
	{
		doEnqueueProgram(csTransID);
		enqueueData(data);
	}
	
	public void enqueueData(CESMStartData data)
	{
		m_qData.add(data) ;
	}


	public CESMStartData GetEnqueuedData()
	{
			if (m_qData.isEmpty())
			{
				return null ;
			}
			CESMStartData v = (CESMStartData)m_qData.remove() ;
			return v ;
	}
	
	public void StartAsynchronousProgram(String transID, String csProgramParent, CESMStartData data, int intervalTimeSeconds)
	{
		BaseProgramLoader.StartAsynchronousProgram(transID, csProgramParent, data, intervalTimeSeconds);
	}
	
	/**
	 * @return
	 */
	public String getApplicationCredentials()
	{
		return m_csApplicationCredentials ;
	}
	public void resetApplicationCredentials(String cs)
	{
		m_csApplicationCredentials = cs ;
	}
	protected String m_csApplicationCredentials = "" ;
	

	
	protected char [] m_acTCTTUA = new char [1024];
	public char [] getTCTUA()
	{
		return m_acTCTTUA ;
	}
	

	protected char [] m_acTWA = new char [1024];
	public char [] getTWA()
	{
		return m_acTWA ;
	}
	
	protected char [] m_acCWA = new char [1024];
	public char [] getCWA()
	{
		return m_acCWA ;
	}
	
	public Document getXMLData()
	{
		return null;
	}
	
	protected KeyPressed m_KeyPressed = null ;
	public KeyPressed GetKeyPressed()
	{
		if (m_KeyPressed != null)
		{
			return m_KeyPressed;
		}
		else
		{
			return null ;
		}
	}
	
	public void resetKeyPressed()
	{
		m_KeyPressed = null ;
	}
	
	public void setKeyPressed(Var v)
	{
		m_KeyPressed = KeyPressed.getKey(v);
		assertIfNull(m_KeyPressed);
	}
	
	public void setKeyPressed(KeyPressed keyPressed)
	{
		m_KeyPressed = keyPressed;
//		assertIfNull(m_KeyPressed);
	}
	
	// Accounting
	public void setInitialConnectDb(boolean bInitialConnectDb)
	{
		m_bInitialConnectDb = bInitialConnectDb;
	}
	
	public void setExternalDbConnection(DbConnectionBase dbConnection)
	{
		if(dbConnection != null)	// Provide an external db connection by caller
		{
			m_bInitialConnectDb = false;
			m_SQLConnection = dbConnection;
			m_bExternalConnection = true;
		}
		else	// No db connection provided: It means that nacaRT must estblish itslef the connection
		{
			m_bInitialConnectDb = true;
		}
	}
	
	public boolean startRunTransaction()
	{
		if(m_bSimulateRealEnvironment)
		{
			m_abStopProcessing.set(false);
			getTempCacheFromStack();
			m_accountingRecordManager.startRunTransaction(m_csCurrentTransaction);
			startSessionRequest(m_csCurrentTransaction);
			
			TransThreadManager.startTransaction(this);
			return true;
		}
		
		boolean bStarted = true;
		m_abStopProcessing.set(false);
				
		getTempCacheFromStack();
		
		if(m_bInitialConnectDb)
		{
			m_SQLConnection = null;
			DbConnectionBase con = getSQLConnection();	// Establish a sql connection before lauching 1st program
			if(con == null)
				bStarted = false;
		}
		
		if(bStarted)
		{
			m_accountingRecordManager.startRunTransaction(m_csCurrentTransaction);
			startSessionRequest(m_csCurrentTransaction);
			
			TransThreadManager.startTransaction(this);
		}
		return bStarted;
	}
	
	public void endRunTransaction(CriteriaEndRunMain criteria)
	{
		if(m_accountingRecordManager != null) 
		{
			m_accountingRecordManager.endRunTransaction(m_csCurrentTransaction, criteria);
		}
		endSessionRequest();
		TransThreadManager.endTransaction(this);
	}
	
	void startRunProgram(String csProgramName)
	{
		if(!m_accountingRecordManager.isFilled())
			m_accountingRecordManager.setSessionPub2000Info(getSession(), getProfitCenter(), getUserId());

		AccountingRecordProgram accountingRecord = m_accountingRecordManager.createNewAccountingRecord(m_csCurrentTransaction, m_csTermID);
		accountingRecord.beginRunProgram(csProgramName);
	}

	void endRunProgram(CriteriaEndRunMain criteria)
	{
		m_accountingRecordManager.endRunProgram(criteria);
	}

	public AccountingRecordTrans getAccountingRecordManager()
	{
		return m_accountingRecordManager; 
	}
	
	private AccountingRecordTrans m_accountingRecordManager = null; 
	
	
	// Anti-loop management
	private void startSessionRequest(String csCurrentTransaction)
	{
		m_dateStart.setNow();
		m_lSessionRequestEndBefore_ms = BaseResourceManager.getSessionRequestEndTimeLimit(csCurrentTransaction);
		m_envStatus = EnvironmentStatus.RUNNING;
	}
	
	void offsetMaxTimeLimit(long lOffset_ms)
	{
		m_lSessionRequestEndBefore_ms += lOffset_ms; 
	}

	private void endSessionRequest()
	{
		m_nNbTransactionsExecuted++;
		m_dateEnd.setNow();
		m_lSessionRequestEndBefore_ms = 0;	// No running 
		m_envStatus = EnvironmentStatus.STOPPED;
		m_nSumTransactionsExecTime_ms += (int)getStartRunTime().getTimeOffset_ms(getEndRunTime());
	}
	
	void requestStopProcessing()
	{
		m_envStatus = EnvironmentStatus.STOP_REQUESTED;
		m_abStopProcessing.set(true);
	}
		
	CurrentDateInfo getStartRunTime()
	{
		return m_dateStart;
	}
	
	CurrentDateInfo getEndRunTime()
	{
		return m_dateEnd;
	}
		
	int getLastTransactionExecTime_ms()
	{
		if(!isRunning())
			return (int)getStartRunTime().getTimeOffset_ms(getEndRunTime());
		return (int)getStartRunTime().getTimeOffsetFromNow_ms();
	}
	
	int getSumTransactionsExecTime_ms()
	{
		if(isRunning())
			return (int)getStartRunTime().getTimeOffset_ms(getEndRunTime()) + m_nSumTransactionsExecTime_ms;
		return (int)m_nSumTransactionsExecTime_ms;
	}
	
	int getNbTransactionsExecuted()
	{
		return m_nNbTransactionsExecuted;
	}
		
	String getStatusAsString()
	{
		return m_envStatus.getString();
	}
	
	boolean isRunning()
	{
		return m_envStatus.isRunning();
	}
	
	int getRunningTime_ms()
	{
		CurrentDateInfo now = new CurrentDateInfo();
		int n = (int)(now.getTimeInMillis() - m_dateStart.getTimeInMillis());
		return n;
	}
	
	public void breakCurrentSessionIfTimeout()
	{		
		if(m_abStopProcessing.get())	// Forced stop
		{
			AbortSessionException exp = new AbortSessionException() ;
			exp.m_Reason = new Error("SessionForcedStop");
			exp.m_ProgramName = null;  // register current program that throws the exception.
			throw exp ;
		}
		
		if(m_lSessionRequestEndBefore_ms != 0)
		{
			long lAlmostCurrentTime_ms = Time_ms.getCurrentTime_ms();
			if(lAlmostCurrentTime_ms > m_lSessionRequestEndBefore_ms)
			{
				AbortSessionException exp = new AbortSessionException() ;
				exp.m_Reason = new Error("SessionTimeoutInternal");
				exp.m_ProgramName = null;  // register current program that throws the exception.
				throw exp ;
			}
		}
	}
	
	boolean canManageThreadMBean()
	{
		if(m_accountingRecordManager != null)
			return true;
		return false;
	}
	
	public String getSocietyCode()
	{
		String cs = "   ";
		return cs ;
	}
	
	public String getApplication()
	{
		String cs = "  ";
		return cs ;
	}
	
	public FileManagerEntry getFileManagerEntry(String csLogicalName)
	{
		if(m_fileManager == null)
			m_fileManager = new FileManager();
		return m_fileManager.getFileManagerEntry(csLogicalName);
	}
	
	public void autoCloseOpenFile()
	{
		if(m_fileManager != null)
			m_fileManager.autoCloseOpenFile();
	}
	
	public void autoFlushOpenFile()
	{
		if(m_fileManager != null)
			m_fileManager.autoFlushOpenFile();
	}
	
	public void cleanupOnExceptionCatched()
	{
		rollbackSQL() ;
		releaseSQLConnection() ;
		autoCloseOpenFile();
		returnTempCacheToStack();
	}
	
	private static int ms_LastJobBatchID = 0 ;
	public static String getNextJobBatchID()
	{
		int n = ms_LastJobBatchID ++ ;
		return "" + (n/1000)%10 + (n/100)%10 + (n/10)%10 + (n)%10 ;
	}
				
	private AtomicBoolean m_abStopProcessing = new AtomicBoolean(false);
	private long m_lSessionRequestEndBefore_ms = 0;
	private CurrentDateInfo m_dateStart = new CurrentDateInfo();
	private CurrentDateInfo m_dateEnd  = new CurrentDateInfo();
	private EnvironmentStatus m_envStatus = EnvironmentStatus.UNKNOWN;
	
}
