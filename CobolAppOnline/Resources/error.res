<?xml version="1.0" encoding="iso-8859-1"?>
<form name="error" title="Technical Problem">
	<pfkeydefine logout="true"/>
	<formbody>
		<vbox>
			<hbox>
				<blank length="4"/>
				<title brightness="normal" col="8" color="green" highlighting="off" length="30" line="1" name="pageTitle">
					<labelfield value="Problème technique / Technisches Problem / Problema tecnico"/>
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
					<labelfield value="Un problème technique est intervenu."/>
				</label>
			</hboxSpecial>
			<hboxSpecial>
				<label length="80" color="turquoise">
					<labelfield value="Ein technisches Problem ist erfolgt."/>
				</label>
			</hboxSpecial>
			<hboxSpecial>
				<label length="80" color="turquoise">
					<labelfield value="Un problema tecnico ha intervenuto."/>
				</label>
			</hboxSpecial>
			<hbox>
				<blank length="80" text=" "/>
			</hbox>
			<hbox>
				<blank length="80" text=" "/>
			</hbox>
			<hbox>
				<label length="13" color="DEFAULT" highLight="OFF">
					<labelfield value="Program : "/>
				</label>
				<edit length="20" linkedvalue="programName" name="programName" color="GREEN" highLight="OFF" intensity="normal" protection="autoskip"/>
			</hbox>
			<hbox>
				<label length="13" color="DEFAULT" highLight="OFF">
					<labelfield value="Message : "/>
				</label>
				<edit length="20" linkedvalue="errorMessage" name="errorMessage" color="GREEN" highLight="OFF" intensity="normal" protection="autoskip"/>
			</hbox>
		</vbox>
	</formbody>
</form>