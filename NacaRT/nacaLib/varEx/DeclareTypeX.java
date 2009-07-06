/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 12 nov. 2004
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


public class DeclareTypeX extends DeclareTypeBase
{		
	private int m_nLength = 0;
	private boolean m_bJustifyRight = false;
	private CInitialValue m_InitialValue = null;
	
	public DeclareTypeX()
	{
	}
	
	public void set(VarLevel varLevel, int nLength)
	{
		super.set(varLevel);
		m_nLength = nLength;
		m_bJustifyRight = false;
		m_InitialValue = null;
	}
	
	int getLength()
	{
		return m_nLength;
	}
	
	boolean getJustifyRight()
	{
		return m_bJustifyRight;
	}
	
	public VarAlphaNum var()
	{
		VarAlphaNum var2X = new VarAlphaNum(this);
		return var2X;
	}
	
	public VarAlphaNum filler()
	{
		VarAlphaNum var2X = new VarAlphaNum(this);
		var2X.declareAsFiller();
		//return null;
		return var2X;
	}
	
	public VarDefBuffer createVarDef(VarDefBuffer varDefParent)
	{
		VarDefBuffer varDef = new VarDefX(varDefParent, this);
		return varDef;		
	}
		
	/**
	 * 
	 */

	public DeclareTypeX value(String cs)
	{
		if(getProgramManager().isFirstInstance())
			m_InitialValue = new CInitialValue(cs, false);
		return this;
	}
	
	public DeclareTypeX valueAll(char c)
	{
		if(getProgramManager().isFirstInstance())
			m_InitialValue = new CInitialValue(c, true);
		return this;
	}

	public DeclareTypeX valueAll(String cs)
	{
		if(getProgramManager().isFirstInstance())
			m_InitialValue = new CInitialValue(cs, true);
		return this;
	}
	
	public DeclareTypeX valueSpaces()
	{
		//m_InitialValue = new CInitialValue(CobolConstant.Space.getValue(), true);
		if(getProgramManager().isFirstInstance())
			m_InitialValue = CInitialValueStd.Spaces;
		return this;
	}

	public DeclareTypeX valueZero()
	{
		//m_InitialValue = new CInitialValue(CobolConstant.Zero.getValue(), true);
		if(getProgramManager().isFirstInstance())
			m_InitialValue = CInitialValueStd.Zero;
		return this;
	}

	public DeclareTypeX valueHighValue()
	{
		//m_InitialValue = new CInitialValue(CobolConstant.HighValue.getValue(), true);
		if(getProgramManager().isFirstInstance())
			m_InitialValue = CInitialValueStd.HighValue;
		return this;
	}

	public DeclareTypeX valueLowValue()
	{
		//m_InitialValue = new CInitialValue(CobolConstant.LowValue.getValue(), true);
		if(getProgramManager().isFirstInstance())
			m_InitialValue = CInitialValueStd.LowValue;
		return this;
	} 
	
	public CInitialValue getInitialValue()
	{
		return m_InitialValue;
	}
	
	public DeclareTypeX justifyRight()
	{
		m_bJustifyRight = true;
		return this ;
	}
}
