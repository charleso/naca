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
package semantic.SQL;

public class MissingFetchVariable
{
	private int m_nNbMissingFetchVariable = 0;
	
	MissingFetchVariable(int nNbMissingFetchVariable)
	{
		m_nNbMissingFetchVariable = nNbMissingFetchVariable;
	}
	
	public int getValue()
	{
		return m_nNbMissingFetchVariable;
	}
}
