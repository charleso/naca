/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package semantic.SQL;

import java.util.Vector;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import utils.CObjectCatalog;

/**
 * @author S. Charton
 * @version $Id: CEntitySQLCall.java,v 1.1 2006/08/04 08:32:52 u930cv Exp $
 */
public abstract class CEntitySQLCall extends CBaseActionEntity
{

	protected CDataEntity m_ProgramReference = null ;
	protected Vector<CDataEntity> m_arrParameters = new Vector<CDataEntity>() ;
	
	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntitySQLCall(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	/**
	 * @param prgRef
	 */
	public void setReference(CDataEntity prgRef)
	{
		m_ProgramReference = prgRef ;
	}

	/**
	 * @param param
	 */
	public void addParameter(CDataEntity param)
	{
		m_arrParameters.add(param) ;
	}



}
