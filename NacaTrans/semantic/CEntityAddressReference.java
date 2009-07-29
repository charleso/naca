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
 * Created on Aug 25, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic;

import generate.*;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityAddressReference extends CBaseDataReference
{
	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CEntityAddressReference(CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity ref)
	{
		super(0, "", cat, out);
		m_Reference = ref ;
	}
	public CDataEntityType GetDataType()
	{
		return CDataEntityType.ADDRESS ;
	}
//	protected CDataEntity m_Reference = null ;

	public boolean ignore()
	{
		return m_Reference.ignore() ;
	}
	public String GetConstantValue()
	{
		return "" ;
	} 	 
}
