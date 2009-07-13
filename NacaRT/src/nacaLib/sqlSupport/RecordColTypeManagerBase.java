/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package nacaLib.sqlSupport;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jlib.sql.DbPreparedStatement;
import nacaLib.varEx.VarBase;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public abstract class RecordColTypeManagerBase
{
	RecordColTypeManagerBase(int nColSourceIndex)
	{
		m_nColSourceIndex = nColSourceIndex;
	}
	
	abstract boolean fillColValue(ResultSet rs, VarBase varInto);
	public abstract boolean transfer(int nColumnNumber1Based, ResultSet resultSetSource, PreparedStatement insertStatementInsert);
	
	protected int m_nColSourceIndex;
}
