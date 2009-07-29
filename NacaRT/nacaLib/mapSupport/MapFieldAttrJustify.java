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

public class MapFieldAttrJustify extends MapFieldBaseAttr
{
	private MapFieldAttrJustify(int nValue, String text)
	{
		super(nValue, text);
	}
	
	public static MapFieldAttrJustify RIGHT = new MapFieldAttrJustify(1, "right");
	public static MapFieldAttrJustify LEFT = new MapFieldAttrJustify(2, "left");	
	
	
	static int getNbBitsEncoding()
	{
		return 2;
	}
	
	static int getMask()
	{
		return 3;
	}
	
//	public String toString()
//	{
//		return getText();
//	}
//
//	public String getText()
//	{
//		if(getInternalValue() == 0)
//			return "right";
//		return "left";
//	}
	
	boolean isJustifyLeft()
	{
		if(getInternalValue() == LEFT.getInternalValue())
			return true;
		return false;
	}
	
	boolean isJustifyRight()
	{
		if(getInternalValue() == RIGHT.getInternalValue())
			return true;
		return false;
	}
	
//	char encodeInto(char cBitField)	//Uses bit 7
//	{
//		if(getInternalValue() == 1)	// Set bit 7 to 1
//		{
//			cBitField |= 0x80;
//		}
//		else	// Set bit 7 to 0
//		{
//			cBitField &= 0x7f;
//		}
//		return cBitField;
//	}

	/**
	 * @param nValue
	 * @return
	 */
	public static MapFieldAttrJustify Select(int nValue)
	{
		if (nValue == RIGHT.getInternalValue())
		{
			return RIGHT ;
		}
		else if (nValue == LEFT.getInternalValue())
		{
			return LEFT ;
		}
		else
		{
			return null ;
		}
	}
}
