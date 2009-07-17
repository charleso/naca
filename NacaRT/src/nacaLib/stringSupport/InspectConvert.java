/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */

package nacaLib.stringSupport;

import nacaLib.varEx.VarAndEdit;

public class InspectConvert
{
	private final VarAndEdit var;

	public InspectConvert(VarAndEdit var)
	{
		this.var = var;
	}

	public InspectConvert to(VarAndEdit from, VarAndEdit to) 
	{
		return to(from.getString(), to.getString());
	}
	
	public InspectConvert to(String from, String to) 
	{
		String value = var.getString();
		char[] charArray = value.toCharArray();
		
		for(int i = 0; i<charArray.length;i++)
		{
			int index = from.indexOf(charArray[i]);
			if(index >= 0)
			{
				charArray[i] = to.charAt(index);
			}
		}
		var.set(new String(charArray));
		return this;
	}
}
