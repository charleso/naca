/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.program;

public class SpecialCode
{
	public static String CARRIAGE_RETURN = "\r"; // 0D
	public static String LINE_FEED = "\u008e"; // 0A
	public static String TABULATOR = "\u008d"; // 09

	public static String AFP_5A = "]"; // 5A
	public static String AFP_PAGEFORMAT = "L\u00bf\u00ad"; // D3ABCA
	public static String AFP_COPYGROUP = "L\u00bf\u00f6"; // D3ABCC
	public static String AFP_SEGMENT ="L\u00ae^"; // D3AF5F
	public static String AFP_LINE ="L\u00d3\u00ba"; // D3EE9B	
}
