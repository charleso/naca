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
 * Created on 11 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.forms;

import generate.*;

import java.util.Vector;

import parser.expression.CTerminal;
import semantic.*;
import semantic.expression.CBaseEntityCondition;
import semantic.expression.CUnitaryEntityCondition;
import utils.CObjectCatalog;
import utils.NacaTransAssertException;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityFieldColor extends CBaseEntityFieldAttribute
{
	public static class CFieldColor
	{
		protected CFieldColor(String s)	{
			m_text = s ;
		}
		public String m_text="" ;
		public static CFieldColor RED = new CFieldColor("RED") ; 
		public static CFieldColor YELLOW = new CFieldColor("YELLOW") ; 
		public static CFieldColor GREEN = new CFieldColor("GREEN") ; 
		public static CFieldColor BLUE = new CFieldColor("BLUE") ; 
		public static CFieldColor PINK = new CFieldColor("PINK") ; 
		public static CFieldColor TURQUOISE = new CFieldColor("TURQUOISE") ; 
		public static CFieldColor NEUTRAL = new CFieldColor("NEUTRAL") ; 
		public static CFieldColor WhichColor(String col)
		{
			if (col.equals(""))
			{
				return null ; //default color
			}
			else if (col.equals("1"))
			{
				return CFieldColor.BLUE ; 
			}
			else if (col.equals("2"))
			{
				return CFieldColor.RED ; 
			}
			else if (col.equals("3"))
			{
				return CFieldColor.PINK ; 
			}
			else if (col.equals("4"))
			{
				return CFieldColor.GREEN ; 
			}
			else if (col.equals("5"))
			{
				return CFieldColor.TURQUOISE ; 
			}
			else if (col.equals("6"))
			{
				return CFieldColor.YELLOW ; 
			}
			else if (col.equals("7"))
			{
				return CFieldColor.NEUTRAL; 
			}
			else
			{
				return null ;
			}
		}
	}
	
	public CEntityFieldColor(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity owner)
	{
		super(l, name, cat, out, CEntityFieldAttributeType.COLOR, owner) ;
	}
	public CBaseActionEntity GetSpecialAssignment(CTerminal term, CBaseEntityFactory factory, int l)
	{
		String col = term.GetValue() ;
		CEntityFieldAttributeReference ref = factory.NewEntityFieldAttributeReference(m_Reference) ;
		CEntitySetColor eSet = factory.NewEntitySetColor(l, ref);
		m_Color = CFieldColor.WhichColor(col) ;
		eSet.SetColor(m_Color) ;
		ref.RegisterWritingAction(eSet) ;
		return eSet;
	}
	public CBaseActionEntity GetSpecialAssignment(CDataEntity term, CBaseEntityFactory factory, int l)
	{
		if (term.GetDataType() == CDataEntity.CDataEntityType.FIELD_ATTRIBUTE)
		{
			CEntityFieldAttributeReference ref = factory.NewEntityFieldAttributeReference(m_Reference) ;
			CEntitySetColor eSet = factory.NewEntitySetColor(l, ref);
			eSet.SetColor(term) ;
			ref.RegisterWritingAction(eSet) ;
			term.RegisterReadingAction(eSet) ;
			return eSet;
		}
		else
		{
			return null ;
		}
	}

	protected CFieldColor m_Color = null ;
	public CDataEntity GetArrayReference(Vector v, CBaseEntityFactory factory) 
	{
		CDataEntity e = m_Reference.GetArrayReference(v, factory) ;
		return factory.NewEntityFieldColor(getLine(), "", e);
	};
	public CBaseEntityCondition GetSpecialCondition(int nLine, String value, CBaseEntityCondition.EConditionType type, CBaseEntityFactory factory)
	{
		return CEntityFieldColor.GetSpecialCondition(nLine, value, m_Reference, factory, type) ;
	}
	static CUnitaryEntityCondition GetSpecialCondition(int nLine, String value, CDataEntity ref, CBaseEntityFactory factory, CBaseEntityCondition.EConditionType type)
	{
		CFieldColor col = CFieldColor.WhichColor(value);
		if (col == null)
		{
			return null ;
		}
		else
		{
			CEntityIsFieldColor eCond = factory.NewEntityIsFieldColor() ;
			eCond.IsColor(col, ref);
			ref.RegisterVarTesting(eCond) ;
			if (type == CBaseEntityCondition.EConditionType.IS_DIFFERENT)
			{
				eCond.SetOpposite();
			}
			else if (type != CBaseEntityCondition.EConditionType.IS_EQUAL)
			{
				throw new NacaTransAssertException("Unexpecting condition type in CEntityFieldColor") ;
			}
			return eCond ;
		}
	}
	public boolean ignore()
	{
		return false ;
	}
}
