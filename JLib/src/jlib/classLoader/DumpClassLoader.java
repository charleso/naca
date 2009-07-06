/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.classLoader;

//import com.vladium.utils.ClassLoaderRegistry;
//import com.vladium.utils.ClassScope;

// ----------------------------------------------------------------------------
/**
 * A simple test driver for (@link com.vladium.utils.ClassScope} API. See the
 * article for more details.
 * 
 * @see com.vladium.utils.DumpClassScope
 * 
 * @author (C) <a href="http://www.javaworld.com/columns/jw-qna-index.shtml">Vlad Roubtsov</a>, 2003
 */
public class DumpClassLoader
{
    public DumpClassLoader()
    {
    }
    
    public void dump(Object obj)
    {
    	dump(obj.getClass().getClassLoader());
    }
    
    public void dump(ClassLoader classLoader)
    {
        // option (1): manually enumerating class loaders
//        final ClassLoader appLoader = ClassLoader.getSystemClassLoader ();
//        final ClassLoader extLoader = appLoader.getParent ();        
//        final ClassLoader [] loaders = new ClassLoader [] {appLoader, extLoader};

        // option (2): enumerating class loaders for all classes on the call stack
  //      final ClassLoader [] loaders = ClassScope.getCallerClassLoaderTree ();
        
        // option (3): enumerating class loaders using the registry
   //     final ClassLoader [] loaders = ClassLoaderRegistry.getClassLoaders ();
        
        final Class [] classes = DumpClassScope.getLoadedClasses(classLoader);
        for (int c = 0; c < classes.length; ++ c)
        {
            final Class cls = classes [c];

     //       System.out.print (cls.getName () + ":");
      //      System.out.println (" loaded by " + cls.getClassLoader().getClass().getName() + " [" + cls.getClassLoader().toString() + "]");
        //    System.out.println (" from [" + DumpClassScope.getClassLocation (cls) + "]");
        }
    }

}
