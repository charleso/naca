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
package nacaLib.program ;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import jlib.log.Log;
import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.exceptions.*;

public class Section extends CJMapRunnable
{
	private BaseProgram m_Program = null;
	private boolean m_bRun = true;
	private Paragraph m_currentParagraph = null;
	private ArrayList<Paragraph> m_arrParagraph = new ArrayList<Paragraph>();	// Array of paragraphs inside the current section
	private String m_csName = null;
	private Method m_Method = null;	
	
	public Section(BaseProgram Program)
	{
		m_Program = Program;
		m_bRun = true;
		BaseProgramManager programManager = Program.getProgramManager();
		programManager.addSection(this);
		programManager.addSectionToParagraphArray(this);
	}

	public Section(BaseProgram Program, boolean bRun)
	{
		m_Program = Program;
		m_bRun = bRun;
		BaseProgramManager programManager = Program.getProgramManager();
		programManager.addSection(this);
		programManager.addSectionToParagraphArray(this);
	}
	
	public String toString()
	{
		return m_csName;	//.substring(m_csName.lastIndexOf('$')+1) ;
	}
	
	public void name(String csName)
	{
		m_csName = csName;
	}
	
	public void addParapgraph(Paragraph Paragraph)
	{
		m_arrParagraph.add(Paragraph);
	}
	
	private void setNextParagraphCurrent()
	{
		int nNbParagraph = m_arrParagraph.size();
		if(m_currentParagraph == null)	// No current paragraph: the next one will be the first one
		{
			if(nNbParagraph > 0)
			{
				m_currentParagraph = m_arrParagraph.get(0);
			}
			else	// No paragraph in the section
			{
				m_currentParagraph = null;
			}
		}
		else
		{
			int nCurrentParagraphIndex = getCurrentParagraphIndex();
			if(nCurrentParagraphIndex >= 0)
			{
				nCurrentParagraphIndex++;
				if(nCurrentParagraphIndex < nNbParagraph)
				{
					 m_currentParagraph = m_arrParagraph.get(nCurrentParagraphIndex);
				}
				else	// We are on the last paragraph of the section: no next paragraph
					m_currentParagraph = null;
			}
			else
				m_currentParagraph = null;
		}
	}
			 
	private int getCurrentParagraphIndex()	// locate where we are in the section
	{	
		int nNbParagraph = m_arrParagraph.size();
		int nCurrentParagraphIndex = 0;
		while(nCurrentParagraphIndex < nNbParagraph)
		{
			Paragraph paragraph = m_arrParagraph.get(nCurrentParagraphIndex);
			if(m_currentParagraph == paragraph)
				return nCurrentParagraphIndex;
			nCurrentParagraphIndex++;
		}	
		return -1;		
	}
	
	public void run()
	{
		if (!m_bRun || m_csName == null)
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
				StatCoverage.logStatCoverage(StatCoverageType.Section, m_Program, m_csName);
			
			m_Method.invoke(m_Program, args);
		}
		catch (NoSuchMethodException e)
		{
			throw new RuntimeException(e);
		}
//		catch (InvocationTargetException e)
//		{	
//			Throwable e1 = e.getTargetException();
//			if (e1 instanceof NacaRTException)
//			{	
//				throw (NacaRTException)e1;
//			}
//			else if (e1 instanceof Error)
//			{ 
//				throw (Error)e1; 
//	        }
//			else if (e1 instanceof RuntimeException)
//			{ 
//	            throw (RuntimeException)e1; 
//	        }   
//			throw new RuntimeException(e);
//		}
		catch (InvocationTargetException e)
		{
			Throwable e1 = e.getTargetException();
			if (e1 instanceof NacaRTException)
			{	
				if(e1 instanceof CGotoException)
				{
					CGotoException eGoto = (CGotoException) e1;
					if(eGoto.m_bSentence)
					{
						eGoto.m_Paragraph.run();
						setCurrentParagraph(eGoto.m_Paragraph);

//						BaseProgramManager programManager = m_Program.getProgramManager();
//						Section currentSection = programManager.getCurrentSection();
//						currentSection.runSectionFromParagraph(eGoto.m_Paragraph);
						return ;
					}
				}
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
	
	public void setCurrentParagraph(Paragraph paragraph)
	{
		m_currentParagraph = paragraph;
	}
	
	public void runSectionFromParagraph(Paragraph paragraph)
	{
		m_currentParagraph = paragraph;
		runSectionFromCurrentParagraph();
	}
	
	public void runSection()
	{
		m_currentParagraph = null;	// The code in the section headser is out of any paragraph
		runSectionFromCurrentParagraph();
	}
	
	private void runSectionFromCurrentParagraph()
	{
		try
		{
			if(m_currentParagraph == null)
			{
				if(isLogFlow)
					Log.logDebug("Run section: "+m_Program.getSimpleName()+"."+toString());
				run();		// Run the code directly written into the section, not included in a paragraph
			}
			else
			{
				if(isLogFlow)
					Log.logDebug("Run paragraph: "+m_Program.getSimpleName()+"."+m_currentParagraph.toString());
				m_currentParagraph.run();
			}
				
			setNextParagraphCurrent();  
		}
		catch (CGotoException e)
		{
			Paragraph gotoParagraph = e.m_Paragraph;
			if(gotoParagraph != null)	// goto a paragraph
			{
				m_currentParagraph = e.m_Paragraph;
			}
			else
			{
				Section gotoSection = e.m_Section;
				if(gotoSection != null)
				{
					CGotoOtherSectionException eGotoOtherSection = new CGotoOtherSectionException(gotoSection);
					throw eGotoOtherSection;
				}
			}
		}
		
		while(m_currentParagraph != null)	// Loop while we have sone paragraph to execute
		{
			try
			{
				if(isLogFlow)
					Log.logDebug("Run paragraph:"+m_Program.getSimpleName()+"."+m_currentParagraph.toString());		
				m_currentParagraph.run();
							
				setNextParagraphCurrent();
			}
			catch (CGotoException e)
			{
				Paragraph gotoParagraph = e.m_Paragraph;
				if(gotoParagraph != null)	// goto a paragraph
				{
					boolean b = isParagraphInCurrentSection(gotoParagraph);
					if(b)	// the section that owns the paragraph where we goto is the current one
					{
						m_currentParagraph = e.m_Paragraph;
					}
					else 
					{
						CGotoOtherSectionParagraphException eGotoOtherSectionPara = new CGotoOtherSectionParagraphException(gotoParagraph);
						throw eGotoOtherSectionPara;
					}
				}
				else
				{
					Section gotoSection = e.m_Section;
					if(gotoSection != null)
					{
						CGotoOtherSectionException eGotoOtherSection = new CGotoOtherSectionException(gotoSection);
						throw eGotoOtherSection;
					}
				}
			} 
		}
	}
	
	public boolean isParagraphInCurrentSection(Paragraph paragraph)
	{
		int nNbParagraph = m_arrParagraph.size();
		for(int n=0; n<nNbParagraph; n++)
		{
			Paragraph p = m_arrParagraph.get(n);
			if(p == paragraph)
				return true;
		}
		return false;
	}
	
	public void runFirstParagraph()
	{
		if(m_arrParagraph.size() > 0)
		{
			Paragraph ParaFirst = m_arrParagraph.get(0);
			if(ParaFirst != null)
				ParaFirst.run(); 
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
}
