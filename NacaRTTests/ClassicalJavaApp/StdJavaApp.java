/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
import java.sql.SQLException;

import jlib.log.Asserter;
import jlib.misc.NumberParser;
import jlib.sql.DbConnectionBase;
import jlib.sql.DbConnectionManagerContext;
import nacaLib.batchPrgEnv.BatchMain;
import nacaLib.calledPrgSupport.ProgramCallerWithArgsPosition;

/**
 * 
 */

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: StdJavaApp.java,v 1.2 2007/09/18 08:24:07 u930di Exp $
 */
public class StdJavaApp
{
	StdJavaApp()
	{
	}
	
	void runBatch()
	{
		DbConnectionBase dbConnection = getConnection();
		if(dbConnection != null)
		{
			// We have a valid connection; now launch a naca cobol like program with it !
			int nStatus0 = BatchMain.executeTranscodedProgram("D:/Dev/naca/NacaRTTests/ClassicalJavaApp/nacaRTForBatchTest.cfg", dbConnection, "TestHelloWorld");
			int nStatus1 = BatchMain.executeTranscodedProgram("D:/Dev/naca/NacaRTTests/ClassicalJavaApp/nacaRTForBatchTests.cfg", dbConnection, "TestHelloWorld");
			int nStatus2 = BatchMain.executeTranscodedProgram("D:/Dev/naca/NacaRTTests/ClassicalJavaApp/nacaRTForBatchTests.cfg", dbConnection, "TestSQLAsBatch");

			dbConnection.returnConnectionToPool();
		}
	}
	
	void runCalledProgram()
	{
		//try
		{
			DbConnectionBase dbConnection = getConnection();
			if(dbConnection != null)
			{
				String csCountOut[] = new String[1];
				
				// We have a valid connection; now launch a naca cobol like program with it !
				ProgramCallerWithArgsPosition prgCaller = new ProgramCallerWithArgsPosition("D:/Dev/naca/NacaRTTests/ClassicalJavaApp/nacaRTForCalledTest.cfg", dbConnection, "TestHelloWorldCalled");
				prgCaller.setIn("1234");
				prgCaller.setOut(csCountOut);
				prgCaller.execute();
				int n = NumberParser.getAsInt(csCountOut[0]);
				Asserter.assertIfDifferent(n, 1235);
								
//				int nNbRecordInDB[] = new int [1];
//				ProgramCallerWithArgsNamed prgCallerSQL1 = new ProgramCallerWithArgsNamed("D:/Dev/naca/NacaRTTests/ClassicalJavaApp/nacaRTForCalledTest.cfg", dbConnection, "TestCalledProgramWithSQL");
//				
//				// Arguments are placed by name, not by position; Argument name must ParamDeclaration line of the called program: 
//				// public ParamDeclaration c = declare.using(LSPrefix).using(LSNbRecordToAdd).using(LSNbRecord).using(LSClear);
//				// Placement by name and position cannot be mixed within the same program call 
//				
//				prgCallerSQL1.setIn("LSClear", "Y");	// Clear at begin
//				prgCallerSQL1.setIn("LSNbRecordToAdd", 5);
//				prgCallerSQL1.setOut("LSNbRecord", nNbRecordInDB);
//				prgCallerSQL1.setIn("LSPrefix", "C");
//				prgCallerSQL1.execute();				
//				Asserter.assertIfDifferent(nNbRecordInDB[0], 5);
//				
//				// Program called with arg passed by position
//				ProgramCallerWithArgsPosition prgCallerSQL2 = new ProgramCallerWithArgsPosition("D:/Dev/naca/NacaRTTests/ClassicalJavaApp/nacaRTForCalledTest.cfg", dbConnection, "TestCalledProgramWithSQL");
//				prgCallerSQL2.setIn("D");
//				prgCallerSQL2.setIn(2);
//				prgCallerSQL2.setOut(nNbRecordInDB);
//				prgCallerSQL2.setIn("N");	// no Clear at begin
//				prgCallerSQL2.execute();				
//				Asserter.assertIfDifferent(nNbRecordInDB[0], 7);				
//
//				ProgramCallerWithArgsPosition prgCallerSQL3 = new ProgramCallerWithArgsPosition("D:/Dev/naca/NacaRTTests/ClassicalJavaApp/nacaRTForCalledTest.cfg", dbConnection, "TestCalledProgramWithSQL");
//				prgCallerSQL3.setIn("E");
//				prgCallerSQL3.setIn(0);
//				prgCallerSQL3.setOut(nNbRecordInDB);
//				prgCallerSQL3.setIn("Y");	// Clear at begin
//				prgCallerSQL3.execute();				
//				Asserter.assertIfDifferent(nNbRecordInDB[0], 0);				
			}			
			dbConnection.returnConnectionToPool();
		}
//		catch(SQLException e)
//		{
//			int n = 0;
//		}
	}
			
	private DbConnectionBase getConnection()
	{
		DbConnectionManagerContext dbConnectionManagerContext = new DbConnectionManagerContext();
		boolean b = dbConnectionManagerContext.create("DBTestUDB");	
		if(b)
		{
			DbConnectionBase dbConnectionBase = dbConnectionManagerContext.getConnection();
			return dbConnectionBase;
		}
		return null;
	}
}

