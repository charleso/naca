/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.exceptions;

import jlib.misc.LogicalFileDescriptor;

public class CannotOpenFileException extends NacaBatchFileException
{
	private static final long serialVersionUID = 1L;
	
	public CannotOpenFileException(String csFileName, LogicalFileDescriptor logicalFileDescriptor)
	{
		super("CannotOpenFileException", csFileName, logicalFileDescriptor);
	}
}
