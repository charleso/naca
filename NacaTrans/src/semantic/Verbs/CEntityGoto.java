/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 6 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.Verbs;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import semantic.CEntityProcedureSection;
import semantic.CProcedureReference;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityGoto extends CBaseActionEntity
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityGoto(int line, CObjectCatalog cat, CBaseLanguageExporter out, String ref, CEntityProcedureSection sectionContainer)
	{
		super(line, cat, out);
		String sec= "";
		if (sectionContainer != null)
		{
			sec = sectionContainer.GetName();
		} 
		m_Reference = new CProcedureReference(ref, sec, cat) ;
		cat.getCallTree().RegisterGoto(this) ;
	}
	
	protected CProcedureReference m_Reference = null;
	public void Clear()
	{
		super.Clear() ;
		m_Reference.Clear() ;
		m_Reference= null ;
	}
	public boolean ignore()
	{
		return false ;
	}
	public boolean hasExplicitGetOut()
	{
		return true ;
	}
	/**
	 * @return
	 */
	public CProcedureReference getReference()
	{
		return m_Reference ;
	}
}
