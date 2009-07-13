/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 29 nov. 04
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

import nacaLib.base.CJMapObject;
import nacaLib.basePrgEnv.BaseProgram;

public class ParamDeclaration extends CJMapObject
{	
	public ParamDeclaration(BaseProgram Program)
	{
		m_Program = Program;
	}
	
	public ParamDeclaration using(Var var)
	{
		m_Program.getProgramManager().using(var);
		return this;
	}
	
	public BaseProgram getProgram()
	{
		return m_Program;
	}
	
	protected BaseProgram m_Program = null;
}
