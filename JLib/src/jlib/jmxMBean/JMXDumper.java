/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.jmxMBean;

import java.util.*;
import javax.management.*;

import jlib.xml.Tag;

public class JMXDumper
{
	private MBeanServer m_server = null;
	
	JMXDumper(MBeanServer server)
	{
		m_server = server;
	}
	
	void dumpAllMBeans(Tag tagOut)
	{
		ObjectName oName = null;
		try
		{
			oName = new ObjectName("jmxMbean:*");
			Set names = m_server.queryNames(oName, null);
			
			Iterator itr = names.iterator();
			while(itr.hasNext()) 
			{
				ObjectName name = (ObjectName)itr.next();
				dumpMBean(tagOut, name);
			}
		}
		catch (MalformedObjectNameException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void dumpMBean(Tag tagOut, ObjectName name)
	{
		String csName = name.toString();
		Tag tagChild = tagOut.addTag("MBean");
		tagChild.addVal("Name", csName);
		Tag tagAttributes = tagChild.addTag("Attributes");
		
		MBeanInfo info = getMBeanInfo(name);
		if(info != null)
		{
			MBeanAttributeInfo tAttr[] = info.getAttributes();
			if(tAttr != null)
			{
				for(int n=0; n<tAttr.length; n++)
				{
					MBeanAttributeInfo attr = tAttr[n];
										
					String csAttributeName = attr.getName();
					String csValue = getAttributeValue(name, csAttributeName);
					
					Tag tagAttribute = tagAttributes.addTag("Attribute");
					tagAttribute.addVal("Name", csAttributeName);
					tagAttribute.addVal("Value", csValue);
				}
			}
		}
	}
		
	private MBeanInfo getMBeanInfo(ObjectName name)
	{		
		try
		{
			MBeanInfo info;
			info = m_server.getMBeanInfo(name);
			return info;
		}
		catch (InstanceNotFoundException e)
		{
		}
		catch (IntrospectionException e)
		{
		}
		catch (ReflectionException e)
		{
		}
		return null;
	}
	
	private String getAttributeValue(ObjectName name, String csAttributeName)
	{
		try
		{
			Object oValue = m_server.getAttribute(name, csAttributeName);
			if(oValue != null)
				return oValue.toString();
		}
		catch (AttributeNotFoundException e)
		{
		}
		catch (InstanceNotFoundException e)
		{
		}
		catch (MBeanException e)
		{
		}
		catch (ReflectionException e)
		{
		}
		return "";
	}
}
