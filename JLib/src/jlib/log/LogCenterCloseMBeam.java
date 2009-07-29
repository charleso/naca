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
package jlib.log;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeType;

import jlib.jmxMBean.BaseCloseMBean;


public abstract class LogCenterCloseMBeam extends BaseCloseMBean
{
	LogCenterCloseMBeam(String csName, String csDescription)
	{
		super(csName, csDescription);
	}
	
    protected void buildDynamicMBeanInfo() 
    {
    	addAttribute("Enable", getClass(), "Enable", Boolean.class);
    	addAttribute("Level", getClass(), "Level", String.class);
    	
    	addOperation("Set or reset Enable", getClass(), "setEnable", Boolean.class);	//Boolean.TYPE);
        addOperation("Set critical level", getClass(), "setCritical");
        addOperation("Set Important level", getClass(), "setImportant");
        addOperation("Set Normal level", getClass(), "setNormal");
        addOperation("Set Verbose level", getClass(), "setVerbose");
        addOperation("Set Debug level", getClass(), "setDebug");
        addOperation("Set Fine Debug level", getClass(), "setFineDebug");
		
//		MBeanParameterInfo[] params = null;
//		addOperation("Enable", "Enable logger", params, "void", MBeanOperationInfo.ACTION);
//      addOperation("Critical", "Critical level", params, "void", MBeanOperationInfo.ACTION);
//		addOperation("Important", "Important level", params, "void", MBeanOperationInfo.ACTION);
//		addOperation("Normal", "Normal level", params, "void", MBeanOperationInfo.ACTION);
//		addOperation("Verbose", "Verbose level", params, "void", MBeanOperationInfo.ACTION);
//		addOperation("Debug", "Debug level", params, "void", MBeanOperationInfo.ACTION);
//		addOperation("Fine Debug", "Fine Debug level", params, "void", MBeanOperationInfo.ACTION);
//
//        dNotifications[0] =
//            new MBeanNotificationInfo(
//            new String[] { AttributeChangeNotification.ATTRIBUTE_CHANGE },
//            AttributeChangeNotification.class.getName(),
//            "This notification is emitted when the reset() method is called.");

    }
    
	public abstract Boolean getEnable();
	public abstract void setEnable(Boolean b);

	public abstract String getLevel();
	public abstract void setLevel(String csLevel);
	
	public abstract void setCritical();
	public abstract void setImportant();
	public abstract void setNormal();
	public abstract void setVerbose();
	public abstract void setDebug();
	public abstract void setFineDebug();
	
	public abstract CompositeData getState();
	public abstract void setState(CompositeData data);
	public abstract CompositeType getStateType();
}
