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
/**
 * 
 */
package nacaLib.fpacPrgEnv;

import nacaLib.varEx.CInitialValue;
import nacaLib.varEx.CobolConstant;
import nacaLib.varEx.DeclareTypeBase;
import nacaLib.varEx.VarDefBuffer;
import nacaLib.varEx.VarDefFPacRaw;
import nacaLib.varEx.VarLevel;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class DeclareTypeFPacRaw extends DeclareTypeBase
{
	public DeclareTypeFPacRaw(VarLevel varLevel, int nLength)
	{
		super(varLevel);
		m_nLength = nLength;
	}

	public int getLength()
	{
		return m_nLength;
	}
	
	private int m_nLength = 0;
	
	
	public VarFPacRaw var()
	{
		VarFPacRaw var = new VarFPacRaw(this);
		return var;
	}
	
	public VarFPacRaw filler()
	{
		VarFPacRaw var = new VarFPacRaw(this);
		var.declareAsFiller();
		//return null;
		return var;
	}
	
	public VarDefBuffer createVarDef(VarDefBuffer varDefParent)
	{
		VarDefBuffer varDef = new VarDefFPacRaw(varDefParent, this);
		return varDef;		
	}
		
	/**
	 * 
	 */

	public DeclareTypeFPacRaw value(String cs)
	{
		m_InitialValue = new CInitialValue(cs, false);
		return this;
	}
	
	public DeclareTypeFPacRaw valueAll(char c)
	{
		m_InitialValue = new CInitialValue(c, true);
		return this;
	}

	public DeclareTypeFPacRaw valueAll(String cs)
	{
		m_InitialValue = new CInitialValue(cs, true);
		return this;
	}
	
	public DeclareTypeFPacRaw valueSpaces()
	{
		m_InitialValue = new CInitialValue(CobolConstant.Space.getValue(), true);
		return this;
	}

	public DeclareTypeFPacRaw valueZero()
	{
		m_InitialValue = new CInitialValue(CobolConstant.Zero.getValue(), true);
		return this;
	}

	public DeclareTypeFPacRaw valueHighValue()
	{
		m_InitialValue = new CInitialValue(CobolConstant.HighValue.getValue(), true);
		return this;
	}

	public DeclareTypeFPacRaw valueLowValue()
	{
		m_InitialValue = new CInitialValue(CobolConstant.LowValue.getValue(), true);
		return this;
	} 
	
	public CInitialValue getInitialValue()
	{
		return m_InitialValue;
	}
	
	private CInitialValue m_InitialValue = null;
}
