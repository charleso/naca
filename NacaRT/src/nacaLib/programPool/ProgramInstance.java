/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
///*
// * Created on 28 avr. 2005
// *
// * TODO To change the template for this generated file go to
// * Window - Preferences - Java - Code Style - Code Templates
// */
//package nacaLib.programPool;
//
//import nacaLib.basePrgEnv.BaseProgram;
//import nacaLib.program.CopyManager;
//
///**
// * @author PJD
// *
// * TODO To change the template for this generated type comment go to
// * Window - Preferences - Java - Code Style - Code Templates
// */
//public class ProgramInstance
//{
//	public ProgramInstance(String csProgramName, BaseProgram program, boolean bNewProgramInstance)//, ClassLoaderUnloaderRef classLoaderUnloaderRef)
//	{
//		//m_csProgramName = csProgramName;
//		//m_bNewProgramInstance = bNewProgramInstance;
//		m_program = program;
//		if(m_program != null)
//		{
//			m_program.setProgramName(csProgramName);
//			m_program.setNewInstance(bNewProgramInstance);
//		}
//		
//	}
//	
//	public void setNewProgramInstance(boolean bNewProgramInstance)
//	{
//		//m_bNewProgramInstance = b;
//		if(m_program != null)
//		{
//			m_program.setNewInstance(bNewProgramInstance);
//		}
//	}
//	
//	public BaseProgram getProgram()
//	{
//		return m_program;
//	}
//	
//	public boolean isNewProgramInstance()
//	{
//		if(m_program != null)
//		{
//			return m_program.getNewInstance();
//		}
//		return false;
//	}
//	
//	String getProgramName()
//	{
//		if(m_program != null)
//			return m_program.getProgramName();
//		return "";
//	}
//	
//	/*
//	public Class getUnloadableClass()
//	{
//		return m_classLoaderUnloaderRef.get();
//	}
//	*/
//	
//	long getTimeRun()
//	{
//		return m_program.getProgramManager().getTimeRun();
//	}
//
//	long getTimeLastRunBegin_ms()
//	{
//		return m_program.getProgramManager().getTimeLastRunBegin_ms();
//	}
//	
//	void setLastTimeRunBegin()
//	{
//		m_program.getProgramManager().setLastTimeRunBegin();
//	}
//	
//	void setLastTimeRunEnd()
//	{
//		m_program.getProgramManager().setLastTimeRunEnd();
//	}
//	
//	void unloadClassCode()
//	{		
//		SharedProgramInstanceData sharedProgramInstanceData = m_program.getProgramManager().getSharedProgramInstanceData();
//		int nNbCopy = sharedProgramInstanceData.getNbCopy();
//		String csProgramName = getProgramName();
//		for(int n=0; n<nNbCopy; n++)
//		{
//			String csCopyName = sharedProgramInstanceData.getCopy(n);
//			CopyManager.removeCopyFormProg(csCopyName, csProgramName);
//		}
//		
//		
////		Class classParent = m_program.getClass();
////		// Get all copy
////		Field fieldlist[] = classParent.getDeclaredFields();
////		for (int i=0; i < fieldlist.length; i++) 
////		{
////			Field fld = fieldlist[i];
////			fld.setAccessible(true);
////			String csName = fld.getName();
////			Class type = fld.getType();
////			String csTypeName = type.getName();
////			try
////			{
////				Object obj = fld.get(m_program);
////				if(obj != null)
////				{
////					if(type != null)
////					{
////						Class superType = type.getSuperclass();
////						if(superType != null)
////						{
////							String csSuperTypeName = superType.getName();
////							if(csSuperTypeName != null)
////							{
////								if(csSuperTypeName.equals("nacaLib.program.Copy"))
////								{
////									String csCopyName = csTypeName;
////									CopyManager.removeCopyFormProg(csCopyName, getProgramName());
////								}
////							}
////						}
////					}
////				}
////			}
////			catch (IllegalAccessException e) 
////			{
////			   System.err.println(e);
////			}
////		}
//		
//		//m_program.getProgramManager().prepareAutoRemoval();
//		m_program = null;
//	}
//	
//		
//	//String m_csProgramName = null;
//	//boolean m_bNewProgramInstance = false;
//	BaseProgram m_program = null;
//	
//	//ClassLoaderUnloaderRef m_classLoaderUnloaderRef = null;
//}
