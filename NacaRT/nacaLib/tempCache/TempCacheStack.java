/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.tempCache;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class TempCacheStack
{
	synchronized static void push(TempCache cache)
	{
		ms_stCacheManager.push(cache);
	}
	
	synchronized static TempCache pop()
	{
		try
		{
			TempCache cache = ms_stCacheManager.pop();
			return cache;
		}
		catch(EmptyStackException e)
		{
			return null;
		}
	}
	
	private static Stack<TempCache> ms_stCacheManager = new Stack<TempCache>();
}
