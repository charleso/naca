/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.misc;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import jlib.log.Log;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: JSon.java,v 1.7 2008/04/15 06:29:35 u930fc Exp $
 */
public class JSon
{
	// Automatically exports a java object into a JSon structre.
	// The following variables types are supported: int, double, String, StringBuffer, StringBuilder, Array, boolean, short, long
	// See http://www.json.org/
	
	private StringBuilder m_sbOut = null;
	private int m_nNbItemSet = 0;
	private int m_nTabDepth = 0;
	private boolean m_bSetLines = true;
	
	public void setLines(boolean b)
	{
		m_bSetLines = b;
	}
	
	public boolean exportAsJSon(Object oSource)
	{
		m_nTabDepth = 0;
		m_sbOut = new StringBuilder();
		
		boolean b = export(oSource, null);
		if(!b)
			m_sbOut = null;
		return b;
	}
	
	public String getResult()
	{
		if(m_sbOut != null)
			return m_sbOut.toString();
		return "";
	}
	
	public static String exportAsJSon(Object oSource, String className)
	{
		JSon json = new JSon();
		json.m_bSetLines = false;
		json.m_nTabDepth = 0;
		json.m_sbOut = new StringBuilder();
		boolean b = json.export(oSource, className);
		if (b)
			return json.m_sbOut.toString();
		return null;
	}
	
	public static String concatJSon(String json1, String json2)
	{
		return json1.substring(0, json1.length() - 1) + "," + json2.substring(1); 
	}
	
	private boolean export(Object oSource, String className)
	{
		m_nNbItemSet = 0;
		if(oSource != null)
		{
			beginNewLine(m_sbOut);
			m_sbOut.append("{");
			if(m_bSetLines)
				m_sbOut.append(EndOfLine.CR);
			m_nTabDepth++;
			
			if (className == null)
			{
				// Dump current class and it's hierarchy
				Class programClass = oSource.getClass();
				String csClassName = programClass.getCanonicalName();
				while(!csClassName.equals("java.lang.Object"))
				{
					dumpJSonClass(programClass, oSource);	// Dump current class
					
					programClass = programClass.getSuperclass();
					csClassName = programClass.getCanonicalName();
				}
			}
			else
			{
				m_sbOut.append("\"" + className + "\":");				
				exportItem(oSource);
			}
			
			if(m_bSetLines)
				m_sbOut.append(EndOfLine.CR);
			beginNewLine(m_sbOut);
			m_sbOut.append("}");
			if(m_bSetLines)
				m_sbOut.append(EndOfLine.CR);
		}
		return true;
	}

	private boolean dumpJSonClass(Class programClass, Object oSource)
	{
		Field fieldlist[] = programClass.getDeclaredFields();
		for (int i=0; i < fieldlist.length; i++) 
		{
			Field fld = fieldlist[i];
			fld.setAccessible(true);
			String csName = fld.getName();
			Class type = fld.getType();
			int iMod=fld.getModifiers();
			if (Modifier.isStatic(iMod))
				continue;
			String csTypeName = type.getName();
			try
			{
				if(m_nNbItemSet > 0) // Terminates previous line is there was one
					endCurrentLine(m_sbOut);
				beginNewLine(m_sbOut);
				
				// remove prefix membership
				if(csName.startsWith("m_"))
					csName = csName.substring(2);
				else if(csName.startsWith("_"))
					csName = csName.substring(1);
				
				m_sbOut.append("\"" + csName + "\":");	// Write "<name>":
				Object oMember = fld.get(oSource);
				if(oMember != null)
				{
					boolean bExported = exportItem(oMember);
					if(!bExported)
					{
						Log.logCritical("Unsupported JSon serialization format; JLib.JSon.exportItem must be completed");
						return false;
					}
				}
				else
					m_sbOut.append("null");
				m_nNbItemSet++;
			}
			catch (IllegalArgumentException e)
			{
				m_sbOut.append("null");
				return false;
			}
			catch (IllegalAccessException e)
			{
				m_sbOut.append("null");
				return false;
			}
		}
		return true;
	}
	
	private boolean exportItem(Object oMember)
	{
		if (oMember instanceof String)
		{
			String csValue = oMember.toString();
			csValue = quoteAndReplaceSpecialChars(csValue);
			m_sbOut.append(csValue);
			return true;
		}
		else if (oMember instanceof Boolean)
		{
			String csValue = oMember.toString();			
			m_sbOut.append(csValue);
			return true;
		}
		else if (oMember instanceof Integer)
		{
			String csValue = oMember.toString();
			m_sbOut.append(csValue);
			return true;
		}
		else if (oMember instanceof java.util.ArrayList)
		{
			return exportArrayAsJSon(oMember, m_sbOut);
		}
		else if (oMember instanceof Long)
		{
			String csValue = oMember.toString();
			m_sbOut.append(csValue);
			return true;
		}
		else if (oMember instanceof Double)
		{
			String csValue = oMember.toString();
			m_sbOut.append(csValue);
			return true;
		}
		else if (oMember instanceof Float)
		{
			String csValue = oMember.toString();
			m_sbOut.append(csValue);
			return true;
		}
		else if (oMember instanceof Short)
		{
			String csValue = oMember.toString();
			m_sbOut.append(csValue);
			return true;
		}
		else if (oMember instanceof java.util.Date)
		{
			String csValue = oMember.toString();
			csValue = quoteAndReplaceSpecialChars(csValue);
			m_sbOut.append(csValue);
			return true;
		}	
		else if (oMember instanceof StringBuffer)
		{
			String csValue = oMember.toString();
			csValue = quoteAndReplaceSpecialChars(csValue);
			m_sbOut.append(csValue);
			return true;
		}
		else if (oMember instanceof StringBuilder)
		{
			String csValue = oMember.toString();
			csValue = quoteAndReplaceSpecialChars(csValue);
			m_sbOut.append(csValue);
			return true;
		}
		
		// At last position !
		else if (oMember instanceof Object)	// Applicative Object; must be last test
		{
			return export(oMember, null);
		}
		return false;
	}

    /**
     * Produce a string in double quotes with backslash sequences in all the
     * right places. A backslash will be inserted within </, allowing JSON
     * text to be delivered in HTML. In JSON text, a string cannot contain a
     * control character or an unescaped quote or backslash.
     * @param string A String
     * @return  A String correctly formatted for insertion in a JSON text.
     */
	private String quoteAndReplaceSpecialChars(String csValue)
	{
        if (csValue == null || csValue.length() == 0) 
        {
            return "\"\"";
        }

        char         b;
        char         c = 0;
        int          i;
        int          len = csValue.length();
        StringBuffer sb = new StringBuffer(len + 4);
        String       t;

        sb.append('"');
        for (i = 0; i < len; i += 1)
        {
            b = c;
            c = csValue.charAt(i);
            switch (c)
            {
            case 'à':
            	sb.append('a');
            	break;
            case 'ô':
            	sb.append('o');
            	break;
            case '\\':
                sb.append('\\');
                sb.append(c);
                break;
            case '"':
                sb.append('\'');
                //sb.append(c);
                break;
            case '/':
                if (b == '<') {
                    sb.append('\\');
                }
                sb.append(c);
                break;
            case '\b':
                sb.append("\\b");
                break;
            case '\t':
                sb.append("\\t");
                break;
            case '\n':
                sb.append("\\n");
                break;
            case '\f':
                sb.append("\\f");
                break;
            case '\r':
                sb.append("\\r");
                break;
            default:
                if (c < ' ')
                {
                    t = "000" + Integer.toHexString(c);
                    sb.append("\\u" + t.substring(t.length() - 4));
                }
                else 
                {
                    sb.append(c);
                }
            }
        }
        sb.append('"');
        return sb.toString();
    }
		
	private boolean exportArrayAsJSon(Object oArray, StringBuilder sbOut)
	{
		sbOut.append("[ ");
		if(m_bSetLines)
			sbOut.append(EndOfLine.CR);
		m_nTabDepth++;
		ArrayList<Object> arr = (ArrayList<Object>)oArray;
		for(int n=0; n<arr.size(); n++)
		{
			if(n > 0)
				endCurrentLine(sbOut);
			beginNewLine(sbOut);
			Object oArrItem = arr.get(n);
			boolean bArrayItemsExported = exportItem(oArrItem);
			if(!bArrayItemsExported)
			{
				return false;
			}									
		}
		sbOut.append(" ]");
		m_nTabDepth--;
		return true;
	}
	
	private void beginNewLine(StringBuilder sbOut)
	{
		if(m_bSetLines)
		{
			for(int n=0; n<m_nTabDepth; n++)
				sbOut.append(EndOfLine.TAB);
		}
	}
	
	private void endCurrentLine(StringBuilder sbOut)
	{		
		sbOut.append(",");
		if(m_bSetLines)
			sbOut.append(EndOfLine.CR);
	}

}
