/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/**
 * 
 */
package utils;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: BasePluginMarker.java,v 1.1 2007/06/28 06:19:46 u930bm Exp $
 */
public abstract class BasePluginMarker
{
	public abstract void debug(String csText);
	public abstract void info(String csText);
	public abstract void warn(String csText);
	public abstract void error(String csText);
	
}
