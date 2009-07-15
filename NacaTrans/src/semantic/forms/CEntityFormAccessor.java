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
import parser.expression.CTerminal;
import semantic.CBaseActionEntity;
import semantic.CBaseDataReference;
import semantic.CDataEntity;
import semantic.CBaseEntityFactory;
import semantic.CEntityNoAction;
import semantic.Verbs.CEntityInitialize;
import semantic.Verbs.CEntitySetConstant;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityFormAccessor extends CBaseDataReference
{
	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 * @param type
	 * @param owner
	 */
	public CEntityFormAccessor(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out, CEntityResourceForm owner)
	{
		super(l, name, cat, out);
		m_Owner = owner ;
		m_Reference = owner ;
		m_parent = owner ;
	}
	public CEntityResourceForm GetForm()
	{
		return m_Owner ;
	}
	protected CEntityResourceForm m_Owner = null ;
	public void Clear()
	{
		super.Clear();
		m_Owner = null ;
	}
	public CBaseActionEntity GetSpecialAssignment(CTerminal term, CBaseEntityFactory factory, int l)
	{
		String value = term.GetValue() ;
		CEntitySetConstant eAssign = factory.NewEntitySetConstant(l) ;
		if (value.equals("ZERO") || value.equals("ZEROS") || value.equals("ZEROES"))
		{
			eAssign.SetToZero(m_Owner) ;
		}
		else if (value.equals("SPACE") || value.equals("SPACES"))
		{
			eAssign.SetToSpace(m_Owner) ;
		}
		else if (value.equals("LOW-VALUE") || value.equals("LOW-VALUES"))
		{
			CEntityInitialize init = factory.NewEntityInitialize(l, m_Owner);
			m_Owner.RegisterWritingAction(init);
			return init ;
			//eAssign.SetToLowValue(m_Owner) ;
		}
		else
		{
			return null ;
		}
		m_Owner.RegisterWritingAction(eAssign);
		return eAssign ;
	}
	public boolean ignore()
	{
		return false ;
	}
	
	protected boolean m_bVirtual = false ;
	public void setVirtual()
	{
		m_bVirtual = true ;		
	}
	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#GetDataType()
	 */
	public CDataEntityType GetDataType()
	{
		if (m_bVirtual)
		{
			return CDataEntityType.VIRTUAL_FORM ;
		}
		else
		{
			return CDataEntityType.FORM ;
		}
	}
	public String GetConstantValue()
	{
		return "" ;
	} 	 
	public CBaseActionEntity GetSpecialAssignment(CDataEntity term, CBaseEntityFactory factory, int l)
	{
		if (term.GetDataType() == CDataEntityType.FORM && !m_Owner.IsSaveCopy())
		{
			CEntityNoAction act = factory.NewEntityNoAction(l) ; 
			factory.m_ProgramCatalog.RegisterMapCopy(act) ;
			return act ;
		}
		else
		{
			return null;
		}
	}
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var, boolean bRead)
	{
		boolean b = super.ReplaceVariable(field, var, bRead) ;
		if (field == m_Owner)
		{
			m_Owner = (CEntityResourceForm)var ;
			if (bRead)
			{
				var.RegisterReadReference(this) ;
				field.UnRegisterReadReference(this) ;
			}
			else
			{
				var.RegisterWriteReference(this) ;
				field.UnRegisterWriteReference(this) ;
			}
			return true ;
		}		
		return b ;
	}
	public CEntityResourceForm getSaveCopy()
	{
		return m_Owner.getSaveCopy() ;
	}
//	protected void RegisterMySelfToCatalog()
//	{
//		m_ProgramCatalog.RegisterDataEntity(GetName(), this) ;
//		m_ProgramCatalog.RegisterDataEntity("S" + GetName(), this) ;
//	}
}
