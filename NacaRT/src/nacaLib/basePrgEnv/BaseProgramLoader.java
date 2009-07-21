/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.basePrgEnv;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.locks.ReentrantLock;

import jlib.classLoader.ClassDynLoaderFactory;
import jlib.classLoader.CodeManager;
import jlib.log.AssertException;
import jlib.log.Log;
import jlib.misc.DateUtil;
import jlib.misc.Mail;
import jlib.misc.MailService;
import jlib.misc.StringArray;
import jlib.sql.DbConnectionManagerBase;
import jlib.xml.Tag;
import nacaLib.CESM.CESMStartData;
import nacaLib.accounting.CriteriaEndRunMain;
import nacaLib.asyncTasks.CAsynchronousTask;
import nacaLib.calledPrgSupport.BaseCalledPrgPublicArgPositioned;
import nacaLib.exceptions.AbortSessionException;
import nacaLib.exceptions.CESMAbendException;
import nacaLib.exceptions.CESMReturnException;
import nacaLib.exceptions.CExitException;
import nacaLib.exceptions.CStopRunException;
import nacaLib.exceptions.CXctlException;
import nacaLib.exceptions.CannotOpenFileException;
import nacaLib.exceptions.DumpProgramException;
import nacaLib.exceptions.FileDescriptorNofFoundException;
import nacaLib.exceptions.InputFileNotFoundException;
import nacaLib.exceptions.TooManyCloseFileException;
import nacaLib.programPool.ProgramInstancesPool;
import nacaLib.programPool.ProgramPoolManager;
import nacaLib.programPool.SharedProgramInstanceData;
import nacaLib.programPool.SharedProgramInstanceDataCatalog;
import nacaLib.sqlSupport.CSQLStatus;
import nacaLib.tempCache.TempCache;
import nacaLib.tempCache.TempCacheLocator;
import nacaLib.varEx.CCallParam;

import org.w3c.dom.Document;

public abstract class BaseProgramLoader extends ProgramSequencer	//ProgramSequencerExt
{
	private DbConnectionManagerBase m_connectionManager = null;
	private static ReentrantLock ms_lock = new ReentrantLock(); 
	protected static BaseProgramLoader ms_Instance = null ;
	
	protected MailService m_MailService = null ;
	protected String m_csAlertMailSubjectTitle = "" ;
	
	private Hashtable<BaseSession, BaseEnvironment> m_tabSyncSessions = null; 
	protected static StringArray ms_arrPath = null;
	
	protected Tag m_tagCESMConfig = null ;

	protected static Hashtable<String, String> ms_tabTransID = null;
	protected static Hashtable<String, String> ms_tabPrograms = null;
	private ProgramPoolManager m_programPoolManager = null;


	public static BaseProgramLoader GetInstance()
	{
		return ms_Instance ;
	}
	
	public static BaseProgramLoader GetProgramLoaderInstance() 
	{
		return ms_Instance ;
	}
	
	public BaseProgramLoader(DbConnectionManagerBase connectionManager, Tag tagSequencerConfig, boolean bUseJmx)
	{	
		super();
		
		m_tabSyncSessions = new Hashtable<BaseSession, BaseEnvironment>() ;
		ms_arrPath = new StringArray();
		ms_tabTransID = new Hashtable<String, String>();
		ms_tabPrograms = new Hashtable<String, String>() ;
		
		//m_programPoolManager = new ProgramPoolManager(bUseJmx);
		
		if(tagSequencerConfig != null)
		{
			m_tagCESMConfig = tagSequencerConfig.getChild("CESMConfig");
		}	
		
		m_connectionManager = connectionManager;
		
		ms_Instance = this ;				  
		
		ms_arrPath = new StringArray();
		m_programPoolManager = new ProgramPoolManager(bUseJmx);
	}
	
	public void setPaths(StringArray arrPath)
	{
		ms_arrPath = arrPath;
	}
	
	private void returnProgramInstanceToPool(BaseProgram program)
	{
		m_programPoolManager.returnProgramInstanceToPool(program);
	}
	
	private void doNotReturnProgramInstanceToPool(BaseProgram program)
	{
		int n = 0;
		//TempCache tempCache = TempCacheLocator.getTLSTempCache();
		//tempCache.resetStackProgram();
		
		//m_programPoolManager.returnProgramInstanceToPool(currentProgramInstance);
	}
	
	public SharedProgramInstanceData forcePreloadSessionProgram(String csDefaultProgramName, int nNbInstanceToPreload)
	{
		int nNbProgLoaded = 0;
		BaseProgram currentProgram = loadPooledProgramInstance(csDefaultProgramName);
		if(currentProgram != null)
		{
			//BaseProgram currentProgram = programInstance.getProgram();
			
			BaseProgramManager baseProgramManager = currentProgram.getProgramManager();
			
			baseProgramManager.prepareCall(this, currentProgram, null, null, baseProgramManager.isNewProgramInstance()) ;
	
			SharedProgramInstanceData s = currentProgram.getProgramManager().getSharedProgramInstanceData();
			SharedProgramInstanceDataCatalog.putSharedProgramInstanceData(csDefaultProgramName, s);
			
			ProgramInstancesPool programInstancesPool = m_programPoolManager.getProgramPool(csDefaultProgramName);
			if(programInstancesPool != null)
				programInstancesPool.returnProgram(currentProgram);
			
			TempCache tempCache = TempCacheLocator.getTLSTempCache();
			tempCache.resetStackProgram();
			
			int nNbInstancesPreloaded = 1;
			// Create other program instances
			for(int n=1; n<nNbInstanceToPreload; n++)
			{
				Log.logNormal("Program=" + csDefaultProgramName + " nb instances preloaded=" + nNbInstancesPreloaded);
				BaseProgram currentProgram2ndInstance = m_programPoolManager.preloadSecondInstanceProgram(csDefaultProgramName);
				if(currentProgram2ndInstance != null && programInstancesPool != null)
				{
					boolean bNewProgramInstance = currentProgram2ndInstance.getProgramManager().isNewProgramInstance();
					currentProgram2ndInstance.getProgramManager().prepareCall(this, currentProgram, null, null, bNewProgramInstance);
					programInstancesPool.returnProgram(currentProgram2ndInstance);
					
					tempCache.resetStackProgram();
					nNbInstancesPreloaded++;
				}
			}
			
			Log.logNormal("Program=" + csDefaultProgramName + " Total instances preloaded="+nNbInstancesPreloaded);
			
			nNbProgLoaded++;
			Log.logDebug("nNbProgLoaded="+nNbProgLoaded);
			return s;
		}
		else	// Destroy program instance pool
		{
			ProgramInstancesPool programInstancesPool = m_programPoolManager.getProgramPool(csDefaultProgramName);
			if(programInstancesPool != null)
				programInstancesPool.unregisterMBean();
			m_programPoolManager.removeProgramInstancesPool(csDefaultProgramName);
		}
		return null;
	}

	private BaseProgram LoadProgramInstance(String csProgram, boolean bUseProgramPool)
	{		
		/*String csProgramName = ResolveTransID(ProgId) ;
		if (csProgramName == null || csProgramName.equals(""))
		{
			csProgramName = ProgId ;
		}*/
		csProgram = Character.toUpperCase(csProgram.charAt(0)) + csProgram.substring(1).toLowerCase();
		BaseProgram program = null;
		if(bUseProgramPool)
			program = loadPooledProgramInstance(csProgram);
		else
			program = loadUnpooledProgramInstance(csProgram);
		return program;
	}	
	
//	private BaseProgram loadProgramInstance(String csProgramName)
//	{
//		BaseProgram program = loadUnpooledProgramInstance(csProgramName);
//		if(program != null)
//		{
//			//program.getProgramManager().declareInstance(csProgramName);
//			//ProgramInstance programInstance = new ProgramInstance(csProgramName, program, true);
//			return program;
//		}
//		return null;		
//	}	
	
	public void unloadProgram(String csProgramName)
	{
		ProgramInstancesPool programInstancesPool = m_programPoolManager.getProgramPool(csProgramName);
		if(programInstancesPool != null)
			programInstancesPool.unloadProgram();		
	}

	private BaseProgram loadPooledProgramInstance(String csProgramName)
	{
		return m_programPoolManager.loadPooledProgramInstance(csProgramName);
	}
	
	private BaseProgram loadUnpooledProgramInstance(String csProgName)
	{
		Object obj = CodeManager.getInstance(csProgName, ClassDynLoaderFactory.getInstance());
		if(obj != null)
		{
			return (BaseProgram)obj ;
		}
		else
		{
			Log.logImportant("Cannot load class or create object of class: "+csProgName);
		}
		return null;
	}

	/**
	 * @param ProgId
	 * @return
	 */
	public static String ResolveTransID(String ProgId)
	{
		return ms_tabTransID.get(ProgId);
	}
	
	
	/**
	 * @param transIDMappingFilePath
	 */
	protected void LoadTransIDMapping(String transIDMappingFilePath)
	{
		Tag tagRoot = Tag.createFromFile(transIDMappingFilePath);
		if(tagRoot != null)
		{
			Tag tagTransid = tagRoot.getEnumChild("transid");
			while(tagTransid != null)
			{
				String csTransactionId = tagTransid.getVal("id") ;
				String csProg = tagTransid.getVal("program") ;
				String csMaxExecutionTime_ms = tagTransid.getVal("MaxExecutionTime_ms") ;
				ms_tabTransID.put(csTransactionId, csProg);
				ms_tabPrograms.put(csProg, csTransactionId);
				BaseResourceManager.registerTransactionMaxExecTime(csTransactionId, csMaxExecutionTime_ms);
				
				tagTransid = tagRoot.getEnumChild();
			}
		}
	}
	
	public BaseEnvironment GetEnvironment(BaseSession appSession, String defaultProgramName, String csProgramParent) 
	{
		BaseEnvironment env = m_tabSyncSessions.get(appSession);
		if (env == null)
		{			
			env = appSession.createEnvironment(m_connectionManager);
			if(env != null)
			{
				env.Init(m_tagCESMConfig) ;
				
				if(!appSession.isAsync())
					m_tabSyncSessions.put(appSession, env) ;
				if (defaultProgramName != null)
				{
					env.setNextProgramToLoad(defaultProgramName, csProgramParent) ;
				}
			}
		}
		
		if(env != null)
		{
			String prg = env.getNextProgramToLoad() ;
			String tid = ms_tabPrograms.get(prg) ;
			if (tid == null)
			{
				tid = prg ;
			}
			env.setCurrentTransaction(tid, prg) ;
		}
		
		return env ;
	}
	
	public void runTopProgram(BaseEnvironment env, ArrayList<BaseCalledPrgPublicArgPositioned> arrCallerCallParam) throws AbortSessionException
	{
		try
		{
			runProgram(env, arrCallerCallParam) ;
			Exception e = env.commitSQL();
			if (e != null)
			{
				Log.logImportant("Commit Exception occured");
				String csProgramName = handleExceptionCatched(e, env);
				throwAbortSession(e, csProgramName);
			}
			Log.logVerbose("Transaction finished; Commit");
		}
		catch (AbortSessionException e)
		{
			Log.logImportant("AbortSessionException occured");
			String csProgramName = handleExceptionCatched(e, env);
			throwAbortSession(e, csProgramName);
		}
		catch (CESMAbendException e)
		{
			Log.logImportant("CESMAbendException occured");
			String csProgramName = handleExceptionCatched(e, env);
			throwAbortSession(e, csProgramName);
		}
		catch (AssertException e)
		{
			Log.logImportant("CESMAbendException occured");
			String csProgramName = handleExceptionCatched(e, env);
			throwAbortSession(e, csProgramName);
		}
		catch (DumpProgramException e)
		{
			Log.logImportant("DumpProgramException occured");
			String csProgramName = handleExceptionCatched(e, env);
			throwAbortSession(e, csProgramName);
		}
		catch (InputFileNotFoundException e)
		{
			Log.logImportant("InputFileNotFoundException occured");
			String csProgramName = handleExceptionCatched(e, env);
			throwAbortSession(e, csProgramName);
		}
		catch (FileDescriptorNofFoundException e)
		{
			Log.logImportant("FileDescriptorNofFoundException occured");
			String csProgramName = handleExceptionCatched(e, env);
			throwAbortSession(e, csProgramName);
		}
		catch (CannotOpenFileException e)
		{
			Log.logImportant("CannotOpenFileException occured");
			String csProgramName = handleExceptionCatched(e, env);
			throwAbortSession(e, csProgramName);
		}
		catch (TooManyCloseFileException e)
		{
			Log.logImportant("TooManyCloseFileException occured");
			String csProgramName = handleExceptionCatched(e, env);
			throwAbortSession(e, csProgramName);
		}
		catch (Exception e)
		{
			Log.logImportant("Exception occured");
			String csProgramName = handleExceptionCatched(e, env);
			throwAbortSession(e, csProgramName);
		}
		env.releaseSQLConnection() ;
		env.autoCloseOpenFile();
		env.returnTempCacheToStack();
	}
	
	
	private void throwAbortSession(Throwable e, String csProgramName)
	{
		AbortSessionException exp = new AbortSessionException(e);
		exp.m_ProgramName = csProgramName;
		exp.m_Reason = e;
		throw exp;
	}
	
	
	private String handleExceptionCatched(Exception e, BaseEnvironment env)
	{
		TempCache tempCache = TempCacheLocator.getTLSTempCache();
		BaseProgram prgLast = tempCache.popCurrentProgram();
		String csClassName = "";
		CSQLStatus sqlStatus = null;
		if (prgLast != null)
		{
			csClassName = prgLast.getSimpleName();
			sqlStatus = prgLast.m_BaseProgramManager.getSQLStatus();
		}
		logMail(env, csClassName, "FATAL occured", e, sqlStatus);
		
		env.cleanupOnExceptionCatched();
		return csClassName;
	}
	
	private String handleExceptionCatched(AssertException e, BaseEnvironment env)
	{
		TempCache tempCache = TempCacheLocator.getTLSTempCache();
		BaseProgram prgLast = tempCache.popCurrentProgram();
		String csClassName = "";
		CSQLStatus sqlStatus = null;
		if (prgLast != null)
		{
			csClassName = prgLast.getSimpleName();
			sqlStatus = prgLast.m_BaseProgramManager.getSQLStatus();
		}
		logMail(env, csClassName, "FATAL occured", e, sqlStatus);

		env.cleanupOnExceptionCatched();
		return csClassName;
	}

	private void runProgram(BaseEnvironment env, ArrayList<BaseCalledPrgPublicArgPositioned> arrCallerCallParam) throws Exception
	{
		TempCacheLocator.getTLSTempCache().setCurrentEnv(env);
		boolean bContinue = true ;
		while (bContinue)
		{
			String csProgramID = env.getNextProgramToLoad() ;
			//ProgramInstance currentProgramInstance = null ;
			
			if(csProgramID.equals(""))	// No current program
			{
				bContinue = false ;
			}
			else
			{
				BaseProgram currentProgram = null;
				ms_lock.lock();
				Log.logNormal("Loading program instance:"+csProgramID);
				try
				{
					currentProgram = LoadProgramInstance(csProgramID, BaseResourceManager.getUseProgramPool()) ;
				}
				catch(Exception e)
				{
					Log.logImportant("Exception " + e.toString() + ": Cannot load program instance:"+csProgramID);
					env.endRunProgram(CriteriaEndRunMain.Abort);
					ms_lock.unlock();
					throw e;
				}
				if (currentProgram == null)
				{
					Log.logImportant("ERROR: Cannot load program instance:"+csProgramID);
					ms_lock.unlock();
					throw new AssertException("Cannot load program instance : " +csProgramID);
				}
				
				String csProgramName = currentProgram.getSimpleName();
				try
				{
					boolean bNewInstance = currentProgram.getProgramManager().isNewProgramInstance();
					currentProgram.getProgramManager().prepareCall(this, currentProgram, arrCallerCallParam, env, bNewInstance) ;
					ms_lock.unlock();
				}
				catch(Exception e)
				{
					Log.logImportant("ERROR: Cannot prepare program call :"+csProgramID);
					env.endRunProgram(CriteriaEndRunMain.Abort);
					ms_lock.unlock();
					throw e;
				}
				
				BaseProgramManager programManager = null;
				try
				{
					env.resetNewTransaction() ;
					if(isLogCESM || isLogFlow)
						Log.logVerbose("Run program: "+csProgramName);
					
					env.startRunProgram(csProgramName);
										
					programManager = currentProgram.getProgramManager();  
					programManager.stdPrepareRunMain(currentProgram);
					programManager.runMain() ;
					currentProgram.getProgramManager().mapCalledPrgReturnParameters(arrCallerCallParam);
										
					TempCacheLocator.getTLSTempCache().popCurrentProgram();
					env.endRunProgram(CriteriaEndRunMain.Normal);
				}
				catch (CESMReturnException e)
				{
					currentProgram.getProgramManager().mapCalledPrgReturnParameters(arrCallerCallParam);
					TempCacheLocator.getTLSTempCache().popCurrentProgram();
					env.endRunProgram(CriteriaEndRunMain.Return);
					if(isLogCESM || isLogFlow)
						Log.logVerbose("Program finished: "+csProgramName);
					bContinue = false ;
				}
				catch (CExitException e)
				{
					currentProgram.getProgramManager().mapCalledPrgReturnParameters(arrCallerCallParam);
					TempCacheLocator.getTLSTempCache().popCurrentProgram();
					env.endRunProgram(CriteriaEndRunMain.Exit);
					if(isLogCESM || isLogFlow)
						Log.logVerbose("Program exited: "+csProgramName);
					bContinue = false ;
				}
				catch (CStopRunException e)
				{
					currentProgram.getProgramManager().mapCalledPrgReturnParameters(arrCallerCallParam);
					TempCacheLocator.getTLSTempCache().popCurrentProgram();
					env.endRunProgram(CriteriaEndRunMain.StopRun);
					if(isLogCESM || isLogFlow)
						Log.logVerbose("Program exited: "+csProgramName);
					bContinue = false ;
				}
				catch (CXctlException e)
				{
					currentProgram.getProgramManager().mapCalledPrgReturnParameters(arrCallerCallParam);
					TempCacheLocator.getTLSTempCache().popCurrentProgram();
					env.endRunProgram(CriteriaEndRunMain.XCtl);
					env = e.m_Environment ;
					returnProgramInstanceToPool(currentProgram);	// Return program to pool
					continue ;
				}
				catch (AbortSessionException e)
				{
					env.endRunProgram(CriteriaEndRunMain.Abort);
					e.m_ProgramName = csProgramName;
					doNotReturnProgramInstanceToPool(currentProgram);	// Do not return program to pool anymore
					currentProgram = null;
					throw e;
				}
				catch (AssertException e)
				{
					env.endRunProgram(CriteriaEndRunMain.Abort);
					doNotReturnProgramInstanceToPool(currentProgram);	// Return program to pool
					currentProgram = null;
					throw e;
				}
				catch(CannotOpenFileException e)
				{
					env.endRunProgram(CriteriaEndRunMain.Abort);
					returnProgramInstanceToPool(currentProgram);	// Return program to pool
					throw e;
				}
				catch(InputFileNotFoundException e)
				{
					env.endRunProgram(CriteriaEndRunMain.Abort);
					returnProgramInstanceToPool(currentProgram);	// Return program to pool
					throw e;
				}
				catch(Exception e)
				{
					env.endRunProgram(CriteriaEndRunMain.Abort);
					doNotReturnProgramInstanceToPool(currentProgram);	// Do not return program to pool anymore
					currentProgram = null;
					throw e;
				}

				if (env.hasOutput())
				{
					env.RegisterOutput() ;
					if (env.getNextProgramToLoad().equals(""))
					{
						env.setNextProgramToLoad(csProgramID);
						env.setCommarea(null);
					}
					bContinue = false ;
				}
				else
				{
					env.deQueueProgram() ;
					bContinue = true ;
				}
				
				returnProgramInstanceToPool(currentProgram);
			}
		}
	}
	
	public void runSubProgram(String csProgramID, ArrayList<CCallParam> arrCallerCallParam, BaseEnvironment CESMEnv)
	{
		BaseProgram currentProgram = null ;
		
		ms_lock.lock();
		currentProgram = LoadProgramInstance(csProgramID, BaseResourceManager.getUseProgramPool()) ;
		if (currentProgram == null)
		{
			Log.logImportant("ERROR: Cannot load sub program instance:"+csProgramID);
			ms_lock.unlock();
			throw new AssertException("Cannot load sub program instance : " +csProgramID);
		}
		
		try
		{
			BaseProgramManager baseProgramManager = currentProgram.getProgramManager();
			boolean bNewProgramInstance = baseProgramManager.isNewProgramInstance();
			baseProgramManager.prepareCall(this, currentProgram, arrCallerCallParam, CESMEnv, bNewProgramInstance) ;
			
			ms_lock.unlock();
		}
		catch (AssertException e)
		{
			TempCacheLocator.getTLSTempCache().popCurrentProgram();
			
			Log.logImportant("ERROR: Cannot prepare program call :"+csProgramID);
			ms_lock.unlock();
			throw e ;
		}
		
		try
		{
			if(isLogFlow)
				Log.logVerbose("Calling program: "+currentProgram.getSimpleName());
			CESMEnv.startRunProgram(currentProgram.getSimpleName());
			currentProgram.getProgramManager().prepareRunMain(currentProgram);
			currentProgram.getProgramManager().runMain();
			TempCacheLocator.getTLSTempCache().popCurrentProgram();
			CESMEnv.endRunProgram(CriteriaEndRunMain.Normal);
		}
		catch (CESMReturnException e)
		{
			// program out...
			TempCacheLocator.getTLSTempCache().popCurrentProgram();
			CESMEnv.endRunProgram(CriteriaEndRunMain.Exit);
			if(isLogCESM || isLogFlow)
				Log.logVerbose("Program finished: "+currentProgram.getSimpleName());
		}
		catch (CExitException e)
		{
			// program out...
			TempCacheLocator.getTLSTempCache().popCurrentProgram();
			CESMEnv.endRunProgram(CriteriaEndRunMain.Exit);
			if(isLogCESM || isLogFlow)
				Log.logVerbose("Program finished: "+currentProgram.getSimpleName());
		}
		catch (AbortSessionException e)
		{
			CESMEnv.endRunProgram(CriteriaEndRunMain.Abort);
			e.m_ProgramName = currentProgram.getSimpleName();
			doNotReturnProgramInstanceToPool(currentProgram);
			currentProgram = null;
			throw e;
		}
		catch (AssertException e)
		{
			CESMEnv.endRunProgram(CriteriaEndRunMain.Abort);
			String csProgramName = currentProgram.getSimpleName();
			doNotReturnProgramInstanceToPool(currentProgram);
			currentProgram = null;
			throwAbortSession(e, csProgramName);
		}
		catch (Exception e)
		{
			CESMEnv.endRunProgram(CriteriaEndRunMain.Abort);
			String csProgramName = currentProgram.getSimpleName();
			doNotReturnProgramInstanceToPool(currentProgram);
			currentProgram = null;
			throwAbortSession(e, csProgramName);
		}
		
		returnProgramInstanceToPool(currentProgram);
		currentProgram = null; 
	}

	public void logMail(BaseEnvironment env, String className, String label, Throwable e, CSQLStatus sqlStatus) 
	{
	    DateUtil date = new DateUtil();
		String subject = date.toString() + " - " + m_csAlertMailSubjectTitle + " - " + className + " - " + label;
		if (m_MailService != null) 
		{
			if (e instanceof AbortSessionException && ((AbortSessionException)e).m_Reason != null) {
				e = ((AbortSessionException)e).m_Reason;
			}
				
		    StringBuffer sb = new StringBuffer();
		    String text = e.getMessage();
		    if (text == null)
		    {
		    	text = e.toString();
		    }
		    else
		    {
		    	text = e.toString() + " : " + text;
		    }
		    sb.append("" + text + "\r\n");
		    Log.logImportant("Dump-" + "Error:       " + text);
		    sb.append("Time:        " + date.toString() + "\r\n");
		    Log.logImportant("Dump-" + "Time:        " + date.toString());
			sb.append("Program:     " + className + "\r\n") ;
			Log.logImportant("Dump-" + "Program:     " + className);
			sb.append("Context:    \r\n") ;
			Log.logImportant("Dump-" + "Context:");
		    for (int i=0; i<e.getStackTrace().length; i++)
		    {
				StackTraceElement te = e.getStackTrace()[i] ;
				sb.append("             ");
				sb.append(te.toString() + "\r\n");
				Log.logImportant("Dump-" + "             " + te.toString());
		    }
		    
		    sb.append("\r\n");
		    TempCache tempCache = TempCacheLocator.getTLSTempCache();
		    String csLastSQLCodeErrorText = tempCache.getLastSQLCodeErrorText();
		    if(csLastSQLCodeErrorText != null && !csLastSQLCodeErrorText.equals(""))
		    {
		    	sb.append("Last SQL Status in error:\r\n");
		    	sb.append(csLastSQLCodeErrorText + "\r\n");
		    }
		    else
		    {
		    	sb.append("Unknown Last SQL Status in error\r\n");
		    }

		    sb.append("\r\n");
		    if(sqlStatus != null)
		    {
		    	sb.append("Current program SQL Status:\r\n");
		    	sb.append(sqlStatus.toString() + "\r\n");
		    }
		    else
		    {
		    	sb.append("Unknown Current program SQL Status\r\n");
		    }
		    
		    if(env != null)
		    {
		    	Document doc = env.getLastScreenXMLData();
		    	if(doc != null)
		    	{
		    		
		    		Tag tag = new Tag();
		    		tag.setDoc(doc);
		    		sb.append("\r\n");
		    		sb.append("Last Screen data:\r\n");
		    		String cs = tag.exportToString();
		    		sb.append(cs);
		    		sb.append("\r\n");
		    	}
		    }

		    Mail mail = m_MailService.createMail();
			mail.setSubject(subject);
			mail.setText(sb.toString());
			mail.send();
		}
	}
	
	public static void logMail(String csSubject, String csBodyText)
	{
		logMail(csSubject, csBodyText, null);
	}
	
	public static void logMail(String csSubject, String csBodyText, Throwable e)
	{
	    DateUtil date = new DateUtil();
		String subject = date.toString() + " - " + ms_Instance.m_csAlertMailSubjectTitle + " - " + csSubject;
		if (ms_Instance.m_MailService != null) 
		{
		    StringBuffer sb = new StringBuffer();
		    String csText;
		    if (e != null)
		    	csText = e.toString() + " : " + csBodyText;
		    else
		    	csText = csBodyText;
		    
		    sb.append(csText + "\r\n");
		    sb.append("Time:        " + date.toString() + "\r\n");
			
		    if(e != null)
		    {
			    sb.append("Context:    \r\n") ;
			    for (int i=0; i<e.getStackTrace().length; i++)
			    {
					StackTraceElement te = e.getStackTrace()[i] ;
					sb.append("             ");
					sb.append(te.toString() + "\r\n");
			    }
		    }
		    Mail mail = ms_Instance.m_MailService.createMail();
			mail.setSubject(subject);
			mail.setText(csText);
			mail.send();
		}
	}

	
	public void removeSession(BaseSession session)
	{
		m_tabSyncSessions.remove(session);
	}
	
	public BaseEnvironment getEnvironment(BaseSession appSession)
	{
		return m_tabSyncSessions.get(appSession);
	}
	
	/**
	 * @param elSequencerConfig
	 */
	public void initMailService(Tag tagSequencerConfig)
	{
		Tag tagMailConfig = tagSequencerConfig.getChild("MailConfig") ;
		if(tagMailConfig != null)
		{
			String addressFrom = tagMailConfig.getVal("addressFrom") ;
			String smtpServer = tagMailConfig.getVal("smtpServer");
			m_csAlertMailSubjectTitle = tagMailConfig.getVal("title");
			m_MailService = new MailService(smtpServer, addressFrom) ;
			
			Tag tagAdressTo = tagMailConfig.getEnumChild("addressTo") ;
			while(tagAdressTo != null)
			{
				String add = tagAdressTo.getVal("email");
				m_MailService.addAddressTo(add) ;
				tagAdressTo = tagMailConfig.getEnumChild() ;
			}
		}
	}

	/**
	 * 
	 */

	@Override
	public SessionEnvironmentRequester getSessionEnvironmentRequester(BaseSession appSession)
	{
		BaseEnvironment env = m_tabSyncSessions.get(appSession);
		return env ;
	}
	
	/**
	 * @param transID
	 * @param var
	 * @param intervalTimeSeconds
	 */
	public static void StartAsynchronousProgram(String transID, String csProgramParent, CESMStartData startData, int intervalTimeSeconds)
	{
		CAsynchronousTask task = new CAsynchronousTask(transID, csProgramParent, startData, intervalTimeSeconds) ;
		task.Start() ;
	}

}
