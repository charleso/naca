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
public class CalledPrgPublicArgDoubleInPositioned extends BaseCalledPrgPublicArgPositioned
{
	CalledPrgPublicArgDoubleInPositioned(double dValue)
	{
		super();
		m_dValue = dValue;
	}
	
	public void MapOn(Var varLinkageSection)
	{
		varLinkageSection.set(m_dValue);
	}
	
	public void doFillWithVar(Var varSource)
	{
		m_dValue = varSource.getDouble();		
	}
	
	public int getParamLength()
	{
		return 8;
	}
	
	public Var getCallerSourceVar()
	{
		return null;
	}

	private double m_dValue;
}