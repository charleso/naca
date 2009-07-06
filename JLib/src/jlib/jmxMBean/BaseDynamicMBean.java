/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.jmxMBean;

import java.util.Iterator;
import javax.management.*;


public abstract class BaseDynamicMBean implements DynamicMBean
{
	public AttributeList getAttributes(String[] attributeNames) 
	{
        if(attributeNames != null)
        {
        	AttributeList resultList = new AttributeList();

	        if (attributeNames.length == 0)
    	        return resultList;
        
	        // Build the result attribute list
	        for(int i=0 ; i<attributeNames.length; i++)
	        {
	            try 
	            {        
	                Object oValue = getAttribute(attributeNames[i]);     
	                resultList.add(new Attribute(attributeNames[i], oValue));
	            } 
	            catch (Exception e) 
	            {
	            }
	        }
	        return resultList;
    	}
        return null;
	}
	
	 public AttributeList setAttributes(AttributeList attributes) 
	 {
        // Check attributes is not null to avoid NullPointerException later on
        //
        if (attributes != null) 
        {
	        AttributeList resultList = new AttributeList();
	
	        // If attributeNames is empty, nothing more to do
	        //
	        if (attributes.isEmpty())
	            return resultList;
	
	        // For each attribute, try to set it and add to the result list if
	        // successfull
	        //
	        for (Iterator i = attributes.iterator(); i.hasNext();)
	        {
	            Attribute attr = (Attribute) i.next();
	            try
	            {
	                setAttribute(attr);
	                String name = attr.getName();
	                Object value = getAttribute(name); 
	                resultList.add(new Attribute(name,value));
	            } 
	            catch(Exception e)
	            {
	                e.printStackTrace();
	            }
	        }
	        return resultList;
    	}
        return null;
	}
}