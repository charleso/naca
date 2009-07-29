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

import java.text.SimpleDateFormat;

public class IsTypeUtil {
	
	//private static Logger _logger = Logger.getLogger(IsTypeUtil.class.getName());
	
  /**
   * Creates a new StreamUtil object.
   */
  public IsTypeUtil() {
  }
  
 
//--------------------------------------------------------------------------	
	
	public static boolean isNumeric(String s){
	
	  if ("".equalsIgnoreCase(s)) return false;	
		
	  String validChars = "0123456789";
	  boolean isNumeric = false;
	
	  for (int i = 0; i < s.length() && !isNumeric; i++) { 
	    char c = s.charAt(i); 
	    if (validChars.indexOf(c) == -1) {	
	    	isNumeric = false;
	    } else {
	    	isNumeric = true;
	    }
	  }
	  return isNumeric;
	  
	}// end of isNumeric()

//	---------------------------------------------------------

	public static boolean isDate(String strDate, SimpleDateFormat sdf) {
		
	  boolean isDate = false;
	  
	  try {
	  	
		  sdf.parse(strDate);
		  isDate = true;
	  	
	  } catch (Exception ex) {
		  isDate = false;
		  //_logger.error("Util.java - Not able to convert strToDate - The given date format is incorrect", ex);
	  }
	  	
	  return isDate;
	  
	}//end of isDate()

  
//------------------------------------------------------------------

	
} // end of class