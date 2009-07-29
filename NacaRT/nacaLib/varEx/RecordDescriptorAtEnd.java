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
package nacaLib.varEx;

public class RecordDescriptorAtEnd
{
	public static RecordDescriptorAtEnd End = new RecordDescriptorAtEnd(true);
	public static RecordDescriptorAtEnd NotEnd = new RecordDescriptorAtEnd(false);
	
	private RecordDescriptorAtEnd(boolean bEnd)
	{
		m_bEnd = bEnd;
	}
	
	public boolean atEnd()
	{
		return m_bEnd;
	}
	
	private boolean m_bEnd = false;
}
