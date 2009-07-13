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
 * @version $Id: CalledPrgPublicArgIntInPositioned.java,v 1.2 2007/09/21 15:11:30 u930bm Exp $
 */
public class CalledPrgPublicArgIntInPositioned extends BaseCalledPrgPublicArgPositioned
{
	CalledPrgPublicArgIntInPositioned(int nValue)
	{
		super();
		m_nValue = nValue;
	}
	
	public void MapOn(Var varLinkageSection)
	{
		varLinkageSection.set(m_nValue);
	}
	
	public void doFillWithVar(Var varSource)
	{
		m_nValue = varSource.getInt();		
	}
	
	public int getParamLength()
	{
		return 4;
	}
		
	private int m_nValue;
}
