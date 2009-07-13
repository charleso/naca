/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.exceptions;

import nacaLib.program.*;

public class CGotoException extends NacaRTException
{
	private static final long serialVersionUID = 1L;
	public Paragraph m_Paragraph = null;
	public Section m_Section = null;
	
	public CGotoException(Paragraph functor)
	{
		super();
		m_Paragraph = functor;
	}
	public CGotoException(Section functor)
	{
		super();
		m_Section = functor;
	}
}
