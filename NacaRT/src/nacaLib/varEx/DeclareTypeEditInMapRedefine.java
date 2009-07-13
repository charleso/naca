/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 27 mars 2005
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
public class DeclareTypeEditInMapRedefine extends DeclareTypeBase
{
	public DeclareTypeEditInMapRedefine()
	{
	}
	
	public void set(VarLevel varLevel)
	{
		super.set(varLevel);
	}
	
	public VarDefBuffer createVarDef(VarDefBuffer varDefParent)
	{
		VarDefBuffer varDef = new VarDefEditInMapRedefine(varDefParent, this);
		return varDef;		
	}
	
	public CInitialValue getInitialValue()
	{
		return null;
	}
}
