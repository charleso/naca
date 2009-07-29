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
 * Created on Jan 10, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.SQL;

import java.util.Vector;

import parser.expression.CExpression;

import generate.CBaseLanguageExporter;
import semantic.CBaseEntityFactory;
import semantic.CDataEntity;
import semantic.expression.CBaseEntityCondition;
import semantic.expression.CBaseEntityExpression;
import semantic.expression.CBaseEntityCondition.EConditionType;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntitySQLCode extends CDataEntity
{
	protected CBaseEntityExpression m_eHistoryItem = null ;
	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 */
	public CEntitySQLCode(String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(0, name, cat, out);
	}
	public CEntitySQLCode(String name, CObjectCatalog cat, CBaseLanguageExporter out, CBaseEntityExpression eHistoryItem)
	{
		super(0, name, cat, out);
		m_eHistoryItem = eHistoryItem ;
	}
	/* (non-Javadoc)
	 * @see semantic.CDataEntity#GetDataType()
	 */
	public CDataEntityType GetDataType()
	{
		return CDataEntityType.CONSTANT ;
	}
	/* (non-Javadoc)
	 * @see semantic.CDataEntity#GetSpecialCondition(java.lang.String, semantic.expression.CBaseEntityCondition.ConditionType, semantic.CBaseEntityFactory)
	 */
	public CBaseEntityCondition GetSpecialCondition(int nLine, String value, EConditionType type, CBaseEntityFactory factory)
	{
		if (m_eHistoryItem!=null) return null;
		int n =0 ;
		try
		{
			if (value.equals("ZERO") || value.equals("ZEROS") || value.equals("ZEROES")) {
				n = 0 ;
			}
			else
			{
				n = Integer.parseInt(value) ;
			}
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
			return null ;
		}
		if (type == EConditionType.IS_EQUAL)
		{
			CEntityCondIsSQLCode isCode = factory.NewEntityCondIsSQLCode() ;
			isCode.setIsEqual(n) ;
			return isCode ;
		}
		else if (type == EConditionType.IS_DIFFERENT)
		{
			CEntityCondIsSQLCode isCode = factory.NewEntityCondIsSQLCode() ;
			isCode.setIsNotEqual(n) ;
			return isCode ;
		}
		return null ;
	}

	/* (non-Javadoc)
	 * @see semantic.CDataEntity#GetArrayReference(java.util.Vector, semantic.CBaseEntityFactory)
	 */
	public CDataEntity GetArrayReference(Vector v, CBaseEntityFactory factory)
	{
//		 http://publib.boulder.ibm.com/infocenter/iseries/v5r3/ic2924/index.htm?info/db2/rbafzmstfielddescsqlca.htm
		CExpression expr = (CExpression)v.get(0);
		CBaseEntityExpression exp = expr.AnalyseExpression(factory);
		return factory.NewEntitySQLCode("", exp) ;
	}
}
