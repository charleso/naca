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
/**
 * 
 */
package jlib.misc;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id$
 */
public class MarkerFile
{
	private String m_csMakerPath = null;
	private FileLock m_outLock = null;
	private BufferedOutputStream m_out = null;
	
	public MarkerFile(String csMakerPath)
	{
		m_csMakerPath = csMakerPath;
	}
	
	public boolean exclusiveLockFile()
	{
		try
		{
			FileOutputStream fileOutput = new FileOutputStream(m_csMakerPath, false);
			m_out = new BufferedOutputStream(new DataOutputStream(fileOutput));
			FileChannel outChannel = fileOutput.getChannel();
			try
			{
				m_outLock = outChannel.lock();
			}
			catch(IOException e)
			{
				return false;	
			}
			return true;
		}
		catch (FileNotFoundException e)
		{
			//Logger.error("Marker file " + m_csMakerPath + " doesn't exists and thus cannot be exclivilly locked !");
			return false;
		} 
	}
	
	public boolean unlockFile()
	{
		try
		{
			if(m_out != null)
			{
				if(m_outLock != null)
				{
					m_outLock.release();
					m_outLock = null;
				}
				m_out.close();
				m_out = null;
				return true;
			}
		}
		catch (IOException e)
		{
			return false;
		}
		return false;
	}
}
