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
/**
 * 
 */
package generate.java.expressions;

import generate.CBaseLanguageExporter;
import semantic.expression.CEntityCurrentDateSQLFunction;
import utils.CObjectCatalog;
import utils.Transcoder;
import utils.SQLSyntaxConverter.SQLSyntaxConverter;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: CJavaCurrentDateSQLFunction.java,v 1.1 2009/04/24 13:05:13 u930di Exp $
 */
public class CJavaCurrentDateSQLFunction extends CEntityCurrentDateSQLFunction
{
	private String m_csOriginalValue = null;
	
	public CJavaCurrentDateSQLFunction(CObjectCatalog cat, CBaseLanguageExporter out, String csOriginalValue)
	{
		super(cat, out);
		m_csOriginalValue = csOriginalValue;
	}
	
	public String ExportReference(int nLine)
	{
		SQLSyntaxConverter sqlSyntaxConverter = Transcoder.getSQLSyntaxConverter();
		if(sqlSyntaxConverter != null)
		{
			String cs = sqlSyntaxConverter.resolve("CURRENT_DATE");
			Transcoder.logInfo(nLine, "SQLSyntaxConverter: Replacing declared 'CURRENT_DATE' SQL Function " + m_csOriginalValue + " by " + cs);
			return cs;
		}		
		return m_csOriginalValue;
	}
	
	public boolean isValNeeded()
	{
		return false;
	}
}