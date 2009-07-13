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
 * @version $Id: CalledPrgPublicArgStringOutPositioned.java,v 1.2 2007/09/21 15:11:30 u930bm Exp $
 */
public class CalledPrgPublicArgStringOutPositioned extends BaseCalledPrgPublicArgPositioned
{
	CalledPrgPublicArgStringOutPositioned(String csValue[], boolean bInOut)
	{
		super(bInOut);
		m_csValue = csValue;
	}
	
	public void MapOn(Var varLinkageSection)
	{
		varLinkageSection.set(m_csValue[0]);
	}
	
	public void doFillWithVar(Var varSource)
	{
		m_csValue[0] = varSource.getString();
		//BasicLogger.log("SpPublicArgIntStringOut::doFillOutputVar; m_csValue[0]="+m_csValue[0]);		
	}
	
	public int getParamLength()
	{
		return m_csValue[0].length();
	}
	
	private String m_csValue[] = null;
}