/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package nacaLib.classLoad;

import java.util.ArrayList;

import nacaLib.basePrgEnv.BaseResourceManager;

import jlib.classLoader.ClassDynLoader;
import jlib.classLoader.CoupleCodeLoader;
import jlib.classLoader.JarEntries;

public class CustomClassDynLoader extends ClassDynLoader
{
	public CustomClassDynLoader()
	{
		super();
	}
	
	public CustomClassDynLoader(ArrayList<String> arrPaths, JarEntries jarEntries, boolean bCanLoadClass, boolean bCanLoadJar)
	{
		super(arrPaths, jarEntries, bCanLoadClass, bCanLoadJar);
	}
	
//    private synchronized Class loadClassInternal(String name) throws ClassNotFoundException
//    {
//    	return loadClass(name);
//    }

	
	public Class loadClass(String csClassName)
    {
		//if(csClassName.equals("FUF1A00"))
		//{
		//	int gg = 0;
		//}
		
		char c = csClassName.charAt(0);
		if(c == 'j' || c == 'n' || c == 'i' || c == 'p' || c == 's')
		{
			if(csClassName.startsWith("java"))
				return tryLoadWithPrimordialClassLoader(csClassName);
			if(csClassName.startsWith("nacaLib"))	// All nacalib classes are directly loaded by promordial class loader
				return tryLoadWithPrimordialClassLoader(csClassName);
			if(csClassName.startsWith("idea"))	// All nacalib classes are directly loaded by promordial class loader
				return tryLoadWithPrimordialClassLoader(csClassName);
			if(csClassName.startsWith("jlib"))	// All nacalib classes are directly loaded by promordial class loader
				return tryLoadWithPrimordialClassLoader(csClassName);
			if(csClassName.startsWith("pub2000Utils"))	// All nacalib classes are directly loaded by promordial class loader
				return tryLoadWithPrimordialClassLoader(csClassName);
			if(csClassName.startsWith("sun"))
				return tryLoadWithPrimordialClassLoader(csClassName);
		}
		if(!m_bCanLoadJar && !m_bCanLoadClass)
			return tryLoadWithPrimordialClassLoader(csClassName);
		
		boolean bProgram = false;
		boolean bCopyOrStdClass = false;
		boolean bParagraph = false;
		boolean bCallOrStdClass = false;
		
    	Class classCode = null;
    	
    	if(m_csCurrentClassName != null)	// paragraph or copy
    	{
    		if(csClassName.equals(m_csCurrentClassName))	// Program
    			bProgram = true;
    		else if(csClassName.startsWith(m_csCurrentClassName) && csClassName.indexOf('$') == m_csCurrentClassName.length())	// Paragraph
    			bParagraph = true;
    		else
    		{
    			bCopyOrStdClass = true;	// Copy
    		}
    	}
    	else
    	{
    		if(csClassName.equals("Pub2000Routines"))	// Pub2000Routines must be moved into a package of nacaRT, like pub2000Utils 
    			bCopyOrStdClass = true;	// Copy
    		else
    			bCallOrStdClass = true;	// Call
    	}
		
    	boolean bIntf = csClassName.endsWith("Intf");
    	
    	if(bCallOrStdClass || bProgram || bIntf)
    	{
	    	CoupleCodeLoader couple = ms_hashByName.get(csClassName);
			if(couple != null)
			{
				classCode = couple.getClassCode();
				return classCode;
			}
    	}
    	
    	if(bCallOrStdClass || (bCopyOrStdClass && BaseResourceManager.isLoadCopyByPrimordialLoader()))
    	{
    		classCode = tryLoadWithPrimordialClassLoader(csClassName);
    		if(classCode != null)
    			return classCode; 
    	}
        
       	if(bCallOrStdClass)	// If we want to share a copy among all programs, do a if(bCall || bCopyOrStdClass)
    	{
    		CustomClassDynLoader newCustomClassDynLoader = new CustomClassDynLoader(m_arrPaths, m_jarEntries, m_bCanLoadClass, m_bCanLoadJar);
    		Class cls = newCustomClassDynLoader.doLoadClass(csClassName);
    		return cls;
    	}
  
        // Try to load it from our paths 
        byte  arrbyteClassData[] = getClassFileBytes(csClassName);
        if (arrbyteClassData == null)
        {
        	return null;	// Class not found
        }

        // Define it (parse the class file) 
        classCode = defineClass(csClassName, arrbyteClassData, 0, arrbyteClassData.length);
        if (classCode == null)
        {
        	throw new ClassFormatError();
        }
        
        resolveClass(classCode);
		
		if(classCode != null)
		{
			if(bCallOrStdClass || bProgram || bIntf)
			{
				CoupleCodeLoader couple = new CoupleCodeLoader(classCode, this);
				register(csClassName, couple);
			}
		} 

		return classCode;
    }
}
