/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.program;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.exceptions.NacaRTException;

public class Paragraph extends CJMapRunnable
{	
	private BaseProgram m_Program;	
	private String m_csName = null;
	private Method m_Method = null;
	
	public Paragraph(BaseProgram Program)
	{
		m_Program = Program;
		Program.getProgramManager().addParagraphToCurrentSection(this);
	}
	
	public String toString()
	{
		return m_csName;
	}
	
	public void name(String csName)
	{
		m_csName = csName;
	}	
	
	public void run()
	{
		if (m_csName == null) return;
		try
		{
			if (m_Method == null)
			{	
				Class[] paramTypes = null;
				m_Method = m_Program.getClass().getMethod(m_csName, paramTypes);
			}
			Object[] args = null;
			m_Method.invoke(m_Program, args);
		}
		catch (NoSuchMethodException e)
		{
			throw new RuntimeException(e);
		}
		catch (InvocationTargetException e)
		{
			Throwable e1 = e.getTargetException();
			if (e1 instanceof NacaRTException)
			{	
				throw (NacaRTException)e1;
			}
			else if (e1 instanceof Error)
			{ 
				throw (Error)e1; 
	        }
			else if (e1 instanceof RuntimeException)
			{ 
	            throw (RuntimeException)e1; 
	        }   
			throw new RuntimeException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
	}
}
