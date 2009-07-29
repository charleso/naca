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
 * Created on Jan 10, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.forms;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import generate.CBaseLanguageExporter;
import semantic.CBaseEntityFactory;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityResourceFieldArray extends CEntityResourceField
{

	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param lexp
	 */
	public CEntityResourceFieldArray(int l, String name, CObjectCatalog cat, CBaseLanguageExporter lexp)
	{
		super(l, name, cat, lexp);
	}

	public CDataEntityType GetDataType()
	{
		return CDataEntityType.FIELD ;
	}
	public boolean IsEntryField()
	{
		return false;
	}

	public void SetArray(int nbItems, int NbCol, boolean bVerticalFilling)
	{
		m_NbItems = nbItems ;
		m_NbColumns = NbCol ;
		m_bVerticalFilling = bVerticalFilling ;
	}
	
	protected int m_NbItems = 0 ;
	protected int m_NbColumns = 0 ;
	protected boolean m_bVerticalFilling = false ;

	public void SetPosition(int Line, int Col)
	{
		m_nPosCol = Col ;
		m_nPosLine = Line ;		
	} 
	public void InitDependences(CBaseEntityFactory factory)
	{
		ListIterator iter = m_lstChildren.listIterator() ;
		try
		{
			CEntityResourceField field = (CEntityResourceField)iter.next() ;
			while (field != null)
			{
				field.InitDependences(factory) ;
				field = (CEntityResourceField)iter.next() ;
			}
		}
		catch (NoSuchElementException e)
		{
			e.printStackTrace();
		}
	} 
}
