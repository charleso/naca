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
package idea.view;

import java.util.EmptyStackException;
import java.util.Stack;

import idea.onlinePrgEnv.OnlineSession;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class XMLMergerManager
{
	public static synchronized XMLMerger get(OnlineSession appSession)
	{
		try
		{
			XMLMerger merger = ms_stack.pop();
			merger.set(appSession);
			return merger;
		}
		catch(EmptyStackException e)
		{
			return new XMLMerger(appSession);
		}
	}
	
	public static synchronized void release(XMLMerger merger)
	{
		merger.clear();
		ms_stack.push(merger);
	}
	
	private static Stack<XMLMerger> ms_stack = new Stack<XMLMerger>(); 
}
