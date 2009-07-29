/*
 * NacaRTTests - Naca Tests for NacaRT support v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
import nacaLib.batchPrgEnv.BatchProgram;
import nacaLib.varEx.DataSection;
import nacaLib.varEx.Var;


public class TestSQLCurrentDate extends BatchProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();
	
	Var dts_Db2 = declare.level(1).picX(26).var() ;                             // (185)  01  DTS-DB2        PIC X(26).	
	
	public void procedureDivision()
	{
		System.out.println("TestSQLCurrentDate");
		
		SQLSetCurrentDate(dts_Db2) ;
		SQLSetCurrentTimeStamp(dts_Db2) ;

		SQLSetCurrentDate(dts_Db2) ;
		SQLSetCurrentTimeStamp(dts_Db2) ;

	}
}
