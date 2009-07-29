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
 * Created on 17 janv. 2005
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

import nacaLib.varEx.*;
import nacaLib.basePrgEnv.BaseProgram;
import nacaLib.mapSupport.*;

public class TestMapForm extends Map 
{
	static TestMapForm Copy(BaseProgram program) 
	{
		return new TestMapForm(program);
	}
	
	TestMapForm(BaseProgram program) 
	{
		super(program);
	}
	                                                                            // (2) 
	Form TestMapFormf = declare.form("TestMapForm", 24, 80) ;                            // (3) 
		                                                                        // (4) 
		                                                                        // (5) 
		                                                                        // (6) 
		                                                                        // (7) 
		                                                                        // (8) 
		                                                                        // (9) 
		Edit nmmasq = declare.edit("nmmasq", 6).color(MapFieldAttrColor.GREEN).highLighting(MapFieldAttrHighlighting.OFF).intensity(MapFieldAttrIntensity.NORMAL).protection(MapFieldAttrProtection.AUTOSKIP).justifyFill(MapFieldAttrFill.BLANK).justify(MapFieldAttrJustify.LEFT).edit();	// (10) 
		                                                                        // (11) 
		                                                                        // (12) 
		                                                                        // (13) 
		                                                                        // (14) 
		                                                                        // (15) 
		                                                                        // (16) 
		                                                                        // (17) 
		                                                                        // (18) 
		                                                                        // (19) 
		Edit dtexec = declare.edit("dtexec", 8).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).intensity(MapFieldAttrIntensity.NORMAL).protection(MapFieldAttrProtection.AUTOSKIP).justifyFill(MapFieldAttrFill.BLANK).justify(MapFieldAttrJustify.LEFT).edit();	// (20) 
		                                                                        // (21) 
		                                                                        // (22) 
		                                                                        // (23) 
		                                                                        // (24) 
		Edit hrexec = declare.edit("hrexec", 8).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).intensity(MapFieldAttrIntensity.NORMAL).protection(MapFieldAttrProtection.AUTOSKIP).justifyFill(MapFieldAttrFill.BLANK).justify(MapFieldAttrJustify.LEFT).edit();	// (25) 
		                                                                        // (26) 
		                                                                        // (27) 
		                                                                        // (28) 
		                                                                        // (29) 
		                                                                        // (30) 
		                                                                        // (31) 
		                                                                        // (32) 
		Edit cdtrans = declare.edit("cdtrans", 6).color(MapFieldAttrColor.DEFAULT).highLighting(MapFieldAttrHighlighting.OFF).intensity(MapFieldAttrIntensity.DARK).protection(MapFieldAttrProtection.AUTOSKIP).justifyFill(MapFieldAttrFill.BLANK).justify(MapFieldAttrJustify.LEFT).setModified().edit();	// (33) 
		                                                                        // (34) 
		                                                                        // (35) 
		                                                                        // (36) 
		                                                                        // (37) 
		                                                                        // (38) 
		                                                                        // (39) 
		                                                                        // (40) 
		                                                                        // (41) 
		                                                                        // (42) 
		                                                                        // (43) 
		                                                                        // (44) 
		                                                                        // (45) 
		                                                                        // (46) 
		                                                                        // (47) 
		                                                                        // (48) 
		                                                                        // (49) 
		                                                                        // (50) 
		                                                                        // (51) 
		                                                                        // (52) 
		                                                                        // (53) 
		                                                                        // (54) 
		                                                                        // (55) 
		                                                                        // (56) 
		                                                                        // (57) 
		                                                                        // (58) 
		Edit cdstini = declare.edit("cdstini", 3).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).intensity(MapFieldAttrIntensity.NORMAL).protection(MapFieldAttrProtection.AUTOSKIP).justifyFill(MapFieldAttrFill.BLANK).justify(MapFieldAttrJustify.LEFT).edit();	// (59) 
		                                                                        // (60) 
		                                                                        // (61) 
		                                                                        // (62) 
		                                                                        // (63) 
		                                                                        // (64) 
		                                                                        // (65) 
		                                                                        // (66) 
		Edit siste = declare.edit("siste", 20).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).intensity(MapFieldAttrIntensity.NORMAL).protection(MapFieldAttrProtection.AUTOSKIP).justifyFill(MapFieldAttrFill.BLANK).justify(MapFieldAttrJustify.LEFT).edit();	// (67) 
		                                                                        // (68) 
		                                                                        // (69) 
		                                                                        // (70) 
		                                                                        // (71) 
		                                                                        // (72) 
		                                                                        // (73) 
		                                                                        // (74) 
		                                                                        // (75) 
		                                                                        // (76) 
		                                                                        // (77) 
		                                                                        // (78) 
		                                                                        // (79) 
		                                                                        // (80) 
		                                                                        // (81) 
		                                                                        // (82) 
		                                                                        // (83) 
		                                                                        // (84) 
		                                                                        // (85) 
		Edit cdcenpi = declare.edit("cdcenpi", 3).color(MapFieldAttrColor.YELLOW).highLighting(MapFieldAttrHighlighting.UNDERLINE).intensity(MapFieldAttrIntensity.NORMAL).protection(MapFieldAttrProtection.UNPROTECTED).justifyFill(MapFieldAttrFill.BLANK).justify(MapFieldAttrJustify.LEFT).edit();	// (86) 
		                                                                        // (87) 
		                                                                        // (88) 
		                                                                        // (89) 
		                                                                        // (90) 
		                                                                        // (91) 
		                                                                        // (92) 
		                                                                        // (93) 
		Edit destecr = declare.edit("destecr", 13).color(MapFieldAttrColor.TURQUOISE).highLighting(MapFieldAttrHighlighting.OFF).intensity(MapFieldAttrIntensity.NORMAL).protection(MapFieldAttrProtection.AUTOSKIP).justifyFill(MapFieldAttrFill.BLANK).justify(MapFieldAttrJustify.LEFT).edit();	// (94) 
		                                                                        // (95) 
		                                                                        // (96) 
		                                                                        // (97) 
		                                                                        // (98) 
		                                                                        // (99) 
		                                                                        // (100) 
		                                                                        // (101) 
		                                                                        // (102) 
		                                                                        // (103) 
		                                                                        // (104) 
		                                                                        // (105) 
		                                                                        // (106) 
		                                                                        // (107) 
		                                                                        // (108) 
		                                                                        // (109) 
		                                                                        // (110) 
		                                                                        // (111) 
		                                                                        // (112) 
		Edit recoll = declare.edit("recoll", 3).color(MapFieldAttrColor.YELLOW).highLighting(MapFieldAttrHighlighting.UNDERLINE).intensity(MapFieldAttrIntensity.NORMAL).protection(MapFieldAttrProtection.UNPROTECTED).justifyFill(MapFieldAttrFill.BLANK).justify(MapFieldAttrJustify.LEFT).edit();	// (113) 
		                                                                        // (114) 
		                                                                        // (115) 
		                                                                        // (116) 
		                                                                        // (117) 
		                                                                        // (118) 
		                                                                        // (119) 
		                                                                        // (120) 
		                                                                        // (121) 
		                                                                        // (122) 
		                                                                        // (123) 
		                                                                        // (124) 
		                                                                        // (125) 
		                                                                        // (126) 
		                                                                        // (127) 
		                                                                        // (128) 
		Edit passcol = declare.edit("passcol", 8).color(MapFieldAttrColor.DEFAULT).highLighting(MapFieldAttrHighlighting.OFF).intensity(MapFieldAttrIntensity.DARK).protection(MapFieldAttrProtection.UNPROTECTED).justifyFill(MapFieldAttrFill.BLANK).justify(MapFieldAttrJustify.LEFT).edit();	// (129) 
		                                                                        // (130) 
		                                                                        // (131) 
		                                                                        // (132) 
		                                                                        // (133) 
		Edit finpas1 = declare.edit("finpas1", 1).color(MapFieldAttrColor.DEFAULT).highLighting(MapFieldAttrHighlighting.OFF).intensity(MapFieldAttrIntensity.DARK).justifyFill(MapFieldAttrFill.BLANK).justify(MapFieldAttrJustify.LEFT).edit();	// (134) 
		                                                                        // (135) 
		                                                                        // (136) 
		                                                                        // (137) 
		                                                                        // (138) 
		                                                                        // (139) 
		                                                                        // (140) 
		                                                                        // (141) 
		                                                                        // (142) 
		                                                                        // (143) 
		                                                                        // (144) 
		                                                                        // (145) 
		                                                                        // (146) 
		                                                                        // (147) 
		                                                                        // (148) 
		                                                                        // (149) 
		Edit newpass = declare.edit("newpass", 8).color(MapFieldAttrColor.DEFAULT).highLighting(MapFieldAttrHighlighting.OFF).intensity(MapFieldAttrIntensity.DARK).protection(MapFieldAttrProtection.UNPROTECTED).justifyFill(MapFieldAttrFill.BLANK).justify(MapFieldAttrJustify.LEFT).edit();	// (150) 
		                                                                        // (151) 
		                                                                        // (152) 
		                                                                        // (153) 
		                                                                        // (154) 
		Edit finpas2 = declare.edit("finpas2", 1).color(MapFieldAttrColor.DEFAULT).highLighting(MapFieldAttrHighlighting.OFF).intensity(MapFieldAttrIntensity.DARK).justifyFill(MapFieldAttrFill.BLANK).justify(MapFieldAttrJustify.LEFT).edit();	// (155) 
		                                                                        // (156) 
		                                                                        // (157) 
		                                                                        // (158) 
		                                                                        // (159) 
		                                                                        // (160) 
		                                                                        // (161) 
		                                                                        // (162) 
		Edit lierr = declare.edit("lierr", 79).color(MapFieldAttrColor.RED).highLighting(MapFieldAttrHighlighting.OFF).intensity(MapFieldAttrIntensity.BRIGHT).protection(MapFieldAttrProtection.AUTOSKIP).justifyFill(MapFieldAttrFill.BLANK).justify(MapFieldAttrJustify.LEFT).edit();	// (163) 
}
