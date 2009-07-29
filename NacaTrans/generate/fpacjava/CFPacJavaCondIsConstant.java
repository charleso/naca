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

import semantic.expression.CBaseEntityCondition;
import semantic.expression.CEntityCondIsConstant;

/**
 * @author S. Charton
 * @version $Id$
 */
public class CFPacJavaCondIsConstant extends CEntityCondIsConstant
{


	/* (non-Javadoc)
	 * @see semantic.expression.CBaseEntityCondition#GetPriorityLevel()
	 */
	public int GetPriorityLevel()
	{
		return 7;
	}

	/* (non-Javadoc)
	 * @see semantic.expression.CBaseEntityCondition#GetOppositeCondition()
	 */
	public CBaseEntityCondition GetOppositeCondition()
	{
		CFPacJavaCondIsConstant not = new CFPacJavaCondIsConstant() ;
		not.m_bIsLowValue = m_bIsLowValue ;
		not.m_bIsHighValue = m_bIsHighValue ;
		not.m_bIsOpposite = ! m_bIsOpposite ;
		not.m_bIsSpace = m_bIsSpace ;
		not.m_bIsZero = m_bIsZero ;
		not.m_Reference = m_Reference ;
		m_Reference.RegisterVarTesting(not);
		return not;
	}

	/* (non-Javadoc)
	 * @see semantic.expression.CBaseEntityCondition#ExportTo(semantic.CBaseLanguageExporter)
	 */
	public String Export()
	{
		String cs = "is" ;
		if (m_bIsOpposite)
		{
			cs += "Not" ;
		}
		if (m_bIsZero)
		{
			cs += "Zero(";
		}
		else if (m_bIsSpace)
		{
			cs += "Space(";
		}
		else if (m_bIsLowValue)
		{
			cs += "LowValue(";
		}
		else if (m_bIsHighValue)
		{
			cs += "HighValue(";
		}
		cs += m_Reference.ExportReference(getLine()) + ")";
		return cs ;

	}

	/* (non-Javadoc)
	 * @see semantic.expression.CBaseEntityCondition#GetSpecialCondition(java.lang.String)
	 */

}
