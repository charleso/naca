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

import parser.expression.CProdExpression;
import semantic.expression.CEntityExprProd;
import utils.modificationsReporter.Reporter;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaExprProd extends CEntityExprProd
{

	public String Export()
	{
		String cs ;
		if (m_Type == CProdExpression.CProdType.PROD)
		{
			cs = "multiply(" ;
		}
		else if(m_Type == CProdExpression.CProdType.DIVIDE)
		{
			cs = "divide(" ;
		}
		else if(m_Type == CProdExpression.CProdType.POWER)
		{
			Reporter.Add("Modif_PJ", "CProdExpression.CProdType.POWER");
			cs = "power(" ;
		}
		else
			cs = "[UnknownProductFunction](" ;
		cs += m_Op1.Export() + ", \n" + m_Op2.Export() + ")" ;
		return cs ;
	}

}
