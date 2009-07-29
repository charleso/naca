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
package generate.fpacjava;

import generate.CBaseLanguageExporter;
import semantic.CEntityArrayReference;
import semantic.expression.CBaseEntityExpression;
import utils.CObjectCatalog;

public class CFPacJavaArrayReference extends CEntityArrayReference
{

	public CFPacJavaArrayReference(int l, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, cat, out);
	}

	@Override
	public CDataEntityType GetDataType()
	{
		return m_Reference.GetDataType() ;
	}

	@Override
	public String ExportReference(int nLine)
	{
		String cs = "" ;
		for (CBaseEntityExpression exp : m_arrIndexes)
		{
			if (cs.equals(""))
			{
				cs = m_Reference.ExportReference(getLine()) + "(" + exp.ExportReference(getLine()) ;
			}
			else
			{
				cs += ", " + exp.ExportReference(getLine()) ;
			}
		}
		cs += ")" ;
		return cs ;
		
	}

	@Override
	public boolean HasAccessors()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String ExportWriteAccessorTo(String value)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValNeeded()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void DoExport()
	{
		// TODO Auto-generated method stub

	}

}
