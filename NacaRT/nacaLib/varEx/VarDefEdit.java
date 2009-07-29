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

public abstract class VarDefEdit extends VarDefBuffer
{
	public VarDefEdit(VarDefBase varDefParent, VarLevel varLevel)
	{
		super(varDefParent, varLevel);
	}
	
	protected VarDefEdit()
	{
		super();
	}
	
	void transfer(VarBufferPos bufferSource, Var Dest)
	{
	}
	
	protected boolean isAVarDefMapRedefine()
	{
		return false;
	}

	public int getSingleItemRequiredStorageSize()
	{
		return getHeaderLength() + getBodyLength();
	}
}
