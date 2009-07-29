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
 * Created on 2 mai 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.mapSupport;

import nacaLib.base.CJMapObject;

/**
 * @author U930CV
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LanguageCode extends CJMapObject
{
	public static String FR = "FR" ;
	public static String DE = "DE" ;
	public static String IT = "IT" ;
	public static String EN = "EN" ;
	public static String getLanguageCode(int n)
	{
		switch (n)
		{
			case 1:
				return DE ;
			case 2:
				return FR ;
			case 3:
				return IT ;
			case 4:
				return EN ;
			default:
				return null ;
		}
	}
}
