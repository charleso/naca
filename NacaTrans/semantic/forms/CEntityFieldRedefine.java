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
 * Created on Aug 17, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.forms;

import generate.CBaseLanguageExporter;

import java.util.Vector;

import parser.expression.CExpression;

import semantic.CDataEntity;
import semantic.CBaseEntityFactory;
import semantic.expression.CBaseEntityExpression;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityFieldRedefine extends CEntityResourceField
{
	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 */
	public String m_csLevel = "" ;
	public CEntityFieldRedefine(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out, String level)
	{
		super(l, name, cat, out);
		m_csLevel = level;
	}
	public CDataEntity GetArrayReference(Vector v, CBaseEntityFactory factory) 
	{
		CEntityFieldArrayReference e = factory.NewEntityFieldArrayReference(getLine()) ;
		e.SetReference(this) ;
		for (int i=0; i<v.size(); i++)
		{
			CExpression expr = (CExpression)v.get(i);
			CBaseEntityExpression exp = expr.AnalyseExpression(factory);
			e.AddIndex(exp);
		}
		return e ;
	}
	/**
	 * @param format
	 */
//	public void SetFormat(String format)
//	{
//		m_csFormat = format ;
//	}
	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#RegisterMySelfToCatalog()
	 */
//	protected void RegisterMySelfToCatalog()
//	{
//		// nothing for this kind of data : this object can't be accessed directly..
//	}

	protected void RegisterMySelfToCatalog()
	{
		//super.RegisterMySelfToCatalog();
//		String name = GetName() ;
//		boolean b = m_ProgramCatalog.IsExistingDataEntity(name, "") ;
//		if (b)
//		{
//			String newname = "" ;
//			int n=0; 
//			while (b)
//			{
//				n++ ;
//				newname = name + "$" + n ;
//				b = m_ProgramCatalog.IsExistingDataEntity(newname, "") ;
//			}
//			SetName(newname) ;
//		}
		int n=0 ;
		// a Field Redefined must not register itself, because it depends on aliases found in original program : only those aliases are registered
	}

}
