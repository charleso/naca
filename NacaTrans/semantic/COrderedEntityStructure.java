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
package semantic;

public class COrderedEntityStructure
{
	private CEntityStructure m_eStruct = null;
	private boolean m_bAscending = false;
	
	public COrderedEntityStructure(CEntityStructure eStruct, boolean bAscending)
	{
		m_eStruct = eStruct;
		m_bAscending = bAscending;
	}
	
	public boolean getAscending()
	{
		return m_bAscending;
	}
	
	public CEntityStructure getStruct()
	{
		return m_eStruct;
	}
}
