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
 * Created on 23 mars 2005
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

public abstract class VarDefItemManager
{
	VarDefItemManager()
	{
	}
	
//	abstract VarDefBuffer createVarDefItems(ProgramManager programManager, VarDefBase varDefMaster, int x, VarDefBase varDefOccursParent);
//	abstract VarDefBuffer createVarDefItems(ProgramManager programManager, VarDefBase varDefMaster, int y, int x, VarDefBase varDefOccursParent);
//	abstract VarDefBuffer createVarDefItems(ProgramManager programManager, VarDefBase varDefMaster, int z, int y, int x, VarDefBase varDefOccursParent);
	
	abstract VarDefBuffer getAt(VarDefBase varDef, int x);
	abstract VarDefBuffer getAt(VarDefBase varDef, int y, int x);
	abstract VarDefBuffer getAt(VarDefBase varDef, int z, int y, int x);	
}
