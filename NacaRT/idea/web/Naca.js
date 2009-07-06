var topIntWindow = null;
var topSubmitCount=0;
var topFieldFocus = '';
var topFieldFocusCurrent = null;
var topKeyDown = false;

document.onkeydown = function() {
	topKeyDown = true;
	if (window.event.keyCode == 13 || (window.event.keyCode >= 113 && window.event.keyCode <= 135)) {
		var myKeyCode = window.event.keyCode;
		if (window.event.shiftKey && myKeyCode != 13) {
			myKeyCode = myKeyCode + 12;
		}	
		topEnter(myKeyCode);
		window.event.keyCode = 505;
		return false;
	} else if (window.event.keyCode == 9) {
		if (topFieldFocusCurrent != null) {
			if (window.event.shiftKey) {
				foundField = searchField(topFieldFocusCurrent, true, true);
			} else {
				foundField = searchField(topFieldFocusCurrent, true, false);
			}
			if (foundField == null) {
				if (window.event.shiftKey) {
					foundField = searchLoopField(false);
				} else {
					foundField = searchLoopField(true);
				}
				if (foundField != null && foundField == topFieldFocusCurrent) {
					foundField = null;
				}
			}
			if (foundField != null) {
				try {foundField.focus();foundField.select();} catch (e) {}
			}
		}
		window.event.keyCode = 505;
		return false;
	} else if (window.event.keyCode == 35 && (window.event.ctrlKey || window.event.altKey)) {
		foundField = searchLoopField(false);			
		if (foundField != null) {
			try {foundField.focus();foundField.select();} catch (e) {}
		}
		window.event.keyCode = 505;
		return false;
	} else if (window.event.keyCode == 36 && (window.event.ctrlKey || window.event.altKey)) {
		if (window.event.altKey) alert("Blocking Alt Home!");
		topFocusForm(document.form);
		window.event.keyCode = 505;
		return false;
	}
}

function topNotInService() {
	alert("Application not in service!\nservicedesk@consultas.ch");
}

function topOpen(page, frame, haut, gauche, largeur, hauteur) {
	var sTop = 0;
	var sLeft = 0;
	if (document.all) {
		x = window.screenTop;
		y = window.screenLeft;
	} else {
		x = window.screenY + 100;
		y = window.screenX;
	}
	sTop= x + haut;
	sLeft= y + gauche;
	topOpenSpecial(page, frame, 'toolbar=0,location=1,status=0,menubar=0,scrollbars=1,directories=0,resizable=0,fullscreen=0,width=' + largeur + ',height=' + hauteur + ',left=' + sLeft + ',top=' + sTop);
}
function topOpenSpecial(page, frame, info) {
	topClose();
	topIntWindow = window.open(page, 'PUB2000_' + frame, info);
	topIntWindow.focus();
}
function topOpenNew(page, frame, info) {
	mywindow = window.open(page, 'PUB2000_' + frame, info);
	mywindow.focus();
}
function topClose() {
	if (topIntWindow) {
		if (!topIntWindow.closed) {
			topIntWindow.close()
		}
	}
}

function topCheckForm() {
	if (topSubmitCount == 0) {
		topSubmitCount++;
		return true;
	} else {
		return false;
	}
}

function topPrintScreen() {
	top.document.form.printScreen.value = 'requested' ;
	top.document.form.submit();
}

function topDMS() {
	myWindow = window.open("appLaunch.do?ApplicationId=RS01A11_1A", 'pub2000_DMS', "");
	try
	{
		myWindow.moveTo(0, 0);
		myWindow.resizeTo(screen.availWidth, screen.availHeight);
	}
	catch (e) {}
	myWindow.focus();
}

function topZoom() {
	top.document.form.zoom.value = 'requested' ;
	top.document.form.submit();
}

function topBold() {
	top.document.form.bold.value = 'requested' ;
	top.document.form.submit();
}

function topOver(img) {
	document[img].src = document[img].src.replace('_lo','_hi');
}
function topOut(img) {
	document[img].src = document[img].src.replace('_hi','_lo');
}

function topOnFocus(input, name) {
	topFieldFocus = name;
	try {
		if (topFieldFocusCurrent != null) {
			topFieldFocusCurrent.style.borderColor = '';
		}
		if (input != null) {
			input.style.borderColor = '#003399';
			topFieldFocusCurrent = input;
		}
	} catch (e) {}	
}

function topOnChange(input) {
	try {document.getElementById(input.name + 'UPD').value = '1';} catch (e) {}
}
function topOnKeyDown(input) {
	if (window.event.keyCode == 8 || window.event.keyCode == 32 || (window.event.keyCode >= 46 && window.event.keyCode <= 90) || (window.event.keyCode >= 96 && window.event.keyCode <= 105) || window.event.keyCode >= 186) {
		try {document.getElementById(input.name + 'UPD').value = '1';} catch (e) {}
	}
}
function topOnKeyDownSpecial(input) {
	if (window.event.keyCode == 13 || window.event.keyCode == 8 || window.event.keyCode == 32 || (window.event.keyCode >= 46 && window.event.keyCode <= 90) || (window.event.keyCode >= 96 && window.event.keyCode <= 105) || window.event.keyCode >= 186) {
		try {document.getElementById(input.name + 'UPD').value = '1';} catch (e) {}
	}
	if (window.event.keyCode == 13) {
		window.event.keyCode = 505;
		return false;
	}
}
function topOnActivEdit(id, val)
{
	window.open('popup.do?id='+id+'&val='+val,'popup', 'left=20,top=20,width=500,height=500,toolbar=1,resizable=0');
}
function topOnDevelopMessage(messageId)
{
	topOpen('help.do?sessionId=H&error=' + messageId + '&currentPage='+document.form.idPage.value, 'help', 40, 300, 570, 400);
}
function topOnWarningDetails(warningid)
{
	topOpen('help.do?scenarioWarningId=' + warningid, 'help', 50, 280, 500, 400);
}
function topOnActiveChoice(target, val, submit)
{
	target.value = val ;
	try {document.getElementById(target.name + 'UPD').value = '1';} catch (e) {}
 	if (submit)
	{
		top.document.form.PFKey.value = '0';
		doSubmit();
	}
}
function topToggleCheckBox(input, field, flag, valOn, valOff)
{
	flag.value='1' ;
	if (input.checked == true)
	{
		field.value = valOn ;
	}
	else
	{
		field.value = valOff ;
	}
	foundField = searchField(input, true, false);
	if (foundField != null)
	{
		try {foundField.focus();foundField.select();} catch (e) {}
	}
}

function topOnKeyUp(input) {
	if (!topKeyDown) return;
	topKeyDown = false;
	if (window.event.keyCode == 38 || window.event.keyCode == 40) {
		if (window.event.keyCode == 38) {
			foundField = searchField(input, false, true);
		} else {
			foundField = searchField(input, false, false);
		}
		if (foundField == null) {
			if (window.event.keyCode == 38) {
				foundField = searchLoopField(false);
			} else {
				foundField = searchLoopField(true);
			}
			if (foundField != null && foundField == input) {
				foundField = null;
			}
		}
		if (foundField != null) {
			try {foundField.focus();foundField.select();} catch (e) {}
		}
	} else if (window.event.keyCode == 8 || window.event.keyCode == 32 || (window.event.keyCode >= 46 && window.event.keyCode <= 90) || (window.event.keyCode >= 96 && window.event.keyCode <= 105) || window.event.keyCode >= 186) {
		if (input.value.length >= input.getAttribute('maxlength')) {			
			foundField = searchField(input, true, false);
			if (foundField != null) {
				topFocus(foundField);
			}
		}
	}
}	

function searchField(input, next, up) {
	var i = 0, found = false, id, previousField = null;
	while (i < document.form.length) {
		if (document.form[i].type != 'hidden' && document.form[i].id != '') {
			if (found) {
				if (next || document.form[i].id != id) {
					return document.form[i];
				}
			} else {
				if (document.form[i] == input) {
					if (next && up) {
						return previousField;
					}
					found = true;
					id = document.form[i].id;
					if (!next && up) break;
				}
				previousField = document.form[i];
			}
		}
		i++;
	}
	if (!next && up && found) {
		i = 0;
		var idOld = '', previousField = null;
		while (i < document.form.length) {
			if (document.form[i].type != 'hidden' && document.form[i].id != '') {
				if (parseInt(document.form[i].id) < parseInt(id)) {
					if (document.form[i].id != idOld) {
						previousField = document.form[i];
						idOld = document.form[i].id;
					}
				} else {
					return previousField;
				}
			}
			i++;
		}
	}
	return null;
}
function searchLoopField(first) {
	var i = 0, id = "", field = null;
	while (i < document.form.length) {
		if (document.form[i].type != 'hidden') {
			if (first) {
				return document.form[i];
			} else {
				if (document.form[i].id != id) {
					id = document.form[i].id;
					field = document.form[i];
				}
			}
		}
		i++;
	}
	return field;
}

function topFocus(input) {
	try {
		input.focus();
		input.select();
	} catch (e) {
		try {
			action = 'document.form.CB_'+input.name+".focus()";
			eval(action);
		} catch (e) {
	    	topFocusForm(document.form);
		}
	}
}
function topFocus(input, form) {
	try {
		input.focus();
		input.select();
	} catch (e) {
		try {
			for (var i = 0; i < form.length; i++) {
				if (form[i].name == 'CB_'+input.name) {
					form[i].focus();
					return;
				}
			}
		} catch (e) {
	    	topFocusForm(form);
		}
	}
}
function topFocusCurrent() {
	if (topFieldFocusCurrent != null) {
		try {topFieldFocusCurrent.focus();topFieldFocusCurrent.select();} catch (e) {}
	}
}
function topFocusForm(form) {
	for (var i = 0; i < form.length; i++) {
		if (form[i].type != 'hidden') {
			topFocus(form[i]);
			return;
		}
	}
}
function topSetSelectedTextRange(input, selectionStart, selectionEnd) {
	try {
		if (input.createTextRange) {
			var range = input.createTextRange();
			range.collapse(true);
			range.moveEnd('character', selectionEnd+1);
			range.moveStart('character', selectionStart);
			range.select();
		} else if (input.setSelectionRange) {
			input.focus();
			input.setSelectionRange(selectionStart, selectionEnd+1);
		}
	} catch (e) {}
}