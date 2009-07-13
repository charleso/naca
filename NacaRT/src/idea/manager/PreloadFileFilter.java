/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package idea.manager;

import java.io.File;
import java.io.FilenameFilter;

public class PreloadFileFilter implements FilenameFilter
{
	PreloadFileFilter()
	{
	}
	
	public boolean accept(File dir, String csName)
	{
		if(csName != null)
		{
			if(csName.indexOf('$') == -1)	// No $ in name 
			{
				String cs = csName.toUpperCase();			
				if(cs.endsWith(".CLASS"))
					return true;
			}
		}
		return false;		
	}
}
