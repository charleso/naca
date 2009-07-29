/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.jmxMBean;

import java.util.HashMap;
import java.util.Map;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;

public class CompositeDataDesc
{
	public CompositeDataDesc(CompositeType compositeType)
	{
		m_compositeType = compositeType;
		m_map = new HashMap<String, Object>();	
	}
	
	public void setItemValue(String csKey, Object oValue)
	{
		m_map.put(csKey, oValue);
	}
	
	public CompositeData generateCompositeData()
	{
		try
		{			
			CompositeDataSupport compositeData = new CompositeDataSupport(m_compositeType, m_map);
			return compositeData;
		} 
		catch (OpenDataException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	private CompositeType m_compositeType = null;
	Map<String, Object> m_map = null;
}
