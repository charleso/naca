/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on Oct 19, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.CESM;

import nacaLib.base.*;
import nacaLib.varEx.*;

public class CESMWriteQueue extends CJMapObject
{
	protected boolean m_bTransient = false ;
	protected String m_Name = "" ;
	protected CESMQueueManager m_Manager = null;
	protected int m_nItemPosition = 0 ;
	
	public CESMWriteQueue(boolean bTransient, String name, CESMQueueManager manager)
	{
		m_bTransient = bTransient ;
		m_Name = name ;
		m_Manager = manager ;
	}
	
	public CESMWriteQueue from(Var varSource, Var tsLong)
	{
		return from(varSource, tsLong.getInt());
	}
	public CESMWriteQueue from(Var varSource, int tsLong)
	{
		if (tsLong > varSource.getLength())
		{
			tsLong = varSource.getLength();
		}
		InternalCharBuffer charBufferCopy = varSource.exportToCharBuffer(tsLong);
		if (m_Manager != null)
		{
			if (m_bRewrite)
			{
				m_Manager.writeTempQueue(m_Name, charBufferCopy, m_nItemPosition - 1) ;
			}
			else
			{
				m_nItemPosition = m_Manager.writeTempQueue(m_Name, charBufferCopy) ;
			}
		}
		return this;
	}
	
	public CESMWriteQueue from(Var varSource)
	{
		InternalCharBuffer charBufferCopy = varSource.exportToCharBuffer();
		if (m_Manager != null)
		{
			if (m_bRewrite)
			{
				m_Manager.writeTempQueue(m_Name, charBufferCopy, m_nItemPosition - 1) ;
			}
			else
			{
				m_nItemPosition = m_Manager.writeTempQueue(m_Name, charBufferCopy) ;
			}
		}
		return this;
	}

	public CESMWriteQueue item(Var tsItem)
	{
		tsItem.set(m_nItemPosition) ;
		return this ;
	}

//	public CESMWriteQueue main()
//	{
//		// unsupported
//		return this ;
//	}

	public CESMWriteQueue rewrite(int item)
	{
		m_bRewrite = true ;
		m_nItemPosition = item ;
		return this ;
	}
	protected boolean m_bRewrite = false ;
	/**
	 * 
	 */
//	public CESMWriteQueue auxiliary()
//	{
//		return this ;
//	}
}


