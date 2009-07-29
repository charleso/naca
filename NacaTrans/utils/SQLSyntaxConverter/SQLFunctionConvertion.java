/*
 * NacaTrans - Naca Transcoder v1.2.0.
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaTrans - Naca Transcoder v1.2.0.beta.1
 *
 * Copyright (c) 2008-2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
package utils.SQLSyntaxConverter;

import utils.DCLGenConverter.DCLGenConverterTarget;
import jlib.xml.Tag;

public class SQLFunctionConvertion
{
	private String m_csSource = null;
	private String m_csValueDB2 = null;
	private String m_csValueOracle = null;
	
	SQLFunctionConvertion()
	{
	}
	
	String fill(Tag tagFunction)
	{
		m_csSource = tagFunction.getVal("Source");
		m_csValueDB2 = tagFunction.getVal("TargetDB2");
		m_csValueOracle = tagFunction.getVal("TargetOracle");
		return m_csSource;
	}

	public String getSource()
	{
		return m_csSource;
	}

	public String getValueForTarget(DCLGenConverterTarget target)
	{
		if(target.isDB2())
			return m_csValueDB2;
		return m_csValueOracle;	
	}
}
