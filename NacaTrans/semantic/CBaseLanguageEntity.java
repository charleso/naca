/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
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
package semantic;

import generate.*;

import java.util.*;

import org.apache.log4j.Logger;

import parser.CLanguageElement;

import semantic.expression.CBaseEntityCondition;
import utils.*;
import utils.SQLSyntaxConverter.SQLFunctionConvertion;
import utils.SQLSyntaxConverter.SQLSyntaxConverter;


/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CBaseLanguageEntity //extends CBaseEntity
{
	// deprecated : use CBaseLanguageExporter.FormatIdentifier instead
//	public String NormalizeCobolVariableName(String cs)
//	{
//		String csNormalized = cs.trim().replace('-', '_').toUpperCase();
//		return csNormalized;
//	}
	
	protected String GetDefaultName()
	{
		
		String name = "Filler$" + m_output.GetLastFillerIndex() ;
		return name ;
	}
	public String GetName()
	{
		return m_Name ;
	}
	private String m_Name = "" ;
	public void SetName(String name)
	{
		m_Name = name ;
		RegisterMySelfToCatalog() ;		
	}
	public void Rename(String name)
	{
		if (!m_Name.equals(""))
		{
			m_ProgramCatalog.RemoveObject(this) ;
		}
		m_Name = name ;
		RegisterMySelfToCatalog() ;		
	}
	protected abstract void RegisterMySelfToCatalog() ;
	public CObjectCatalog m_ProgramCatalog = null ;

//	protected CEntityHierarchy m_Hierarchy = null ;
	protected CBaseLanguageEntity m_parent = null ;
	public void SetParent(CBaseLanguageEntity e)
	{
		if (m_parent != null)
		{
			boolean b = m_parent.m_lstChildren.remove(this) ;
			int n =0 ;
		}
		m_parent = e ;
	}
	public CBaseLanguageEntity GetParent()
	{
		return m_parent ;
	}
	
	public CBaseLanguageEntity getTopParent()
	{
		CBaseLanguageEntity parent = GetParent();
		if(parent != null)
			return parent.getTopParent();
		return this;
	}
	
	public CEntityHierarchy GetHierarchy()
	{
		CEntityHierarchy hier = null ;
		if (m_parent == null)
		{
			hier = new CEntityHierarchy() ;
		}
		else
		{
			hier = m_parent.GetHierarchy() ;
		}
		if (!m_Name.equals(""))
		{
			hier.AddLevel(m_Name);
		}
		if (m_arrAliases != null)
		{
			for (String alias : m_arrAliases)
			{
				hier.AddLevel(alias) ;
			}
		}
		return hier ;
	}
	
	private void AddAlias(String alias)
	{
		if (m_arrAliases == null)
		{
			m_arrAliases = new Vector<String>() ;
		}
		m_arrAliases.add(alias) ;
	}
	protected Vector<String> m_arrAliases = null ;
	protected void ApplyAliasPatternToChildren(String csPattern)
	{
		for (CBaseLanguageEntity le : m_lstChildren)
		{
			String name = le.GetName() ;
			name = csPattern + name.substring(csPattern.length()) ;
			le.AddAlias(name) ;
			le.ApplyAliasPatternToChildren(csPattern) ;
		}
	}
	
	
	//protected Logger m_logger = Transcoder.ms_logger ;
 
 
 	private int m_line = 0 ;
 	
 	public void SetLine(int line)
 	{
 		m_line = line ;
 		Transcoder.setLine(m_line);
 	}
 	public int getLine()
 	{
 		return m_line;
 	}
 	
	protected CBaseLanguageEntity(int line, String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		SetLine(line);
		m_ProgramCatalog = cat ;
		if (cat == null)
		{
			int n=0 ;
		}
		m_Name = name ;
		if (!m_Name.equals(""))
		{
			RegisterMySelfToCatalog() ;
		}
		m_output = out ;
	}
	public void AddChild(CBaseLanguageEntity e)
	{
		if (e != this)
		{
			m_lstChildren.add(e) ;
			e.SetParent(this) ;
		}
	}
	
	public CBaseLanguageEntity getChildAtIndex(int nIndex)
	{
		return m_lstChildren.get(nIndex);
	}
	
	public void registerDeferredChildren(CBaseLanguageEntity entity, CLanguageElement element)
	{
		if (entity != this)
		{
			DeferredItem deferredItem = new DeferredItem(entity, element);
			if(m_arrDeferredChildren == null)
				m_arrDeferredChildren = new ArrayList<DeferredItem>() ;
			m_arrDeferredChildren.add(deferredItem);
		}
	}
	
	public ArrayList<DeferredItem> getDeferredChildren()
	{
		return m_arrDeferredChildren;
	}
	
	public void AddChildSpecial(CBaseLanguageEntity e)
	{
		if (e != this)
		{
			m_lstChildren.add(e) ;
		}
	}
	protected void ExportChildren()
	{
		ListIterator i = m_lstChildren.listIterator() ;
		try
		{
			CBaseLanguageEntity le = (CBaseLanguageEntity)i.next() ;
			while (le != null)
			{
				if (!le.ignore())
				{
					le.DoExport();
				}
				else
				{
					boolean b = le.ignore();
					int n=0 ; // debug
					//le.DoExport();	// PJD 16/10/08
				}
				le = (CBaseLanguageEntity)i.next() ;
			}
		}
		catch (NoSuchElementException e)
		{
			//e.printStackTrace();
			//System.out.println(e.toString());
		}
	}
	
	
	public Vector<CBaseLanguageEntity> GetListOfChildren()
	{
		Vector<CBaseLanguageEntity> v = new Vector<CBaseLanguageEntity>() ;
		ListIterator i = m_lstChildren.listIterator() ;
		try
		{
			CBaseLanguageEntity le = (CBaseLanguageEntity)i.next() ;
			while (le != null)
			{
				v.add(le);
				le = (CBaseLanguageEntity)i.next() ;
			}
		}
		catch (NoSuchElementException e)
		{
			//e.printStackTrace();
			//System.out.println(e.toString());
		}
		return v ;
	}
	
	protected LinkedList<CBaseLanguageEntity> m_lstChildren = new LinkedList<CBaseLanguageEntity>() ;
	private ArrayList<DeferredItem> m_arrDeferredChildren = null;	// Arrey of items that cannot be semantically analyzed at declaration point. They are analyzed just before ProecdureDivision. This is used in case the Cobol references variables that are not defined yet.   
	public boolean HasChildren()
	{
		return ! m_lstChildren.isEmpty();
	}
	private CBaseLanguageExporter m_output = null ;
	
	public void setLanguageExporter(CBaseLanguageExporter exp)
	{
		m_output = exp ;
		ListIterator i = m_lstChildren.listIterator() ;
		try
		{
			CBaseLanguageEntity le = (CBaseLanguageEntity)i.next() ;
			while (le != null)
			{
				le.setLanguageExporter(exp) ;
				le = (CBaseLanguageEntity)i.next() ;
			}
		}
		catch (NoSuchElementException e)
		{
			e.printStackTrace();
			//System.out.println(e.toString());
		}
	}
	
	protected SQLDumper getSQLDumper()
	{
		if(m_output != null)
			return m_output.getSQLDumper();
		return null;
	}
	
//	protected SQLSyntaxConverter getSQLSyntaxConverter()
//	{
//		if(m_output != null)
//			return m_output.getSQLSyntaxConverter();
//		return null;
//	}
	
	protected CBaseLanguageExporter GetXMLOutput()
	{
		return m_output;
	}
	protected void WriteComment(String text)
	{
		m_output.WriteComment(text, getLine());
	}
	protected void WriteLine(String text)
	{
		m_output.WriteLine(text, getLine());
	}
	protected void WriteLine(String text, int l)
	{
		m_output.WriteLine(text, l);
	}
	protected void WriteEOL()
	{
		m_output.WriteEOL(getLine());
	}
	protected void WriteWord(String text)
	{
		m_output.WriteWord(text, getLine());
	}
	protected void WriteLongString(String text)
	{
		m_output.WriteLongString(text, getLine());
	}
	protected void WriteWord(String text, int l)
	{
		m_output.WriteWord(text, l);
	}
	protected void StartOutputBloc()
	{
		m_output.StartBloc();
	}
	protected void EndOutputBloc()
	{
		m_output.EndBloc();
	}
	protected String FormatIdentifier(String cs)
	{
		if (m_output != null)
		{
			return m_output.FormatIdentifier(cs);
		}
		else
		{
			return cs ;
		}
	}
	protected abstract void DoExport() ;
	protected void DoExport(CBaseLanguageEntity le)
	{
		le.DoExport() ;
	}
	public void StartExport()
	{
		DoExport() ;
		m_output.closeOutput() ;
	}
	
	protected void ASSERT(Object o)
	{
		if (o == null)
		{
			throw new NacaTransAssertException("ASSERT if null") ;
		}
	}
	public int GetInternalLevel()
	{
		return 0 ;
	}

	public CBaseLanguageEntity FindLastEntityAvailableForLevel(int level)
	{
		CBaseLanguageEntity le = null ;
		try
		{
			le = m_lstChildren.getLast() ;
		}
		catch (NoSuchElementException e)
		{
			//e.printStackTrace();
			return this ;
		}
		if (le.GetInternalLevel()>0 && le.GetInternalLevel() < level)
		{
			CBaseLanguageEntity e = le.FindLastEntityAvailableForLevel(level);
			if (e != null)
			{
				return e ;
			}
			else
			{
				return le ;
			}
		}
//		else if (m_parent != null)
//		{
//			return m_parent.FindLastEntityAvailableForLevel(level) ;
//		}
		else
		{
			return null ;
		}
	}

	public CDataEntity FindFirstDataEntityAtLevel(int level)
	{
		CBaseLanguageEntity le = null ;

		for (int i=0; i<m_lstChildren.size(); i++)
		{
			le = m_lstChildren.get(i) ;

			if (le.GetInternalLevel() <= level)
			{
				CDataEntity e = le.FindFirstDataEntityAtLevel(level);
				if (e != null)
				{
					return e ;
				}
			}
		}
		return null ;
	}
	
	public String GetProgramName()
	{
		if (m_parent != null)
		{
			return m_parent.GetProgramName();
		}
		return "" ;
	}
	public CEntityProcedureSection getSectionContainer()
	{
		if (m_parent != null)
		{
			return m_parent.getSectionContainer() ;
		}
		else
		{
			return null ;
		}		
	} 
	
	public boolean ignore() 
	{
		return m_bIgnore ;
	}
	protected boolean isChildrenIgnored()
	{
		Iterator i = m_lstChildren.iterator() ;
		boolean ignore = true ;
		try
		{
			CBaseLanguageEntity e = (CBaseLanguageEntity)i.next() ;
			while (e != null)
			{
				ignore &= e.ignore() ;
				e = (CBaseLanguageEntity)i.next() ;
			}
		}
		catch (NoSuchElementException e)
		{
			//e.printStackTrace();
		}
		return ignore ;
	}
	public void UpdateCondition(CBaseEntityCondition condition, CBaseEntityCondition newCond)
	{
		int n=0 ;
		n++ ;
		// nothing
	}
	public void Clear()
	{
		Iterator i = m_lstChildren.iterator() ;
		try
		{
			CBaseLanguageEntity e = (CBaseLanguageEntity)i.next() ;
			while (e != null)
			{
				e.Clear();
				e = (CBaseLanguageEntity)i.next() ;
			}
		}
		catch (NoSuchElementException e)
		{
			//e.printStackTrace();
		}
		m_lstChildren.clear();
		m_parent = null ;
		m_ProgramCatalog = null ;
		m_output = null ;
	}
	/**
	 * @param entity
	 * @param newCond
	 * @return
	 */
	public boolean UpdateAction(CBaseActionEntity entity, CBaseActionEntity newCond)
	{
		// to be overwritten
		Transcoder.logError(getLine(), "Unexpecting call to method UpdateAction in "+ this.getClass().toString()) ;
		return false;
	}
	/**
	 * @param link
	 * @param call
	 */
//	public void ReplaceChild(CBaseLanguageEntity link, CBaseLanguageEntity call)
//	{
//		int n = m_lstChildren.indexOf(link) ;
//		if (n>=0)
//		{
//			m_lstChildren.set(n, call) ;
//		}
//	}
	public void SetIgnoreStructure()
	{
		m_bIgnore = true ;
		ListIterator i = m_lstChildren.listIterator() ;
		try
		{
			CBaseLanguageEntity le = (CBaseLanguageEntity)i.next() ;
			while (le != null)
			{
				le.SetIgnoreStructure() ;
				le = (CBaseLanguageEntity)i.next() ;
			}
		}
		catch (NoSuchElementException e)
		{
			e.printStackTrace();
			//System.out.println(e.toString());
		}
	}
	protected boolean m_bIgnore = false ;
	/**
	 * @param start
	 * @param end
	 * @return
	 */
	public CBaseLanguageEntity[] GetChildrenList(CBaseLanguageEntity start, CBaseLanguageEntity end)
	{
		int nStart = 0 ;
		if (start != null)
		{
			nStart = m_lstChildren.indexOf(start) ;
		}
		int nEnd = m_lstChildren.size()-1 ;
		if (end != null)
		{
			nEnd = m_lstChildren.indexOf(end) ;
		}
		if(nEnd < nStart)	// PJD Added; variables must be swapped 
		{
			int nTmp = nEnd;
			nEnd = nStart;
			nStart = nTmp;
			Transcoder.logError(getLine(), "Paragraphs " + start.GetName() + " and + " +end.GetName() + " are in wrong order");
		}
		List<CBaseLanguageEntity> l = m_lstChildren.subList(nStart, nEnd+1) ;
		CBaseLanguageEntity[] arr = new CBaseLanguageEntity[l.size()] ;
		l.toArray(arr) ;
		return arr;
	}
	/**
	 * @param th
	 * @param call1
	 */
	public void ReplaceChild(CBaseLanguageEntity th, CBaseLanguageEntity call1)
	{
		int n = m_lstChildren.indexOf(th) ;
		if (n>=0)
		{
			m_lstChildren.set(n, call1) ;
		}
	}
	/**
	 * @param call2
	 * @param call1
	 */
	public void AddChild(CBaseLanguageEntity call2, CBaseLanguageEntity call1)
	{
		if (call1 == null)
		{
			m_lstChildren.add(0, call2) ;
		}
		else
		{
			int n = m_lstChildren.indexOf(call1) ;
			if (n>=0)
			{
				m_lstChildren.add(n+1, call2) ;
			}
		}
	}

	public String GetDisplayName()
	{
		if (m_csDisplayName.equals(""))
		{
			return GetName() ;
		}
		else
		{
			return m_csDisplayName ;
		}
	}
	protected String m_csDisplayName = "" ;
	public void SetDisplayName(String name)
	{
		m_csDisplayName = name ;
	}
	
	public boolean canOwnTableSize()
	{
		return false;
	}
	
	public void registerRequiredToolsLib(String csRequiredToolsLib)
	{
		if(m_arrRequiredToolsLib == null)
			m_arrRequiredToolsLib = new ArrayList<String>();
		m_arrRequiredToolsLib.add(csRequiredToolsLib);
	}
	
	public void exportRequiredToolsLibDeclarations()
	{
		if(m_arrRequiredToolsLib == null)
			return ;
		for(int n=0; n<m_arrRequiredToolsLib.size(); n++)
		{
			String csClassName = m_arrRequiredToolsLib.get(n);
			String csInstanceName = "m_" + csClassName;
			WriteLine(csClassName + " " + csInstanceName + " = new " + csClassName + "(getProgramManager());");
		}
	}
	
	private ArrayList<String> m_arrRequiredToolsLib = null;
}
