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
 * Created on 18 janv. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.sqlSupport;

// PJD ROWID Support:import oracle.sql.ROWID;
import oracle.sql.ROWID;
import jlib.log.Log;
import nacaLib.base.CJMapObject;
import nacaLib.varEx.Var;
import nacaLib.varEx.VarAndEdit;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public class CSQLIntoItem extends CJMapObject
{
	public CSQLIntoItem(VarAndEdit varInto, Var varIndicator)
	{
		m_varInto = varInto;
		m_varIndicator = varIndicator;
	}
	public void set(VarAndEdit varInto, Var varIndicator)
	{
		m_varInto = varInto;
		m_varIndicator = varIndicator;
	}
	

	public CSQLIntoItem()
	{
		m_varIndicator = null;
	}
	
	public void setRowIdValue(ROWID rowid)	// Overloaded in CSQLIntoItemRowId
	{		
		int gg =0 ;
	}
	
	public ROWID getRowIdValue()		// Overloaded in CSQLIntoItemRowId
	{
		return null;
	}

	public void setColValue(String csValue, boolean bNull)	//, String csSemanticContext)
	{
		if(m_varInto != null)
		{			
			m_varInto.m_varDef.write(m_varInto.m_bufferPos, csValue);	//m_varInto.set(csValue);
			
			//Sytem.out.println("setColValue: m_varInto="+m_varInto.toString());
			//m_varInto.setSemanticContextValue(csSemanticContext);
		}
		if(m_varIndicator != null)
		{
			if(bNull)
				m_varIndicator.set(-1);	// The col is SQL NULL
			else
				m_varIndicator.set(0);	// The col is not sql null
		}
		if(isLogSql)
			Log.logDebug("sql into filling:"+getLoggableValue());		
	}
	
	public void setColValueNull(boolean bNull)
	{
		if(m_varIndicator != null)
		{
			if(bNull)
				m_varIndicator.set(-1);	// The col is SQL NULL
			else
				m_varIndicator.set(0);	// The col is not sql null
		}
	}
	
	// PJD ROWID Support:
	/*
	public void setColValue(ROWID rowId)
	{
		m_RowId = rowId;
	}
	*/
	
	public boolean getIndicatorNull()
	{
		if(m_varIndicator != null)
		{
			int n = m_varIndicator.getInt();
			if(n == -1)
				return true;	// SQL NULL
		}
		return false;
	}
		
	public VarAndEdit getVarInto()
	{
		return m_varInto;	// null for entries created by missingFetchVariables()
	}
		
	public Var getVarIndicator()
	{
		return m_varIndicator;
	}
	
	public String getLoggableValue()
	{
		if(m_varInto != null)
		{
			if(m_varIndicator != null)
				return "into="+m_varInto.getLoggableValue() + " Indicator="+m_varIndicator.getLoggableValue();
			else
				return "into="+m_varInto.getLoggableValue() + " IndicatorNull";
		}
		return "into=Null";
	}
	
	public long getUniqueHashedId()
	{
		long l = 0;
		if(m_varInto != null)
			l = m_varInto.getId();
		if(m_varIndicator != null)
		{
			l *= 32678;
			l += m_varIndicator.getId();
		}
		return l;
	}

	// PJD ROWID Support:
	/*
	public ROWID getRowId()
	{
		return m_RowId;
	}
	*/
	
	private VarAndEdit m_varInto = null;
	private Var m_varIndicator = null;	
	// PJD ROWID Support:private oracle.sql.ROWID m_RowId;
}
