<?xml version="1.0" encoding="ISO-8859-1"?>
<form allLanguages="FR" name="onlinm1" title="onlinm1">
<pfkeydefine enter="true" pf2="true" pf7="true"/>
<pfkeyaction/>
<formbody>
<vbox>
<hbox>
<edit col="1" color="green" fill="blank" highlighting="off" intensity="normal" justify="left" length="6" line="1" linkedvalue="nmmasq" name="nmmasq" protection="autoskip">
<texts>
<text lang="FR">INIT01</text>
</texts>
</edit>
<title brightness="normal" col="8" color="green" highlighting="off" length="30" line="1" name="pageTitle">
<texts>
<text lang="FR">DEMO ONLINE</text>
</texts>
</title>
<edit col="39" color="turquoise" fill="blank" highlighting="off" intensity="normal" justify="left" length="8" line="1" linkedvalue="execdate" name="execdate" namecopy="dtexec" protection="autoskip" replayMutable="true"/>
<edit col="48" color="turquoise" fill="blank" highlighting="off" intensity="normal" justify="left" length="8" line="1" linkedvalue="exechour" name="exechour" namecopy="hrexec" protection="autoskip" replayMutable="true"/>
</hbox>
<hbox/>
<hbox/>
<hbox/>
<hbox/>
<hbox/>
<hbox/>
<hbox/>
<hbox/>
<hbox>
<label brightness="normal" col="1" color="green" highlighting="off" length="32" line="10">
<texts>
<text lang="FR">REFERENCE / REFERENZ / REFERENZA</text>
</texts>
</label>
<blank length="2"/>
<label brightness="normal" col="37" color="green" highlighting="off" length="1" line="10">
<texts>
<text lang="FR">=</text>
</texts>
</label>
<edit col="39" color="yellow" fill="blank" highlighting="underline" intensity="normal" justify="left" length="3" line="10" linkedvalue="recoll" name="recoll" protection="unprotected"/>
</hbox>
<hbox/>
<hbox>
<label brightness="normal" col="1" color="green" highlighting="off" length="8" line="12">
<texts>
<text lang="FR">PASSWORD</text>
</texts>
</label>
<label brightness="normal" col="10" color="green" highlighting="off" length="28" line="12">
<texts>
<text lang="FR">                           =</text>
</texts>
</label>
<edit col="39" color="default" fill="blank" highlighting="off" intensity="dark" justify="left" length="8" line="12" linkedvalue="passcol" name="passcol" protection="unprotected"/>
</hbox>
<hbox/>
<hbox>
<label brightness="normal" col="1" color="green" highlighting="off" length="12" line="14">
<texts>
<text lang="FR">NEW PASSWORD</text>
</texts>
</label>
<label brightness="normal" col="14" color="green" highlighting="off" length="24" line="14">
<texts>
<text lang="FR">                       =</text>
</texts>
</label>
<edit col="39" color="default" fill="blank" highlighting="off" intensity="dark" justify="left" length="8" line="14" linkedvalue="newpass" name="newpass" protection="unprotected"/>
</hbox>
<hbox/>
<hbox/>
<hbox/>
<hbox/>
<hbox/>
<hbox/>
<hbox/>
<hbox/>
<hbox/>
<hbox>
<edit col="1" color="red" fill="blank" highlighting="off" intensity="bright" justify="left" length="79" line="24" linkedvalue="errormessage" name="errormessage" namecopy="lierr" protection="autoskip" replayMutable="true"/>
</hbox>
</vbox>
</formbody>
</form>
