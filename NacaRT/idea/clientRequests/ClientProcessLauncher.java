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
package idea.clientRequests;

import java.util.ArrayList;

import jlib.xml.Tag;

public class ClientProcessLauncher extends httpClientRequester
{
	public ClientProcessLauncher()
	{
	}
	

	public void prepareLaunch(String csProcess, String csPath, boolean bSynchronous)
	{
		m_csProcess = csProcess;
		m_csPath = csPath;
		m_bSynchronous = bSynchronous;
	}
		
	public void addArg(String csArg)
	{
		if(m_arrArg == null)
			m_arrArg = new ArrayList<String>();
		m_arrArg.add(csArg);
	}
			
	public String launch(String csUrl)
	{
		if(!csUrl.startsWith("http://"))
			csUrl = "http://" + csUrl;
		if(!csUrl.endsWith(":11111"))
			csUrl += ":11111";
		csUrl += "/LaunchProcess";
		
		if(m_csProcess != null)
			csUrl += "&Process=" + m_csProcess;
		if(m_csPath != null)
			csUrl += "&Path=" + m_csPath;
		if(m_bSynchronous)
			csUrl += "&Wait=true";
		else
			csUrl += "&Wait=false";
		
		if(m_arrArg != null)
		{
			for(int n=0; n<m_arrArg.size(); n++)
			{
				csUrl += "&Arg=" + m_arrArg.get(n);
			}
		}

		boolean b = doHttpget(csUrl);
		if(b)
		{
			Tag tag = getResponseBodyAsTag();
			if(tag != null)
			{
				String cs = tag.getChildText("Status");
				return cs;
			}
		}
		return "";
	}
	
	private String m_csProcess = null;
	private String m_csPath = null;
	private boolean m_bSynchronous = false;
	private ArrayList<String> m_arrArg = null;
}
