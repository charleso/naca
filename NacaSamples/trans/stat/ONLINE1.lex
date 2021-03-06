1:
1:[COMMENT: *************]
2:[ID][DIVISION][DOT]
3:[COMMENT: *************]
5:[PROGRAM-ID][DOT][ID:ONLINE1][DOT]
6:[AUTHOR][DOT][ID:XXXXXXXXX][DOT]
7:[DATE-WRITTEN][DOT](2008)[DOT]
9:[COMMENT: REMARKS.                   ]
10:[COMMENT: ]
11:[COMMENT:  Programme demo ONLINE]
12:[COMMENT: ]
13:[COMMENT: **********************]
14:[ENVIRONMENT][DIVISION][DOT]
15:[COMMENT: **********************]
17:[COMMENT: ***************]
18:[DATA][DIVISION][DOT]
19:[COMMENT: ***************]
21:[WORKING-STORAGE][SECTION][DOT]
22:[COMMENT: ------------------------]
24:(77)[ID:IND1][PIC][ID:S9][LEFT_BRACKET](4)[RIGHT_BRACKET][COMP-4][SYNC][VALUE][PLUS](1)[DOT]
25:(77)[ID:IND-LNG][PIC][ID:S9][LEFT_BRACKET](4)[RIGHT_BRACKET][COMP-4][SYNC][VALUE][PLUS](1)[DOT]
26:(77)[ID:LONGCAR][PIC][ID:S9][LEFT_BRACKET](4)[RIGHT_BRACKET][COMP-4][SYNC][VALUE][PLUS](1)[DOT]
27:(77)[ID:LONGERR][PIC][ID:S9][LEFT_BRACKET](4)[RIGHT_BRACKET][COMP-4][SYNC][VALUE][PLUS](92)[DOT]
28:(77)[ID:LONGPLAUS][PIC][ID:S9][LEFT_BRACKET](4)[RIGHT_BRACKET][COMP-4][SYNC][VALUE][PLUS](505)[DOT]
29:(77)[ID:LONGSMAP][PIC][ID:S9][LEFT_BRACKET](4)[RIGHT_BRACKET][COMP-4][SYNC][VALUE][PLUS](400)[DOT]
31:(77)[ID:DATANAME][PIC][ID:X][LEFT_BRACKET](8)[RIGHT_BRACKET][VALUE][SPACE][DOT]
32:(77)[ID:W-CICS][PIC][ID:X][LEFT_BRACKET](8)[RIGHT_BRACKET][VALUE][SPACE][DOT]
33:(77)[ID:HEX80][PIC][ID:X][VALUE]("�")[DOT]
34:(77)[ID:ECR-VIERGE][PIC][ID:X][VALUE][SPACE][DOT]
35:(77)[ID:SVEIBCALEN][PIC][ID:S9][LEFT_BRACKET](4)[RIGHT_BRACKET][COMP-4][VALUE][ZERO][DOT]
36:(77)[ID:SVEIBFN][PIC][ID:X][LEFT_BRACKET](2)[RIGHT_BRACKET][VALUE][SPACE][DOT]
38:(77)[ID:W-CDCENPI][PIC][ID:X][LEFT_BRACKET](3)[RIGHT_BRACKET][VALUE][SPACE][DOT]
40:(01)[ID:W-ABEND-DB2][DOT]
41:(05)[ID:W-DB2][PIC][ID:X][VALUE]("D")[DOT]
42:(05)[ID:W-SQLCODE][PIC](9)[LEFT_BRACKET](3)[RIGHT_BRACKET][VALUE][ZERO][DOT]
44:(01)[ID:W-DATE-FORM][DOT]
45:(05)[ID:W-JJ][PIC][ID:XX][DOT]
46:(05)[FILLER][PIC][ID:X][VALUE](".")[DOT]
47:(05)[ID:W-MM][PIC][ID:XX][DOT]
48:(05)[FILLER][PIC][ID:X][VALUE](".")[DOT]
49:(05)[ID:W-AA][PIC][ID:XX][DOT]
51:(01)[ID:W-DATE-X][DOT]
52:(05)[ID:W-SIECLE][PIC](99)[VALUE][ZERO][DOT]
53:(05)[ID:W-ANNEE][PIC](99)[VALUE][ZERO][DOT]
54:(05)[ID:W-MOIS][PIC](99)[VALUE][ZERO][DOT]
55:(05)[ID:W-JOUR][PIC](99)[VALUE][ZERO][DOT]
56:(01)[ID:W-DATE-N][REDEFINES][ID:W-DATE-X]
57:[PIC](9)[LEFT_BRACKET](8)[RIGHT_BRACKET][DOT]
59:(01)[ID:W-EIBTIME][PIC](9)[LEFT_BRACKET](7)[RIGHT_BRACKET][VALUE][ZERO][DOT]
60:(01)[FILLER][REDEFINES][ID:W-EIBTIME][DOT]
61:(05)[FILLER][PIC][ID:X][DOT]
62:(05)[ID:W-HEURE][PIC][ID:XX][DOT]
63:(05)[ID:W-MINUTE][PIC][ID:XX][DOT]
64:(05)[ID:W-SECONDE][PIC][ID:XX][DOT]
66:(01)[ID:W-HEURE-FORM][DOT]
67:(05)[ID:W-HH][PIC][ID:XX][DOT]
68:(05)[FILLER][PIC][ID:X][VALUE](":")[DOT]
69:(05)[ID:W-MI][PIC][ID:XX][DOT]
70:(05)[FILLER][PIC][ID:X][VALUE](":")[DOT]
71:(05)[ID:W-SS][PIC][ID:XX][DOT]
73:(01)[ID:W-LIBEL][DOT]
74:(03)[ID:W-LIB][PIC][ID:X][OCCURS](5)[DOT]
75:(01)[ID:W-LIBEL1][DOT]
76:(03)[ID:W-LIB1-CICS][PIC][ID:X][LEFT_BRACKET](2)[RIGHT_BRACKET][DOT]
77:(03)[ID:W-LIB1-STE][PIC][ID:X][LEFT_BRACKET](3)[RIGHT_BRACKET][DOT]
79:[COMMENT:  COPY MAP]
80:[COPY][ID:ONLINM1][DOT]
82:[EXEC][SQL][INCLUDE][ID:ONLINM1S][END-EXEC][DOT]
84:[COMMENT:  COPY IO CALLMSG    ]
85:[COPY][ID:MSGZONE][DOT]
87:[COMMENT:  COPY DB2]
88:[EXEC][SQL][INCLUDE][ID:SQLCA][END-EXEC][DOT]
90:[COMMENT:  COPY INCLUDE TABLE]
91:[EXEC][SQL][INCLUDE][ID:VTBMSGA][END-EXEC][DOT]
93:[COPY][ID:DFHAID][SUPPRESS][DOT]
94:[COMMENT: *]
95:[LINKAGE][SECTION][DOT]
96:[COMMENT: ----------------]
97:(01)[ID:DFHCOMMAREA][DOT]
98:(05)[ID:COMZONE][PIC][ID:X][LEFT_BRACKET](1)[RIGHT_BRACKET][OCCURS](1)[TO](10000)[DEPENDING][ON][ID:EIBCALEN][DOT]
100:[EXEC][SQL][INCLUDE][ID:TUAZONE][END-EXEC][DOT]
102:[COMMENT: ********************]
103:[PROCEDURE][DIVISION][DOT]
104:[COMMENT: ********************]
105:[MOVE][ID:EIBFN][TO][ID:SVEIBFN]
106:[MOVE][ID:EIBCALEN][TO][ID:SVEIBCALEN]
108:[EXEC][SQL][WHENEVER][SQLERROR][GOTO][ID:PC-ERR-DB2][END-EXEC]
110:[EXEC][CICS][ADDRESS]
111:[ID:TCTUA][LEFT_BRACKET][ADDRESS][OF][ID:TUA-ZONE][RIGHT_BRACKET]
112:[END-EXEC]
114:[IF][ID:SVEIBFN][EQUALS]("�")
115:[COMMENT: ** pgm appele par XCTL (SVEIBFN = '0E04')]
116:[MOVE][ZERO][TO][ID:SVEIBCALEN]
117:[END-IF][DOT][END_OF_BLOCK]
119:[ID:P-TRAITEMENT][DOT]
120:[COMMENT: -------------]
121:[IF][ID:SVEIBCALEN][EQUALS][ZERO]
122:[PERFORM][ID:1ER-PASSAGE]
123:[ELSE]
124:[PERFORM][ID:2EME-PASSAGE]
125:[END-IF]
127:[EXEC][CICS][RETURN][END-EXEC]
129:[GOBACK][DOT]
131:[COMMENT: *********************]
132:[ID:1ER-PASSAGE][SECTION][DOT]
133:[COMMENT: *********************]
134:[MOVE][LOW-VALUE][TO][ID:ONLINEFS]
136:[MOVE][ID:CIXJOUR][TO][ID:W-JJ][ID:W-JOUR]
137:[MOVE][ID:CIXMOIS][TO][ID:W-MM][ID:W-MOIS]
138:[MOVE][ID:CIXAN][TO][ID:W-AA][ID:W-ANNEE]
139:[MOVE][ID:W-DATE-FORM][TO][ID:TUA-I-DTJOURF]
140:[IF][ID:W-ANNEE][LESS](84)
141:[MOVE](20)[TO][ID:W-SIECLE]
142:[ELSE]
143:[MOVE](19)[TO][ID:W-SIECLE][DOT]
144:[MOVE][ID:W-DATE-N][TO][ID:TUA-I-DTJOUR]
146:[MOVE][ID:TUA-I-DTJOURF][TO][ID:SDTEXECI][ID:DTEXECI]
148:[MOVE][MINUS](1)[TO][ID:SRECOLLL][ID:RECOLLL]
149:[PERFORM][ID:ENVOI-MASQUE][DOT]
151:[COMMENT: **********************]
152:[ID:2EME-PASSAGE][SECTION][DOT]
153:[COMMENT: **********************]
154:[MOVE][ID:DFHCOMMAREA][TO][ID:ONLINEFS][DOT]
156:[IF][ID:EIBAID][EQUALS][ID:DFHCLEAR][OR]
157:[ID:DFHPA1][OR]
158:[ID:DFHPA2][OR]
159:[ID:DFHPA3]
160:[PERFORM][ID:P-ANYKEY]
161:[END-IF]
163:[EXEC][CICS][RECEIVE]
164:[ID:MAP][LEFT_BRACKET]("ONLINEF")[RIGHT_BRACKET]
165:[ID:MAPSET][LEFT_BRACKET]("ONLINE1")[RIGHT_BRACKET]
166:[INTO][LEFT_BRACKET][ID:ONLINEFI][RIGHT_BRACKET]
167:[END-EXEC][DOT]
169:[PERFORM][ID:P-MERGE-MASQUE][DOT]
171:[COMMENT: *** TEST DES DIFFERENTES TOUCHES FONCTIONS :]
172:[IF][ID:EIBAID][EQUALS][ID:DFHPF2]
173:[PERFORM][ID:P-MASQUE-VIDE]
174:[ELSE]
175:[IF][ID:EIBAID][EQUALS][ID:DFHPF7]
176:[PERFORM][ID:TEST-SQL]
177:[ELSE]
178:[IF][ID:EIBAID][EQUALS][ID:DFHENTER]
179:[CONTINUE]
180:[ELSE]
181:[PERFORM][ID:P-ANYKEY][DOT]
183:[PERFORM][ID:PLAUS-REL][DOT]
185:[COMMENT: *******************][END_OF_BLOCK]
186:[ID:TEST-SQL][SECTION][DOT]
187:[COMMENT: *******************]
188:[MOVE]("1234")[TO][ID:NO][OF][ID:DVTBMSGA]
189:[EXEC][SQL]
190:[SELECT][STAR]
191:[INTO][COLON][ID:DVTBMSGA]
192:[FROM][ID:VTBMSGA]
193:[WHERE][ID:NO][EQUALS][COLON][ID:DVTBMSGA][DOT][ID:NO]
194:[END-EXEC]
195:[IF][ID:SQLCODE][EQUALS](0)
196:[MOVE][ID:TEXT][OF][ID:DVTBMSGA][TO][ID:LIERRI][ID:SLIERRI]
197:[ELSE]
198:[MOVE]("0003")[TO][ID:MSG-NO]
199:[MOVE][MINUS](1)[TO][ID:RECOLLL]
200:[PERFORM][ID:RECH-MSGERR]
201:[END-IF]
203:[PERFORM][ID:ENVOI-MASQUE][DOT]
205:[COMMENT: *******************][END_OF_BLOCK]
206:[ID:PLAUS-REL][SECTION][DOT]
207:[COMMENT: *******************]
208:[IF][ID:SRECOLLI][EQUALS][LOW-VALUE]
209:[MOVE]("0002")[TO][ID:MSG-NO]
210:[MOVE][MINUS](1)[TO][ID:RECOLLL]
211:[MOVE]("H")[TO][ID:RECOLLA]
212:[MOVE]("2")[TO][ID:RECOLLC]
213:[PERFORM][ID:RECH-MSGERR][DOT]
215:[PERFORM][ID:ENVOI-MASQUE][DOT]
217:[COMMENT: **********************][END_OF_BLOCK]
218:[ID:ENVOI-MASQUE][SECTION][DOT]
219:[COMMENT: **********************]
220:[MOVE][ID:EIBTIME][TO][ID:W-EIBTIME]
221:[MOVE][ID:W-HEURE][TO][ID:W-HH]
222:[MOVE][ID:W-MINUTE][TO][ID:W-MI]
223:[MOVE][ID:W-SECONDE][TO][ID:W-SS]
224:[MOVE][ID:W-HEURE-FORM][TO][ID:SHREXECI][ID:HREXECI]
226:[IF][ID:SVEIBCALEN][EQUALS][ZERO]
227:[MOVE][ID:ONLINEFS][TO][ID:ONLINEFI]
228:[EXEC][CICS][SEND]
229:[ID:MAP][LEFT_BRACKET]("ONLINEF")[RIGHT_BRACKET]
230:[ID:MAPSET][LEFT_BRACKET]("ONLINE1")[RIGHT_BRACKET]
231:[FROM][LEFT_BRACKET][ID:ONLINEFI][RIGHT_BRACKET]
232:[FREEKB]
233:[CURSOR]
234:[ERASE]
235:[END-EXEC]
236:[ELSE]
237:[EXEC][CICS][SEND]
238:[ID:MAP][LEFT_BRACKET]("ONLINEF")[RIGHT_BRACKET]
239:[ID:MAPSET][LEFT_BRACKET]("ONLINE1")[RIGHT_BRACKET]
240:[FROM][LEFT_BRACKET][ID:ONLINEFI][RIGHT_BRACKET]
241:[FREEKB]
242:[CURSOR]
243:[DATAONLY]
244:[END-EXEC]
245:[END-IF][DOT]
247:[EXEC][CICS][RETURN]
248:[TRANSID][LEFT_BRACKET]("TRA1")[RIGHT_BRACKET]
249:[COMMAREA][LEFT_BRACKET][ID:ONLINEFS][RIGHT_BRACKET]
250:[END-EXEC][DOT]
252:[COMMENT: ***************************][END_OF_BLOCK]
253:[ID:ROUTINES-COMMUNES][SECTION][DOT]
254:[COMMENT: ***************************][END_OF_BLOCK]
256:[ID:P-MERGE-MASQUE][DOT]
257:[COMMENT: ---------------]
258:[MOVE][SPACE][TO][ID:LIERRI][ID:SLIERRI][DOT]
260:[IF][ID:RECOLLL][GREATER][ZERO]
261:[OR][ID:RECOLLF][EQUALS][ID:HEX80]
262:[MOVE]("6")[TO][ID:RECOLLC]
263:[MOVE]("D")[TO][ID:RECOLLA]
264:[MOVE][ID:RECOLLI][TO][ID:SRECOLLI]
265:[MOVE][LOW-VALUE][TO][ID:RECOLLI][DOT][END_OF_BLOCK]
267:[ID:P-MASQUE-VIDE][DOT]
268:[COMMENT: --------------]
269:[EXEC][CICS][SYNCPOINT][END-EXEC]
270:[EXEC][CICS][XCTL]
271:[PROGRAM][LEFT_BRACKET]("ONLINE1")[RIGHT_BRACKET]
272:[END-EXEC][DOT][END_OF_BLOCK]
274:[ID:P-ANYKEY][DOT]
275:[COMMENT: ---------]
276:[MOVE]("0002")[TO][ID:MSG-NO]
277:[MOVE][MINUS](1)[TO][ID:RECOLLL]
278:[PERFORM][ID:RECH-MSGERR]
279:[PERFORM][ID:ENVOI-MASQUE][DOT][END_OF_BLOCK]
281:[ID:RECH-MSGERR][DOT]
282:[COMMENT: ------------]
283:[EXEC][CICS][LINK]
284:[PROGRAM][LEFT_BRACKET]("CALLMSG")[RIGHT_BRACKET]
285:[COMMAREA][LEFT_BRACKET][ID:MSG-ZONE][RIGHT_BRACKET]
286:[END-EXEC]
287:[MOVE][ID:MSG-TEXT][TO][ID:LIERRI][ID:SLIERRI][DOT][END_OF_BLOCK]
289:[ID:PC-ERR-DB2][DOT]
290:[COMMENT: -----------]
291:[MOVE]("D")[TO][ID:W-DB2]
292:[MOVE][ID:SQLCODE][TO][ID:W-SQLCODE]
293:[EXEC][CICS][ID:ABEND]
294:[ID:ABCODE][LEFT_BRACKET][ID:W-ABEND-DB2][RIGHT_BRACKET]
295:[END-EXEC][DOT]