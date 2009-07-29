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
package semantic;

import generate.CBaseLanguageExporter;
import utils.CObjectCatalog;

/**
 * @author S. Charton
 * @version $Id$
 */
public abstract class CEntityFileDescriptorLengthDependency extends
				CBaseLanguageEntity
{

	protected CEntityFileDescriptor m_FileDescriptor = null ;
	protected CDataEntity m_LenghtDep = null ;

	/**
	 * @param line
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CEntityFileDescriptorLengthDependency(String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(0, name, cat, out);
	}

	public void setDependency(CEntityFileDescriptor desc, CDataEntity var)
	{
		m_FileDescriptor = desc ;
		m_LenghtDep = var ;
	}
	

	/**
	 * @see semantic.CBaseLanguageEntity#RegisterMySelfToCatalog()
	 */
	@Override
	protected void RegisterMySelfToCatalog()
	{
		// nothing
	}	
}
