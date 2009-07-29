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
import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.exceptions.CGotoException;
import nacaLib.exceptions.NacaRTException;

public class Paragraph extends CJMapRunnable
{
	private BaseProgram m_Program;	
	private String m_csName = null;
	private Method m_Method = null;
	Section m_currentSection = null;
	
	public Paragraph(BaseProgram Program)
	{
		m_Program = Program;
		m_currentSection = Program.getProgramManager().addParagraphToCurrentSection(this);
		int gg =0 ;
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
		if (m_csName == null)
			return;
		try
		{
			if (m_Method == null)
			{	
				Class[] paramTypes = null;
				m_Method = m_Program.getClass().getMethod(m_csName, paramTypes);
			}
			Object[] args = null;
			
			if(isLogStatCoverage)
				StatCoverage.logStatCoverage(StatCoverageType.Paragraph, m_Program, m_csName);
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
				boolean b = true;
				if(e1 instanceof CGotoException)
				{
					CGotoException eGoto = (CGotoException) e1;
					if(eGoto.m_bSentence)
					{
//						BaseProgramManager programManager = m_Program.getProgramManager();
//						Section currentSection = programManager.getCurrentSection();
//						currentSection.runSectionFromParagraph(eGoto.m_Paragraph);
						//m_currentSection.runSectionFromParagraph(eGoto.m_Paragraph);
						eGoto.m_Paragraph.run();
						m_currentSection.setCurrentParagraph(eGoto.m_Paragraph);
						return ;
					}
				}
				if(b)
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
	
	public CJMapRunnable runForPerformThrough()
	{
		if (m_csName == null)
			return this;
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
				boolean b = true;
				if(e1 instanceof CGotoException)
				{
					CGotoException eGoto = (CGotoException) e1;
					if(eGoto.m_bSentence)
					{
//						BaseProgramManager programManager = m_Program.getProgramManager();
//						Section currentSection = programManager.getCurrentSection();
//						currentSection.runSectionFromParagraph(eGoto.m_Paragraph);
						//m_currentSection.runSectionFromParagraph(eGoto.m_Paragraph);
						eGoto.m_Paragraph.run();
						return eGoto.m_Paragraph;
					}
				}
				if(b)
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
		return this;
	}	
	
	public Section getSectionOwner()
	{
		return m_currentSection;
	}
}
