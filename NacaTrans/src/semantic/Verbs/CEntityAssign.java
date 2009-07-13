/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 3 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.Verbs;

import generate.CBaseLanguageExporter;

import java.util.Vector;

import parser.expression.CStringTerminal;
import parser.expression.CTerminal;

import semantic.CBaseActionEntity;
import semantic.CBaseEntityFactory;
import semantic.CDataEntity;
import utils.*;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityAssign extends CBaseActionEntity
{

	/**
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CEntityAssign(int l, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, cat, out);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseSemanticEntity#DoExport()
	 */
	public boolean SetValue(CDataEntity e)
	{
		m_Value = e ;
		return true ;
	}
	
	public void AddRefTo(CDataEntity id)
	{
		m_arrRefTo.add(id) ;
	}
	protected CDataEntity GetRefTo(int i)
	{
		if (i >= m_arrRefTo.size())
		{
			return null ;
		}
		else
		{
			return m_arrRefTo.get(i) ;
		}
	}
	protected int GetNbRefTo()
	{
		return m_arrRefTo.size() ;
	}
	
	protected CDataEntity m_Value = null ;
	protected boolean m_bFillAll = false ;
	protected boolean m_bMoveCorresponding = false ;
	private Vector<CDataEntity> m_arrRefTo = new Vector<CDataEntity>() ;
	public void Clear()
	{
		super.Clear();
		m_arrRefTo.clear() ;
	}

	public void SetFillAll(boolean bFillAll)
	{
		m_bFillAll = bFillAll ;
	}

	public void SetAssignCorresponding(boolean bCorr)
	{
		m_bMoveCorresponding = bCorr ;
	}
	
	public boolean ignore()
	{
		if (m_Value == null || m_Value.ignore())
		{
			return true ;
		}
		else
		{
			boolean ignore = true ;
			for (int i=0; i<m_arrRefTo.size(); i++)
			{
				CDataEntity e = m_arrRefTo.get(i);
				ignore &= e.ignore() ;
			}
			if (ignore)
			{
				int n=0;
			}
			return ignore ;
		}
	}
	public boolean IgnoreVariable(CDataEntity data)
	{
		if (m_Value == data)
		{
			m_Value = null ;
			data.UnRegisterReadingAction(this) ;
			return true ;
		}
		else
		{
			if (m_arrRefTo.remove(data))
			{
				data.UnRegisterWritingAction(this) ;
				return true ;
			}
		}
		return false ;
	}
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		if (m_Value == field)
		{
			m_Value.UnRegisterReadingAction(this) ;
			if (m_arrRefTo.contains(var))
			{
				m_Value = null ;
			}
			else
			{
				m_Value = var ;
				var.RegisterReadingAction(this) ;
			}
			return true ;
		} 
		else if (m_arrRefTo.contains(field))
		{
			field.UnRegisterWritingAction(this) ;
			if (m_Value == var || m_arrRefTo.contains(var))
			{
				m_arrRefTo.remove(field);
			}
			else
			{
				int n = m_arrRefTo.indexOf(field) ;
				m_arrRefTo.set(n, var) ;
				var.RegisterWritingAction(this) ;
			}
			return true ;
		}
		return false ;
	}

	public CBaseActionEntity GetSpecialAssignement(String val, CBaseEntityFactory factory)
	{
		if (m_arrRefTo.size() == 1)
		{
			CDataEntity ref = m_arrRefTo.get(0) ;
			CTerminal term = new CStringTerminal(val) ;
			CBaseActionEntity act = ref.GetSpecialAssignment(term, factory, getLine()) ;
			return act ;
		}
		return null;
	}
	public CDataEntity getValueAssigned()
	{
		return m_Value ;
	}
	public Vector getVarsAssigned()
	{
		return m_arrRefTo ;
	}

}
