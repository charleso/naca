/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.Helpers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import jlib.exception.TechnicalException;
import jlib.jmxMBean.JmxClassPath;
import jlib.misc.StringUtil;

public class PropertyLoader
{
//**********************************************************************************
//**                        Class properties                                      **
//**********************************************************************************
/**
 * The properties file content.
 */
	protected static Properties ms_properties=null;
	
	public String getProperty(String csName)
		throws TechnicalException 
	{
		return getPropertyWithContext(this, csName);
	}

	public String getProperty(String csName, String csDefaultValue)
	{
		return getPropertyWithContext(this, csName, csDefaultValue);
	}
	
	public static String getPropertyWithContext(Object context, String csName)
		throws TechnicalException 
	{
		try
		{
			Properties p = getProperties(context);
			if(p != null)
			{
				String csValue = p.getProperty(csName);
				if(csValue == null)
					TechnicalException.throwException(TechnicalException.MISSING_KEY_VALUE_IN_PROPERTY_FILE, csName);
				return csValue;
			}
		}
		catch(TechnicalException e)
		{
			throw e;
		}
		return null;
	}
	
	public static String getPropertyWithContext(Object context, String csName, String csDefaultValue)
	{
		try
		{
			Properties p = getProperties(context);
			if(p != null)
			{
				String csValue = p.getProperty(csName);
				if(csValue != null)
					return csValue;
			}
		}
		catch(TechnicalException e)
		{
			// Do nothing, we returns the default value
		}
		return csDefaultValue;
	}
	
//**********************************************************************************
//**                    Returns the URL to the properties file.                   **
//**********************************************************************************
/**
 * Returns the URL to the properties file.
 * This method is normally used internally by the other methods of the class.
 * The file has to be in the standard {@link Properties} format.
 * @param context A pointer to the class requesting the properties file. If the
 * parameter is left to null, the context will be the {@link PropertyLoader} instance
 * itself.
 * @return The content of the properties file.
 */
	private static Properties getProperties(Object context) 
		throws TechnicalException 
	{
		return getProperties(context, null);
	}
	
	
//	public Properties getProperties() 
//		throws TechnicalException 
//	{
//		return getProperties(this);
//	}
/**
 * Returns an {@link Properties} instance initialized with the content
 * of the specified properties file.
 * The file has to be in the standard {@link Properties} format.
 * @param context A pointer to the class requesting the properties file.
 * @param propertiesFileName The properties file name. If left empty, the default
 * filename is "app.properties".
 * @return The content of the properties file.
 */
	public static Properties getProperties(Object context, String propertiesFileName) 
		throws TechnicalException 
	{
//********************** If the properties are not already loaded *******************
		if (ms_properties==null)
		{
//........................ When no properties file name is specified ................
			if (StringUtil.isEmpty(propertiesFileName))
				propertiesFileName = "app.properties";

//..................... Searches for the properties file ...........................
			InputStream is=null;
			
			TechnicalException.throwIfNull(context, TechnicalException.CONTEXT_IS_NULL, propertiesFileName);

// When context is known, starts searching in the class resources collection.
			is = context.getClass().getResourceAsStream("/"+propertiesFileName);
			if (is == null)
				is = context.getClass().getResourceAsStream(propertiesFileName);
			
// If context is not known, or nothing has been found in the class resources collection:
			if (is == null)
				is = context.getClass().getClassLoader().getResourceAsStream("/"+propertiesFileName);
			if (is == null)
				is = context.getClass().getClassLoader().getResourceAsStream(propertiesFileName);

// Raises an exception is the file could not be found:
			
			if(is == null)
			{
				String cs = "Property file " + propertiesFileName + " Not found in " + JmxClassPath.getAllPaths();			
				TechnicalException.throwIfNull(is, TechnicalException.MISSINGE_CONFIG_FILE, cs);
			}

//.................... Loads the properties file ...................................
			ms_properties=new Properties();
			try
			{
				ms_properties.load(is);
			}
			catch (IOException e)
			{
				TechnicalException.throwException(TechnicalException.IO_ERROR, propertiesFileName, e);
			}

		}
		return ms_properties;
	}
/**
 * Returns a <code>InputStream</code> initialized on the specified properties file.
 * This method (in opposite to {@link #getProperties()}, allows to use any format for
 * the properties file.
 * @param context A pointer to the class requesting the properties file.
 * @param propertiesFileName The properties file name.
 * @return An initialized <code>InputStream</code> from which the content of the 
 * properties file can be retrieved.
 * @throws FileNotFoundException If the specified properties file is not found in the
 * application's class path.
 */	
	public static InputStream getInputStream(Object context, String propertiesFileName) 
		throws TechnicalException 
	{
//..................... Searches for the properties file ...........................
		InputStream is=null;
		if (context == null)
			throw new RuntimeException("'context' parameter must not be null.");

// When context is known, starts searching in the class resources collection.
		is = context.getClass().getResourceAsStream("/"+propertiesFileName);
		if (is == null)
			is = context.getClass().getResourceAsStream(propertiesFileName);

// If context is not known, or nothing has been found in the class resources collection:
		if (is == null)
			is = context.getClass().getClassLoader().getResourceAsStream("/"+propertiesFileName);
		if (is == null)
			is = context.getClass().getClassLoader().getResourceAsStream(propertiesFileName);

// Raises an exception is the file could not be found:
		TechnicalException.throwIfNull(is, TechnicalException.MISSINGE_CONFIG_FILE, propertiesFileName);
		return is;
	}
}
