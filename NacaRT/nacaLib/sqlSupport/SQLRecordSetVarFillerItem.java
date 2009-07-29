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
/*
package nacaLib.sqlSupport;

import nacaLib.varEx.VarBase;


public class SQLRecordSetVarFillerItem
{
	SQLRecordSetVarFillerItem(int nColSource, VarBase varInto, VarBase varIndicator)
	{
		m_nColSource = nColSource;
		m_varInto = varInto;
		m_varIndicator = varIndicator;
	}
	
	void apply(CSQLResultSet resultSet)
	{
		String csValue = resultSet.getColValueAsString(m_nColSource+1, m_varInto);
		if(m_varInto != null)
			m_varInto.set(csValue);
		//System.out.println("SQLRecordSetVarFillerItem::apply m_varInto="+m_varInto.toString());
	
		if(m_varIndicator != null)
		{
			if(resultSet.m_bNull)
				m_varIndicator.set(-1);	// The col is SQL NULL
			else
				m_varIndicator.set(0);	// The col is not sql null
		}
		if (resultSet.m_bNull && m_varIndicator == null)
		{
			resultSet.m_bNullError = true;
		}
	}
	
	int m_nColSource = 0;
	VarBase m_varInto = null;
	VarBase m_varIndicator = null;
}

*/
package nacaLib.sqlSupport;

import nacaLib.varEx.VarBase;

public class SQLRecordSetVarFillerItem
{
	SQLRecordSetVarFillerItem(int nColSource, VarBase varInto, VarBase varIndicator)
	{
		m_nColSource = nColSource;
		m_varInto = varInto;
		m_varIndicator = varIndicator;
	}
	
	void apply(CSQLResultSet resultSet, RecordSetCacheColTypeType recordSetCacheColTypeType)
	{
		if(m_varInto != null)
		{
			boolean bNull = resultSet.fillColValue(m_nColSource, m_varInto, recordSetCacheColTypeType);
			if(m_varIndicator == null)
			{
				if(!bNull)
					return ;
				resultSet.m_bNullError = true;
				return;
			}
			if(bNull)
				m_varIndicator.m_varDef.write(m_varIndicator.m_bufferPos, -1);	//set(-1);	// The col is SQL NULL
			else
				m_varIndicator.m_varDef.write(m_varIndicator.m_bufferPos, 0);	//m_varIndicator.set(0);	// The col is not sql null
		}
	}
	
//	void apply(CSQLResultSet resultSet)
//	{
//		String csValue = resultSet.getColValueAsString(m_nColSource+1, m_varInto);
//		if(m_varInto != null)
//			m_varInto.set(csValue);
//	
//		if(m_varIndicator != null)
//		{
//			if(resultSet.m_bNull)
//				m_varIndicator.set(-1);	// The col is SQL NULL
//			else
//				m_varIndicator.set(0);	// The col is not sql null
//		}
//		if (resultSet.m_bNull && m_varIndicator == null)
//		{
//			resultSet.m_bNullError = true;
//		}
//	}


	
	private int m_nColSource = 0;
	private VarBase m_varInto = null;
	private VarBase m_varIndicator = null;
}
