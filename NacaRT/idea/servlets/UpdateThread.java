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
package idea.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

import jlib.misc.RunSystemCommand;
import jlib.misc.Time_ms;
import nacaLib.basePrgEnv.BaseResourceManager;

public class UpdateThread extends Thread
{
	public void run()
	{
		String csTomcatStartCommand = BaseResourceManager.getTomcatStartCommand();
		if (csTomcatStartCommand == null || csTomcatStartCommand.equals("")) return;
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 4);
		BaseResourceManager.setUpdateTime(cal.getTime());
		Time_ms.wait_ms(4 * 60 * 1000); // 4 minutes
		BaseResourceManager.setUpdateMode(true);
		cal.add(Calendar.MINUTE, 1);
		BaseResourceManager.setUpdateTime(cal.getTime());
		Time_ms.wait_ms(1 * 30 * 1000); // 30 secondes
		
		// restart tomcat
		runSystemCommand(csTomcatStartCommand);
		
		BaseResourceManager.setUpdateMode(false);
		BaseResourceManager.setUpdateTime(null);
	}
	
	private boolean runSystemCommand(String command) {
		try
		{
			Process proc = RunSystemCommand.Launch(command);
			boolean haserror = false ;
			BufferedReader output = new BufferedReader(new InputStreamReader(proc.getInputStream())) ;
			BufferedReader err = new BufferedReader(new InputStreamReader(proc.getErrorStream())) ;
			boolean bProcessEnded = false ;
			do
			{
				while (output.ready())
				{
					System.out.println(output.readLine().trim());
				}
				while (err.ready())
				{
					System.out.println(err.readLine().trim());
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

	private boolean isTerminated(Process proc)
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
