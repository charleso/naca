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
package generate.java.expressions;

import semantic.expression.CEntityConstant;
import utils.modificationsReporter.Reporter;

/**
 * @author S. Charton
 * @version $Id$
 */
public class CJavaConstant extends CEntityConstant
{

	/**
	 * @param val
	 */
	public CJavaConstant(Value val)
	{
		super(val);
	}

	/**
	 * @see semantic.CDataEntity#ExportReference(getLine())
	 */
	@Override
	public String ExportReference(int nLine)
	{
		switch (m_eValue)
		{
			case LOW_VALUE :
			{
				Reporter.Add("Modif_PJ", "CobolConstant.LowValue");
				return "CobolConstant.LowValue" ;		
			}
			case HIGH_VALUE :
				return "CobolConstant.HighValue" ;
			case SPACES :
				return "CobolConstant.Spaces" ;
			default:
				return "[Undefined]" ;
		}
	}

	/**
	 * @see semantic.CBaseLanguageEntity#DoExport()
	 */
	@Override
	protected void DoExport()
	{
		// unused
	}

}
