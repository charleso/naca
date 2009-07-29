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
package jlib.jmxMBean;

import jlib.xml.Tag;

public class JMXDumperGui extends BaseCloseMBean
{
	public JMXDumperGui(String csOutputFile)
	{
		super("# JMX Dump","# JMX Dump");
		setOutputFile(csOutputFile);
	}
	
	protected void buildDynamicMBeanInfo() 
    {
		addOperation("Dump", getClass(), "setDump");
		addAttribute("OutputFile", getClass(), "OutputFile", String.class);
    }
	
	public void setDump()
	{
		JMXDumper dumper = new JMXDumper(JmxRegistration.getMBeanServer());
		Tag tag = new Tag("root");
		if(tag != null)
		{
			dumper.dumpAllMBeans(tag);
			tag.exportToFile(getOutputFile());
		}
	}
	
	public String getOutputFile()
	{
		return m_csOutputFile;
	}
	
	public void setOutputFile(String csOutputFile)
	{
		m_csOutputFile = csOutputFile;
	}
	
	private String m_csOutputFile = "./JMXOutput.txt";	
}
