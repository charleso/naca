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
package jlib.sql;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;

import jlib.sqlMapper.RecordId;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class ColValueCollection
{
	private ArrayList<ColValue> m_arrCols = null;
	private Hashtable<String, ColValue> m_hashColsByName = null;
	
	public ColValueCollection()
	{
		if(m_arrCols == null)
			m_arrCols = new ArrayList<ColValue>();
		if(m_hashColsByName == null)
			m_hashColsByName = new Hashtable<String, ColValue>(); 
	}
	
	synchronized public boolean isStored(String csColName)
	{
		ColValue colValue = getColValueByNameCaseInsensitive(csColName);
		if(colValue == null)
			return false;
		return true;
	}
	
	public String getAsString(String csColName)
	{
		ColValue colValue = getColValueByNameCaseInsensitive(csColName);
		if(colValue != null)
			return colValue.getValueAsString();
		return null;
	}
	
	public int getAsInt(String csColName)
	{
		ColValue colValue = getColValueByNameCaseInsensitive(csColName);
		if(colValue != null)
			return colValue.getValueAsInt();
		return 0;
	}
	
	public double getAsDouble(String csColName)
	{
		ColValue colValue = getColValueByNameCaseInsensitive(csColName);
		if(colValue != null)
			return colValue.getValueAsDouble();
		return 0.0;
	}
	
	public synchronized ColValue getColValueByNameCaseInsensitive(String csColName)
	{	
		csColName = csColName.toUpperCase();	// Case insensitive access
		ColValue colValue = m_hashColsByName.get(csColName);
		return colValue;
	}
		
	synchronized public ColValue getColValueAtIndex(int n)
	{
		return m_arrCols.get(n);
	}	
	
	synchronized protected void replaceInternalContainer(ColValueCollection colValueCollectionSource)
	{
		m_arrCols = colValueCollectionSource.m_arrCols;
		m_hashColsByName = colValueCollectionSource.m_hashColsByName;
	}
	
	synchronized public void clearValues()
	{
		m_arrCols.clear();
		m_hashColsByName.clear();
	}
	
	
	synchronized public void add(ColValue colValue)
	{
		m_arrCols.add(colValue);
		m_hashColsByName.put(colValue.getNameUppercase(), colValue);
	}
	
	public void add(String csName, String csValue)
	{
		ColValue colValue = new ColValueString(csName, csValue);
		add(colValue);
	}	

	public void add(String csName, int nValue)
	{
		ColValue colValue = new ColValueInt(csName, nValue);
		add(colValue);
	}
	
	public void add(String csName, long lValue)
	{
		ColValue colValue = new ColValueLong(csName, lValue);
		add(colValue);
	}	
	
	public void add(String csName, double dValue)
	{
		ColValue colValue = new ColValueDouble(csName, dValue);
		add(colValue);
	}
	
	public void add(String csName, boolean bValue)
	{
		ColValue colValue = new ColValueBoolean(csName, bValue);
		add(colValue);
	}
	
	public void add(String csName, BigDecimal bdValue)
	{
		ColValue colValue = new ColValueBigDecimal(csName, bdValue);
		add(colValue);
	}
	
	public void add(String csName, Timestamp tsValue)
	{
		ColValue colValue = new ColValueTimestamp(csName, tsValue);
		add(colValue);
	}
	
	public void add(String csName, Date dtValue)
	{
		ColValue colValue = new ColValueDate(csName, dtValue);
		add(colValue);
	}
	
	public int getNbColValues()
	{
		return m_arrCols.size();
	}

	synchronized public String toString()
	{
		StringBuilder sb = new StringBuilder();
		int nNbCols = getNbColValues();
		{
			for(int nCol=0; nCol<nNbCols; nCol++)
			{
				ColValue colValue = getColValueAtIndex(nCol);
				String cs = colValue.toString();
				sb.append(cs+"; ");
			}
		}
		return sb.toString();
	}
}
