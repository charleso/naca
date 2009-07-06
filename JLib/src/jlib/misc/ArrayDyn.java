/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.misc;

import java.util.ArrayList;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class ArrayDyn<T> extends ArrayFixDyn<T>
{
	ArrayList<T> m_arr = null;
	
//	public T[] getAsArray()
//	{
//		return (T[])m_arr.toArray();
//	}
	
	public int size()
	{
		if(m_arr != null)
			return m_arr.size();
		return 0;
	}
	
	public T get(int n)
	{
		if(m_arr != null)
			return m_arr.get(n);
		return null;
	}
	
	public void add(T t)
	{
		if(m_arr == null)
			m_arr = new ArrayList<T>();
		m_arr.add(t);
	}
	
	public void transferInto(T arr[])
	{
		int nSize = size();
		for(int n=0; n<nSize; n++)
		{
			T t = m_arr.get(n);
			arr[n] = t;				
		}
	}
	
	public boolean isDyn()
	{
		return true;
	}

	public void setSize(int n)
	{
	}

	public void set(int n, T t)
	{
		m_arr.set(n, t);
	}
}
