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
public class FixedArray
{
	public static String[] getStringArray(int nSize)
	{
		String t[] = new String [nSize];
		for(int n=0; n<nSize; n++)
		{
			t[n] = new String(""); 
		}
		return t;
	}
	
	public static Integer[] getIntArray(int nSize)
	{
		Integer t[] = new Integer[nSize];
		for(int n=0; n<nSize; n++)
		{
			t[n] = 0; 
		}
		return t;
	}
	
	public static Short[] getShortArray(int nSize)
	{
		Short t[] = new Short[nSize];
		for(int n=0; n<nSize; n++)
		{
			t[n] = (short)0; 
		}
		return t;
	}
	
	public static Long[] getLongArray(int nSize)
	{
		Long t[] = new Long[nSize];
		for(int n=0; n<nSize; n++)
		{
			t[n] = 0L; 
		}
		return t;
	}
	
	public static Double[] getDoubleArray(int nSize)
	{
		Double t[] = new Double[nSize];
		for(int n=0; n<nSize; n++)
		{
			t[n] = 0.0; 
		}
		return t;
	}
	
	public static Boolean[] getBooleanArray(int nSize)
	{
		Boolean t[] = new Boolean[nSize];
		for(int n=0; n<nSize; n++)
		{
			t[n] = false; 
		}
		return t;
	}
}
