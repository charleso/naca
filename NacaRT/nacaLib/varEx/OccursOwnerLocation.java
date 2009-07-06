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

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: OccursOwnerLocation.java,v 1.1 2006/04/19 09:53:08 cvsadmin Exp $
 */
public class OccursOwnerLocation
{
	public OccursOwnerLocation(int nDistanceFromOccursOwner, int nAbsolutePositionOccursOwner, int nSizeOccursOwnerOf1Entry)
	{
		m_nDistanceFromOccursOwner = nDistanceFromOccursOwner;
		m_nAbsolutePositionOccursOwner = nAbsolutePositionOccursOwner;
		m_nSizeOccursOwnerOf1Entry = nSizeOccursOwnerOf1Entry;
	}
	
	int m_nDistanceFromOccursOwner;
	int m_nAbsolutePositionOccursOwner;
	int m_nSizeOccursOwnerOf1Entry;	
}
