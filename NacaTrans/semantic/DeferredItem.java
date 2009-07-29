/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package semantic;

import parser.CLanguageElement;

public class DeferredItem
{
	private CBaseLanguageEntity m_entity = null;
	private CLanguageElement m_element = null;
	
	public DeferredItem(CBaseLanguageEntity entity, CLanguageElement element /*this*/)
	{
		m_entity = entity; 
		m_element = element;
	}

	public CLanguageElement getElement()
	{
		return m_element;
	}

	public CBaseLanguageEntity getEntity()
	{
		return m_entity;
	}
	
}
