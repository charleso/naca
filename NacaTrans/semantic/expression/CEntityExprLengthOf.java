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
 * Created on Sep 29, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.expression;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityExprLengthOf extends CBaseEntityExpression
{
	public CEntityExprLengthOf(CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super();
	}
	
	public void setVariable(CDataEntity dataEntity)
	{
		m_dataEntity = dataEntity;
	}
	
	public boolean ignore()
	{
		if(m_dataEntity != null)
			return m_dataEntity.ignore();
		return true;
	}
	
	protected CDataEntity m_dataEntity = null;
}
