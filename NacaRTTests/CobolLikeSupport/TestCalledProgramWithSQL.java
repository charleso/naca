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
/**
 * 
 */

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
import java.sql.Time;
import java.util.Date;

import nacaLib.callPrg.CalledProgram;
import nacaLib.program.Paragraph;
import nacaLib.sqlSupport.SQLCode;
import nacaLib.sqlSupport.SQLCursor;
import nacaLib.varEx.DataSection;
import nacaLib.varEx.ParamDeclaration;
import nacaLib.varEx.Var;

/**
 * 
 */

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class TestCalledProgramWithSQL extends CalledProgram
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
	
	public DataSection LinkageSection = declare.linkageSection();
		public Var LSPrefix = declare.level(1).picX(2).var();
		public Var LSNbRecordToAdd = declare.level(1).pic9(4).var();
		public Var LSNbRecord = declare.level(1).pic9(4).var();
		public Var LSClear = declare.level(1).picX(1).var();

	public ParamDeclaration c = declare.using(LSPrefix).using(LSNbRecordToAdd).using(LSNbRecord).using(LSClear);
	
	public void procedureDivision()
	{
		setAssertActive(true);
		
		if(isEqual(LSClear, "Y"))	// Clear at beginning 
		{	
			// Clean all tables
			deleteAllRecords();
			sqlCommit();			
		}
		
		int nBaseId = getTableSize();
		
		int nNbRecordToAdd = LSNbRecordToAdd.getInt();
		for(int n=0; n<nNbRecordToAdd; n++)
		{
			String cs = LSPrefix.getString() + n;
			createClient(nBaseId+n, cs, cs);
		}
		
		int nNbRecord = getTableSize();		
		move(nNbRecord, LSNbRecord);
		sqlCommit();		
	}

	void deleteAllRecords()
	{
		sql("Delete from VIT101");
	}
	
	void createClient(int nId, String csName, String csPrenom)
	{
		sql("insert into VIT101 (ID, NOM, PRENOM) VALUES (#1, #2, #3)")
			.value(1, nId)
			.value(2, csName)
			.value(3, csPrenom);		
	}
	
	int getTableSize()
	{
		sql("SELECT count(*) from VIT101").into(vNbRecord);
		return vNbRecord.getInt();		
	}
}
