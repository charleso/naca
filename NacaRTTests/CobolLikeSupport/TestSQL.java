/*
 * NacaRTTests - Naca Tests for NacaRT support v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
import idea.onlinePrgEnv.OnlineProgram;

import java.sql.Time;
import java.util.Date;

import nacaLib.program.Paragraph;
import nacaLib.sqlSupport.SQLCode;
import nacaLib.sqlSupport.SQLCursor;
import nacaLib.varEx.DataSection;
import nacaLib.varEx.Var;

/**
 * 
 */

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class TestSQL extends OnlineProgram
{
	DataSection sqlcursorsection = sqlCursorSection() ;
		SQLCursor curFactHeader = cursorDefine();
		SQLCursor curFactLines = cursorDefine();

	// Tables are
	/*
	// TSTClient
	INSERT INTO TEST.VIT101(
	ID, NOM, PRENOM 
	) values (
	--ENTER VALUES BELOW  COLUMN NAME       DATA TYPE           LENGTH    NULL
	                     ,--ID                --INTEGER           10      NO  
	                     ,--NOM               --VARCHAR           20      NO  
	                      --PRENOM            --VARCHAR           20      NO  
	)
	
	// TstFactHeader
	INSERT INTO TEST.VIT102(
	ID, CLIENTID, FACTDATE 
	) values (
	--ENTER VALUES BELOW  COLUMN NAME       DATA TYPE           LENGTH    NULL
	                     ,--ID                --INTEGER           10      NO  
	                     ,--CLIENTID          --INTEGER           10      NO  
	                      --FACTDATE          --VARCHAR           10      NO  
	)
	
	// // TstFactLine
	INSERT INTO TEST.VIT103(
	FACTID, LINEID, QTY, ARTICLE, PRICE 
	) values (
	--ENTER VALUES BELOW  COLUMN NAME       DATA TYPE           LENGTH    NULL
	                     ,--FACTID            --INTEGER           10      NO  
	                     ,--LINEID            --INTEGER           10      NO  
	                     ,--QTY               --INTEGER           10      NO  
	                     ,--ARTICLE           --VARCHAR           20      NO  
	                     ,--PRICE             --DECIMAL           6 2     NO  
	)
	
	// TstCount
	INSERT INTO TEST.VIT104(
	COUNTID, VALUE 
	) values (
	--ENTER VALUES BELOW  COLUMN NAME       DATA TYPE           LENGTH    NULL
	                     ,--COUNTID           --INTEGER           10      NO  
	                     ,--VALUE             --DECIMAL           6 0     NO  
	*/

	
	DataSection WorkingStorage = declare.workingStorageSection();

	// Records
	// VIT101
	Var recClient = declare.level(1).var() ;
		Var clientId = declare.level(5).pic9(10).var() ;
		Var clientName = declare.level(5).picX(20).var() ;
		Var clientPreNom = declare.level(5).picX(20).var() ;
	       
	Var recFactHeader = declare.level(1).var() ;
		Var headerFactId = declare.level(5).pic9(10).var() ;
		Var headerClientId = declare.level(5).pic9(10).var() ;
		Var headerFactDate = declare.level(5).picX(10).var() ;

	Var recFactLine = declare.level(1).var() ;
		Var lineFactId = declare.level(5).pic9(10).var() ;
		Var lineLineId = declare.level(5).pic9(10).var() ;
		Var lineQty = declare.level(5).pic9(10).var() ;
		Var lineArticle = declare.level(5).picX(20).var() ;
		Var linePrice = declare.level(5).pic9(6, 2).var() ;

	Var recCount = declare.level(1).var() ;
		Var countId = declare.level(5).pic9(10).var() ;
		Var countValue = declare.level(5).picS9(6).comp3().var() ;
	
		
	Var vNbRecord = declare.level(77).pic9(5).var();
	
	public void procedureDivision()
	{
		System.out.println("TestSQL");
		
		setAssertActive(true);
		
		// Clean all tables
		deleteAllRecords();
		sqlCommit();
		
		checkTableSize("VIT101", 0);
		checkTableSize("VIT102", 0);
		checkTableSize("VIT103", 0);
		checkTableSize("VIT104", 0);
		
		createClient(1, "C1", "P1");
		createClient(2, "C1", "P2");
		createClient(3, "C3", "P3");
		sqlCommit();
		
		checkTableSize("VIT101", 3);
		
		createFactHeader(1, 1);
		createFactHeader(2, 2);
		sqlCommit();
		checkTableSize("VIT102", 2);
		
		createFactLine(1, 1, 5, "Article1", 1.5);
		createFactLine(1, 2, 2, "Article2", 1.5);
		sqlCommit();
		checkTableSize("VIT103", 2);

		createFactLine(2, 1, 5, "Article1", 1);
		createFactLine(2, 2, 5, "Article2", 1);
		createFactLine(2, 3, 5, "Article3", 1);
		sqlCommit();
		checkTableSize("VIT103", 5);
		
		sqlCommit();

		int nNbRecordsRead = selectAllFactHeaders();
		assertIfDifferent(nNbRecordsRead, 2);
	}
	
	void deleteAllRecords()
	{
		sql("Delete from VIT101");
		sql("Delete from VIT102");
		sql("Delete from VIT103");
		sql("Delete from VIT104");
	}
	
	void checkTableSize(String csTableName, int nNbRecords)
	{
		sql("SELECT count(*) from " + csTableName).into(vNbRecord);
		assertIfDifferent(nNbRecords, vNbRecord);		
	}
	
	void createClient(int nId, String csName, String csPrenom)
	{
		sql("insert into VIT101 (ID, NOM, PRENOM) VALUES (#1, #2, #3)")
			.value(1, nId)
			.value(2, csName)
			.value(3, csPrenom);		
	}
	
	void createFactHeader(int nFactId, int nClientId)
	{
		Date date = new Date();
		Time time = new Time(date.getTime());
		String csDate = String.valueOf(time);
		
		sql("insert into VIT102 (ID, CLIENTID, FACTDATE) VALUES (#1, #2, #3)")
			.value(1, nFactId)
			.value(2, nClientId)
			.value(3, csDate);		
	}
	
	void createFactLine(int nFactId, int nLineId, int nQty, String csArticle, double dPrice)
	{
		sql("insert into VIT103 (FACTID, LINEID, QTY, ARTICLE, PRICE) VALUES (#1, #2, #3, #4, #5)")
		.value(1, nFactId)
		.value(2, nLineId)
		.value(3, nQty)
		.value(4, csArticle)
		.value(5, dPrice);
	}
	
	int selectAllFactHeaders()
	{
		cursorOpen(curFactHeader, "SELECT H.ID, H.CLIENTID, H.FACTDATE, C.NOM, C.PRENOM " +
			" FROM VIT101 H, VIT102 C " +
			"  where H.CLIENTID=C.ID");
		int nNbRecordsFound = 0;
		while(isSQLCode(SQLCode.SQL_OK))
		{
			cursorFetch(curFactHeader)
				.into(headerFactId)
				.into(headerClientId)
				.into(headerFactDate)
				.into(clientName)
				.into(clientPreNom).onErrorGoto(errorCursorFactHeader);
		
			if(isSQLCode(SQLCode.SQL_OK))
				nNbRecordsFound++;
		}
		return nNbRecordsFound;	
	}
	
	
	Paragraph errorCursorFactHeader = new Paragraph(this){public void run(){errorCursorFactHeader();}} ;                                                   
	void errorCursorFactHeader() 
	{
		assertIfFalse(false);
	}
}
