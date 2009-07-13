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
public class CallParamByRef extends CCallParam
{
	public CallParamByRef(Var var)
	{
		m_var = var;
		m_edit = null;
	}
	
	public CallParamByRef(Edit edit)
	{
		m_var = null;
		m_edit = edit;
	}
	
	public int getParamLength()
	{
		if(m_var != null)
			return m_var.getLength();
		if(m_edit != null)
			return m_edit.getLength();
		return 0;
	}
	
	public void MapOn(Var varLinkageSection)
	{
		if(m_var != null)
			varLinkageSection.setAtAdress(m_var);
		else
			varLinkageSection.setAtAdress(m_edit);
	}

	private Var m_var;
	private Edit m_edit;
}
