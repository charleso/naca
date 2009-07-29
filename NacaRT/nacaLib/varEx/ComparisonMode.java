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

public class ComparisonMode
{
	public static ComparisonMode Ebcdic = new ComparisonMode();
	public static ComparisonMode Unicode = new ComparisonMode();
	public static ComparisonMode UnicodeOrEbcdic = new ComparisonMode();
	
	private ComparisonMode()
	{
	}
}
