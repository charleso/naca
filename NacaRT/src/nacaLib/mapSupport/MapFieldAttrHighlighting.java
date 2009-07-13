/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 14 oct. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.mapSupport;

public class MapFieldAttrHighlighting extends MapFieldBaseAttr
{
	private MapFieldAttrHighlighting(int nValue, String text)
	{
		super(nValue, text);
	}
	
	public static MapFieldAttrHighlighting OFF = new MapFieldAttrHighlighting(1, "off");
	public static MapFieldAttrHighlighting BLINK = new MapFieldAttrHighlighting(2, "blink");
	public static MapFieldAttrHighlighting REVERSE = new MapFieldAttrHighlighting(3, "reverse");
	public static MapFieldAttrHighlighting UNDERLINE = new MapFieldAttrHighlighting(4, "underline");	
	
	static int getNbBitsEncoding()
	{
		return 3;
	}
	
	static int getMask()
	{
		return 7;
	}
		
//	char encode()
//	{
//		char c = Character.forDigit(getInternalValue(), 10);
//		return c;
//	}
	
//	public String toString()
//	{
//		return getText();
//	}
//	
//	String getText()
//	{
//		int n = getInternalValue();
//		if(n == 1)
//			return "blink";
//		else if(n == 2)
//			return "reverse";
//		else if(n == 4)
//			return "underline";
//		return "off";
//	}

	/**
	 * @param nValue
	 * @return
	 */
	public static MapFieldAttrHighlighting Select(int nValue)
	{
		if (nValue == BLINK.getInternalValue())
		{
			return BLINK ;
		}
		else if (nValue == REVERSE.getInternalValue())
		{
			return REVERSE ;
		}
		else if (nValue == UNDERLINE.getInternalValue())
		{
			return UNDERLINE ;
		}
		else if (nValue == OFF.getInternalValue())
		{
			return OFF ;
		}
		else
		{
			return null ;
		}
	}

}
