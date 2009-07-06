import nacaLib.misc.KeyPressed;
import nacaLib.mapSupport.* ;
import nacaLib.sqlSupport.* ;
import nacaLib.program.* ;
import nacaLib.varEx.* ;
import idea.onlinePrgEnv.OnlineProgram;
public class ONLINE1 extends OnlineProgram	// *************                        (1) *
{
	                                                                            // (2)  ID DIVISION.
	// *************                                                            // (3)
	                                                                            // (4) 
	                                                                            // (5)  PROGRAM-ID.                ONLINE1.
	                                                                            // (6)  AUTHOR.                    XXXXXXXXX.
	                                                                            // (7)  DATE-WRITTEN.              2008.
	                                                                            // (8) 
	// REMARKS.                                                                 // (9)
	//                                                                          // (10)
	//  Programme demo ONLINE                                                   // (11)
	//                                                                          // (12)
	// **********************                                                   // (13)
	                                                                            // (14)  ENVIRONMENT DIVISION.
	// **********************                                                   // (15)
	                                                                            // (16) 
	// ***************                                                          // (17)
	                                                                            // (18)  DATA DIVISION.
	// ***************                                                          // (19)
	                                                                            // (20) 
	DataSection workingstoragesection = declare.workingStorageSection() ;       // (21)  WORKING-STORAGE SECTION.
	// ------------------------                                                 // (22)
	                                                                            // (23) 
	                                                                            // (24)  77  IND1                   PIC S9(4) COMP-4 SYNC VALUE +1.
	                                                                            // (25)  77  IND-LNG                PIC S9(4) COMP-4 SYNC VALUE +1.
	                                                                            // (26)  77  LONGCAR                PIC S9(4) COMP-4 SYNC VALUE +1.
	                                                                            // (27)  77  LONGERR                PIC S9(4) COMP-4 SYNC VALUE +92.
	                                                                            // (28)  77  LONGPLAUS              PIC S9(4) COMP-4 SYNC VALUE +505.
	                                                                            // (29)  77  LONGSMAP               PIC S9(4) COMP-4 SYNC VALUE +400.
	                                                                            // (30) 
	                                                                            // (31)  77  DATANAME               PIC X(8)         VALUE SPACE.
	                                                                            // (32)  77  W-CICS                 PIC X(8)         VALUE SPACE.
	                                                                            // (33)  77  HEX80                  PIC X            VALUE 'Ø'.
	                                                                            // (34)  77  ECR-VIERGE             PIC X            VALUE SPACE.
	Var sveibcalen = declare.level(77).picS9(4).comp().valueZero().var() ;      // (35)  77  SVEIBCALEN             PIC S9(4) COMP-4 VALUE ZERO.
	Var sveibfn = declare.level(77).picX(2).valueSpaces().var() ;               // (36)  77  SVEIBFN                PIC X(2)         VALUE SPACE.
	                                                                            // (37) 
	                                                                            // (38)  77  W-CDCENPI              PIC X(3)         VALUE SPACE.
	                                                                            // (39) 
	Var w_Abend_Db2 = declare.level(1).var() ;                                  // (40)  01  W-ABEND-DB2.
		Var w_Db2 = declare.level(5).picX(1).value("D").var() ;                 // (41)      05 W-DB2               PIC X            VALUE 'D'.
		Var w_Sqlcode = declare.level(5).pic9(3).valueZero().var() ;            // (42)      05 W-SQLCODE           PIC 9(3)         VALUE ZERO.
	                                                                            // (43) 
	Var w_Date_Form = declare.level(1).var() ;                                  // (44)  01  W-DATE-FORM.
		Var w_Jj = declare.level(5).picX(2).var() ;                             // (45)      05 W-JJ                PIC XX.
		Var filler$1 = declare.level(5).picX(1).value(".").filler() ;           // (46)      05 FILLER              PIC X            VALUE '.'.
		Var w_Mm = declare.level(5).picX(2).var() ;                             // (47)      05 W-MM                PIC XX.
		Var filler$2 = declare.level(5).picX(1).value(".").filler() ;           // (48)      05 FILLER              PIC X            VALUE '.'.
		Var w_Aa = declare.level(5).picX(2).var() ;                             // (49)      05 W-AA                PIC XX.
	                                                                            // (50) 
	Var w_Date_X = declare.level(1).var() ;                                     // (51)  01  W-DATE-X.
		Var w_Siecle = declare.level(5).pic9(2).valueZero().var() ;             // (52)      05 W-SIECLE            PIC 99           VALUE ZERO.
		Var w_Annee = declare.level(5).pic9(2).valueZero().var() ;              // (53)      05 W-ANNEE             PIC 99           VALUE ZERO.
		Var w_Mois = declare.level(5).pic9(2).valueZero().var() ;               // (54)      05 W-MOIS              PIC 99           VALUE ZERO.
		Var w_Jour = declare.level(5).pic9(2).valueZero().var() ;               // (55)      05 W-JOUR              PIC 99           VALUE ZERO.
	Var w_Date_N = declare.level(1).redefines(w_Date_X).pic9(8).var() ;         // (56)  01  W-DATE-N REDEFINES W-DATE-X
	                                                                            // (57)                             PIC 9(8).
	                                                                            // (58) 
	Var w_Eibtime = declare.level(1).pic9(7).valueZero().var() ;                // (59)  01  W-EIBTIME              PIC 9(7)         VALUE ZERO.
	Var filler$3 = declare.level(1).redefines(w_Eibtime).filler() ;             // (60)  01  FILLER                 REDEFINES W-EIBTIME.
		Var filler$4 = declare.level(5).picX(1).filler() ;                      // (61)      05 FILLER              PIC X.
		Var w_Heure = declare.level(5).picX(2).var() ;                          // (62)      05 W-HEURE             PIC XX.
		Var w_Minute = declare.level(5).picX(2).var() ;                         // (63)      05 W-MINUTE            PIC XX.
		Var w_Seconde = declare.level(5).picX(2).var() ;                        // (64)      05 W-SECONDE           PIC XX.
	                                                                            // (65) 
	Var w_Heure_Form = declare.level(1).var() ;                                 // (66)  01  W-HEURE-FORM.
		Var w_Hh = declare.level(5).picX(2).var() ;                             // (67)      05 W-HH                PIC XX.
		Var filler$5 = declare.level(5).picX(1).value(":").filler() ;           // (68)      05 FILLER              PIC X            VALUE ':'.
		Var w_Mi = declare.level(5).picX(2).var() ;                             // (69)      05 W-MI                PIC XX.
		Var filler$6 = declare.level(5).picX(1).value(":").filler() ;           // (70)      05 FILLER              PIC X            VALUE ':'.
		Var w_Ss = declare.level(5).picX(2).var() ;                             // (71)      05 W-SS                PIC XX.
	                                                                            // (72)  
	Var w_Libel = declare.level(1).var() ;                                      // (73)  01  W-LIBEL.
		Var w_Lib = declare.level(3).occurs(5).picX(1).var() ;                  // (74)      03 W-LIB               PIC X OCCURS 5.
	Var w_Libel1 = declare.level(1).var() ;                                     // (75)  01  W-LIBEL1.
		Var w_Lib1_Cics = declare.level(3).picX(2).var() ;                      // (76)      03 W-LIB1-CICS         PIC X(2).
		Var w_Lib1_Ste = declare.level(3).picX(3).var() ;                       // (77)      03 W-LIB1-STE          PIC X(3).
	                                                                            // (78) 
	//  COPY MAP                                                                   (79) *
	ONLINM1 onlinm1 = ONLINM1.Copy(this) ;                                      // (80)      COPY ONLINM1.
	                                                                            // (81) 
	ONLINM1S onlinm1s = ONLINM1S.Copy(this) ;                                   // (82)      EXEC SQL INCLUDE ONLINM1S END-EXEC.
	                                                                            // (83) 
	//  COPY IO CALLMSG                                                            (84) *
	MSGZONE msgzone = MSGZONE.Copy(this) ;                                      // (85)      COPY MSGZONE.
	                                                                            // (86) 
	//  COPY DB2                                                                // (87)
	                                                                            // (88)      EXEC SQL INCLUDE SQLCA END-EXEC.
	                                                                            // (89) 
	//  COPY INCLUDE TABLE                                                         (90) *
	VTBMSGA vtbmsga = VTBMSGA.Copy(this) ;                                      // (91)      EXEC SQL INCLUDE VTBMSGA END-EXEC.
	                                                                            // (92) 
	                                                                            // (93)      COPY DFHAID SUPPRESS.
	// *                                                                           (94) /
	DataSection linkagesection = declare.linkageSection() ;                     // (95)  LINKAGE SECTION.
	// ----------------                                                            (96) *
	Var dfhcommarea = declare.level(1).var() ;                                  // (97)  01  DFHCOMMAREA.
		Var comzone = declare.level(5).picX(10000).var() ;                      // (98)      05 COMZONE  PIC X(1) OCCURS 1 TO 10000 DEPENDING ON EIBCALEN.
	                                                                            // (99) 
	TUAZONE tuazone = TUAZONE.Copy(this) ;                                      // (100)      EXEC SQL INCLUDE TUAZONE END-EXEC.
	                                                                            // (101) 
	// ********************                                                        (102) /
	ParamDeclaration callParameters = declare.using(dfhcommarea);               // (103)  PROCEDURE DIVISION.
	public void procedureDivision() {
		// ********************                                                    (104) *
		move(getLastCICSCommandExecutedCode(), sveibfn);                        // (105)      MOVE EIBFN             TO  SVEIBFN
		move(getCommAreaLength(), sveibcalen);                                  // (106)      MOVE EIBCALEN          TO  SVEIBCALEN
		                                                                        // (107) 
		                                                                        // (108)      EXEC SQL WHENEVER SQLERROR GOTO PC-ERR-DB2 END-EXEC
		                                                                        // (109) 
		CESM.getAddressOfTCTUA(addressOf(tuazone.tua_Zone)) ;                   // (110)      EXEC CICS ADDRESS
		                                                                        // (111)           TCTUA(ADDRESS OF TUA-ZONE)
		                                                                        // (112)      END-EXEC
		                                                                        // (113) 
		if (isEqual(sveibfn, CESMCommandCode.XCTL)) {                           // (114)      IF SVEIBFN = 'Ü'
			// ** pgm appele par XCTL (SVEIBFN = '0E04')                           (115) *
			moveZero(sveibcalen) ;                                              // (116)         MOVE ZERO           TO  SVEIBCALEN
		}                                                                       // (117)      END-IF.
	}
	                                                                            // (118) 
	Paragraph p_Traitement = new Paragraph(this);                               // (119)  P-TRAITEMENT.
	public void p_Traitement() {
		// -------------                                                           (120) *
		if (isZero(sveibcalen)) {                                               // (121)      IF SVEIBCALEN          = ZERO
			perform($1er_Passage) ;                                             // (122)         PERFORM             1ER-PASSAGE
		}
		else {                                                                  // (123)      ELSE
			perform($2eme_Passage) ;                                            // (124)         PERFORM             2EME-PASSAGE
		}                                                                       // (125)      END-IF
		                                                                        // (126) 
		CESM.returnTrans() ;                                                    // (127)      EXEC CICS RETURN END-EXEC
		                                                                        // (128) 
		exitProgram();                                                          // (129)      GOBACK.
	}
	                                                                            // (130) 
	// *********************                                                       (131) /
	Section $1er_Passage = new Section(this);                                   // (132)  1ER-PASSAGE SECTION.
	public void $1er_Passage() {
		// *********************                                                   (133) *
		initialize(onlinm1s.onlinefs) ;                                         // (134)      MOVE LOW-VALUE         TO ONLINEFS
		                                                                        // (135)      
		move(CESM.getCurrentDay(), w_Jj);                                       // (136)      MOVE CIXJOUR           TO W-JJ W-JOUR
		move(CESM.getCurrentDay(), w_Jour);
		move(CESM.getCurrentMonth(), w_Mm);                                     // (137)      MOVE CIXMOIS           TO W-MM W-MOIS
		move(CESM.getCurrentMonth(), w_Mois);
		move(CESM.getCurrentShortYear(), w_Aa);                                 // (138)      MOVE CIXAN             TO W-AA W-ANNEE
		move(CESM.getCurrentShortYear(), w_Annee);
		move(w_Date_Form, tuazone.tua_I_Dtjourf);                               // (139)      MOVE W-DATE-FORM       TO TUA-I-DTJOURF
		if (isLess(w_Annee, 84)) {                                              // (140)      IF W-ANNEE             < 84
			move(20, w_Siecle);                                                 // (141)         MOVE 20             TO W-SIECLE
		}
		else {                                                                  // (142)      ELSE
			move(19, w_Siecle);                                                 // (143)         MOVE 19             TO W-SIECLE.
		}
		move(w_Date_N, tuazone.tua_I_Dtjour);                                   // (144)      MOVE W-DATE-N          TO TUA-I-DTJOUR
		                                                                        // (145)      
		move(tuazone.tua_I_Dtjourf, onlinm1s.sdtexec);                          // (146)      MOVE TUA-I-DTJOURF     TO SDTEXECI DTEXECI
		move(tuazone.tua_I_Dtjourf, onlinm1.dtexec);
		                                                                        // (147) 
		setCursor(onlinm1s.srecoll) ;                                           // (148)      MOVE -1                TO SRECOLLL RECOLLL
		setCursor(onlinm1.recoll) ;
		perform(envoi_Masque) ;                                                 // (149)      PERFORM                ENVOI-MASQUE.
	}
	                                                                            // (150) 
	// **********************                                                      (151) /
	Section $2eme_Passage = new Section(this);                                  // (152)  2EME-PASSAGE SECTION.
	public void $2eme_Passage() {
		// **********************                                                  (153) *
		move(dfhcommarea, onlinm1s.onlinefs);                                   // (154)      MOVE DFHCOMMAREA       TO ONLINEFS.
		                                                                        // (155) 
		if (isKeyPressed(KeyPressed.CLEAR)) {                                   // (156)      IF EIBAID = DFHCLEAR OR
			                                                                    // (157)                  DFHPA1 OR
			                                                                    // (158)                  DFHPA2 OR
			                                                                    // (159)                  DFHPA3
			perform(p_Anykey) ;                                                 // (160)         PERFORM             P-ANYKEY
		}                                                                       // (161)      END-IF
		                                                                        // (162) 
		CESM.receiveMap(LanguageCode.FR).mapSet("ONLINE1")                      // (163)      EXEC CICS RECEIVE
			.into(onlinm1.onlinef) ;
		                                                                        // (164)           MAP('ONLINEF')
		                                                                        // (165)           MAPSET('ONLINE1')
		                                                                        // (166)           INTO(ONLINEFI)
		                                                                        // (167)      END-EXEC.
		                                                                        // (168) 
		perform(p_Merge_Masque) ;                                               // (169)      PERFORM P-MERGE-MASQUE.           
		                                                                        // (170) 
		// *** TEST DES DIFFERENTES TOUCHES FONCTIONS :                            (171) *
		if (isKeyPressed(KeyPressed.PF2)) {                                     // (172)      IF EIBAID              = DFHPF2
			perform(p_Masque_Vide) ;                                            // (173)         PERFORM             P-MASQUE-VIDE
		}
		else {                                                                  // (174)      ELSE
			if (isKeyPressed(KeyPressed.PF7)) {                                 // (175)         IF EIBAID           = DFHPF7
				perform(test_Sql) ;                                             // (176)            PERFORM          TEST-SQL
			}
			else {                                                              // (177)         ELSE
				if (isKeyPressed(KeyPressed.ENTER)) {                           // (178)            IF EIBAID        = DFHENTER
				}                                                               // (179)               CONTINUE
				else {                                                          // (180)            ELSE
					perform(p_Anykey) ;                                         // (181)               PERFORM       P-ANYKEY.
				}
			}
		}
		                                                                        // (182) 
		perform(plaus_Rel) ;                                                    // (183)      PERFORM                PLAUS-REL.
	}
	                                                                            // (184)      
	// *******************                                                         (185) /
	Section test_Sql = new Section(this);                                       // (186)  TEST-SQL SECTION.
	public void test_Sql() {
		// *******************                                                     (187) *
		move("1234", vtbmsga.no);                                               // (188)      MOVE '1234'            TO NO OF DVTBMSGA
		sql("SELECT * FROM TBMSG  WHERE NO = #1")                               // (189)      EXEC SQL
			.into(vtbmsga.dvtbmsga)
			.param(1, vtbmsga.no)
			.onErrorGoto(pc_Err_Db2) ;
		                                                                        // (190)           SELECT *
		                                                                        // (191)             INTO :DVTBMSGA
		                                                                        // (192)             FROM VTBMSGA
		                                                                        // (193)             WHERE NO = :DVTBMSGA.NO
		                                                                        // (194)      END-EXEC
		if (isSQLCode(SQLCode.SQL_OK)) {                                        // (195)      IF SQLCODE             = 0
			move(vtbmsga.text, onlinm1.lierr);                                  // (196)         MOVE TEXT OF DVTBMSGA TO LIERRI SLIERRI
			move(vtbmsga.text, onlinm1s.slierr);
		}
		else {                                                                  // (197)      ELSE
			move("0003", msgzone.msg_No);                                       // (198)         MOVE '0003'         TO MSG-NO
			setCursor(onlinm1.recoll) ;                                         // (199)         MOVE -1             TO RECOLLL
			perform(rech_Msgerr) ;                                              // (200)         PERFORM             RECH-MSGERR
		}                                                                       // (201)      END-IF
		                                                                        // (202)      
		perform(envoi_Masque) ;                                                 // (203)      PERFORM                ENVOI-MASQUE.
	}
	                                                                            // (204) 
	// *******************                                                         (205) /
	Section plaus_Rel = new Section(this);                                      // (206)  PLAUS-REL SECTION.
	public void plaus_Rel() {
		// *******************                                                     (207) *
		if (isLowValue(onlinm1s.srecoll)) {                                     // (208)      IF SRECOLLI            = LOW-VALUE
			move("0002", msgzone.msg_No);                                       // (209)         MOVE '0002'         TO MSG-NO
			setCursor(onlinm1.recoll) ;                                         // (210)         MOVE -1             TO RECOLLL
			moveAttribute(MapFieldAttrProtection.UNPROTECTED, onlinm1.recoll) ; // (211)         MOVE 'H'            TO RECOLLA
			moveAttribute(MapFieldAttrIntensity.BRIGHT, onlinm1.recoll) ;
			moveColor(MapFieldAttrColor.RED, onlinm1.recoll) ;                  // (212)         MOVE '2'            TO RECOLLC
			perform(rech_Msgerr) ;                                              // (213)         PERFORM             RECH-MSGERR.
		}
		                                                                        // (214)         
		perform(envoi_Masque) ;                                                 // (215)      PERFORM                ENVOI-MASQUE.
	}
	                                                                            // (216) 
	// **********************                                                      (217) /
	Section envoi_Masque = new Section(this);                                   // (218)  ENVOI-MASQUE SECTION.
	public void envoi_Masque() {
		// **********************                                                  (219) *
		move(getTime(), w_Eibtime);                                             // (220)      MOVE EIBTIME           TO W-EIBTIME
		move(w_Heure, w_Hh);                                                    // (221)      MOVE W-HEURE           TO W-HH
		move(w_Minute, w_Mi);                                                   // (222)      MOVE W-MINUTE          TO W-MI
		move(w_Seconde, w_Ss);                                                  // (223)      MOVE W-SECONDE         TO W-SS
		move(w_Heure_Form, onlinm1s.shrexec);                                   // (224)      MOVE W-HEURE-FORM      TO SHREXECI HREXECI
		move(w_Heure_Form, onlinm1.hrexec);
		                                                                        // (225) 
		if (isZero(sveibcalen)) {                                               // (226)      IF SVEIBCALEN          = ZERO
			move(onlinm1s.onlinefs, onlinm1.onlinef);                           // (227)         MOVE ONLINEFS       TO ONLINEFI
			CESM.sendMap(LanguageCode.FR).mapSet("ONLINE1")                     // (228)         EXEC CICS SEND
				.dataFrom(onlinm1s.onlinefs).cursor().erase().freeKB() ;
		                                                                        // (229)              MAP('ONLINEF')
		                                                                        // (230)              MAPSET('ONLINE1')
		                                                                        // (231)              FROM(ONLINEFI)
		                                                                        // (232)              FREEKB
		                                                                        // (233)              CURSOR
		                                                                        // (234)              ERASE
		}                                                                       // (235)         END-EXEC
		else {                                                                  // (236)      ELSE
			CESM.sendMap(LanguageCode.FR).mapSet("ONLINE1")                     // (237)         EXEC CICS SEND
				.dataOnlyFrom(onlinm1s.onlinefs).cursor().freeKB() ;
		                                                                        // (238)              MAP('ONLINEF')
		                                                                        // (239)              MAPSET('ONLINE1')
		                                                                        // (240)              FROM(ONLINEFI)
		                                                                        // (241)              FREEKB
		                                                                        // (242)              CURSOR
		                                                                        // (243)              DATAONLY
		                                                                        // (244)         END-EXEC
		}                                                                       // (245)      END-IF.
		                                                                        // (246) 
		CESM.returnTrans("TRA1", onlinm1s.onlinefs) ;                           // (247)      EXEC CICS RETURN
	}
	                                                                            // (248)           TRANSID('TRA1')
	                                                                            // (249)           COMMAREA(ONLINEFS)
	                                                                            // (250)      END-EXEC.
	                                                                            // (251) 
	// ***************************                                                 (252) /
	Section routines_Communes = new Section(this, false);                       // (253)  ROUTINES-COMMUNES SECTION.
	// ***************************                                              // (254)
	                                                                            // (255) 
	Paragraph p_Merge_Masque = new Paragraph(this);                             // (256)  P-MERGE-MASQUE.
	public void p_Merge_Masque() {
		// ---------------                                                         (257) *
		moveSpace(onlinm1.lierr) ;                                              // (258)      MOVE SPACE             TO LIERRI SLIERRI.
		moveSpace(onlinm1s.slierr) ;
		                                                                        // (259)      
		if (isFieldModified(onlinm1.recoll) || isFieldCleared(onlinm1.recoll)) {	// (260)      IF RECOLLL             > ZERO
			                                                                    // (261)         OR RECOLLF           = HEX80
			moveColor(MapFieldAttrColor.YELLOW, onlinm1.recoll) ;               // (262)         MOVE '6'            TO RECOLLC
			moveAttribute(MapFieldAttrProtection.UNPROTECTED, onlinm1.recoll) ; // (263)         MOVE 'D'            TO RECOLLA
			moveAttribute(MapFieldAttrIntensity.NORMAL, onlinm1.recoll) ;
			move(onlinm1.recoll, onlinm1s.srecoll);                             // (264)         MOVE RECOLLI        TO SRECOLLI
			moveLowValue(onlinm1.recoll) ;                                      // (265)         MOVE LOW-VALUE      TO RECOLLI.           
		}
	}
	                                                                            // (266) 
	Paragraph p_Masque_Vide = new Paragraph(this);                              // (267)  P-MASQUE-VIDE.
	public void p_Masque_Vide() {
		// --------------                                                          (268) *
		CESM.syncPointCommit() ;                                                // (269)      EXEC CICS SYNCPOINT END-EXEC
		CESM.xctl(ONLINE1.class).go() ;                                         // (270)      EXEC CICS XCTL
	}
	                                                                            // (271)           PROGRAM('ONLINE1')
	                                                                            // (272)      END-EXEC.
	                                                                            // (273) 
	Paragraph p_Anykey = new Paragraph(this);                                   // (274)  P-ANYKEY.
	public void p_Anykey() {
		// ---------                                                               (275) *
		move("0002", msgzone.msg_No);                                           // (276)      MOVE '0002'            TO MSG-NO
		setCursor(onlinm1.recoll) ;                                             // (277)      MOVE -1                TO RECOLLL
		perform(rech_Msgerr) ;                                                  // (278)      PERFORM                RECH-MSGERR
		perform(envoi_Masque) ;                                                 // (279)      PERFORM                ENVOI-MASQUE.
	}
	                                                                            // (280)      
	Paragraph rech_Msgerr = new Paragraph(this);                                // (281)  RECH-MSGERR.
	public void rech_Msgerr() {
		// ------------                                                            (282) *
		CESM.link(CALLMSG.class).commarea(msgzone.msg_Zone, -1, -1) ;           // (283)      EXEC CICS LINK
		                                                                        // (284)           PROGRAM('CALLMSG')
		                                                                        // (285)           COMMAREA(MSG-ZONE)
		                                                                        // (286)      END-EXEC
		move(msgzone.msg_Text, onlinm1.lierr);                                  // (287)      MOVE MSG-TEXT          TO LIERRI SLIERRI.
		move(msgzone.msg_Text, onlinm1s.slierr);
	}
	                                                                            // (288) 
	Paragraph pc_Err_Db2 = new Paragraph(this);                                 // (289)  PC-ERR-DB2.
	public void pc_Err_Db2() {
		// -----------                                                             (290) *
		move("D", w_Db2);                                                       // (291)      MOVE 'D'               TO W-DB2
		move(getSQLCode(), w_Sqlcode);                                          // (292)      MOVE SQLCODE TO W-SQLCODE
		CESM.abend(w_Abend_Db2) ;                                               // (293)      EXEC CICS ABEND
	}
}
                                                                                // (294)           ABCODE(W-ABEND-DB2)
                                                                                // (295)      END-EXEC.
