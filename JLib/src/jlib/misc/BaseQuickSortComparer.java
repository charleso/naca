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
 * @version $Id: BaseQuickSortComparer.java,v 1.1 2008/07/07 11:26:29 u930di Exp $
 */
public abstract class BaseQuickSortComparer<Item>
{
	public abstract int compare(Item i1, Item i2);
}
