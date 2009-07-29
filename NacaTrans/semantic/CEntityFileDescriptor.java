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
package semantic;

import jlib.misc.IntegerRef;
import generate.CBaseLanguageExporter;
import semantic.Verbs.CEntityOpenFile;
import utils.CObjectCatalog;

public abstract class CEntityFileDescriptor extends CBaseLanguageEntity
{
	public CEntityFileDescriptor(int line, String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(line, name, cat, out);
	}

	@Override
	protected void RegisterMySelfToCatalog()
	{
		m_ProgramCatalog.RegisterFileDescriptor(this) ;
		m_FileSelect = m_ProgramCatalog.getFileSelect(GetName()) ;
	}


	protected CEntityFileSelect m_FileSelect ;


	public String ExportReference(int nLine)
	{
		return FormatIdentifier(GetDisplayName());
	}

	public CDataEntity GetRecord()
	{
		if (m_lstChildren.getFirst() != null)
		{
			CDataEntity le = FindFirstDataEntityAtLevel(1) ;
			if (le != null)
			{
				return le ;
			}
		}
		return null ;
	}
	
	public CDataEntity enumRecords(IntegerRef iIndex)
	{
		if(iIndex.get() >= m_lstChildren.size())
			return null;

		CBaseLanguageEntity le = null ;
		for (int i=iIndex.get(); i<m_lstChildren.size(); i++)
		{
			le = m_lstChildren.get(i) ;

			if (le.GetInternalLevel() == 1)	// The record is mandatory at level 1 
			{
				CDataEntity e = le.FindFirstDataEntityAtLevel(1);
				if (e != null)
				{
					iIndex.set(i+1);
					return e ;
				}
			}
		}
		
		iIndex.set(m_lstChildren.size());
		return null ;
	}
	
	
	

	protected CEntityOpenFile.OpenMode m_eAccessMode = null ;
	public void setFileAccessType(CEntityOpenFile.OpenMode access)
	{
		m_eAccessMode = access;		
	}
	public CEntityOpenFile.OpenMode getAccessMode()
	{
		return m_eAccessMode;
	}

	protected boolean m_bVariableFile = false ;
	protected CDataEntity m_RecSizeDependingOn = null ;
	protected CDataEntity m_eOutputBufferInitialValue = null ;
	public void setRecordSizeVariable(boolean variableFile)
	{
		m_bVariableFile = variableFile ; 
	}
	public void setRecordSizeVariable(CDataEntity depOn)
	{
		m_bVariableFile = true ; 
		m_RecSizeDependingOn = depOn ;
	}
	public CDataEntity getRecordSizeDepending()
	{
		return m_RecSizeDependingOn;
	}
	public boolean isRecordSizeVariable()
	{
		return m_bVariableFile ;
	}

	/**
	 * @param e
	 */
	public void setOutputBufferInitialValue(CDataEntity e)
	{
		m_eOutputBufferInitialValue  = e ;
	}
}
