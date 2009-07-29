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
package nacaLib.misc;

import idea.onlinePrgEnv.OnlineEnvironment;
import idea.onlinePrgEnv.OnlineSession;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import jlib.log.AssertException;
import jlib.log.Log;
import jlib.misc.AsciiEbcdicConverter;
import jlib.misc.BaseDataFile;
import jlib.misc.DataFileReadWrite;
import jlib.misc.DateUtil;
import jlib.misc.EnvironmentVar;
import jlib.misc.FileEndOfLine;
import jlib.misc.FileSystem;
import jlib.misc.FtpUtil;
import jlib.misc.JVMReturnCodeManager;
import jlib.misc.LineRead;
import jlib.misc.LogicalFileDescriptor;
import jlib.misc.NumberParser;
import jlib.misc.StringUtil;
import jlib.sql.DbConnectionBase;
import nacaLib.base.CJMapObject;
import nacaLib.basePrgEnv.BaseProgramLoader;
import nacaLib.basePrgEnv.BaseProgramManager;
import nacaLib.basePrgEnv.BaseResourceManager;
import nacaLib.basePrgEnv.BaseSession;
import nacaLib.exceptions.DumpProgramException;
import nacaLib.fileConverter.FileSearchGeneration;
import nacaLib.fpacPrgEnv.VarFPacLengthUndef;
import nacaLib.sqlSupport.CSQLStatus;
import nacaLib.varEx.CobolConstant;
import nacaLib.varEx.FileDescriptor;
import nacaLib.varEx.Var;
import nacaLib.varEx.VarAndEdit;

public class NacaToolBox extends CJMapObject
{
	private BaseProgramManager m_ProgramManager;
	
	public NacaToolBox(BaseProgramManager manager)
	{
		m_ProgramManager = manager ;
	}
	
	/**
	 * @param longcar
	 * @param mm_Zone
	 */
	public void toUpper(VarAndEdit length, VarAndEdit data)
	{
		toUpper(data);
	}
	public void toUpper(VarAndEdit data)
	{
		String cs = data.getString() ;
		cs = cs.replaceAll("á", "a");
		cs = cs.replaceAll("à", "a");
		cs = cs.replaceAll("ä", "a");
		cs = cs.replaceAll("â", "a");
		cs = cs.replaceAll("é", "e");
		cs = cs.replaceAll("è", "e");
		cs = cs.replaceAll("ë", "e");
		cs = cs.replaceAll("ê", "e");
		cs = cs.replaceAll("í", "i");
		cs = cs.replaceAll("ì", "i");
		cs = cs.replaceAll("ï", "i");
		cs = cs.replaceAll("î", "i");
		cs = cs.replaceAll("ó", "o");
		cs = cs.replaceAll("ò", "o");
		cs = cs.replaceAll("ö", "o");
		cs = cs.replaceAll("ô", "o");
		cs = cs.replaceAll("ú", "u");
		cs = cs.replaceAll("ù", "u");
		cs = cs.replaceAll("ü", "u");
		cs = cs.replaceAll("û", "u");		
		cs = cs.toUpperCase() ; 
		data.set(cs) ;
	}
	public void toLower(VarAndEdit data)
	{
		String cs = data.getString() ;
		cs = cs.toLowerCase() ; 
		data.set(cs) ;
	}
	
	/**
	 * @param language
	 * @param length
	 * @param data
	 */
	public void toUpperSpecial(VarAndEdit type, VarAndEdit length, VarAndEdit data)
	{
		String csType = type.getString();
		String cs = data.getString() ;
		if (csType.equals("1"))
		{
			cs = cs.replaceAll("ä", "AE");
			cs = cs.replaceAll("ö", "OE");
			cs = cs.replaceAll("ü", "UE");
		}
		data.set(cs) ;
		toUpper(data);
	}

	/**
	 * @param retCode
	 * @param data
	 * @param length
	 */
	public void leftPaddingBlank(VarAndEdit retCode, VarAndEdit data, VarAndEdit length)
	{
		leftPaddingBlank(retCode, data, length.getBodySize());
	}
	public void leftPaddingBlank(VarAndEdit retCode, VarAndEdit data, int length)
	{
		leftPadding(retCode, data, length, ' ');
	}
	
	/**
	 * @param retCode
	 * @param data
	 * @param length
	 */
	public void leftPaddingZero(VarAndEdit retCode, VarAndEdit data, VarAndEdit length)
	{
		leftPaddingZero(retCode, data, length.getBodySize());
	}
	public void leftPaddingZero(VarAndEdit retCode, VarAndEdit data, int length)
	{
		retCode.set(0) ;
		String cs = data.getString() ;
		cs = cs.trim();
		if (cs.length() == 0)
		{
			return;
		}
		leftPadding(retCode, data, length, '0');
		if (!checkNumberIsLong(data.getString()))
		{
			retCode.set(1);
		}
	}

	/**
	 * @param retCode
	 * @param data
	 * @param length
	 * @param c
	 */
	public void leftPadding(VarAndEdit retCode, VarAndEdit data, int length, char c)
	{
		String cs = data.getString() ;
		cs = cs.trim();
		int nbSpace = length - cs.length() ;
		String space = "" ;
		for (int i=0; i<nbSpace; i++)
		{
			space += c ;
		}
		String fullcs = space + cs ;
		data.set(fullcs) ;		
	}
	
	public void checkDate(VarAndEdit format, VarAndEdit date, VarAndEdit retCode)
	{
		String csDate;
		String csFormat;
		if (format.getInt() == 1) 
		{
			csDate = date.getString().substring(0, 6);
			csFormat = "ddMMyy";
		} 
		else if (format.getInt() == 6) 
		{
			csDate = date.getString().substring(0, 6);
			csFormat = "yyMMdd";
		} 
		else if (format.getInt() == 7) 
		{
			csDate = date.getString();
			csFormat = "ddMMyyyy";
		} 
		else if (format.getInt() == 12) 
		{
			csDate = date.getString();
			csFormat = "yyyyMMdd";
		}
		else 
		{
			throw new RuntimeException("Y01S18 format not supported");
		}
		if (DateUtil.formatDate(csDate, csFormat, false) == null) 
		{
			retCode.set(CobolConstant.HighValue);
		} 
		else 
		{
			retCode.set(CobolConstant.LowValue);
		}
	}

	/**
	 * @param var
	 * @param w_Nmpgm
	 */
	public void getProgramForTransID(VarAndEdit TID, VarAndEdit progName)
	{
		String tid = TID.getString() ;
		String p = BaseProgramLoader.ResolveTransID(tid) ;
		if (p != null)
		{
			progName.set(p) ;
		}
	}
	/**
	 * @param var
	 * @param w_Nmpgm
	 */
	public void getProgramForTransID(String tid, Var progName)
	{
		String p = BaseProgramLoader.ResolveTransID(tid) ;
		if (p != null)
		{
			progName.set(p) ;
		}
	}
	
	public void addDate(VarAndEdit outputDate)
	{	
		String inputYear = outputDate.getString().substring(0, 2).trim();
		
		GregorianCalendar calendar = new GregorianCalendar();
		
		if (!inputYear.equals("")) {
			int year = NumberParser.getAsInt(inputYear) ;
		    if (year < 80) 
		    {
		    	year = 2000 + year ;
		    } 
		    else 
		    {
		    	year = 1900 + year ;
		    }
			calendar = findDateEaster(year);
		}
		
	    outputDate.set(completeDate(calendar, false));
	}
	public void addDate(VarAndEdit outputDate, VarAndEdit inputDate)
	{
		String inputDay = inputDate.getString().substring(4, 6) ;
		String inputMonth = inputDate.getString().substring(2, 4) ;
		String inputYear = inputDate.getString().substring(0, 2) ;
		
		GregorianCalendar calendar = new GregorianCalendar();
	    int year = NumberParser.getAsInt(inputYear) ;
	    if (year < 80) 
	    {
	    	year = 2000 + year ;
	    } 
	    else 
	    {
	    	year = 1900 + year ;
	    }
	    int month = NumberParser.getAsInt(inputMonth) - 1;
	    int day = NumberParser.getAsInt(inputDay) ;
	    calendar.set(year, month, day);
	    
	    outputDate.set(completeDate(calendar, false));
	}
	public void addDate(VarAndEdit outputDate, VarAndEdit inputDate, VarAndEdit arg)
	{
		String inputDay = inputDate.getString().substring(4, 6) ;
		String inputMonth = inputDate.getString().substring(2, 4) ;
		String inputYear = inputDate.getString().substring(0, 2) ;
		String argDay;
		String argMonth;
		if (arg.getString().substring(5, 6).equals(" "))
		{
			argDay = arg.getString().substring(2, 5) ;
			argMonth = "00";
		}
		else
		{
			argDay = arg.getString().substring(4, 6) ;
			argMonth = arg.getString().substring(2, 4) ;
		}
		String argYear = arg.getString().substring(0, 2) ;
		String argOp = arg.getString().substring(6, 7) ;
		
		if (!argOp.trim().equals("") && !argOp.equals("+") && !argOp.equals("-"))
		{
			Assert("addDate supports only the operator + and -");
		}
		
	    GregorianCalendar calendar = new GregorianCalendar();
	    int add;
	    int year = NumberParser.getAsInt(inputYear) ;
	    if (year < 80) 
	    {
	    	year = 2000 + year ;
	    } 
	    else 
	    {
	    	year = 1900 + year ;
	    }
	    int month = NumberParser.getAsInt(inputMonth) - 1;
	    int day = NumberParser.getAsInt(inputDay) ;
	    calendar.set(year, month, day);
	    if (!argDay.equals("00")) 
	    {
	    	add = new Integer(argDay).intValue();
	    } 
	    else if (!argMonth.equals("00")) 
	    {
			add = new Integer(argMonth).intValue();
	    } 
	    else 
	    {
			add = new Integer(argYear).intValue();
	    }
	
	    if (argOp.equals("-")) 
	    {
	    	add = add * -1;
	    }
	    if (!argDay.equals("00")) 
	    {
	    	calendar.add(GregorianCalendar.DATE, add);
	    }
	    else if (!argMonth.equals("00")) 
	    {
	    	calendar.add(GregorianCalendar.MONTH, add);
	    } 
	    else 
	    {
	    	calendar.add(GregorianCalendar.YEAR, add);
	    }
	    
	    outputDate.set(completeDate(calendar, false));
	}

	public void doDateJob(VarAndEdit vOperation, VarAndEdit vParam1)
	{
		doDateJob(vOperation, vParam1, null, null) ;
	}
	public void doDateJob(VarFPacLengthUndef vOperation, VarFPacLengthUndef vParam1)
	{
		Var varOperation = vOperation.createVar();
		Var varParam1 = vParam1.createVar();
		doDateJob(varOperation, varParam1, null, null);
	}
	public void doDateJob(VarFPacLengthUndef vOperation, VarFPacLengthUndef vParam1, VarFPacLengthUndef vParam2, VarFPacLengthUndef vParam3)
	{
		Var varOperation = vOperation.createVar();
		Var varParam1 = vParam1.createVar();
		Var varParam2 = vParam2.createVar();
		Var varParam3 = vParam3.createVar();
		doDateJob(varOperation, varParam1, varParam2, varParam3);
	}
	public void doDateJob(VarAndEdit vOperation, VarAndEdit vParam1, VarAndEdit vParam2, VarAndEdit vParam3)
	{
		String operation = vOperation.getString().trim();
		String csYear = vParam1.getString().substring(0, 4) ;
    	String csMonth = vParam1.getString().substring(4, 6) ;
    	String csDay = vParam1.getString().substring(6, 8).trim() ;
    	
    	int argYear = 0 ;
    	int argMonth = 0;
    	int argDay = 0;
    	int argWeek = 0;
    	if (vParam3 != null)
    	{
    		argYear = NumberParser.getAsInt(vParam3.getString().substring(0, 4));
    		if (vParam3.getString().substring(6, 8).trim().length() == 0)
    		{
    			argWeek = NumberParser.getAsInt(vParam3.getString().substring(4, 6));
    		}
    		else if (vParam3.getString().substring(6, 8).trim().length() == 1) 
			{
    			argDay = NumberParser.getAsInt(vParam3.getString().substring(4, 7));
			} 
			else 
			{	
				argMonth = NumberParser.getAsInt(vParam3.getString().substring(4, 6));
    	    	argDay = NumberParser.getAsInt(vParam3.getString().substring(6, 8));
			}
    	}
    	
    	GregorianCalendar calendar = new GregorianCalendar();
    	calendar.set(GregorianCalendar.HOUR, 0);
    	
    	if (operation.equals(">") || operation.equals("<"))
		{
			Assert("doDateJob doesn't implement the operator > and <");
		}
		
		if (operation.equals("P"))
		{
			calendar = findDateEaster(calendar.get(GregorianCalendar.YEAR));
		}
		else if (!operation.equals("J") && !csDay.equals("")) 
		{
			int year = NumberParser.getAsInt(csYear) ;
			if (csDay.length() > 1) 
			{	
		    	int month = NumberParser.getAsInt(csMonth) -1 ;
		    	int day = NumberParser.getAsInt(csDay) ;		    	
				calendar.set(year, month, day);
			}
			else 
			{
				calendar.set(GregorianCalendar.YEAR, year);
				calendar.set(GregorianCalendar.DAY_OF_YEAR, new Integer(csMonth + csDay.substring(0, 1)).intValue());
			}
		}

		if (operation.equals("P"))
		{
			vParam1.set(completeDate(calendar, true));
		}
		else if (operation.equals("J") || operation.equals("C")) 
		{
			vParam1.set(completeDate(calendar, true));
		} 
		else if (operation.equals("A")) 
		{
			vParam1.set(completeDate(calendar, true));
			vParam2.set(completeDate(calendar, true));
			int add;
			if (argDay != 0)
			{
				add = argDay;
				calendar.add(GregorianCalendar.DATE, add);
			}
			if (argWeek != 0)
			{
				add = argWeek;
				calendar.add(GregorianCalendar.WEEK_OF_YEAR, add);
			}
			if (argMonth != 0) 
			{
				add = argMonth;
				calendar.add(GregorianCalendar.MONTH, add);
			} 
			if (argYear != 0)
			{
				add = argYear;
				calendar.add(GregorianCalendar.YEAR, add);
			}
			vParam2.set(completeDate(calendar, true));
		}
		else if (operation.equals("S"))
		{
			vParam1.set(completeDate(calendar, true));
			int add;
			if (argDay != 0)
			{
				add = argDay * -1;
				calendar.add(GregorianCalendar.DATE, add);
			}
			if (argWeek != 0)
			{
				add = argWeek * -1;
				calendar.add(GregorianCalendar.WEEK_OF_YEAR, add);
			}
			if (argMonth != 0) 
			{
				add = argMonth * -1;
				calendar.add(GregorianCalendar.MONTH, add);
			} 
			if (argYear != 0)
			{
				add = argYear * -1;
				calendar.add(GregorianCalendar.YEAR, add);
			}
			vParam2.set(completeDate(calendar, true));
		}
		else if (operation.equals("D"))
		{	
			vParam1.set(completeDate(calendar, true));
			
			GregorianCalendar calendarEnd = new GregorianCalendar();
			calendarEnd.set(GregorianCalendar.HOUR, 0);
			csYear = vParam2.getString().substring(0, 4);
	    	csMonth = vParam2.getString().substring(4, 6);
	    	csDay = vParam2.getString().substring(6, 8).trim();
	    	int year = NumberParser.getAsInt(csYear);
			if (csDay.length() > 1) 
			{	
		    	int month = NumberParser.getAsInt(csMonth) -1;
		    	int day = NumberParser.getAsInt(csDay);		    	
				calendarEnd.set(year, month, day);
			}
			else
			{
				calendarEnd.set(GregorianCalendar.YEAR, year);
				calendarEnd.set(GregorianCalendar.DAY_OF_YEAR, new Integer(csMonth + csDay.substring(0, 1)).intValue());
			}
			vParam2.set(completeDate(calendarEnd, true));

			String csDiffYears = "0000";
			String csDiffMonths = "00";
			String csDiffDays = "00";
			String csDiffDayOfYear = "000";
			String csDiffDaysTotal = "0000000";
			
			// int diffDaysTotal = elapsedTime(calendar, calendarEnd, GregorianCalendar.DATE);
			long diffMillis = calendarEnd.getTimeInMillis() - calendar.getTimeInMillis();
			long diffDaysTotal = diffMillis/(24*60*60*1000);
						
			csDiffDaysTotal = StringUtil.leftPad(String.valueOf(diffDaysTotal), 7, '0');

			int diffYears = elapsedTime(calendar, calendarEnd, GregorianCalendar.YEAR);
			csDiffYears = StringUtil.leftPad(String.valueOf(diffYears), 4, '0');
			if (diffYears > 0)
			{
				calendar.add(GregorianCalendar.YEAR, diffYears);
				//int DiffDayOfYear = elapsedTime(calendar, calendarEnd, GregorianCalendar.DATE);				
				diffMillis = calendarEnd.getTimeInMillis() - calendar.getTimeInMillis();
				long DiffDayOfYear = diffMillis/(24*60*60*1000);
				csDiffDayOfYear = StringUtil.leftPad(String.valueOf(DiffDayOfYear), 3, '0');
			}
			else
			{
				csDiffDayOfYear = StringUtil.leftPad(String.valueOf(diffDaysTotal), 3, '0');
			}

			int diffMonths = elapsedTime(calendar, calendarEnd, GregorianCalendar.MONTH);
			csDiffMonths = StringUtil.leftPad(String.valueOf(diffMonths), 2, '0');
			if (diffMonths > 0)
			{
				calendar.add(GregorianCalendar.MONTH, diffMonths);
			}

			//int diffDays = elapsedTime(calendar, calendarEnd, GregorianCalendar.DATE);
			diffMillis = calendarEnd.getTimeInMillis() - calendar.getTimeInMillis();
			long diffDays = diffMillis/(24*60*60*1000);
			csDiffDays = StringUtil.leftPad(String.valueOf(diffDays), 2, '0');
			
			vParam3.set(csDiffYears + csDiffMonths + csDiffDays + csDiffYears + csDiffDayOfYear + csDiffDaysTotal);
		}
	}
	
	private int elapsedTime(GregorianCalendar gc1, GregorianCalendar gc2, int type)
	{
		GregorianCalendar calendarBegin, calendarEnd;
		calendarBegin = (GregorianCalendar)gc1.clone();
		calendarEnd = (GregorianCalendar)gc2.clone();
		int elapsed = 0;
		while (true)
		{
			calendarBegin.add(type, 1);
			if (calendarBegin.before(calendarEnd))
			{
				elapsed++;
			}
			else
			{
				break;
			}
        }
		return elapsed;
	}

	private String completeDate(GregorianCalendar gc, boolean bLongInfo)
	{
		GregorianCalendar calendar = (GregorianCalendar)gc.clone();
		
		String csDate = "00000000";
		String csDayOfWeek = "0";
		String csDaysInMonth = "00";
    	String csDayOfYear = "000";
    	String csDaysToEndYear = "000";
    	String csDaysInYear = "000";
    	String csWeekOfYear = "00";
    	String csDayEaster = "000";
    	String csFlagHoliday = " ";
    	String csLeapYear = "0";
    	
    	calendar.setFirstDayOfWeek(GregorianCalendar.MONDAY);
    	calendar.setMinimalDaysInFirstWeek(4);
    	
    	Format formatter = new SimpleDateFormat("yyyyMMdd");
    	csDate = formatter.format(calendar.getTime());
		
    	int dayOfWeek = calendar.get(GregorianCalendar.DAY_OF_WEEK);
		if (dayOfWeek == 1) 
		{
			csDayOfWeek = "7";
		} 
		else 
		{
			csDayOfWeek = String.valueOf(dayOfWeek - 1) ;
		}
		
		csDaysInMonth = String.valueOf(calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
		
		csWeekOfYear = StringUtil.leftPad(String.valueOf(calendar.get(GregorianCalendar.WEEK_OF_YEAR)), 2, '0');
		
		int dayOfYear = calendar.get(GregorianCalendar.DAY_OF_YEAR);
		calendar.set(GregorianCalendar.MONTH, 11);
		calendar.set(GregorianCalendar.DAY_OF_MONTH, 31);		
		int daysInYear = calendar.get(GregorianCalendar.DAY_OF_YEAR);		
		csDayOfYear = StringUtil.leftPad(String.valueOf(dayOfYear), 3, '0');
		csDaysToEndYear = StringUtil.leftPad(String.valueOf(daysInYear - dayOfYear), 3, '0');
		csDaysInYear = StringUtil.leftPad(String.valueOf(daysInYear), 3, '0');
		
		GregorianCalendar easterCalendar = findDateEaster(calendar.get(GregorianCalendar.YEAR));
		easterCalendar.set(GregorianCalendar.HOUR, 0);
		csDayEaster = StringUtil.leftPad(String.valueOf(easterCalendar.get(GregorianCalendar.DAY_OF_YEAR)), 3, '0');
		
		//int diffEasterDays = elapsedTime(easterCalendar, calendar, GregorianCalendar.DATE);
		long diffMillis = gc.getTimeInMillis() - easterCalendar.getTimeInMillis();
		long diffEasterDays = diffMillis/(24*60*60*1000);
		
		if (csDate.substring(4).equals("0101") ||
			csDate.substring(4).equals("0102") ||
			csDate.substring(4).equals("0801") ||
			csDate.substring(4).equals("1225") ||
			csDate.substring(4).equals("1226") ||
			csDayEaster.equals(csDayOfYear) ||
			diffEasterDays == 1 ||   // lundi de Pâques
			diffEasterDays == 39 ||  // Ascension
			diffEasterDays == 50)    // Pentecôte
		{
			csFlagHoliday = "F";
		}
		
		if (calendar.isLeapYear(calendar.get(GregorianCalendar.YEAR)))
		{
			csLeapYear = "9";
		}
		
		if (bLongInfo)
		{
			return csDate + csDayOfWeek + csDaysInMonth + csDayOfYear + csDaysToEndYear + csDaysInYear + csWeekOfYear + csDayEaster + csFlagHoliday;
		}
		else
		{
			String csTarif = csDayOfWeek;
			if (csFlagHoliday.equals("F"))
				csTarif = "7";
			return csDate.substring(2) + csDayOfYear + csLeapYear + csTarif  + csDayOfWeek + csWeekOfYear;
		}
	}
	
	/*
	 * http://www.tondering.dk/claus/cal/node3.html#SECTION003137000000000000000
	 */
	private GregorianCalendar findDateEaster(int year)
	{
		int g = year % 19;
        int c = year / 100;
        int h = (c - c / 4 - (8 * c + 13) / 25 + 19 * g + 15) % 30;
        int i = h - h / 28 * (1 - h / 28 * 29 / (h + 1) * (21 - g) / 11);
        int j = (year + year / 4 + i + 2 - c + c / 4) % 7;
        int l = i - j;
        int month = 3 + (l + 40) / 44;
        int day = l + 28 - 31 * (month / 4);
        
		return new GregorianCalendar(year, month - 1, day);
	}
	
	public void getTimeSpecial(VarAndEdit vParam)
	{
		GregorianCalendar calendar = new GregorianCalendar();
		Format formatter = new SimpleDateFormat("HHmmssSSS");
    	vParam.set(formatter.format(calendar.getTime()) + "000");
	} 

	public void checkShortNumber(Var returnCode, VarAndEdit input, int length, Var output, Var posBeforeDecimal, Var posAfterDecimal)
	{
		checkNumber(returnCode, input.getString().substring(0, length).trim(), output, posBeforeDecimal, posAfterDecimal, true);
	}
	public void checkLongNumber(Var returnCode, VarAndEdit input, int length, Var output, Var posBeforeDecimal, Var posAfterDecimal)
	{
		checkNumber(returnCode, input.getString().substring(0, length).trim(), output, posBeforeDecimal, posAfterDecimal, false);		
	}
	private void checkNumber(Var returnCode, String input, Var output, Var posBeforeDecimal, Var posAfterDecimal, boolean isShort)
	{
		int length = 0;
	    int lengthDecimal = 0;
	    String formatSigne = "";
	    String formatBeforeDecimal = "";
    	String formatAfterDecimal = "";
    	
    	output.set(0);
    	posBeforeDecimal.set(0);
		posAfterDecimal.set(0);
		
		if (!input.equals(""))
		{
			input = input.replaceAll("" + (char)20, "");
	    	input = input.replaceAll("" + (char)0, "");
		}
    	
    	if (input.equals(""))
    	{
    		returnCode.set(1);
			return;
    	}
    	else
    	{
	    	if (input.indexOf("-") != -1)
	    	{
	    		formatSigne = "-";
	    		input = input.replace("-", "");
	    	}
	    	else
	    	{
	    		input = input.replace("+", "");
	    	}
	    	int posSearch = input.indexOf(".");
	    	if (posSearch == -1)
	    	{
	    		posSearch = input.indexOf(",");
	    	}
	    	if (posSearch == -1)
			{
	    		formatBeforeDecimal = input;
			}
	    	else 
			{
	    		formatBeforeDecimal = input.substring(0, posSearch);
	    		if (posSearch + 1 < input.length())
	    		{
	    			formatAfterDecimal = input.substring(posSearch + 1);
	    		}
			}
	    	length = checkNumberGetLength(formatBeforeDecimal);
	    	lengthDecimal = checkNumberGetLengthDecimal(formatAfterDecimal);
	    	if (!checkNumberIsLong(formatBeforeDecimal) || !checkNumberIsLong(formatAfterDecimal))
	    	{
	    		returnCode.set(1);
				return;
	    	}
	    }
		if ((isShort && length > 9) || (!isShort && length > 14)) 
		{
			returnCode.set(2);
			return;
		}
		if ((isShort && lengthDecimal > 9) || (!isShort && lengthDecimal > 4)) 
		{
			returnCode.set(3);
			return;
		}
		returnCode.set(0);
		posBeforeDecimal.set(length);
		posAfterDecimal.set(lengthDecimal);
		if (formatAfterDecimal.equals(""))
		{
			output.set(formatSigne + formatBeforeDecimal);
		}
		else
		{
			output.set(formatSigne + formatBeforeDecimal + "." + formatAfterDecimal);
		}
	}

	private int checkNumberGetLength(String s) 
	{
		int length = 0;
		boolean found = false;
	    for (int i=0; i < s.length(); i++) 
	    {
	    	if (found || s.charAt(i) != '0') 
	    	{
	    		found = true;
	    		length++;
	    	}
	    }
	    return length;
	}

	private int checkNumberGetLengthDecimal(String s) 
	{
	  	int length = 0;
	  	boolean found = false;
	    for (int i=s.length() - 1; i > -1; i--) 
	    {
	    	if (found || s.charAt(i) != '0') 
	    	{
	    		found = true;
	    		length++;
	    	}
	    }
	    return length;
	}
	
	private boolean checkNumberIsLong(String s)
	{
		if (s.equals(""))
		{
			return true;
		}

		try
		{
			long l = Long.parseLong(s);
			if (l < 0)
				return false;
		}	
		catch (NumberFormatException e)
		{
			return false ;
		}
		return true;
	}
	
	/*
	 * Var dfhcommarea = declare.level(1).var() ;                               // (41) 01   DFHCOMMAREA.
		Var net = declare.level(5).picX(8).var() ;                              // (42)      05  NET                  PIC X(08).
		Var term = declare.level(5).picX(4).var() ;                             // (43)      05  TERM                 PIC X(04).
		Var netlu62 = declare.level(5).picX(8).var() ;                          // (44)      05  NETLU62              PIC X(08).
		Var termlu62 = declare.level(5).picX(4).var() ;                         // (45)      05  TERMLU62             PIC X(04).
	 */
	public void getTerminal(BaseSession baseSession, Var param)
	{
		String net = "L930CON1";
		String term = "CON1";
		String netLu62 = "L930CON1";
		String termLu62 = "CON1";

		OnlineSession session = (OnlineSession) baseSession; 
		if(session != null)
		{
			net = session.getTerminalNet();
			term = session.getTerminalTerm();
			netLu62 = session.getTerminalNetLu62();
			termLu62 = session.getTerminalTermLu62();
		}
		
		param.set(StringUtil.rightPad(net, 8, ' ') +
  				StringUtil.rightPad(term, 4, ' ') +
				StringUtil.rightPad(netLu62, 8, ' ') +
				StringUtil.rightPad(termLu62, 4, ' '));
	}
	
	public void generateRandomNumber(Var param)
	{
		Random generator = new Random();
		param.set(generator.nextInt(param.getInt() + 1));
	}
	
	/*
	 * Var p2_Loce = declare.level(1).var() ;                                   // (46)  01  P2-LOCE.                                                     
		Var p2_Adrpayn = declare.level(5).picX(3).var() ;                       // (47)      05 P2-ADRPAYN           PIC X(3).                            
		Var p2_Adrnpe = declare.level(5).picX(8).var() ;                        // (48)      05 P2-ADRNPE            PIC X(8).                            
		Var p2_Adrloce = declare.level(5).picX(20).var() ;                      // (49)      05 P2-ADRLOCE           PIC X(20).                           
		Var p2_Adrpays = declare.level(5).picX(3).var() ;                       // (50)      05 P2-ADRPAYS           PIC X(3).                            
		Var p2_Adrpayl = declare.level(5).picX(20).var() ;                      // (51)      05 P2-ADRPAYL           PIC X(20).                           
		Var filler$0 = declare.level(5).picX(6).valueSpaces().var() ;           // (52)      05 FILLER               PIC X(6)      VALUE SPACE.           
	   Var filler$1 = declare.level(1).redefines(p2_Loce).var() ;               // (53)  01  FILLER REDEFINES P2-LOCE.                                    
		Var p2_Ligloce1 = declare.level(5).var() ;                              // (54)      05 P2-LIGLOCE1.                                              
			Var p2_Cdretn = declare.level(10).picX(1).var() ;                   // (55)         10 P2-CDRETN         PIC X.                               
			Var filler$2 = declare.level(10).picX(29).var() ;                   // (56)         10 FILLER            PIC X(29).                           
		Var p2_Ligloce2 = declare.level(5).picX(30).var() ;                     // (57)      05 P2-LIGLOCE2          PIC X(30).
	 */
	public void formatForeignAddress(Var param)
	{
		Var adrpayn = param.getVarChildAt(1) ;
		Var adrnpe = param.getVarChildAt(2) ;
		Var adrloce = param.getVarChildAt(3) ;
		Var adrpays = param.getVarChildAt(4) ;
		Var adrpayl = param.getVarChildAt(5) ;
		
		String formatting = "";		
		if (adrpayn.getString().equals("998") || adrpayn.getString().equals("999"))
		{
			formatting = "1";
		}
		//else
		//{	
		//	VI0801Sql vi0801sql = new VI0801Sql();
		//	vi0801sql.read(m_ProgramManager, adrpayn.getString());
		//	formatting = vi0801sql.getAdrpyem();
		//}
		if (formatting.equals(""))
		{
			formatting = "3";
		}


		String line1 = "";
		String line2 = "";
		if (formatting.equals("1"))
		{
			line1 = adrnpe.getString().trim() + " " + adrloce.getString().trim();
		}
		else if (formatting.equals("2"))
		{
			line1 = adrpays.getString().trim() + adrnpe.getString().trim() + " " + adrloce.getString().trim();
			line2 = adrpayl.getString().trim();
		}
		else if (formatting.equals("3"))
		{
			line1 = adrnpe.getString().trim() + " " + adrloce.getString().trim();
			line2 = adrpayl.getString().trim();
		}
		else if (formatting.equals("4"))
		{
			line1 = adrnpe.getString().trim() + "-" + adrloce.getString().trim();
			line2 = adrpayl.getString().trim();
		}
		else if (formatting.equals("5"))
		{
			line1 = adrloce.getString().trim() + " " + adrnpe.getString().trim();
			line2 = adrpayl.getString().trim();
		}
		else if (formatting.equals("6"))
		{
			line1 = adrloce.getString().trim() + "-" + adrnpe.getString().trim();
			line2 = adrpayl.getString().trim();
		}
		else if (formatting.equals("7"))
		{
			line1 = adrloce.getString().trim();
			line2 = adrnpe.getString().trim() + " " + adrpayl.getString().trim();
		}
		else if (formatting.equals("8"))
		{
			line1 = adrloce.getString().trim();
			line2 = adrpayl.getString().trim() + " " + adrnpe.getString().trim();
		}
		else if (formatting.equals("9"))
		{
			line1 = adrloce.getString().trim();
			line2 = adrpayl.getString().trim();
		}
		else if (formatting.equals("A"))
		{
			line1 = adrpays.getString().trim() + adrloce.getString().trim() + " " + adrnpe.getString().trim();
			line2 = adrpayl.getString().trim();
		}
		else if (formatting.equals("B"))
		{
			// provisoire (assume 3)
			line1 = adrnpe.getString().trim() + " " + adrloce.getString().trim();
			line2 = adrpayl.getString().trim();
		}
		else if (formatting.equals("C"))
		{
			// provisoire (assume 5)
			line1 = adrloce.getString().trim() + " " + adrnpe.getString().trim();
			line2 = adrpayl.getString().trim();
		}
		else if (formatting.equals("D"))
		{
			// provisoire (assume 8)
			line1 = adrloce.getString().trim();
			line2 = adrpayl.getString().trim() + " " + adrnpe.getString().trim();
		}
		else if (formatting.equals("E"))
		{
			// provisoire (assume 8)
			line1 = adrloce.getString().trim();
			line2 = adrpayl.getString().trim() + " " + adrnpe.getString().trim();
		}
		else if (formatting.equals("F"))
		{
			// provisoire (assume 3)
			line1 = adrnpe.getString().trim() + " " + adrloce.getString().trim();
			line2 = adrpayl.getString().trim();
		}
		else if (formatting.equals("G"))
		{
			// provisoire (assume 3)
			line1 = adrnpe.getString().trim() + " " + adrloce.getString().trim();
			line2 = adrpayl.getString().trim();
		}
		else
		{
			// 3 par defaut
			line1 = adrnpe.getString().trim() + " " + adrloce.getString().trim();
			line2 = adrpayl.getString().trim();
		}

		param.set(StringUtil.rightPad(line1, 30, ' ') + StringUtil.rightPad(line2, 30, ' '));
	}
	
	/*
	 * 
	Var vc01pa24 = declare.level(1).var() ;                                     // (238)  01  VC01PA24.
		Var dicjob = declare.level(5).picX(8).var() ;                           // (239)      05 DICJOB     PIC X(8).
		Var filler$48 = declare.level(5).redefines(dicjob).filler() ;           // (240)      05 FILLER     REDEFINES DICJOB.
			Var cdret = declare.level(10).picX(1).var() ;                       // (241)         10 CDRET   PIC X.
			Var filler$49 = declare.level(10).picX(7).filler() ;                // (242)         10 FILLER  PIC X(7).
		Var diccomp = declare.level(5).picX(8).var() ;                          // (243)      05 DICCOMP    PIC X(8).
		Var utient = declare.level(5).var() ;                                   // (244)      05 UTIENT.
			Var utiste = declare.level(10).picX(3).var() ;                      // (245)         10 UTISTE  PIC X(3).
			Var uticpr = declare.level(10).picX(3).var() ;                      // (246)         10 UTICPR  PIC X(3).
		Var dicproc = declare.level(5).picX(8).var() ;                          // (247)      05 DICPROC    PIC X(8).
		Var trtref = declare.level(5).picX(3).var() ;                           // (248)      05 TRTREF     PIC X(3).
		Var filler$50 = declare.level(5).occurs(2).filler() ;                   // (249)      05 FILLER     OCCURS 2.
			Var orsparam = declare.level(10).picX(79).var() ;                   // (250)         10 ORSPARAM PIC X(79).
			Var suite = declare.level(10).picX(1).var() ;                       // (251)         10 SUITE   PIC X.
	 */
	public void startBatch(Var param)
	{
		Var returnCode = param.getVarChildAt(1);
		String job = param.getString().substring(0, 8).trim();		
		String account = param.getString().substring(8, 16).trim();
		String entity = param.getString().substring(16, 22).trim();
		String procedure = param.getString().substring(22, 30).trim();
		String reference = param.getString().substring(30, 33).trim();
		String card = param.getString().substring(33);

		int cardCount = (card.trim().length() / 80) + 1;
		String[] cards = new String[cardCount];
		for (int i=0, j=0; i < cardCount; i++, j+=80)
		{
			cards[i] = card.substring(j, j+80);
		}

		returnCode.set("0");
		try
		{
			String prefix = m_ProgramManager.getEnv().getConfigOption("StartBatchPrefix");
			String jobId = OnlineEnvironment.getNextJobBatchID();
			
			File temp = File.createTempFile(m_ProgramManager.getEnv().getTerminalID() + new DateUtil("HHmmssSSS").toString(), null);
			if (m_ProgramManager.getEnv().getConfigOption("StartBatchHostFtpUrl").equals(""))
			{
				startBatchLinuxPrepareFtp(temp, cards);
				String local = m_ProgramManager.getEnv().getConfigOption("StartBatchLinuxLocal");
				boolean isLocal = Boolean.parseBoolean(local);
				String ftpUrl = m_ProgramManager.getEnv().getConfigOption("StartBatchLinuxFtpUrl") ;
				String ftpUser = m_ProgramManager.getEnv().getConfigOption("StartBatchLinuxFtpUser") ;
				String ftpPassword = m_ProgramManager.getEnv().getConfigOption("StartBatchLinuxFtpPassword");				
				String ftpDirectory = m_ProgramManager.getEnv().getConfigOption("StartBatchLinuxFtpDirectory");
				String date = new DateUtil("yyyyMMdd").toString();
				String time = new DateUtil("HHmmssSSS").toString().substring(0, 7) + jobId.substring(2);
				String filename = "temp." + procedure + "." + date + "." + time;				
				int rc = startBatchLinuxFtp(isLocal, temp, ftpUrl, ftpUser, ftpPassword, ftpDirectory, filename);
				if (rc == 0)
				{
					String sshPath = m_ProgramManager.getEnv().getConfigOption("StartBatchLinuxSshPath");
					String sshUser = m_ProgramManager.getEnv().getConfigOption("StartBatchLinuxSshUser");
					String sshCommand = m_ProgramManager.getEnv().getConfigOption("StartBatchLinuxSshCommand");
					String jobclass = startBatchLinuxGetJobclass(cards);
					String jobname = job.substring(0, 4) + prefix + jobId.substring(1);
					rc = startBatchLinuxSsh(isLocal, sshPath, sshUser, ftpUrl, sshCommand, procedure, account, entity, reference, filename, date, time, jobclass, jobname);
				}
				returnCode.set(rc);
			}
			else
			{
				startBatchHostPrepareFtp(temp, job, account, entity, procedure, reference, cards, prefix, jobId);
				String ftpUrl = m_ProgramManager.getEnv().getConfigOption("StartBatchHostFtpUrl");
				String ftpUser = m_ProgramManager.getEnv().getConfigOption("StartBatchHostFtpUser");
				String ftpPassword = m_ProgramManager.getEnv().getConfigOption("StartBatchHostFtpPassword");
				String ftpSiteCommand = m_ProgramManager.getEnv().getConfigOption("StartBatchHostFtpSiteCommand");
				int rc = startBatchHostFtp(temp, ftpUrl, ftpUser, ftpPassword, ftpSiteCommand);
				returnCode.set(rc);
			}
			temp.delete();
		}
		catch (Exception ex) 
		{
			throw new RuntimeException(ex);
	    }
	}
	
	public int startBatchLinuxFtp(boolean isLocal, File temp, String url, String user, String password, String directoryRemote, String fileNameRemote) throws IOException
	{
//		int rc = 0;
//		
//		if (isLocal)
//		{
//			if (!FileSystem.copy(temp, new File(FileSystem.normalizePath(directoryRemote) + fileNameRemote)))
//				rc = 1;
//		}
//		else
//		{
//			FtpUtil ftp = new FtpUtil();
//			boolean bConnected = ftp.connect(url, user, password, null);
//			if (bConnected)
//			{
//				ftp.setFileTransferMode(2);
//				ftp.setFileType(2);
//				if (ftp.changeWorkingDirectory(directoryRemote))
//				{
//					if (!ftp.storeFile(new FileInputStream(temp), fileNameRemote)) 
//					{
//						rc = 2;
//					}
//				}
//				else
//				{
//					rc = 2;
//				}
//			}
//			else
//			{
//				rc = 1;
//			}
//			ftp.disconnect();
//		}
//		
//		return rc;
		return 0;
	}
		
	public int startBatchLinuxSsh(boolean isLocal, String sshPath, String sshUser, String url, String sshCommand, 
								  String procedure, String account, String entity, String reference, String fileName, String date, String time, String jobclass, String jobname)
	{
		int rc = 0;
		
		try
		{
			String[] commands;
			if (isLocal)
			{
				commands = new String[]{sshCommand,
						procedure,
						account,
						entity,
						reference,
						fileName,
						date,
						time,
						jobclass,
						jobname};
			}
			else
			{
				commands = new String[]{sshPath + "ssh",
						sshUser + "@" + url,
						sshCommand,
						procedure,
						account,
						entity,
						reference,
						fileName,
						date,
						time,
						jobclass,
						jobname};
			}
			Runtime.getRuntime().exec(commands);
		}
		catch (IOException e)
		{
			rc = 1;
		}
//		String command;
//		String[] args;
//		if (isLocal)
//		{
//			command = sshCommand;
//			args = new String[]{procedure,
//					account,
//					entity,
//					reference,
//					fileName,
//					date,
//					time,
//					jobclass,
//					jobname};
//		}
//		else
//		{
//			command = sshPath + "ssh";
//			args = new String[]{sshUser + "@" + url,
//					sshCommand,
//					procedure,
//					account,
//					entity,
//					reference,
//					fileName,
//					date,
//					time,
//					jobclass,
//					jobname};
//		}
//		if (!RunSystemCommand.runSystemCommand(command, args))
//			rc = 1;

		return rc;
	}
	
	public int startBatchHostFtp(File temp, String url, String user, String password, String siteCommand) throws IOException 
	{
//		int rc = 0;
//		
//		FtpUtil ftp = new FtpUtil();
//		if (ftp.connect(url, user, password, siteCommand))
//		{
//			if (!ftp.storeFile(new FileInputStream(temp), "XXX"))
//			{
//				rc = 2;
//			}
//		}
//		else
//		{
//			rc = 1;
//	    }
//		ftp.disconnect();
//		
//		return rc;
		return 0;
	}
	
	private void startBatchLinuxPrepareFtp(File temp, String[] cards) throws IOException 
	{
		BufferedWriter out = new BufferedWriter(new FileWriter(temp));
	    for (int i=0; i < cards.length; i++)
    	{
    		String cs = cards[i];
    		if (!(cs.startsWith("JOB") || cs.startsWith("OUTPUT") || cs.startsWith("PROC"))) 
    		{
    			out.write(cs);
    			out.write((char)FileEndOfLine.LF);
    		}
    	}
	    out.close();
	}
	private String startBatchLinuxGetJobclass(String[] cards) 
	{
		String jobCard = "";
		if (cards.length > 0)
		{
    		if (cards[0].startsWith("JOB"))
			{
				jobCard = cards[0];
			}
		}

		String jobclass = "";
		int nPos = jobCard.indexOf("CLASS=");
		if (nPos == -1)
		{
			String test = m_ProgramManager.getEnv().getConfigOption("StartBatchHostTest");
			boolean isTest = Boolean.parseBoolean(test);
			if (isTest)
			{
				jobclass = "A";
			}
			else
			{
				jobclass = "P";
			}
		}
		else
		{
			jobclass = jobCard.substring(nPos + 6, nPos + 7);
		}

		return jobclass;
	}
	
	private void startBatchHostPrepareFtp(File temp,
			   String job, String account, String entity, String procedure, String reference, String[] cards, 
			   String prefix, String jobId) throws IOException 
	{
		BufferedWriter out = new BufferedWriter(new FileWriter(temp));
		
		String test = m_ProgramManager.getEnv().getConfigOption("StartBatchHostTest");
		boolean isTest = Boolean.parseBoolean(test);
		
		int cardItem = 0;
		
		// job card
		String jobCard = "";
		if (cards.length > 0)
		{
    		String cs = cards[0];
			if (cs.startsWith("JOB"))
			{
				jobCard = "," + cs.substring(3).trim();
				cardItem = 1;
			}
		}

		String jobCardCopy = jobCard;
		String msgClass = "";
		if (jobCardCopy.indexOf("MSGCLASS=") == -1) 
		{
			if (isTest)
			{
				msgClass = ",MSGCLASS=7";
			}
			else
			{
				msgClass = ",MSGCLASS=X";
			}
		}
		else
		{
			jobCardCopy = jobCardCopy.replaceAll("MSGCLASS=", "");
		}

		String jobClass = "";
		if (jobCardCopy.indexOf("CLASS=") == -1) 
		{
			if (isTest)
			{
				jobClass = ",CLASS=A";
			}
			else
			{
				jobClass = ",CLASS=P";
			}
		}

		out.write("//");
		if (isTest)
		{
			out.write("C930" + reference + "T");
		}
		else
		{
			out.write(job.substring(0, 4) + jobId);
		}
		out.write(" JOB (" + account + "," + entity + "),");
		out.newLine();
		out.write("// " + m_ProgramManager.getEnv().getSQLConnection().getEnvironmentPrefix() + jobClass + msgClass + jobCard);
	
		// output card
		int cardOutput = 0;
		for (int i=cardItem; i < cards.length; i++) 
		{
	   		String cs = cards[i];
			if (cs.startsWith("OUTPUT")) 
			{
				cardOutput++;
			}
			else
			{
				break;
			}
		}
		if (cardOutput > 0)
		{
			for (int i=0; i < cardOutput; i++) 
			{
				if (i != 0)
				{
					out.write(",");
				}
				out.newLine();
				out.write("//");
				if (i == 0)
				{
					out.write("OUTDEF OUTPUT ");
				}
				else
				{
					out.write("       ");
				}
				out.write(cards[cardItem].substring(6).trim());
				cardItem++;
			}
		}
		
		// exec card
		String execName = "";
		for (int i=cardItem; i < cards.length; i++) 
		{
	   		String cs = cards[i];
			if (cs.startsWith("PROC"))
			{
				execName = cs.substring(4).trim();
				cardItem++;
			}
			else
			{
				break;
			}
		}
		out.newLine();
		out.write("//" + procedure + " EXEC " + procedure);
		out.write(",T='" + entity + "." + prefix + new DateUtil("HHmm").toString() + jobId.substring(2) + "'");
		out.write(",USER=" + reference);
		if (!execName.equals(""))
		{
			out.write(",");
			out.newLine();
			out.write("//       " + execName);
		}
		
		// orsparam card
		if (cardItem < cards.length)
		{
			out.newLine();
			out.write("//ST5.ORSPARAM DD *");
			for (; cardItem < cards.length; cardItem++) 
			{
				out.newLine();
				out.write(cards[cardItem]);
			}
			out.newLine();
			out.write("/*");
		}
		
		// end card
		out.newLine();
		out.write("//");
		out.close();
	}	
	
	public void dumpProgram(Var var1, Var var2)
	{
		JVMReturnCodeManager.setExitCode(var1.getInt());
		DumpProgramException dumpProgramException = new DumpProgramException(m_ProgramManager, var1, var2);
		throw dumpProgramException;
	}	
	
	public void dumpProgram(Var var1)
	{
		JVMReturnCodeManager.setExitCode(var1.getInt());
		DumpProgramException dumpProgramException = new DumpProgramException(m_ProgramManager, var1, null);
		throw dumpProgramException;
	}	
	
	public void formatSQLCode(Var varErrorMessage, Var varErrorTextLen)
	{
		int nSizeBufferError = varErrorMessage.getVarChildAt(1).getInt();
		int nSizeLine = varErrorTextLen.getInt();
		
		CSQLStatus sqlStatus = m_ProgramManager.getSQLStatus();
		if (sqlStatus != null)
		{		
			varErrorMessage.getVarChildAt(2).set(sqlStatus.toString());
		}
	}
	
	public void getJobInfo(VarFPacLengthUndef jobInfo)
	{
		Var varSource = jobInfo.createVar();
		getJobInfo(varSource);
	}
	public void getJobInfo(Var jobInfo)
	{
		String jobId = getJobInfoForKey("JOBID");
		String stepId = getJobInfoForKey("STEPID");
		String procId = getJobInfoForKey("PROCID");
		jobInfo.set(StringUtil.rightPad(jobId, 8, ' ') +
				StringUtil.rightPad(stepId, 8, ' ') +
				StringUtil.rightPad(procId, 8, ' '));
	}
	public void getJobInfo(Var jobInfo, Var account1Info)
	{
		getJobInfo(jobInfo);		
		String account1 = getJobInfoForKey("ACCOUNT1");				
		account1Info.set(StringUtil.rightPad(account1, 8, ' '));
	}
	public void getJobInfo(Var jobInfo, Var account1Info, Var account2Info)
	{
		getJobInfo(jobInfo, account1Info);		
		String account2 = getJobInfoForKey("ACCOUNT2");		
		account2Info.set(StringUtil.rightPad(account2, 8, ' '));
	}
	public String getJobInfoForKey(String key) {
		String value = EnvironmentVar.getParamValue(key);
		if (value.equals("") && m_ProgramManager.getEnv().getBaseSession() != null)
			value = m_ProgramManager.getEnv().getBaseSession().getLogicalJobInfo(key);
		return value;
	}

	public int getCardKey(Var card, Var key, Var value, Var index)
	{
		String csCard = card.getString().trim();
		if (csCard.length() > 1 && csCard.charAt(csCard.length() - 1) == '*')
			csCard = csCard.substring(0, csCard.length() - 1).trim();
		String[] parms = csCard.split(",");
		int pos = new Integer(index.getDottedSignedString()).intValue();
		
		if (parms.length > pos + 1)
		{
			pos++;
			String keyFound = null;
			String valueFound = null;
			int posEqual = parms[pos].indexOf('=');
			if (posEqual == -1)
			{
				return 8;
			}
			else
			{
				keyFound = parms[pos].substring(0, posEqual).trim();
				valueFound = parms[pos].substring(posEqual + 1).trim();
				if (valueFound.length() > 1 && valueFound.charAt(0) == '\'' &&
						                       valueFound.charAt(valueFound.length() - 1) == '\'')
				{
					valueFound = valueFound.substring(1, valueFound.length() - 1);
				}
				if (keyFound.length() > key.getVarChildAt(2).getLength())
					return 4;
				if (valueFound.length() > value.getVarChildAt(2).getLength())
					return 12;
				key.getVarChildAt(2).set(keyFound);
				value.getVarChildAt(2).set(valueFound);
			}
			if (parms.length > pos + 1)
				index.set(pos);
			else
				index.set(-1);
		}
		else
		{
			index.set(-1);
		}
		return 0;
	}
	public void getCardKey(Var card, Var key, Var value, Var index, int returnCode)
	{
		JVMReturnCodeManager.setExitCode(getCardKey(card, key, value, index));
	}

	public void dynamicAllocation(Var[] params)
	{
		int operation = new Integer(params[0].getDottedSignedString()).intValue();
		
		if (operation == 1 || operation == 11)
		{
			for (int i=1; i < params.length; i++)
			{
				String key = params[i].getString().substring(0, 8).trim();
				String value;
				if (key.equals("PRTY") || key.equals("COPIES"))
				{
					value = params[i].getVarChildAt(2).getDottedSignedString();
				}
				else
				{
					value = params[i].getString().substring(8).trim();
				} 
				m_ProgramManager.getEnv().getBaseSession().addDynamicAllocationInfo(key, value);
			}
		}
		else
		{
			Assert("dynamicAllocation doesn't implement the operation " + operation);
		}
		
		if (operation == 1)
		{
			String dynamicAllocationPath = FileSystem.normalizePath(BaseResourceManager.getDynamicAllocationPath());
			
			String ddname = m_ProgramManager.getEnv().getBaseSession().getDynamicAllocationInfo("DDNAME");
			String dsn = m_ProgramManager.getEnv().getBaseSession().getDynamicAllocationInfo("DSN");

			if (dsn == null)
			{
				// InfoPrint Manager
				Date date = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
				String csDate = formatter.format(date);
				formatter.applyPattern("HHmm");
				String csHour = formatter.format(date);
				dsn = dynamicAllocationPath + 
					"temp/linux." +
					getJobInfoForKey("JOBID") +
					"." +
					getJobInfoForKey("STEPID") +
					".I" + csDate +
					".H" + csHour + 
					m_ProgramManager.getEnv().getBaseSession().getNextDynamicAllocationID();
				dsn = dsn.toLowerCase();
			}
			else
			{
				boolean bSearchGeneration = false;
				int nGeneration = 0;
				if (dsn.length() > 44)
				{
					bSearchGeneration = true;
					nGeneration = new Integer(dsn.substring(44)).intValue();					
					dsn = dsn.substring(0, 44);
				}
				dsn = dsn.toLowerCase();
				int pos = dsn.indexOf(".");
				if (pos == - 1)
				{
					dsn = dynamicAllocationPath + dsn;
				}
				else
				{
					dsn = dynamicAllocationPath + dsn.substring(0, pos) + "/" + dsn.substring(pos + 1);
				}
				if (bSearchGeneration)
				{
					String dsnBefore = dsn;
					FileSearchGeneration fileSearch = new FileSearchGeneration();
					dsn = fileSearch.search(dsn, nGeneration);
					if (dsn == null)
					{
						throw new AssertException("Cannot search a generation " + nGeneration + " for the file " + dsnBefore);
					}
				}
			}
			EnvironmentVar.registerProgramVar(ddname, dsn);
		}
	}

	public void byteToBitInEbcdic(Var inBytes, Var outBits, Var length)
	{
		String inBytesInEbcdic = AsciiEbcdicConverter.getEbcdicString(inBytes.getString());
		int len = new Integer(length.getDottedSignedString()).intValue();

		String cs = "";
		for (int i=0; i < len; i++)
		{
            cs += Integer.toBinaryString(inBytesInEbcdic.charAt(i));
        }

        outBits.set(cs);
	}

	public void bitToByteInEbcdic(Var outBytes, Var inBits, Var length)
	{
		int len = new Integer(length.getDottedSignedString()).intValue();
		String cs = inBits.getString();
		
		String csX = "";
		for (int i = 0, k = 0; i < (len * 8) + 1; i++, k++)
		{
			if (k == 8)
			{
				k = 0;
				csX += AsciiEbcdicConverter.getAsciiChar((char)(Integer.parseInt(cs.substring(i - 8, i), 2)));
			}
		}
        outBytes.set(csX);
	}

	/*public void formatPSFLoadFont(Var nbFont, Var font, Var returnCode)
	{
		TextPrintHelper textPrintHelper = formatPSFTextGetInstance();
		ReturnCode rc = textPrintHelper.defineFonts(FontSets.fontset1);
		returnCode.set(rc.getCode());
	}
	*/
	public void formatPSFLoadColumn(Var nbColumn, Var rule, Var interline, Var returnCode)
	{
		//TextPrintHelper textPrintHelper = formatPSFTextGetInstance();

//		Var s31_Regle = declare.level(5).var() ;                                // (1443)     05 S31-REGLE.                                                 
//			Var s31_Colonne = declare.level(10).occurs(4).var() ;               // (1444)        10 S31-COLONNE OCCURS 4.                                   
//				Var s31_Pos = declare.level(15).picS9(9).comp().var() ;         // (1445)           15 S31-POS            PIC S9(9) COMP.                   
//				Var s31_Large = declare.level(15).picS9(9).comp().var() ;       // (1446)           15 S31-LARGE          PIC S9(9) COMP.                   
//				Var s31_Flags = declare.level(15).picS9(9).comp().var() ;       // (1447)           15 S31-FLAGS          PIC S9(9) COMP.                   
//				Var s31_Aligne = declare.level(15).picS9(9).comp().var() ;      // (1448)           15 S31-ALIGNE         PIC S9(9) COMP.                   
//				Var s31_Lmode = declare.level(15).picS9(9).comp().var() ;       // (1449)           15 S31-LMODE          PIC S9(9) COMP.                   
//				Var s31_Yline = declare.level(15).picS9(9).comp().var() ;       // (1450)           15 S31-YLINE          PIC S9(9) COMP.                   
//				Var s31_Cyline = declare.level(15).picS9(9).comp().var() ;      // (1451)           15 S31-CYLINE         PIC S9(9) COMP.                   
//				Var s31_Trc = declare.level(15).picX(1).var() ;                 // (1452)           15 S31-TRC            PIC X.                            
//				Var filler$273 = declare.level(15).picX(3).filler() ;           // (1453)           15 FILLER             PIC XXX.
		/*Var varColonne = rule.getVarChildAt(1);
		Var varPos = varColonne.getVarChildAt(1);
		Var varLarge = varColonne.getVarChildAt(2);
		Var varFlags = varColonne.getVarChildAt(3);
		Var varAligne = varColonne.getVarChildAt(4);
		Var varLmode = varColonne.getVarChildAt(5);
		Var varYline = varColonne.getVarChildAt(6);
		Var varCyline = varColonne.getVarChildAt(7);
		Var varTrc = varColonne.getVarChildAt(8);		
				
		ColumnDef[] cols = new ColumnDef[nbColumn.getInt()];
		for (int i=0, j=1; i < nbColumn.getInt(); i++, j++)
		{
			int nPos = varPos.getAt(j).getInt();
			int nLarge = varLarge.getAt(j).getInt();
			int nFlags = varFlags.getAt(j).getInt();
			int nAligne = varAligne.getAt(j).getInt();
			int nLmode = varLmode.getAt(j).getInt();
			int nYline = varYline.getAt(j).getInt();
			int nCyline = varCyline.getAt(j).getInt();
			String csTrc = varTrc.getAt(j).getString();
			cols[i] = new ColumnDef(nPos, nLarge, flags, quad, nLmode, nYline, nCyline, cfid);
		}
		FormatDef format = new FormatDef(cols, interline.getInt());*/
		/*
		FormatDef format = null;
		int nRule = rule.getVarChildAt(1).getVarChildAt(9).getAt(1).getInt();		
		switch (nRule)
		{
			case 1:
				format = FormatsSet1.format1;
				break;
			case 2:
				format = FormatsSet1.format2;
				break;
			case 3:
				format = FormatsSet1.format3;
				break;
			case 4:
				format = FormatsSet1.format4;
				break;
			case 5:
				format = FormatsSet1.format5;
				break;
			case 6:
				format = FormatsSet1.format6;
				break;
			case 7:
				format = FormatsSet1.format7;
				break;
			case 8:
				format = FormatsSet1.format8;
				break;
			case 9:
				format = FormatsSet1.format9;
				break;
			case 10:
				format = FormatsSet1.format10;
				break;
			case 11:
				format = FormatsSet1.format11;
				break;
			case 12:
				format = FormatsSet1.format12;
				break;
			case 13:
				format = FormatsSet1.format13;
				break;
			case 14:
				format = FormatsSet1.format14;
				break;
			case 15:
				format = FormatsSet1.format15;
				break;
			case 16:
				format = FormatsSet1.format16;
				break;
			default:
				Assert("formatPSFLoadColumn doesn't implement the rule " + rule);
				break;
		}
		ReturnCode rc = textPrintHelper.defineFormats(format);
		returnCode.set(rc.getCode());
		*/
	}
	public void formatPSFText(Var input, Var blockMode, Var output, Var blockHeight, Var returnCode)
	{
		//TextPrintHelper textPrintHelper = formatPSFTextGetInstance();
		
//		Var s32_Input = declare.level(5).var() ;                                // (2497)      05 S32-INPUT.                                                
//			Var s32_Inbch = declare.level(10).picS9(9).comp().var() ;           // (2498)         10 S32-INBCH             PIC S9(9) COMP.                  
//			Var s32_Imaxch = declare.level(10).picS9(9).comp().value(4001).var() ; // (2499)      10 S32-IMAXCH            PIC S9(9) COMP VALUE 4001.
//			Var s32_Intxt = declare.level(10).var() ;                           // (2500)         10 S32-INTXT.                                             
//				Var s32_Ichar = declare.level(15).occurs(4001).picX(1).var() ;  // (2501)            15 S32-ICHAR OCCURS 4001 PIC X.
		
		/*ReturnCode rc;
		PSFOutputBuffer psf = new PSFOutputBuffer(4001);
		if (blockMode.getInt() == 1)
			rc = textPrintHelper.printText(input.getVarChildAt(3).getString().substring(0, input.getVarChildAt(1).getInt()), TextBlockMode.BLKM_MORE, psf);
		else
			rc = textPrintHelper.printText(input.getVarChildAt(3).getString().substring(0, input.getVarChildAt(1).getInt()), TextBlockMode.BLKM_ENDOFBLOCK, psf);
		
//		Var s32_Output = declare.level(5).var() ;                               // (2493)      05 S32-OUTPUT.                                               
//			Var s32_Onbch = declare.level(10).picS9(9).comp().var() ;           // (2494)         10 S32-ONBCH             PIC S9(9) COMP.                  
//			Var s32_Omaxch =declare.level(10).picS9(9).comp().value(4001).var();// (2495)         10 S32-OMAXCH            PIC S9(9) COMP VALUE 4001.       
//			Var s32_Outxt = declare.level(10).var() ;                           // (2496)         10 S32-OUTXT.                                             
//				Var s32_Ochar = declare.level(15).occurs(4001).picX(1).var() ;  // (2497)            15 S32-OCHAR OCCURS 4001 PIC X.
		output.getVarChildAt(1).set(psf.getLength() - 1);
		output.getVarChildAt(3).importFromByteArray(psf.getBytes(), psf.getLength());		
		blockHeight.set(psf.getHeight());
		returnCode.set(rc.getCode());*/
	}
	public void formatPSFLine(Var input, Var blockMode, Var output, Var blockHeight, Var returnCode)
	{
		//TextPrintHelper textPrintHelper = formatPSFTextGetInstance();
		
//		Var s33_Input = declare.level(5).var() ;                                // (2508)      05 S33-INPUT.                                                
//			Var s33_Pos = declare.level(10).picS9(9).comp().var() ;             // (2509)         10 S33-POS               PIC S9(9) COMP.                  
//			Var s33_Large = declare.level(10).picS9(9).comp().var() ;           // (2510)         10 S33-LARGE             PIC S9(9) COMP.                  
//			Var s33_Yline = declare.level(10).picS9(9).comp().var() ;           // (2511)         10 S33-YLINE             PIC S9(9) COMP.                  
//			Var s33_Cyline = declare.level(10).picS9(9).comp().var() ;          // (2512)         10 S33-CYLINE            PIC S9(9) COMP.
		/*LineDef lineDef = new LineDef(input.getVarChildAt(1).getInt(), input.getVarChildAt(2).getInt(), input.getVarChildAt(3).getInt(), input.getVarChildAt(4).getInt());
		
		ReturnCode rc;
		PSFOutputBuffer psf = new PSFOutputBuffer(4001);
		if (blockMode.getInt() == 1)			
			rc = textPrintHelper.drawLine(lineDef, TextBlockMode.BLKM_MORE, psf);
		else
			rc = textPrintHelper.drawLine(lineDef, TextBlockMode.BLKM_ENDOFBLOCK, psf);

//		Var s32_Output = declare.level(5).var() ;                               // (2493)      05 S32-OUTPUT.                                               
//			Var s32_Onbch = declare.level(10).picS9(9).comp().var() ;           // (2494)         10 S32-ONBCH             PIC S9(9) COMP.                  
//			Var s32_Omaxch =declare.level(10).picS9(9).comp().value(4001).var();// (2495)         10 S32-OMAXCH            PIC S9(9) COMP VALUE 4001.       
//			Var s32_Outxt = declare.level(10).var() ;                           // (2496)         10 S32-OUTXT.                                             
//				Var s32_Ochar = declare.level(15).occurs(4001).picX(1).var() ;  // (2497)            15 S32-OCHAR OCCURS 4001 PIC X.
		output.getVarChildAt(1).set(psf.getLength() - 1);
		output.getVarChildAt(3).setFromByteArray(psf.getBytes(), 0, psf.getLength());		
		blockHeight.set(psf.getHeight());
		returnCode.set(rc.getCode());*/
	}
	/*
	private TextPrintHelper formatPSFTextGetInstance()
	{
		TextPrintHelper textPrintHelper = (TextPrintHelper)m_ProgramManager.getEnv().getBaseSession().getSpecialObject("TextPrintHelper");
		if (textPrintHelper == null)
		{
			textPrintHelper = new TextPrintHelper();
			m_ProgramManager.getEnv().getBaseSession().addSpecialObject("TextPrintHelper", textPrintHelper);
		}
		return textPrintHelper;
	}
	*/
	public void formatPSFOrderText(Var input, Var output)
	{
		/*
		ImagePrintHelper imagePrintHelper = (ImagePrintHelper)m_ProgramManager.getEnv().getBaseSession().getSpecialObject("ImagePrintHelper");
		if (imagePrintHelper == null)
		{
			imagePrintHelper = new ImagePrintHelper();
			m_ProgramManager.getEnv().getBaseSession().addSpecialObject("ImagePrintHelper", imagePrintHelper);
		}
		imagePrintHelper.setProgramManager(m_ProgramManager);
		imagePrintHelper.print(input, output);
		*/
	}
	
	public void formatXMLOrder(Var input, Var output)
	{
		/*
		XmlPrintHelper xmlPrintHelper = (XmlPrintHelper)m_ProgramManager.getEnv().getBaseSession().getSpecialObject("XmlPrintHelper");
		if (xmlPrintHelper == null)
		{
			xmlPrintHelper = new XmlPrintHelper();
			m_ProgramManager.getEnv().getBaseSession().addSpecialObject("XmlPrintHelper", xmlPrintHelper);
		}
		xmlPrintHelper.setProgramManager(m_ProgramManager);
		xmlPrintHelper.print(input, output);
		*/
	}
	
	public void restartFile(Var outputFile)
	{
		String csLogicalFileName = outputFile.getVarChildAt(1).getString().trim();
		int nNbRecordsToKeep = outputFile.getVarChildAt(2).getInt();
		restartFile(csLogicalFileName, nNbRecordsToKeep);
	}
	
	public void restartFile(String csLogicalFileName, int nNbRecordsToKeep)
	{
		FileDescriptor file = new FileDescriptor(csLogicalFileName, m_ProgramManager.getEnv().getBaseSession());
		moveEndOfFilePointer(file, nNbRecordsToKeep);	
	}
	
	private int moveEndOfFilePointer(FileDescriptor file, int nNbRecordsToKeep)
	{
		String csPhysicalFileName = file.getPhysicalName();
		if (BaseDataFile.isNullFile(csPhysicalFileName))
			return -1;

		int nNbRecordRead = 0;
		DataFileReadWrite dataFileIn = new DataFileReadWrite();
		LogicalFileDescriptor logicalFileDescriptor = file.getLogicalFileDescriptor();
		if(logicalFileDescriptor != null)
		{
			dataFileIn.setName(csPhysicalFileName);
			boolean bInOpened = dataFileIn.open(logicalFileDescriptor);
			if(bInOpened)
			{
				if(!logicalFileDescriptor.isLengthInfoDefined())
				{
					logicalFileDescriptor.tryAutoDetermineRecordLength(dataFileIn);
				}
				long lNbBytesRead = logicalFileDescriptor.getFileHeaderLength();
				LineRead lineRead = file.readALine(dataFileIn, null);
				while(lineRead != null && nNbRecordRead < nNbRecordsToKeep)
				{
					nNbRecordRead++;
					if (logicalFileDescriptor.isVariableLength())
						lNbBytesRead += 4;
					lNbBytesRead += lineRead.getBodyLength() + 1;
					lineRead = file.readALine(dataFileIn, lineRead);
				}
				if(nNbRecordRead == nNbRecordsToKeep)	// Read all records to keep
				{
					dataFileIn.setFileLength(lNbBytesRead);
				}
				dataFileIn.close();
				return nNbRecordRead;
			}
		}
		Log.logCritical("Could not open file " + csPhysicalFileName);	
		return -1;
	}
	
	public void doEncodingUTF8(Var input, Var output)
	{
		String utf8 = input.getString().trim();
		utf8 = utf8.replace("&", "&amp;");
		utf8 = utf8.replace("\"", "&quot;");		
		utf8 = utf8.replace("'", "&apos;");
		utf8 = utf8.replace("<", "&lt;");
		utf8 = utf8.replace(">", "&gt;");
		byte[] utf8Bytes;
		try
		{
			utf8Bytes = utf8.getBytes("UTF8");			
		}
		catch (Exception ex)
		{
			utf8Bytes = utf8.getBytes();
		}
		output.set("");
		output.setFromByteArray(utf8Bytes, 0, utf8Bytes.length);
	}
}