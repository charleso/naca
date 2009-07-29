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
package jlib.xml;

/*
 * Created on 26 mai 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * @author PJD
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ValCursor
{
	public ValCursor()
	{
	}
	
	void setEnumVal(NamedNodeMap nodeMap)
	{
		m_nodeMap = nodeMap; 
	}
	
	public String getFirstVal()
	{
		m_nIndex = 0;
		return getNextVal();
	}
	
	public String getNextVal()
	{
		int nNbIndex = m_nodeMap.getLength();
		if(m_nIndex < nNbIndex)
		{
			Node node = m_nodeMap.item(m_nIndex);
			m_nIndex++;
			String cs = node.getNodeValue();
			return cs;
		}
		return null;
	}
	
	public Node getFirstParam()
	{
		m_nIndex = 0;
		return getNextParam();
	}
	
	public Node getNextParam()
	{
		int nNbIndex = m_nodeMap.getLength();
		if(m_nIndex < nNbIndex)
		{
			Node node = m_nodeMap.item(m_nIndex);
			m_nIndex++;
			return node;
		}
		return null;
	}
	
	
	
	NamedNodeMap m_nodeMap = null;
	int m_nIndex = 0;
}
