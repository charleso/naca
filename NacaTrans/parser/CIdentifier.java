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
package parser;

import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.expression.CExpression;
import semantic.CBaseLanguageEntity;
import semantic.CDataEntity;
import semantic.CBaseEntityFactory;
import semantic.expression.CBaseEntityExpression;
import utils.Transcoder;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CIdentifier
{
	public CIdentifier(String s)
	{
		m_Name = s ;
	}
	public CIdentifier(String s, String m)
	{
		m_Name = s ;
		m_MemberOf = m;
	}
	public String GetName()
	{
		return m_Name ;
	}
		
	public void ExportTo(Element e, Document root)
	{
		if (m_arrArrayIndex != null && m_arrArrayIndex.size()>0 && m_exprStringLengthReference != null && m_exprStringStartReference != null)
		{
			e.setAttribute("SubStringOfArrayIdem", m_Name) ;
		}
		else if (m_arrArrayIndex != null && m_arrArrayIndex.size()>0)
		{
			e.setAttribute("ArrayItem", m_Name) ;
		}
		else if (m_exprStringLengthReference != null || m_exprStringStartReference != null)
		{
			e.setAttribute("SubStringOf", m_Name) ;
		}
		else
		{
			e.setAttribute("Identifier", m_Name) ;
		}
		if (m_arrArrayIndex != null && m_arrArrayIndex.size()>0)
		{
			for (int i=0; i<m_arrArrayIndex.size(); i++)
			{
				CExpression exp = m_arrArrayIndex.get(i);
				Element eIndexLabel = root.createElement("Index"+i) ;
				e.appendChild(eIndexLabel) ;
				Element eIndex = exp.Export(root);
				eIndexLabel.appendChild(eIndex) ;
			}
		}
		if (m_exprStringStartReference  != null)
		{
			Element eStart = root.createElement("Start") ;
			e.appendChild(eStart) ;
			eStart.appendChild(m_exprStringStartReference.Export(root)) ;
		}
		if (m_exprStringLengthReference != null)
		{
			Element eLength = root.createElement("Length") ;
			e.appendChild(eLength) ;
			eLength.appendChild(m_exprStringLengthReference.Export(root)) ;			
		}
		if (!m_MemberOf.equals(""))
		{
			Element eOf = root.createElement("Of") ;
			e.appendChild(eOf) ;
			eOf.setAttribute("Ascendant", m_MemberOf) ;
		}
	}
	
	public void SetSubStringReference(CExpression exp1, CExpression exp2)
	{
		m_exprStringStartReference = exp1 ;
		m_exprStringLengthReference = exp2 ;
	}		
	
	public void AddArrayIndex(CExpression e)
	{
		if (m_arrArrayIndex==null)
		{
			m_arrArrayIndex = new Vector<CExpression>() ;
		}
		m_arrArrayIndex.add(e) ;
	}
	
	public CDataEntity GetDataReference(int nLine, CBaseEntityFactory fact)
	{
		return GetDataReference(nLine, fact, null) ;
	}
	
	public CDataEntity GetDataReference(int nLine, CBaseEntityFactory fact, CBaseLanguageEntity parent)
	{
		CDataEntity e = null ;
		if (fact.m_ProgramCatalog.IsExistingDataEntity(m_Name, m_MemberOf))
		{
			e = fact.m_ProgramCatalog.GetDataEntity(nLine, m_Name, m_MemberOf) ;
			if (e == null)
			{
				Transcoder.addOnceUnboundReference(nLine, m_Name);
				//Transcoder.ms_logger.error("ERROR : identifier not bound : "+m_Name);
				return fact.NewEntityUnknownReference(nLine, m_Name) ;
			}
		}
		else if (m_MemberOf.equals("") && parent != null)
		{
			if (fact.m_ProgramCatalog.IsExistingDataEntity(m_Name, parent.GetName()))
			{
				e = fact.m_ProgramCatalog.GetDataEntity(nLine, m_Name, parent.GetName()) ;
			}
			else
			{
				e = fact.m_ProgramCatalog.GetDataEntity(nLine, m_Name, m_MemberOf) ;
				Transcoder.addOnceUnboundReference(nLine, m_Name);
				//Transcoder.ms_logger.error("ERROR : identifier not bound : "+m_Name);
				return fact.NewEntityUnknownReference(nLine, m_Name) ;
			}
		}
		else
		{
			e = fact.m_ProgramCatalog.GetDataEntity(nLine, m_Name, m_MemberOf) ;
			Transcoder.addOnceUnboundReference(nLine, m_Name);
			//Transcoder.ms_logger.error("ERROR : identifier not bound : "+m_Name);
			return fact.NewEntityUnknownReference(nLine, m_Name) ;
		}
		if (m_arrArrayIndex != null)
		{
			e = e.GetArrayReference(m_arrArrayIndex, fact);
		}
		if (m_exprStringStartReference != null && m_exprStringLengthReference != null)
		{
			CBaseEntityExpression expStart = m_exprStringStartReference.AnalyseExpression(fact);
			CBaseEntityExpression expLen = m_exprStringLengthReference.AnalyseExpression(fact); 
			e = e.GetSubStringReference(expStart, expLen, fact);
		}
		
		return e ;
	}
	
	protected CExpression m_exprStringStartReference = null ;
	protected CExpression m_exprStringLengthReference = null ;
	protected Vector<CExpression> m_arrArrayIndex = null ;
	protected String m_Name = "" ;
	protected String m_MemberOf = "" ;
	public String toString()
	{
		String cs = "" ;
		if (!m_MemberOf.equals(""))
		{
			cs = m_MemberOf +"." ;
		}
		cs += m_Name ;
		if (m_arrArrayIndex != null)
		{
			for (int i=0; i<m_arrArrayIndex.size(); i++)
			{
				if (i==0)
				{
					cs += "(" ;
				}
				else
				{
					cs += ", " ;
				}
				CExpression exp = m_arrArrayIndex.get(i);
				cs += exp.toString() ;
			}
			cs += ")" ;
		}
		if (m_exprStringLengthReference != null &&  m_exprStringStartReference != null)
		{
			cs += "("+m_exprStringStartReference.toString()+":"+m_exprStringLengthReference.toString()+")" ;
		}
		return cs ;
	}
	/**
	 * @return
	 */
	public String GetMemberOf()
	{
		return m_MemberOf ;
	}
	public void setMemberOf(String string)
	{
		if (!m_MemberOf.equals(""))
		{
			m_MemberOf += ";" ;
		}
		m_MemberOf += string ;
	}

}
