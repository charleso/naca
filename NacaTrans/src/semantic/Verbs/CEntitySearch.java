/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 28 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package semantic.Verbs;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import semantic.CEntityBloc;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class CEntitySearch extends CBaseActionEntity
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntitySearch(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	/**
	 * @param var
	 * @param index
	 */
	public void setVariable(CDataEntity var, CDataEntity index)
	{
		m_eVariable = var ;
		m_eIndex = index ;
	}
	protected CDataEntity m_eVariable = null ;
	protected CDataEntity m_eIndex = null ;
	protected CEntityBloc m_blocElse = null;
	/**
	 * @param bloc
	 */
	public void setElseBloc(CEntityBloc bloc)
	{
		m_blocElse = bloc ;
	}


}
