/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 19 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.Verbs;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import semantic.expression.CBaseEntityExpression;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntitySetConstant extends CBaseActionEntity
{

	/* (non-Javadoc)
	 * @see semantic.CBaseActionEntity#getValueAssigned()
	 */
	@Override
	public CDataEntity getValueAssigned()
	{
		return m_CsteValue ;
	}

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntitySetConstant(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}

	public void SetToZero(CDataEntity var)
	{
		m_Variable = var ;
		if (var == null)
		{
			int n=0 ;
		}
		m_bSetToZero = true ; 
		m_bSetToSpace = false ; 
		m_bSetToLowValue = false ;
		m_bSetToHighValue = false ; 
	}
	public void SetToSpace(CDataEntity var)
	{
		if (var == null)
		{
			int n=0 ;
		}
		m_Variable = var ;
		m_bSetToZero = false ; 
		m_bSetToSpace = true ; 
		m_bSetToLowValue = false ;
		m_bSetToHighValue = false ; 
	}
	public void SetToHighValue(CDataEntity var)
	{
		if (var == null)
		{
			int n=0 ;
		}
		m_Variable = var ;
		m_bSetToZero = false ; 
		m_bSetToSpace = false ; 
		m_bSetToLowValue = false ;
		m_bSetToHighValue = true ; 
	}
	public void SetToLowValue(CDataEntity var)
	{
		if (var == null)
		{
			int n=0 ;
		}
		m_Variable = var ;
		m_bSetToZero = false ; 
		m_bSetToSpace = false ; 
		m_bSetToHighValue = false ;
		m_bSetToLowValue = true ; 
	}
	public void SetCsteValue(CDataEntity var, CDataEntity val)
	{
		if (var == null)
		{
			int n=0 ;
		}
		m_Variable = var ;
		m_bSetToZero = false ; 
		m_bSetToSpace = false ; 
		m_bSetToLowValue = false ;
		m_bSetToHighValue = false ;
		m_CsteValue = val ;
	}

	public void SetSubStringRef(CBaseEntityExpression s, CBaseEntityExpression l)
	{
		m_SubStringRefStart = s ;
		m_SubStringRefLength = l ;
	}
	public void SetCondition(CDataEntity cond, boolean bCond)
	{
		if (cond == null)
		{
			int n=0 ;
		}
		m_Variable = cond ;
		m_bSetToZero = false ; 
		m_bSetToSpace = false ; 
		m_bSetToLowValue = false ;
		m_bSetToHighValue = false ;
		m_CsteValue = null ;
		m_bSetToTrue = bCond ;
		m_bSetToFalse = !bCond ;
	}
	
	protected CDataEntity m_Variable = null ;
	protected boolean m_bSetToZero = false ; 
	protected boolean m_bSetToSpace = false ; 
	protected boolean m_bSetToLowValue = false ;
	protected boolean m_bSetToHighValue = false ;
	protected boolean m_bSetToTrue = false ;
	protected boolean m_bSetToFalse = false ;

	protected CDataEntity m_CsteValue = null ; 
	protected CBaseEntityExpression m_SubStringRefStart = null ;
	protected CBaseEntityExpression m_SubStringRefLength = null ;
	public void Clear()
	{
		super.Clear() ;
		m_CsteValue = null ;
		m_Variable =  null ;
		if (m_SubStringRefLength!=null)
		{
			m_SubStringRefLength.Clear() ;
			m_SubStringRefLength = null ;
		}
		if (m_SubStringRefStart!=null)
		{
			m_SubStringRefStart.Clear() ;
			m_SubStringRefStart = null ;
		}
	}
	public boolean ignore()
	{
		return m_Variable == null ;
	}
	public boolean IgnoreVariable(CDataEntity data)
	{
		if (data == m_Variable)
		{
			m_Variable = null ;
			data.UnRegisterWritingAction(this) ;
			return true ;
		}
		return false  ;
	}
	public boolean ReplaceVariable(CDataEntity var, CDataEntity by)
	{
		if (m_Variable == var)
		{
			m_Variable = by ;
			var.UnRegisterWritingAction(this);
			by.RegisterWritingAction(this) ;
			return true ;
		}
		return false ;
	}
}
