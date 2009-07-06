Publicitas Naca readme.txt version 1.0.0.

0. Contacts

Please use the following contact information for details:
Web site: technology.publicitas.com
email: naca-contact@publicitas.com

Google group: http://groups.google.com/group/naca-automated-cobol-to-java-transcoding

See Changes.txt file for recent updates.

1. Licencing

Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
JLib and NacaRT are licensed under LGPL (LGPL-LICENSE.txt) license.
NacaRTTests and NacaTrans are licensed under GPL (GPL-LICENSE.txt) license.

2. Documentation

2.1. Readme.txt

It's this file !

2.2. Whitepaper

Launch your favorite browser on doc/WhitePaper/NacaWhitePaper.htm to access Naca's whitepaper. 

2.3. Details on Java libraries and operations: JLib / NacaRT

Launch your favorite browser on doc/doc/JLib_NacaRT/index.html for implementation details of those two critical libraries.

2.4. Details on Transcoder: NacaTrans

Transcoder usage and implementation is not publicly documented yet. Please send an email at naca-contact@publicitas.com to get futher details.
However, NacaTrans transcoder source code is provided in the NacaTrans project.

3. Installation

- Install Sun JDK version 1.6.
- Instal Eclipse version >= 3.1.
- Download the package naca.zip.
- Unzip it in some directory. Configuration files are setup to D:\Dev\naca for windows XP computer, thus it's easier to use this path.
- Lauch Eclispe IDE.
- Import Projects in Eclipe:
	- D:\Dev\naca\JLib: JLib is Publicitas's Base java library
	- D:\Dev\naca\NacaRT: NacaRT, is NacaRuntime, runtime library for all Cobol transcoded programs
	- D:\Dev\naca\NacaRTTests: NacaRTTests implements some unit program source files; used for testing Cobol verbs
	- D:\Dev\naca\NacaTrans: NacaTrans is Naca's transcoder: Cobol to Java transcoder.
- Build all 4 projects. A bin directory should be created in every projet; it contains the compiled byte code files.

4. Test

4.1 Create a launch configuration

At this step, we will run a sample program written using the transcoded notation. it's run through NacaRT's own main function.

In  Eclispe, select "Run" menu option "Open debug dialog".
Right click "Java application in the left pane. Select "New", and set it's name to "NacaRTTests".
Then fill the following settings:
	- Project: NacaRT
	- Main class: idea.entryPoint.OnlineMain
	- Program arguments: 
		-Path=D:\Dev\naca\NacaRTTests\bin  
		-ConfigFile=D:\Dev\naca\NacaRTTests\Main\nacaRT.cfg
		-Program=HelloWorld
		-DisableInitialDbConnection
Save the configuration by clicking "Apply".

The arguments provided means:
-Path: Path where .class files are compiled.
-ConfigFile: path and file name of the configuration file.
-Program: class Main of the program to run.
-DisableInitialDbConnection: disable initial DB connection.

The configuation file is stored in D:\Dev\naca\NacaRTTests\Main\nacaRT.cfg.
It's an XML file that contains various settings. The log configuration is given by the parameter "LogSettingsPathFile".

You can of course adjust these files path at your convenience.

4.2 Launch debug configuration

Launch it in debug Mode (click "Debug").

Select the Eclispe console output. It will display something like:
Naca>2008/07/09 11:52:19 (main): ImportantEvent:Beginning to cache 0 resources files from D:/Dev/naca/Resources/
Naca>2008/07/09 11:52:19 (main): NormalEvent:LoadResourceCache Unique XML files loaded=0; XML from jar resource=0; Total load Time (ms)=0
Naca>2008/07/09 11:52:19 (main): ImportantEvent:Db connection error: DbConnectionException: Required property "serverName" is unknown host
Naca>2008/07/09 11:52:19 (main): NormalEvent:Loading program instance:HelloWorld
Naca>2008/07/09 11:52:20 (main): LogDisplay:Hello world ; count=000
Naca>2008/07/09 11:52:20 (main): LogDisplay:Hello world ; count=001
Naca>2008/07/09 11:52:20 (main): LogDisplay:Hello world ; count=002
Naca>2008/07/09 11:52:20 (main): LogDisplay:Hello world ; count=003
Naca>2008/07/09 11:52:20 (main): LogDisplay:Hello world ; count=004
Naca>2008/07/09 11:52:20 (main): LogDisplay:Hello world ; count=005
Naca>2008/07/09 11:52:20 (main): LogDisplay:Hello world ; count=006
Naca>2008/07/09 11:52:20 (main): LogDisplay:Hello world ; count=007
Naca>2008/07/09 11:52:20 (main): LogDisplay:Hello world ; count=008
Naca>2008/07/09 11:52:20 (main): LogDisplay:Hello world ; count=009

The Db connection error is normal: no correct db connection string has been provided.

You can set breakpoints in HelloWorld java file (D:\Dev\naca\NacaRTTests\CobolLikeSupport) line 24 and 46, and check variables' value.
You will probably have to edit source lookup path:
- click Edit Source Lookup Path button
- in dialog box, click "Add"
- Select "Java project", and click "Ok"
- Check NacaRTTests, and click "Ok"
- Finally, click "Ok". The break point line should diplay correctly within the program's procedureDivision() method.

