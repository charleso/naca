/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.threads;

public abstract class ThreadPoolRequest
{
	public ThreadPoolRequest(boolean bTerminaison)
	{
		m_bTerminaisonRequest = bTerminaison;
	}

	public boolean getTerminaisonRequest()
	{
		return m_bTerminaisonRequest;
	};
	
	protected void setNotTerminaisonRequest()
	{
		m_bTerminaisonRequest = false;
	};
	
	/*!	
	Execute (virtual)
	\retval: ULONG: return code of the execution
	\note This function mus be override in derivated
	*/
	public abstract void execute();
	
	private	boolean m_bTerminaisonRequest;
}
