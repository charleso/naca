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
 * Created on 22 oct. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.forms;

import generate.CBaseLanguageExporter;


import semantic.CDataEntity;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityFieldOccurs extends CEntityResourceField
{
	protected CDataEntity m_Occurs = null ;
	protected String m_csLevel = "" ;
	public void Clear()
	{
		super.Clear() ;
		m_Occurs = null ;
	}
	
	public CEntityFieldOccurs(int l, String name, CObjectCatalog cat, CBaseLanguageExporter lexp)
	{
		super(l, name, cat, lexp);
		if (name.equals(""))
		{
			name = GetDefaultName() ;
			if (!name.equals(""))
			{
				SetName(name) ;
			}
		}
	}
	public void SetFieldOccurs(String level, CDataEntity occurs)
	{
		m_Occurs = occurs ;
		m_csLevel = level ;
	}

	public boolean IsEntryField()
	{
		return false;
	}

	public String GetTypeDecl()
	{
		return null;
	}

	public CDataEntityType GetDataType()
	{
		return CDataEntityType.FIELD;
	}
	protected void RegisterMySelfToCatalog()
	{
		String name = GetName() ;
		m_ProgramCatalog.RegisterDataEntity(name, this) ;
//		m_ProgramCatalog.RegisterDataEntity(name+"I", this) ;
//		m_ProgramCatalog.RegisterDataEntity(name+"O", this) ;
	}
}
