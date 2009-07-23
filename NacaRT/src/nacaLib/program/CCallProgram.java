/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.program;

import java.util.ArrayList;

import jlib.log.AssertException;
import jlib.log.Log;

import nacaLib.base.*;
import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.basePrgEnv.BaseProgramLoader;
import nacaLib.exceptions.AbortSessionException;
import nacaLib.varEx.CCallParam;
import nacaLib.varEx.CallParamByLength;
import nacaLib.varEx.CallParamByRef;
import nacaLib.varEx.CallParamByStringValue;
import nacaLib.varEx.CallParamByValue;
import nacaLib.varEx.Edit;
import nacaLib.varEx.Var;
import nacaLib.varEx.VarAndEdit;

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
public class CCallProgram extends CJMapObject
{

	/**
	 * @param CESMEnv Runtime environnement
	 * @param Class classPrgToCall: class to load dynamically and call
	 * Internal usage only
	 */
	public CCallProgram(BaseEnvironment env, Class classPrgToCall)
	{
		m_Environment = env ;
		m_csProgramClassName = classPrgToCall.getName();
	}

	/**
	 * @param CESMEnv Runtime environnement
	 * @param String csPrgClassName: class name to load dynamically and call
	 * Internal usage only
	 */
	public CCallProgram(BaseEnvironment env, String csPrgClassName)
	{
		m_Environment = env ;
		m_csProgramClassName = csPrgClassName ;
	}
	
	
	/**
	 * Must be called explicitly to terminate all arguments passing to a called program
	 */
	public void executeCall()
	{
		m_baseProgramLoader.runSubProgram(m_csProgramClassName, m_arrCallParam, m_Environment);
	}

	public boolean executeCallSafe()
	{
		try
		{
			executeCall();
			return true;
		}
		catch (AssertException t)
		{
			return false;
		}
	}
	
	/**
	 * @param Var var: Variable to pass by value to the called program
	 * @return this
	 * The variable var will be passed by value, so even if modified in called prog., it's value will stay unmodified in caller prog.
	 * They do not share the same variable.
	 */
	public CCallProgram usingValue(Var var)
	{
		if(m_arrCallParam == null)
			m_arrCallParam = new ArrayList<CCallParam>();
		CallParamByValue CallParam = new CallParamByValue(var);
				  
		m_arrCallParam.add(CallParam);
		return this;
	}

	/**
	 * @param Var var: Variable to pass by value to the called program
	 * @return this
	 * The variable var will be passed by value, so even if modified in called prog., it's value will stay unmodified in caller prog.
	 * They do not share the same variable.
	 */
	public CCallProgram usingContent(Var var)
	{
		if(m_arrCallParam == null)
			m_arrCallParam = new ArrayList<CCallParam>();
		CallParamByValue CallParam = new CallParamByValue(var);
				  
		m_arrCallParam.add(CallParam);
		return this;
	}

	/**
	 * @param Var var: Variable to pass by reference to the called program
	 * @return this
	 * The variable var will be passed by reference, so if it is modified in called prog., it's value will also be modified in caller prog. 
	 * They both share the same variable.
	 */
	public CCallProgram using(Var var)
	{
		if(m_arrCallParam == null)
			m_arrCallParam = new ArrayList<CCallParam>();
		CallParamByRef CallParam = new CallParamByRef(var);
		
		m_arrCallParam.add(CallParam);
		return this;
	}
	
	public CCallProgram using(Edit edit)
	{
		if(m_arrCallParam == null)
			m_arrCallParam = new ArrayList<CCallParam>();
		CallParamByRef CallParam = new CallParamByRef(edit);
		
		m_arrCallParam.add(CallParam);
		return this;
	}

	public CCallProgram using(String string)
	{
		if(m_arrCallParam == null)
			m_arrCallParam = new ArrayList<CCallParam>();
		CallParamByStringValue CallParam = new CallParamByStringValue(string);
		m_arrCallParam.add(CallParam);
		return this;
	}

	/**
	 * @param Var var: Variable to pass whose length if passed by value to the called program
	 * @return this
	 * The variable's lengthvar will be passed by value to the called program 
	 */
	public CCallProgram usingLengthOf(VarAndEdit var)
	{
		if(m_arrCallParam == null)
			m_arrCallParam = new ArrayList<CCallParam>();
		
		CallParamByLength callParam = new CallParamByLength(var);
		  
		m_arrCallParam.add(callParam);
		return this;
	}
	
	public void setProgramLoader(BaseProgramLoader baseProgramLoader)
	{
		m_baseProgramLoader = baseProgramLoader; 
	}

	protected BaseEnvironment m_Environment = null ;
	protected String m_csProgramClassName = "" ;
	private ArrayList<CCallParam> m_arrCallParam = null;	// Array of all call parameters
	private BaseProgramLoader m_baseProgramLoader = null;
	
}
