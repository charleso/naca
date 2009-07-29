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
/**
 * 
 */
package nacaLib.varEx;

import java.util.Hashtable;

import jlib.misc.LineRead;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class VarDefEncodingConvertibleManagerContainer
{
	public VarDefEncodingConvertibleManagerContainer()
	{
	}
	
	public VarDefEncodingConvertibleManager getEncodingManager(VarBase varDest)
	{
		if(m_hash == null)
			m_hash = new Hashtable<Integer, VarDefEncodingConvertibleManager>();

		Integer varId = new Integer(varDest.getId());
		VarDefEncodingConvertibleManager encodingManager = m_hash.get(varId);
		if(encodingManager == null)
		{
			encodingManager = new VarDefEncodingConvertibleManager();
			varDest.getVarDef().getChildrenEncodingConvertiblePosition(encodingManager);
			encodingManager.compress();
			m_hash.put(varId, encodingManager);
		}
		return encodingManager;
	}
	
	public boolean getEncodingManagerConvertAndWrite(LineRead lineRead, VarBase varDest)
	{
		VarDefEncodingConvertibleManager encodingManager = getEncodingManager(varDest);
		if(encodingManager != null)
		{
			encodingManager.fillDestAndConvertIntoAscii(lineRead, varDest);
			return true;
		}
		return false;
	}
	
	void getConvertedBytesAsciiToEbcdic(VarBase varSource, byte tbyDest[], int nLengthDest)
	{
		if(m_hash == null)
			m_hash = new Hashtable<Integer, VarDefEncodingConvertibleManager>();

		Integer varId = new Integer(varSource.getId());
		VarDefEncodingConvertibleManager v = m_hash.get(varId);
		if(v == null)
		{
			v = new VarDefEncodingConvertibleManager();
			varSource.getVarDef().getChildrenEncodingConvertiblePosition(v);
			v.compress();
			m_hash.put(varId, v);
		}				
		if(v != null)
		{
			varSource.exportIntoByteArray(tbyDest, nLengthDest);
			v.getConvertedBytesAsciiToEbcdic(varSource.m_bufferPos.m_nAbsolutePosition, tbyDest, nLengthDest);
		}
	}
	
	private Hashtable<Integer, VarDefEncodingConvertibleManager> m_hash = null;	
}
