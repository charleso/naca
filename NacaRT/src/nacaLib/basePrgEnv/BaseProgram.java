/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 *
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 * @author U930DI
 *
 * To change the template for th generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.basePrgEnv;


import java.text.SimpleDateFormat;
import java.util.Date;

import jlib.log.Log;
import jlib.misc.ConsoleInput;
import jlib.misc.JVMReturnCodeManager;
import jlib.misc.NumberParser;
import nacaLib.base.CJMapObject;
import nacaLib.base.JmxGeneralStat;
import nacaLib.exceptions.CExitException;
import nacaLib.exceptions.CGotoException;
import nacaLib.exceptions.CStopRunException;
import nacaLib.mapSupport.Map;
import nacaLib.mathSupport.MathAdd;
import nacaLib.mathSupport.MathBase;
import nacaLib.mathSupport.MathDivide;
import nacaLib.mathSupport.MathPow;
import nacaLib.mathSupport.MathMultiply;
import nacaLib.mathSupport.MathSubtract;
import nacaLib.misc.KeyPressed;
import nacaLib.misc.LogDisplay;
import nacaLib.misc.NacaToolBox;
import nacaLib.misc.Pointer;
import nacaLib.misc.StringAsciiEbcdicUtil;
import nacaLib.program.CCallProgram;
import nacaLib.program.CJMapRunnable;
import nacaLib.program.CopyReplacing;
import nacaLib.program.Paragraph;
import nacaLib.program.Section;
import nacaLib.programPool.SharedProgramInstanceData;
import nacaLib.programPool.SharedProgramInstanceDataCatalog;
import nacaLib.sqlSupport.CSQLStatus;
import nacaLib.sqlSupport.SQL;
import nacaLib.sqlSupport.SQLCall;
import nacaLib.sqlSupport.SQLCursor;
import nacaLib.sqlSupport.SQLCursorFetch;
import nacaLib.sqlSupport.SQLCursorOperation;
import nacaLib.stringSupport.Concat;
import nacaLib.stringSupport.InspectReplacing;
import nacaLib.stringSupport.InspectTallying;
import nacaLib.stringSupport.Unstring;
import nacaLib.tempCache.TempCache;
import nacaLib.tempCache.TempCacheLocator;
import nacaLib.varEx.CobolConstant;
import nacaLib.varEx.CobolConstantHighValue;
import nacaLib.varEx.CobolConstantLowValue;
import nacaLib.varEx.CobolConstantSpace;
import nacaLib.varEx.CobolConstantZero;
import nacaLib.varEx.ComparisonMode;
import nacaLib.varEx.Cond;
import nacaLib.varEx.Console;
import nacaLib.varEx.DataSection;
import nacaLib.varEx.Edit;
import nacaLib.varEx.FileDescriptor;
import nacaLib.varEx.Form;
import nacaLib.varEx.InitializeCache;
import nacaLib.varEx.InternalCharBuffer;
import nacaLib.varEx.MapRedefine;
import nacaLib.varEx.MoveCorrespondingEntryManager;
import nacaLib.varEx.Output;
import nacaLib.varEx.RecordDescriptorAtEnd;
import nacaLib.varEx.SortCommand;
import nacaLib.varEx.SortDescriptor;
import nacaLib.varEx.SortParagHandler;
import nacaLib.varEx.Var;
import nacaLib.varEx.VarAndEdit;
import nacaLib.varEx.VarBase;
import nacaLib.varEx.VarEnumerator;
import nacaLib.varEx.VarTypeId;


/**
 * @author PJD
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class BaseProgram extends CJMapObject
{
	protected NacaToolBox tools = null;
	
	/**Method: Constructor
	 * Main entry point of a program
	 * @param:
	 * @return:
	 */
	public BaseProgram(BaseProgramManagerFactory programManagerFactory)
	{
		super();
		
		initNames();
		
		boolean bInheritedSharedProgramInstanceData = true;
		SharedProgramInstanceData sharedProgramInstanceData = SharedProgramInstanceDataCatalog.getSharedProgramInstanceData(m_csSimpleName);
		if(sharedProgramInstanceData == null)
		{
			sharedProgramInstanceData = new SharedProgramInstanceData();
			SharedProgramInstanceDataCatalog.putSharedProgramInstanceData(m_csSimpleName, sharedProgramInstanceData);
			bInheritedSharedProgramInstanceData = false;
		}
		if(programManagerFactory != null)
			m_BaseProgramManager = programManagerFactory.createProgramManager(this, sharedProgramInstanceData, bInheritedSharedProgramInstanceData);
		
		tools = new NacaToolBox(m_BaseProgramManager) ;
		if(BaseResourceManager.getUsingJmx())
			JmxGeneralStat.incNbProgramInstanceLoaded(1);
	}
	
	

	public void finalize()
	{
		//Log.logNormal("Program finalized: " +toString());
		JmxGeneralStat.incNbProgramInstanceLoaded(-1);
	}

	/**Method: getProgramManager
	 * 
	 * @param:
	 * @return: Internal Prorgram manager Objet; A program has 2 sides:
	 * 	- a public one, that is accessible to applications programs
	 * 	- an internal one that i sused only internally by the library. 
	 * This methods gives 
	 * 	access to this internal manager
	 * <b>This method is not intended to be used by an application</b> 
	 */ 
	/**
	 * @return
	 */
	public BaseProgramManager getProgramManager()
	{
		return m_BaseProgramManager ; 
	}
	
	// Access
	protected BaseProgramManager m_BaseProgramManager = null;	// Program manager object
	//private ProgramManager;
	
	//protected VarSectionDeclaration declare = null;	// Declare Object used for data section and variable declaration inside an application  
	
	// http://www.nwrdc.fsu.edu/contents_support_cics_progguide_general.html#dfhcommarea
	// CICS LINK: COMMAREA is passed by ref: The linked prog can return values by the Commearea
	// CICS XCTL: COMMAREA is passed by value
	// CICS RETURN: COMMAREA is passed by value
	
	// Creation
	
	
	// Program code
	/**Method: procedureDivision
	 * Virtual method that ca be derived to describe formally an entry point for am application program, that is not at in the first paragraph
	 * Commment <b>It is not mandatory to derived in an app, but it makes life easier</b> 
	 * @param:
	 * @return:
	 */
	public void procedureDivision()	// Virtual that can be derived
	{
	}
	
	/**Method: Coin call
	 * Declare a sub-program to call; Used internally
	 * @param: IN Class classPrgToCall: Gives the java class of the sub program to call. It must itself be derived form Program.
	 * @return: CCallProgram: A internal object, that is used to define a program to be called later
	 * @see call(String )
	 * <b>Not intended to be used by applications directly, but they should use call(String) instead</b>
	 */
	protected CCallProgram call(Class<?> classPrgToCall)
	{
		if(IsSTCheck)
			Log.logFineDebug("call_Class:" + classPrgToCall.getName());
		
		CCallProgram call = new CCallProgram(m_BaseProgramManager.getEnv(), classPrgToCall);
	
		call.setProgramLoader(m_BaseProgramManager.getProgramLoader());
		return call ;
	}	
	
	/**Method: call
	 * Declare a sub-program to call
	 * @param: IN String csPrgClassName: Program name to call 
	 * @return: CCallProgram: A internal object, that is used to define a program to be called later
	 */
	protected CCallProgram call(String csPrgClassName) // temporary function, until all class are available for CALL
	{
		if(IsSTCheck)
			Log.logFineDebug("call_cs:" + csPrgClassName);
		
		CCallProgram call = new CCallProgram(m_BaseProgramManager.getEnv(), csPrgClassName);
		call.setProgramLoader(m_BaseProgramManager.getProgramLoader());
		return call ;
	}

		
	/**Method: isEqual
	 * return true if the 2 variables' value are equal. 
	 * 	 * @param: IN Var var1: 1st variable to compare 
	 * @param: IN Var var2: 2nd Variable to compare
	 * @return: true if var1's value == var2's value, false otherwise.
	 * The variables can by of different type. A conversion can be done in order to have comparable values. 
	 * The comparison is done in Unicode. 
	 */
	protected boolean isEqual(VarAndEdit var1, VarAndEdit var2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isEqual_V_V:" + var1.getSTCheckValue() + "/" + var2.getSTCheckValue());
		if(var1.m_varTypeId == var2.m_varTypeId && var1.m_varTypeId <= VarTypeId.MaxStandardCobolVarId)	// Same type
		{
			if(var1.m_varDef.getBodyLength() == var2.m_varDef.getBodyLength()) 
			{
				boolean b = var1.m_varDef.isEqualWithSameTypeTo(var1.m_bufferPos, var2.m_varDef, var2.m_bufferPos);
				return b;
			}
		}
		
		int n = var1.compareTo(ComparisonMode.Unicode, var2);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1, var2);
		if(n == 0)
			return true;
		return false;
	}
	
	// Tests
	/**Method: isEqual
	 * return true if the 2 variables' value are equal. 
	 * 	 * @param: IN int a: 1st variable to compare 
	 * @param: IN int b: 2nd Variable to compare
	 * @return: true if a == b, false otherwise.
	 */
	protected boolean isEqual(int a, int b)
	{
		if(IsSTCheck)
			Log.logFineDebug("isEqual_n_n:" + a + "/" + b);
		return a == b;
	}
	
	/**Method: isEqual
	 * return true if the 2 variables' value are equal. 
	 * 	 * @param: IN String a: 1st variable to compare 
	 * @param: IN int b: 2nd Variable to compare
	 * @return: true if the integer value of a  == b, false otherwise. 
	 */
	protected boolean isEqual(String a, int b)
	{
		if(IsSTCheck)
			Log.logFineDebug("isEqual_cs_n:" + a + "/" + b);
		return NumberParser.getAsInt(a) == b;
	}


	/**Method: isEqual
	 * Returns true if the 2 integers in param are equals
	 * @param: IN MathBase a: Contains the result of a previous math operations; it will be evaluated as an integer (can be rounded) 
	 * @param: int b: int to compare
	 * @return: true if they are equals, false otherwise
	 */
	protected boolean isEqual(MathBase a, int b)
	{
		if(IsSTCheck)
			Log.logFineDebug("isEqual_M_n:" + a.getSTCheckValue() + "/" + b);
		int n = a.m_d.intValue() ;
		return n == b;
	}

	/**Method: isEqual
	 * Returns true if the 2 integers in param are equals
	 * @param: IN MathBase a: Contains the result of a previous math operations; it will be evaluated as an integer (can be rounded) 
	 * @param: int b: Var to compare; it is comverted internally in an int
	 * @return: true if they are equals, false otherwise
	 */
	protected boolean isEqual(MathBase a, Var b)
	{
		if(IsSTCheck)
			Log.logFineDebug("isEqual_M_V" + a.getSTCheckValue() + "/" + b.getSTCheckValue());
		int n = a.m_d.intValue() ;
		int m = b.getInt() ;
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(b);

		return n == m;
	}
	
	/**Method: is
	 * Condition value negative evaluation
	 * @param: IN Cond cond
	 * @return: true if the Cond evaluates as true
	 * @see Class Cond
	 */
	protected boolean is(Cond cond)
	{
		if(IsSTCheck)
			Log.logFineDebug("is_Cond:" + cond.getSTCheckValue());
		return cond.is();
	}

	/**Method: is
	 * Condition value negative evaluation
	 * @param: IN Cond cond 
	 * @return: true if the Cond evaluates as false
	 * @ see Class Cond
	 */
	protected boolean isNot(Cond cond)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNot_Cond:" + cond.getSTCheckValue());
		return !cond.is();
	}
	
	/**Method: is
	 * Variable value boolean comparison
	 * @param: IN Variable v
	 * @return: true if the variable is true. 
	 * @ see Class Cond
	 */
	protected boolean isNot(Var v)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNot_v:" + v.getSTCheckValue());
		return v.compareTo(false);
	}
	
	/**Method: isLowValue
	 * return true if the Var contains only low value bytes.
	 * @param: IN Var var
	 * @return: true if all bytes inside the var are low value; false otherwise
	 */
	protected boolean isLowValue(VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLowValue_V:" + var.getSTCheckValue());

		return isAll(var, CobolConstant.LowValue.getValue());
	}

	/**Method: isLowValue
	 * return true if the String contains only low value bytes.
	 * @param: IN String cs
	 * @return: true if all bytes inside the string are low value; false otherwise
	 */
	protected boolean isLowValue(String cs)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLowValue_cs:" + cs);
		return isAll(cs, CobolConstant.LowValue.getValue());
	}

	/**Method: isNotLowValue
	 * Opposite result than isLowValue
	 * @param:
	 * @return:
	 * @see isLowValue
	 */
	protected boolean isNotLowValue(VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNotLowValue_V:" + var.getSTCheckValue());

		return !isAll(var, CobolConstant.LowValue.getValue());
	}
	
	/**Method: isNotLowValue
	 * Opposite result than isLowValue
	 * @param:
	 * @return:
	 * @see isLowValue
	 */
	protected boolean isNotLowValue(String cs)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNotLowValue_cs:" + cs);
	
		return !isAll(cs, CobolConstant.LowValue.getValue());
	}
	
	/**Method: isLowValue
	 * return true if the String contains only low value bytes.
	 * @param: IN String cs
	 * @return: true if all bytes inside the string are high value; false otherwise
	 */	
	protected boolean isHighValue(VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isHighValue_V:" + var.getSTCheckValue());
		return isAll(var, CobolConstant.HighValue.getValue());	
	}
	
	/**Method: isHighValue
	 * return true if the String contains only high value bytes.
	 * @param: IN String cs
	 * @return: true if all bytes insed the string are high value; false otherwise
	 */
	protected boolean isHighValue(String cs)
	{
		if(IsSTCheck)
			Log.logFineDebug("isHighValue_cs:" + cs);
		return isAll(cs, CobolConstant.HighValue.getValue());
	}
	

	/**Method: isNotHighValue
	 * Opposite result than isHighValue
	 * @param:
	 * @return:
	 * @see isHighValue
	 */
	protected boolean isNotHighValue(VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNotHighValue_V:" + var.getSTCheckValue());		
		return !isAll(var, CobolConstant.HighValue.getValue());	
	}
	
	/**Method: isNotHighValue
	 * Opposite result than isHighValue
	 * @param:
	 * @return:
	 * @see isHighValue
	 */
	protected boolean isNotHighValue(String cs)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNotHighValue_cs:" + cs);	
		return !isAll(cs, CobolConstant.HighValue.getValue());
	}
		
	/**Method: isHighValue
	 * @param: IN VarAndEdit var
	 * @return: true if the var contains only space chars; false otherwise
	 * If the var is an Edit, then only, it's textual value is taken in account
	 */
	protected boolean isSpace(VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isSpace_V:" + var.getSTCheckValue());
		return isAll(var, CobolConstant.Space.getValue());
	}
	
	/**Method: isHighValue
	 * @param: IN String cs
	 * @return: true if the string contains only space chars; false otherwise
	 */
	protected boolean isSpace(String cs)
	{
		if(IsSTCheck)
			Log.logFineDebug("isSpace_cs:" + cs);
		return isAll(cs, CobolConstant.Space.getValue());
	}

	
	/**Method: isHighValue
	 * @param: IN VarAndEdit var
	 * @return: false if the var contains only space chars; true otherwise
	 * If the var is an Edit, then only, it's textual value is taken in account
	 */
	protected boolean isNotSpace(VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNotSpace_V:" + var.getSTCheckValue());
		return !isAll(var, CobolConstant.Space.getValue());
	}
	
	/**Method: isHighValue
	 * @param: IN String cs
	 * @return: false if the string contains only space chars; true otherwise
	 */	
	protected boolean isNotSpace(String cs)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNotSpace_cs:" + cs);
		return !isAll(cs, CobolConstant.Space.getValue());
	}
	
	/**Method: isZero
	 * return true if the Var contains only 0 chars
	 * @param: IN var var
	 * @return:
	 */	
	protected boolean isZero(VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isZero_V:" + var.getSTCheckValue());

		if (var == null)
		{
			return true ;
		}
		if (var.isNumeric())
		{
			double n = var.getDouble() ;
			if(m_bUsedTempVarOrCStr)
				m_tempCache.resetTempIndex(var);
			return n == 0 ;
		}
		else
		{
			return isAll(var, '0') ;
		}
	}
	
	protected boolean isZero(String cs)
	{
		if(IsSTCheck)
			Log.logFineDebug("isZero_cs:" + cs);
		return BaseProgram.isAll(cs, '0') ;
	}


	protected boolean isZero(int n)
	{
		if(IsSTCheck)
			Log.logFineDebug("isZero_n:" + n);
		return n == 0 ; 
	}

	protected boolean isNotZero(String cs)
	{
		return !isZero(cs); 
	}

	/**Method: isZero
	 * return false if the Var contains only 0 chars
	 * @param: IN var var
	 * @return:
	 */	
	protected boolean isNotZero(VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNotZero_V:" + var.getSTCheckValue());

		return !isZero(var);
	}

	/**Method: isZero
	 * return true if the int equals 0
	 * @param: IN var var
	 * @return:
	 */	
	protected boolean isNotZero(int n)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNotZero_n:" + n);

		if(n != 0)
			return true;
		return false;
	}
	
	/**Method: isAll
	 * return true if the var's value contains only the same char cPattern
	 * @param: IN var var
	 * @param: IN char cPattern 
	 * @return:
	 */	
	protected boolean isAll(VarAndEdit var, char cPattern)
	{
		if(IsSTCheck)
			Log.logFineDebug("isAll_V_c:" + var.getSTCheckValue() + "/" + cPattern);

		String sValue = var.getString();
		boolean b = isAll(sValue, cPattern);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		return b;
	}
	
	protected boolean isAll(VarAndEdit var, CobolConstantZero cobolConstant)
	{
		if(IsSTCheck)
			Log.logFineDebug("isAll_V_cst:" + var.getSTCheckValue() + "/" + cobolConstant.getSTCheckValue());

		String sValue = var.getString();
		return isAll(sValue, cobolConstant.getValue());
	}
	
	/**Method: isNotAll
	 * return true if the var's value contains only occurences of the same string csPattern
	 * @param: IN Var var
	 * @param: IN String csPattern 
	 * @return:
	 */
	protected boolean isNotAll(VarAndEdit var, String csPattern)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNotAll_V_cs:" + var.getSTCheckValue() + "/" + csPattern);

		return !isAll(var, csPattern) ;
	}
	
	/**Method: isNotAll
	 * return true if the var's value contains only occurences of the same string csPattern
	 * @param: IN Var var
	 * @param: IN String csPattern 
	 * @return:
	 */
//	protected boolean isNotAll(Var var, String csPattern)
//	{
//		return !isAll(var, csPattern) ;
//	}
	
		/**Method: isAll
	 * return true if the var's value contains only occurences of the same string csPattern
	 * @param: IN Var var
	 * @param: IN String csPattern 
	 * @return:
	 */
	protected boolean isAll(VarAndEdit var, String csPattern)
	{
		if(IsSTCheck)
			Log.logFineDebug("isAll_V_cs::" + var.getSTCheckValue() + "/" + csPattern);

		int nPatternLg = csPattern.length();
		if(nPatternLg > 1)
		{	
			String csValue = var.getString();
			int nValueLg = csValue.length();
			
			int nStart = 0;
			int nNbLoop = nValueLg / nPatternLg;
			while(nNbLoop > 0)
			{
				if(!csValue.startsWith(csPattern, nStart))
					return false;
				nStart += nPatternLg;
				nNbLoop--;
			}
			String csEnd = csValue.substring(nStart, nValueLg);
			if(m_bUsedTempVarOrCStr)
				m_tempCache.resetTempIndex(var);
			if(!csPattern.startsWith(csEnd))
				return false;
			return true;
		}
		return isAll(var, csPattern.charAt(0));
	}
	
	protected boolean isAll(VarAndEdit var, CobolConstantLowValue cobolConstant)
	{
		if(IsSTCheck)
			Log.logFineDebug("isAll_V_cst:" + var.getSTCheckValue() + "/" + cobolConstant.getSTCheckValue());

		String sValue = var.getString();
		return isAll(sValue, cobolConstant.getValue());
	}
	
	

	protected boolean isAll(VarAndEdit var, CobolConstantHighValue cobolConstant)
	{
		if(IsSTCheck)
			Log.logFineDebug("isAll_V_cst:" + var.getSTCheckValue() + "/" + cobolConstant.getSTCheckValue());

		String sValue = var.getString();
		return isAll(sValue, cobolConstant.getValue());
	}

	protected boolean isAll(VarAndEdit var, CobolConstantSpace cobolConstant)
	{
		if(IsSTCheck)
			Log.logFineDebug("isAll_V_cst:" + var.getSTCheckValue() + "/" + cobolConstant.getSTCheckValue());

		String sValue = var.getString();
		return isAll(sValue, cobolConstant.getValue());
	}

	/**Method: isAll
	 * return true if the string's value contains only the same char cPattern
	 * @param: IN String csValue
	 * @param: IN char cPattern 
	 * @return:
	 */
	public static boolean isAll(String csValue, char cPattern)
	{
//		if(isLog.logFineTrace())
//			Log.logFineTrace("isAll_cs_c:" + csValue + "/" + cPattern);
		
		int nValueLg = csValue.length();
		
		for(int n=0; n<nValueLg; n++)
		{
			char c = csValue.charAt(n);
			if(c != cPattern)
				return false;
		}
		return true;
	}
	
	/**Method: isAll
	 * return true if the var's value contains only occurences of the same string contained into varPattern
	 * @param: IN Var var
	 * @param: IN Var varPattern 
	 * @return:
	 */
	protected boolean isAll(Var var, Var varPattern)
	{
		if(IsSTCheck)
			Log.logFineDebug("isAll_V_V:" + var.getSTCheckValue()+ "/" + varPattern.getSTCheckValue());

		String s = varPattern.getString();
		return isAll(var, s);
	}
	
	/**Method: isAll
	 * return true if the var's value contains only occurences of the same string csPattern
	 * @param: IN Var var
	 * @param: IN String csPattern 
	 * @return:
	 */
	protected boolean isAll(Var var, String csPattern)
	{
		if(IsSTCheck)
			Log.logFineDebug("isAll_V_cs:" + var.getSTCheckValue()+ "/" + csPattern);
		
		int nPatternLg = csPattern.length();
		if(nPatternLg > 1)
		{	
			String csValue = var.getString();
			int nValueLg = csValue.length();
			
			int nStart = 0;
			int nNbLoop = nValueLg / nPatternLg;
			while(nNbLoop > 0)
			{
				if(!csValue.startsWith(csPattern, nStart))
					return false;
				nStart += nPatternLg;
				nNbLoop--;
			}
			String csEnd = csValue.substring(nStart, nValueLg);
			if(m_bUsedTempVarOrCStr)
				m_tempCache.resetTempIndex(var);
			if(!csPattern.startsWith(csEnd))
				return false;
			return true;
		}
		return isAll(var, csPattern.charAt(0));
	}
	
	// Comparisons
	
	
	/**Method: isDifferent
	 * return true if the 2 var's value are different
	 * @param: IN Var var1
	 * @param: IN Var var2
	 * @return:
	 */	
	protected boolean isDifferent(VarAndEdit var1, VarAndEdit var2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isDifferent_V_V:" + var1.getSTCheckValue()+ "/" + var2.getSTCheckValue());

		return !isEqual(var1, var2);
	}	


	/**Method: isLess
	 * return true if var1's value < var2's value 
	 * @param: IN Var var1
	 * @param: IN Var var2
	 * @return:
	 */	
	protected boolean isLess(VarAndEdit var1, VarAndEdit var2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLess_V_V:" + var1.getSTCheckValue()+ "/" + var2.getSTCheckValue());
		int n = var1.compareTo(ComparisonMode.UnicodeOrEbcdic, var2);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1, var2);
		if(n < 0)
			return true;
		return false;
	}
	
	/**Method: isLessInEbcdic
	 * @param: IN VarAndEdit var1
	 * @param: IN VarAndEdit var2
	 * @return: true if var1's value < var2's value, false otherwise. The comparison is done using ebcdic ordering
	 * Warning: This method is available only for ebcdic compatibility. It should not be used except when dealing with not ascii convertered data
	 * If Var1 or var2 are Edit or dervied from Edit, then only the textual part of the edit is taken in account   
	 */	
	protected boolean isLessInEbcdic(VarAndEdit var1, VarAndEdit var2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLessInEbcdic_V_V:" + var1.getSTCheckValue()+ "/" + var2.getSTCheckValue());
		int n = var1.compareTo(ComparisonMode.Ebcdic, var2);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1, var2);
		if(n < 0)
			return true;
		return false;
	}
	
	/**Method: isLessOrEqual
	 * @param: IN VarAndEdit var1
	 * @param: IN VarAndEdit var2
	 * @return: true if var1's value <= var2's value, false otherwise.
	 * If Var1 or var2 are Edit or dervied from Edit, then only the textual part of the edit is taken in account	 */
	protected boolean isLessOrEqual(VarAndEdit var1, VarAndEdit var2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLessOrEqual_V_V:" + var1.getSTCheckValue()+ "/" + var2.getSTCheckValue());

		int n = var1.compareTo(ComparisonMode.UnicodeOrEbcdic, var2);
		
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1, var2);
		if(n <= 0)
			return true;
		return false;
	}
	
	/**Method: isLessOrEqualInEbcdic
	 * @param: IN VarAndEdit var1
	 * @param: IN VarAndEdit var2
	 * @return: true if var1's value <= var2's value, false otherwise. The comparison is done using ebcdic ordering
	 * Warning: This method is available only for ebcdic compatibility. It should not be used except when dealing with not ascii convertered data
	 * If Var1 or var2 are Edit or dervied from Edit, then only the textual part of the edit is taken in account   
	 */	
	protected boolean isLessOrEqualInEbcdic(VarAndEdit var1, VarAndEdit var2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLessOrEqualInEbcdic_V_V:" + var1.getSTCheckValue()+ "/" + var2.getSTCheckValue());

		int n = var1.compareTo(ComparisonMode.Ebcdic, var2);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1, var2);
		if(n <= 0)
			return true;
		return false;
	}
	
	protected boolean isLessOrEqual(String cs, int n)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLessOrEqual_cs_n:" + cs+ "/" + n);

		int v = Integer.parseInt(cs) ;
		if(v <= n)
			return true;
		return false;
	}

	protected boolean isLessOrEqual(int n1, int n2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLessOrEqual_n_n:" + n1 + "/" + n2);

		return n1 <= n2 ;  
	}

	/**Method: isGreater
	 * @param: IN VarAndEdit var1
	 * @param: IN VarAndEdit var2
	 * @return: true if var1's value > var2's value, false otherwise
	 * If Var1 or var2 are Edit or dervied from Edit, then only the textual part of the edit is taken in account   
	 */	
	protected boolean isGreater(VarAndEdit var1, VarAndEdit var2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreater_V_V:" + var1.getSTCheckValue()+ "/" + var2.getSTCheckValue());

		int n = var1.compareTo(ComparisonMode.UnicodeOrEbcdic, var2);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1, var2);
		if(n > 0)
			return true;
		return false;
	}
	
	/**Method: isGreaterOrEqualInEbcdic
	 * @param: IN VarAndEdit var1
	 * @param: IN VarAndEdit var2
	 * @return: true if var1's value >= var2's value, false otherwise. The comparison is done using ebcdic ordering
	 * Warning: This method is available only for ebcdic compatibility. It should not be used except when dealing with not ascii convertered data
	 * If Var1 or var2 are Edit or dervied from Edit, then only the textual part of the edit is taken in account   
	 */	
	protected boolean isGreaterInEbcdic(VarAndEdit var1, VarAndEdit var2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreaterInEbcdic_V_V:" + var1.getSTCheckValue()+ "/" + var2.getSTCheckValue());

		int n = var1.compareTo(ComparisonMode.Ebcdic, var2);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1, var2);
		if(n > 0)
			return true;
		return false;
	}
	
	/**Method: isGreater
	 * @param: IN VarAndEdit var1
	 * @param: IN VarAndEdit var2
	 * @return: true if var1's value >= var2's value, false otherwise. 
	 * If Var1 or var2 are Edit or dervied from Edit, then only the textual part of the edit is taken in account   
	 */	
	protected boolean isGreater(String cs, int v)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreater_cs_n:" + cs + "/" + v);

		int n = NumberParser.getAsInt(cs) ;
		if(n > v)
			return true;
		return false;
	}
	
	/**Method: isGreaterInEbcdic
	 * @param: IN VarAndEdit var1
	 * @param: IN VarAndEdit var2
	 * @return: true if var1's value > var2's value, false otherwise. The comparison is done using ebcdic ordering
	 * Warning: This method is available only for ebcdic compatibility. It should not be used except when dealing with not ascii convertered data
	 * If Var1 or var2 are Edit or dervied from Edit, then only the textual part of the edit is taken in account   
	 */	
	protected boolean isGreaterInEbcdic(String cs1, String cs2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreaterInEbcdic_cs_cs:" + cs1 + "/" + cs2);
//		int n = cs.compareTo(s) ;
//		if(n > 0)
//			return true;
//		return false;
		return StringAsciiEbcdicUtil.compare(ComparisonMode.Ebcdic, cs1, cs2) > 0;
	}

	protected boolean isGreater(String cs1, String cs2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreater_cs_cs:" + cs1 + "/" + cs2);
//		int n = cs.compareTo(s) ;
//		if(n > 0)
//			return true;
//		return false;
		return StringAsciiEbcdicUtil.compare(ComparisonMode.UnicodeOrEbcdic, cs1, cs2) > 0;
	}


	/**Method: isGreater
	 * return true if var1's value > var2's value 
	 * @param: IN MathBase var1
	 * @param: IN Var var2
	 * @return:
	 */	
	protected boolean isGreater(MathBase math1, Var var2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreater_M_V:" + math1.getSTCheckValue() + "/" + var2.getSTCheckValue());

		int n = math1.compareTo(var2);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var2);
		if(n > 0)
			return true;
		return false;
	}

	/**Method: isGreater
	 * return true if var1's value > nb; var1 is evaluated as an int (can be rounded). 
	 * @param: IN MathBase var1
	 * @param: IN int nb
	 * @return:
	 */	
	protected boolean isGreater(MathBase var1, int nb)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreater_M_n:" + var1.getSTCheckValue() + "/" + nb);

		int n = var1.compareTo(nb);
		if(n > 0)
			return true;
		return false;
	}
	
	/**Method: isGreaterOrEqualInEbcdic
	 * @param: IN VarAndEdit var1
	 * @param: IN VarAndEdit var2
	 * @return: true if var1's value >= var2's value, false otherwise
	 * If Var1 or var2 are Edit or dervied from Edit, then only the textual part of the edit is taken in account   
	 */	
	protected boolean isGreaterOrEqual(VarAndEdit var1, VarAndEdit var2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreaterOrEqual_V_V:" + var1.getSTCheckValue() + "/" + var2.getSTCheckValue());

		int n = var1.compareTo(ComparisonMode.UnicodeOrEbcdic, var2);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1, var2);
		if(n >= 0)
			return true;
		return false;
	}
	
	/**Method: isGreaterOrEqualInEbcdic
	 * @param: IN VarAndEdit var1
	 * @param: IN VarAndEdit var2
	 * @return: true if var1's value >= var2's value, false otherwise. The comparison is done using ebcdic ordering
	 * Warning: This method is available only for ebcdic compatibility. It should not be used except when dealing with not ascii convertered data
	 * If Var1 or var2 are Edit or dervied from Edit, then only the textual part of the edit is taken in account   
	 */	
	protected boolean isGreaterOrEqualInEbcdic(VarAndEdit var1, VarAndEdit var2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreaterOrEqualInEbcdic_V_V:" + var1.getSTCheckValue() + "/" + var2.getSTCheckValue());

		int n = var1.compareTo(ComparisonMode.Ebcdic, var2);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1, var2);
		if(n >= 0)
			return true;
		return false;
	}

	// int <-> math
	protected boolean isDifferent(int nVal, MathBase Math)
	{
		if(IsSTCheck)
			Log.logFineDebug("isDifferent_n_M:" + nVal + "/" + Math.getSTCheckValue());

		int nMath = Math.m_d.intValue() ;
		if(nVal != nMath)
			return true;
		return false;
	}
	
	protected boolean isDifferent(int nVal, int val2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isDifferent_n_n:" + nVal + "/" + val2);

		if(nVal != val2)
			return true;
		return false;
	}

	protected boolean isEqual(int nVal, MathBase Math)
	{
		if(IsSTCheck)
			Log.logFineDebug("isEqual_n_M:" + nVal + "/" + Math.getSTCheckValue());

		int nMath = Math.m_d.intValue() ;
		if(nVal == nMath)
			return true;
		return false;
	}

	protected boolean isLess(int nVal, MathBase Math)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLess_n_M:" + nVal + "/" + Math.getSTCheckValue());

		int nMath = Math.m_d.intValue() ;
		if(nVal < nMath)
			return true;
		return false;
	}	
	
	protected boolean isLess(int nVal, int n)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLess_n_n:" + nVal + "/" + n);

		return nVal < n;
	}	
	
	protected boolean isLessOrEqual(int nVal, MathBase Math)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLessOrEqual_n_M:" + nVal + "/" + Math.getSTCheckValue());

		int nMath = Math.m_d.intValue() ;
		if(nVal <= nMath)
			return true;
		return false;
	}
	
	protected boolean isLessOrEqual(MathBase Math, int nVal)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLessOrEqual_M_n:" + Math.getSTCheckValue() + "/" + nVal);
		int nMath = Math.m_d.intValue() ;
		if(nVal >= nMath)
			return true;
		return false;
	}
	
	protected boolean isGreater(int nVal, MathBase Math)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreater_n_M:" + nVal + "/" + Math.getSTCheckValue());

		int nMath = Math.m_d.intValue() ;
		if(nVal > nMath)
			return true;
		return false;
	}
	protected boolean isGreater(int nVal, int p)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreater_n_n:" + nVal + "/" + p);

		return nVal > p ;
	}
		
	protected boolean isGreaterOrEqual(MathBase Math, int nVal)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreaterOrEqual_M_n:" + Math.getSTCheckValue() + "/" + nVal);

		int nMath = Math.m_d.intValue() ;
		if(nMath >= nVal)
			return true;
		return false;
	}
	
	protected boolean isGreaterOrEqual(String s, int nVal)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreaterOrEqual_cs_n:" + s + "/" + nVal);

		int nMath = NumberParser.getAsInt(s) ;
		if(nMath >= nVal)
			return true;
		return false;
	}
	
	protected boolean isGreaterOrEqual(int n, int nVal)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreaterOrEqual_n_n::" + n + "/" + nVal);

		return (n >= nVal) ;
	}

	protected boolean isGreaterOrEqual(int nVal, MathBase Math)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreaterOrEqual_n_M:" + nVal + "/" + Math.getSTCheckValue());

		int nMath = Math.m_d.intValue() ;
		if(nVal >= nMath)
			return true;
		return false;
	}
	
	// Var <-> Math
	protected boolean isDifferent(Var var1, MathBase Math2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isDifferent_V_M:" + var1.getSTCheckValue() + "/" + Math2.getSTCheckValue());

		int n = var1.compareTo(Math2);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);

		if(n != 0)
			return true;
		return false;
	}

	protected boolean isEqual(Var var1, MathBase Math2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isEqual_V_M:" + var1.getSTCheckValue() + "/" + Math2.getSTCheckValue());

		int n = var1.compareTo(Math2);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);

		if(n == 0)
			return true;
		return false;
	}

	protected boolean isLess(Var var1, MathBase Math2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLess_V_M:" + var1.getSTCheckValue() + "/" + Math2.getSTCheckValue());

		int n = var1.compareTo(Math2);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);

		if(n < 0)
			return true;
		return false;
	}

	protected boolean isLessOrEqual(Var var1, MathBase Math2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLessOrEqual_V_M:" + var1.getSTCheckValue() + "/" + Math2.getSTCheckValue());

		int n = var1.compareTo(Math2);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);

		if(n <= 0)
			return true;
		return false;
	}
	
	protected boolean isGreater(Var var1, MathBase Math2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreater_V_M:" + var1.getSTCheckValue() + "/" + Math2.getSTCheckValue());

		int n = var1.compareTo(Math2);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);

		if(n > 0)
			return true;
		return false;
	}
	
	protected boolean isGreaterOrEqual(Var var1, MathBase Math2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreaterOrEqual_V_M:" + var1.getSTCheckValue() + "/" + Math2.getSTCheckValue());

		int n = var1.compareTo(Math2);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);

		if(n >= 0)
			return true;
		return false;
	}
	
	// Var <-> int
	protected boolean isEqual(VarAndEdit var, int n)
	{
		if(IsSTCheck)
			Log.logFineDebug("isEqual_V_n:" + var.getSTCheckValue() + "/" + n);

		int nResult = var.compareTo(n);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult == 0)
			return true;
		return false;
	}
	
	protected boolean isEqual(int n, VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isEqual_n_V:" + n + "/" + var.getSTCheckValue());

		int nResult = var.compareTo(n);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult == 0)
			return true;
		return false;
	}

	
	protected boolean isDifferent(VarAndEdit var, int n)
	{
		if(IsSTCheck)
			Log.logFineDebug("isDifferent_V_n:" + var.getSTCheckValue() + "/" + n);

		return !isEqual(var, n);
	}		
	protected boolean isDifferent(int n, VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isDifferent_n_V:" + n + "/" + var.getSTCheckValue());

		return !isEqual(n, var);
	}
	
	protected boolean isLess(VarAndEdit var, int n)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLess_V_n:" + var.getSTCheckValue() + "/" + n);

		int nResult = var.compareTo(n);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult < 0)
			return true;
		return false;
	}
	
	protected boolean isLess(int n, VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLess_n_V:" + n + "/" + var.getSTCheckValue());

		int nResult = var.compareTo(n);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult > 0)
			return true;
		return false;
	}
	
	protected boolean isLessOrEqual(VarAndEdit var, int n)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLessOrEqual_V_n:" + var.getSTCheckValue() + "/" + n);
		
		int nResult = var.compareTo(n);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult <= 0)
			return true;
		return false;
	}
	
	protected boolean isLessOrEqual(int n, VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLessOrEqual_n_V:" + n + "/" + var.getSTCheckValue());

		int nResult = var.compareTo(n);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult >= 0)
			return true;
		return false;
	}
	protected boolean isLessOrEqualInEbcdic(int n, VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLessOrEqual_n_V:" + n + "/" + var.getSTCheckValue());

		int nResult = var.compareTo(n);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult >= 0)
			return true;
		return false;
	}
	
	protected boolean isGreater(VarAndEdit var, int n)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreater_V_n:" + var.getSTCheckValue() + "/" + n);		
		
		int nResult = var.compareTo(n);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult > 0)
			return true;
		return false;
	}
	
	protected boolean isGreater(int n, VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreater_n_V:" + n + "/" + var.getSTCheckValue());		
		int nResult = var.compareTo(n);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult < 0)
			return true;
		return false;
	}
	
	protected boolean isGreaterOrEqual(VarAndEdit var, int n)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreaterOrEqual_V_n:" + var.getSTCheckValue() + "/" + n);
		
		int nResult = var.compareTo(n);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult >= 0)
			return true;
		return false;
	}
	
	protected boolean isGreaterOrEqual(int n, VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreaterOrEqual_n_V:" + n + "/" + var.getSTCheckValue());

		int nResult = var.compareTo(n);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult <= 0)
			return true;
		return false;
	}
	protected boolean isGreaterOrEqualInEbcdic(int n, VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreaterOrEqual_n_V:" + n + "/" + var.getSTCheckValue());

		int nResult = var.compareTo(n);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult <= 0)
			return true;
		return false;
	}
	
	// Var <-> double
	protected boolean isEqual(VarAndEdit var1, double d)
	{
		if(IsSTCheck)
			Log.logFineDebug("isEqual_V_d:" + var1.getSTCheckValue() + "/" + d);

		int n = var1.compareTo(d);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);
		if(n == 0)
			return true;
		return false;
	}
	
	protected boolean isDifferent(VarAndEdit var1, double d)
	{
		if(IsSTCheck)
			Log.logFineDebug("isDifferent_V_d:" + var1.getSTCheckValue() + "/" + d);
		
		int n = var1.compareTo(d);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);
		if(n != 0)
			return true;
		return false;
	}

	protected boolean isLess(VarAndEdit var1, double d)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLess_V_d:" + var1.getSTCheckValue() + "/" + d);

		int n = var1.compareTo(d);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);
		if(n < 0)
			return true;
		return false;
	}
	
	protected boolean isLessOrEqual(VarAndEdit var1, double d)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLessOrEqual_V_d:" + var1.getSTCheckValue() + "/" + d);

		int n = var1.compareTo(d);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);
		if(n <= 0)
			return true;
		return false;
	}
	
	protected boolean isGreater(VarAndEdit var1, double d)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreater_V_d:" + var1.getSTCheckValue() + "/" + d);

		int n = var1.compareTo(d);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);
		if(n > 0)
			return true;
		return false;
	}
	
	protected boolean isGreaterOrEqual(VarAndEdit var1, double d)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreaterOrEqual_V_d:" + var1.getSTCheckValue() + "/" + d);

		int n = var1.compareTo(d);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);
		if(n >= 0)
			return true;
		return false;
	}
	
	protected boolean isLess(MathBase math, int val)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLess_M_n:" + math.getSTCheckValue() + "/" + val);

		int n = math.m_d.intValue() ;
		return n < val;
	}
	
	protected boolean isLess(MathBase math, Var var2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLess_M_V:" + math.getSTCheckValue() + "/" + var2.getSTCheckValue());

		double n = math.m_d.doubleValue() ;
		double i = var2.getDouble() ; 
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var2);
		if(n < i)
			return true;
		return false;
	}

	
	// Var <-> String
	protected boolean isEqual(VarAndEdit var, String cs)
	{
		if(IsSTCheck)
			Log.logFineDebug("isEqual_V_cs:" + var.getSTCheckValue() + "/" + cs);

		int nResult = var.compareTo(ComparisonMode.Unicode, cs);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult == 0)
			return true;
		return false;
	}
	protected boolean isEqual(String s1, String s2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isEqual_cs_cs:" + s1 + "/" + s2);

		return s1.equals(s2) ;
	}
	
	protected boolean assertIfDifferent(String cs, VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("assertIfDifferent_cs_V:" + cs + "/" + var.getSTCheckValue());

		int nResult = var.compareTo(ComparisonMode.Unicode, cs);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult == 0)
			return true;
		assertIfFalse(false);
		return false;
	}
	
	protected boolean assertIfDifferent(int n, VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("assertIfDifferent_l_V:" + n + "/" + var.getSTCheckValue());

		if(var.getInt() != n)
		{
			assertIfFalse(false);
			return false;
		}
		return true;
	}
	
	protected boolean isEqual(String cs, VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isEqual_cs_V:" + cs + "/" + var.getSTCheckValue());

		int nResult = var.compareTo(ComparisonMode.Unicode, cs);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult == 0)
			return true;
		return false;
	}
	
	protected boolean isDifferent(VarAndEdit var, String s)
	{
		if(IsSTCheck)
			Log.logFineDebug("isDifferent_V_cs:" + var.getSTCheckValue() + "/" + s);

		return !isEqual(var, s);
	}
	protected boolean isDifferent(String s, VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isDifferent_cs_V:" + s + "/" + var.getSTCheckValue());

		return !isEqual(s, var);
	}
	protected boolean isDifferent(String a, String s)
	{
		if(IsSTCheck)
			Log.logFineDebug("isDifferent_cs_cs:" + a + "/" + s);

		return !a.equals(s) ;
	}

	protected boolean isLess(VarAndEdit var, String cs)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLess_V_cs:" + var.getSTCheckValue() + "/" + cs);

		int nResult = var.compareTo(ComparisonMode.UnicodeOrEbcdic, cs);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult < 0)
			return true;
		return false;
	}
	
	protected boolean isLessInEbcdic(VarAndEdit var, String cs)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLessInEbcdic_V_cs:" + var.getSTCheckValue() + "/" + cs);

		int nResult = var.compareTo(ComparisonMode.Ebcdic, cs);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult < 0)
			return true;
		return false;
	}
	
	protected boolean isLess(String s, int n)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLess_cs_n:" + s + "/" + n);

		int nResult = NumberParser.getAsInt(s) ;
		if(nResult < n)
			return true;
		return false;
	}
	
	protected boolean isLess(String cs, VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLess_cs_V:" + cs + "/" + var.getSTCheckValue());

		int nResult = var.compareTo(ComparisonMode.UnicodeOrEbcdic, cs);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult > 0)
			return true;
		return false;
	}

	protected boolean isLessInEbcdic(String cs, VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLessInEbcdic_cs_V:" + cs + "/" + var.getSTCheckValue());

		int nResult = var.compareTo(ComparisonMode.Ebcdic, cs);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult > 0)
			return true;
		return false;
	}

	protected boolean isLessOrEqual(VarAndEdit var, String cs)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLessOrEqual_V_cs:" + var.getSTCheckValue() + "/" + cs);

		int nResult = var.compareTo(ComparisonMode.UnicodeOrEbcdic, cs);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult <= 0)
			return true;
		return false;
	}
	
	protected boolean isLessOrEqualInEbcdic(VarAndEdit var, String cs)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLessOrEqualInEbcdic_V_cs:" + var.getSTCheckValue() + "/" + cs);

		int nResult = var.compareTo(ComparisonMode.Ebcdic, cs);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult <= 0)
			return true;
		return false;
	}
	
	protected boolean isLessOrEqualInEbcdic(String cs, VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLessOrEqualInEbcdic_cs_V:" + cs + "/" + var.getSTCheckValue());

		int nResult = var.compareTo(ComparisonMode.Ebcdic, cs);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult >= 0)
			return true;
		return false;
	}
	
	protected boolean isLessOrEqual(String cs, VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLessOrEqual_cs_V:" + cs + "/" + var.getSTCheckValue());

		int nResult = var.compareTo(ComparisonMode.UnicodeOrEbcdic, cs);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult >= 0)
			return true;
		return false;
	}
	
	protected boolean isLessOrEqualInEbcdic(String cs1, String cs2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLessOrEqualInEbcdic_cs_cs:" + cs1 + "/" + cs2);

		//return cs.compareTo(s) <= 0 ;
		return StringAsciiEbcdicUtil.compare(ComparisonMode.Ebcdic, cs1, cs2) <= 0;
	}
	
	protected boolean isLessOrEqual(String cs1, String cs2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLessOrEqual_cs_cs:" + cs1 + "/" + cs2);

		//return cs.compareTo(s) <= 0 ;
		return StringAsciiEbcdicUtil.compare(ComparisonMode.UnicodeOrEbcdic, cs1, cs2) <= 0;
	}

	
	protected boolean isLessInEbcdic(String cs1, String cs2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLessInEbcdic_cs_cs:" + cs1 + "/" + cs2);

		//return cs.compareTo(s) < 0 ;
		return StringAsciiEbcdicUtil.compare(ComparisonMode.Ebcdic, cs1, cs2) < 0;
	}
	
	protected boolean isLess(String cs1, String cs2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isLess_cs_cs:" + cs1 + "/" + cs2);

		//return cs.compareTo(s) < 0 ;
		return StringAsciiEbcdicUtil.compare(ComparisonMode.UnicodeOrEbcdic, cs1, cs2) < 0;
	}

	
	protected boolean isGreater(VarAndEdit var, String cs)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreater_V_cs:" + var.getSTCheckValue() + "/" + cs);

		int nResult = var.compareTo(ComparisonMode.UnicodeOrEbcdic, cs);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult > 0)
			return true;
		return false;
	}
	
	protected boolean isGreaterInEbcdic(VarAndEdit var, String cs)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreaterInEbcdicC_V_cs:" + var.getSTCheckValue() + "/" + cs);

		int nResult = var.compareTo(ComparisonMode.Ebcdic, cs);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult > 0)
			return true;
		return false;
	}
	
	protected boolean isGreater(String cs, VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreater_cs_V:" + cs + "/" + var.getSTCheckValue());
		
		int nResult = var.compareTo(ComparisonMode.UnicodeOrEbcdic, cs);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult < 0)
			return true;
		return false;
	}

	protected boolean isGreaterInEbcdic(String cs, VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreaterInEbcdic_cs_V:" + cs + "/" + var.getSTCheckValue());
		
		int nResult = var.compareTo(ComparisonMode.Ebcdic, cs);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult < 0)
			return true;
		return false;
	}


	protected boolean isGreaterOrEqual(VarAndEdit var, String cs)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreaterOrEqual_V_cs:" + var.getSTCheckValue() + "/" + cs);

		int nResult = var.compareTo(ComparisonMode.UnicodeOrEbcdic, cs);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult >= 0)
			return true;
		return false;
	}
	
	protected boolean isGreaterOrEqualInEbcdic(VarAndEdit var, String cs)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreaterOrEqualInEbcdic_V_cs:" + var.getSTCheckValue() + "/" + cs);

		int nResult = var.compareTo(ComparisonMode.Ebcdic, cs);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult >= 0)
			return true;
		return false;
	}
	
	protected boolean isGreaterOrEqual(String cs1, String cs2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreaterOrEqual_cs_cs:" + cs1 + "/" + cs2);

		return StringAsciiEbcdicUtil.compare(ComparisonMode.UnicodeOrEbcdic, cs1, cs2) >= 0 ;
		//return cs.compareTo(s) >= 0 ;
	}

	
	protected boolean isGreaterOrEqualInEbcdic(String cs1, String cs2)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreaterOrEqualInEbcdic_cs_cs:" + cs1 + "/" + cs2);

		return StringAsciiEbcdicUtil.compare(ComparisonMode.Ebcdic, cs1, cs2) >= 0 ;
		//return cs.compareTo(s) >= 0 ;
	}
	
	protected boolean isGreaterOrEqualInEbcdic(String cs, VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreaterOrEqualInEbcdic_cs_V:" + cs + "/" + var.getSTCheckValue());
		
		int nResult = var.compareTo(ComparisonMode.Ebcdic, cs);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult <= 0)
			return true;
		return false;
	}

	protected boolean isGreaterOrEqual(String cs, VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isGreaterOrEqualInEbcdic_cs_V:" + cs + "/" + var.getSTCheckValue());
		
		int nResult = var.compareTo(ComparisonMode.UnicodeOrEbcdic, cs);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		if(nResult <= 0)
			return true;
		return false;
	}
	

	/**Method: add
	 * addition
	 * @param: IN Var var1: 1st operand of the operation; must contain a numeric value, or string that evalues as a numeric value; it can be signed or not; it can contain either an integer or decimal value  
	 * @param: IN Var var2: same remark than var1   
	 * @return: MathAdd: wrapper object containg the sum of the 2 parameters; the maximum precision is kept
	 */
	protected MathAdd add(VarAndEdit var1, VarAndEdit var2)
	{
		if(IsSTCheck)
			Log.logFineDebug("add_V_V:" + var1.getSTCheckValue() + "/" + var2.getSTCheckValue());
		MathAdd math = new MathAdd(var1, var2);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1, var2);
		return math;
	}
	
	protected MathSubtract opposite(MathBase val)
	{
		if(IsSTCheck)
			Log.logFineDebug("opposite_M:" + val.getSTCheckValue());

		MathSubtract math = new MathSubtract(0, val);
		return math;
	}
	protected MathSubtract opposite(VarAndEdit val)
	{
		if(IsSTCheck)
			Log.logFineDebug("opposite_V:" + val.getSTCheckValue());

		MathSubtract math = new MathSubtract(0, val);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(val);
		return math;
	}

	
	protected MathAdd add(String var1, int var2)
	{
		if(IsSTCheck)
			Log.logFineDebug("add_cs_n:" + var1 + "/" + var2);
		MathAdd math = new MathAdd(Integer.parseInt(var1), var2);
		return math;
	}
	
	/**Method: add
	 * addition
	 * @param: IN Var var1: 1st operand of the operation; must contain a numeric value, or string that evalues as a numeric value; it can be signed or not; it can contain either an integer or decimal value  
	 * @param: IN int n: integer ot add to var1   
	 * @return: MathAdd: wrapper object containg the sum of the 2 parameters; the maximum precision is kept
	 */
	protected MathAdd add(VarAndEdit var, int n)
	{
		if(IsSTCheck)
			Log.logFineDebug("add_V_n:" + var.getSTCheckValue() + ":" + n);

		MathAdd math = new MathAdd(var, n);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		return math;
	}
	protected MathAdd add(int n, VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("add_V_n:" + var.getSTCheckValue() + ":" + n);

		MathAdd math = new MathAdd(var, n);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		return math;
	}

	/**Method: add
	 * addition
	 * @param: IN int m: integer ot add to n  
	 * @param: IN int n: integer ot add to m
	 * @return: MathAdd: wrapper object containg the sum of the 2 parameters; the maximum precision is kept
	 */
	protected MathAdd add(int m, int n)
	{
		if(IsSTCheck)
			Log.logFineDebug("add_n_n:" + m + ":" + n);

		MathAdd math = new MathAdd(m, n);
		return math;
	}

	/**Method: add
	 * addition
	 * @param: IN Var var1: operand of the operation; must contain a numeric value, or string that evalues as a numeric value; it can be signed or not; it can contain either an integer or decimal value  
	 * @param: IN double d: floting point value added to var, giving an operand of the operation   
	 * @return: MathAdd: wrapper object containg the sum of the 2 parameters; the maximum precision is kept
	 */
	protected MathAdd add(VarAndEdit var1, double d)
	{
		if(IsSTCheck)
			Log.logFineDebug("add_V_d:" + var1.getSTCheckValue() + ":" + d);

		MathAdd math = new MathAdd(var1, d);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);
		return math;
	}
	
	/**Method: add
	 * addition
	 * @param: IN Var var1: 1st operand of the operation; must contain a numeric value, or string that evalues as a numeric value; it can be signed or not; it can contain either an integer or decimal value  
	 * @param: IN String s: String that must be able to be converted to a numeric value   
	 * @return: MathAdd: wrapper object containg the sum of the 2 parameters; the maximum precision is kept
	 */
	protected MathAdd add(VarAndEdit var, String s)
	{
		if(IsSTCheck)
			Log.logFineDebug("add_V_cs:" + var.getSTCheckValue() + ":" + s);

		MathAdd math= new MathAdd(var, s);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		return math;
	}
	
	/**Method: add
	 * addition
	 * @param: IN Var var1: 1st operand of the operation; must contain a numeric value, or string that evalues as a numeric value; it can be signed or not; it can contain either an integer or decimal value  
	 * @param: IN MathBase mathBase: Math container giving an operand of the operation
	 * @return: MathAdd: wrapper object containg the sum of the 2 parameters; the maximum precision is kept
	 */
	protected MathAdd add(VarAndEdit var, MathBase mathBase)
	{
		if(IsSTCheck)
			Log.logFineDebug("add_V_M:" + var.getSTCheckValue() + ":" + mathBase.getSTCheckValue());

		MathAdd math= new MathAdd(var, mathBase);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		return math;
	}
	
	/**Method: add
	 * addition
	 * @param: IN MathBase mathBase: Math container giving an operand of the operation   
	 * @param: IN Var var1: operand of the operation; must contain a numeric value, or string that evalues as a numeric value; it can be signed or not; it can contain either an integer or decimal value  
	 * @return: MathAdd: wrapper object containg the sum of the 2 parameters; the maximum precision is kept
	 */
	protected MathAdd add(MathBase mathBase, VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("add_M_V:" + mathBase.getSTCheckValue() + ":" + var.getSTCheckValue());

		MathAdd math= new MathAdd(var, mathBase);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		return math;
	}
	
	protected MathAdd add(MathBase mathBase, String cs)
	{
		if(IsSTCheck)
			Log.logFineDebug("add_M_cs:" + mathBase.getSTCheckValue() + ":" + cs);

		MathAdd math= new MathAdd(cs, mathBase);
		return math;
	}
	
	protected MathAdd add(MathBase mathBase, MathBase var)
	{
		if(IsSTCheck)
			Log.logFineDebug("add_M_M:" + mathBase.getSTCheckValue() + ":" + var.getSTCheckValue());
		MathAdd math= new MathAdd(var, mathBase);
		return math;
	}

	
	/**Method: add
	 * addition
	 * @param: IN MathBase mathBase: Math container giving an operand of the operation   
	 * @param: IN int n: operand of the operation  
	 * @return: MathSubtract: wrapper object containg the sum of the 2 parameters; the maximum precision is kept
	 */
	protected MathAdd add(MathBase mathBase, int n)
	{
		if(IsSTCheck)
			Log.logFineDebug("add_M_n:" + mathBase.getSTCheckValue() + ":" + n);
		
		MathAdd math= new MathAdd(mathBase, n);
		return math;
	}
	
	
	/**Method: add
	 * addition
	 * @param: IN int n: operand of the operation  
	 * @param: IN MathBase mathBase: Math container giving an operand of the operation   
	 * @return: MathSubtract: wrapper object containg the sum of the 2 parameters; the maximum precision is kept
	 */
	protected MathAdd add(int n, MathBase mathBase)
	{
		if(IsSTCheck)
			Log.logFineDebug("add_n_M:" + n + ":" + mathBase.getSTCheckValue());

		MathAdd math= new MathAdd(n, mathBase);
		return math;
	}
	
	
	/**Method: subtract
	 * addition
	 * @param: IN Var var1: operand of the operation; Must evaluate to a numeric value    
	 * @param: IN Var var2: operand of the operation; Must evaluate to a numeric value  
	 * @return: MathSubtract: wrapper object containg the var1 - var2, while keeping the maximum precision  
	 */
	protected MathSubtract subtract(VarAndEdit var1, VarAndEdit var2)
	{
		if(IsSTCheck)
			Log.logFineDebug("subtract_V_V:" + var1.getSTCheckValue() + ":" + var2.getSTCheckValue());

		MathSubtract math = new MathSubtract(var1, var2);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1, var2);
		return math;
	}
	
	protected MathSubtract subtract(int n, MathBase mathBase)
	{
		if(IsSTCheck)
			Log.logFineDebug("subtract_n_M:" + n + ":" + mathBase.getSTCheckValue());

		MathSubtract math = new MathSubtract(n, mathBase);
		return math;
	}
	protected MathSubtract subtract(int n, int b)
	{
		if(IsSTCheck)
			Log.logFineDebug("subtract_n_n:" + n + ":" + b);

		MathSubtract math = new MathSubtract(n, b);
		return math;
	}



	/**Method: subtract
	 * addition
	 * @param: IN Var var1: operand of the operation; Must evaluate to a numeric value    
	 * @param: IN int n: operand of the operation  
	 * @return: MathSubtract: wrapper object containg the var1 - n, while keeping the maximum precision  
	 */	
	protected MathSubtract subtract(VarAndEdit var1, int n)
	{
		if(IsSTCheck)
			Log.logFineDebug("subtract_V_n:" + var1.getSTCheckValue() + ":" + n);
		
		MathSubtract math = new MathSubtract(var1, n);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);
		return math;
	}

	/**Method: subtract
	 * addition
	 * @param: IN int n: operand of the operation  
	 * @return: MathSubtract: wrapper object containg the var1 - n, while keeping the maximum precision  
	 */	
//	protected MathSubtract subtract(int n)
//	{
//		if(IsSTCheck)
//			Log.logFineDebug("subtract_n:" + n);
//
//		MathSubtract math = new MathSubtract(n);
//		return math;
//	}

	/**Method: subtract
	 * addition
	 * @param: IN Var var1: operand of the operation; Must evaluate to a numeric value    
	 * @return: MathSubtract: wrapper object containg the var1, while keeping the maximum precision  
	 */	
//	protected MathSubtract subtract(VarAndEdit var1)
//	{
//		if(IsSTCheck)
//			Log.logFineDebug("subtract_V:" + var1.getSTCheckValue());
//
//		MathSubtract math = new MathSubtract(var1);
//		return math;
//	}

	/**Method: subtract
	 * addition
	 * @param: IN int n: operand of the operation    
	 * @param: IN Var var2: operand of the operation; Must evaluate to a numeric value  
	 * @return: MathSubtract: wrapper object containg the n - var2, while keeping the maximum precision  
	 */	
	protected MathSubtract subtract(int n, VarAndEdit var1)
	{
		if(IsSTCheck)
			Log.logFineDebug("subtract_n_V:" + n + ":" + var1.getSTCheckValue());

		MathSubtract math = new MathSubtract(n, var1);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);
		
		return math;
	}
	
	/**Method: subtract
	 * addition
	 * @param: IN Var var1: operand of the operation; Must evaluate to a numeric value    
	 * @param: IN double d: operand of the operation;   
	 * @return: MathSubtract: wrapper object containg the var1 - d, while keeping the maximum precision  
	 */	
	protected MathSubtract subtract(VarAndEdit var1, double d)
	{
		if(IsSTCheck)
			Log.logFineDebug("subtract_V_d:" + var1.getSTCheckValue() + ":" + d);

		MathSubtract math = new MathSubtract(var1, d);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);
		return math;
	}
	
	/**Method: subtract
	 * addition
	 * @param: IN double d: operand of the operation;     
	 * @param: IN Var var2: operand of the operation; Must evaluate to a numeric value  
	 * @return: MathSubtract: wrapper object containg the d - var2, while keeping the maximum precision  
	 */	
	protected MathSubtract subtract(double d, VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("subtract_d_V:" + d + ":" + var.getSTCheckValue());

		MathSubtract math = new MathSubtract(d, var);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		return math;
	}
	
	/**Method: subtract
	 * addition
	 * @param: IN Var var1: operand of the operation; Must evaluate to a numeric value    
	 * @param: IN String s: operand of the operation; Must evaluate to a numeric value  
	 * @return: MathSubtract: wrapper object containg the var1 - var2, while keeping the maximum precision  
	 */	
	protected MathSubtract subtract(VarAndEdit var1, String s)
	{
		if(IsSTCheck)
			Log.logFineDebug("subtract_V_cs:" + var1.getSTCheckValue() + ":" + s);

		MathSubtract math = new MathSubtract(var1, s);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);
		return math;
	}
	
	/**Method: subtract
	 * addition
	 * @param: IN String: operand of the operation; Must evaluate to a numeric value    
	 * @param: IN Var var2: operand of the operation; Must evaluate to a numeric value  
	 * @return: MathSubtract: wrapper object containg the s - var2, while keeping the maximum precision  
	 */
	protected MathSubtract subtract(String s, VarAndEdit var1)
	{
		if(IsSTCheck)
			Log.logFineDebug("subtract_cs_V:" + s + ":" + var1.getSTCheckValue());

		MathSubtract math = new MathSubtract(s, var1);
		
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);
		return math;
	}
	
	/**Method: subtract
	 * addition
	 * @param: IN Var var1: operand of the operation; Must evaluate to a numeric value    
	 * @param: IN MathBase mathBase: operand of the operation  
	 * @return: MathSubtract: wrapper object containg the var1 - mathBase, while keeping the maximum precision  
	 */
	protected MathSubtract subtract(VarAndEdit var1, MathBase mathBase)
	{
		if(IsSTCheck)
			Log.logFineDebug("subtract_V_M:" + var1.getSTCheckValue() + ":" + mathBase.getSTCheckValue());

		MathSubtract math = new MathSubtract(var1, mathBase);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);
		return math;
	}

	/**Method: subtract
	 * addition
	 * @param: IN MathBase mathBase: operand of the operation; Must evaluate to a numeric value    
	 * @param: IN Var var2: operand of the operation; Must evaluate to a numeric value  
	 * @return: MathSubtract: wrapper object containg the mathBase - var2, while keeping the maximum precision  
	 */
	protected MathSubtract subtract(MathBase mathBase, VarAndEdit var2)
	{
		if(IsSTCheck)
			Log.logFineDebug("subtract_M_V:" + mathBase.getSTCheckValue() + ":" + var2.getSTCheckValue());

		MathSubtract math = new MathSubtract(mathBase, var2);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var2);
		return math;
	}

	/**Method: subtract
	 * addition
	 * @param: IN MathBase mathBase: operand of the operation;     
	 * @param: IN int n: operand of the operation;   
	 * @return: MathSubtract: wrapper object containg the mathBase - n, while keeping the maximum precision  
	 */
	protected MathSubtract subtract(MathBase mathBase, int n)
	{
		if(IsSTCheck)
			Log.logFineDebug("subtract_M_n:" + mathBase.getSTCheckValue() + ":" + n);

		MathSubtract math = new MathSubtract(mathBase, n);
		return math;
	}
	
	/**Method: subtract
	 * addition
	 * @param: IN MathBase mathBase: operand of the operation;     
	 * @param: IN double d: operand of the operation;   
	 * @return: MathSubtract: wrapper object containg the mathBase - d, while keeping the maximum precision  
	 */
	protected MathSubtract subtract(MathBase mathBase, double d)
	{
		if(IsSTCheck)
			Log.logFineDebug("subtract_M_d:" + mathBase.getSTCheckValue() + ":" + d);
		
		MathSubtract math = new MathSubtract(mathBase, d);
		return math;
	}
	
	/**Method: subtract
	 * addition
	 * @param: IN MathBase mathBase: operand of the operation;     
	 * @param: IN String s: operand of the operation; must evaluates to a numeric value   
	 * @return: MathSubtract: wrapper object containg the mathBase - s, while keeping the maximum precision  
	 */
	protected MathSubtract subtract(MathBase mathBase, String s)
	{
		if(IsSTCheck)
			Log.logFineDebug("subtract_M_cs:" + mathBase.getSTCheckValue() + ":" + s);

		MathSubtract math = new MathSubtract(mathBase, s);
		return math;
	}
	
	/**Method: subtract
	 * addition
	 * @param: IN MathBase mathBase1: operand of the operation;     
	 * @param: IN MathBAse mathBase2: operand of the operation;    
	 * @return: MathSubtract: wrapper object containg the mathBase1 - mathBase2, while keeping the maximum precision  
	 */
	protected MathSubtract subtract(MathBase mathBase1, MathBase mathBase2)
	{
		if(IsSTCheck)
			Log.logFineDebug("subtract_M_M:" + mathBase1.getSTCheckValue() + ":" + mathBase2.getSTCheckValue());

		MathSubtract math = new MathSubtract(mathBase1, mathBase2);
		return math;
	}
	

	// Divide	
	/** divide
	 * @param IN var1 Dividende variable; may be integer or decimal
	 * @param IN var2 Divisor variable; may be integer or decimal
	 * @return Return the MathDivide objet that embed the return of var1 / var2. It's value may also be used as modulo and rest of an integer division 
	 */
	protected MathDivide divide(VarAndEdit var1, VarAndEdit var2)
	{
		if(IsSTCheck)
			Log.logFineDebug("divide_V_V:" + var1.getSTCheckValue() + ":" + var2.getSTCheckValue());

		MathDivide math = new MathDivide(var1, var2);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1, var2);
		return math;
	}

	/** divide
	 * @param IN var1 Dividende variable; may be integer or decimal
	 * @param IN n Divisor variable
	 * @return Return the MathDivide objet that embed the return of var1 / var2. It's value may also be used as modulo and rest of an integer division 
	 */
	protected MathDivide divide(VarAndEdit var1, int n)
	{
		if(IsSTCheck)
			Log.logFineDebug("divide_V_n:" + var1.getSTCheckValue() + ":" + n);

		MathDivide math = new MathDivide(var1, n);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);
		return math;
	}
	protected MathDivide divide(int n, VarAndEdit var1)
	{
		if(IsSTCheck)
			Log.logFineDebug("divide_n_V:" + n + ":" + var1.getSTCheckValue());

		MathDivide math = new MathDivide(n, var1);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);
		return math;
	}

	protected MathDivide divide(int var1, int n)
	{
		if(IsSTCheck)
			Log.logFineDebug("divide_n_n:" + var1 + ":" + n);

		MathDivide math = new MathDivide(var1, n);
		return math;
	}
	
	/** divide
	 * @param IN Var var1 Dividende variable; may be integer or decimal
	 * @param IN double d Divisor variable
	 * @return Return the MathDivide objet that embed the return of var1 / var2. It's value may also be used as modulo and rest of an integer division 
	 */
	protected MathDivide divide(VarAndEdit var1, double d)
	{
		if(IsSTCheck)
			Log.logFineDebug("divide_V_d:" + var1.getSTCheckValue() + ":" + d);

		MathDivide math = new MathDivide(var1, d);
		return math;
	}
	
	/** divide
	 * @param IN Var var1 Dividende variable; may be integer or decimal
	 * @param IN String s Divisor variable, must contain a numeric value, that may be decimal or not
	 * @return Return the MathDivide objet that embed the return of var1 / var2. It's value may also be used as modulo and rest of an integer division 
	 */
	protected MathDivide divide(VarAndEdit var1, String s)
	{
		if(IsSTCheck)
			Log.logFineDebug("divide_V_cs:" + var1.getSTCheckValue() + ":" + s);

		MathDivide math = new MathDivide(var1, s);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);
		return math;
	}
	
	/** divide
	 * @param IN Var var1 Dividende variable; may be integer or decimal
	 * @param IN MathBase mathBase Divisor variable resulting from a previosu operation
	 * @return Return the MathDivide objet that embed the return of var1 / var2. It's value may also be used as modulo and rest of an integer division 
	 */
	protected MathDivide divide(VarAndEdit var1, MathBase mathBase)
	{
		if(IsSTCheck)
			Log.logFineDebug("divide_V_M:" + var1.getSTCheckValue() + ":" + mathBase.getSTCheckValue());

		MathDivide math = new MathDivide(var1, mathBase);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);
		return math;
	}
	
	/** divide
	 * @param IN MathBase mathBase: Dividende variable; may be integer or decimal
	 * @param IN Var var2  Divisor variable resulting from a previosu operation
	 * @return Return the MathDivide objet that embed the return of var1 / var2. It's value may also be used as modulo and rest of an integer division 
	 */
	protected MathDivide divide(MathBase mathBase, VarAndEdit var2)
	{
		if(IsSTCheck)
			Log.logFineDebug("divide_M_V:" + mathBase.getSTCheckValue() + ":" + var2.getSTCheckValue());

		MathDivide math = new MathDivide(mathBase, var2);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var2);
		return math;
	}

	/** divide
	 * @param IN MathBase mathBase: Dividende variable; may be integer or decimal
	 * @param IN int n  Divisor variable
	 * @return Return the MathDivide objet that embed the return of mathBase / n. It's value may also be used as modulo and rest of an integer division 
	 */
	protected MathDivide divide(MathBase mathBase, int n)
	{
		if(IsSTCheck)
			Log.logFineDebug("divide_M_n:" + mathBase.getSTCheckValue() + ":" + n);

		MathDivide math = new MathDivide(mathBase, n);
		return math;
	}
	
	/** divide
	 * @param IN MathBase mathBase: Dividende variable; may be integer or decimal
	 * @param IN double d  Divisor variable
	 * @return Return the MathDivide objet that embed the return of mathBase / n. It's value may also be used as modulo and rest of an integer division 
	 */
	protected MathDivide divide(MathBase mathBase, double d)
	{
		if(IsSTCheck)
			Log.logFineDebug("divide_M_d:" + mathBase.getSTCheckValue() + ":" + d);

		MathDivide math = new MathDivide(mathBase, d);
		return math;
	}
	
	/** divide
	 * @param IN MathBase mathBase: Dividende variable; may be integer or decimal
	 * @param IN String s  Divisor variable
	 * @return Return the MathDivide objet that embed the return of mathBase / s. It's value may also be used as modulo and rest of an integer division 
	 */
	protected MathDivide divide(MathBase mathBase, String s)
	{
		if(IsSTCheck)
			Log.logFineDebug("divide_M_cs:" + mathBase.getSTCheckValue() + ":" + s);

		MathDivide math = new MathDivide(mathBase, s);
		return math;
	}	

	/** divide
	 * @param IN MathBase mathBase1: Dividende variable; may be integer or decimal
	 * @param IN MathBase mathBase2  Divisor variable
	 * @return Return the MathDivide objet that embed the return of mathBase1 / MathBase2. It's value may also be used as modulo and rest of an integer division 
	 */
	protected MathDivide divide(MathBase mathBase1, MathBase mathBase2)
	{
		if(IsSTCheck)
			Log.logFineDebug("divide_M_M:" + mathBase1.getSTCheckValue() + ":" + mathBase2.getSTCheckValue());

		MathDivide math = new MathDivide(mathBase1, mathBase2);
		return math;
	}
	
	/** multiply
	 * @param IN Var var1: operand1 variable; may be integer or decimal
	 * @param IN Var var2: operand2 variable
	 * @return Return the MathMultiply objet that embed the return of var1 * var2. 
	 */
	protected MathMultiply multiply(VarAndEdit var1, VarAndEdit var2)
	{
		if(IsSTCheck)
			Log.logFineDebug("multiply_V_V:" + var1.getSTCheckValue() + ":" + var2.getSTCheckValue());

		MathMultiply math = new MathMultiply(var1, var2);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1, var2);
		return math;
	}

	/** multiply
	 * @param IN Var var1: operand1 variable; may be integer or decimal
	 * @param IN int n: operand2 variable
	 * @return Return the MathMultiply objet that embed the return of var1 * n. 
	 */
	protected MathMultiply multiply(VarAndEdit var1, int n)
	{
		if(IsSTCheck)
			Log.logFineDebug("multiply_V_n:" + var1.getSTCheckValue() + ":" + n);

		MathMultiply math = new MathMultiply(var1, n);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);
		return math;
	}

	/** multiply
	 * @param IN int a: operand1 variable; may be integer or decimal
	 * @param IN int b: operand2 variable
	 * @return Return the MathMultiply objet that embed the return of a * b. 
	 */
	protected MathMultiply multiply(int a, int b)
	{
		if(IsSTCheck)
			Log.logFineDebug("multiply_n_n:" + a + ":" + b);

		MathMultiply math = new MathMultiply(a, b);
		return math;
	}
	
	/** multiply
	 * @param IN int n: operand1 variable; may be integer or decimal
	 * @param IN Var var2: operand2 variable
	 * @return Return the MathMultiply objet that embed the return of n * var2. 
	 */
	protected MathMultiply multiply(int n, VarAndEdit var1)
	{
		if(IsSTCheck)
			Log.logFineDebug("multiply_n_n:" + n + ":" + var1.getSTCheckValue());

		MathMultiply math = new MathMultiply(var1, n);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);
		return math;
	}

	/** multiply
	 * @param IN Var var1: operand1 variable; may be integer or decimal
	 * @param IN double d: operand2 variable
	 * @return Return the MathMultiply objet that embed the return of var1 * d. 
	 */
	protected MathMultiply multiply(VarAndEdit var1, double d)
	{
		if(IsSTCheck)
			Log.logFineDebug("multiply_V_d:" + var1.getSTCheckValue() + ":" + d);

		MathMultiply math = new MathMultiply(var1, d);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);
		return math;
	}
	
	/** multiply
	 * @param IN double d: operand1 variable; may be integer or decimal
	 * @param IN Var var2: operand2 variable
	 * @return Return the MathMultiply objet that embed the return of d * var2. 
	 */
	protected MathMultiply multiply(double d, VarAndEdit var1)
	{
		if(IsSTCheck)
			Log.logFineDebug("multiply_d_V:" + d + ":" + var1.getSTCheckValue());

		MathMultiply math = new MathMultiply(var1, d);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);
		return math;
	}
	
	protected MathMultiply multiply(String s, VarAndEdit var1)
	{
		return multiply(var1, s);
	}
	
	/** multiply
	 * @param IN Var var1: operand1 variable; may be integer or decimal
	 * @param IN String s: operand2 variable that must parse as a number
	 * @return Return the MathMultiply objet that embed the return of var1 * s. 
	 */
	protected MathMultiply multiply(VarAndEdit var1, String s)
	{
		if(IsSTCheck)
			Log.logFineDebug("multiply_V_cs:" + var1.getSTCheckValue() + ":" + s);
		MathMultiply math= new MathMultiply(var1, s);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);
		return math;
	}

	/** multiply
	 * @param IN String s: operand1 variable; may be integer or decimal
	 * @param IN Var var1: operand2 variable
	 * @return Return the MathMultiply objet that embed the return of s * var1. 
	 */
	protected MathMultiply MathMultiply(String s, VarAndEdit var1)
	{
		if(IsSTCheck)
			Log.logFineDebug("multiply_cs_V:" + s + ":" + var1.getSTCheckValue());

		MathMultiply math= new MathMultiply(var1, s);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);
		return math;
	}
	
	/** multiply
	 * @param IN MathBase mathBase1: operand1 variable; may be integer or decimal
	 * @param IN MathBase mathBase2: operand2 variable
	 * @return Return the MathMultiply objet that embed the return of mathBase1 * mathBase2. 
	 */
	protected MathMultiply multiply(MathBase mathBase1, MathBase mathBase2)
	{
		if(IsSTCheck)
			Log.logFineDebug("multiply_M_M:" + mathBase1.getSTCheckValue() + ":" + mathBase2.getSTCheckValue());

		MathMultiply math0= new MathMultiply(mathBase1, mathBase2);
		return math0;
	}

	/** multiply
	 * @param IN Var var1: operand1 variable; may be integer or decimal
	 * @param IN MathBase mathBase2: operand2 variable
	 * @return Return the MathMultiply objet that embed the return of var1 * mathBase1. 
	 */
	protected MathMultiply multiply(VarAndEdit var1, MathBase mathBase)
	{
		if(IsSTCheck)
			Log.logFineDebug("multiply_V_M:" + var1.getSTCheckValue() + ":" + mathBase.getSTCheckValue());

		MathMultiply math= new MathMultiply(var1, mathBase);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);
		return math;
	}
	
	/** multiply
	 * @param IN MathBase mathBase: operand1 variable; may be integer or decimal
	 * @param IN Var var: operand2 variable
	 * @return Return the MathMultiply objet that embed the return of mathBase * var. 
	 */
	protected MathMultiply multiply(MathBase mathBase, VarAndEdit var1)
	{
		if(IsSTCheck)
			Log.logFineDebug("multiply_M_V:" + mathBase.getSTCheckValue() + ":" + var1.getSTCheckValue());

		MathMultiply math= new MathMultiply(var1, mathBase);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);
		return math;
	}
	
	/** multiply
	 * @param IN MathBase mathBase: operand1 variable; may be integer or decimal
	 * @param IN int n: operand2 variable
	 * @return Return the MathMultiply objet that embed the return of mathBase * n. 
	 */
	protected MathMultiply multiply(MathBase mathBase, int n)
	{
		if(IsSTCheck)
			Log.logFineDebug("multiply_M_n:" + mathBase.getSTCheckValue() + ":" + n);

		MathMultiply math= new MathMultiply(n, mathBase);
		return math;
	}

	/** multiply
	 * @param IN int n: operand1 variable; may be integer or decimal
	 * @param IN MathBase mathBase: operand2 variable
	 * @return Return the MathMultiply objet that embed the return of n * mathBase. 
	 */
	protected MathMultiply multiply(int n, MathBase mathBase)
	{
		if(IsSTCheck)
			Log.logFineDebug("multiply_n_M:" + n + ":" + mathBase.getSTCheckValue());

		MathMultiply math= new MathMultiply(n, mathBase);
		return math;
	}

	/** multiply
	 * @param IN MathBase mathBase: operand1 variable; may be integer or decimal
	 * @param IN int n: operand2 variable
	 * @return Return the MathMultiply objet that embed the return of n * mathBase. 
	 */
	protected MathMultiply multiply(MathBase mathBase, double d)
	{
		if(IsSTCheck)
			Log.logFineDebug("multiply_M_d:" + mathBase.getSTCheckValue() + ":" + d);

		MathMultiply math= new MathMultiply(d, mathBase);
		return math;
	}
	
	/** multiply
	 * @param IN MathBase mathBase: operand1 variable; may be integer or decimal
	 * @param IN int n: operand2 variable
	 * @return Return the MathMultiply objet that embed the return of n * mathBase. 
	 */
	protected MathMultiply multiply(MathBase mathBase, String cs)
	{
		if(IsSTCheck)
			Log.logFineDebug("multiply_M_cs:" + mathBase.getSTCheckValue() + ":" + cs);

		MathMultiply math= new MathMultiply(new Double(cs), mathBase);
		return math;
	}

	/** multiply
	 * @param IN double d: operand1 variable; may be integer or decimal
	 * @param IN MathBase mathBase: operand2 variable
	 * @return Return the MathMultiply objet that embed the return of d * mathBase. 
	 */
	protected MathMultiply multiply(double d, MathBase mathBase)
	{
		if(IsSTCheck)
			Log.logFineDebug("multiply_d_M:" + d + ":" + mathBase.getSTCheckValue());

		MathMultiply math= new MathMultiply(d, mathBase);
		return math;
	}

	protected MathPow pow(VarAndEdit var1, String s)
	{
		if(IsSTCheck)
			Log.logFineDebug("expon_V_cs:" + var1.getSTCheckValue() + ":" + s);
		MathPow math= new MathPow(var1, s);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);
		return math;
	}

	/**Method: setTrue
	 * Force a condition to always evaluate as true 
	 * @param: IN Cond cond     
	 * @return: current program object; enables to chain another Program's method.
	 */
	protected BaseProgram setTrue(Cond cond)
	{
		if(IsSTCheck)
			Log.logFineDebug("setTrue_cond:" + cond.getSTCheckValue());

		cond.setTrue();
		return this;
	}

	/**Method: move
	 * move a value into a var 
	 * @param: IN int n
	 * @param: OUT Var varDest 
	 * @return: current program object; enables to chain another Program's method. 
	 */
	protected void move(int n, Var varDest)
	{
		if(IsSTCheck)
		{
			String cs = varDest.getSTCheckValue();
			Log.logFineDebug("move_n_V:" + n + ":" + cs);
		}

		varDest.set(n);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest);
	}

	protected void move(boolean b, Var varDest)
	{
		if(IsSTCheck)
			Log.logFineDebug("move_b_V:" + b + ":" + varDest.getSTCheckValue());

		varDest.set(b);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest);
	}

	protected void move(int n, Edit varDest)
	{
		if(IsSTCheck)
			Log.logFineDebug("move_n_E:" + n + ":" + varDest.getSTCheckValue());
		if(isLogCESM)
			Log.logDebug("moveEdit n="+n+" to Edit="+varDest.getLoggableValue());
		varDest.set(n);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest);
	}
	
	protected void move(Var varSource, Edit varDest)
	{	
		if(IsSTCheck)
			Log.logFineDebug("move_V_E:" + varSource.getSTCheckValue() + ":" + varDest.getSTCheckValue());
		
		varDest.set(varSource);	// PJD Var TO Edit
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varSource, varDest);
	}
	
	protected void move(Edit varSource, Var varDest)
	{	
		if(IsSTCheck)
			Log.logFineDebug("move_E_V:" + varSource.getSTCheckValue() + ":" + varDest.getSTCheckValue());
		varSource.transferTo(varDest); 	// PJD Edit TO Var
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varSource, varDest);
	}
	
	protected void move(Edit varSource, Edit varDest)
	{
		if(IsSTCheck)
			Log.logFineDebug("move_E_E:" + varSource.getSTCheckValue() + ":" + varDest.getSTCheckValue());

		varSource.transferTo(varDest); 	// PJD EditInMap TO EditInMap or EditInMap TO EditInMapRedefine
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varSource, varDest);
	}

	/**Method: move
	 * move a constant (LowValue, HighValue, Zero, Space) into a var; all data chars of the destination var are filled with the source constant   
	 * @param: CobolConstant constant
	 * @param: OUT Var varDest 
	 * @return: current program object; enables to chain another Program's method.
	 * @see CobolConstant
	 */
	protected void move(CobolConstantSpace constant, VarAndEdit varDest)
	{
		if(IsSTCheck)
			Log.logFineDebug("move_cst_V:" + constant.getSTCheckValue() + ":" + varDest.getSTCheckValue());

		varDest.set(constant);
	}

	protected void move(CobolConstantZero constant, VarAndEdit varDest)
	{
		if(IsSTCheck)
			Log.logFineDebug("move_cst_V:" + constant.getSTCheckValue() + ":" + varDest.getSTCheckValue());
	
		varDest.set(constant);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest);
	}

	protected void move(CobolConstantLowValue constant, VarAndEdit varDest)
	{
		if(IsSTCheck)
			Log.logFineDebug("move_cst_V:" + constant.getSTCheckValue() + ":" + varDest.getSTCheckValue());
		
		varDest.set(constant);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest);
	}
	
	protected void move(CobolConstantHighValue constant, VarAndEdit varDest)
	{
		if(IsSTCheck)
			Log.logFineDebug("move_cst_V:" + constant.getSTCheckValue() + ":" + varDest.getSTCheckValue());

		varDest.set(constant);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest);
	}
	
	/**Method: move
	 * move a flotting point numeric value into a var 
	 * @param: IN double d
	 * @param: OUT Var varDest 
	 * @return: current program object; enables to chain another Program's method. 
	 */
	protected void move(double d, Var varDest)
	{
		if(IsSTCheck)
			Log.logFineDebug("move_d_V:" + d + ":" + varDest.getSTCheckValue());

		varDest.set(d);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest);
	}
	
	protected void move(double d, Edit editDest)
	{
		if(IsSTCheck)
			Log.logFineDebug("move_d_E:" + d + ":" + editDest.getSTCheckValue());
		if(isLogCESM)
			Log.logDebug("moveEdit: d="+d+" to Edit="+editDest.getLoggableValue());
		editDest.set(d);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(editDest);
	}
	
	protected void move(long l, Var varDest)
	{
		if(IsSTCheck)
			Log.logFineDebug("move_l_V:" + l + ":" + varDest.getSTCheckValue());

		varDest.set(l);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest);
	}
	
	protected void move(long l, Edit editDest)
	{
		if(IsSTCheck)
			Log.logFineDebug("move_l_E:" + l + ":" + editDest.getSTCheckValue());
		if(isLogCESM)
			Log.logDebug("moveEdit: l="+l+" to Edit="+editDest.getLoggableValue());
		editDest.set(l);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(editDest);
	}
	
	/**Method: move
	 * move a string value into a var 
	 * @param: IN String cs
	 * @param: OUT Var varDest 
	 * @return: current program object; enables to chain another Program's method. 
	 */
	protected void move(String cs, Var varDest)
	{
		if(IsSTCheck)
		{
			Log.logFineDebug("move_cs_V:" + cs + ":" + varDest.getSTCheckValue());
		}
		//varDest.set(cs);
		varDest.m_varDef.write(varDest.m_bufferPos, cs);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest);
	}
	
	
	protected void move(String cs, Edit varDest)
	{
		if(IsSTCheck)
			Log.logFineDebug("move_cs_E:" + cs + ":" + varDest.getSTCheckValue());
		if(isLogCESM)
			Log.logDebug("moveEdit: cs="+cs+" to Edit="+varDest.getLoggableValue());
		varDest.m_varDef.write(varDest.m_bufferPos, cs);
		//varDest.set(cs);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest);
	}

	/**Method: move
	 * fill the destination var with the source string, used as a pattern 
	 * @param: IN String cs
	 * @param: OUT Var varDest 
	 * @return: current program object; enables to chain another Program's method. 
	 */
	protected BaseProgram moveAll(String cs, VarAndEdit varDest)
	{	
		if(IsSTCheck)
			Log.logFineDebug("moveAll_cs_V:" + cs + ":" + varDest.getSTCheckValue());

		varDest.setAndFill(cs);	
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest);
		return this;
	}
	

	/**Method: move
	 * move a var's value into another var; convertions may occur, depending on the pic declaration of the 2 vars. 
	 * @param: IN Var varSource
	 * @param: OUT Var varDest 
	 * @return: current program object; enables to chain another Program's method. 
	 */
	
	protected void move(Var varSource, Var varDest)
	{	
		if(IsSTCheck)
			Log.logFineDebug("move_V_V:" + varSource.getSTCheckValue() + ":" + varDest.getSTCheckValue());

		if(varSource.m_varTypeId == varDest.m_varTypeId)
		{
			varDest.m_varDef.moveIntoSameType(varDest.m_bufferPos, varSource.m_varDef, varSource.m_bufferPos);
			if(m_bUsedTempVarOrCStr)
				m_tempCache.resetTempIndex(varSource, varDest);
			return ;
		}
		varSource.transferTo(varDest);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varSource, varDest);
	}

	
	protected BaseProgram move(Var varSource, Form formDest)
	{	
		if(IsSTCheck)
			Log.logFineDebug("move_V_F:" + varSource.getSTCheckValue() + ":" + formDest.getSTCheckValue());

		formDest.decodeFromVar(varSource);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varSource);
		return this;
	}


//	protected Program move(Var varSource, Edit varDest)
//	{
//		logCesm("moveAllEdit: varSource="+varSource.getLoggableValue()+" to Edit="+varDest.getLoggableValue());
//		varDest.set(varSource);
//		return this;
//	}
	
	/**Method: move
	 * move all fields of a form (GUI applications only) into a destionation var. Every field has a 7 chars header containg various 
	 * attributes, follwed by the filed's data value. It can be thought of as a serialization of a form into a var;
	 * <b>used internally</b>     
	 * @param: IN Form formSource
	 * @param: OUT Var varDest 
	 * @return: current program object; enables to chain another Program's method. 
	 */
	protected BaseProgram move(Form formSource, Var varDest)
	{
		if(IsSTCheck)
			Log.logFineDebug("move_F_V:" + formSource.getSTCheckValue() + ":" + varDest.getSTCheckValue());

		formSource.encodeToVar(varDest);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest);
		return this;
	}
	
	protected BaseProgram move(MapRedefine mapRedefineSource, Var varDest)
	{
		if(IsSTCheck)
			Log.logFineDebug("move_MR_V:" + mapRedefineSource.getSTCheckValue() + ":" + varDest.getSTCheckValue());

		mapRedefineSource.encodeToVar(varDest);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest);
		return this;
	}
	
	protected BaseProgram move(Form formSource, Form formDest)
	{
		if(IsSTCheck)
			Log.logFineDebug("move_F_F:" + formSource.getSTCheckValue() + ":" + formDest.getSTCheckValue());
		
		InternalCharBuffer charBuffer = formSource.encodeToCharBuffer();
		formDest.decodeFromCharBuffer(charBuffer);
		
		return this;
	}
	
	protected BaseProgram move(MapRedefine mapSource, Form formDest)
	{
		if(IsSTCheck)
			Log.logFineDebug("move_MP_F:" + mapSource.getSTCheckValue() + ":" + formDest.getSTCheckValue());

		InternalCharBuffer charBuffer = mapSource.encodeToCharBuffer();
		formDest.decodeFromCharBuffer(charBuffer);
		
		return this;
	}
	
	protected BaseProgram move(Form formSource, MapRedefine mapDest)
	{
		if(IsSTCheck)
			Log.logFineDebug("move_F_MP:" + formSource.getSTCheckValue() + ":" + mapDest.getSTCheckValue());

		InternalCharBuffer charBuffer = formSource.encodeToCharBuffer();
		mapDest.decodeFromCharBuffer(charBuffer);
		
		return this;
	}

	protected BaseProgram move(MapRedefine mapSource, MapRedefine mapDest)
	{
		if(IsSTCheck)
			Log.logFineDebug("move_MP_MP:" + mapSource.getSTCheckValue() + ":" + mapDest.getSTCheckValue());

		InternalCharBuffer charBuffer = mapSource.encodeToCharBuffer();
		mapDest.decodeFromCharBuffer(charBuffer);
		
		return this;
	}

	
	/**Method: move
	 * move a var content into all fields of a form (GUI applications only). The var source must have been previously filled by a previous move(Form formSource, Var varDest).  
	 * Thus this method can be thought of as a deserialization of a var into a form;
	 * <b>used internally</b>     
	 * @param: IN Var varSource
	 * @param: OUT Form formDest 
	 * @return: current program object; enables to chain another Program's method. 
	 */
//	protected Program move(Var varSource, Form fromDest)
//	{
//		logCesm("move var to Form: varSource="+varSource.getLoggableValue()+" to Form="+fromDest.getLoggableValue());
//		fromDest.decodeFromVar(varSource);
//		return this;
//	}
	
	/** move
	 * move a form to another one
	 * @param IN formSource: Source form
	 * @param OUT fromDest: Destination form
	 * @return: current program object; enables to chain another Program's method.
	 */
//	protected Program move(Form formSource, Form fromDest)
//	{
//		logCesm("move Form to Form: formSource="+formSource.getLoggableValue()+" to Form="+fromDest.getLoggableValue());
//		fromDest.set(formSource);
//		return this;
//	}

	/**Method: move
	 * fill the destination var with the sourceVar contents, used as a pattern 
	 * @param: IN Var varSource
	 * @param: OUT Var varDest 
	 * @return: current program object; enables to chain another Program's method. 
	 */
	protected BaseProgram moveAll(Var varSource, Var varDest)
	{	
		if(IsSTCheck)
			Log.logFineDebug("moveAll_V_V:" + varSource.getSTCheckValue() + ":" + varDest.getSTCheckValue());

		varDest.setAndFill(varSource.getString());	
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varSource, varDest);
		return this;
	}
	
	protected BaseProgram moveAll(Var varSource, Edit varDest)
	{	
		if(IsSTCheck)
			Log.logFineDebug("moveAll_V_E:" + varSource.getSTCheckValue() + ":" + varDest.getSTCheckValue());
		if(isLogCESM)
			Log.logDebug("moveAllEdit: varSource="+varSource.getLoggableValue()+" to Edit="+varDest.getLoggableValue());
		varDest.setAndFill(varSource.getString());	
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varSource, varDest);

		return this;
	}
		
	/**Method: moveCorresponding
	 * move all identically named vars below varSource to varDestGroup 
	 * @param: IN Var varSource: It may have children
	 * @param: OUT Var varDestGroup: It may have children 
	 * @return: current program object; enables to chain another Program's method. 
	 */
	
	protected BaseProgram moveCorresponding(VarBase varSource, VarBase varDestGroup)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveCorresponding_V_V:" + varSource.getSTCheckValue() + ":" + varDestGroup.getSTCheckValue());

		// This method is to help a recursive descent in case the move corresponding manages multi level depth. It seems not to be the case, so it's implemented as signle level depth.
		if(varSource != null)
		{
			MoveCorrespondingEntryManager manager = getProgramManager().getOrCreateMoveCorrespondingEntryManager(varSource.getVarDef(), varDestGroup.getVarDef());
			varSource.moveCorresponding(manager, varDestGroup);
//			varSource.moveCorresponding(null, varDestGroup);
		}
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varSource, varDestGroup);

		return this;
	}

//	protected BaseProgram moveCorresponding(VarBase varSource, VarBase varDestGroup)
//	{
//		if(IsSTCheck)
//			Log.logFineDebug("moveCorresponding_V_V:" + varSource.getSTCheckValue() + ":" + varDestGroup.getSTCheckValue());
//
//		CacheMoveCorresponding cacheMoveCorresponding = getCacheMoveCorresponding(varSource, varDestGroup);
//		
//		// This method is to help a recursive descent in case the move corresponding manages multi level depth. It seems not to be the case, so it's implemented as signle level depth.
//		if(varSource != null)
//			varSource.moveCorresponding(varDestGroup, cacheMoveCorresponding);
//		if(varSource.isTempVar() || varDest.isTempVar())
//			m_tempCache.resetTempIndex(varSource, varDestGroup);
//
//		return this;
//	}
//	
//	private CacheMoveCorresponding getCacheMoveCorresponding(VarBase varSource, VarBase varDestGroup)
//	{
//		int nId = varSource.getId();
//		if(m_CacheCacheMoveCorresponding == null)
//			m_CacheCacheMoveCorresponding = new CacheCacheMoveCorresponding();
//		CacheMoveCorresponding c = m_CacheCacheMoveCorresponding.get(nId);
//		if(c == null)
//		{
//			c = new CacheMoveCorresponding();
//			m_CacheCacheMoveCorresponding.add(nId, c);
//		}
//		return c;
//	}
//	private CacheCacheMoveCorresponding m_CacheCacheMoveCorresponding = null;
	
	
	/**Method: perform
	 * calls a paragraph, using the library. Used to wrap and hide internally used exception handlers 
	 * @param: IN Paragraph paragraph: Functor objet which is called
	 * @return:  
	 */
	protected void perform(Paragraph paragraph)
	{		
		if(paragraph != null)
		{
			if(isLogFlow)
				Log.logDebug("Performing: "+ getSimpleName()+"."+paragraph.toString());
			if(IsSTCheck)
				Log.logFineDebug("perform_para:" + getSimpleName()+"."+paragraph.toString());
			paragraph.run();
		}
	}
	
	/**Method: perform
	 * calls the first paragraph of a section, using the library. 
	 * @param: IN Section section: Functor objet that group multiple paragraphs. 
	 * All paragraphs of the section identified will be called in sequence if no flow breaking (goto StopRun ...) occurs.
	 * @return:  
	 */	
	protected void perform(Section section)
	{		
		if(IsSTCheck)
			Log.logFineDebug("perform_section:" + getSimpleName()+"."+section.toString());
		if(isLogFlow)
			Log.logDebug("Performing section:"+ getSimpleName()+"."+section.toString());
		
		m_BaseProgramManager.perform(section);
	}
	
	/**Method: performTrough
	 * calls sequentially all paragraphs that lies between paragraphBegin and paragraphEnd. 
	 * @param: Paragraph paragraphBegin: Functor objet that identified the first paragraph to call
	 * @param: Paragraph paragraphEnd: Functor objet that identified the last paragraph to call
	 * @return:  
	 */	
	protected void performThrough(Paragraph paragraphBegin, Paragraph paragraphEnd)
	{
		if(IsSTCheck)
			Log.logFineDebug("performThrough_para_para:" + getSimpleName()+"." + paragraphBegin.toString() + ":" + getSimpleName()+"." + paragraphEnd.toString());
		
		if(isLogFlow)
			Log.logDebug("Performing through:"+ getSimpleName()+"." + paragraphBegin.toString() + " -> "+paragraphEnd.toString());
		m_BaseProgramManager.performThrough(paragraphBegin, paragraphEnd);
	}
	
	/**Method: goTo
	 * Transfer the flow control the provided paragraph. No stack pushing occurs. 
	 * @param: Paragraph paragraph: Functor objet that identified the paragraph where to transfer flow control.
	 * @return:  
	 */
	protected void goTo(Paragraph functor)
	{
		if(IsSTCheck)
			Log.logFineDebug("goto_para:"+ getSimpleName()+"."+functor.toString());

		if(isLogFlow)
			Log.logDebug("goTo paragraph:"+ getSimpleName()+"."+functor.toString());
		CGotoException e = new CGotoException(functor);
		throw e;
	}

	/**Method: goTo
	 * Transfer the flow control the first paragrpaph of the provided section. No stack pushing occurs. 
	 * @param: Section section: Functor objet that contains mulpliple paragraphs. The first one is used as the destination of the flow control.
	 * @return:  
	 */
	protected void goTo(Section functor)
	{
		if(IsSTCheck)
			Log.logFineDebug("goto_section:"+ getSimpleName()+"."+functor.toString());

		if(isLogFlow)
			Log.logDebug("goTo section:"+ getSimpleName()+"."+functor.toString());
		CGotoException e = new CGotoException(functor);
		throw e;
	}

	protected void goTo(CJMapRunnable[] functors, Var dependingOn)
	{
		int i = dependingOn.getInt() - 1;
		if (i >= 0)
		{
			CJMapRunnable f = functors[i];
			if (f instanceof Section)
				goTo((Section) f);
			goTo((Paragraph) f);
		}
	}

	/**Method: goTo
	 * Transfer the flow control the first paragrpaph of the provided section. No stack pushing occurs. 
	 * @param: Section section: Functor objet that contains mulpliple paragraphs. The first one is used as the destination of the flow control.
	 * @return:  
	 */
	protected void goBack() throws CGotoException
	{
		if(IsSTCheck)
			Log.logFineDebug("goBack():");

		CExitException e = new CExitException();
		throw e;
	}

	/**
	 * @throws CGotoException
	 * Return from the current sub program; do nothing if we are not inside a sub program. 
	 * A sub program is a program that is called explicitly by another application program 
	 */
	protected void exitProgram() throws CGotoException
	{
		if(IsSTCheck)
			Log.logFineDebug("exitProgram():");

		CExitException e = new CExitException();
		throw e;
	}
	
	/**
	 * exit
	 * Do nothing:
	 * The EXIT statement enables you to assign a procedure-name to a given point in a program.
	 * As an IBM extension, the EXIT statement does not need to appear in a
	 * sentence by itself. Any statements following the EXIT statement are
	 * executed; the EXIT statement is treated as the CONTINUE statement.
	 * @see http://publibz.boulder.ibm.com/cgi-bin/bookmgr_OS390/BOOKS/IGYLR205/6.2.14?DT=20000927030801 
	 */
	protected void exit() 
	{
		if(IsSTCheck)
			Log.logFineDebug("exit:");

		// Do nothing:
		// http://publibz.boulder.ibm.com/cgi-bin/bookmgr_OS390/BOOKS/IGYLR205/6.2.14?DT=20000927030801
		// The EXIT statement enables you to assign a procedure-name to a given point in a program. 
		// As an IBM extension, the EXIT statement does not need to appear in a 
		// sentence by itself. Any statements following the EXIT statement are 
		// executed; the EXIT statement is treated as the CONTINUE statement.
	}
	

	/**
	 * @throws CGotoException
	 * Exit the application
	 */
	protected void stopRun() throws CGotoException	// Exit the application
	{
		if(IsSTCheck)
			Log.logFineDebug("stopRun:");

		CStopRunException e = new CStopRunException(getProgramManager());
		throw e;
	}	
	
	
	/** inc
	 * @param IN Var var: Variable that is incremented of 1; the var must contain a numeric value
	 */
	protected void inc(Var var)
	{
		if(IsSTCheck)
			Log.logFineDebug("inc_V:" + var.getSTCheckValue());

		//var.inc();
		var.m_varDef.inc(var.m_bufferPos, 1); 
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
	}

	/** dec
	 * @param IN/OUT Var var: Variable that is decremented of 1; the var must contain a numeric value
	 */
	protected void dec(Var var)
	{
		if(IsSTCheck)
			Log.logFineDebug("dec_V:" + var.getSTCheckValue());

//		var.dec();
		var.m_varDef.inc(var.m_bufferPos, -1);		
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
	}
	protected void dec(Var varStep, Var var)
	{
		if(IsSTCheck)
			Log.logFineDebug("dec_V_V:" + varStep.getSTCheckValue() + ":" + var.getSTCheckValue());

		
		var.dec(varStep);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varStep, var);
	}

	/**
	 * @param IN int nStep: Increment step
	 * @param IN/OUT Var var: Variable that is incremented of nStep; the var must contain a numeric value
	 */	
	protected void inc(int nStep, Var var)
	{
		if(IsSTCheck)
			Log.logFineDebug("inc_n_V:" + nStep + ":" + var.getSTCheckValue());

		var.m_varDef.inc(var.m_bufferPos, nStep);
		//var.inc(nStep);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
	}
	protected void inc(double dStep, Var var)
	{
		if(IsSTCheck)
			Log.logFineDebug("inc_d_V:" + dStep + ":" + var.getSTCheckValue());

		var.inc(dStep);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
	}
	
	protected void inc(String csStep, Var var)
	{
		if(IsSTCheck)
			Log.logFineDebug("inc_cs_V:" + csStep + ":" + var.getSTCheckValue());

		var.inc(csStep);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
	}

	
	/**
	 * @param IN Var varStep: Increment step
	 * @param IN/OUT Var var: Variable that is incremented of varStep's value; the var must contain a numeric value
	 */
	protected void inc(Var varStep, Var var)
	{
		if(IsSTCheck)
			Log.logFineDebug("inc_V_V:" + varStep.getSTCheckValue() + ":" + var.getSTCheckValue());

		var.inc(varStep);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varStep, var);
	}

	/**
	 * @param IN int nStep: Decrement step
	 * @param IN/OUT Var var: Variable that is decremented of nStep; the var must contain a numeric value
	 */
	protected void dec(int nStep, Var var)
	{
		if(IsSTCheck)
			Log.logFineDebug("dec_n_V:" + nStep + ":" + var.getSTCheckValue());

		var.dec(nStep);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
	}
	
	protected void dec(String csStep, Var var)
	{
		if(IsSTCheck)
			Log.logFineDebug("dec_d_V:" + csStep + ":" + var.getSTCheckValue());

		var.dec(csStep);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
	}
	
	/**
	 * @param IN MathBase value: Contains a numeric vale, resulting from a previos calculous
	 * @param OUT Var var: Variable whose value is set on output the value of mathBase; No rounding is done upon set var's value 
	 * @return mathBase object, enabling to chain calculous 
	 */
	protected MathBase compute(MathBase mathBase, Var var)
	{
		if(IsSTCheck)
			Log.logFineDebug("compute_M_V:" + mathBase.getSTCheckValue() + ":" + var.getSTCheckValue());

		mathBase.to(var) ;
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		return mathBase;
	}

	/**
	 * @param IN MathBase value: Contains a numeric vale, resulting from a previos calculous
	 * @param OUT Var var: Variable whose value is set on output the value of mathBase; The var's value is rounded upon setting, as opposite of "MathBase compute(MathBase mathBase, Var var)"  
	 * @return mathBase object, enabling to chain calculous 
	 */
	protected MathBase computeRounded(MathBase value, Var var)
	{
		if(IsSTCheck)
			Log.logFineDebug("computeRounded_M_V:" + value.getSTCheckValue() + ":" + var.getSTCheckValue());

		value.toRounded(var) ;
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		return value ;
	}
	
	/**
	 * @param IN int nValue
	 * @param OUT Var varDest; Set the value of the varDest to nValue; same as "move(nValue, varDest);"	 
	 * @return
	 */
	protected BaseProgram compute(int nValue, Var varDest)
	{
		if(IsSTCheck)
			Log.logFineDebug("computeRounded_n_V:" + nValue + ":" + varDest.getSTCheckValue());

		move(nValue, varDest);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest);
		return this;
	}
	

	protected SQLCall sqlCall(VarAndEdit statement)
	{
		if(IsSTCheck)
			Log.logFineDebug("sqlCall_V:" + statement.getSTCheckValue());
		
		String csStatement = statement.getString().trim();
		return sqlCall(csStatement);
	}

	protected SQLCall sqlCall(String csStatement)
	{
		if(IsSTCheck)
			Log.logFineDebug("sqlCall_cs:" + csStatement);
		
		SQLCall sqlCall = new SQLCall(m_BaseProgramManager, csStatement); 
		return sqlCall;
	}
	
	
	/**
	 * @param String csStatement. Must be sql valid.
	 * @return SQL Internal object used to chain sql orders
	 */
	protected SQL sql(String csStatement)
	{
		if(IsSTCheck)
			Log.logFineDebug("sql_cs:" + csStatement);
		//String csFileLine = StackStraceSupport.getFileLineAtStackDepth(2);	// Caller File Line
//		int nHashFileLine = 0;
//		if(csFileLine != null)
//			nHashFileLine = csFileLine.hashCode(); 
		//SQL sql = m_BaseProgramManager.getOrCreateSQL(csStatement);	//, csFileLine);
		SQL sql = m_BaseProgramManager.getOrCreateSQLGeneral(csStatement, null);

		// Was active: DbConnectionBase SQLConnection = m_ProgramManager.m_CESMEnv.getSQLConnection();
		// SQL sql = new SQL(m_ProgramManager, csStatement, null);
				
		return sql;
	}
	
	/**
	 * @param IN csString Statement; Must be sql valid.
	 * @return SQLCursor internal object used to chain sql cusror declarations. 
	 */
	
	/*
	protected SQLCursor sqlCursor(String csStatement)
	{
		if(m_ProgramManager.m_CESMEnv != null)
			return sqlCursor(m_ProgramManager.m_CESMEnv.getSQLConnection(), csStatement);
		return sqlCursor(null, csStatement);
	}
	*/
	/**
	 * @param SQLConnection Internal SQL Connection
	 * @param IN csString Statement; Must be sql valid.
	 * @return SQLCursor internal object used to chain sql cursor declarations.
	 * Remark: This method should not be used as it requires a SQLConnection. To use the default SQL connection established in a standard way, use SQL sql(String csStatement) 
	 */ 
//	protected SQLCursor sqlCursor(CSQLConnection SQLConnection, String csStatement)
//	{		
//		SQLCursor sqlCursor = new SQLCursor(m_ProgramManager.m_DataDivision.getWorkingStorageSectionVarBuffer(), SQLConnection, csStatement);
//		return sqlCursor;
//	}
	
	/**
	 * @param IN/OUT SQLCursor sqlCursor
	 * Opens the cursor in parameter
	 */
	protected SQLCursor cursorDefine()
	{
		if(IsSTCheck)
			Log.logFineDebug("cursorDefine");

		SQLCursor sqlCursor = new SQLCursor(m_BaseProgramManager);
		return sqlCursor; 
	}
	
//	protected SQLCursor cursorOpen(String csQuery)
//	{
//		if(IsSTCheck)
//			Log.logFineDebug("cursorOpen_cs:" + csQuery);
//
//	
//		SQLCursor sqlCursor = new SQLCursor(m_ProgramManager, m_ProgramManager.m_DataDivision.getWorkingStorageSectionVarBuffer(), m_ProgramManager.m_CESMEnv, csQuery, m_ProgramManager.getSQLStatus());
//		sqlCursor.open();
//		return sqlCursor; 
//	}
	
	protected SQLCursor cursorOpen(SQLCursor sqlCursor, String csQuery)
	{
		if(IsSTCheck)
			Log.logFineDebug("cursorOpen_cur_cs:" + csQuery);
		
		if(sqlCursor.isOpen())	// Auto close
			sqlCursor.close();
		sqlCursor.setQuery(csQuery);
		sqlCursor.open();
		return sqlCursor; 
	}
	
	protected SQLCursor cursorOpen(SQLCursor sqlCursor, Var vQuery)
	{
		if(IsSTCheck)
			Log.logFineDebug("cursorOpen_cur_V:" + vQuery.toString());
		
		if(sqlCursor.isOpen())	// Auto close
			sqlCursor.close();
		if (vQuery.getVarDef().isLongVarCharVarStructure())
		{
			VarEnumerator e = new VarEnumerator(vQuery.getProgramManager(), vQuery); 
			VarBase varChildLength = e.getFirstVarChild();
			VarBase varChildText = e.getNextVarChild();

			int nLength = varChildLength.getInt();
			String csValue = varChildText.getString();
			if(nLength < csValue.length())
				csValue = csValue.substring(0, nLength);
			sqlCursor.setQuery(csValue);
		}
		else
		{	
			sqlCursor.setQuery(vQuery.getString());
		}	
		sqlCursor.open();
		
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(vQuery);

		return sqlCursor; 
	}
	
	/**
	 * @param IN/OUT SQLCursor sqlCursor
	 * Closes the cursor in parameter 
	 */
	protected CSQLStatus cursorClose(SQLCursor sqlCursor)
	{
		if(IsSTCheck)
			Log.logFineDebug("cursorClose_cur:");

		if (sqlCursor != null)
		{
			CSQLStatus sqlStatus = sqlCursor.close();
			return sqlStatus;
		}
		return null;
	}
	
	protected SQLCursorOperation cursorUpdateCurrent(SQLCursor sqlCursor, String csUpdateClause)
	{
		if(IsSTCheck)
			Log.logFineDebug("cursorUpdateCurrent_cur_cs:" + "NotDisplayed" + ":" + csUpdateClause);

//		SQLCursorOperation sqlCursorOperation = new SQLCursorOperation(
//			m_ProgramManager.m_CESMEnv.getSQLConnection(), 
//			m_ProgramManager.m_DataDivision.getWorkingStorageSectionVarBuffer(), 
//			sqlCursor, 
//			csUpdateClause,
//			m_ProgramManager.getSQLStatus());
		
		SQLCursorOperation sqlCursorOperation = new SQLCursorOperation(
			m_BaseProgramManager, 
			sqlCursor, 
			csUpdateClause);
		return sqlCursorOperation;
	}
	
	protected SQLCursorOperation cursorDeleteCurrent(SQLCursor sqlCursor, String csDeleteClause)
	{
		if(IsSTCheck)
			Log.logFineDebug("cursorDeleteCurrent_cur_cs:" + "NotDisplayed" + ":" + csDeleteClause);

//		SQLCursorOperation sqlCursorOperation = new SQLCursorOperation(
//			m_ProgramManager.m_CESMEnv.getSQLConnection(), 
//			m_ProgramManager.m_DataDivision.getWorkingStorageSectionVarBuffer(), 
//			sqlCursor, 
//			csDeleteClause,
//			m_ProgramManager.getSQLStatus());
	
		SQLCursorOperation sqlCursorOperation = new SQLCursorOperation(
			m_BaseProgramManager, 
			sqlCursor, 
			csDeleteClause);
		return sqlCursorOperation;
	}	

	/**
	 * @param IN/OUT SQLCursor sqlCursor
	 * @return Internal SQLCursor object;
	 * The into parameters are filled with the columns values read during the fetch. 
	 */
	protected SQLCursorFetch cursorFetch(SQLCursor sqlCursor)
	{
		if(IsSTCheck)
			Log.logFineDebug("cursorFetch_cur:" + "NotDisplayed");

		return sqlCursor.fetch(getProgramManager().getEnv());
	}
	
	/**
	 * @param VAR OUT var: variable that is filled with CobolConstant.Space characters
	 */
	protected void moveSpace(VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveSpace_cs:" + var.getSTCheckValue());

		var.set(CobolConstant.Space);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
	}
	
	/**
	 * @param VAR OUT var: variable that is filled with CobolConstant.LowValue characters
	 */
	protected void moveLowValue(VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveLowValue_V:" + var.getSTCheckValue());

		var.set(CobolConstant.LowValue);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
	}
	
	/**
	 * @param VAR OUT var: variable that is filled with CobolConstant.HighValue characters
	 */
	protected void moveHighValue(VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveHighValue_V:" + var.getSTCheckValue());

		var.set(CobolConstant.HighValue);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
	}
	
	/**
	 * @param VAR OUT var: variable that is filled with CobolConstant.Zero characters
	 */
	protected void moveZero(VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveZero_V:" + var.getSTCheckValue());

		var.set(CobolConstant.Zero);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
	}
	
	/**
	 * @param OUT Cond cond: Force the condition to always evalate as true 
	 */
	protected void moveTrue(Cond cond)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveTrue_cond:" + cond.getSTCheckValue());

		cond.setTrue() ;
	}

	/**
	 * @param IN Var var: Variable whose value is evaluated
	 * @return true if the var contains a numeric value
	 */
	protected boolean isNotNumeric(VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNotNumeric_V:" + var.getSTCheckValue());
		
		boolean b = !var.isNumeric() ;
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		return b;
	}
	
	/**
	 * @param IN Var var: Variable whose value is evaluated
	 * @return true if the var contains a numeric value
	 */
	protected boolean isNumeric(VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNumeric_V:" + var.getSTCheckValue());

		boolean b = var.isNumeric();
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		return b;
	}
	
	/**
	 * @param IN Var var
	 * @return true if the var's value contains a valid alphanumeric value 
	 */
	protected boolean isAlphabetic(VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isAlphabetic_V:" + var.getSTCheckValue());

		boolean b = var.isAlphabetic();
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		return b;
	}
	
	/**
	 * @param IN Var var
	 * @return true if the var's value contains a valid alphanumeric value 
	 */
	protected boolean isNotAlphabetic(VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNotAlphabetic_V:" + var.getSTCheckValue());

		boolean b = !var.isAlphabetic();
		m_tempCache.resetTempIndex(var);
		return b;
	}
	
	/**
	 * @param IN Var var: Variable whose value is tested as been a valid number
	 * @return true if the var contains a valid numeric value
	 */
	public static boolean isNotNumeric(String s)
	{
	//	if(isLog.logFineTrace())
	//		Log.logFineTrace("isNotNumeric_cs:" + s);

		boolean b = !isNumeric(s) ;
		return b;
	}
	
	/**
	 * @param IN Var var: Variable whose value is tested as been a valid number
	 * @return true if the var contains a valid numeric value
	 */
	public static boolean isNumeric(String s)
	{
	//	if(isLog.logFineTrace())
	//		Log.logFineTrace("isNumeric_cs:" + s);

		try
		{
			Integer.parseInt(s) ;
			return true ;
		}
		catch (NumberFormatException e)
		{
			return false ;
		}
	}
	
	public static boolean isAlphabetic(String s)
	{
	//	if(isLog.logFineTrace())
	//		Log.logFineTrace("isAlphabetic_cs:" + s);
		int nLg = s.length();
		for(int n=0; n<nLg; n++)
		{
			char c = s.charAt(n);
			if(!((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == ' '))
				return false;  
		}
		return true;
	}
	
	public void execute(BaseProgram prg)
	{
		if(IsSTCheck)
			Log.logFineDebug("execute_prg:" + prg.getSimpleName());
	}
	
	protected void initialize(Form vSource)
	{
		if(IsSTCheck)
			Log.logFineDebug("initialize_F:" + vSource.getSTCheckValue());
		InitializeCache initializeCache = getProgramManager().getOrCreateInitializeCache(vSource.getVarDef());
		vSource.initialize(initializeCache) ;
		//vSource.initialize();	// Works
	}

	/**
	 * @param OUT Var vSource
	 * Intialize the var group in parameter, whatever it's children types
	 * @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_028.htm#index_x_727 for sample 
	 */
	protected void initialize(VarAndEdit vSource)
	{
		if(IsSTCheck)
			Log.logFineDebug("initialize_V:" + vSource.getSTCheckValue());
		
		if(vSource.m_varTypeId == VarTypeId.VarDefGTypeId)
		{
			InitializeCache initializeCache = getProgramManager().getOrCreateInitializeCache(vSource.getVarDef());
			if(initializeCache.isManaged())
				vSource.initialize(initializeCache);
			else
				vSource.initialize(null);
		}
		else
			vSource.initialize(null);

		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(vSource);
	}

	
//	protected InitializeCache initializeOptInit()
//	{
//		InitializeCache initializeCache = getProgramManager().getOrCreateInitializeCacheAtFirstAppCall();
//		return initializeCache;
//	}
//	
//	protected void initializeOpt(VarAndEdit vSource, InitializeCache initializeCache)
//	{
//		if(IsSTCheck)
//			Log.logFineDebug("initialize_V:" + vSource.getSTCheckValue());
//		
//		//InitializeCache initializeCache = getProgramManager().getOrCreateInitializeCacheAtStackDepth(3);
//		vSource.initialize(initializeCache);
//		if(vSource.isTempVar())
//			m_tempCache.resetTempIndex(vSource);
//	}
	
	protected void initializeReplacingNum(VarAndEdit vSource, int n)
	{
		if(IsSTCheck)
			Log.logFineDebug("initializeReplacingNum_V_n:" + vSource.getSTCheckValue() + ":" + n);

		vSource.initializeReplacingNum(n);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(vSource);
	}
	
	/**
	 * @param OUT Var vSource
	 * @param IN int n
	 * Intialize all numeric children of the group vSource by n
	 * @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_028.htm#index_x_727 for sample 
	 */	

	/**
	 * @param OUT Var vSource
	 * @param IN int n
	 * Intialize all numeric edited children of the group vSource by n
	 * @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_028.htm#index_x_727 for sample 
	 */	
	protected void initializeReplacingNumEdited(VarAndEdit vSource, int n)
	{
		if(IsSTCheck)
			Log.logFineDebug("initializeReplacingNumEdited_V_n:" + vSource.getSTCheckValue() + ":" + n);

		vSource.initializeReplacingNumEdited(n);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(vSource);
	}
	
	/**
	 * @param OUT Var vSource
	 * @param IN double d
	 * Intialize all numeric children of the group vSource by d
	 * @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_028.htm#index_x_727 for sample 
	 */	
	protected void initializeReplacingNum(VarAndEdit vSource, double d)
	{
		if(IsSTCheck)
			Log.logFineDebug("initializeReplacingNum_V_n:" + vSource.getSTCheckValue() + ":" + d);
		// vSource.initializeReplacingNum(d);
		assertIfFalse(false);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(vSource);
	}
	
	/**
	 * @param OUT Var vSource
	 * @param IN String s
	 * Intialize all numeric children of the group vSource by d
	 * @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_028.htm#index_x_727 for sample 
	 */	
	protected void initializeReplacingNum(VarAndEdit vSource, String s)
	{
		if(IsSTCheck)
			Log.logFineDebug("initializeReplacingNum_V_S:" + vSource.getSTCheckValue() + ":" + s);
		vSource.initializeReplacingNum(s);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(vSource);
	}

	/**
	 * @param OUT Var vSource
	 * @param IN double d
	 * Intialize all numeric edited children of the group vSource by d
	 * @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_028.htm#index_x_727 for sample 
	 */	
	protected void initializeReplacingNumEdited(VarAndEdit vSource, double d)
	{
		if(IsSTCheck)
			Log.logFineDebug("initializeReplacingNumEdited_V_d:" + vSource.getSTCheckValue() + ":" + d);

		vSource.initializeReplacingNumEdited(d);	
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(vSource);
	}
	
	/**
	 * @param OUT Var vSource
	 * @param IN String cs
	 * Intialize all alphanumeric children of the group vSource by cs
	 * @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_028.htm#index_x_727 for sample 
	 */	
	protected void initializeReplacingAlphaNum(VarAndEdit vSource, String cs)
	{
		if(IsSTCheck)
			Log.logFineDebug("initializeReplacingAlphaNum_V_cs:" + vSource.getSTCheckValue() + ":" + cs);

		vSource.initializeReplacingAlphaNum(cs);	
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(vSource);
	}

	/**
	 * @param OUT Var vSource
	 * @param IN String cs
	 * Intialize all alphanumeric edited children of the group vSource by cs
	 * @see http://www.helsinki.fi/atk/unix/dec_manuals/cobv27ua/cobrm_028.htm#index_x_727 for sample 
	 */	
	protected void initializeReplacingAlphaNumEdited(VarAndEdit vSource, String cs)
	{
		if(IsSTCheck)
			Log.logFineDebug("initializeReplacingAlphaNumEdited_V_cs:" + vSource.getSTCheckValue() + ":" + cs);

		vSource.initializeReplacingAlphaNumEdited(cs);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(vSource);
	}

	/**
	 * @param IN String cs: String to be concated with followings
	 * @return Internal Concat object, that is used to accumulate the string concatenation
	 */
	protected Concat concat(String cs)
	{
		if(IsSTCheck)
			Log.logFineDebug("concat_cs:" + cs);

		Concat conc = new Concat();
		conc.concat(cs); 
		return conc;
	}

	/**
	 * @param IN Var var: variable whose vaue is to be concated with followings
	 * @return Internal Concat object, that is used to accumulate the string concatenation
	 */
	protected Concat concat(VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("concat_V:" + var.getSTCheckValue());

		Concat conc = new Concat();
		conc.concat(var.getString());
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		return conc;
	}

	protected String concat(VarAndEdit var1, VarAndEdit var2)
	{
		if(IsSTCheck)
			Log.logFineDebug("concat_V_V:" + var1.getSTCheckValue() + ":"+ var2.getSTCheckValue());

		String cs1 = var1.getString() ;
		String cs2 = var2.getString() ;
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1, var2);
		return cs1+cs2;
	}
	protected String concat(VarAndEdit var1, String cs2)
	{
		if(IsSTCheck)
			Log.logFineDebug("concat_V_cs:" + var1.getSTCheckValue() + ":"+ cs2);

		String cs1 = var1.getString() ;
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var1);
		return cs1+cs2;
	}
	protected String concat(String  cs1, VarAndEdit var2)
	{
		if(IsSTCheck)
			Log.logFineDebug("concat_cs_V:" + cs1 + ":" + var2.getSTCheckValue());

		String cs2 = var2.getString() ;
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var2);
		return cs1+cs2;
	}
	protected String concat(String  cs1, String cs2)
	{
		if(IsSTCheck)
			Log.logFineDebug("concat_cs_cs:" + cs1 + ":" + cs2);
		return cs1+cs2;
	}
	protected String digits(VarAndEdit var2)
	{
		if(IsSTCheck)
			Log.logFineDebug("digits_V:" + var2.getSTCheckValue());

		String cs2 = var2.digits();
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var2);
		return cs2;
	}

	/**
	 * @param IN Var var: variable whose vaue is to be concated with followings
	 * @param IN String csDelimiter: String used to find to leading string from var's value to concatenate
	 * @return Internal Concat object, that is used to accumulate the string concatenation
	 */
	protected Concat concatDelimitedBy(VarAndEdit var, String csDelimiter)
	{
		if(IsSTCheck)
			Log.logFineDebug("concatDelimitedBy_V_cs:" + var.getSTCheckValue() + ":" + csDelimiter);

		Concat conc = new Concat();
		conc.concatDelimitedBy(var.getString(), csDelimiter);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		return conc;
	}
	
	/**
	 * @param IN Var var: variable whose vaue is to be concated with followings
	 * @param IN Var varDelimiter: variable used to find to leading string from var's value to concatenate
	 * @return Internal Concat object, that is used to accumulate the string concatenation
	 */
	protected Concat concatDelimitedBy(VarAndEdit var, Var varDelimiter)
	{
		if(IsSTCheck)
			Log.logFineDebug("concatDelimitedBy_V_V:" + var.getSTCheckValue() + ":" + varDelimiter.getSTCheckValue());

		Concat conc = new Concat();
		conc.concatDelimitedBy(var.getString(), varDelimiter.getString());
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var, varDelimiter);
		return conc;
	}

	/**
	 * @param IN String csVar: String whose vaue is to be concated with followings
	 * @param IN String csDelimiter: String used to find to leading string from var's value to concatenate
	 * @return Internal Concat object, that is used to accumulate the string concatenation
	 */
	protected Concat concatDelimitedBy(String csVar, String csDelimiter)
	{
		if(IsSTCheck)
			Log.logFineDebug("concatDelimitedBy_cs_cs:" + csVar + ":" + csDelimiter);

		Concat conc = new Concat();
		conc.concatDelimitedBy(csVar, csDelimiter);
		return conc;
	}

	/**
	 * @param IN Var var: variable whose value, up to the first space char, is to be concated with followings
	 * @return Internal Concat object, that is used to accumulate the string concatenation
	 */
	protected Concat concatDelimitedBySpaces(VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("concatDelimitedBySpaces_V:" + var.getSTCheckValue());

		Concat conc = new Concat();
		conc.concatDelimitedBySpaces(var.getString());
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		return conc;
	}

	/**
	 * @param IN String cs: String whose value, up to the first space char, is to be concated with followings
	 * @return Internal Concat object, that is used to accumulate the string concatenation
	 */
	protected Concat concatDelimitedBySpaces(String cs)
	{
		if(IsSTCheck)
			Log.logFineDebug("concatDelimitedBySpaces_cs:" + cs);

		Concat conc = new Concat();
		conc.concatDelimitedBySpaces(cs);
		return conc;
	}
	
	/**
	 * @param IN Var var: var's value that is to be concated with followings one
	 * @return Internal Concat object, that is used to accumulate the string concatenation
	 */
	protected Concat concatDelimitedBySize(VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("concatDelimitedBySize_V:" + var.getSTCheckValue());

		Concat conc = new Concat();
		conc.concatDelimitedBySize(var.getString());
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		return conc;
	}

	/**
	 * @param IN String cs: String whose whole value, whatever it's end, is to be concated with followings
	 * @return Internal Concat object, that is used to accumulate the string concatenation
	 */
	protected Concat concatDelimitedBySize(String cs)
	{
		if(IsSTCheck)
			Log.logFineDebug("concatDelimitedBySize_cs:" + cs);

		Concat conc = new Concat();
		conc.concatDelimitedBySize(cs);
		return conc;
	}
	
	protected void moveReferenceTo(Pointer v, Pointer y)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveReferenceTo_Ptr_Ptr:");

		//TODO fake function moveReferenceTo
	}
	
	/**
	 * @param IN/OUT Map map
	 * Fill all forms of a map with low value 
	 */
	protected void moveLowValue(Map map)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveLowValue_Map");

		//map.move(CobolConstant.LowValue);
		assertIfFalse(false);
	}

	/**
	 * @param IN Var var
	 * @return a pointer on var
	 * Used internally
	 */
	protected Pointer addressOf(VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("addressOf_V:"+var.getSTCheckValue());

		return new Pointer(var) ;
	}

	/**
	 * @param var Source variable to unstring
	 * @return Unstring objet, used to get string chunks through it's methods
	 */
	protected Unstring unstring(VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("unstring_V:"+var.getSTCheckValue());

		Unstring u = new Unstring(var);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		return u;
	}
	
	/**
	 * @param Var v: String whose substring is to be filled with 0's
	 * @param int nOffsetPosition Start position (begining at 1) of the substring to fill 
	 * @param int nNbChar Number of chars to fill
	 * Fill the substring of v with 0's, from position start up to nNbChar characters 
	 */
	protected void moveSubStringZero(VarAndEdit v, int nOffsetPosition, int nNbChar)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveSubStringZero_V_n_n:"+v.getSTCheckValue() + ":" + nOffsetPosition + ":" + nNbChar);

		// Fill nNbCharToSet 0 at based 1 position nPosition
		v.setRepeatingCharAtOffsetFromStart(CobolConstant.Zero, nOffsetPosition-1, nNbChar);	// Fill with a 0 base index
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(v);
	}
	
	/**
	 * @param Var v: String whose substring is to be filled with space chars
	 * @param int nOffsetPosition Start position (begining at 1) of the substring to fill 
	 * @param int nNbChar Number of chars to fill
	 * Fill the substring of v with spaces, from position start up to nNbChar characters 
	 */	
	protected void moveSubStringSpace(VarAndEdit v, int nOffsetPosition, int nNbChar)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveSubStringSpace_V_n_n:"+v.getSTCheckValue() + ":" + nOffsetPosition + ":" + nNbChar);

		v.setRepeatingCharAtOffsetFromStart(CobolConstant.Space, nOffsetPosition-1, nNbChar);	// Fill with a 0 base index
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(v);
	}

	/**
	 * @param Var v: String whose substring is to be filled with low value chars
	 * @param int nOffsetPosition Start position (begining at 1) of the substring to fill 
	 * @param int nNbChar Number of chars to fill
	 * Fill the substring of v with low value, from position start up to nNbChar characters 
	 */
	protected void moveSubStringLowValue(VarAndEdit v, int nOffsetPosition, int nNbChar)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveSubStringLowValue_V_n_n:"+v.getSTCheckValue() + ":" + nOffsetPosition + ":" + nNbChar);

		v.setRepeatingCharAtOffsetFromStart(CobolConstant.LowValue, nOffsetPosition-1, nNbChar);	// Fill with a 0 base index
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(v);
	}
	
	protected void moveSubStringLowValue(VarAndEdit v, Var vOffsetPosition, int nNbChar)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveSubStringLowValue_V_V_n:"+v.getSTCheckValue() + ":" + vOffsetPosition.getSTCheckValue() + ":" + nNbChar);

		int nOffsetPosition = vOffsetPosition.getInt() ;
		v.setRepeatingCharAtOffsetFromStart(CobolConstant.LowValue, nOffsetPosition-1, nNbChar);	// Fill with a 0 base index
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(v, vOffsetPosition);
	}


	/**
	 * @param Var v: String whose substring is to be filled with high value chars
	 * @param int nOffsetPosition Start position (begining at 1) of the substring to fill 
	 * @param int nNbChar Number of chars to fill
	 * Fill the substring of v with high value, from position start up to nNbChar characters 
	 */
	protected void moveSubStringHighValue(VarAndEdit v, int nOffsetPosition, int nNbChar)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveSubStringHighValue_V_n_n:"+v.getSTCheckValue() + ":" + nOffsetPosition + ":" + nNbChar);

		v.setRepeatingCharAtOffsetFromStart(CobolConstant.HighValue, nOffsetPosition-1, nNbChar);	// Fill with a 0 base index
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(v);
	}
	
	protected void moveSubStringHighValue(VarAndEdit v, MathBase nOffsetPosition, MathBase nNbChar)
	{
		moveSubStringHighValue(v, nOffsetPosition.m_d.intValue(), nNbChar.m_d.intValue());
	}
	
	protected void moveSubStringHighValue(VarAndEdit v, Var vOffsetPosition, int nNbChar)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveSubStringHighValue_V_V_n:"+v.getSTCheckValue() + ":" + vOffsetPosition.getSTCheckValue() + ":" + nNbChar);

		int nOffsetPosition = vOffsetPosition.getInt() ;
		v.setRepeatingCharAtOffsetFromStart(CobolConstant.HighValue, nOffsetPosition-1, nNbChar);	// Fill with a 0 base index
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(v, vOffsetPosition);
	}


	/**
	 * @param Var v: String whose substring is to be filled with space chars
	 * @param Var varOffsetPosition: Start position (begining at 1) of the substring to fill 
	 * @param int nNbChar Number of chars to fill
	 * Fill the substring of v with spaces, from position start up to nNbChar characters 
	 */
	protected void moveSubStringSpace(VarAndEdit v, Var varOffsetPosition, int nNbChar)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveSubStringSpace_V_V_n:"+v.getSTCheckValue() + ":" + varOffsetPosition.getSTCheckValue() + ":" + nNbChar);

		int nOffsetPosition = varOffsetPosition.getInt();
		v.setRepeatingCharAtOffsetFromStart(CobolConstant.Space, nOffsetPosition-1, nNbChar);	// Fill with a 0 base index
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(v, varOffsetPosition);
	}
	
	/**
	 * @param Var v: String whose substring is to be filled with space chars
	 * @param Var varOffsetPosition: Start position (begining at 1) of the substring to fill 
	 * @param int nNbChar Number of chars to fill
	 * Fill the substring of v with spaces, from position start up to nNbChar characters 
	 */
	protected void moveSubStringSpace(VarAndEdit v, Var varOffsetPosition, Var nNbChar)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveSubStringSpace_V_V_V:"+v.getSTCheckValue() + ":" + varOffsetPosition.getSTCheckValue() + ":" + nNbChar.getSTCheckValue());

		int nOffsetPosition = varOffsetPosition.getInt();
		v.setRepeatingCharAtOffsetFromStart(CobolConstant.Space, nOffsetPosition-1, nNbChar.getInt());	// Fill with a 0 base index
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(v, varOffsetPosition);
	}

	
	/**
	 * @param Var varDest: Destination variable 
	 * @param int nOffsetPosition: Start position into to source variable
	 * @param int nNbChar: Number of char to extract form source variable
	 * @param Var varValue: Source variable
	 * Fill the destination variable with the substring of source variable, starting from position nOffsetPosition, and containing up to nNbChar 
	 */
	protected void setSubString(VarAndEdit varDest, int nOffsetPosition, int nNbChar, VarAndEdit varValue)
	{
		if(IsSTCheck)
			Log.logFineDebug("setSubString_V_n_n_V:"+varDest.getSTCheckValue() + ":" + nOffsetPosition + ":" + nNbChar + ":" + varValue.getSTCheckValue());

		String csValue = varValue.getString();
		varDest.setStringAtPosition(csValue, nOffsetPosition-1, nNbChar);	// Fill with a 0 base index
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest, varValue);
	}

	protected void setSubString(VarAndEdit varDest, int nOffsetPosition, int nNbChar, int nValue)
	{
		if(IsSTCheck)
			Log.logFineDebug("setSubString_V_n_n_n:"+varDest.getSTCheckValue() + ":" + nOffsetPosition + ":" + nNbChar + ":" + nValue);

		String csValue = String.valueOf(nValue) ;
		while(csValue.length() < nNbChar)
			csValue = "0" + csValue;	// Prefixe by leading 0's
		varDest.setStringAtPosition(csValue, nOffsetPosition-1, nNbChar);	// Fill with a 0 base index
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest);
	}

	
	/**
	 * @param Var varDest: Destination variable 
	 * @param int nOffsetPosition: Start position into to source variable
	 * @param Var varNbChar: Number of char to extract form source variable
	 * @param Var varValue: Source variable
	 * Fill the destination variable with the substring of source variable, starting from position nOffsetPosition, and containing up to nNbChar 
	 */
	protected void setSubString(VarAndEdit varDest, int nOffsetPosition, Var varNbChar, VarAndEdit varValue)
	{
		if(IsSTCheck)
			Log.logFineDebug("setSubString_V_n_V_V:"+varDest.getSTCheckValue() + ":" + nOffsetPosition + ":" + varNbChar.getSTCheckValue() + ":" + varValue.getSTCheckValue());

		String csValue = varValue.getString();
		int nNbChar = varNbChar.getInt();
		varDest.setStringAtPosition(csValue, nOffsetPosition-1, nNbChar);	// Fill with a 0 base index
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest, varNbChar);
	}	
	
	protected void setSubString(VarAndEdit varDest, Var vOffsetPosition, Var varNbChar, Var varValue)
	{
		if(IsSTCheck)
			Log.logFineDebug("setSubString_V_V_V_V:"+varDest.getSTCheckValue() + ":" + vOffsetPosition.getSTCheckValue() + ":" + varNbChar.getSTCheckValue() + ":" + varValue.getSTCheckValue());

		String csValue = varValue.getString();
		int nNbChar = varNbChar.getInt();
		int nOffsetPosition = vOffsetPosition.getInt() ; 
		varDest.setStringAtPosition(csValue, nOffsetPosition-1, nNbChar);	// Fill with a 0 base index
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest, vOffsetPosition, varNbChar, varValue);
	}
	
	protected void setSubString(VarAndEdit varDest, Var vOffsetPosition, MathBase nNbChar, Var varValue)
	{
		if(IsSTCheck)
			Log.logFineDebug("setSubString_V_V_M_V:"+varDest.getSTCheckValue() + ":" + vOffsetPosition.getSTCheckValue() + ":" + nNbChar.getSTCheckValue() + ":" + varValue.getSTCheckValue());

		String csValue = varValue.getString();
		varDest.setStringAtPosition(csValue, vOffsetPosition.getInt()-1, nNbChar.m_d.intValue());	// Fill with a 0 base index
		
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest, vOffsetPosition, varValue);
	}
	
	/**
	 * @param Var varDest: Destination variable 
	 * @param int nOffsetPosition: Startposition into to source variable
	 * @param Var varNbChar: Number of char to extract form source variable
	 * @param String csValue: Source string
	 * Fill the destination variable with the substring of source variable, starting from position nOffsetPosition, and containing up to nNbChar 
	 */
	protected void setSubString(VarAndEdit varDest, Var nOffsetPosition, Var nNbChar, String csValue)
	{
		if(IsSTCheck)
			Log.logFineDebug("setSubString_V_V_V_cs:"+varDest.getSTCheckValue() + ":" + nOffsetPosition.getSTCheckValue() + ":" + nNbChar.getSTCheckValue() + ":" + csValue);

		varDest.setStringAtPosition(csValue, nOffsetPosition.getInt()-1, nNbChar.getInt());	// Fill with a 0 base index
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest, nOffsetPosition, nNbChar);
	}	

	/**
	 * @param Var varDest: Destination variable 
	 * @param int nOffsetPosition: Startposition into to source variable
	 * @param Var varNbChar: Number of char to extract form source variable
	 * @param String csValue: Source string
	 * Fill the destination variable with the substring of source variable, starting from position nOffsetPosition, and containing up to nNbChar 
	 */
	protected void setSubString(VarAndEdit varDest, MathBase nOffsetPosition, Var nNbChar, String csValue)
	{
		if(IsSTCheck)
			Log.logFineDebug("setSubString_V_M_V_cs:"+varDest.getSTCheckValue() + ":" + nOffsetPosition.getSTCheckValue() + ":" + nNbChar.getSTCheckValue() + ":" + csValue);

		varDest.setStringAtPosition(csValue, nOffsetPosition.m_d.intValue()-1, nNbChar.getInt());	// Fill with a 0 base index
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest, nNbChar);
	}
	
	protected void setSubString(VarAndEdit varDest, MathBase nOffsetPosition, int nNbChar, String csValue)
	{
		setSubString(varDest, nOffsetPosition.m_d.intValue(), nNbChar, csValue);
	}
	
	/**
	 * @param Var varDest: Destination variable 
	 * @param int nOffsetPosition: Start position into to source variable
	 * @param int nNbChar: Number of char to extract form source variable
	 * @param String csValue: Source string
	 * Fill the destination variable with the substring of source variable, starting from position nOffsetPosition, and containing up to nNbChar 
	 */
	protected void setSubString(VarAndEdit varDest, int nOffsetPosition, int nNbChar, String csValue)
	{
		if(IsSTCheck)
			Log.logFineDebug("setSubString_V_n_n_cs:"+varDest.getSTCheckValue() + ":" + nOffsetPosition + ":" + nNbChar + ":" + csValue);

		varDest.setStringAtPosition(csValue, nOffsetPosition-1, nNbChar);	// Fill with a 0 base index
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest);
	}	
	
	/**
	 * @param Var varDest: Destination variable 
	 * @param int nOffsetPosition: Start position into to source variable
	 * @param int nNbChar: Number of char to extract form source variable
	 * @param String csValue: Source string
	 * Fill the destination variable with the substring of source variable, starting from position nOffsetPosition, and containing up to nNbChar 
	 */
	protected void setSubStringAll(VarAndEdit varDest, int nOffsetPosition, int nNbChar, String csPattern)
	{
		if(IsSTCheck)
			Log.logFineDebug("setSubStringAll_V_n_n_cs:"+varDest.getSTCheckValue() + ":" + nOffsetPosition + ":" + nNbChar + ":" + csPattern);
		String csValue = "";
		while(csValue.length() < nNbChar)
		{
			csValue += csPattern;
		}
		if(csValue.length() > nNbChar)
			csValue = csValue.substring(0, nNbChar);
		varDest.setStringAtPosition(csValue, nOffsetPosition-1, nNbChar);	// Fill with a 0 base index
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest);
	}
	
	/**
	 * @param Var varDest: Destination variable 
	 * @param Var varOffsetPosition: Start position into to source variable
	 * @param int nNbChar: Number of char to extract form source variable
	 * @param String csValue: Source string
	 * Fill the destination variable with the substring of source variable, starting from position nOffsetPosition, and containing up to nNbChar 
	 */
	protected void setSubStringAll(VarAndEdit varDest, Var varOffsetPosition, int nNbChar, String csPattern)
	{
		if(IsSTCheck)
			Log.logFineDebug("setSubStringAll_V_V_n_cs:"+varDest.getSTCheckValue() + ":" + varOffsetPosition.getInt() + ":" + nNbChar + ":" + csPattern);
		String csValue = "";
		while(csValue.length() < nNbChar)
		{
			csValue += csPattern;
		}
		if(csValue.length() > nNbChar)
			csValue = csValue.substring(0, nNbChar);
		varDest.setStringAtPosition(csValue, varOffsetPosition.getInt()-1, nNbChar);	// Fill with a 0 base index
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest, varOffsetPosition);
	}
	
	/**
	 * @param Var varDest: Destination variable 
	 * @param int nOffsetPosition: Start position into to source variable
	 * @param Var nNbChar: Number of char to extract form source variable
	 * @param String csValue: Source string
	 * Fill the destination variable with the substring of source variable, starting from position nOffsetPosition, and containing up to nNbChar 
	 */
	protected void setSubStringAll(VarAndEdit varDest, int nOffsetPosition, Var varNbChar, String csPattern)
	{
		if(IsSTCheck)
			Log.logFineDebug("setSubStringAll_V_n_V_cs:"+varDest.getSTCheckValue() + ":" + nOffsetPosition + ":" + varNbChar.getInt() + ":" + csPattern);
		String csValue = "";
		while(csValue.length() < varNbChar.getInt())
		{
			csValue += csPattern;
		}
		if(csValue.length() > varNbChar.getInt())
			csValue = csValue.substring(0, varNbChar.getInt());
		varDest.setStringAtPosition(csValue, nOffsetPosition-1, varNbChar.getInt());	// Fill with a 0 base index
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest, varNbChar);
	}
	
	/**
	 * @param Var varDest: Destination variable 
	 * @param Var varOffsetPosition: Start position into to source variable
	 * @param Var varChar: Number of char to extract form source variable
	 * @param String csValue: Source string
	 * Fill the destination variable with the substring of source variable, starting from position nOffsetPosition, and containing up to nNbChar 
	 */
	protected void setSubStringAll(VarAndEdit varDest, Var varOffsetPosition, Var varNbChar, String csPattern)
	{
		if(IsSTCheck)
			Log.logFineDebug("setSubStringAll_V_n_n_cs:"+varDest.getSTCheckValue() + ":" + varOffsetPosition.getInt() + ":" + varNbChar.getInt() + ":" + csPattern);
		String csValue = "";
		while(csValue.length() < varNbChar.getInt())
		{
			csValue += csPattern;
		}
		if(csValue.length() > varNbChar.getInt())
			csValue = csValue.substring(0, varNbChar.getInt());
		varDest.setStringAtPosition(csValue, varOffsetPosition.getInt()-1, varNbChar.getInt());	// Fill with a 0 base index
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest, varOffsetPosition, varNbChar);
	}
	
	/**
	 * @param Var varDest: Destination variable 
	 * @param int nOffsetPosition: Startposition into to source variable
	 * @param Var varNbChar: Number of char to extract form source variable
	 * @param String csValue: Source string
	 * Fill the destination variable with the substring of source variable, starting from position nOffsetPosition, and containing up to nNbChar 
	 */
	protected void setSubString(VarAndEdit varDest, int nOffsetPosition, Var nNbChar, String csValue)
	{
		if(IsSTCheck)
			Log.logFineDebug("setSubString_V_n_V_cs:"+varDest.getSTCheckValue() + ":" + nOffsetPosition + ":" + nNbChar.getSTCheckValue() + ":" + csValue);

		varDest.setStringAtPosition(csValue, nOffsetPosition-1, nNbChar.getInt());	// Fill with a 0 base index
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest, nNbChar);
	}	
	
	/**
	 * @param Var varDest: Destination variable 
	 * @param Var varOffsetPosition: Start position into to source variable
	 * @param int nNbChar: Number of char to extract form source variable
	 * @param String csValue: Source string
	 * Fill the destination variable with the substring of source variable, starting from position nOffsetPosition, and containing up to nNbChar 
	 */
	protected void setSubString(VarAndEdit varDest, Var varOffsetPosition, int nNbChar, String csValue)
	{
		if(IsSTCheck)
			Log.logFineDebug("setSubString_V_V_n_cs" + varDest.getSTCheckValue() + ":" + varOffsetPosition.getSTCheckValue() + ":" + nNbChar + ":" + csValue);

		int nOffsetPosition = varOffsetPosition.getInt();
		varDest.setStringAtPosition(csValue, nOffsetPosition-1, nNbChar);	// Fill with a 0 base index
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest, varOffsetPosition);
	}	
	
	/**
	 * @param Var varDest: Destination variable 
	 * @param Var varOffsetPosition: Start position into to source variable
	 * @param int nNbChar: Number of char to extract form source variable
	 * @param Var varValue: Source variable
	 * Fill the destination variable with the substring of source variable, starting from position nOffsetPosition, and containing up to nNbChar 
	 */
	protected void setSubString(VarAndEdit varDest, Var varOffsetPosition, int nNbChar, VarAndEdit varValue)
	{
		if(IsSTCheck)
			Log.logFineDebug("setSubString_V_V_n_V" + varDest.getSTCheckValue() + ":" + varOffsetPosition.getSTCheckValue() + ":" + nNbChar + ":" + varValue.getSTCheckValue());

		String csValue = varValue.getString();
		int nOffsetPosition = varOffsetPosition.getInt();
		varDest.setStringAtPosition(csValue, nOffsetPosition-1, nNbChar);	// Fill with a 0 base index
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varDest, varOffsetPosition, varValue);
	}	
	
	
	/**
	 * @return void
	 * Placeholder to identify the SLQCursor variable declaration. Can be omitted
	 */
	protected DataSection sqlCursorSection()
	{
		if(IsSTCheck)
			Log.logFineDebug("sqlCursorSection");

		//TODO fake function sqlCursorSection
		return null ;
	}
	
	/**
	 * @param Var var: Source variable on which doing inspect replacing operations 
	 * @return InspectReplacing object, enabling options
	 * see class InspectReplacing 
	 */
	protected InspectReplacing inspectReplacing(VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("inspectReplacing_V:" + var.getSTCheckValue());

		InspectReplacing inspect = new InspectReplacing(var);
		return inspect;  
	}
	
	/**
	 * @param Var var: Source variable on which doing inspect tallying operations 
	 * @return InspectTallying object, enabling options
	 * see class InspectTallying 
	 */
	protected InspectTallying inspectTallying(VarAndEdit var)
	{
		if(IsSTCheck)
			Log.logFineDebug("inspectTallying_V:" + var.getSTCheckValue());

		InspectTallying inspect = new InspectTallying(var);
		return inspect;  
	}

	protected InspectTallying inspectTallying(String cs)
	{
		if(IsSTCheck)
			Log.logFineDebug("inspectTallying_cs:" + cs);

		InspectTallying inspect = new InspectTallying(cs);
		return inspect;  
	}		
	
	/**
	 * @param String csSource: Source string
	 * @param int nStart: 1 based start position into source string
	 * @param int nNbChars: Number of chars to extract
	 * @return String Subtring of the source string, starting form position start, up to nNbChars chars
	 */
	protected String subString(String csSource, MathBase nStart, MathBase nNbChars)
	{
		if(IsSTCheck)
			Log.logFineDebug("subString_cs_M_M:" + csSource + ":" + nStart.getSTCheckValue() + ":" + nNbChars.getSTCheckValue());

		return subString(csSource, nStart.m_d.intValue(), nNbChars.m_d.intValue());
	}
	
	/**
	 * @param String csSource: Source string
	 * @param int nStart: 1 based start position into source string
	 * @param int nNbChars: Number of chars to extract
	 * @return String Subtring of the source string, starting form position start, up to nNbChars chars
	 */
	protected String subString(Var varSource, MathBase nStart, MathBase nNbChars)
	{
		if(IsSTCheck)
			Log.logFineDebug("subString_V_M_M:" + varSource.getSTCheckValue() + ":" + nStart.getSTCheckValue() + ":" + nNbChars.getSTCheckValue());

		
		String cs = subString(varSource.getString(), nStart.m_d.intValue(), nNbChars.m_d.intValue());
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varSource);
		return cs;
	}
	
	/**
	 * @param String csSource: Source string
	 * @param int nStart: 1 based start position into source string
	 * @param int nNbChars: Number of chars to extract
	 * @return String Subtring of the source string, starting form position start, up to nNbChars chars
	 */
	protected String subString(Var varSource, MathBase nStart, int nNbChars)
	{
		if(IsSTCheck)
			Log.logFineDebug("subString_V_M_n:" + varSource.getSTCheckValue() + ":" + nStart.getSTCheckValue() + ":" + nNbChars);

		String cs = subString(varSource.getString(), nStart.m_d.intValue(), nNbChars);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varSource);
		return cs;
	}
	
	/**
	 * @param String csSource: Source string
	 * @param int nStart: 1 based start position into source string
	 * @param int nNbChars: Number of chars to extract
	 * @return String Subtring of the source string, starting form position start, up to nNbChars chars
	 */
	protected String subString(Var varSource, Var varStart, MathBase nNbChars)
	{
		if(IsSTCheck)
			Log.logFineDebug("subString_V_V_M:" + varSource.getSTCheckValue() + ":" + varStart.getSTCheckValue() + ":" + nNbChars.getSTCheckValue());

		String cs = subString(varSource.getString(), varStart.getInt(), nNbChars.m_d.intValue());
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varSource, varStart);
		return cs;
	}
	
	/**
	 * @param String csSource: Source string
	 * @param int nStart: 1 based start position into source string
	 * @param int nNbChars: Number of chars to extract
	 * @return String Subtring of the source string, starting form position start, up to nNbChars chars
	 */
	protected String subString(Var varSource, Var varStart, Var nNbChars)
	{
		if(IsSTCheck)
			Log.logFineDebug("subString_V_V_V:" + varSource.getSTCheckValue() + ":" + varStart.getSTCheckValue() + ":" + nNbChars.getSTCheckValue());

		String cs = subString(varSource.getString(), varStart.getInt(), nNbChars.getInt());
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varSource, varStart, nNbChars);
		return cs;
	}
	
	/**
	 * @param String csSource: Source string
	 * @param int nStart: 1 based start position into source string
	 * @param int nNbChars: Number of chars to extract
	 * @return String Subtring of the source string, starting form position start, up to nNbChars chars
	 */
	protected String subString(String csSource, int nStart, int nNbChars)
	{
		if(IsSTCheck)
			Log.logFineDebug("subString_cs_n_n:" + csSource + ":" + nStart + ":" + nNbChars);

		try
		{
			if (nNbChars > (csSource.length() - nStart + 1))
			{
				nNbChars = csSource.length() - nStart + 1;
			}
			String csFound = csSource.substring(nStart-1, nStart-1+nNbChars);
			return csFound;
		}
		catch (IndexOutOfBoundsException  e)
		{
			return "" ;
		}
	}
	
	/**
	 * @param Var varSource: Source variable
	 * @param int nStart: 1 based start position into source string
	 * @param int nNbChars: Number of chars to extract
	 * @return String Subtring of varSource's string value, starting form position start, up to nNbChars chars
	 */
	protected String subString(VarAndEdit varSource, int nStart, int nNbchars)
	{
		if(IsSTCheck)
			Log.logFineDebug("subString_V_n_n:" + varSource.getSTCheckValue() + ":" + nStart + ":" + nNbchars);

		String cs = subString(varSource.getString(), nStart, nNbchars);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varSource);
		return cs;
	}	
	
	/**
	 * @param Var varSource: Source variable
	 * @param Var varStart: 1 based start position into source string
	 * @param int nNbChars: Number of chars to extract
	 * @return String Subtring of varSource's string value, starting form position start, up to nNbChars chars
	 */
	protected String subString(VarAndEdit varSource, Var varStart, int nNbchars)
	{
		if(IsSTCheck)
			Log.logFineDebug("subString_V_V_n:" + varSource.getSTCheckValue() + ":" + varStart.getSTCheckValue() + ":" + nNbchars);

		String cs = subString(varSource.getString(), varStart.getInt(), nNbchars);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varSource, varStart);
		return cs;
	}
		
	/**
	 * @param Var varSource: Source variable
	 * @param int nStart: 1 based start position into source string
	 * @param Var varNbChars: Number of chars to extract
	 * @return String Subtring of varSource's string value, starting form position start, up to nNbChars chars
	 */	
	protected String subString(VarAndEdit varSource, int nStart, Var varNbchars)
	{
		if(IsSTCheck)
			Log.logFineDebug("subString_V_n_V:" + varSource.getSTCheckValue() + ":" + nStart + ":" + varNbchars.getSTCheckValue());
		
		String cs = subString(varSource.getString(), nStart, varNbchars.getInt());
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varSource, varNbchars);
		return cs;
	}	

	// info link : http://www.caliberdt.com/tips/sqlcode.htm
	protected int getSQLCode()
	{
		if(IsSTCheck)
			Log.logFineDebug("getSQLCode");

		return m_BaseProgramManager.getSQLStatus().getSQLCode() ;
	}
	
	protected int getSQLDiagnosticCode(int n)
	{
		if(IsSTCheck)
			Log.logFineDebug("getSQLDiagnosticCode_n" + n);

		return m_BaseProgramManager.getSQLStatus().getSQLDiagnosticCode(n) ;
	}
	
	protected int getSQLDiagnosticCode(Var v)
	{
		if(IsSTCheck)
			Log.logFineDebug("getSQLDiagnosticCode_V" + v.getSTCheckValue());

		int n = m_BaseProgramManager.getSQLStatus().getSQLDiagnosticCode(v.getInt()) ;
		if(m_bUsedTempVarOrCStr) 
			m_tempCache.resetTempIndex(v);
		return n;
	}	
	
	protected boolean isSQLCode(int n)
	{
		if(IsSTCheck)
			Log.logFineDebug("isSQLCode_n" + n);

		int c = m_BaseProgramManager.getSQLStatus().getSQLCode() ;
		return  c == n ;
	}		
	
	protected boolean isNotSQLCode(int n)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNotSQLCode_n" + n);

		int c = m_BaseProgramManager.getSQLStatus().getSQLCode() ;
		return  c != n ;
	}
	
	protected void resetSQLCode(int n)
	{
		if(IsSTCheck)
			Log.logFineDebug("resetSQLCode_n" + n);
	
		m_BaseProgramManager.getSQLStatus().setSQLCode(n) ;
	}
	
	protected void resetSQLCode(MathBase mathBase)
	{
		if(IsSTCheck)
			Log.logFineDebug("resetSQLCode_M" + mathBase.getSTCheckValue());
	
		int n = NumberParser.getAsInt(mathBase.getSTCheckValue()) ;
		m_BaseProgramManager.getSQLStatus().setSQLCode(n) ;
	}
	
	protected void resetSQLCode(String cs)
	{
		if(IsSTCheck)
			Log.logFineDebug("resetSQLCode_cs" + cs);

		int n = NumberParser.getAsInt(cs) ;
		m_BaseProgramManager.getSQLStatus().setSQLCode(n) ;
	}
	
	protected void resetSQLCode(Var v)
	{
		if(IsSTCheck)
			Log.logFineDebug("resetSQLCode_V" + v.getSTCheckValue());

		int n = v.getInt() ;
		m_BaseProgramManager.getSQLStatus().setSQLCode(n) ;
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(v);
	}

	
	/**
	 * @param Var var
	 * @return Number of bytes ureserved for storage of the var, as defined in working storage section
	 */
	protected int lengthOf(VarAndEdit v)
	{
		if(IsSTCheck)
			Log.logFineDebug("lengthOf_V" + v.getSTCheckValue());

		int n = v.getBodySize();
		if(m_bUsedTempVarOrCStr) 
			m_tempCache.resetTempIndex(v);
		return n;
	}
	
	protected CopyReplacing replacing(int nOldLevel, int nNewLevel) 
	{
		if(IsSTCheck)
			Log.logFineDebug("replacing_n_n" + nOldLevel + ":" + nNewLevel);
	
		CopyReplacing copyReplacing = new CopyReplacing(nOldLevel, nNewLevel);
		return copyReplacing;
	};	
	
	/**
	 * @param Var varDest: Destination variable
	 * @param String csValue: Semantic context value
	 * Associates the semantic context value csVlaue, to the variable varDest. It is also associated to all variables that share it's position and length in storage buffer (redefines, ...)
	 */
	public void setSemanticContextValue(Var varDest, String csValue)
	{
		if(IsSTCheck)
			Log.logFineDebug("setSemanticContextValue_V_cs" + varDest.getSTCheckValue() + ":" + csValue);
		varDest.setSemanticContextValue(csValue);
		if(m_bUsedTempVarOrCStr) 
			m_tempCache.resetTempIndex(varDest);
	}
	
	public void setSemanticContextValue(Edit editDest, String csValue)
	{
		if(IsSTCheck)
			Log.logFineDebug("setSemanticContextValue_E_cs" + editDest.getSTCheckValue() + ":" + csValue);
		editDest.setSemanticContextValue(csValue);
		if(m_bUsedTempVarOrCStr) 
			m_tempCache.resetTempIndex(editDest);
	}
	
	/**
	 * @param Var varSource: Source variable whose semantic value is to be found
	 * @return Semantic value associated with the source variable.
	 */
	public String getSemanticContextValue(Var varSource)
	{
		if(IsSTCheck)
			Log.logFineDebug("getSemanticContextValue_V" + varSource.getSTCheckValue());

		String cs = varSource.getSemanticContextValue();
		if(m_bUsedTempVarOrCStr) 
			m_tempCache.resetTempIndex(varSource);
		return cs;
	}
	
	public String getSimpleName()
	{		
		return m_csSimpleName;
	}
	
	private void initNames()
	{
		m_csSimpleName = toString();
		int n = m_csSimpleName.indexOf("@");
		if(n != -1)
			m_csSimpleName = m_csSimpleName.substring(0, n);
	}
	
	//private String m_csDecoratedName = null;		
	public String m_csSimpleName = null;
	

//  CV 27-04-05 : unused
//	protected int getLength(Edit e)
//	{
//		if(isLog.logFineTrace())
//			Log.logFineTrace("getLength_E" + e.getSTCheckValue());
//
//		return e.getLength() ;
//	}
	
	protected String currentDate()
	{
		if(IsSTCheck)
			Log.logFineDebug("currentDate");

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSZ") ;
		Date d = new Date() ;
		String cs = format.format(d)  ;
		cs = cs.subSequence(0, 16) + cs.substring(17) ;
		return cs ;
	}
	
	protected int getNbOccurs(VarAndEdit v)
	{
		if(IsSTCheck)
			Log.logFineDebug("getNbOccurs_V" + v.getSTCheckValue());
		
		int n = v.getNbOccurs() ;
		if(m_bUsedTempVarOrCStr) 
			m_tempCache.resetTempIndex(v);
		return n;
	}
	
//	public CSession getCurrentSession()
//	{
//		if(IsSTCheck)
//			Log.logFineDebug("getCurrentSession");
//		return m_ProgramManager.getCurrentSession();
//	}
	
	public Console console()
	{
		if(IsSTCheck)
			Log.logFineDebug("console");
		return new Console() ;
	}
	
	public CSQLStatus sqlRollback()
	{
		if(IsSTCheck)
			Log.logFineDebug("sqlRollback");
		return m_BaseProgramManager.sqlRollback();
	}
		
	public CSQLStatus sqlCommit()
	{
		if(IsSTCheck)
			Log.logFineDebug("sqlCommit");
		return m_BaseProgramManager.sqlCommit();
	}	

	public void openOutput(FileDescriptor fileDesc)
	{
		if(IsSTCheck)
			Log.logFineDebug("openOutput_FD" + fileDesc.toString());
		fileDesc.openOutput();
	}
	
	public void openInputOutput(FileDescriptor fileDesc)
	{
		if(IsSTCheck)
			Log.logFineDebug("openInputOutput_FD" + fileDesc.toString());
		fileDesc.openInputOutput();
	}
	
	public void openInput(FileDescriptor fileDesc)
	{
		if(IsSTCheck)
			Log.logFineDebug("openInputFD" + fileDesc.toString());
		fileDesc.openInput();
	}
	
	public void openExtend(FileDescriptor fileDesc)
	{
		if(IsSTCheck)
			Log.logFineDebug("openExtendFD" + fileDesc.toString());
		fileDesc.openExtend();
	}
	
	public void close(FileDescriptor fileDesc)
	{
		if(IsSTCheck)
			Log.logFineDebug("close_FD" + fileDesc.toString());
		fileDesc.close();
	}
	
	public void write(FileDescriptor fileDesc)
	{
		if(IsSTCheck)
			Log.logFineDebug("write_FD" + fileDesc.toString());
		fileDesc.write();
	}
	
	public void writeFrom(FileDescriptor fileDesc, Var varFrom)
	{
		if(IsSTCheck)
			Log.logFineDebug("write_FD" + fileDesc.toString());
		fileDesc.writeFrom(varFrom);
	}
	
	public void rewriteFrom(FileDescriptor fileDesc, Var varFrom)
	{
		if(IsSTCheck)
			Log.logFineDebug("rewrite_FD" + fileDesc.toString());
		fileDesc.rewriteFrom(varFrom);
	}
	
	public void rewrite(FileDescriptor fileDesc)
	{
		if(IsSTCheck)
			Log.logFineDebug("rewrite_FD" + fileDesc.toString());
		fileDesc.rewrite();
	}
	
	public RecordDescriptorAtEnd read(FileDescriptor fileDesc)
	{
		if(IsSTCheck)
			Log.logFineDebug("read_FD" + fileDesc.toString());
		RecordDescriptorAtEnd end = fileDesc.read();
		return end;
	}
	
	public RecordDescriptorAtEnd readInto(FileDescriptor fileDesc, Var varDest)
	{
		if(IsSTCheck)
			Log.logFineDebug("read_FD" + fileDesc.toString());
		return fileDesc.readInto(varDest);
	}
	
	public SortCommand sort(SortDescriptor sortDescriptor)
	{
		if(IsSTCheck)
			Log.logFineDebug("sort_SortDescriptor");
		SortCommand sortCommand = new SortCommand(getProgramManager(), sortDescriptor);
		return sortCommand; 
	}
	
	public void release(Var varRecord)
	{
		if(IsSTCheck)
			Log.logFineDebug("release_V" + varRecord.getSTCheckValue());
		SortParagHandler sortParagHandler = getProgramManager().getCurrentSortParagHandler();
		if(sortParagHandler != null)
		{
			sortParagHandler.release(varRecord);
		}
		if(m_bUsedTempVarOrCStr) 
			m_tempCache.resetTempIndex(varRecord);
	}
	
	public void release(Var varRecord, Var varFrom)
	{
		if(IsSTCheck)
			Log.logFineDebug("release_V_V" + varRecord.getSTCheckValue() + varFrom.getSTCheckValue());
		SortParagHandler sortParagHandler = getProgramManager().getCurrentSortParagHandler();
		if(sortParagHandler != null)
		{
			sortParagHandler.release(varFrom);
		}
		if(m_bUsedTempVarOrCStr)  
			m_tempCache.resetTempIndex(varRecord, varFrom);
	}
	
	public RecordDescriptorAtEnd returnSort(SortDescriptor sortDescriptor)
	{
		if(IsSTCheck)
			Log.logFineDebug("release_SortDescriptor");
		SortParagHandler sortParagHandler = getProgramManager().getCurrentSortParagHandler();
		if(sortParagHandler != null)
		{
			RecordDescriptorAtEnd end = sortParagHandler.returnSort(sortDescriptor);
			return end;
		}
		return RecordDescriptorAtEnd.End;
	}
	
	public RecordDescriptorAtEnd returnSort(SortDescriptor sortDescriptor, Var varInto)
	{
		if(IsSTCheck)
			Log.logFineDebug("release_SortDescriptor_Var");
		
		SortParagHandler sortParagHandler = getProgramManager().getCurrentSortParagHandler();
		if(sortParagHandler != null)
		{
			RecordDescriptorAtEnd end = sortParagHandler.returnSort(sortDescriptor);
			if(!end.atEnd())
				sortDescriptor.moveInto(varInto); 
			if(m_bUsedTempVarOrCStr)
				m_tempCache.resetTempIndex(varInto);
			return end;
		}
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(varInto);
		return RecordDescriptorAtEnd.End;
	}
	
	public Output output()
	{
		return new Output();
	}
	
	public String getDateBatch()
	{
		if(IsSTCheck)
			Log.logFineDebug("getDateBatch()");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
		String cs = formatter.format(date) ;
		return cs;
	}
	
	public String getTimeBatch()
	{
		if(IsSTCheck)
			Log.logFineDebug("gettimeBatch()");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("HHmmssSS");
		String cs = formatter.format(date) ;
		return cs.substring(0, 8);
	}
	
	public String getDayBatch()
	{
		if(IsSTCheck)
			Log.logFineDebug("getDayBatch()");
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyDDD");
		String cs = formatter.format(date) ;
		return cs;
	}
	
	public int getReturnCode()
	{
		if(IsSTCheck)
			Log.logFineDebug("getReturnCode()");
		return JVMReturnCodeManager.getExitCode();
	}
	
	public void setReturnCode(int nReturnCode)
	{
		if(IsSTCheck)
			Log.logFineDebug("setReturnCode(n)" + nReturnCode);
		JVMReturnCodeManager.setExitCode(nReturnCode);
	}
	
	public void setReturnCode(Var varReturnCode)
	{
		if(IsSTCheck)
			Log.logFineDebug("setReturnCode(V)" + varReturnCode.getSTCheckValue());
		JVMReturnCodeManager.setExitCode(varReturnCode.getInt());
	}
	
	public void setReturnCode(String csReturnCode)
	{
		if(IsSTCheck)
			Log.logFineDebug("setReturnCode(cs)" + csReturnCode);
		JVMReturnCodeManager.setExitCode(new Integer(csReturnCode).intValue());
	}
	
	public String val(String cs)
	{
		return cs;
	}
	
	public String val(int n)
	{
		return String.valueOf(n);
	}
	
	public String val(double d)
	{
		return String.valueOf(d);
	}
	
	public String val(boolean b)
	{
		return String.valueOf(b);
	}
	
	public String val(long l)
	{
		return String.valueOf(l);
	}
	
	public String val(short s)
	{
		return String.valueOf(s);
	}
	
	public String val(Var var)
	{
		//String cs = var.getDottedSignedString()
		String cs = var.getAsAlphaNumString();
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
		return cs;
	}
	
	public void display(Concat conc)
	{
		display(conc.getString());
	}
	
	public void display(int n)
	{
		display(String.valueOf(n));
	}

	public void display(long l)
	{
		display(String.valueOf(l));
	}
	
	public void display(short s)
	{
		display(String.valueOf(s));
	}
	
	public void display(double d)
	{
		display(String.valueOf(d));
	}
	
	public void display(boolean b)
	{
		display(String.valueOf(b));
	}
	
	public void display(String csMessage)
	{
		LogDisplay.log(csMessage);
	}

	public void display(VarAndEdit var)
	{
		String csMessage = var.getDottedSignedString();
		LogDisplay.log(csMessage);
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(var);
	}
	
	protected String getLastCommandReturnCode()
	{
		/*
		 * This EIB field contains the CICS response code returned after the function requested by the 
		 * last CICS command to be issued by the task has been completed. 
		 * For a normal response, this field contains hexadecimal zeros (X'00'). 
		 * For COBOL programs only, almost all of the information in this field can be used within 
		 * Var VNS4 = _02().picS9(4).value(4);ication programs by the HANDLE CONDITION command.
		 * For COBOL: PIC X(6)
		 */ 
		if(IsSTCheck)
			Log.logFineDebug("getLastCommandReturnCode:");

		return getCESM().getLastCommandReturnCode() ;
	}

	protected String getTime()
	{
		if(IsSTCheck)
			Log.logFineDebug("getTime:");

		return m_BaseProgramManager.getEnv().getTime() ;
	}
	protected void setTime(Var v)
	{
		if(IsSTCheck)
			Log.logFineDebug("setTime_V:" + v.getSTCheckValue());
		
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(v);

		// TODO fake method
		// getProgramManager().m_CESMEnv.setTime(v) ;
	}
	protected void setTime(MathBase v)
	{
		if(IsSTCheck)
			Log.logFineDebug("setTime_M:" + v.getSTCheckValue());

		// TODO fake method
		//getProgramManager().m_CESMEnv.setTime(v) ;
	}

	
	/**
	 * @return String
	 * This EIB field contains the date  at which the task is started
	 * (this field is updated by the ASKTIME command).
	 * The date is in packed decimal form (0CYYDDD+).  Ex : 0099365 for 31/12/1999 or 0104001 for 01/01/2004
	 * For COBOL: PIC S9(7) COMP-3
	 */
	protected String getDate()
	{
		if(IsSTCheck)
			Log.logFineDebug("getDate:");

		return m_BaseProgramManager.getEnv().getDate() ;
	}

	protected int getConditionOccured()
	{	// http://publib.boulder.ibm.com/infocenter/txen/topic/com.ibm.txseries510.doc/erzhai00148.htm#TBLZR009T4
	/* EIBRESP
	 * This EIB field contains a 32-bit binary number corresponding to the condition that has occurred. 
	 * These numbers are listed in Table 26 (in decimal) for the conditions that can occur 
	 * on local requests during execution of the commands described in CICS API command reference. 
	 * For COBOL: PIC S9(8) COMP
	 */ 
		if(IsSTCheck)
			Log.logFineDebug("getConditionOccured:");

		return getCESM().getConditionOccured() ;
	}
	protected void setConditionOccured(Var v)
	{	// http://publib.boulder.ibm.com/infocenter/txen/topic/com.ibm.txseries510.doc/erzhai00148.htm#TBLZR009T4
	/* EIBRESP
	 * This EIB field contains a 32-bit binary number corresponding to the condition that has occurred. 
	 * These numbers are listed in Table 26 (in decimal) for the conditions that can occur 
	 * on local requests during execution of the commands described in CICS API command reference. 
	 * For COBOL: PIC S9(8) COMP
	 */ 
		if(IsSTCheck)
			Log.logFineDebug("setConditionOccured_V:" + v.getSTCheckValue());

		getCESM().setConditionOccured(v.getInt());
	}
	protected void setConditionOccured(int n)
	{	// http://publib.boulder.ibm.com/infocenter/txen/topic/com.ibm.txseries510.doc/erzhai00148.htm#TBLZR009T4
	/* EIBRESP
	 * This EIB field contains a 32-bit binary number corresponding to the condition that has occurred. 
	 * These numbers are listed in Table 26 (in decimal) for the conditions that can occur 
	 * on local requests during execution of the commands described in CICS API command reference. 
	 * For COBOL: PIC S9(8) COMP
	 */ 
		if(IsSTCheck)
			Log.logFineDebug("setConditionOccured_n:" + n);

		getCESM().setConditionOccured(n);
	}
		
	protected int getTaskNumber()
	{
		/*
		 * This EIB field contains the task number assigned to the task by CICS. 
		 * This number appears in trace entries generated while the task is in control. 
		 * For COBOL: PIC S9(7) COMP-3
		 */
		//TODO fake function getTaskNumber
		if(IsSTCheck)
			Log.logFineDebug("getTaskNumber:");

		return 0 ;
	}
	
	protected String getTerminalID()
	{ // PIC X(4)
		if(IsSTCheck)
			Log.logFineDebug("getTerminalID:");

		return getProgramManager().getTerminalID() ;
	}
	
	protected String getTransID()
	{ // PIC X(4)
		if(IsSTCheck)
			Log.logFineDebug("getTransID:");

		return getCESM().getEnvironment().getCurrentTransaction() ;
	}
	
	/**
	 * @param Edit e: KeyPressed key
	 * @return true if the last key hit is the same as key in parameter, false otherwise  
	 */
	protected boolean isKeyPressed(KeyPressed key)
	{
		if(IsSTCheck)
			Log.logFineDebug("isKeyPressed_k:" + key.getSTCheckValue());

		return getProgramManager().GetKeyPressed() == key ;
	}

	/**
	 * 
	 * Forget last key pressed 
	 * Internal usage only 
	 */
	protected void resetKeyPressed()
	{
		if(IsSTCheck)
			Log.logFineDebug("resetKeyPressed:");

		getProgramManager().resetKeyPressed() ;
	}
	/**
	 * @param Var var
	 * Internal usage only
	 * Set the last key pressed as the value of var 
	 */
	protected void setKeyPressed(Var v)
	{
		if(IsSTCheck)
			Log.logFineDebug("setKeyPressed_V:" + v.getSTCheckValue());

		getProgramManager().setKeyPressed(v) ;
		if(m_bUsedTempVarOrCStr)
			m_tempCache.resetTempIndex(v);
	}
	
	/**
	 * @param KeyPressed key
	 * Internal usage only
	 * * Set the last key pressed as key
	 */
	protected void setKeyPressed(KeyPressed key)
	{
		if(IsSTCheck)
			Log.logFineDebug("setKeyPressed_k:" + key.getSTCheckValue());

		getProgramManager().setKeyPressed(key) ;
	}
	
	/**
	 * @param Edit e: KeyPressed key
	 * @return false if the last key hit is the same as key in parameter, true otherwise  
	 */	
	protected boolean isNotKeyPressed(KeyPressed key)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNotKeyPressed_k:" + key.getSTCheckValue());
		return getProgramManager().GetKeyPressed() != key ;
	}
	
		/**
	 * @return KeyPressed object identifying last key pressed
	 */
	protected KeyPressed getKeyPressed()
	{
		if(IsSTCheck)
			Log.logFineDebug("getKeyPressed:");

		return getProgramManager().GetKeyPressed() ;
	}
	
	protected abstract BaseCESMManager getCESM();
	
	/**Method: Var getCommAreaLength()
	 * Return the commarea length in a Var. It may be 0 if no commera defined.
	 * @param:
	 * @return: Var containing the commarea length
	 */
	protected Var getCommAreaLength()
	{
		if(IsSTCheck)
			Log.logFineDebug("getCommAreaLength:");
		return getProgramManager().getCommAreaLength();
	}

	/**Method: setCommAreaLength
	 * Set the commarea length; Used internally
	 * @param: int n
	 * @return:
	 */
	protected void setCommAreaLength(int n)
	{
		if(IsSTCheck)
			Log.logFineDebug("setCommAreaLength_n:" + n);
		getProgramManager().setCommAreaLength(n);
	}

	
	/**
	 * @return String identifying the last CICS command
	 * @see http://publib.boulder.ibm.com/infocenter/txen/index.jsp?topic=/com.ibm.txseries510.doc/erzhai00150.htm ; 
	 */
	protected String getLastCICSCommandExecutedCode()
	{	 // PIC X(2)
		if(IsSTCheck)
			Log.logFineDebug("getLastCICSCommandExecutedCode:");

		return getCESM().getLastCommandCode() ;
	}
//	
//	private void releaseTempCache()
//	{
//		m_tempCache = null;
//	}

	void setTempCache()
	{
		m_tempCache = TempCacheLocator.getTLSTempCache();
	}

	protected TempCache getTempCache()
	{
		return m_tempCache;
	}
//	
//	private void m_tempCache.resetTempIndex(VarBase a)
//	{
//		a.m_tempCache.resetTempIndex(m_tempCache);
//	}
//
//	private void m_tempCache.resetTempIndex(VarBase a, VarBase b)
//	{
//		m_tempCache.resetTempVarIndex(a);
//		m_tempCache.resetTempVarIndex(b.getTypeId());
//	}
//
//	private void m_tempCache.resetTempIndex(VarBase a, VarBase b, VarBase c)
//	{
//		a.m_tempCache.resetTempIndex(m_tempCache);
//		b.m_tempCache.resetTempIndex(m_tempCache);
//		c.m_tempCache.resetTempIndex(m_tempCache);
//	}
//
//	private void m_tempCache.resetTempIndex(VarBase a, VarBase b, VarBase c, VarBase d)
//	{
//		a.m_tempCache.resetTempIndex(m_tempCache);
//		b.m_tempCache.resetTempIndex(m_tempCache);
//		c.m_tempCache.resetTempIndex(m_tempCache);
//		d.m_tempCache.resetTempIndex(m_tempCache);
//	}
	private TempCache m_tempCache = null;
	private boolean m_bUsedTempVarOrCStr = false;
	private boolean m_bUsedTempVar = false;
	private boolean m_bUsedCStr = false;
	
	public void setUseTempVar()
	{
		m_bUsedTempVar = true;
		m_bUsedTempVarOrCStr = true;		
	}
	
	public void setUseCStr()
	{
		m_bUsedCStr = true;
		m_bUsedTempVarOrCStr = true;		
	}
	
	public void resetUseTempVar()
	{
		m_bUsedTempVar = false;
		m_bUsedTempVarOrCStr = m_bUsedCStr;		
	}
	
	public void resetUseCStr()
	{
		m_bUsedCStr = false;
		m_bUsedTempVarOrCStr = m_bUsedTempVar;		
	}
	
	public void accept(Var varDest)
	{		
		String cs = ConsoleInput.getKeyboardLine();
		varDest.set(cs);
	}		
}


