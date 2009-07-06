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
 * @version $Id: ClassHelper.java,v 1.1 2006/11/09 14:46:17 u930di Exp $
 */
public class ClassHelper
{
	public static String getLocalName(Class cls)
	{		
		if(cls != null)
		{
			String cs = cls.getName();
			int n = cs.lastIndexOf(".");
			if(n != -1)
				cs = cs.substring(n);
			return cs;
		}
		return "";
	}
}
