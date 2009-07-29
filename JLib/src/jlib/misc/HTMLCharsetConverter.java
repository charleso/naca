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
package jlib.misc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class HTMLCharsetConverter
{
	private static ArrayList<String> ms_arrCodes = null;
	private static Hashtable<String, Character> ms_hashCodes = null;
	
	public synchronized static void initOnce()
	{
		if(ms_arrCodes != null)	// init only once
			return ;
		ms_arrCodes = new ArrayList<String>();
		ms_hashCodes = new Hashtable<String, Character>();
		
		for(int n=0; n<256; n++)
		{			
			String cs = "&#" + n + ";";
			ms_arrCodes.add(cs);
			ms_hashCodes.put(cs, (char) n);
		}
		
		set(34, "&quot;"); //     "         
		set(38, "&amp;");	//             
		set(39, "&rsquo;"); //     '
		set(60, "&lt;");	//       <         
		set(62, "&gt;");	//       >         
		set(160, "&nbsp;"); //                
		set(161, "&iexcl;"); //	¡       
		set(162, "&cent;");  //    ¢           
		set(163, "&pound;"); //	£       
		set(164, "&curren;"); //  ¤           
		set(165, "&yen;");		//	¥       
		set(166, "&brvbar;");	//  ¦           
		set(167, "&sect;");	//§       
		set(168, "&uml;");		//  ¨           
		set(169, "&copy;");	//©       
		set(170, "&ordf;");	//    ª           
		set(171, "&laquo;");	//«       
		set(172, "&not;");		//     ¬          
		set(173, "&shy;");		//­       
		set(174, "&reg;");		//     ®           
		set(175, "&macr;");	//¯       
		set(176, "&deg;");		//     °          
		set(177, "&plusmn;"); //±       
		set(178, "&sup2;"); //    ²          
		set(179, "&sup3;"); //³       
		set(180, "&acute;"); //   ´          
		set(181, "&micro;"); //µ       
		set(182, "&para;"); //    ¶          
		set(183, "&middot;"); //·       
		set(184, "&cedil;"); //   ¸           
		set(185, "&sup1;"); //¹       
		set(186, "&ordm;"); //    º           
		set(187, "&raquo;"); //»       
		set(188, "&frac14;"); //  ¼           
		set(189, "&frac12;"); //½       
		set(190, "&frac34;"); //  ¾           
		set(191, "&iquest;"); //¿       
		set(192, "&Agrave;"); //  À          
		set(193, "&Aacute;"); //Á       
		set(194, "&Acirc;"); //   Â          
		set(195, "&Atilde;"); //Ã       
		set(196, "&Auml;"); //    Ä          
		set(197, "&Aring;"); //Å       
		set(198, "&AElig;"); //   Æ          
		set(199, "&Ccedil;"); //Ç       
		set(200, "&Egrave;"); //  È          
		set(201, "&Eacute;"); //É       
		set(202, "&Ecirc;"); //   Ê          
		set(203, "&Euml;"); //Ë       
		set(204, "&Igrave;"); //  Ì          
		set(205, "&Iacute;"); //Í       
		set(206, "&Icirc;"); //   Î          
		set(207, "&Iuml;"); //Ï       
		set(208, "&ETH;"); //     Ð            
		set(209, "&Ntilde;"); //Ñ       
		set(210, "&Ograve;"); //  Ò          
		set(211, "&Oacute;"); //Ó       
		set(212, "&Ocirc;"); //   Ô          
		set(213, "&Otilde;"); //Õ       
		set(214, "&Ouml;"); //    Ö         
		set(215, "&times;"); //×       
		set(216, "&Oslash;"); //  Ø         
		set(217, "&Ugrave;"); //Ù       
		set(218, "&Uacute;"); //  Ú          
		set(219, "&Ucirc;"); //Û       
		set(220, "&Uuml;"); //    Ü          
		set(221, "&Yacute;"); //Ý       
		set(222, "&THORN;"); //   Þ          
		set(223, "&szlig;"); //ß       
		set(224, "&agrave;"); //  à          
		set(225, "&aacute;"); //á       
		set(226, "&acirc;"); //   â          
		set(227, "&atilde;"); //ã       
		set(228, "&auml;"); //    ä          
		set(229, "&aring;"); //å       
		set(230, "&aelig;"); //   æ          
		set(231, "&ccedil;"); //ç       
		set(232, "&egrave;"); //  è         
		set(233, "&eacute;"); //é       
		set(234, "&ecirc;"); //   ê         
		set(235, "&euml;"); //ë       
		set(236, "&igrave;"); //  ì         
		set(237, "&iacute;"); //í       
		set(238, "&icirc;"); //   î         
		set(239, "&iuml;"); //ï       
		set(240, "&eth;"); //     ð         
		set(241, "&ntilde;"); //ñ       
		set(242, "&ograve;"); //  ò         
		set(243, "&oacute;"); //ó       
		set(244, "&ocirc;"); //   ô         
		set(245, "&otilde;"); //õ       
		set(246, "&ouml;"); //    ö         
		set(247, "&divide;"); //÷       
		set(248, "&oslash;"); //  ø          
		set(249, "&ugrave;"); //ù       
		set(250, "&uacute;"); //  ú         
		set(251, "&ucirc;"); //û       
		set(252, "&uuml;"); //    ü         
		set(253, "&yacute;"); //ý       
		set(254, "&thorn;"); //   þ         
		set(255, "&yuml;"); //ÿ       
	}
	
	private static void set(int nAsciiCode, String csHTMLCode)
	{
		ms_arrCodes.set(nAsciiCode, csHTMLCode);
		ms_hashCodes.put(csHTMLCode, (char) nAsciiCode);
	}
	
	static String getEncodedString(char c)
	{
		int nAsciiCode = (int) c;
		if(nAsciiCode >= 0 && nAsciiCode < 256)
		{
			String csHTMLCode = ms_arrCodes.get(nAsciiCode);
			return csHTMLCode; 
		}
		return "" + c;
	}
	
	static char getDecodedChar(String csHTMLValue)
	{
		return ms_hashCodes.get(csHTMLValue);
	}
	
	static String encodeIntoCustomString(String csHTMLValue)
	{
		Character c = ms_hashCodes.get(csHTMLValue);
		if(c != null)
		{
			csHTMLValue = "[" + csHTMLValue.substring(1).toUpperCase() + "]";
		}
		return csHTMLValue;
	}

	static String decodeFromCustomString(String csHTMLValue)
	{
		if(csHTMLValue.startsWith("[") && csHTMLValue.endsWith("]"))
		{
			csHTMLValue = csHTMLValue.substring(1, csHTMLValue.length()-1);
			csHTMLValue = csHTMLValue.toLowerCase();
			csHTMLValue = "&" + csHTMLValue; 
		}
		return csHTMLValue;
	}
	
	public static StringBuilder encodeIntoCustomString(StringBuilder sb)
	{
		initOnce();
		Set<String> arr = ms_hashCodes.keySet();
		Iterator<String> iter = arr.iterator();
		while(iter.hasNext())
		{
			String csHTMLValue = iter.next();
			if(csHTMLValue.length() > 1)
			{
				String csEncodedHTMLValue = encodeIntoCustomString(csHTMLValue);
				StringUtil.replace(sb, csHTMLValue, csEncodedHTMLValue);
			}
		}
		// Encode also chars encoded with numeric value (&#215; -> [#215])
		int nStart = sb.indexOf("&#");
		while(nStart >= 0)
		{
			int nEnd = sb.indexOf(";", nStart);
			String csNumVal = sb.substring(nStart+2, nEnd);
			if(StringUtil.isAllDigits(csNumVal))
			{
				csNumVal = "[#" + csNumVal + "]"; 
				String csLeft = sb.substring(0, nStart);
				String csRight = sb.substring(nEnd+1);
				sb.setLength(0);
				sb.append(csLeft);
				sb.append(csNumVal);
				sb.append(csRight);
			}
			
			nStart = sb.indexOf("&#", nEnd);
		}
		return sb;
	}
	
	public static StringBuilder decodeFromCustomString(StringBuilder sb)
	{
		initOnce();
		Set<String> arr = ms_hashCodes.keySet();
		Iterator<String> iter = arr.iterator();
		while(iter.hasNext())
		{
			String csHTMLValue = iter.next();
			if(csHTMLValue.length() > 1)
			{
				String csEncodedHTMLValue = encodeIntoCustomString(csHTMLValue);
				StringUtil.replace(sb, csEncodedHTMLValue, csHTMLValue);
			}
		}
		// Decode also chars encoded with numeric value ([#215] -> &#215;)
		int nStart = sb.indexOf("[#");
		while(nStart >= 0)
		{
			int nEnd = sb.indexOf("]", nStart);
			String csNumVal = sb.substring(nStart+2, nEnd);
			if(StringUtil.isAllDigits(csNumVal))
			{
				csNumVal = "&#" + csNumVal + ";"; 
				String csLeft = sb.substring(0, nStart);
				String csRight = sb.substring(nEnd+1);
				sb.setLength(0);
				sb.append(csLeft);
				sb.append(csNumVal);
				sb.append(csRight);
			}
			
			nStart = sb.indexOf("[#", nEnd);
		}

		return sb;
	}
}
