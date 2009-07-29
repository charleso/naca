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

import java.math.BigDecimal;

import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.debug.BufferSpy;
import nacaLib.mathSupport.MathBase;
import nacaLib.misc.StringAsciiEbcdicUtil;
import nacaLib.tempCache.CStr;
import nacaLib.tempCache.TempCache;
import nacaLib.tempCache.TempCacheLocator;
import jlib.log.Log;
import jlib.misc.*;

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class Var extends VarAndEdit
{
	public Var(DeclareTypeBase declareTypeBase)
	{
		super(declareTypeBase);
	}
	
	protected Var()
	{
		super();
	}
	
	boolean isEdit()
	{
		return false;
	}
	
	public void assignBufferExt(VarBuffer bufferSource)
	{
		if(m_bufferPos == null)
			m_bufferPos = new VarBufferPos(bufferSource, m_varDef.m_nDefaultAbsolutePosition);
		else	// reuse
			m_bufferPos.reuse(bufferSource, m_varDef.m_nDefaultAbsolutePosition);
	}
	
	public void set(CobolConstantZero cst)
	{
		m_varDef.write(m_bufferPos, cst);
	}

	public void set(CobolConstantSpace cst)
	{
		m_varDef.write(m_bufferPos, cst);
	}

	public void set(CobolConstantHighValue cst)
	{
		m_varDef.write(m_bufferPos, cst);
	}

	public void set(CobolConstantLowValue cst)
	{
		m_varDef.write(m_bufferPos, cst);
	}
	
	public void setStringAtPosition(String csValue, int nOffsetPosition, int nNbChar)
	{
		m_varDef.write(m_bufferPos, csValue, nOffsetPosition, nNbChar);
	}
	
			
	public void setAndFill(String csValue)
	{
		if(csValue.length() > 0)
			m_varDef.writeAndFill(m_bufferPos, csValue.charAt(0));
	}
	
//	public String digits()
//	{
//		return m_varDef.digits(); 
//	}


//	public void set(GenericValue gv)
//	{
//		m_varDef.write(m_buffer, gv);
//	}
	
	public Var getAt(VarAndEdit varX)
	{
		int x = varX.getInt();
		return getAt(x);
	}
	
	public Var getAt(MathBase math)
	{
		int x = math.m_d.intValue() ;
		return getAt(x);
	}
	
	
	public Var subStringFrom(Var vStart1Based)
	{
		int nStart1Based = vStart1Based.getInt();
		return subStringFrom(nStart1Based);
	}
	
	public Var subStringFrom(int nStart1Based)
	{
		int nTotalLength = this.getLength();
		int nRemainingLength = nTotalLength - nStart1Based;  
		Var var = subString(nStart1Based, nRemainingLength);
		return var;
	}
	
	/**
	 * PJReady
	 * @param String csSource: Source string
	 * @param int nStart: 1 based start position into source string
	 * @param int nNbChars: Number of chars to extract
	 * @return Var temp var represening the subtring of the source var, starting form position start, up to nNbChars chars
	 */
	public Var subString(Var vStart1Based, Var vNbChars)
	{
		int nStart1Based = vStart1Based.getInt();
		int nNbChars = vNbChars.getInt();
		Var var = subString(nStart1Based, nNbChars);
		return var;
	}
	
	public Var subString(MathBase mStart1Based, MathBase mNbChars)
	{
		int nStart1Based = mStart1Based.m_d.intValue();
		int nNbChars = mNbChars.m_d.intValue();
		Var var = subString(nStart1Based, nNbChars);
		return var;
	}
	
	public Var subString(MathBase mStart1Based, Var vNbChars)
	{
		int nStart1Based = mStart1Based.m_d.intValue();
		int nNbChars = vNbChars.getInt();
		Var var = subString(nStart1Based, nNbChars);
		return var;
	}
	
	public Var subString(Var vStart1Based, MathBase mNbChars)
	{
		int nStart1Based = vStart1Based.getInt();
		int nNbChars = mNbChars.m_d.intValue();
		Var var = subString(nStart1Based, nNbChars);
		return var;
	}
	
	public Var subString(int nStart1Based, Var vNbChars)
	{
		int nNbChars = vNbChars.getInt();
		Var var = subString(nStart1Based, nNbChars);
		return var;
	}
	
	public Var subString(int nStart1Based, MathBase mNbChars)
	{
		int nNbChars = mNbChars.m_d.intValue();
		Var var = subString(nStart1Based, nNbChars);
		return var;
	}
	
	public Var subString(MathBase mStart1Based, int nNbChars)
	{
		int nStart1Based = mStart1Based.m_d.intValue();
		Var var = subString(nStart1Based, nNbChars);
		return var;
	}
	
	public Var subString(Var vStart1Based, int nNbChars)
	{
		int nStart1Based = vStart1Based.getInt();
		Var var = subString(nStart1Based, nNbChars);
		return var;
	}

	
	/**
	 * PJReady
	 * @param String csSource: Source string
	 * @param int nStart: 1 based start position into source string
	 * @param int nNbChars: Number of chars to extract
	 * @return Var temp var represening the subtring of the source var, starting form position start, up to nNbChars chars
	 */
	public Var subString(int nStart1Based, int nNbChars)
	{
		if(nStart1Based <= 0) 
			Log.logCritical("Substring error: start offset is too low (" + nStart1Based + ")");
		if(nNbChars <= 0)
			Log.logCritical("Substring error: number of char to extract is too low: "+nNbChars);
		if(nStart1Based > getLength() + nStart1Based)
		{
			int nMax = getLength() + nStart1Based;
			Log.logCritical("Substring error: number of char to extract is too high: requets=" + nStart1Based + " maximum value is "+nMax);
		}
		
		TempCache cache = TempCacheLocator.getTLSTempCache();
		if(cache != null)
		{
			int nTypeId = m_varDef.getTypeId();
			CoupleVar coupleVarGetAt = cache.getTempVar(nTypeId);
			if(coupleVarGetAt != null)
			{
				// Not an occursed item, but get the nth char: m_varDef.m_occursItemSettings == null 
				int nAbsStart = m_varDef.getBodyAbsolutePosition(m_bufferPos);
				nAbsStart += nStart1Based-1;
				m_varDef.adjustSettingForCharGetAt(coupleVarGetAt.m_varDefBuffer, nAbsStart);

				if(coupleVarGetAt.m_variable == null)
					coupleVarGetAt.m_variable = allocCopy(coupleVarGetAt.m_varDefBuffer);
				
				adjustSubString(coupleVarGetAt.m_varDefBuffer, (Var)coupleVarGetAt.m_variable);
				coupleVarGetAt.m_varDefBuffer.setTotalSize(nNbChars);

				return (Var)coupleVarGetAt.m_variable;
			}
			// Not an occursed item, but get the nth char: m_varDef.m_occursItemSettings == null 
			int nAbsStart = m_varDef.getBodyAbsolutePosition(m_bufferPos);
			nAbsStart += nStart1Based-1;
			
			VarDefBuffer varDefGetAt = m_varDef.allocCopy();
			m_varDef.adjustSettingForCharGetAt(varDefGetAt, nAbsStart);
			varDefGetAt.setTotalSize(nNbChars);
			Var varGetAt = allocCopySubString(varDefGetAt);

			cache.addTempVar(nTypeId, varDefGetAt, varGetAt);
			return varGetAt;
		}

//		VarDefBuffer varDefItem = m_varDef.getAt(x);
//		if(varDefItem == null)
//			return this;
//		Var var = allocCopy(varDefItem);
//		return var;
		return null;
	}
	
	public Var getAt(int x)
	{
		TempCache cache = TempCacheLocator.getTLSTempCache();
		if(cache != null)
		{
			int nTypeId = m_varDef.getTypeId();
			CoupleVar coupleVarGetAt = cache.getTempVar(nTypeId);
			if(coupleVarGetAt != null)
			{
				if(m_varDef.m_occursItemSettings == null) // Not an occursed item, but get the nth char
				{
					int nAbsStart = m_varDef.getBodyAbsolutePosition(m_bufferPos);
					nAbsStart += x-1;
					m_varDef.adjustSettingForCharGetAt(coupleVarGetAt.m_varDefBuffer, nAbsStart);

					if(coupleVarGetAt.m_variable == null)
						coupleVarGetAt.m_variable = allocCopy(coupleVarGetAt.m_varDefBuffer);
					
					adjust(coupleVarGetAt.m_varDefBuffer, (Var)coupleVarGetAt.m_variable);

					return (Var)coupleVarGetAt.m_variable;
				}
				else	// Real getAt the reached an occursed item
				{
				  	// Adjust varDefGetAt to m_varDef.getAt(x); It is already created in the correct type
					int nXBase0 = x-1;
					m_varDef.checkIndexes(nXBase0);		// PJD Check added even if the var is retrieved form caches
					int nAbsStart = m_varDef.getAbsStart(x-1);
					int nDebugIndex = VarDefBase.makeDebugIndex(x);
					m_varDef.adjustSetting(coupleVarGetAt.m_varDefBuffer, nAbsStart, nDebugIndex, 1, m_varDef.m_varDefParent);
					
					if(coupleVarGetAt.m_variable == null)
						coupleVarGetAt.m_variable = allocCopy(coupleVarGetAt.m_varDefBuffer);
					
					adjust(coupleVarGetAt.m_varDefBuffer, (Var)coupleVarGetAt.m_variable);
					return (Var)coupleVarGetAt.m_variable;
				}
			}
			if(m_varDef.m_occursItemSettings == null) // Not an occursed item, but get the nth char
			{
				int nAbsStart = m_varDef.getBodyAbsolutePosition(m_bufferPos);
				nAbsStart += x-1;
				
				VarDefBuffer varDefGetAt = m_varDef.allocCopy();
				m_varDef.adjustSettingForCharGetAt(varDefGetAt, nAbsStart);
				Var varGetAt = allocCopy(varDefGetAt);

				cache.addTempVar(nTypeId, varDefGetAt, varGetAt);
				return varGetAt;
			}
			else	// Real getAt the reached an occursed item
			{
				VarDefBuffer varDefGetAt = m_varDef.getAt(x);
				if(varDefGetAt == null)
					return this;
				Var varGetAt = allocCopy(varDefGetAt);
				
				cache.addTempVar(nTypeId, varDefGetAt, varGetAt);
				return varGetAt;
			}
		}

		VarDefBuffer varDefItem = m_varDef.getAt(x);
		if(varDefItem == null)
			return this;
		Var var = allocCopy(varDefItem);
		return var;
	}
	
	public Var getAt(int x, VarAndEdit varY)
	{
		int y = varY.getInt();
		return getAt(x, y);
//		VarDefBuffer varDefItem = m_varDef.getAt(x, y);
//		if(varDefItem == null)
//			return this;
//		Var var = allocCopy(varDefItem);
//		return var;
	}
	
	public Var getAt(MathBase math, VarAndEdit varIndexY)
	{
		int x = math.m_d.intValue() ;
		int y = varIndexY.getInt();
		return getAt(x, y);
	}
	
	public Var getAt(VarAndEdit vx, VarAndEdit varIndexY)
	{
		int x = vx.getInt() ;
		int y = varIndexY.getInt();
		return getAt(x, y);
	}
	
	public Var getAt(VarAndEdit varIndexY, MathBase math)
	{
		int x = math.m_d.intValue() ;
		int y = varIndexY.getInt();
		return getAt(y, x);
	}
	public Var getAt(VarAndEdit varIndexY, int x)
	{
		int y = varIndexY.getInt();
		return getAt(y, x);
	}
	
	public Var getAt(int y, int x)
	{
		int nYBase0 = y-1;
		int nXBase0 = x-1;
		TempCache cache = TempCacheLocator.getTLSTempCache();
		if(cache != null)
		{
			int nTypeId = m_varDef.getTypeId();
			CoupleVar coupleVarGetAt = cache.getTempVar(nTypeId);
			if(coupleVarGetAt != null)
			{
			  	// Adjust varDefGetAt to m_varDef.getAt(x); It is already created in the correct type
				m_varDef.checkIndexes(nXBase0, nYBase0);	// PJD Check added even if the var is retrieved form caches 
				int nAbsStart = m_varDef.getAbsStart(nXBase0, nYBase0);
				int nDebugIndex = VarDefBase.makeDebugIndex(y, x);
				m_varDef.adjustSetting(coupleVarGetAt.m_varDefBuffer, nAbsStart, nDebugIndex, 2, m_varDef.m_varDefParent);
				
				if(coupleVarGetAt.m_variable == null)
					coupleVarGetAt.m_variable = allocCopy(coupleVarGetAt.m_varDefBuffer);
					
				adjust(coupleVarGetAt.m_varDefBuffer, (Var)coupleVarGetAt.m_variable);
				return (Var)coupleVarGetAt.m_variable;
			}
			VarDefBuffer varDefGetAt = m_varDef.getAt(y, x);
			if(varDefGetAt == null)
				return this;
			Var varGetAt = allocCopy(varDefGetAt);
			
			cache.addTempVar(nTypeId, varDefGetAt, varGetAt);
			
			return varGetAt;
		}

		VarDefBuffer varDefItem = m_varDef.getAt(y, x);
		if(varDefItem == null)
			return this;
		Var var = allocCopy(varDefItem);
		return var;
	}
	
	public Var getAt(VarAndEdit varIndexX, VarAndEdit varIndexY, int z)
	{
		int x = varIndexX.getInt();
		int y = varIndexY.getInt();
		return getAt(x, y, z);
	}
	
	public Var getAt(int x, VarAndEdit varIndexY, int z)
	{
		int y = varIndexY.getInt();
		return getAt(x, y, z);
	}
	
	public Var getAt(VarAndEdit varIndexX, VarAndEdit varIndexY, VarAndEdit varIndexZ)
	{
		int x = varIndexX.getInt();
		int y = varIndexY.getInt();
		int z = varIndexZ.getInt();
		return getAt(x, y, z);
	}
	
	public Var getAt(int x, VarAndEdit varIndexY, VarAndEdit varIndexZ)
	{
		int y = varIndexY.getInt();
		int z = varIndexZ.getInt();
		return getAt(x, y, z);
	}
	
	public Var getAt(VarAndEdit varIndexX, int y, VarAndEdit varIndexZ)
	{
		int x = varIndexX.getInt();
		int z = varIndexZ.getInt();
		return getAt(x, y, z);
	}
	
	public Var getAt(int z, int y, int x)
	{
		int nZBase0 = z-1;
		int nYBase0 = y-1;
		int nXBase0 = x-1;
		TempCache cache = TempCacheLocator.getTLSTempCache();
		if(cache != null)
		{
			int nTypeId = m_varDef.getTypeId();
			CoupleVar coupleVarGetAt = cache.getTempVar(nTypeId);
			if(coupleVarGetAt != null)
			{
			  	// Adjust varDefGetAt to m_varDef.getAt(x); It is already created in the correct type
				m_varDef.checkIndexes(nXBase0, nYBase0, nZBase0);	// PJD Check added even if the var is retrieved form caches
				int nAbsStart = m_varDef.getAbsStart(nXBase0, nYBase0, nZBase0);
				int nDebugIndex = VarDefBase.makeDebugIndex(z, y, x);
				m_varDef.adjustSetting(coupleVarGetAt.m_varDefBuffer, nAbsStart, nDebugIndex, 3, m_varDef.m_varDefParent);
				
				if(coupleVarGetAt.m_variable == null)
					coupleVarGetAt.m_variable = allocCopy(coupleVarGetAt.m_varDefBuffer);
					
				adjust(coupleVarGetAt.m_varDefBuffer, (Var)coupleVarGetAt.m_variable);
				return (Var)coupleVarGetAt.m_variable;
			}
			VarDefBuffer varDefGetAt = m_varDef.getAt(z, y, x);
			if(varDefGetAt == null)
				return this;
			Var varGetAt = allocCopy(varDefGetAt);
			
			cache.addTempVar(nTypeId, varDefGetAt, varGetAt);
			
			return varGetAt;
		}

		VarDefBuffer varDefItem = m_varDef.getAt(z, y, x);
		if(varDefItem == null)
			return this;
		Var var = allocCopy(varDefItem);
		return var;
	}
	
	public String getStringAt(int x)
	{
		Var var = getAt(x);
		return var.getString();
	}
	
	public String getStringAt(int x, int y)
	{
		Var var = getAt(x, y);
		return var.getString();
	}  

	public String getStringAt(int x, int y, int z)
	{
		Var var = getAt(x, y, z);
		return var.getString();
	}
	
	public void adjust(VarDefBuffer varDefGetAt, Var varGetAt)
	{
		// Fill varGetAt with custom setting of this 
		varGetAt.m_varDef = varDefGetAt;
		adjust(varDefGetAt, varGetAt.m_bufferPos);
//		int nOffset = m_bufferPos.m_nAbsolutePosition - m_varDef.m_nDefaultAbsolutePosition;
//		varGetAt.m_bufferPos.shareDataBufferFrom(m_bufferPos);		
//		varGetAt.m_bufferPos.m_nAbsolutePosition = varDefGetAt.m_nDefaultAbsolutePosition + nOffset;		
	}
	
	public void adjust(VarDefBuffer varDefGetAt, VarBufferPos varBufferPos)
	{
		// Fill varGetAt with custom setting of this 
		varBufferPos.shareDataBufferFrom(m_bufferPos);
		int nOffset = m_bufferPos.m_nAbsolutePosition - m_varDef.m_nDefaultAbsolutePosition;
		varBufferPos.m_nAbsolutePosition = varDefGetAt.m_nDefaultAbsolutePosition + nOffset;
		//varBufferPos.setProgramManager(m_bufferPos.getProgramManager());
	}
	
	private void adjustSubString(VarDefBuffer varDefGetAt, Var varGetAt)
	{
		// Fill varGetAt with custom setting of this 
		varGetAt.m_varDef = varDefGetAt;
		// Fill varGetAt with custom setting of this 
		varGetAt.m_bufferPos.shareDataBufferFrom(m_bufferPos);
		//int nOffset = m_bufferPos.m_nAbsolutePosition - m_varDef.m_nDefaultAbsolutePosition;
		varGetAt.m_bufferPos.m_nAbsolutePosition = varDefGetAt.m_nDefaultAbsolutePosition;	// + nOffset;
	}

	
	public Var allocCopy(VarDefBuffer varDefItem)
	{ 
		VarBase varItem = allocCopy();
		varItem.m_varDef = varDefItem;
		
		int nOffset = m_bufferPos.m_nAbsolutePosition - m_varDef.m_nDefaultAbsolutePosition;
		varItem.m_bufferPos = new VarBufferPos(m_bufferPos, varDefItem.m_nDefaultAbsolutePosition + nOffset);
		varItem.m_varTypeId = varDefItem.getTypeId();
		
		//assertIfFalse(varItem.m_bufferPos.getProgramManager() == m_bufferPos.getProgramManager());
		
		return (Var)varItem;
	}
	
	private Var allocCopySubString(VarDefBuffer varDefItem)
	{ 
		VarBase varItem = allocCopy();
		varItem.m_varDef = varDefItem;
		
		//int nOffset = m_bufferPos.m_nAbsolutePosition - m_varDef.m_nDefaultAbsolutePosition;
		varItem.m_bufferPos = new VarBufferPos(m_bufferPos, varDefItem.m_nDefaultAbsolutePosition);
		varItem.m_varTypeId = varDefItem.getTypeId();
		
		//assertIfFalse(varItem.m_bufferPos.getProgramManager() == m_bufferPos.getProgramManager());
		
		return (Var)varItem;
	}
			
	protected abstract VarBase allocCopy();
	
	public String getString()
	{
		CStr cstr = getOwnCStr();
		String cs = cstr.getAsString();
		//cstr.resetManagerCache();
		return cs;
	}
	
//	public CStr getCStr()
//	{
//		CStr cstr = m_bufferPos.getBufChunkAt(m_varDef.getBodyLength());
//		return cstr;
//	}
	
	public CStr getOwnCStr()
	{
		CStr cstr = m_bufferPos.getOwnCStr(m_varDef.getBodyLength());
		return cstr;
	}
	
	public String getStringIncludingHeader()
	{
		//return m_varDef.getRawStringIncludingHeader(m_bufferPos);
		CStr cstr = m_bufferPos.getOwnCStr(m_varDef.getLength());
		String cs = cstr.getAsString();
		return cs;
	}
			
	public int getLength()
	{
		return m_varDef.getLength();
	}
	
//	public int getDependingLength()
//	{
//		return m_varDef.getRecordDependingLength(m_bufferPos);
//	}

	
	public void transferTo(Var varDest)
	{
		m_varDef.transfer(m_bufferPos, varDest);
		//varDest.inheritSemanticContext(this);
	}
		
	public void set(Edit varSource)
	{
		if(varSource.isEditInMap())
			set((EditInMap)varSource);
		else
			set((EditInMapRedefine)varSource);
	}
	
	public void set(VarBase varSource)
	{
		if(varSource.isEdit())
			set((Edit)varSource);
		else
		{
			varSource.m_varDef.transfer(varSource.m_bufferPos, this);
			//inheritSemanticContext(varSource);
		}
	}
	
	public void set(EditInMap varSource)
	{
		varSource.m_varDef.transfer(varSource.m_bufferPos, this);
		//inheritSemanticContext(varSource);
	}
	
	public void set(EditInMapRedefine varSource)
	{
		//varSource.m_Var2EditInMap.m_varDef.transfer(varSource.m_Var2EditInMap.m_buffer, this);
		int n = 0;
		assertIfFalse(false);
	}
	


	public void transferTo(Edit varDest)
	{
		m_varDef.transfer(m_bufferPos, varDest);	// PJD Var TO EditInMapRedefine; // PJD Var TO EditInMap 
		//varDest.inheritSemanticContext(this);
	}
	
	public boolean equals(VarAndEdit varValue)	
	{
		if(compareTo(ComparisonMode.Unicode, varValue) == 0)
			return true;
		return false;
	}
	
	public int compareTo(ComparisonMode mode, VarAndEdit varValue)	
	{
		return m_varDef.compare(mode, m_bufferPos, varValue);
	}


	
	public boolean equals(int nValue)	
	{
		if(compareTo(nValue) == 0)
			return true;
		return false;
	}
	
	public boolean equals(double dValue)	
	{
		if(compareTo(dValue) == 0)
			return true;
		return false;
	}
	
	
	public boolean equals(String csValue)	
	{
		if(compareTo(ComparisonMode.Unicode, csValue) == 0)
			return true;
		return false;
	}

	public int compareTo(ComparisonMode mode, String sValue)	
	{
//		 PJD removed for Batch optimization
		//String s = getString();
		//return StringAsciiEbcdicUtil.compare(mode, s, sValue);
//		 PJD end removed for Batch optimization
		
//		 PJD added for Batch optimization
		CStr cstr = getOwnCStr();
		int n = StringAsciiEbcdicUtil.compare(mode, cstr, sValue);
		//TempCacheLocator.getTLSTempCache().resetCStr();
//		 PJD end added for Batch optimization
		return n;
	}
	
	public boolean equals(MathBase MathValue)	
	{
		if(compareTo(MathValue) == 0)
			return true;
		return false;
	}
	
	public int compareTo(MathBase MathValue)	
	{
		int n = MathValue.compareTo(this);
		// Return opposite sign, as we changed the operand order
		if(n < 0)
			return 1;
		if(n > 0)
			return -1;
		return 0;
	}
	
	public void inc()
	{
		m_varDef.inc(m_bufferPos, 1); 
	}

	public void inc(int nStep)
	{
		m_varDef.inc(m_bufferPos, nStep);
	}
	public void inc(double dStep)
	{
		BigDecimal bdStep = new BigDecimal(dStep); 
		m_varDef.inc(m_bufferPos, bdStep);
	}
	
	public void inc(String csStep)
	{
		BigDecimal bdStep = new BigDecimal(csStep);
		m_varDef.inc(m_bufferPos, bdStep);
	}

	public void inc(Var varStep)
	{
		String csStep = varStep.getDottedSignedString();
		BigDecimal bdStep = new BigDecimal(csStep);
		m_varDef.inc(m_bufferPos, bdStep);
	}

	public void dec()
	{
		m_varDef.inc(m_bufferPos, -1);
	}
		
	public void dec(int nStep)
	{
		m_varDef.inc(m_bufferPos, -nStep);
	}
	
	public void dec(Var varStep)
	{
		String csStep = varStep.getDottedSignedString();
		BigDecimal bdStep = new BigDecimal(csStep);
		bdStep = bdStep.negate();
		m_varDef.inc(m_bufferPos, bdStep);
	}
	
	public void dec(String csStep)
	{
		BigDecimal bdStep = new BigDecimal(csStep);
		bdStep = bdStep.negate();
		m_varDef.inc(m_bufferPos, bdStep);
	}
	
	public String getDottedSignedString()
	{
		String cs = m_varDef.getDottedSignedString(m_bufferPos).getAsString();
		return cs;
	}
	
	public String getDottedSignedStringAsSQLCol()
	{
		String cs = m_varDef.getDottedSignedStringAsSQLCol(m_bufferPos).getAsString();
		return cs;
	}
	
	public String getAsAlphaNumString()
	{
		String cs = m_varDef.getAsAlphaNumString(m_bufferPos).getAsString();
		return cs;
	}
	
	public int getAbsolutePosition()
	{
		return m_bufferPos.m_nAbsolutePosition;	//m_varDef.DEBUGgetAbsolutePosition();
	}
	
	public int DEBUGgetAbsolutePosition()
	{
		return m_bufferPos.m_nAbsolutePosition;	//m_varDef.DEBUGgetAbsolutePosition();
	}
	
	EditAttributManager getEditAttributManager()
	{
		return null;
	}

	public void set(boolean b)
	{
		Assert("Var.set(boolean) not implemented") ;
	}
	
	public boolean compareTo(boolean b)
	{
		Assert("Var.compareTo(boolean) not implemented") ;
		return false ;
	}
	
	int getOffsetFromLevel01()
	{
		return m_varDef.getOffsetFromLevel01();
	}
	
	public Var spyWrite()
	{
		BufferSpy.addVarToSpy(this);
		return this;
	}

}
