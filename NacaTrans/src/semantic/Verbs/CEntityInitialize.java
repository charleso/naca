/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 5 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.Verbs;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import utils.*;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityInitialize extends CBaseActionEntity
{

	/**
	 * @param cat
	 * @param out
	 */
	public CEntityInitialize(int l, CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity data)
	{
		super(l, cat, out);
		m_data = data ;
	}

	protected CDataEntity m_data = null ;

	protected CDataEntity m_RepNumWith = null ;
	public void ReplaceNumWith(CDataEntity d)
	{
		m_RepNumWith = d ;
	}

	protected CDataEntity m_RepNumEditedWith = null ;
	public void ReplaceNumEditedWith(CDataEntity d)
	{
		m_RepNumEditedWith = d ;
	}

	protected CDataEntity m_RepAlphaWith = null ;
	public void ReplaceAlphaNumWith(CDataEntity d)
	{
		m_RepAlphaWith = d ;
	}

	protected CDataEntity m_FillAlphaWith = null ;
	public void FillAlphaNumWith(CDataEntity d)
	{
		m_FillAlphaWith = d ;
	}
	public void Clear()
	{
		super.Clear() ;
		m_data = null ;
		m_FillAlphaWith = null ;
		m_RepAlphaWith = null ;
		m_RepNumEditedWith = null ;
		m_RepNumWith = null ;
	}
	public boolean ignore()
	{
		return m_data == null || m_data.ignore();
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseActionEntity#ReplaceVariable(semantic.CDataEntity, semantic.CDataEntity)
	 */
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		if (m_data == field)
		{
			m_data = var ;
			field.UnRegisterWritingAction(this);
			var.RegisterWritingAction(this) ;
			return true ;
		}
		else if (m_FillAlphaWith == field)
		{
			m_FillAlphaWith = var ;
			field.UnRegisterReadingAction(this);
			var.RegisterReadingAction(this) ;
			return true ;
		}
		else if (m_RepAlphaWith == field)
		{
			m_RepAlphaWith = var ;
			field.UnRegisterReadingAction(this);
			var.RegisterReadingAction(this) ;
			return true ;
		}
		else if (m_RepNumEditedWith == field)
		{
			m_RepNumEditedWith = var ;
			field.UnRegisterReadingAction(this);
			var.RegisterReadingAction(this) ;
			return true ;
		}
		else if (m_RepNumWith == field)
		{
			m_RepNumWith = var ;
			field.UnRegisterReadingAction(this);
			var.RegisterReadingAction(this) ;
			return true ;
		}
		return false ;
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseActionEntity#IgnoreVariable(semantic.CDataEntity)
	 */
	public boolean IgnoreVariable(CDataEntity field)
	{
		if (m_data == field)
		{
			m_data = null ;
			field.UnRegisterWritingAction(this);
			return true ;
		}
		else if (m_FillAlphaWith == field)
		{
			m_FillAlphaWith = null ;
			field.UnRegisterReadingAction(this);
			return true ;
		}
		else if (m_RepAlphaWith == field)
		{
			m_RepAlphaWith = null ;
			field.UnRegisterReadingAction(this);
			return true ;
		}
		else if (m_RepNumEditedWith == field)
		{
			m_RepNumEditedWith = null ;
			field.UnRegisterReadingAction(this);
			return true ;
		}
		else if (m_RepNumWith == field)
		{
			m_RepNumWith = null ;
			field.UnRegisterReadingAction(this);
			return true ;
		}
		return false ;
	}

}
