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
package semantic.Verbs;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import utils.CObjectCatalog;

public abstract class CEntityAccept extends CBaseActionEntity
{
	protected enum AcceptMode
	{
		FROM_INPUT,
		FROM_DATE,
		FROM_DAY,
		FROM_DAYOFWEEK,
		FROM_TIME,
		FROM_VARIABLE
	}
	
	public CEntityAccept(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}
	
	protected CDataEntity m_eVariable = null  ;
	protected CDataEntity m_eSource = null ;
	protected AcceptMode m_eMode ;

	public void AcceptFromDate(CDataEntity var)
	{
		m_eMode = AcceptMode.FROM_DATE ;
		m_eVariable = var ;		
	}

	public void AcceptFromDay(CDataEntity var)
	{
		m_eMode = AcceptMode.FROM_DAY ;
		m_eVariable = var ;		
	}

	public void AcceptFromDayOfWeek(CDataEntity var)
	{
		m_eMode = AcceptMode.FROM_DAYOFWEEK ;
		m_eVariable = var ;		
	}

	public void AcceptFromInput(CDataEntity var)
	{
		m_eMode = AcceptMode.FROM_INPUT;
		m_eVariable = var ;		
	}

	public void AcceptFromTime(CDataEntity var)
	{
		m_eMode = AcceptMode.FROM_TIME;
		m_eVariable = var ;		
	}

	public void AcceptFromVariable(CDataEntity var, CDataEntity source)
	{
		m_eMode = AcceptMode.FROM_VARIABLE;
		m_eVariable = var ;		
		m_eSource = source ;
	}
	
	

}
