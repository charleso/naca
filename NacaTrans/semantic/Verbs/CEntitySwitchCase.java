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
 * Created on 5 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.Verbs;

import java.util.Iterator;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import utils.*;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntitySwitchCase extends CBaseActionEntity
{

	/**
	 * @param cat
	 * @param out
	 */
	public CEntitySwitchCase(int l, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, cat, out);
	}
	public boolean ignore()
	{
		return isChildrenIgnored() ;
	}
	public boolean hasExplicitGetOut()
	{
		Iterator iter = m_lstChildren.iterator() ;
		boolean bExplicit = true ;
		while (iter.hasNext())
		{
			CBaseActionEntity act = (CBaseActionEntity)iter.next() ;
			bExplicit &= act.hasExplicitGetOut() ;
		}
		return bExplicit ;
	}
}
