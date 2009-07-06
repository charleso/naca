/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.calledPrgSupport;

import nacaLib.varEx.CCallParam;
import nacaLib.varEx.Var;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: BaseCalledPrgPublicArgPositioned.java,v 1.2 2007/09/21 15:11:30 u930bm Exp $
 */
public abstract class BaseCalledPrgPublicArgPositioned extends CCallParam
{
	private BaseCalledPrgPublicArgWay m_way = null;
	
	public BaseCalledPrgPublicArgPositioned()
	{
		m_way = BaseCalledPrgPublicArgWay.IN;
	}
	
	public BaseCalledPrgPublicArgPositioned(boolean bInOut)
	{
		if(bInOut)
			m_way = BaseCalledPrgPublicArgWay.INOUT;
		else
			m_way = BaseCalledPrgPublicArgWay.OUT;
	}
	
	public void fillWithVar(Var varDest)
	{
		if(m_way != BaseCalledPrgPublicArgWay.IN)
			doFillWithVar(varDest);
	}

	public abstract void doFillWithVar(Var varDest);
}
