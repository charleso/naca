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
 * Created on 2 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic;

import java.util.Vector;

import generate.*;
import utils.*;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CBaseActionEntity extends CBaseLanguageEntity
{

	/**
	 * @param name
	 * @param cat
	 */
	public CBaseActionEntity(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, "", cat, out);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseSemanticEntity#RegisterMySelfToCatalog()
	 */
	protected void RegisterMySelfToCatalog()
	{
		// nothing
	}

	public boolean IgnoreVariable(CDataEntity data)
	{
		// nothing
		return false ;
	}

	public boolean ReplaceVariable(CDataEntity field, CDataEntity var)
	{
		// nothing
		return false ;
	}

	/**
	 * @param val
	 * @param factory
	 * @return
	 */
	public CBaseActionEntity GetSpecialAssignement(String val, CBaseEntityFactory factory)
	{
		// have to be overwritten
		return null;
	}

	/**
	 * @param newCond
	 */
	public boolean Replace(CBaseActionEntity newCond)
	{
		// have to be overwritten
		return m_parent.UpdateAction(this, newCond) ;
	}

	/**
	 * @return
	 */
	public CDataEntity getValueAssigned()
	{
		// have to be overwritten
		return null;
	}
	public Vector getVarsAssigned()
	{
		// have to be overwritten
		return null;
	}

	/**
	 * @return
	 */
	public boolean hasExplicitGetOut()
	{
		return false ;
	}
}
