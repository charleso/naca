/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
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
