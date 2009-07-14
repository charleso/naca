/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/**
 * 
 */
package utils;

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: NacaTransLauncher.java,v 1.3 2007/12/06 07:24:07 u930bm Exp $
 */
public class NacaTransLauncher extends Transcoder
{
	public static void launchMain(String[] args)
	{
		String csCfg = "NacaTrans.cfg" ;
		boolean bCfgSet = false;
		String csGroupToTranscode = "" ;
		String csApplication = null;
		TranscoderAction transcoderAction = TranscoderAction.All; 
		for (int nArg=0; nArg<args.length; nArg++)
		{
			String s = args[nArg];				
			if (s.startsWith("-") || s.startsWith("/"))
			{
				String sArg = s.substring(1);
				String sArgUpper = sArg.toUpperCase();
				if (sArgUpper.startsWith("APPLICATION="))
				{
					csApplication = sArg.substring(12); 
				}
				else if (sArgUpper.startsWith("GROUP="))
				{
					csGroupToTranscode = sArg.substring(6); 
				}
				else if (sArgUpper.startsWith("ACTION="))
				{
					String csAction = sArgUpper.substring(7); 
					transcoderAction = getTranscoderAction(csAction);
				}
			}
			else
			{
				if(!bCfgSet)
				{
					csCfg = args[nArg] ;
					bCfgSet = true;
				}
				else
					csGroupToTranscode = args[nArg] ;
			}
		}
		doStart(csApplication, transcoderAction, csCfg, csGroupToTranscode);
	}

	public static NacaTransLauncher doInitForPlugin(String configFilePath)
	{
		NacaTransLauncher transLauncher = new NacaTransLauncher() ;
		transLauncher.initForPlugin(configFilePath);
		return transLauncher;		
	}

	public static void doStart(String csApplication, TranscoderAction transcoderAction, String csCfg, String csGroupToTranscode)
	{
		NacaTransLauncher obj = new NacaTransLauncher() ;
		obj.setTranscoderAction(transcoderAction);

		obj.Start(csCfg, csGroupToTranscode) ;
	}

}
