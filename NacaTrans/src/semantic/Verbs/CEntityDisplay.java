/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 4 oct. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.Verbs;

import java.util.Vector;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityDisplay extends CBaseActionEntity
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityDisplay(int line, CObjectCatalog cat, CBaseLanguageExporter out, Upon t)
	{
		super(line, cat, out);
		m_upon = t ;
	}
	public void AddItemToDisplay(CDataEntity e)
	{
		m_arrItemsToDisplay.add(e) ; 
	}
	
	protected Vector<CDataEntity> m_arrItemsToDisplay = new Vector<CDataEntity>();
	protected Upon m_upon = Upon.DEFAULT ;
	public void Clear()
	{
		super.Clear() ;
		m_arrItemsToDisplay.clear();
	}
	public boolean ignore()
	{
		boolean ignore = true ;
		for (int i=0; i<m_arrItemsToDisplay.size(); i++)
		{
			CDataEntity e = m_arrItemsToDisplay.get(i);
			ignore &= e.ignore() ;
		}
		return ignore ;
	}
	
	public static enum Upon
	{
		DEFAULT, CONSOLE, ENVINONMENT,
	}
}
