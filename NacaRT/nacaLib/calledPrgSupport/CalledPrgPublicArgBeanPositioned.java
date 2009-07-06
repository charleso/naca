/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.calledPrgSupport;

import nacaLib.batchOOApi.FillerReadWriteExt;
import nacaLib.batchOOApi.ModeReadWriteExt;
import nacaLib.varEx.Var;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: CalledPrgPublicArgBeanPositioned.java,v 1.1 2007/09/21 15:11:30 u930bm Exp $
 */
public abstract class CalledPrgPublicArgBeanPositioned extends BaseCalledPrgPublicArgPositioned
{
	private FillerReadWriteExt m_filler = new FillerReadWriteExt();
	private String m_csValue = null;
	
	public CalledPrgPublicArgBeanPositioned()
	{
		super();
	}
	
	public CalledPrgPublicArgBeanPositioned(boolean bInOut)
	{
		super(bInOut);
	}
	
	public FillerReadWriteExt getFiller()
	{
		return m_filler;
	}
	
	public void MapOn(Var varLinkageSection)
	{
		getFiller().setMode(ModeReadWriteExt.Write);
		getFiller().allocOrResetBufferExt();
		fillRW();
		varLinkageSection.set(getFiller().getBuffer().getString());
		getFiller().setMode(ModeReadWriteExt.Unknown);
	}
	
	public void doFillWithVar(Var varSource)
	{
		m_csValue = varSource.getString();
		getFiller().setMode(ModeReadWriteExt.Read);
		getFiller().allocOrResetBufferExt();
		getFiller().getBuffer().setStringAt(0, m_csValue, m_csValue.length());
		fillRW();
		getFiller().setMode(ModeReadWriteExt.Unknown);
	}
	
	public int getParamLength()
	{
		return m_csValue.length();
	}
	
	public abstract void fillRW();
}