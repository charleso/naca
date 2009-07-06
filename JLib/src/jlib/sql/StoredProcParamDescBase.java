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

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import jlib.misc.CoupleNameValueItem;
import jlib.misc.ListCoupleRender;


/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: StoredProcParamDescBase.java,v 1.2 2007/10/16 09:48:06 u930di Exp $
 */
public abstract class StoredProcParamDescBase
{
	protected String m_csProcedureCatalog = null;
	protected String m_csProcedureSchem = null;
	protected String m_csProcedureName = null;
	protected short m_sColType = 0;
	protected int m_nLength = 0;
	protected short m_sScale = 0;
	protected short m_sRadix = 0;
	protected short m_sNullable = 0;
	protected String m_csRemarks = null;	
	protected ColDescriptionInfo m_colDescriptionInfo = null;
		
	public StoredProcParamDescBase()
	{
	}
	
	public boolean isColOut()
	{
		if(m_sColType == DatabaseMetaData.procedureColumnOut)
			return true;
		return false;
	}
	
	public boolean isColInOut()
	{
		if(m_sColType == DatabaseMetaData.procedureColumnInOut)
			return true;
		return false;
	}
	
	public boolean isColIn()
	{
		if(m_sColType == DatabaseMetaData.procedureColumnIn)
			return true;
		return false;
	}
	
	
	public boolean fill(ResultSet rsParam)
	{
		try
		{
			// Procedure identification
			m_colDescriptionInfo = new ColDescriptionInfo(); 
			m_csProcedureCatalog = rsParam.getString("PROCEDURE_CAT");
			m_csProcedureSchem = rsParam.getString("PROCEDURE_SCHEM");
			m_csProcedureName = rsParam.getString("PROCEDURE_NAME");
			
			// Kind of column / parameter
			m_sColType = rsParam.getShort("COLUMN_TYPE");
			
			m_colDescriptionInfo.m_csColName = rsParam.getString("COLUMN_NAME");
			m_colDescriptionInfo.m_nTypeId = rsParam.getInt("DATA_TYPE");
			m_colDescriptionInfo.m_nPrecision = rsParam.getInt("PRECISION");
			m_nLength = rsParam.getInt("LENGTH");
			
			m_sScale = rsParam.getShort("SCALE");
			m_sRadix = rsParam.getShort("RADIX");
			m_sNullable = rsParam.getShort("NULLABLE");
			m_csRemarks = rsParam.getString("REMARKS");
			
			return true;
		}
		catch(SQLException e)
		{
		}
		return false;
	}
	
	public boolean registerIntoCallableStatement(int nParamId, DbPreparedCallableStatement callableStatement)
	{
		nParamId++;	// 1 based
		if(m_sColType == DatabaseMetaData.procedureColumnOut)
			return callableStatement.registerOutParameter(nParamId, m_colDescriptionInfo);

		if(m_sColType == DatabaseMetaData.procedureColumnInOut)
			callableStatement.registerOutParameter(nParamId, m_colDescriptionInfo);
		
		return fillInValue(nParamId, callableStatement);
	}
	
	public String toString()
	{
		ListCoupleRender lst = ListCoupleRender.set("Column description: ");
		if(m_sColType == DatabaseMetaData.procedureColumnOut)
			lst.set("Way", "Out");
		if(m_sColType == DatabaseMetaData.procedureColumnIn)
			lst.set("Way", "In");
		if(m_sColType == DatabaseMetaData.procedureColumnInOut)
			lst.set("Way", "InOut");
		
		lst.set("Name", m_colDescriptionInfo.m_csColName);
		lst.set("Type", m_colDescriptionInfo.m_nTypeId);
		lst.set("Precision", m_colDescriptionInfo.m_nPrecision);
		lst.set("Length", m_nLength);
		lst.set("Scale", m_sScale);
		lst.set("Radix", m_sRadix);
		lst.set("Nullable", m_sNullable);
		lst.set("Remarks", m_csRemarks);
		
		return lst.toString();
	}
	
	public abstract boolean fillInValue(int nParamId, DbPreparedCallableStatement callableStatement);
	public abstract String getInValueAsString();
	public abstract double getInValueAsDouble();
	public abstract int getInValueAsInt();
	public abstract short getInValueAsShort();
}
