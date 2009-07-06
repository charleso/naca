/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.jmxMBean;

public class CheckUpAndRunning implements CheckUpAndRunningMBean
{
	public boolean isUp()
	{
		if(m_bInc)	
			m_nbUp++;
		else
			m_nbUp--;
		return true;
	}
	
	public int getNbCheckUp()
	{
		return m_nbUp;
	}

	public boolean getInc()
	{
		return m_bInc;
	}
	
	public void setInc(boolean b)
	{
		m_bInc = b;
	}

	private int m_nbUp = 0;
	private boolean m_bInc = true;
}


