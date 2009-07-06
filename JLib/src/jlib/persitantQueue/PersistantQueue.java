/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package jlib.persitantQueue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;

import jlib.misc.FileSystem;
import jlib.misc.NumberParser;
import jlib.xml.Tag;


/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: PersistantQueue.java,v 1.2 2007/02/08 06:26:40 u930di Exp $
 */

/*
 * 
 * Sample usage
 
 		PersistantQueue p = new PersistantQueue("d:/Dev/JLibTests/TestQueue/Queue");
		String cs = (String)p.getFirst();
		cs = (String)p.getLast();
		cs = (String)p.getFirst();
		cs = (String)p.getLast();
		cs = (String)p.getFirst();
		
//		p.addLast("a1");
//		p.addLast("a2");
//		p.addLast("a3");
//		p.addLast("a4");
//		p.addLast("a5");
//		p.addLast("a6");
//		p.addLast("a7");
//		p.addLast("a8");
//		p.addLast("a9");
//		p.addLast("b1");
//		p.addLast("b2");

		
		cs = (String)p.getFirst();
		cs = (String)p.getFirst();
		p.addLast("b1");
		cs = (String)p.getFirst();
		cs = (String)p.getFirst();
		cs = (String)p.getFirst();
		
		p.addLast("c1");
		cs = (String)p.getFirst();
		cs = (String)p.getFirst();
		cs = (String)p.getFirst();
		cs = (String)p.getFirst();
*/

// Persitent thread safe queue; can be used as a stack or a queue.
public class PersistantQueue
{
	private String m_csDir = null;
	private String m_csIndexFile = null;
	
	public PersistantQueue(String csDir)
	{
		m_csDir = csDir;
		if(!m_csDir.endsWith("/"))
			m_csDir += '/';
		
		m_csIndexFile = m_csDir + "index.qdx";  
		buildIndexes();
	}
	
	public synchronized void addLast(Serializable object)
	{
		int nLastMax = incMaxIndex();
		
		String csFileName = m_csDir + nLastMax + ".q";
		write(csFileName, object);
	}
	
	public synchronized void addLast(Tag tag)
	{
		int nLastMax = incMaxIndex();
		
		String csFileName = m_csDir + nLastMax + ".q";
		String cs = tag.exportToString();
		write(csFileName, cs);
	}
	
	public synchronized Object getFirst()
	{
		Object o = null;
		while(o == null)
		{
			int nLastMin = incMinIndex();
			if(nLastMin == -1)	// Empty queue
				return null;
			String csFileName = m_csDir + nLastMin + ".q";
			
			o = read(csFileName);
		}		
		return o;
	}
	
	public synchronized Tag getFirstAsTag()
	{
		boolean b = false;
		while(!b)
		{
			int nLastMin = incMinIndex();
			if(nLastMin == -1)	// Empty queue
				return null;
			String csFileName = m_csDir + nLastMin + ".q";
			
			String csTag = (String)read(csFileName);
			if(csTag != null)
			{
				Tag tagItem = new Tag(); 
				b = tagItem.loadFromString(csTag);
				if(b)
					return tagItem;
			}
		}		
		return null;
	}
	
	public synchronized Object getLast()
	{
		Object o = null;
		while(o == null)
		{
			int nLastMax = decMaxIndex();
			if(nLastMax == -1)	// Empty queue
				return null;
			String csFileName = m_csDir + nLastMax + ".q";
			
			o = read(csFileName);
		}		
		return o;
	}
	
	public synchronized Object getLastAsTag()
	{
		Object o = null;
		while(o == null)
		{
			int nLastMax = decMaxIndex();
			if(nLastMax == -1)	// Empty queue
				return null;
			String csFileName = m_csDir + nLastMax + ".q";
			
			o = read(csFileName);
		}		
		return (Tag)o;
	}
	
	public synchronized Object getFirst(BaseQueueItemFactory baseQueueItemFactory)
	{
		Object o = null;
		while(o == null)
		{
			int nLastMin = incMinIndex();
			if(nLastMin == -1)	// Empty queue
				return null;
			String csFileName = m_csDir + nLastMin + ".q";
			
			o = read(csFileName, baseQueueItemFactory);
		}		
		return o;
	}
	
	public synchronized Object getLast(BaseQueueItemFactory baseQueueItemFactory)
	{
		Object o = null;
		while(o == null)
		{
			int nLastMax = decMaxIndex();
			if(nLastMax == -1)	// Empty queue
				return null;
			String csFileName = m_csDir + nLastMax + ".q";
			
			o = read(csFileName);
		}		
		return o;
	}
	
	// index file contains a string :folling format [min index : max index[; the max index is the highest file name + 1
	// If the queue is empty, then min index == max index
	// The index file is rebuild in ctor
	private synchronized void buildIndexes()
	{
		int nMin = 0x7fffffff;
		int nMax = 0x80000000;
		
		FileSystem.createPath(m_csDir);
		String tcsNames[] = FileSystem.getFileNameListBySuffix(m_csDir, ".q");
		if(tcsNames == null)
			nMin = nMax = 0;
		else if(tcsNames.length == 0)
			nMin = nMax = 0;
		else
		{
			for(int n=0; n<tcsNames.length; n++)
			{
				String csFullName = tcsNames[n];
				String csName = FileSystem.getNameWithoutExtension(csFullName);
				int nName = NumberParser.getAsInt(csName);
				if(nName < nMin)
					nMin = nName;
				if(nName > nMax)
					nMax = nName;
			}
			nMax++;
		}
		
		RandomAccessFile fileIndex;
		try
		{
			fileIndex = new RandomAccessFile(m_csIndexFile, "rw");
			String csLine = "" + nMin + ":" + nMax;
				
			byte tb[] = csLine.getBytes();
			fileIndex.seek(0);
	
			fileIndex.write(tb);
			fileIndex.setLength(csLine.length());
			fileIndex.close();
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
	}
	
	private int incMaxIndex()
	{
		int nMin = 0x4000000;
		int nMax = 0x4000000;
		try
		{
			RandomAccessFile fileIndex = new RandomAccessFile(m_csIndexFile, "rw");
			String csLine = fileIndex.readLine();
			if(csLine != null)
			{
				int nSep = csLine.indexOf(':');
				if(nSep >= 0)
				{
					String csMin = csLine.substring(0, nSep);
					String csMax = csLine.substring(nSep+1);
					nMin = NumberParser.getAsInt(csMin); 
					nMax = NumberParser.getAsInt(csMax);
				}
			}
			fileIndex.seek(0);

			String csMin = Integer.toString(nMin);
			String csMax = Integer.toString(nMax+1);

			csLine = csMin + ":" + csMax;
			
			byte tb[] = csLine.getBytes();
			fileIndex.write(tb);
			fileIndex.setLength(csLine.length());
			
			fileIndex.close();

			return nMax;
		}
		catch (FileNotFoundException e)
		{
			return -1;
		}
		catch (IOException e)
		{
			return -1;
		}		
	}

	private int incMinIndex()
	{
		int nMin = 0x4000000;
		int nMax = 0x4000000;
		try
		{
			RandomAccessFile fileIndex = new RandomAccessFile(m_csIndexFile, "rw");
			String csLine = fileIndex.readLine();
			if(csLine != null)
			{
				int nSep = csLine.indexOf(':');
				if(nSep >= 0)
				{
					String csMin = csLine.substring(0, nSep);
					String csMax = csLine.substring(nSep+1);
					nMin = NumberParser.getAsInt(csMin); 
					nMax = NumberParser.getAsInt(csMax);
				}
			}
			String csMin;
			String csMax;

			if(nMin >= nMax)		// Empty queue
			{
				csMin = "0";	// Same value
				csMax = "0";
				nMin = -1;
			}
			else
			{
				csMin = Integer.toString(nMin+1);
				csMax = Integer.toString(nMax);
			}
	
			csLine = csMin + ":" + csMax;
				
			byte tb[] = csLine.getBytes();
			fileIndex.seek(0);
			fileIndex.write(tb);
			fileIndex.setLength(csLine.length());
			fileIndex.close();
			
			return nMin;
		}
		catch (FileNotFoundException e)
		{
			return -1;
		}
		catch (IOException e)
		{
			return -1;
		}		
	}
	
	private int decMaxIndex()
	{
		int nMin = 0x4000000;
		int nMax = 0x4000000;
		try
		{
			RandomAccessFile fileIndex = new RandomAccessFile(m_csIndexFile, "rw");
			String csLine = fileIndex.readLine();
			if(csLine != null)
			{
				int nSep = csLine.indexOf(':');
				if(nSep >= 0)
				{
					String csMin = csLine.substring(0, nSep);
					String csMax = csLine.substring(nSep+1);
					nMin = NumberParser.getAsInt(csMin); 
					nMax = NumberParser.getAsInt(csMax);
				}
			}
			String csMin;
			String csMax;

			if(nMin >= nMax)		// Empty queue
			{
				csMin = "0";	// Same value
				csMax = "0";
				nMax = -1;
			}
			else
			{
				csMin = Integer.toString(nMin);
				csMax = Integer.toString(nMax-1);
			}
	
			csLine = csMin + ":" + csMax;
				
			byte tb[] = csLine.getBytes();
			fileIndex.seek(0);
			fileIndex.write(tb);
			fileIndex.setLength(csLine.length());
			fileIndex.close();
			
			return nMax;
		}
		catch (FileNotFoundException e)
		{
			return -1;
		}
		catch (IOException e)
		{
			return -1;
		}		
	}
	
	private boolean write(String csFileName, Object o)
	{
		try
		{
			FileOutputStream fileOutput = new FileOutputStream(csFileName, false);
			ObjectOutputStream fileOut;
			fileOut = new ObjectOutputStream(fileOutput);

			fileOut.writeObject(o);
			
			fileOut.close();
			fileOutput.close();
			
			return true;
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}	
	}
	
	private synchronized Object read(String csFileName)
	{
		try
		{
			// Read data
			FileInputStream fileInput = new FileInputStream(csFileName);
			ObjectInputStream fileIn = new ObjectInputStream(fileInput);

			Object o = fileIn.readObject();
			
			fileIn.close();
			fileInput.close();
			
			// Remove file			
			File file = new File(csFileName);
			file.delete();
			
			return o;
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			return null;
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			return null;
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			return null;
		}			
	}

	private synchronized Object read(String csFileName, BaseQueueItemFactory baseQueueItemFactory)
	{
		try
		{
			// Read data
			FileInputStream fileInput = new FileInputStream(csFileName);
			ObjectInputStream fileIn = new ObjectInputStream(fileInput);

			Object o = baseQueueItemFactory.read(fileIn);
			
			fileIn.close();
			fileInput.close();
			
			// Remove file			
			File file = new File(csFileName);
			file.delete();
			
			return o;
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			return null;
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			return null;
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			return null;
		}			
	}

	
}
