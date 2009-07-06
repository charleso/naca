/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.log;

import junit.framework.AssertionFailedError;

public class AssertException extends AssertionFailedError
{
	private static final long serialVersionUID = 1L;

	public AssertException()
	{
	}
	
	public AssertException(String arg0)
	{
		super(arg0);
	}
}
