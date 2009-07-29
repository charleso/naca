/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.pathManager;

import java.util.ArrayList;
import java.util.Hashtable;

import jlib.misc.FileSystem;
import jlib.misc.StringUtil;
import jlib.xml.Tag;
import jlib.xml.TagCursor;

public class PathsManager
{	
	private static ArrayList<PathNameValue> ms_arrPathNames = new ArrayList<PathNameValue>();
	
	public static void Load(Tag eConf)
	{
		Tag tagPaths = eConf.getChild("Paths");
		if(tagPaths == null)
			return ;
		
		TagCursor cur = new TagCursor(); 
		Tag tagPath = tagPaths.getFirstChild(cur, "Path");
		while(tagPath != null)
		{
			String csName = tagPath.getVal("Name");
			String csValue = tagPath.getVal("Value");
			PathNameValue pathNameValue = new PathNameValue(csName, csValue);
			ms_arrPathNames.add(pathNameValue);
			tagPath = tagPaths.getNextChild(cur);
		}
	}
	
	public static String adjustPath(String csPath)
	{
		if(!StringUtil.isEmpty(csPath))
		{
			for(int n=0; n<ms_arrPathNames.size(); n++)
			{
				PathNameValue pathNameValue = ms_arrPathNames.get(n);
				String csName = pathNameValue.getName();
				String csValue = pathNameValue.getValue();
				csPath = StringUtil.replace(csPath, csName, csValue, true);
			}
		}
		return csPath;
	}
}
