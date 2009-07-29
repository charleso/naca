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

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class OccursDefRecordDependingVar extends OccursDef
{
	public OccursDefRecordDependingVar(int n, Var varOccurs)
	{
		super(n);
		m_varDepending = varOccurs;
	}

	public Var getRecordDependingVar()
	{
		return m_varDepending;
	}
	
	private transient Var m_varDepending = null;
}
