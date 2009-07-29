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

import java.lang.management.*;
import javax.management.*;


public class JmxRegistration
{
	static public void register()
	{
		if(ms_MBeanServer == null)
		{
			ms_MBeanServer = ManagementFactory.getPlatformMBeanServer();
		}
	}
	
	static public MBeanServer getMBeanServer()
	{
		return ms_MBeanServer;
	}
	

	static public boolean unregisterMBean(String csName)
	{
		register();
		
		try
		{
			ObjectName name = new ObjectName("jmxMbean:type="+csName);
			ms_MBeanServer.unregisterMBean(name);
		} 
		catch (MalformedObjectNameException e)
		{
			e.printStackTrace();
			return false;
		} 
		catch (NullPointerException e)
		{
			e.printStackTrace();
			return false;
		} 
		catch (InstanceNotFoundException e)
		{
			e.printStackTrace();
			return false;
		} 
		catch (MBeanRegistrationException e)
		{
			e.printStackTrace();
			return false;
		}		
		return true;
	}
	
	static public boolean registerMBean(String csName, Object MBeanObject)
	{
		register();
		
		try
		{
			ObjectName name = new ObjectName("jmxMbean:type="+csName);
			ms_MBeanServer.registerMBean(MBeanObject, name);
			
		}
		catch (InstanceAlreadyExistsException e)
		{
			return false;
		} 
		catch (MBeanRegistrationException e)
		{
			return false;
		} 
		catch (NotCompliantMBeanException e)
		{
			return false;
		} 
		catch (MalformedObjectNameException e)
		{
			e.printStackTrace();
			return false;
		} 
		catch (NullPointerException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private static MBeanServer ms_MBeanServer = null;
}