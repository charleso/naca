/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.classLoader;

import java.util.ArrayList;

public class CoupleCodeLoader
{
	public CoupleCodeLoader(Class classCode, ClassDynLoader classDynLoader)
	{
		m_classCode = classCode;
		m_classDynLoader = classDynLoader;
	}
	
	public Class getClassCode()
	{
		return m_classCode;
	}

	void addInstance(Object obj)
	{
		if(m_arrInstances == null)
			m_arrInstances = new ArrayList<Object>();
		m_arrInstances.add(obj);
	}

	
//	Object makeNewInstance()
//	{
//		Object obj = null;
//		try
//		{
//			m_classDynLoader.inMakeNewInstance();
//			obj = m_classCode.newInstance();
//			if(obj != null)
//			{
//				if(m_arrInstances == null)
//					m_arrInstances = new ArrayList<Object>();
//				m_arrInstances.add(obj);
//			}
//			m_classDynLoader.outMakeNewInstance();
//			return obj;
//		}
//		catch (InstantiationException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		catch (IllegalAccessException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;		
//	}
	
	void removeAllInstances()
	{
		if(m_arrInstances != null)
		{
//			int nNbinstances = getNbInstances();
//			for(int nInstance=0; nInstance<nNbinstances; nInstance++)
//			{
//				Object obj = getInstance(nInstance);
//				// remove all copy parented by obj 
//			}
			m_arrInstances.clear();
			m_arrInstances = null;
		}
		m_classCode = null;
		m_classDynLoader = null;
	}
	
	int getNbInstances()
	{
		if(m_arrInstances != null)
		{
			return m_arrInstances.size();
		}
		return 0;
	}
	
	Object getInstance(int n)
	{
		if(m_arrInstances != null)
		{
			return m_arrInstances.get(n);
		}
		return null;
	}
	
	

	private Class m_classCode = null;
	private ClassDynLoader m_classDynLoader = null;	// Holds a ref; do not delete 
	private ArrayList<Object> m_arrInstances = null;	
}
