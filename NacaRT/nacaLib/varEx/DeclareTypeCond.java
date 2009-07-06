/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 26 nov. 2004
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
import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.basePrgEnv.BaseProgramManager;

public class DeclareTypeCond extends CJMapObject
{
	public DeclareTypeCond()
	{
	}
	
	public void set(BaseProgram program)
	{
		m_ProgramManager = program.getProgramManager();
		m_arrValues = new ArrayList<CondValue>();
	}
	
	public DeclareTypeCond value(String s)
	{
		CondValue condValue = new CondValue(s);
		m_arrValues.add(condValue);
		return this;
	}

	public DeclareTypeCond value(String sMin, String sMax)
	{
		CondValue condValue = new CondValue(sMin, sMax);
		m_arrValues.add(condValue);
		return this;
	}
	
	public DeclareTypeCond value(int nMin, int nMax)
	{
		String sMin = String.valueOf(nMin);
		String sMax = String.valueOf(nMax);

		CondValue condValue = new CondValue(sMin, sMax);
		m_arrValues.add(condValue);
		return this;
	}
	
	public DeclareTypeCond value(int n)
	{
		String s = String.valueOf(n);

		CondValue condValue = new CondValue(s);
		m_arrValues.add(condValue);
		return this;
	}

	public DeclareTypeCond value(CobolConstantBase constant)
	{
		CondValue condValue = new CondValue(constant);
		m_arrValues.add(condValue);
		return this;
	}
	
	public Cond var()
	{
		Var varParent = (Var)m_ProgramManager.getLastVarCreated();

		Cond cond = new Cond(varParent, this);
		return cond;
	}
	
	ArrayList<CondValue> m_arrValues = null;
	BaseProgramManager m_ProgramManager = null;
}
