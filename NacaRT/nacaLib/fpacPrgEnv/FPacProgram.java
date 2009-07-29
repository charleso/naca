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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import jlib.misc.AsciiEbcdicConverter;
import jlib.misc.StringUtil;
import nacaLib.basePrgEnv.BaseCESMManager;
import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.batchPrgEnv.BatchProgramManagerFactory;
import nacaLib.misc.CCommarea;
import nacaLib.varEx.BaseFileDescriptor;
import nacaLib.varEx.Console;
import nacaLib.varEx.Var;

public abstract class FPacProgram extends BaseProgram
{
	protected Console wto = null;
	private FPacVarCacheManager m_fpacVarCacheManager = null;
	protected FPacVarSectionDeclaration declare = null;
	private boolean m_bLastDone = false;
	
	FPacVarCacheManager getFPacVarCacheManager()
	{
		return m_fpacVarCacheManager;
	}
	
	public FPacProgram()
	{
		super(new BatchProgramManagerFactory());
		
		m_fpacVarCacheManager = new FPacVarCacheManager();
		declare = new FPacVarSectionDeclaration(this);
		
		working = new PackWorking(this);
		wto = new Console();
		m_StartDate = getCurrentDataJJMMAA();
		m_StartTime = getCurrentTimeHHMMSS();
	}
	
	public void procedureDivision()	// Virtual that can be derived
	{
		Var v = getCommAreaLength();
		if(v != null && v.getInt() != 0)
		{
			int nCommeareLength = v.getInt();
			CCommarea commarea = getProgramManager().getEnv().getCommarea();
			working.fillCommarea(nCommeareLength, commarea);
		}
		X = new int[NB_X];
		for(int n=0; n<NB_X; n++)
		{
			X[n] = 0;
		}
		run();
	}
	
	private void run()
	{	
		m_bLastDone = false;
		int nCurrent = FIRST;
		int nNext = 0;
		while(nCurrent != END)
		{
			if(nCurrent == FIRST)
				nNext = first();
			else if(nCurrent == NORMAL)
				nNext = normal();				
			else if(nCurrent == LAST)
				nNext = doOnceLast();
			
			if(nNext == END)
				return;
			
			if(nNext == NEXT)
				nCurrent++;
			else
				nCurrent = nNext;
		}
		doOnceLast(); 
	}
	
	private int doOnceLast()
	{
		if(m_bLastDone == false)
		{
			m_bLastDone = true;
			last();
		}
		return END;
	}

	protected BaseCESMManager getCESM()
	{
		return null;
	}
	
	protected VarFPacLengthUndef working(int nAbsolutePosition0Based)
	{
		int nAbsolutePosition1Based = nAbsolutePosition0Based + 1;
		return working.createFPacVarLengthUndef(nAbsolutePosition1Based);
	}
	
	protected VarFPacLengthUndef workingX(int nAbsolutePosition0Based)
	{
		int nAbsolutePosition1Based = nAbsolutePosition0Based + 1;
		return working.createFPacVarXLengthUndef(nAbsolutePosition1Based);
	}
	
	protected VarFPacLengthUndef workingP(int nAbsolutePosition0Based)
	{
		int nAbsolutePosition1Based = nAbsolutePosition0Based + 1;
		return working.createFPacVarPLengthUndef(nAbsolutePosition1Based);
	}
	
	protected Var working(int nAbsolutePosition0Based, int nBufferLength)
	{
		int nAbsolutePosition1Based = nAbsolutePosition0Based + 1;
		return working.createFPacVar(nAbsolutePosition1Based, nBufferLength);
	}
	
	protected Var workingX(int nAbsolutePosition0Based, int nBufferLength)
	{
		int nAbsolutePosition1Based = nAbsolutePosition0Based + 1;
		return working.createFPacVarX(nAbsolutePosition1Based, nBufferLength);
	}
	
	protected Var workingP(int nAbsolutePosition0Based, int nBufferLength)
	{
		int nAbsolutePosition1Based = nAbsolutePosition0Based + 1;
		return working.createFPacVarP(nAbsolutePosition1Based, nBufferLength);
	}

	protected Var working(int nAbsolutePosition0Based, String csEditMask)
	{
		int nAbsolutePosition1Based = nAbsolutePosition0Based + 1;
		return working.createFPacVar(nAbsolutePosition1Based, csEditMask);
	}
	protected Var workingX(int nAbsolutePosition0Based, String csEditMask)
	{
		int nAbsolutePosition1Based = nAbsolutePosition0Based + 1;
		return working.createFPacVar(nAbsolutePosition1Based, csEditMask);
	}
	

	//protected FPacTransfer transfer = null;
	
//	protected VarFPacUndef buffer(FPacFileDescriptor fd, int nAbsolutePosition)
//	{
//		return new VarFPacNoTypeUndef(fd.getFPacVarManager(), fd.getVarBuffer(), nAbsolutePosition);
//	}
//	
	protected Var buffer(FPacFileDescriptor fd, int nAbsolutePosition1Based, int nBufferLength)
	{
		return fd.createFPacVarRaw(nAbsolutePosition1Based, nBufferLength);
	}
	protected Var bufferX(FPacFileDescriptor fd, int nAbsolutePosition1Based, int nBufferLength)
	{
		return fd.createFPacVarRaw(nAbsolutePosition1Based, nBufferLength);
	}
	
	protected VarFPacRawLengthUndef buffer(FPacFileDescriptor fd, int nAbsolutePosition1Based)
	{
		return new VarFPacRawLengthUndef(fd.getFPacVarManager(), fd.getVarBuffer(), nAbsolutePosition1Based);
	}
	
	protected VarFPacRawLengthUndef bufferX(FPacFileDescriptor fd, int nAbsolutePosition1Based)
	{
		return new VarFPacRawLengthUndef(fd.getFPacVarManager(), fd.getVarBuffer(), nAbsolutePosition1Based);
	}
	
	protected Var bufferC(FPacFileDescriptor fd, int nAbsolutePosition1Based, int nBufferLength)
	{
		return fd.createFPacVarAlphaNum(nAbsolutePosition1Based, nBufferLength);
	}
	
	protected VarFPacLengthUndef bufferC(FPacFileDescriptor fd, int nAbsolutePosition1Based)
	{
		return new VarFPacAlphaNumLengthUndef(fd.getFPacVarManager(), fd.getVarBuffer(), nAbsolutePosition1Based);
	}

	protected Var bufferP(FPacFileDescriptor fd, int nAbsolutePosition1Based, int nBufferLength)
	{
		return fd.createFPacVarNumIntSignComp3(nAbsolutePosition1Based, nBufferLength);
	}
	
	protected Var bufferB(FPacFileDescriptor fd, int nAbsolutePosition1Based, int nBufferLength)
	{
		return fd.createFPacVarNumSignComp4(nAbsolutePosition1Based, nBufferLength);
	}
		
	protected VarFPacLengthUndef bufferP(FPacFileDescriptor fd, int nAbsolutePosition1Based)
	{
		return new VarFPacNumIntSignComp3LengthUndef(fd.getFPacVarManager(), fd.getVarBuffer(), nAbsolutePosition1Based);		
	}
	
	protected void move(VarFPacLengthUndef vSource, Var varDest)
	{
		Var varSource = vSource.createVar(varDest.getBodySize());
		move(varSource, varDest);
	}
	
	protected void move(Var varSource, VarFPacLengthUndef varDestUndef)
	{
//		Var vDest = vDestUndef.createVar();
//		return move(vSource, vDest);
		int nLengthSource = varDestUndef.getParamLength(varSource);
		//int nLengthSource = varSource.getLength();
		Var varDest = varDestUndef.createVar(nLengthSource);
		move(varSource, varDest);

	}

	protected void move(int nValue, VarFPacLengthUndef vDestUndef)
	{
		int nLengthSource = vDestUndef.getParamLength(nValue);
		Var vDest = vDestUndef.createVar(nLengthSource); 
		move(nValue, vDest);
	}
	
	protected void move(String csValue, VarFPacLengthUndef vDestUndef)
	{
		int nLengthSource = vDestUndef.getParamLength(csValue);
		Var vDest = vDestUndef.createVar(nLengthSource); 
		move(csValue, vDest);
	}
	
	protected void move(VarFPacRaw varSource, VarFPacRawLengthUndef vDestUndef)
	{	
		int nLengthSource = vDestUndef.getParamLength(varSource);
		//int nLengthSource = varSource.getLength();
		Var vDest = vDestUndef.createVar(nLengthSource);
		move(varSource, vDest);
	}
	
	protected BaseProgram move(VarFPacRaw varSource, VarFPacRaw varDest)
	{
		varDest.copy(varSource);
		return this;
	}	

	private String getCurrentDataJJMMAA()
	{
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy");
		String cs = formatter.format(date);
		
		return cs;
	}
	
	protected String Date()
	{
		return m_StartDate;
	}
	
	protected String CDate()
	{
		return getCurrentDataJJMMAA();
	}
	
	private String getCurrentTimeHHMMSS()
	{
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("HH.mm.ss");
		String cs = formatter.format(date);
		
		return cs;
	}
	
	protected String Time()
	{
		return m_StartTime;
	}
	
	protected String CTime()
	{
		return getCurrentTimeHHMMSS();
	}
	

//	protected void transfer(Var varSource, VarFPacLengthUndef vDestUndef)
//	{
//		FPacTransferFromVar transferer = new FPacTransferFromVar(varSource);
//		transferer.to(vDestUndef);
//	}
//	
//	protected void transfer(String csSource, VarFPacLengthUndef vDestUndef)
//	{
//		FPacTransferFromString transferer = new FPacTransferFromString(csSource);
//		transferer.to(vDestUndef);
//	}
//
//	protected void transfer(int nSource, VarFPacLengthUndef vDestUndef)
//	{
//		FPacTransferFromInt transferer = new FPacTransferFromInt(nSource);
//		transferer.to(vDestUndef);
//	}

	protected abstract int first();
	protected abstract int normal();
	protected abstract int last();
	
	protected final static int NEXT=-10;
	protected final static int FIRST=-9;
	protected final static int NORMAL=-8;
	protected final static int LAST=-7;
	protected final static int END=-6;
	protected final static int ABEND=-6;	
	
	static private final int NB_X = 100;
	
	protected int X[] = null;	 // Array of indexes
	protected PackWorking working  = null;
	protected static final byte CR = 13;
	private String m_StartDate = null;	
	private String m_StartTime = null;
	protected int index = 0;
	
	
	protected boolean isGreater(VarFPacLengthUndef undef, int i)
	{
		// TODO fake method
		return false;
	}
	/**
	 * @param undef
	 */
	protected void inc(int n, VarFPacLengthUndef undef)
	{
		// TODO Fake method
		
	}	
	protected boolean isEof(BaseFileDescriptor fd)
	{
		if(fd != null)
			return fd.isEOF();
		return true;		
	}	
		
	private boolean isPackedHexa(String cs)	// Autodermine if the cs value is a packed one or a string described in hexadecimal codes
	{
		for(int n=0; n<cs.length()-1; n++)
		{
			char c = cs.charAt(n);
			if(c < '0' || c > '9')
				return false;
		}
		char c = cs.charAt(cs.length()-1);
		if(c == 'D' || c == 'C')
			return true;
		return false;
	}
	
	protected String Hexa(String cs)
	{
		return hexa(cs);
	}
	
	protected String hexa(String cs)
	{
		if(!isPackedHexa(cs))
		{
			StringBuffer csOut = new StringBuffer();
			for(int n=0; n<cs.length(); n+=2)
			{
				int nHigh = StringUtil.convertHexDigitAsInt(cs.charAt(n));
				int nLow = StringUtil.convertHexDigitAsInt(cs.charAt(n+1));
				int nByteEbcdic = (nHigh * 16) + nLow;
				int nAscii = AsciiEbcdicConverter.getAsAscii(nByteEbcdic);
				char cAscii = (char)nAscii;
				csOut.append(cAscii);
			}
			return csOut.toString();
		}
		else	// Packed Hexa
		{
			String csOut = new String();
			for(int n=0; n<cs.length(); n++)
			{
				char c = cs.charAt(n);
				if(c >= '0' && c <= '9')
				{
					csOut += c;
				}
				else if(c >= 'D' && n == cs.length()-1)
				{
					csOut = "-" + csOut;
				}
			}
			return csOut;
		}		
	}
	
	/**
	 * @param i
	 * @param var
	 */
	protected void movePacked(int i, Var var)
	{
		move(i, var);
	}	
}
