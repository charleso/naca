/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 13 oct. 2004
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

public class MapFieldAttrColor extends MapFieldBaseAttr
{
	private MapFieldAttrColor(int nEncodedColorValue, String text)
	{
		super(nEncodedColorValue, text);
	}
	
	static int getNbBitsEncoding()
	{
		return 3;
	}
	
	static int getMask()
	{
		return 7;
	}
	
		
//	String getText()
//	{
//		int n = getInternalValue();
//		if(n == BLUE.getInternalValue())
//			return "blue";
//		else if(n == 2)
//			return "red";
//		else if(n == 3)
//			return "pink";
//		else if(n == 4)
//			return "green";
//		else if(n == 5)
//			return "turquoise";
//		else if(n == 6)
//			return "yellow";
//		else if(n == 7)
//			return "neutral";
//		return "default";
//	}
	
//	char encode()
//	{
//		if(getInternalValue() > 0 && getInternalValue() <= 7)
//		{
//			char c = Character.forDigit(getInternalValue(), 10);
//			return c;
//		}
//		return '7';
//	}

//	void decode(char cValue)
//	{
//		if(cValue >= 1 && cValue <= 7)
//			m_nValue = Character.digit(cValue, 10);
//		else
//			m_nValue = 7; 
//	}
	
	public static MapFieldAttrColor BLUE = new MapFieldAttrColor(1, "blue");
	public static MapFieldAttrColor RED = new MapFieldAttrColor(2, "red");
	public static MapFieldAttrColor PINK = new MapFieldAttrColor(3, "pink");
	public static MapFieldAttrColor GREEN = new MapFieldAttrColor(4, "green");
	public static MapFieldAttrColor TURQUOISE = new MapFieldAttrColor(5, "turquoise");
	public static MapFieldAttrColor YELLOW = new MapFieldAttrColor(6, "yellow");
	public static MapFieldAttrColor NEUTRAL = new MapFieldAttrColor(7, "neutral");
	public static MapFieldAttrColor DEFAULT = NEUTRAL ;
	/**
	 * @param nValue
	 * @return
	 */
	public static MapFieldAttrColor Select(int nValue)
	{
		if(nValue == BLUE.getInternalValue())
			return BLUE;
		else if(nValue == RED.getInternalValue())
			return RED;
		else if(nValue == PINK.getInternalValue())
			return PINK;
		else if(nValue == GREEN.getInternalValue())
			return GREEN;
		else if(nValue == TURQUOISE.getInternalValue())
			return TURQUOISE;
		else if(nValue == YELLOW.getInternalValue())
			return YELLOW;
		else if(nValue == NEUTRAL.getInternalValue())
			return NEUTRAL;
		return null ;
	}
}
