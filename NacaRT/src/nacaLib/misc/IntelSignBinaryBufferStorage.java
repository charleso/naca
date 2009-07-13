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

public class IntelSignBinaryBufferStorage
{
	// Big endian binary storage
    public final static short readShort(byte buf[], int i)
    {
        int j = 0;
        int k = 0;
        short word0 = 0;
        j = buf[i] >= 0 ? ((int) (buf[i])) : buf[i] + 256;
        k = buf[i + 1] >= 0 ? ((int) (buf[i + 1])) : buf[i + 1] + 256;
        if(j < 0)
        {
            j <<= 8;
            j >>>= 8;
        }
        word0 |= j;
        word0 |= k << 8;
        return word0;
    }

    public final static void writeShort(byte buf[], short word0, int i)
    {
        buf[i] = (byte)(word0 >>> 0 & 0xff);
        buf[i + 1] = (byte)(word0 >>> 8 & 0xff);
    }

    public final static int readInt(byte buf[], int i)
    {
        int j = buf[i] >= 0 ? ((int) (buf[i])) : buf[i] + 256;
        i++;
        int k = buf[i] >= 0 ? ((int) (buf[i])) : buf[i] + 256;
        i++;
        int l = buf[i] >= 0 ? ((int) (buf[i])) : buf[i] + 256;
        i++;
        int i1 = buf[i] >= 0 ? ((int) (buf[i])) : buf[i] + 256;
        if(j < 0)
        {
            j <<= 24;
            j >>>= 24;
        }
        if(k < 0)
        {
            k <<= 24;
            k >>>= 24;
        }
        if(l < 0)
        {
            l <<= 24;
            l >>>= 24;
        }
        if(i1 < 0)
        {
            i1 <<= 24;
            i1 >>>= 24;
        }
        return (i1 << 24) + (l << 16) + (k << 8) + (j << 0);
    }

    public final static void writeInt(byte buf[], int i, int j)
    {
        buf[j] = (byte)(i >>> 0 & 0xff);
        buf[j + 1] = (byte)(i >>> 8 & 0xff);
        buf[j + 2] = (byte)(i >>> 16 & 0xff);
        buf[j + 3] = (byte)(i >>> 24 & 0xff);
    }

    public final static long readLong(byte buf[], int i)
    {
        long l = buf[i] >= 0 ? buf[i] : buf[i] + 256;
        i++;
        long l1 = buf[i] >= 0 ? buf[i] : buf[i] + 256;
        i++;
        long l2 = buf[i] >= 0 ? buf[i] : buf[i] + 256;
        i++;
        long l3 = buf[i] >= 0 ? buf[i] : buf[i] + 256;
        i++;
        long l4 = buf[i] >= 0 ? buf[i] : buf[i] + 256;
        i++;
        long l5 = buf[i] >= 0 ? buf[i] : buf[i] + 256;
        i++;
        long l6 = buf[i] >= 0 ? buf[i] : buf[i] + 256;
        i++;
        long l7 = buf[i] >= 0 ? buf[i] : buf[i] + 256;
        return (l7 << 56) + (l6 << 48) + (l5 << 40) + (l4 << 32) + (l3 << 24) + (l2 << 16) + (l1 << 8) + (l << 0);
    }

    public final static void writeLong(byte buf[], long l, int i)
    {
        buf[i] = (byte)(int)(l >>> 0 & 255L);
        buf[i + 1] = (byte)(int)(l >>> 8 & 255L);
        buf[i + 2] = (byte)(int)(l >>> 16 & 255L);
        buf[i + 3] = (byte)(int)(l >>> 24 & 255L);
        buf[i + 4] = (byte)(int)(l >>> 32 & 255L);
        buf[i + 5] = (byte)(int)(l >>> 40 & 255L);
        buf[i + 6] = (byte)(int)(l >>> 48 & 255L);
        buf[i + 7] = (byte)(int)(l >>> 56 & 255L);
    }
}
