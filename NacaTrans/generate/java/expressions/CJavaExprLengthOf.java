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
import semantic.CDataEntity;
import semantic.expression.CEntityExprLengthOf;
import semantic.expression.CEntityLengthOf;
import semantic.expression.CBaseEntityExpression.CEntityExpressionType;
import utils.CObjectCatalog;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class CJavaExprLengthOf extends CEntityExprLengthOf
{
	/**
	 * @param cat
	 * @param out
	 * @param data
	 */
	public CJavaExprLengthOf(int nLine, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(cat, out);
	}
	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#ExportReference(generate.CBaseLanguageExporter)
	 */
	public String ExportReference(int nLine)
	{
		return "lengthOf(" + m_dataEntity.ExportReference(getLine()) + ")";
	}
	
	public String Export()
	{
		return "lengthOf(" + m_dataEntity.ExportReference(getLine()) + ")";
	}
	
	public boolean isValNeeded()
	{
		return true;
	}
	
	@Override
	public CEntityExpressionType getExpressionType()
	{
		return CEntityExpressionType.VARIABLE;
	}
	
	public CDataEntity GetSingleOperator()
	{
		return m_dataEntity;
	}


}
