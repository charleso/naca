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
package jlib.jmxMBean;

import java.lang.reflect.Method;

import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;

public class MBeanAttributeInfoWrapper
{	
	public MBeanAttributeInfoWrapper(String csName, String csDescription, Method getter, Method setter)
	{
		try
		{
			m_attribute = new MBeanAttributeInfo(csName, csDescription, getter, setter);
		} 
		catch (IntrospectionException e)
		{
			e.printStackTrace();
		}
		m_getter = getter;
		m_setter = setter;
	}
	
	MBeanAttributeInfo getAttribute()
	{
		return m_attribute;
	}
	
	Method getMethodGetter()
	{
		return m_getter;
	}
	
	Method getMethodSetter()
	{
		return m_setter;
	}
	
	private Method m_getter = null;
	private Method m_setter = null;
	private MBeanAttributeInfo m_attribute = null;
}
