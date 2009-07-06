/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.jmxMBean;

import java.lang.reflect.Method;

import javax.management.MBeanOperationInfo;

public class MBeanOperationInfoWrapper
{
	public MBeanOperationInfoWrapper(String csDescription, Method method)
	{
		m_operation = new MBeanOperationInfo(csDescription, method);
		m_method = method;
	}
	
	MBeanOperationInfo getOperation()
	{
		return m_operation;
	}
	
	Method getMethod()
	{
		return m_method;
	}
	
	private Method m_method = null;
	MBeanOperationInfo m_operation = null;

}
