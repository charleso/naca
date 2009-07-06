/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.log;

public abstract class BasePluginMarker
{
	public abstract void info(String csText);
	public abstract void warn(String csText);
	public abstract void error(String csText);
}

