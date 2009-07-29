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
package nacaLib.calledPrgSupport;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class BaseCalledPrgPublicArgWay
{
	public static BaseCalledPrgPublicArgWay IN = new BaseCalledPrgPublicArgWay(true, false);
	public static BaseCalledPrgPublicArgWay OUT = new BaseCalledPrgPublicArgWay(false, true);
	public static BaseCalledPrgPublicArgWay INOUT = new BaseCalledPrgPublicArgWay(true, true);
	
	private boolean m_bIn = false;
	private boolean m_bOut = false;
	
	private BaseCalledPrgPublicArgWay(boolean bIn, boolean bOut)
	{
		m_bIn = bIn;
		m_bOut = bOut;
	}
}
