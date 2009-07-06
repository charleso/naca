/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.blowfish;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Blowfish
{
	private String m_csKey = null;
	
    public Blowfish(String csKey, boolean bMixWithIpAdress)
	{
		try
		{
			if(bMixWithIpAdress)
			{
				InetAddress adr = InetAddress.getLocalHost();
				byte ipAdress[] = adr.getAddress();
				int nVal = 0;
				for(int n=0; n<ipAdress.length; n++)
				{
					nVal *= 256;
					nVal += ipAdress[n];
				}
				csKey += nVal;
			}
		}
		catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m_csKey = csKey;
	}
	
    public String encrypt(String csClearValue)
	{
        byte[] testkey = m_csKey.getBytes();

        BlowfishECB bfecb = new BlowfishECB(testkey);
        
        // align to the next 8 byte border
        byte[] messbuf = null;
        byte[] tempbuf = csClearValue.getBytes();
        int nMessSize = csClearValue.length();
        int nRest = nMessSize & 7;
        if (nRest != 0) 
        {
        	messbuf = new byte[(nMessSize & (~7)) + 8];
        	System.arraycopy(tempbuf, 0, messbuf, 0, nMessSize);

        	for (int nI = nMessSize; nI < messbuf.length ; nI++) 
        	{
        		messbuf[nI] = 0x20;
        	}
        	//System.out.println("message with " + nMessSize + " bytes aligned to " + messbuf.length + " bytes");
        }
        else 
        {
        	messbuf = new byte[nMessSize];
        	System.arraycopy(tempbuf, 0, messbuf, 0, nMessSize);
        }

        bfecb.encrypt(messbuf);
        String csCryptedValue = BinConverter.bytesToBinHex(messbuf);
        return csCryptedValue;
	}
	
    public String decrypt(String csCryptedValue)
	{
		byte[] testkey = m_csKey.getBytes();
        BlowfishECB bfecb = new BlowfishECB(testkey);

        int n = csCryptedValue.length()/2;
        byte[] tByteCrypedValue = new byte[n];
        BinConverter.binHexToBytes(csCryptedValue, tByteCrypedValue, 0, 0, n);  
        
        
        bfecb.decrypt(tByteCrypedValue);
        String csClearValue = new String(tByteCrypedValue).trim();
        return csClearValue;
	}
}
