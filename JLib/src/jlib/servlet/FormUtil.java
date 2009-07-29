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
/**
 * 
 */
package jlib.servlet;

import javax.servlet.http.HttpServletRequest;

import jlib.misc.NumberParser;
import jlib.misc.StringUtil;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class FormUtil
{
	public static String getParameterAsString(HttpServletRequest request, String csName)
	{
		String csValue = request.getParameter(csName);
		if(StringUtil.isEmpty(csValue))
			return "";
		return csValue;
	}
	
	public static String getParameterAsString(HttpServletRequest request, String csName, String csDefaultValue)
	{
		String csValue = request.getParameter(csName);
		if(StringUtil.isEmpty(csValue))
			return csDefaultValue;
		return csValue;
	}
	
	public static int getParameterAsInt(HttpServletRequest request, String csName)
	{
		String csValue = request.getParameter(csName);
		if(StringUtil.isEmpty(csValue))
			return 0;
		return NumberParser.getAsInt(csValue);
	}
	
	public static int getParameterAsInt(HttpServletRequest request, String csName, int nDefaultValue)
	{
		String csValue = request.getParameter(csName);
		if(StringUtil.isEmpty(csValue))
			return nDefaultValue;
		return NumberParser.getAsInt(csValue);
	}
}
