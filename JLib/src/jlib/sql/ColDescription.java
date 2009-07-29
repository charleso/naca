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

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import jlib.log.Log;


/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class ColDescription extends ColDescriptionInfo
{
	ColDescription()
	{
	}


	boolean fill(ResultSet col)	// Fill from a resiultSet of the catalog of a table; not a resultSet of the data themselves
	{
		try
		{
			m_csColName = col.getString("COLUMN_NAME");
			m_nTypeId = col.getInt("DATA_TYPE");
			m_csTypeName = col.getString("TYPE_NAME");
			m_nScale = col.getInt("DECIMAL_DIGITS");
			m_nPrecision = col.getInt("COLUMN_SIZE");
			
			return true;
		}
		catch (SQLException e)
		{
			Log.logCritical("Exception catched While filling DB table's Column Description:" + e.toString());
		}
		return false;
	}
	
	boolean fill(ResultSetMetaData mt, int nColId)	// Fill from the meta data of a result set
	{
		try
		{
			m_csColName = mt.getColumnName(nColId);
			m_nTypeId = mt.getColumnType(nColId);
			m_nPrecision = mt.getPrecision(nColId);
			m_nScale = mt.getScale(nColId);
			return true;
		}
		catch (SQLException e)
		{
			Log.logCritical("Exception catched While filling DB table's Column Description:" + e.toString());
		}
		return false;
	}
}
