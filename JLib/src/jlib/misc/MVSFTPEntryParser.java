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

import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.parser.ConfigurableFTPFileEntryParserImpl;

public class MVSFTPEntryParser extends ConfigurableFTPFileEntryParserImpl
{  
    private static final String REGEX = "(.*)\\s+([^\\s]+)\\s*";
	
    static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd"; // 2001/11/09
    
    public MVSFTPEntryParser()
    {
        super(REGEX);
    }

    public FTPFile parseFTPEntry(String entry)
    {       
        FTPFile f = null;
        if (matches(entry))          
        {
            f = new FTPFile();
            String group = group(1);            
            String dataSetName = group(2);
            f.setGroup(group);
            f.setType(FTPFile.FILE_TYPE);
			f.setName(dataSetName);

			return (f);
        }
        return null;
    }

    protected FTPClientConfig getDefaultConfiguration() {
        return new FTPClientConfig(
                FTPClientConfig.SYST_MVS,
                DEFAULT_DATE_FORMAT,
                null, null, null, null);
    }
}