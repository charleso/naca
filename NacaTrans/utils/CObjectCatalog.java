/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 2 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package utils;

import generate.CBaseLanguageExporter;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Vector;

import jlib.engine.BaseNotification;
import jlib.engine.BaseNotificationHandler;
import jlib.engine.NotificationEngine;
import jlib.xml.Tag;

import semantic.CBaseActionEntity;
import semantic.CBaseEntityFactory;
import semantic.CBaseExternalEntity;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.CEntityAttribute;
import semantic.CEntityClass;
import semantic.CEntityDataSection;
import semantic.CEntityExternalDataStructure;
import semantic.CEntityFileDescriptor;
import semantic.CEntityFileSelect;
import semantic.CEntityProcedure;
import semantic.CEntityProcedureDivision;
import semantic.CEntityProcedureSection;
import semantic.CICS.CEntityCICSLink;
import semantic.SQL.CEntitySQLCursor;
import semantic.SQL.CEntitySQLDeclareTable;
import semantic.Verbs.CEntityCallFunction;
import semantic.Verbs.CEntityCallProgram;
import semantic.Verbs.CEntityRoutineEmulation;
import semantic.forms.CEntityFieldRedefine;
import semantic.forms.CEntityResourceField;
import semantic.forms.CEntityResourceForm;
import semantic.forms.CEntityResourceFormContainer;
import utils.CobolTranscoder.ProcedureCallTree;


/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CObjectCatalog
{

	protected ProcedureCallTree m_CallTree = null ;
	protected CGlobalCatalog m_Global = null ;
	public COriginalLisiting m_Listing = null ;
	protected CEntityResourceFormContainer m_FormContainer = null ;
	protected boolean m_bUseCICSPreprocessor = false ;
	private NotificationEngine m_Engine;
	
	public CObjectCatalog(
		CGlobalCatalog cat, 
		COriginalLisiting listing, 
		CTransApplicationGroup.EProgramType eType,
		NotificationEngine engine)
	{
		m_Engine = engine ;
		m_Global = cat ;
		m_Listing = listing ;
		m_CallTree = new ProcedureCallTree() ;
		m_eProgType = eType ;
	}
		
	public CBaseExternalEntity GetExternalDataReference(String id, CBaseEntityFactory factory)
	{
		return GetExternalDataReference(id, "", factory) ;
	}
	
	public CBaseExternalEntity GetExternalDataReference(String id, String csRenamePattern, CBaseEntityFactory factory)
	{
		Transcoder.setCurrentObjectCatalog(this);
		CEntityResourceFormContainer mapset = GetFormContainer(id, factory) ;
		Transcoder.clearCurrentObjectCatalog();
		if (mapset != null)
		{
			if (m_arrMaps.isEmpty())
			{
				m_arrMaps.addAll(mapset.m_ProgramCatalog.m_arrMaps) ;
				m_arrSymbolicFields.addAll(mapset.m_ProgramCatalog.m_arrSymbolicFields) ;
				m_tabFields.putAll(mapset.m_ProgramCatalog.m_tabFields) ;
			}
			if (m_arrSaveMaps.isEmpty())
			{
				m_arrSaveMaps.addAll(mapset.m_ProgramCatalog.m_arrSaveMaps) ;
				m_arrSaveFields.addAll(mapset.m_ProgramCatalog.m_arrSaveFields) ;
				m_tabSaveFields.putAll(mapset.m_ProgramCatalog.m_tabSaveFields) ;
				m_tabSaveMaps.putAll(mapset.m_ProgramCatalog.m_tabSaveMaps) ;
			}
			if (!mapset.isSavCopy())
			{
				m_FormContainer = mapset ;
			}
			return mapset ; 
		}
	
		CEntityExternalDataStructure ext = m_Global.GetExternalDataStructure(id) ;
		if (ext == null)
		{
			m_bMissingIncludeStructure = true ;
			return null ;
		}
		String csName = ext.GetName() ;
		if (!csRenamePattern.equals(""))
		{
			csName = csRenamePattern + csName.substring(csRenamePattern.length()) ;
			ext.SetDisplayName(csName) ;
			ext.ApplyAliasPattern(csRenamePattern) ;
		}
		if (IsExistingDataEntity(csName, "") || m_tabFileDescriptor.containsKey(csName))
		{
			csName += "$Copy" ;
			while (m_tabExternalStructures.containsKey(csName))
			{
				csName += "1" ;
			}
			ext.SetDisplayName(csName) ;
		}
		if (ext != null && !ext.ignore())
		{
			if (ext.isInlined())
			{
				ImportCatalogUpdateDependencies(ext.m_ProgramCatalog, null, csRenamePattern) ;
			}
			else
			{
				ImportCatalogUpdateDependencies(ext.m_ProgramCatalog, ext, csRenamePattern) ;
			}
		}
		return ext ;
	}
	
	// PJD: Management of save maps
	public void clearSaveMaps()
	{
		m_arrSaveMaps.clear();
		m_arrSaveFields.clear();
		m_tabSaveMaps.clear();
		m_tabSaveFields.clear();
	}
	
	public void ExportRegisteredFormContainer(boolean bResources)
	{
		if (m_FormContainer != null)
		{			
			m_FormContainer.MakeXMLOutput(bResources);  
			m_FormContainer.StartExport() ;
			
			Tag t = CRulesManager.getInstance().getRule("ReduceMaps") ;
			if (t != null)
			{
				boolean bReduce = t.getValAsBoolean("active") ;
				if (bReduce)
				{ // no export of sav copy
					return ;
				}
			}
			
			// else, if not reducing maps
			if (m_FormContainer.GetSavCopy() != null)
			{
				m_FormContainer.GetSavCopy().StartExport() ;
			}
		}
	}

	private void ImportCatalogUpdateDependencies(CObjectCatalog cat, CBaseExternalEntity dep, String csRenamePattern)
	{
		Enumeration enumere = cat.m_tabDataEntities.keys() ;
		CDataEntity de = null ;
		String name = "" ;
		try 
		{
			name = (String)enumere.nextElement() ;
			de = cat.m_tabDataEntities.get(name) ;
			while (de != null)
			{
				de.m_Of = dep ;
				if (!csRenamePattern.equals(""))
				{
					name = csRenamePattern + name.substring(csRenamePattern.length()) ;
				}
				RegisterDataEntity(name, de) ;
				name = (String)enumere.nextElement() ;
				de = cat.m_tabDataEntities.get(name) ;
			}
		}
		catch (NoSuchElementException e)
		{
			de = null ;
		}

		CNameConflictSolver.CNameConflictItem item = null ;
		enumere = cat.m_ConflictSolver.m_tabConflicts.keys() ;
		try 
		{
			name = (String)enumere.nextElement() ;
			item = cat.m_ConflictSolver.m_tabConflicts.get(name) ;
			while (item != null)
			{
				for (int i=0; i<item.m_arrEntities.size(); i++)
				{
					de = item.m_arrEntities.get(i) ;
					de.m_Of = dep ;
					m_ConflictSolver.AddConflictedEntity(name, de) ;
				}
				CDataEntity e = m_tabDataEntities.get(name) ;
				if (e != null)
				{
					m_tabDataEntities.remove(name) ;
					m_ConflictSolver.AddConflictedEntity(name, e) ;
				}
				name = (String)enumere.nextElement() ;
				item = cat.m_ConflictSolver.m_tabConflicts.get(name) ;
			}
		}
		catch (NoSuchElementException e)
		{
			item = null ;
		}
		
		
		
		enumere = cat.m_tabSQLTables.elements() ;
		CEntitySQLDeclareTable sql = null ;
		try {sql = (CEntitySQLDeclareTable)enumere.nextElement() ;}
		catch (NoSuchElementException e)
		{
			sql = null ;
		}
		while (sql != null)
		{
			RegisterSQLTable(sql.GetName(), sql) ;
			try {sql = (CEntitySQLDeclareTable)enumere.nextElement() ;}
			catch (NoSuchElementException e)
			{
				sql = null ;
			}
		}
		
		for (int i=0; i<cat.GetNbAttributes(); i++)
		{
			CEntityAttribute att = cat.GetAttribute(i);
			att.ResetReferenceCount() ;
			RegisterAttribute(att) ;
		}
		
	}
	// container
	public void RegisterContainer(String name, CEntityClass eCont)
	{
		m_tabContainers.put(name, eCont) ;
	}
	public CEntityClass GetContainer(String name)
	{
		return m_tabContainers.get(name) ;
	}
	
	// data entity
	public void RegisterDataEntity(String name, CDataEntity eCont)
	{
		if (!name.equals(""))
		{
			CDataEntity eAlready = m_tabDataEntities.get(name);
			if (eAlready == null)
			{
				if (m_ConflictSolver.HasConflictForName(name))
				{
					m_ConflictSolver.AddConflictedEntity(name, eCont);
				}
				else
				{
					m_tabDataEntities.put(name, eCont) ;
				}
			}
			else if (eAlready != eCont)
			{
				m_tabDataEntities.remove(name) ; // 
				m_ConflictSolver.AddConflictedEntity(name, eAlready) ;
				m_ConflictSolver.AddConflictedEntity(name, eCont) ;
			}
		}
	}
	
	public CDataEntity GetDataEntity(String name, String of)
	{
		return GetDataEntity(0, name, of);
	}
	
	public CDataEntity GetDataEntity(int nLine, String name, String of)
	{
		CDataEntity eData = m_tabDataEntities.get(name) ;
		if (eData == null)
		{
			if (m_ConflictSolver.HasConflictForName(name))
			{
				eData = m_ConflictSolver.GetQualifiedReference(name, of);
				if (eData == null)
				{
					//int nLine = Transcoder.getAnalyseExpressionCurrentLine();
					if (of.equals(""))
					{
						Transcoder.logError(nLine, "ERROR : missing specialization ('OF') for variable : " + name);
					}
					else
					{
						Transcoder.logError(nLine, "ERROR : full declared reference not bound : " + name + " OF " + of);
					}
					eData = m_ConflictSolver.GetQualifiedReference(name, of);  // for debug 
					return null ; 
				}
				else
				{
					return eData ;
				}
			}
			else
			{
				Transcoder.addOnceUnboundReference(nLine, name);
				//Transcoder.ms_logger.error("ERROR : reference not bound : " + name); 
				eData = m_tabDataEntities.get(name) ;
				eData = m_ConflictSolver.GetQualifiedReference(name, of);
				return null;
			}
		}
		else
		{
			if (!of.equals(""))
			{
				if (eData.GetHierarchy().CheckAscendant(of))
				{
					return eData ;
				}
				else
				{
					//int nLine = Transcoder.getAnalyseExpressionCurrentLine();
					Transcoder.logError(nLine, "ERROR : full declared reference not bound : " + name + " OF " + of);
					eData = m_tabDataEntities.get(name) ;
					m_ConflictSolver.GetQualifiedReference(name, of);
					return null ;
				}
			}
			else
			{
				return eData ;
			}
		}
	}

	// Procedure	
	public void RegisterProcedure(String name, CEntityProcedure eCont, CEntityProcedureSection section)
	{
		CEntityProcedure proc = m_tabProcedures.get(name) ;
		if (proc != null)
		{ // conflict
			m_tabProcedures.remove(name);
			proc.setFullName() ;
			eCont.setFullName() ;
		}
		else
		{
			m_tabProcedures.put(name, eCont) ;
		}
		if (!m_arrProcedures.contains(eCont))
		{
			m_arrProcedures.add(eCont) ;
		}
	}
	public CEntityProcedure GetProcedure(String name, String section)
	{
		CEntityProcedure proc = m_tabProcedures.get(name) ;
		if (proc == null && !section.equals(""))
		{
			String fullName = name + "$" + section;
			proc = m_tabProcedures.get(fullName) ;
		}
		return proc ;
	}
	public void GetProcedureFromThru(String from, String to, Vector<String> arr)
	{
		boolean bOk = false ;
		for (int i=0; i<m_arrProcedures.size();i++)
		{
			CEntityProcedure proc = m_arrProcedures.get(i);
			String cs = proc.GetName() ; 
			if (bOk)
			{
				arr.addElement(cs) ;
				if (cs.equals(to))
				{
					bOk = false ;
					return ;
				}
			}
			else if (cs.equals(from))
			{
				bOk = true ;
				arr.addElement(cs);
			}
		}
	}	
	// Form container
	public CEntityResourceFormContainer GetFormContainer(String name, CBaseEntityFactory factory)
	{
		CEntityResourceFormContainer cont = m_Global.GetFormContainer(name, factory) ;
		return cont ;
	}
	
	public boolean CheckProgramReference(String prg, boolean bWithDFHCommarea, int nbParameters, boolean bRegisterSubProgram)
	{
		return m_Global.CheckProgramReference(prg, bWithDFHCommarea, nbParameters, bRegisterSubProgram) ;
	}
		
	// general
	public void RemoveObject(CBaseLanguageEntity e)
	{
		String name = e.GetName();
		RemoveObjectFromHashTable(m_tabContainers, e) ;
//		RemoveObjectFromHashTable(m_tabFormContainers, e) ;
		RemoveObjectFromHashTable(m_tabDataEntities, e) ;
		RemoveObjectFromHashTable(m_tabProcedures, e) ;
		m_ConflictSolver.RemoveObject(e) ;
	}
	private void RemoveObjectFromHashTable(Hashtable tab, Object obj)
	{
		Enumeration e = tab.keys();
		try
		{
			Object k = e.nextElement() ;
			while (k != null)
			{
				Object o = tab.get(k) ;
				if (o == obj)
				{
					tab.remove(k) ;
				}
				k = e.nextElement() ;
			}
		}
		catch (NoSuchElementException ex)
		{
		}
	}
	protected Hashtable<String, CEntityClass> m_tabContainers = new Hashtable<String, CEntityClass>() ; 
	protected Hashtable<String, CEntityProcedure> m_tabProcedures = new Hashtable<String, CEntityProcedure>() ; 
	protected Vector<CEntityProcedure> m_arrProcedures = new Vector<CEntityProcedure>() ; 
	protected Hashtable<String, CDataEntity> m_tabDataEntities = new Hashtable<String, CDataEntity>() ; 
	protected Hashtable<String, CEntitySQLCursor> m_tabSQLCursors = new Hashtable<String, CEntitySQLCursor>() ; 
	protected Hashtable<String, CEntitySQLDeclareTable> m_tabSQLTables = new Hashtable<String, CEntitySQLDeclareTable>() ; 
	protected Vector<CEntitySQLCursor> m_arrSQLCursors = new Vector<CEntitySQLCursor>() ; 
	protected CNameConflictSolver m_ConflictSolver = new CNameConflictSolver() ;

	public void RegisterSQLCursor(CEntitySQLCursor cur)
	{
		addImportDeclaration("SQL") ;
		m_tabSQLCursors.put(cur.GetName(), cur) ;
		m_arrSQLCursors.add(cur) ;
	}
	public void RegisterSQLCursor(String alias, CEntitySQLCursor cur)
	{
		m_tabSQLCursors.put(alias, cur) ;
	}
	public Vector GetSQLCursorList()
	{
		return m_arrSQLCursors ;
	}
	public CEntitySQLCursor GetSQLCursor(String csCursorName)
	{
		return m_tabSQLCursors.get(csCursorName);
	}

	public void RegisterSQLTable(String csTableName, CEntitySQLDeclareTable table)
	{
		m_tabSQLTables.put(csTableName, table);
	}

	public CEntitySQLDeclareTable GetSQLTable(String cs)
	{
		return m_tabSQLTables.get(cs);
	}

	public boolean IsExistingDataEntity(String name, String of)
	{
		CDataEntity eData = m_tabDataEntities.get(name) ;
		if (eData == null)
		{
			if (m_ConflictSolver.IsExistingDataEntity(name, of))
			{
				return true ;
			}
			else
			{
				return false ;
			}
		}
		else
		{
			if (!of.equals(""))
			{
				if (eData.GetHierarchy().CheckAscendant(of))
				{
					return true;
				}
				else
				{
					return false ;
				}
			}
			else
			{
				return true ;
			}
		}
	}


	// algorythmic analysis : attributes
	protected Vector<CEntityAttribute> m_arrAttributes = new Vector<CEntityAttribute>() ;
	public void RegisterAttribute(CEntityAttribute att)
	{
		m_arrAttributes.add(att) ;
	}
	public CEntityAttribute GetAttribute(int i)
	{
		if (i<m_arrAttributes.size())
		{
			return m_arrAttributes.get(i);
		}
		else
		{
			return null ;
		}
	}
	public int GetNbAttributes()
	{
		return m_arrAttributes.size();
	}
	
	// algorythmic analysis : maps
	protected Hashtable<String, CEntityResourceField> m_tabFields = new Hashtable<String, CEntityResourceField>() ;
	protected Vector<CEntityResourceField> m_arrSymbolicFields = new Vector<CEntityResourceField>() ;
	protected Vector<CEntityResourceField> m_arrSaveFields = new Vector<CEntityResourceField>() ;
	protected Hashtable<CEntityResourceField, CEntityResourceField> m_tabSaveFields = new Hashtable<CEntityResourceField, CEntityResourceField>() ;
	private Hashtable<CEntityResourceForm, CEntityResourceForm> m_tabSaveMaps = new Hashtable<CEntityResourceForm, CEntityResourceForm>() ;
	protected Vector<CEntityResourceForm> m_arrMaps = new Vector<CEntityResourceForm>() ;
	protected Vector<CEntityResourceForm> m_arrSaveMaps = new Vector<CEntityResourceForm>() ;
	protected Vector<CBaseActionEntity> m_arrMapCopy = new Vector<CBaseActionEntity>() ;
	protected Vector<CBaseActionEntity> m_arrMapSend = new Vector<CBaseActionEntity>() ;
	protected Hashtable<String, CEntityFieldRedefine> m_tabFieldRedefine = new Hashtable<String, CEntityFieldRedefine>() ;
	public void RegisterFieldRedefine(CEntityFieldRedefine f)
	{
		m_tabFieldRedefine.put(f.GetName(), f) ;
	}
	public void RegisterSymbolicField(CEntityResourceField f)
	{
		if (f != null)
		{
			m_arrSymbolicFields.add(f) ;
			m_tabFields.put(f.GetName(), f) ;
		}
	}
	public boolean IsExistingFieldRedefine(String name)
	{
		return m_tabFieldRedefine.containsKey(name) ; 
	}
	public void RegisterSaveField(CEntityResourceField sav, CEntityResourceField f)
	{
		m_arrSaveFields.add(sav) ;
		m_tabFields.put(sav.GetName(), sav) ;
		if (f != null)
		{
			m_tabSaveFields.put(sav, f) ;
		}
	}
	public void RegisterMap(CEntityResourceForm f)
	{
		m_arrMaps.add(f) ;
	}
	
	public void ClearSavCopy()
	{
		m_arrSaveMaps.clear();
		m_tabSaveMaps.clear();
	}	
	
	public void RegisterSaveMap(CEntityResourceForm f, CEntityResourceForm associated)
	{
		m_arrSaveMaps.add(f) ;
		m_tabSaveMaps.put(f, associated);
	}
	public void RegisterMapCopy(CBaseActionEntity act)
	{
		m_arrMapCopy.add(act);		
	}
	public void RegisterMapSend(CBaseActionEntity act)
	{
		m_arrMapSend.add(act);		
	}

	public int GetNbMapCopy()
	{
		return m_arrMapCopy.size() ;
	}
	public CBaseActionEntity getMapCopy(int i)
	{
		return m_arrMapCopy.get(i) ;
	}
	public int GetNbMapSend()
	{
		return m_arrMapSend.size() ;
	}
	public CBaseActionEntity getMapSend(int i)
	{
		return m_arrMapSend.get(i) ;
	}
	public int GetNbSymbolicFields()
	{
		return m_arrSymbolicFields.size();
	}
	public CEntityResourceField GetSymbolicField(int i)
	{
		if (i<m_arrSymbolicFields.size())
		{
			return m_arrSymbolicFields.get(i);
		}
		else
		{
			return null;
		}
	}
	public int GetNbSaveFields()
	{
		return m_arrSaveFields.size();
	}
	public CEntityResourceField GetSaveField(int i)
	{
		if (i<m_arrSaveFields.size())
		{
			return m_arrSaveFields.get(i);
		}
		else
		{
			return null;
		}
	}
	public CEntityResourceField GetAssociatedField(CEntityResourceField savfield)
	{
		return m_tabSaveFields.get(savfield);
	}

	public int GetNbSaveMap()
	{
		return m_arrSaveMaps.size();
	}
	public int GetNbMap()
	{
		return m_arrMaps.size();
	}
	public CEntityResourceForm GetSaveMap(int i)
	{
		if (i<m_arrSaveMaps.size())
		{
			return m_arrSaveMaps.get(i);
		}
		else
		{
			return null ;
		}
	}
	public CEntityResourceForm GetMap(int i)
	{
		if (i<m_arrMaps.size())
		{
			return m_arrMaps.get(i);
		}
		else
		{
			return null ;
		}
	}
	public CEntityResourceForm GetAssociatedMap(CEntityResourceForm map)
	{
		return m_tabSaveMaps.get(map);
	}

	public void Clear()
	{
		m_arrAttributes.clear() ;
		m_arrCICSLink.clear() ;
		m_arrCallProgram.clear() ;
		m_arrImportDeclarations = new Vector<String>() ;
		m_arrInitializedStructure.clear();
		m_arrMapCopy.clear();
		m_arrMaps.clear() ;
		m_arrMapSend.clear() ;
		m_arrProcedures.clear() ;
		m_arrPerformThrough.clear() ;
		m_arrSaveFields.clear();
		m_arrSaveMaps.clear() ;
		m_arrSections.clear() ;
		m_arrSQLCursors.clear() ;
		m_arrSymbolicFields.clear() ;
		m_arrTransID.clear() ;
		m_Listing.Clear() ;
		m_tabContainers.clear() ;
		m_tabDataEntities.clear() ;
		m_tabFields.clear() ;
		m_tabFileDescriptor.clear() ;
		m_tabFileSelect.clear() ;
		m_tabProcedures.clear() ;
		m_tabRoutineEmulation.clear();
		m_tabSaveFields.clear() ;
		m_tabSaveMaps.clear() ;
		m_tabSQLCursors.clear() ;
		m_tabSQLTables.clear() ;
		m_FormContainer = null ;
		m_WorkingSection = null ;
	}

	public String GetProgramForTransaction(String transID)
	{
		return m_Global.GetProgramForTransaction(transID);
	}

	protected Vector<CDataEntity> m_arrTransID = new Vector<CDataEntity>();
	public void RegisterVariableTransID(CDataEntity TID)
	{
		m_arrTransID.add(TID) ;
	}
	public int GetNbVariableTransID()
	{
		return m_arrTransID.size() ;
	}
	public CDataEntity GetVariableTransID(int i)
	{
		if (i < m_arrTransID.size())
		{
			return m_arrTransID.get(i) ;
		}
		else
		{
			return null ;
		}
	}

	/**
	 * @param string
	 * @param string2
	 */
	public void RegisterRoutineEmulation(String alias, String display)
	{
		CEntityRoutineEmulation emul = new CEntityRoutineEmulation(alias, display) ;
		m_tabRoutineEmulation.put(alias, emul) ;
	}
	public CEntityRoutineEmulation getRoutineEmulation(String alias) 
	{
		return m_tabRoutineEmulation.get(alias) ;
	}
	protected Hashtable<String, CEntityRoutineEmulation> m_tabRoutineEmulation = new Hashtable<String, CEntityRoutineEmulation>() ;

	protected Vector<String> m_arrImportDeclarations = new Vector<String>() ;
	public void addImportDeclaration(String cs)
	{
		if (!m_arrImportDeclarations.contains(cs))
		{
			m_arrImportDeclarations.addElement(cs);
		}
	}
	public int getNbImportDeclaration()
	{
		return m_arrImportDeclarations.size() ;
	}
	public String getImportDeclaration(int i)
	{
		return m_arrImportDeclarations.elementAt(i) ;
	}
	
	public void setMissingIncludeStructure()
	{
		m_bMissingIncludeStructure = true ;
	}
	protected boolean m_bMissingIncludeStructure = false ;
	public boolean isMissingIncludeStructure()
	{
		return m_bMissingIncludeStructure ;
	}
	
	public void registerSQLWarningContinue(String csArg)
	{
		m_SQLWarning = SQLWarningErrorType.WarningContinue;
		m_csSQLWarningArg = csArg;		
	}

	public void registerSQLWarningGoto(String csArg)
	{
		m_SQLWarning = SQLWarningErrorType.WarningGoto;
		m_csSQLWarningArg = csArg;		
	}
	
	public void RegisterSQLErrorContinue(String csArg)
	{
		m_SQLError = SQLWarningErrorType.ErrorContinue;
		m_csSQLErrorArg = csArg;		
	}

	public void registerSQLErrorGoto(String csArg)
	{
		m_SQLError = SQLWarningErrorType.ErrorGoto;
		m_csSQLErrorArg = csArg;		
	}
	
	public String getSQLWarningErrorStatement()
	{
		String cs = "" ;
		if(m_SQLWarning != null)
			cs += SQLWarningErrorType.getSQLWarningErrorStatement(m_SQLWarning, m_csSQLWarningArg);
		if (m_SQLError != null)
			cs += SQLWarningErrorType.getSQLWarningErrorStatement(m_SQLError, m_csSQLErrorArg);
		if (cs.equals(""))
			return null ;
		return cs;
	}
	
	protected SQLWarningErrorType m_SQLError = null;
	protected SQLWarningErrorType m_SQLWarning = null;
	protected String m_csSQLErrorArg = null;
	protected String m_csSQLWarningArg = null;
	/**
	 * @param section
	 */
	public void RegisterLinkageSection(CEntityDataSection section)
	{
		m_LinkageSection = section ;
	}
	protected CEntityDataSection m_LinkageSection = null ;
	public CEntityDataSection getLinkageSection()
	{
		return m_LinkageSection ;
	}
	public void RegisterWorkingSection(CEntityDataSection section)
	{
		m_WorkingSection = section ;
	}
	protected CEntityDataSection m_WorkingSection = null ;
	public CEntityDataSection getWorkingSection()
	{
		return m_WorkingSection ;
	}

	/**
	 * @param division
	 */
	public void RegisterProcedureDivision(CEntityProcedureDivision division)
	{
		m_ProcedureDivision = division ;
		m_CallTree.SetProcedureDivision(division) ;
	}
	protected CEntityProcedureDivision m_ProcedureDivision = null ;
	public CEntityProcedureDivision getProcedureDivision()
	{
		return m_ProcedureDivision ;
	}

	/**
	 * @return
	 */
	public int getNbCICSLink()
	{
		return m_arrCICSLink.size() ;
	}
	public int getNbCallProgram()
	{
		return m_arrCallProgram.size() ;
	}
	protected Vector<CEntityCICSLink> m_arrCICSLink = new Vector<CEntityCICSLink>() ;
	protected Vector<CEntityCallProgram> m_arrCallProgram = new Vector<CEntityCallProgram>() ;
	public CEntityCICSLink getCICSLink(int n)
	{
		if (n<m_arrCICSLink.size())
		{
			return m_arrCICSLink.get(n);
		}
		return null ;
	}
	public CEntityCallProgram getCallProgram(int n)
	{
		if (n<m_arrCallProgram.size())
		{
			return m_arrCallProgram.get(n);
		}
		return null ;
	}
	public void RegisterCICSLink(CEntityCICSLink l)
	{
		m_arrCICSLink.add(l);
	}
	public void RegisterCallProgram(CEntityCallProgram l)
	{
		m_arrCallProgram.add(l);
	}

	/**
	 * @param goodName
	 * @param currentEntity
	 */
	public void EntityRenamed(String goodName, CDataEntity e)
	{
		String name = e.GetName();
		RemoveObjectFromHashTable(m_tabContainers, e) ;
//		RemoveObjectFromHashTable(m_tabFormContainers, e) ;
		RemoveObjectFromHashTable(m_tabDataEntities, e) ;
		RemoveObjectFromHashTable(m_tabProcedures, e) ;
	}

	/**
	 * @param structure
	 */
	public void RegisterExternalDataStructure(CBaseExternalEntity structure)
	{
		String cs = structure.GetDisplayName() ;
		CBaseExternalEntity ext = m_tabExternalStructures.get(cs) ;
		if (ext != null) 
		{
			int n = 0 ;
			CBaseExternalEntity ext2 = ext ;
			String newname = "" ;
			while (ext2 != null)
			{
				n ++ ;
				newname = cs + "$" + n ;
				ext2 = m_tabExternalStructures.get(newname) ;
			}
			ext.SetDisplayName(newname) ;
			m_tabExternalStructures.remove(cs);
			m_tabExternalStructures.put(newname, ext) ;
		}
		m_tabExternalStructures.put(cs, structure);
	}
	protected Hashtable<String, CBaseExternalEntity> m_tabExternalStructures = new Hashtable<String, CBaseExternalEntity>() ;
	/**
	 * @param name
	 * @param string
	 * @return
	 */
	public boolean HasNameConflict(String name, String string)
	{
		CDataEntity e = m_tabDataEntities.get(name) ;
		if (e == null)
		{
			return m_ConflictSolver.HasConflictForName(name, string) ;
		}
		else
		{
			if (e.m_Of == null)
			{
				return string.equals("") ;
			}
			else
			{
				return e.m_Of.GetName().equals(string) ;
			}
		}
	}

	/**
	 * @param e
	 */
	public void RegisterPerformThrough(CEntityCallFunction e)
	{
		m_arrPerformThrough.add(e) ;	
	}
	protected Vector<CEntityCallFunction> m_arrPerformThrough = new Vector<CEntityCallFunction>();
	public int getNbPerformThrough()
	{
		return m_arrPerformThrough.size() ;
	}
	public CEntityCallFunction getPerformThrough(int i)
	{
		if (i<m_arrPerformThrough.size())
		{
			return m_arrPerformThrough.get(i) ;
		}
		else
		{
			return null ;
		}
	}

	/**
	 * @param e
	 */
	public void RegisterInitializedStructure(CEntityAttribute e)
	{
		m_arrInitializedStructure.add(e) ;
	}
	protected Vector<CEntityAttribute> m_arrInitializedStructure = new Vector<CEntityAttribute>() ;
	public int getNbInitializedStructure()
	{
		return m_arrInitializedStructure.size() ;
	}
	public CEntityAttribute getInitializedStructure(int i)
	{
		return m_arrInitializedStructure.get(i) ;
	}

	/**
	 * @return
	 */
	public int getNbSections()
	{
		return m_arrSections.size() ;
	}
	protected Vector<CEntityProcedureSection> m_arrSections = new Vector<CEntityProcedureSection>() ;
	public void RegisterProcedureSection(CEntityProcedureSection sec)
	{
		m_arrSections.add(sec) ;
	}
	public CEntityProcedureSection getProcedureSection(int n)
	{
		if (n>=0 && n<m_arrSections.size())
		{
			return m_arrSections.get(n) ;
		}
		return null ;
	}

	/**
	 * @return
	 */
	public ProcedureCallTree getCallTree()
	{
		return m_CallTree;
	}

	public void RegisterFileSelect(CEntityFileSelect select)
	{
		m_tabFileSelect.put(select.GetName(), select) ;		
	}
	public CEntityFileSelect getFileSelect(String name)
	{
		return m_tabFileSelect.get(name) ;
	}
	protected Hashtable<String, CEntityFileSelect> m_tabFileSelect = new Hashtable<String, CEntityFileSelect>() ;
	public void RegisterFileDescriptor(CEntityFileDescriptor descriptor)
	{
		String name = descriptor.GetName() ;
		if (IsExistingDataEntity(name, ""))
		{
			CDataEntity e = GetDataEntity(name, "") ;
			if (e != null)
			{
				String disp = e.GetDisplayName() ;
				if (name.equalsIgnoreCase(disp))
				{
					disp += "$1" ;
					e.SetDisplayName(disp) ;
				}
			}
		}
		else if (m_tabExternalStructures.containsKey(name))
		{
			CBaseExternalEntity e = m_tabExternalStructures.get(name) ;
			if (e != null)
			{
				String disp = e.GetDisplayName() ;
				while (name.equalsIgnoreCase(disp) || m_tabExternalStructures.containsKey(disp))
				{
					disp += "$Copy" ;
					e.SetDisplayName(disp) ;
				}
				m_tabExternalStructures.put(disp, e) ;
			}
		}
		m_tabFileDescriptor.put(name, descriptor) ;
	} 
	public void RegisterFileDescriptor(String name, CEntityFileDescriptor descriptor)
	{
		m_tabFileDescriptor.put(name, descriptor) ;
	}
	protected Hashtable<String, CEntityFileDescriptor> m_tabFileDescriptor = new Hashtable<String, CEntityFileDescriptor>() ;
	public Collection<CEntityFileDescriptor> getFileDescriptors()
	{
		return m_tabFileDescriptor.values() ;
	}
	public CEntityFileDescriptor getFileDescriptor(String name)
	{
		CEntityFileDescriptor eFD = m_tabFileDescriptor.get(name) ;
		if (eFD != null)
		{
			return  eFD ;
		}
		
		CDataEntity record = GetDataEntity(name, "") ;
		if (record != null)
		{
			Enumeration<CEntityFileDescriptor> enm = m_tabFileDescriptor.elements() ;
			while (enm.hasMoreElements())
			{
				eFD = enm.nextElement() ;
				CDataEntity r = eFD.GetRecord() ;
				if (r == record)
				{
					return eFD ;
				}
			}
		}
		return null ;
	}

	public void setExporter(CBaseLanguageExporter out)
	{
		m_Exporter = out ;
	}
	protected CBaseLanguageExporter m_Exporter = null;
	public CTransApplicationGroup.EProgramType getProgramType()
	{
		return m_eProgType;
	}  
	protected CTransApplicationGroup.EProgramType m_eProgType = null ;


	public CGlobalCatalog GetGlobalCatalog()
	{
		return m_Global ;
	}

	public void SendNotifRequest(BaseNotification notif)
	{
		m_Engine.SendNotification(notif) ;
	}
	public void RegisterNotifHandler(BaseNotificationHandler handler) 
	{
		m_Engine.RegisterNotificationHandler(handler) ;
	}


}
