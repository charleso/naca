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
package nacaLib.varEx;

import java.util.Hashtable;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class CacheCacheMoveCorresponding
{
	public CacheCacheMoveCorresponding()
	{
		m_h = new Hashtable<Integer, MoveCorrespondingEntryManager>();
	}
	
	public MoveCorrespondingEntryManager get(int nId)
	{
		return m_h.get(nId);
	}

	public void add(int nId, MoveCorrespondingEntryManager c)
	{
		m_h.put(nId, c);
	}

	private Hashtable<Integer, MoveCorrespondingEntryManager> m_h = null;
}
