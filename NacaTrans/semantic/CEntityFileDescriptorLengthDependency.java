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
 * @version $Id: CEntityFileDescriptorLengthDependency.java,v 1.1 2006/05/23 11:08:18 u930cv Exp $
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
