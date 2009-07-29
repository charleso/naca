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
package jlib.Helpers;

import java.io.BufferedInputStream;
import java.io.InputStream;

public class ApplicationHelper {
	
//***********************************************************************************
//**                Shows a text file in the console.                              **
//***********************************************************************************
/**
 * Shows a text file in the console.
 * This method is typically used from <i>main</i> methods of pre-import modules
 * to show readme files, or help files.
 * <pre>
 *	 	
 * </pre>
 */
	public static void showStreamOnConsole(InputStream is) throws Exception {
		try {
			if (is==null)
				throw new Exception("No text specified.");
			else {
				int c;
				BufferedInputStream bis=new BufferedInputStream(is);
				while((c=bis.read())>=0)
					System.out.print((char)c);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
}
