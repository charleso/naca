/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.varEx;

public class VarType
{
	private int m_nId = 0;
	private static int ms_nLastId = 0;
	
	public static VarType VarForm = new VarType();
	public static VarType VarMapRedefine = new VarType();
	public static VarType VarEdit = new VarType(); 
	public static VarType VarEditInMap = new VarType();
	public static VarType VarEditInMapRedefine = new VarType();
	public static VarType VarAlphaNum = new VarType();
	public static VarType VarGroup = new VarType();
	public static VarType VarNumEdited = new VarType();
		
	public static VarType VarInternalInt = new VarType();
	public static VarType VarInternalBool = new VarType();
	public static VarType VarNumDecComp0 = new VarType();
	public static VarType VarNumDecComp0Long = new VarType();
	public static VarType VarNumDecComp3 = new VarType();
	public static VarType VarNumDecComp4 = new VarType();
	public static VarType VarNumDecSignComp0 = new VarType();
	public static VarType VarNumDecSignComp3 = new VarType();
	public static VarType VarNumDecSignComp4 = new VarType();
	public static VarType VarNumDecSignLeadingComp0 = new VarType();
	public static VarType VarNumDecSignTrailingComp0 = new VarType();
	
	public static VarType VarNumIntComp0 = new VarType();
	public static VarType VarNumIntComp0Long = new VarType();
	public static VarType VarNumIntComp3 = new VarType();
	public static VarType VarNumIntComp4 = new VarType();
	public static VarType VarNumIntComp4Long = new VarType();
	public static VarType VarNumIntSignComp0 = new VarType();
	public static VarType VarNumIntSignComp3 = new VarType();
	public static VarType VarNumIntSignComp4 = new VarType();
	public static VarType VarNumIntSignComp4Long = new VarType();
	public static VarType VarNumIntSignLeadingComp0 = new VarType();
	public static VarType VarNumIntSignTrailingComp0 = new VarType();
			
	public static VarType VarFpacAlphaNum = new VarType();
	public static VarType VarFPacVarNumIntSignComp3 = new VarType(); 
	public static VarType VarFPacNumShortSignComp4 = new VarType();
	public static VarType VarFPacNumIntSignComp4 = new VarType();
	public static VarType VarFPacNumLongSignComp4 = new VarType();
	public static VarType VarFPacNumEdited = new VarType();
	public static VarType VarFPacVarRaw = new VarType();
	
	private VarType()
	{
		ms_nLastId++;
		m_nId = ms_nLastId;
	}
	
	public int getId()
	{
		return m_nId;
	}
}
