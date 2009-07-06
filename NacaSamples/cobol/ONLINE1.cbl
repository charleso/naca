      **************
       ID DIVISION.
      **************

       PROGRAM-ID.                ONLINE1.
       AUTHOR.                    XXXXXXXXX.
       DATE-WRITTEN.              2008.
      
      *REMARKS.                   
      *
      * Programme demo ONLINE
      *
      /**********************
       ENVIRONMENT DIVISION.
      ***********************

      ****************
       DATA DIVISION.
      ****************

       WORKING-STORAGE SECTION.
      *------------------------

       77  IND1                   PIC S9(4) COMP-4 SYNC VALUE +1.
       77  IND-LNG                PIC S9(4) COMP-4 SYNC VALUE +1.
       77  LONGCAR                PIC S9(4) COMP-4 SYNC VALUE +1.
       77  LONGERR                PIC S9(4) COMP-4 SYNC VALUE +92.
       77  LONGPLAUS              PIC S9(4) COMP-4 SYNC VALUE +505.
       77  LONGSMAP               PIC S9(4) COMP-4 SYNC VALUE +400.

       77  DATANAME               PIC X(8)         VALUE SPACE.
       77  W-CICS                 PIC X(8)         VALUE SPACE.
       77  HEX80                  PIC X            VALUE 'Ø'.
       77  ECR-VIERGE             PIC X            VALUE SPACE.
       77  SVEIBCALEN             PIC S9(4) COMP-4 VALUE ZERO.
       77  SVEIBFN                PIC X(2)         VALUE SPACE.

       77  W-CDCENPI              PIC X(3)         VALUE SPACE.

       01  W-ABEND-DB2.
           05 W-DB2               PIC X            VALUE 'D'.
           05 W-SQLCODE           PIC 9(3)         VALUE ZERO.

       01  W-DATE-FORM.
           05 W-JJ                PIC XX.
           05 FILLER              PIC X            VALUE '.'.
           05 W-MM                PIC XX.
           05 FILLER              PIC X            VALUE '.'.
           05 W-AA                PIC XX.

       01  W-DATE-X.
           05 W-SIECLE            PIC 99           VALUE ZERO.
           05 W-ANNEE             PIC 99           VALUE ZERO.
           05 W-MOIS              PIC 99           VALUE ZERO.
           05 W-JOUR              PIC 99           VALUE ZERO.
       01  W-DATE-N REDEFINES W-DATE-X
                                  PIC 9(8).

       01  W-EIBTIME              PIC 9(7)         VALUE ZERO.
       01  FILLER                 REDEFINES W-EIBTIME.
           05 FILLER              PIC X.
           05 W-HEURE             PIC XX.
           05 W-MINUTE            PIC XX.
           05 W-SECONDE           PIC XX.

       01  W-HEURE-FORM.
           05 W-HH                PIC XX.
           05 FILLER              PIC X            VALUE ':'.
           05 W-MI                PIC XX.
           05 FILLER              PIC X            VALUE ':'.
           05 W-SS                PIC XX.
       
       01  W-LIBEL.
           03 W-LIB               PIC X OCCURS 5.
       01  W-LIBEL1.
           03 W-LIB1-CICS         PIC X(2).
           03 W-LIB1-STE          PIC X(3).

      * COPY MAP
           COPY ONLINM1.

           EXEC SQL INCLUDE ONLINM1S END-EXEC.

      * COPY IO CALLMSG    
           COPY MSGZONE.

      * COPY DB2
           EXEC SQL INCLUDE SQLCA END-EXEC.

      * COPY INCLUDE TABLE
           EXEC SQL INCLUDE VTBMSGA END-EXEC.

           COPY DFHAID SUPPRESS.
      /*
       LINKAGE SECTION.
      *----------------
       01  DFHCOMMAREA.
           05 COMZONE  PIC X(1) OCCURS 1 TO 10000 DEPENDING ON EIBCALEN.

           EXEC SQL INCLUDE TUAZONE END-EXEC.

      /********************
       PROCEDURE DIVISION.
      *********************
           MOVE EIBFN             TO  SVEIBFN
           MOVE EIBCALEN          TO  SVEIBCALEN

           EXEC SQL WHENEVER SQLERROR GOTO PC-ERR-DB2 END-EXEC

           EXEC CICS ADDRESS
                TCTUA(ADDRESS OF TUA-ZONE)
           END-EXEC

           IF SVEIBFN = 'Ü'
      *** pgm appele par XCTL (SVEIBFN = '0E04')
              MOVE ZERO           TO  SVEIBCALEN
           END-IF.

       P-TRAITEMENT.
      *-------------
           IF SVEIBCALEN          = ZERO
              PERFORM             1ER-PASSAGE
           ELSE
              PERFORM             2EME-PASSAGE
           END-IF

           EXEC CICS RETURN END-EXEC

           GOBACK.

      /*********************
       1ER-PASSAGE SECTION.
      **********************
           MOVE LOW-VALUE         TO ONLINEFS
           
           MOVE CIXJOUR           TO W-JJ W-JOUR
           MOVE CIXMOIS           TO W-MM W-MOIS
           MOVE CIXAN             TO W-AA W-ANNEE
           MOVE W-DATE-FORM       TO TUA-I-DTJOURF
           IF W-ANNEE             < 84
              MOVE 20             TO W-SIECLE
           ELSE
              MOVE 19             TO W-SIECLE.
           MOVE W-DATE-N          TO TUA-I-DTJOUR
           
           MOVE TUA-I-DTJOURF     TO SDTEXECI DTEXECI
      
           MOVE -1                TO SRECOLLL RECOLLL
           PERFORM                ENVOI-MASQUE.

      /**********************
       2EME-PASSAGE SECTION.
      ***********************
           MOVE DFHCOMMAREA       TO ONLINEFS.

           IF EIBAID = DFHCLEAR OR
                       DFHPA1 OR
                       DFHPA2 OR
                       DFHPA3
              PERFORM             P-ANYKEY
           END-IF

           EXEC CICS RECEIVE
                MAP('ONLINEF')
                MAPSET('ONLINE1')
                INTO(ONLINEFI)
           END-EXEC.

           PERFORM P-MERGE-MASQUE.           

      **** TEST DES DIFFERENTES TOUCHES FONCTIONS :
           IF EIBAID              = DFHPF2
              PERFORM             P-MASQUE-VIDE
           ELSE
              IF EIBAID           = DFHPF7
                 PERFORM          TEST-SQL
              ELSE
                 IF EIBAID        = DFHENTER
                    CONTINUE
                 ELSE
                    PERFORM       P-ANYKEY.

           PERFORM                PLAUS-REL.
           
      /*******************
       TEST-SQL SECTION.
      ********************
           MOVE '1234'            TO NO OF DVTBMSGA
           EXEC SQL
                SELECT *
                  INTO :DVTBMSGA
                  FROM VTBMSGA
                  WHERE NO = :DVTBMSGA.NO
           END-EXEC
           IF SQLCODE             = 0
              MOVE TEXT OF DVTBMSGA TO LIERRI SLIERRI
           ELSE
              MOVE '0003'         TO MSG-NO
              MOVE -1             TO RECOLLL
              PERFORM             RECH-MSGERR
           END-IF
           
           PERFORM                ENVOI-MASQUE.

      /*******************
       PLAUS-REL SECTION.
      ********************
           IF SRECOLLI            = LOW-VALUE
              MOVE '0002'         TO MSG-NO
              MOVE -1             TO RECOLLL
              MOVE 'H'            TO RECOLLA
              MOVE '2'            TO RECOLLC
              PERFORM             RECH-MSGERR.
              
           PERFORM                ENVOI-MASQUE.

      /**********************
       ENVOI-MASQUE SECTION.
      ***********************
           MOVE EIBTIME           TO W-EIBTIME
           MOVE W-HEURE           TO W-HH
           MOVE W-MINUTE          TO W-MI
           MOVE W-SECONDE         TO W-SS
           MOVE W-HEURE-FORM      TO SHREXECI HREXECI

           IF SVEIBCALEN          = ZERO
              MOVE ONLINEFS       TO ONLINEFI
              EXEC CICS SEND
                   MAP('ONLINEF')
                   MAPSET('ONLINE1')
                   FROM(ONLINEFI)
                   FREEKB
                   CURSOR
                   ERASE
              END-EXEC
           ELSE
              EXEC CICS SEND
                   MAP('ONLINEF')
                   MAPSET('ONLINE1')
                   FROM(ONLINEFI)
                   FREEKB
                   CURSOR
                   DATAONLY
              END-EXEC
           END-IF.

           EXEC CICS RETURN
                TRANSID('TRA1')
                COMMAREA(ONLINEFS)
           END-EXEC.

      /***************************
       ROUTINES-COMMUNES SECTION.
      ****************************

       P-MERGE-MASQUE.
      *---------------
           MOVE SPACE             TO LIERRI SLIERRI.
           
           IF RECOLLL             > ZERO
              OR RECOLLF           = HEX80
              MOVE '6'            TO RECOLLC
              MOVE 'D'            TO RECOLLA
              MOVE RECOLLI        TO SRECOLLI
              MOVE LOW-VALUE      TO RECOLLI.           

       P-MASQUE-VIDE.
      *--------------
           EXEC CICS SYNCPOINT END-EXEC
           EXEC CICS XCTL
                PROGRAM('ONLINE1')
           END-EXEC.

       P-ANYKEY.
      *---------
           MOVE '0002'            TO MSG-NO
           MOVE -1                TO RECOLLL
           PERFORM                RECH-MSGERR
           PERFORM                ENVOI-MASQUE.
           
       RECH-MSGERR.
      *------------
           EXEC CICS LINK
                PROGRAM('CALLMSG')
                COMMAREA(MSG-ZONE)
           END-EXEC
           MOVE MSG-TEXT          TO LIERRI SLIERRI.

       PC-ERR-DB2.
      *-----------
           MOVE 'D'               TO W-DB2
           MOVE SQLCODE TO W-SQLCODE
           EXEC CICS ABEND
                ABCODE(W-ABEND-DB2)
           END-EXEC.
