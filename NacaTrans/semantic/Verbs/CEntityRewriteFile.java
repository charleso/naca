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
import semantic.CEntityFileDescriptor;
import utils.CObjectCatalog;

public abstract class CEntityRewriteFile extends CBaseActionEntity
{

	public CEntityRewriteFile(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	public void setFileDescriptor(CEntityFileDescriptor efd, CDataEntity data)
	{
		m_eFileDescriptor = efd ;
		m_eDataFrom = data ;
	}
	protected CEntityFileDescriptor m_eFileDescriptor = null  ;
	protected CDataEntity m_eDataFrom = null ;

}
