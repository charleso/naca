/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.bdb;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class MultiThreadedSortAddItemCache
{
	MultiThreadedSortAddItemCache()
	{
		m_stk = new Stack<MultiThreadedSortAddItem>(); 
	}
	
	MultiThreadedSortAddItem getUsusedItem()
	{	
		try
		{
			MultiThreadedSortAddItem item = m_stk.pop();
			return item;			
		}
		catch (EmptyStackException e) 
		{
		}
		return null;
	}
	
	void disposeItemForReuse(MultiThreadedSortAddItem item)
	{
		m_stk.push(item);
	}
	
	private Stack<MultiThreadedSortAddItem> m_stk = null;
}
