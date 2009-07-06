/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.jmxMBean;

import java.util.ArrayList;

import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;

public class CompositeTypeDesc
{
	public CompositeTypeDesc(String csName, String csDescription)
	{
		m_csName = csName;
		m_csDescription = csDescription;
	}
	
	public void addItem(String csName, String csDescription, OpenType openType)
	{
		CompositeTypeDescItem itemDesc = new CompositeTypeDescItem(csName, csDescription, openType);
		m_arrItemDesc.add(itemDesc);		
	}
	
	public CompositeType generateCompositeType()
	{
		try
		{
			int nNbItems = m_arrItemDesc.size();
			OpenType [] openTypes = new OpenType [nNbItems];
			String [] itemTypeNames = new String [nNbItems];
			String [] itemTypeDescriptions = new String [nNbItems];
			for(int n=0; n<nNbItems; n++)
			{
				CompositeTypeDescItem itemDesc = m_arrItemDesc.get(n);
				openTypes[n] = itemDesc.m_openType;
				itemTypeNames[n] = itemDesc.m_csName;
				itemTypeDescriptions[n] = itemDesc.m_csDescription;			
			}
			
			CompositeType compositeType = new CompositeType(
				m_csName,
			    m_csDescription,
			    itemTypeNames,
			    itemTypeDescriptions,
			    openTypes);
			return compositeType;
		}
		catch (OpenDataException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	private String m_csName = null;
	private String m_csDescription = null;
	private ArrayList<CompositeTypeDescItem> m_arrItemDesc = new ArrayList<CompositeTypeDescItem>(); 
}
