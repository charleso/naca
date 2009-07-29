/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 21 oct. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.CESM;

import java.util.ArrayList;

import jlib.log.Log;
import nacaLib.base.CJMapObject;
import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.basePrgEnv.BaseProgramLoader;
import nacaLib.misc.CCommarea;
import nacaLib.varEx.CCallParam;
import nacaLib.varEx.Var;

public class CESMLink extends CJMapObject
{
	protected BaseEnvironment m_Environment = null ;
	protected String m_csProgramClassName = null;
	
	public CESMLink(BaseEnvironment env, String csProgramClassName)
	{
		m_Environment = env;
		m_csProgramClassName = csProgramClassName;
	}
	
	public void go()
	{
		if(isLogCESM)
			Log.logDebug("Linking program: "+m_csProgramClassName);
		BaseProgramLoader baseProgramLoader = BaseProgramLoader.GetProgramLoaderInstance();
		baseProgramLoader.runSubProgram(m_csProgramClassName, null, m_Environment ,null);
	}

	public void commarea(Var var, int length)
	{
		if(isLogCESM)
			Log.logDebug("Linking program: "+m_csProgramClassName);
		BaseProgramLoader baseProgramLoader = BaseProgramLoader.GetProgramLoaderInstance();
		CCommarea comm = new CCommarea() ;
		m_Environment.setCommarea(comm);
		comm.setVarPassedByRef(var);
		baseProgramLoader.runSubProgram(m_csProgramClassName, null, m_Environment, null);
	}
	
	public void commarea(Var var)
	{
		commarea(var, -1) ;
	}
	
	public void commarea(Var v, Var length)
	{
		int l = length.getInt() ;		
		commarea(v, l) ;
	}
	
	public void commarea(Var var, int length, int datalength)
	{
		commarea(var, length) ;
	}
	
	public void commarea(Var var, Var length, int datalength)
	{
		int l = length.getInt() ;		
		commarea(var, l) ;
	}
}