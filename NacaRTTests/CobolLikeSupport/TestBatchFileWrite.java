/*
 * NacaRTTests - Naca Tests for NacaRT support v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
import jlib.misc.DataFileLineReader;
import nacaLib.basePrgEnv.BaseSession;
import nacaLib.batchPrgEnv.BatchProgram;
import nacaLib.varEx.DataSection;
import nacaLib.varEx.FileDescriptor;
import nacaLib.varEx.FileDescriptorDepending;
import nacaLib.varEx.Var;

/**
 * 
 */

/**
 *
 * @author Pierre-Jean Ditscheid, Consultas SA
 * @version $Id: TestBatchFileWrite.java,v 1.2 2009/05/15 14:07:25 u930di Exp $
 */
public class TestBatchFileWrite extends BatchProgram  
{
	DataSection ws = declare.workingStorageSection();
	
	Var wLong = declare.level(77).pic9(4).value(0).var() ;
	Var wText = declare.level(77).pic9(4).value(0).var() ;
	
	
	FileDescriptor fileRLarge = declare.file("fileRLarge") ;                        // (250)  FD  COMPINPC
	Var record0 = declare.level(1).picX(84).var() ;                           // (255)  01  RECPINPC                PIC X(550).
 	
	FileDescriptor fileRW = declare.file("fileRW") ;                        // (250)  FD  COMPINPC
		Var record = declare.level(1).var() ;                           // (255)  01  RECPINPC                PIC X(550).
			Var letter = declare.level(5).picX(4).var() ;                           // (255)  01  RECPINPC                PIC X(550).
			Var counter = declare.level(5).pic9(4).var() ;                           // (255)  01  RECPINPC                PIC X(550).

	FileDescriptor fileRWLgVar = declare.file("fileRWLgVar");	//.lengthDependingOn(wLong);
 		Var enregOut = declare.level(1).var() ;
 			Var oConst = declare.level(2).pic9(6).var();
 			Var oNum = declare.level(2).picS9(8).comp().var();
 			Var oNumAsX = declare.level(2).redefines(oNum).picX(4).var();
 			Var oLine = declare.level(2).occursDepending(4000, wLong).picX(1).var();
 			
 	
 	 		
 	FileDescriptorDepending rs03extr$dependency = declare.fileDescriptorDepending(fileRWLgVar, wLong);	


 	FileDescriptor fileFixedCRLF = declare.file("fileFixedCRLF");	
 	 	 Var enregFixedCRLF = declare.level(1).pic9(10).var() ;

 	FileDescriptor fileFixedLF = declare.file("fileFixedLF");	
	 	 Var enregFixedLF = declare.level(1).pic9(10).var() ;
	 	 
	FileDescriptor fileFixed = declare.file("fileFixed");
		Var enregFixed = declare.level(1).var() ;
		 Var enregFixedA = declare.level(5).picX(10).value("abcdefghij").var() ;
	 	 Var enregFixedL = declare.level(5).pic9(10).var() ;

	// Write only
	FileDescriptor fileFixedHeaderCRLF = declare.file("fileFixedHeaderCRLF");	
	 	 Var enregFixedHeaderCRLF = declare.level(1).picX(20).var() ;

	// Write only
	FileDescriptor fileFixedHeaderLF = declare.file("fileFixedHeaderLF");	
	 	 Var enregFixedHeaderLF = declare.level(1).picX(20).var() ;
	 	 
	 	 
	 	 
	FileDescriptor fileVariableCRLF = declare.file("fileVariableCRLF");
 	 	Var enregVariableCRLF = declare.level(1).var() ;
 	 		Var oVariableCRLF = declare.level(2).occursDepending(4000, wText).picX(1).var();
 	FileDescriptorDepending fileVariableCRLF$dependency = declare.fileDescriptorDepending(fileVariableCRLF, wText);
 	
	FileDescriptor fileVariableLF = declare.file("fileVariableLF");
	 	Var enregVariableLF = declare.level(1).var() ;
	 		Var oVariableLF = declare.level(2).occursDepending(4000, wText).picX(1).var();
	FileDescriptorDepending fileVariableLF$dependency = declare.fileDescriptorDepending(fileVariableLF, wText);

	FileDescriptor fileVariableHeaderCRLF = declare.file("fileVariableHeaderCRLF");
	 	Var enregVariableHeaderCRLF = declare.level(1).var() ;
	 		Var oVariableHeaderCRLF = declare.level(2).occursDepending(4000, wText).picX(1).var();
	FileDescriptorDepending fileVariableHeaderCRLF$dependency = declare.fileDescriptorDepending(fileVariableHeaderCRLF, wText);
	
	FileDescriptor fileVariableHeaderLF = declare.file("fileVariableHeaderLF");
	 	Var enregVariableHeaderLF = declare.level(1).var() ;
	 		Var oVariableHeaderLF = declare.level(2).occursDepending(4000, wText).picX(1).var();
	FileDescriptorDepending fileVariableHeaderLF$dependency = declare.fileDescriptorDepending(fileVariableHeaderLF, wText);

 	
 	
 			
 	Var error_Text_Len = declare.level(77).pic9(9).comp().value(100).var() ;    // (93)  77  ERROR-TEXT-LEN         PIC 9(9)  COMP   VALUE 100.
		
  	public void procedureDivision() 
  	{
//  		update("bbbb", 123, 10, 15);

  		move("abcdefghij", enregFixedA);
  		// Advanced mode Fixed record
  		createFixedFileCRLF();
  		readFixedFileCRLF();
  		
  		createFixedFileLF();
  		readFixedFileLF();
  		
  		createFixedFile();
  		readFixedFile();
  		
  		createFixedFileHeaderCRLF();
  		// readFixedFileHeaderCRLF(); // Cannot support
  		
  		createFixedFileHeaderLF();
  		// readFixedFileHeaderLF();	// Cannot support
  		
  		// Advanced mode Variable record
  		createVariableFileCRLF();
  		readVariableFileCRLF();	// To support
  		
  		createVariableFileLF();
  		readVariableFileLF();	// To support


  		createVariableFileHeaderCRLF();
  		readVariableFileHeaderCRLF();
  		
  		createVariableFileHeaderLF();  		
  		readVariableFileHeaderLF();
  		

  		createLgVar2("a", 0);
  		checkLgVar2("a", 0, "123456", 16, 2000);
//  		
// 		
//  		checkLoadLarge();
//  		
  		create("aaaa", 0);
  		check("aaaa", 0, 0, 100);  		
  		update("bbbb", 123, 10, 15);
  		check("bbbb", 123, 10, 15);
  		check("aaaa", 0, 0, 9);
  		check("aaaa", 0, 16, 9);
//
//  		createLgVar("a", 0);
//  		checkLgVar("a", 0, "123456", 0, 2000);  		
//  		updateLgVar("b", 123, 10, 15);
//  		checkLgVar("b", 123, "987654", 10, 15);
//  		checkLgVar("a", 0, "123456", 0, 9);
//  		checkLgVar("a", 0, "123456", 16, 2000);
  		
  	}
  	
  	void createFixedFileCRLF()
  	{
  		openOutput(fileFixedCRLF);
  		move("1234567890", enregFixedCRLF);
  		
  		for(int n=1; n<=100; n++)
  		{
  			write(fileFixedCRLF);
  			enregFixedCRLF.inc();
  		}
  		close(fileFixedCRLF);
  	}
  	
  	void readFixedFileCRLF()
  	{
  		long l = 1234567890;
  		openInput(fileFixedCRLF);
  		
  		for(int n=1; n<=100; n++)
  		{
  			read(fileFixedCRLF);
  			long lVar = enregFixedCRLF.getLong();
  			if(lVar != l)
  			{
  				int gg = 0;
  			}
  			l++;
  		}
  		close(fileFixedCRLF);
  	}

  	
  	void createFixedFileLF()
  	{
  		openOutput(fileFixedLF);
  		move("1234560000", enregFixedLF);
  		
  		for(int n=1; n<=100; n++)
  		{
  			write(fileFixedLF);
  			enregFixedLF.inc();
  		}
  		close(fileFixedLF);
  	}
  	
  	void readFixedFileLF()
  	{
  		long l = 1234560000;
  		openInput(fileFixedLF);
  		
  		for(int n=1; n<=100; n++)
  		{
  			read(fileFixedLF);
  			long lVar = enregFixedLF.getLong();
  			if(lVar != l)
  			{
  				int gg = 0;
  			}
  			l++;
  		}
  		close(fileFixedLF);
  	}
  	
  	void createFixedFile()
  	{
  		openOutput(fileFixed);
  		move("1234560000", enregFixedL);
  		
  		for(int n=1; n<=100; n++)
  		{
  			write(fileFixed);
  			enregFixedL.inc();
  		}
  		close(fileFixed);
  	}
  	
  	void readFixedFile()
  	{
  		long l = 1234560000;
  		openInput(fileFixed);
  		
  		for(int n=1; n<=100; n++)
  		{
  			read(fileFixed);
  			long lVar = enregFixedL.getLong();
  			if(lVar != l)
  			{
  				int gg = 0;
  			}
  			l++;
  		}
  		close(fileFixed);
  	}

  	
  	void createFixedFileHeaderCRLF()
  	{
  		openOutput(fileFixedHeaderCRLF);
  		move("abcdefghijklmnopqrst", enregFixedHeaderCRLF);
  		
  		for(int n=1; n<=100; n++)
  		{
  			write(fileFixedHeaderCRLF);
  		}
  		close(fileFixedHeaderCRLF);
  	}
  	
  	void createFixedFileHeaderLF()
  	{
  		openOutput(fileFixedHeaderLF);
  		move("abcdefghijklmnopqrst", enregFixedHeaderLF);
  		
  		for(int n=1; n<=100; n++)
  		{
  			write(fileFixedHeaderLF);
  		}
  		close(fileFixedHeaderLF);
  	}
  	
  	void create(String cs, int nOffset)
  	{
  		openOutput(fileRW);
  		for(int n=0; n<100; n++)
  		{
  			move(cs, letter);
  			move(nOffset + n, counter);
  			write(fileRW);	  			
  		}
  		close(fileRW);
  	}
  	
  	boolean check(String cs, int nOffset, int nMin, int nMax)
  	{
  		openInput(fileRW);
  		for(int n=0; n<100; n++)
  		{
  			read(fileRW);
  			if(n >= nMin && n <= nMax)
  			{
  				assertIfFalse(letter.equals(cs));
  				assertIfFalse(counter.equals(nOffset + n));
  			}
  		}
  		close(fileRW);
  		return true;
  	}
  		
  	void update(String cs, int nOffset, int nMin, int nMax)
  	{
  		openInputOutput(fileRW);
  		for(int n=0; n<100; n++)
  		{
  			fileRW.read();
  			if(n >= nMin && n <= nMax)
  			{
	  			move(cs, letter);
	  			inc(nOffset, counter); 
	  			rewrite(fileRW);
  			}
  		}
  		close(fileRW);
  	}
//  	
//  	void createLgVar(String cs, int nOffset)
//  	{
//  		String csVal = "";
//  		openOutput(fileRWLgVar);
//  		for(int n=0; n<2000; n++)
//  		{
//  			move("123456", oConst);
//  			move(n + nOffset, oNum);
//  			move(csVal, oLine);
//  			move(n, wLong);
//  			
//  			write(fileRWLgVar);
//  			
//  			csVal += cs;
//  		}
//  		close(fileRWLgVar);
//  	}
//  	
//  	boolean checkLgVar(String cs, int nOffset, String csValueConst, int nMin, int nMax)
//  	{
//  		String csVal = "";
//  		openInput(fileRWLgVar);
//  		initialize(enregOut);
//  		for(int n=0; n<2000; n++)
//  		{
//  			read(fileRWLgVar);
//  			if(n >= nMin && n <= nMax)
//  			{
//	  			assertIfFalse(oNum.equals(n + nOffset));
//	  			assertIfFalse(oConst.equals(csValueConst));
//	  			assertIfFalse(wLong.equals(n));
//  			}
//  			
//  			csVal += cs;
//  			initialize(enregOut);
//  		}
//  		close(fileRWLgVar);
//  		return true;
//  	}
//  		
//  	void updateLgVar(String cs, int nOffset, int nMin, int nMax)
//  	{
//  		String csVal = "";
//  		openInputOutput(fileRWLgVar);
//  		initialize(enregOut);
//  		for(int n=0; n<2000; n++)
//  		{
//  			if(n == 9)
//  			{
//  				int gg =0 ;
//  			}
//  			read(fileRWLgVar);
//  			if(n >= nMin && n <= nMax)
//  			{
//	  			move("987654", oConst);
//	  			move(n + nOffset, oNum);
//	  			move(csVal, oLine);
//	  			move(n, wLong);
//	  			
//	  			rewrite(fileRWLgVar);
//  			}
//  			
//  			csVal += cs;
//  			initialize(enregOut);
//  		}
//  		close(fileRWLgVar);
//  	}
//  	
//  	void checkLoadLarge()
//  	{
//  		int n  = 0;
//  		String cs = fileRLarge.toString();
//  		openInput(fileRLarge);
//  		
//  		RecordDescriptorAtEnd end = read(fileRLarge);
//  		while(!end.atEnd())
//  		{
//  			n++;
//  			end = read(fileRLarge);
//  		}
//  		
//  		close(fileRLarge);
//  	}
//  	
  	void createLgVar2(String cs, int nOffset)
  	{
  		String csVal = "123456789012345678901234567890123456789012345678901234567890";
  		openOutput(fileRWLgVar);
  		for(int n=60; n>=0; n--)
  		{
  			move("123456", oConst);
  			move(n + nOffset, oNum);
  			move(csVal, oLine);
  			move(n, wLong);
  			
  			write(fileRWLgVar);
  			
  			if(n >= 1)
  				csVal = csVal.substring(0, n-1);
  			else 
  				csVal = "";
  		}
  		close(fileRWLgVar);
  	}
  	
  	boolean checkLgVar2(String cs, int nOffset, String csValueConst, int nMin, int nMax)
  	{
  		BaseSession baseSession = getProgramManager().getEnv().getBaseSession();
		FileDescriptor fileDescriptorIn = new FileDescriptor("fileRWLgVar");
		fileDescriptorIn.setSession(baseSession);
		String csPhysicalFileIn = fileDescriptorIn.getPhysicalName();
		
		
		boolean bEbcdicInput = fileDescriptorIn.isEbcdic();
		
		DataFileLineReader dataFileIn = new DataFileLineReader(csPhysicalFileIn, 65500, 0);
		boolean bInOpened = dataFileIn.open();

		fileDescriptorIn.tryAutoDetermineRecordLengthIfRequired(dataFileIn);
  		
  		
  		String csVal = "123456789012345678901234567890123456789012345678901234567890";
  		openInput(fileRWLgVar);
  		//enregOut.initialize();
  		for(int n=60; n>=0; n--)
  		{
  			read(fileRWLgVar);
  			if(n >= nMin && n <= nMax)
  			{
	  			assertIfFalse(oNum.equals(n + nOffset));
	  			assertIfFalse(oConst.equals(csValueConst));
	  			assertIfFalse(wLong.equals(n));
  			}
  			
  			if(n >= 1)
  				csVal = csVal.substring(0, n-1);
  			else 
  				csVal = "";
  			//enregOut.initialize();
  		}
  		close(fileRWLgVar);
  		return true;
  	}
  	
  	
  	void createVariableFileCRLF()
  	{
  		openOutput(fileVariableCRLF);
  		String cs = "A";
  		for(int n=1; n<=100; n++)
  		{
  			move(cs, oVariableCRLF);
  			move(n, wText);
  			cs += "B";
  			write(fileVariableCRLF);
  		}
  		close(fileVariableCRLF);
  	}
  	
  	void readVariableFileCRLF()
  	{
  		openInput(fileVariableCRLF);
  		String cs = "A";
  		for(int n=1; n<=100; n++)
  		{
  			read(fileVariableCRLF);	// enregVariableCRLF
  			cs += "B";

//  		move(cs, oVariableCRLF);
//  		move(n, wText);
  		}
  		close(fileVariableCRLF);
  	}
  	
  	void createVariableFileLF()
  	{
  		openOutput(fileVariableLF);
  		String cs = "A";
  		for(int n=1; n<=100; n++)
  		{
  			move(cs, oVariableLF);
  			move(n, wText);
  			cs += "B";
  			write(fileVariableLF);
  		}
  		close(fileVariableLF);
  	}
  	
  	void readVariableFileLF()
  	{
  		openInput(fileVariableLF);
  		String cs = "A";
  		for(int n=1; n<=100; n++)
  		{
  			cs += "B";
  			read(fileVariableLF);	// enregVariableLF
  			assertIfDifferent(n, wText);
//  		move(cs, oVariableCRLF);
//  		move(n, wText);
  		}
  		close(fileVariableLF);
  	}
  	
  	void createVariableFileHeaderCRLF()
  	{
  		openOutput(fileVariableHeaderCRLF);
  		String cs = "A";
  		for(int n=1; n<=100; n++)
  		{
  			move(cs, oVariableHeaderCRLF);
  			move(n, wText);
  			cs += "B";
  			write(fileVariableHeaderCRLF);
  		}
  		close(fileVariableHeaderCRLF);
  	}
  	
  	void readVariableFileHeaderCRLF()
  	{
		openInput(fileVariableHeaderCRLF);
		String cs = "A";
		for(int n=1; n<=100; n++)
		{
			read(fileVariableHeaderCRLF);	// enregVariableHeaderCRLF
			assertIfDifferent(n, wText);
			cs += "B";
		}
		close(fileVariableHeaderCRLF);
  	}
  	
  	void createVariableFileHeaderLF()
  	{
  		openOutput(fileVariableHeaderLF);
  		String cs = "A";
  		for(int n=1; n<=100; n++)
  		{
  			move(cs, oVariableHeaderLF);
  			move(n, wText);
  			cs += "B";
  			write(fileVariableHeaderLF);
  		}
  		close(fileVariableHeaderLF);
  	}
  	
  	void readVariableFileHeaderLF()
  	{
		openInput(fileVariableHeaderLF);
		String cs = "A";
		for(int n=1; n<=100; n++)
		{
			read(fileVariableHeaderLF);	// enregVariableHeaderLF
			assertIfDifferent(n, wText);
			cs += "B";
		}
		close(fileVariableHeaderLF);
  	}

}

