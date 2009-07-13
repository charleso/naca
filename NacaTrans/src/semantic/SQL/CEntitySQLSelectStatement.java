/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 18 août 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.SQL;
import java.util.Vector;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import utils.CObjectCatalog;

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntitySQLSelectStatement extends CBaseActionEntity
{
	public CEntitySQLSelectStatement(int line, CObjectCatalog cat, CBaseLanguageExporter out, String csStatement, Vector<CDataEntity> arrParameters, Vector<CDataEntity> arrInto, Vector<CDataEntity> arrInd)
	{
		super(line, cat, out);
		m_csStatement = csStatement ;
		m_arrParameters = arrParameters;
		m_arrInto = arrInto;
		m_arrInd = arrInd ;
	}
	protected String m_csStatement = "" ;
	protected Vector<CDataEntity> m_arrParameters = null;
	protected Vector<CDataEntity> m_arrInto = null;
	protected Vector<CDataEntity> m_arrInd = null;
	public void Clear()
	{
		super.Clear();
		m_arrParameters = null ;
		m_arrInto = null ;
	}
	public boolean ignore()
	{
		return m_csStatement.equals("") ;
	}
	public boolean IgnoreVariable(CDataEntity data)
	{
		if (m_arrParameters.contains(data) || m_arrInto.contains(data))
		{
			data.UnRegisterReadingAction(this) ;
			data.UnRegisterWritingAction(this) ;
			m_csStatement = "" ;
			return true ;
		}
		return false ;
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseActionEntity#ReplaceVariable(semantic.CDataEntity, semantic.CDataEntity)
	 */
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		int n = m_arrParameters.indexOf(field) ;
		if (n>=0)
		{
			m_arrParameters.get(n).UnRegisterReadingAction(this) ;
			m_arrParameters.set(n, var);
			var.RegisterReadingAction(this) ;
			return true ;
		}
		n = m_arrInto.indexOf(field) ;
		if (n>=0)
		{
			m_arrInto.get(n).UnRegisterWritingAction(this) ;
			m_arrInto.set(n, var);
			var.RegisterWritingAction(this) ;
			return true ;
		}
		return false ;
	}

}

