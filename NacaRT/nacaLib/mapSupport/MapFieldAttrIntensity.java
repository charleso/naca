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

public class MapFieldAttrIntensity extends MapFieldBaseAttr
{
	private MapFieldAttrIntensity(int nValue, String text)
	{
		super(nValue, text);
	}
	
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
//		if(getInternalValue() == 1)
//			return "bright";
//		else if(getInternalValue() == 2)
//			return "dark";
//		return "normal";
//	}

	public static MapFieldAttrIntensity NORMAL = new MapFieldAttrIntensity(1, "normal");
	public static MapFieldAttrIntensity BRIGHT = new MapFieldAttrIntensity(2, "bright");		// high intensity
	public static MapFieldAttrIntensity DARK = new MapFieldAttrIntensity(3, "dark");
	public static MapFieldAttrIntensity HIDE = DARK ;
	/**
	 * @param nValue
	 * @return
	 */
	public static MapFieldAttrIntensity Select(int nValue)
	{
		if (nValue == BRIGHT.getInternalValue())
		{
			return BRIGHT ;
		}
		else if (nValue == DARK.getInternalValue())
		{
			return DARK ;
		}
		else if (nValue == NORMAL.getInternalValue())
		{
			return NORMAL ;
		} 
		else
		{
			return null ;
		}
	}
}
