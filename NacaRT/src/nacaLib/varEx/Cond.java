/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 1 sept. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 * @author PJD
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.varEx;

import java.util.ArrayList;
import nacaLib.base.CJMapObject;

public class Cond extends CJMapObject 
{
	public Cond(Var varParent, DeclareTypeCond declareTypeCond)
	{
		m_var = varParent;
		m_arrValues = declareTypeCond.m_arrValues;
	}
	
	public String getSTCheckValue()
	{
		return toString();
	}

	private Cond(Var varParent, Cond condValue)
	{
		m_var = varParent;
		m_arrValues = condValue.m_arrValues;	
	}

	public void setTrue()
	{
		int nNbValues = m_arrValues.size();
		if(nNbValues > 0)
		{
			CondValue condValue = (CondValue)m_arrValues.get(0);
			String s = condValue.getMin();
			if(s != null)
				m_var.set(s);
		}
	}
	public boolean is()
	{
		int nNbValues = m_arrValues.size();
		for(int n=0; n<nNbValues; n++)
		{			
			CondValue condValue = (CondValue)m_arrValues.get(n);
			if(condValue.is(m_var))
				return true;
		}
		return false;		
	}
	
	public Cond getAt(Var x_Cmaj)
	{
		return getAt(x_Cmaj.getInt());
	}
	
	public Cond getAt(int x_Cmaj)	// 1 based
	{
		Var var = m_var.getAt(x_Cmaj);
		return new Cond(var, this);
	}
	
	public Cond getAt(VarAndEdit x, VarAndEdit y)
	{
		return getAt(x.getInt(), y.getInt());
	}
	
	public Cond getAt(int x, VarAndEdit y)
	{
		return getAt(x, y.getInt());
	}
	
	public Cond getAt(int x, int y)
	{
		return new Cond(m_var.getAt(x, y), this);
	}
	
	public void setName(String csName)
	{
		m_csName = csName;
	}
	
	public String toString()
	{
		String cs = "Cond {";
		for(int n=0; n<m_arrValues.size(); n++)
		{
			if(n != 0)
				cs += "; ";
			CondValue condValue = (CondValue)m_arrValues.get(n);
			cs += condValue.toString();
		}
		cs += "}";
		return cs;
	}

	@SuppressWarnings("unused")
	private String m_csName = null;
	private Var m_var = null;
	private ArrayList<CondValue> m_arrValues = null;	// Array of CondValue
}
