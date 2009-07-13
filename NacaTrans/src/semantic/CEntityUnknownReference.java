/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Oct 28, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic;

import generate.CBaseLanguageExporter;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityUnknownReference extends CDataEntity
{
	/**
	 * @see semantic.CDataEntity#GetDataType()
	 */
	@Override
	public CDataEntityType GetDataType()
	{
		return CDataEntityType.UNKNWON ;
	}
	protected CEntityUnknownReference(int nLine, String csName, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(nLine, csName, cat, out);
	}
	public boolean ignore()
	{
		return false ; // maybe true
	}
	public String GetConstantValue()
	{
		return "" ;
	} 	 
}
