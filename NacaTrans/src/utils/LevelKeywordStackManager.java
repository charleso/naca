/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/**
 * 
 */
package utils;

import java.util.Stack;

import lexer.CBaseToken;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: LevelKeywordStackManager.java,v 1.1 2007/06/28 06:19:46 u930bm Exp $
 */
public class LevelKeywordStackManager
{
	private LevelKeywordStackManager m_LevelKeywordStackManager = null;
	private static Stack<LevelKeywords> ms_stack = new Stack<LevelKeywords>(); 
	
	private LevelKeywordStackManager()
	{
	}
	
	LevelKeywordStackManager getLevelKeywordStackManager()
	{
		if(m_LevelKeywordStackManager == null)
			m_LevelKeywordStackManager = new LevelKeywordStackManager();
		return m_LevelKeywordStackManager;
	}
	
	public static LevelKeywords getAndPushNewLevelKeywords()
	{
		LevelKeywords l = new LevelKeywords();
		ms_stack.push(l);
		return l;
	}
	
	public static void popLevelKeywords()
	{
		ms_stack.pop();
	}
	
	public static boolean isTokenManagedByAnyParents(CBaseToken tok)
	{
		int nNbParents = ms_stack.size();
		for(int n=0; n<nNbParents; n++)
		{
			LevelKeywords l = ms_stack.get(n);
			if(l.isManaging(tok))
				return true;
		}
		return false;
	}
}
