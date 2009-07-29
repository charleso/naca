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
/**
 * 
 */
package nacaLib.misc;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class IntelUnsignBinaryBufferStorage
{
//	byte b[] = new byte[4];
//	IntelBinaryBufferStorage.writeShort(b, 65535, 0);
//	int n = IntelBinaryBufferStorage.readShort(b, 0);
//
//	IntelBinaryBufferStorage.writeInt(b, -1, 0);
//	long l = IntelBinaryBufferStorage.readInt(b, 0);

    public final static int readShort(byte buf[], int i)
    {
    	int n = buf[i];
    	if(n < 0)
    		n += 256;
    	int m = buf[i+1];
    	if(m < 0)
    		m += 256;
    	int v = n + (m << 8);
    	return v;
    }

    public final static void writeShort(byte buf[], int sValue, int nPos)
    {
        buf[nPos] = (byte)(sValue >>> 0 & 0xff);
        buf[nPos+1] = (byte)(sValue >>> 8 & 0xff);
    }

    public final static long readInt(byte buf[], int i)
    {
    	long j = buf[i] >= 0 ? ((int) (buf[i])) : buf[i] + 256;
        i++;
        long k = buf[i] >= 0 ? ((int) (buf[i])) : buf[i] + 256;
        i++;
        long l = buf[i] >= 0 ? ((int) (buf[i])) : buf[i] + 256;
        i++;
        long i1 = buf[i] >= 0 ? ((int) (buf[i])) : buf[i] + 256;
        return (i1 << 24) + (l << 16) + (k << 8) + (j << 0);
    }

    public final static void writeInt(byte buf[], long nValue, int nPos)
    {
        buf[nPos] = (byte)(nValue >>> 0 & 0xff);
        buf[nPos+1] = (byte)(nValue >>> 8 & 0xff);
        buf[nPos+2] = (byte)(nValue >>> 16 & 0xff);
        buf[nPos+3] = (byte)(nValue >>> 24 & 0xff);
    }

    // The values are signed, as we don't have a larger type than long, to avoid sign bit 
    public final static long readLong(byte buf[], int i)
    {
    	return IntelSignBinaryBufferStorage.readLong(buf, i);
    }

    public final static void writeLong(byte buf[], long l, int i)
    {
    	IntelSignBinaryBufferStorage.writeLong(buf, l, i);
    }

}
