/*
 * NacaRTTests - Naca Tests for NacaRT support v1.2.0.
 *
 * Copyright (c) 2005, 2006, 2007, 2008, 2009 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * NacaRTTests - Naca Tests for NacaRT support.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under GPL (GPL-LICENSE.txt) license.
 */
/*
 * Created on 19 avr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author u930di
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import idea.onlinePrgEnv.OnlineProgram;
import jlib.log.Asserter;
import jlib.log.Log;
import nacaLib.program.Paragraph;
import nacaLib.varEx.DataSection;
import nacaLib.varEx.Var;


public class TestWorking2 extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();
	
	Var wmonarr = declare.level(77).picS9(7,4).comp3().valueZero().var() ;      // (34)  77  WMONARR                PIC S9(7)V9(4)  COMP-3 VALUE ZERO.
	
	Var tsG1 = declare.level(1).occurs(5).var() ;
		Var tsG11 = declare.level(5).picX(5).var() ;
		Var tsG20 = declare.level(5).occurs(5).var() ;
			Var tsG21 = declare.level(10).picX(5).var() ;
			Var tsG22 = declare.level(10).pic9(5).var() ;
		Var tsG12 = declare.level(5).pic9(5).var() ;
	
	Var ts_Zone = declare.level(1).var() ;                                                                            
		Var ts_Nbre = declare.level(5).picS9(4).comp().var() ;                          
		Var ts_Occ = declare.level(5).occurs(5).var() ;                                     
			Var ts_item1 = declare.level(10).picX(5).var() ;                                  
			Var ts_item2 = declare.level(10).picX(2).var() ;     
			Var ts_item3 = declare.level(10).occurs(2).var() ;   
				Var ts_item31 = declare.level(15).picX(1).var() ;
				Var ts_item32 = declare.level(15).picX(1).value("Z").var();
			Var ts_item4 = declare.level(10).occurs(2).var() ;   
				Var ts_item41 = declare.level(15).picX(1).var() ;
				Var ts_item42 = declare.level(15).occurs(3).var() ;
					Var ts_item421 = declare.level(20).picX(1).value("T").var() ;
					Var ts_item422 = declare.level(20).pic9(2).value(12).var() ;

	
	TestWorking2Copy include1 = TestWorking2Copy.Copy(this);
	
	Var varMain = declare.level(1).var() ;                                  // (57)  01  DFHCOMMAREA.                                                 
		TestWorking2CopyReplacing include2 = TestWorking2CopyReplacing.Copy(this, replacing(1, 2));
		
	Var OccuGroups = declare.level(1).occurs(3).var() ;	
			Var Group = declare.level(5).var() ;
				Var Item1 = declare.level(10).picX(1).var() ;
				Var Item2 = declare.level(10).pic9(2).var() ;
				
	Var vDest = declare.level(1).picX(200).var() ;
	
	Var debmont = declare.level(1).picS9(14,4).comp3().var() ;
	
	Var vd1 = declare.level(1).picS9(3, 2).var() ;
	Var vd2redef = declare.level(1).redefines(vd1).var() ;
		Var vd2redef1 = declare.level(5).pic9(2).var() ;
		Var vd2redef2 = declare.level(5).pic9(1).var() ;
		Var vd2redef3 = declare.level(5).pic9(2).var() ;
	
	TestWorking2Copy2 include3 = TestWorking2Copy2.Copy(this);
	
	Var v1 = declare.level(1).var();
		Var v21a = declare.level(5).picX(1).var();	// include3.v21a
		Var v22a$v1 = declare.level(5).picX(2).var();	// include3.v22a
		Var v23a$v1 = declare.level(5).picX(3).var();	// no include3
		Var v24a = declare.level(5).picX(4).var();		// include3.v24a
		Var v25a = declare.level(5).picX(5).var();		// no include3
		Var v26a = declare.level(5).picX(6).var();		// include3.v26a$v1
		Var v27a = declare.level(5).picX(7).var();		// include3.v27a
		// no v28a
			
//	VRS7H01M vrs7h01m = VRS7H01M.Copy(this);	
	Var zone_Dvrs7h01m = declare.level(1).var() ;                               // (325)  01  ZONE-DVRS7H01M.                                              
		Var utiste = declare.level(10).picX(2).var() ;                          // (326)      10 UTISTE               PIC X(2).                            
		Var utiento = declare.level(10).picX(5).var() ;                         // (327)      10 UTIENTO              PIC X(5).                            
		Var cdenum = declare.level(10).picX(6).var() ;                          // (328)      10 CDENUM               PIC X(6).                            
		Var utistec = declare.level(10).picX(2).var() ;                         // (329)      10 UTISTEC              PIC X(2).                            
		Var utientc$zone_Dvrs7h01m = declare.level(10).picX(5).var() ;          // (330)      10 UTIENTC              PIC X(5).                            
		Var clino$zone_Dvrs7h01m = declare.level(10).picX(6).var() ;            // (331)      10 CLINO                PIC X(6).                            
		Var clicch$zone_Dvrs7h01m = declare.level(10).picX(3).var() ;           // (332)      10 CLICCH               PIC X(3).                            
		Var cliadmn = declare.level(10).picX(3).var() ;                         // (333)      10 CLIADMN              PIC X(3).     

	Var dtlligne = declare.level(1).var() ;                                     // (440)  01  DTLLIGNE.                                                    
		Var det1_Trc = declare.level(5).picX(1).valueSpaces().var() ;           // (441)      05 DET1-TRC             PIC X VALUE SPACE.                   
		Var filler$39 = declare.level(5).picX(1).valueSpaces().filler() ;       // (442)      05 FILLER               PIC X VALUE SPACE.                   
		Var det1_Cdlib = declare.level(5).pic9(13).blankWhenZero().var() ;       // (443)      05 DET1-CDLIB           PIC 999 BLANK WHEN ZERO.             
		Var filler$40 = declare.level(5).picX(1).valueSpaces().filler() ;       // (444)      05 FILLER               PIC X VALUE SPACE.                   
		Var det1_Lib = declare.level(5).picX(14).var() ;                        // (445)      05 DET1-LIB             PIC X(14).                           
		Var filler$41 = declare.level(5).picX(1).valueSpaces().filler() ;       // (446)      05 FILLER               PIC X VALUE SPACE.                   
		Var det1_Cen = declare.level(5).pic("ZZZZZZ9").var() ;                  // (447)      05 DET1-CEN             PIC ZZZZZZ9.                         
		Var filler$42 = declare.level(5).picX(3).valueSpaces().filler() ;       // (448)      05 FILLER               PIC XXX VALUE SPACE.                 
		Var det1_Suc = declare.level(5).pic("ZZZZZZ9").var() ;                  // (449)      05 DET1-SUC             PIC ZZZZZZ9.                         
		Var filler$43 = declare.level(5).picX(3).valueSpaces().filler() ;       // (450)      05 FILLER               PIC XXX VALUE SPACE.                 
		Var det1_Tot = declare.level(5).pic("ZZZZZZ9").var() ;                  // (451)      05 DET1-TOT             PIC ZZZZZZ9.                         
		Var filler$44 = declare.level(5).picX(5).valueSpaces().filler() ;       // (452)      05 FILLER               PIC X(5) VALUE SPACE.                
		Var det1_Cafac = declare.level(5).pic("ZBZZZBZZZBZZ9.99-").var() ;      // (453)      05 DET1-CAFAC           PIC ZBZZZBZZZBZZ9.99-.               
		Var filler$45 = declare.level(5).picX(2).valueSpaces().filler() ;       // (454)      05 FILLER               PIC XX VALUE SPACE.                  
		Var det1_0fac = declare.level(5).pic("ZZ9.9").var() ;                   // (455)      05 DET1-0FAC           PIC ZZ9.9.                            
		Var filler$46 = declare.level(5).picX(5).valueSpaces().filler() ;       // (456)      05 FILLER               PIC X(5) VALUE SPACE.                
		Var det1_Mtfacf = declare.level(5).pic("ZZZBZZZBZZ9.99-").var() ;       // (457)      05 DET1-MTFACF         PIC ZZZBZZZBZZ9.99-.                  
		Var filler$47 = declare.level(5).picX(19).valueSpaces().filler() ;      // (458)      05 FILLER               PIC X(19) VALUE SPACE.      

	Var XXX = declare.level(5).picX(3).var() ;       // (443)      05 DET1-CDLIB           PIC 999 BLANK WHEN ZERO.
	Var V9_8 = declare.level(5).pic9(8).var() ;
	Var VX_8 = declare.level(5).picX(8).var() ;
	
	Var calc_An = declare.level(1).pic9(4).valueZero().var() ;
	Var wk_Jnldtpa = declare.level(1).pic9(8).valueZero().var() ;
	
	Var piccomp36 = declare.level(1).picS9(6).comp3().var() ;
	Var piccomp37 = declare.level(1).picS9(7).comp3().var() ;
	Var piccomp38 = declare.level(1).picS9(8).comp3().var() ;
		
	Var ors_Utiste = declare.level(5).picX(2).valueSpaces().var() ;         // (121)      05 ORS-UTISTE                 PIC XX          VALUE SPACE.   
	Var ors_Utiento = declare.level(5).picX(5).valueSpaces().var() ;        // (122)      05 ORS-UTIENTO                PIC X(5)        VALUE SPACE.   
	Var ors_Cligenr = declare.level(5).picX(1).valueSpaces().var() ;        // (123)      05 ORS-CLIGENR                PIC X           VALUE SPACE.   
	Var ors_Conccc = declare.level(5).picX(1).valueSpaces().var() ;         // (124)      05 ORS-CONCCC                 PIC X           VALUE SPACE.
	Var sw_Code_Ors = declare.level(77).picX(9).valueSpaces().var() ;           // (109)  77  SW-CODE-ORS                   PIC X(9)        VALUE SPACE.
	
	Var TST = declare.level(77).pic9(3,2).comp3().var() ;           // (109)  77  SW-CODE-ORS                   PIC X(9)        VALUE SPACE.
	Var TSTS = declare.level(77).picS9(3,2).comp3().var() ;           // (109)  77  SW-CODE-ORS                   PIC X(9)        VALUE SPACE.
	Var VS94 = declare.level(77).picS9(5).comp().var() ;           // (109)  77  SW-CODE-ORS                   PIC X(9)        VALUE SPACE.
	
	Var vX9 = declare.level(77).picX(9).var();
	Var vX9b = declare.level(77).picX(9).var();
	Var vX10 = declare.level(77).picX(10).var();
	Var vX15 = declare.level(77).picX(15).var();
	Var v901 = declare.level(77).pic9(0, 1).var();
	Var v931 = declare.level(77).pic9(4, 1).comp3().var();
	Var v931b = declare.level(77).pic9(4, 1).comp3().var();
	
	Var v932 = declare.level(77).pic9(3, 2).comp3().var();
	
	public void procedureDivision()
	{
		Asserter.setAssertActive(true);
		
		moveLowValue(tsG1);
		Var v0 = tsG21.getAt(2, 3);
		assertIfFalse(isLowValue(v0));
		move("AAAAA", v0);
		assertIfFalse(v0.equals("AAAAA"));
				
		for(int i=1; i<=5; i++)
		{
			move("aaaa"+i, tsG11.getAt(i));
			for(int j=1; j<=5; j++)
			{
				move("a"+i+"b"+j+"c", tsG21.getAt(i, j));
				move(10000+(1000*i)+(10*j)+2, tsG22.getAt(i, j));
			}
			move(i*10, tsG12.getAt(i));
		}
		for(int i=1; i<=5; i++)
		{
			assertIfDifferent("aaaa"+i, tsG11.getAt(i));
			for(int j=1; j<=5; j++)
			{
				assertIfDifferent("a"+i+"b"+j+"c", tsG21.getAt(i, j));
				assertIfDifferent(10000+(1000*i)+(10*j)+2, tsG22.getAt(i, j));
			}
			assertIfDifferent(i*10, tsG12.getAt(i));
		}
		
		//move("01234567890123456789012345678901234567890123456789", tsG20.getAt(2));
		initialize(tsG20.getAt(2, 1));
		initialize(tsG20.getAt(2, 5));
		
		initialize(tsG21.getAt(2, 3));
		
		
		move(123.45, v931);
		move(v931, v931b);
		boolean bbb = isEqual(v931, v931b);
		assertIfFalse(bbb);

		move(123.45, v932);
		move(v932, v931b);
		bbb = isEqual(v932, v931b);
		assertIfFalse(!bbb);

//		move(v931, v901);
//		
//		move(99.5, v901);
//		move("12.7", v901);
//		
//		move(1234.543, v901);
		
		move("123456789", vX9);
		assertIfFalse(vX9.getString().equals("123456789"));

		move(vX9, vX9b);
		assertIfFalse(vX9b.getString().equals("123456789"));

		move(vX9, vX10);
		assertIfFalse(vX10.getString().equals("123456789 "));
		
		move(vX9, vX15);
		assertIfFalse(vX15.getString().equals("123456789      "));
		
		move("0987654321", vX10);
		assertIfFalse(vX10.getString().equals("0987654321"));
		
		move(vX10, vX15);
		assertIfFalse(vX15.getString().equals("0987654321     "));
		
		move(vX10, vX9);
		assertIfFalse(vX9.getString().equals("098765432"));

		move("012345678901234", vX15);
		assertIfFalse(vX15.getString().equals("012345678901234"));
		
		move(vX15, vX10);
		assertIfFalse(vX10.getString().equals("0123456789"));
		
		move(vX15, vX9);
		assertIfFalse(vX9.getString().equals("012345678"));


		move(28986, ts_Nbre);
		
		initialize(ts_Zone);
		initialize(ts_Nbre);
		
		move(1234, VS94);
		int nS94 = VS94.getInt();
		assertIfDifferent(1234, nS94);
		
		move(-12.04, TST);
		String cs0 = TST.getString();
		assertIfDifferent("12.04", TST);
		double d = TST.getDouble();
		assertIfFalse(d == 12.04);

		move(-12.04, TSTS);
		cs0 = TSTS.getString();
		assertIfDifferent("-12.04", TSTS);
		double dS = TSTS.getDouble();
		assertIfFalse(dS == -12.04);

//		move("1234", det1_Cdlib);
//		move("567", det1_Cdlib);
//		move("89", det1_Cdlib);
//		move("4", det1_Cdlib);
//		move("0", det1_Cdlib);
//		move(det1_Cdlib, XXX);
//		move(det1_Cdlib, V9_8);
//		move(V9_8, det1_Cdlib);
		
		move(1, det1_Cdlib);
		dec(det1_Cdlib);
		assertIfDifferent(0, det1_Cdlib);
		
		move(1236, wk_Jnldtpa);
		moveHighValue(det1_Lib);
		boolean b0 = isGreater(det1_Lib, wk_Jnldtpa);
		assertIfFalse(b0);
		
		boolean b1 = isGreater(wk_Jnldtpa, det1_Lib);
		assertIfFalse(!b1);
								
		move("a", ors_Utiste);
		move("b", ors_Utiento);
		move("c", ors_Cligenr);
		move("d", ors_Conccc);
		concat(ors_Utiste).concat(ors_Utiento).concat(ors_Cligenr).             // (365)      STRING ORS-UTISTE                                            
		concat(ors_Conccc).into(sw_Code_Ors) ;
		assertIfDifferent("a b    cd", sw_Code_Ors);
		
		move(601538, piccomp36);
		move(601538, piccomp37);
		move(601538, piccomp38);
		
		int nnn = 0;
		Var v1 = ts_Occ.getAt(1);
		Var v2 = ts_Occ.getAt(2);
		Var v3 = ts_Occ.getAt(3);
		Var v4 = ts_Occ.getAt(4);
		Var v5 = ts_Occ.getAt(5);

		move("01", utiste);	// = declare.level(10).picX(2).var() ;                          // (326)      10 UTISTE               PIC X(2).                            
		move("cdefg", utiento);// = declare.level(10).picX(5).var() ;                         // (327)      10 UTIENTO              PIC X(5).                            
		move("cdenum", cdenum);	// = declare.level(10).picX(6).var() ;                          // (328)      10 CDENUM               PIC X(6).                            
		move("utistec", utistec);// = declare.level(10).picX(2).var() ;                         // (329)      10 UTISTEC              PIC X(2).                            
		move("12", utientc$zone_Dvrs7h01m); //= declare.level(10).picX(5).var() ;          // (330)      10 UTIENTC              PIC X(5).                            
		move("ABCDEF", clino$zone_Dvrs7h01m);//declare.level(10).picX(6).var() ;            // (331)      10 CLINO                PIC X(6).                            
		move("ABCDEFGHIJ", clicch$zone_Dvrs7h01m);// = declare.level(10).picX(3).var() ;           // (332)      10 CLICCH               PIC X(3).                            
		move("hij", cliadmn);	// = declare.level(10).picX(3).var() ;
		
		move(-5, debmont);
		String cs = debmont.toString();
		boolean b = isGreater(debmont, 0);
		assertIfFalse(!b);
		multiply(-1, debmont).to(debmont);
		b = isGreater(debmont, 0);
		assertIfFalse(b);

		
		move(-0.5, debmont);
		cs = debmont.toString();
		b = isGreater(debmont, 0);
		assertIfFalse(!b);
		multiply(-1, debmont).to(debmont);		 
		b = isGreater(debmont, 0);
		assertIfFalse(b);
		
		move(-0.5, debmont);
		multiply(2, debmont).to(debmont);		 
		b = isGreater(debmont, 0);
		assertIfFalse(!b);
		
		move(0.5, wmonarr);
		b = isGreater(wmonarr, 0);
		assertIfFalse(b);

		b = isGreater(wmonarr, 1);
		assertIfFalse(!b);
		
		move(0.5, wmonarr);
		b = isGreater(wmonarr, 0.5);
		assertIfFalse(!b);
		
		move(ts_Occ, vDest);
		move(ts_Occ.getAt(1), vDest);
		move(ts_Occ.getAt(2), vDest);
		
			
		
		Var v = ts_Occ.getAt(1);
		v = ts_Occ.getAt(2);
		v = ts_Occ.getAt(3);
		v = ts_Occ.getAt(4);
		v = ts_Occ.getAt(5);
		for(int n=0; n<10; n++)
		{
			perform(testOccurs);
			perform(testmoveCorrespondingIndexToVar);
			perform(testmoveCorrespondingVarToIndex);
			perform(testmoveCorrespondingIndexToIndex);
			perform(testmoveCorrespondingIndexToIndexReversed);
		}
		
		CESM.returnTrans();
	}
		
	Paragraph testmoveCorrespondingIndexToIndex = new Paragraph(this){public void run(){testmoveCorrespondingIndexToIndex();}};void testmoveCorrespondingIndexToIndex()
	{
		move("i", include1.Item1$GOccursed.getAt(1));
		move("j", include1.Item1$GOccursed.getAt(2));
		move("k", include1.Item1$GOccursed.getAt(3));
		
		move("46", include1.Item2$GOccursed.getAt(1));
		move("80", include1.Item2$GOccursed.getAt(2));
		move("24", include1.Item2$GOccursed.getAt(3));

		
		moveCorresponding(include1.GroupOccursed.getAt(1), Group.getAt(1));
		assertIfFalse(Group.getAt(1).equals("i46"));

		moveCorresponding(include1.GroupOccursed.getAt(2), Group.getAt(2));
		assertIfFalse(Group.getAt(2).equals("j80"));
		assertIfFalse(Group.getAt(1).equals("i46"));

		moveCorresponding(include1.GroupOccursed.getAt(3), Group.getAt(3));
		assertIfFalse(Group.getAt(3).equals("k24"));
		assertIfFalse(Group.getAt(2).equals("j80"));
		assertIfFalse(Group.getAt(1).equals("i46"));
	}
	
	Paragraph testmoveCorrespondingIndexToIndexReversed = new Paragraph(this){public void run(){testmoveCorrespondingIndexToIndexReversed();}};void testmoveCorrespondingIndexToIndexReversed()
	{
		move("l", Item1.getAt(1));
		move("m", Item1.getAt(2));
		move("n", Item1.getAt(3));
		
		move("98", Item2.getAt(1));
		move("76", Item2.getAt(2));
		move("54", Item2.getAt(3));
		
		moveCorresponding(Group.getAt(1), include1.GroupOccursed.getAt(3));
		assertIfFalse(include1.GroupOccursed.getAt(3).equals("l98"));

		moveCorresponding(Group.getAt(2), include1.GroupOccursed.getAt(2));
		assertIfFalse(include1.GroupOccursed.getAt(2).equals("m76"));
		assertIfFalse(include1.GroupOccursed.getAt(3).equals("l98"));

		moveCorresponding(Group.getAt(3), include1.GroupOccursed.getAt(1));
		assertIfFalse(include1.GroupOccursed.getAt(1).equals("n54"));
		assertIfFalse(include1.GroupOccursed.getAt(2).equals("m76"));
		assertIfFalse(include1.GroupOccursed.getAt(3).equals("l98"));

	}
	
	Paragraph testmoveCorrespondingIndexToVar = new Paragraph(this){public void run(){testmoveCorrespondingIndexToVar();}};void testmoveCorrespondingIndexToVar()
	{
		move("A", Item1.getAt(1));
		move(12, Item2.getAt(1));
		
		move("M", Item1.getAt(2));
		move(56, Item2.getAt(2));
		
		move("Z", Item1.getAt(3));
		move(89, Item2.getAt(3));

		moveCorresponding(Group.getAt(1), include1.Group);
		assertIfFalse(include1.Group.equals("A12"));
		
		moveCorresponding(Group.getAt(2), include1.Group);
		assertIfFalse(include1.Group.equals("M56"));
		
		moveCorresponding(Group.getAt(3), include1.Group);
		assertIfFalse(include1.Group.equals("Z89"));
	}
	
	Paragraph testmoveCorrespondingVarToIndex = new Paragraph(this){public void run(){testmoveCorrespondingVarToIndex();}};void testmoveCorrespondingVarToIndex()
	{

		move("a", include1.Item1);
		move(23, include1.Item2);
		moveCorresponding(include1.Group, Group.getAt(1));
		assertIfFalse(Group.getAt(1).equals("a23"));
		
		move("b", include1.Item1);
		move(45, include1.Item2);
		moveCorresponding(include1.Group, Group.getAt(2));
		assertIfFalse(Group.getAt(2).equals("b45"));
		assertIfFalse(Group.getAt(1).equals("a23"));

		move("c", include1.Item1);
		move(67, include1.Item2);
		moveCorresponding(include1.Group, Group.getAt(3));
		assertIfFalse(Group.getAt(3).equals("c67"));
		assertIfFalse(Group.getAt(2).equals("b45"));
		assertIfFalse(Group.getAt(1).equals("a23"));
	}

		

	Paragraph testOccurs = new Paragraph(this){public void run(){testOccurs();}};void testOccurs()
	{	
		move("ABCDE", ts_item1);
		initializeReplacingAlphaNum(ts_item1, "W");    //->  Quelle est la bonne valeur: ts_item1= "ZBCDE"     ou "ZZZZZ" ?
	
		
		Var v1 = ts_Occ.getAt(1);
		Var v2 = ts_Occ.getAt(2);
		Var v3 = ts_Occ.getAt(3);
		Var v4 = ts_Occ.getAt(4);
		Var v5 = ts_Occ.getAt(5);
		
		Var v = ts_item31.getAt(1);
		v = ts_item31.getAt(2);
		v = ts_item4.getAt(1);
		v = ts_item4.getAt(2);
		
		initialize(ts_Occ.getAt(2)) ;
		initialize(ts_Occ.getAt(1)) ;
		
		
		assertIfFalse(v2.equals("             00 00 00  00 00 00"));
		assertIfFalse(v1.equals("             00 00 00  00 00 00"));
		
		move("ABCDE", ts_item1.getAt(2));
		move("FG", ts_item2.getAt(2));
		move("HI", ts_item3.getAt(2, 1));
		move("J", ts_item31.getAt(2, 2));
		move("K", ts_item32.getAt(2, 2));
		move("@", ts_item41.getAt(2, 1));
		move("#", ts_item41.getAt(2, 2));
	
	
		move("L", ts_item421.getAt(2, 1, 1));
		move("M", ts_item421.getAt(2, 1, 2));
		move("N", ts_item421.getAt(2, 1, 3));
		move("l", ts_item421.getAt(2, 2, 1));
		move("m", ts_item421.getAt(2, 2, 2));
		move("n", ts_item421.getAt(2, 2, 3));
		
		move(12, ts_item422.getAt(2, 1, 1));
		move(34, ts_item422.getAt(2, 1, 2));
		move(56, ts_item422.getAt(2, 1, 3));
		move(78, ts_item422.getAt(2, 2, 1));
		move(90, ts_item422.getAt(2, 2, 2));
		move(25, ts_item422.getAt(2, 2, 3));
		
		assertIfFalse(v1.equals("             00 00 00  00 00 00"));
		assertIfFalse(v2.equals("ABCDEFGHIJK@L12M34N56#l78m90n25"));
		
		move("abcde", ts_item1.getAt(3));
		move("fg", ts_item2.getAt(3));
		move("hi", ts_item3.getAt(3, 1));
		move("j", ts_item31.getAt(3, 2));
		move("k", ts_item32.getAt(3, 2));
		move("_", ts_item41.getAt(3, 1));
		move("+", ts_item41.getAt(3, 2));
	
	
		move("l", ts_item421.getAt(3, 1, 1));
		move("m", ts_item421.getAt(3, 1, 2));
		move("n", ts_item421.getAt(3, 1, 3));
		move("L", ts_item421.getAt(3, 2, 1));
		move("M", ts_item421.getAt(3, 2, 2));
		move("N", ts_item421.getAt(3, 2, 3));
		
		move(1, ts_item422.getAt(3, 1, 1));
		move(2, ts_item422.getAt(3, 1, 2));
		move(3, ts_item422.getAt(3, 1, 3));
		move(4, ts_item422.getAt(3, 2, 1));
		move(5, ts_item422.getAt(3, 2, 2));
		move(6, ts_item422.getAt(3, 2, 3));
		
		assertIfFalse(v1.equals("             00 00 00  00 00 00"));
		assertIfFalse(v2.equals("ABCDEFGHIJK@L12M34N56#l78m90n25"));
		assertIfFalse(v3.equals("abcdefghijk_l01m02n03+L04M05N06"));
		
		initializeReplacingNum(ts_Occ.getAt(2), 98);
		assertIfFalse(v1.equals("             00 00 00  00 00 00"));
		assertIfFalse(v2.equals("ABCDEFGHIJK@L98M98N98#l98m98n98"));
		assertIfFalse(v3.equals("abcdefghijk_l01m02n03+L04M05N06"));
	
	
		initializeReplacingAlphaNum(ts_Occ.getAt(2), "A");
		assertIfFalse(v1.equals("             00 00 00  00 00 00"));
		assertIfFalse(v2.equals("A    A AAAAAA98A98A98AA98A98A98"));
		assertIfFalse(v3.equals("abcdefghijk_l01m02n03+L04M05N06"));
	
		initializeReplacingAlphaNum(ts_Occ.getAt(2), "ABC");
		assertIfFalse(v1.equals("             00 00 00  00 00 00"));
		assertIfFalse(v2.equals("ABC  ABAAAAAA98A98A98AA98A98A98"));
		assertIfFalse(v3.equals("abcdefghijk_l01m02n03+L04M05N06"));

		Var i11 = ts_item1.getAt(1);
		Var i21 = ts_item2.getAt(1);
		Var i31 = ts_item3.getAt(1);
		Var i311 = ts_item3.getAt(1, 1);
		Var i312 = ts_item3.getAt(1, 2);
		Var i3111 = ts_item31.getAt(1, 1);
		Var i3211 = ts_item32.getAt(1, 1);
		Var i3112 = ts_item31.getAt(1, 2);
		Var i3212 = ts_item32.getAt(1, 2);

		Var i12 = ts_item1.getAt(2);
		Var i22 = ts_item2.getAt(2);
		Var i321 = ts_item3.getAt(2, 1);
		Var i322 = ts_item3.getAt(2, 1);
		Var i3121 = ts_item31.getAt(2, 1);
		Var i3221 = ts_item32.getAt(2, 1);
		Var i3122 = ts_item31.getAt(2, 2);
		Var i3222 = ts_item32.getAt(2, 2);

		Var i13 = ts_item1.getAt(3);
		Var i23 = ts_item2.getAt(3);
		Var i331 = ts_item3.getAt(3, 1);
		Var i332 = ts_item3.getAt(3, 2);
		Var i3131 = ts_item31.getAt(3, 1);
		Var i3231 = ts_item32.getAt(3, 1);
		Var i3132 = ts_item31.getAt(3, 2);
		Var i3232 = ts_item32.getAt(3, 2);			
	}
}
