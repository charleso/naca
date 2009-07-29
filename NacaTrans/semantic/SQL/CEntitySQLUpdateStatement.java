/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 20 août 04
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

public abstract class CEntitySQLUpdateStatement extends CBaseActionEntity
{
	public CEntitySQLUpdateStatement(int line, CObjectCatalog cat, CBaseLanguageExporter out, String csStatement, Vector<CDataEntity> arrSets, Vector<CDataEntity> arrSetsIndicators, Vector<CDataEntity> arrParameters, Vector<CDataEntity> arrParametersIndicators)
	{
		super(line, cat, out);
		
		m_csStatement = csStatement ;
		
		m_arrSets = arrSets;
		m_arrSetsIndicators = arrSetsIndicators;
		
		m_arrParameters = arrParameters;		
		m_arrParametersIndicators = arrParametersIndicators;
	}
	
	protected String m_csStatement = "" ;
	
	protected Vector<CDataEntity> m_arrSets = null;
	protected Vector<CDataEntity> m_arrSetsIndicators = null;
	
	protected Vector<CDataEntity> m_arrParameters = null;	
	protected Vector<CDataEntity> m_arrParametersIndicators = null;
	
	public void Clear()
	{
		super.Clear();
		m_arrSets.clear() ;
		m_arrParameters.clear() ;
	}
	public boolean ignore()
	{
		return false ;
	}
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		int n = m_arrParameters.indexOf(field);
		if (n>=0)
		{
			m_arrParameters.get(n).UnRegisterReadingAction(this) ;
			m_arrParameters.set(n, var);
			var.RegisterReadingAction(this) ;
			return true ;
		}
		n = m_arrSets.indexOf(field);
		if (n>=0)
		{
			m_arrSets.get(n).UnRegisterReadingAction(this) ;
			m_arrSets.set(n, var);
			var.RegisterReadingAction(this) ;
			return true ;
		}
		return false ;
	}
	/**
	 * @param cur
	 */
	public void setCursor(CEntitySQLCursor cur)
	{
		m_Cursor = cur ;
	}
	protected CEntitySQLCursor m_Cursor = null ;
}