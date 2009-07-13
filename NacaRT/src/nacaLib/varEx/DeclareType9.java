/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 11 nov. 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.varEx;

import nacaLib.tempCache.TempCache;
import nacaLib.tempCache.TempCacheLocator;


public class DeclareType9 extends DeclareTypeBase
{
	protected NumericValue m_numericValue = new NumericValue(); 
	private CInitialValue m_InitialValue = null;
	boolean m_bBlankWhenZero = false;
	
	public DeclareType9()
	{
	}
	
	public void set(VarLevel varLevel, boolean bSigned, int nNbDigitInteger, int nNbDigitDecimal)
	{
		super.set(varLevel);
		m_numericValue.set(bSigned, nNbDigitInteger, nNbDigitDecimal);
		m_InitialValue = null;
		m_bBlankWhenZero = false;
	}
	
	public VarDefBuffer createVarDef(VarDefBuffer varDefParent)
	{
		VarDefBuffer varDef = m_numericValue.createVarDef(varDefParent, this);
		return varDef;		
	}
	
	public VarNum var()
	{
		VarNum var = m_numericValue.createVar(this);
		return var;
	}

	public VarNum filler()
	{
		VarNum var = m_numericValue.createVar(this);
		var.declareAsFiller();
		return null;
	}	
	
	public DeclareType9 signLeadingSeparated()
	{
		m_numericValue.setSignLeadingSeparated(true);
		return this;
	}

	public DeclareType9 signTrailingSeparated()
	{
		m_numericValue.setSignLeadingSeparated(false);
		return this;
	}		
	
	public DeclareType9 comp3()
	{
		m_numericValue.m_nComp = -3;
		return this;		
	}

	public DeclareType9 comp()
	{
		m_numericValue.m_nComp = -4;
		return this;		
	}
	
	public DeclareType9 value(double d)
	{
		if(getProgramManager().isFirstInstance())
			m_InitialValue = new CInitialValue(d, false);
		return this;
	}
	
	public DeclareType9 value(String s)
	{
		if(getProgramManager().isFirstInstance())
			m_InitialValue = new CInitialValue(s, false);
		return this;
	}
		
	public DeclareType9 value(int n)
	{
		if(getProgramManager().isFirstInstance())
			m_InitialValue = new CInitialValue(n, false);
		return this;
	}

	public DeclareType9 valueSpaces()
	{
		//m_InitialValue = new CInitialValue(CobolConstant.Space.getValue(), true);
		if(getProgramManager().isFirstInstance())
			m_InitialValue = CInitialValueStd.Spaces;
		return this;
	}

	//	
//	// private VarLevelManager m_varLevelManager = null;
//
//
	public DeclareType9 valueZero()
	{
		//m_InitialValue = new CInitialValue(CobolConstant.Zero.getValue(), false);
		if(getProgramManager().isFirstInstance())
			m_InitialValue = new CInitialValue(0, false);
		return this ;
	}

	public DeclareType9 sync()
	{
		return this;
	}
	
	public CInitialValue getInitialValue()
	{
		return m_InitialValue;
	}
	
	

	public DeclareType9 blankWhenZero()
	{
		m_bBlankWhenZero = true;
		return this;
	}
	
	/**
	 * @return
	 */
	public Edit edit()
	{
		TempCache tempCache = TempCacheLocator.getTLSTempCache();
		DeclareTypeEditInMapRedefineNum declareTypeEditInMapRedefineNum = tempCache.getDeclareTypeEditInMapRedefineNum();
		declareTypeEditInMapRedefineNum.set(getLevel(), m_numericValue);
		
		EditInMapRedefineNum var2Edit = new EditInMapRedefineNum(declareTypeEditInMapRedefineNum);
		return var2Edit;
	}
}