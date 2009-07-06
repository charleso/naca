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
 * @version $Id: ConstantItem.java,v 1.1 2008/04/01 07:10:30 u930di Exp $
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
