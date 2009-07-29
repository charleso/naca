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
package parser;

public class COrderedIdentfier
{
	private CIdentifier m_identifier = null;
	private boolean m_bAscending = false;
	
	public COrderedIdentfier(CIdentifier identifier, boolean bAscending)
	{
		m_identifier = identifier;
		m_bAscending = bAscending;
	}
	
	public CIdentifier getIdentifier()
	{
		return m_identifier;
	}
	
	public boolean getAscending()
	{
		return m_bAscending;
	}
}
