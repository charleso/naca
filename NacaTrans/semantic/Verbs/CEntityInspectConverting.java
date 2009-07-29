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
/**
 * 
 */
package semantic.Verbs;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import utils.CObjectCatalog;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public abstract class CEntityInspectConverting extends CBaseActionEntity
{
	public CEntityInspectConverting(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	public void SetVariable(CDataEntity e)
	{
		m_variable = e ;
	}
	
	public void SetFromTo(CDataEntity eFrom, CDataEntity eTo)
	{
		m_from = eFrom ;
		m_to = eTo ;
	}
	
	
	protected CDataEntity m_variable = null ;
	protected CDataEntity m_from = null ;
	protected CDataEntity m_to = null ;
}
