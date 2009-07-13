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

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class ConcatTo
{
	private Concat m_concat = null;
	
	ConcatTo(Concat concat)
	{
		m_concat = concat;
	}
	
	public boolean failed()
	{
		return m_concat.failed();
	}
}
