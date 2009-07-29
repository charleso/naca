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

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public abstract class BaseDbColDefinition
{
	private String m_csName = null;
	
	public class ColInsertValue
	{
		public int m_nOffset = 0;
		public String m_csValue = null;
	}
	
	public BaseDbColDefinition(ColDescriptionInfo colDescription)
	{
		m_csName = colDescription.getColName();
	}
	
	public String getColumnName()
	{
		return m_csName;
	}
	
	public abstract boolean fillCallableStatementParam(int nParamId, StoredProcParamDescBase storedProcParamDescBase, DbPreparedCallableStatement callableStatement);
	public abstract byte[] getByteValue(ResultSet resultSet, int nCol1Based, boolean bEbcdicOutput);
	//public abstract int setByteValue(byte arrByteValue[], int nSourceOffset, boolean bEbcdicInput, ColValueGeneric colValueGenericDest);
	public abstract int setByteValueInStmtCol(DbColDefErrorManager dbColDefErrorManager, DbPreparedStatement stmt, int nCol, byte arrByteValue[], int nSourceOffset, boolean bEbcdicInput);
	public abstract byte[] getExcelValue(ResultSet resultSet, int nCol1Based, boolean bEbcdicOutput);
}
