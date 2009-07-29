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

import java.sql.Types;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class ColDescriptionInfo
{
	public String m_csColName = null;
	public String m_csTypeName = null;
	public int m_nTypeId = 0;
	public int m_nPrecision = 0;
	public int m_nScale = 0;
	
	public ColDescriptionInfo()
	{
	}
	
	String getColName()
	{
		return m_csColName;
	}
	
	public int getPrecision()
	{
		return m_nPrecision;
	}

	public int getScale()
	{
		return m_nScale;
	}
	
	public BaseDbColDefinition makeDbColDefinition()
	{
		BaseDbColDefinition dbColDef = null;
		if(m_nTypeId == Types.CHAR)
		{
			dbColDef = new DbColDefinitionChar(this);
		}
		else if(m_nTypeId == Types.DECIMAL)	// Unsupported type in jlib, but supported in nacaRT
		{
			dbColDef = new DbColDefinitionDecimal(this);
		}			
		else if(m_nTypeId == Types.TIME)
		{
			dbColDef = new DbColDefinitionTime(this);
		}
		else if(m_nTypeId == Types.TIMESTAMP)
		{
			dbColDef = new DbColDefinitionTimestamp(this);
		}
		else if(m_nTypeId == Types.DATE)
		{
			dbColDef = new DbColDefinitionDate(this);
		}
		else if(m_nTypeId == Types.VARCHAR)
		{
			dbColDef = new DbColDefinitionVarchar(this);
		}
		else if(m_nTypeId == Types.LONGVARCHAR)
		{
			dbColDef = new DbColDefinitionLongVarchar(this);
		}
		else if(m_nTypeId == Types.SMALLINT)
		{
			dbColDef = new DbColDefinitionSmallint(this);
		}
		else if(m_nTypeId == Types.INTEGER)
		{
			dbColDef = new DbColDefinitionInteger(this);
		}
		else if(m_nTypeId == Types.DOUBLE)
		{
			dbColDef = new DbColDefinitionDouble(this);
		}

		return dbColDef;
	}
	
	public String toString()
	{
		return m_csColName + ";" + m_nTypeId + ";" + m_nPrecision + ";" + m_nScale; 
	}
	
}
