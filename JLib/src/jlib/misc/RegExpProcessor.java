/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.misc;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author U930CV
 *
 */
public class RegExpProcessor {


	private class RegExpRule
	{
		public Pattern pattern ;
		public String export ;
	}
	
	private List<RegExpRule> lstRules = new LinkedList<RegExpRule>() ;
	private boolean caseInsensitive = false  ;
	
	/**
	 * @param regexp
	 * @param output
	 */
	public void AddRule(String regexp, String output) {
		int nflags = 0 ;
		if (this.caseInsensitive)
		{
			nflags = Pattern.CASE_INSENSITIVE ;
		}
		RegExpRule rule = new RegExpRule() ;
		rule.pattern = Pattern.compile(regexp, nflags) ;
		rule.export = output ;
		lstRules.add(rule) ;
	}

	/**
	 * @param originalPoneNumber
	 * @return
	 */
	public String ApplyRules(String text) {
		for (RegExpRule rule : lstRules)
		{
			Matcher matcher = rule.pattern.matcher(text) ;
			if (matcher.matches())
			{
				if (rule.export != null && !rule.export.equals(""))
				{
					String out = matcher.replaceFirst(rule.export) ;
					return out ;
				}
				else
				{
					return "" ;
				}
			}
			else
			{
			}
		}
		return null ;
	}

	/**
	 * @param caseSensitive2
	 */
	public void setCaseInsensitive(boolean caseInsensitive) {
		this.caseInsensitive = caseInsensitive ;
	}
	
	
}
