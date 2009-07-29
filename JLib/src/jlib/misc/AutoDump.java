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

import java.lang.reflect.Field;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class AutoDump
{
	/*
	 * Autmatically dump the member variables of the given class
	 * Visibility is not a concern 
	 */
	public static String dump(Object oSource)
	{	
		if(oSource == null)
			return "";
		
		Class programClass = oSource.getClass();
		
		ListCoupleRender out = ListCoupleRender.set(programClass.getCanonicalName());
		
		Field fieldlist[] = programClass.getDeclaredFields();
		for (int i=0; i < fieldlist.length; i++) 
		{
			Field fld = fieldlist[i];
			fld.setAccessible(true);
			String csName = fld.getName();
			Class type = fld.getType();
			String csTypeName = type.getName();
			try
			{
				Object oMember = fld.get(oSource);
				if(oMember != null)
				{
					String csValue = oMember.toString();
					out.set(csTypeName + " " + csName, csValue);
				}
				else
					out.set(csTypeName + " " + csName, "(null)");
			}
			catch (IllegalArgumentException e)
			{
				out.set(csTypeName + " " + csName, "?");
			}
			catch (IllegalAccessException e)
			{
				out.set(csTypeName + " " + csName, "?");
			}
		}
		return out.toString();
	}
}
