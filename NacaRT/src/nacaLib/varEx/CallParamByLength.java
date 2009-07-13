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
public class CallParamByLength extends CCallParam
{
	public CallParamByLength(VarAndEdit var)
	{
		if(var != null)
			m_nLength = var.getTotalSize();
	}
	
	public int getParamLength()
	{
		return m_nLength;
	}
	
	public void MapOn(Var varLinkageSection)
	{
		varLinkageSection.set(m_nLength);	// Copy the value of the argument provided by the caller
	}
	
	
	private int m_nLength = 0;
}
