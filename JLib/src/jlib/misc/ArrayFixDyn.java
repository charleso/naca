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
public abstract class ArrayFixDyn<T>
{
	//public abstract T[] getAsArray();
	public abstract int size();
	public abstract T get(int n);
	public abstract void add(T t);
	public abstract void transferInto(T arr[]);
	public abstract boolean isDyn();
	
	public abstract void setSize(int n);
	public abstract void set(int n, T t);
}
