/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 4 oct. 04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package nacaLib.misc;

import nacaLib.varEx.*;
import nacaLib.base.CJMapObject;

public class CCommarea extends CJMapObject
{
	public CCommarea()
	{
	}
	
	public void setVarPassedByValue(Var var, int length)
	{
		if (var.getLength() < length)
		{
			length = var.getLength();
		}
		m_charBufferCopy = var.exportToCharBuffer(length);
		m_Var = null;
		m_bByValue = true;
	}
	public void setVarPassedByValue(InternalCharBuffer buff)
	{
		m_charBufferCopy = buff ;
		m_Var = null;
		m_bByValue = true;
	}

	public void setVarPassedByValue(Form form)
	{
		m_charBufferCopy = form.encodeToCharBuffer();
		m_Var = null;
		m_bByValue = true;
	}

	public void setVarPassedByRef(Var var)
	{
		m_charBufferCopy = null;
		m_Var = var;
		m_bByValue = false;
	}
	
	void setLength(int nLength)
	{
		m_nLength = nLength;
		m_bLengthSpecified = true;
	}
	
	public int getLength()
	{
		if(m_Var != null)
		{
			if(m_bLengthSpecified)
				return m_nLength;
			return m_Var.getLength(); 
		}
		if(m_charBufferCopy != null)
		{
			if(m_bLengthSpecified)
				return m_nLength;
			return m_charBufferCopy.getBufferSize(); 
		}
		return 0; 
	}
	
	public CCallParam buildCallParam()
	{
		if(m_Var != null)	// By ref
		{
			CallParamByRef callParam = new CallParamByRef(m_Var);
			return callParam;
		}
		if(m_charBufferCopy != null)	// By value
		{
			CallParamByCharBuffer callParam = new CallParamByCharBuffer(m_charBufferCopy);
			return callParam;
		}
		return null;
	}
	
	public CallParamFpac buildCallParamFPac()
	{
		if(m_charBufferCopy != null)	// By value
		{
			CallParamFpac callParam = new CallParamFpac(m_charBufferCopy);
			return callParam;
		}
		return null;
	}
		
	private boolean m_bByValue = false;
	private Var m_Var = null;
	private InternalCharBuffer m_charBufferCopy = null;
	private int m_nLength = 0;
	private boolean m_bLengthSpecified = false;
	/**
	 * @param varDest
	 */
//	public CCallParam GetParam()
//	{
//		CallParamByCharBuffer param = new CallParamByCharBuffer(m_charBufferCopy) ;
//		return param ;
//		//param.MapOn(varDest) ;
//	}
}
