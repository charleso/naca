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
 * Created on 23 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class VarDefItemManagerStored extends VarDefItemManager
{
	VarDefItemManagerStored(int nNbItems)
	{
		m_aVarDefItems = new VarDefBuffer[nNbItems];
	}
	
	VarDefBuffer getAtAllDim(int nStorageIndex)
	{
		if(m_aVarDefItems != null)
		{
			VarDefBuffer varDefItem = m_aVarDefItems[nStorageIndex];
			return varDefItem;
		}
		return null;
	}
	
	VarDefBuffer getAt(VarDefBase varDef, int x)
	{
		int n = m_aVarDefItems.length;
		int index = x % n;
		VarDefBuffer varDefItem = m_aVarDefItems[index];
		return varDefItem;
	}
	
	VarDefBuffer getAt(VarDefBase varDefMaster, int y, int x)
	{
		int nStorageIndex = getStorageIndex(varDefMaster, y, x);
		VarDefBuffer varDefItem = m_aVarDefItems[nStorageIndex];
		return varDefItem;
	}
	
	VarDefBuffer getAt(VarDefBase varDefMaster, int z, int y, int x)	
	{
		int nStorageIndex = getStorageIndex(varDefMaster, z, y, x);
		
		VarDefBuffer varDefItem = m_aVarDefItems[nStorageIndex];
		return varDefItem;
	}
	
	private int getStorageIndex(VarDefBase varDefMaster, int y, int x)
	{
		int nNbIndexX = varDefMaster.getMaxIndexAtDim(0);
		return getStorageIndex(nNbIndexX, y, x);
//		int nStorageIndex = (y * nNbIndexX) + x;
//		return nStorageIndex;		
	}
	
	static int getStorageIndex(int nNbIndexX, int y, int x)
	{
		int nStorageIndex = (y * nNbIndexX) + x;
		return nStorageIndex;		
	}
	
	private int getStorageIndex(VarDefBase varDefMaster, int z, int y, int x)
	{
		int nNbIndexY = varDefMaster.getMaxIndexAtDim(1);
		int nNbIndexX = varDefMaster.getMaxIndexAtDim(0);
		return getStorageIndex(nNbIndexY, nNbIndexX, z, y, x);
	}
	
	static int getStorageIndex(int nNbIndexY, int nNbIndexX, int z, int y, int x)
	{
		int nStorageIndex = (z * nNbIndexY * nNbIndexX) + (y * nNbIndexX) + x;
		return nStorageIndex;		
	}
	
//	VarDefBuffer createVarDefItems(ProgramManager programManager, VarDefBase varDefMaster, int x, VarDefBase varDefOccursParent)
//	{
//		VarDefBuffer varDefItem = createAt(programManager, varDefMaster, x, varDefOccursParent); 
//		m_aVarDefItems[x] = varDefItem;
//		return varDefItem;
//	}
//	
//	VarDefBuffer createVarDefItems(ProgramManager programManager, VarDefBase varDefMaster, int x, int y, VarDefBase varDefOccursParent)
//	{
//		int nStorageIndex = getStorageIndex(varDefMaster, y, x);
//		m_aVarDefItems[nStorageIndex] = createAt(programManager, varDefMaster, y, x, varDefOccursParent);
//		return m_aVarDefItems[nStorageIndex];		
//	}
//	
//	VarDefBuffer createVarDefItems(ProgramManager programManager, VarDefBase varDefMaster, int x, int y, int z, VarDefBase varDefOccursParent)
//	{
//		int nStorageIndex = getStorageIndex(varDefMaster, z, y, x);
//		m_aVarDefItems[nStorageIndex] = createAt(programManager, varDefMaster, z, y, x, varDefOccursParent);
//		return m_aVarDefItems[nStorageIndex];
//	}
	
//	private VarDefBuffer createAt(ProgramManager programManager, VarDefBase varDef, int nXBase0, VarDefBase varDefOccursParent)
//	{
//		int nNbDim = varDef.getNbDim();
//		int nAbsStart = 
//			varDef.getAbsolutePositionOccursOwnerAtDim(0) + 
//			(nXBase0 * varDef.getSizeOccursOwnerOf1Entry(0)) + 
//			varDef.getDistanceFromOccursOwner(0);				
//
//		VarDefBuffer varDefBuffer = varDef.createCopySingleItem(nAbsStart, String.valueOf(nXBase0+1));
//		varDefBuffer.m_varDefParent = varDefOccursParent;
//		
//		if(varDefOccursParent != null)
//		{
//			if(varDefOccursParent.m_arrChildren == null)
//				varDefOccursParent.m_arrChildren = new ArrayList<VarDefBase>();
//			varDefOccursParent.m_arrChildren.add(varDefBuffer);
//		}	
//		
//		return varDefBuffer;
//	}
	
//	private VarDefBuffer createAt(ProgramManager programManager, VarDefBase varDef, int nYBase0, int nXBase0, VarDefBase varDefOccursParent)
//	{
//		int nNbDim = varDef.getNbDim();
//		if(nNbDim == 2)			
//		{
//			int n = varDef.getAbsolutePositionOccursOwnerAtDim(1) + 
//					(nYBase0 * varDef.getSizeOccursOwnerOf1Entry(1)) + 
//					varDef.getDistanceFromOccursOwner(1) + 
//					(nXBase0 * varDef.getSizeOccursOwnerOf1Entry(0)) + 
//					varDef.getDistanceFromOccursOwner(0);
//					
//			
//			String csIndexes = String.valueOf(nYBase0+1) + "," + String.valueOf(nXBase0+1);
//			VarDefBuffer varDefBuffer = varDef.createCopySingleItem(n, csIndexes);
//			
//			varDefBuffer.m_varDefParent = varDefOccursParent;
//			if(varDefOccursParent != null)
//			{
//				if(varDefOccursParent.m_arrChildren == null)
//					varDefOccursParent.m_arrChildren = new ArrayList<VarDefBase>();
//				varDefOccursParent.m_arrChildren.add(varDefBuffer);
//			}	
//			return varDefBuffer;
//		}
//		return null;
//	}
	
//	private VarDefBuffer createAt(ProgramManager programManager, VarDefBase varDef, int nZBase0, int nYBase0, int nXBase0, VarDefBase varDefOccursParent)	
//	{
//		int nNbDim = varDef.getNbDim();
//		if(nNbDim == 3)
//		{
//			int n = varDef.getAbsolutePositionOccursOwnerAtDim(2) + 
//							(nZBase0 * varDef.getSizeOccursOwnerOf1Entry(2)) + 
//							varDef.getDistanceFromOccursOwner(2) +
//							(nYBase0 * varDef.getSizeOccursOwnerOf1Entry(1)) + 
//							varDef.getDistanceFromOccursOwner(1) + 
//							(nXBase0 * varDef.getSizeOccursOwnerOf1Entry(0)) + 
//							varDef.getDistanceFromOccursOwner(0);
//
//			String csIndexes = String.valueOf(nZBase0+1) + "," + String.valueOf(nYBase0+1) + "," + String.valueOf(nXBase0+1);
//			VarDefBuffer varDefBuffer = varDef.createCopySingleItem(n, csIndexes);
//			
//			varDefBuffer.m_varDefParent = varDefOccursParent;
//			if(varDefOccursParent != null)
//			{
//				if(varDefOccursParent.m_arrChildren == null)
//					varDefOccursParent.m_arrChildren = new ArrayList<VarDefBase>();
//				varDefOccursParent.m_arrChildren.add(varDefBuffer);
//			}	
//			return varDefBuffer;
//		}
//		return null;
//
//	}	

	
	VarDefBuffer m_aVarDefItems[] = null;
}
