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
package jlib.Helpers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;
/**
 * Class containing helpers for creating ZIP archives from folders, files and list of files.
 */
public class ZipHelper {
//**********************************************************************************
//**               Zips the content of the specified folder.                      **
//**********************************************************************************
/**
 * Zips the content of the specified folder.
 * The create Zip archive preserves the same structure as the specified folder. If the
 * Zip archive already exists, it is overwritten.
 * @param sourceFolder The folder whose content is to be archived.
 * @param destinationZipArchive The zip archive where to archive the folder content.
 * @param moveFiles If <i>true</i>, archived files are erased from the source folder. The source folder
 * itself is not removed.
 */
	public static void zipFolder(File sourceFolder,File destinationZipArchive,boolean moveFiles) throws Exception {
		FileOutputStream fos;        // File stream, open on the zip archive.
		ZipOutputStream zos;         // Stream zipper.
		try {
//***************************** Initialization *************************************
			if (!sourceFolder.exists())
				throw new Exception("The specified source folder doesn't exist.");
			if (!sourceFolder.isDirectory())
				throw new Exception("The specified source folder isn't a folder.");

			if (destinationZipArchive.exists()) {
				if (destinationZipArchive.isDirectory())
					throw new Exception("The specified zip archive is a folder.");
				destinationZipArchive.delete();
			}

//******************** Launches the archiving process ******************************
			fos=new FileOutputStream(destinationZipArchive);
			zos=new ZipOutputStream(fos);
			_zipFolder(sourceFolder,"",zos);
			zos.close();
			fos.close();

			if (moveFiles)
				_deleteFolder(sourceFolder,"");
		}

//********************** Exception management **************************************
		catch (Exception e) {
			String s1,s2;
			if (sourceFolder==null) s1="null"; else s1=sourceFolder.getAbsolutePath();
			if (destinationZipArchive==null) s2="null"; else s2=destinationZipArchive.getAbsolutePath();
			throw new Exception(ParseError.parseError("ApplicationHelper.zipFolder('"+s1+"','"+s2+"',"+moveFiles+")",e));
		}
	}

//*********************** Deletes one folder ***************************************
	private static void _deleteFolder(File baseFolder,String path) throws Exception {
		File currentFolder;               // The current folder is the concatenation of baseFolder and path.
		File[] contents;                  // The list of contents of the current folder.
		int n,nn;                         // To parse the list of contents of the current folder.
		File content;                     // One entry in the content list.
		try {
//............................. Initialization ..................................... 
			currentFolder=new File(baseFolder,path);
			if (!currentFolder.exists())
				throw new Exception("Folder '"+baseFolder+"' doesn't exist.");
			if (!currentFolder.isDirectory())
				throw new Exception("'"+currentFolder+"' is not a folder.");

			if (path==null) path="";
			if (path.length()>0) path+="/";

//..................... Deletes all content in the folder .........................
			contents=currentFolder.listFiles();
			nn=contents.length;
			for(n=0;n<nn;n++) {
				content=contents[n];
// If the current content entry is a folder:
				if (content.isDirectory()) {
					_deleteFolder(baseFolder,path+content.getName());
					content.delete();
				}

// If the current content entry is a file:
				else
					content.delete();
			}
		}
//............................ Exception management ..............................
		catch (Exception e) {
			String s1;
			if (baseFolder==null) s1="null"; else s1=baseFolder.getAbsolutePath();
			throw new Exception(ParseError.parseError("ApplicationHelper._deleteFolder('"+s1+"','"+path+"')",e));
		}
	}

//********************  Archives one folder ****************************************
	private static void _zipFolder(File baseFolder,String path,ZipOutputStream zos) throws Exception {
		File currentFolder;               // The current folder is the concatenation of baseFolder and path.
		File[] contents;                  // The list of contents of the current folder.
		int n,nn;                         // To parse the list of contents of the current folder.
		File content;                     // One entry in the content list.
		
		ZipEntry ze;                      // Each file in the current folder is inserted as a new ZipEntry.
		BufferedInputStream bis;          // To read one file in the current folder.
		byte buffer[]=new byte[1000];     // A buffer between the content of the file and the zip archive.
		int bufferSize=buffer.length;     // The size of the buffer.
		int bytesRead;                    // The number of bytes read from the file.
		try {
//............................. Initialization ..................................... 
			currentFolder=new File(baseFolder,path);
			if (!currentFolder.exists())
				throw new Exception("Folder '"+baseFolder+"' doesn't exist.");
			if (!currentFolder.isDirectory())
				throw new Exception("'"+currentFolder+"' is not a folder.");

			if (path==null) path="";
			if (path.length()>0) path+="/";

//..................... Archives all content in the folder .........................
			contents=currentFolder.listFiles();
			nn=contents.length;
			for(n=0;n<nn;n++) {
				content=contents[n];
//	 If the current content entry is a folder:
				if (content.isDirectory()) {
					_zipFolder(baseFolder,path+content.getName(),zos);
				}

//	 If the current content entry is a file:
				else {
					ze=new ZipEntry(path+content.getName());
					try {
						zos.putNextEntry(ze);

//		 Adds the content of the file:
						bis=new BufferedInputStream(new FileInputStream(content));
						for(;;) {
							bytesRead=bis.read(buffer,0,bufferSize);
							if (bytesRead<=0) break;
							zos.write(buffer,0,bytesRead);
						}
						bis.close();

//		 Closes the entry:
						zos.closeEntry();
					}
					catch (ZipException e) {
						if (!e.getMessage().startsWith("duplicate entry"))
							throw new Exception(ParseError.parseError("Error zipping '"+content.getAbsolutePath()+"': ",e));
					}
				}
			}
		}
		catch (Exception e) {
			String s1;
			if (baseFolder==null) s1="null"; else s1=baseFolder.getAbsolutePath();
			throw new Exception(ParseError.parseError("ApplicationHelper._zipFolder('"+s1+"','"+path+"',ZipOutputStream)",e));
		}
	}
//**********************************************************************************
//**                 Zips the specified collection of files.                      **
//**********************************************************************************
/**
 * Zips the specified file.
 * @param file The file to archive.
 * @param destinationZipArchive Specifies the zip archive where to store the specified 
 * file. If the zip archive already exists, it is deleted before starting
 * the process. Folders and subfolders are created if needed. If the specified file
 * already exists and it is a folder, an exception is raised.
 * @param moveFile If <i>true</i> the specified file is deleted from its original
 * location after the zip archive has been successfully created.
 */
	public static void zipFile(File file,File destinationZipArchive,boolean moveFile) throws Exception {
		try {
			ArrayList<File> list=new ArrayList<File>();
			list.add(file);
			zipFiles(list,destinationZipArchive,moveFile);
		}
		catch  (Exception e) {
			String sFile="null";
			if (file!=null) sFile=file.getAbsolutePath();
			String sDestinationZipArchive="null";
			if (destinationZipArchive!=null) sDestinationZipArchive=destinationZipArchive.getAbsolutePath();
			throw new Exception(ParseError.parseError("ZipHelper.zipFile('"+sFile+"','"+sDestinationZipArchive+"',"+moveFile,e));
		}
	}

//**********************************************************************************
//**                 Zips the specified collection of files.                      **
//**********************************************************************************
/**
 * Zips the specified collection of files.
 * The created Zip archive is flat, without any folder hierarchy. This is an
 * example of use:
 * <pre>
 * 	ArrayList<String> list=new ArrayList<String>();
 * 	File []files=FileHelper.getFileList("C:\\MyFolder\\*.*");
 * 	for (int n=0;n&lt;files.length;n++)
 * 		if (files[n].isFile())
 * 			list.add(files[n].getAbsolutePath());
 * 
 * 	ApplicationHelper.zipFiles(list,new File("C:\\MyFolder\MyArchive.zip"),false);
 * </pre>
 * @param files The list of files to add to the zip archive. Entries in the list
 * can be either {@link String} containing full paths to the files, or {@link File}
 * objects. Any other class raise an exception. All files have to exist, and can't be
 * folders (an exception is raised in either case).
 * @param destinationZipArchive Specifies the zip archive where to store the files
 * specified in the list. If the zip archive already exists, it is deleted before starting
 * the process. Folders and subfolders are created if needed. If the specified file
 * already exists and it is a folder, an exception is raised.
 * @param moveFiles If <i>true</i> the specified files are deleted from their original
 * location after the zip archive has been successfully created. No file is deleted
 * before the zip archive is completed. 
 */
	public static void zipFiles(List files,File destinationZipArchive,boolean moveFiles) throws Exception {
		int nFiles,nnFiles;            // To parse the list of files.
		String fileName;               // Absolute path to one of the files in the list.
		File file;                     // One of the files in the list.

		FileOutputStream fos;          // A stream to create the ZIP archive. 
		ZipOutputStream zos;           // The Zip deflater. 
		ZipEntry ze;                   // Each file in the list is one zip entry.

		FileInputStream fis;           // To read the content of one file in the list.
		BufferedInputStream bis;       // To read the content of one file in the list.
		byte buffer[]=new byte[1000];  // A buffer between the content of the file and the zip archive.
		int bufferSize=buffer.length;  // The size of the buffer.
		int bytesRead;                 // The number of bytes read from the file.

		try {
//****************************** Initialization *****************************

//................. Checks the destination zip archive ......................
			if (destinationZipArchive.exists()) {
				if (destinationZipArchive.isDirectory())
					throw new Exception("The specified zip archive '"+destinationZipArchive+"' already exists, and it is a folder.");
				else
					destinationZipArchive.delete();
			} else {
				File destinationFolder=destinationZipArchive.getParentFile();
				if (!destinationFolder.exists())
					if (!destinationFolder.mkdirs())
						throw new Exception("Could not create the folder for '"+destinationZipArchive.getAbsolutePath()+"'.");
			}

//.................... Initializes a ZIP stream .............................
			fos=new FileOutputStream(destinationZipArchive);
			zos=new ZipOutputStream(fos);
				
//**************** Puts every file in the list in the zip archive ***********
			nnFiles=files.size();
			for(nFiles=0;nFiles<nnFiles;nFiles++) {
//......................... Retrieves one item in the list ..................
				Object o=files.get(nFiles);

// Checks if it is an admissible instance:
				if (o instanceof String) {
					fileName=(String)o;
					file=new File(fileName);
				} else if (o instanceof File) {
					file=(File)o;
				} else {
					throw new Exception("Only 'String' and 'File' instances can be specified in the files list.");
				}

//........................... Checks if the file exists .........................
				if (!file.exists())
					throw new Exception("File '"+file.getAbsolutePath()+"', specified in the files list, doesn't exist.");
				if (file.isDirectory())
					throw new Exception("File '"+file.getAbsolutePath()+"', specified in the files list, is actually a folder.");

//................ Adds the content of the file in the zip archive ..............
				ze=new ZipEntry(file.getName());
				try {
					zos.putNextEntry(ze);

// Adds the content of the file:
					fis=new FileInputStream(file);
					bis=new BufferedInputStream(fis);
					for(;;) {
						bytesRead=bis.read(buffer,0,bufferSize);
						if (bytesRead<=0) break;
						zos.write(buffer,0,bytesRead);
					}
					fis.close();
					bis.close();

// Closes the entry:
					zos.closeEntry();
				}
				catch (ZipException e) {
					if (!e.getMessage().startsWith("duplicate entry"))
						throw new Exception(ParseError.parseError("Error zipping '"+file.getAbsolutePath()+"':",e));
				}
			}

//************************* Closes the Zip archive ******************************
			zos.close();
			fos.close();
				
//*********************** Deletes all files in the list *************************
			if (moveFiles) {
				nnFiles=files.size();
				for(nFiles=0;nFiles<nnFiles;nFiles++) {
//......................... Retrieves one item in the list ..................
					Object o=files.get(nFiles);

// Checks if it is an admissible instance:
					if (o instanceof String) {
						fileName=(String)o;
						file=new File(fileName);
					} else if (o instanceof File) {
						file=(File)o;
					} else {
						throw new Exception("Only 'String' and 'File' instances can be specified in the files list.");
					}

//........................... Deletes the file ..................................
					if (file.exists())
						if (file.isFile()) {
							if (file.delete())
								System.out.println("Deleted "+file.getAbsolutePath()+".");
							else
								System.out.println("Could not delete "+file.getAbsolutePath()+".");
							
						}
				}
			}
		}

//************************* Exception management *******************************
		catch (Exception e) {
			String sFiles="null";
			if (files!=null) sFiles=files.size()+" elements.";
			String sDestinationZipArchive="null";
			if (destinationZipArchive!=null) sDestinationZipArchive=destinationZipArchive.getAbsolutePath();
			throw new Exception(ParseError.parseError("ZipHelper.zipFiles('"+sFiles+"','"+sDestinationZipArchive+"',"+moveFiles,e));
		}
	}
}
