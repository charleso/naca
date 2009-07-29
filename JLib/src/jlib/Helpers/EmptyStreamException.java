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

import java.io.IOException;

public class EmptyStreamException extends IOException {

	private static final long serialVersionUID = 1907789442213041432L;

	public EmptyStreamException() {
		super();
	}

	public EmptyStreamException(String message) {
		super(message);
	}
}

