/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Aug 26, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.expression;

import semantic.CEntityNamedCondition;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityIsNamedCondition extends CUnitaryEntityCondition
{
	public void SetCondition(CEntityNamedCondition cond)
	{
		m_Reference = cond ;
	} 
	protected boolean m_bOpposite = false ;
	public boolean ignore()
	{
		return m_Reference.ignore();
	}
	public boolean isBinaryCondition()
	{
		return false;
	}

}
