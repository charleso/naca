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
 * Created on 21 avr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NameManager
{	
	static public String getUnprefixedName(String csName)
	{
		int nPosSep = csName.indexOf('.');
		if(nPosSep != -1)
			csName = csName.substring(nPosSep+1);
		
		nPosSep = csName.indexOf('$');
		if(nPosSep != -1)
			csName = csName.substring(0, nPosSep);		// 1st name that follows the dot (File$X.Y$Z$T -> returns Y)
		
		return csName;
	}
	
	static public String getUnprefixedUnindexedName(String csName)
	{
		String cs = getUnprefixedName(csName);
		int n = cs.indexOf("[");
		if(n != -1)
		{
			cs = cs.substring(0, n);
		}
		return cs;
	}
}
