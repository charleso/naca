/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 26 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

/**
 * @author PJD
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DeclareTypeMapRedefine extends DeclareTypeBase
{
	private int m_nLength = 0;
	Form m_formRedefineOrigin = null;
	
	public DeclareTypeMapRedefine()
	{
	}
		
	public void set(VarLevel varLevel, Form formRedefineOrigin)
	{
		super.set(varLevel);
		m_formRedefineOrigin = formRedefineOrigin;
		m_nLength = 0;
	}	
	
	int getLength()
	{
		return m_nLength;
	}
	
	public VarDefBuffer createVarDef(VarDefBuffer varDefParent)
	{
		VarDefMapRedefine varDef = new VarDefMapRedefine(varDefParent, this);
		varDef.m_varDefFormRedefineOrigin = m_formRedefineOrigin.getDefForm(); 
		return varDef;		
	}
	
	public CInitialValue getInitialValue()
	{
		return null;
	}
}
