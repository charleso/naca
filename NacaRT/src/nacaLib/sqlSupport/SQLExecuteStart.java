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

import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.varEx.VarAndEdit;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: SQLExecuteStart.java,v 1.6 2007/02/02 06:12:01 u930di Exp $
 */
public class SQLExecuteStart extends SQL
{
	// No statement cache managed, as the clause is dynamic
	// PJD: To be removed
	public SQLExecuteStart(BaseProgramManager programManager, String csQuery)
	{
		super(programManager);
	}
	
	public SQLExecuteStart(BaseProgramManager programManager)	//, String csQuery)
	{
		super(programManager);
	}
	
	public SQL param(String csName, String csStatement)
	{
		csStatement = csStatement.trim();
		SQL sql = new SQL(m_programManager, csStatement, null/*, ""*/, 0);
		return sql;
	}

	public SQL param(int nName, String csStatement)
	{
		csStatement = csStatement.trim();
		SQL sql = new SQL(m_programManager, csStatement, null/*, ""*/, 0);
		return sql;
	}
	
	public SQL param(String csName, VarAndEdit var)
	{
		String cs = var.getString();
		cs = cs.substring(2);	// Skip leading 2 bytes
		return param(csName, cs);
	}	
	
	public SQL param(int nName, VarAndEdit var)
	{
		String cs = var.getString();
		cs = cs.substring(2);	// Skip leading 2 bytes
		return param(nName, cs);
	}
}
