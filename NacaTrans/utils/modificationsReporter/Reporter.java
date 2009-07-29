/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaTrans - Naca Transcoder v1.2.0.beta.1
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/**
 * 
 */
package utils.modificationsReporter;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Stack;

import utils.Transcoder;

import jlib.misc.StringUtil;
import jlib.xml.Tag;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: Reporter.java,v 1.2 2009/04/24 13:10:42 u930di Exp $
 */
public class Reporter
{
	private static Reporter ms_Report = null;
	private Hashtable<Integer, ModificationToReportCount> m_tabOptions = new Hashtable<Integer, ModificationToReportCount>() ;
	private Stack<String> m_stackCurrentFilename = null;
	
	private Reporter()
	{
		m_tabOptions = new Hashtable<Integer, ModificationToReportCount>();
		m_stackCurrentFilename = new Stack<String>();
	}
	
	public static void Add(String csReason, String csText)
	{
		if(ms_Report == null)
			ms_Report = new Reporter();
		ms_Report.add(csReason, csText);
	}
	
	public static void setCurrentFileName(String csFilename)
	{
		if(ms_Report == null)
			ms_Report = new Reporter();
		ms_Report.pushCurrentFileName(csFilename);
	}
	
	public static void resetCurrentFileName()
	{
		if(ms_Report == null)
			ms_Report = new Reporter();
		ms_Report.popCurrentFileName();
	}

	public void pushCurrentFileName(String csFilename)
	{
		m_stackCurrentFilename.push(csFilename);
	}
	
	public void popCurrentFileName()
	{
		if(!m_stackCurrentFilename.isEmpty())
			m_stackCurrentFilename.pop();
		else
		{
			int gg = 0;
		}
	}
	
	public String getCurrentFileName()
	{
		if(!m_stackCurrentFilename.isEmpty())
		{
			String cs = m_stackCurrentFilename.peek();
			return cs;
		}
		return "";
	}
	
	public static void export(String csPath)
	{
		if(ms_Report != null)
			ms_Report.doExport(csPath);
	}
	
	public void add(String csReason, String csText)
	{
		String csFile = Transcoder.getCurrentTranscodedUnit();
		if(StringUtil.isEmpty(csFile))
			csFile = getCurrentFileName();
		if(StringUtil.isEmpty(csFile))
		{
			int gg =0 ;
		}
		ModificationToReportCount modif = new ModificationToReportCount(csReason, csText);
		addOrIncCount(csFile, modif);
	}
	
	private void addOrIncCount(String csFile, ModificationToReportCount modif)
	{
		Integer iId = modif.getId();
		ModificationToReportCount modifFound = m_tabOptions.get(iId);
		if(modifFound == null)
		{
			m_tabOptions.put(iId, modif);
			modif.addFileReference(csFile);
		}
		else
			modifFound.addFileReference(csFile);
	}
	
	private void doExport(String csPath)
	{
		Tag tag = new Tag("Modifications");
		if(m_tabOptions == null)
			return;
		Collection<ModificationToReportCount> colValues =  m_tabOptions.values();
		Iterator<ModificationToReportCount> iter = colValues.iterator();
		try
		{
			ModificationToReportCount modif = iter.next();
			while(modif != null)
			{
				modif.dump(tag);
				modif = iter.next();
			}
		}
		catch(Exception e)
		{
		}
		tag.exportIndentedUtf8(csPath);		
	}
}
