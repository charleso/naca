/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 1 oct. 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.CICS;

import generate.CBaseLanguageExporter;
import semantic.CICS.CEntityCICSRead;
import utils.CObjectCatalog;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCICSRead extends CEntityCICSRead
{

	/**
	 * @param line
	 * @param cat
	 * @param out
	 */
	public CJavaCICSRead(int line, CObjectCatalog cat, CBaseLanguageExporter out, CEntityCICSReadMode mode)
	{
		super(line, cat, out, mode);
	}

	protected void DoExport()
	{
		if (m_bReadtoDataSet && m_DataInto.GetName().equals("PLAU-ZONE"))
		{
			if (m_DataInto.m_Of != null)
			{
				String cs = "Pub2000Routines.readCode(getProgramManager(), " + m_DataInto.m_Of.ExportReference(getLine()) + ") ;" ;
				WriteLine(cs);
				return ;
			}
			else
			{
				ASSERT(null);
			}
		}
		else if (m_bReadtoDataSet && m_DataInto.GetName().equals("PLAU-ZONE-ASP"))
		{
			if (m_DataInto.m_Of != null)
			{
				String cs = "Pub2000Routines.readCodeMedia(getProgramManager(), " + m_DataInto.m_Of.ExportReference(getLine()) + ") ;" ;
				WriteLine(cs);
				return ;
			}
			else
			{
				ASSERT(null);
			}
		}
		else if (m_bReadtoDataSet && m_DataInto.GetName().equals("MSG-ZONE"))
		{
			if (m_DataInto.m_Of != null)
			{
				String cs = "Pub2000Routines.readMessage(getProgramManager(), " + m_DataInto.m_Of.ExportReference(getLine()) + ") ;" ;
				WriteLine(cs);
				return ;
			}
			else
			{
				ASSERT(null);
			}
		}
		else if ((m_Mode == CEntityCICSReadMode.PREVIOUS || m_Mode == CEntityCICSReadMode.NEXT) &&
				m_DataInto.GetName().equals("CURS-ZONE"))
		{
			if (m_DataInto.m_Of != null && m_RecIDField != null)
			{
				String cs = "Pub2000Routines.readFieldInMap";
				if (m_Mode == CEntityCICSReadMode.PREVIOUS)
				{	
					cs += "Previous";
				}
				else
				{
					cs += "Next";
				}
				cs += "(getProgramManager(), " + m_DataInto.m_Of.ExportReference(getLine()) + ", " +	m_RecIDField.ExportReference(getLine()) + ");";
				WriteLine(cs);
				return ;
			}
			else
			{
				ASSERT(null);
			}
		}
		else if (m_bReadtoDataSet && m_DataInto.GetName().equals("CURS-ZONE"))
		{
			if (m_DataInto.m_Of != null && m_RecIDField != null)
			{
				String cs = "Pub2000Routines.readFieldInMap(getProgramManager(), " +
							m_DataInto.m_Of.ExportReference(getLine()) + ", " +
							m_RecIDField.ExportReference(getLine()) + ");";
				WriteLine(cs);
				return ;
			}
			else
			{
				ASSERT(null);
			}
		}
		else
		{
			String title = "" ;
			if (m_Mode == CEntityCICSReadMode.NORMAL)
			{
				title = "CESM.read" ;
			}
			else if (m_Mode == CEntityCICSReadMode.PREVIOUS)
			{
				title = "CESM.readPrevious" ;
			}
			else if (m_Mode == CEntityCICSReadMode.NEXT)
			{
				title = "CESM.readNext" ;
			}
			if (m_bReadtoDataSet)
			{
				title += "DataSet(" ;
			}
			else if (m_bReadToFile)
			{
				title += "File(" ;
			}
			title += m_Name.ExportReference(getLine()) + ")" ;
			WriteWord(title) ;
			WriteWord(".into(" + m_DataInto.ExportReference(getLine()) + ")") ;
			if (m_RecIDField != null)
			{
				WriteWord(".recIDField(" + m_RecIDField.ExportReference(getLine()) + ")");
			}
			if (m_KeyLength != null)
			{
				WriteWord(".keyLength(" + m_KeyLength.ExportReference(getLine()) + ")");
			}
			if (m_bEqual)
			{
				WriteWord(".equal()") ;
			}
			WriteWord(" ;");
			WriteEOL();		
		}
	}
}
