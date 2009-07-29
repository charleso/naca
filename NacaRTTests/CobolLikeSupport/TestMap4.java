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
import idea.onlinePrgEnv.OnlineProgram;
import nacaLib.varEx.*;
import nacaLib.program.*;

/*
 * Created on 7 avr. 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author U930DI
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestMap4 extends OnlineProgram
{
//	public TestMap4(ProgramArgument programArgument)
//	{
//		super(programArgument);
//	}
	
	DataSection WS = declare.workingStorageSection() ;

		//Var vIndex = declare.variable().picS9(2).var();
				
		TestMap4Form testMap4Form = TestMap4Form.Copy(this);
			Var vNext1 = declare.level(2).picX(100).var();
			Var vNext2 = declare.level(2).picX(100).var();
		
		MapRedefine MAP = declare.level(1).redefinesMap(testMap4Form.Screen);
			Edit editMRGenre = declare.level(5).edit();	// Edit editMRGenre = declare.level(5).redefines(testMap4Form.editGenre).edit();
			Var varId = declare.level(5).var();
				Edit editMRNom = declare.level(10).edit();
				Edit editPrenoms = declare.level(10).editOccurs(3, "Prenoms");
					Edit editMRPrenom = declare.level(15).edit();
					Edit editAdresses = declare.level(15).editOccurs(2, "Adresses");				
						Edit editMRAdr = declare.level(20).edit();
						Edit editMRTel = declare.level(20).edit();
							Var Prefixe = declare.level(25).picX(2).var();
							Var Separator = declare.level(25).picX(1).value("/").var();
							Var Digits = declare.level(25).occurs(10).var();
								Var Digit = declare.level(30).pic9(1).var();
						Edit editMRFax = declare.level(20).edit();
				
			Edit editMRDateNais = declare.level(5).edit();
				Var JJ = declare.level(10).picX(2).var();
				Var filler1 = declare.level(10).picX(1).value("/").var();
				Var MM = declare.level(10).picX(2).var();
				Var filler2 = declare.level(10).picX(1).value("/").var();
				Var AA = declare.level(10).picX(2).var();
			Edit editMRSalaire = declare.level(5).pic9("ZZZ'ZZZ.ZZ").edit();
			Edit editItem = declare.level(5).edit();
			
		Var copy1 = declare.variable().picX(900).var();
		Var copy2 = declare.variable().picX(900).var();
		
		Var v1 = declare.level(1).var();
			Var v5A = declare.level(5).picX(3).var();
			Var v5B = declare.level(5).picX(8).var();
			Var v5C = declare.level(5).picX(20).var();
			Var v5D = declare.level(5).picX(3).var();
			Var v5E = declare.level(5).picX(20).var();
		
		TestMap4Form testMap4Form2 = TestMap4Form.Copy(this);	// Another form
			Var vNext11 = declare.level(2).picX(100).var();
			Var vNext12 = declare.level(2).picX(100).var();
			
			
			
			
			
			
			
			
// RS7AA4S rs7aa4ss = RS7AA4S.Copy(this) ;                                   // (713)      EXEC SQL INCLUDE RS7AA4SS END-EXEC.                          
//                                                                             // (714)                                                                   
// MapRedefine smap = declare.level(1).redefinesMap(rs7aa4ss.rs7a4sf) ;       // (715)  01  SMAP REDEFINES RS7A4SFS.                                     
//  Edit filler$42 = declare.level(3).editSkip(17) ;                       // (716)      03 FILLER               PIC X(318).                          
//  Var szone_Lw = declare.level(3).var() ;                                 // (717)      03 SZONE-LW.                                                 
//    Edit szone_Conc = declare.level(5).editOccurs(10, "szone_Conc") ;  // (718)         05 SZONE-CONC        OCCURS 10.                           
//      Edit sconc = declare.level(10).edit() ;                         // (719)            10 SCONCL         PIC S9(4) COMP.                      
//                                                                       // (720)            10 SCONCA         PIC X.                               
//                                                                       // (721)            10 SCONCC         PIC X.                               
//                                                                       // (722)            10 SCONCP         PIC X.                               
//                                                                       // (723)            10 SCONCH         PIC X.                               
//                                                                       // (724)            10 FILLER         PIC X.                               
//                                                                       // (725)            10 SCONCI         PIC X(30).                           
//   Edit filler$43 = declare.level(05).editSkip(23) ;                   // (726)         05 FILLER            PIC X(333).                          
//   Edit szone_Sup = declare.level(05).editOccurs(4, "szone_Sup") ;     // (727)         05 SZONE-SUP         OCCURS 4.                            
//    Edit ssup = declare.level(10).edit() ;                          // (728)            10 SSUPL          PIC S9(4) COMP.                      
//                                                                       // (729)            10 SSUPA          PIC X.                               
//                                                                       // (730)            10 SSUPC          PIC X.                               
//                                                                       // (731)            10 SSUPP          PIC X.                               
//                                                                       // (732)            10 SSUPH          PIC X.                               
//                                                                       // (733)            10 FILLER         PIC X.                               
//                                                                       // (734)            10 SSUPI          PIC XX.                              
//   Edit filler$44 = declare.level(05).editSkip(5) ;                    // (735)         05 FILLER            PIC X(45).                           
//   Edit srcdedtp_Zone = declare.level(05).editOccurs(10, "srcdedtp_Zone") ; // (736)         05 SRCDEDTP-ZONE     OCCURS 10.                           
//    Edit srcdedtp = declare.level(10).edit() ;                      // (737)            10 SRCDEDTPL      PIC S9(4) COMP.                      
//                                                                       // (738)            10 SRCDEDTPA      PIC X.                               
//                                                                       // (739)            10 SRCDEDTPC      PIC X.                               
//                                                                       // (740)            10 SRCDEDTPP      PIC X.                               
//                                                                       // (741)            10 SRCDEDTPH      PIC X.                               
//                                                                       // (742)            10 FILLER         PIC X.                               
//                                                                       // (743)            10 SRCDEDTPI      PIC X(4).                            
//   Edit filler$45 = declare.level(05).editSkip(1) ;                    // (744)         05 FILLER            PIC X(10).                           
//   Var srm7attda_Zone = declare.level(5).var() ;                       // (745)         05 SRM7ATTDA-ZONE.                                        
//    Edit filler$46 = declare.level(10).editOccurs(5, "filler$46") ; // (746)            10 FILLER OCCURS 5.                                    
//       Edit srm7cstat = declare.level(15).edit() ;                 // (747)               15 SRM7CSTATL  PIC S9(4) COMP.                      
//                                                                 // (748)               15 SRM7CSTATA  PIC X.                               
//                                                                 // (749)               15 SRM7CSTATF REDEFINES SRM7CSTATA PIC X.           
//                                                                 // (750)               15 SRM7CSTATC  PIC X.                               
//                                                                 // (751)               15 SRM7CSTATP  PIC X.                               
//                                                                 // (752)               15 SRM7CSTATH  PIC X.                               
//                                                                 // (753)               15 FILLER      PIC X.                               
//                                                                 // (754)               15 SRM7CSTATI  PIC X(2).                            
//     Edit srm7attda = declare.level(15).edit() ;                 // (755)               15 SRM7ATTDAL  PIC S9(4) COMP.                      
//                                                                       // (756)               15 SRM7ATTDAA  PIC X.                               
//                                                                       // (757)               15 SRM7ATTDAF REDEFINES SRM7ATTDAA PIC X.           
//                                                                       // (758)               15 SRM7ATTDAC  PIC X.                               
//                                                                       // (759)               15 SRM7ATTDAP  PIC X.                               
//                                                                       // (760)               15 SRM7ATTDAH  PIC X.                               
//                                                                       // (761)               15 FILLER      PIC X.                               
//                                                                       // (762)               15 SRM7ATTDAI  PIC X(1).                            
//   Var srm7aqqda_Zone = declare.level(5).var() ;                       // (763)         05 SRM7AQQDA-ZONE.                                        
//     Edit filler$47 = declare.level(10).editOccurs(10, "filler$47") ; // (764)            10 FILLER OCCURS 10.                                   
//       Edit srlista = declare.level(15).edit() ;                   // (765)               15 RLISTAL     PIC S9(4) COMP.                      
//                                                                 // (766)               15 RLISTAA     PIC X.                               
//                                                                 // (767)               15 RLISTAF REDEFINES RLISTAA PIC X.                 
//                                                                 // (768)               15 RLISTAC     PIC X.                               
//                                                                 // (769)               15 RLISTAP     PIC X.                               
//                                                                 // (770)               15 RLISTAH     PIC X.                               
//                                                                 // (771)               15 FILLER      PIC X.                               
//                                                                 // (772)               15 SRLISTAI    PIC X(2).                            
//     Edit srm7aqqda = declare.level(15).edit() ;                 // (773)               15 SRM7AQQDAL  PIC S9(4) COMP.                      
//                                                                       // (774)               15 SRM7AQQDAA  PIC X.                               
//                                                                       // (775)               15 SRM7AQQDAF REDEFINES SRM7AQQDAA PIC X.           
//                                                                       // (776)               15 SRM7AQQDAC  PIC X.                               
//                                                                       // (777)               15 SRM7AQQDAP  PIC X.                               
//                                                                       // (778)               15 SRM7AQQDAH  PIC X.                               
//                                                                       // (779)               15 FILLER      PIC X.                               
//                                                                       // (780)               15 SRM7AQQDAI  PIC X(1).                            
//   Edit filler$48 = declare.level(05).editSkip(10) ;  
//   
//  
//	Var w_Tar_Ed = declare.level(1).var() ;                                     // (214)  01  W-TAR-ED.                                                    
//		Var w_Tar = declare.level(5).pic("ZZZZZZ9.9999").valueZero().var() ;    // (215)      05 W-TAR               PIC Z(6)9.9(4)        VALUE ZERO.     
// 		Var filler111 = declare.level(5).redefines(w_Tar).filler() ;             // (216)      05 FILLER REDEFINES W-TAR.                                   
//			Var filler112 = declare.level(10).picX(10).filler() ;                // (217)         10 FILLER           PIC X(10).                            
//			Var w_Tar12 = declare.level(10).picX(2).var() ;                     // (218)         10 W-TAR12          PIC XX.
	
			
	Paragraph Main = new Paragraph(this){public void run(){Main();}};void Main()
	{
//		move(3.2, w_Tar);
//		move(w_Tar_Ed, testMap4Form.editItem);                    // (6840)                MOVE W-TAR-ED TO M7ATARMI SM7ATARMI
		
		moveSubStringSpace(editItem, 10, 1) ;
			

 
		int n = 0;
 			
		
		
		
		
		
		
		perform(checkNumEdited);
		perform(checkPositions);
		perform(checkInitialValues);
		perform(checkSerialization);
		perform(checkChildAt);
						
		CESM.returnTrans();
	}

	Paragraph checkNumEdited = new Paragraph(this){public void run(){checkNumEdited();}};void checkNumEdited()
	{
		move(12345, editMRSalaire);
		assertIfDifferent(" 12,34", editMRSalaire);
		assertIfDifferent(" 12,34", testMap4Form.editSalaire);
		
		move(9876543, testMap4Form.editSalaire);
		assertIfDifferent("987654", editMRSalaire);
		assertIfDifferent("987654", testMap4Form.editSalaire);
	}
		
	Paragraph checkInitialValues = new Paragraph(this){public void run(){checkInitialValues();}};void checkInitialValues()
	{
		Edit e = editMRAdr.getAt(1);
		e = editMRAdr.getAt(1, 1);
		e = editMRAdr.getAt(1, 2);
		e = editMRAdr.getAt(2, 1);
		e = editMRAdr.getAt(2, 2);
		e = editMRAdr.getAt(3, 1);
		e = editMRAdr.getAt(3, 2);
		
		e = editMRTel.getAt(1, 1);
		e = editMRTel.getAt(1, 2);
		e = editMRTel.getAt(2, 1);
		e = editMRTel.getAt(2, 2);
		e = editMRTel.getAt(3, 1);
		e = editMRTel.getAt(3, 2);

		e = editMRFax.getAt(1, 1);		
		e = editMRFax.getAt(1, 2);
		e = editMRFax.getAt(2, 1);
		e = editMRFax.getAt(2, 2);
		e = editMRFax.getAt(3, 1);
		e = editMRFax.getAt(3, 2);
		
		for(int x=1; x<=3; x++)
		{
			for(int y=1; y<=2; y++)
			{
				e = editMRFax.getAt(x, y);
				String cs = e.getString();
				char c = cs.charAt(2);
				assertIfFalse(c != '/');
			}
		}
	}
	
	Paragraph checkSerialization = new Paragraph(this){public void run(){checkSerialization();}};void checkSerialization()
	{
		move("MR", editMRGenre);
		move("Ditscheid", editMRNom);
		move("Prenom1", editMRPrenom.getAt(1));
		move("Prenom2", editMRPrenom.getAt(2));
		move("Prenom3", editMRPrenom.getAt(3));
		
		move("Adr11", editMRAdr.getAt(1, 1));
		move("Adr12", editMRAdr.getAt(1, 2));
		move("Adr21", editMRAdr.getAt(2, 1));
		move("Adr22", editMRAdr.getAt(2, 2));
		move("Adr31", editMRAdr.getAt(3, 1));
		move("Adr32", editMRAdr.getAt(3, 2));
		
		move("tel11", editMRTel.getAt(1, 1));
		move("tel12", editMRTel.getAt(1, 2));
		move("tel21", editMRTel.getAt(2, 1));
		move("tel22", editMRTel.getAt(2, 2));
		move("tel31", editMRTel.getAt(3, 1));
		move("tel32", editMRTel.getAt(3, 2));
		
		move("Fax11", editMRFax.getAt(1, 1));
		move("Fax12", editMRFax.getAt(1, 2));
		move("Fax21", editMRFax.getAt(2, 1));
		move("Fax22", editMRFax.getAt(2, 2));
		move("Fax31", editMRFax.getAt(3, 1));
		move("Fax32", editMRFax.getAt(3, 2));

		move("11", JJ);
		move("11", MM);
		move("63", AA);
		move(12345, editMRSalaire);
		
		// Check serialization from Form
		moveLowValue(copy1);
		moveLowValue(copy2);
		
//		assertIfFalse(testMap4Form.editPrenoma.getSemanticContextValue().equals("SEM_editPrenoma"));
//		assertIfFalse(editMRPrenom.getAt(1).getSemanticContextValue().equals("SEM_editPrenoma"));
		
//		setSemanticContextValue(testMap4Form.editPrenoma, "SEM_editPrenoma_2");
		
		
//		setSemanticContextValue(testMap4Form.editGenre, "editGenre");
//		setSemanticContextValue(testMap4Form.editNom, "editNom");
		
		move(testMap4Form.Screen, copy1);
		
//		assertIfFalse(testMap4Form.editGenre.getSemanticContextValue().equals("editGenre"));
//		assertIfFalse(testMap4Form.editNom.getSemanticContextValue().equals("editNom"));
//		
//		setSemanticContextValue(testMap4Form.editGenre, "Erased0");
//		setSemanticContextValue(testMap4Form.editNom, "Erased1");
		moveLowValue(testMap4Form.Screen);
		moveLowValue(testMap4Form.Screen);
//		assertIfFalse(testMap4Form.editGenre.getSemanticContextValue().equals("Erased0"));
//		assertIfFalse(testMap4Form.editNom.getSemanticContextValue().equals("Erased1"));
		
		move(copy1, testMap4Form.Screen);
		
//		assertIfFalse(testMap4Form.editGenre.getSemanticContextValue().equals("editGenre"));
//		assertIfFalse(testMap4Form.editNom.getSemanticContextValue().equals("editNom"));
				

		// Check serialization from Map
		move(MAP, copy2);
		String csCopy1 = copy1.getString();
		String csCopy2 = copy2.getString();
		assertIfFalse(csCopy1.equals(csCopy2));
		
		
		moveLowValue(testMap4Form.Screen);
		move(copy1, testMap4Form.Screen);
		
		
		moveLowValue(copy1);
		move(testMap4Form.Screen, copy1);
		assertIfFalse(csCopy1.equals(csCopy2));
		
		moveLowValue(copy1);
//		assertIfFalse(editMRGenre.getSemanticContextValue().equals("editGenre"));
//		assertIfFalse(editMRNom.getSemanticContextValue().equals("editNom"));
		
		move(MAP, copy1);
		
//		editMRGenre.setSemanticContextValue("editGenreOld");
//		editMRNom.setSemanticContextValue("editNomOld");

		move(copy1, MAP);
//		assertIfFalse(editMRGenre.getSemanticContextValue().equals("editGenre"));
//		assertIfFalse(editMRNom.getSemanticContextValue().equals("editNom"));
		
		
		
		assertIfFalse(csCopy1.equals(csCopy2));
		
		// Serialization form -> form
		moveLowValue(testMap4Form2.Screen);

		move(testMap4Form.Screen, copy1);
		csCopy1 = copy1.getString();
		
		move(testMap4Form.Screen, testMap4Form2.Screen);
		move(testMap4Form2.Screen, copy2);
		csCopy2 = copy2.getString();

		assertIfFalse(csCopy1.equals(csCopy2));
		
		
		// Serialization map -> form
		moveLowValue(testMap4Form2.Screen);

		move(MAP, testMap4Form2.Screen);
		move(testMap4Form2.Screen, copy2);
		csCopy2 = copy2.getString();

		assertIfFalse(csCopy1.equals(csCopy2));		

		// Serialization form -> map
		moveLowValue(MAP);

		move(testMap4Form2.Screen, MAP);
		move(MAP, copy2);
		csCopy2 = copy2.getString();

		assertIfFalse(csCopy1.equals(csCopy2));	

	}
	
	Paragraph checkOccurs = new Paragraph(this){public void run(){checkOccurs();}};void checkOccurs()
	{
		Var v = filler1.getAt(1);
		v = filler1.getAt(2);
		
//		v = Separator.getAt(1);
//		v = Separator.getAt(2); // should be /
		
		move("33/11345678", editMRTel.getAt(1, 1));
		move("44/12456789", editMRTel.getAt(1, 2));
		move("55/21345678", editMRTel.getAt(2, 1));
		move("66/22456789", editMRTel.getAt(2, 2));
		move("77/31345678", editMRTel.getAt(3, 1));
		move("88/32456789", editMRTel.getAt(3, 2));
	
		Edit e = editMRAdr.getAt(1);
		e = editMRAdr.getAt(1, 1);
		e = editMRAdr.getAt(1, 2);
		e = editMRAdr.getAt(2, 1);
		e = editMRAdr.getAt(2, 2);
		e = editMRAdr.getAt(3, 1);
		e = editMRAdr.getAt(3, 2);
		
		e = editMRTel.getAt(1, 1);
		e = editMRTel.getAt(1, 2);
		e = editMRTel.getAt(2, 1);
		e = editMRTel.getAt(2, 2);
		e = editMRTel.getAt(3, 1);
		e = editMRTel.getAt(3, 2);

		e = editMRFax.getAt(1, 1);		
		e = editMRFax.getAt(1, 2);
		e = editMRFax.getAt(2, 1);
		e = editMRFax.getAt(2, 2);
		e = editMRFax.getAt(3, 1);
		e = editMRFax.getAt(3, 2);
		for(int x=1; x<=3; x++)
		{
			for(int y=1; y<=2; y++)
			{
				e = editMRTel.getAt(x, y);
				for(int z=1; z<=4; z++)
				{
					v = Digits.getAt(x, y, z);
					int nn = 0;			
				}
				int nDigit1 = Digits.getAt(x, y, 1).getInt();
				int nDigit2 = Digits.getAt(x, y, 2).getInt();
				assertIfFalse(nDigit1 == x);
				assertIfFalse(nDigit2 == y);
			}
		}
	}
		
	Paragraph checkPositions = new Paragraph(this){public void run(){checkPositions();}};void checkPositions()
	{
		int n0 = testMap4Form.editGenre.DEBUGgetAbsolutePosition();
		int n1 = editMRGenre.DEBUGgetAbsolutePosition();
		assertIfFalse(n0 == n1);

		n0 = testMap4Form.editNom.DEBUGgetAbsolutePosition();
		n1 = editMRNom.DEBUGgetAbsolutePosition();
		assertIfFalse(n0 == n1);

		n0 = testMap4Form.editPrenoma.DEBUGgetAbsolutePosition();
		n1 = editMRPrenom.getAt(1).DEBUGgetAbsolutePosition();
		assertIfFalse(n0 == n1);
		
		n0 = testMap4Form.editPrenomb.DEBUGgetAbsolutePosition();
		n1 = editMRPrenom.getAt(2).DEBUGgetAbsolutePosition();
		assertIfFalse(n0 == n1);
		
		n0 = testMap4Form.editAdr1a.DEBUGgetAbsolutePosition();
		n1 = editMRAdr.getAt(1, 1).DEBUGgetAbsolutePosition();
		assertIfFalse(n0 == n1);
		
		n0 = testMap4Form.editAdr2a.DEBUGgetAbsolutePosition();
		n1 = editMRAdr.getAt(1, 2).DEBUGgetAbsolutePosition();
		assertIfFalse(n0 == n1);
		
		n0 = testMap4Form.editAdr1b.DEBUGgetAbsolutePosition();
		n1 = editMRAdr.getAt(2, 1).DEBUGgetAbsolutePosition();
		assertIfFalse(n0 == n1);
		
		n0 = testMap4Form.editAdr2b.DEBUGgetAbsolutePosition();
		n1 = editMRAdr.getAt(2, 2).DEBUGgetAbsolutePosition();
		assertIfFalse(n0 == n1);
		
		
		
		n0 = testMap4Form.editDateNais.DEBUGgetAbsolutePosition();
		n1 = editMRDateNais.DEBUGgetAbsolutePosition();
		assertIfFalse(n0 == n1);

		n0 = testMap4Form.editDateNais.DEBUGgetAbsolutePosition();
		n1 = JJ.DEBUGgetAbsolutePosition();
		assertIfFalse(n0+7 == n1);

		n0 = testMap4Form.editDateNais.DEBUGgetAbsolutePosition();
		n1 = MM.DEBUGgetAbsolutePosition();
		assertIfFalse(n0+7+3 == n1);

		n0 = testMap4Form.editDateNais.DEBUGgetAbsolutePosition();
		n1 = AA.DEBUGgetAbsolutePosition();
		assertIfFalse(n0+7+3+3 == n1);

		n0 = testMap4Form.editSalaire.DEBUGgetAbsolutePosition();
		n1 = editMRSalaire.DEBUGgetAbsolutePosition();
		assertIfFalse(n0 == n1);
	}
	
	Paragraph checkChildAt = new Paragraph(this){public void run(){checkChildAt();}};void checkChildAt()
	{
		Var v = v1.getVarChildAt(1);
		v = v1.getVarChildAt(2);
		v = v1.getVarChildAt(3);
		
		Edit e = varId.getEditChildAt(1);
		e = varId.getEditChildAt(2);

		e = v1.getEditChildAt(1);
		e = v1.getEditChildAt(2);
		e = v1.getEditChildAt(3);

		int n = 0;
	}
	
	
}
