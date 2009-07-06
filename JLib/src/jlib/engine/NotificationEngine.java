/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.engine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Hashtable;
import java.util.LinkedList;


public class NotificationEngine
{
	protected class NotifHandlerMapping
	{
		public BaseNotificationHandler m_Object ;
		public Method m_Mehod ;
	}
	public void RegisterNotificationHandler(BaseNotificationHandler handler)
	{ // build a Hashtable with all handlers for each notification class
		Class cl = handler.getClass() ;
		Method[] arrMet = cl.getMethods() ;
		for (Method m : arrMet)
		{
			Class[] params = m.getParameterTypes() ;
			Class ret = m.getReturnType() ;
			if (params.length == 1 && BaseNotification.class.isAssignableFrom(params[0]) && ret == boolean.class)
			{
				NotifHandlerMapping map = new NotifHandlerMapping();
				Class clNotif = params[0] ;
				map.m_Mehod = m ;
				map.m_Object = handler ;
				
				Collection<NotifHandlerMapping> colHandlers = m_tabHandlers.get(clNotif) ;
				if (colHandlers == null)
				{
					colHandlers = new LinkedList<NotifHandlerMapping>() ;
					m_tabHandlers.put(clNotif, colHandlers) ;
				}
				colHandlers.add(map) ;
			}
		}
	}
	
	public boolean SendNotification(BaseNotification notif)
	{
		boolean bDone = false ;

		// find all handlers for the notification class, and call for them
		Class clNotif = notif.getClass() ;
		Collection<NotifHandlerMapping> colHandler = m_tabHandlers.get(clNotif) ;
		if (colHandler != null)
		{
			for (NotifHandlerMapping map : colHandler)
			{
				try
				{
					Boolean b = (Boolean)map.m_Mehod.invoke(map.m_Object, new Object[] {notif}) ;
					bDone |= b ;
				}
				catch (IllegalArgumentException e)
				{
					e.printStackTrace();
				}
				catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
				catch (InvocationTargetException e)
				{
					e.printStackTrace();
				}
			}
		}
		return bDone ;
	}
	
	protected Hashtable<Class, Collection<NotifHandlerMapping>> m_tabHandlers = new Hashtable<Class, Collection<NotifHandlerMapping>>() ;

}
