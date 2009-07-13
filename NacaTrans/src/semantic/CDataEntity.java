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
package semantic;

import generate.*;

import java.util.Vector;

import parser.expression.CTerminal;
import semantic.expression.CBaseEntityCondExpr;
import semantic.expression.CBaseEntityCondition;
import semantic.expression.CBaseEntityExpression;
import semantic.expression.CUnitaryEntityCondition;
import semantic.expression.CBaseEntityCondition.EConditionType;
import utils.*;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CDataEntity extends CBaseLanguageEntity
{
	public enum CDataEntityType
	{
		VAR, 
		NUMERIC_VAR,
		EXTERNAL_REFERENCE,
		FIELD,
		FIELD_ATTRIBUTE,
		FORM,
		CONSTANT,
		NUMBER,
		STRING,
		CONDITION,
		CONSOLE_KEY,
		IGNORE,
		VIRTUAL_FORM, 
		EXPRESSION,
		ADDRESS,
		UNKNWON
	} 
	
	public abstract CDataEntityType GetDataType();

	/**
	 * @param name
	 * @param cat
	 */
//	public CDataEntity(int nLine)
//	{
//		super(nLine);
//	}

	
	protected CDataEntity(int l, String name, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, name, cat, out);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseSemanticEntity#RegisterMySelfToCatalog()
	 */

	protected void RegisterMySelfToCatalog()
	{
		m_ProgramCatalog.RegisterDataEntity(GetName(), this) ;
	}

	public abstract String ExportReference(int nLine) ;
	public int getNbDimOccurs()
	{
		return 0;
	}
	
	public CBaseActionEntity GetSpecialAssignment(CTerminal term, CBaseEntityFactory factory, int l)
	{
		return null ;
	} ;
	public CBaseActionEntity GetSpecialAssignment(CDataEntity term, CBaseEntityFactory factory, int l) 
	{
		return null ;
	}
	
//	abstract public CBaseEntityCondition GetSpecialCondition(int nLine, String value, CBaseEntityCondition.EConditionType type, CBaseEntityFactory factory);
	public CBaseEntityCondition GetSpecialCondition(int nLine, String value, CBaseEntityCondition.EConditionType type, CBaseEntityFactory factory)
	{
		if(getClass().getName().endsWith("CJavaUnknownReference"))
			Transcoder.logError(nLine, "ERROR : special condition needed for value '"+value + "; Undefined variable: "+GetName());
		else
			Transcoder.logError(nLine, "ERROR : special condition needed for value '"+value + "; Undefined variable: "+GetName() + " for class="+getClass().getName());
		return null ;
	}
		
	public CBaseExternalEntity m_Of = null ;
	
	public abstract boolean HasAccessors() ;
	public abstract String ExportWriteAccessorTo(String value) ;
	
	public abstract boolean isValNeeded();
	public CDataEntity GetSubStringReference(CBaseEntityExpression start, CBaseEntityExpression length, CBaseEntityFactory factory) 
	{
		Transcoder.logError(getLine(), "Error, substring not implemented for variable: " + GetName()) ;
		return null ;
	};
	public CDataEntity GetArrayReference(Vector v, CBaseEntityFactory factory) 
	{
		Transcoder.logError(getLine(), "Error, GetArray not implemented for variable: " + GetName()) ;
		return null ;
	};
	
	public CUnitaryEntityCondition GetAssociatedCondition(CBaseEntityFactory factory)
	{
//		m_logger.error("GetAssociatedCondition not implemented for this tye of data") ;
		return null ;
	}

	public CBaseEntityCondition GetSpecialCondition(int nLine, CDataEntity eData2, EConditionType type, CBaseEntityFactory factory)
	{
		return null;
	}
	
	// algorythmic analysis
	protected Vector<CBaseActionEntity> m_arrActionsWriting = new Vector<CBaseActionEntity>() ; // when this var is accessed in write mode : MOVE a TO THIS
	protected Vector<CBaseActionEntity> m_arrActionsReading = new Vector<CBaseActionEntity>() ; // when this var is accessed in read mode : MOVE THIS TO a
	protected Vector<CGenericDataEntityReference> m_arrWriteReference = new Vector<CGenericDataEntityReference>() ; // when this var is accessed in write mode : MOVE a TO THIS
	protected Vector<CGenericDataEntityReference> m_arrReadReference = new Vector<CGenericDataEntityReference>() ; // when this var is accessed in read mode : MOVE THIS TO a
	protected Vector<CBaseEntityCondition> m_arrTestsAsVar = new Vector<CBaseEntityCondition>() ; // when this var is tested : IF THIS = a / IF IS NUMERIC(THIS)
	protected Vector<CBaseEntityCondExpr> m_arrAccessAsValue = new Vector<CBaseEntityCondExpr>() ; // when the value of this var occures in a test : IF a = THIS
	protected Vector<CEntityFileDescriptor> m_arrFileDescriptorDepending = new Vector<CEntityFileDescriptor>() ; // when this var is used in a file descriptor : DEPENDING ON THIS
	
	public void RegisterReadReference(CGenericDataEntityReference ent)
	{
		m_arrReadReference.add(ent) ;
	}
	public void RegisterWriteReference(CGenericDataEntityReference ent)
	{
		m_arrWriteReference.add(ent) ;
	}
	public void RegisterWritingAction(CBaseActionEntity act)
	{
		m_arrActionsWriting.add(act) ;
	}
	public void UnRegisterWritingAction(int i)
	{
		m_arrActionsWriting.remove(i) ;
	}
	public void UnRegisterWritingAction(CBaseActionEntity e)
	{
		m_arrActionsWriting.remove(e) ;
	}
	public void RegisterFileDescriptorDepending(CEntityFileDescriptor fileDescriptor)
	{
		m_arrFileDescriptorDepending.add(fileDescriptor) ;
	}
	public int GetNbWriteReferences()
	{
		return m_arrWriteReference.size();
	}
	public int GetNbReadReferences()
	{
		return m_arrReadReference.size();
	}
	public int GetNbWrittingActions()
	{
		return m_arrActionsWriting.size();
	}
	public CBaseActionEntity GetActionWriting(int i)
	{
		if (i<m_arrActionsWriting.size())
		{
			return m_arrActionsWriting.get(i);
		}
		else
		{
			return null ;
		}
	}
	public CGenericDataEntityReference GetWriteReference(int i)
	{
		if (i<m_arrWriteReference.size())
		{
			return m_arrWriteReference.get(i);
		}
		else
		{
			return null ;
		}
	}
	public CGenericDataEntityReference GetReadReference(int i)
	{
		if (i<m_arrReadReference.size())
		{
			return m_arrReadReference.get(i);
		}
		else
		{
			return null ;
		}
	}
	public void RegisterReadingAction(CBaseActionEntity act)
	{
		m_arrActionsReading.add(act) ;
	}
	public void UnRegisterReadingAction(int i)
	{
		m_arrActionsReading.remove(i) ;
	}
	public void UnRegisterReadingAction(CBaseActionEntity e)
	{
		m_arrActionsReading.remove(e) ;
	}
	public int GetNbReadingActions()
	{
		return m_arrActionsReading.size();
	}
	public CBaseActionEntity GetActionReading(int i)
	{
		if (i<m_arrActionsReading.size())
		{
			return m_arrActionsReading.get(i);
		}
		else
		{
			return null ;
		}
	}
	public void RegisterVarTesting(CBaseEntityCondition cond)
	{
		m_arrTestsAsVar.add(cond) ;
	}
	public void UnRegisterVarTesting(int i)
	{
		m_arrTestsAsVar.remove(i) ;
	}
	public int GetNbVarTesting()
	{
		return m_arrTestsAsVar.size();
	}
	public CBaseEntityCondition GetVarTesting(int i)
	{
		if (i<m_arrTestsAsVar.size())
		{
			return m_arrTestsAsVar.get(i);
		}
		else
		{
			return null ;
		}
	}
	public void RegisterValueAccess(CBaseEntityCondExpr cond)
	{
		m_arrAccessAsValue.add(cond) ;
	}
	public void UnRegisterValueAccess(int i)
	{
		m_arrAccessAsValue.remove(i) ;
	}
	public int GetNbValueAccess()
	{
		return m_arrAccessAsValue.size();
	}
	public CBaseEntityCondExpr GetValueAccess(int i)
	{
		if (i<m_arrAccessAsValue.size())
		{
			return m_arrAccessAsValue.get(i);
		}
		else
		{
			return null ;
		}
	}

	public abstract String GetConstantValue() ;
	public boolean ignore()
	{
		boolean ignore = m_arrActionsReading.size()== 0 ;
		ignore &= m_arrActionsWriting.size() == 0 ;
		ignore &= m_arrReadReference.size() == 0 ;
		ignore &= m_arrWriteReference.size() == 0 ;
		ignore &= m_arrAccessAsValue.size() == 0 ;
		ignore &= m_arrTestsAsVar.size() == 0 ;
		ignore &= m_arrFileDescriptorDepending.size() == 0 ;
		ignore &= m_lstChildren.size()== 0 ;
		if (ignore)
		{
			return true ;
		}
		return m_bIgnore ;
	}
	public void ReplaceBy(CDataEntity var)
	{
		for (int j = 0; j<m_arrActionsReading.size();)
		{
			CBaseActionEntity act = m_arrActionsReading.get(j);
			if (!act.ReplaceVariable(this, var))
				j++ ;
		}
		for (int j = 0; j<m_arrActionsWriting.size(); )
		{
			CBaseActionEntity act = m_arrActionsWriting.get(j);
			if (!act.ReplaceVariable(this, var))
				j++ ;
		}
		for (int j = 0; j<m_arrAccessAsValue.size();)
		{
			CBaseEntityCondExpr act = m_arrAccessAsValue.get(j);
			if (!act.ReplaceVariable(this, var))
				j++ ;
		}
		for (int j = 0; j<m_arrTestsAsVar.size(); )
		{
			CBaseEntityCondition act = m_arrTestsAsVar.get(j);
			if (!act.ReplaceVariable(this, var))
				j++ ;
		}
		for (int j = 0; j<m_arrReadReference.size(); )
		{
			CGenericDataEntityReference act = m_arrReadReference.get(j);
			if (!act.ReplaceVariable(this, var, true))
				j++ ;
		}
		for (int j = 0; j<m_arrWriteReference.size();)
		{
			CGenericDataEntityReference act = m_arrWriteReference.get(j);
			if (!act.ReplaceVariable(this, var, false))
				j++ ;
		}
	}
	public void Clear()
	{
		super.Clear();
		m_arrActionsReading.clear() ;
		m_arrActionsWriting.clear() ;
		m_arrReadReference.clear() ;
		m_arrAccessAsValue.clear() ;
		m_arrTestsAsVar.clear() ;
		m_arrWriteReference.clear() ;
		m_arrFileDescriptorDepending.clear() ;
		if (m_Of != null)
		{
			m_Of = null ;
		}
	}
	
	public void ResetReferenceCount()
	{
		m_arrActionsReading.clear() ;
		m_arrActionsWriting.clear() ;
		m_arrReadReference.clear() ;
		m_arrAccessAsValue.clear() ;
		m_arrTestsAsVar.clear() ;
		m_arrWriteReference.clear() ;
		m_arrFileDescriptorDepending.clear() ;
	}
	public int getActualSubLevel()
	{
		return 0 ;
	}

	public void UnRegisterReadReference(CBaseDataReference reference)
	{
		m_arrReadReference.remove(reference) ;
	}

	public void UnRegisterWriteReference(CBaseDataReference reference)
	{
		m_arrWriteReference.remove(reference) ;
	}

	public void UnRegisterVarTesting(CBaseEntityCondition cond)
	{
		m_arrTestsAsVar.remove(cond) ;
	}

	public void UnRegisterValueAccess(CBaseEntityCondExpr attribute)
	{
		m_arrAccessAsValue.remove(attribute) ;
	}


}
