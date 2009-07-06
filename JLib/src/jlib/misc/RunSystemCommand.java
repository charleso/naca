/*
 * JLib - Publicitas Java library.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
package jlib.misc;


import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
* @author wharfie
* 2004-02-27
* Run a system command 
*/
public class RunSystemCommand 
{
	public static BufferedReader run(String command, String[] args, String directory) 
	{
		try 
		{
			Process proc = Launch(command, args, directory) ;
			int i = proc.waitFor();
			if (i == 0)
			{
				BufferedReader stdOutput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
				return stdOutput ;
			}
			else 
			{
				BufferedReader stdErr = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
				return stdErr ;
			}
		}
		catch (Exception e) 
		{
			System.out.println(e);
		}
		return null ;
	}
	
	public static Process Launch(String commandLine) 
	{
		try 
		{
			Process proc = Runtime.getRuntime().exec(commandLine);
			return proc ;
		}
		catch (Exception e) 
		{
			System.out.println(e);
		}
		return null ;
	}
	
	public static Process Launch(String command, String[] args) 
	{
		String[] com = new String[args.length+1] ;
		com[0] = command ;
		for (int i=0; i<args.length; i++)
		{
			com[i+1] = args[i] ;
		}
		return Launch(com) ;
	}
	
	public static Process Launch(String command, String[] args, String directory) 
	{
		return Launch(command, args, new File(directory)) ;
	}

	public static Process Launch(String command, String[] args, File directory) 
	{
		String[] com = new String[args.length+1] ;
		com[0] = command ;
		for (int i=0; i<args.length; i++)
		{
			com[i+1] = args[i] ;
		}
		return Launch(com, directory) ;
	}
	
	public static Process Launch(String[] commandAndArgs) 
	{
		try 
		{
			Process proc = Runtime.getRuntime().exec(commandAndArgs);
			return proc ;
		}
		catch (Exception e) 
		{
			System.out.println(e);
		}
		return null ;
	}

	public static Process Launch(String[] commandAndArgs, File workDir) 
	{
		try 
		{
			Process proc = Runtime.getRuntime().exec(commandAndArgs, null, workDir);
			return proc ;
		}
		catch (Exception e) 
		{
			System.out.println(e);
		}
		return null ;
	}
	
	public static Process Launch(String commandLine, String directory) 
	{
		File workDir = new File(directory);
		try 
		{
			Process proc = Runtime.getRuntime().exec(commandLine, null, workDir);
			return proc ;
		}
		catch (Exception e) 
		{
			System.out.println(e);
		}
		return null ;
	}

	/**
	 * @param task
	 */
	public static Process Launch(ProcessExecutionTask task)
	{
		String args[] = new String[task.m_arrParameters.size()];
		for (int i=0; i<task.m_arrParameters.size(); i++)
		{
			args[i] = task.m_arrParameters.get(i) ;
		}
		return Launch(task.m_csCommand, args, task.m_dirRuntimeDir) ;
	}
	
	public static boolean runSystemCommand(String command, String[] args)
	{
		try
		{
			Process proc = RunSystemCommand.Launch(command, args);
			boolean haserror = false ;
			BufferedReader output = new BufferedReader(new InputStreamReader(proc.getInputStream())) ;
			BufferedReader err = new BufferedReader(new InputStreamReader(proc.getErrorStream())) ;
			boolean bProcessEnded = false ;
			do
			{
				while (output.ready())
				{
					String csLine = output.readLine().trim();
					//System.out.println(csLine);
				}
				while (err.ready())
				{
					String csLine = err.readLine().trim();
					System.out.println(csLine);
					haserror = true ;
				}

				bProcessEnded = isTerminated(proc) ;
				if (!bProcessEnded)
				{
					try	{
						Thread.sleep(1000) ;// 1s
					} catch (InterruptedException e){
						break ;
					}
				}
			}
			while (!bProcessEnded || err.ready() || output.ready()) ;
			output.close();
			err.close();
			if (haserror)
			{
				return false;
			}
		}
		catch (IOException e)
		{	
			return false;
		}
		return true;
	}
	
	public static boolean isTerminated(Process proc)
	{
		try
		{
			proc.exitValue();
			return true ;
		}
		catch (IllegalThreadStateException e)
		{
			return false ;
		}
	}
}
