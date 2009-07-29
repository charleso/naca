/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 24 juin 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package jlib.log;

/**
 * @author PJD
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Asserter
{
	static private boolean ms_ActivateAssert = false ;

	static public void assertAlways(String csMessage)
	{
		throw new AssertException(csMessage);
	}
	
	static public void setAssertActive(boolean b)
	{
		ms_ActivateAssert = b ;
	} 

	static public void assertIfNull(Object o)
	{
		if (ms_ActivateAssert)
		{
			if (o == null)
			{
				AssertException exp = new AssertException("AssertIsNull"); 
				throw exp ;
			}
		}
	}
	
	static public void assertIfNotNull(Object o)
	{
		if (ms_ActivateAssert)
		{
			if (o != null)
			{
				AssertException exp = new AssertException("AssertIsNotNull"); 
				throw exp ;
			}
		}
	}
	
	static public void assertIfEmpty(String cs)
	{
		if (ms_ActivateAssert)
		{
			if (cs.equals(""))
			{
				AssertException exp = new AssertException("AssertStringIsEmpty"); 
				throw exp ;
			}
		}
	}
	
	static public void assertIfFalse(boolean b)
	{
		if (ms_ActivateAssert)
		{
			if (!b)
			{
				AssertException exp = new AssertException("AssertTestIfFalse"); 
				throw exp ;
			}
		}
	}
	
	static public void assertIfFalse(boolean b, String csReason)
	{
		if (ms_ActivateAssert)
		{
			if (!b)
			{
				AssertException exp = new AssertException(csReason); 
				throw exp ;
			}
		}
	}
	
	static public void assertIfDifferent(String a, String b)
	{
		if (ms_ActivateAssert)
		{
			if (!a.equals(b))
			{
				AssertException exp = new AssertException("assertIfDifferent"); 
				throw exp ;
			}
		}
	}
	
	static public void assertIfEquals(String a, String b)
	{
		if (ms_ActivateAssert)
		{
			if (a.equals(b))
			{
				AssertException exp = new AssertException("assertIfDifferent"); 
				throw exp ;
			}
		}
	}
	
	static public void assertIfDifferent(int a, int b)
	{
		if (ms_ActivateAssert)
		{
			if(a != b) 
			{
				AssertException exp = new AssertException("assertIfDifferent"); 
				throw exp ;
			}
		}
	}

	static public void assertIfDifferent(double a, double b)
	{
		if (ms_ActivateAssert)
		{
			if(a < b - 0.0001 || a > b + 0.0001) 
			{
				AssertException exp = new AssertException("assertIfDifferent"); 
				throw exp ;
			}
		}
	}	

}
