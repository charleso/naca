/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.CESM;

import java.util.Queue;
import java.util.concurrent.SynchronousQueue;

import nacaLib.base.CJMapObject;
import nacaLib.varEx.Var;

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
public class CESMQueue extends CJMapObject
{
	protected Queue m_Queue = new SynchronousQueue();
	protected int m_nbElements = 0;
	protected int m_nbLastRead = 0 ;

	public int Add(Var data)
	{
		m_Queue.add(data);
		m_nbElements ++ ;
		return m_nbElements ;
	}

	public int length()
	{
		return m_nbElements ;
	}

	public int read(Var data)
	{
		if (m_nbLastRead < m_nbElements)
		{
			Var e = (Var)m_Queue.remove() ;
			data.set(e) ;
			m_nbLastRead ++ ;
			return m_nbLastRead ;
		}
		return 0;
	}
}
