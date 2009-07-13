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

import java.util.ListIterator;
import java.util.NoSuchElementException;

import lexer.CReservedKeyword;
import lexer.CTokenList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import semantic.CBaseEntityFactory;
import semantic.CBaseLanguageEntity;
import semantic.forms.CEntityResourceField;
import semantic.forms.CEntityResourceFieldArray;
import semantic.forms.CResourceStrings;
import utils.NacaTransAssertException;
import utils.Transcoder;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CFieldArray extends CFieldElement
{
	/**
	 * @param name
	 * @param line
	 */
	public CFieldArray()
	{
		super("", 0);
	}
	/* (non-Javadoc)
	 * @see parser.CBMSElement#GetType()
	 */
	public EBMSElementType GetType()
	{
		return EBMSElementType.ARRAY ;
	}
	/* (non-Javadoc)
	 * @see parser.CBMSElement#DoExportCustom(org.w3c.dom.Document)
	 */
	protected Element DoExportCustom(Document root)
	{
		Element eArray = root.createElement("FieldArray");
		eArray.setAttribute("Line", ""+m_PosLine);
		eArray.setAttribute("Col", ""+m_PosCol);
		eArray.setAttribute("NbCol", ""+m_NbCol);
		eArray.setAttribute("NbITems", ""+m_nbItems);
		eArray.setAttribute("VerticalFilling", ""+m_bVerticalFilling);
		return eArray;
	}
	/* (non-Javadoc)
	 * @see parser.CBMSElement#InterpretKeyword(lexer.CReservedKeyword, lexer.CTokenList)
	 */
	protected boolean InterpretKeyword(CReservedKeyword kw, CTokenList lstTokens)
	{
		return false;
	}
	/* (non-Javadoc)
	 * @see parser.CBMSElement#GetResourceStrings()
	 */
	public CResourceStrings GetResourceStrings()
	{
		return null;
	}
	/* (non-Javadoc)
	 * @see parser.CBMSElement#SetResourceStrings(semantic.forms.CResourceStrings)
	 */
	public void SetResourceStrings(CResourceStrings res)
	{
	}
	protected boolean m_bRegisterMotif = false ;
	protected boolean m_bValidateMotif = false ;
	protected int m_NbCol = 1 ;
	protected boolean m_bVerticalFilling = false ; 
	protected int m_nLastColIndexStart = 0 ;
	protected int m_nbItems = 0 ;

	public boolean ReadField(CFieldElement field)
	{
		String fullName = field.getName() ; 
		String name = "" ;
		String index = "" ;
		int n = 0 ;
		if (!fullName.equals(""))
		{
			name = fullName.substring(0, fullName.indexOf('(')) ;
			index = fullName.substring(fullName.indexOf('(')+1, fullName.length()-1) ;
			if (!index.equals(""))
			{
				n = Integer.parseInt(index) ;
			}
		}
		if (!m_bRegisterMotif && !m_bValidateMotif)
		{ // first step : initialisation
			if (n != 1)
			{
				throw new NacaTransAssertException("ASSERT ReadField 1") ;
			}
			m_PosCol = field.m_PosCol ;
			m_PosLine = field.m_PosLine ;
			m_nLastColIndexStart = 1 ;
			m_NbCol = 1 ;
			field.SetName(name);
			m_children.add(field) ;
			m_bRegisterMotif = true ;
			m_nbItems = 1 ;
			return true ;
		}
		else if (m_bRegisterMotif)
		{
			if (fullName.equals(""))
			{ // label
				m_children.add(field) ;
				return true ;
			}
			else if (index.equals(""))
			{ // edit not in array
				throw new NacaTransAssertException("ASSERT ReadField 2") ;
			}
			else if (n == 1)
			{ // still registering motif : another field
				field.SetName(name);
				m_children.add(field) ;
				return true ;
			}
			else
			{
				m_bRegisterMotif = false ;
				m_bValidateMotif = true ;
			}
		}
		if (m_bValidateMotif)
		{ // check if current field is in the motif
			CFieldElement cur = GetNextFieldInMotif() ;
			if (!cur.getName().equals(name) || cur.m_Length != field.m_Length)
			{
				return false ; 
			}
			if (!name.equals("") && index.equals(""))
			{
				return false ;
			}
			if (field.m_PosLine == m_PosLine)
			{
				if (n == m_nLastColIndexStart+1)
				{
					m_NbCol ++ ;
					m_nLastColIndexStart = n ;
					m_bVerticalFilling = false ;
				}
				else if (n>0 && m_nLastColIndexStart != n) 
				{
					m_NbCol ++ ;
					m_nLastColIndexStart = n ;
					m_bVerticalFilling = true ;
				}
			}
			return true ;
		} 
		return false;
	}
	private CFieldElement GetNextFieldInMotif()
	{
		if (m_curFieldInMotif == null)
		{
			m_curFieldInMotif = m_children.listIterator() ;
			m_nbItems ++ ;
		}
		try
		{
			return (CFieldElement)m_curFieldInMotif.next() ;
		}
		catch (NoSuchElementException e)
		{
			m_curFieldInMotif = m_children.listIterator() ;
			m_nbItems ++ ;
			return (CFieldElement)m_curFieldInMotif.next() ;
		}
	}
	private ListIterator m_curFieldInMotif ;
	/* (non-Javadoc)
	 * @see parser.CBaseElement#DoSemanticAnalysis(semantic.CBaseLanguageEntity, semantic.CBaseEntityFactory)
	 */
	public CBaseLanguageEntity DoSemanticAnalysis(CBaseLanguageEntity parent, CBaseEntityFactory factory)
	{
		CEntityResourceFieldArray eArray = factory.NewEntityFieldArray() ;
		eArray.SetArray(m_nbItems, m_NbCol, m_bVerticalFilling) ; 
		eArray.SetPosition(m_PosLine, m_PosCol) ;

		CFieldElement[] arrFields = new CFieldElement[m_children.size()] ;
		m_children.toArray(arrFields) ;
		for (int i=0; i<arrFields.length; i++)
		{
			CFieldElement el = arrFields[i] ;
			CEntityResourceField rf = (CEntityResourceField)el.DoSemanticAnalysis(eArray, factory) ;
			if (rf != null)
			{
				eArray.AddChild(rf) ;
				rf.m_nOccurs = m_nbItems ;
			}
		}
		return eArray ;
	}

}
