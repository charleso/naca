/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 7 oct. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package utils;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Vector;

import com.sun.org.apache.xml.internal.utils.StringVector;

import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CNameConflictSolver
{
	protected class CNameConflictItem
	{
		String m_ConflictName = "" ;
		Vector<CDataEntity> m_arrEntities = new Vector<CDataEntity>() ;
		//Vector m_arrHierachies = new Vector() ;
	}
	
	protected Hashtable<String, CNameConflictItem> m_tabConflicts = new Hashtable<String, CNameConflictItem>() ;

	public void AddConflictedEntity(String name, CDataEntity eCont)
	{
		CNameConflictItem item = m_tabConflicts.get(name) ;
		if (item == null)
		{
			item = new CNameConflictItem() ;
			item.m_ConflictName = name ;
			m_tabConflicts.put(name, item);
			item.m_arrEntities.add(eCont) ;
			CEntityHierarchy newHier = eCont.GetHierarchy() ;
			if (newHier == null)
			{
				int n = 0 ;
			}
			//item.m_arrHierachies.add(newHier) ;
		}
		else
		{
			if (item.m_arrEntities.contains(eCont))
			{
				return ;
			}
					
			item.m_arrEntities.add(eCont) ;
			CEntityHierarchy newHier = eCont.GetHierarchy() ;
			if (newHier == null)
			{
				int n = 0 ;
			}
			//item.m_arrHierachies.add(newHier) ;
			
			StringVector arr = new StringVector() ;
			boolean bToDo = false ;
			for (int i=0; i<item.m_arrEntities.size(); i++)
			{
				CDataEntity e = item.m_arrEntities.get(i) ;
				if (e.m_Of == null)
				{
					String cs = e.GetName() ;
					if (arr.contains(cs))
					{
						bToDo = true ;
					}
					else
					{
						arr.addElement(cs) ;
					}
				}
			}
			
			if (bToDo)
			{
				//int counter = 0 ;
				// rename entities, except the first one, which is not renamed 
				for (int i=1; i<item.m_arrEntities.size(); i++)
				{
					CDataEntity currentEntity = item.m_arrEntities.get(i);
					if (currentEntity.m_Of == null)
					{ // if this entity is part of an external structure (like COPY), this name is qualified this way
						CEntityHierarchy hier = currentEntity.GetHierarchy() ;
						CEntityHierarchy tab[] = new CEntityHierarchy[item.m_arrEntities.size()-1] ;
						int k = 0 ; 
						for (int j=0; j<item.m_arrEntities.size(); j++)
						{
							if (i != j)
							{
								CDataEntity d = item.m_arrEntities.get(j) ;
								tab[k] = d.GetHierarchy();
								k ++ ;
							}
						}
						String goodName = hier.FindGoodName(tab, currentEntity.GetName(), i) ;
//						if (goodName.equals(""))
//						{
//							goodName = String.valueOf(counter);
//							counter ++ ;
//						}
//						goodName = currentEntity.GetName() + "$" + goodName ;
						currentEntity.m_ProgramCatalog.EntityRenamed(goodName, currentEntity) ;
						currentEntity.SetName(goodName);
					}
				}
			}
		}
	}
	
	public boolean HasConflictForName(String name)
	{
		return m_tabConflicts.containsKey(name) ;
	}
	public boolean HasConflictForName(String name, String memberOf)
	{
		CNameConflictItem item = m_tabConflicts.get(name) ;
		if (item == null)
		{
			return false ;
		}
		else
		{
			if (memberOf.equals(""))
			{
				for (int i=0; i<item.m_arrEntities.size(); i++)
				{
					CDataEntity d = item.m_arrEntities.get(i) ;
					if (d.m_Of == null)
					{
						return true ;
					}	
				}			
				return false ;
			}
			else
			{
				for (int i=0; i<item.m_arrEntities.size(); i++)
				{
					CDataEntity d = item.m_arrEntities.get(i) ;				
					CEntityHierarchy hier = d.GetHierarchy() ;				
					if (hier.CheckAscendant(memberOf))
					{
						return true ;
					}
				}
				return false ;
			}
		}
	}
	
	public boolean IsExistingDataEntity(String name, String of)
	{
		CNameConflictItem item = m_tabConflicts.get(name) ;
		if (item == null)
		{
			return false ;
		}
		else
		{
			if (of.equals(""))
			{
				CDataEntity eData = null ;
				for (int i=0; i<item.m_arrEntities.size(); i++)
				{
					CDataEntity d = item.m_arrEntities.get(i) ;
					if (d.m_Of == null)
					{
						if (eData == null)
						{
							eData = d;
						}
						else
						{ // there are 2 entries with the same ascendant
							return false ;
						}
					}	
				}			
				return true ;
			}
			else
			{
				CDataEntity eData = null ;
				for (int i=0; i<item.m_arrEntities.size(); i++)
				{
					CDataEntity d = item.m_arrEntities.get(i) ;				
					CEntityHierarchy hier = d.GetHierarchy() ;				
					if (hier.CheckAscendant(of))
					{
						if (eData == null)
						{
							eData = d;
						}
						else
						{ // there are 2 entries with the same ascendant
							return false ;
						}
					}
				}
				if (eData == null)
				{
					return false ;
				}
				return true ;
			}
		}
	}
	public CDataEntity GetQualifiedReference(String name, String of)
	{
		CNameConflictItem item = m_tabConflicts.get(name) ;
		if (item == null)
		{
			return null ;
		}
		else
		{
			if (of.equals(""))
			{
				CDataEntity eData = null ;
				for (int i=0; i<item.m_arrEntities.size(); i++)
				{
					CDataEntity d = item.m_arrEntities.get(i) ;
					if (d.m_Of == null)
					{
						if (eData == null)
						{
							eData = d;
						}
						else
						{ // there are 2 entries with the same ascendant
							return null ;
						}
					}	
				}			
				return eData ;
			}
			else
			{
				CDataEntity eData = null ;
				for (int i=0; i<item.m_arrEntities.size(); i++)
				{
					CDataEntity d = item.m_arrEntities.get(i) ;				
					CEntityHierarchy hier = d.GetHierarchy() ;				
					if (hier.CheckAscendant(of))
					{
						if (eData == null)
						{
							eData = d;
						}
						else
						{ // there are 2 entries with the same ascendant
							return null ;
						}
					}
				}
				if (eData == null)
				{
					return null ;
				}
				return eData ;
			}
		}
	}

	/**
	 * @param e
	 */
	public void RemoveObject(CBaseLanguageEntity e)
	{
//		StringVector arrToRemove = new StringVector() ;
		Enumeration enumere = m_tabConflicts.elements() ;
		try
		{
			CNameConflictItem item = (CNameConflictItem)enumere.nextElement() ;
			while (item != null)
			{
				if (item.m_arrEntities.contains(e))
				{
					item.m_arrEntities.remove(e) ;
					if (item.m_arrEntities.size() == 1)
					{
						CDataEntity alone = item.m_arrEntities.get(0);
						String itemName = item.m_ConflictName ;

						try
						{
							item = (CNameConflictItem)enumere.nextElement() ;
						}
						catch (NoSuchElementException ex)
						{
							item = null ;
						}
						m_tabConflicts.remove(itemName) ;
						
						String cs = alone.GetName() ;
						int nPos = cs.indexOf('$') ;
						if (nPos>0)
						{
							cs = cs.substring(0, nPos) ;
						}
						alone.m_ProgramCatalog.EntityRenamed(cs, alone);
						alone.SetName(cs) ;
						continue ;
//						arrToRemove.addElement(item.m_ConflictName) ;
					}
				}
				item = (CNameConflictItem)enumere.nextElement() ;
			}
		}
		catch (NoSuchElementException ex)
		{
		}
//		for (int i=0; i<arrToRemove.size(); i++)
//		{
//			String cs = arrToRemove.elementAt(i) ;
//			m_tabConflicts.remove(cs) ;
//		}
	}
}
