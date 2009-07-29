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
package utils.CobolTranscoder;

public class OrderedName
{
	private String m_csName;
	private boolean m_bAscending;
	
	public OrderedName(String csName, boolean bAscending)
	{
		m_csName = csName;
		m_bAscending = bAscending;
	}
	
	public boolean getAscending()
	{
		return m_bAscending;
	}
	
	public String getName()
	{
		return m_csName;
	}
}
