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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jlib.exception.ProgrammingException;

//****************************************************************************
//**           Implements a filename filter based on wildcards.             **
//****************************************************************************
/**
 * Implements a filename filter based on wildcards.
 * Wildcards are used as in DOS. For example:
 * <ul>
 * 	<li><b>?</b>Replaces one occurrence of any valid char.</li>
 * 	<li><b>*</b>Replaces zero or several occurrences of any valid char.</li>
 * </ul>
 * Valid chars are all available except:
 * <pre>\ / : * ? " < > |</pre>
 * The following example retrieves all files whose names
 * start with <i>dd</i>, and finishing with <i>.java</i>:
 * <pre>
 * 	File folder=new File("C:\\temp");
 * 	FileHelper filter=new FileHelper("dd*.java");
 * 	fileList=folder.list(filter);
 * </pre> 
 */
public class FileHelper implements FilenameFilter 
{
	
//****************************************************************************
//**                          The class constructor.                        **
//****************************************************************************
	private Pattern m_pattern = null;
/**
 * Sets the wildcard to use for filtering filenames.
 * @param wildcard The wildcard to use for filtering filenames.
 */
	public FileHelper(String wildcard)
	{
//******************* Checks if the specified wildcard is not empty ***********
		if (wildcard == null) 
			wildcard = "";
		int nn = wildcard.length();
		if (nn > 0)
		{

//*************** Translates the wildcard into a regular expression ***********
			StringBuilder regex = new StringBuilder();
			for(int n=0; n<nn; n++)
			{
				char c = wildcard.charAt(n);
				switch(c)
				{
// Checks the chars not legal in file names:
					case '\\':
					case '/':
					case ':':
					case '"':
					case '<':
					case '>':
					case '|':
						throw new ProgrammingException(ProgrammingException.INVALID_FILENAME,"Character '"+c+"' is not valid for a file name.");

// Checks the chars having a special meaning for regular expressions:
					case '[':
					case '^':
					case '$':
					case '.':
					case '+':
					case '(':
					case ')':
						regex.append("\\");
						regex.append(c);
						break;

// Checks the wildcard chars:
					case '?':
						regex.append(".");
						break;
					case '*':
						regex.append(".*");
						break;

// All other chars don't have any special treatment:
					default:
						regex.append(c);
				}
			}

//************************* Compiles the translated pattern ***************************
			m_pattern = Pattern.compile(regex.toString(), Pattern.CASE_INSENSITIVE);
		} 
	}
//************************************************************************************
//**         Checks if a particular filename matches the specified wildcard         **
//************************************************************************************
/**
 * Checks if a particular filename matches the specified wildcard.
 * This method is part of the {@link FilenameFilter} interface.
 */
	public boolean accept(File folder, String filename)
	{
		if (m_pattern==null)
			return true;
		
		if (filename == null)
			filename="";
		
		if (filename.length() == 0)
			return false;
		
		Matcher m = m_pattern.matcher(filename);
		return m.matches();
	}
	
//***********************************************************************************
//**               Returns the list of files matching
//***********************************************************************************
/**
 * Returns the list of filenames matching the specified path.
 * The path can contain wildcards in its 'file' section, but
 * not in its 'folder' section.
 * @param path The physical path to the files. If the path is relative,
 * the current user directory is used as default folder.
 * @return The list of matching files. If no files match the
 * specified path, the collection will be empty.
 */
	public static File [] getFileList(String path)
	{
		return getFileList(null, path, false);
	}

/**
 * Returns a list of files from the specified absolute folder path and
 * whose files end with the extension given in parameter (with or without '.' in the extension)
 * @param path The physical path to the files. If the path is relative,
 * the current user directory is used as default folder.
 * @param filesOnly If <i>true</i>, returns only files, not folders.
 * @param endsWith : will only filter files with this extension
 * @return The list of matching files. If no files match the
 * specified path, the collection will be empty.
 */
	public static ArrayList<File> getFileList(String path, boolean filesOnly, String endsWith)
	{
		ArrayList<File> fileListOutput = new ArrayList<File>();
		File file = null;
		
		File [] fileListInput = getFileList(null, path, filesOnly);
		for (int i=0; i<fileListInput.length; i++) {
		 	file = fileListInput[i];
		 	if (file.getName().endsWith(endsWith)) {
		 		fileListOutput.add(file);
		 	}
		}
		
		return fileListOutput;
	}

	/**
 * Returns the list of filenames matching the specified path.
 * The path can contain wildcards in its 'file' section, but
 * not in its 'folder' section.
 * @param path The physical path to the files. If the path is relative,
 * the current user directory is used as default folder.
 * @param filesOnly If <i>true</i>, returns only files, not folders.
 * @return The list of matching files. If no files match the
 * specified path, the collection will be empty.
 */
	public static File [] getFileList(String path,boolean filesOnly)
	{
		return getFileList(null, path, filesOnly);
	}
	
/**
 * Returns the list of filenames matching the specified path.
 * The path can contain wildcards in its 'file' section, but
 * not in its 'folder' section.
 * @param defaultFolder The default folder is used when the
 * specified path is relative. If this param is left null or empty,
 * the current user directory is used instead.
 * @param path The physical path to the files.
 * @param filesOnly If <i>true</i>, returns only files, not folders.
 * @return The list of matching files. If no files match the
 * specified path, the collection will be empty.
 */
	public static File [] getFileList(String defaultFolder, String path, boolean filesOnly)
	{
		File fileList[];                       // Contains the list of items corresponding to the specified path.
		File filesOnlyList[];                  // Contains the list of files (not folders) corresponding to the specified path.
		File file;
		File folder;

//******************************* Initialization ********************************************
		if (path == null)
			return new File[0];
		
		if (path.length() == 0) 
			return new File[0];

		if (defaultFolder == null)
			defaultFolder = "";			
		if (defaultFolder.length() == 0)
			defaultFolder = System.getProperty("user.dir");


//************************ Tries the path as a relative path ********************************
		file = new File(defaultFolder,path);
		if (file.exists())
		{

//......................... The source can be a single file .................................
			if (file.isFile()) 
			{
				fileList = new File[1];
				fileList[0] = file;
			}

//............................. The source can be a folder ..................................
			else 
			{
				fileList = file.listFiles();
			}
		}

//.......................... The source can be a path with wildcards ........................
		else
		{
			folder = new File(file.getParent());
			if (folder.exists()) 
			{
				String wildcard = file.getName();
				FileHelper filter = new FileHelper(wildcard);
				fileList = folder.listFiles(filter);
			}
			else 
			{
//************************ Tries the source as an absolute path *****************************
				file = new File(path);
				if (file.exists()) 
				{

//......................... The source can be a single file .................................
					if (file.isFile()) 
					{
						fileList = new File[1];
						fileList[0] = file;
					}

//............................. The source can be a folder ..................................
					else
					{
						fileList = file.listFiles();
					}
				}

//.......................... The source can be a path with wildcards ........................
				else
				{
					folder = new File(file.getParent());
					if (folder.exists()) 
					{
						String wildcard = file.getName();
						FileHelper filter = new FileHelper(wildcard);
						fileList = folder.listFiles(filter);
					}
//****************************** Nothing is found ******************************************
					else 
					{
						return new File[0];
					}
				}					
			}
		}

//******************* If needed, filters the file list to remove all folders ***************
		if (filesOnly)
		{
			int nFiles = 0;
			int nn = fileList.length;
			for(int n=0; n<nn; n++) {
				if (fileList[n].isFile())
					nFiles++;
			}
			filesOnlyList = new File[nFiles];
			nFiles = 0;
			for(int n=0; n<nn; n++) 
			{
				if (fileList[n].isFile())
					filesOnlyList[nFiles++] = fileList[n];
			}
			return filesOnlyList;
		}

//******************************* Or else, returns the complete list of items ****************
// (folders + files).
		else 
		{
			return fileList;
		}

	}

//******************************************************************************************
//**                        Copies a file to a folder or to a file.                       **
//******************************************************************************************
/**
 * Copies a file to a folder or to a file.
 * @param origin The source file to copy. The file must exist, and it must be a 
 * file (not a folder).
 * @param destination The destination file or folder. If the destination doesn't exist,
 * it is assumed to be a file.
 * @trows Exception If the source file is not specified (null), doesn't exist, or 
 * is a folder. Also if the destination can't be created. 
 */
	public static void copyFile(File origin, File destination) {
		copyFile(origin,destination,null);
	}	

/**
 * Copies a file to a folder or to a file.
 * @param origin The source stream to copy
 * @param destination The destination stream.
 */
	public static void copyFile(InputStream origin,OutputStream destination) {
		try {
			byte[] buf = new byte[1024];
			int i = 0;
			while((i=origin.read(buf))!=-1) 
			{
				destination.write(buf, 0, i);
			}
			origin.close();
			destination.close();
		} catch (IOException e) {
			throw new ProgrammingException(ProgrammingException.IO_ERROR,e.getMessage(),e);
		}
	}

/**
 * Copies a file to a folder or to a file.
 * @param origin The source file to copy. The file must exist, and it must be a 
 * file (not a folder).
 * @param destination The destination file or folder. If the destination doesn't exist,
 * it is assumed to be a file.
 * @trows Exception If the source file is not specified (null), doesn't exist, or 
 * is a folder. Also if the destination can't be created. 
 */
	public static void copyFile(String origin, String destination)
	{
		File file1 = new File(origin);
		File file2 = new File(destination);
		copyFile(file1,file2);
	}

/**
 * 
 * Copies a file to a folder or to a file.
 * @param origin The source file to copy. The file must exist, and it must be a 
 * file (not a folder).
 * @param destination The destination file or folder. If the destination doesn't exist,
 * it is assumed to be a file.
 * @newName The name under which we copy the file
 * @trows Exception If the source file is not specified (null), doesn't exist, or 
 * is a folder. Also if the destination can't be created. 
 */		
	public static void copyFile(File origin, File destination, String newName) {
		String name;
		//controle que le fichier origin n'est pas null
		if (origin == null)
			throw new ProgrammingException(ProgrammingException.INVALID_FILENAME,"origin file is not specified (null).");
		//controle que le fichier origin existe
		if (!origin.exists())
			throw new ProgrammingException(ProgrammingException.IO_ERROR,"The origin file '" + origin.getAbsolutePath() + "' doesn't exist.");
		if (!origin.isFile())
			throw new ProgrammingException(ProgrammingException.IO_ERROR,"The origin file '" + origin.getAbsolutePath() + "' is not a file.");
			
		if (destination!=null) {
			//si destination n'existe pas on assume que c'est un fichier
			//sinon 
			if (destination.exists()){
				if (destination.isDirectory()){
					//on ajoute au path du dossier le nouveau nom du fichier destination.
					if (newName==null)
						newName="";
					if (newName.length()>0)
						name=newName;
					else
						name = origin.getName();
					destination = new File(destination, name);
				}
			} 
		} else 
			throw new ProgrammingException(ProgrammingException.INVALID_FILENAME,"destination file or folder is null.");
				
		FileInputStream fis;
		FileOutputStream fos;
		try {
			fis  = new FileInputStream(origin);
			fos = new FileOutputStream(destination);
		} catch (FileNotFoundException e) {
			throw new ProgrammingException(ProgrammingException.INVALID_FILENAME,e.getMessage(),e);
		}
		copyFile(fis,fos);
	}

//************************************************************************************
//**               Checks if the specified path is absolute.                        **
//************************************************************************************
/**
 * Checks if the specified path is absolute.
 * <ul>
 * 	<li><b>In Windows</b> the path is absolute if it starts with a letter unit:
 * 	<pre>C:/this/that/...</pre></li>
 * 	<li><b>In Unix</b> the path is absolute if it starts with a "/":
 * 	<pre>/root/data/...</pre></li>
 * </ul>
 * @param The path to check.
 * @return <i>true</i> if the path is absolute.
 */
	public static boolean isAbsolutePath(String path) {
		String osName;
		if (path==null) return false;
		if (path.length()==0) return false;
		osName=System.getProperty("os.name").toLowerCase();

		if (osName.startsWith("win")) {
			if (path.length()<2) return false;
			if (path.charAt(1)==':') return true;
		} 
		else if (path.startsWith("/"))
			return true;
		return false;
	}
//**********************************************************************************
//**                 Ensures that a file name is unique.                          **
//**********************************************************************************
/**
 * Ensures that a file name is unique.
 */
	public static File makeUnique(File folder,String fileName) {
		File unique=new File(folder,fileName);
		int sufix=0;
		while (unique.exists()) {
			int n=fileName.lastIndexOf('.');
			String baseName=fileName.substring(0,n);
			String extension=fileName.substring(n);
			unique=new File(folder,baseName+String.valueOf(sufix++)+extension);
		}
		return unique;
	}

/**
 * Ensures that a file name is unique.
 */
	public static File makeUnique(File file) {
		if (!file.exists())
			return file;
		File baseFolder=file.getParentFile();
		String fileName=file.getName();
		return makeUnique(baseFolder,fileName);
	}

//**********************************************************************************
//**                 Standardize a file name.                                      **
//**********************************************************************************
/**
 * Standardize the specified file name.
 * Changing all special chars to US-ASCII char set, and removing all dots except
 * the extension. For example:
 * <pre>5. Hönigstrasse 6-a.jpg</pre>
 * is converted to:
 * <pre>5__Hoenigstrasse_6-a.jpg</pre>
 * 
 */
	public static String standarizeFilename(String fileName) {
		StringBuilder standard;
		char c;
		int n,nn;

//********************************** Initialization ********************************
		if (fileName==null) return null;
		if (fileName.length()==0) return "";
		standard=new StringBuilder();

//********************** Transforms each char in the specified file name ***********
		nn=fileName.length();
		for(n=0;n<nn;n++) {
			c=fileName.charAt(n);
			switch(c) {
				case '_':
				case '.':
					break;
				case '-':
					break;
				case 'ç': c='c'; break;
				case 'Ç': c='C'; break;
				case 'Ñ': c='N'; break;
				case 'ñ': c='n'; break;

				case 'ä':
				case 'â':
				case 'à':
				case 'á':
					c='a';
					break;
				case 'ë':
				case 'ê':
				case 'è':
				case 'é':
					c='e';
					break;
				case 'ï':
				case 'î':
				case 'ì':
				case 'í':
					c='i';
					break;
				case 'ö':
				case 'ô':
				case 'ò':
				case 'ó':
					c='o';
					break;
				case 'ü':
				case 'û':
				case 'ù':
				case 'ú':
					c='u';
					break;
				case '\'':
				case ' ':
				case '+':
				case '&':
				default:
					if (c>='0' && c<='9')
						break;
					if (c>='a' && c<='z')
						break;
					if (c>='A' && c<='Z')
						break;
					c='_';
			}
			standard.append(c);
		}

//********** Checks that only one dot remains: the one of the extension *************
		n=standard.lastIndexOf(".");
		while(n>0){
			n--;
			n=standard.lastIndexOf(".",n);
			if (n>0)
				standard.replace(n,n+1,"_");
		}

//************************** Returns the standarized file name **********************
		return standard.toString();
	}	

//************************************************************************************
//**               Delete a file
//************************************************************************************
	
	/**
	 * Delete the file targeted by the filePath
	 * @param String filePath
	 * @return Boolean isDeleted
	 * !!! the method returns false if :
	 *     - the filePath targets a folder
	 *     - the file does not exist 
	 *     - the file is locked
	 */
	public static boolean deleteFile(String filePath) {
		
		File fileToDelete = new File(filePath);
		
		if (!fileToDelete.isFile()) return false;
		
		return fileToDelete.delete();
	}
}
