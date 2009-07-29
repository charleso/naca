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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

//TODO replace this class with a JLIB implementation !!!!
public class StreamUtil {
	
	//private static Logger _logger = Logger.getLogger(StreamUtil.class.getName());
	
  /**
   * Creates a new StreamUtil object.
   */
  public StreamUtil() {
  }
  
 //---------------------------------------------------------- 

  	/**
  	 * Check if a folder path exists in the file system
  	 * If not, create it
  	 * @param String folderPath 
  	 */
	public static void mkDir(final String folderPath) {
		final File file = new File(folderPath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

// ----------------------------------------------------------------
  
  
  /**
   * Get a BufferedInputStream from an InputStream
   *
   * @param in InputStream source
   *
   * @return BufferedInputStream
   */
  public static BufferedInputStream getBufferedInputStream(InputStream in) {
    BufferedInputStream bin = null;

    if (in instanceof BufferedInputStream) {
      bin = (BufferedInputStream) in;
    } else {
      bin = new BufferedInputStream(in);
    }

    return bin;
  }
  
//------------------------------------------------------------

  /**
   * @param bin
   * @return
   * @throws IOException
   */
  public static String readInputStream(BufferedInputStream bin) throws IOException {
  	if (bin == null) {
  		return null;
  	}
  	byte data[] = null;
  	int s = bin.read();
  	if (s == -1) {
  		return null; //Connection lost
  	}
  	int alength = bin.available();
  	if (alength > 0) {
  		data = new byte[alength + 1];
  
  		bin.read(data, 1, alength);
  	} else {
  		data = new byte[1];
  	}
  	data[0] = (byte) s;
  	return new String(data);
  }
  
  //----------------------------------------------------------
  
  /**
   * Get a BufferedOutputStream from an OutputStream
   *
   * @param out OutputStream source
   *
   * @return BufferedOutputStream
   */
  public static BufferedOutputStream getBufferedOutputStream(OutputStream out) {
    BufferedOutputStream bout = null;

    if (out instanceof BufferedOutputStream) {
      bout = (BufferedOutputStream) out;
    } else {
      bout = new BufferedOutputStream(out);
    }

    return bout;
  }
  
//----------------------------------------------------------

  /**
   * Convert an InputStream in a String
   *
   * @param inputStream InputStream to convert
   *
   * @return String with the content of InputStream
   *
   * @throws UnsupportedEncodingException
   * @throws IOException
   */
  public static String getStringFromInputStream(InputStream inputStream) throws UnsupportedEncodingException, IOException {
    
	  InputStreamReader inputTxtStream = new InputStreamReader(inputStream, "ISO-8859-1");

    String StrTemp = "";

    if (inputTxtStream != null) {
      final int DATA_BLOCK_SIZE = 2048;

      // Create the byte array to hold the data
      char[] bytes = new char[DATA_BLOCK_SIZE];

      // Read in the bytes
      int numRead = 0;

      while ((numRead = inputTxtStream.read(bytes, 0, bytes.length)) >= 0) {
        StrTemp += new String(bytes, 0, numRead);
      }
    }

    return StrTemp;
  }

//----------------------------------------------------------

  /**
   * Convert an InputStream in a Blob
   *
   * @param inputStream InputStream to convert
   *
   * @return Blob with the content of InputStream
   *
   */
  public static InputStream getBlobFromByteArray(byte[] byteArray) {
      
	  ByteArrayInputStream stream = new ByteArrayInputStream(byteArray);
		  
	  //blob = new Blob(byteArray);
	  
	  return stream;
  }
  
//----------------------------------------------------------
  
  /**
   * Close an InputStream
   *
   * @param is InputStream to close
   */
  public static void close(InputStream is) {
    if (is != null) {
      try {
        is.close();
      } catch (Exception ex) {
      }
    }
  }

//----------------------------------------------------------
  
  /**
   * Close an OutputStream
   *
   * @param os OutputStream to close
   */
  public static void close(OutputStream os) {
    if (os != null) {
      try {
        os.close();
      } catch (Exception ex) {
      }
    }
  }

//----------------------------------------------------------

  /**
   * Convert a file to a ByteArrayInputStream
   *
   * @param String filePath
   *
   * @return ByteArrayInputStream
   *
   * @throws IOException
   */
  public static ByteArrayInputStream getByteArrayInputStreamFromFile(String filePath) throws IOException {

    return new ByteArrayInputStream(StreamUtil.getByteArrayFromFile(filePath));
  }  
  
//----------------------------------------------------------

  /**
   * Convert a byte[] to a ByteArrayInputStream
   *
   * @param byte[] byteArray
   *
   * @return ByteArrayInputStream
   */
  public static InputStream getByteArrayInputStreamFromByteArray(byte[] byteArray){

    return new ByteArrayInputStream(byteArray);
  }  
  
//----------------------------------------------------------

  /**
   * Convert a String to a ByteArrayInputStream using specified encoding ("UTF-8", "UTF-16", "latin1", ...)
   *
   * @param String inData, String charset
   *
   * @return ByteArrayInputStream
   *
   * @throws UnsupportedEncodingException
   */
  public static ByteArrayInputStream getByteArrayInputStreamFromString(String inData, String charset) throws UnsupportedEncodingException {
    
	byte[] byteArray = inData.getBytes(charset);

    return new ByteArrayInputStream(byteArray);
  }

//----------------------------------------------------------

  /**
   * Convert a String to a ByteArray using specified encoding ("UTF-8", "UTF-16", "latin1", ...)
   *
   * @param String inData, String charset
   *
   * @return byte[]
   *
   * @throws UnsupportedEncodingException
   */
  public static byte[] getByteArrayFromString(String inData, String charset) throws UnsupportedEncodingException {
    
    byte[] byteArray = inData.getBytes(charset);

    return byteArray;
  }
  
//---------------------------------------------------------
	
  /**
   * Convert a byte[] to a String using specified encoding ("UTF-8", "UTF-16", "latin1", ...)
   * 
   * @param byte[] byteArray, String charset
   * 
   * @return String
   * 
   * @throws UnsupportedEncodingException
   */
  public static String getStringFromByteArray(byte[] byteArray, String charset) throws UnsupportedEncodingException {
  	  	
	 String str = new String(byteArray, charset);
	  
  	 return str; 
  }


//	---------------------------------------------------------

	
  /**
   * Convert a file to a String using specified encoding ("UTF-8", "UTF-16", "latin1", ...)
   * 
   * @param String filePath, String charset
   * 
   * @return String
   * 
   * @throws UnsupportedEncodingException, IOException
   */
public static String getStringFromFile(String filePath, String charset) throws Exception {

	File file = new File(filePath);
	String str = null;
	int numRead = 0;
	final int DATA_BLOCK_SIZE = 2048;
	
	InputStreamReader isr = new InputStreamReader(new FileInputStream(file), charset);

    if (isr != null) {

      // Create the byte array to hold the data
      char[] charArray = new char[DATA_BLOCK_SIZE];

      while ((numRead = isr.read(charArray, 0, DATA_BLOCK_SIZE)) >= 0) {
        str += new String(charArray, 0, numRead);
      }
      isr.close();
    }

    return str;
}	
  
//	---------------------------------------------------------
	
/**
 * Convert a file to a byte[]
 * 
 * @param String filePath
 * 
 * @return byte[]
 * 
 * @throws IOException
 */
  public static byte[] getByteArrayFromFile(String filePath) throws IOException {
	  
  	File file = new File(filePath);
  	byte[] byteArray = null;
  	
  	BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
  	
  	if (bis != null) {
  	
	  	byteArray = new byte[(int)file.length()];
    	bis.read(byteArray);
	  	bis.close();
  	}
  	
  	return byteArray;
  }	
  
  
//	---------------------------------------------------------
	
  /**
   * Convert an InputStream to a byte[]
   * 
   * @param InputStream is
   * 
   * @return byte[]
   * 
   * @throws IOException
   */
    public static byte[] getByteArrayFromInputStream(InputStream is) throws IOException { 
    	byte[] byteArray = null;
    	
    	BufferedInputStream bis = new BufferedInputStream(is);
    	
    	if (bis != null) {
    	
    		//!!! To be implemented
  	  		
  	  		bis.close();
    	}
    	
    	return byteArray;
    }	
  
//--------------------------------------------------------------------------	
	
} // end of class