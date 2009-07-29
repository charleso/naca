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
package jlib.misc;

import java.io.File;
import java.io.FilenameFilter;

public class FileFilterByPrefix implements FilenameFilter
{
	private String m_csPrefix = null;
	
	public FileFilterByPrefix(String csPrefix)
	{
		m_csPrefix = csPrefix.toUpperCase();
	}
	
	public boolean accept(File dir, String csName)
	{
		if(m_csPrefix != null && csName != null)
		{
			String cs = csName.toUpperCase();
			if(cs.startsWith(m_csPrefix))
				return true;
		}
		return false;
	}
}
