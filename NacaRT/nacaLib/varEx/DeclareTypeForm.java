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
 * Created on 25 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DeclareTypeForm extends DeclareTypeBase
{
	public DeclareTypeForm()
	{
	}
	
	public void set(VarLevel varLevel)
	{
		super.set(varLevel);
	}
	
//	public Var2Edit var()
//	{
//		Var2G var2G = new Var2G(this);
//		return var2G;
//	}
//	
//	public Var2G filler()
//	{
//		Var2G var2G = new Var2G(this);
//		var2G.declareAsFiller();
//		return null;
//	}
	
	public VarDefBuffer createVarDef(VarDefBuffer varDefParent)
	{
		VarDefBuffer varDef = new VarDefForm(varDefParent, this);
		return varDef;		
	}
	
	public CInitialValue getInitialValue()
	{
		return null;
	}
}
