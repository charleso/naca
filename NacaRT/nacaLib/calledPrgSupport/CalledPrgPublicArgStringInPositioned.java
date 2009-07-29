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
package nacaLib.calledPrgSupport;

import nacaLib.varEx.Var;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class CalledPrgPublicArgStringInPositioned extends BaseCalledPrgPublicArgPositioned
{
	CalledPrgPublicArgStringInPositioned(String csValue)
	{
		super();
		m_csValue = csValue;
	}
	
	public void MapOn(Var varLinkageSection)
	{
		varLinkageSection.set(m_csValue);
	}
	
	public void doFillWithVar(Var varSource)
	{
		m_csValue = varSource.getString();		
	}
	
	public int getParamLength()
	{
		return m_csValue.length();
	}
	
	public Var getCallerSourceVar()
	{
		return null;
	}
	
	private String m_csValue = null;
}
