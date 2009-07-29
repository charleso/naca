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
package jlib.exception;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 * This interface must be implemented in concrete class that can be used for checking their internal state.
 * ApplicationExceptions and other Consultas standard exceptions supports a throwIfNullOrInvalid() method 
 * that takes an IValidable concrete object. 
 * The throwIfNullOrInvalid(IValidable) static method calls takes an IValidable concrete instance, 
 * and calls the isValid() of this implementation. 
 * The implementation returns a boolean value that reflects the validity of it's internal state.
 *
 * Sample: 
 * // Item whose internal state can be validated
 * class Item implements IValidable	
 * {
 * 		private String m_csSomeNotEmptyValue = null;
 *  	private int m_nSomePositiveValue = 0;
 *  	...
 *  	Item(String csSomeNotEmptyValue, int nSomePositiveValue)
 *  	{
 *  		m_csSomeNotEmptyValue = csSomeNotEmptyValue;
 *  		m_nSomePositiveValue = nSomePositiveValue;
 *  	}
 *  
 *  	void dec()
 *  	{
 *  		m_nSomePositiveValue--;
 *  	}
 *  
 *  	public boolean isValid()
 *  	{
 *  		if(m_nSomePositiveValue > 0)
 *  			if(!StringUtil.isEmtpy(m_csUpperCaseOnlyValue))
 *  				return true;		// The internal state of Item is valid
 *  		return false;		// Item'state is invalid
 *  	}
 *  	...   
 * }   
 * 
 * In a method that wants to check the validity of some Item instance:
 * class Manager
 * {
 * 		...
 * 		void doSomeAction()
 * 			throws CustomApplicativeException
 * 		{
 * 			...
 * 			Item item = new Item(1, "xxx");
 * 			handleItem(item);
 * 			...
 * 		}
 * 		
 * 		private void handleItem(Item item)
 * 			throws CustomApplicativeException
 * 		{ 
 * 			item.dec();	// item.m_nSomePositiveValue is set to 0 !
 * 
 * 			// An exception will be thrown in this sample, as item.m_nSomePositiveValue == 0 !
 * 			CustomApplicativeException.throwIfNullOrInvalid(
 * 				item, 
 * 				CustomApplicativeException.INVALID_ITEM, 
 * 				"The item has an invalid state: Item="+StringUtil.toString(item));
 * 			...
 * 		}
 * 		...
 * }
 * 
 * Note: The StringUtil.toString(item) construction calls item.toString() method if item is not null.
 * If item is null, then "(null)" is returned.    
 */
public interface IValidable
{
	public boolean isValid();
}
