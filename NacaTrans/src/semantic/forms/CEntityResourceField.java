/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 5 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package semantic.forms;

import generate.CBaseLanguageExporter;

import java.util.Vector;

import lexer.Cobol.CCobolConstantList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.expression.CExpression;
import parser.expression.CTerminal;
import semantic.CBaseActionEntity;
import semantic.CDataEntity;
import semantic.CBaseEntityFactory;
import semantic.CBaseResourceEntity;
import semantic.CEntityArrayReference;
import semantic.CSubStringAttributReference;
import semantic.ITypableEntity;
import semantic.Verbs.CEntityAssign;
import semantic.Verbs.CEntitySetConstant;
import semantic.expression.CBaseEntityCondition;
import semantic.expression.CBaseEntityExpression;
import semantic.expression.CEntityCondIsConstant;
import semantic.expression.CBaseEntityCondition.EConditionType;
import utils.*;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CEntityResourceField extends CBaseResourceEntity  implements ITypableEntity
{
	/* (non-Javadoc)
	 * @see semantic.ITypableEntity#SetTypeEdited(java.lang.String)
	 */
	protected String m_Format = "" ;
	protected String m_Type = "" ;
	public void SetTypeEdited(String format)
	{
		m_Type = "pic" ;
		m_nLength = 0;
		m_nDecimals = 0;
		m_Format = format ;
	}
	/* (non-Javadoc)
	 * @see semantic.ITypableEntity#SetTypeNum(int, int)
	 */
	public void SetTypeNum(int length, int decimal)
	{
		m_Type = "pic9" ;
		m_nLength = length ;
		m_nDecimals = decimal ;
	}
	/* (non-Javadoc)
	 * @see semantic.ITypableEntity#SetTypeSigned(int, int)
	 */
	public void SetTypeSigned(int length, int decimal)
	{
		m_Type = "picS9" ;
		m_nLength = length ;
		m_nDecimals = decimal ;
	}
	/* (non-Javadoc)
	 * @see semantic.ITypableEntity#SetTypeString(int)
	 */
	public void SetTypeString(int length)
	{
		m_Type = "" ;
		m_nLength = length ;
	}
	protected enum FieldMode
	{
		NORMAL, CHECKBOX,TITLE, ACTIVE_CHOICE, LINKED_ACTIVE_CHOICE, SWITCH, HIDDEN; 
	}
	protected FieldMode m_Mode = FieldMode.NORMAL ;
	/**
	 * @param name
	 * @param cat
	 * @param exp
	 */
	public CEntityResourceField(int l, String name, CObjectCatalog cat, CBaseLanguageExporter lexp)
	{
		super(l, name, cat, lexp);
	}

//	protected String GetDefaultName()
//	{
//		return "" ;
//	}

	public abstract boolean IsEntryField();
	
	public void InitDependences(CBaseEntityFactory factory)
	{
		String name = GetName() ;
		if (!name.equals(""))
		{
			CBaseEntityFieldAttribute m_Length = factory.NewEntityFieldLengh(getLine(), name+"L", this);
			CBaseEntityFieldAttribute m_Color = factory.NewEntityFieldColor(getLine(), name+"C", this); 
			CBaseEntityFieldAttribute m_Highlight = factory.NewEntityFieldHighlight(getLine(), name+"H", this);
			CBaseEntityFieldAttribute m_Flag = factory.NewEntityFieldFlag(getLine(), name+"P", this);
			CBaseEntityFieldAttribute m_AttributeF = factory.NewEntityFieldAttribute(getLine(), name+"F", this);
			CBaseEntityFieldAttribute m_Attribute = factory.NewEntityFieldAttribute(getLine(), name+"A", this);
//			factory.m_ProgramCatalog.RegisterDataEntity(name, this);
			factory.m_ProgramCatalog.RegisterDataEntity(name+"I", this);
			factory.m_ProgramCatalog.RegisterDataEntity(name+"O", this);
//			CBaseEntityFieldAttribute m_DataI = factory.NewEntityFieldData(getLine(), name+"I", this);
//			CBaseEntityFieldAttribute m_DataO = factory.NewEntityFieldData(getLine(), name+"O", this);
			//m_Validation = factory.NewEntityFieldValidation(getLine(), name+"V", CBaseEntityFieldAttribute.CEntityFieldAttributeType.VALIDATION, this);
		}
//		ListIterator iter = m_lstChildren.listIterator() ;
//		try
//		{
//			CEntityResourceField field = (CEntityResourceField)iter.next() ;
//			while (field != null)
//			{
//				field.InitDependences(factory) ;
//				field = (CEntityResourceField)iter.next() ;
//			}
//		}
//		catch (NoSuchElementException e)
//		{
//		}
	} 
//	CBaseEntityFieldAttribute m_Length = null ;
//	CBaseEntityFieldAttribute m_Color = null ;
//	CBaseEntityFieldAttribute m_DataI = null ;
//	CBaseEntityFieldAttribute m_DataO = null ;
//	CBaseEntityFieldAttribute m_Highlight = null ;
//	CBaseEntityFieldAttribute m_Protected = null ;
//	CBaseEntityFieldAttribute m_Flag = null ;
//	CBaseEntityFieldAttribute m_Attribute = null ;
	//CBaseEntityFieldAttribute m_Validation = null ;
	/* (non-Javadoc)
	 * @see semantic.CBaseEntity#RegisterMySelfToCatalog()
	 */
//	protected void RegisterMySelfToCatalog()
//	{
////		m_ProgramCatalog.RegisterDataEntity(GetName()+"I", this) ;
////		m_ProgramCatalog.RegisterDataEntity(GetName()+"O", this);
//	}

	public CResourceStrings m_ResourceStrings = null ;
	public int m_nOccurs = 0 ;
	public int m_nPosCol = 0 ;
	public int m_nPosLine = 0 ; 
	public int m_nLength = 0 ;
	public int m_nDecimals = 0 ;
	public String m_csInitialValue = "" ;
	protected String m_csHighLight = "" ;
	public void Clear()
	{
		super.Clear();
		m_ResourceStrings = null ;
	}

	public void SetHighLight(String cs)
	{
		m_csHighLight = cs ;
	}
	protected String m_csColor = "" ;
	public void SetColor(String cs)
	{
		m_csColor = cs ;
	}
//	protected StringVector m_arrJustify = new StringVector() ;
//	public void AddJustify(String cs)
//	{
//		m_arrJustify.addElement(cs);
//	} 
//	protected StringVector m_arrAttrib = new StringVector() ;
//	public void AddAttrib(String cs)
//	{
//		m_arrAttrib.addElement(cs);
//	}
	protected String m_csFillValue = "" ;
	public void SetFillValue(String cs)
	{
		m_csFillValue = cs ;
	}
	protected String m_csProtection = "" ;
	public void SetProtection(String cs)
	{
		m_csProtection = cs ;
	}
	protected String m_csBrightness = "" ;
	public void SetBrightness(String cs)
	{
		m_csBrightness = cs ;
	}
	protected boolean m_bModified = false ;
	public void SetModified()
	{
		m_bModified = true ;
	}
	protected boolean m_bCursor = false ;
	public void SetCursor()
	{
		m_bCursor = true ;
	}
	
	

	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#GetSpecialAssignment(parser.expression.CTerminal)
	 */
	public CBaseActionEntity GetSpecialAssignment(CTerminal term, CBaseEntityFactory factory, int l)
	{
		String value = term.GetValue() ;
		CEntitySetConstant eAssign = factory.NewEntitySetConstant(l) ;
		if (value.equals("ZERO") || value.equals("ZEROS") || value.equals("ZEROES"))
		{
			eAssign.SetToZero(this) ;
		}
		else if (value.equals("SPACE") || value.equals("SPACES"))
		{
			eAssign.SetToSpace(this) ;
		}
		else if (value.equals("LOW-VALUE") || value.equals("LOW-VALUES"))
		{	 
			eAssign.SetToLowValue(this) ;
		}
		else if (value.equals("HIGH-VALUE") || value.equals("HIGH-VALUES"))
		{	 
			eAssign.SetToHighValue(this) ;
		}
		else if (term.IsNumber())
		{
			CEntityAssign asgn = factory.NewEntityAssign(l) ;
			asgn.SetValue(factory.NewEntityString(value)) ;
			asgn.AddRefTo(this) ;
			Transcoder.logDebug(l, "Number converted to string to move into EDIT var : "+value) ;
			RegisterWritingAction(asgn) ;
			return asgn ;
		}
		else
		{
			return null ;
		}
		RegisterWritingAction(eAssign) ;
		return eAssign ;
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#GetSpecialAssignment(semantic.CBaseDataEntity)
	 */
	public CBaseActionEntity GetSpecialAssignment(CDataEntity term, CBaseEntityFactory factory, int l)
	{
		return null;
	}
	
	public int GetByteLength ()
	{
		return 7 + m_nLength ;
	}
	public CDataEntity GetArrayReference(Vector v, CBaseEntityFactory factory) 
	{
		CEntityArrayReference e = factory.NewEntityArrayReference(getLine()) ;
		e.SetReference(this) ;
		for (int i=0; i<v.size(); i++)
		{
			CExpression expr = (CExpression)v.get(i);
			CBaseEntityExpression exp = expr.AnalyseExpression(factory);
			e.AddIndex(exp);
		}
		return e ;
	};
	public CBaseEntityCondition GetSpecialCondition(int nLine, String value, CBaseEntityCondition.EConditionType type, CBaseEntityFactory factory)
	{
		if (type == CBaseEntityCondition.EConditionType.IS_FIELD_ATTRIBUTE)
		{
			return CEntityFieldAttribute.GetSpecialCondition(nLine, value, this, factory, CBaseEntityCondition.EConditionType.IS_EQUAL) ;
		}
		else if (type == CBaseEntityCondition.EConditionType.IS_FIELD_COLOR)
		{
			return CEntityFieldColor.GetSpecialCondition(nLine, value, this, factory, CBaseEntityCondition.EConditionType.IS_EQUAL) ;
		}
//		else if (type == CBaseEntityCondition.ConditionType.IS_FIELD_HIGHLITING)
//		{
//		}
//		else if (type == CBaseEntityCondition.ConditionType.IS_FIELD_PROTECTED)
//		{
//		}
//		else if (type == CBaseEntityCondition.ConditionType.IS_FIELD_MODIFIED)
//		{
//		}
		else
		{			
			CEntityCondIsConstant eCond = factory.NewEntityCondIsConstant() ;
			if (value.equals("ZERO") || value.equals("ZEROS") || value.equals("ZEROES"))
			{
				eCond.SetIsZero(this);
			}
			else if (value.equals("SPACE") || value.equals("SPACES"))
			{
				eCond.SetIsSpace(this);
			}
			else if (value.equals(CCobolConstantList.LOW_VALUE.m_Name) || value.equals(CCobolConstantList.LOW_VALUES.m_Name))
			{
				eCond.SetIsLowValue(this);
			}
			else if (value.equals("HIGH-VALUE") || value.equals("HIGH-VALUES"))
			{
				eCond.SetIsHighValue(this);
			}
			else
			{
//				try
//				{
//					int n = Integer.parseInt(value) ;
//					if (type == CBaseEntityCondition.EConditionType.IS_DIFFERENT)
//					{
//						CEntityCondEquals cond = factory.NewEntityCondEquals() ;
//						cond.SetDifferentCondition(factory.NewEntityExprTerminal(this), factory.NewEntityExprTerminal(factory.NewEntityString(value))) ;
//						m_logger.info("line "+getLine()+" : numeric value converted to string to compare with EDIT var : " + value) ;
//						return cond ;
//					}
//					else if (type == CBaseEntityCondition.EConditionType.IS_EQUAL)
//					{
//						CEntityCondEquals cond = factory.NewEntityCondEquals() ;
//						cond.SetEqualCondition(factory.NewEntityExprTerminal(this), factory.NewEntityExprTerminal(factory.NewEntityString(value))) ;
//						m_logger.info("line "+getLine()+" : numeric value converted to string to compare with EDIT var : " + value) ;
//						return cond ;
//					}
//					else
//					{
//						m_logger.info("line "+getLine()+" : numeric value to compare with EDIT var not managed : " + value) ;
//						return null ;
//					}
//				}
//				catch (NumberFormatException e)
//				{
//					return null ;
//				}
				return null ;
			}
			if (type == CBaseEntityCondition.EConditionType.IS_DIFFERENT)
			{
				eCond.SetOpposite();
				RegisterVarTesting(eCond) ;
				return eCond ;
			}
			else if (type == CBaseEntityCondition.EConditionType.IS_EQUAL)
			{
				RegisterVarTesting(eCond) ;
				return eCond ;
			}
			else if (type == CBaseEntityCondition.EConditionType.IS_GREATER_THAN && value.startsWith("LOW-VALUE"))
			{
				eCond.SetOpposite() ;
				RegisterVarTesting(eCond) ;
				return eCond ;
			}
			else
			{
				return null ;
			}
		}
	}
	public boolean ignore()
	{
		return false ;
	}
	/* (non-Javadoc)
	 * @see semantic.CBaseLanguageEntity#RegisterMySelfToCatalog()
	 */
	protected void RegisterMySelfToCatalog()
	{
		String name = GetName() ;
//		m_ProgramCatalog.RegisterDataEntity(name, this) ;
		m_ProgramCatalog.RegisterDataEntity(name+"I", this) ;
		m_ProgramCatalog.RegisterDataEntity(name+"O", this) ;
	}

	/* (non-Javadoc)
	 * @see semantic.CDataEntity#GetSpecialCondition(semantic.CDataEntity, semantic.expression.CBaseEntityCondition.ConditionType, semantic.CBaseEntityFactory)
	 */
	public CBaseEntityCondition GetSpecialCondition(int nLine, CDataEntity eData2, EConditionType type, CBaseEntityFactory factory)
	{
		return null ;
	}
	public CDataEntity GetSubStringReference(CBaseEntityExpression start, CBaseEntityExpression length, CBaseEntityFactory factory) 
	{
		CSubStringAttributReference ref = factory.NewEntitySubString(getLine()) ;
		ref.SetReference(this, start, length) ;
		return ref ;
	}

	public abstract Element DoXMLExport(Document doc, CResourceStrings res) ;

	public void SetOf(CEntityResourceFormContainer container)
	{
		m_Of = container ;
		CDataEntity [] arr = new CDataEntity[m_lstChildren.size()] ;
		m_lstChildren.toArray(arr) ;
		for (int i=0; i<arr.length; i++)
		{
			CDataEntity e = arr[i] ;
			e.m_Of = container ;
		}
	}

	/**
	 * @param string
	 */
//	public void setDisplayName(String string)
//	{
//		m_csDisplayName = string ;		
//	}
//	protected String m_csDisplayName = "" ;
	/**
	 * @param valueOn
	 * @param valueOff
	 */
	public void setCheckBox(String valueOn, String valueOff)
	{
		m_Mode = FieldMode.CHECKBOX ;
		m_csCheckBoxValueOff = valueOff ;
		m_csCheckBoxValueOn = valueOn ;
	}
	protected String m_csCheckBoxValueOn = "" ;
	protected String m_csCheckBoxValueOff = "" ;

	/**
	 * @param flagMark
	 */
	public void setDevelopable(String flagMark)
	{
		m_csDevelopableFlagMark = flagMark ;
	}
	protected String m_csDevelopableFlagMark = "" ;
	
	/**
	 * @param flagMark
	 */
	public void setFormat(String format)
	{
		m_csFormat = format;
	}
	protected String m_csFormat = "" ;
	
	/**
	 * @param strings
	 * 
	 */
	public void SetTitle(CResourceStrings strings)
	{
		m_Mode = FieldMode.TITLE ;
		if (strings != null)
		{
			strings.FormatResource(m_csInitialValue) ;
		}
	}

	/**
	 * @param value
	 * @param target
	 * @param submit
	 */
	public void setActiveChoice(String value, String target, boolean submit)
	{
		m_Mode = FieldMode.ACTIVE_CHOICE ;
		m_csActiveChoiceTarget = target ;
		m_csActiveChoiceValue = value ;
		m_bActiveChoiceSubmit = submit ;
	}
	public void setLinkedActiveChoice(String value, String target, boolean submit)
	{
		m_Mode = FieldMode.LINKED_ACTIVE_CHOICE ;
		m_csActiveChoiceTarget = target ;
		m_csActiveChoiceValue = value ;
		m_bActiveChoiceSubmit = submit ;
	}
	protected String m_csActiveChoiceValue = "" ;
	protected String m_csActiveChoiceTarget = "" ;
	protected boolean m_bActiveChoiceSubmit = true ;
	/**
	 * 
	 */
	public void setReplayMutable()
	{
		m_bReplayMutable = true ;
	}
	protected boolean m_bReplayMutable = false ;
	public void AddSwitchCase(String value, String protection, Element tag)
	{
		m_Mode = FieldMode.SWITCH ;
		if (m_arrSwitchCaseElement == null)
		{
			m_arrSwitchCaseElement = new Vector<CSwitchCaseElement>() ;
		}
		CSwitchCaseElement el = new CSwitchCaseElement() ;
		el.m_val = value;
		el.m_protection = protection;
		el.m_tag = tag ;
		m_arrSwitchCaseElement.add(el) ;
	}
	protected Vector<CSwitchCaseElement> m_arrSwitchCaseElement = null ;
	protected class CSwitchCaseElement
	{
		public String m_val = "" ;
		public String m_protection = "" ;
		public Element m_tag = null ;
	}
	public void Hide()
	{
		m_Mode = FieldMode.HIDDEN ;		
	}
	public void SetRightJustified(boolean justifiedRight)
	{
		m_bRightJustified = justifiedRight ;
	}
	protected boolean m_bRightJustified = false ;
	
	public void SetBlankWhenZero(boolean blankWhenZero)
	{
		m_bBlankWhenZero = blankWhenZero ;
	}
	protected boolean m_bBlankWhenZero = false ;
	
	public void move(int nc, int nl)
	{
		m_nPosCol = nc ;
		m_nPosLine = nl ;		
	}
}
