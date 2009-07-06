/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 8 juil. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.sqlSupport;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import jlib.exception.TechnicalException;
import jlib.sql.DbConnectionBase;
import jlib.sql.DbDriverId;
import jlib.sql.DbPreparedCallableStatement;
import jlib.sql.DbPreparedStatement;

/**
 * @author PJD
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SQLConnection extends DbConnectionBase
{
	public SQLConnection(Connection conn, String csPrefId, String csEnv, boolean bUseCachedStatements, boolean bUseJmx, DbDriverId dbDriverId)
	{
		super(conn, csPrefId, csEnv, bUseCachedStatements, bUseJmx, dbDriverId);
	}
	
	public DbPreparedStatement createAndPrepare(String csQuery, boolean bHoldability)
	{
		CSQLPreparedStatement preparedStatement = new CSQLPreparedStatement(/*this*/);
		if(preparedStatement.prepare(this, csQuery, bHoldability))
		{
			//preparedStatement.setSemanticContextDef(BaseResourceManager.getSemanticContextDef()); 
			return preparedStatement;
		}
		return null;
	}
		
	public boolean prepareCallableStatement(DbPreparedCallableStatement preparedCallableStatement, String csStoredProcName, int nNbParamToProvide)
	{
		String sql = "CALL " + csStoredProcName;
		int n=0;
		for(; n<nNbParamToProvide; n++)
		{
			if(n == 0)
				sql += " (?";
			else
				sql += ",?";
		}
		if(n != 0)
			sql += ")";
		
		try
		{
			CallableStatement callableStatement = getDbConnection().prepareCall(sql);
			if(callableStatement != null)
				preparedCallableStatement.init(callableStatement);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see jlib.sql.DbConnectionBase#createAndPrepareWithException(java.lang.String, boolean)
	 */
	@Override
	public DbPreparedStatement createAndPrepareWithException(String csQuery, boolean bHoldability) 
		throws TechnicalException
	{
		return createAndPrepare(csQuery, bHoldability);
	}
}
