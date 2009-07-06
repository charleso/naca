/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.basePrgEnv;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import jlib.log.Log;
import jlib.misc.ArrayDyn;
import jlib.misc.ArrayFix;
import jlib.misc.ArrayFixDyn;
import jlib.misc.Time_ms;
import nacaLib.base.CJMapObject;
import nacaLib.calledPrgSupport.BaseCalledPrgPublicArgPositioned;
import nacaLib.exceptions.CESMReturnException;
import nacaLib.exceptions.CGotoException;
import nacaLib.exceptions.CGotoOtherSectionException;
import nacaLib.exceptions.CGotoOtherSectionParagraphException;
import nacaLib.mapSupport.Map;
import nacaLib.misc.KeyPressed;
import nacaLib.program.CJMapRunnable;
import nacaLib.program.Copy;
import nacaLib.program.CopyManager;
import nacaLib.program.CopyReplacing;
import nacaLib.program.Paragraph;
import nacaLib.program.Section;
import nacaLib.programPool.SharedProgramInstanceData;
import nacaLib.programPool.SharedProgramInstanceDataCatalog;
import nacaLib.programStructure.DataDivision;
import nacaLib.programStructure.DataSectionFile;
import nacaLib.programStructure.Division;
import nacaLib.sqlSupport.CSQLStatus;
import nacaLib.sqlSupport.SQL;
import nacaLib.sqlSupport.SQLCursor;
import nacaLib.sqlSupport.SQLExecuteStart;
import nacaLib.tempCache.TempCache;
import nacaLib.tempCache.TempCacheLocator;
import nacaLib.varEx.CCallParam;
import nacaLib.varEx.Cond;
import nacaLib.varEx.DataSection;
import nacaLib.varEx.Edit;
import nacaLib.varEx.EditInMap;
import nacaLib.varEx.Form;
import nacaLib.varEx.InitializeCache;
import nacaLib.varEx.MapRedefine;
import nacaLib.varEx.MoveCorrespondingEntryManager;
import nacaLib.varEx.SortParagHandler;
import nacaLib.varEx.Var;
import nacaLib.varEx.VarBase;
import nacaLib.varEx.VarBuffer;
import nacaLib.varEx.VarBufferPos;
import nacaLib.varEx.VarDefBase;
import nacaLib.varEx.VarDefBuffer;
import nacaLib.varEx.VarInternalInt;

public abstract class BaseProgramManager extends CJMapObject 
{
	public BaseProgramManager(BaseProgram program, SharedProgramInstanceData sharedProgramInstanceData, boolean bInheritedSharedProgramInstanceData)
	{
		super();
		
		setLastTimeRunBegin();
		m_program = program;
		m_sharedProgramInstanceData = sharedProgramInstanceData;
		m_bInheritedSharedProgramInstanceData = bInheritedSharedProgramInstanceData;
		m_nLastVarId = 0;
		m_sqlStatus = new CSQLStatus() ;
		m_hashInitializeCache = new Hashtable<Integer, InitializeCache>();
		m_hashMoveCorrespondingEntryManager = new Hashtable<Integer, MoveCorrespondingEntryManager>();
		m_bNewInstance = true;
	}
	
	public BaseProgram prepareCall(BaseProgramLoader baseProgramLoader, BaseProgram currentProgram, ArrayList arrCallerCallParam, BaseEnvironment env, boolean bNewProgramInstance)
	{
		TempCache tempCache = TempCacheLocator.getTLSTempCache();
		tempCache.pushCurrentProgram(currentProgram);
		
		m_baseProgramLoader = baseProgramLoader;
		setEnv(env);
		
		if(env != null)
			m_csTransID = env.getNextProgramToLoad() ;
		determineCommareaLength(env);

		if(env != null && env.getCommarea()!=null)
		{
			if(arrCallerCallParam == null) 
				arrCallerCallParam = new ArrayList<CCallParam>();
			
			CCallParam CallParam = env.getCommarea().buildCallParam();
			arrCallerCallParam.add(CallParam);
		}
		
//		int nParametersTotalLength = calcCallParametersTotalLength(arrCallerCallParam);
//		Var var = getCommAreaLength();
//		if(var!= null)
//			var.set(nParametersTotalLength);
		
		if(m_DataDivision != null)
		{
			if(bNewProgramInstance)	// Only if we are a new instance of the program
			{
				loadNewInstance(arrCallerCallParam);
			}
			else
			{
				VarBuffer varBufferWS = m_DataDivision.getWorkingStorageVarBuffer();
				//varBufferWS.removeAllSemanticContext();
				
				VarBuffer bufferLS = m_DataDivision.getLinkageSectionVarBuffer();
				assignBufferLS(bufferLS);
				
				m_DataDivision.restoreFileManagerEntries(env);
								
				m_sharedProgramInstanceData.restoreOriginalValues(varBufferWS, m_arrEditInMap);
							
				m_DataDivision.mapLinkageCallParameters(arrCallerCallParam, m_arrDeclaredCallArg);
			}
			
			//checkAllEditsAttributValidity();
		}
	
		return m_program;
	}

	public void mapCalledPrgReturnParameters(ArrayList<BaseCalledPrgPublicArgPositioned> arrCallerCallParam)
	{
		if (arrCallerCallParam != null)
			m_DataDivision.mapCalledPrgReturnParameters(arrCallerCallParam, m_arrDeclaredCallArg);
	}
	
	private void loadNewInstance(ArrayList arrCallerCallParam)
	{
		Log.logDebug("loadNewInstance Program="+m_program.getSimpleName());
		m_DataDivision.grantLinkageSection(m_program);
		
		boolean bFirstInstance = isFirstInstance();	// true if are the 1st instance of the program
		  
		if(bFirstInstance)
		{
			findVarNames();
		}
		else
		{
			findVarNamesSectionAndParagraph();
			m_sharedProgramInstanceData.restoreCursorNames(m_arrCursor);
		}
		
		indexVars();
		
		VarBuffer varBufferWS = m_DataDivision.manageWorkingLinkageVars(m_program, bFirstInstance, arrCallerCallParam, m_arrDeclaredCallArg);

		
		if(bFirstInstance)	// 1st instance: The vardef have not already been computed
		{
			checkFileSection();
			Log.logDebug("loadNewInstance Program="+m_program.getSimpleName() + "; 1st instance");

			m_sharedProgramInstanceData.compress();
			m_sharedProgramInstanceData.saveOriginalValues(varBufferWS, m_arrEditInMap);
		}		
		else	// instance > 1
		{
			Log.logDebug("loadNewInstance Program="+m_program.getSimpleName() + "; NOT 1st instance");
			m_sharedProgramInstanceData.restoreOriginalValues(varBufferWS, m_arrEditInMap);
		}
	}
	
	private void checkFileSection()
	{
		DataSectionFile fileSection = fileSection();
		if(fileSection != null && getEnv() != null)
		{
			BaseSession session = getEnv().getBaseSession();
			fileSection.setOnSession(session);
		}
	}

	private void findVarNames()
	{
		Class programClass = m_program.getClass();
		String csProgramName = m_program.getSimpleName();
		m_sharedProgramInstanceData.setProgramName(csProgramName);
		setVarName(programClass, m_program, "", csProgramName);
	}
	
	private void findVarNamesSectionAndParagraph()
	{
		Class programClass = m_program.getClass();
		String csProgramName = m_program.getSimpleName();
		m_sharedProgramInstanceData.setProgramName(csProgramName);
		setVarNameSectionAndParagraph(programClass, m_program, "", csProgramName);
	}

	private int calcCallParametersTotalLength(ArrayList arrCallerCallParam)
	{
		int nTotalParamLength = 0;
		
		if(arrCallerCallParam != null)
		{
			int nNbArg = arrCallerCallParam.size();
			for(int nArg=0; nArg<nNbArg; nArg++)
			{
				CCallParam CallParam = (CCallParam) arrCallerCallParam.get(nArg);
				if(CallParam != null)
					nTotalParamLength += CallParam.getParamLength();
			}
		}
		return nTotalParamLength;
	}
	
	public Division dataDivision()
	{
		if(m_DataDivision == null)
			m_DataDivision = new DataDivision(m_program);
		return m_DataDivision; 
	}
	
	public void checkWorkingStorageSection()
	{
		if(m_DataDivision == null)	// Check DataDivision creation
		{
			dataDivision();
			m_DataDivision.grantAndSetCurrentWorkingStorageSection(m_program);
		}
	}
	
	public DataSection workingStorageSection()
	{
		if(m_DataDivision == null)	// Check DataDivision creation
			dataDivision();
		
		return m_DataDivision.grantAndSetCurrentWorkingStorageSection(m_program);
	}
	
	public DataSection linkageSection()
	{
		if(m_DataDivision == null)	// Check DataDivision creation
			dataDivision();			// Should never occur as WorkingStorageSection() followed
			
		return m_DataDivision.grantAndSetCurrentLinkageSection(m_program); 
	}
	
	public DataSectionFile fileSection()
	{
		if(m_DataDivision == null)	// Check DataDivision creation
			dataDivision();
		
		return m_DataDivision.grantAndSetCurrentFileSection(m_program);
	}
	
	public BaseProgram getProgram()
	{
		return m_program;
	}	
	
	public void prepareAutoRemoval()
	{
		m_arrCursor = null;
		m_arrDeclaredCallArg = null;
		m_arrEditInMap = null;
		m_arrParagraph = null;
		m_arrSection = null;
		m_arrVarsFile = null;
		m_arrVarsLS = null;
		m_arrVarsWS = null;
		m_baseProgramLoader = null;
		m_copyReplacing = null;
		m_currentParagraph = null;
		m_currentSection = null;
		m_DataDivision = null;
		m_hashSQL = null;
		m_arrVarsFullName = null;
		m_lastVarCreated = null;
		m_program = null;
		m_rootVar = null;
		m_sharedProgramInstanceData = null;
		m_sortParagHandler = null;
		m_sqlStatus = null;
		m_varEIBCALEN = null;
	}

	public BaseProgram m_program = null;	
	public String m_csTransID = "" ;
	public DataDivision m_DataDivision = null;
		
	int getReplacedLevel(int nLevel)
	{
		if(m_copyReplacing != null)
			return m_copyReplacing.getReplacedLevel(nLevel);
		return nLevel;
	}
	
	public void setVarName(Class classParent, Object owner, String csPrefixeName, String csProgramName)
	{		
		boolean bSetPrefix = false;
		
		if(csPrefixeName != null && csPrefixeName.length() > 0)
		{
			csPrefixeName += ".";
			bSetPrefix = true;
		}

		if(classParent != null)
		{
			try
			{
				Field fieldlist[] = classParent.getDeclaredFields();
				for (int i=0; i < fieldlist.length; i++) 
				{
					Field fld = fieldlist[i];
					fld.setAccessible(true);
					String csName = fld.getName();
					Class type = fld.getType();
					String csTypeName = type.getName(); 
					Object obj = fld.get(owner);
					if(obj != null)
					{
						if(csTypeName.equals("nacaLib.varEx.Var"))
						{
							Var var = (Var)obj;
							if(var != null)
							{
								if(var.getVarDef() != null)
								{
									if(bSetPrefix)
									{
										m_sharedProgramInstanceData.setVarFullName(var.getVarDef().getId(), csPrefixeName + csName);
									}
									else
									{
										m_sharedProgramInstanceData.setVarFullName(var.getVarDef().getId(), csName);
									}
								}
							}
						}
						else if(csTypeName.equals("nacaLib.varEx.DataSection"))
						{
							int n = 0;	// Do nothing; A data section has no name
						}
						else if(csTypeName.equals("nacaLib.program.Section"))
						{
							Section section = (Section)obj;
							if(bSetPrefix)
								section.name(csPrefixeName + csName);
							else
								section.name(csName);
						}	 
						else if(csTypeName.equals("nacaLib.program.Paragraph"))
						{
							Paragraph para = (Paragraph)obj;
							if(bSetPrefix)
								para.name(csPrefixeName + csName);
							else
								para.name(csName);
						}
						else if(csTypeName.equals("nacaLib.varEx.Form"))
						{
							Form form = (Form)obj;
							if(bSetPrefix)
							{
								m_sharedProgramInstanceData.setVarFullName(form.getVarDef().getId(), csPrefixeName + csName);
							}
							else
							{
								m_sharedProgramInstanceData.setVarFullName(form.getVarDef().getId(), csName);
							}
						}
						else if(csTypeName.equals("nacaLib.varEx.Edit"))
						{
							Edit edit = (Edit)obj;
							if(edit != null)
							{
								if(bSetPrefix)
								{
									m_sharedProgramInstanceData.setVarFullName(edit.getVarDef().getId(), csPrefixeName + csName);
								}
								else
								{
									m_sharedProgramInstanceData.setVarFullName(edit.getVarDef().getId(), csName);
								}
							}
						}
						else if(csTypeName.equals("nacaLib.varEx.MapRedefine"))
						{
							MapRedefine mapRedefine = (MapRedefine)obj;
							if(mapRedefine != null)
							{
								if(bSetPrefix)
								{
									m_sharedProgramInstanceData.setVarFullName(mapRedefine.getVarDef().getId(), csPrefixeName + csName);
								}
								else
								{
									m_sharedProgramInstanceData.setVarFullName(mapRedefine.getVarDef().getId(), csName);
								}
							}
						}
						else if(csTypeName.equals("nacaLib.varEx.Cond"))
						{
							Cond cond = (Cond)obj;
							if(bSetPrefix)
								cond.setName(csPrefixeName + csName);
							else
								cond.setName(csName);
						}	
						else if(csTypeName.equals("nacaLib.varEx.ParamDeclaration"))
						{
							int n =0 ;
						}	
						else if(csTypeName.equals("nacaLib.mapSupport.LocalizedString"))
						{
							int n =0 ;
						}
						else if(csTypeName.equals("nacaLib.sqlSupport.SQLCursor"))
						{
							SQLCursor cursor = (SQLCursor)obj;
							String csCursorName = csName; 
							if(bSetPrefix)
								csCursorName = csPrefixeName + csName;
							cursor.setName(csProgramName, csCursorName);
							m_sharedProgramInstanceData.saveCursorName(csCursorName);
						}
						else // Maybe a VarContainer derviated
						{
							if(type != null)
							{
								Class superType = type.getSuperclass();
								if(superType != null)
								{
									String csSuperTypeName = superType.getName();
									if(csSuperTypeName != null)
									{
										if(csSuperTypeName.equals("nacaLib.program.Copy"))
										{
											Copy copyFile = (Copy)obj;
											
											m_sharedProgramInstanceData.addCopy(csTypeName);
											
											String cs = csPrefixeName + csName; 
											setVarName(type, copyFile, cs, csProgramName);
										}
										else if(csSuperTypeName.equals("nacaLib.mapSupport.Map"))
										{
											Map mapFile = (Map)obj;
											String cs = csName; 
											setVarName(type, mapFile, cs, csProgramName);
										}
										else if(csSuperTypeName.equals("nacaLib.sqlSupport.SQLCursor"))
										{
											SQLCursor cur = (SQLCursor)obj;
											String cs = csName; 
											setVarName(type, cur, cs, csProgramName);
										}
										else if(csSuperTypeName.startsWith("nacaLib.varEx.RecordDescriptor"))	// Unhandled type
										{
										}	
										else if(csSuperTypeName.startsWith("nacaLib.varEx.BaseFileDescriptor"))	// Unhandled type
										{
										}					
										else if(csSuperTypeName.startsWith("nacaLib"))	// Unhandled type
										{
											Log.logImportant("setVarName: Unhandled nacaLib type=" + csSuperTypeName + " SuperType=" + csTypeName); 
										}
									}
								}
							}
						}
					}
				}
			}
			catch (IllegalAccessException e) 
			{
			   System.err.println(e);
			}
		}
	}
	
	public void setVarNameSectionAndParagraph(Class classParent, Object owner, String csPrefixeName, String csProgramName)
	{		
		boolean bSetPrefix = false;
		
		if(csPrefixeName != null && csPrefixeName.length() > 0)
		{
			csPrefixeName += ".";
			bSetPrefix = true;
		}

		if(classParent != null)
		{
			try
			{
				Field fieldlist[] = classParent.getDeclaredFields();
				for (int i=0; i < fieldlist.length; i++) 
				{
					Field fld = fieldlist[i];
					fld.setAccessible(true);
					String csName = fld.getName();
					Class type = fld.getType();
					String csTypeName = type.getName(); 
					Object obj = fld.get(owner);
					if(obj != null)
					{
						if(csTypeName.equals("nacaLib.program.Section"))
						{
							Section section = (Section)obj;
							if(bSetPrefix)
								section.name(csPrefixeName + csName);
							else
								section.name(csName);
						}	 
						else if(csTypeName.equals("nacaLib.program.Paragraph"))
						{
							Paragraph para = (Paragraph)obj;
							if(bSetPrefix)
								para.name(csPrefixeName + csName);
							else
								para.name(csName);
						}
					}
				}
			}
			catch (IllegalAccessException e) 
			{
			   System.err.println(e);
			}
		}
	}
	
	public void using(Var var)
	{
		if(m_arrDeclaredCallArg == null)
			m_arrDeclaredCallArg = new ArrayList<Var>(); 
		m_arrDeclaredCallArg.add(var);
	}
	
	public void runMain()
	{
		m_program.setTempCache();
		m_currentSection = null;
		m_currentParagraph = null;
		
		Paragraph gotoParagraph = null;
		try
		{
			setNextSectionCurrent();
			if(isLogFlow)
				Log.logDebug("Run: "+m_program.getSimpleName()+"."+"procedureDivision()");
			m_program.procedureDivision();
		}
		catch (CGotoException e)
		{
			gotoParagraph = e.m_Paragraph;
			if(gotoParagraph == null)
				m_currentSection = e.m_Section;
			else
				m_currentSection = getSectionOwnerParagraph(gotoParagraph);
		} 
		catch (CGotoOtherSectionException e)	// goto another section
		{
			m_currentSection = e.m_Section;
			gotoParagraph = null;				
		}
		catch (CGotoOtherSectionParagraphException e)	// Goto a paragraph of another section
		{
			gotoParagraph = e.m_Paragraph;
			m_currentSection = getSectionOwnerParagraph(gotoParagraph) ;
		}
		
		while(m_currentSection != null)
		{
			try
			{		
				m_currentSection.runSectionFromParagraph(gotoParagraph);
				gotoParagraph = null;
				setNextSectionCurrent();
			}
			catch (CGotoOtherSectionParagraphException e)	// Goto a paragraph of another section
			{
				gotoParagraph = e.m_Paragraph;
			}
			catch (CGotoOtherSectionException e)	// goto another section
			{
				m_currentSection = e.m_Section;
				gotoParagraph = null;				
			}
			catch (CESMReturnException e)
			{
				m_currentSection = null ;	// Force a return to CESM
			}
		}
	}
	
	private Section getSectionOwnerParagraph(Paragraph paragraph)
	{
		Section section = null;
		int nNbSection = m_arrSection.size();
		for(int n=0; n<nNbSection; n++)
		{
			section = m_arrSection.get(n);
			if(section.isParagraphInCurrentSection(paragraph))
				return section;
		}
		return null;
	}
	
	boolean runStartParagraph() throws CGotoException
	{
		Section section = getFirstSection();	// The program beins at first section
		if(section != null)
		{
			section.runFirstParagraph();
			return true;
		}
		return false;
	}
	
	public void addSection(Section section)
	{
		if(section != null)
		{
			m_arrSection.add(section);
		}
	}
	
	public Section addParagraphToCurrentSection(Paragraph paragraph)
	{
		Section section = getLastSection();
		if(section == null)
			section = section("Unnamed");
		section.addParapgraph(paragraph);
		m_arrParagraph.add(paragraph);
		return section;
	}
	
	public Section section(String csName)
	{
		Section section = new Section(m_program, false);
		section.name(csName);
		return section;
	}
	
	private Section getLastSection()
	{
		int n = m_arrSection.size(); 
		if(n > 0)
			return m_arrSection.get(n-1);
		return null;
	}
	
	public VarDefBase getVarAtParentLevel(int nLevel)
	{
		VarDefBase varDef = m_DataDivision.getVarDefAtParentLevel(nLevel);
		return varDef;
	}
	
	private Section getFirstSection()
	{
		int n = m_arrSection.size(); 
		if(n > 0)
			return m_arrSection.get(0);
		return null;
	}
	
	private void setNextSectionCurrent()
	{
		int nNbSection = m_arrSection.size();
		if(m_currentSection == null)	// No current paragraph: the next one will be the first one
		{
			if(nNbSection > 0)
			{
				m_currentSection = m_arrSection.get(0);
			}
			else	// No paragraph in the section
			{
				m_currentSection = null;
			}
		}
		else
		{
			int nCurrentSectionIndex = getCurrentSectionIndex();
			if(nCurrentSectionIndex >= 0)
			{
				nCurrentSectionIndex++;
				if(nCurrentSectionIndex < nNbSection)
				{
					 m_currentSection = m_arrSection.get(nCurrentSectionIndex);
				}
				else	// We are omn the last section: no next paragraph
					m_currentSection = null;
			}
			else
				m_currentSection = null;
		}
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
				else	// We are omn the last paragraph of the section: no next paragraph
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
		
	private int getCurrentSectionIndex()	// locate where we are in the section
	{	
		int nNbSection = m_arrSection.size();
		int nCurrentSectionIndex = 0;
		while(nCurrentSectionIndex < nNbSection)
		{
			Section section = m_arrSection.get(nCurrentSectionIndex);
			if(m_currentSection == section)
				return nCurrentSectionIndex;
			nCurrentSectionIndex++;
		}	
		return -1;		
	}
	
	public void perform(Paragraph paragraph)
	{
		if(paragraph != null)
			paragraph.run();
	}
	
	public void perform(Section section)
	{
		if(section != null)
			section.runSection();
	}
	
	public void performThrough(Paragraph paragraphBegin, Paragraph paragraphEnd)
	{
		// Enum all paragraphs that are between functorBegin and functorEnd, whatever their sections
		m_currentParagraph = paragraphBegin;
		boolean bDone = false ;
		while(m_currentParagraph != null && !bDone)
		{
			try
			{
				m_currentParagraph.run();
				if (m_currentParagraph == paragraphEnd)
				{
					bDone = true ;
				}
				else
				{
					setNextParagraphCurrent();
				}
			}
			catch (CGotoException e)
			{
				m_currentParagraph = e.m_Paragraph;
			}
		}
	}

	private CJMapRunnable m_currentParagraph = null;
	private ArrayList<Var> m_arrDeclaredCallArg = null;		// Arguments declared on call
	
	private ArrayFixDyn<Section>  m_arrSection= new ArrayDyn<Section>();	// Array of Sections inside the procedure division
	private ArrayFixDyn<Paragraph> m_arrParagraph = new ArrayDyn<Paragraph>();	// Array of all paragraphs, whatever their section
	
	private CopyReplacing m_copyReplacing;

	public VarDefBuffer popLevel(int nReplacedLevel)
	{
		if(m_DataDivision == null)	// No working storage section defined
			workingStorageSection();
		VarDefBuffer varDefParent = m_DataDivision.getVarDefAtParentLevel(nReplacedLevel);
		return varDefParent;
	}
	
	public void pushLevel(VarDefBuffer varDef)
	{
		if(m_DataDivision == null)	// No working storage section defined
			workingStorageSection();
		m_DataDivision.pushLevel(varDef);
	}
	
	public VarBase getLastVarCreated()
	{
		return m_lastVarCreated;
	}
	
	private void setLastVarCreated(VarBase var)
	{
		if(m_rootVar == null)
			m_rootVar = m_lastVarCreated;
		m_lastVarCreated = var;
	}
	
	public VarBase getRoot()
	{
		return m_rootVar;
	}
	
	public VarBase getVarFullName(int nId)
	{
		VarBase varBase = m_arrVarsFullName[nId];
		if(varBase == null)
			logSevereError(nId);
		return varBase;
	}
	
	public VarBase getVarFullName(VarDefBase varDef)
	{
		if(varDef != null)
		{
			VarBase varBase = m_arrVarsFullName[varDef.getId()];
			if(varBase == null)
				logSevereError(varDef);
			return varBase;
		}
		logSevereError();
		return null;
	}

	private void logSevereError(int nId)
	{		
		// Severe Error
		String csTitle =  "BaseProgramManager::getVarFullName(" + nId + ") called: SEVERE ERROR; should never happen !!!";
		String csText = "Could not find variable of id="+nId+ "\r\n";
		logSevereErrorNext(csTitle, csText);
	}
	
	private void logSevereError(VarDefBase varDef)
	{		
		// Severe Error
		String csTitle =  "BaseProgramManager::getVarFullName(" + varDef.toString() + ") called: SEVERE ERROR; should never happen !!!";
		String csText = "Could not find variable of id="+varDef.getId()+" Name="+varDef.toString() + "\r\n";
		logSevereErrorNext(csTitle, csText);
	}
	
	private void logSevereError()
	{		
		// Severe Error
		String csTitle =  "BaseProgramManager::getVarFullName(null) called:  SEVERE ERROR; should never happen !!!";
		String csText =  "No variable passed in parameter\r\n";
		logSevereErrorNext(csTitle, csText);
	}
	
	private void logSevereErrorNext(String csTitle, String csText)
	{		
		String csSimpleName = getProgramName();
		StringBuffer sbText = new StringBuffer(); 
		sbText.append("in program " + csSimpleName + "\r\n");
		sbText.append("It will crash\r\n"); 
		sbText.append("m_arrVarsFullName dump ("+m_arrVarsFullName.length+" entries):\r\n");
		for(int n=0; n<m_arrVarsFullName.length; n++)
		{
			//System.out.println(n);
//			if(n  == 148)
//			{
//				int g1g = 0;
//			}
			if(m_arrVarsFullName[n] != null)
			{
				String cs = m_arrVarsFullName[n].toString();
				sbText.append("ID="+n + " : " + cs + "\r\n");
			}
			else
			{
				sbText.append("ID="+n + " : <null !!!>\r\n");
			}
		}
		
		sbText.append("\r\n");
		if(TempCacheLocator.getTLSTempCache().getProgramManager() != this)
			sbText.append("ERROR: TempCacheLocator.getTLSTempCache().getProgramManager() != currentProgramManager: SEVERE corruption of TLS data\r\n");
		else
			sbText.append("TLS ProgramManger is set correctly: TempCacheLocator.getTLSTempCache().getProgramManager() == currentProgramManager\r\n");
		
		sbText.append("\r\n");
		
		SharedProgramInstanceData sharedProgramInstanceData = SharedProgramInstanceDataCatalog.getSharedProgramInstanceData(csSimpleName);
		if(sharedProgramInstanceData != null)
		{		
			sbText.append("\r\nsharedProgramInstanceData:\r\n");
			String cs = sharedProgramInstanceData.dumpAll();
			sbText.append(cs);
		}
		else
			sbText.append("\r\nERROR: sharedProgramInstanceData == null !!!\r\n");
		
		csText += sbText.toString(); 
		BaseProgramLoader.logMail(csSimpleName + " - " + csTitle, csText);
	}
	
	public boolean getBufferPosOfVarDef(VarDefBuffer varDefBuffer, VarBufferPos varBufferPos)
	{
		if(varDefBuffer != null)
		{
			VarBase varBase = m_arrVarsFullName[varDefBuffer.getId()];
			if(varDefBuffer.getTempNbDim() == 0)
			{
				varBufferPos.setAsVar(varBase);
				return true;
			}
			else
			{
				((Var)varBase).adjust(varDefBuffer, varBufferPos);
				return true;
			}
		}
		return false;
	}
	
	public void registerVar(VarBase var)
	{
		setLastVarCreated(var);
		
		if(m_DataDivision.isWorkingSectionCurrent())
			m_arrVarsWS.add(var);
		else if(m_DataDivision.isLinkageSectionCurrent())
			m_arrVarsLS.add(var);
		else if(m_DataDivision.isFileSectionCurrent())
		{
			m_arrVarsFile.add(var);
			short sLevel = var.getVarDef().getLevel();
			if(sLevel == 1)	// Level 1: Only 1 struct of a a record of a file 
				m_DataDivision.registerFileVarStruct((Var)var);
		}
	}
	
	public void defineVarDynLengthMarker(Var var)
	{
		if(m_DataDivision.isFileSectionCurrent())
		{
			m_DataDivision.defineVarDynLengthMarker(var);
		}
	}
	
	public boolean isLinkageSectionCurrent()
	{
		return m_DataDivision.isLinkageSectionCurrent();
	}
	
	public void indexVars()
	{
		int nNbVar = m_arrVarsWS.size() + m_arrVarsLS.size() + m_arrVarsFile.size() + 3;	// Must include space for roots  
		
		m_arrVarsFullName = new VarBase[nNbVar];
		
		for(int n=0; n<m_arrVarsWS.size(); n++)
		{
			VarBase var = m_arrVarsWS.get(n);
			int nVarId = var.getVarDef().getId();
			m_arrVarsFullName[nVarId] = var;
		}
		
		for(int n=0; n<m_arrVarsLS.size(); n++)
		{
			VarBase var = m_arrVarsLS.get(n);
			int nVarId = var.getVarDef().getId();
			m_arrVarsFullName[nVarId] = var;
		}
		
		for(int n=0; n<m_arrVarsFile.size(); n++)
		{
			VarBase var = m_arrVarsFile.get(n);
			int nVarId = var.getVarDef().getId();
			m_arrVarsFullName[nVarId] = var;
		}
	}
	
	public void registerEditInMap(EditInMap edit)
	{
		if(m_arrEditInMap == null)
			m_arrEditInMap = new ArrayDyn<EditInMap>();
		m_arrEditInMap.add(edit);
	}

	private ArrayFixDyn<EditInMap> m_arrEditInMap = null;	// Array of all EditInMap
	
	public SharedProgramInstanceData getSharedProgramInstanceData()
	{
		return m_sharedProgramInstanceData;
	}
	
	public void clearSharedProgramInstanceData()
	{
		m_sharedProgramInstanceData = null;
	}
	
	public int getAndIncLastVarId()
	{
		int n = m_nLastVarId;
		m_nLastVarId++;
		return n;
	}

	private ArrayFixDyn<VarBase> m_arrVarsLS = new ArrayDyn<VarBase>();	// array of all VarBase of the linkage section
	private ArrayList<VarBase> m_arrVarsWS = new ArrayList<VarBase>();	// array of all VarBase of the working section
	private ArrayList<VarBase> m_arrVarsFile = new ArrayList<VarBase>();	// array of all VarBase of the File section
	
	//private Hashtable<String, VarBase> m_hashVarsFullName = null;		// hash table of the varBase indexed by their full name
	private VarBase m_arrVarsFullName[] = null;
	
	private VarBase m_lastVarCreated = null;
	private VarBase m_rootVar = null;
	SharedProgramInstanceData m_sharedProgramInstanceData = null;
	private int m_nLastVarId = 0;
	private Section m_currentSection = null;
	//private String m_csProgramName = null;

	/**
	 * @return
	 */
	public String getProgramName()
	{
		return m_program.m_csSimpleName;
	}
	
	protected CSQLStatus m_sqlStatus ;
	
	public CSQLStatus getSQLStatus()
	{
		return m_sqlStatus ;
	}

	public SQL getOrCreateSQL(String csStatement)	//, String csFileLine)
	{
		return getOrCreateSQLGeneral(csStatement, null);	//, csFileLine);
	}

	public SQL getOrCreateSQLForCursor(String csQuery, SQLCursor cursor)//, String csFileLine)
	{
		return getOrCreateSQLGeneral(csQuery, cursor);//, csFileLine);
	}
	
//	private SQL doSQLExecuteStart()	//String csQuery)
//	{
//		return new SQLExecuteStart(this);	//, csQuery);
//	}
	
	public SQL getOrCreateSQLGeneral(String csQuery, SQLCursor cursor)	//, String csFileLine)
	{
		if(csQuery != null && csQuery.length() > 0)	// Fast check for EXECUTE IMMEDIATE order 
		{
			char c = csQuery.charAt(0);
			if(c == 'e' || c == 'E')
			{
				String csQueryUpper = csQuery.toUpperCase();
				if(csQueryUpper.startsWith("EXECUTE IMMEDIATE"))
				{
					SQLExecuteStart sqlExecuteStart = new SQLExecuteStart(this);
					return sqlExecuteStart;
					//return doSQLExecuteStart();	//csQuery);
				}
			}
		}
		
		String csId;
		if(cursor != null)	// use cursor name instead of hashline
			csId = cursor.getUniqueCursorName() + "_" + csQuery;
		else
			csId = csQuery;
		int nHashQuery = csId.hashCode();
		
		if(!BaseResourceManager.getUseSQLObjectCache())
		{	
			SQL sql = new SQL(this, csQuery, cursor/*, csFileLine*/, nHashQuery);
			return sql;
		}
		
		SQL sql = null;
		if(m_hashSQL != null)
			sql = m_hashSQL.get(nHashQuery);	// The returned value may be a SQLOrderFrontEnd or a SQL object
		else
			m_hashSQL = new Hashtable<Integer, SQL>();
		
		if(sql != null)
		{
			sql.reuse(m_sqlStatus, getEnv(), cursor);
		}
		else
		{
			sql = new SQL(this, csQuery, cursor/*, csFileLine*/, nHashQuery);
			m_hashSQL.put(nHashQuery, sql);
		}
			
		return sql;
	}
	
	public void compressSharedProgramInstanceData()
	{
		if(m_sharedProgramInstanceData != null)
			m_sharedProgramInstanceData.compress();
	}
	
	public boolean isFirstInstance()
	{
		return !m_bInheritedSharedProgramInstanceData;
	}
	
	public void assignBufferWS(VarBuffer bufferWS)
	{
		if(m_arrVarsWS != null)
		{
			int nNbVars = m_arrVarsWS.size();
			for(int n=0; n<nNbVars; n++)
			{
				VarBase var = m_arrVarsWS.get(n);
				var.assignBufferExt(bufferWS);
			}
		}
		m_arrVarsWS = null;
	}
	
	public void assignBufferFile(VarBuffer bufferFile)
	{
		if(m_arrVarsFile != null)
		{
			int nNbVars = m_arrVarsFile.size();
			for(int n=0; n<nNbVars; n++)
			{
				VarBase var = m_arrVarsFile.get(n);
				var.assignBufferExt(bufferFile);
			}
		}
		//m_arrVarsFile = null;
	}

	
	public void assignBufferLS(VarBuffer bufferLS)
	{
		if(m_arrVarsLS != null)
		{
			int nNbVars = m_arrVarsLS.size();
			if(m_arrVarsLS.isDyn())
			{
				VarBase arr[] = new VarBase[nNbVars];
				m_arrVarsLS.transferInto(arr);
				ArrayFix<VarBase> arrVarDefFix = new ArrayFix<VarBase>(arr);
				m_arrVarsLS = arrVarDefFix;	// replace by a fix one (uning less memory)
			}
			
			for(int n=0; n<nNbVars; n++)
			{
				VarBase var = m_arrVarsLS.get(n);
				if(!var.isWSVar())
					var.assignBufferExt(bufferLS);
			}
			// Do not set to null, as the buffer must be set again on next program reuse
			// Instead, it is compressed
		}
	}
	
//	private boolean checkAllEditsAttributValidity()
//	{
//		if(m_arrEditInMap != null)
//		{
//			for(int n=0; n<m_arrEditInMap.size(); n++)
//			{
//				EditInMap editInMap = m_arrEditInMap.get(n);
//				if(!editInMap.isEditAttributsValid())
//					return false;
//			}
//		}
//		return true;
//	}
	
	private Hashtable<Integer, SQL> m_hashSQL = null;		// hash table of the varBase indexed by their full name
	private Hashtable<Integer, InitializeCache> m_hashInitializeCache = null;		// hash table of the varBase indexed by their full name
	private Hashtable<Integer, MoveCorrespondingEntryManager> m_hashMoveCorrespondingEntryManager = null;
	private boolean m_bInheritedSharedProgramInstanceData = false;
		
	public MoveCorrespondingEntryManager getOrCreateMoveCorrespondingEntryManager(VarDefBase varDefSource, VarDefBase varDefDest)
	{
		int nVarSourceIdWithSolvedDim = varDefSource.getIdSolvedDim();
		int nVarDestIdWithSolvedDim = varDefDest.getIdSolvedDim();
		int nVarSourceDest = (nVarSourceIdWithSolvedDim * 1024) + nVarDestIdWithSolvedDim;	// Make a hash value (unique for couple)
		
		MoveCorrespondingEntryManager manager = m_hashMoveCorrespondingEntryManager.get(nVarSourceDest);
		if(manager == null)
		{
			manager = new MoveCorrespondingEntryManager(); 
			m_hashMoveCorrespondingEntryManager.put(nVarSourceDest, manager);
		}
		return manager;
	}
	
	public InitializeCache getOrCreateInitializeCache(VarDefBase varDef)
	{
		int nVarIdWithSolvedDim = varDef.getIdSolvedDim();
		InitializeCache initializeCache = m_hashInitializeCache.get(nVarIdWithSolvedDim);
		if(initializeCache == null)
		{
			initializeCache = new InitializeCache(); 
			m_hashInitializeCache.put(nVarIdWithSolvedDim, initializeCache);
		}
		return initializeCache;
	}

//
//	public InitializeCache getOrCreateInitializeCacheAtFirstAppCall()
//	{
//		String csFileLine = StackStraceSupport.getFileLineAtFirstAppCall();	// Caller File Line
//		InitializeCache initializeCache = m_hashInitializeCache.get(csFileLine);
//		if(initializeCache == null)
//		{
//			initializeCache = new InitializeCache(); 
//			m_hashInitializeCache.put(csFileLine, initializeCache);
//		}
//		return initializeCache;
//	}

	
	public void registerCursor(SQLCursor cursor)
	{
		if(m_arrCursor == null)
			m_arrCursor = new ArrayDyn<SQLCursor>();
		m_arrCursor.add(cursor);
	}
	
	private long m_lTimeLastRunBegin_ms = 0;
	private long m_lTimeLastRunEnd_ms = 0;
	
	public void setLastTimeRunBegin()
	{
		m_lTimeLastRunBegin_ms = Time_ms.getCurrentTime_ms(); 
	}
	
	public long getTimeLastRunBegin_ms()
	{
		return m_lTimeLastRunBegin_ms;
	}
	
	public long getTimeLastRunEnd_ms()
	{
		return m_lTimeLastRunEnd_ms;
	}
	
	public long getTimeRun()
	{
		return m_lTimeLastRunEnd_ms - m_lTimeLastRunBegin_ms;
	}
	
	public void setCurrentSortCommand(SortParagHandler sortParagHandler)
	{
		m_sortParagHandler = sortParagHandler;
	}
	
	public SortParagHandler getCurrentSortParagHandler()
	{
		return m_sortParagHandler;
	}
	
	public CSQLStatus sqlRollback()
	{
		SQLException e = getEnv().rollbackSQL();
		if(e != null)
		{
			//String csFileLine = StackStraceSupport.getFileLineAtStackDepth(3);	// Caller File Line
			if(m_sqlStatus == null)
				m_sqlStatus = new CSQLStatus(); 
			m_sqlStatus.setSQLCode("Rollback", e, "sqlRollback"/*, csFileLine*/, null);
		}	
		else
		{
			if(m_sqlStatus == null)
				m_sqlStatus = new CSQLStatus(); 
			m_sqlStatus.setSQLCodeOk();
		}	
		return m_sqlStatus;
	}
		
	public CSQLStatus sqlCommit()
	{
		getEnv().autoFlushOpenFile();
		SQLException e = getEnv().commitSQL();
		if(e != null)
		{
			//String csFileLine = StackStraceSupport.getFileLineAtStackDepth(3);	// Caller File Line
			if(m_sqlStatus == null)
				m_sqlStatus = new CSQLStatus(); 
			m_sqlStatus.setSQLCode("Commit", e, "sqlCommit"/*, csFileLine*/, null);
		}	
		else
		{
			if(m_sqlStatus == null)
				m_sqlStatus = new CSQLStatus(); 
			m_sqlStatus.setSQLCodeOk();
		}	
		return m_sqlStatus;
	}	
	
	public void setCurrentMapRedefine(MapRedefine mapRedefined)
	{
	}
	
	public /*ProgramSequencerExt*/BaseProgramLoader getProgramLoader()
	{
		return m_baseProgramLoader;
	}

	KeyPressed GetKeyPressed()
	{
		return getEnv().GetKeyPressed();
	}
	
	void resetKeyPressed()
	{
		getEnv().resetKeyPressed();
	}
	
	void setKeyPressed(KeyPressed key)
	{
		getEnv().setKeyPressed(key);
	}
	
	void setKeyPressed(Var v)
	{
		getEnv().setKeyPressed(v);
	}
	
	private VarInternalInt m_varEIBCALEN = new VarInternalInt() ;	// Contains the total length of the parameters passed upon calling
	public void determineCommareaLength(BaseEnvironment env)
	{
		if(env == null || env.getCommarea() == null)
		{
			m_varEIBCALEN.set(0) ;
		}
		else
		{
			m_varEIBCALEN.set(env.getCommarea().getLength()) ;
		}
	}
	
	protected Var getCommAreaLength()
	{
		return m_varEIBCALEN ;
	}
	
	protected void setCommAreaLength(int n)
	{
		m_varEIBCALEN.set(n);
	}


	public void stdPrepareRunMain(BaseProgram prg)
	{
		prepareRunMain(prg);
	}
	
	public void changeBufferAndShiftPosition(char oldBuffer[], int nStartPos, int nLength, VarBuffer newVarBuffer, int nShift)
	{
		int nNbVars = m_arrVarsFullName.length;
		for(int n=0; n<nNbVars; n++)
		{
			VarBase var = m_arrVarsFullName[n];
			if(var != null)
				var.internalAssignBufferShiftPosition(oldBuffer, nStartPos, nLength, newVarBuffer, nShift);
		}
	}
	
	private void compress()
	{
		if(m_arrCursor != null)
		{
			int nSize = m_arrCursor.size();
			SQLCursor arr[] = new SQLCursor[nSize];
			m_arrCursor.transferInto(arr);
			ArrayFix<SQLCursor> arrFix = new ArrayFix<SQLCursor>(arr);
			m_arrCursor = arrFix;
		}
		
		if(m_arrSection != null)
		{
			int nSize = m_arrSection.size();
			Section arr[] = new Section[nSize];
			m_arrSection.transferInto(arr);
			ArrayFix<Section> arrFix = new ArrayFix<Section>(arr);
			m_arrSection = arrFix;
		}
		
		if(m_arrParagraph != null)
		{
			int nSize = m_arrParagraph.size();
			Paragraph arr[] = new Paragraph[nSize];
			m_arrParagraph.transferInto(arr);
			ArrayFix<Paragraph> arrFix = new ArrayFix<Paragraph>(arr);
			m_arrParagraph = arrFix;
		}
		
		if(m_arrEditInMap != null)
		{
			int nSize = m_arrEditInMap.size();
			EditInMap arr[] = new EditInMap[nSize];
			m_arrEditInMap.transferInto(arr);
			ArrayFix<EditInMap> arrFix = new ArrayFix<EditInMap>(arr);
			m_arrEditInMap = arrFix;
		}

		m_bCompressed = true;
	}
	
	public void prepareBeforeReturningToPool()
	{	
		detachFromEnv();
		
		if(!m_bCompressed)
			compress();
		
		// Close cursors
		if(m_arrCursor != null)
		{
			for(int n=0; n<m_arrCursor.size(); n++)
			{
				SQLCursor cursor = m_arrCursor.get(n);
				cursor.closeIfOpen();
			}
		}
		
		m_lTimeLastRunEnd_ms = Time_ms.getCurrentTime_ms();
	}
	
	public void setOldInstance()
	{
		m_bNewInstance = false;
	}
	
	public boolean isNewProgramInstance()
	{
		return m_bNewInstance;
	}
	
	public void unloadClassCode()
	{		
		SharedProgramInstanceData sharedProgramInstanceData = getSharedProgramInstanceData();
		int nNbCopy = sharedProgramInstanceData.getNbCopy();
		String csProgramName = getProgramName();
		for(int n=0; n<nNbCopy; n++)
		{
			String csCopyName = sharedProgramInstanceData.getCopy(n);
			CopyManager.removeCopyFormProg(csCopyName, csProgramName);
		}

		m_program.m_BaseProgramManager = null;	// Disconnect program form this (it's program manager)
		m_program = null;	// Symetrically disconnect
	}
	
	
//	
//	public void declareInstance(String csProgramName)
//	{
//		m_csProgramName2 = csProgramName;
//	}
//		
//	
//	public String getProgramName()
//	{
//		return m_csProgramName2;
//	}
//
//	
//	private String m_csProgramName2 = null;

	
	private boolean m_bNewInstance = true;
	
	private SortParagHandler m_sortParagHandler = null;	// object wrapping a SortCommand for supporting release call with input paragraphs.
	private ArrayFixDyn<SQLCursor> m_arrCursor = null;

	public abstract void prepareRunMain(BaseProgram prg);
	public abstract String getTerminalID();
	
	public abstract void setEnv(BaseEnvironment env);
	public abstract void detachFromEnv();
	public abstract BaseEnvironment getEnv();
	private BaseProgramLoader m_baseProgramLoader = null;
	// private ProgramSequencerExt m_baseProgramLoader = null;
	private boolean m_bCompressed = false;
}
