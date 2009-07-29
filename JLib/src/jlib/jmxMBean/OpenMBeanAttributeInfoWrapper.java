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

import javax.management.openmbean.OpenMBeanAttributeInfo;
import javax.management.openmbean.OpenMBeanAttributeInfoSupport;

public class OpenMBeanAttributeInfoWrapper
{
	public OpenMBeanAttributeInfoWrapper(String csName, String csDescription, OpenMBeanAttributeInfoSupport openType, Method getter, Method setter)
	{
		m_openType = openType;
		m_getter = getter;
		m_setter = setter;
	}
	
	OpenMBeanAttributeInfo getAttribute()
	{
		return m_openType;
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
	private OpenMBeanAttributeInfoSupport m_openType = null;
}
