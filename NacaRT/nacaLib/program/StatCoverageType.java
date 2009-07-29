/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.program;

import java.util.ArrayList;

public class StatCoverageType
{
	private static int ms_nNbEntries = 8;
	private static ArrayList<String> ms_arrNames = null;
	
	public static StatCoverageType CallbackSearch = new StatCoverageType(0, "CallbackSearch");
	public static StatCoverageType Paragraph = new StatCoverageType(1, "Paragraph");
	public static StatCoverageType Section = new StatCoverageType(2, "Section");
	public static StatCoverageType Sentence = new StatCoverageType(3, "Sentence");
	public static StatCoverageType Call = new StatCoverageType(4, "Call");
	public static StatCoverageType CallFailed = new StatCoverageType(5, "CallFailed");
	public static StatCoverageType CallPrepareFailed = new StatCoverageType(6, "CallPrepareFailed");
	public static StatCoverageType ReturnFromCall = new StatCoverageType(7, "ReturnFromCall");	
	
	
	private int m_nId = 0;
	
	private StatCoverageType(int nId, String csName)
	{		
		m_nId = nId;
		if(ms_arrNames == null)
		{
			ms_arrNames = new ArrayList<String>();
			for(int n=0; n<ms_nNbEntries; n++)
			{
				ms_arrNames.add("");
			}
		}
		ms_arrNames.set(nId, csName);
	}
	
	public int getId()
	{
		return m_nId;
	}
	
	static int getNbEntries()
	{
		return ms_nNbEntries;
	}
	
	static String getNameAtIndex(int n)
	{
		return ms_arrNames.get(n);
	}
}
