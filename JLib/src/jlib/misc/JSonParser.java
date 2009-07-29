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
package jlib.misc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.List;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class JSonParser
{
	private Hashtable<String, JSonCoupleItem> m_hashItems = new Hashtable<String, JSonCoupleItem>();
	
	public static boolean fillObject(String cs, Object oTarget)
	{
		JSonParser jsonParser = new JSonParser();
		return jsonParser.fill(cs, oTarget);
	}
	
	public static boolean fillObject(String cs, Object oTarget, Class cls)
	{
		JSonParser jsonParser = new JSonParser();
		return jsonParser.fill(cs, oTarget, cls);
	}
	
	public boolean fill(String cs, Object oTarget, Class cls)
	{
		if (!(oTarget instanceof List))
			return false;

		List list = (List)oTarget;
		cs = cs.trim();
		
		if(!cs.startsWith("[{"))
			return false;
		cs = cs.substring(2);
		
		if(!cs.endsWith("}]"))
			return false;		
		cs = cs.substring(0, cs.length()-2);
		
		cs = cs.trim();
		
		String[] csItem = cs.split("},\\{");
		for (int i=0; i < csItem.length; i++)
		{
			try
			{
				Constructor ct = cls.getConstructor();
				Object obj = ct.newInstance();	//null);
				boolean b = fill("{" + csItem[i] + "}", obj);
				if (b)
					list.add(obj);
				else
					return false;
			}
			catch (Exception ex)
			{
				return false;
			}
		}
		
		return true;
	}
	
	public boolean fill(String cs, Object oTarget)
	{
		cs = cs.trim();
		
		if(!cs.startsWith("{"))
			return false;
		cs = cs.substring(1);
		
		if(!cs.endsWith("}"))
			return false;
		cs = cs.substring(0, cs.length()-1);
		
		cs = cs.trim();
		
		String[] csItem = cs.split(",");
		for (int i=0; i < csItem.length; i++)
		{
			JSonCoupleItem couple = new JSonCoupleItem();
			boolean b = couple.parse(csItem[i]);
			if(!b)
				return false;
			m_hashItems.put(couple.getName(), couple);
		}
		
		boolean b = fillTargetMembers(oTarget);		
		return b;
	}
	
	boolean fillTargetMembers(Object oTarget)
	{
		Class programClass = oTarget.getClass();
		String csClassName = programClass.getCanonicalName();
		boolean b = true;
		while(b && !csClassName.equals("java.lang.Object"))
		{
			b = fillTarget(programClass, oTarget);	// Fill target class
			
			programClass = programClass.getSuperclass();
			csClassName = programClass.getCanonicalName();
		}
		
		return b;
	}
	
	private boolean fillTarget(Class programClass, Object oTarget)
	{
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
				if(csName.startsWith("_"))
					csName = csName.substring(1);
				else if(csName.startsWith("m_"))
					csName = csName.substring(2);
				JSonCoupleItem couple = m_hashItems.get(csName);
				if(couple != null)
				{
					Object oMember = fld.get(oTarget);
					if(csTypeName.equals("java.lang.String"))
					{
						String cs = couple.getValueAsString();
						fld.set(oTarget, cs);
					}
					else if(csTypeName.equals("int") || csTypeName.equals("java.lang.Integer"))
					{
						int n = couple.getValueAsInt();
						fld.setInt(oTarget, n);
					}
					else if(csTypeName.equals("boolean") || csTypeName.equals("java.lang.Boolean"))
					{
						boolean b = couple.getValueAsBoolean();
						fld.setBoolean(oTarget, b);
					}
				}
			}
			catch (IllegalArgumentException e)
			{
				return false;
			}
			catch (IllegalAccessException e)
			{
				return false;
			}
		}
		return true;
	}
}
