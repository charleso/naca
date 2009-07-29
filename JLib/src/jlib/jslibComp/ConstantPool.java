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
/**
 * 
 */
package jlib.jslibComp;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class ConstantPool
{
	private ArrayList<ConstantItem> m_arr = null;
	
	public void add(String csId, String csValue)
	{
		if(m_arr == null)
			m_arr = new ArrayList<ConstantItem>();
		
		ConstantItem item = new ConstantItem(csId, csValue);
		m_arr.add(item);
	}
	
	public Collection<ConstantItem> getCollection()
	{
		return m_arr;
	}
	
}
