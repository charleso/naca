/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
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

import java.util.ArrayList;

import lexer.CBaseToken;
import lexer.CReservedKeyword;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class LevelKeywords
{	
	protected void resetManagedKeyword()
	{
		m_arrManagedKeywords = new ArrayList<CReservedKeyword>();
	}
	
	public void registerManagedKeyword(CReservedKeyword keyword)
	{
		m_arrManagedKeywords.add(keyword);
	}
	
	public boolean isManaging(CBaseToken tok)
	{
		if(tok == null)
			return true;
		CReservedKeyword keyTok = tok.GetKeyword();
		if(keyTok != null)
		{
			String csTokName = keyTok.m_Name;
			for(int n=0; n<m_arrManagedKeywords.size(); n++)
			{
				if(m_arrManagedKeywords.get(n).m_Name.equalsIgnoreCase(csTokName))
					return true;
			}
		}
		return false;
	}
	
	private ArrayList<CReservedKeyword> m_arrManagedKeywords = new ArrayList<CReservedKeyword>();
}
