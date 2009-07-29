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
 * Created on 16 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

/**
 * @author u930di
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DeclareTypeEditInMapRedefineNum extends DeclareTypeBase
{
	private NumericValue m_numericValue = null;
	
	public DeclareTypeEditInMapRedefineNum()
	{
	}
	
	public void set(VarLevel varLevel, NumericValue numericValue)
	{
		super.set(varLevel);
		m_numericValue = numericValue;
	}
	
	public VarDefBuffer createVarDef(VarDefBuffer varDefParent)
	{
		VarDefBuffer varDef = new VarDefEditInMapRedefineNum(varDefParent, this);
		return varDef;		
	}
	
	public CInitialValue getInitialValue()
	{
		return null;
	}
	
	NumericValue getNumericValue()
	{
		return m_numericValue;
	}
}
