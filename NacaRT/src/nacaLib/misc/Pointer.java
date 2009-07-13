/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */


/*
 * Created on Nov 1, 2004
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
package nacaLib.misc;

import nacaLib.base.*;
import nacaLib.varEx.VarAndEdit;

public class Pointer extends CJMapObject
{
	public Pointer(VarAndEdit v)
	{
		m_AddressOf = v ;
	}
	
	public VarAndEdit m_AddressOf = null ;
}
