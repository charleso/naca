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
package idea.onlinePrgEnv;


import jlib.log.Log;

import idea.manager.CESMManager;
import nacaLib.basePrgEnv.BaseCESMManager;
import nacaLib.basePrgEnv.BaseEnvironment;
import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.mapSupport.*;
import nacaLib.misc.*;
import nacaLib.program.CompareResult;
import nacaLib.tempCache.TempCache;
import nacaLib.varEx.*;


/**
* @author PJD
*
* Window - Preferences - Java - Code Style - Code Templates
*/
public class OnlineProgram extends BaseProgram
{
	//private OnlineProgramManager m_ProgramManager = null;
	private static OnlineProgramManagerFactory ms_onlineProgramManagerFactory = new OnlineProgramManagerFactory();  
	
	public OnlineProgram()
	{
		super(ms_onlineProgramManagerFactory);
		declare = new VarSectionDeclaration(this);
	}
	
	public OnlineProgramManager getProgramManager()
	{
		return (OnlineProgramManager)m_BaseProgramManager;
	}

	public CESMManager CESM = null ;				// GUI methods accessor used by GUI applications
	protected BaseCESMManager getCESM()
	{
		return CESM;
	}
	
	protected VarSectionDeclaration declare = null; 

	 
	
	/**Method: setEnvironment
	 * Internal usage only
	 * Defined the current exnvironment for GUI apps
	 * @param: IN CESMManager man: Current GUI manager
	 * @param: IN CESMUSerEnv uenv: Current user envionnement
	 */
	
	protected int getCursorPosition()
	{
		/*
		 * This EIB field contains the cursor address (position) associated with the last terminal control 
		 * or Basic Mapping Support (BMS) input operation from a display device such as a terminal. 
		 * For COBOL: PIC S9(4) COMP
		 */
		//TODO fake function getCursorPosition
		if(IsSTCheck)
			Log.logFineDebug("getCursorPosition:");

		return 0 ;
	}
	
	/**
	 * @return String
	 * This EIB field contains the time at which the task is started
	 * (this field is updated by the ASKTIME command).
	 * The time is in packed decimal form (0HHMMSS+).
	 * For COBOL: PIC S9(7) COMP-3
	 */

	
	protected Var getCommareaR()
	{
		if(IsSTCheck)
			Log.logFineDebug("getCommareaR:");

		//TODO fake function getCommareaR
		// return new Var(null, 5, false) ;
		assertIfFalse(false);
		return null;
	}
	
	protected boolean isFieldAttribute(Edit e, VarAndEdit v)
	{
		if(IsSTCheck)
			Log.logFineDebug("isFieldAttribute_E_V:" + e.getSTCheckValue() + ":" + v.getSTCheckValue());

		String val = v.getString() ;
		if (val.equals("\u00d8"))
		{
			boolean b = e.isCleared() ;
			getTempCache().resetTempVarIndex(e.m_varTypeId);
			return b;
		}
		else
		{
			getTempCache().resetTempVarIndex(e.m_varTypeId);
			return false ;
		}
	}	
	
	/**
	 * @param Edit e: Edit whose color attribute is to be tested
	 * @param MapFieldAttrColor color: Color attribute definition 
	 * @return true if the edit e has the color identified in parameter, false otherwise
	 */
	protected boolean isFieldColored(Edit e, MapFieldAttrColor color)
	{
		if(IsSTCheck)
			Log.logFineDebug("isFieldColored_E_Color:" + e.getSTCheckValue());

		boolean b = e.IsColored(color) ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}	
	
	protected boolean isNotFieldColored(Edit e, MapFieldAttrColor color)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNotFieldColored_E_Color:" + e.getSTCheckValue());

		boolean b = ! e.IsColored(color) ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}

	
	/**
	 * @param Edit e:
	 * @return true if the edit e has the bright attribute set, false otherwise
	 */
	protected boolean isFieldBright(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("isFieldBright_E:" + e.getSTCheckValue());

		boolean b = e.IsAttribute(MapFieldAttrIntensity.BRIGHT) ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}
	
	protected boolean isNotFieldBright(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNotFieldBright_E:" + e.getSTCheckValue());

		boolean b = ! e.IsAttribute(MapFieldAttrIntensity.BRIGHT) ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}
	
	/**
	 * @param Edit e:
	 * @return true if the edit e has the blonk attribute set, false otherwise
	 */
	protected boolean isFieldBlink(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("isFieldBlink_E:" + e.getSTCheckValue());

		boolean b = e.IsHighlighting(MapFieldAttrHighlighting.BLINK) ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}
	
	/**
	 * @param Edit e:
	 * @return true if the edit e has the unprotected attribute set, false otherwise
	 */
	protected boolean isFieldUnprotected(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("isFieldUnprotected_E:" + e.getSTCheckValue());

		boolean b = e.IsAttribute(MapFieldAttrProtection.UNPROTECTED) ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}

	protected boolean isNotFieldUnprotected(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNotFieldUnprotected_E:" + e.getSTCheckValue());

		boolean b = ! e.IsAttribute(MapFieldAttrProtection.UNPROTECTED) ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}

	
	/**
	 * @param MapFieldAttrIntensity intens: Intensity attribute
	 * @param Edit e: Destination edit
	 * Set the edit e intensity attribute's value the intens's value.
	 */
	protected void moveAttribute(MapFieldAttrIntensity intens, Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveAttribute_Intens_E:" + intens.getSTCheckValue() + ":" + e.getSTCheckValue());

		e.intensity(intens) ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
	}	

	protected void moveAttribute(Var v, Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveAttribute_V_E:" + v.getSTCheckValue() + ":" + e.getSTCheckValue());

		String cs = v.getString() ;
		int n = cs.charAt(0) ;
		e.setAttributes(n) ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
}	

	
	
	/**
	 * @param MapFieldAttribute att: Contains all edit attribute values (
	 * @param Edit e: Destination edit
	 * Set the edit e attributes to all values contained in att
	 */
	protected void moveAttribute(MapFieldAttribute att, Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveAttribute_Attr_E:" + att.getSTCheckValue() + ":" + e.getSTCheckValue());
		e.setAttribute(att) ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
	}	
	
	protected void moveFlag(int n, Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveFlag_n_E:" + n + ":" + e.getSTCheckValue());

		String cs = String.valueOf(n);
		e.setFlag(cs) ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
	}	

	protected void moveFlag(Var v, Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveFlag_V_E:" + v.getSTCheckValue() + ":" + e.getSTCheckValue());

		String cs = v.getString() ;
		e.setFlag(cs) ;
		
		TempCache tempCache = getTempCache();
		tempCache.resetTempVarIndex(e.m_varTypeId);
		tempCache.resetTempVarIndex(v.m_varTypeId);

//		e.resetTempIndex(getTempCache());
//		v.resetTempIndex(getTempCache());
	}	

	
	/**
	 * @param Edit e
	 * @return All attributes of the Edit e
	 */
	protected MapFieldAttribute getAttribute(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("getAttribute_E:" + e.getSTCheckValue());

		MapFieldAttribute mapFieldAttribute = e.getAttribute() ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return mapFieldAttribute;
	}	
	
	/**
	 * @param Edit e
	 * @return MapFieldAttrHighlighting: Current highlight attribute of Edit e
	 */
	protected MapFieldAttrHighlighting getHighlighting(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("getHighlighting_E:" + e.getSTCheckValue());

		MapFieldAttrHighlighting a = e.getHighlighting() ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return a;
	}	
	
	/**
	 * @param MapFieldAttrProtection att: Protectection attribute to set to e
	 * @param Edit e: Destination edit
	 * Set the edit it's protection attribute value
	 */
	protected void moveAttribute(MapFieldAttrProtection att, Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("getHighlighting_Protect_E:" + att.getSTCheckValue()+ ":" + e.getSTCheckValue());

		e.protection(att) ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
	}	
	
	protected void moveFlag(String cs, Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveFlag_cs_E:" + cs+ ":" + e.getSTCheckValue());

		e.setFlag(cs) ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
	}	

	protected void resetFlag(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("resetFlag_E:" + e.getSTCheckValue());

		e.resetFlag();
		getTempCache().resetTempVarIndex(e.m_varTypeId);
	}	

	protected boolean isFieldFlag(Edit e, String cs)
	{
		if(IsSTCheck)
			Log.logFineDebug("isFieldFlag_E_cs:" + e.getSTCheckValue() + ":" + cs);

		boolean b = e.isFlag(cs) ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}	
	
	protected boolean isFieldFlagSet(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("isFieldFlagSet_E:" + e.getSTCheckValue());

		boolean b = e.isFlagSet() ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}	
	
	protected boolean isNotFieldFlag(Edit e, String cs)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNotFieldFlag_E_cs:" + e.getSTCheckValue() + ":" + cs);

		boolean b = !e.isFlag(cs) ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}	

	protected boolean isNotFieldFlagSet(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNotFieldFlagSet_E:" + e.getSTCheckValue());

		boolean b = !e.isFlagSet() ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}	
	
	/**
	 * @param MapFieldAttrModified att: Modified attribute to set to e
	 * @param Edit e: Destination edit
	 * Set the edit it's modified attribute value
	 */
	protected void moveAttribute(MapFieldAttrModified att, Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveAttribute_Attr_E:" + att.getSTCheckValue() + ":" + e.getSTCheckValue());

		if (att == MapFieldAttrModified.MODIFIED)
			e.setModified(MapFieldAttrModified.TO_BE_MODIFIED) ;
		else
			e.setModified(att) ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
	}	
	
	/**
	 * @param Edit e
	 * @return true if the edit e has attribute modified set, false otherwise
	 */
	protected boolean isFieldModified(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("isFieldModified_E:" + e.getSTCheckValue());

		boolean b = e.isModified() ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}	
	
	protected boolean isNotFieldModified(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNotFieldModified_E:" + e.getSTCheckValue());

		boolean b = !e.isModified() ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}	


	/**
	 * @param Edit e
	 * @return true if the edit e has attribute 'cleared' set, false otherwise
	 */
	protected boolean isFieldCleared(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("isFieldCleared_E:" + e.getSTCheckValue());

		boolean b = e.isCleared() ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}	
	
	/**
	 * @param Edit e
	 * @return false if the edit e has attribute 'cleared' set, true otherwise
	 */
	protected boolean isNotFieldCleared(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("isFieldCleared_E:" + e.getSTCheckValue());

		boolean b = !e.isCleared() ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}	
	
	/**
	 * @param Edit e
	 * @return true if the edit e has attribute unmodified set, false otherwise
	 */
	protected boolean isFieldUnmodified(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("isFieldUnmodified_E:" + e.getSTCheckValue());

		boolean b = e.isUnmodified() ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}	
	
	/**
	 * @param Edit e
	 * @return true if the edit e has attribute underline set, false otherwise
	 */
	protected boolean isFieldUnderlined(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("isFieldUnderlined_E:" + e.getSTCheckValue());

		boolean b = e.isUnderlined();	
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}
	
	protected boolean isNotFieldUnderlined(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNotFieldUnderlined_E:" + e.getSTCheckValue());

		boolean b = !e.isUnderlined();
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}
	
	protected boolean isFieldReverse(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("isFieldReverse_E:" + e.getSTCheckValue());

		boolean b = e.isReverse();
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}
	
	protected boolean isNotFieldReverse(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNotFieldReverse_E:" + e.getSTCheckValue());

		boolean b = !e.isReverse();
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}
	
	protected boolean isFieldHighlightNormal(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("isFieldHighlightNormal_E:" + e.getSTCheckValue());

		boolean b = e.isHighlightNormal();
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}
	
	protected boolean isNotFieldHighlightNormal(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNotFieldHighlightNormal_E:" + e.getSTCheckValue());

		boolean b = !e.isHighlightNormal();
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}
	

	
	/**
	 * @param Edit e
	 * @return true if the edit e has attribute autoskip set, false otherwise
	 */
	protected boolean isFieldAutoSkip(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("isFieldAutoSkip_E:" + e.getSTCheckValue());

		boolean b = e.isAutoSkip() ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}	
	
	protected boolean isNotFieldAutoSkip(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNotFieldAutoSkip_E:" + e.getSTCheckValue());

		boolean b = !e.isAutoSkip() ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}	
	
	protected boolean isFieldDark(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("isFieldDark_E:" + e.getSTCheckValue());

		boolean b = e.isDark() ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}	
	
	protected boolean isNotFieldDark(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNotFieldDark_E:" + e.getSTCheckValue());

		boolean b = !e.isDark() ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}	

	/**
	 * @param Edit e
	 * Set the cursor on the edit e
	 */
	protected void setCursor(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("setCursor_E:" + e.getSTCheckValue());

		e.setCursor(true) ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
	}

	protected void moveCursor(Edit from, Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveCursor_E_E:" + e.getSTCheckValue() + ":" + e.getSTCheckValue());

		if (from.hasCursor())
		{
			e.setCursor(true) ;
		}
		TempCache tempCache = getTempCache();
		tempCache.resetTempVarIndex(e.m_varTypeId);
		tempCache.resetTempVarIndex(from.m_varTypeId);
//		e.resetTempIndex(getTempCache());
//		from.resetTempIndex(getTempCache());		
	}
		
	protected void moveCursor(Var from, Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveCursor_V_E:" + e.getSTCheckValue() + ":" + e.getSTCheckValue());
		TempCache tempCache = getTempCache();
		tempCache.resetTempVarIndex(e.m_varTypeId);
		tempCache.resetTempVarIndex(from.m_varTypeId);
//		e.resetTempIndex(getTempCache());
//		from.resetTempIndex(getTempCache());
		// Fake method
	}
		
	protected void removeCursor(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("removeCursor_E:" + e.getSTCheckValue());

		e.setCursor(false) ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
	}
		
	/**
	 * @param Edit e
	 * @return true if e has the cursor set, false otherwise
	 */
	protected boolean isFieldHasCursor(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("isFieldHasCursor_E:" + e.getSTCheckValue());

		boolean b = e.hasCursor() ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}
	

	/**
	 * @param Edit e
	 * @return true if e has not the cursor set, false otherwise
	 */
	protected boolean isNotFieldHasCursor(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNotFieldHasCursor_E:" + e.getSTCheckValue());

		boolean b = !e.hasCursor() ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}

		
	/**
	 * @param MapFieldAttrColor color: Source color attribute
	 * @param Edit e: Destination edit
	 * Set the edit it's color attribute value
	 */
	protected boolean isColored(Edit e, MapFieldAttrColor color)
	{
		if(IsSTCheck)
			Log.logFineDebug("isColored_E_color:" + e.getSTCheckValue() + ":" + color.getSTCheckValue());
		
		boolean b = e.isColored(color);
		getTempCache().resetTempVarIndex(e.m_varTypeId);
		return b;
	}
	
	protected void moveColor(MapFieldAttrColor color, Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveColor_color_E:" + color.getSTCheckValue() + ":" +  e.getSTCheckValue());

		e.color(color);
		getTempCache().resetTempVarIndex(e.m_varTypeId);
	}	
	
	protected void moveColor(Edit edt, Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveColor_E_E:" + edt.getSTCheckValue() + ":" +  e.getSTCheckValue());
		
		MapFieldAttrColor color = edt.getColor() ;
		e.color(color);
		
		TempCache tempCache = getTempCache();
		tempCache.resetTempVarIndex(e.m_varTypeId);
		tempCache.resetTempVarIndex(edt.m_varTypeId);
		
//		e.resetTempIndex(getTempCache());
//		edt.resetTempIndex(getTempCache());
	}	
	
	protected void moveColor(Var v, Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveColor_V_E:" + v.getSTCheckValue() + ":" +  e.getSTCheckValue());

		int n = v.getInt() ;
		MapFieldAttrColor color = MapFieldAttrColor.Select(n) ;
		e.color(color);
		
		TempCache tempCache = getTempCache();
		tempCache.resetTempVarIndex(e.m_varTypeId);
		tempCache.resetTempVarIndex(v.m_varTypeId);
//		e.resetTempIndex(getTempCache());
//		v.resetTempIndex(getTempCache());
	}	

//	CV 27-04-05 : not used any longer
//	protected void moveLength(int n, Edit e)
//	{
//		if(isLog.logFineTrace())
//			Log.logFineTrace("moveLength_n_E:" + n + ":" +  e.getSTCheckValue());
//
//		e.setLength(n);
//	}	
//	protected void moveLength(String s, Edit e)
//	{
//		if(isLog.logFineTrace())
//			Log.logFineTrace("moveLength_cs_E:" + s + ":" +  e.getSTCheckValue());
//
//		int n = NumberParser.getAsInt(s);
//		e.setLength(n);
//	}	
//	protected void moveLength(Var v, Edit e)
//	{
//		if(isLog.logFineTrace())
//			Log.logFineTrace("moveLength_V_E:" + v.getSTCheckValue() + ":" +  e.getSTCheckValue());
//
//		int n = v.getInt() ;
//		e.setLength(n);
//	}	

//	/**
//	 * @param Var var: Variable indicating highlighting hint
//	 * @param Edit e: Destination edit
//	 * Set the edit highlight attribute as blink, if var's value is 255 (high value)
//	 * * Set the edit highlight attribute as normal, if var's value is not 255 (high value)
//	 */
	protected void moveHighLighting(Var var, Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveHighLighting_V_E:" + var.getSTCheckValue() + ":" +  e.getSTCheckValue());

		int n = var.getInt();
		if(n == 0xFF)
			e.highLighting(MapFieldAttrHighlighting.BLINK);
		else
			e.highLighting(MapFieldAttrHighlighting.OFF);
		
		TempCache tempCache = getTempCache();
		tempCache.resetTempVarIndex(var.m_varTypeId);
		tempCache.resetTempVarIndex(e.m_varTypeId);
//
//		var.resetTempIndex(getTempCache());
//		e.resetTempIndex(getTempCache());
	}	
	
	
	/**
	 * @param MapFieldAttrHighlighting att: Source highligt attribute 
	 * @param Edit e: Destination edit
	 * Set the edit it's highlighting attribute's value
	 */
	protected void moveHighLighting(MapFieldAttrHighlighting att, Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("moveHighLighting_high_E:" + att.getSTCheckValue() + ":" +  e.getSTCheckValue());

		e.highLighting(att);
		getTempCache().resetTempVarIndex(e.m_varTypeId);
	}
	
	
	/**
	 * @param Edit e: Destination edit
	 * Set the edit highlight attribute as normal
	 */
	protected void setFieldUnhighlighted(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("setFieldUnhighlighted_E:" + e.getSTCheckValue());

		e.highLighting(MapFieldAttrHighlighting.OFF);
		getTempCache().resetTempVarIndex(e.m_varTypeId);
	}
	
	/**
	 * @param Edit e: Destination edit
	 * Set the edit highlight attribute as reverse
	 */
	protected void setFieldReverse(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("setFieldReverse_E:" + e.getSTCheckValue());

		e.highLighting(MapFieldAttrHighlighting.REVERSE) ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
	}
	
	/**
	 * @param Edit e: Destination edit
	 * Set the edit highlight attribute as blink
	 */
	protected void setFieldBlink(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("setFieldBlink_E:" + e.getSTCheckValue());

		e.highLighting(MapFieldAttrHighlighting.BLINK) ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
	}
	
	/**
	 * @param Edit e: Destination edit
	 * Set the edit highlight attribute as underline
	 */
	protected void setFieldUnderline(Edit e)
	{
		if(IsSTCheck)
			Log.logFineDebug("setFieldUnderline_E:" + e.getSTCheckValue());

		e.highLighting(MapFieldAttrHighlighting.UNDERLINE) ;
		getTempCache().resetTempVarIndex(e.m_varTypeId);
	}
	

	
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
	}
	
	public void setSemanticContextValue(Edit editDest, String csValue)
	{
		if(IsSTCheck)
			Log.logFineDebug("setSemanticContextValue_E_cs" + editDest.getSTCheckValue() + ":" + csValue);
		editDest.setSemanticContextValue(csValue);
		getTempCache().resetTempVarIndex(editDest.m_varTypeId);
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
		getTempCache().resetTempVarIndex(varSource.m_varTypeId);
		return cs;
	}

	// Var  <-> Key	
	/**Method: isDifferent
	 * return false if the var's value equals the key value, true otherwise
	 * @param: IN Var var
	 * @param: IN KeyPressed k 
	 * @return:
	 */
	protected boolean isDifferent(VarAndEdit var1, KeyPressed key)
	{
		if(IsSTCheck)
			Log.logFineDebug("isDifferent_V_k:" + var1.getSTCheckValue()+ "/" + key.getSTCheckValue());
		boolean b = !isEqual(var1, key);
		getTempCache().resetTempVarIndex(var1.m_varTypeId);
		return b;
	}
	
	/**Method: isDifferent
	 * return true if the var's value equals the key value
	 * @param: IN Var var
	 * @param: IN KeyPressed k 
	 * @return:
	 */
	protected boolean isEqual(VarAndEdit var, KeyPressed k)
	{
		if(IsSTCheck)
			Log.logFineDebug("isEqual_V_k:" + var.getSTCheckValue()+ "/" + k.getSTCheckValue());
		int nResult = var.compareTo(ComparisonMode.Unicode, k.getValue());
		getTempCache().resetTempVarIndex(var.m_varTypeId);
		if(nResult == 0)
			return true;
		return false;
	}
	
	/**Method: move
	 * move the key's value into a var 
	 * @param: IN KeyPressed k
	 * @param: OUT Var varDest 
	 * @return: current program object; enables to chain another Program's method. 
	 */
	protected BaseProgram move(KeyPressed k, Var varDest)
	{
		if(IsSTCheck)
			Log.logFineDebug("move_k_V:" + k.getSTCheckValue() + ":" + varDest.getSTCheckValue());

		varDest.set(k.getValue());
		return this;
	}
	
	protected BaseProgram move(KeyPressed k, Edit varDest)
	{
		if(IsSTCheck)
			Log.logFineDebug("move_k_E:" + k.getSTCheckValue() + ":" + varDest.getSTCheckValue());
		if(isLogCESM)
			Log.logDebug("moveEdit: keypressed="+k.getName()+" to Edit="+varDest.getLoggableValue());
		varDest.set(k.getValue());
		getTempCache().resetTempVarIndex(varDest.m_varTypeId);
		return this;
	}
	

	void prepareRunMain(BaseEnvironment env)
	{
		OnlineEnvironment e = (OnlineEnvironment)env;
		CESM = (CESMManager)e.createCESMManager();
	}
	
	protected boolean isFieldNumeric(Edit edit)
	{
		if(IsSTCheck)
			Log.logFineDebug("isFieldNumeric_E:" + edit.getSTCheckValue());
		boolean b = edit.isNumericProtected() ;
		getTempCache().resetTempVarIndex(edit.m_varTypeId);
		return b;
	}

	protected boolean isNotFieldNumeric(Edit edit)
	{
		if(IsSTCheck)
			Log.logFineDebug("isNotFieldNumeric_E:" + edit.getSTCheckValue());
		boolean b = ! edit.isNumericProtected() ;
		getTempCache().resetTempVarIndex(edit.m_varTypeId);
		return b;

	}
	
}


