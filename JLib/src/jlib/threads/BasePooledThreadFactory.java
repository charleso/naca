/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.threads;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: BasePooledThreadFactory.java,v 1.1 2006/11/29 07:35:36 u930di Exp $
 */
public abstract class BasePooledThreadFactory
{
	public abstract PooledThread make(PoolOfThreads owningPool); 
}
