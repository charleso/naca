/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.sql;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class ConnectionGenerationManager
{
	private static AtomicInteger ms_nId = new AtomicInteger(0);
	
	static void incCurrentGenerationId()
	{
		ms_nId.incrementAndGet();
	}
	
	static boolean isGenerationCurrent(int nGenerationId)
	{
		if(ms_nId.get() == nGenerationId)
			return true;
		return false;
	}
	
	static int getGenerationId()
	{
		return ms_nId.get();
	}
}
