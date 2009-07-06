/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Jul 27, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.expression;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import parser.CIdentifier;
import semantic.CDataEntity;
import semantic.CBaseEntityFactory;
import utils.Transcoder;

/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CIdentifierTerminal extends CTerminal
{
	/* (non-Javadoc)
	 * @see parser.condition.CConditionalTerminal#Export()
	 */
	public CIdentifierTerminal(CIdentifier Id)
	{
		m_Identifier = Id;
	}
	
//	public String GetType()
//	{
//		return "Identifier" ;
//	}
//	public String GetValue()
//	{
//		return m_Identifier.Export() ;
//	}
	CIdentifier m_Identifier = null ;

	/* (non-Javadoc)
	 * @see parser.expression.CTerminal#ExportTo(org.w3c.dom.Element, org.w3c.dom.Document)
	 */
	public void ExportTo(Element e, Document root)
	{
		if (m_Identifier != null)
		{
			m_Identifier.ExportTo(e, root) ;
		}		
	}

	/* (non-Javadoc)
	 * @see parser.expression.CTerminal#IsReference()
	 */
	public boolean IsReference()
	{
		return true;
	}
	
	public boolean IsOne()
	{
		return false;
	}
	
	public boolean IsMinusOne()
	{
		return false;
	}
	
	public CIdentifier GetIdentifier()
	{
		return m_Identifier ;
	}

	/* (non-Javadoc)
	 * @see parser.expression.CTerminal#GetValue()
	 */
	public String GetValue()
	{
		return "" ;
	}

	/* (non-Javadoc)
	 * @see parser.expression.CTerminal#GetDataEntity(semantic.CBaseEntityFactory)
	 */
	public CDataEntity GetDataEntity(int nLine, CBaseEntityFactory factory)
	{
		CDataEntity e = m_Identifier.GetDataReference(nLine, factory);
		if (e == null)
		{
			Transcoder.logError(nLine, "ERROR : identifier not found : "+m_Identifier.GetName());
			int n = 0 ;
		}
		return e ; 
	}
	public String toString()
	{
		return m_Identifier.toString() ;
	}
	
	public CDataEntity GetDataReference(int nLine, CBaseEntityFactory factory)
	{
		return m_Identifier.GetDataReference(nLine, factory);
	}	

	public boolean IsNumber()
	{
		return false ;
	}
}
