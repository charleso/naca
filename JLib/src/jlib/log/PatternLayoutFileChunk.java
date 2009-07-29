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
 * Created on 7 juil. 2005
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
public class PatternLayoutFileChunk extends LogPatternLayout
{
	public PatternLayoutFileChunk()
	{
		super();
	}
	
	String getMessage(LogParams logParams)
	{
		String csMessage = logParams.getMessage();
		return ">" + csMessage + "\r\n";
	}

	String format(LogParams logParams, int n)
	{
		String cs = logParams.getTextItem(n);
		if(cs != null)
		{
			return "." + cs + "\r\n";
		}
		return null;
	}	
	
	int getNbLoop(LogParams logParams)
	{
		return logParams.getNbParamInfoMember();
	}

}
