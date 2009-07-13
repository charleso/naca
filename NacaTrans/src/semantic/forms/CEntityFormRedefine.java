/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Aug 17, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.forms;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import semantic.CBaseEntityFactory;
import semantic.CDataEntity;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityFormRedefine extends CEntityResourceForm //CEntityAttribute
{
	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 */
	protected CDataEntity m_eForm = null ;
	//protected CEntityResourceForm m_eForm = null ;
	public void Clear()
	{
		super.Clear();
		m_eForm = null ;
	}
	
	public CEntityFormRedefine(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity eForm, boolean bSaveMap)
	{
		super(l, name, cat, out, bSaveMap);
		if (name.equals(""))
		{
			name = GetDefaultName() ;
			if (!name.equals(""))
			{
				SetName(name) ;
			}
		}
		m_eForm = eForm ;
	}
	public CDataEntityType GetDataType()
	{
		return CDataEntityType.FORM ;
	}
	public boolean ignore()
	{
		return false ;
	}
	public CBaseActionEntity GetSpecialAssignment(CDataEntity term, CBaseEntityFactory factory, int l)
	{
		return null;
	}
}
