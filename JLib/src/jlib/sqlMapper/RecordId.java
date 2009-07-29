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
package jlib.sqlMapper;

import java.util.ArrayList;

import jlib.sql.ColValue;
import jlib.sql.ColValueCollection;
import jlib.sql.SQLClause;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 */
public class RecordId extends ColValueCollection
{
	private String m_csName = null;	// Unique name of a record within a table, within a SQLMapper instance
	private ArrayList<OrderSegment> m_arrOrderBy = null;	// Array of the column for generating the order by statement
	private String m_csWhereExpression = null;				// Specific where expression 

	public RecordId(int nName)
	{
		super();
		m_csName = "" + nName;		
	}
	
	public RecordId(String csName)
	{
		super();
		m_csName = csName;		
	}
	
	public RecordId(StringBuffer sbName)
	{
		m_csName = sbName.toString();
	}
	
	public String getName()
	{
		return m_csName; 
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Record name: "+ m_csName + "\rKey segments:" + super.toString());		
		
		return sb.toString();		
	}
	
	boolean hasName(RecordId recordId)	// Sematic comparison
	{
		if(recordId != null)
			if(recordId.m_csName.equalsIgnoreCase(m_csName))
				return true;
		return false;
	}
		
	public RecordId orderByAscending(String csName)
	{
		OrderSegment orderBy = new OrderSegmentAscending(csName);
		if(m_arrOrderBy == null)
			m_arrOrderBy = new ArrayList<OrderSegment>();
		m_arrOrderBy.add(orderBy);
		return this;
	}
	
	public RecordId orderByDescending(String csName)
	{
		OrderSegment orderBy = new OrderSegmentDescending(csName);  
		if(m_arrOrderBy == null)
			m_arrOrderBy = new ArrayList<OrderSegment>();
		m_arrOrderBy.add(orderBy);
		return this;
	}
	
	public RecordId setWhereExpression(String csWhereExpression)
	{
		m_csWhereExpression = csWhereExpression;
		return this;
	}
	
	private String findAndUpdateMarkers(String csQuery, ArrayList<String> arrItemNames)
	{
		// Replace #xx placeholdersd by ?
		int nPosStart = csQuery.indexOf('#', 0);
		while (nPosStart != -1)
		{
			String sLeft = csQuery.substring(0, nPosStart);
			int n = nPosStart;
			n++; // Skip the #
			String sItemId = extractItemId(n, csQuery);
			if (sItemId != null)
			{
				n += sItemId.length();
				arrItemNames.add(sItemId);
				String sRight = csQuery.substring(n);
				csQuery = sLeft + "?" + sRight;
			}

			nPosStart = csQuery.indexOf('#', nPosStart);
		}
		String csQueryUpper = csQuery.toUpperCase();
		return csQueryUpper;
	}
	

	/**
	 * @return Internal usage only
	 */
	private String extractItemId(int nPos, String csQuery)
	{
		int nStart = nPos;
		int nLength = csQuery.length();
		char c = csQuery.charAt(nPos);
		while (Character.isLetterOrDigit(c) || c == '_'  || c == '-')
		{
			nPos++;
			if (nPos == nLength)
			{
				String s = csQuery.substring(nStart);
				return s;
			}

			c = csQuery.charAt(nPos);
		}
		String s = csQuery.substring(nStart, nPos);
		return s;
	}

	void buildWhereClauseAndMapParams(StringBuilder sbClause, SQLClause clause)
	{
		if(m_csWhereExpression != null)	// We specified a custom where expression
		{
			ArrayList<String> arrItemNames = new ArrayList<String>(); 
			String csQueryUpper = findAndUpdateMarkers(m_csWhereExpression, arrItemNames);
			sbClause.append(" where " + csQueryUpper);
			
			clause.set(sbClause.toString());
			for(int n=0; n<arrItemNames.size(); n++)
			{
				String csColName = arrItemNames.get(n);
				ColValue colValue = getColValueByNameCaseInsensitive(csColName);
				clause.param(colValue);	
			}
		}
		else
		{					
			int nNbKeys = getNbColValues();
			for(int nKey=0; nKey<nNbKeys; nKey++)
			{
				if(nKey != 0)
					sbClause.append(" and ");
				else
					sbClause.append(" where ");
				ColValue col = getColValueAtIndex(nKey);
				sbClause.append(col.getName() + "=? ");
			}
			
			if(m_arrOrderBy != null)
			{
				sbClause.append(" order by ");
				for(int n=0; n<m_arrOrderBy.size(); n++)
				{
					if(n != 0)
						sbClause.append(" and ");
					
					OrderSegment orderBy = m_arrOrderBy.get(n);
					String csOrderBy = orderBy.getAsString();
					sbClause.append(csOrderBy);
				}
			}
			
			clause.set(sbClause.toString());
			
			for(int nCol=0; nCol<getNbColValues(); nCol++)	// Enum all cols of the record
			{
				ColValue colValue = getColValueAtIndex(nCol);
				clause.param(colValue);			
			}
		}
	}
}
