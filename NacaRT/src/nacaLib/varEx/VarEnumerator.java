/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 18 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

import nacaLib.basePrgEnv.BaseProgramManager;


/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class VarEnumerator
{
	public VarEnumerator(BaseProgramManager programManager, VarBase var)
	{
		m_programManager = programManager;
		m_var = var;
		if(m_var != null)
			m_varDef = m_var.getVarDef(); 
	}
	
	public VarBase getFirstVarChild()
	{
		m_nIndex = 0;
		return getNextVarChild();
	}

	public VarBase getNextVarChild()
	{
		VarBase v = getChildAtIndex(m_nIndex);
		m_nIndex++;
		return v;
	}
	
	VarBase getChildAtIndex(int nIndex)
	{
		if(m_varDef != null)
		{
			VarDefBase varDefChild = m_varDef.getChild(nIndex);
			if(varDefChild != null)
			{
				VarBase varChild = m_programManager.getVarFullName(varDefChild);
				return varChild;
			}
		}		
		return null; 
	}
	
	private int m_nIndex = 0;
	private VarBase m_var = null;
	private VarDefBase m_varDef = null;
	BaseProgramManager m_programManager = null;
}
