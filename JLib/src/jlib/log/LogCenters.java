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

import java.util.ArrayList;

import jlib.xml.Tag;

/**
 * Class containing a collection of {@link LogCenterLoader} instances, and populating
 * it based on the content [Organisation] tag in a JLib logger configuration file.
 * <i>LogCenterLoader</i> instances can be individually accessed through
 * the {@link #getLogCenterloader} method. One <i>LogCenterLoaded</i> instance
 * is created for each [LogCenter] tag present in the [Organisation] tag
 * provided to the class constructor.<p/>
 * Each <i>LogCenterLoader</i> will instantiate a new {@link LogCenter} object.
 * Those objects will be registered to the {@link Log} singleton, and will eventually
 * change their configuration. The new configuration can be retrieve in the form
 * of a {@link Tag} instance through {@link #saveDefinition}.
 * 
 * @author PJD
 *
 */
public class LogCenters
{
	public LogCenters()
	{
	}
/**
 * Reads the [Organisation] tag of a JLib.log configuration file and instantiates
 * one {@link LogCenterLoader} for each [LogCenter] tag in it. The <i>LogCenterLoader</i>
 * reads the [LogCenter] tag and builds a new {@link LogCenter} instance with it,
 * and then registers the new instance to the {@link Log} singleton.<p/>
 * 
 * The general effect of calling this method is that all log centers described in
 * the specified configuration are registered to the {@link Log} singleton.
 *  
 * @param csChannelRestriction If not <i>null</i>, the method will only load
 * [LogCenter] belonging to the specified channel.
 * @param tagOrganisation The [Organisation] tag to be read (comming from a JLib.log
 * xml configuration file loaded through a {@link Tag} class).
 * @param arrIncludePath If additional [LogCenter] can be found in external
 * files, a "File" attribute is placed in the [LogCenters] tag. This attribute
 * contains the path to another xml file. The external file will be searched
 * in the list of path specified in <b>arrIncludePath</b>.
 * @return <i>true</i> if the [Organisation] tag has been correctly read.
 */
	public boolean loadDefinition(String csChannelRestriction, Tag tagOrganisation, ArrayList<String> arrIncludePath)
	{
		Tag tagLogCenters = tagOrganisation.getEnumChild("LogCenters");
		if(tagLogCenters != null)
		{
			Tag tagLogCenter = tagLogCenters.getEnumChild("LogCenter");
			while(tagLogCenter != null)
			{
				String csChannel = tagLogCenter.getVal("Channel");
				if(csChannelRestriction == null || csChannel.equalsIgnoreCase(csChannelRestriction))
				{
					LogCenterLoader logCenterloader = new LogCenterLoader();
					logCenterloader.loadDefinition(tagLogCenter);
					
					if(m_arrLogCenterloader == null)
						m_arrLogCenterloader = new ArrayList<LogCenterLoader>();
					m_arrLogCenterloader.add(logCenterloader);
				}
				
				tagLogCenter = tagLogCenters.getEnumChild();
			}
			
			// Maybe a file is indicated
			String csFileLogIni = tagLogCenters.getVal("File");
			if(csFileLogIni != null && csFileLogIni.length() != 0)
			{
				Tag tagLogIni = Tag.createFromFile(csFileLogIni, arrIncludePath);
				if(tagLogIni != null)
				{
					loadDefinition(csChannelRestriction, tagLogIni, arrIncludePath);
				}
			}
			
		}
		return true;	
	}
	
	void openLogCenters()
	{
			
	}
/**
 * Saves the configuration of all {@link LogCenterLoader} instances into
 * the provided {@link Tag} object.
 * @param tagOrganisation The <i>Tag</i> where save the <i>LogCenterLoader</i>
 * configuration.
 * @return <i>true</i> if the operation terminated correctly.
 */
	public boolean saveDefinition(Tag tagOrganisation)
	{
		Tag tagLogCenters = tagOrganisation.addTag("LogCenters");
		if(m_arrLogCenterloader != null)
		{
			for(int n=0; n<m_arrLogCenterloader.size(); n++)
			{
				LogCenterLoader logCenterloader = getLogCenterloader(n);
				
				Tag tagLogCenter = tagLogCenters.addTag("LogCenter");
				logCenterloader.saveDefinition(tagLogCenter);
			}
		}
		return true;	
	}

/**
 * Returns a reference to the specified <i>LogCenterLoader</i>.
 * @param n The item ordinal to retrieve. Has to be smaller than the
 * maximum number of items (see {@link #getNbLogCenterloader}). 
 * @return A reference to the specified <i>LogCenterLoader</i>.
 */
	public LogCenterLoader getLogCenterloader(int n)
	{
		return m_arrLogCenterloader.get(n);
	}

/**
 * Returns the number of {@link LogCenterLoader} instances currently loaded.
 * @return The number of {@link LogCenterLoader} instances currently loaded.
 */
	public int getNbLogCenterloader()
	{
		return m_arrLogCenterloader.size();
	}
			
	private ArrayList<LogCenterLoader> m_arrLogCenterloader = null;
}
