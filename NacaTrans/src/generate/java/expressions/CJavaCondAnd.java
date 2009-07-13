/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 18 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.expressions;

import generate.java.CJavaExporter;
import semantic.expression.CBaseEntityCondition;
import semantic.expression.CEntityCondAnd;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCondAnd extends CEntityCondAnd
{
	public int GetPriorityLevel()
	{
		return 1;
	}
	public CBaseEntityCondition GetOppositeCondition()
	{
		CJavaCondOr eOr = new CJavaCondOr();
		eOr.SetCondition(m_Op1.GetOppositeCondition(), m_Op2.GetOppositeCondition()) ;
		return eOr;
	}
	public String Export()
	{
		if (m_Op1.ignore())
		{
			return m_Op2.Export();
		}
		else if (m_Op2.ignore())
		{
			return m_Op1.Export();
		}
		String cs = CJavaExporter.ExportChildCondition(GetPriorityLevel(), m_Op1) + " \n&& " ; 
		cs += CJavaExporter.ExportChildCondition(GetPriorityLevel(), m_Op2) ;
		return cs ;
	}
	/* (non-Javadoc)
	 * @see semantic.expression.CBaseEntityCondition#isBinaryCondition()
	 */
}
