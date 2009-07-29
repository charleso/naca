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

/**
 * 
 * @author U930DAN
 * Cette classe permet de supprimer dans un fichier tout ce qui
 * est contenu entre les tags < et >
 * par exemple si on soumet le contenu suivant:
 * <?xml version="1.0" encoding="ISO-8859-1"?>
 * <pic_1 FileName="t0014395.jpg">Hello</pic_1>
 * en output on obtiendra:
 * Hello
 */
public class TagRemoverInputStream extends InputStream {
	
//	*************************************************************************
//	**                      The class constructor.                         **
//	*************************************************************************
/**
 * The constructor admits any <code>InputStream</code> source.
 */
	public TagRemoverInputStream(InputStream inputStream) {
		_inputStream=inputStream;
	}

//	*************************************************************************
//	**                         Class properties.                           **
//	*************************************************************************

//	***************************** The data input ****************************
/**
 * Contains the data input source.
 */
	private InputStream _inputStream;

//	*************************************************************************
//	**                The actual data transformation                       **
//	*************************************************************************
/**
 * Remove xml tag from the provided input source (see {@link #TagRemoverInputStream(InputStream)}
 */
	@Override
	public int read() throws IOException {
		int data=0;

//	................ Reads the source .....................
			data=_inputStream.read();
			while(data>0) {
			    // if source contains certains caracters then we don't write it.
				//10= code ascii de saut de ligne
				//13=code ascii de retour a la ligne
				if (data=='<' || data==10 || data==13) {
					while (data != '>' && data != '\r' && data != '\n' && data!='\t'){
						data=_inputStream.read();
					}
				}
				else 
					return data;
				data=_inputStream.read();
				}

		return -1;

	}
}
