/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.basePrgEnv;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import jlib.classLoader.CodeManager;
import jlib.jmxMBean.JMXDumperGui;
import jlib.misc.AsciiEbcdicConverter;
import jlib.misc.CodeConverter;
import jlib.misc.FileSystem;
import jlib.misc.NumberParser;
import jlib.misc.StringUtil;
import jlib.misc.ThreadSafeCounter;
import jlib.misc.Time_ms;
import jlib.sql.ArrayDbConnectionPool;
import jlib.sql.DbConnectionBase;
import jlib.sql.DbConnectionPool;
import jlib.sql.ThreadStatementGC;
import jlib.xml.Tag;
import nacaLib.accounting.AccountingRessourceDesc;
import nacaLib.appOpening.CalendarOpenState;
import nacaLib.appOpening.JmxAppCloser;
import nacaLib.appOpening.JmxAppOpener;
import nacaLib.appOpening.OpenCalendarManager;
import nacaLib.base.CJMapObject;
import nacaLib.base.JmxGeneralStat;
import nacaLib.classLoad.CustomClassDynLoaderFactory;
import nacaLib.fileConverter.CopyConverterClassLoader;
import nacaLib.misc.SemanticContextDef;
import nacaLib.sqlSupport.SQLCode;
import nacaLib.varEx.Pic9Comp3BufferSupport;


public abstract class BaseResourceManager extends CJMapObject
{
	private static BaseResourceManager ms_Instance = null;
	private static ThreadSafeCounter ms_sessionRequestIdCounter = new ThreadSafeCounter();
	private static JmxGeneralStat ms_baseJmxGeneralStat = null;
	private static ArrayDbConnectionPool ms_arrayDbConnectionPool = null;
			
	static public void unloadProgram(String csProgramName)
	{
		if(ms_Instance != null)
			ms_Instance.m_Sequencer.unloadProgram(csProgramName);
	}
	
	public int getUniqueSessionRequestId()
	{
		return ms_sessionRequestIdCounter.inc();
	}
	
	protected BaseResourceManager(boolean bUseJmx)
	{		
		if(ms_Instance == null)
		{
			ms_Instance = this;
			
			ms_arrayDbConnectionPool = new ArrayDbConnectionPool();
			
			if(bUseJmx)
				ms_baseJmxGeneralStat = new JmxGeneralStat();
			
			AsciiEbcdicConverter.create();
			Pic9Comp3BufferSupport.init();
			SQLCode.init();
			
			ms_hashMaxExecutionTimeByTrans = new Hashtable<String, Long>();
		}
	}
	
	static public boolean getUsingJmx()
	{
		if(ms_baseJmxGeneralStat == null)
			return false;
		return true;
	}
	
	public Tag setXMLConfigFilePath(String csINIFilePath)
	{
		m_csIniFilePath = csINIFilePath ;
		Tag tagRoot = Tag.createFromFile(m_csIniFilePath);
		
		if(tagRoot != null)
		{
			m_bSimulateRealEnvironment = tagRoot.getValAsBoolean("SimulateRealEnvironment", false) ;
			
			ms_bUseProgramPool = tagRoot.getValAsBoolean("UseProgramPool") ;
			ms_bUseStatementCache = tagRoot.getValAsBoolean("UseSQLStatementCache") ;
			ms_bUseSQLObjectCache = tagRoot.getValAsBoolean("UseSQLObjectCache") ;
						
			if(ms_bUseSQLObjectCache)
				ms_bUseVarFillCache = tagRoot.getValAsBoolean("UseVarFillCache") ;
			else
				ms_bUseVarFillCache = false;
			
			ms_bAsynchronousPreloadPrograms = tagRoot.getValAsBoolean("AsynchronousPreloadPrograms") ;
			
			ms_bGCAfterPreloadPrograms = tagRoot.getValAsBoolean("GCAfterPreloadPrograms", false);
			ms_bLoadCopyByPrimordialLoader = tagRoot.getValAsBoolean("LoadCopyByPrimordialLoader", true);

			m_csApplicationClassPath = tagRoot.getVal("ApplicationClassPath") ;
			m_csApplicationClassPath = FileSystem.normalizePath(m_csApplicationClassPath);
			m_csJarFile = tagRoot.getVal("JarFile") ;
			m_bCanLoadJar = tagRoot.getValAsBoolean("CanLoadJar") ;
			m_bCanLoadClass = tagRoot.getValAsBoolean("CanLoadClass") ;
			
			//ms_bMustWriteFileHeader = tagRoot.getValAsBoolean("MustWriteFileHeader") ;
			
			m_csSequencerFactoryClass = tagRoot.getVal("SequencerFactoryClass") ;
			m_tagSequencerConfig = tagRoot.getChild("SequencerConfig") ;
			ms_lMaxSessionExecTime_ms = tagRoot.getValAsLong("MaxSessionExecTime_ms");
			
			m_csDynamicAllocationPath = tagRoot.getVal("DynamicAllocationPath");
			ms_csTempDir = tagRoot.getVal("TempDir");			
			m_csTomcatStartCommand = tagRoot.getVal("TomcatStartCommand");			
			m_csCmpGetTextGifUrl = tagRoot.getVal("CmpGetTextGifUrl");
			m_csCmpDefaultTextGif = tagRoot.getVal("CmpDefaultTextGif");
			
			boolean b = tagRoot.isValExisting("NbThreadsSort");
			if(b)
				ms_nNbThreadsSort = tagRoot.getValAsInt("NbThreadsSort");
			else
				ms_nNbThreadsSort = 1;
			
			b = tagRoot.isValExisting("NbMaxRequestAsyncSortPending");
			if(b)
				ms_nNbMaxRequestAsyncSortPending = tagRoot.getValAsInt("NbMaxRequestAsyncSortPending");
			else
				ms_nNbMaxRequestAsyncSortPending = 100000;	// Default value
			
			ms_nFileLineReaderBufferSize = tagRoot.getValAsInt("FileLineReaderBufferSize", 65536);
			
			ms_nSQLInsertStatementBatchSize = tagRoot.getValAsInt("SQLInsertStatementBatchSize", 100);
			ms_nSQLInsertStatementBatchCommitSize = tagRoot.getValAsInt("SQLInsertStatementBatchCommitSize", 100);
			
			String csCalendar = tagRoot.getVal("StandardCalendar");
			if(csCalendar != null)
				createCalendar(OpenCalendarManager.Standard, csCalendar);
			
			csCalendar = tagRoot.getVal("CustomCalendar");
			if(csCalendar != null)
				createCalendar(OpenCalendarManager.Custom, csCalendar);
			
			if(!ms_bForcedComparisonInEbcdic)
			{
				String csComparisonMode = tagRoot.getVal("ComparisonMode") ;
				if(csComparisonMode.equalsIgnoreCase("EBCDIC"))
					m_bComparisonInEbcdic = true;
				else
					m_bComparisonInEbcdic = false;
			}
			
			ms_csTempDir = FileSystem.normalizePath(ms_csTempDir);
			FileSystem.createPath(ms_csTempDir);
			
			String csCode = tagRoot.getVal("CodeJavaToDb");
			if(!StringUtil.isEmpty(csCode))
			{
				ms_CodeJavaToDb = new CodeConverter(csCode);
				ms_bUpdateCodeJavaToDb = true;
			}
			
			csCode = tagRoot.getVal("CodeDbToJava");
			if(!StringUtil.isEmpty(csCode))
			{
				ms_CodeDbToJava = new CodeConverter(csCode);
				ms_bUpdateCodeDbToJava = true;
			}
			
			Tag tagGCThread = tagRoot.getChild("GCThread");
			if(tagGCThread != null)
			{
				ms_threadStatementGC = new ThreadStatementGC(tagGCThread, ms_arrayDbConnectionPool);
			}
			 			
			Tag tagAccounting = tagRoot.getChild("Accounting");
			if(tagAccounting != null)
			{
				m_accountingRessourceDesc = new AccountingRessourceDesc();
				m_accountingRessourceDesc.load(tagAccounting);
			}
			
			Tag tagDebugLoadTest = tagRoot.getChild("DebugLoadTest");
			if(tagDebugLoadTest != null)
			{
				ms_bLogAllSQLException = tagDebugLoadTest.getValAsBoolean("LogAllSQLException");
				//ms_bUseSQLMBean = tagDebugLoadTest.getValAsBoolean("UseSQLMBean") ;
			}
		}
		
		LoadConfigFromFile(tagRoot);
		
		if(ms_threadStatementGC != null)
			ms_threadStatementGC.start();
		
		setAppManuallyClosed(false);
		return tagRoot;
	}
	
	public synchronized static void removeAllDBConnections()
	{
		ms_arrayDbConnectionPool.forceRemoveAllDBConnections();
	}
	
	static public void setCurrentMaxPermanentHeap_Mo(int currentMaxPermanentHeap_Mo)
	{
		if(ms_threadStatementGC != null)
			ms_threadStatementGC.setCurrentMaxPermanentHeap_Mo(currentMaxPermanentHeap_Mo);		
	}
	
	static public int getCurrentMaxPermanentHeap_Mo()
	{
		if(ms_threadStatementGC != null)
			return ms_threadStatementGC.getCurrentMaxPermanentHeap_Mo();
		return 0;
	}
	
	static public void initCopyConverterClassLoader()
	{
		ms_Instance.doInitCopyConverterClassLoader();
	}
	
	private void doInitCopyConverterClassLoader()
	{
		CopyConverterClassLoader.init(m_csApplicationClassPath, m_bCanLoadClass, m_bCanLoadJar);
	}
	
	private AccountingRessourceDesc m_accountingRessourceDesc = null;
	private static ThreadStatementGC ms_threadStatementGC = null;
	
	public static void addDbConnectionPool(DbConnectionPool dbConnectionPool)
	{
		ms_arrayDbConnectionPool.addDbConnectionPool(dbConnectionPool);
	}
	
	public AccountingRessourceDesc getAccountingRessourceDesc()
	{
		return m_accountingRessourceDesc;
	}

	protected void baseInitSequenceur(String csDBParameterPrefix)
	{
		//boolean bSpServerMode = false;
		
		if (m_csApplicationClassPath != null && !m_csApplicationClassPath.equals(""))
		{
			CodeManager.setPath(m_csApplicationClassPath);
		}
		
		CodeManager.initLoadPossibilities(m_bCanLoadClass, m_bCanLoadJar);
		
		CodeManager.preloadJar(CustomClassDynLoaderFactory.getInstance(), m_csJarFile);
		
		// load program sequencer		
		m_seqFactory = loadSequencerFactory(m_csSequencerFactoryClass);		
		if (m_seqFactory == null)
		{
			throw new RuntimeException() ;
		}
		
		m_seqFactory.init(csDBParameterPrefix, m_tagSequencerConfig);	//, m_ClassLoader);		
		if(m_seqFactory != null)
			m_Sequencer = m_seqFactory.NewSequencer() ;
	}
	
	
	private CBaseProgramLoaderFactory loadSequencerFactory(String csClassName)
	{
		ClassLoader classLoader = getClass().getClassLoader();
		try	// Check with java runtime primordial class loader 
        {
			Class clsLoaded = classLoader.loadClass(csClassName);
 
			if(clsLoaded != null)
			{
				try
				{
					Object obj = clsLoaded.newInstance();
					if(obj != null)
						return (CBaseProgramLoaderFactory)obj;
				} 
				catch (InstantiationException e)
				{
					int n = 0;
				}
				catch (IllegalAccessException e)
				{
					int n = 0;
				}
				catch (NoClassDefFoundError e)
				{
					int n = 0;
				}
			}
        } 
        catch (ClassNotFoundException e) 
        {
            int n = 0;
        }
        return null;
	}
	
	public static long getSessionRequestEndTimeLimit(String csTransactionId)
	{
		Long LMaxSessionExecTime_ms = ms_hashMaxExecutionTimeByTrans.get(csTransactionId);
		if(LMaxSessionExecTime_ms == null)
		{
			long l = Time_ms.getCurrentTime_ms() + ms_lMaxSessionExecTime_ms;
			return l;
		}
		long l = Time_ms.getCurrentTime_ms() + LMaxSessionExecTime_ms.longValue();
		return l;
	}
	
	public static void dumpStat()
	{
		ms_JMXDumperGui.setDump();
	}
	
	public static void setDumpStatOutput(String csPathFileStat)
	{
		ms_JMXDumperGui.setOutputFile(csPathFileStat);
	}
	
	protected abstract void LoadConfigFromFile(Tag tagRoot);
	protected abstract void initSequenceur(String csDBParameterPrefix);

	private static boolean ms_bUseProgramPool = false;
	private static boolean ms_bUseStatementCache = false;
	private static String ms_csTempDir ="./";
	private static int ms_nNbThreadsSort = 1;
	private static int ms_nNbMaxRequestAsyncSortPending = 100000;
	private static int ms_nFileLineReaderBufferSize = 0;
	private static int ms_nSQLInsertStatementBatchSize = 100;
	private static int ms_nSQLInsertStatementBatchCommitSize = 100;
	protected static boolean ms_bUseSQLObjectCache = false;
	public static boolean ms_bUseVarFillCache = false;
	//protected static boolean ms_bMustWriteFileHeader = false;
	protected String m_csApplicationClassPath = "" ;
	protected String m_csJarFile = "";
	protected boolean m_bCanLoadJar = true;
	protected boolean m_bCanLoadClass = true;
	protected String m_csSequencerFactoryClass = ""; //"CESMProgramManagerFactory" ;
	protected Tag m_tagSequencerConfig = null ;
	protected CBaseProgramLoaderFactory m_seqFactory = null ;
	protected ProgramSequencer m_Sequencer = null ;
	private static long ms_lMaxSessionExecTime_ms = 0;
	private static JMXDumperGui ms_JMXDumperGui = new JMXDumperGui("./JMXOutput.xml");
	protected String m_csIniFilePath = "" ;
	private static boolean m_bComparisonInEbcdic = false;
	private static boolean ms_bForcedComparisonInEbcdic = false;
	
	private static String m_csDynamicAllocationPath = "";	
	private static String m_csTomcatStartCommand = "";
	private static String m_csCmpGetTextGifUrl = "";
	private static String m_csCmpDefaultTextGif = "";
	
	//private static boolean ms_bUseSQLMBean = false;
	public static boolean ms_bLogAllSQLException = false;
	private static CodeConverter ms_CodeJavaToDb = null;
	public static CodeConverter ms_CodeDbToJava = null;
	private static boolean ms_bUpdateCodeDbToJava = false;
	private static boolean ms_bUpdateCodeJavaToDb = false;
	
//	public static boolean getUseSQLMBean()
//	{
//		return ms_bUseSQLMBean;
//	}	
	public static boolean getUseProgramPool()
	{
		return ms_bUseProgramPool;
	}
	public static boolean getUseStatementCache()
	{
		return ms_bUseStatementCache;
	}
	public static boolean getUseSQLObjectCache()
	{
		return ms_bUseSQLObjectCache;
	}
	
	public static void DEBUGdisableSQLCache()
	{
		ms_bUseSQLObjectCache = false;
		ms_bUseStatementCache = false;		
	}
	
	public static String getDynamicAllocationPath()
	{
		return m_csDynamicAllocationPath;
	}
	
	public static String getTomcatStartCommand()
	{
		return m_csTomcatStartCommand;
	}
	
	public static String getTempDir()
	{
		return ms_csTempDir;
	}
	
	public static String getCmpGetTextGifUrl()
	{
		return m_csCmpGetTextGifUrl;
	}
	
	public static String getCmpDefaultTextGif()
	{
		return m_csCmpDefaultTextGif;
	}
	
	public static int getNbThreadsSort()
	{
		return ms_nNbThreadsSort;		
	}
	
	public static int getNbMaxRequestAsyncSortPending()
	{
		return ms_nNbMaxRequestAsyncSortPending;		
	}
	
	public static int getFileLineReaderBufferSize()
	{
		return ms_nFileLineReaderBufferSize;
	}
	
	public static int getSQLInsertStatementBatchSize()
	{
		return ms_nSQLInsertStatementBatchSize;
	}
	
	public static int getSQLInsertStatementBatchCommitSize()
	{
		return ms_nSQLInsertStatementBatchCommitSize;
	}
	
	public static void removeResourceCache(String csForm)
	{
		ms_Instance.doRemoveResourceCache(csForm);
	}
	
	public static String updateCodeJavaToDb(String csValue)
	{
		return ms_CodeJavaToDb.convert(csValue);
	}
	public static String updateCodeDbToJava(String csValue)
	{
		return ms_CodeDbToJava.convert(csValue);
	}
	
	public static boolean isUpdateCodeDbToJava()
	{
		return ms_bUpdateCodeDbToJava;
	}

	public static boolean isUpdateCodeJavaToDb()
	{
		return ms_bUpdateCodeJavaToDb;
	}
	
	public abstract void doRemoveResourceCache(String csForm);
	
	protected void loadDBSemanticContextDef()
	{
		if (m_seqFactory!=null)
		{
			DbConnectionBase sqlConnection = m_seqFactory.getConnection("", false);
			
			// Load db semantic defintion with the specified connection
			
			// Hard coded version: To be changed to a DB select
			defineDBSemanticContext("PR0103", "RECOLL", "recoll");	// associates the semantic context recoll to the col RECOLL of table PR0103
			//sqlConnection.Release() ;
		}
	} 

	public static SemanticContextDef getSemanticContextDef()
	{
		return ms_Instance.m_semanticContextDef;
	}
	
	private void defineDBSemanticContext(String csTable, String csCol, String csSemanticContext)
	{
		m_semanticContextDef.setSemanticContextValueDefinition(csTable, csCol, csSemanticContext);
	}
	
	public String getDBSemanticContext(String csTable, String csCol)
	{
		return m_semanticContextDef.getSemanticContextValueDefinition(csTable, csCol);
	}
	
	public static final StringBuffer getEmptyStringBuffer()
	{
		return ms_sbEmptyStringBuffer;
	}
	
	private static final StringBuffer ms_sbEmptyStringBuffer = new StringBuffer();
	
	private SemanticContextDef m_semanticContextDef = new SemanticContextDef();
	
	public static boolean isInUpdateMode()
	{
		return ms_bUpdateMode;
	}
	
	public static void setUpdateMode(boolean bUpdateMode)
	{
		ms_bUpdateMode = bUpdateMode;
	}
	
	private static boolean ms_bUpdateMode = false;
	
	public static Date getUpdateTime()
	{
		return ms_updateTime;
	}
	public static String getUpdateTimeFormated()
	{
		if (ms_updateTime == null) return "";
		SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss");
		String cs = formater.format(ms_updateTime);
		return cs;
	}
	public static String getUpdateTimeAutoRefresh()
	{
		if (ms_updateTime == null || !isInUpdateMode()) return "";
		long lAutoRefresh = (ms_updateTime.getTime() - new Date().getTime()) / 1000;
		return new Long(lAutoRefresh).toString();
	}

	public static void setUpdateTime(Date updateTime)
	{
		ms_updateTime = updateTime;
	}
	
	private static Date ms_updateTime = null;
	
	public static void setAppManuallyClosed(boolean bClosed)
	{
		ms_bAppManuallyClosed = bClosed;
		if(ms_calendarManager != null)			
			ms_calendarManager.flushCalendarCache();
		
		if(bClosed)
		{
			if(ms_JmxAppOpener == null)
				ms_JmxAppOpener = new JmxAppOpener();
			if(ms_JmxAppCloser != null)
				ms_JmxAppCloser.unregisterMBean();
			ms_JmxAppCloser = null; 
		}
		else
		{
			ms_csManualCloseReason = "";
			if(ms_JmxAppCloser == null)
				ms_JmxAppCloser = new JmxAppCloser();
			if(ms_JmxAppOpener != null)
				ms_JmxAppOpener.unregisterMBean();
			ms_JmxAppOpener = null;
		}
	}
	
	public static CalendarOpenState getAppManualStatusState()
	{
		if(ms_bAppManuallyClosed)
			return CalendarOpenState.AppManuallyClosed;
		return CalendarOpenState.AppOpened;		
	}
	
	public static void reloadCalendarFiles()
	{
		if(ms_calendarManager != null)
			ms_calendarManager.setReloadCalendarFiles();
	}
	
	private static void createCalendar(int nCalendardId, String csCalendarFilePath)
	{
		if(ms_calendarManager == null)
			ms_calendarManager = new OpenCalendarManager();
		ms_calendarManager.addCalendarDefinition(nCalendardId, csCalendarFilePath);
	}
		
	public static CalendarOpenState getAppOpenState()
	{
		if(ms_bAppManuallyClosed)
			return CalendarOpenState.AppManuallyClosed;
		CalendarOpenState state = getAppPlanifiedOpenState();
		return state;
	}
	
	public static CalendarOpenState getAppPlanifiedOpenState()
	{
		if(ms_calendarManager != null)
			return ms_calendarManager.getServiceOpenState();
		return CalendarOpenState.AppOpened;
	}
	
	public static CalendarOpenState getAppCustomOpenState()
	{
		if(ms_calendarManager != null)
			return ms_calendarManager.getAppCustomOpenState();
		return CalendarOpenState.AppOpened;
	}
	
	public static CalendarOpenState getAppStandardOpenState()
	{
		if(ms_calendarManager != null)
			return ms_calendarManager.getAppStandardOpenState();
		return CalendarOpenState.AppOpened;
	}
		
	public static boolean isAppManuallyClosed()
	{
		return ms_bAppManuallyClosed;
	}
	
	public static String getManualCloseReason()
	{
		return ms_csManualCloseReason;
	}
	
	public static void setManualCloseReason(String csManualCloseReason)
	{
		ms_csManualCloseReason = csManualCloseReason;
	}
	
	public static String getCurrentOpenCalendarRangeString()
	{
		if(ms_calendarManager != null)
			return ms_calendarManager.getCurrentOpenCalendarRangeString();
		return "Undefined calendar";
	}
	
	public static void flushCalendarCache()
	{
		if(ms_calendarManager != null)
			ms_calendarManager.flushCalendarCache();
	}
	
	public static boolean getComparisonInEbcdic()
	{
		return m_bComparisonInEbcdic;
	}
	
	public static void setForcedComparisonInEbcdic(boolean bComparisonInEbcdic)
	{
		ms_bForcedComparisonInEbcdic = true;
		m_bComparisonInEbcdic = bComparisonInEbcdic;
	}
	
//	public static boolean getMustWriteFileHeader()
//	{
//		return ms_bMustWriteFileHeader;
//	}
	
	public static boolean isAsynchronousPreloadPrograms()
	{
		return ms_bAsynchronousPreloadPrograms;
	}
	
	public static boolean isGCAfterPreloadPrograms()
	{
		return ms_bGCAfterPreloadPrograms;
	}
		
	public static boolean isLoadCopyByPrimordialLoader()
	{
		return ms_bLoadCopyByPrimordialLoader;
	}
	
	public static void registerTransactionMaxExecTime(String csTransactionId, String csMaxExecutionTime_ms)
	{
		if(StringUtil.isEmpty(csMaxExecutionTime_ms))
			ms_hashMaxExecutionTimeByTrans.put(csTransactionId, ms_lMaxSessionExecTime_ms);
		else
		{
			long lMaxExecutionTime_ms = NumberParser.getAsLong(csMaxExecutionTime_ms);
			ms_hashMaxExecutionTimeByTrans.put(csTransactionId, lMaxExecutionTime_ms);
		}		
	}
	
	public boolean getSimulateRealEnvironment()
	{
		return m_bSimulateRealEnvironment;
	}
	
	private boolean m_bSimulateRealEnvironment = true;
	private static OpenCalendarManager ms_calendarManager = null;

	private static boolean ms_bAppManuallyClosed = false;
	private static String ms_csManualCloseReason = "";
	private static JmxAppOpener ms_JmxAppOpener = null;
	private static JmxAppCloser ms_JmxAppCloser = null;
	private static boolean ms_bAsynchronousPreloadPrograms = false;
	private static boolean ms_bGCAfterPreloadPrograms = false;
	private static boolean ms_bLoadCopyByPrimordialLoader = true;
	private static Hashtable<String, Long> ms_hashMaxExecutionTimeByTrans = null;	// Maximum execution time in ms for a transaction 
}
