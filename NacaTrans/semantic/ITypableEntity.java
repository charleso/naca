/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 1 avr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package semantic;

/**
 * @author U930CV
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface ITypableEntity
{

	/**
	 * @param format
	 */
	void SetTypeEdited(String format);

	/**
	 * @param length
	 */
	void SetTypeString(int length);

	/**
	 * @param length
	 * @param decimal
	 */
	void SetTypeNum(int length, int decimal);

	/**
	 * @param length
	 * @param decimal
	 */
	void SetTypeSigned(int length, int decimal);

}
