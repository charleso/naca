/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Jan 7, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.map_elements;

import lexer.CReservedKeyword;
import lexer.CTokenList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;



import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.CEntityStructure;
import semantic.expression.CEntityString;
import semantic.forms.CEntityResourceField;
import semantic.forms.CResourceStrings;
import utils.CEntityHierarchy;
import utils.Transcoder;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CFieldGroup extends CFieldElement
{

	/**
	 * @param name
	 * @param line
	 */
	public CFieldGroup(String name)
	{
		super(name, 0);
	}

	/* (non-Javadoc)
	 * @see parser.CBMSElement#GetType()
	 */
	public EBMSElementType GetType()
	{
		return EBMSElementType.GROUP ;
	}

	/* (non-Javadoc)
	 * @see parser.CBMSElement#DoExportCustom(org.w3c.dom.Document)
	 */
	protected Element DoExportCustom(Document root)
	{
		Element eGrp = root.createElement("Group") ;
		eGrp.setAttribute("Name", getName()) ;
		eGrp.setAttribute("PosLine", String.valueOf(m_PosLine));
		eGrp.setAttribute("PosCol", String.valueOf(m_PosCol));
		return eGrp ;
	}

	/* (non-Javadoc)
	 * @see parser.CBMSElement#InterpretKeyword(lexer.CReservedKeyword, lexer.CTokenList)
	 */
	protected boolean InterpretKeyword(CReservedKeyword kw, CTokenList lstTokens)
	{
		// nothing
		return false;
	}

	/* (non-Javadoc)
	 * @see parser.CBMSElement#GetResourceStrings()
	 */
	public CResourceStrings GetResourceStrings()
	{
		// nothing
		return null;
	}

	/* (non-Javadoc)
	 * @see parser.CBMSElement#SetResourceStrings(semantic.forms.CResourceStrings)
	 */
	public void SetResourceStrings(CResourceStrings res)
	{
		// nothing
	}
	
	
	public CBaseLanguageEntity DoSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityResourceField ef ;
		CEntityHierarchy hier = null ;
		ef = factory.NewEntityEntryField(getLine(), getName()) ;
		
		CFieldElement[] arrFields = new CFieldElement[m_children.size()] ;
		m_children.toArray(arrFields) ;
		ef.m_nLength = 0;
		for (int i=0; i<arrFields.length; i++)
		{
			CFieldElement el = arrFields[i] ;
			ef.m_nLength += el.m_Length ;
			if (i==0)
			{
				ef.m_nPosCol = el.m_PosCol ;
				ef.m_nPosLine = el.m_PosLine ;
				ef.m_csInitialValue = "" ; 
				// el.m_Value ;
				if (el.m_HighLight != null)
				{
					ef.SetHighLight(el.m_HighLight.m_Name) ;
				}
				if (el.m_Color != null)
				{
					ef.SetColor(el.m_Color.m_Name) ;
				}
				for (int j=0; j<el.m_arrATTRB.size(); j++)
				{
					String cs = el.m_arrATTRB.elementAt(j) ;
					if (cs.equals("ASKIP")){
						ef.SetProtection("AUTOSKIP") ;	}
					else if (cs.equals("UNPROT")){
						ef.SetProtection("UNPROTECTED");}
					else if (cs.equals("NUM")){
						ef.SetProtection("NUMERIC"); }
					else if (cs.equals("NORM")){
						ef.SetBrightness("NORMAL");}
					else if (cs.equals("DRK")){
						ef.SetBrightness("DARK"); }
					else if (cs.equals("BRT")){
						ef.SetBrightness("BRIGHT");}
					else if (cs.equals("FSET")){
						ef.SetModified();}
					else if (cs.equals("IC")){
						ef.SetCursor();}
					else{
						int n=0 ;
					}
				}
				for (int j=0; j<el.m_arrJustify.size(); j++)
				{
					String cs = el.m_arrJustify.elementAt(j) ;
					if (cs.equals("LEFT")){
						ef.SetRightJustified(false) ;}
					else if (cs.equals("RIGHT")){
						ef.SetRightJustified(true);}
					else if (cs.equals("BLANK")){
						ef.SetFillValue("BLANK");}
					else if (cs.equals("ZERO") || cs.equals("ZEROS") || cs.equals("ZEROES")){
						ef.SetFillValue("ZERO");}
					else{
						int n=0 ;
					}
				}
			}
			CEntityStructure es = factory.NewEntityStructure(0, el.getName(), "10") ;
			CEntityString val = factory.NewEntityString(el.m_Value) ;
			es.SetInitialValue(val) ;
			es.SetTypeString(el.m_Length) ;
			ef.AddChild(es) ;
		}
		return ef;
	}

	public void setPosition(CFieldElement eField)
	{
		m_PosCol = eField.m_PosCol ;
		m_PosLine = eField.m_PosLine ;		
	}
	
	public void AddChildField(CFieldElement e)
	{
		AddChild(e) ;
	}

}
