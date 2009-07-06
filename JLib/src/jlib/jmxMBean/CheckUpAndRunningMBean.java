/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.jmxMBean;

public interface CheckUpAndRunningMBean
{
	boolean isUp();
	
	int getNbCheckUp();
	
	public boolean getInc();
	public void setInc(boolean b);
}

