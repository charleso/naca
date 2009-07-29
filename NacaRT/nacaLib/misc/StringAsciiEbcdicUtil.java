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
package nacaLib.misc;

import jlib.misc.AsciiEbcdicConverter;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.tempCache.CStr;
import nacaLib.varEx.ComparisonMode;

public class StringAsciiEbcdicUtil
{
	public static int compare(ComparisonMode mode, String cs1, String cs2)
	{
		if(mode == ComparisonMode.UnicodeOrEbcdic)
		{
			if(BaseResourceManager.getComparisonInEbcdic())
				mode = ComparisonMode.Ebcdic;
			else
				mode = ComparisonMode.Unicode;
		}
		int n1 = cs1.length();
		int n2 = cs2.length();
        for(int i1=0, i2=0; i1<n1 && i2<n2; i1++, i2++)
        {
            char c1 = cs1.charAt(i1);
            char c2 = cs2.charAt(i2);
            if (c1 != c2)
            {
            	if(mode == ComparisonMode.Ebcdic)
            	{
            		c1 = AsciiEbcdicConverter.getEbcdicChar(c1);
                	c2 = AsciiEbcdicConverter.getEbcdicChar(c2);
            	}
                return c1 - c2;
            }
        }
        if(n1 != n2)
        {
            String csLargest;
            int nMin;
            int nMax;
            int nRet;
            char cSpace = ' ';
        	if(mode == ComparisonMode.Ebcdic)
        		cSpace = AsciiEbcdicConverter.getEbcdicChar(cSpace);

        	if(n1 > n2)
            {
            	csLargest = cs1;
            	nMin = n2;
            	nMax = n1;
            	nRet = 1;	            	
            }
            else	// n1 < n2
            {
            	csLargest = cs2;
            	nMin = n1;
            	nMax = n2;
            	nRet = -1;
            }
            for(int n=nMin; n<nMax; n++)
            {
            	char c = csLargest.charAt(n);
            	if(mode == ComparisonMode.Ebcdic)
            		c = AsciiEbcdicConverter.getEbcdicChar(c);
            	if(c > cSpace)
            		return nRet;
            	if(c < cSpace)
            		return -nRet;
            }
        }
		return 0;
    }

//	 PJD added for Batch optimization
	public static int compare(ComparisonMode mode, CStr cs1, String cs2)
	{
		if(mode == ComparisonMode.UnicodeOrEbcdic)
		{
			if(BaseResourceManager.getComparisonInEbcdic())
				mode = ComparisonMode.Ebcdic;
			else
				mode = ComparisonMode.Unicode;
		}
		int n1 = cs1.length();
		int n2 = cs2.length();
        for(int i1=0, i2=0; i1<n1 && i2<n2; i1++, i2++)
        {
            char c1 = cs1.charAt(i1);
            char c2 = cs2.charAt(i2);
            if (c1 != c2)
            {
            	if(mode == ComparisonMode.Ebcdic)
            	{
            		c1 = AsciiEbcdicConverter.getEbcdicChar(c1);
                	c2 = AsciiEbcdicConverter.getEbcdicChar(c2);
            	}
                return c1 - c2;
            }
        }
        if(n1 != n2)
        {
            int nMin;
            int nMax;
            int nRet;
            char cSpace = ' ';
        	if(mode == ComparisonMode.Ebcdic)
        		cSpace = AsciiEbcdicConverter.getEbcdicChar(cSpace);

        	if(n1 > n2)
            {
            	nMin = n2;
            	nMax = n1;
            	nRet = 1;
                for(int n=nMin; n<nMax; n++)
                {
                	char c = cs1.charAt(n);
                	if(mode == ComparisonMode.Ebcdic)
                		c = AsciiEbcdicConverter.getEbcdicChar(c);
                	if(c > cSpace)
                		return nRet;
                	if(c < cSpace)
                		return -nRet;
                }
            }
            else	// n1 < n2
            {
            	nMin = n1;
            	nMax = n2;
            	nRet = -1;
                for(int n=nMin; n<nMax; n++)
                {
                	char c = cs2.charAt(n);
                	if(mode == ComparisonMode.Ebcdic)
                		c = AsciiEbcdicConverter.getEbcdicChar(c);
                	if(c > cSpace)
                		return nRet;
                	if(c < cSpace)
                		return -nRet;
                }
            }
        }
		return 0;
    }
//	 PJD end added for Batch optimization

	public static int compare(ComparisonMode mode, CStr cs1, CStr cs2)
	{		
		if(mode == ComparisonMode.UnicodeOrEbcdic)
		{
			if(BaseResourceManager.getComparisonInEbcdic())
				mode = ComparisonMode.Ebcdic;
			else
				mode = ComparisonMode.Unicode;
		}
		int n1 = cs1.length();
		int n2 = cs2.length();
        for(int i1=0, i2=0; i1<n1 && i2<n2; i1++, i2++)
        {
            char c1 = cs1.charAt(i1);
            char c2 = cs2.charAt(i2);
            if (c1 != c2)
            {
            	if(mode == ComparisonMode.Ebcdic)
            	{
            		c1 = AsciiEbcdicConverter.getEbcdicChar(c1);
                	c2 = AsciiEbcdicConverter.getEbcdicChar(c2);
            	}
                return c1 - c2;
            }
        }
        if(n1 != n2)
        {
            CStr csLargest;
            int nMin;
            int nMax;
            int nRet;
            char cSpace = ' ';
        	if(mode == ComparisonMode.Ebcdic)
        		cSpace = AsciiEbcdicConverter.getEbcdicChar(cSpace);

        	if(n1 > n2)
            {
            	csLargest = cs1;
            	nMin = n2;
            	nMax = n1;
            	nRet = 1;	            	
            }
            else	// n1 < n2
            {
            	csLargest = cs2;
            	nMin = n1;
            	nMax = n2;
            	nRet = -1;
            }
            for(int n=nMin; n<nMax; n++)
            {
            	char c = csLargest.charAt(n);
            	if(mode == ComparisonMode.Ebcdic)
            		c = AsciiEbcdicConverter.getEbcdicChar(c);
            	if(c > cSpace)
            		return nRet;
            	if(c < cSpace)
            		return -nRet;
            }
        }
		return 0;
	}
		
		
//		if(mode == ComparisonMode.UnicodeOrEbcdic)
//		{
//			if(BaseResourceManager.getComparisonInEbcdic())
//				mode = ComparisonMode.Ebcdic;
//			else
//				mode = ComparisonMode.Unicode;
//		}
//		
//		if(mode == ComparisonMode.Unicode)
//		{
//			int n1 = cs1.length();
//			int n2 = cs2.length();
//            for(int i1=0, i2=0; i1<n1 && i2<n2; i1++, i2++)
//            {
//                char c1 = cs1.charAt(i1);
//                char c2 = cs2.charAt(i2);
//                if (c1 != c2)
//                {
//                    return c1 - c2;
//                }
//            }
//            return n1 - n2;
//		}
//		else if(mode == ComparisonMode.Ebcdic)
//		{
//			int n1 = cs1.length();
//			int n2 = cs2.length();
//            for(int i1=0, i2=0; i1<n1 && i2<n2; i1++, i2++)
//            {
//                char c1 = cs1.charAt(i1);
//                char c2 = cs2.charAt(i2);
//                if (c1 != c2)
//                {
//                    char cEbcdic1 = AsciiEbcdicConverter.getEbcdicChar(c1);
//                    char cEbcdic2 = AsciiEbcdicConverter.getEbcdicChar(c2);
//                    return cEbcdic1 - cEbcdic2;
//                }
//            }
//            return n1 - n2;
//        }
//		return 0;
//    }


}
