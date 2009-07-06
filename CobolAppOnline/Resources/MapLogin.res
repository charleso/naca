<?xml version="1.0" encoding="iso-8859-1"?>
<form name="MapLogin" title="Login" allLanguages="DE;FR;IT" cursor="userid">
	<pfkeydefine enter="true"/>
	<formbody>
		<vbox>
			<hbox>
				<title length="13">
					<labelfield value="Identification / Anmeldung / Identificazione"/>
				</title>
			</hbox>
			<hbox>
				<blank length="80" text=" "/>
			</hbox>
			<hbox>
				<blank length="80" text=" "/>
			</hbox>
			<hboxSpecial>
				<label length="80" color="turquoise">
					<labelfield value="Veuillez vous identifier avec votre ID et mot de passe de Windows."/>
				</label>
			</hboxSpecial>
			<hboxSpecial>
				<label length="80" color="turquoise">
					<labelfield value="Bitte melden Sie sich mit der ID und dem Passwort von Windows an."/>
				</label>
			</hboxSpecial>
			<hboxSpecial>
				<label length="80" color="turquoise">
					<labelfield value="Vogliate identificarvi con il vostro ID e la password di Windows."/>
				</label>
			</hboxSpecial>
			<hbox>
				<blank length="80" text=" "/>
			</hbox>
			<hbox>
				<blank length="80" text=" "/>
			</hbox>
			<hbox>
				<label length="34" line="2" col="6">
					<labelfield value="Utilisateur / Benutzer / Utente    ="/>
				</label>
				<blank length="2"/>
				<edit length="25" line="1" linkedvalue="userid" name="userid"  protection="unprotected"/>
			</hbox>
			<hbox>
				<label length="34" line="2" col="6">
					<labelfield value="Mot de passe / Passwort / Password ="/>
				</label>
				<blank length="2" text=" "/>
				<edit length="25" line="2" linkedvalue="password" name="password" protection="unprotected" intensity="dark"/>
			</hbox>
			<hbox>
				<blank length="80" text=" "/>
			</hbox>
			<hbox>
				<blank length="80" text=" "/>
			</hbox>
			<hbox>
				<edit color="red" highlighting="off" intensity="bright" length="79" line="24" linkedvalue="errormessage" name="errormessage" protection="autoskip"/>
			</hbox>
			<!--<hbox>
				<applet class="ClientRequestApplet.class" path="applet" width="0" height="0">
					<param name="ServerURL" value="$URL"/>
					<param name="SessionID" value="$SESSIONID"/>
					<param name="LocalURL" value="http://127.0.0.1:11113/RequestGetLU"/>
				</applet>
				<blank length="80" text=" "/>
			</hbox>-->
		</vbox>
	</formbody>
</form>