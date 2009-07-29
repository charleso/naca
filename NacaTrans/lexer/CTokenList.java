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
/*
 * Created on Jul 16, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package lexer;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import utils.Transcoder;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CTokenList
{
	LinkedList<CBaseToken> m_lstTokens = new LinkedList<CBaseToken>() ;
	ListIterator m_iter = null ;
	CBaseToken m_curToken = null ;

	public void dump()
	{
		try
		{
			ListIterator iter = m_lstTokens.listIterator() ;
			CBaseToken curToken = (CBaseToken)iter.next() ;
			while(curToken != null)
			{
				System.out.println(curToken.toString());
				curToken = (CBaseToken)iter.next() ;
			}
		}
		catch (Exception e)
		{
		}
	}
	
	public void fillFromArray(ArrayList<CBaseToken> arr)
	{
		for(int n=0; n<arr.size(); n++)
		{
			CBaseToken tok = arr.get(n);
			Add(tok);
		}
	}
	
	public ArrayList<CBaseToken> getAsArray()
	{
		ArrayList<CBaseToken> arrTokens = null;
		try
		{
			ListIterator iter = m_lstTokens.listIterator() ;
			CBaseToken curToken = (CBaseToken)iter.next() ;
			while(curToken != null)
			{
				if(arrTokens == null)
					arrTokens = new ArrayList<CBaseToken>(); 
				arrTokens.add(curToken);
				curToken = (CBaseToken)iter.next() ;
			}
		}
		catch (Exception e)
		{
		}
		return arrTokens;
	}
	
	public void removeLast(int nNbEntries)
	{
		while(nNbEntries > 0)
		{
			m_lstTokens.removeLast();
			nNbEntries--;
		}
	}
	
	public void Add(CBaseToken tok)
	{
		m_lstTokens.add(tok) ;
	}
	
	public CBaseToken GetCurrentToken()
	{
		return m_curToken ;
	}
	
	public void UpdateCurrentToken(CBaseToken tok)
	{
		String csName = tok.GetValue();
		Transcoder.logWarn(tok.getLine(), "Using identifier with reserved keyword name " + tok.GetValue());
		m_curToken = tok;
		m_iter.set(tok);	// replace old token by the new one
		COverridenKeywordManager.Add(tok);
	}
	
	public void StartIter()
	{
		try
		{
			m_iter = m_lstTokens.listIterator() ;
			m_curToken = (CBaseToken)m_iter.next() ;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			m_iter = null ;
			m_curToken = null ;
		}
	}
	public CBaseToken GetNext()
	{
		try
		{
			if (m_iter == null)
			{
				m_iter = m_lstTokens.listIterator() ;
			}
			m_curToken = (CBaseToken)m_iter.next();
			return m_curToken;
		}
		catch (NoSuchElementException e)
		{
			m_curToken = null ;
			return null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			m_curToken = null ;
			return null ;
		}
	} 
	public int GetNbTokens()
	{
		return m_lstTokens.size();
	}

	public void Clear()
	{
		m_curToken = null ;
		m_iter = null ;
		m_lstTokens.clear() ;
	}
	
	public void UpdateKewyordsByIdentifers()
	{		
		int nInitialIndex = m_iter.nextIndex();
		
		// Act
		ListIterator iter = m_lstTokens.listIterator() ;
		int nIndex = iter.nextIndex();
		while(nIndex != nInitialIndex)
		{
			iter.next();
			nIndex = iter.nextIndex();
		}
		
		try
		{
			CBaseToken tok = (CBaseToken)iter.next();
			while(tok != null)
			{
				if(tok.IsKeyword())
				{
					String csName = tok.GetValue();
					CBaseToken tokReplacement = COverridenKeywordManager.GetOverridingToken(csName);
					if(tokReplacement != null)
					{
						int nLine = tok.getLine();	// Adjust source line
						Transcoder.logWarn(nLine, "Overriding keyword " + csName + " by identifier");
						tokReplacement.setLine(nLine);
						iter.set(tokReplacement);	// replace old token by the new one
					}
				}
				
				tok = (CBaseToken)iter.next();
			}
		}
		catch (NoSuchElementException e)
		{
			int gg = 0;
		}
		catch (Exception e)
		{
			int gg = 0;
		}
		int nInitialIndex2 = iter.nextIndex();
//		ExportTokens(this, "D:\\toto.lex");
		int ggg = 0;
		
//		
//		
//		DoUpdateKewyordsByIdentifers();	
//		
//		// Return to initial position
//		int nIndex = m_iter.nextIndex();
//		while(nIndex > nNextIndex)
//		{
//			m_iter.previous();
//			nIndex = m_iter.nextIndex();
//		}
//		COverridenKeywordManager.Clear();
//		m_curToken
	}
	
//	private void DoUpdateKewyordsByIdentifers()
//	{		
//		CBaseToken tok = GetNext();
//		while(tok != null)
//		{
//			if(tok.IsKeyword())
//			{
//				String csName = tok.GetValue();
//				CBaseToken tokReplacement = COverridenKeywordManager.GetOverridingToken(csName);
//				if(tokReplacement != null)
//				{
//					int nLine = tok.getLine();	// Adjust source line
//					tokReplacement.setLine(nLine);
//					UpdateCurrentToken(tokReplacement);
//				}
//			}
//			tok = GetNext();
//		}
//	}
	
	protected void ExportTokens(CTokenList lst, String filename)
	{
		try
		{
			if (lst != null && lst.GetNbTokens()>0)
			{
				FileOutputStream file = new FileOutputStream(filename) ;
				PrintStream output = new PrintStream(file, true) ;
				lst.StartIter() ;
				CBaseToken tok = lst.GetCurrentToken() ;
				int nCurLine = tok.getLine() ;
				output.print("" + nCurLine + ":") ;
				while (tok != null)
				{
					//if (tokEntry.getLine() > nCurLine)
					if (tok.m_bIsNewLine)
					{
						output.println("") ;
						nCurLine = tok.getLine() ;
						output.print("" + nCurLine + ":") ;
					}
					output.print(tok.toString());
					tok = lst.GetNext() ;
				}
			}
			else
			{
				Transcoder.logError("No tokens to export for "+filename);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Transcoder.logError(e.toString() + "\n" + e.getStackTrace());
		}
	}

	public CBaseToken getFromLastToken(int i) 
	{
		if (m_lstTokens==null) 
			return null;
		if (m_lstTokens.size() < i)  
			return null;
		return m_lstTokens.get(m_lstTokens.size()-i-1);
	}
	
	public LinkedList<CBaseToken> getInternalList()
	{
		return m_lstTokens;	
	}
	
	
	
//	public ListIterator getCustomTokenListIterator()
//	{
//		ListIterator iter = m_lstTokens.listIterator() ;
//		return iter;
//	}
}
