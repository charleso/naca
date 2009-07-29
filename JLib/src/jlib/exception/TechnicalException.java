/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.exception;

import java.io.IOException;
import java.sql.SQLException;

import jlib.misc.StringUtil;
import jlib.sql.SQLClause;

/**
 * Notifies all technical exceptions detected during a processing.
 * Usually an application is connected to several other systems (databases, web services,
 * servers...). A <i>technical error</i> signals that one of those systems is malfunctioning.<p/>
 * 
 * A technical exception is by definition a temporary error. A technical exception is communicating 
 * to the calling system that currently the requested operation cannot be achieved, but the same 
 * operation may succeed later on. Some configuration errors (for example a bad database connection string),
 * lead to systematic technical errors. It is not possible to diagnose such misconfiguration as the 
 * configuration is not missing nor misformated.<p/>
 * 
 * The  technical exception are raised as {@link RuntimeException} to avoid forcing the calling system
 * to explicitly deal with it.<p/>
 * 
 * The technical exception has the following properties:
 * <ul>
 * 	<li>An error code ({@link #getCode()}), used to classify the exceptions into categories.</li>
 * 	<li>An error message ({@link #getError()}), used to describe the error in human-understandable terms.</li>
 * </ul>
 * The default {@link #getMessage()} property returns a default concatenation
 * of <code>code</code> and <code>error</code>. 
 * 
 * @author Pierre-Jean Ditscheid, Consultas SA
 */
public class TechnicalException extends RuntimeException
{
	private static final long serialVersionUID = -8558906700620059084L;
	private String m_csError = null;
	private String m_csMessage = null;

/**
 * Returns a description of the technical exception.
 * @return A description of the technical exception.
 */
	public String getError() {
		return m_csMessage;
	}

/**
 * Returns the error code.
 * @return The error code.
 */
	public String getCode() {
		return m_csError;
	}

	public TechnicalException(String csError, String csMessage)
	{
		super(StringUtil.concatArgWithSeparator(csError,"-",csMessage));
		m_csError = csError;
		m_csMessage = csMessage;
	}

	private TechnicalException(String csError, SQLClause sqlClause, SQLException sqlException)
	{
		super(StringUtil.concatArgWithSeparator(csError,"-",sqlClause.toString()),sqlException);
		m_csError = csError;
		m_csMessage = sqlClause.toString();
	}

	
	public TechnicalException(String csError, String csMessage, Exception exception)
	{
		super(StringUtil.concatArgWithSeparator(csError, "-", csMessage),exception);
		m_csError = csError;
		m_csMessage = csMessage;
	}


	private TechnicalException(String csError, String csMessage, IOException eIO)
	{
		super(StringUtil.concatArgWithSeparator(csError, "-", csMessage), eIO);
		m_csError = csError;
		m_csMessage = csMessage;
	}
	
	public void appendMessage(String cs)
	{	
		m_csMessage += cs;
	}
	
	public static void throwException(String csError, SQLClause sqlClause, SQLException sqlException)
	{
		throw new TechnicalException(csError, sqlClause, sqlException);
	}
	
	public static void throwException(String csError, String csClause, SQLException sqlException)
	{
		throw new TechnicalException(csError, csClause, sqlException);
	}
	
	public static void throwException(String csError, String csMessage, Exception exception)
	{
		throw new TechnicalException(csError, csMessage, exception);
	}
	
	public static void throwException(String csError, String csMessage, IOException ioexception)
	{
		throw new TechnicalException(csError, csMessage, ioexception);
	}

	
	public static void throwException(String csError, String csMessage)
	{
		throw new TechnicalException(csError, csMessage);
	}
	
	public static void throwIfNull(Object o, String csError, String csMessage)
	{
		if(o == null)
			throw new TechnicalException(csError, csMessage);
	}
	
	public static void throwIfNullOrEmpty(String csObject, String csError, String csMessage)
	{
		if(StringUtil.isEmpty(csObject))
			throw new TechnicalException(csError, csMessage);
	}
	
//	public static void throwIfNullOrInvalid(IValidable validable, String csError, String csMessage)
//	{
//		if(validable == null || !validable.isValid())
//			throw new TechnicalException(csError, csMessage);
//	}

	public static final String STORED_PROC_CALL_INOUT_PARAM_WAY_NOT_MATCHING_DEF = "A parameter in / inout / out definition does not match it's usage during stored procedure call parameters settings: ";
	public static final String STORED_PROC_CALL_INOUT_PARAM_SET_ERROR = "Could not set IN/OUT parameter to stored procededure: ";
	public static final String STORED_PROC_CALL_OUT_PARAM_SET_ERROR = "Could not set OUT parameter to stored procededure: ";
	public static final String STORED_PROC_CALL_IN_PARAM_SET_ERROR = "Could not set IN parameter to stored procededure: ";
	public static final String STORED_PROC_CALL_CLOSE_ERROR = "Error when closing called stored procededure: ";
	public static final String STORED_PROC_CALL_RETRIEVE_OUT_VALUES_ERROR = "Error when retrieve out value from called stored procededure; parameter number (1 based): ";
	public static final String STORED_PROC_CALL_EXECUTE_ERROR = "Could not execute prepared stored procedure call; statement: ";
	public static final String STORED_PROC_CALL_PREPARE_ERROR = "Could not prepare stored procedure call; statement: ";
	public static final String NOT_SELECT_STMT = "Statement specified is not a select statement; should never happen; statement: ";
	public static final String DB_ERROR = "DB Error: ";
	public static final String DB_ERROR_CONNECTION_CREATION = "DB Error while creating DB Connection";
	public static final String DB_ERROR_DRIVER_CREATION = "DB_ERROR_DRIVER_CREATION";
	public static final String DB_ERROR_RESULT_SET_COL_ACCESS_STRING = "Error during accessing a result set column as String; name: ";
	public static final String DB_ERROR_RESULT_SET_COL_ACCESS_INT = "Error during accessing a result set column as Int; name: ";
	public static final String DB_ERROR_PREPARE_STATEMENT = "DB Error during prepare statement: ";
	public static final String DB_ERROR_SELECT = "DB Error executing select: ";
	public static final String DB_ERROR_INSERT = "DB Error executing insert: ";
	public static final String DB_ERROR_UPDATE = "DB Error executing update: ";
	public static final String DB_ERROR_DELETE = "DB Error executing delete: ";
	public static final String MISSING_CONFIGURATION = "Missing configuration ";
	public static final String MISSING_KEY_VALUE_IN_PROPERTY_FILE = "Missing Key value in property file.";
	public static final String SQL_PARSING_ERROR = "Could not correctly parse SQL Statement to add tablespace prefix; Please check statement: ";
	public static final String RESULTSET_NEXT_SQL_ERROR = "SQLException catche while calling next() in resultset; statement: "; 
	
	public static final String DB_ERROR_STOREDPROC = "Stored procedure error";
	public static final String DB_ERROR_STOREDPROC_PARAM_MANDATORY = "Stored procedure: Missing mandatory parameter ";
	
	public static final String IO_ERROR = "IO_ERROR";
	public static final String WEBSERVICENOTRESPONDING="WEBSERVICENOTRESPONDING";
	public static final String HOSTUNKNOWN="HOSTUNKNOWN";
	
	public static final String MISSINGE_CONFIG_FILE = "Missing configuration file";
	public static final String CONTEXT_IS_NULL = "Context parameter is null while accessing config file: ";

}
