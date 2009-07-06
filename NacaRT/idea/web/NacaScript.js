var nProfitCenter = '';
var nServerName = '';
var nTerminalName = '';
var nUserName = '';

var nUpdateTime = '';

var nForm = '';
var nIdPage = '';
var nCmpSession = '';
var nFocus = '';
var nLanguage = '';
var nOnload = '';
var nOnsubmit = '';
var nIsZoom = false;
var nIsBold = false;
var nIsPrintScreen = false;
var nIsStartPrintScreen = false;
var nIsShowScreen = false;

var nSize = 8;
var nSizeAdd = 8;
var nSizeAddEdit = 6;
var nSizeHeight = 24;

var nTitle = '';
var nLine = false;
var nLineSpecial = false;

var nPf = new Array();

var nDateStart = new Date();

function n_start() {
	if (nIsPrintScreen) {
		nSizeAdd = 10;
		document.write("<TABLE border=0 cellspacing=0 cellpadding=0><TR valign=top>");
		document.write("<FORM action=\"" + nForm + "\" method=post name=form>");
		document.write("<TD><TABLE border=0 cellspacing=0 cellpadding=0 width=100%><TR>");
		document.write("<TD width=300></TD>");
		document.write("<TD><TABLE border=0 cellspacing=0 cellpadding=0>");
		n_showInfo();
		document.write("</TABLE></TD>");
		document.write("<TD width=50></TD>");
		document.write("<TD><TABLE border=0 cellspacing=0 cellpadding=0>");
		n_showLogo();
		document.write("</TABLE></TD>");
		document.write("</TR></TABLE></TD>");
		
		document.write("<TR><TR><TD><TABLE border=0 cellspacing=0 cellpadding=0 width=100%>");
		n_showForm();
		n_showTitle();
	} else {
		if (nIsZoom) {
			nSize = 11;
			nSizeAdd = 11;
			nSizeAddEdit = 8;
			nSizeHeight = 28;
		}
		document.write("<TABLE border=0 cellspacing=0 cellpadding=0><TR valign=top>");
		document.write("<FORM action=\"" + nForm + "\" method=post name=form>");
		document.write("<TD><TABLE border=0 cellspacing=0 cellpadding=0 width=100%>");		
		n_showLogo();
		document.write("<TR><TD height=10></TD></TR>");
		if (nIsShowScreen) {
			n_showScreen();
		} else {
			n_showInfo();
			document.write("<TR><TD height=10></TD></TR>");
			n_showSpecial();
			document.write("<TR><TD height=10></TD></TR>");		
			n_showPf();
		}	
		document.write("</TABLE></TD>");
		
		document.write("<TD width=20></TD>");
		document.write("<TD><TABLE border=0 cellspacing=0 cellpadding=0 width=100%>");
		n_showForm();
		n_showTitle();
	}
}

function n_stop() {
	n_checkEndLine();
	document.write("</FORM>");
	document.write("</TABLE></TD>");	

	document.write("</TR></TABLE>");
	if (nIsPrintScreen) {
		n_showPrintScreen();
	} else {
		n_showPfAction();
		n_showFocus();
		n_showStartPrintScreen();
		if (nOnload != "") {
	  		eval(nOnload + "()");
    	}
	}
}

function n_showForm() {	
	var myLine = "<INPUT type=hidden name=idPage value=\"" + nIdPage + "\">";
	myLine += "<INPUT type=hidden name=idLang value=\"" + nLanguage + "\">";
	myLine += "<INPUT type=hidden name=cmpSession value=\"" + nCmpSession + "\">";
	myLine += "<INPUT type=hidden name=elapsedTime>";
	myLine += "<INPUT type=hidden name=PFKey>";
	myLine += "<INPUT type=hidden name=printScreen>";
	myLine += "<INPUT type=hidden name=zoom>";
	myLine += "<INPUT type=hidden name=bold>";
	document.write(myLine);
}

function n_showTitle() {
	var myLine = "<TR><TD height=12></TD></TR>";
	myLine += "<TR><TD class=tit>" + nTitle + "</TD></TR>";
	document.write(myLine);
}

function n_showFocus() {
	window.focus();
	if (nFocus != '') {
		eval("topFocus(document.form." + nFocus + ", document.form)");
	} else {
		topFocusForm(document.form);
	}
}
function n_showPrintScreen() {
	if (window.print) self.print();
  	setTimeout('self.close()', 3000);
}
function n_showStartPrintScreen() {
	if (nIsStartPrintScreen) {
		topOpen('showprintscreen', 'ps', 20, 220, 750, 500);
	}
}

function n_showLogo() {
	var logoApp = "images/logoApplication.gif";
	var logoCompany = "images/logoCompany.gif";
	document.write("<TR><TD><TABLE BORDER=0 cellspacing=0 cellpadding=0 width=100%>");
	document.write("<TR><TD width=30></TD>");
	document.write("<TD><TABLE BORDER=0 cellspacing=0 cellpadding=0 width=132>");
	document.write("<TR><TD height=30></TD></TR>");
	document.write("<TR><TD><IMAGE src=" + logoCompany + "></IMAGE></TD></TR>");
	document.write("<TR><TD height=30></TD></TR>");
	document.write("</TABLE></TD></TR>");
	document.write("<TR><TD colspan=3><TABLE BORDER=0 cellspacing=0 cellpadding=0 width=172>");
	document.write("<TR><TD align=right><IMAGE src=" + logoApp + "></IMAGE></TD></TR>");
	document.write("</TABLE></TD></TR>");
	document.write("</TABLE></TD></TR>");
}
function n_showInfo() {
	var myLine = "<TR><TD><TABLE border=0 cellspacing=0 cellpadding=0 width=100%>";
	myLine += "<TR>";
	myLine += "<TD width=20></TD>";
	myLine += "<TD><TABLE border=0 class=tableinfo width=152>";
	myLine += "<TR><TD height=16>" + nServerName + "  (" + nTerminalName + ")</TD></TR>";
	myLine += "<TR><TD height=16>" + nUserName + "</TD></TR>";
	myLine += "</TABLE></TD>";
	myLine += "</TR>";
	myLine += "</TABLE></TD></TR>";
	document.write(myLine);
}
function n_showSpecial() {
	var myLine = "<TR><TD><TABLE border=0 cellspacing=0 cellpadding=0 width=100%>";
	myLine += "<TR>";
	myLine += "<TD width=20></TD>";
	myLine += "<TD><A href=javascript:topPrintScreen()><IMG src=images/ico_printscreen.gif border=0></A></TD>";
	if (nIsBold) {
		myLine += "<TD width=22></TD><TD><A href=javascript:topBold()><IMG src=images/ico_bold_minus.gif border=0></A></TD>";
	} else {
		myLine += "<TD width=22></TD><TD><A href=javascript:topBold()><IMG src=images/ico_bold_plus.gif border=0></A></TD>";
	}
	if (nIsZoom) {
		myLine += "<TD width=10></TD><TD><A href=javascript:topZoom()><IMG src=images/ico_zoom_minus.gif border=0></A></TD>";
	} else {
		myLine += "<TD width=10></TD><TD><A href=javascript:topZoom()><IMG src=images/ico_zoom_plus.gif border=0></A></TD>";
	}
	myLine += "<TD width=20></TD>";
	myLine += "</TR>";
	myLine += "</TABLE></TD></TR>";
	document.write(myLine);
}

function n_showScreen() {
	var myLine = "<TR><TD><TABLE border=0 cellspacing=0 cellpadding=0 width=100%>";
	myLine += "<TR><TD height=20></TD></TR>";
	myLine += "<TR>";
	myLine += "<TD width=20></TD>";
	myLine += "<TD class=txtin>Page</TD>";
	myLine += "<TD><INPUT class=txtin style=width:80px TYPE=text NAME=showPage VALUE=" + nIdPage + " width=8 maxlength=8></TD></TR>";
	myLine += "</TR>";
	myLine += "<TR><TD height=5></TD></TR>";
	myLine += "<TR>";
	myLine += "<TD></TD>";
	myLine += "<TD class=txtin>Langue</TD>";
	myLine += "<TD><SELECT class=txtin style=width:80px TYPE=text NAME=showLanguage>";
	myLine += "<OPTION VALUE=FR>FR</OPTION>";
	myLine += "<OPTION VALUE=DE>DE</OPTION>";
	myLine += "<OPTION VALUE=IT>IT</OPTION>";
	myLine += "</SELECT></TD>";
	myLine += "</TR>";
	myLine += "</TABLE></TD></TR>";
	myLine += "<TR><TD height=20></TD></TR>";
	myLine += "<TR><TD><TABLE border=0 cellspacing=0 cellpadding=0 width=100%><TR>";
	myLine += "<TD width=20></TD>";
	myLine += "<TD><TABLE border=0 cellspacing=0 cellpadding=0>";	
	myLine += n_showPfkey("enter", "images/ico_enter_lo.gif", "Enter", "");
	myLine += "</TABLE></TD>";
	myLine += "</TR></TABLE></TD></TR>";
	myLine += "<TR><TD height=20></TD></TR>";
	myLine += "<TR><TD><TABLE border=0 cellspacing=0 cellpadding=0 width=100%>";
	myLine += "<TR>";
	myLine += "<TD width=50></TD>";
	myLine += "<TD><TABLE border=0 class=tableinfo width=92>";
	for (i=0; i < nPf.length; i++) {
		myLine += "<TR><TD ALIGN=CENTER>" + nPf[i][1] + "</TD></TR>";
	}
	myLine += "</TABLE></TD>";
	myLine += "</TR>";
	myLine += "</TABLE></TD></TR>";
	document.write(myLine);
}

function n_showPf() {
	var myLine = "<TR><TD><TABLE border=0 cellspacing=0 cellpadding=0 width=100%><TR>";
	myLine += "<TD width=20></TD>";
	myLine += "<TD><TABLE border=0 cellspacing=0 cellpadding=0>";
	myLine += n_showPfkey("pf1", "images/language/ico_F1_lo.gif", "Help", "topOnFocus(null, '');");
	myLine += "<TR><TD height=10></TD></TR>";
	myLine += n_showPfkey("pf3", "images/language/ico_F3_lo.gif", "F3", "");
	myLine += "<TR><TD height=10></TD></TR>";
	myLine += n_showPfkey("pf9", "images/language/ico_F9_lo.gif", "F9", "");
	myLine += "<TR><TD height=30></TD></TR>";
	myLine += n_showPfkey("pf7", "images/ico_F7_lo.gif", "F7", "");
	myLine += "<TR><TD height=10></TD></TR>";
	myLine += n_showPfkey("pf8", "images/ico_F8_lo.gif", "F8", "");
	myLine += "<TR><TD height=30></TD></TR>";
	myLine += n_showPfkey("pf2", "images/language/ico_F2_lo.gif", "F2", "");
	myLine += "<TR><TD height=10></TD></TR>";
	myLine += n_showPfkey("pf12", "images/language/ico_F12_lo.gif", "F12", "");
	myLine += "<TR><TD height=10></TD></TR>";
	myLine += n_showPfkey("pf4", "images/language/ico_F4_lo.gif", "F4", "");
	myLine += "<TR><TD height=30></TD></TR>";
	myLine += n_showPfkey("chguser", "images/ico_l_ogin_lo.gif", "Login", "");
	myLine += "<TR><TD height=10></TD></TR>";
	myLine += n_showPfkey("logout", "images/ico_l_ogout_lo.gif", "Logout", "");
	myLine += "<TR><TD height=30></TD></TR>";
	myLine += n_showPfkey("enter", "images/ico_enter_lo.gif", "Enter", "");
	myLine += "<TR><TD height=10></TD></TR>";
	myLine += "<TR><TD><img src=images/ico_progress_lo.gif name=progress width=150 height=10></TD></TR>";
	if (nUpdateTime != '') {
		myLine += "<TR><TD height=10></TD></TR>";
		myLine += "<TR><TD style='FONT-WEIGHT:bold;FONT-SIZE:13px;COLOR:#ff3300;'>Restart at " + nUpdateTime + "</TD></TR>";
	}
	myLine += "</TABLE></TD>";
	myLine += "</TR>";
	myLine += "</TR></TABLE></TD></TR>";
	document.write(myLine);
}
function n_showPfkey(key, logo, display, specialAction) {
	var myLinePart = '';
	var myPfKey = n_getPfKey(key);
	if (myPfKey == null) {
		myLinePart += "<TR><TD><IMG src=images/empty.gif width=1 height=21 border=0></TD></TR>";
	} else {
		if (nLanguage == '') {
			logo = logo.replace('language', 'FR');
		} else {
			logo = logo.replace('language', nLanguage);
		}
		myLinePart += "<TR><TD><A href=\"javascript:" + specialAction + "topEnter('" + myPfKey[1] + "')\"; onMouseOver=\"topOver('" + myPfKey[1] + "')\" onMouseOut=\"topOut('" + myPfKey[1] + "')\"><IMG src=" + logo + " alt=\"" + display + "\" name=" + myPfKey[1] + " width=150 height=21 border=0></A></TD></TR>";
	}
	return myLinePart;
}

function n_showPfAction() {
	document.write("<scr" + "ipt language=JavaScript>");
	document.write("function doSubmit() {");
	if (nOnsubmit == "") {
		document.write("  top.document.form.submit();topOver('progress');");
	} else {
		document.write("  if (" + nOnsubmit + "()) {");
		document.write("     top.document.form.submit();topOver('progress');");
		document.write("  }");
	}
	document.write("}");

	document.write("function topEnter(key) {");
	document.write("  var nDateStop = new Date();");
	document.write("  var nTimeElapsed = nDateStop - nDateStart;");
	document.write("  top.document.form.elapsedTime.value = nTimeElapsed;");
	document.write("  top.document.form.PFKey.value = key;");
	document.write("  if (key != 112 && key != 117) window.focus();");
	document.write("  if (key == 0) {");
	document.write("  }");
	for (i=0; i < nPf.length; i++) {
		document.write("  else if (key == " + nPf[i][0] + " || key == '" + nPf[i][1] + "') {");
		myAction = nPf[i][3];
		if (myAction == "") {
			myAction = "top.document.form.submit();topOver('progress');";
		}
		document.write("    top.document.form.PFKey.value = '" + nPf[i][2] + "';");
		document.write("    " + myAction);
		document.write("  }");
	}
	document.write("  else {");
	document.write("    topFocusCurrent();");
	document.write("  }");
	document.write("}");
	document.write("</scr" + "ipt>");
}
function n_getPfKey(key) {
	for (i=0; i < nPf.length; i++) {
		if (nPf[i][1] == key) return nPf[i];
	}
	return null;
}

function n_update(updateTime) {
	nUpdateTime = updateTime;
}

function n_user(profitCenter, serverName, terminalName, userName) {
	nProfitCenter = profitCenter;
	nServerName = serverName;
	nTerminalName = terminalName;
	nUserName = userName;
}

function n_form(form, idPage, cmpSession, focus, defaultFocus, language, zoom, bold, printScreen, onload, onsubmit) {
	nForm = form;
	nIdPage = idPage;
	nCmpSession = cmpSession;
	nFocus = focus;
	if (nFocus == "") nFocus = defaultFocus;
	nLanguage = language;
	nIsZoom = zoom;
	nIsBold = bold;
	if (printScreen == "true") {
		nIsPrintScreen = true;
	} else if (printScreen == "show") {
		nIsStartPrintScreen = true;
	} else if (printScreen == "showScreen") {
		nIsShowScreen = true;
	}
	nOnload = onload;
	nOnsubmit = onsubmit;
}

function n_addPf(key, name, value, action) {
	nPf.push(new Array(key, name, value, action));
}

function n_title(title) {
	nTitle = title;
}

function n_line() {
	n_checkEndLine();
	nLine = true;
	document.write("<TR><TD><TABLE border=0 cellspacing=0 cellpadding=0><TR>");
}
function n_lineSpecial() {
	n_checkEndLine();
	nLineSpecial = true;
	document.write("<TR><TD><TABLE border=0 cellspacing=0 cellpadding=0><TR>");
}
function n_checkEndLine() {
	if (nLine) {
		nLine = false;
		document.write("<TD class=txtdis height=" + nSizeHeight + "></TD></TR></TABLE></TD></TR>");
	} else if (nLineSpecial) {
		nLineSpecial = false;
		document.write("</TR></TABLE></TD></TR>");
	}
}

function n_image(ref, name, width, height) {
	document.write("<TD><IMG src=" + ref + " name=\"" + name + "\" width=" + width + " height=" + height + "/></TD>");
}

function n_underline(start, length) {
	var myLine = "<TD><TABLE border=0 cellspacing=0 cellpadding=0><TR>";
	if (start > 1)
		myLine += "<TD style=\"width:" + (start * nSize) + "px;height:3px;\"></TD>";
	myLine += "<TD style=\"width:" + ((length * nSize) + nSizeAdd) + "px;height:3px;\"><IMG src=images/ico_line.gif width=" + ((length * nSize) + nSizeAdd) + " height=1/></TD>";
	myLine += "</TR></TABLE></TD>";
	document.write(myLine);
}

function n_height(length) {
	document.write("<TD height=" + length + "></TD>");
}

function n_blank(length) {
	document.write("<TD class=txtdis style=\"width:" + ((length * nSize) + nSizeAdd) + "px\"></TD>");
}

function n_label(type, value, action, activeChoiceTarget, activeChoiceValue, activeChoiceLink, activeChoiceSubmit, highlighting, intensity, color, length) {
	var myLine = "<TD class=txtdis style=\"" + n_getStyle('label', highlighting, color, length) + "\">";
	if (intensity != 'dark') {
		if (type == 'activeChoice' || type == 'linkedActiveChoice' || type == 'actionActiveChoice') {
			myLine += n_getActiveLabel(type, value, 'txtdis', '', action, activeChoiceTarget, activeChoiceValue, activeChoiceLink, activeChoiceSubmit);
		} else {
			myLine += n_changeSpace(value);
		}
	}
	myLine += "</TD>";
	document.write(myLine);
}

function n_edit(type, name, value, valueOn, valueOff, valueDefault, modified, messageId, activeChoiceTarget, activeChoiceValue, activeChoiceLink, activeChoiceSubmit, protection, highlighting, intensity, color, line, length) {
	var myLine = "<TD class=txtout";
	if (type == 'hidden') {
		myLine += "><INPUT type=hidden name=" + name + " value=\"" + n_changeQuote(value) + "\">";
		myLine += n_edit_UPD(name, modified);
	} else if (protection == 'autoskip' && !n_isSpace(value)) {
		myLine += " style=\"" + n_getStyle('label', highlighting, color, length) + "\">";
		myLine += "<INPUT type=hidden name=" + name + " value=\"" + n_changeQuote(value) + "\">";
		if (intensity != 'dark' && messageId != '' && highlighting == 'reverse') {
			myLine += n_getActiveLabel('action', value, 'txtdis', '', 'javascript:topOnDevelopMessage(\'' + messageId + '\')', '', '', '', '');
		} else if (intensity != 'dark' && messageId != '') {
			myLine += n_getActiveLabel('action', value, 'txterror', '', 'javascript:topOnDevelopMessage(\'' + messageId + '\')', '', '', '', '');
		} else if (intensity != 'dark' && (type == 'activeChoice' || type == 'linkedActiveChoice')) {
			myLine += n_getActiveLabel(type, value, 'txtdis', n_getStyle('link', highlighting, color, length), "", activeChoiceTarget, activeChoiceValue, activeChoiceLink, activeChoiceSubmit);
		} else if (intensity != 'dark') {
			myLine += n_changeSpace(value);
		} else {
		}
	} else if (protection == 'unprotected' || protection == 'numeric') {
		if (color == 'red') {
			myLine += " style=\"width:" + ((length * nSize) + nSizeAdd + 12) + "px;\">";
			myLine += "<IMG src=images/ico_error.gif width=12 height=9 border=0 align=absmiddle>";
		} else {
			myLine += " style=\"width:" + ((length * nSize) + nSizeAdd) + "px;\">";
		}
		if (type == 'checkbox') {
			myLine += n_edit_UPD(name, modified);
			myLine += "<INPUT type=hidden name=" + name + " value=\"" + value + "\">";
			myLine += "<INPUT class=txtin valign=top style=\"" + n_getStyle('edit', highlighting, color, length);
			if (value != "" && value.toUpperCase() != valueOn)
				myLine += "background-color:#FF8080;";
			myLine += "\" name=CB_" + name + " id=\"" + line + "\" type=checkbox";
			if (value.toUpperCase() == valueOn)
				myLine += " checked";
			myLine += " onClick=\"topToggleCheckBox(document.form.CB_" + name + ", document.form." + name + ", document.form." + name + "UPD, '" + valueOn + "', '" + valueOff + "');\"";
			myLine += " onFocus=\"topOnFocus(this, '" + name + "');\"";			
			myLine += " onKeyup=\"topOnKeyUp(this);\"";
			myLine += ">";
		} else if (type == 'radio') {
			if (valueDefault == "true")
				myLine += n_edit_UPD(name, modified);
			myLine += "<INPUT class=txtin valign=top style=\"" + n_getStyle('edit', highlighting, color, length) + "\" name=" + name + " type=radio value=\"" + valueOn + "\"";
			if (value == valueOn || (valueDefault == "true" && value == ""))
				myLine += " checked";
			myLine += " onClick=\"topOnChange(this);\"";
			myLine += " onFocus=\"topOnFocus(this, '" + name + "');\"";
			myLine += ">";
		} else {
			myLine += n_edit_UPD(name, modified);
			myLine += "<INPUT class=txtin valign=top style=\"" + n_getStyle('edit', highlighting, color, length) + "\" name=" + name + " id=\"" + line + "\"";
			if (intensity == 'dark') {
				myLine += " type=password";
			} else {
				myLine += " type=text";
			}
			myLine += " value=\"" + n_changeQuote(value) + "\" size=" + length + " maxlength=" + length;
			myLine += " onFocus=\"topOnFocus(this, '" + name + "');\"";
			myLine += " onChange=\"topOnChange(this);\"";
			myLine += " onKeydown=\"topOnKeyDown(this);\"";
			myLine += " onKeyup=\"topOnKeyUp(this);\"";
			myLine += ">";
		}
	} else {
		myLine += " style=\"width:" + ((length * nSize) + nSizeAdd) + "px;\">";
		myLine += n_edit_UPD(name, modified);
	}
	myLine += "</TD>";
	document.write(myLine);
}

function n_edit_UPD(name, modified) {
	var myLinePart = '';
	if (modified == 'true') {
		myLinePart = "<INPUT type=hidden name=" + name + "UPD value=1>";
	} else {
		myLinePart = "<INPUT type=hidden name=" + name + "UPD>";
	}
	return myLinePart;
}

function n_button(name, action, logo, display, length) {
	var myLine = '';
	if (length == "")
		myLine = "<TD>";
	else
		myLine = "<TD style=\"width:" + ((length * nSize) + nSizeAdd) + "px;\">";
	if (logo != "") {
		myLine += "<A href=\"javascript:" + action + "\" onMouseOver=\"topOver('" + name + "');\" onMouseOut=\"topOut('" + name + "');\">";
		myLine += "<IMG src=" + logo + " name=\"" + name + "\" alt=\"" + display + "\" border=0/></A>";
	} else {
		myLine += "<INPUT name=\"" + name + "\" type=button onClick=\"" + action + "\" value=\"" + n_changeQuote(display) + "\" style=\"width:" + (length * nSize) + "px;\">";
	}
	myLine += "</TD>";
	document.write(myLine);
}	

function n_select(name) {
	document.write("<SELECT name=\"" + name + "\">");
}

function n_selectOption(value, name, selected) {
}

function n_selectEnd() {
	document.write("</SELECT>");
}

function n_warning(id, value) {
	var myLine = "<TD class=txtdis>";
	myLine += "<A href=\"javascript:topOnWarningDetails('" + id + "');\"><IMG src=images/ico_warning.gif width=12 height=9 border=0 align=absmiddle alt=\"" + value + "\"/></A>";
	myLine += "</TD>";
	document.write(myLine);
}

function n_getStyle(type, highlighting, color, length) {
	var myStyle = '';
	var myBold = '';
	if (nIsBold) myBold = 'font-weight:bold;';
	if (type == 'label' && highlighting == 'reverse') {
										myStyle = 'width:' + ((length * nSize) + nSizeAdd) + 'px;';
		if      (color == 'green') 		myStyle += 'background-color:#00C000;color:black;' + myBold;
		else if (color == 'yellow') 	myStyle += 'background-color:#FFA000;color:black;' + myBold;
		else if (color == 'turquoise') 	myStyle += 'background-color:#C0C0C0;color:black;' + myBold;
		else if (color == 'blue')		myStyle += 'background-color:#4040FF;color:black;' + myBold;
		else if (color == 'red')		myStyle += 'background-color:#FF0000;color:black;' + myBold;
		else if (color == 'pink')		myStyle += 'background-color:#FF00FF;color:black;' + myBold;
		else							myStyle += 'background-color:#C0C0C0;color:black;' + myBold;
	} else if (type == 'edit' && highlighting == 'reverse') {
										myStyle = 'width:' + ((length * nSize) + nSizeAddEdit) + 'px;';
		if      (color == 'green') 		myStyle += 'background-color:#80FF80;color:black;';
		else if (color == 'yellow') 	myStyle += 'background-color:#C0C0C0;color:black;';
		else if (color == 'turquoise') 	myStyle += 'background-color:#8080FF;color:black;';
		else if (color == 'blue')		myStyle += 'background-color:#8080FF;color:black;';
		else if (color == 'red')		myStyle += 'background-color:#FF8080;color:black;';
		else if (color == 'pink')		myStyle += 'background-color:#FF80FF;color:black;';
		else							myStyle += 'background-color:#C0C0C0;color:black;';
	} else if (type == 'link' && highlighting == 'reverse') {
										myStyle = 'color:black;' + myBold;
	} else if (type == 'label' || type == 'link') {
		if 		(type == 'label')		myStyle = 'width:' + ((length * nSize) + nSizeAdd) + 'px;';
		if      (color == 'green') 		myStyle += 'color:black;';
		else if (color == 'neutral') 	myStyle += 'color:#8080FF;' + myBold;
		else if (color == 'turquoise') 	myStyle += 'color:#0000C0;' + myBold;
		else if (color == 'blue')		myStyle += 'color:blue;' + myBold;
		else if (color == 'red')		myStyle += 'color:#FF0000;font-weight:bold;';
		else if (color == 'pink')		myStyle += 'color:#FF00FF;' + myBold;
		else							myStyle += 'color:black;' + myBold;
	} else {
										myStyle = 'width:' + ((length * nSize) + nSizeAddEdit) + 'px;';
		if      (color == 'green') 		myStyle += 'background-color:#80FF80;color:black;';
		else if (color == 'yellow') 	myStyle += 'color:black;';
		else if (color == 'turquoise') 	myStyle += 'background-color:#8080FF;color:black;';
		else if (color == 'blue')		myStyle += 'background-color:#8080FF;color:black;';			
		else if (color == 'red')		myStyle += 'color:black;';
		else if (color == 'pink')		myStyle += 'background-color:#FF00FF;color:black;';
		else							myStyle += 'color:black;';
	}
	return myStyle;
}

function n_getActiveLabel(type, value, classtxt, style, action, activeChoiceTarget, activeChoiceValue, activeChoiceLink, activeChoiceSubmit) {
	var myAction = '';
	if (type == 'activeChoice') {
		myAction = "javascript:topOnActiveChoice(document.form." + activeChoiceTarget + ", '" + activeChoiceValue + "', " + activeChoiceSubmit + ");";
	} else if (type == 'linkedActiveChoice') {
	    myAction = 'javascript:topOnActiveChoice(document.form.' + activeChoiceTarget + ', document.form.' + activeChoiceLink + '.value, ' + activeChoiceSubmit + ');';
	} else {
		myAction = action;
	}
	
	var iLength = value.length;
	var iLeft = 0;
	var iRight = iLength;	
	for (; iLeft < iLength && value.charAt(iLeft) == " "; iLeft++) {}
	for (; iRight > 0 && value.charAt(iRight - 1) == " "; iRight--) {}

	var myLabel = n_changeSpace(value.substring(0, iLeft));
	myLabel += "<A class=" + classtxt;
	if (style != "") {
		myLabel += " style=\"" + style + "\"";
	}
	myLabel += " href=\"" + myAction + "\">" + n_changeSpace(value.substring(iLeft, iRight)) + '</A>';
	myLabel += n_changeSpace(value.substring(iRight, iLength));
	return myLabel;
}

function n_changeSpace(value) {
	var myValue = value.replace(/\s/g, '&nbsp;');
	myValue = myValue.replace('<', '&lt;');
	myValue = myValue.replace('>', '&gt;');
	if (nIsPrintScreen) {
		myValue = myValue.replace('-', '&#8722;');
	}
	return myValue;
}
function n_changeQuote(value) {
	return value.replace(/"/g, '&quot;');
}

function n_isSpace(value) {
	if (value.replace(/\s/g, '') == "")
		return true;
	else
		return false
}