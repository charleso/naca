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
 * Created on 17 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

import jlib.log.Log;
import jlib.log.LogLevel;
import jlib.misc.ArrayDyn;
import jlib.misc.ArrayFix;
import jlib.misc.ArrayFixDyn;
import jlib.misc.IntegerRef;
import nacaLib.base.CJMapObject;
import nacaLib.base.JmxGeneralStat;
import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.bdb.BtreeSegmentKeyTypeFactory;
import nacaLib.exceptions.OccursOverflowException;
import nacaLib.programPool.SharedProgramInstanceData;
import nacaLib.sqlSupport.CSQLStatus;
import nacaLib.tempCache.TempCache;
import nacaLib.tempCache.TempCacheLocator;


/**
 * @author  U930DI  TODO To change the template for this generated type comment go to  Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class VarDefBase extends CJMapObject //implements Serializable
{
	public VarDefBase(VarDefBase varDefParent, VarLevel varLevel)
	{
		boolean bWSVar = !varLevel.getProgramManager().isLinkageSectionCurrent();
		setWSVar(bWSVar);
		
		m_varDefParent = varDefParent;
		setLevel(varLevel.getLevel());
		m_varDefRedefinOrigin = varLevel.getVarDefRedefineOrigin();
		m_OccursDef = varLevel.getOccursDef();		
		if(varDefParent != null) 
			m_varDefFormRedefineOrigin = varDefParent.m_varDefFormRedefineOrigin;
		
		if(varDefParent != null)
		{
			if(getLevel() != 77)
			{
				setVarDefPreviousSameLevel(varDefParent.getLastVarDefAtLevel(getLevel()));
				varDefParent.addChild(this);
			}
			else
			{
				setVarDefPreviousSameLevel(varDefParent.getLastVarDefAtAnyLevel());
				VarDefBase varDefRoot = getVarDefRoot();
				varDefRoot.addChild(this);				
			}
			
			getArrVarDefOccursOwner(this);
		}
		//JmxGeneralStat.incNbVarDef();
	}
	
	public VarDefBase()
	{
		this.setGetAt(true);
		//JmxGeneralStat.incNbVarDefGetAt();
	}
	
//	public void finalize()
//	{
//		if(this.getIsGetAt())
//			JmxGeneralStat.decNbVarDefGetAt();
//		else
//			JmxGeneralStat.decNbVarDef();
//	}
	
	private VarDefBase getVarDefRoot()
	{
		if(m_varDefParent == null)
			return this;
		else
			return m_varDefParent.getVarDefRoot();
	}
	
	private void getArrVarDefOccursOwner(VarDefBase varDefCurrent)
	{
		if(m_OccursDef != null)
			varDefCurrent.addVarDefOccursOwner(this);
		if(m_varDefParent != null)
			m_varDefParent.getArrVarDefOccursOwner(varDefCurrent);
	}
	
	private void addVarDefOccursOwner(VarDefBase varDefOccursOwner)
	{
		if(m_occursItemSettings == null)
			m_occursItemSettings = new OccursItemSettings(); 
		m_occursItemSettings.m_arrVarDefOccursOwner.add(varDefOccursOwner);
	}
	
	protected VarDefBase getLastVarDefAtLevel(short sLevel)
	{
		if(m_arrChildren != null)
		{
			int nNbChildren = m_arrChildren.size();
			for(int n=nNbChildren-1; n>=0; n--)
			{
				VarDefBase varDefChild = getChild(n);
				if(varDefChild.getLevel() == sLevel || varDefChild.getLevel() == 77)
					return varDefChild;
				if(varDefChild.getLevel() < sLevel)
					return null;
			}
			
			/* 
			// PJD: Pb with the following working:
			Var l1 = declare.level(1).var() ;                                                       
				Var l11 = declare.level(5).picX(1).var() ;                          
				Var l12 = declare.level(5).picX(2).var() ;
			                                                                                                  
			Var l3 = declare.level(3).var() ;
				Var l31 = declare.level(5).pic9(3).var() ;
				Var l32 = declare.level(5).pic9(4).var() ;
				
			l3 must be parented by l1, after l12.
			l3 has no previous var of the same level (3) as it.
			l3 must follow the last var of it's parent l1.
			Thus l3 must follow l32
			*/
			// Pb return getLastVarDefAtAnyLevel();
			return null;	// Previous code returned null, meaning falsly that there wan't previous var for l3				
		}		
		return null;
	}
	
	protected VarDefBase getLastVarDefAtAnyLevel()
	{
		if(m_arrChildren != null)
		{
			int nNbChildren = m_arrChildren.size();
			if(nNbChildren >= 1)
			{
				VarDefBase varDefChild = getChild(nNbChildren-1);
				return varDefChild;
			}
			if(m_varDefParent != null)
				return m_varDefParent.getLastVarDefAtAnyLevel();
		}
		return null;
	}
	
	private void addChild(VarDefBase varDefChild)
	{
		if(m_arrChildren == null)
			m_arrChildren = new ArrayDyn<VarDefBase>();
		m_arrChildren.add(varDefChild);
	}
	
	public void mapOnOriginEdit()
	{
	}	
		
	public void assignEditInMapRedefine()
	{
		if(m_arrChildren != null)
		{			
			int nNbChildren = m_arrChildren.size();
			for(int nChild=0; nChild<nNbChildren; nChild++)
			{
				VarDefBase varDefChild = getChild(nChild);
				varDefChild.mapOnOriginEdit();
				varDefChild.assignEditInMapRedefine();
			}
		}
	}
	
	public int calcSize()
	{
		m_nTotalSize = getSumChildrenSize();
		return m_nTotalSize;
	}
	
	private int getSumChildrenSize()
	{
		int nNbOccurs = getNbOccurs();
		
		int nSingleItemSize = getSingleItemRequiredStorageSize();
		int nSumChildrenSize = 0;
		if(isVarDefForm())
			nSumChildrenSize = getHeaderLength();
		
		if(m_arrChildren != null)
		{			
			int nNbChildren = m_arrChildren.size();
			for(int nChild=0; nChild<nNbChildren; nChild++)
			{
				VarDefBase varDefChild = getChild(nChild);
				int nSize = varDefChild.calcSize();
				if(varDefChild.m_varDefRedefinOrigin == null || varDefChild.isEditInMapRedefine()) 
					nSumChildrenSize += nSize;
				else if(isVarInMapRedefine() && !m_varDefParent.isEditInMapRedefine())
					nSumChildrenSize += nSize;
			}
		}
		
		if(nSingleItemSize == 0)	// We have no size defined for ourself
		{
			if(isEditInMapRedefine() && m_OccursDef != null)
				return nNbOccurs * nSumChildrenSize;	
			if(m_varDefRedefinOrigin != null)	// We are a redefine
			{
				int n = m_varDefRedefinOrigin.getTotalSize();
				return n;	// do not count the number of occurances, because 
			}			
			return nNbOccurs * nSumChildrenSize;
		}
		return nNbOccurs * nSingleItemSize;
	}
	
	public void calcPositionsIntoBuffer(SharedProgramInstanceData sharedProgramInstanceData)
	{
		if(m_arrChildren != null)
		{
			int nNbChildren = m_arrChildren.size();
			for(int nChild=0; nChild<nNbChildren; nChild++)
			{
				VarDefBase varDefChild = getChild(nChild);
				varDefChild.calcAbsolutePosition(sharedProgramInstanceData);
			}
		}
	}
	
	public int calcSizeVarInEdit()
	{
		int n = m_nTotalSize;
		int nNbOccurs = getNbOccurs();
		int nSumChildrenSize = getSumChildrenSizeVarInEdit();
		if(m_varDefFormRedefineOrigin != null)	// Var in a map redefine
		{
			n = nNbOccurs * nSumChildrenSize;
			if(!isEditInMapRedefine())	// do not change the size of the edit in map redefine, only the size of the var groups in an edit of a map redefine
				m_nTotalSize = n;
		}
		return m_nTotalSize;
	}
	
	private int getSumChildrenSizeVarInEdit()
	{
		int nSumSize = 0;
		if(m_arrChildren != null)
		{
			int nNbChildren = m_arrChildren.size();
			for(int nChild=0; nChild<nNbChildren; nChild++)
			{
				VarDefBase varDefChild = getChild(nChild);
				int nSize = varDefChild.calcSizeVarInEdit();
				if(varDefChild.m_varDefRedefinOrigin == null)
					nSumSize += nSize; 
			}
		}
		else
			nSumSize = getSingleItemRequiredStorageSize();
		return nSumSize;		
	}
		
	public void calcOccursOwners()
	{
		if(m_occursItemSettings != null && m_occursItemSettings.m_arrVarDefOccursOwner != null)
		{				
			int nNbDimensions = m_occursItemSettings.m_arrVarDefOccursOwner.size();
			m_occursItemSettings.m_aOccursOwnerLocation = new OccursOwnerLocation[nNbDimensions];
			VarDefBase varDefOccursOwnerCurrent = this;
			for(int n=0; n<nNbDimensions; n++)
			{				
				VarDefBase varDefOccursOwner = m_occursItemSettings.m_arrVarDefOccursOwner.get(n);
				int nDistanceFromOccursOwner = varDefOccursOwnerCurrent.m_nDefaultAbsolutePosition - varDefOccursOwner.m_nDefaultAbsolutePosition;
				int nSignleEntrySize = varDefOccursOwner.getOneEntrySize();
				m_occursItemSettings.m_aOccursOwnerLocation[n] = new OccursOwnerLocation(nDistanceFromOccursOwner, varDefOccursOwner.m_nDefaultAbsolutePosition, nSignleEntrySize);

				varDefOccursOwnerCurrent = varDefOccursOwner;
			}
		}
		
		if(m_arrChildren != null)
		{
			int nNbChildren = m_arrChildren.size();
			for(int nChild=0; nChild<nNbChildren; nChild++)
			{
				VarDefBase varDefChild = getChild(nChild);
				varDefChild.calcOccursOwners();
			}
		}
	}
	
	private int getOneEntrySize()
	{
		int n = getNbOccurs();
		if(n != 0)
			return m_nTotalSize / n;
		return m_nTotalSize;
	}
	
	public boolean isARedefine()
	{
		if(m_varDefRedefinOrigin != null)
			return true;
		return false;
	}

	private void calcAbsolutePosition(SharedProgramInstanceData sharedProgramInstanceData)
	{
		m_nDefaultAbsolutePosition = 0;

		if(m_varDefRedefinOrigin != null)	// We are a redefine
		{
			if(isVarInMapRedefine() && m_varDefRedefinOrigin.isEditInMapRedefine())
			{
				// We are a var that redefines an edit; The var must point to the text part of the edit, not in the attribute header 
				m_nDefaultAbsolutePosition = m_varDefRedefinOrigin.m_nDefaultAbsolutePosition + m_varDefRedefinOrigin.getHeaderLength();
			}
			else	// no header to skip
			{
				m_nDefaultAbsolutePosition = m_varDefRedefinOrigin.m_nDefaultAbsolutePosition;	// Set at the redefine origin position
			}
		}
		else // We are not a redefine
		{
			VarDefBase varDefPreviousSameLevelNonRedefine = getPreviousSameLevelNonRedefine(sharedProgramInstanceData);
			if(varDefPreviousSameLevelNonRedefine != null)
				m_nDefaultAbsolutePosition = varDefPreviousSameLevelNonRedefine.m_nDefaultAbsolutePosition + varDefPreviousSameLevelNonRedefine.getTotalSize();
			else if(m_varDefParent != null)
				m_nDefaultAbsolutePosition = m_varDefParent.m_nDefaultAbsolutePosition + m_varDefParent.getHeaderLength();
		}

		calcPositionsIntoBuffer(sharedProgramInstanceData);
	}	


	private VarDefBase getPreviousSameLevelNonRedefine(SharedProgramInstanceData sharedProgramInstanceData)
	{
		if(getVarDefPreviousSameLevel(sharedProgramInstanceData) != null)
		{
			VarDefBase varDefPrevious = getVarDefPreviousSameLevel(sharedProgramInstanceData);
			if(varDefPrevious.m_varDefRedefinOrigin != null)	// The previous is a redefine
			{
				if(varDefPrevious.isEditInMapRedefine())		// PJD: previous sibling determination error correction
					return varDefPrevious;						// PJD: previous sibling determination error correction
				return varDefPrevious.getPreviousSameLevelNonRedefine(sharedProgramInstanceData);
			}
			return varDefPrevious;	// the previous is not a redefine
		}
		return null;	// No previous at the same level
	}
	
	public void getChildrenEncodingConvertiblePosition(VarDefEncodingConvertibleManager varDefEncodingConvertibleManager)
	{
		if(m_arrChildren != null)
		{
			int nNbChildren = m_arrChildren.size();
			for(int nChild=0; nChild<nNbChildren; nChild++)
			{
				VarDefBase varDefChild = getChild(nChild);
				if(!varDefChild.isARedefine())
					varDefChild.getChildrenEncodingConvertiblePosition(varDefEncodingConvertibleManager);
			}
		}
		else	// No child: We are a final node
		{
			if(!isARedefine() && isEbcdicAsciiConvertible())
			{
				int nNbDim = getNbDim();
				if(nNbDim == 0)
					varDefEncodingConvertibleManager.add(this);
				else if(nNbDim == 1)
				{
					TempCache cache = TempCacheLocator.getTLSTempCache();
					int nNbX = getMaxIndexAtDim(0);
					for(int x=0; x<nNbX; x++)
					{
						VarDefBuffer varDefItem = getCachedGetAt(cache, x+1);
						if(varDefItem != null)
							varDefEncodingConvertibleManager.add(varDefItem);
						cache.resetTempVarIndex(varDefItem.getTypeId());					
					}
				}
				else if(nNbDim == 2)
				{
					TempCache cache = TempCacheLocator.getTLSTempCache();
					int nNbY = getMaxIndexAtDim(1);
					int nNbX  = getMaxIndexAtDim(0);
					for(int y=0; y<nNbY; y++)
					{
						for(int x=0; x<nNbX; x++)
						{
							VarDefBuffer varDefItem = getCachedGetAt(cache, y+1, x+1);
							if(varDefItem != null)
								varDefEncodingConvertibleManager.add(varDefItem);
							cache.resetTempVarIndex(varDefItem.getTypeId());
						}
					}
				}
				else if(nNbDim == 3)
				{
					TempCache cache = TempCacheLocator.getTLSTempCache();
					int nNbZ = getMaxIndexAtDim(2);
					int nNbY = getMaxIndexAtDim(1);
					int nNbX  = getMaxIndexAtDim(0);
					for(int z=0; z<nNbZ; z++)
					{
						for(int y=0; y<nNbY; y++)
						{
							for(int x=0; x<nNbX; x++)
							{
								VarDefBuffer varDefItem = getCachedGetAt(cache, z+1, y+1, x+1);
								if(varDefItem != null)
									varDefEncodingConvertibleManager.add(varDefItem);
								cache.resetTempVarIndex(varDefItem.getTypeId());
							}
						}
					}
				}
			}
		}
	}

	VarDefBuffer getChild(int nChild)
	{
		if(m_arrChildren != null && m_arrChildren.size() > nChild)
			return (VarDefBuffer)m_arrChildren.get(nChild);
		return null;
	}
	
	int getNbChildren()
	{
		if(m_arrChildren != null)
			return m_arrChildren.size();
		return 0;
	}
	
	protected int getNbOccurs()
	{
		if(m_OccursDef != null)
			return m_OccursDef.getNbOccurs();
		return 1;
	}
	
	public String toDump(SharedProgramInstanceData sharedProgramInstanceData)
	{
		String cs = "#" + getLevel() + " ";
		String csFullName = getFullName(sharedProgramInstanceData); 
		if(csFullName != null)
		{
			csFullName = csFullName + getDebugIndex();
			cs += "<£" + csFullName + "£>" +"@"+m_nDefaultAbsolutePosition+"/"+m_nTotalSize;
		}
		else
			cs += "?@" + m_nDefaultAbsolutePosition + "/" + m_nTotalSize;
		
		return cs;
	}
	
	int getTotalSize()
	{
		return m_nTotalSize;
	}
		
	public String getFullName(SharedProgramInstanceData s)
	{
		if(s != null)
		{
			String cs = s.getVarFullName(getId());
			if(cs != null)
				return cs;
		}
		return "";
	}
	
	int getNbDim()
	{
		if(m_occursItemSettings != null)
			return m_occursItemSettings.m_arrVarDefOccursOwner.size();
		return 0;
	}
	
	int getMaxIndexAtDim(int n)
	{
		if(m_occursItemSettings != null)
		{
			VarDefBase occursOwner = m_occursItemSettings.m_arrVarDefOccursOwner.get(n);
			if(occursOwner == null)
			{
				return 0;
			}
			return occursOwner.getNbOccurs();
		}
		return 0;
	}
		
	int getAbsolutePositionOccursOwnerAtDim(int n)
	{		
		if(m_occursItemSettings.m_aOccursOwnerLocation != null)
			if(m_occursItemSettings.m_aOccursOwnerLocation.length > n)
				return m_occursItemSettings.m_aOccursOwnerLocation[n].m_nAbsolutePositionOccursOwner;
		return DEBUGgetDefaultAbsolutePosition();
	}
	
	int getSizeOccursOwnerOf1Entry(int n)
	{
		if(m_occursItemSettings.m_aOccursOwnerLocation != null)
			if(m_occursItemSettings.m_aOccursOwnerLocation.length > n)
				return m_occursItemSettings.m_aOccursOwnerLocation[n].m_nSizeOccursOwnerOf1Entry;
		return 0;
	}
	
	int getDistanceFromOccursOwner(int n)
	{
		if(m_occursItemSettings.m_aOccursOwnerLocation != null)
			if(m_occursItemSettings.m_aOccursOwnerLocation.length > n)
				return m_occursItemSettings.m_aOccursOwnerLocation[n].m_nDistanceFromOccursOwner;
		return 0;
	}
	
	public VarDefBuffer createCopySingleItem(int nAbsStart, int nDebugIndexes, int nNbDim, VarDefBase varDefOccursParent)
	{
		VarDefBuffer varDefBufferCopySingleItem = allocCopy();
		adjustSetting(varDefBufferCopySingleItem, nAbsStart, nDebugIndexes, nNbDim, varDefOccursParent);
		return varDefBufferCopySingleItem;
	}
		
	void adjustSetting(VarDefBuffer varDefBufferCopySingleItem, int nAbsStart, int nDebugIndexes, int nNbDim, VarDefBase varDefOccursParent)
	{
		varDefBufferCopySingleItem.m_varDefParent = null;	//m_varDefParent;
		varDefBufferCopySingleItem.m_arrChildren = m_arrChildren; // PJD; Was = null, but assigned to children array because of ebcdic comparison of occursed items. We need to have access to the children. 
		varDefBufferCopySingleItem.m_nTotalSize = getOneEntrySize();
		varDefBufferCopySingleItem.m_nDefaultAbsolutePosition = nAbsStart;
		varDefBufferCopySingleItem.setId(getId());
		varDefBufferCopySingleItem.setIndex(nDebugIndexes);
		
		varDefBufferCopySingleItem.setFiller(getFiller());
		varDefBufferCopySingleItem.assignForm(m_varDefFormRedefineOrigin);
		varDefBufferCopySingleItem.setTempNbDim(nNbDim);
		
		varDefBufferCopySingleItem.m_varDefParent = varDefOccursParent;
		varDefBufferCopySingleItem.setVarDefMaster(this);
		
		adjustCustomProperty(varDefBufferCopySingleItem);
	}
	
	void adjustSettingForCharGetAt(VarDefBuffer varDefBufferCopySingleItem, int nAbsStart)
	{
		varDefBufferCopySingleItem.m_varDefParent = null;	//m_varDefParent;
		varDefBufferCopySingleItem.m_arrChildren = null; 
		varDefBufferCopySingleItem.m_nTotalSize = 1;
		varDefBufferCopySingleItem.m_nDefaultAbsolutePosition = nAbsStart;
		varDefBufferCopySingleItem.setId(getId());
		varDefBufferCopySingleItem.setIndex(0);
		
		varDefBufferCopySingleItem.setFiller(getFiller());
		varDefBufferCopySingleItem.assignForm(m_varDefFormRedefineOrigin);
		varDefBufferCopySingleItem.setTempNbDim(0);
		
		varDefBufferCopySingleItem.m_varDefParent = m_varDefParent;
		varDefBufferCopySingleItem.setVarDefMaster(this);
		
		adjustCustomPropertyForCharGetAt(varDefBufferCopySingleItem);
	}
	
	
	protected abstract void adjustCustomProperty(VarDefBuffer varDefBufferCopySingleItem);
	protected abstract void adjustCustomPropertyForCharGetAt(VarDefBuffer varDefBufferCopySingleItem);

	void setWSVar(boolean bWSVar)
	{
		if(bWSVar)
			m_n_Filler_TempDim_Level |= 0x00000800;	// 00000000 00000000 00001000 00000000
		else
			m_n_Filler_TempDim_Level &= ~0x00000800;
	}
	
	void setFiller(boolean bFiller)
	{
		if(bFiller)
			m_n_Filler_TempDim_Level |= 0x00000400;		// 00000000 00000000 00000100 00000000
		else
			m_n_Filler_TempDim_Level &= ~0x00000400;
	}
	
	void setTempNbDim(int nTempDim)
	{
		int n = 0x00000300 & (nTempDim * 256);
		m_n_Filler_TempDim_Level &= ~0x00000300;	// 00000000 00000000 00000011 00000000
		m_n_Filler_TempDim_Level |= n;
	}
	
	void setLevel(short sLevel)
	{
		int n = 0x000000FF & sLevel;
		m_n_Filler_TempDim_Level &= ~0x000000FF;	// 00000000 00000000 00000000 11111111
		m_n_Filler_TempDim_Level |= n;
	}
	
	void setGetAt(boolean b)
	{
		if(b)
			m_n_Filler_TempDim_Level |= 0x80000000;		// 10000000 00000000 00000000 00000000
		else
			m_n_Filler_TempDim_Level &= ~0x80000000;
	}
	
	
	public short getLevel()
	{
		int n = m_n_Filler_TempDim_Level & 0xff;		// 00000000 00000000 00000000 11111111
		return (short)n;
	}
	
	public int getTempNbDim()
	{
		int n = m_n_Filler_TempDim_Level & 0x00000300;		// 00000000 00000000 00000011 00000000
		n = n >> 8;
		return (short)n;
	}
	
	public boolean getWSVar()
	{
		int n = m_n_Filler_TempDim_Level & 0x00000800;		// 00000000 00000000 00001000 00000000
		n = n >> 11;
		if(n == 1)
			return true;
		return false;
	}
	
	public boolean getFiller()
	{
		int n = m_n_Filler_TempDim_Level & 0x00000400;		// 00000000 00000000 00000100 00000000
		n = n >> 10;
		if(n == 1)
			return true;
		return false;
	}
	
	public boolean getIsGetAt()
	{
		int n = m_n_Filler_TempDim_Level & 0x80000000;		// 10000000 00000000 00000000 00000000
		if(n != 0)
			return true;
		return false;
	}
	
	public static int makeDebugIndex(int x)
	{
		return (x & 0x40) << 12;		// 6 bits by index for debug display only
	}
	
	public static int makeDebugIndex(int x, int y)
	{
		int n = ((y & 0x40 << 6) + (x & 0x40)) << 12;
		return n;
	}
	
	public static int makeDebugIndex(int x, int y, int z)
	{
		int n = ((z & 0x40 << 12) + (y & 0x40 << 6) + (x & 0x40)) << 12;  
		return n;
	}
	
	public void setIndex(int nDebugIndex)
	{ 
		m_n_Filler_TempDim_Level &= ~0x3FFFF000;	// ~00111111 11111111 11110000 00000000
		m_n_Filler_TempDim_Level |= nDebugIndex;
	}
	
	public String getDebugIndex()
	{ 
		int nNbDim = getTempNbDim();
		if(nNbDim == 0)
			return "";
		int n = m_n_Filler_TempDim_Level & 0x3FFFF000;	// ~00111111 11111111 11110000 00000000
		n = n >> 12;
		int x = n | 0x40;
		n = n >> 6;
		int y = n | 0x40;
		n = n >> 6;
		if(nNbDim == 3)
			return "[" + n + "," + y + "," + x + "]";
		else if(nNbDim == 2)
			return "[" + y + "," + x + "]";
		else
			return "[" + x + "]";	
	}

	VarDefBuffer getCachedGetAt(TempCache cache, int x)
	{
		assertIfFalse(x>0) ;
		
		if(cache != null)
		{
			int nTypeId = getTypeId();
			CoupleVar coupleVarGetAt = cache.getTempVar(nTypeId);
			if(coupleVarGetAt != null)
			{
			  	// Adjust varDefGetAt to m_varDef.getAt(x); It is already created in the correct type
				int nAbsStart = getAbsStart(x-1);
				int nDebugIndex = VarDefBase.makeDebugIndex(x);
				adjustSetting(coupleVarGetAt.m_varDefBuffer, nAbsStart, nDebugIndex, 1, m_varDefParent);
				return coupleVarGetAt.m_varDefBuffer;
			}
			VarDefBuffer varDefGetAt = createVarDefAt(x-1, m_varDefParent);
			cache.addTempVar(nTypeId, varDefGetAt, null);
			return varDefGetAt;
		}
		VarDefBuffer varDefItem = createVarDefAt(x-1, m_varDefParent);
		return varDefItem;
	}
	
	CoupleVar getCoupleCachedGetAt(TempCache cache, int x)
	{
		assertIfFalse(x>0) ;
		
		if(cache != null)
		{
			int nTypeId = getTypeId();
			CoupleVar coupleVarGetAt = cache.getTempVar(nTypeId);
			if(coupleVarGetAt != null)
			{
			  	// Adjust varDefGetAt to m_varDef.getAt(x); It is already created in the correct type
				int nAbsStart = getAbsStart(x-1);
				int nDebugIndex = VarDefBase.makeDebugIndex(x);
				adjustSetting(coupleVarGetAt.m_varDefBuffer, nAbsStart, nDebugIndex, 1, m_varDefParent);
				return coupleVarGetAt;
			}
			VarDefBuffer varDefGetAt = createVarDefAt(x-1, m_varDefParent);
			coupleVarGetAt = cache.addTempVar(nTypeId, varDefGetAt, null);
			return coupleVarGetAt;
		}
		VarDefBuffer varDefItem = createVarDefAt(x-1, m_varDefParent);
		CoupleVar coupleVarGetAt = new CoupleVar(varDefItem, null); 
		return coupleVarGetAt;
	}


	VarDefBuffer getAt(int x)
	{
		VarDefBuffer varDefItem = createVarDefAt(x-1, m_varDefParent);
		return varDefItem;
	}
	
	private void checkIndex(VarDefBase varDef, int nIndexValue, String csIndexName)
	{
		if(varDef.m_OccursDef != null && (nIndexValue < 0 || nIndexValue >= varDef.m_OccursDef.getNbOccurs()))
		{
			String csMessage = "OccursOverflow index " + csIndexName + " detected accessing variable " + varDef.getNameForDebug() + " at index " + nIndexValue + "; Max="+varDef.m_OccursDef.getNbOccurs();
			Log.logCritical(csMessage);
			String csCallStack = Log.logCallStack("Complete call stack:", LogLevel.Critical);
			BaseProgramManager programManager = TempCacheLocator.getTLSTempCache().getProgramManager();
			
			Throwable throwable = new Throwable();
			programManager.logMail("OccursOverflow index", csMessage, throwable);
			
			if(BaseResourceManager.getCanThrowOccursOverflowException())
			{
				OccursOverflowException e = new OccursOverflowException(this, nIndexValue, varDef.m_OccursDef.getNbOccurs(), csIndexName);
				throw e;
			}
		}
	}
	
	int getAbsStart(int nXBase0)
	{
		int nAbsStart = 
			getAbsolutePositionOccursOwnerAtDim(0) + 
			(nXBase0 * getSizeOccursOwnerOf1Entry(0)) + 
			getDistanceFromOccursOwner(0);				
		return nAbsStart;
	}
	
	private VarDefBuffer createVarDefAt(int nXBase0, VarDefBase varDefOccursParent)
	{
		checkIndexes(nXBase0);
		
		int nAbsStart = getAbsStart(nXBase0);

		VarDefBuffer varDefBuffer = createCopySingleItem(nAbsStart, nXBase0+1, 1, varDefOccursParent);
		
		return varDefBuffer;
	}
		
	void checkIndexes(int nXBase0)
	{
		if(m_occursItemSettings != null)
		{
			VarDefBase varDefX = m_occursItemSettings.m_arrVarDefOccursOwner.get(0);
			checkIndex(varDefX, nXBase0, "X");
		}

	}
	
	void checkIndexes(int nXBase0, int nYBase0)
	{
		if(m_occursItemSettings != null)
		{
			VarDefBase varDefX = m_occursItemSettings.m_arrVarDefOccursOwner.get(0);
			VarDefBase varDefY = m_occursItemSettings.m_arrVarDefOccursOwner.get(1);
			checkIndex(varDefX, nXBase0, "X");
			checkIndex(varDefY, nYBase0, "Y");
		}
	}

	void checkIndexes(int nXBase0, int nYBase0, int nZBase0)
	{
		if(m_occursItemSettings != null)
		{
			VarDefBase varDefX = m_occursItemSettings.m_arrVarDefOccursOwner.get(0);
			VarDefBase varDefY = m_occursItemSettings.m_arrVarDefOccursOwner.get(1);
			VarDefBase varDefZ = m_occursItemSettings.m_arrVarDefOccursOwner.get(2);
			checkIndex(varDefX, nXBase0, "X");
			checkIndex(varDefY, nYBase0, "Y");
			checkIndex(varDefZ, nZBase0, "Z");
		}
	}

	int getAbsStart(int nXBase0, int nYBase0)
	{
		int nAbsStart = getAbsolutePositionOccursOwnerAtDim(1) + 
			(nYBase0 * getSizeOccursOwnerOf1Entry(1)) + 
			getDistanceFromOccursOwner(1) + 
			(nXBase0 * getSizeOccursOwnerOf1Entry(0)) + 
			getDistanceFromOccursOwner(0);
		return nAbsStart;
	}
	
	int getAbsStart(int nXBase0, int nYBase0, int nZBase0)
	{
		int n = getAbsolutePositionOccursOwnerAtDim(2) + 
			(nZBase0 * getSizeOccursOwnerOf1Entry(2)) + 
			getDistanceFromOccursOwner(2) +
			(nYBase0 * getSizeOccursOwnerOf1Entry(1)) + 
			getDistanceFromOccursOwner(1) + 
			(nXBase0 * getSizeOccursOwnerOf1Entry(0)) + 
			getDistanceFromOccursOwner(0);	
		return n;
	}
	
	VarDefBuffer getCachedGetAt(TempCache cache, int y, int x)
	{
		assertIfFalse(x>0) ;
		assertIfFalse(y>0) ;
		
		if(cache != null)
		{
			int nTypeId = getTypeId();
			CoupleVar coupleVarGetAt = cache.getTempVar(nTypeId);
			if(coupleVarGetAt != null)
			{
			  	// Adjust varDefGetAt to m_varDef.getAt(x); It is already created in the correct type
				int nAbsStart = getAbsStart(x-1, y-1);
				String cs = String.valueOf(y) + "," + String.valueOf(x);
				int nDebugIndex = VarDefBase.makeDebugIndex(y, x);
				adjustSetting(coupleVarGetAt.m_varDefBuffer, nAbsStart, nDebugIndex, 2, m_varDefParent);
				return coupleVarGetAt.m_varDefBuffer;
			}
			VarDefBuffer varDefGetAt = createVarDefAt(y-1, x-1, m_varDefParent);
			cache.addTempVar(nTypeId, varDefGetAt, null);
			return varDefGetAt;
		}

		VarDefBuffer varDefItem = createVarDefAt(y-1, x-1, m_varDefParent);
		return varDefItem;
	}
	
	private VarDefBuffer createVarDefAt(int nYBase0, int nXBase0, VarDefBase varDefOccursParent)
	{
		checkIndexes(nXBase0, nYBase0);
		
		int n = getAbsStart(nXBase0, nYBase0);
		
		int nDebugIndex = makeDebugIndex(nYBase0+1, nXBase0+1);
		VarDefBuffer varDefBuffer = createCopySingleItem(n, nDebugIndex, 2, varDefOccursParent);

		return varDefBuffer;
	}
	
	private VarDefBuffer createVarDefAt(int nZBase0, int nYBase0, int nXBase0, VarDefBase varDefOccursParent)	
	{
		checkIndexes(nXBase0, nYBase0, nZBase0);
		
		int nNbDim = getNbDim();
		if(nNbDim == 3)
		{
			int n = getAbsStart(nXBase0, nYBase0, nZBase0);
			int nDebugIndex = makeDebugIndex(nZBase0+1, nYBase0+1, nXBase0+1);
			VarDefBuffer varDefBuffer = createCopySingleItem(n, nDebugIndex, 3, varDefOccursParent);
			return varDefBuffer;
		}
		return null;

	}	

	VarDefBuffer getAt(int y, int x)
	{
		VarDefBuffer varDefItem = createVarDefAt(y-1, x-1, m_varDefParent);
		return varDefItem;
	}
	
	VarDefBuffer getCachedGetAt(TempCache cache, int z, int y, int x)
	{
		assertIfFalse(x>0) ;
		assertIfFalse(y>0) ;
		assertIfFalse(z>0) ;
		
		if(cache != null)
		{
			int nTypeId = getTypeId();
			CoupleVar coupleVarGetAt = cache.getTempVar(nTypeId);
			if(coupleVarGetAt != null)
			{
			  	// Adjust varDefGetAt to m_varDef.getAt(x); It is already created in the correct type
				int nAbsStart = getAbsStart(x-1, y-1, z-1);
				int nDebugIndex = VarDefBase.makeDebugIndex(z, y, x);
				adjustSetting(coupleVarGetAt.m_varDefBuffer, nAbsStart, nDebugIndex, 3, m_varDefParent);
				return coupleVarGetAt.m_varDefBuffer;
			}
			VarDefBuffer varDefGetAt = createVarDefAt(z-1, y-1, x-1, m_varDefParent);
			cache.addTempVar(nTypeId, varDefGetAt, null);
			return varDefGetAt;
		}
		VarDefBuffer varDefItem = createVarDefAt(z-1, y-1, x-1, m_varDefParent);
		return varDefItem;
	}

	VarDefBuffer getAt(int z, int y, int x)
	{
		VarDefBuffer varDefItem = createVarDefAt(z-1, y-1, x-1, m_varDefParent);
		return varDefItem;
	}
	
	protected VarDefMapRedefine getMapRedefine()
	{
		if(isAVarDefMapRedefine())
			return (VarDefMapRedefine)this;
		if(m_varDefParent != null)
			return m_varDefParent.getMapRedefine();
		return null;
	}
	
	int getNbEditInMapRedefine(VarDefBase varExcluded, int nDepth)
	{
		nDepth++;
		int nNbEdit = 0;
		if(m_arrChildren == null)
		{
			if(varExcluded != this)
			{
				int nNbOccurs = getNbOccurs();
				return nNbOccurs;
			}
			return 0;
		}
		else
		{
			for(int nChild=0; nChild<m_arrChildren.size(); nChild++)
			{
				VarDefBuffer varDefChild = getChild(nChild);
				if(varDefChild != null)
				{
					if(varExcluded != varDefChild)
					{
						int nNbOccurs = varDefChild.getNbOccurs();
						int n = varDefChild.getNbEditInMapRedefine(varExcluded, nDepth);
						if(nDepth >= 2)
							nNbEdit += n;	// * nNbOccurs;
						else
							nNbEdit += n * nNbOccurs;							
					}
				}
			}
		}
		return nNbEdit;
	}
	
	int getNbItems()
	{
		int nNbOccurs = getNbOccurs();
		if(m_arrChildren != null)
		{
			int n = 0;
			for(int nChild=0; nChild<m_arrChildren.size(); nChild++)
			{
				VarDefBase varDefChild = getChild(nChild);
				if(varDefChild != null && varDefChild.isEditInMapRedefine())
					n += varDefChild.getNbItems();
			}
			if(n == 0)
				n = 1;
			return nNbOccurs * n;			
		}
		else
			return nNbOccurs;
	}
	
	public VarDefBase getNamedChild(SharedProgramInstanceData sharedProgramInstanceData, String csName)
	{
		int nNbChildren = getNbChildren();
		for(int nIndex=0; nIndex<nNbChildren; nIndex++)
		{
			VarDefBase varDefChild = getChild(nIndex);
			String csChildName = varDefChild.getFullName(sharedProgramInstanceData); 
			if(csChildName.equalsIgnoreCase(csName))
				return varDefChild;
			csChildName = NameManager.getUnprefixedName(csChildName);
			if(csChildName.equalsIgnoreCase(csName))
				return varDefChild;
			varDefChild = varDefChild.getNamedChild(sharedProgramInstanceData, csName);
			if (varDefChild != null)
				return varDefChild;
		}
		return null;
	}
	
	public VarDefBase getUnprefixNamedChild(SharedProgramInstanceData sharedProgramInstanceData, String csName, IntegerRef rnChildIndex)
	{
		String csUpperName = csName.toUpperCase();
		
		int nNbChildren = getNbChildren();
		for(int nIndex=0; nIndex<nNbChildren; nIndex++)
		{
			VarDefBase varDefChild = getChild(nIndex);
			String csChildName = varDefChild.getUnprefixedName(sharedProgramInstanceData).toUpperCase(); 
			if(csChildName.equals(csUpperName))
			{
				if(rnChildIndex != null)
					rnChildIndex.set(nIndex);
				return varDefChild;
			}
			varDefChild = varDefChild.getUnprefixNamedChild(sharedProgramInstanceData, csName, rnChildIndex);
			if (varDefChild != null)
				return varDefChild;
		}
		return null;
	}
	
	public VarDefBase getUnDollarUnprefixNamedChild(SharedProgramInstanceData sharedProgramInstanceData, String csName, IntegerRef rnChildIndex)
	{
		String csUpperName = csName.toUpperCase();
		
		int nNbChildren = getNbChildren();
		for(int nIndex=0; nIndex<nNbChildren; nIndex++)
		{
			VarDefBase varDefChild = getChild(nIndex);
			String csChildName = varDefChild.getUnprefixedName(sharedProgramInstanceData).toUpperCase(); 
			if(csChildName.equals(csUpperName))
			{
				if(rnChildIndex != null)
					rnChildIndex.set(nIndex);
				return varDefChild;
			}
			else 
			{
				int nDollarPos = csChildName.indexOf('$');
				if(nDollarPos >= 0)
				{
					String csUnDollarChildName = csChildName.substring(0, nDollarPos);
					if(csUnDollarChildName.equals(csUpperName))
					{
						if(rnChildIndex != null)
							rnChildIndex.set(nIndex);
						return varDefChild;
					}
				}
			}
			varDefChild = varDefChild.getUnprefixNamedChild(sharedProgramInstanceData, csName, rnChildIndex);
			if (varDefChild != null)
				return varDefChild;
		}
		return null;
	}
	
	public String getUnprefixedName(SharedProgramInstanceData sharedProgramInstanceData)
	{
		String name = getFullName(sharedProgramInstanceData) ;
		int nPosSep = name.indexOf('.');
		if(nPosSep != -1)
			return name.substring(nPosSep+1);
		return name;
	}
	
	public void addRedefinition(VarDefBase varDefRedefinition)
	{
		if(m_arrRedefinition == null)
			m_arrRedefinition = new ArrayDyn<VarDefBase>();
		m_arrRedefinition.add(varDefRedefinition);
	}
	
	public int getNbRedefinition()
	{
		if(m_arrRedefinition == null)
			return 0;
		return m_arrRedefinition.size();
	}
	
	public VarDefBase getRedefinitionAt(int nIndex)
	{
		if(m_arrRedefinition == null)
			return null;
		return m_arrRedefinition.get(nIndex);
	}


			
	public abstract int getSingleItemRequiredStorageSize();
	abstract VarDefBuffer allocCopy();	// Used only for getAt or substring
	
	protected abstract boolean isAVarDefMapRedefine();				
	protected abstract boolean isEditInMapRedefine();
	protected abstract boolean isVarInMapRedefine();
	protected abstract boolean isVarDefForm();
	protected abstract boolean isEditInMapOrigin();
	
	public abstract int getBodyLength(); 
	protected abstract int getHeaderLength();
	protected abstract boolean isEbcdicAsciiConvertible();
	
	abstract void assignForm(VarDefForm varDefForm);
	
	int getBodyAbsolutePosition(VarBufferPos buffer)
	{
		return buffer.m_nAbsolutePosition + getHeaderLength();
	}
	
	public int getLength()
	{
		return m_nTotalSize; 
	}
	
	private int getNbEdit()
	{
		int  nNbEdit = 0;
		if(isEditInMapRedefine() && m_OccursDef == null)
			nNbEdit++;
		
		int nNbChildren = getNbChildren();
		for(int n=0; n<nNbChildren; n++)
		{
			VarDefBase varDefChild = getChild(n);
			int nNbEditUnderChild = varDefChild.getNbEdit();
			nNbEdit += nNbEditUnderChild;
		}
		
		if(isEditInMapRedefine() && m_OccursDef != null)
		{
			int nNbOccurs = m_OccursDef.getNbOccurs();
			nNbEdit = nNbEdit * nNbOccurs;
		}
		
		return nNbEdit;
	}

	int getNbEditUntil(VarDefBase varChildToFind, FoundFlag foundFlag)
	{
		int nNbEdit = 0;
		if(isEditInMapRedefine() && m_OccursDef == null)
			nNbEdit++;
		
		int nNbChildren = getNbChildren();
		for(int n=0; n<nNbChildren && !foundFlag.isFound(); n++)
		{
			VarDefBase varDefChild = getChild(n);
			if(varChildToFind == varDefChild)
			{
				foundFlag.setFound();
				return nNbEdit; 
			}
			if(!foundFlag.isFound())
			{
				int nNbEditUnderChild = varDefChild.getNbEditUntil(varChildToFind, foundFlag);
				if(varDefChild.isVarInMapRedefine() && varDefChild.m_varDefRedefinOrigin != null) // we are a var redefine, and we know what we redefines 
				{						
					if(foundFlag.isFound())	// We found the edit serched as a child of the var redefine
					{
						int nNbEditAlredayCounted = varDefChild.m_varDefRedefinOrigin.getNbEdit();	// Number of items alreday counted in the var redefine origin: it must not be taken into account
						nNbEdit = nNbEdit + nNbEditUnderChild - nNbEditAlredayCounted;
						return nNbEdit; 
					}							
				}
				else
					nNbEdit += nNbEditUnderChild;
			}
		}
		
		if(!foundFlag.isFound())
		{
			if(isEditInMapRedefine() && m_OccursDef != null)
			{
				int nNbOccurs = m_OccursDef.getNbOccurs();
				nNbEdit = nNbEdit * nNbOccurs;
			}
		}

		return nNbEdit;
	}
	
	public int DEBUGgetDefaultAbsolutePosition()
	{
		return m_nDefaultAbsolutePosition;
	}
	
	VarDefBase getVarDefRedefinOrigin()
	{
		return m_varDefRedefinOrigin;
	}
	
	VarDefBase getTopVarDefRedefinOrigin()
	{
		if(m_varDefRedefinOrigin != null)
			return m_varDefRedefinOrigin.getTopVarDefRedefinOrigin();
		return m_varDefRedefinOrigin;
	}
	
	public String getUnprefixedUnindexedName(SharedProgramInstanceData sharedProgramInstanceData)
	{
		String csFullName = getFullName(sharedProgramInstanceData);
		return NameManager.getUnprefixedUnindexedName(csFullName);
	}
	
	public void compress()
	{
		if(m_arrChildren != null)
		{	
			// Swap the type inside m_arrRedefinition
			if(m_arrChildren.isDyn())
			{
				int nSize = m_arrChildren.size();
				VarDefBase arr[] = new VarDefBase [nSize];
				m_arrChildren.transferInto(arr);
				
				ArrayFix<VarDefBase> arrChildrenFix = new ArrayFix<VarDefBase>(arr);
				m_arrChildren = arrChildrenFix;	// replace by a fix one (uning less memory)
			}
		}
		if(m_occursItemSettings != null)
			m_occursItemSettings.compress();

		if(m_arrRedefinition != null)
		{	
			// Swap the type inside m_arrRedefinition 
			if(m_arrRedefinition.isDyn())
			{
				int nSize = m_arrRedefinition.size();
				VarDefBase arr[] = new VarDefBase [nSize];
				m_arrRedefinition.transferInto(arr);
				
				ArrayFix<VarDefBase> arrRedefinitionFix = new ArrayFix<VarDefBase>(arr);
				m_arrRedefinition = arrRedefinitionFix;	// replace by a fix one (uning less memory)
			}
		}
	}

	private void setVarDefPreviousSameLevel(VarDefBase varDefPreviousSameLevel)
	{
		int nVarDefPreviousSameLevelId = NULL_ID;
		if(varDefPreviousSameLevel != null)
			nVarDefPreviousSameLevelId = varDefPreviousSameLevel.getId();
		m_n_PreviousSameLevel_Id = setHigh(m_n_PreviousSameLevel_Id, nVarDefPreviousSameLevelId);
	}
	
	private VarDefBase getVarDefPreviousSameLevel(SharedProgramInstanceData sharedProgramInstanceData)
	{
		VarDefBase varDefBase = getVarDefBaseAtHigh(sharedProgramInstanceData, m_n_PreviousSameLevel_Id);
		return varDefBase;
	}
	
//	private void writeObject(ObjectOutputStream out)
//	{
//	}
//
//	private void readObject(ObjectInputStream in)
//	{
//	}
		
//	public void serializeDetails(ObjectOutputStream out, Hashtable<VarDefBase, Integer> hashVarDefById, int nId) throws IOException
//	{
//		out.writeInt(1);	// Version
//		serializeVarDefId(out, hashVarDefById, this);	// Serialize our id
//		out.writeInt(m_n_Filler_TempDim_Level);
//		out.writeObject(m_OccursDef);
//		out.writeObject(m_csFullname);
//		
//		out.writeInt(m_nTotalSize);
//		out.writeInt(m_nDefaultAbsolutePosition);
//		
//		serializeVarDefId(out, hashVarDefById, m_varDefParent);	// Serialized our parent's id
//		serializeVarDefId(out, hashVarDefById, m_varDefPreviousSameLevel);
//		serializeVarDefId(out, hashVarDefById, m_arrVarDefMaster);
//		serializeVarDefId(out, hashVarDefById, m_varDefRedefinOrigin);
//		
//		serializeArrayVarDef(out, hashVarDefById, m_arrChildren);
//		serializeArrayVarDef(out, hashVarDefById, m_arrVarDefOccursOwner);
//		serializeArrayVarDef(out, hashVarDefById, m_arrRedefinition);
//		
//		out.writeObject(m_aOccursOwnerLocation);
//		
//		// TO BE DONE: m_varDefFormRedefineOrigin
//	}
	
//	private void serializeArrayVarDef(ObjectOutputStream out, Hashtable<VarDefBase, Integer> hashVarDefById, ArrayList<VarDefBase> arr) throws IOException
//	{
//		int nNbChildren = 0; 
//		if(arr != null)
//			nNbChildren = arr.size();
//		out.writeInt(nNbChildren);
//		for(int n=0; n<nNbChildren; n++)
//		{
//			VarDefBase varDefBaseChild = arr.get(n);
//			serializeVarDefId(out, hashVarDefById, varDefBaseChild);	
//		}
//	}

//	public void deserializeDetails(ObjectInputStream in, ArrayList<VarDefBuffer> arrVarDef, int nId) throws IOException, ClassNotFoundException
//	{
//		int nVersion = in.readInt();	// Version
//		if(nVersion == 1)
//		{
//			VarDefBase me = findVarDefFromId(in, arrVarDef);	// deserialize our id
//			assertIfFalse(me == this);
//			m_n_Filler_TempDim_Level = in.readInt();
//			m_OccursDef = (OccursDef)in.readObject();
//			m_csFullname = (String)in.readObject();	
//			
//			m_nTotalSize = in.readInt();
//			m_nDefaultAbsolutePosition = in.readInt();
//			
//			m_varDefParent = findVarDefFromId(in, arrVarDef);
//			m_varDefPreviousSameLevel = findVarDefFromId(in, arrVarDef);
//			m_arrVarDefMaster = findVarDefFromId(in, arrVarDef);
//			m_varDefRedefinOrigin = findVarDefFromId(in, arrVarDef);
//			
//			m_arrChildren = findArrVarDefFromId(in, arrVarDef);
//			m_arrVarDefOccursOwner = findArrVarDefFromId(in, arrVarDef);
//			m_arrRedefinition = findArrVarDefFromId(in, arrVarDef);
//			
//			m_aOccursOwnerLocation = (OccursOwnerLocation[])in.readObject();
//			
//			// TO BE DONE: m_varDefFormRedefineOrigin
//		}
//	}
		
//	 ArrayList<VarDefBase> findArrVarDefFromId(ObjectInputStream in, ArrayList<VarDefBuffer> arrVarDef) throws IOException
//	 {
//		int nNbChildren = in.readInt();
//		if(nNbChildren != 0)
//		{
//			ArrayList<VarDefBase> arr = new ArrayList<VarDefBase>();
//			for(int n=0; n<nNbChildren; n++)
//			{
//				VarDefBase varDefChild = findVarDefFromId(in, arrVarDef);	
//				arr.add(varDefChild);
//			}
//			return arr;
//		}
//		return null;
//	}

		
//	private void serializeVarDefId(ObjectOutputStream out, Hashtable<VarDefBase, Integer> hashVarDefById, VarDefBase varDef) throws IOException
//	{
//		if(varDef != null)
//		{
//			Integer iId = hashVarDefById.get(varDef);
//			int nId = iId.intValue(); 
//			out.writeInt(nId);	// Serialize our position in the hash
//		}
//		else
//			out.writeInt(-1);	// Id for an inexisting object
//	}
	
//	VarDefBase findVarDefFromId(ObjectInputStream in, ArrayList<VarDefBuffer> arrVarDef) throws IOException
//	{
//		if(arrVarDef != null)
//		{
//			int nId = in.readInt();
//			if(nId >= 0 && nId < arrVarDef.size())
//				return arrVarDef.get(nId);
//		}
//		return null;
//	}
	
	public VarDefBase getParentAtLevel01()
	{
		VarDefBase varDefLevel01 = this;
		while(varDefLevel01 != null)
		{
			int nLevel = varDefLevel01.getLevel();
			if(nLevel == 1)
				return varDefLevel01;
			varDefLevel01 = varDefLevel01.m_varDefParent;
		}
		return null;
	}
	
	public void prepareAutoRemoval()
	{ 
		//m_aOccursOwnerLocation = null;
		m_arrChildren = null;
		m_arrRedefinition = null;
		//m_arrVarDefOccursOwner = null;
		//m_varDefMaster = null;
		
		if(m_OccursDef != null)
		{
			m_OccursDef.prepareAutoRemoval();
			m_OccursDef = null;
		}
		
		if(m_varDefFormRedefineOrigin != null)
		{
			m_varDefFormRedefineOrigin.prepareAutoRemoval();
			m_varDefFormRedefineOrigin = null;
		}
		
		m_varDefParent = null;
		m_varDefRedefinOrigin = null;
	}
	
	private int getLow(int n)
	{
		int nLow = n & 0x0000ffff;
		return nLow;
	}
	
	private int getHigh(int n)
	{
		int nHigh = (n >> 16) & 0x0000ffff;
		return nHigh;  
	}
	
	private int setHighLow(int nHigh, int nLow)
	{
		int nNvalueH = ((nHigh & 0x0000ffff) << 16);
		nNvalueH += (nLow & 0x0000ffff);
		return nNvalueH; 
	}

	private int setHigh(int nOldValue, int nHigh)
	{
		int nNewValue = ((nHigh & 0x0000ffff) << 16) + (nOldValue & 0x0000ffff);
		return nNewValue;
	}

	private int setLow(int nOldValue, int nLow)
	{
		int nNewValue = (((nOldValue >> 16) & 0x0000ffff) << 16) + (nLow & 0x0000ffff);
		return nNewValue;
	}
	

	public void setId(int nId)
	{
		m_n_PreviousSameLevel_Id = setLow(m_n_PreviousSameLevel_Id, nId);
	}
	
	public int getId()
	{
		return getLow(m_n_PreviousSameLevel_Id);
	}
	
	public int getIdSolvedDim()	// Unique id combined with resolved var dimension
	{
		return (getId() * 4) + getTempNbDim();
	}

	VarDefBase getVarDefBaseAtHigh(SharedProgramInstanceData sharedProgramInstanceData, int nValue)
	{
		int nId = getHigh(nValue);
		return sharedProgramInstanceData.getVarDef(nId);
	}
	
	VarDefBase getVarDefMaster(SharedProgramInstanceData sharedProgramInstanceData)
	{
		return getVarDefBaseAtHigh(sharedProgramInstanceData, m_n_varDefMaster_Free);
	}
	
	void setVarDefMaster(VarDefBase varDefBase)
	{
		int nId = varDefBase.getId();
		m_n_varDefMaster_Free = setHigh(m_n_varDefMaster_Free, nId);		
	}
	
	public String getNameForDebug()
	{
		BaseProgramManager programManager = TempCacheLocator.getTLSTempCache().getProgramManager();
		if(programManager != null)
		{
			SharedProgramInstanceData s = programManager.getSharedProgramInstanceData();
			if(s != null)
			{
				return getFullName(s);				
			}
			return "[Unknown SharedProgramInstanceData]";
		}
		return "[Unknown BaseProgramManager]";
	}
	
	public String toString()
	{
		BaseProgramManager programManager = TempCacheLocator.getTLSTempCache().getProgramManager();
		if(programManager != null)
		{
			SharedProgramInstanceData s = programManager.getSharedProgramInstanceData();
			if(s != null)
			{
				return toDump(s);				
			}
			return "Unknown SharedProgramInstanceData";
		}
		return "Unknown BaseProgramManager";
	}

	abstract void initializeItemAndChildren(VarBufferPos varBufferPos, InitializeManager initializeManager, int nOffset, InitializeCache initializeCache);
	public abstract int getTypeId();
	public abstract BtreeSegmentKeyTypeFactory getSegmentKeyTypeFactory();
	
	public int getTrailingLengthToNotconvert()
	{
		return 0;
	}

	protected ArrayFixDyn<VarDefBase> m_arrChildren = null;	// Array of VarDefBase
	private ArrayFixDyn<VarDefBase> m_arrRedefinition = null;	// Array of VarDefBase
	
	protected OccursDefBase m_OccursDef = null;
	
	protected OccursItemSettings m_occursItemSettings = null;
	
	private int m_n_Filler_TempDim_Level = 0;  
	// 00000000 00000000 00000000 11111111: Level
	// 00000000 00000000 00000011 00000000: Dim
	// 00000000 00000000 00000100 00000000: Filler
	// 00000000 00000000 00001000 00000000: Working storage var
	// 00000000 00000011 11110000 00000000: X Index debug purpose only
	// 00000000 11111100 00000000 00000000: Y Index debug purpose only
	// 00111111 00000000 00000000 00000000: Z Index debug purpose only
	// 10000000 00000000 00001000 00000000: GetAt access type
	
	protected int m_nTotalSize = 0;					// Total size of the item, including the occurs 
	protected int m_nDefaultAbsolutePosition = 0;	// Absolute start position into the buffer
	
	
	// Grouped by 16 bits id
	private int m_n_PreviousSameLevel_Id = 0;	// high short:m_varDefPreviousSameLevel id; low short: Id of the variable's (an index in SharedProgramInstanceData) m_arrVarName array
	// Grouping:
	//private VarDefBase m_varDefPreviousSameLevel = null;	// Previous VarDef at the same level
	//private int m_nId;
	
	
	private int m_n_varDefMaster_Free = 0xffff0000;
	// Grouping:
	//protected VarDefBase m_varDefMaster = null;
	
	
	protected VarDefBase m_varDefRedefinOrigin = null;
	protected VarDefForm m_varDefFormRedefineOrigin = null;
	protected VarDefBase m_varDefParent = null;
	
	public static final int NULL_ID = 0xffff;
}


