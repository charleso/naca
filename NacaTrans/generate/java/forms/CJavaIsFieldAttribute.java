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
/*
 * Created on 19 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.forms;

import semantic.expression.CBaseEntityCondition;
import semantic.forms.CEntityIsFieldAttribute;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaIsFieldAttribute extends CEntityIsFieldAttribute
{

	/* (non-Javadoc)
	 * @see semantic.expression.CBaseEntityCondition#GetPriorityLevel()
	 */
	public int GetPriorityLevel()
	{
		if (m_nbConditions <= 1)
		{
			return 7;
		}
		else
		{
			return 1 ; // --> there are " || " in case of several conditions
		}
	}

	/* (non-Javadoc)
	 * @see semantic.expression.CBaseEntityCondition#GetOppositeCondition()
	 */
	public CBaseEntityCondition GetOppositeCondition()
	{
		CJavaIsFieldAttribute cond = new CJavaIsFieldAttribute() ;
		cond.m_bIsAutoSkip = m_bIsAutoSkip ;
		cond.m_bIsBright = m_bIsBright ;
		cond.m_bIsCleared = m_bIsCleared ;
		cond.m_bIsDark = m_bIsDark ;
		cond.m_bIsModified = m_bIsModified ;
		cond.m_bIsProtected = m_bIsProtected ;
		cond.m_bIsNumeric = m_bIsNumeric ;
		cond.m_bIsUnmodified = m_bIsUnmodified ;
		cond.m_bIsUnprotected = m_bIsUnprotected ;
		cond.m_bOpposite = ! m_bOpposite ;
		cond.m_nbConditions = m_nbConditions ;
		cond.m_Reference = m_Reference ;
		cond.m_VarValue = m_VarValue ;
		m_Reference.RegisterVarTesting(cond) ;
		return cond ;
	}

	/* (non-Javadoc)
	 * @see semantic.expression.CBaseEntityCondition#ExportTo(semantic.CBaseLanguageExporter)
	 */
	public String Export()
	{
		String start = "is" ;
		if (m_bOpposite)
		{
			start += "Not" ;
		}
		String cs = "" ;
		boolean bAddBracket = false;
		if (m_VarValue != null)
		{
			cs += start + "FieldAttribute("+ m_Reference.ExportReference(getLine())+", " + m_VarValue.ExportReference(getLine())+ ")";
		}		
		else
		{
			if (m_bIsAutoSkip)
			{
				cs += BuildString(cs, start+"FieldAutoSkip");
			}
			else if (m_bIsProtected)
			{
				cs += BuildString(cs, start+"FieldProtected");
			}
			else if (m_bIsNumeric)
			{
				cs += BuildString(cs, start+"FieldNumeric");
			}
			else if (m_bIsUnprotected)
			{
				cs += BuildString(cs, start+"FieldUnprotected");
			}
			if (m_bIsBright)
			{
				if (cs.length() > 0) bAddBracket = true; 
				cs += BuildString(cs, start+"FieldBright");
			}
			else if (m_bIsDark)
			{
				if (cs.length() > 0) bAddBracket = true;
				cs += BuildString(cs, start+"FieldDark");
			}
			if (m_bIsModified)
			{
				if (cs.length() > 0) bAddBracket = true;
				cs += BuildString(cs, start+"FieldModified");
			}
			else if (m_bIsUnmodified)
			{
				if (cs.length() > 0) bAddBracket = true;
				cs += BuildString(cs, start+"FieldUnmodified");
			}
			else if (m_bIsCleared)
			{
				if (cs.length() > 0) bAddBracket = true;
				cs += BuildString(cs, start+"FieldCleared");
			}
		}
		if (bAddBracket)
			return "(" + cs + ")";
		else
			return cs ;
	}
	private String BuildString(String line, String cs)
	{
		String toto = "" ;
		if (line.length() > 0)
		{
			if (m_bOpposite)
			{
				toto += " || " ;
			}
			else
			{
				toto += " && " ;
			}
		}
		return toto + cs + "(" + m_Reference.ExportReference(getLine()) + ")" ;
	} 

}
