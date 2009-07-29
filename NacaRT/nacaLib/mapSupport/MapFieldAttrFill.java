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

public class MapFieldAttrFill extends MapFieldBaseAttr
{
	private MapFieldAttrFill(int nValue, String text)
	{
		super(nValue, text);
	}
	public static MapFieldAttrFill BLANK = new MapFieldAttrFill(1, "blank");
	public static MapFieldAttrFill ZERO = new MapFieldAttrFill(2, "zero");
	
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
//			return "zero";
//		return "blank";
//	}
	
	boolean isFillZero()
	{
		if(getInternalValue() == ZERO.getInternalValue())
			return true;
		return false;		
	}

	boolean isFillBlank()
	{
		if(getInternalValue() == BLANK.getInternalValue())
			return true;
		return false;		
	}
		
	// Encoded in 1 bit
//	char encodeInto(char cBitField)	//Uses bit 7
//	{
//		if(getInternalValue() == 1)	// Set bit 6 to 1
//		{
//			cBitField |= 0x40;	// 0100 0000
//		}
//		else	// Set bit 6 to 0
//		{
//			cBitField &= 0xBf;	// 1011 1111
//		}
//		return cBitField;
//	}

	/**
	 * @param nValue
	 * @return
	 */
	public static MapFieldAttrFill Select(int nValue)
	{
		if (nValue == BLANK.getInternalValue())
		{
			return BLANK ;
		}
		else if (nValue == ZERO.getInternalValue())
		{
			return ZERO ;
		}
		return null ;
	}
}
