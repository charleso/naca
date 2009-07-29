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
public class CallParamByValue extends CCallParam
{
	public CallParamByValue(Var var)
	{
		m_var = var;
	}
	
	public int getParamLength()
	{
		if(m_var != null)
			return m_var.getLength();
		return 0;
	}
	
	public void MapOn(Var varLinkageSection)
	{
		m_var.transferTo(varLinkageSection);
	}
	
	public Var getCallerSourceVar()
	{
		return m_var;
	}
	
	private Var m_var; 
}
