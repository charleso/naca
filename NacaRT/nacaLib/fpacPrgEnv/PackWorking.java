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
package nacaLib.fpacPrgEnv;


import jlib.misc.AsciiEbcdicConverter;
import nacaLib.misc.CCommarea;
import nacaLib.varEx.CCallParam;
import nacaLib.varEx.CallParamFpac;
import nacaLib.varEx.CobolConstant;
import nacaLib.varEx.Var;
import nacaLib.varEx.VarBuffer;
import nacaLib.varEx.VarBufferPos;

public class PackWorking extends FPacVarManager
{
	static private final int WORKING_SIZE = 10001;	// Nb bytes in preallocated working
	private int m_nBufferId = 0;
	private FPacVarCacheManager m_fpacVarCacheManager = null;
	
	public PackWorking(FPacProgram program)
	{
		super(program);
		
		m_nBufferId = FPacBufferCounter.getBufferId();
		
		char acBuffer[] = new char [WORKING_SIZE];
		m_varBuffer = new VarBuffer(acBuffer); 
		
		init();
		m_fpacVarCacheManager = program.getFPacVarCacheManager();
	}
	
	private void init()
	{
		for(int n=0; n<5999; n++)
		{
			setAtLowValue(n);
		}
		for(int n=6000; n<=6999; n+=10)
		{
			setInitPacked8Digits(n);
			setAtSpace(n+8);
			setAtSpace(n+9);
		}
		for(int n=7000; n<=7999; n++)
		{
			setAtSpace(n);
		}
		for(int n=8000; n<=8999; n++)
		{
			setAtHighValue(n);
		}
		for(int n=9000; n<=10000; n++)
		{
			setAtLowValue(n);
		}
	}

	private boolean isPositionInRangeComp3(int nPosition1Based)
	{
		if(nPosition1Based >= 6001 && nPosition1Based <= 7000)
			return true;
		return false;
	}
	
	VarFPacLengthUndef createFPacVarLengthUndef(int nAbsolutePosition1Based)
	{
		VarFPacLengthUndef v = null;
		if(isPositionInRangeComp3(nAbsolutePosition1Based))	
		{
			v = new VarFPacNumIntSignComp3LengthUndef(this, getVarBuffer(), nAbsolutePosition1Based);
		}
		else
		{
			v = new VarFPacAlphaNumLengthUndef(this, getVarBuffer(), nAbsolutePosition1Based);
		}		
		return v;
	}
	
	VarFPacLengthUndef createFPacVarXLengthUndef(int nAbsolutePosition1Based)
	{
		VarFPacLengthUndef v = new VarFPacAlphaNumLengthUndef(this, getVarBuffer(), nAbsolutePosition1Based);
		return v;
	}
	
	VarFPacLengthUndef createFPacVarPLengthUndef(int nAbsolutePosition1Based)
	{
		VarFPacLengthUndef v = new VarFPacNumIntSignComp3LengthUndef(this, getVarBuffer(), nAbsolutePosition1Based);
		return v;
	}
	
	private int getBufferId()
	{
		return m_nBufferId;
	}
	
	Var createFPacVar(int nPosition1Based, int nBufferLength)
	{		
		if(isPositionInRangeComp3(nPosition1Based))		
		{
			return createFPacVarNumIntSignComp3(m_varBuffer, nPosition1Based, nBufferLength);
		}
		else //if(nPosition >= 7000 && nPosition <= 7999)
		{
			return createFPacVarAlphaNum(m_varBuffer, nPosition1Based, nBufferLength);
		}
	}
	
	Var createFPacVarX(int nPosition1Based, int nBufferLength)
	{		
		return createFPacVarAlphaNum(m_varBuffer, nPosition1Based, nBufferLength);
	}
	
	Var createFPacVarP(int nPosition1Based, int nBufferLength)
	{		
		return createFPacVarNumIntSignComp3(m_varBuffer, nPosition1Based, nBufferLength);
	}
	
	Var createFPacVar(int nPosition1Based, String csEditMask)
	{		
		return createFPacVarNumEdited(m_varBuffer, nPosition1Based, csEditMask);
	}

//	
//	private int getDefaultLength(int nPosition)
//	{
//		if(nPosition >= 6000 && nPosition <= 6999)
//		{
//			return 8;	// 8 chars by default for packed
//		}
//		else //if(nPosition >= 7000 && nPosition <= 7999)
//		{
//			return 1;			
//		}		
//	}

	
	private void setAtLowValue(int n)
	{
		m_varBuffer.setCharAt(n, CobolConstant.LowValue.getValue());
	}
	
	private void setAtHighValue(int n)
	{
		m_varBuffer.setCharAt(n, CobolConstant.HighValue.getValue());
	}

	private void setAtSpace(int n)
	{
		m_varBuffer.setCharAt(n, CobolConstant.Space.getValue());
	}
	
	private void setInitPacked8Digits(int nAbsoluteStartPosition)
	{
		VarBufferPos varBufferPos = new VarBufferPos(m_varBuffer, nAbsoluteStartPosition);
		m_varBuffer.setIntSignComp3At(varBufferPos, 0L, 15, 8);
	}
	
	VarBuffer getVarBuffer()
	{
		return m_varBuffer;
	}
	
	public void dumpHexa(int nPosition, int nLength)
	{
		m_varBuffer.dumpHexa(nPosition, nLength);
	}
	
	void fillCommarea(int nLength, CCommarea commarea)
	{
		CallParamFpac callParamFPac = commarea.buildCallParamFPac();
		Var varDest = createFPacVarX(5001, nLength);
		callParamFPac.MapOn(varDest);		
	}
	
	private VarBuffer m_varBuffer = null;	
}
