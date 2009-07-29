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
package jlib.sql;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class VarBinary
{
	public VarBinary()
	{
		m_tb = null;
	}
	
	public VarBinary(byte tb[])
	{
		m_tb = tb;
	}
	
	public String getAsString()
	{
		if(m_tb	!= null)
		{
			String cs = new String(m_tb);
			return cs; 
		}
		
		return "";
	}
	
	public String getAsUTF8String()
	{
		if(m_tb	!= null)
		{
			String cs;
			try
			{
				cs = new String(m_tb, "UTF-8");
				return cs; 
			}
			catch (UnsupportedEncodingException e)
			{
				
			}			
		}
		
		return "";
	}
	
	public boolean setUTF8FromString(String cs)
	{
		try
		{
			m_tb = cs.getBytes("UTF-8");
			return true;
		}
		catch (UnsupportedEncodingException e)
		{
		}
		return false;
	}
	
	public byte [] getBytes()
	{
		return m_tb;
	}
	
	public void setBytes(byte [] tb)
	{
		m_tb = tb;
	}
	
	private byte m_tb[] = null;
}
