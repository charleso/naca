/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package utils.SQLSyntaxConverter;

import java.util.ArrayList;

import lexer.CBaseToken;
import lexer.CTokenList;
import utils.Transcoder;

public class ConvertibleSQLStatement
{
	private String m_csSearchPattern = null;
	private CTokenList m_lstSearchPattern = null;
	private String m_csReplacements = null;
	private CTokenList m_lstReplacements = null;
	private boolean m_bFromSource = false;
	private boolean m_bIntoSignificant = false;
	
	ConvertibleSQLStatement(String csSearchPattern, String csReplacements, boolean bFromSource, boolean bIntoSignificant)
	{
		m_csSearchPattern = csSearchPattern;
		m_csReplacements = csReplacements;
		m_bFromSource = bFromSource;
		m_bIntoSignificant = bIntoSignificant;
	}
	
	void lex(String csGroup, Transcoder transcoder)
	{
		if(m_bFromSource)
		{
			int gg = 0;
		}
		m_lstSearchPattern = transcoder.doTextTranscoding(csGroup, m_csSearchPattern, m_bFromSource);
		m_lstReplacements = transcoder.doTextTranscoding(csGroup, m_csReplacements, m_bFromSource);
	}
	
	ArrayList<CBaseToken> getSearchPattern()
	{
		return m_lstSearchPattern.getAsArray();
	}
	
	String getSearchPatternString()
	{
		return m_csSearchPattern;
	}
	
	ArrayList<CBaseToken> getReplacements()
	{
		return m_lstReplacements.getAsArray();
	}
	
	boolean getIntoSignificant()
	{
		return m_bIntoSignificant;
	}
}
