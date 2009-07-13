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
import semantic.expression.CBaseEntityCondition.EConditionType;
import utils.CObjectCatalog;
import utils.NacaTransAssertException;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityFieldAttribute extends CBaseEntityFieldAttribute
{

	/**
	 * @param l
	 * @param name
	 * @param cat
	 * @param out
	 * @param type
	 * @param owner
	 */
	public CEntityFieldAttribute(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity owner)
	{
		super(l, name, cat, out, CEntityFieldAttributeType.ATTRIBUTE, owner);
	}
	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#GetSpecialAssignment(parser.expression.CTerminal)
	 */
	public CBaseActionEntity GetSpecialAssignment(CDataEntity data, CBaseEntityFactory factory, int l)
	{
		CEntityFieldAttributeReference ref = factory.NewEntityFieldAttributeReference(m_Reference) ;
		CEntitySetAttribute eSet = factory.NewEntitySetAttribute(l, ref) ;
		eSet.SetAttribute(data) ;
		ref.RegisterWritingAction(eSet) ;
		return eSet ;
	}
	
	public CBaseActionEntity GetSpecialAssignment(CTerminal term, CBaseEntityFactory factory, int l)
	{
		return CEntityFieldAttribute.intGetSpecialAssignment(m_Reference, term, factory, l) ;
	}
	
	public static CBaseActionEntity intGetSpecialAssignment(CDataEntity field, CTerminal term, CBaseEntityFactory factory, int l)
	{
		/*
		 * look at URL for information :
		 * http://publib.boulder.ibm.com/infocenter/txen/index.jsp?topic=/com.ibm.txseries510.doc/erzhai00150.htm
		 * http://www-clips.imag.fr/geta/User/daniel.baud/pres-Trans.28092001.htm
		 */
		CEntityFieldAttributeReference ref = factory.NewEntityFieldAttributeReference(field) ;
		CEntitySetAttribute eSet = factory.NewEntitySetAttribute(l, ref) ;
		String v = term.GetValue().substring(0, 1) ;
		if (v.equals(" "))	{eSet.SetUnprotected() ; eSet.SetNormal() ; /*eSet.SetUnmodified();*/ }
		else if (v.equals("A"))	{eSet.SetUnprotected() ; eSet.SetNormal() ; eSet.SetModified() ; }
		else if (v.equals("D"))	{eSet.SetUnprotected() ; eSet.SetNormal() ; /*eSet.SetUnmodified();*/}
		else if (v.equals("E"))	{eSet.SetUnprotected() ; eSet.SetNormal() ; eSet.SetModified() ; }	// "E" same as A
		else if (v.equals("H")) {eSet.SetUnprotected() ; eSet.SetBright() ; /*eSet.SetUnmodified();*/}
		else if (v.equals("I"))	{eSet.SetUnprotected() ; eSet.SetBright() ; eSet.SetModified() ; }
		else if (v.equals("<"))	{eSet.SetUnprotected() ; eSet.SetDark() ; /*eSet.SetUnmodified();*/}
		else if (v.equals("("))	{eSet.SetUnprotected() ; eSet.SetDark() ; eSet.SetModified() ; }
		else if (v.equals("&"))	{eSet.SetNumeric() ; eSet.SetNormal() ; /*eSet.SetUnmodified();*/}
		else if (v.equals("J"))	{eSet.SetNumeric() ; eSet.SetNormal() ; eSet.SetModified() ; }
		else if (v.equals("M"))	{eSet.SetNumeric() ; eSet.SetNormal(); /*eSet.SetUnmodified();*/}
		else if (v.equals("N"))	{eSet.SetNumeric() ; eSet.SetNormal() ; eSet.SetModified() ; }
		else if (v.equals("Q"))	{eSet.SetNumeric() ; eSet.SetBright() ; /*eSet.SetUnmodified();*/}
		else if (v.equals("R"))	{eSet.SetNumeric() ; eSet.SetBright() ; eSet.SetModified() ; }
		else if (v.equals("*"))	{eSet.SetNumeric() ; eSet.SetDark() ; }
		else if (v.equals(")"))	{eSet.SetNumeric() ; eSet.SetDark() ; eSet.SetModified() ; }
		else if (v.equals("-"))	{eSet.SetProtected() ; eSet.SetNormal() ;/*eSet.SetUnmodified();*/}
		else if (v.equals("/"))	{eSet.SetProtected() ; eSet.SetNormal() ; eSet.SetModified() ; }
		else if (v.equals("U"))	{eSet.SetProtected() ; eSet.SetNormal() ;/*eSet.SetUnmodified();*/}	// U Same as '-'
		else if (v.equals("V"))	{eSet.SetProtected() ; eSet.SetModified();}
		else if (v.equals("Y"))	{eSet.SetProtected() ; eSet.SetBright() ; /*eSet.SetUnmodified();*/}
		else if (v.equals("Z"))	{eSet.SetProtected() ; eSet.SetBright() ; eSet.SetModified(); }
		else if (v.equals("%")) {eSet.SetProtected() ; eSet.SetDark() ; /*eSet.SetUnmodified();*/}
		else if (v.equals("_")) {eSet.SetProtected() ; eSet.SetDark() ; eSet.SetModified(); }
		else if (v.equals("0"))	{eSet.SetAutoSkip() ; eSet.SetNormal() ; /*eSet.SetUnmodified();*/}
		else if (v.equals("1"))	{eSet.SetAutoSkip() ; eSet.SetNormal() ; eSet.SetModified() ; }
		else if (v.equals("4"))	{eSet.SetAutoSkip() ; eSet.SetNormal() ; /*eSet.SetUnmodified();*/}
		else if (v.equals("5"))	{eSet.SetAutoSkip() ; eSet.SetNormal() ; eSet.SetModified();}
		else if (v.equals("6"))	{eSet.SetAutoSkip() ; eSet.SetDark() ;}
		else if (v.equals("8"))	{eSet.SetAutoSkip() ; eSet.SetBright() ; /*eSet.SetUnmodified();*/}
		else if (v.equals("9"))	{eSet.SetAutoSkip() ; eSet.SetBright() ; eSet.SetModified();}
		else if (v.equals("@"))	{eSet.SetAutoSkip() ; eSet.SetDark() ; }
		else if (v.equals("'"))	{eSet.SetAutoSkip() ; eSet.SetDark() ; eSet.SetModified();}
		else
		{
			eSet.SetAttribute(term.GetDataEntity(l, factory)) ;
		}
		ref.RegisterWritingAction(eSet) ;
		return eSet;
	}
	public CDataEntity GetArrayReference(Vector v, CBaseEntityFactory factory) 
	{
		CDataEntity e = m_Reference.GetArrayReference(v, factory) ;
		return factory.NewEntityFieldAttribute(getLine(), "", e);
	};
	public CBaseEntityCondition GetSpecialCondition(int nLine, String v, CBaseEntityCondition.EConditionType type, CBaseEntityFactory factory)
	{
		CUnitaryEntityCondition cond = CEntityFieldAttribute.GetSpecialCondition(nLine, v, m_Reference, factory, type) ;
		if (type == CBaseEntityCondition.EConditionType.IS_EQUAL || type == CBaseEntityCondition.EConditionType.IS_DIFFERENT)
		{
			return cond ;
		}
		else
		{
			throw new NacaTransAssertException("Unexcpected condition type in CEntityFieldAttribute") ; // ASSERT
		}
	};
	static CUnitaryEntityCondition GetSpecialCondition(int nLine, String v, CDataEntity ref, CBaseEntityFactory factory, CBaseEntityCondition.EConditionType type)
	{
		CEntityIsFieldAttribute eCond = factory.NewEntityIsFieldAttribute() ;
		if (type == CBaseEntityCondition.EConditionType.IS_DIFFERENT)
		{
			eCond.SetOpposite() ;
		}
		else if (type != CBaseEntityCondition.EConditionType.IS_EQUAL)
		{
			throw new NacaTransAssertException("Unexpecting situation in CEntityFieldAttribute.GetSpecialCondition()") ; // ASSERT
		}
		eCond.SetVariable(ref);
		if      (v.equals("H")) {eCond.IsUnprotected() ; eCond.IsBright() ;}
		else if (v.equals("Y"))	{eCond.IsProtected() ; eCond.IsBright() ;}
		else if (v.equals("8"))	{eCond.IsAutoSkip() ; eCond.IsBright() ; }
		else if (v.equals("D"))	{eCond.IsUnprotected() ; }
		else if (v.equals("M"))	{eCond.IsNumeric() ; }
		else if (v.equals("N"))	{eCond.IsNumeric() ; eCond.IsModified() ; }
		else if (v.equals("I"))	{eCond.IsUnprotected() ; eCond.IsBright() ; eCond.IsModified() ; }
		else if (v.equals("R"))	{eCond.IsNumeric() ; eCond.IsBright() ; eCond.IsModified() ; }
		else if (v.equals("Q"))	{eCond.IsNumeric() ; eCond.IsBright() ; }
		else if (v.equals("5"))	{eCond.IsAutoSkip() ; eCond.IsModified();}
		else if (v.equals("4"))	{eCond.IsAutoSkip() ;}
		else if (v.equals(" "))	{eCond.IsUnprotected() ; }
		else if (v.equals("&"))	{eCond.IsNumeric() ;  }
		else if (v.equals("-"))	{eCond.IsProtected() ; }
		else if (v.equals("0"))	{eCond.IsAutoSkip() ; }
		else if (v.equals("<"))	{eCond.IsUnprotected() ; eCond.IsDark() ; }
		else if (v.equals("%")) {eCond.IsProtected() ; eCond.IsDark() ;	}
		else if (v.equals("A"))	{eCond.IsUnprotected() ; eCond.IsModified() ; }
		else if (v.equals("1"))	{eCond.IsAutoSkip() ; eCond.IsModified() ; }
		else if (v.equals("J"))	{eCond.IsNumeric()  ; eCond.IsModified() ; }
		else if (v.equals("/"))	{eCond.IsProtected() ; eCond.IsModified() ; }
		else if (v.equals("("))	{eCond.IsUnprotected() ; eCond.IsDark() ; eCond.IsModified() ; }
		else if (v.equals(")"))	{eCond.IsNumeric() ; eCond.IsDark() ; eCond.IsModified() ; }
		else if (v.equals("@"))	{eCond.IsAutoSkip() ; eCond.IsDark() ; }
		else if (v.equals("LOW-VALUE") || v.equals("SPACE"))	{eCond.IsUnmodified() ;}
		else if (v.equals("Ø") || (v.equals("\u0080")))	
		{
			eCond.IsCleared() ;
		}
		else
		{
			return null ;
		}
		ref.RegisterVarTesting(eCond) ;
		return eCond ;
	}
	public CBaseEntityCondition GetSpecialCondition(int nLine, CDataEntity eData2, CBaseEntityCondition.EConditionType type, CBaseEntityFactory factory)
	{
		CEntityIsFieldAttribute eCond = factory.NewEntityIsFieldAttribute() ;
		eCond.IsAttribute(eData2, m_Reference);
		if (type == EConditionType.IS_DIFFERENT)
		{
			eCond.SetOpposite() ;
		}
		else if (type != EConditionType.IS_EQUAL)
		{
			ASSERT("Unsupported condition type : "+type.name()) ;
		}
		return eCond ;
	}

}
