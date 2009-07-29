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
package jlib.misc;

/**
 * @author S. Charton
 * @version $Id$
 */
public class FileCompareStat
{

	private int nbLinesLeft = 0;
	private int nbLinesRight = 0 ;
	private boolean equal = false ;
	private int nLineDiff = 0;
	/**
	 * @return Returns the equal.
	 */
	public boolean isEqual()
	{
		return equal;
	}
	/**
	 * @param equal The equal to set.
	 */
	public void setEqual(boolean equal)
	{
		this.equal = equal;
	}
	/**
	 * @return Returns the nbLinesLeft.
	 */
	public int getNbLinesLeft()
	{
		return nbLinesLeft;
	}
	/**
	 * @param nbLinesLeft The nbLinesLeft to set.
	 */
	public void setNbLinesLeft(int nbLinesLeft)
	{
		this.nbLinesLeft = nbLinesLeft;
	}
	/**
	 * @return Returns the nbLinesRight.
	 */
	public int getNbLinesRight()
	{
		return nbLinesRight;
	}
	/**
	 * @param nbLinesRight The nbLinesRight to set.
	 */
	public void setNbLinesRight(int nbLinesRight)
	{
		this.nbLinesRight = nbLinesRight;
	}
	/**
	 * @return Returns the nbLinesRight.
	 */
	public int getNLineDiff()
	{
		return nLineDiff;
	}
	/**
	 * @param nbLinesRight The nbLinesRight to set.
	 */
	public void setNLineDiff(int nLineDiff)
	{
		this.nLineDiff = nLineDiff;
	}
}
