/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * Created on 7 déc. 04
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
package nacaLib.stringSupport;
import nacaLib.misc.KeyPressed;
import nacaLib.varEx.Var;
import nacaLib.varEx.VarAndEdit;


import java.util.ArrayList;

public class Concat
{
	public Concat()
	{
	}
	
	public Concat concat(VarAndEdit var)
	{
		String cs = var.getString();
		return concat(cs);
	}
	public Concat concat(KeyPressed key)
	{
		if (key != null)
		{
			String cs = key.getValue() ;
			return concat(cs);
		}
		return this ;
	}
	
	public Concat concat(String cs)
	{
		m_arrChunks.add(cs);
		return this;
	}

	public Concat concatDelimitedDecimalPoint(Var var)
	{
		String cs = var.getString();
		return concatDelimitedDecimalPoint(cs);
	}
		
	public Concat concatDelimitedDecimalPoint(String cs)
	{
		int nPos = cs.lastIndexOf('.');
		if(nPos != -1)
			cs = cs.substring(0, nPos); 
		m_arrChunks.add(cs);
		return this;
	}
	
	public Concat concatDelimitedBySpaces(Var var)
	{
		String cs = var.getString();
		return concatDelimitedBySpaces(cs);
	}

	public Concat concatDelimitedBySpaces(String cs)
	{
		int nSpacePos = cs.indexOf(' ');
		if(nSpacePos != -1)
		 	cs = cs.substring(0, nSpacePos);
		m_arrChunks.add(cs);
		return this;
	}

	public Concat concatDelimitedBySize(Var var)
	{
		String cs = var.getString();
		return concatDelimitedBySize(cs);
	}
	
	public Concat concatDelimitedBySize(String cs)
	{
		m_arrChunks.add(cs);
		return this;
	}	

	public Concat concatDelimitedBy(Var var)
	{
		String cs = var.getString();
		return concatDelimitedBy(cs);
	}
	
	public Concat concatDelimitedBy(String cs)
	{
		cs = cs.trim();
		m_arrChunks.add(cs);
		return this;
	}
	
	public Concat concatDelimitedBy(Var var, String csDelimiter)
	{
		String cs = var.getString();
		return concatDelimitedBy(cs, csDelimiter);
	}
	
	public Concat concatDelimitedBy(Var var, Var varDelimiter)
	{
		String cs = var.getString();
		return concatDelimitedBy(cs, varDelimiter.getString());
	}
	
	public Concat concatDelimitedBy(String cs, String csDelimiter)
	{
		int nPos = cs.indexOf(csDelimiter);
		if(nPos != -1)
		 	cs = cs.substring(0, nPos);
		m_arrChunks.add(cs);
		return this;
	}
	
	public Concat withPointer(Var varPointer)
	{
		m_varPointer = varPointer;
		return this;
	}
	
	public ConcatTo into(VarAndEdit var)
	{
		String csOut = null;
		
		if(m_varPointer != null)
		{
			String csVar = var.getString();
			int nInitPosition = m_varPointer.getInt() -1;
			String csInitial = csVar.substring(0, nInitPosition); 
			csOut = csInitial + getString();
		}
		else
			csOut = getString();
		
//		int nNbItems = m_arrChunks.size();
//		for(int n=0; n<nNbItems; n++)
//		{
//			String cs = m_arrChunks.get(n);
//			csOut = csOut.concat(cs);
//		}

		//var.set(csOut);
		var.setStringAtPosition(csOut, 0, csOut.length());
		if(m_varPointer != null)	// Set the pointer to the string length 
		{
			int nOutLg = csOut.length();
			int nVarLength = var.getBodySize();
			int nLg = Math.min(nVarLength, nOutLg);
			
			m_varPointer.set(nLg+1);
			
			if (nOutLg > nVarLength)
			{
				m_bFailed = true;
			}
		}
	
		ConcatTo concatTo = new ConcatTo(this);
		return concatTo;
	}
	
	public String getString()
	{
		String csOut = new String();
		int nNbItems = m_arrChunks.size();
		for(int n=0; n<nNbItems; n++)
		{
			String cs = m_arrChunks.get(n);
			csOut += cs;	// + csOut.concat(cs);
		}
		return csOut;
	}
	
	boolean failed()
	{
		return m_bFailed;
	}
	
	Var m_varPointer = null;
	ArrayList<String> m_arrChunks = new ArrayList<String>();
	boolean m_bFailed = false;
}
