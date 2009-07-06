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
 * Notifies all programming exceptions detected during a processing.
 * A programming exception is any abnormal condition detected during a process.<p/>
 * 
 * As opposite to {@link TechnicalException}, a <i>programming error</i>, is communicating to the 
 * calling system that currently the requested operation cannot be achieved, and won't succeed
 * in the future until the application is corrected.<p/>
 * 
 * The  programming exception are raised as {@link RuntimeException} to avoid forcing the calling system
 * to explicitly deal with it.<p/>
 * 
 * The programming exception has the following properties:
 * <ul>
 * 	<li>An error code ({@link #getCode()}), used to classify the exceptions into categories.</li>
 * 	<li>An error message ({@link #getError()}), used to describe the error in human-understandable terms.</li>
 * </ul>
 * The default {@link #getMessage()} property returns a default concatenation
 * of <code>code</code> and <code>error</code>. 

 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: ProgrammingException.java,v 1.20 2008/06/30 13:16:41 u930lv Exp $
 */
public class ProgrammingException extends RuntimeException
{
	private static final long serialVersionUID = 6564135776272937738L;
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
	
	public ProgrammingException(String csError, String csMessage)
	{
		super(StringUtil.concatArgWithSeparator(csError,"-",csMessage));
		m_csError = csError;
		m_csMessage = csMessage;	
	}

	public ProgrammingException(String csError, SQLClause sqlClause, SQLException sqlException)
	{
		super(StringUtil.concatArgWithSeparator(csError,"-",sqlClause.toString()),sqlException);
		m_csError = csError;
		m_csMessage = sqlClause.toString();
	}

	
	public ProgrammingException(String csError, String csMessage, Exception exception)
	{
		super(StringUtil.concatArgWithSeparator(csError,"-",csMessage),exception);
		m_csError = csError;
		m_csMessage = csMessage;
	}


	public ProgrammingException(String csError, String csMessage, IOException eIO)
	{
		super(StringUtil.concatArgWithSeparator(csError,"-",csMessage),eIO);
		m_csError = csError;
		m_csMessage = csMessage;
	}
	
	public static void throwException(String csError, SQLClause sqlClause, SQLException sqlException)
	{
		throw new ProgrammingException(csError, sqlClause, sqlException);
	}
	
	public static void throwException(String csError, String csClause, SQLException sqlException)
	{
		throw new ProgrammingException(csError, csClause, sqlException);
	}
	
	public static void throwException(String csError, String csMessage, Exception exception)
	{
		throw new ProgrammingException(csError, csMessage, exception);
	}
	
	public static void throwException(String csError, String csMessage, IOException ioexception)
	{
		throw new ProgrammingException(csError, csMessage, ioexception);
	}

	
	public static void throwException(String csError, String csMessage)
	{
		throw new ProgrammingException(csError, csMessage);
	}
	
	public static void throwIfNull(Object o, String csError, String csMessage)
	{
		if(o == null)
			throw new ProgrammingException(csError, csMessage);
	}
	
	public static void throwIfNullOrEmpty(String csObject, String csError, String csMessage)
	{
		if(StringUtil.isEmpty(csObject))
			throw new ProgrammingException(csError, csMessage);
	}
	
	public static void throwIfNullOrInvalid(IValidable validable, String csError, String csMessage)
	{
		if(validable == null || !validable.isValid())
			throw new ProgrammingException(csError, csMessage);
	}

	
// *************************************************************************************
// * PROGRAMMING EXCEPTION CODES 	
// *************************************************************************************
	
	/* DATA TYPE */

	public static final String STRING_IS_NULL="Specified string is null";
	public static final String OBJECT_IS_NULL="Specified object is null";

	/* FTP */
	public static final String FTP_CONNECTION="FTP_CONNECTION";
	public static final String FTP_CHANGEDIRECTORY="FTP_CHANGEDIRECTORY";
	/* DB */
	
	public static final String NOT_SELECT_STMT = "Statement specified is not a select statement; should never happen; statement: ";
	public static final String DB_ERROR = "DB Error: ";
	public static final String DB_ERROR_CONNECTION_CREATION = "DB Error while creating DB Connection";
	public static final String DB_ERROR_DRIVER_CREATION = "CRITICAL ERROR: DB Connection ERROR: Cannot create DB Driver: ";
	public static final String DB_ERROR_RESULT_SET_COL_ACCESS_STRING = "Error during accessing a result set column as String; name: ";
	public static final String DB_ERROR_RESULT_SET_COL_ACCESS_INT = "Error during accessing a result set column as Int; name: ";
	public static final String DB_ERROR_RESULT_SET_COL_ACCESS_LONG = "Error during accessing a result set column as Long; name: ";
	public static final String DB_ERROR_RESULT_SET_COL_BIG_DECIMAL_ACCESS_INT = "Error during accessing a BigDecimal result set column as Int; name: ";
	public static final String DB_ERROR_RESULT_SET_COL_BIG_DECIMAL_ACCESS_STRING = "Error during accessing a BigDecimal result set column as String; name: ";
	public static final String DB_ERROR_PREPARE_STATEMENT = "DB Error during prepare statement: ";
	public static final String DB_ERROR_SELECT = "DB Error executing select: ";
	public static final String DB_ERROR_INSERT = "DB Error executing insert: ";
	public static final String DB_ERROR_UPDATE = "DB Error executing update: ";
	public static final String DB_ERROR_DELETE = "DB Error executing delete: ";
	public static final String SQL_PARSING_ERROR = "Could not correctly parse SQL Statement to add tablespace prefix; Please check statement: ";
	public static final String RESULTSET_NEXT_SQL_ERROR = "SQLException catche while calling next() in resultset; statement: "; 
	
	public static final String DB_ERROR_STOREDPROC = "Stored procedure error";
	public static final String DB_ERROR_STOREDPROC_PARAM_MANDATORY = "Stored procedure: Missing mandatory parameter ";
	
	/* FILE */
	
	public static final String IO_ERROR = "IO Error while accessing file: ";
	public static final String FILEUPLOAD_ERROR = "File upload Error";
	
	public static final String INVALID_FILENAME="Invalid file name.";
	
	/* CONFIGURATION / PROPERTY / PARAMETER */
	
	public static final String MISSING_CONFIGURATION = "Missing configuration ";
	public static final String MISSING_KEY_VALUE_IN_PROPERTY_FILE = "Missing Key value in property file; Key: ";
	public static final String MISSING_CONFIG_FILE = "Missing configuration file";
	
	public static final String INVALID_CONFIG_PARAMETER = "Invalid config parameter.";
	public static final String INVALID_PARAMETER_VALUE = "Invalid parameter value.";
	public static final String MISSING_PARAMETER = "Missing input parameter.";
	public static final String MISSING_REQUEST_PARAMETER = "Missing request parameter.";

	/* XML FORMAT / XSLT / DOM */
	
	public static final String XML_FORMAT_ERROR = "XML format error.";
	public static final String XML_DATA_POPULATED_ERROR = "XML data not populated error.";
	
	public static final String DOM_CREATION_ERROR = "DOM_CREATION_ERROR";
	public static final String DOM_READING_ERROR = "DOM_READING_ERROR";
	public static final String DOM_EMPTYFILE = "DOM_EMPTYFILE";
	
	public static final String INVALID_XML_FORMAT = "Invalid xml format.";
	
	public static final String XSL_COMPILATION_ERROR = "Error during XSL compilation";
	public static final String XSL_RUNTIME_ERROR = "Error during XSL execution.";

	/* ENUM FORMAT */
	
	public static final String INVALID_ENUM_DATA_TYPE="Invalid Enum data type.";
	public static final String INVALID_ENUM_VALUE="Invalid Enum value.";
	
	/* EXTENDED TRANSACTION */
	
	public static final String STORE_TRANSACTION_ERROR = "Store transaction error : ";
	public static final String RETRIEVE_TRANSACTION_ERROR = "Retrieve transaction error : ";
	public static final String DELETE_TRANSACTION_ERROR = "Delete transaction error : ";
			
	/* OTHERS */
	
	public static final String UNKNOWN = "Unexpected error";
	
	public static final String CONTEXT_IS_NULL = "Context parameter is null while accessing config file: ";
	
	public static final String DATE_FORMATTING_ERROR = "Date formatting error. ";
	public static final String DATE_PARSING_ERROR = "Date parsing error. ";
	public static final String INVALID_URL_FORMAT = "Url format error. ";

}
