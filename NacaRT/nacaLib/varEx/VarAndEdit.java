/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 31 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package nacaLib.varEx;

import jlib.misc.IntegerRef;
import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.programPool.SharedProgramInstanceData;
import nacaLib.tempCache.CStr;
import nacaLib.tempCache.TempCacheLocator;


/**
 * @author PJD
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class VarAndEdit extends VarBase
{
	public VarAndEdit(DeclareTypeBase declareTypeBase)
	{
		super(declareTypeBase);
	}
	
	protected VarAndEdit()
	{
		super();
	}
	
	public String getSTCheckValue()
	{
		SharedProgramInstanceData sharedProgramInstanceData = getSharedProgramInstanceData();
		
		if(m_bufferPos != null)
		{
			CStr cstr = m_bufferPos.getOwnCStr(m_varDef.getLength());
			String csValue = cstr.getAsString();
			String cs = m_varDef.toDump(sharedProgramInstanceData) + "={\"" + csValue + "\"} ";
			//cstr.resetManagerCache();
			return cs;
		}
		else
		{
			String cs = m_varDef.toDump(sharedProgramInstanceData) + " NO BUFFER !";
			return cs;
		}
	}

	public abstract int compareTo(ComparisonMode mode, VarAndEdit var2);
	public abstract int compareTo(int nValue);
	public abstract int compareTo(double dValue);
	public abstract int compareTo(ComparisonMode mode, String cs);
	
	public abstract void set(CobolConstantZero cst);
	public abstract void set(CobolConstantSpace cst);
	public abstract void set(CobolConstantHighValue cst);
	public abstract void set(CobolConstantLowValue cst);
	public abstract void setStringAtPosition(String csValue, int nOffsetPosition, int nNbChar);
	public abstract void setAndFill(String csValue);
	
	public String digits()
	{
		return m_varDef.digits(m_bufferPos);
	}
	
	public void setRepeatingCharAtOffsetFromStart(CobolConstantZero cst, int nOffsetPosition, int nNbChar)	// Fill with a 0 base index)
	{
		m_varDef.write(m_bufferPos, cst, nOffsetPosition, nNbChar);
	}

	public void setRepeatingCharAtOffsetFromStart(CobolConstantSpace cst, int nOffsetPosition, int nNbChar)
	{
		m_varDef.write(m_bufferPos, cst, nOffsetPosition, nNbChar);
	}

	public void setRepeatingCharAtOffsetFromStart(CobolConstantHighValue cst, int nOffsetPosition, int nNbChar)
	{
		m_varDef.write(m_bufferPos, cst, nOffsetPosition, nNbChar);
	}

	public void setRepeatingCharAtOffsetFromStart(CobolConstantLowValue cst, int nOffsetPosition, int nNbChar)
	{
		m_varDef.write(m_bufferPos, cst, nOffsetPosition, nNbChar);
	}
	
	public boolean isNumeric()
	{
		return m_varDef.isNumeric(m_bufferPos);		
	}	
		
	public boolean isAlphabetic()
	{
		return m_varDef.isAlphabetic(m_bufferPos);
	}
	
	public int getBodySize() 
	{
		return m_varDef.getBodyLength() ;
	}
	
	public VarBase getUnprefixNamedVarChild(BaseProgramManager programManager, String csColName, IntegerRef rnChildIndex)
	{
		VarDefBase varDefChild = m_varDef.getUnprefixNamedChild(programManager.getSharedProgramInstanceData(), csColName, rnChildIndex);
		if(varDefChild != null)
		{
			//VarBase varChild = m_bufferPos.getVarFullName(varDefChild.getFullName(m_bufferPos.getProgramManager().getSharedProgramInstanceData()));
			VarBase varChild = programManager.getVarFullName(varDefChild.getId());
			return varChild;
		}
		return null;
	}
	
	public VarBase getUnDollarUnprefixNamedChild(BaseProgramManager programManager, String csColName, IntegerRef rnChildIndex)
	{
		VarDefBase varDefChild = m_varDef.getUnDollarUnprefixNamedChild(programManager.getSharedProgramInstanceData(), csColName, rnChildIndex);
		if(varDefChild != null)
		{
			//VarBase varChild = m_bufferPos.getVarFullName(varDefChild.getFullName(m_bufferPos.getProgramManager().getSharedProgramInstanceData()));
			VarBase varChild = programManager.getVarFullName(varDefChild.getId());
			return varChild;
		}
		return null;
	}
	
		
	public Var getVarChildAt(int n)
	{
		n--;	// given as 1-based
		int nNChildren = m_varDef.getNbChildren();
		if(n <= nNChildren)
		{
			VarDefBuffer varDefChild = m_varDef.getChild(n);
			if(varDefChild != null)
			{
				BaseProgramManager programManager = TempCacheLocator.getTLSTempCache().getProgramManager();
				VarBase varChild = programManager.getVarFullName(varDefChild);
				if(!varChild.isEdit())
					return (Var)varChild;
			}
		}	
		return null;
	}
			
	public Edit getEditChildAt(int n)
	{
		n--;	// given as 1-based
		int nNChildren = m_varDef.getNbChildren();
		if(n <= nNChildren)
		{
			VarDefBuffer varDefChild = m_varDef.getChild(n);
			if(varDefChild != null)
			{
				BaseProgramManager programManager = TempCacheLocator.getTLSTempCache().getProgramManager();
				VarBase varChild = programManager.getVarFullName(varDefChild);
				if(varChild.isEdit())
				{
					Edit editChild = (Edit)varChild;
					return editChild;
				}
			}
		}	
		return null;
	}
	
	void inheritSemanticContext(VarBase varSource)
	{
		String csSemanticValue = varSource.getSemanticContextValue();
		setSemanticContextValue(csSemanticValue);
	}

	/**
	 * @return
	 */
	public int getNbOccurs()
	{
		return m_varDef.getNbOccurs() ;
	}	

}
