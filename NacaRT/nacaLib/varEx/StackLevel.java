/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.varEx;

import java.util.ArrayList;

import nacaLib.base.CJMapObject;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: StackLevel.java,v 1.1 2006/04/19 09:53:18 cvsadmin Exp $
 */
public class StackLevel extends CJMapObject
{
	CLevel getParentLevel(int nLevel)
	{
		CLevel level = null;
		for(int n=m_nTopValidIndex; n>=0; n--)
		{
			level = m_arr.get(n);
			if(level.hasLowerLevel(nLevel))
				return level; 
		}		
		return null;
	}
	
	void push(CLevel levelToPush)
	{
		int nLevelToPush = levelToPush.m_nLevel; 
		for(int n=m_nTopValidIndex; n>=0; n--)
		{
			CLevel levelStacked = m_arr.get(n);
			if(levelStacked.hasLowerLevel(nLevelToPush))
			{
				setLevelInArray(n+1, levelToPush);
				return ;
			}
		}
		setLevelInArray(0, levelToPush);
	}
	
	private void setLevelInArray(int nIndex, CLevel levelToPush)
	{
		if(nIndex < m_arr.size())
		{
			CLevel levelStacked = m_arr.get(nIndex);
			levelStacked.setWith(levelToPush);
			m_nTopValidIndex = nIndex; 
		}
		else
		{
			assertIfFalse(nIndex == m_arr.size());
			m_arr.add(levelToPush);
			m_nTopValidIndex = nIndex;
		}
	}
	
	private int m_nTopValidIndex = -1;
	private ArrayList<CLevel> m_arr = new ArrayList<CLevel>(); 
}
