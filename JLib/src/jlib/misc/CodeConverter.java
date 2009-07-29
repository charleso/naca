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
package jlib.misc;

public class CodeConverter
{
	private int ms_nSize = 0;
	private int[] ms_tFrom = null;
	private int[] ms_tTo = null;

	public CodeConverter(String csConversion)
	{
		if (csConversion == null || csConversion.equals("")) return;
		
		String[] csSplit = csConversion.split(",");
		ms_nSize = csSplit.length;
		
		ms_tFrom = new int [ms_nSize];
		ms_tTo = new int [ms_nSize];
		for (int n=0; n < ms_nSize; n++)
		{	
			String[] csFromTo = csSplit[n].split("-");
			int nFrom = new Integer(csFromTo[0]).intValue();
			int nTo = new Integer(csFromTo[1]).intValue();
			ms_tFrom[n] = nFrom; 
			ms_tTo[n] = nTo;
		}
	}
	
	public String convert(String csIn)
	{
		if (ms_nSize == 0) return csIn;
		
		String csOut = csIn;
		for (int n=0; n < ms_nSize; n++)
		{
			csOut = csOut.replace((char)ms_tFrom[n], (char)ms_tTo[n]);
		}
		return csOut;
	}
	
//	public byte [] convert(byte tbSource[])
//	{
//		if (ms_nSize == 0) 
//			return tbSource;
//		
//		int nNbBytes = tbSource.length;
//		if (ms_nSize == 1)
//		{
//			for(int n=0; n<nNbBytes; n++)
//			{
//				int nSource = (int)tbSource[n];
//				if(nSource < 0)
//					nSource += 256;
//				if(nSource == ms_tFrom[0])
//					tbSource[n] = (byte)ms_tTo[n];
//			}
//			return tbSource;
//		}
//		else if(ms_nSize == 2)
//		{
//			for(int n=0; n<nNbBytes; n++)
//			{
//				int nSource = (int)tbSource[n];
//				if(nSource < 0)
//					nSource += 256;
//				if(nSource == ms_tFrom[0])
//				{
//					tbSource[n] = (byte)ms_tTo[0];
//					continue;
//				}
//				if(nSource == ms_tFrom[1])
//					tbSource[n] = (byte)ms_tTo[1];
//			}
//			return tbSource;
//		}
//		else if(ms_nSize == 3)
//		{
//			for(int n=0; n<nNbBytes; n++)
//			{
//				int nSource = (int)tbSource[n];
//				if(nSource < 0)
//					nSource += 256;
//				if(nSource == ms_tFrom[0])
//				{
//					tbSource[n] = (byte)ms_tTo[0];
//					continue;
//				}
//				if(nSource == ms_tFrom[1])
//				{
//					tbSource[n] = (byte)ms_tTo[1];
//					continue;
//				}
//				if(nSource == ms_tFrom[2])
//					tbSource[n] = (byte)ms_tTo[2];
//			}
//			return tbSource;
//		}
//		else
//		{
//			int m=0;
//			for(int n=0; n<nNbBytes; n++)
//			{
//				int nSource = (int)tbSource[n];
//				if(nSource < 0)
//					nSource += 256;
//				
//				for(m=0; m<ms_nSize; m++)
//				{
//					if(nSource == ms_tFrom[m])
//					{
//						tbSource[n] = (byte)ms_tTo[m];
//						break;
//					}
//				}
//			}
//			return tbSource;
//		}
//	}
}