/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.exceptions;

import jlib.misc.LogicalFileDescriptor;

public class InputFileNotFoundException extends NacaBatchFileException
{
	private static final long serialVersionUID = 1L;
	
	public InputFileNotFoundException(String csFileName, LogicalFileDescriptor logicalFileDescriptor)
	{
		super("InputFileNotFoundException", csFileName, logicalFileDescriptor);
	}
}
