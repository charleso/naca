/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on Jul 28, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package parser.expression;

import org.w3c.dom.*;

import com.sun.org.apache.xpath.internal.Expression;

import semantic.CDataEntity;
import semantic.CBaseEntityFactory;
import semantic.expression.CBaseEntityCondition;
import semantic.expression.CBaseEntityExpression;
import utils.NacaTransAssertException;
import utils.Transcoder;


/**
 * @author U930CV
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class CExpression
{
//	public abstract void WriteTo(CBaseExpressionExporter exporter);
	//public abstract int GetPriorityLevel() ;
	public CExpression(int line)
	{
		m_line = line ;
	}
	private int m_line = 0 ;
	
	public int getLine()
	{
		return m_line;
	}
	
	public abstract CBaseEntityExpression AnalyseExpression(CBaseEntityFactory factory);
	public CBaseEntityCondition AnalyseCondition(CBaseEntityFactory factory)
	{
		return AnalyseCondition(factory, new CDefaultConditionManager(null));
	}
	public abstract CBaseEntityCondition AnalyseCondition(CBaseEntityFactory factory, CDefaultConditionManager masterCond);
	
	public CExpression NewCopy(int line, CExpression term1, CExpression term2)
	{ // used by some child classes
		return null ;
	}
	public Element Export(Document root)
	{
		boolean b = CheckMembersBeforeExport();
		if(b)
		{
			Element e = DoExport(root) ;
			return e ;
		}
		return null;
	}
	public abstract Element DoExport(Document root);
	protected abstract boolean CheckMembersBeforeExport();
	
	protected boolean CheckMemberNotNull(Object o)
	{
		if(o == null)
		{
			Transcoder.logError(getLine(), "ERROR: Expression member is null: Cannot generate");
			return false;
		}
		return true;
	}
	

	public boolean IsReference()
	{
		return false ;
	}	
	public boolean IsConstant()
	{
		return false ;
	}
	public CDataEntity GetReference(CBaseEntityFactory factory)
	{
		return null ;
	} 
	public String GetConstantValue()
	{
		return "" ;
	} 
	
	protected void ASSERT(Object o, CExpression expressionSource) 
	{
		if (o == null)
		{
			if(expressionSource != null)
				Transcoder.logError(getLine(), "ERROR: generated string is wrong; cannot generate output; please check source syntax; output: "+expressionSource.toString());
			else
				Transcoder.logError(getLine(), "ERROR: generated string is wrong; cannot generate output; please check source syntax; output is null");
			throw new NacaTransAssertException("ASSERTION: Cannot continue transcoding");
		}
	}
	protected void ASSERT() 
	{
		throw new NacaTransAssertException("ASSERT");
	}

	public abstract CExpression GetFirstConditionOperand() ;
	public abstract CExpression GetSimilarExpression(CExpression operand) ;
	public abstract boolean IsBinaryCondition() ;
	/**
	 * @return
	 */
	public Object GetConditionType()
	{
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * @return
	 */
	//public abstract CExpression getMasterBinaryCondition() ;
	public abstract CExpression GetFirstCalculOperand();

}
