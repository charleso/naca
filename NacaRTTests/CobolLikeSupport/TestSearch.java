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
import nacaLib.program.CallbackSearch;
import nacaLib.program.CompareResult;
import nacaLib.varEx.Cond;
import nacaLib.varEx.DataSection;
import nacaLib.varEx.Var;


public class TestSearch extends OnlineProgram
{
	DataSection WorkingStorage = declare.workingStorageSection();
	
	Var nb_Postes_Mot_Douteux = declare.level(1).picS9(9).comp().var() ;        // (96)  01  NB-POSTES-MOT-DOUTEUX   PIC S9(9) COMP-4.                    
	Var tab_Mot_Douteux = declare.level(1).var() ;                              // (97)  01  TAB-MOT-DOUTEUX.                                             
		Var ind_Mot_Douteux = declare.index() ;
		Var le_Mot = declare.level(5).occursDepending(999, nb_Postes_Mot_Douteux)	// (98)      05  LE-MOT    OCCURS 1 TO 999                                
			.var() ;
			                                                                    // (99)                 DEPENDING ON NB-POSTES-MOT-DOUTEUX                
			                                                                    // (100)                 ASCENDING KEY UN-MOT-DOU                          
			                                                                    // (101)                 INDEXED BY IND-MOT-DOUTEUX.                       
			Var un_Mot_Dou = declare.level(10).picX(30).var() ;                 // (102)          10  UN-MOT-DOU  PIC X(30).        
			
	Var filler$1 = declare.level(1).picX(1).filler() ;                          // (83)  01     PIC X.                                                    
	Cond pas_Mot_Trouve = declare.condition().value("0").var() ;            // (84)      88 PAS-MOT-TROUVE   VALUE '0'.                               
	Cond mot_Trouve = declare.condition().value("1").var() ;                // (85)      88 MOT-TROUVE       VALUE '1'.
	
	Var searchedValue = declare.level(77).picX(30).var();

	public void procedureDivision()
	{
		moveTrue(pas_Mot_Trouve) ;
		move(4, nb_Postes_Mot_Douteux);
//		for(int n=nb_Postes_Mot_Douteux.getInt(); n>=0; n--)
//		{
//			move("A"+(char)(nb_Postes_Mot_Douteux.getInt()-n+65), un_Mot_Dou.getAt(n+1));
//		}
		
		for(int n=0; n<nb_Postes_Mot_Douteux.getInt(); n++)
		{
			move("A"+(char)(n+65), un_Mot_Dou.getAt(n+1));
		}
		
		move("AD", searchedValue);
		
		searchAll(le_Mot, nb_Postes_Mot_Douteux).indexedBy(ind_Mot_Douteux)
		//.keyDesc(un_Mot_Dou)                                                 // (286)         SEARCH ALL LE-MOT                                         
		.keyAsc(un_Mot_Dou)                                                 // (286)         SEARCH ALL LE-MOT
		.when(new CallbackSearch(this){public CompareResult run(){
			                                                                // (287)           WHEN UN-MOT-DOU(IND-MOT-DOUTEUX) = UN-MOT(IND)          
			CompareResult result = compare(un_Mot_Dou.getAt(ind_Mot_Douteux), searchedValue);
			if(result.isEqual())
			{
				moveTrue(mot_Trouve) ;
				return result;
			}
			return result;
		}});
		
		int gg = 0;
	}

}
