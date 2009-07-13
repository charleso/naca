/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.varEx;

import nacaLib.bdb.BtreeSegmentKeyTypeFactory;

public class SortKeySegmentDefinition
{
	SortKeySegmentDefinition(Var var, boolean bAscending)
	{
		m_var = var;
		m_bAscending = bAscending;
	}
	
	public int getBufferStartPosKey()
	{
		int n = m_var.getOffsetFromLevel01();
		return n;
	}
	
	public int getBufferLengthKey()
	{
		return m_var.getLength();
	}
	
	public BtreeSegmentKeyTypeFactory getSegmentKeyType()
	{
		return m_var.getVarDef().getSegmentKeyTypeFactory();
	}
	
	public Var m_var = null;
	public boolean m_bAscending = true;
}
