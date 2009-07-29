/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.basePrgEnv;

import java.util.Hashtable;

import jlib.classLoader.ClassDynLoaderFactory;
import jlib.classLoader.CodeManager;
import jlib.log.Log;
import jlib.xml.Tag;
import jlib.xml.TagCursor;

public class CallInterceptorManager
{
	// Class of CallInterceptor must be stateless, as they can be executed concurrently, and only 1 instance is ever loaded.
	private Tag m_tagCallInterceptor = null;
	private Hashtable<String, CallInterceptor> m_hashSsPrgInstanceByName = new Hashtable<String, CallInterceptor>();
	
	CallInterceptorManager(Tag tagCallInterceptor)
	{		
		m_tagCallInterceptor = tagCallInterceptor;
	}
	
	void init()
	{
		if(m_tagCallInterceptor == null)
			return ;
		
		TagCursor cur = new TagCursor(); 
		Tag tag = m_tagCallInterceptor.getFirstChild(cur);
		while(tag != null)
		{
			String csName = tag.getVal("Name");
			csName = csName.toUpperCase();
			String csStatelessClass = tag.getVal("StatelessClass");
			Object ssPrgSingleInstanceByName = CodeManager.getInstance(csStatelessClass, ClassDynLoaderFactory.getInstance());
			if(ssPrgSingleInstanceByName != null)
			{
				if(ssPrgSingleInstanceByName instanceof CallInterceptor)
					m_hashSsPrgInstanceByName.put(csName, (CallInterceptor)ssPrgSingleInstanceByName);
				else
					Log.logCritical("Could not load CallInterceptor class for sub-program doen't derives from CallInterceptor base class "+csName);
				
			}
			else
				Log.logCritical("Could not load CallInterceptor class for sub-program "+csName);
			tag = m_tagCallInterceptor.getNextChild(cur);
		}
		m_tagCallInterceptor = null;
	}
	
	public CallInterceptor getInterceptorClass(String csProgramName)
	{
		if(m_hashSsPrgInstanceByName == null)
			return null;
		
		String cs = csProgramName.toUpperCase();		
		return m_hashSsPrgInstanceByName.get(cs);
	}
}
