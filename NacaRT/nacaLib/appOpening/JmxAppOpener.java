/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.appOpening;

import jlib.jmxMBean.BaseCloseMBean;
import nacaLib.basePrgEnv.BaseResourceManager;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class JmxAppOpener extends BaseCloseMBean
{
	public JmxAppOpener()
	{
		super("# App_Open", "# App_Open");
	}
	
	protected void buildDynamicMBeanInfo()
	{
		addOperation("Open", getClass(), "setOpen");
		addOperation("ReloadCalendarFiles", getClass(), "setReloadCalendarFiles");
		addAttribute("A_ApplicationManualStatus", getClass(), "A_ApplicationManualStatus", String.class);
		addAttribute("B0_ApplicationCustomStatus", getClass(), "B0_ApplicationCustomStatus", String.class);
		addAttribute("B1_ApplicationStandardStatus", getClass(), "B1_ApplicationStandardStatus", String.class);
		addAttribute("C_ApplicationCurrentStatus", getClass(), "C_ApplicationCurrentStatus", String.class);
	}

	public boolean getOpen()
	{
		return !BaseResourceManager.isAppManuallyClosed();
	}

	public void setOpen()
	{
		BaseResourceManager.setAppManuallyClosed(false);
	}	
	
	public void setReloadCalendarFiles()
	{
		BaseResourceManager.reloadCalendarFiles();
	}
	
	
	public String getApplicationStatus()
	{
		return getC_ApplicationCurrentStatus();
	}
	
	public String getA_ApplicationManualStatus()
	{
		return BaseResourceManager.getAppManualStatusState().getString();
	}

	public String getB0_ApplicationCustomStatus()
	{
		CalendarOpenState state = BaseResourceManager.getAppCustomOpenState();
		return state.getString();
	}
	
	public String getB1_ApplicationStandardStatus()
	{
		CalendarOpenState state = BaseResourceManager.getAppStandardOpenState();
		return state.getString();
	}
	
	public String getC_ApplicationCurrentStatus()
	{
		if(BaseResourceManager.isAppManuallyClosed())
			return getA_ApplicationManualStatus();
		return BaseResourceManager.getAppPlanifiedOpenState().getString();
	}

}

