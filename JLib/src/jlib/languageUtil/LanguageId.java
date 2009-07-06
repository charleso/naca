/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.languageUtil;

import java.util.ArrayList;

import jlib.log.Log;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: LanguageId.java,v 1.2 2008/05/12 14:35:49 u930ds Exp $
 */
/* LanguageId
 * List of all supported languages; support for a default language
 */
public class LanguageId
{
	private String m_csIsoCode = null;		    // Public ISO code
	private String m_csCodeUpper = null;		// Public textual code; Used to select a language id; it's fr, de, it, , ... It has the corresponding value in lower case in m_csCodeLower
	private String m_csCodeLower = null;		// Public textual code in lowercase matching m_csCodeUpper 
	private String m_csNumericCode = null;		// P2000 code; Used to select a language id; It's a numeric value 01, 02, 03, 04
	
	private static LanguageId ms_defaultLanguageId = null;	// Holder for default language id
	private static ArrayList<LanguageId> ms_arrLanguages = null; 	// List of all language Id

	// List of all possible languages;
	public static LanguageId German = new LanguageId("DE", "01", false);
	public static LanguageId French = new LanguageId("FR", "02", true);	// French; Default language id
	public static LanguageId Italian = new LanguageId("IT", "03", false);
	public static LanguageId English = new LanguageId("EN", "04", false);	
	public static LanguageId NoLanguage = new LanguageId("XX", "", false);
	
	public LanguageId()
	{
	}
	
	public static LanguageId chooseInstanceByName(String csName)
	{
		if(csName.equalsIgnoreCase("DE") || csName.equals("01") || csName.equalsIgnoreCase("German") || csName.equalsIgnoreCase("DE_DE"))
			return LanguageId.German;
		if(csName.equalsIgnoreCase("FR") || csName.equals("02") || csName.equalsIgnoreCase("French")|| csName.equalsIgnoreCase("FR_FR"))
			return LanguageId.French;
		if(csName.equalsIgnoreCase("IT") || csName.equals("03") || csName.equalsIgnoreCase("Italian")|| csName.equalsIgnoreCase("IT_IT"))
			return LanguageId.Italian;
		if(csName.equalsIgnoreCase("EN") || csName.equals("04") || csName.equalsIgnoreCase("English")|| csName.equalsIgnoreCase("EN_EN"))
			return LanguageId.English;
		Log.logCritical("Unknown languageId: " + csName + "; No language default value is returned"); 
		return LanguageId.NoLanguage;
	}

	
	private LanguageId(String csCode, String csNumericCode, boolean bDefault)
	{
		m_csCodeUpper = csCode.toUpperCase();
		m_csCodeLower = csCode.toLowerCase();
		m_csIsoCode = csCode.toLowerCase();
		m_csNumericCode = csNumericCode;
		
		if(ms_arrLanguages == null)
			ms_arrLanguages = new ArrayList<LanguageId>(); 	// List of all language Id
		ms_arrLanguages.add(this);
		if(bDefault)
			ms_defaultLanguageId = this;			
	}
	
	public String getNumericCode()
	{
		return m_csNumericCode;
	}
	
	public String getTextCodeLowerCase()
	{
		return m_csCodeLower;
	}
	
	public String getTextCodeUpperCase()
	{
		return m_csCodeUpper;
	}
	
	public String getIsoCode()
	{
		return m_csIsoCode;
	}
	
	/* 
	 * Get a languageId by it's public name; returns the default one if none match
	 * Can be used to retrieve a LanguageId by it's code
	 * LanguageId lg = LanguageId.get("FR");
	 * If the code doesn't exists, then the default language id is returned.
	 */	
	public static LanguageId get(String cs)
	{
		cs = cs.toUpperCase();
		for(int n=0; n<ms_arrLanguages.size(); n++)
		{
			LanguageId languageId = ms_arrLanguages.get(n);
			if(languageId.getTextCodeUpperCase().equals(cs) || languageId.equals(cs)) 
				return languageId; 
		}
		return ms_defaultLanguageId;
	}
	
	public String toString()
	{
		return m_csCodeUpper + "/" + m_csCodeLower + "/" + m_csNumericCode;
	}
}
