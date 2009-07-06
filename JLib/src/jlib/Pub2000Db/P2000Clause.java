/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.Pub2000Db;

import java.util.Calendar;
import java.util.Date;
import jlib.languageUtil.LanguageId;
import jlib.misc.DateUtil;
import jlib.misc.NumberParser;
import jlib.sql.DbAccessor;
import jlib.sql.DbConnectionBase;
import jlib.sql.SQLClause;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: P2000Clause.java,v 1.10 2008/02/14 15:21:34 u930bm Exp $
 */
/* Sample usage of P2000Clause with a main and alternate connection:
   P2000Clause clause = new P2000Clause(); 
  
   DbConnectionBase mainConnection = clause.getAlternateConnection();
   Asserter.assertIfNotNull(mainConnection); // mainConnection is null as it's not an alternate connection
   // L a connection principale est gérée dans le TLS. Il n'y a pas besoin de la passer explicitement d'une méthode à l'autre; elle n'est pas accessible publiquement
   
   clause.set("SELECT * FROM RSGV43");
   clause.prepareAndExecute();
   clause.next();
   
   P2000Clause clauseAlternate = new P2000Clause(P2000Accessor.accessor, null);    // 1st call to establish an alternate connection; it's identified by the null parameter
   DbConnectionBase alternateConnection = clauseAlternate.getAlternateConnection(); // Access to the alternate connection
   Asserter.assertIfNull(alternateConnection);                             // alternateConnection is not null as it's an alternate connection

   // Do some actions on the alternate connection
   clauseAlternate.set("SELECT count(*) FROM RSGV00");
   clauseAlternate.prepareAndExecute();
   clauseAlternate.next();
   clauseAlternate.close();

   // Open another clause with the same alternate connection
   P2000Clause clauseAlternate2 = new P2000Clause(P2000Accessor.accessor, alternateConnection);    // The previously allocated alternate connection is given in second parameter
   // Do some actions on the seond clause, using the alternate connection
   clauseAlternate2.set("SELECT count(*) FROM RSGV01"); 
   clauseAlternate2.prepareAndExecute();
   clauseAlternate2.next();
   clauseAlternate2.close();
   
   // alternateConnection.commitWithException();    // If we wanted to commit operations done on alternate connection
   // alternateConnection.rollbackWithException();    // Or roolback operations done on alternate connection
    
   alternateConnection.returnConnectionToPool(); // Return alternate connection to pool
      
   clause.close(); // Close 1st clause, 
   P2000Accessor.returnConnectionToPool(); // Return main connection to pool
   */

public class P2000Clause extends SQLClause
{
	/**
	 * Use this ctor to have the connection to DB automatically established. 
	 * The clause uses automatically the DB defined under the PUB2000Db section of 
	 * the app.properties file. This is defined by the accessor used (@see P2000Accessor.accessor)
	 */
	public P2000Clause()
	{
		super(P2000Accessor.accessor);	// Identifies the DB that we access
	}
	
	/**
	 * @param connection: Valid conection already established
	 * Use this ctor to create a new specific SQLClause, without automatic connection allocation.
	 * The TLS is not used in that case.
	 */
	public P2000Clause(DbAccessor accessor)
	{
		super(accessor);		
	}
	
	// CTor used to access DB with an aleternate connection (not managed in TLS)
	// Call with connection==null to establish 1st alternate connection
	// Call with connection filled to reused an already established alternate connection
	public P2000Clause(DbAccessor accessor, DbConnectionBase connection)
	{
		super(accessor, connection);		
	}
	
	
	/**
	 * Call this method to pass a languageId as a "Where" parameter of a SQLClause.  
	 * @param languageId Valid languageId identifying a language in the Pub2000 system
	 */
	public String param(LanguageId languageId)
	{
		return super.param(languageId.getNumericCode());		
	}

	/**
	 * @param csName: Column name
	 * @param languageId: Column value
	 * @return
	 */
	public SQLClause paramInsert(String csName, LanguageId languageId)
	{
		super.paramInsert(csName, languageId.getNumericCode());		
		return this;
	}
	

//	***************************************************************************
//	**                  MYPPUM handles booleans as char type.                **
//	***************************************************************************
/**
 * MYPPUM handles booleans as <code>char</code> type.
 * <ul>
 * 	<li><code>true</code> is encoded as <code>"1"</code>.</li>
 * 	<li><code>false</code> is encoded as <code>""</code>.</li>
 * </ul>
 * @param bVal A boolean value.
 * @return See {@link #param(String)}.
 */
	public String param(boolean bVal)
	{
		if (bVal)
			return super.param("1");
		else
			return super.param("");
	}

/**
 * MYPPUM handles booleans as <code>char</code> type.
 * <ul>
 * 	<li><code>true</code> is encoded as <code>"1"</code>.</li>
 * 	<li><code>false</code> is encoded as <code>""</code>.</li>
 * </ul>
 * @param bVal A boolean value.
 * @return See {@link #param(String)}.
 */
public P2000Clause paramInsert(String csName,boolean bVal)
{
	if (bVal)
		super.paramInsert(csName,"1");
	else
		super.paramInsert(csName,"");
	return this;
}
	
//	**************************************************************************
//	**                  Handles Date parameters in the MYPPUM database.     **
//	**************************************************************************
/**
 * Handles <code>Date</code> parameters in the P2000 database.
 * In the P2000 database, <code>Date</code> parameters are stored as integer
 * with the following format:
 * <pre>YYYYMMDD</pre>
 * @param bVal A <code>Date</code> parameter.
 * @return See {@link #param(int)}.
 */
	public String paramDate(Date bVal)
	{
		String s=String.format("%1$tY%1$tm%1$td",bVal);
		int n=Integer.parseInt(s.toString());
		return super.param(n);
	}
	
	public String paramDateNow()
	{
		String csNowYYYYMMDD = DateUtil.getDateNowYYYYMMDD();
		int nNowYYYYMMDD = NumberParser.getAsInt(csNowYYYYMMDD);
		return super.param(nNowYYYYMMDD);
	}


/**
 * Handles <code>Date</code> parameters in the P2000 database.
 * In the P2000 database, <code>Date</code> parameters are stored as integer
 * with the following format:
 * <pre>YYYYMMDD</pre>
 * @param bVal A <code>Date</code> parameter.
 * @return See {@link #param(int)}.
 */
	public P2000Clause paramInsertDate(String csName, Date bVal) 
	{
		String s=String.format("%1$tY%1$tm%1$td",bVal);
		int n=Integer.parseInt(s.toString());

		super.paramInsert(csName,n);
		return this;
	}

/**
 * Retrieves <code>Date</code> parameters from the P2000 database.
 * In the P2000 database, <code>Date</code> parameters are stored as integer
 * with the following format:
 * <pre>YYYYMMDD</pre>
 * @param bVal A <code>Date</code> parameter.
 * @return See {@link #param(int)}.
 */
	public Date getDate(String csColName) {
		int nVal = getInt(csColName);
		return transformToDate(nVal);
	}

/**
 * Retrieves <code>Date</code> parameters from the P2000 database.
 * In the P2000 database, <code>Date</code> parameters are stored as integer
 * with the following format:
 * <pre>YYYYMMDD</pre>
 * @param bVal A <code>Date</code> parameter.
 * @return See {@link #param(int)}.
 */
	public Date getDate(int nColNumber) { 
		int nVal = getInt(nColNumber);
		return transformToDate(nVal);
	}

	private Date transformToDate(int nVal) {
		if (nVal==0)
			return null;
		if (nVal>=99990000) {
			Calendar c=Calendar.getInstance();
			c.set(Calendar.YEAR,9999);
			c.set(Calendar.MONTH,11);
			c.set(Calendar.DAY_OF_MONTH,31);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE,0);
			c.set(Calendar.SECOND,0);
			c.set(Calendar.MILLISECOND,0);
			return c.getTime();
		}
		int nYear=nVal/10000;
		nVal-=nYear*10000;
		int nMonth=nVal/100;
		nVal-=nMonth*100;
		int nDay=nVal;
		Calendar c=Calendar.getInstance();
		c.set(Calendar.YEAR, nYear);
		c.set(Calendar.MONTH, nMonth-1);
		c.set(Calendar.DAY_OF_MONTH, nDay);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND,0);
		c.set(Calendar.MILLISECOND,0);
		return c.getTime();
	}

//	**************************************************************************
//	**                  Handles Time parameters in the MYPPUM database.     **
//	**************************************************************************
/**
 * Handles <code>Time</code> parameters in the MYPPUM database.
 * In the MYPPUM database, <code>Time</code> parameters are stored as integer
 * with the following format:
 * <pre>HHMMSS</pre>
 * @param bVal A <code>Date</code> parameter, where only the <code>Time</code>
 * is taken (see {@link #paramDate}).
 * @return See {@link #param(int)}.
 */
	public String paramTime(Date bVal)
	{
		String s=String.format("%1$tH%1$tM%1$tS",bVal);
		int n=Integer.parseInt(s.toString());
	
		return super.param(n);		
	}
	
	public String paramTimeNow()
	{
		String csNowHHMMSS = DateUtil.getTimeNowHHMMSS();
		int nNowHHMMSS = NumberParser.getAsInt(csNowHHMMSS);
		return super.param(nNowHHMMSS);
	}

/**
 * Handles <code>Time</code> parameters in the MYPPUM database.
 * In the MYPPUM database, <code>Time</code> parameters are stored as integer
 * with the following format:
 * <pre>HHMMSS</pre>
 * @param bVal A <code>Date</code> parameter, where only the <code>Time</code>
 * is taken (see {@link #paramDate}).
 * @return See {@link #param(int)}.
 */
	public P2000Clause paramInsertTime(String csName, Date bVal)
	{
		String s=String.format("%1$tH%1$tM%1$tS",bVal);
		int n=Integer.parseInt(s.toString());

		super.paramInsert(csName,n);
		return this;
	}
	
	/**
	 * Insert the current date in format YYYYMMDD as an int in the identified column
	 * @param csName Column's name; it must be typed as an int
	 * @return
	 */
	public P2000Clause paramInsertDateNow(String csName)
	{
		String csNowYYYYMMDD = DateUtil.getDateNowYYYYMMDD();
		int nNowYYYYMMDD = NumberParser.getAsInt(csNowYYYYMMDD);
		super.paramInsert(csName, nNowYYYYMMDD);
		return this;
	}
	
	/**
	 * Insert the current time in format HHMMSS as an int in the identified column
	 * @param csName Column's name; it must be typed as an int
	 * @return
	 */
	public P2000Clause paramInsertTimeNow(String csName)
	{
		String csNowHHMMSS = DateUtil.getTimeNowHHMMSS();
		int nNowHHMMSS = NumberParser.getAsInt(csNowHHMMSS);
		super.paramInsert(csName, nNowHHMMSS);
		return this;
	}

}
