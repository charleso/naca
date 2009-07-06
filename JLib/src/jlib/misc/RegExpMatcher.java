/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.misc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author S. Charton
 * @version $Id: RegExpMatcher.java,v 1.1 2006/06/21 07:09:21 u930cv Exp $
 */
public class RegExpMatcher {

	private Pattern pattern = null ;
	private Matcher matcher = null ;
	
	public boolean isMatching(String value, String regEx, boolean caseSensitive)
	{
		int flag = 0 ;
		if (!caseSensitive)
		{
			flag = Pattern.CASE_INSENSITIVE ;
		}
		this.pattern = Pattern.compile(regEx, flag) ;
		this.matcher = this.pattern.matcher(value) ;
		return this.matcher.matches() ;
	}
	public boolean isMatchingMotif(String value, String regEx)
	{
		int flag = 0 ;
		flag = Pattern.CASE_INSENSITIVE ;
		this.pattern = Pattern.compile(regEx, flag) ;
		this.matcher = this.pattern.matcher(value) ;
		return this.matcher.find() ;
	}
	public boolean isMatching(String value, String regEx)
	{
		return isMatching(value, regEx, false) ;
	}
	public boolean isMatching(String value)
	{
		if (this.pattern != null)
		{
			this.matcher = this.pattern.matcher(value) ;
			return this.matcher.matches() ;
		}
		return false  ;
	}
	public String group(int i)
	{
		if (this.matcher != null)
		{
			return this.matcher.group(i) ;
		}
		return "" ;		
	}

}
