/*
 * JLib - Publicitas Java library v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.jslibComp;

import java.io.Serializable;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class ConstantItem implements Serializable
{
	public String codeId;
	public String value;
	
	public ConstantItem(String csId, String csValue)
	{
		codeId = csId;
		value = csValue;
	}
	
	public String getCodeId()
	{
		return codeId;
	}
	
	public String getValue()
	{
		return value;
	}
}
