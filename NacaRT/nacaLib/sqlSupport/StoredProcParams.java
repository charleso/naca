/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
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

import java.sql.ResultSet;
import java.util.ArrayList;

import jlib.sql.DbPreparedCallableStatement;
import jlib.sql.StoredProcParamDescBase;


/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class StoredProcParams
{
	private ArrayList<StoredProcParamDesc> m_arrParamDesc = null;
	
	public StoredProcParams()
	{
		m_arrParamDesc = new ArrayList<StoredProcParamDesc>();
	}
	
	public void addAParam(ResultSet rsParams)
	{
		StoredProcParamDesc param = new StoredProcParamDesc();
		if(param.fill(rsParams))
			m_arrParamDesc.add(param);
	}
	
	public int getNbParamToProvide()
	{
		return m_arrParamDesc.size();
	}
	
	public StoredProcParamDesc get(int nParamId)
	{
		return m_arrParamDesc.get(nParamId);
	}
	
	public boolean registerInOutParameters(DbPreparedCallableStatement callableStatement)
	{
		boolean b = true;
		if(callableStatement != null)
		{
			for(int n=0; n<getNbParamToProvide() && b; n++)
			{
				StoredProcParamDescBase paramDesc = get(n);
				b = paramDesc.registerIntoCallableStatement(n, callableStatement);
			}
		}
		return b;
	}
	
	void retrieveOutValues(PreparedCallableStatement preparedCallableStatement, CSQLStatus sqlStatus)
	{
		if(preparedCallableStatement != null)
		{
			for(int n=0; n<m_arrParamDesc.size(); n++)
			{
				StoredProcParamDesc paramDesc = m_arrParamDesc.get(n);
				paramDesc.retrieveOutValues(n, preparedCallableStatement, sqlStatus);
			}
		}
	}
}
