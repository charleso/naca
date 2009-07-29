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
/*
 * Created on 12 oct. 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.varEx;

public class OccursDef extends OccursDefBase
{
	public OccursDef(int n)
	{
		m_nNbOccurs = n;
	}
	
	public int getNbOccurs()
	{
		return m_nNbOccurs; 
	}
	
	public Var getRecordDependingVar()
	{
		return null;
	}
	
	public void prepareAutoRemoval()
	{
	}

	int m_nNbOccurs = -1;	// Undefined
}
