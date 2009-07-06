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

import java.util.Vector;

import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import utils.*;


/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityCallProgram extends CBaseActionEntity
{
	/**
	 * @param cat
	 * @param out
	 */
	public CEntityCallProgram(int l, CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity Reference)
	{
		super(l, cat, out);
		m_Reference = Reference ;
		cat.RegisterCallProgram(this) ;
	}
	
	public void SetParameterByRef(CDataEntity e)
	{
		CCallParameter p = new CCallParameter(e, CCallParameterMethode.BY_REFERENCE);
		m_arrParameters.add(p);		
	}
	public void SetParameterByContent(CDataEntity e)
	{
		CCallParameter p = new CCallParameter(e, CCallParameterMethode.BY_CONTENT);
		m_arrParameters.add(p);		
	}
	public void SetParameterByValue(CDataEntity e)
	{
		CCallParameter p = new CCallParameter(e, CCallParameterMethode.BY_VALUE);
		m_arrParameters.add(p);		
	}
	public void SetParameterLengthOf(CDataEntity e)
	{
		CCallParameter p = new CCallParameter(e, CCallParameterMethode.LENGTH_OF);
		m_arrParameters.add(p);		
	}
	
	protected boolean m_bChecked = false ;
	protected CDataEntity m_Reference = null ;
	protected Vector<CCallParameter> m_arrParameters = new Vector<CCallParameter>() ;
	public void Clear()
	{
		super.Clear() ;
		m_Reference = null ;
		m_arrParameters.clear();
	}
	protected static class CCallParameterMethode
	{
		public static CCallParameterMethode BY_REFERENCE = new CCallParameterMethode();
		public static CCallParameterMethode BY_CONTENT = new CCallParameterMethode();
		public static CCallParameterMethode BY_VALUE = new CCallParameterMethode();
		public static CCallParameterMethode LENGTH_OF = new CCallParameterMethode();
	}
	protected class CCallParameter
	{
		public CCallParameter(CDataEntity e, CCallParameterMethode m)
		{
			m_Reference = e ;
			m_Methode = m ;
		}
		public CDataEntity m_Reference ;
		public CCallParameterMethode m_Methode ;
	}
	public boolean ignore()
	{
		return false ;
	}
	public boolean IgnoreVariable(CDataEntity data)
	{
		if (m_Reference == data)
		{
			data.UnRegisterReadingAction(this) ;
			m_Reference = null ;
			return true ;
		}
		for (int i=0; i<m_arrParameters.size(); i++)
		{
			CCallParameter param = m_arrParameters.get(i) ;
			if (param.m_Reference == data)
			{
				data.UnRegisterReadingAction(this) ;
				param.m_Reference = null ;
				return true ;
			}
		}		
		return false ;
	}
	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		if (m_Reference == field)
		{
			m_Reference = var ;
			field.UnRegisterReadingAction(this) ;
			var.RegisterReadingAction(this) ;
			return true ;
		}
		for (int i=0; i<m_arrParameters.size(); i++)
		{
			CCallParameter param = m_arrParameters.get(i) ;
			if (param.m_Reference == field)
			{
				param.m_Reference = var ;
				field.UnRegisterReadingAction(this) ;
				var.RegisterReadingAction(this) ;
				return true ;
			}
		}		
		return false ;
	}
	
	public CDataEntity getProgramReference()
	{
		return m_Reference ;
	}

	public void setChecked(boolean bChecked)
	{
		m_bChecked = bChecked ;
	}

	public void UpdateProgramReference(CDataEntity newProgram)
	{
		m_Reference = newProgram ;		
	}
}
