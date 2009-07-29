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
package jlib.jmxMBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.ReflectionException;

public abstract class BaseCloseMBean extends BaseDynamicMBean
{
	private boolean m_bCreated = false;
	
	public BaseCloseMBean()
	{		
	}
		
	public BaseCloseMBean(String csName, String csDescription)
	{
		createMBean(csName, csDescription);
	}
	
	public void createMBean(String csName, String csDescription)
	{
		m_csMBeanName = csName;
		if(!m_bCreated)
		{
			buildDynamicMBeanInfo();
			registerInfos(csName, csDescription);
		}
		JmxRegistration.registerMBean(csName, this);
		m_bCreated = true;
	}
	
	public void unregisterMBean()
	{
		if(m_csMBeanName != null)
			JmxRegistration.unregisterMBean(m_csMBeanName);
		m_csMBeanName = null;
	}
	
	protected boolean isBeanCreated()
	{
		if(m_csMBeanName == null)
			return false;
		return true;
	}
	
	public Object getAttribute(String csName) 
	{
		if (csName == null || m_arrMBeanAttributeInfosWrapper == null) 
		{
			return null;
        }
		
		for(int n=0; n<m_arrMBeanAttributeInfosWrapper.size(); n++)
		{
			MBeanAttributeInfoWrapper attributeInfoWrapper = m_arrMBeanAttributeInfosWrapper.get(n);
			MBeanAttributeInfo attributeInfo = attributeInfoWrapper.getAttribute();
			if(attributeInfo.getName().equalsIgnoreCase(csName))	// Found attribut
			{
				// Call method
				Method method = attributeInfoWrapper.getMethodGetter();
				if(method != null)
				{
					try
					{
						Object oReturn = method.invoke(this, (Object[])null);
						return oReturn;
					} 
					catch (IllegalArgumentException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					catch (IllegalAccessException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					catch (InvocationTargetException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}
        return null;
	}
	
    public void setAttribute(Attribute attribute) 
        throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException 
	{
        if (attribute != null) 
        {	        
	        String csName = attribute.getName();
	        Object oValue = attribute.getValue();
	        
      		for(int n=0; n<m_arrMBeanAttributeInfosWrapper.size(); n++)
			{
				MBeanAttributeInfoWrapper attributeInfoWrapper = m_arrMBeanAttributeInfosWrapper.get(n);
				MBeanAttributeInfo attributeInfo = attributeInfoWrapper.getAttribute();
				if(attributeInfo.getName().equalsIgnoreCase(csName))	// Found attribut
				{
					// Call method
					Method method = attributeInfoWrapper.getMethodSetter();
					if(method != null)
					{
						Class[] arrClassArgs = method.getParameterTypes();
						if(arrClassArgs.length == 1)	// Check: only 1 arg
						{
							try
							{
								method.invoke(this, oValue);
								return;
							} 
							catch (IllegalArgumentException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							catch (IllegalAccessException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							catch (InvocationTargetException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
        }
    }
    
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
     
	 
	public Object invoke(String csOperationName,
                         Object params[],
                         String signature[])
	{
        if (csOperationName != null) 
        {
        	for(int n=0; n<m_arrMBeanOperationInfosWrapper.size(); n++)
			{
				MBeanOperationInfoWrapper operationInfoWrapper = m_arrMBeanOperationInfosWrapper.get(n);
				MBeanOperationInfo operationInfo = operationInfoWrapper.getOperation();
				if(operationInfo.getName().equalsIgnoreCase(csOperationName))	// Found attribut
				{
					// Call method
					Method method = operationInfoWrapper.getMethod();
					if(method != null)
					{
						Object oReturn;
						try
						{
							oReturn = method.invoke(this, params);
							return oReturn;
						} 
						catch (IllegalArgumentException e)
						{
							e.printStackTrace();
						} 
						catch (IllegalAccessException e)
						{
							e.printStackTrace();
						} 
						catch (InvocationTargetException e)
						{
							e.printStackTrace();
						}
						return null;
					}
				}
			}
        }
        return null;
	}
	    					
			
    /**
     * This method provides the exposed attributes and operations of the
     * Dynamic MBean. It provides this information using an MBeanInfo object.
     */
    public MBeanInfo getMBeanInfo()
    {
        return m_MBeanInfo;
    }
    
//	protected void addAttribute(String csName, String csType, String csDescription, boolean bIsReadable, boolean bIsWritable, boolean bIsIs)
//	{
//		MBeanAttributeInfoWrapper attr = new MBeanAttributeInfoWrapper(csName, csType, csDescription, bIsReadable, bIsWritable, bIsIs);
//		if(m_arrMBeanAttributeInfosWrapper == null)
//			m_arrMBeanAttributeInfosWrapper = new ArrayList<MBeanAttributeInfoWrapper>();
//		m_arrMBeanAttributeInfosWrapper.add(attr);
//	}
	
	protected void addAttribute(String csDescription, Class cls, String csMethodName, Class clsType)
	{
		Method methodGet = MethodFinder.getMethod(cls, "get"+csMethodName);
		Method methodSet = MethodFinder.getMethod(cls, "set"+csMethodName, clsType);
		MBeanAttributeInfoWrapper attr = new MBeanAttributeInfoWrapper(csMethodName, csDescription, methodGet, methodSet);
		if(m_arrMBeanAttributeInfosWrapper == null)
			m_arrMBeanAttributeInfosWrapper = new ArrayList<MBeanAttributeInfoWrapper>();
		m_arrMBeanAttributeInfosWrapper.add(attr);
	}
	
//	protected void addOperation(String csName, String csDescription, MBeanParameterInfo[] arrSignature, String csType, int nImpact)
//	{
//		MBeanOperationInfo operation = new MBeanOperationInfo(csName, csDescription, arrSignature, csType, nImpact);
//		if(m_arrMBeanOperationInfos == null)
//			m_arrMBeanOperationInfos = new ArrayList<MBeanOperationInfo>();
//		m_arrMBeanOperationInfos.add(operation);
//	}
	
	private void addOperation(String csDescription, Method method)
	{
		MBeanOperationInfoWrapper operation = new MBeanOperationInfoWrapper(csDescription, method);
		if(m_arrMBeanOperationInfosWrapper == null)
			m_arrMBeanOperationInfosWrapper = new ArrayList<MBeanOperationInfoWrapper>();
		m_arrMBeanOperationInfosWrapper.add(operation);
	}
	
	protected void addOperation(String csDescription, Class cls, String csMethodName)
	{
		Method method = MethodFinder.getMethod(cls, csMethodName);
		if(method != null)
		{
			addOperation(csDescription, method);
		}
	}
	
	protected void addOperation(String csDescription, Class cls, String csMethodName, Class clsArg0)
	{
		Method method = MethodFinder.getMethod(cls, csMethodName, clsArg0);
		if(method != null)
		{
			addOperation(csDescription, method);
		}
	}

	protected void addOperation(String csDescription, Class cls, String csMethodName, Class clsArg0, Class clsArg1)
	{
		Method method = MethodFinder.getMethod(cls, csMethodName, clsArg0, clsArg1);
		if(method != null)
		{
			addOperation(csDescription, method);
		}
	}
	
	protected void addOperation(String csDescription, Class cls, String csMethodName, Class clsArg0, Class clsArg1, Class clsArg2)
	{
		Method method = MethodFinder.getMethod(cls, csMethodName, clsArg0, clsArg1, clsArg2);
		if(method != null)
		{
			addOperation(csDescription, method);
		}
	}
	
	protected void addOperation(String csDescription, Class cls, String csMethodName, Class clsArg0, Class clsArg1, Class clsArg2, Class clsArg3)
	{
		Method method = MethodFinder.getMethod(cls, csMethodName, clsArg0, clsArg1, clsArg2, clsArg3);
		if(method != null)
		{
			addOperation(csDescription, method);
		}
	}

	private void registerInfos(String csName, String csDescription)
	{
		MBeanAttributeInfo[] arrAttributes = null;
		if(m_arrMBeanAttributeInfosWrapper != null)
		{
			int nNbItems = m_arrMBeanAttributeInfosWrapper.size();
			arrAttributes = new MBeanAttributeInfo[nNbItems]; 
			for(int n=0; n<nNbItems; n++)
			{
				MBeanAttributeInfoWrapper wrapper = m_arrMBeanAttributeInfosWrapper.get(n);
				arrAttributes[n] = wrapper.getAttribute();
			}
		} 
		
		MBeanOperationInfo[] arrOperations = null;
		if(m_arrMBeanOperationInfosWrapper != null)
		{
			int nNbItems = m_arrMBeanOperationInfosWrapper.size();
			arrOperations = new MBeanOperationInfo[nNbItems]; 
			for(int n=0; n<nNbItems; n++)
			{
				MBeanOperationInfoWrapper wrapper = m_arrMBeanOperationInfosWrapper.get(n);
				arrOperations[n] = wrapper.getOperation();
			}
		} 
		
        m_MBeanInfo = new MBeanInfo(csName,
                                   csDescription,
                                   arrAttributes,
                                   null,	//dConstructors,
                                   arrOperations,
                                   null);	//dNotifications);
	}
	
	protected void removeAllOperations()
	{
		m_arrMBeanOperationInfosWrapper.clear();
	}

	protected void removeAllAttributes()
	{
		m_arrMBeanAttributeInfosWrapper.clear();
	}

	protected abstract void buildDynamicMBeanInfo() ;
	
	protected String getMBeanName()
	{
		return m_csMBeanName;
	}
	
	private ArrayList<MBeanAttributeInfoWrapper> m_arrMBeanAttributeInfosWrapper = null;
	private ArrayList<MBeanOperationInfoWrapper> m_arrMBeanOperationInfosWrapper = null;
	private MBeanInfo m_MBeanInfo = null;
	private String m_csMBeanName = null;
}
