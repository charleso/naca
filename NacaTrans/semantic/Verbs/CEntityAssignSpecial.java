/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package semantic.Verbs;

import generate.CBaseLanguageExporter;
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import utils.CObjectCatalog;

/**
 * @author S. Charton
 * @version $Id: CEntityAssignSpecial.java,v 1.1 2006/07/25 10:36:16 u930cv Exp $
 */
public abstract class CEntityAssignSpecial extends CBaseActionEntity
{
	protected CDataEntity source = null ;
	protected CDataEntity destination = null ;
	protected boolean arithmeticAssign = false ;
	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CEntityAssignSpecial(int line, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, cat, out);
	}
	/**
	 * @param source The source to set.
	 */
	public void setSource(CDataEntity source)
	{
		this.source = source;
	}
	/**
	 * @param destination The destination to set.
	 */
	public void setDestination(CDataEntity destination)
	{
		this.destination = destination;
	}
	/**
	 * @param arithmeticAssign The arithmeticAssign to set.
	 */
	public void setArithmeticAssign(boolean arithmeticAssign)
	{
		this.arithmeticAssign = arithmeticAssign;
	}


}
