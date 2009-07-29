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
 * Created on 6 août 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package generate.java;

//import parser.expression.CBaseExpressionExporter;
import generate.CBaseLanguageExporter;
import semantic.CEntityArrayReference;
import semantic.expression.CBaseEntityExpression;
import utils.*;

/**
 * @author sly
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CJavaArrayReference extends CEntityArrayReference
{

	/**
	 * @param l
	 * @param cat
	 * @param out
	 */
	public CJavaArrayReference(int l, CObjectCatalog cat, CBaseLanguageExporter out)
	{
		super(l, cat, out);
	}

	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#ExportReference(semantic.CBaseLanguageExporter)
	 */
	public String ExportReference(int nLine)
	{
		String out = "" ;
		if (m_Reference != null && m_arrIndexes != null)
		{
			CBaseEntityExpression exp = m_arrIndexes.get(0) ;
			out = m_Reference.ExportReference(nLine) + ".getAt(" + exp.Export();
			for (int i=1; i<m_arrIndexes.size();i++)
			{
				exp = m_arrIndexes.get(i) ;
				out += ", " + exp.Export();
			} 
			out += ")" ;
			
//			int nNbOccurs = m_Reference.getNbDimOccurs();
//			if(nNbOccurs != m_arrIndexes.size())
//			{
//				Transcoder.logWarn(nLine, "Invalid number of indexes specified for variable: "+m_Reference.GetName()+"; There should be "+ nNbOccurs + " indexes");
//			}
		}
		return out ;
	}
	
	public int getNbDimOccurs()
	{
		return 0;
	}
	
	public boolean HasAccessors()
	{
		return false;
	}
	protected void DoExport()
	{
		// unused
	}
	public String ExportWriteAccessorTo(String value)
	{
		//unsued
		return "" ; 
	}
	public boolean isValNeeded()
	{
		return true;
	}



	/* (non-Javadoc)
	 * @see semantic.CBaseDataEntity#GetDataType()
	 */
	public CDataEntityType GetDataType()
	{
		return CDataEntityType.VAR ;
	}

}
