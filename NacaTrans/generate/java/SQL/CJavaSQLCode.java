/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Jan 10, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.SQL;

import generate.CBaseLanguageExporter;
import semantic.SQL.CEntitySQLCode;
import semantic.expression.CBaseEntityExpression;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaSQLCode extends CEntitySQLCode
{
	/**
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CJavaSQLCode(String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(name, cat, out);
	}
	public CJavaSQLCode(String name, CObjectCatalog cat, CBaseLanguageExporter out, CBaseEntityExpression eHistoryItem)
	{
		super(name, cat, out, eHistoryItem);
	}
	/* (non-Javadoc)
	 * @see semantic.CDataEntity#ExportReference(getLine())
	 */
	public String ExportReference(int nLine)
	{
		if (m_eHistoryItem==null)
		{
			return "getSQLCode()";
		}
		else
		{
// http://publib.boulder.ibm.com/infocenter/iseries/v5r3/ic2924/index.htm?info/db2/rbafzmstfielddescsqlca.htm
			return "getSQLDiagnosticCode("+m_eHistoryItem.Export()+")" ;
		}
	}
	/* (non-Javadoc)
	 * @see semantic.CDataEntity#HasAccessors()
	 */
	public boolean HasAccessors()
	{
		return true;
	}
	/* (non-Javadoc)
	 * @see semantic.CDataEntity#ExportWriteAccessorTo()
	 */
	public String ExportWriteAccessorTo(String value)
	{
		return "resetSQLCode("+value+");";
	}
	/* (non-Javadoc)
	 * @see semantic.CDataEntity#GetConstantValue()
	 */
	public String GetConstantValue()
	{
		return "" ;
	}
	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	protected void DoExport()
	{
		// unused
	}
	public boolean isValNeeded()
	{
		return false;
	}

}
