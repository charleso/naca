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
package generate.fpacjava;

import generate.java.CJavaExporter;
import semantic.expression.CBaseEntityCondition;
import semantic.expression.CEntityCondOr;

/**
 * @author S. Charton
 * @version $Id$
 */
public class CFPacJavaCondOr extends CEntityCondOr
{

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
		String cs = CJavaExporter.ExportChildCondition(GetPriorityLevel(), m_Op1) ;
		cs += " \n|| " + CJavaExporter.ExportChildCondition(GetPriorityLevel(), m_Op2) ;
		return cs ;
	}
	public CBaseEntityCondition GetOppositeCondition()
	{
		CFPacJavaCondAnd eAnd = new CFPacJavaCondAnd();
		eAnd.SetCondition(m_Op1.GetOppositeCondition(), m_Op2.GetOppositeCondition()) ;
		return eAnd;
	}
	public int GetPriorityLevel()
	{
		return 2;
	}

}
