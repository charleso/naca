/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.log;

public class PatternLayoutDbFlat extends LogPatternLayout
{
	public PatternLayoutDbFlat()
	{
		super();
	}
	
	String getMessage(LogParams logParams)
	{
		String csMessage = logParams.getMessage();
		return csMessage;
	}
	
	String format(LogParams logParams, int n)
	{
		if(n == 0)
		{
			String csMessage = logParams.toString();
			return csMessage;
		}
		return null;
	}
	
	int getNbLoop(LogParams logParams)
	{
		return 1;
	}
}
