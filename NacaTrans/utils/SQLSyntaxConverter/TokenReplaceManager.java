/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package utils.SQLSyntaxConverter;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.ListIterator;

import lexer.CBaseToken;
import lexer.CTokenList;
import lexer.CTokenType;

public class TokenReplaceManager
{
	private ArrayList<CBaseToken> m_arrTokensSearchPattern = null;
	private int m_nCurTokenIndex = 0;
	private int m_nFirstSourceIndex = -1;
	private boolean m_bNextTokenIsVarName = false;
	private Hashtable<String, CBaseToken> m_hashVarToReplace = null;
	
	TokenReplaceManager()
	{		
	}
	
	void init(ArrayList<CBaseToken> arrTokensFrom)
	{
		m_arrTokensSearchPattern = arrTokensFrom;
	}
	
	boolean isTokenEquals(CBaseToken curToken, int nSourceIndex, boolean bIntoSignificant)
	{
		if(curToken == null)
			return notFound();
		
		CBaseToken tok = m_arrTokensSearchPattern.get(m_nCurTokenIndex);
		if(tok == null)
			return notFound();
		if(tok.isSemanticallyEquals(curToken))
		{
			if(m_bNextTokenIsVarName)	// We are on a variable's name: Ignore the difference
				registerVariableNameMapping(tok, curToken);

			if(tok.GetType() == CTokenType.COLON)
				m_bNextTokenIsVarName = true;

			return found(nSourceIndex);
		}
		else if(m_bNextTokenIsVarName)
		{
			if(!bIntoSignificant)// We are on a variable's name: Ignore the name difference 
			{
				registerVariableNameMapping(tok, curToken);
				return found(nSourceIndex);
			}
			else
			{
				m_bNextTokenIsVarName = false;		// return notFound
			}			
		}
			
		return notFound();
	}
	
	private void registerVariableNameMapping(CBaseToken tokVarReplacementId, CBaseToken tokVarSource)
	{
		m_bNextTokenIsVarName = false;
		
		if(m_hashVarToReplace == null)
			m_hashVarToReplace = new Hashtable<String, CBaseToken>();  
		m_hashVarToReplace.put(tokVarReplacementId.GetValue(), tokVarSource);
	}
	
	CBaseToken getSourceVarToken(String csReplacementVarId)
	{
		if(m_hashVarToReplace == null)
			return null;
		return m_hashVarToReplace.get(csReplacementVarId);
	}
	
	private boolean notFound()
	{
		if(m_nCurTokenIndex > 0)
		{
			m_nCurTokenIndex = 0;
			m_nFirstSourceIndex = -1;
			m_bNextTokenIsVarName = false;
		}
		return false;
	}
	
	private boolean found(int nSourceIndex)
	{
		if(m_nCurTokenIndex == 0)
			m_nFirstSourceIndex = nSourceIndex;
		
		m_nCurTokenIndex++;
		return true;
	}
	
	boolean isAllTokenFound()
	{
		if(m_arrTokensSearchPattern.size() == m_nCurTokenIndex)
			return true;
		return false;
	}
	
	int getFirstSourceIndex()
	{
		return m_nFirstSourceIndex;
	}
	
	int getNbTokensToSearch()
	{
		return m_arrTokensSearchPattern.size();
	}
	
	void reset()
	{
		m_nCurTokenIndex = 0;
		m_nFirstSourceIndex = -1;
		m_bNextTokenIsVarName = false;
	}
	
}
