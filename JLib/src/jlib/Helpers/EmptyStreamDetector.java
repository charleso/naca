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
import java.io.InputStream;

public class EmptyStreamDetector extends InputStream {
	private boolean _firstWord=true;
	private InputStream _is;
	public EmptyStreamDetector(InputStream is) {
		_is=is;
	}
	@Override
	public int read() throws IOException {
		// TODO Auto-generated method stub
		int word=_is.read();
		if (word==-1)
			if (_firstWord)
				throw new EmptyStreamException("Stream is empty");
		_firstWord=false;
		return word;
	}
}
