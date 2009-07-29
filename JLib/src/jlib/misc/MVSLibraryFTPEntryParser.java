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
//
public class MVSLibraryFTPEntryParser extends ConfigurableFTPFileEntryParserImpl
{  
    private static final String REGEX = "(.*)";
	
    static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd"; // 2001/11/09
    
    public MVSLibraryFTPEntryParser()
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
            String dataSetName = group;
            if (group != null && group.length() > 8)
            	dataSetName = group(1).substring(0, 8).trim();
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