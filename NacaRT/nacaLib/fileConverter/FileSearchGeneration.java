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
package nacaLib.fileConverter;

import java.util.Arrays;

import jlib.log.Log;
import jlib.misc.FileSystem;
import jlib.misc.StringUtil;

public class FileSearchGeneration
{
	public String search(String csParameter)
	{
		return search(csParameter, 0);
	}
	public String search(String csParameter, int nGeneration)
	{
		if (nGeneration > 1)
		{
			Log.logCritical("Cannot search a generation " + nGeneration + " for the file " + csParameter);
			return null;
		}
		csParameter = csParameter.trim();
		if (!csParameter.endsWith("_"))
		{
			csParameter += "_";
		}
		
		String csDirectory = "";
		String csPrefix = "";		
		int nPos = csParameter.lastIndexOf("/");
		if (nPos == -1)
		{
			csPrefix = csParameter;
		}
		else
		{
			csDirectory = csParameter.substring(0, nPos);
			csPrefix = csParameter.substring(nPos + 1);
		}
		
		String lst[] = FileSystem.getFileNameListByPrefix(csDirectory, csPrefix);
		if (lst.length == 0)
		{
			if (nGeneration != 1)
			{
				Log.logCritical("Cannot search a generation " + nGeneration + " for the file " + csParameter);
				return null;
			}
			return csParameter + "0001";
		}
		else
		{
			Arrays.sort(lst, String.CASE_INSENSITIVE_ORDER);
			
			if (lst[lst.length - 1].endsWith("9999") && lst[0].endsWith("0001"))
			{
				// reorganize the list of file
				while (true)
				{
					String csLastGeneration = lst[lst.length - 1];
					if (csLastGeneration.charAt(csLastGeneration.length() - 4) == '9')
					{
						for (int i=lst.length - 2; i > -1; i--)
						{
							lst[i++] = lst[i];
						}
						lst[0] = csLastGeneration;
					}
					else
					{
						break;
					}
				}
			}
			
			if (nGeneration == 0)
			{
				int nLastGeneration = getGeneration(lst[lst.length - 1]);
				return csParameter + StringUtil.FormatWithFill4LeftZero(nLastGeneration);
			}
			else if (nGeneration > 0)
			{
				int nLastGeneration = getGeneration(lst[lst.length - 1]);
				int nNewGeneration = 1;
				if (nLastGeneration != 9999)
					 nNewGeneration = nLastGeneration + 1;
				return csParameter + StringUtil.FormatWithFill4LeftZero(nNewGeneration);
			}
			else
			{
				if (lst.length <= -nGeneration)
				{
					Log.logCritical("Cannot search a generation " + nGeneration + " for the file " + csParameter);
					return null;
				}
				int nSearchGeneration = getGeneration(lst[lst.length - 1 + nGeneration]);
				return csParameter + StringUtil.FormatWithFill4LeftZero(nSearchGeneration);
			}
		}
	}
	
	private int getGeneration(String csName)
	{
		int nPos = csName.lastIndexOf("_");
		return new Integer(csName.substring(nPos + 1)).intValue();
	}
}