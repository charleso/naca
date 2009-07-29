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
 * <p>Title: Numbers</p>
 * <p>Description: Number utilities</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: Consultas SA</p>
 * @author  u930do
 * @version Jun-2006
 */
package jlib.misc;


/**
 * @author u930do
 */
public final class Numbers {
 
	private Numbers () {}
	
	  
  public static double roundCurrency(String cur, double amount) {
	  
	  if (amount == 0)
		  return amount;
	  
	  if (("CHF").equals(cur))
		  return Numbers.round5(amount);
	  else 
		  return Numbers.round1(amount);
  }
	
  /**
   * round to 1 cent
   * @param x   original value
   * @return    rounded value
   */
  public static double round1 (double x) {
    return Math.floor(x * 100.0 + 0.5) / 100.0;
  }
 
  /**
   * round to 5 cents
   * @param x   original value
   * @return    rounded value
   */
  public static double round5 (double x) {
    return Math.floor(x * 20.0 + 0.5) / 20.0;
  }

 
}
