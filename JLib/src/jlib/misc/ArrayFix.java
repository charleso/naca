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

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class ArrayFix<T> extends ArrayFixDyn<T>
{
	T m_arr[] = null;
	
	public ArrayFix(T arr[])
	{
		m_arr = arr;
	}
	
	public int size()
	{
		if(m_arr != null)
			return m_arr.length;
		return 0;
	}
	
	public T get(int n)
	{
		//if(m_arr != null)
			return m_arr[n];
		//return null;
	}
	
	public void add(T t)
	{
		//assertIfFalse(false);
	}
	
//	public T[] getAsArray()
//	{
//		return m_arr;
//	}

	
	public void transferInto(T[] arr)
	{
		m_arr = arr;		
	}
	
	public boolean isDyn()
	{
		return false;
	}
	
	public void setSize(int n)
	{
	}

	public void set(int n, T t)
	{
		m_arr[n] = t;
	}
}
