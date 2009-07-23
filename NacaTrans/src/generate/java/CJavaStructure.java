/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 2 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java;

import parser.Cobol.elements.CWorkingEntry.CWorkingSignType;
import generate.*;
import semantic.*;
import utils.*;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaStructure extends CEntityStructure
{

	/**
	 * @param name
	 * @param cat
	 * @param level
	 * @param type
	 */
	public CJavaStructure(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out, String level)
	{
		super(l, name, cat, out, level);
	}
	protected void DoExport()
	{
		if (m_bBlankWhenZero && m_Type.equals("pic9"))
		{
			m_Type = "pic";
			m_Format = "";
			for (int i=0; i < m_Length; i++)
				m_Format += "9";
			if (m_Decimals>0)
			{
				m_Format += ".";
				for (int i=0; i < m_Decimals; i++)
					m_Format += "9";
			}
		}
		String line = "Var " + FormatIdentifier(GetDisplayName()) + " = declare.level(" + Integer.parseInt(m_csLevel) + ")" ;
		if (m_RefRedefine != null)
		{
			line += ".redefines(" + m_RefRedefine.ExportReference(getLine()) + ")" ;
		}
		if (m_TableSize != null)
		{
			if (m_TableSizeDepending != null)
			{
				line += ".occursDepending(" + m_TableSize.ExportReference(getLine()) + ", " ;
				line += m_TableSizeDepending.ExportReference(getLine()) + ")" ;
			}
			else if (m_bIsVariableLenght)
			{
				line += ".variableLength()" ; 
				m_Length = m_Length * getTableSizeAsInt() ;
			}
			else
			{
				line += ".occurs(" + m_TableSize.ExportReference(getLine()) + ")" ;
			}
		}
		if (!m_Type.equals(""))
		{
			line += "." + m_Type + "(" ;
			if (m_Format.equals(""))
			{
				line += m_Length ;
				if (m_Decimals > 0)
				{
					line += "," + m_Decimals ;
				}
			}
			else
			{
				line += "\"" + m_Format + "\"" ;
			}
			line += ")" ;
		}
		if (!m_Comp.equals(""))
		{
			if (m_Comp.equalsIgnoreCase("Comp3"))
			{
				line += ".comp3()" ;
			}
			else if (m_Comp.equalsIgnoreCase("Comp4"))
			{
				line += ".comp()" ;
			}
			else if (m_Comp.equalsIgnoreCase("Comp"))
			{
				line += ".comp()" ;
			}
			else if (m_Comp.equalsIgnoreCase("Comp2"))
			{
				line += ".comp2()" ;
			}
		}
		WriteWord(line) ;
		if (m_bSync)
		{
			WriteWord(".sync()");
		}
		if (m_Value != null)
		{
			String cs = "" ;
			if (m_bFillWithValue)
			{
				cs = ".valueAll(" ;
			}
			else
			{
				cs = ".value(" ;
			}
			cs += m_Value.ExportReference(getLine());
			WriteWord(cs + ")") ;
		}
		else if (m_bInitialValueIsSpaces)
		{
			WriteWord(".valueSpaces()") ;
		}
		else if (m_bInitialValueIsZeros)
		{
			WriteWord(".valueZero()") ;
		}
		else if (m_bInitialValueIsLowValue)
		{
			WriteWord(".valueLowValue()") ;
		}
		else if (m_bInitialValueIsHighValue)
		{
			WriteWord(".valueHighValue()") ;
		}
		if (m_bJustifiedRight)
		{
			WriteWord(".justifyRight()") ;
		}
		if (m_bBlankWhenZero)
		{
			WriteWord(".blankWhenZero()") ;
		}
		if (m_bSignSeparateType == CWorkingSignType.LEADING)
		{
			WriteWord(".signLeadingSeparated()");
		}
		else if (m_bSignSeparateType == CWorkingSignType.TRAILING)
		{
			WriteWord(".signTrailingSeparated()");
		}
		if (m_bFiller)
		{
			WriteWord(".filler() ;") ;
		}
		else
		{
			WriteWord(".var() ;") ;
		}
		WriteEOL() ;
		StartOutputBloc() ;
		ExportChildren();
		EndOutputBloc() ;
	}
	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#ExportReference(semantic.CBaseExporter)
	 */
	public String ExportReference(int nLine)
	{
		String cs = "" ;
		if (m_Of != null)
		{
			cs += m_Of.ExportReference(getLine()) + "." ;
		}
		cs += FormatIdentifier(GetDisplayName()) ;
		
//		if(Transcoder.canGenerateCheckNumberIndexes())
//		{
//			int nNbOccurs = getNbDimOccurs();
//			if(nNbOccurs != 0)
//			{
//				Transcoder.logWarn(nLine, "Invalid number of indexes specified for variable: "+GetName()+"; There should be "+ nNbOccurs + " indexes");
//			}
//		}
		
		return cs ;		
	}
		
	public int getNbDimOccurs()
	{
		int nNbDim = 0;
		if(m_TableSize != null)
			nNbDim++;
		CBaseLanguageEntity e = m_parent;
		while(e != null)
		{
			if(e.canOwnTableSize())
			{
				CEntityStructure s = (CEntityStructure)e; 
				if(s.getTableSize() != null)
					nNbDim++;
			}
			else
			{
				int gg = 0;	//e = null;
			}
			e = e.GetParent();
		}		
		return nNbDim;
	}
	
	public boolean HasAccessors()
	{
		return false;
	}
	public String ExportWriteAccessorTo(String value)
	{
		// unsued		
		return "" ;
	}
	public boolean isValNeeded()
	{
		return true;
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#GetDataType()
	 */
	public CDataEntityType GetDataType()
	{
		if (m_Type.equals("picS9") || m_Type.equals("pic9"))
		{
			return CDataEntityType.NUMERIC_VAR ;
		}
		else
		{
			return CDataEntityType.VAR ;
		}
	}

}
