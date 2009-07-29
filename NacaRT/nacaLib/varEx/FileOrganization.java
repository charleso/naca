/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.varEx;

public class FileOrganization
{
	public static FileOrganization Sequential = new FileOrganization();
	public static FileOrganization Indexed = new FileOrganization();

	private FileOrganization()
	{		
	}
}
