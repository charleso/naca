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
 * Created on 20 mai 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package generate.java.expressions;

import generate.CBaseLanguageExporter;
import semantic.CDataEntity;
import semantic.expression.CEntityDigits;
import utils.CObjectCatalog;

/**
 * @author U930CV
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CJavaDigits extends CEntityDigits
{

	/**
	 * @param cat
	 * @param out
	 * @param data1
	 */
	public CJavaDigits(CObjectCatalog cat, CBaseLanguageExporter out, CDataEntity data1)
	{
		super(cat, out, data1);
	}

	/* (non-Javadoc)
	 * @see semantic.CDataEntity#ExportReference(getLine())
	 */
	public String ExportReference(int nLine)
	{
		return "digits("+m_Reference.ExportReference(getLine())+")";
	}
	public boolean isValNeeded()
	{
		return false;
	}


}
