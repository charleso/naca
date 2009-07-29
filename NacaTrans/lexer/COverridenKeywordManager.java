/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaTrans - Naca Transcoder v1.2.0.beta.1
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/**
 * 
 */
package lexer;

import java.util.Hashtable;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class COverridenKeywordManager
{
	private static Hashtable<String, CBaseToken> ms_hashOverriddenKeywords = null ;
	
	public static void Add(CBaseToken tok)
	{
		if(ms_hashOverriddenKeywords == null)
			ms_hashOverriddenKeywords = new Hashtable<String, CBaseToken>();
		
		String csName = tok.GetValue();
		ms_hashOverriddenKeywords.put(csName, tok);
	}
	
	public static CBaseToken GetOverridingToken(String csName)
	{
		if(ms_hashOverriddenKeywords != null)
			return ms_hashOverriddenKeywords.get(csName);
		return null;
	}
	
	public static void Clear()
	{
		ms_hashOverriddenKeywords = null;
	}
}
