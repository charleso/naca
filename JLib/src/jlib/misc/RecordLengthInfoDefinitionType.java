/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.misc;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: RecordLengthInfoDefinitionType.java,v 1.1 2006/09/04 16:01:41 u930di Exp $
 */
public class RecordLengthInfoDefinitionType
{
	public final static RecordLengthInfoDefinitionType FileDescriptorDef = new RecordLengthInfoDefinitionType(); 
	public final static RecordLengthInfoDefinitionType FileHeaderDef = new RecordLengthInfoDefinitionType();
	public final static RecordLengthInfoDefinitionType AutoDetermination = new RecordLengthInfoDefinitionType();
	
	private RecordLengthInfoDefinitionType()
	{	
	}
}
