/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 23 mars 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package generate.java.expressions;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.expression.CEntityList;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CJavaList extends CEntityList
{

	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CJavaList(String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(name, cat, out);
	}

	/* (non-Javadoc)
	 * @see semantic.CDataEntity#ExportReference(getLine())
	 */
	public String ExportReference(int nLine)
	{
		if (m_arrData.isEmpty())
		{
			return "null" ;
		}
		else
		{
			CDataEntity e = m_arrData.get(0) ;
			String type = "" ;
			if (e.GetDataType() == CDataEntity.CDataEntityType.STRING)
			{
				type = "String" ;
			}
			else if (e.GetDataType() == CDataEntity.CDataEntityType.FIELD)
			{
				type = "Edit" ;
			}
			else if (e.GetDataType() == CDataEntity.CDataEntityType.NUMBER)
			{
				type = "int" ;
			}
			else 
			{
				type = "Var" ;
			}
			String cs = "new "+type+"[] {" ;
			for (int i=0; i<m_arrData.size(); i++)
			{
				e = m_arrData.get(i) ;
				if (i>0)
				{
					cs += ", " ;
				}
				cs += e.ExportReference(getLine()) ;
			}
			cs += "}" ;
			return cs ;
		}
	}
	
	public boolean isValNeeded()
	{
		return true;
	}


	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	protected void DoExport()
	{
		// unused

	}

}
