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
package nacaLib.program;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.exceptions.NacaRTException;

public class CallbackSearch
{	
	private BaseProgram m_Program;	
	private String m_csName = null;
	private Method m_Method = null;
	
	public CallbackSearch(BaseProgram Program)
	{
		m_Program = Program;
	}
	
	public String toString()
	{
		return m_csName;
	}
	
	public void name(String csName)
	{
		m_csName = csName;
	}	
	
	public CompareResult run()
	{
		if (m_csName == null) 
			return null;
		try
		{
			if (m_Method == null)
			{	
				Class[] paramTypes = null;
				m_Method = m_Program.getClass().getMethod(m_csName, paramTypes);
			}
			Object[] args = null;
			if(m_Program.isLogStatCoverage)
				StatCoverage.logStatCoverage(StatCoverageType.CallbackSearch, m_Program, m_csName);

			CompareResult result = (CompareResult)m_Method.invoke(m_Program, args);
			return result;
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
