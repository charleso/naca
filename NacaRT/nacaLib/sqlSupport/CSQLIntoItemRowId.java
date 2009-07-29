/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.sqlSupport;

import oracle.sql.ROWID;

public class CSQLIntoItemRowId extends CSQLIntoItem 
{
	private ROWID m_rowId = null;
	
	public CSQLIntoItemRowId()
	{
		super(null, null);
	}
	
	public void setRowIdValue(ROWID rowid)
	{
		m_rowId = rowid;
	}
	
	public ROWID getRowIdValue()
	{
		return m_rowId;
	}
}
