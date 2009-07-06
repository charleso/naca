/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.jmxMBean;

import java.lang.reflect.Method;

public class MethodFinder
{
	public static Method getMethod(Class cls, String csMethodName)
	{
        try
		{
        	Class clsParamTypes[] = null;
        	Method method = cls.getMethod(csMethodName, clsParamTypes);
        	return method;
		}
        catch (SecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        catch (NoSuchMethodException e)
		{
        	// Do nothing
		}
		return null;
	}
	
	public static Method getMethod(Class cls, String csMethodName, Class clsArg0)
	{
        try
		{
        	Class clsParamTypes[] = new Class[1];
        	clsParamTypes[0] = clsArg0;
        	Method method = cls.getMethod(csMethodName, clsParamTypes);
        	return method;
		}
        catch (SecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        catch (NoSuchMethodException e)
		{
			// Do nothing
		}
		return null;
	}
	
	public static Method getMethod(Class cls, String csMethodName, Class clsArg0, Class clsArg1)
	{
        try
		{
        	Class clsParamTypes[] = new Class[2];
        	clsParamTypes[0] = clsArg0;
        	clsParamTypes[1] = clsArg1;
        	Method method = cls.getMethod(csMethodName, clsParamTypes);
        	return method;
		}
        catch (SecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        catch (NoSuchMethodException e)
		{
			// Do nothing
		}
		return null;
	}
		
	public static Method getMethod(Class cls, String csMethodName, Class clsArg0, Class clsArg1, Class clsArg2)
	{
        try
		{
        	Class clsParamTypes[] = new Class[3];
        	clsParamTypes[0] = clsArg0;
        	clsParamTypes[1] = clsArg1;
        	clsParamTypes[2] = clsArg2;
        	Method method = cls.getMethod(csMethodName, clsParamTypes);
        	return method;
		}
        catch (SecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        catch (NoSuchMethodException e)
		{
			// Do nothing
		}
		return null;
	}
	
	public static Method getMethod(Class cls, String csMethodName, Class clsArg0, Class clsArg1, Class clsArg2, Class clsArg3)
	{
        try
		{
        	Class clsParamTypes[] = new Class[4];
        	clsParamTypes[0] = clsArg0;
        	clsParamTypes[1] = clsArg1;
        	clsParamTypes[2] = clsArg2;
        	clsParamTypes[3] = clsArg3;
        	Method method = cls.getMethod(csMethodName, clsParamTypes);
        	return method;
		}
        catch (SecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        catch (NoSuchMethodException e)
		{
			// Do nothing
		}
		return null;
	}


}
