/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.base;

import jlib.log.Asserter;

public class CJMapObject //extends BaseObject
{
	protected CJMapObject()
	{
	}
	
	public static void setAssertActive(boolean b)
	{
		Asserter.setAssertActive(b);
	}
	
	public static void Assert(String csMessage)
	{
		Asserter.assertAlways(csMessage);
	}
	
	protected void assertIfNull(Object o)
	{
		Asserter.assertIfNull(o);
	}
	
	protected void assertIfNotNull(Object o)
	{
		Asserter.assertIfNotNull(o);
	}
	
	protected void assertIfEmpty(String cs)
	{
		Asserter.assertIfEmpty(cs);
	}

	protected void assertIfFalse(boolean b)
	{
		Asserter.assertIfFalse(b);
	}
	
	protected void assertIfFalse(boolean b, String csReason)
	{
		Asserter.assertIfFalse(b, csReason);
	}
	
	protected void assertIfDifferent(String a, String b)
	{
		Asserter.assertIfDifferent(a, b);
	}
	
	protected void assertIfEquals(String a, String b)
	{
		Asserter.assertIfEquals(a, b);
	}
	
	protected void assertIfDifferent(int a, int b)
	{
		Asserter.assertIfDifferent(a, b);
	}

	protected void assertIfDifferent(double a, double b)
	{
		Asserter.assertIfDifferent(a, b);
	}
	
	public static final boolean isLogCESM = false;
	public static final boolean isLogFlow = false;
	public static final boolean isLogSql = false;
	public static final boolean IsSTCheck = false;
}
