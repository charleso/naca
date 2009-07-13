/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.batchPrgEnv;

import jlib.log.Log;
import jlib.misc.EnvironmentVar;
import jlib.misc.JVMReturnCodeManager;
import jlib.misc.NumberParser;
import jlib.misc.Time_ms;
import jlib.sql.DbConnectionBase;
import jlib.sql.SQLLoadStatus;
import nacaLib.accounting.CriteriaEndRunMain;
import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.basePrgEnv.BaseProgramLoader;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.bdb.BTreeCommandSort;
import nacaLib.bdb.FileKeyExporter;
import nacaLib.dbUtils.DbTransfer;
import nacaLib.dbUtils.FileExporter;
import nacaLib.dbUtils.SQLFileExecutor;
import nacaLib.dbUtils.SQLLoad;
import nacaLib.dbUtils.SQLUnload;
import nacaLib.exceptions.AbortSessionException;
import nacaLib.fileConverter.FileConverter;
import nacaLib.fileConverter.FileEncodingConverterWithClass;
import nacaLib.fileConverter.FileEncodingConverterWithDesc;
import nacaLib.fileConverter.FileSearchGeneration;
import nacaLib.fileConverter.FileUtil;
import nacaLib.misc.CCommarea;
import nacaLib.misc.MailUtil;
import nacaLib.tempCache.TempCacheLocator;
import nacaLib.varEx.FileDescriptor;
import nacaLib.varEx.InternalCharBuffer;

public class BatchMain
{
	public static void main(String[] args)
	{
		String csPrgClassName = null;
		String csConfigFile = null;
		String csDB = "";
		String csStat = null;
		String csParameter = null;
		String csExportKeyFileOut = null;
		boolean bEnableInitialConnectDb = false;
		String csCmdLine = "";
		Object nt[]= null;
			
		EnvironmentVar.registerCmdLineArgs(args);
		for (int nArg=0; nArg<args.length; nArg++)
		{
			String s = args[nArg];
			csCmdLine += s + " ";
			
			if (s.startsWith("-") || s.startsWith("/"))
			{
				String sArg = s.substring(1);
				String sArgUpper = sArg.toUpperCase();

				if (sArgUpper.startsWith("PROGRAM="))
				{
					csPrgClassName = sArg.substring(8); 
				}	
				else if (sArgUpper.startsWith("CONFIGFILE="))
				{
					csConfigFile = sArg.substring(11);
				}
				else if (sArgUpper.startsWith("DB="))
				{
					csDB = sArg.substring(3);
				}
				else if(sArgUpper.startsWith("STAT="))
				{
					csStat = sArg.substring(5);
				}
				else if(sArgUpper.startsWith("NACABATCH="))
				{
					EnvironmentVar.registerCmdLineArgs(s.split(" "));
				}
				else if(sArgUpper.startsWith("EXPORTKEYFILEOUT="))
				{
					csExportKeyFileOut = sArg.substring(17);
				}
				else if(sArgUpper.startsWith("HELP"))
				{
					displayHelp();
					return ;
				}
				else if(sArgUpper.equalsIgnoreCase("EnableInitialDbConnection"))
				{
					bEnableInitialConnectDb = true;
				}
				else if(sArgUpper.startsWith("FORCEDCOMPARISONMODE="))
				{
					String cs = sArg.substring(21);
					if(cs.equalsIgnoreCase("ascii"))
						BaseResourceManager.setForcedComparisonInEbcdic(false);
					if(cs.equalsIgnoreCase("ebcdic"))
						BaseResourceManager.setForcedComparisonInEbcdic(true);
				}
				else if(sArgUpper.startsWith("ALLOCMEMORY="))
				{
					String cs_ms = sArg.substring(12);
					long lNbItems = NumberParser.getAsLong(cs_ms);
					int nNb = (int)lNbItems;
					nt = new Object[nNb];
				}
				else if(sArgUpper.startsWith("WAIT_MS="))
				{
					String cs_ms = sArg.substring(8);
					long l = NumberParser.getAsLong(cs_ms);
					Time_ms.wait_ms(l);
					JVMReturnCodeManager.exitJVM(0);
				}	
			}
			else
			{
				if (s.length() > 0)
				{	
					csParameter = s;
				}
			}
		}
		JVMReturnCodeManager.setExitCode(0);
			
		if(csPrgClassName.equalsIgnoreCase("SortMain"))
			doExternalSort(csConfigFile, csParameter, csExportKeyFileOut);
		else if(csPrgClassName.equalsIgnoreCase("ExportKeySortMain"))
			doExportKeySortMain(csConfigFile, csParameter, csExportKeyFileOut);
		else if(csPrgClassName.equalsIgnoreCase("EncodingConverter"))
			doEncodingConverter(csConfigFile, csParameter);
		else if(csPrgClassName.equalsIgnoreCase("FileConverter"))
			doFileConverter(csConfigFile, csParameter);
		else if(csPrgClassName.equalsIgnoreCase("FileUtil"))
			doFileUtil(csConfigFile, csParameter);
		else if(csPrgClassName.equalsIgnoreCase("FileSearchGeneration"))
			doFileSearchGeneration(csConfigFile, csParameter);
		else if(csPrgClassName.equalsIgnoreCase("MailUtil"))
			doMailUtil(csConfigFile, csParameter);
		else if(csPrgClassName.equalsIgnoreCase("dsntiad"))		// Db Util execute statements
			doDbExecute(csConfigFile, csDB);
		else if(csPrgClassName.equalsIgnoreCase("dsntiaul"))	// Db Util unload
			doDbUnload(csConfigFile, csDB, false);
		else if(csPrgClassName.equalsIgnoreCase("db2unlot"))	// Db Util unload in excel format
			doDbUnload(csConfigFile, csDB, true);
		else if(csPrgClassName.equalsIgnoreCase("dsntial") || csPrgClassName.equalsIgnoreCase("dsnutilb"))	// Db Util load
			doDbLoad(csConfigFile, csDB);
		else if(csPrgClassName.equalsIgnoreCase("export"))	// Export file
			doExport(csConfigFile);
		else if(csPrgClassName.equalsIgnoreCase("DbTransfer"))	// Transfer Database
			dbTransfer(csConfigFile, csDB);
		else if (csPrgClassName != null && csConfigFile != null)
			doRunBatchProgram(csConfigFile, csDB, bEnableInitialConnectDb, csParameter, csPrgClassName);
		else
		{
			displayHelp();
			return;
		}

		if(csStat != null)
		{
			BatchResourceManager.setDumpStatOutput(csStat);		
			BatchResourceManager.dumpStat();
		}
		
		Log.close();
		
		JVMReturnCodeManager.exitJVM();
	}
	
	private static void doExternalSort(String csConfigFile, String csParameter, String csExportKeyFileOut)
	{
		try
		{
			// External sort
			BatchResourceManager batchResourceManager = BatchResourceManagerFactory.GetInstance(csConfigFile);
			BatchSession session = new BatchSession(batchResourceManager) ;
			
			FileDescriptor fileSortIn = null;
			FileDescriptor fileSortOut = null;
			
			boolean b = FileDescriptor.isExistingFileDescriptor("SORTIN", session);
			if(b)
			{
				fileSortIn = new FileDescriptor("SORTIN", session);
			}
			
			b = FileDescriptor.isExistingFileDescriptor("SORTOUT", session);
			if(b)
			{
				fileSortOut = new FileDescriptor("SORTOUT", session);
			}
	
			if(fileSortIn == null || fileSortOut == null)
			{
				Log.logCritical("No SORTIN and SORTOUT specification");
				JVMReturnCodeManager.setExitCode(8);
				return ;
			}
			
			if(csParameter == null)
			{
				Log.logCritical("No sort key parameter defined");
				JVMReturnCodeManager.setExitCode(8);
				return ;
			}
			
			int nPos = csParameter.indexOf("FIELDS=");
			if(csParameter.startsWith("SORT") && nPos > 0)
			{	
//				StopWatch sw = new StopWatch(); 
				String csFileOut = fileSortOut.getPhysicalName();
								
				TempCacheLocator.setTempCache();	// Init TLS
				
				String csKeys = csParameter.substring(nPos+7);
				String csTempDir = BaseResourceManager.getTempDir();
				BTreeCommandSort sort = new BTreeCommandSort();
				sort.set(csTempDir, csFileOut, csKeys);
				
				sort.setExportKeyFileOut(csExportKeyFileOut);
				int nFileLineReaderBufferSize = BaseResourceManager.getFileLineReaderBufferSize();
				boolean bDone = sort.execute(nFileLineReaderBufferSize, fileSortIn, fileSortOut);
				if(!bDone)
				{
					JVMReturnCodeManager.setExitCode(8);
				}
//				long lSortTime_ms = sw.getElapsedTime();
//				System.out.println("Exernal sort time (ms)="+lSortTime_ms);
			}
			else
			{
				Log.logCritical("No valid SORT FIELDS= parameter; it is "+csParameter);
				JVMReturnCodeManager.setExitCode(8);
			}
		}
		catch (Exception e)
		{
			JVMReturnCodeManager.setExitCode(8);
		}
	}
	
	private static void doExportKeySortMain(String csConfigFile, String csParameter, String csExportKeyFileOut)
	{
		try
		{
			// Export the key of the provided in file
			BatchResourceManager batchResourceManager = BatchResourceManagerFactory.GetInstance(csConfigFile);
			BatchSession session = new BatchSession(batchResourceManager) ;
			
			FileDescriptor fileSortIn = null;
			
			boolean b = FileDescriptor.isExistingFileDescriptor("SORTIN", session);
			if(b)
			{
				fileSortIn = new FileDescriptor("SORTIN", session);
			}
	
			if(fileSortIn == null)
			{
				Log.logCritical("No SORTIN specification");
				JVMReturnCodeManager.setExitCode(8);
				return ;
			}
			
			if(csParameter == null)
			{
				Log.logCritical("No sort key parameter defined");
				JVMReturnCodeManager.setExitCode(8);
				return ;
			}
			
			if(csParameter.startsWith("SORT FIELDS="))
			{
				String csFileIn = fileSortIn.getPhysicalName();
				String csKeys = csParameter.substring(12);
				
				FileKeyExporter fk = new FileKeyExporter(csKeys, csExportKeyFileOut, fileSortIn.isEbcdic());
				int nFileLineReaderBufferSize = BaseResourceManager.getFileLineReaderBufferSize();
				fk.execute(csFileIn, nFileLineReaderBufferSize);
			}
			else
			{
				Log.logCritical("No valid SORT FIELDS= parameter; it is "+csParameter);
				JVMReturnCodeManager.setExitCode(8);
			}
		}
		catch (Exception e)
		{
			JVMReturnCodeManager.setExitCode(8);
		}
	}
	
	private static void doEncodingConverter(String csConfigFile, String csParameter)
	{
		try
		{
			BatchResourceManager batchResourceManager = BatchResourceManagerFactory.GetInstance(csConfigFile);
			BatchSession session = new BatchSession(batchResourceManager) ;
			
			FileDescriptor fileIn = null;
			boolean b = FileDescriptor.isExistingFileDescriptor("FILEIN", session);
			if(b)
			{
				fileIn = new FileDescriptor("FILEIN", session);
			}
			
			FileDescriptor fileOut = null;
			b = FileDescriptor.isExistingFileDescriptor("FILEOUT", session);
			if(b)
			{
				fileOut = new FileDescriptor("FILEOUT", session);
			}
			
			String csParameterUpperCase = csParameter.toUpperCase();
			
			boolean bHost = false;
			if (csParameterUpperCase.indexOf("HOST") != -1)
				bHost = true;
				
			if(fileIn == null || fileOut == null)
			{
				Log.logCritical("No FILEIN and FILEOUT specification");
				JVMReturnCodeManager.setExitCode(8);
			}
			else if(csParameter == null)
			{
				Log.logCritical("No encoding converter parameter found");
				JVMReturnCodeManager.setExitCode(8);
			}
			else if(csParameterUpperCase.indexOf("COPYCLASS=") != -1)
			{
				int nPos = csParameterUpperCase.indexOf("COPYCLASS=") + 10;
				String csCopyClass = csParameter.substring(nPos);
				FileEncodingConverterWithClass conv = new FileEncodingConverterWithClass(fileIn, fileOut);
				if (bHost)
					conv.setHost(csParameter);
				conv.execute(csCopyClass);
			}
			else if(csParameterUpperCase.indexOf("DESC=") != -1)
			{
				int nPos = csParameterUpperCase.indexOf("DESC=") + 5;
				String csDesc = csParameter.substring(nPos);
				FileEncodingConverterWithDesc conv = new FileEncodingConverterWithDesc(fileIn, fileOut);
				if (bHost)
					conv.setHost(csParameter);
				conv.execute(csDesc);
			}
			else
			{
				Log.logCritical("No CopyClass or Desc parameter specified : " + csParameter);
				JVMReturnCodeManager.setExitCode(8);
			}
		}
		catch (Exception e)
		{
			JVMReturnCodeManager.setExitCode(8);
		}
	}
	
	private static void doFileConverter(String csConfigFile, String csParameter)
	{
		try
		{
			BatchResourceManager batchResourceManager = BatchResourceManagerFactory.GetInstance(csConfigFile);
			BatchSession session = new BatchSession(batchResourceManager) ;
			
			FileDescriptor fileConv = null;
			boolean b = FileDescriptor.isExistingFileDescriptor("FILECONV", session);
			if(b)
			{
				fileConv = new FileDescriptor("FILECONV", session);
			}
			
			if(fileConv == null)
			{
				Log.logCritical("No FILECONV specification");
				JVMReturnCodeManager.setExitCode(8);
			}
			else
			{
				FileConverter conv = new FileConverter(fileConv);
				conv.execute(csParameter);
			}
		}
		catch (Exception e)
		{
			JVMReturnCodeManager.setExitCode(8);
		}
	}

	private static void doFileUtil(String csConfigFile, String csParameter)
	{
		try
		{
			BatchResourceManager batchResourceManager = BatchResourceManagerFactory.GetInstance(csConfigFile);
			BatchSession session = new BatchSession(batchResourceManager) ;
			
			FileDescriptor fileUtil = null;
			boolean b = FileDescriptor.isExistingFileDescriptor("FILEUTIL", session);
			if(b)
			{
				fileUtil = new FileDescriptor("FILEUTIL", session);
			}
			
			if(csParameter == null)
			{
				Log.logCritical("No parameter defined");
				JVMReturnCodeManager.setExitCode(8);
				return;
			}
	
			if(fileUtil == null)
			{
				Log.logCritical("No FILEUTIL specification");
				JVMReturnCodeManager.setExitCode(8);
			}
			else
			{
				FileUtil util = new FileUtil(fileUtil);
				util.execute(csParameter);
			}
		}
		catch (Exception e)
		{
			JVMReturnCodeManager.setExitCode(8);
		}
	}
	
	private static void doFileSearchGeneration(String csConfigFile, String csParameter)
	{
		try
		{
			if (csParameter == null)
			{	
				Log.logCritical("No parameter specification");
				JVMReturnCodeManager.setExitCode(8);
			}
			else
			{
				int nGeneration = 0;
				int nPos = csParameter.indexOf(",generation=");
				if (nPos != -1)
				{
					csParameter = csParameter.substring(0, nPos);
					nGeneration = new Integer(csParameter.substring(nPos + 12)).intValue();
				}
				FileSearchGeneration file = new FileSearchGeneration();
				String csGeneration = file.search(csParameter, nGeneration);
				if (csGeneration == null)
				{
					JVMReturnCodeManager.setExitCode(8);
				}
				System.out.println(csGeneration);
			}
		}
		catch (Exception e)
		{
			JVMReturnCodeManager.setExitCode(8);
		}
	}
	
	private static void doMailUtil(String csConfigFile, String csParameter)
	{
		try
		{
			BatchResourceManager batchResourceManager = BatchResourceManagerFactory.GetInstance(csConfigFile);
			BatchSession session = new BatchSession(batchResourceManager) ;
			
			FileDescriptor mailUtil = null;
			boolean b = FileDescriptor.isExistingFileDescriptor("MAILUTIL", session);
			if(b)
			{
				mailUtil = new FileDescriptor("MAILUTIL", session);
			}
			
			if(mailUtil == null)
			{
				Log.logCritical("No MAILUTIL specification");
				JVMReturnCodeManager.setExitCode(8);
			}
			else
			{
				MailUtil mail = new MailUtil(mailUtil);
				mail.execute(csParameter);
			}
		}
		catch (Exception e)
		{
			JVMReturnCodeManager.setExitCode(8);
		}
	}
		
	private static void doDbUnload(String csConfigFile, String csDB, boolean bExcel)
	{
		try
		{
			// Unload table in file
			BatchResourceManager batchResourceManager = BatchResourceManagerFactory.GetInstance(csConfigFile, csDB);
			BatchSession session = new BatchSession(batchResourceManager) ;
			BaseProgramLoader loader = BatchProgramLoader.GetProgramLoaderInstance() ;
			BaseEnvironment env = loader.GetEnvironment(session, "DSNTIAUL", null) ;
			boolean bStarted = env.startRunTransaction();
			if(!bStarted)
			{
				env.endRunTransaction(CriteriaEndRunMain.Abort);
				JVMReturnCodeManager.setExitCode(8);
				return ;
			}

	
			DbConnectionBase dbConnection = env.getSQLConnection();
			if(dbConnection != null)
			{
				FileDescriptor fileScript = new FileDescriptor("SYSIN", session);
				SQLUnload sqlUnloader = new SQLUnload(session, dbConnection, bExcel);
				boolean bExecuted = sqlUnloader.execute(fileScript);
				if(bExecuted)
				{
					env.endRunTransaction(CriteriaEndRunMain.Normal);
					dbConnection.commit();
				}
				else
				{
					env.endRunTransaction(CriteriaEndRunMain.Abort);
					dbConnection.rollBack();
					JVMReturnCodeManager.setExitCode(8);
				}
			}
			else
			{
				env.endRunTransaction(CriteriaEndRunMain.Exit);
				JVMReturnCodeManager.setExitCode(8);			
			}
		}
		catch (Exception e)
		{
			JVMReturnCodeManager.setExitCode(8);
		}
	}
	
	private static void doDbLoad(String csConfigFile, String csDB)
	{
		try
		{
			// Lload table in file
			BatchResourceManager batchResourceManager = BatchResourceManagerFactory.GetInstance(csConfigFile, csDB);
			BatchSession session = new BatchSession(batchResourceManager) ;
			BaseProgramLoader loader = BatchProgramLoader.GetProgramLoaderInstance() ;
			BaseEnvironment env = loader.GetEnvironment(session, "DSNTIAL", null) ;
			boolean bStarted = env.startRunTransaction();
			if(!bStarted)
			{
				env.endRunTransaction(CriteriaEndRunMain.Abort);
				JVMReturnCodeManager.setExitCode(8);
				return ;
			}
		
			DbConnectionBase dbConnection = env.getSQLConnection();
			if(dbConnection != null)
			{
				FileDescriptor fileScriptSysin = new FileDescriptor("SYSIN", session);
				SQLLoad sqlLoader = new SQLLoad(session, dbConnection);
				SQLLoadStatus loadStatus = sqlLoader.execute(fileScriptSysin);
				if(loadStatus.isSuccess())
				{
					dbConnection.commit();
					env.endRunTransaction(CriteriaEndRunMain.Normal);

					if(loadStatus.hadDuplicates())
					{
						JVMReturnCodeManager.setExitCode(4);
					}
					else
					{
						JVMReturnCodeManager.setExitCode(0);
					}
				}
				else
				{
					env.endRunTransaction(CriteriaEndRunMain.Abort);
					dbConnection.rollBack();
					JVMReturnCodeManager.setExitCode(8);
				}
			}
			else
			{
				env.endRunTransaction(CriteriaEndRunMain.Exit);
				JVMReturnCodeManager.setExitCode(8);
			}
		}
		catch (Exception e)
		{
			JVMReturnCodeManager.setExitCode(8);
		}	
	}
	
	private static void doDbExecute(String csConfigFile, String csDB)
	{
		try
		{
			// Export the key of the provided in file
			BatchResourceManager batchResourceManager = BatchResourceManagerFactory.GetInstance(csConfigFile, csDB);
			BatchSession session = new BatchSession(batchResourceManager) ;
			BaseProgramLoader loader = BatchProgramLoader.GetProgramLoaderInstance() ;
			BaseEnvironment env = loader.GetEnvironment(session, "DSNTIAD", null) ;
			boolean bStarted = env.startRunTransaction();
			if(!bStarted)
			{
				env.endRunTransaction(CriteriaEndRunMain.Abort);
				JVMReturnCodeManager.setExitCode(8);
				return ;
			}

	
			DbConnectionBase dbConnection = env.getSQLConnection();
			if(dbConnection != null)
			{
				FileDescriptor fileIn = new FileDescriptor("SYSIN", session);
				
				SQLFileExecutor sqlFileExecutor = new SQLFileExecutor(session, dbConnection);
				boolean bExecuted = sqlFileExecutor.execute(fileIn);
				if(bExecuted)
				{
					dbConnection.commit();
					env.endRunTransaction(CriteriaEndRunMain.Normal);
				}
				else
				{
					dbConnection.rollBack();
					env.endRunTransaction(CriteriaEndRunMain.Abort);
					JVMReturnCodeManager.setExitCode(8);
				}
			}
			else
			{
				env.endRunTransaction(CriteriaEndRunMain.Exit);
				JVMReturnCodeManager.setExitCode(8);
			}
		}
		catch (Exception e)
		{
			JVMReturnCodeManager.setExitCode(8);
		}
	}
	
	private static void dbTransfer(String csConfigFile, String csDB)
	{
		int nStatus = -1;
		BatchResourceManager batchResourceManager = BatchResourceManagerFactory.GetInstance(csConfigFile, csDB);
		BatchSession session = new BatchSession(batchResourceManager) ;
		BaseProgramLoader loader = BatchProgramLoader.GetProgramLoaderInstance() ;
		BaseEnvironment env = loader.GetEnvironment(session, "DSNTIAL", null) ;
		boolean bStarted = env.startRunTransaction();
		if(!bStarted)
		{
			env.endRunTransaction(CriteriaEndRunMain.Abort);
			JVMReturnCodeManager.setExitCode(8);
			return ;
		}
	
		DbTransfer dbTransfer = new DbTransfer();
		nStatus = dbTransfer.execute(env, csConfigFile);
		
		if(nStatus != 0)
		{	
			Log.logCritical("Errors during DbTransfer");
		}
		JVMReturnCodeManager.setExitCode(nStatus);
	}

	private static void doExport(String csConfigFile)
	{
		try
		{
			// Export the key of the provided in file
			BatchResourceManager batchResourceManager = BatchResourceManagerFactory.GetInstance(csConfigFile);
			BatchSession session = new BatchSession(batchResourceManager) ;
			
			FileDescriptor exportFileIn = null;
			FileDescriptor exportFileOut = null;
			
			boolean b = FileDescriptor.isExistingFileDescriptor("FILEIN", session);
			if(b)
			{
				exportFileIn = new FileDescriptor("FILEIN", session);
			}
			b = FileDescriptor.isExistingFileDescriptor("FILEOUT", session);
			if(b)
			{
				exportFileOut = new FileDescriptor("FILEOUT", session);
			}
	
			if(exportFileIn == null || exportFileOut == null)
			{
				Log.logCritical("No FILEIN or FILEOUT specification");
				JVMReturnCodeManager.setExitCode(8);
				return ;
			}

			FileExporter fe = new FileExporter(session);
			b = fe.execute(exportFileIn, exportFileOut);
			if(b)
			{
				JVMReturnCodeManager.setExitCode(0);
				return ;
			}
		}
		catch (Exception e)
		{			
		}
		JVMReturnCodeManager.setExitCode(8);
	}
	
	public static int executeTranscodedProgram(String csConfigFile, DbConnectionBase dbConnection, String csPrgClassName)
	{
		BatchResourceManager batchResourceManager = BatchResourceManagerFactory.GetInstance(csConfigFile, dbConnection.getEnvironmentPrefix());
		if(batchResourceManager == null)
			return 8;
		
		BatchSession session = new BatchSession(batchResourceManager) ;
			
		BaseEnvironment env = null;
		try
		{
			BaseProgramLoader loader = BatchProgramLoader.GetProgramLoaderInstance() ;
			env = loader.GetEnvironment(session, csPrgClassName, null) ;
			env.setExternalDbConnection(dbConnection);
			env.fillEnvConnectionWithAllocatedConnection(dbConnection.getDbConnection(), "ExternalConnection", dbConnection.getEnvironmentPrefix(), true);	// bUseStatementCache							
	
			boolean bStarted = env.startRunTransaction();
			if(!bStarted)
			{
				env.endRunTransaction(CriteriaEndRunMain.Abort);
				return 8;
			}

//			if (csParameter != null)
//			{
//				InternalCharBuffer charBuffer = new InternalCharBuffer(2 + csParameter.length());
//				int nPos = 0;
//				nPos = charBuffer.writeShort(new Integer(csParameter.length()).shortValue(), nPos);
//				nPos = charBuffer.writeString(csParameter, nPos);
//				CCommarea comm = new CCommarea();
//				comm.setVarPassedByValue(charBuffer);
//				env.setCommarea(comm);
//			}
				
			loader.runTopProgram(env, null);
			
			env.endRunTransaction(CriteriaEndRunMain.Normal);
			return 0;
		}
		catch (AbortSessionException e)
		{
			env.endRunTransaction(CriteriaEndRunMain.Abort);
			return 8;
		}
		catch(Exception e)
		{
			env.endRunTransaction(CriteriaEndRunMain.Abort);
			return 8;
		}
	}
	
	private static void doRunBatchProgram(String csConfigFile, String csDB, boolean bEnableInitialConnectDb, String csParameter, String csPrgClassName)
	{
		// Batch program
		BatchResourceManager batchResourceManager = BatchResourceManagerFactory.GetInstance(csConfigFile, csDB);
		BaseEnvironment env = null;
		try
		{
			BatchSession session = new BatchSession(batchResourceManager) ;
			BaseProgramLoader loader = BatchProgramLoader.GetProgramLoaderInstance() ;
			env = loader.GetEnvironment(session, csPrgClassName, null) ;
			env.setInitialConnectDb(bEnableInitialConnectDb);
			boolean bStarted = env.startRunTransaction();
			if(!bStarted)
			{
				env.endRunTransaction(CriteriaEndRunMain.Abort);
				JVMReturnCodeManager.setExitCode(8);
				return ;
			}

			boolean bCanExecute = true;
			if (bEnableInitialConnectDb)	// Connection must be established
			{
				bCanExecute = env.abortTransWhenInvalidDbConnection();
			}
						
			if(bCanExecute)
			{
				if (csParameter != null)
				{
					InternalCharBuffer charBuffer = new InternalCharBuffer(2 + csParameter.length());
					int nPos = 0;
					nPos = charBuffer.writeShort(new Integer(csParameter.length()).shortValue(), nPos);
					nPos = charBuffer.writeString(csParameter, nPos);
					CCommarea comm = new CCommarea();
					comm.setVarPassedByValue(charBuffer);
					env.setCommarea(comm);
				}
				
				loader.runTopProgram(env, null);
				
				env.endRunTransaction(CriteriaEndRunMain.Normal);
			}
		}
		catch (AbortSessionException e)
		{
			env.endRunTransaction(CriteriaEndRunMain.Abort);
			JVMReturnCodeManager.setExitCode(8);
		}
		catch(Exception e)
		{
			env.endRunTransaction(CriteriaEndRunMain.Abort);
			JVMReturnCodeManager.setExitCode(8);
		}
	}
	
	static void displayHelp()
	{
		System.out.println("batchMain: Transcoded Batch Cobol Application executor");
		System.out.println("Command line is");
		System.out.println("        batchMain ");
		System.out.println("        	-Program=CaseSensitiveString | SortMain for external Sort");
		System.out.println("        	-ConfigFile=path an file name");
		System.out.println("       		[-DB=Oracle|DB2] (defaulted to DB2)");
		System.out.println("        	[-Stat=path and file name]");
		System.out.println("        	[-EnableInitialDbConnection] : To enable connecting to DB before lauching 1st program"); 
		System.out.println("");
		System.out.println("If mode -Program=SortMain");
		System.out.println("	[-ExportKeyFileOut=Path and file of an optional export key file for external sort]");
		System.out.println("	Last argument must be \"SORT FIELDS=(1 Based position,Length,CH|PD|BI|FI,A|D)+\"	// Describe the sort key e.g. : \"SORT FIELDS=(1,8,CH,A,9,4,PD,A,13,2,CH,A)\"");
	}
}