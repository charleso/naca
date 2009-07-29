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
 * Created on 18 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java.expressions;

import generate.java.CJavaExporter;
import semantic.expression.CBaseEntityCondition;
import semantic.expression.CEntityCondNot;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaCondNot extends CEntityCondNot
{

	public CBaseEntityCondition GetOppositeCondition()
	{
		return m_Cond ;
	}
	public String Export()
	{
		return "!" + CJavaExporter.ExportChildCondition(GetPriorityLevel(), m_Cond) ;
	}
	public int GetPriorityLevel()
	{
		return 6;
	}

	
}
