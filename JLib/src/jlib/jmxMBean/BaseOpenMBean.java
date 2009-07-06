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

import javax.management.Attribute;
import javax.management.AttributeNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.ReflectionException;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenMBeanAttributeInfo;
import javax.management.openmbean.OpenMBeanAttributeInfoSupport;
import javax.management.openmbean.OpenMBeanInfoSupport;
import javax.management.openmbean.TabularType;

public abstract class BaseOpenMBean extends BaseDynamicMBean	//implements DynamicMBean
{
	public BaseOpenMBean(String csName, String csDescription)
	{
		buildDynamicMBeanInfo();
		registerOpenInfos(csName, csDescription);
		JmxRegistration.registerMBean(csName, this);
	}
	
	private void registerOpenInfos(String csName, String csDescription)
	{
		OpenMBeanAttributeInfo[] arrAttributes = null;
		if(m_arrOpenMBeanAttributeInfosWrapper != null)
		{
			int nNbItems = m_arrOpenMBeanAttributeInfosWrapper.size();
			arrAttributes = new OpenMBeanAttributeInfo[nNbItems]; 
			for(int n=0; n<nNbItems; n++)
			{
				OpenMBeanAttributeInfoWrapper wrapper = m_arrOpenMBeanAttributeInfosWrapper.get(n);
				arrAttributes[n] = wrapper.getAttribute();
			}
		} 
		
		m_OpenMBeanInfo = new OpenMBeanInfoSupport(csName, csDescription, arrAttributes, null, null, null); 
	}
	
	public Object getAttribute(String csName) 
	{
		if (csName == null || m_arrOpenMBeanAttributeInfosWrapper == null) 
		{
			return null;
        }
		
		for(int n=0; n<m_arrOpenMBeanAttributeInfosWrapper.size(); n++)
		{
			OpenMBeanAttributeInfoWrapper attributeInfoWrapper = m_arrOpenMBeanAttributeInfosWrapper.get(n);
			OpenMBeanAttributeInfo attributeInfo = attributeInfoWrapper.getAttribute();
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
	        
      		for(int n=0; n<m_arrOpenMBeanAttributeInfosWrapper.size(); n++)
			{
				OpenMBeanAttributeInfoWrapper attributeInfoWrapper = m_arrOpenMBeanAttributeInfosWrapper.get(n);
				OpenMBeanAttributeInfo attributeInfo = attributeInfoWrapper.getAttribute();
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
    
	public Object invoke(String csOperationName,
                         Object params[],
                         String signature[])
	{
//        if (csOperationName != null) 
//        {
//        	for(int n=0; n<m_arrOpenMBeanAttributeInfosWrapper.size(); n++)
//			{
//				OpenMBeanAttributeInfoWrapper operationInfoWrapper = m_arrOpenMBeanAttributeInfosWrapper.get(n);
//				OpenMBeanAttributeInfo operationInfo = operationInfoWrapper.getOperation();
//				if(operationInfo.getName().equalsIgnoreCase(csOperationName))	// Found attribut
//				{
//					// Call method
//					Method method = operationInfoWrapper.getMethod();
//					if(method != null)
//					{
//						Object oReturn;
//						try
//						{
//							oReturn = method.invoke(this, params);
//							return oReturn;
//						} 
//						catch (IllegalArgumentException e)
//						{
//							e.printStackTrace();
//						} 
//						catch (IllegalAccessException e)
//						{
//							e.printStackTrace();
//						} 
//						catch (InvocationTargetException e)
//						{
//							e.printStackTrace();
//						}
//						return null;
//					}
//				}
//			}
//        }
        return null;
	}
	
	protected void addOpenAttribute(String csDescription, Class cls, String csMethodName, CompositeType compositeType)
	{
		Method methodGet = MethodFinder.getMethod(cls, "get"+csMethodName);
		boolean bCanGet = true;
		if(methodGet == null)
			bCanGet = false;
		Method methodSet = MethodFinder.getMethod(cls, "set"+csMethodName, CompositeData.class);
		boolean bCanSet = true;
		if(methodSet == null)
			bCanSet = false;

		OpenMBeanAttributeInfoSupport attrOpen = new OpenMBeanAttributeInfoSupport(csMethodName, csDescription, compositeType, bCanGet, bCanSet, false); 
		OpenMBeanAttributeInfoWrapper attr = new OpenMBeanAttributeInfoWrapper(csMethodName, csDescription, attrOpen, methodGet, methodSet);
		
		if(m_arrOpenMBeanAttributeInfosWrapper == null)
			m_arrOpenMBeanAttributeInfosWrapper = new ArrayList<OpenMBeanAttributeInfoWrapper>();
		m_arrOpenMBeanAttributeInfosWrapper.add(attr);
	}
	
	protected void addOpenAttribute(String csDescription, Class cls, String csMethodName, TabularType tabularType)
	{
		Method methodGet = MethodFinder.getMethod(cls, "get"+csMethodName);
		boolean bCanGet = true;
		if(methodGet == null)
			bCanGet = false;
		Method methodSet = MethodFinder.getMethod(cls, "set"+csMethodName, CompositeData.class);
		boolean bCanSet = true;
		if(methodSet == null)
			bCanSet = false;

		OpenMBeanAttributeInfoSupport attrOpen = new OpenMBeanAttributeInfoSupport(csMethodName, csDescription, tabularType, bCanGet, bCanSet, false); 
		OpenMBeanAttributeInfoWrapper attr = new OpenMBeanAttributeInfoWrapper(csMethodName, csDescription, attrOpen, methodGet, methodSet);
		
		if(m_arrOpenMBeanAttributeInfosWrapper == null)
			m_arrOpenMBeanAttributeInfosWrapper = new ArrayList<OpenMBeanAttributeInfoWrapper>();
		m_arrOpenMBeanAttributeInfosWrapper.add(attr);
	}

	public MBeanInfo getMBeanInfo()
    {
        return m_OpenMBeanInfo;
    }
	
	protected abstract void buildDynamicMBeanInfo();
	    
    private OpenMBeanInfoSupport m_OpenMBeanInfo = null;
    private ArrayList<OpenMBeanAttributeInfoWrapper> m_arrOpenMBeanAttributeInfosWrapper = null;
}
