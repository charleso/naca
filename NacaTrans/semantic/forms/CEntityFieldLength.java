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


package semantic.forms;

import generate.CBaseLanguageExporter;

import java.util.Vector;

import lexer.Cobol.CCobolConstantList;

import parser.expression.CTerminal;
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import semantic.CBaseEntityFactory;
import semantic.expression.CBaseEntityCondition;
import utils.*;


public abstract class CEntityFieldLength extends CBaseEntityFieldAttribute
{
	/* (non-Javadoc)
	 * @see semantic.CDataEntity#GetSpecialAssignment(semantic.CDataEntity, semantic.CBaseEntityFactory, int)
	 */
	public CBaseActionEntity GetSpecialAssignment(CDataEntity term, CBaseEntityFactory factory, int l)
	{
		CEntityFieldAttributeReference ref = factory.NewEntityFieldAttributeReference(m_Reference) ;
		CEntitySetCursor eSet = factory.NewEntitySetCursor(l, ref) ;
		eSet.SetReference(term) ;
		term.RegisterReadingAction(eSet) ;
		ref.RegisterWritingAction(eSet) ;
		return eSet ;
	}
	public CEntityFieldLength(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity owner)
	{
		super(l, name, cat, out, CEntityFieldAttributeType.LENGTH, owner) ;
	}
	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#GetSpecialAssignment(parser.expression.CTerminal)
	 */
	public CBaseActionEntity GetSpecialAssignment(CTerminal term, CBaseEntityFactory factory, int l)
	{
		CEntityFieldAttributeReference ref = factory.NewEntityFieldAttributeReference(m_Reference) ;
		String v = term.GetValue() ;
		int n = 0 ;
		try	
		{
			n = Integer.parseInt(v) ;
		} 
		catch (NumberFormatException e)
		{
			//e.printStackTrace();
		}
		if (v.equals("-1"))
		{
			CEntitySetCursor eSet = factory.NewEntitySetCursor(l, ref) ;
			ref.RegisterWritingAction(eSet) ;
			return eSet ;
		}
		else if (v.equals("1") || n>0)
		{
			CEntitySetAttribute eSet = factory.NewEntitySetAttribute(l, ref) ;
			eSet.SetModified() ;
			ref.RegisterWritingAction(eSet) ;
			return eSet ;
		}
		else if (v.equals("0") || v.equals(CCobolConstantList.ZERO.m_Name) || v.equals(CCobolConstantList.ZEROS.m_Name) || v.equals(CCobolConstantList.ZEROES.m_Name))
		{
			CEntitySetCursor eSet = factory.NewEntitySetCursor(l, ref) ;
			eSet.removeCursor() ;
			ref.RegisterWritingAction(eSet) ;
			return eSet ;
//			CEntitySetConstant eSet = factory.NewEntitySetConstant(l);
//			eSet.SetToZero(m_RefField) ;
//			m_RefField.RegisterWritingAction(eSet) ;
//			return eSet ;
		}
		return null;
	}
	public CBaseEntityCondition GetSpecialCondition(int nLine, String value, CBaseEntityCondition.EConditionType type, CBaseEntityFactory factory)
	{
		boolean bZero = false ;
		if (value.equals("0") || value.equalsIgnoreCase("ZERO") || value.equalsIgnoreCase("ZEROS") || value.equalsIgnoreCase("ZEROES"))
		{
			bZero = true ;
		}		
		if (type == CBaseEntityCondition.EConditionType.IS_GREATER_THAN && bZero)
		{
			CEntityIsFieldModified e = factory.NewEntityIsFieldModified();
			e.SetIsModified(m_Reference);
			m_Reference.RegisterVarTesting(e) ;
			return e ;
		}
		if (!bZero && Integer.parseInt(value)==-1)
		{
			CEntityIsFieldCursor e = factory.NewEntityIsFieldCursor() ;
			if (type == CBaseEntityCondition.EConditionType.IS_EQUAL)
			{
				e.SetHasCursor(m_Reference) ;
			}
			else
			{
				e.SetHasNotCursor(m_Reference) ;
			}
			m_Reference.RegisterVarTesting(e) ;
			return e ;
		}
		return null ;
	}
	public CDataEntity GetArrayReference(Vector v, CBaseEntityFactory factory) 
	{
		CDataEntity e = m_Reference.GetArrayReference(v, factory) ;
		return factory.NewEntityFieldLengh(getLine(), "", e);
	};
}
