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
package utils.modificationsReporter;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import jlib.xml.Tag;

public class ModificationToReportCount
{
	private String m_csReason;
	private String m_csText;
	private Hashtable<String, Integer> m_hashCountPerFile;
	
	ModificationToReportCount(String csReason, String csText)
	{
		m_csReason = csReason;
		m_csText = csText;
	}

	void addFileReference(String csFile)
	{
		if(m_hashCountPerFile == null)
			m_hashCountPerFile = new Hashtable<String, Integer>();
		Integer iCount = m_hashCountPerFile.get(csFile);
		if(iCount == null)
			m_hashCountPerFile.put(csFile, 1);
		else
			iCount++;
	}
	
	String getReason()
	{
		return m_csReason;
	}
	
	String getText()
	{
		return m_csText;
	}
	
	Integer getId()
	{
		return m_csReason.hashCode() * 10000 + m_csText.hashCode();
	}	
	
	void dump(Tag tag)
	{
		Tag tagModif = tag.addTag("Modification");
		tagModif.addVal("Reason", m_csReason);
		tagModif.addVal("Text", m_csText);
		
		if(m_hashCountPerFile == null)
			return;
		
		try
		{
			Enumeration<String> colFileNames =  m_hashCountPerFile.keys();
			String csFileName = colFileNames.nextElement();
			Tag tagFiles = null;
			while(csFileName != null)
			{
				Integer iCount = m_hashCountPerFile.get(csFileName);
				
				if(tagFiles == null)
					tagFiles = tagModif.addTag("Files");
				
				Tag tagFile = tagFiles.addTag("File");
				tagFile.addVal("FileName", csFileName);
				tagFile.addVal("Count", iCount);
	
				csFileName = colFileNames.nextElement();
			}
		}
		catch (Exception e) 
		{
		}
	}
}
