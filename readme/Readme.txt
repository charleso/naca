Publicitas Naca readme.txt version 1.0.0.

0. Contacts

Please use the following contact information for details:
Web site: technology.publicitas.com
email: naca-contact@publicitas.com

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

- Install Sun JDK version 1.6 (http://java.sun.com/javase/downloads/index.jsp), select JDK.
- Instal Eclipse version >= 3.1.	
- Download the package naca.x.x.x.zip.
- Unzip it in some directory. Configuration files are setup by default to D:\Dev\nacaOSS for windows XP computer, thus it's easier to use this path.
If you need to install in some other directory, please edit Path tag of NacaRT.cfg and NacaTrans*.cfg.
These various configuration files are located there:
	<Your root installation path>/NacaTrans/NacaTrans.cfg	
	<Your root installation path>/NacaSamples/trans/NacaTransSamples.cfg
	<Your root installation path>/NacaSamples/NacaRT.cfg
	<Your root installation path>/NacaRTTests/Main/nacaRT.cfg
	<Your root installation path>/NacaRT/idea/web/WEB-INF/nacaRT.cfg
	<Your root installation path>/NacaRT/idea/entryPoint/nacaRT.cfg
	
Edit every liste file, and replace the value of the Value argument of tag Path, replacing d:/dev/nacaOSS by your root installation directory
The structure of this tag is like:
	<Paths>
		<Path Name="%DefaultPath%" Value="d:/dev/nacaOSS" />
	</Paths>

- If you haven't installed Eclispe, you can download Eclispe Windows Europa version at http://www.eclipse.org/downloads/moreinfo/java.php
This version of eclipse is required for Sysdeo pulgin integration used by Online debug operations. 
If you do not use Online programs, then the most current version of Eclipse can be used.	

- Install Eclispe
- Lauch Eclispe IDE
- Check that Projects are built automatically (in Project Menu, Option Build Automatically must be checked).
- Import Projects in Eclipe in this order:
	- <Your root installation path>\JLib: JLib is Publicitas's Base java library
	- <Your root installation path>\NacaRT: NacaRT, is NacaRuntime, runtime library for all Cobol transcoded programs
	- <Your root installation path>\NacaSamples: Online and batch sample
	- <Your root installation path>\NacaRTTests: NacaRTTests implements some unit program source files; used for testing Cobol verbs
	- <Your root installation path>\NacaTrans: NacaTrans is Naca's transcoder: Cobol to Java transcoder.
	To import a project, Select Package Explorer view in Window / Show View.
	Then right click the mouse, and select Import option from context menu.
	Select "Existing Projects Into Workspace", then Click "Next"
	Fill the "Select root directory" entry by clicking on the "Browse" button.
	Select in yout file system the project to import (for exmaple, D:\Dev\nacaOSS\JLib)and validate by pushing OK button.
	Terminate project import by clicking "Finish" button.

- Build all 5 projects (it's automatic if the Build Automatically option of Projects menu is checked. A bin directory should be created in every projet; it contains the compiled byte code files.
- The "Problems" view shouldn't display any error.

4. Tests

4.1 Transcode NacaSamples

4.1.1 Create a transcode profile

In menu "Run", Select option "Debug Configuration"
Right click "Java Application", and select "New" in the popup menu.
Fill the form on the right panel.
Select tab "Main".
Enter "Trancode NacaSamples" in "Name" field.
Set "NacaTrans" in "Project" field.
Set "NacaTrans" in "Main class" field.
Select tab "Arguments"
Set <Your root installation path>\NacaSampels\trans\NacaTransSamples.cfg in the "Program Arguments" field.
Of course, the <Your root installation path> must be replaced the the root path where you unzipped the naca.x.x.x.zip package.
If you installed in recommended path, it's d:\dev\NacaOSS. in that case, you must set "d:\dev\NacaOSS\NacaSamples\trans\NacaTransSamples.cfg" in the "Program Arguments" field.
Then, you can launch the transcoder by clicking "Debug".

Select the Console View.
It should show this dump lines:
NacaTrans:2009-07-25 12:03:58,375 - 0001[Info] %%- Starting NacaTrans Version 1.2.11 
NacaTrans:2009-07-25 12:03:58,375 - 0002[Info] %%- Init rules... 
NacaTrans:2009-07-25 12:03:58,390 - 0003[Info] %%- Loading DCLGEN converter settings ... 
NacaTrans:2009-07-25 12:03:58,390 - 0004[Info] %%- Init Engines... 
NacaTrans:2009-07-25 12:03:58,421 - 0005[Info] %%- Do CSD Registering... 
NacaTrans:2009-07-25 12:03:58,421 - 0006[Info] %%- Init Global Entities... 
NacaTrans:2009-07-25 12:03:58,765 - 0007[Info] %%- Loading paths... 
NacaTrans:2009-07-25 12:03:58,765 - 0008[Info] %%- Init global objects... 
NacaTrans:2009-07-25 12:03:58,765 - 0009[Info] %%- Loading SQLSyntax converter settings ... 
NacaTrans:2009-07-25 12:03:58,765 - 0010[Info] %%- Do Applications... 
NacaTrans:2009-07-25 12:03:58,781 - 0011[Info] %d:/dev/nacaOSS/NacaSamples/cobol/ONLINE1%- Start transcoding top level file from d:/dev/nacaOSS/NacaSamples/cobol/ONLINE1 to d:/dev/nacaOSS/NacaSamples/src/online/ONLINE1.java 
NacaTrans:2009-07-25 12:03:58,843 - 0012[Info] %d:/dev/nacaOSS/NacaSamples/cobol/ONLINE1%- Transcoding ONLINE1 
NacaTrans:2009-07-25 12:03:59,265 - 0013[Info] %d:/dev/nacaOSS/NacaSamples/cobol/ONLINE1%- Transcoding ONLINM1 
NacaTrans:2009-07-25 12:03:59,359 - 0014[Info] %d:/dev/nacaOSS/NacaSamples/cobol/include/MSGZONE%- Transcoding MSGZONE 
NacaTrans:2009-07-25 12:03:59,437 - 0015[Info] %d:/dev/nacaOSS/NacaSamples/cobol/include/VTBMSGA%- Transcoding VTBMSGA 
NacaTrans:2009-07-25 12:03:59,484 - 0016[Info] %d:/dev/nacaOSS/NacaSamples/cobol/include/TUAZONE%- Transcoding TUAZONE 
NacaTrans:2009-07-25 12:03:59,609 - 0017[Info] %d:/dev/nacaOSS/NacaSamples/cobol/ONLINE1(24)%- Unused EntityAttribute IND1; it won't be generated 
NacaTrans:2009-07-25 12:03:59,609 - 0018[Info] %d:/dev/nacaOSS/NacaSamples/cobol/ONLINE1(24)%- Unused EntityAttribute IND1; it won't be generated 
NacaTrans:2009-07-25 12:03:59,609 - 0019[Info] %d:/dev/nacaOSS/NacaSamples/cobol/ONLINE1(25)%- Unused EntityAttribute IND-LNG; it won't be generated 
NacaTrans:2009-07-25 12:03:59,609 - 0020[Info] %d:/dev/nacaOSS/NacaSamples/cobol/ONLINE1(25)%- Unused EntityAttribute IND-LNG; it won't be generated 
NacaTrans:2009-07-25 12:03:59,609 - 0021[Info] %d:/dev/nacaOSS/NacaSamples/cobol/ONLINE1(26)%- Unused EntityAttribute LONGCAR; it won't be generated 
NacaTrans:2009-07-25 12:03:59,609 - 0022[Info] %d:/dev/nacaOSS/NacaSamples/cobol/ONLINE1(26)%- Unused EntityAttribute LONGCAR; it won't be generated 
NacaTrans:2009-07-25 12:03:59,609 - 0023[Info] %d:/dev/nacaOSS/NacaSamples/cobol/ONLINE1(27)%- Unused EntityAttribute LONGERR; it won't be generated 
NacaTrans:2009-07-25 12:03:59,609 - 0024[Info] %d:/dev/nacaOSS/NacaSamples/cobol/ONLINE1(27)%- Unused EntityAttribute LONGERR; it won't be generated 
NacaTrans:2009-07-25 12:03:59,609 - 0025[Info] %d:/dev/nacaOSS/NacaSamples/cobol/ONLINE1(28)%- Unused EntityAttribute LONGPLAUS; it won't be generated 
NacaTrans:2009-07-25 12:03:59,609 - 0026[Info] %d:/dev/nacaOSS/NacaSamples/cobol/ONLINE1(28)%- Unused EntityAttribute LONGPLAUS; it won't be generated 
NacaTrans:2009-07-25 12:03:59,609 - 0027[Info] %d:/dev/nacaOSS/NacaSamples/cobol/ONLINE1(29)%- Unused EntityAttribute LONGSMAP; it won't be generated 
NacaTrans:2009-07-25 12:03:59,609 - 0028[Info] %d:/dev/nacaOSS/NacaSamples/cobol/ONLINE1(29)%- Unused EntityAttribute LONGSMAP; it won't be generated 
NacaTrans:2009-07-25 12:03:59,609 - 0029[Info] %d:/dev/nacaOSS/NacaSamples/cobol/ONLINE1(31)%- Unused EntityAttribute DATANAME; it won't be generated 
NacaTrans:2009-07-25 12:03:59,609 - 0030[Info] %d:/dev/nacaOSS/NacaSamples/cobol/ONLINE1(31)%- Unused EntityAttribute DATANAME; it won't be generated 
NacaTrans:2009-07-25 12:03:59,609 - 0031[Info] %d:/dev/nacaOSS/NacaSamples/cobol/ONLINE1(32)%- Unused EntityAttribute W-CICS; it won't be generated 
NacaTrans:2009-07-25 12:03:59,609 - 0032[Info] %d:/dev/nacaOSS/NacaSamples/cobol/ONLINE1(32)%- Unused EntityAttribute W-CICS; it won't be generated 
NacaTrans:2009-07-25 12:03:59,609 - 0033[Info] %d:/dev/nacaOSS/NacaSamples/cobol/ONLINE1(33)%- Unused EntityAttribute HEX80; it won't be generated 
NacaTrans:2009-07-25 12:03:59,609 - 0034[Info] %d:/dev/nacaOSS/NacaSamples/cobol/ONLINE1(33)%- Unused EntityAttribute HEX80; it won't be generated 
NacaTrans:2009-07-25 12:03:59,609 - 0035[Info] %d:/dev/nacaOSS/NacaSamples/cobol/ONLINE1(34)%- Unused EntityAttribute ECR-VIERGE; it won't be generated 
NacaTrans:2009-07-25 12:03:59,609 - 0036[Info] %d:/dev/nacaOSS/NacaSamples/cobol/ONLINE1(34)%- Unused EntityAttribute ECR-VIERGE; it won't be generated 
NacaTrans:2009-07-25 12:03:59,609 - 0037[Info] %d:/dev/nacaOSS/NacaSamples/cobol/ONLINE1(38)%- Unused EntityAttribute W-CDCENPI; it won't be generated 
NacaTrans:2009-07-25 12:03:59,609 - 0038[Info] %d:/dev/nacaOSS/NacaSamples/cobol/ONLINE1(38)%- Unused EntityAttribute W-CDCENPI; it won't be generated 
NacaTrans:2009-07-25 12:03:59,828 - 0039[Info] %d:/dev/nacaOSS/NacaSamples/cobol/BATCH1%- Start transcoding top level file from d:/dev/nacaOSS/NacaSamples/cobol/BATCH1 to d:/dev/nacaOSS/NacaSamples/src/batch/BATCH1.java 
NacaTrans:2009-07-25 12:03:59,843 - 0040[Info] %d:/dev/nacaOSS/NacaSamples/cobol/BATCH1%- Transcoding BATCH1 
NacaTrans:2009-07-25 12:03:59,890 - 0041[Info] %d:/dev/nacaOSS/NacaSamples/cobol/include/MSGZONE%- Transcoding MSGZONE 
NacaTrans:2009-07-25 12:04:00,000 - 0042[Info] %d:/dev/nacaOSS/NacaSamples/cobol/CALLMSG%- Start transcoding top level file from d:/dev/nacaOSS/NacaSamples/cobol/CALLMSG to d:/dev/nacaOSS/NacaSamples/src/commons/CALLMSG.java 
NacaTrans:2009-07-25 12:04:00,015 - 0043[Info] %d:/dev/nacaOSS/NacaSamples/cobol/CALLMSG%- Transcoding CALLMSG 
NacaTrans:2009-07-25 12:04:00,046 - 0044[Info] %d:/dev/nacaOSS/NacaSamples/cobol/include/MSGZONE%- Transcoding MSGZONE 
NacaTrans:2009-07-25 12:04:00,109 - 0045[Info] %%- Exporting Infos... 
NacaTrans:2009-07-25 12:04:00,562 - 0046[Info] %%- Done; Errors=0 Warnings=0 

The final line reports that tramscoding is correct.

To see the generated files, right click "NacaSamples" project, and select "Refersh" in the popup menu. This will compile the transcoded programs.
A src directory is displayed. You can watch the transcoded batch sample in src/bacth/BATCH1.java file.

The following directories contains:
- D:\Dev\nacaOSS\NacaSamples\src\batch: containing generated batch java files
- D:\Dev\nacaOSS\NacaSamples\src\online: containing generated online java files
- D:\Dev\nacaOSS\NacaSamples\src\online\resources: containing generated screen resources for online sample
- D:\Dev\nacaOSS\NacaSamples\src\common\include: Contains copy files
- D:\Dev\nacaOSS\NacaSamples\trans\info\ItemCount.html: file giving statistics on various verbs, constructions, …
- D:\Dev\nacaOSS\NacaSamples\trans\stat: Contains temporary files used during transcoding


4.2 Execution of the Batch sample

In Eclispe, create a new debug profile:
In the "Run" menu, select the "Debug configuration" option.
Right-click "Java Application" in the left panel, and select "New" in the popup menu.
Fill "Name" field of the right panel with "NacaSamples - Batch"
In the right panel, the "Main" tab should be selected.
In "Project" Browse button, select "NacaRT".
In "Main class" Browse button, select "BatchMain - nacaLib.batchPrgEnv"

Select "Arguments" tab.

In "Program Arguments", set the following values:
-Program=BATCH1
-ConfigFile=<Your root installation path>\NacaSamples\nacaRT.cfg  
-Path=<Your root installation path>\NacaSamples\bin
-FileIn=<Your root installation path>\NacaSamples\BatchFiles\In\FileIn.data,advancedMode,ascii,fixed,CRLF
-fileOut=<Your root installation path>\NacaSamples\BatchFiles\Out\FileOut.data,advancedMode,ascii,fixed,CRLF

As already stated, <Your root installation path> stands for your installation root directory.

If using defaut root directory, fill:
-Program=BATCH1
-ConfigFile=D:\Dev\nacaOSS\NacaSamples\nacaRT.cfg  
-Path=D:\Dev\nacaOSS\NacaSamples\bin
-FileIn=D:\Dev\nacaOSS\NacaSamples\BatchFiles\In\FileIn.data,advancedMode,ascii,fixed,CRLF
-fileOut=D:\Dev\nacaOSS\NacaSamples\BatchFiles\Out\FileOut.data,advancedMode,ascii,fixed,CRLF

Launch execution by pressing "Debug" button.

Seclection the "Console" view.
It displays the following lines:
Naca>2009/07/26 09:11:58 (main): Version: NacaRT: 1.2.16; JLib: 1.2.9
Naca>2009/07/26 09:12:01 (main): Loading program instance:BATCH1
Naca>2009/07/26 09:12:01 (main): DEBUG - TIME : 0912014
Naca>2009/07/26 09:12:01 (main): DEBUG 1 - 1Record1:abcdefghijklmonpqrstuvwxyzabcdefghijklmonpqrstuvwxyzabcdefgh
Naca>2009/07/26 09:12:01 (main): DEBUG 2 - Problem BATCH                                                                 
Naca>2009/07/26 09:12:01 (main): DEBUG 2 - Problem BATCH                                                                 
Naca>2009/07/26 09:12:01 (main): DEBUG 1 - 1Record4:abcdefghijklmonpqrstuvwxyzabcdefghijklmonpqrstuvwxyzabcdefgh
Naca>2009/07/26 09:12:01 (main): DEBUG 1 - 1Record6:abcdefghijklmonpqrstuvwxyzabcdefghijklmonpqrstuvwxyzabcdefgh
Naca>2009/07/26 09:12:01 (main): DEBUG 2 - Problem BATCH                                                                 
Naca>2009/07/26 09:12:01 (main): DEBUG 2 - Problem BATCH                                                                 
Naca>2009/07/26 09:12:01 (main): DEBUG 2 - Problem BATCH                                                                 
STAT FILEIN  - READ RECORDS   : 0000010
STAT FILEOUT - WRITE RECORDS  : 0000003
FILEOUT (d:\Dev\NacaOSS\NacaSamples\BatchFiles\Out\FileOut.data) Read=0 / Write=3
FILEIN (d:\Dev\NacaOSS\\NacaSamples\BatchFiles\In\FileIn.data) Read=10 / Write=0
Naca>2009/07/26 09:12:01 (main): Total elapsed time (ms): 1236
Naca>2009/07/26 09:12:01 (main): ***** Database I/O *****
Naca>2009/07/26 09:12:01 (main): Sum of all DB IO times: 0 ms (or 0.0 seconds or 0.0 minutes)
Naca>2009/07/26 09:12:01 (main): Number of Prepare: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:12:01 (main): Number of OpenCursor: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:12:01 (main): Number of FetchCursor: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:12:01 (main): Number of CloseResultset: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:12:01 (main): Number of Select: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:12:01 (main): Number of Insert: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:12:01 (main): Number of Update: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:12:01 (main): Number of Delete: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:12:01 (main): Number of CreateTable: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:12:01 (main): Number of DropTable: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:12:01 (main): Number of Declare: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:12:01 (main): Number of Lock: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:12:01 (main): ***** File I/O *****
Naca>2009/07/26 09:12:01 (main): Number of Open: 2; Total time (ms): 7; Max time (ms): 7
Naca>2009/07/26 09:12:01 (main): Number of Close: 2; Total time (ms): 1; Max time (ms): 1
Naca>2009/07/26 09:12:01 (main): Number of Flush: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:12:01 (main): Number of Read: 3; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:12:01 (main): Number of Write: 3; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:12:01 (main): Number of Rewrite: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:12:01 (main): Number of Position: 2; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:12:01 (main): 

The file d:\Dev\NacaOSS\NacaSamples\BatchFiles\Out\FileOut.data has been written with some data extraced from d:\Dev\NacaOSS\NacaSamples\BatchFiles\In\FileIn.data

4.3 Execution of the Online sample



4.4. Execution of a unit test program

At this step, we will run a sample program written using the transcoded notation. it's run through NacaRT's own main function.

In  Eclispe, select "Run" menu option "Debug Configuration".
Right click "Java Application" in the left panel. Select "New", and set it's name to "NacaRTTests".
Then fill the following settings in the "Main" tab:
- Project: NacaRT
- Main class: idea.entryPoint.OnlineMain
In the "Arguments" tab, set the "Program arguments" field with: 
-Path=D:\Dev\NacaOSS\NacaRTTests\bin  
-ConfigFile=D:\Dev\NacaOSS\NacaRTTests\Main\nacaRT.cfg
-Program=HelloWorld
-DisableInitialDbConnection

As usual, replace "D:\Dev\NacaOSS\" with your own root install path if you didn't uses the default installation path.

Save the configuration by clicking "Apply".

The arguments provided means:
-Path: Path where .class files are compiled.
-ConfigFile: path and file name of the configuration file.
-Program: class Main of the program to run.
-DisableInitialDbConnection: disable initial DB connection.

The configuation file is stored in D:\Dev\naca\NacaRTTests\Main\nacaRT.cfg.
It's an XML file that contains various settings. The log configuration is given by the parameter "LogSettingsPathFile".


4.3 Launch debug configuration

Launch execution in debug Mode by clicking "Debug" button.

Select the Eclispe console output. It will display:
Naca>2009/07/26 09:26:50 (main): Beginning to cache 0 resources files from d:/Dev/nacaOSS/Resources/
Naca>2009/07/26 09:26:50 (main): Beginning to cache 1 resources files from d:/Dev/nacaOSS/NacaSamples/src/online/resources/
Naca>2009/07/26 09:26:50 (main): LoadResourceCache Unique XML files loaded=1; XML from jar resource=0; Total load Time (ms)=32
Naca>2009/07/26 09:26:50 (main): Loading program instance:HelloWorld
Naca>2009/07/26 09:26:51 (main): Hello world ; count=000
Naca>2009/07/26 09:26:51 (main): Hello world ; count=001
Naca>2009/07/26 09:26:51 (main): Hello world ; count=002
Naca>2009/07/26 09:26:51 (main): Hello world ; count=003
Naca>2009/07/26 09:26:51 (main): Hello world ; count=004
Naca>2009/07/26 09:26:51 (main): Hello world ; count=005
Naca>2009/07/26 09:26:51 (main): Hello world ; count=006
Naca>2009/07/26 09:26:51 (main): Hello world ; count=007
Naca>2009/07/26 09:26:51 (main): Hello world ; count=008
Naca>2009/07/26 09:26:51 (main): Hello world ; count=009
Naca>2009/07/26 09:26:51 (main): Total elapsed time (ms): 283
Naca>2009/07/26 09:26:51 (main): ***** Database I/O *****
Naca>2009/07/26 09:26:51 (main): Sum of all DB IO times: 0 ms (or 0.0 seconds or 0.0 minutes)
Naca>2009/07/26 09:26:51 (main): Number of Prepare: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:26:51 (main): Number of OpenCursor: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:26:51 (main): Number of FetchCursor: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:26:51 (main): Number of CloseResultset: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:26:51 (main): Number of Select: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:26:51 (main): Number of Insert: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:26:51 (main): Number of Update: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:26:51 (main): Number of Delete: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:26:51 (main): Number of CreateTable: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:26:51 (main): Number of DropTable: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:26:51 (main): Number of Declare: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:26:51 (main): Number of Lock: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:26:51 (main): ***** File I/O *****
Naca>2009/07/26 09:26:51 (main): Number of Open: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:26:51 (main): Number of Close: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:26:51 (main): Number of Flush: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:26:51 (main): Number of Read: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:26:51 (main): Number of Write: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:26:51 (main): Number of Rewrite: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:26:51 (main): Number of Position: 0; Total time (ms): 0; Max time (ms): 0
Naca>2009/07/26 09:26:51 (main): 


The Db connection error is normal: no correct db connection string has been provided.

You can set breakpoints in HelloWorld java file (D:\Dev\naca\NacaRTTests\CobolLikeSupport) at the beginning of the procedure division, and execute step by step.

You will probably have to edit source lookup path:
- click Edit Source Lookup Path button
- in dialog box, click "Add"
- Select "Java project", and click "Ok"
- Check NacaRTTests, and click "Ok"
- Finally, click "Ok". The break point line should diplay correctly within the program's procedureDivision() method.

To dissplay a variable's value, select it for exemaple "counter" at line 39), then select Watch in the popup menu, then select "counter" in the Expression panel.

