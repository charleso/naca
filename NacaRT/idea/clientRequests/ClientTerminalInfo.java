/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package idea.clientRequests;

import jlib.xml.Tag;

public class ClientTerminalInfo extends httpClientRequester
{
	public ClientTerminalInfo()
	{
	}
	
	public String get(String csUrl)
	{
		if(!csUrl.startsWith("http://"))
			csUrl = "http://" + csUrl;
		if(!csUrl.endsWith(":11111"))
			csUrl += ":11111";
		csUrl += "/RequestGetLU";
		boolean b = doHttpget(csUrl);
		if(b)
		{
			Tag tag = getResponseBodyAsTag();
			if(tag != null)
			{
				String csLUName = tag.getChildText("LUName");
				return csLUName;
			}
		}
		return "";
	}
}
