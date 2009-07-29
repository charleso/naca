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
/**
 * 
 */
package nacaLib.stringSupport;

import nacaLib.varEx.Var;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class UnstringToManager
{
	private UnstringManager m_unstringManager = null;
	
	UnstringToManager(UnstringManager unstringManager)
	{
		m_unstringManager = unstringManager;
	}
	
	public UnstringToManager to(Var varDelimiterDest, Var varDelimiterIn, Var varCountDest)
	{
		m_unstringManager.doInto(varDelimiterDest, varDelimiterIn, varCountDest);
		return this;
	}
	
	public UnstringToManager to(Var varDelimiterDest)
	{
		m_unstringManager.doInto(varDelimiterDest, null, null);
		return this;
	}
	
	public boolean failed()
	{
		if(m_unstringManager == null)
			return true;
		return m_unstringManager.failed();
	}
	
	public boolean notFailed()
	{
		if(m_unstringManager == null)
			return false;
		return !m_unstringManager.failed();
	}
}
