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
package generate.java.verbs;

import generate.CBaseLanguageExporter;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import semantic.CBaseLanguageEntity;
import semantic.Verbs.CEntitySwitchCase;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaSwitchCase extends CEntitySwitchCase
{

	/**
	 * @param cat
	 * @param out
	 */
	public CJavaSwitchCase(int l, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, cat, out);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	protected void DoExport()
	{
		ListIterator i = m_lstChildren.listIterator() ;
		boolean bFirst = true ;
		try
		{
			CBaseLanguageEntity le = (CBaseLanguageEntity)i.next() ;
			while (le != null)
			{
				if (!le.ignore())
				{
					if (bFirst)
					{
						bFirst = false ;
					}
					else
					{
						WriteWord("else ", le.getLine()) ;
					}
					DoExport(le);
				}
				le = (CBaseLanguageEntity)i.next() ;
			}
		}
		catch (NoSuchElementException e)
		{
			//System.out.println(e.toString());
		}


	}

}
