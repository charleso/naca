/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.sqlMapper;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: OrderSegmentAscending.java,v 1.1 2007/12/04 14:00:23 u930di Exp $
 */
public class OrderSegmentAscending extends OrderSegment
{
	public OrderSegmentAscending(String csColName)
	{
		super(csColName, true);
	}
}
