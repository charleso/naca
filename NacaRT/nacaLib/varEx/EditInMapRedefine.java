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
/*
 * Created on 30 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;


import jlib.misc.AsciiEbcdicConverter;
import nacaLib.basePrgEnv.BaseProgramLoader;
import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.programPool.SharedProgramInstanceData;
import nacaLib.programPool.SharedProgramInstanceDataCatalog;
import nacaLib.tempCache.TempCache;
import nacaLib.tempCache.TempCacheLocator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author PJD
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EditInMapRedefine extends Edit
{
	EditInMapRedefine(DeclareTypeBase declareTypeBase)	//DeclareTypeEditInMapRedefine declareTypeEditInMapRedefine)
	{
		//super(declareTypeEditInMapRedefine);
		super(declareTypeBase);
	}
			
	protected EditInMapRedefine()
	{
		super();
	}
	
	protected VarBase allocCopy()
	{
		EditInMapRedefine v = new EditInMapRedefine();
		return v;
	}
	
	public String toString()
	{		
		String cs = "Var2Edit ";
		if(m_attrManager != null)
			cs += m_attrManager.toString() + " ";
		else
			cs += "(No attributes) ";
		cs += getLoggableValue();
		return cs;
	}
	
//	public Var getChildAt(int n)
//	{
//		int nNChildren = m_varDef.getNbChildren();
//		if(n < nNChildren)
//		{
//			VarDefBuffer varDefChild = m_varDef.getChild(n);
//			if(varDefChild != null)
//			{
//				Var varChild = (Var)m_bufferPos.getProgramManager().getVarFullName(varDefChild);
//				return varChild;
//			}
//		}	
//		return null;
//	}
		
	
	public Edit getAt(Var x)
	{
		int n = x.getInt();
		return getAt(n);
	}
	
	public EditInMapRedefine allocOccursedItem(VarDefBuffer varDefItem)
	{ 
		EditInMapRedefine vItem = new EditInMapRedefine();
		vItem.m_varDef = varDefItem;
		
		int nOffset = m_bufferPos.m_nAbsolutePosition - m_varDef.m_nDefaultAbsolutePosition;
		vItem.m_bufferPos = new VarBufferPos(m_bufferPos, varDefItem.m_nDefaultAbsolutePosition + nOffset);
		vItem.m_varTypeId = varDefItem.getTypeId();
		
		//assertIfFalse(vItem.m_bufferPos.getProgramManager() == m_bufferPos.getProgramManager());
		
		vItem.m_attrManager = vItem.getEditAttributManager();
		return vItem;		
	}
	
	public Edit getEditAt(int x)
	{
		return getAt(x);
	}
	
	public Edit getEditAt(int x, int y)
	{
		return getAt(x, y);
	}
	
	public Edit getEditAt(int x, int y, int z)
	{
		return getAt(x, y, z);
	}


//	public Edit getAt(int x)
//	{
//		
//		VarDefBuffer varDefItem = m_varDef.getAt(x);
//		if(varDefItem == null)
//			return this;
//		Edit editItem = allocOccursedItem(varDefItem);
//		return editItem;
//	}
	
	public Edit getAt(int x)
	{
		TempCache cache = TempCacheLocator.getTLSTempCache();
		if(cache != null)
		{
			int nTypeId = m_varDef.getTypeId();
			//CoupleVar<Edit> coupleEditGetAt = cache.getTempEdit(nTypeId);
			CoupleVar coupleEditGetAt = cache.getTempVar(nTypeId);
			if(coupleEditGetAt != null)
			{
			  	// Adjust varDefGetAt to m_varDef.getAt(x); It is already created in the correct type
				m_varDef.checkIndexes(x-1);
				
				int nAbsStart = m_varDef.getAbsStart(x-1);
				int nDebugIndex = VarDefBase.makeDebugIndex(x);
				m_varDef.adjustSetting(coupleEditGetAt.m_varDefBuffer, nAbsStart, nDebugIndex, 1, m_varDef.m_varDefParent);
				
				if(coupleEditGetAt.m_variable == null)
					coupleEditGetAt.m_variable = allocOccursedItem(coupleEditGetAt.m_varDefBuffer);
								
				adjust(coupleEditGetAt.m_varDefBuffer, (Edit)coupleEditGetAt.m_variable);
				return (Edit)coupleEditGetAt.m_variable;
			}
			VarDefBuffer varDefGetAt = m_varDef.getAt(x);
			if(varDefGetAt == null)
				return this;
			Edit editGetAt = allocOccursedItem(varDefGetAt);
			cache.addTempVar(nTypeId, varDefGetAt, editGetAt);
			
			return editGetAt;
		}

		VarDefBuffer varDefItem = m_varDef.getAt(x);
		if(varDefItem == null)
			return this;
		Edit editItem = allocOccursedItem(varDefItem);
		return editItem;
	}
	
	private void adjust(VarDefBuffer varDefGetAt, Edit editGetAt)
	{
		// Fill varGetAt with custom setting of this 
		editGetAt.m_varDef = varDefGetAt;
		
		int nOffset = m_bufferPos.m_nAbsolutePosition - m_varDef.m_nDefaultAbsolutePosition;
		editGetAt.m_bufferPos.shareDataBufferFrom(m_bufferPos);		
		
		editGetAt.m_bufferPos.m_nAbsolutePosition = varDefGetAt.m_nDefaultAbsolutePosition + nOffset;
		
		editGetAt.m_attrManager = editGetAt.getEditAttributManager();
	}
	
//
//	public Edit getAt(int x, int y)
//	{
//		VarDefBuffer varDefItem = m_varDef.getAt(x, y);
//		if(varDefItem == null)
//			return this;
//		Edit editItem = allocOccursedItem(varDefItem);
//		return editItem;
//	}
	
	
	public Edit getAt(int x, int y)
	{
		TempCache cache = TempCacheLocator.getTLSTempCache();
		if(cache != null)
		{
			int nTypeId = m_varDef.getTypeId();
			CoupleVar coupleEditGetAt = cache.getTempVar(nTypeId);
			if(coupleEditGetAt != null)
			{
				m_varDef.checkIndexes(y-1, x-1);
			  	// Adjust varDefGetAt to m_varDef.getAt(x); It is already created in the correct type
				int nAbsStart = m_varDef.getAbsStart(y-1, x-1);
				int n = 0;
				int nDebugIndex = VarDefBase.makeDebugIndex(x, y);
				m_varDef.adjustSetting(coupleEditGetAt.m_varDefBuffer, nAbsStart, nDebugIndex, 2, m_varDef.m_varDefParent);
				
				if(coupleEditGetAt.m_variable == null)
					coupleEditGetAt.m_variable = allocOccursedItem(coupleEditGetAt.m_varDefBuffer);
					
				
				adjust(coupleEditGetAt.m_varDefBuffer, (Edit)coupleEditGetAt.m_variable);
				//Edit oldEdit = getAtOld(x, y);
				return (Edit)coupleEditGetAt.m_variable;
			}
			VarDefBuffer varDefGetAt = m_varDef.getAt(x, y);
			if(varDefGetAt == null)
				return this;
			Edit editGetAt = allocOccursedItem(varDefGetAt);
			cache.addTempVar(nTypeId, varDefGetAt, editGetAt);
			
			return editGetAt;
		}

		VarDefBuffer varDefItem = m_varDef.getAt(x, y);
		if(varDefItem == null)
			return this;
		Edit editItem = allocOccursedItem(varDefItem);
		return editItem;
	}
	
//	public Edit getAt(int x, int y, int z)
//	{
//		VarDefBuffer varDefItem = m_varDef.getAt(x, y, z);
//		if(varDefItem == null)
//			return this;
//		Edit editItem = allocOccursedItem(varDefItem);
//		return editItem;
//	}
	
	public Edit getAt(int x, int y, int z)
	{
		TempCache cache = TempCacheLocator.getTLSTempCache();
		if(cache != null)
		{
			int nTypeId = m_varDef.getTypeId();
			CoupleVar coupleEditGetAt = cache.getTempVar(nTypeId);
			if(coupleEditGetAt != null)
			{
				m_varDef.checkIndexes(z-1, y-1, x-1);
			  	// Adjust varDefGetAt to m_varDef.getAt(x); It is already created in the correct type
				int nAbsStart = m_varDef.getAbsStart(z-1, y-1, x-1);
				int nDebugIndex = VarDefBase.makeDebugIndex(x, y, z);
				m_varDef.adjustSetting(coupleEditGetAt.m_varDefBuffer, nAbsStart, nDebugIndex, 3, m_varDef.m_varDefParent);
				
				if(coupleEditGetAt.m_variable == null)
					coupleEditGetAt.m_variable = allocOccursedItem(coupleEditGetAt.m_varDefBuffer);
				
				adjust(coupleEditGetAt.m_varDefBuffer, (Edit)coupleEditGetAt.m_variable);
				//Edit oldEdit = getAtOld(x, y);
				return (Edit)coupleEditGetAt.m_variable;
			}
			VarDefBuffer varDefGetAt = m_varDef.getAt(x, y, z);
			if(varDefGetAt == null)
				return this;
			Edit editGetAt = allocOccursedItem(varDefGetAt);
			cache.addTempVar(nTypeId, varDefGetAt, editGetAt);
			
			return editGetAt;
		}

		VarDefBuffer varDefItem = m_varDef.getAt(x, y, z);
		if(varDefItem == null)
			return this;
		Edit editItem = allocOccursedItem(varDefItem);
		return editItem;
	}
	
	public void set(Var varSource)
	{
		varSource.transferTo(this);		
	}	
	
	public void set(Edit varSource)
	{
		varSource.transferTo(this);
	}
	
	public void transferTo(Var varDest)
	{		
		m_varDef.transfer(m_bufferPos, varDest);		
	}

	public void transferTo(Edit varDest)
	{
		m_varDef.transfer(m_bufferPos, varDest);
	}
	
	public boolean isEditInMap()
	{
		return false;
	}
	
	public Element exportXML(Document doc, String csLangId)
	{
		return null;
	}

	EditAttributManager getEditAttributManager()
	{
		BaseProgramManager programManager = TempCacheLocator.getTLSTempCache().getProgramManager(); 
		
		VarDefBuffer varDefEditInMapOrigin = m_varDef.getVarDefEditInMapOrigin();
		//if(varDefEditInMapOrigin != null)
		{
			VarBase varEditInMap = programManager.getVarFullName(varDefEditInMapOrigin);
			EditAttributManager editAttrManager = varEditInMap.getEditAttributManager();
			m_attrManager = editAttrManager;
			return m_attrManager;
		}
//		else
//		{
//			logSevereErrorGetEditAttributManager("getEditAttributManager: SEVERE ERROR");
//		}
//		return null;
	}
	
//	private void logSevereErrorGetEditAttributManager(String csTitle)
//	{	
//		String csSimpleName = TempCacheLocator.getTLSTempCache().getProgramManager().getProgramName();
//		
//		StringBuffer sbText = new StringBuffer(); 
//		sbText.append("In program " + csSimpleName + "\r\n");
//		sbText.append("It will crash\r\n"); 
//		sbText.append("Could not find The variable getVarDefEditInMapOrigin() for current varDef\r\n");
//		sbText.append("Current varDefId="+m_varDef.getId()+" / varDef solvedId="+m_varDef.getIdSolvedDim()+"\r\n");
//		
//		sbText.append("\r\n");
//		
//		SharedProgramInstanceData sharedProgramInstanceData = SharedProgramInstanceDataCatalog.getSharedProgramInstanceData(csSimpleName);
//		if(sharedProgramInstanceData != null)
//		{		
//			sbText.append("\r\nsharedProgramInstanceData:\r\n");
//			String cs = sharedProgramInstanceData.dumpAll();
//			sbText.append(cs);
//		}
//		else
//			sbText.append("\r\nERROR: sharedProgramInstanceData == null !!!\r\n");
//		
//		String csText = sbText.toString(); 
//		BaseProgramLoader.logMail(csTitle, csText);
//	}

	protected byte[] convertUnicodeToEbcdic(char[] tChars)
	{
		return AsciiEbcdicConverter.noConvertUnicodeToEbcdic(tChars);
	}
	
	protected char[] convertEbcdicToUnicode(byte[] tBytes)
	{
		return AsciiEbcdicConverter.noConvertEbcdicToUnicode(tBytes);
	}
	
	public VarType getVarType()
	{
		return VarType.VarEditInMapRedefine;
	}
}
