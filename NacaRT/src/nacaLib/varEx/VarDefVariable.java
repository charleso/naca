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


public abstract class VarDefVariable extends VarDefBuffer
{
	protected VarDefVariable()
	{
		super();
	}
	
	public VarDefVariable(VarDefBase varDefParent, VarLevel varLevel)
	{
		super(varDefParent, varLevel);
	}
	
//	VarDefVariable(VarDefVariable varDefSource)
//	{
//		super(varDefSource);
//	}
	

//	protected void assignOnOriginEdit()
//	{
//	}
	
	protected boolean isVarInMapRedefine()
	{
		if(m_varDefFormRedefineOrigin != null)
			return true;
		return false;
	}
	
	protected boolean isAVarDefMapRedefine()
	{
		return false;
	}
	
	protected boolean isEditInMapRedefine()
	{
		return false;
	}
	
	protected boolean isEditInMapOrigin()
	{
		return false;
	}
	
	protected boolean isVarDefForm()
	{
		return false;
	}
	
	void assignForm(VarDefForm varDefForm)
	{
	}

	
//	protected void inheritOriginEditSizes(VarBase varRoot)
//	{
//	}

}
