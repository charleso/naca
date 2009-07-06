/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.base;

import jlib.jmxMBean.BaseCloseMBean;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: JmxAppEnabler.java,v 1.2 2006/05/04 08:05:02 cvsadmin Exp $
 */
public class JmxAppEnabler extends BaseCloseMBean
{
	private boolean ms_bClosed = false;
	
	public JmxAppEnabler()
	{
		super("# ApplicationEnabler", "# ApplicationEnabler");
	}
	
	protected void buildDynamicMBeanInfo()
	{
		addOperation("Close", getClass(), "setClose", boolean.class);
	}
	
	public boolean getClose()
	{
		return ms_bClosed;
	}

	public boolean getOpen()
	{
		return !ms_bClosed;
	}

	public void setClose(boolean b)
	{
		ms_bClosed = b;
		unregisterMBean();
		createMBean("# ApplicationEnabler", "# ApplicationEnabler");
	}
	
	public void setOpen(boolean b)
	{
		ms_bClosed = !b;
		unregisterMBean();
		createMBean("# ApplicationEnabler", "# ApplicationEnabler");
	}
}
