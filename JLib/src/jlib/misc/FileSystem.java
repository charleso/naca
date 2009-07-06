/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.misc;

/*
 * Created on 4 mars 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.CharBuffer;
import java.util.Collections;
import java.util.Vector;


/**
 * @author U930DI
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FileSystem
{	
	public FileSystem()
	{
	}
	
	public static String getCurrentWorkingDir()
	{
		String cs = System.getProperty("user.dir");
		return cs;
	}
	
	public static String normalizePath(String csPath)
	{
		csPath = csPath.replace('\\', '/') ;
		if(!csPath.endsWith("/"))
			csPath = csPath + "/";
		return csPath;
	}
	
	public static String normalizeFileNamePath(String csPath)
	{
		csPath = csPath.replace('\\', '/') ;
		return csPath;
	}
	
	public static String buildFileName(String csFilePath, String csFileName, String csFileExt)
	{
		String cs = normalizePath(csFilePath) + csFileName;
		if(csFileExt != null && csFileExt.length() > 0)
			cs += "." + csFileExt;
		return cs; 
	}
	
	public static String createFullPath(String csDir, String csSubDir, String csFileName)
	{
		csDir = normalizePath(csDir) + csSubDir;
		csDir = FileSystem.normalizePath(csDir);
		createPath(csDir);
		csFileName = appendFilePath(csDir, csFileName);
		return csFileName; 
	}
	
	public static String appendFilePath(String csPath, String csFileName)
	{
		String cs = normalizePath(csPath) + csFileName;
		return cs;
	}
	
	public static String getNameWithoutExtension(String csFilePath)
	{
		String csFileName = csFilePath.replace('\\', '/') ; 
		int nSep = csFileName.lastIndexOf('/') ;
		if(nSep != -1)
			csFileName = csFileName.substring(nSep+1) ;
		nSep = csFileName.lastIndexOf('.');
		if(nSep != -1)
			return csFileName.substring(0, nSep);
		return csFileName;
	}
	

	/*
	 * StringRef rcsPath = new StringRef();
	StringRef rcsExt = new StringRef();
	String csFileName = split("C:/toto\\tutu.xml", csPath, csExt);
	// csFileName: Filled with "tutu"
	String csPath = rcsPath.get();	// "C:/toto/" 
	String csExt = rcsExt.get();	// "xml"
	*/
	public static String splitFilePathExt(String csFilePath, StringRef rcsPath, StringRef rcsExt)
	{
		String csFileName = csFilePath.replace('\\', '/') ; 
		int nSep = csFileName.lastIndexOf('/') ;
		if(nSep != -1)
		{
			if(rcsPath != null)
			{
				String csPath = csFileName.substring(0, nSep);
				rcsPath.set(csPath);				
			}

			csFileName = csFileName.substring(nSep+1) ;
		}
		
		nSep = csFileName.lastIndexOf('.');
		if(rcsExt != null)
		{
			if(nSep >= 0)
			{
				rcsExt.set(csFileName.substring(nSep+1));
				csFileName = csFileName.substring(0, nSep);
			}
			else
				rcsExt.set("");
		}
		return csFileName;
	}
	
	public static void createPath(String csPath)	// The path can be a path or full file name
	{
		// Check and create path if needed
		File file = new File(csPath);
		if(!file.exists())
		{
			int nIndex0 = csPath.lastIndexOf('/');
			int nIndex1 = csPath.lastIndexOf('\\');
			int nIndex = Math.max(nIndex0, nIndex1);
			if(nIndex != -1)
			{
				csPath = csPath.substring(0, nIndex);
				createPath(csPath);
				file = new File(csPath);
				file.mkdir();
			}
		}
	}
	
	public static boolean exists(String csFile)
	{
		File f = new File(csFile);
		return f.exists();		
	}
	
	public static void keepMoreRecentFile(String csPath, int nMaxBackupFileCount)
	{
		File path = new File(csPath);
		File[] files = path.listFiles((FileFilter)null);
		if (files != null)
		{
			Vector<File> vect = new Vector<File>();

			int nNbFiles = files.length;
			for(int n=0; n<nNbFiles; n++)
			{
				File file = files[n];
				vect.add(file);
			}
			
			FileTimestampComparator fileTimestampComparator = new FileTimestampComparator();  
			Collections.sort(vect, fileTimestampComparator);
			
			if(nNbFiles > nMaxBackupFileCount)
			{
				int nNbFilesToKeep = nNbFiles - nMaxBackupFileCount;
				for(int n=0; n<nNbFilesToKeep; n++)
				{
					File file = vect.get(n);
					file.delete();
				}
			}
		}
	}
	
	public static void DeleteContent(String csPath)
	{
		if(csPath != null)
		{
			File f = new File(csPath);
			DeleteContent(f);
		}
	}

	public static void DeleteContent(File f)
	{
		File[] dir = f.listFiles() ;
		if (dir != null)
		{
			for (int i=0; i<dir.length; i++)
			{
				if(dir[i].isDirectory())
				{
					DeleteContent(dir[i]);
				}
				dir[i].delete() ;
			}
		}
	}
	

	public static void DeleteDirAndContent(String csPath)
	{
		if(csPath != null)
		{
			File f = new File(csPath);
			File[] dir = f.listFiles() ;
			if (dir != null)
			{
				for (int i=0; i<dir.length; i++)
				{
					if(dir[i].isDirectory())
					{
						DeleteContent(dir[i]);
					}
					dir[i].delete() ;
				}
			}
			f.delete();
		}
	}
	
	public static File[] getFileList(String csDir)
	{
		csDir = normalizePath(csDir);
		File file = new File(csDir);
		return file.listFiles();
	}
	
	public static File[] getFileList(String csDir, FilenameFilter filenameFilter)
	{
		csDir = normalizePath(csDir);
		File file = new File(csDir);
		File[] lst = file.listFiles(filenameFilter);
		if (lst == null)
		{
			lst = new File[] {} ;
		}
		return lst ;
	}
	
	public static File[] getFileListByPrefix(String csDir, String csPrefix)
	{
		FileFilterByPrefix filter = new FileFilterByPrefix(csPrefix);
		
		csDir = normalizePath(csDir);
		File file = new File(csDir);
		File[] lst = file.listFiles(filter);
		if (lst == null)
		{
			lst = new File[] {} ;
		}
		return lst ;
	}
	public static String[] getFileNameListByPrefix(String csDir, String csPrefix)
	{
		FileFilterByPrefix filter = new FileFilterByPrefix(csPrefix);
		
		csDir = normalizePath(csDir);
		File file = new File(csDir);
		String[] lst = file.list(filter);
		if (lst == null)
		{
			lst = new String[] {};
		}
		return lst;
	}
	
	public static File[] getFileListBySuffix(String csDir, String csSuffix)
	{
		FileFilterBySuffix filter = new FileFilterBySuffix(csSuffix);
		
		csDir = normalizePath(csDir);
		File file = new File(csDir);
		File[] lst = file.listFiles(filter);
		if (lst == null)
		{
			lst = new File[] {} ;
		}
		return lst ;
	}
	public static String[] getFileNameListBySuffix(String csDir, String csSuffix)
	{
		FileFilterBySuffix filter = new FileFilterBySuffix(csSuffix);
		
		csDir = normalizePath(csDir);
		File file = new File(csDir);
		String[] lst = file.list(filter);
		if (lst == null)
		{
			lst = new String[] {};
		}
		return lst;
	}
	
	public static boolean delete(String csFile)
	{
		File file = new File(csFile);
		return file.delete();
	}
	
	public static boolean moveOrCopy(String csFileSource, String csFileDest)
	{
		File fileSource = new File(csFileSource);
		File fileDest = new File(csFileDest);
		return moveOrCopy(fileSource, fileDest) ;
	}
	
	public static boolean moveOrCopy(File fileSource, File fileDest)
	{
		if(fileSource.exists())
		{
			if(fileDest.exists())
			{
				fileDest.delete();
			}				
			if (!fileSource.renameTo(fileDest))
			{
				boolean ret = copy(fileSource, fileDest);
	            fileSource.delete();
	            return ret ;
			}
			return true ;
		}
		return false ;
	}

	public static boolean copy(String csSource, String csDestination)
	{
		createPath(csDestination);	// The path can be a path or full file name
		File source = new File(csSource);
		File destination = new File(csDestination);
		return copy(source, destination);		
	}
	
	public static boolean copy(File source, File destination)
	{
		boolean resultat = false;
        
        // Declaration des flux
        java.io.FileInputStream sourceFile=null;
        java.io.FileOutputStream destinationFile=null;
        
        try 
        {
			// Création du fichier :
        	destination.createNewFile();
			
			// Ouverture des flux
			sourceFile = new java.io.FileInputStream(source);
			if(sourceFile != null)
			{
				destinationFile = new java.io.FileOutputStream(destination);
				if(destinationFile != null)
				{
					// Lecture par segment de 100 K
					byte buffer[]=new byte[1024 * 100];
					int nNbBytesRead = sourceFile.read(buffer);
					while(nNbBytesRead != -1) 
					{
						destinationFile.write(buffer, 0, nNbBytesRead);
						nNbBytesRead = sourceFile.read(buffer);
					}
					// Copie réussie
					resultat = true;
				}
			}
        } 
        catch( java.io.FileNotFoundException f ) 
        {
        } 
        catch( java.io.IOException e ) 
        {
        } 
        finally 
        {
            // Quoi qu'il arrive, on ferme les flux
            try 
            {
            	if(sourceFile != null)
            	{
            		sourceFile.close();
            	}
            } 
            catch(Exception e)
            {
            }
            try 
            {
            	if(destinationFile != null)
            	{
            		destinationFile.close();
            	}
            }
            catch(Exception e) 
            {
            }
        }
        return resultat;
	}

	public static void WriteEOL(OutputStream stream)
	{
		try
		{
			stream.write('\n') ;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}		
	}
	
	public static int countLines(String csFilename)
	{
		return countLines(csFilename, null, 0);
	}
	
	public static int countLines(String csFilename, String csFormat, int nLength)
	{
		int nLines = 0;
		LineRead header = null;
		
		BaseDataFile dataFile = new DataFileLineReader(csFilename, 65536, 0);
		if (dataFile.open(null))	// PJD: Do not managed file header. Is it correct ?
		{		
			if (csFormat == null)
			{	
				while (dataFile.readNextUnixLine() != null)
				{
					nLines++;
				}
			}
			else
			{
				if (csFormat.equals("VB"))
				{
					while (!dataFile.isEOF())
					{	
						header = dataFile.readBuffer(4, false);
						if (header != null)
						{
							int nLengthExcludingHeader = header.getAsLittleEndingUnsignBinaryInt();
							dataFile.readBuffer(nLengthExcludingHeader, true);
							nLines++;
						}
					}
				}
				else
				{
					while (dataFile.readBuffer(nLength, true) != null)
					{
						nLines++;
					}
				}
			}	
			dataFile.close();
		}	
		
		return nLines;
	}

	public static FileCompareStat compareFiles(String csFilenameLeft, String csFilenameRight, Vector<Integer> vFilterPos, 
			boolean bAsciiLeft, boolean bAsciiRight)
	{
		return compareFiles(csFilenameLeft, csFilenameRight, vFilterPos, bAsciiLeft, bAsciiRight, null, 0);
	}
	
	public static FileCompareStat compareFiles(String csFilenameLeft, String csFilenameRight, Vector<Integer> vFilterPos, 
			boolean bAsciiLeft, boolean bAsciiRight, String csFormat, int nLength)
	{	
		BaseDataFile fileLeft = new DataFileLineReader(csFilenameLeft, 65536, 0);
		fileLeft.open(null);	// PJD: Do not manage file header
		BaseDataFile fileRight = new DataFileLineReader(csFilenameRight, 65536, 0);
		fileRight.open(null);	// Do not manage file header
		
		int nLinesLeft = 0, nLinesRight = 0, nLineDiff = 0;
		boolean equal = true ;
		
		LineRead lineReadLeft = null;
		LineRead lineReadRight = null;
		LineRead header = null;
		
		if (csFormat == null)
		{
			lineReadLeft = fileLeft.readNextUnixLine();
			lineReadRight = fileRight.readNextUnixLine();
			while (lineReadLeft != null)
			{
				nLinesLeft++;
				if (lineReadRight == null)
				{
					equal = false;
					if (nLineDiff == 0)
						nLineDiff = nLinesLeft;
				}
				else
				{
					nLinesRight++;
					if (equal)
					{
						equal = compareFilesString(lineReadLeft, lineReadRight, vFilterPos, bAsciiLeft, bAsciiRight);
						if (!equal)
						{
							nLineDiff = nLinesLeft;
						}
					}
					lineReadRight = fileRight.readNextUnixLine();
				}
				lineReadLeft = fileLeft.readNextUnixLine();
			}
			while (lineReadRight != null)
			{
				equal = false;
				nLinesRight++;
				if (nLineDiff == 0)
					nLineDiff = nLinesRight;
				lineReadRight = fileRight.readNextUnixLine();
			}
		}
		else
		{
			if (csFormat.equals("VB"))
			{
				header = fileLeft.readBuffer(4, false);
				if (header != null)
				{
					int nLengthExcludingHeader = header.getAsLittleEndingUnsignBinaryInt();
					lineReadLeft = fileLeft.readBuffer(nLengthExcludingHeader, true);
				}
				header = fileRight.readBuffer(4, false);
				if (header != null)
				{
					int nLengthExcludingHeader = header.getAsLittleEndingUnsignBinaryInt();
					lineReadRight = fileRight.readBuffer(nLengthExcludingHeader, true);
				}
				while (lineReadLeft != null)
				{
					nLinesLeft++;
					if (lineReadRight == null)
					{
						if (nLineDiff == 0)
							nLineDiff = nLinesLeft;
						equal = false;
					}
					else
					{
						nLinesRight++;
						if (equal)
						{
							equal = compareFilesString(lineReadLeft, lineReadRight, vFilterPos, bAsciiLeft, bAsciiRight);
							if (!equal)
							{
								nLineDiff = nLinesLeft;
							}
						}
						lineReadRight = null;
						header = fileRight.readBuffer(4, false);
						if (header != null)
						{
							int nLengthExcludingHeader = header.getAsLittleEndingUnsignBinaryInt();
							lineReadRight = fileRight.readBuffer(nLengthExcludingHeader, true);
						}
					}
					lineReadLeft = null;
					header = fileLeft.readBuffer(4, false);
					if (header != null)
					{
						int nLengthExcludingHeader = header.getAsLittleEndingUnsignBinaryInt();
						lineReadLeft = fileLeft.readBuffer(nLengthExcludingHeader, true);
					}
				}
				while (lineReadRight != null)
				{	
					equal = false;
					nLinesRight++;
					if (nLineDiff == 0)
						nLineDiff = nLinesRight;
					lineReadRight = null;
					header = fileRight.readBuffer(4, false);
					if (header != null)
					{
						int nLengthExcludingHeader = header.getAsLittleEndingUnsignBinaryInt();
						lineReadRight = fileRight.readBuffer(nLengthExcludingHeader, true);
					}
				}
			}
			else
			{
				lineReadLeft = fileLeft.readBuffer(nLength, true);
				lineReadRight = fileRight.readBuffer(nLength, true);
				while (lineReadLeft != null)
				{
					nLinesLeft++;
					if (lineReadRight == null)
					{
						if (nLineDiff == 0)
							nLineDiff = nLinesLeft;
						equal = false;
					}
					else
					{
						nLinesRight++;
						if (equal)
						{
							equal = compareFilesString(lineReadLeft, lineReadRight, vFilterPos, bAsciiLeft, bAsciiRight);
							if (!equal)
							{
								nLineDiff = nLinesLeft;
							}
						}
						lineReadRight = fileRight.readBuffer(nLength, true);
					}
					lineReadLeft = fileLeft.readBuffer(nLength, true);
				}
				while (lineReadRight != null)
				{	
					equal = false;
					nLinesRight++;
					if (nLineDiff == 0)
						nLineDiff = nLinesRight;
					lineReadRight = fileRight.readBuffer(nLength, true);
				}
			}
		}

		fileRight.close();
		fileLeft.close();
		
		FileCompareStat stat = new FileCompareStat();
		stat.setNbLinesLeft(nLinesLeft);
		stat.setNbLinesRight(nLinesRight);
		stat.setEqual(equal);
		stat.setNLineDiff(nLineDiff);
		
		return stat;
	}
	
	private static boolean compareFilesString(LineRead lineReadLeft, LineRead lineReadRight, Vector<Integer> vFilterPos, boolean bAsciiLeft, boolean bAsciiRight) {
		if (lineReadLeft.getTotalLength() != lineReadRight.getTotalLength())
			return false;
		for (int i=0; i < lineReadLeft.getTotalLength(); i++)
		{
			if (vFilterPos == null || !vFilterPos.contains(i))
			{
				byte byteLeft = lineReadLeft.getBuffer()[lineReadLeft.getOffset() + i];
				byte byteRight = lineReadRight.getBuffer()[lineReadRight.getOffset() + i];
				if (bAsciiLeft == bAsciiRight)
				{
					if (byteLeft != byteRight)
						return false;
				}
				else
				{
					if (byteLeft != byteRight)
					{
						if ((bAsciiLeft && byteLeft != AsciiEbcdicConverter.getAsciiByte(byteRight)) ||
							(bAsciiRight && AsciiEbcdicConverter.getAsciiByte(byteLeft) != byteRight))
							return false;
					}
				}
			}
		}
		return true;
	}

	public static String getTempFileName()
	{
		 RandomGuid guid = new RandomGuid();
		 return guid.formatAsFilename();
	}
	
	public static void copyDirectory(File source, File destination) 
	{
		File[] list = source.listFiles();

		for (int i=0;i<list.length;i++) 
		{
			File dest=new File(destination,list[i].getName());
			if (list[i].isDirectory()) 
			{
				dest.mkdir(); 
				copyDirectory(list[i],dest);
			} 
			else 
			{
				copy(list[i],dest);
			}
		}
	}

	public static byte[] getBytesFromFile(File file) throws IOException
	{
        InputStream is = new FileInputStream(file);
    
        // Get the size of the file
        long length = file.length();
    
        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
    
        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];
    
        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }
    
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }
    
        // Close the input stream and return bytes
        is.close();
        return bytes;
    }	
	
	public static BufferedInputStream openRead(String csFile)
	{
		BufferedInputStream bufStreamIn;
		try
		{
			bufStreamIn = new BufferedInputStream(new DataInputStream(new FileInputStream(csFile)));
			return bufStreamIn;		
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return null;
	}
	
	public static StringBuilder readWholeFile(String csFile)
	{		
		BufferedInputStream buf = openRead(csFile);
		if(buf == null)
			return null;
	
		StringBuilder sbOut = new StringBuilder();
		boolean bContinue = true;
		try
		{
			int n=0;
			while(bContinue && buf.available() > 0)
			{
				char cChar = (char)buf.read();
//				if(n == 1140 || cChar== 'Ã' || cChar== '¼')
//				{
//					int gg =0 ;
//				}
//				
//				
//				
				sbOut.append(cChar);
				n++;
			}
		}
		catch (IOException e)
		{
			closeFile(buf);
			return null;
		}
		closeFile(buf);
		return sbOut;
	}
	
	public static DataOutputStream openWrite(String csFile)
	{
		DataOutputStream streamOut;
		try
		{
			streamOut = new DataOutputStream(new FileOutputStream(csFile));
			return streamOut;		
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean closeFile(BufferedInputStream bufStreamIn)
	{
		try
		{
			if(bufStreamIn != null)
			{
				bufStreamIn.close();
				return true;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean closeFile(DataOutputStream streamOut)
	{
		try
		{
			if(streamOut != null)
			{
				streamOut.close();
				return true;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public static StringBuilder readFile(String csFile)
	{
		try
		{
			return readFile(new FileInputStream(csFile));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public static StringBuilder readFile(FileInputStream is)
	{
		try
		{
			Reader in = new InputStreamReader(is);
			StringBuilder sb = new StringBuilder();
			CharBuffer bufTarget = CharBuffer.allocate(65536); 
			int nLength = in.read(bufTarget);
			while(nLength >= 0)
			{
				char t[] = bufTarget.array();
				sb.append(t, 0, nLength);
				nLength = in.read(bufTarget);				
			}
			return sb;
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static StringBuilder readFileUtf8(String csFile)
	{
		try
		{
			return readFileUtf8(new FileInputStream(csFile));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public static StringBuilder readFileUtf8(FileInputStream is)
	{
		try
		{
			Reader in = new InputStreamReader(is, "UTF-8");
			CharBuffer bufTarget = CharBuffer.allocate(65536); 
			int nLength = in.read(bufTarget);
			if(nLength >= 0)
			{
				char t[] = bufTarget.array();
				StringBuilder sb = new StringBuilder();
				sb.append(t, 0, nLength);
				return sb;
			}
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static boolean writeFile(String csFile, StringBuilder sb)
	{
		return writeFile(csFile, sb.toString());
	}
	public static boolean writeFile(String csFile, String csContent)
	{
		try
		{
		    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csFile)));
		    out.write(csContent);
		    out.close();
		    return true;
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static boolean writeFileUtf8(String csFile, StringBuilder sb)
	{
		return writeFileUtf8(csFile, sb.toString());
	}
	public static boolean writeFileUtf8(String csFile, String csContent)
	{
		try
		{
		    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csFile), "UTF8"));
		    out.write(csContent);
		    out.close();
		    return true;
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}