function onSubmitRS01A11()
{
	selection = top.document.form.mapchoi.value.toUpperCase();
	if (selection=="99")
	{
		topEnter('logout');
	}
	else if (selection=="22" ||
		selection=="23" ||
		selection=="24")
	{
		myWindow = window.open("appLaunch.do?ApplicationId=RS01A11_"+selection, 'pub2000_ERAR', "");
		try
		{
			myWindow.moveTo(0, 0);
			myWindow.resizeTo(screen.availWidth, screen.availHeight);
		}
		catch (e) {}
		top.document.form.mapchoi.value="";
		myWindow.focus();
		return false ;
	}
	else if (selection=="7J")
	{
		myWindow = window.open("appLaunch.do?ApplicationId=RS01A11_"+selection, 'pub2000_SC', "");
		try
		{
			myWindow.moveTo(0, 0);
			myWindow.resizeTo(screen.availWidth, screen.availHeight);
		}
		catch (e) {}
		top.document.form.mapchoi.value="";
		myWindow.focus();
		return false ;
	}
	else if (selection=="7K")
	{
		myWindow = window.open('appLaunch.do?ApplicationId=CROSSMEDIA', 'pub2000_CM', "");
		try
		{
			myWindow.moveTo(0, 0);
			myWindow.resizeTo(screen.availWidth, screen.availHeight);
		}
		catch (e) {}
		top.document.form.mapchoi.value="";
		myWindow.focus();
		return false ;
	}
	else if (selection=="35")
	{
		myWindow = window.open('appLaunch.do?ApplicationId=WATCHLIST', 'pub2000_WL', "");
		try
		{
			myWindow.moveTo(0, 0);
			myWindow.resizeTo(screen.availWidth, screen.availHeight);
		}
		catch (e) {}
		top.document.form.mapchoi.value="";
		myWindow.focus();
		return false ;
	}
	else
	{
		return true ;
	}
}

function onLoadRS7AA2B()
{
	try
	{
		selection = top.document.form.m7aoffd.value.toUpperCase();
	}
	catch (e)
	{
		selection = '';
	}
	if (selection=="C")
	{
		myWindow = window.open('appLaunch.do?ApplicationId=CROSSMEDIA', 'pub2000_CM', "");
		try
		{
			myWindow.moveTo(0, 0);
			myWindow.resizeTo(screen.availWidth, screen.availHeight);
		}
		catch (e) {}
		myWindow.focus();
		return false ;
	}
}

function onLoadRS7AA3H()
{
	var intParent = null;
	intParent = window.opener;
	if (intParent)
	{
		try
		{
			intParent.location.href = "appLaunch.do?ApplicationId=CMP";
			intParent.focus();
		}
		catch (e) {}
	}
}

function onSubmitRS7FA02()
{
	selection = "";
	try
	{
    	selection = top.document.form.m7fretf.value;
    }
    catch (e) {}

	if (selection=="X")
	{
		myWindow = window.open('appLaunch.do?ApplicationId=RS7FA02', 'pub2000_DMS', '');
		try {
			myWindow.moveTo(0, 0);
			myWindow.resizeTo(screen.availWidth, screen.availHeight);
		} catch (e) {}
		top.document.form.m7fretf.value="";
		top.document.form.CB_m7fretf.checked=false;
		top.document.form.m7fretfUPD.value="1";
		myWindow.focus();
		return false ;
	}
	else
	{
		return true ;
	}
}

function onSubmitRS7FA04()
{
	choix = "";
	choixNumeric = 0;
	selection = "";
	commande = new Array("", "", "", "", "", "", "", "", "", "", "", "", "", "", "");
	commandeSel = "";
	try
	{
		choix = top.document.form.mapchoi.value;
    	selection = top.document.form.m7fretf.value;
    	commande[1] = top.document.form.f437.value;
    	commande[2] = top.document.form.f562.value;
    	commande[3] = top.document.form.f571.value;
    	commande[4] = top.document.form.f580.value;
    	commande[5] = top.document.form.f589.value;
    	commande[6] = top.document.form.f598.value;
    	commande[7] = top.document.form.f607.value;
    	commande[8] = top.document.form.f616.value;
    	commande[9] = top.document.form.f625.value;
    	commande[10] = top.document.form.f634.value;
    	commande[11] = top.document.form.f643.value;
    	commande[12] = top.document.form.f652.value;
    	commande[13] = top.document.form.f661.value;
    	commande[14] = top.document.form.f670.value;
    }
    catch (e) {}
    try
    {
    	choixNumeric = parseInt(choix);
    } 
    catch (e) {}
    if (selection=="X" && choixNumeric>0 && choixNumeric<15)
    {
    	commandeSel = commande[choixNumeric];
    }
	if (commandeSel!="")
	{
		myWindow = window.open('appLaunch.do?ApplicationId=RS7FA04&cdenum='+commandeSel, 'pub2000_DMS', '');
		try
		{
			myWindow.moveTo(0, 0);
			myWindow.resizeTo(screen.availWidth, screen.availHeight);
		}
		catch (e) {}
		top.document.form.m7fretf.value="";
		top.document.form.CB_m7fretf.checked=false;
		top.document.form.m7fretfUPD.value="1";
		myWindow.focus();
		return false ;
	}
	else
	{
		return true ;
	}
}