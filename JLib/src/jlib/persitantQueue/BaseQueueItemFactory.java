/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.persitantQueue;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: BaseQueueItemFactory.java,v 1.1 2007/02/06 17:56:36 u930di Exp $
 */
public abstract class BaseQueueItemFactory
{
	public abstract Object read(ObjectInputStream fileIn) throws ClassNotFoundException, IOException;
}
