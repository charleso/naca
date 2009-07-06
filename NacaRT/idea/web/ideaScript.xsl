<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="html" encoding="ISO-8859-1"/>

<xsl:template match="page">
<HTML>
<HEAD>
<title>NACA - PUB 2000</title>
	<xsl:if test="/page/@autoRefresh!=''">
		<xsl:element name="meta">
			<xsl:attribute name="http-equiv">Refresh</xsl:attribute>
			<xsl:attribute name="content"><xsl:value-of select="/page/@autoRefresh"/></xsl:attribute>
		</xsl:element>
	</xsl:if>
	<xsl:choose>
		<xsl:when test="//form/@zoom='true'">
			<xsl:choose>
				<xsl:when test="//form/@printScreen='true'">
<link rel="StyleSheet" href="style.css" type="text/css"/>
				</xsl:when>
				<xsl:otherwise>
<link rel="StyleSheet" href="style_zoom.css" type="text/css"/>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:when>
		<xsl:otherwise>
<link rel="StyleSheet" href="style.css" type="text/css"/>
		</xsl:otherwise>
	</xsl:choose>
<script language="JavaScript" src="Naca.js"/>
<script language="JavaScript" src="NacaCustom.js"/>
<script language="JavaScript" src="NacaScript.js"/>
</HEAD>
	<xsl:element name="body">
	<xsl:choose>
		<xsl:when test="//form/@printScreen='true'">
			<xsl:attribute name="marginwidth">10</xsl:attribute>
			<xsl:attribute name="marginheight">10</xsl:attribute>
			<xsl:attribute name="leftmargin">10</xsl:attribute>
			<xsl:attribute name="topmargin">10</xsl:attribute>
		</xsl:when>
		<xsl:otherwise>
			<xsl:attribute name="marginwidth">0</xsl:attribute>
			<xsl:attribute name="marginheight">0</xsl:attribute>
			<xsl:attribute name="leftmargin">0</xsl:attribute>
			<xsl:attribute name="topmargin">0</xsl:attribute>
			<xsl:attribute name="background">images/bg_content.gif</xsl:attribute>
			<xsl:attribute name="onUnload">topClose();</xsl:attribute>
			<xsl:attribute name="onFocus">topOnFocus(null, '');</xsl:attribute>
			<xsl:attribute name="onHelp">topEnter('pf1');return false;</xsl:attribute>
		</xsl:otherwise>
	</xsl:choose>
	
	<xsl:variable name="quoteUserName">
		<xsl:call-template name="replace-special">
				<xsl:with-param name="text" select="/page/@userName"/>
			</xsl:call-template>
	</xsl:variable>
	
<script language="JavaScript">
n_update("<xsl:value-of select="/page/@updateTime"/>");
n_user("<xsl:value-of select="/page/@profitCenter"/>","<xsl:value-of select="/page/@serverName"/>","<xsl:value-of select="/page/@terminalName"/>","<xsl:value-of select="$quoteUserName"/>");
n_form("<xsl:value-of select="/page/@URL"/>","<xsl:value-of select="//form/@name"/>","<xsl:value-of select="//form/@cmpSession"/>","<xsl:value-of select="//form/@cursor"/>","<xsl:value-of select="//form/@defaultCursor"/>","<xsl:value-of select="/page/@lang"/>",<xsl:value-of select="//form/@zoom"/>,<xsl:value-of select="//form/@bold"/>,"<xsl:value-of select="//form/@printScreen"/>","<xsl:value-of select="//form/@customOnload"/>","<xsl:value-of select="//form/@customSubmit"/>");
<xsl:for-each select="*"><xsl:apply-templates select="."/></xsl:for-each>
</script>
	</xsl:element>
</HTML>
</xsl:template>

<xsl:template match="pfkeylist">
	<xsl:for-each select="pfkey">
		<xsl:choose>
			<xsl:when test="@ignore='false'">n_addPf("<xsl:value-of select="@jsKey"/>","<xsl:value-of select="@name"/>","<xsl:value-of select="@jsValue"/>","<xsl:value-of select="@action"/>");</xsl:when>
		</xsl:choose>
	</xsl:for-each>
</xsl:template>

<xsl:template match="form">
n_start();<xsl:apply-templates select="formbody"/>n_stop();
</xsl:template>

<xsl:template match="formbody">
	<xsl:apply-templates select="vbox"/>
</xsl:template>

<xsl:template match="vbox">
	<xsl:for-each select="*"><xsl:apply-templates select="."/></xsl:for-each>
</xsl:template>

<xsl:template match="hbox">
n_line();<xsl:for-each select="*"><xsl:apply-templates select="."/></xsl:for-each>
</xsl:template>

<xsl:template match="hboxSpecial">
n_lineSpecial();<xsl:for-each select="*"><xsl:apply-templates select="."/></xsl:for-each>
</xsl:template>

<xsl:template match="image">
n_image("<xsl:value-of select="@ref"/>","<xsl:value-of select="@name"/>","<xsl:value-of select="@width"/>","<xsl:value-of select="@height"/>");
</xsl:template>

<xsl:template match="line">
n_underline("<xsl:value-of select="@start"/>","<xsl:value-of select="@length"/>");
</xsl:template>

<xsl:template match="height">
n_height("<xsl:value-of select="@length"/>");
</xsl:template>

<xsl:template match="blank">
n_blank("<xsl:value-of select="@length"/>");
</xsl:template>

<xsl:template match="title">
<xsl:variable name="defaultText">
	<xsl:call-template name="default">
		<xsl:with-param name="text1" select="texts/text[@lang=/page/@lang]"/>
		<xsl:with-param name="text2" select="labelfield/@value"/>
		<xsl:with-param name="text3" select="@value"/>
	</xsl:call-template>
</xsl:variable>
n_title("<xsl:value-of select="$defaultText"/>");
</xsl:template>

<xsl:template match="label">
<xsl:variable name="defaultText">
	<xsl:call-template name="default">
		<xsl:with-param name="text1" select="texts/text[@lang=/page/@lang]"/>
		<xsl:with-param name="text2" select="labelfield/@value"/>
	</xsl:call-template>
</xsl:variable>
n_label("<xsl:value-of select="@type"/>","<xsl:value-of select="$defaultText"/>","<xsl:value-of select="@action"/>","<xsl:value-of select="@activeChoiceTarget"/>","<xsl:value-of select="@activeChoiceValue"/>","<xsl:value-of select="@activeChoiceLink"/>","<xsl:value-of select="@activeChoiceSubmit"/>","<xsl:value-of select="@highlighting"/>","<xsl:value-of select="@brightness"/>","<xsl:value-of select="@color"/>","<xsl:value-of select="@length"/>");
</xsl:template>

<xsl:template match="edit">
<xsl:variable name="quoteValue">
	<xsl:call-template name="replace-special">
		<xsl:with-param name="text" select="@value"/>
	</xsl:call-template>
</xsl:variable>
n_edit("<xsl:value-of select="@type"/>","<xsl:value-of select="@name"/>","<xsl:value-of select="$quoteValue"/>","<xsl:value-of select="@valueOn"/>","<xsl:value-of select="@valueOff"/>","<xsl:value-of select="@default"/>","<xsl:value-of select="@modified"/>","<xsl:value-of select="@messageId"/>","<xsl:value-of select="@activeChoiceTarget"/>","<xsl:value-of select="@activeChoiceValue"/>","<xsl:value-of select="@activeChoiceLink"/>","<xsl:value-of select="@activeChoiceSubmit"/>","<xsl:value-of select="@protection"/>","<xsl:value-of select="@highlighting"/>","<xsl:value-of select="@intensity"/>","<xsl:value-of select="@color"/>","<xsl:value-of select="@line"/>","<xsl:value-of select="@length"/>");
</xsl:template>

<xsl:template match="button">
<xsl:variable name="defaultLogo">
	<xsl:call-template name="default">
		<xsl:with-param name="text1" select="@logo"/>
		<xsl:with-param name="text2" select="logo[@lang=/page/@lang]/@path"/>
		<xsl:with-param name="text3" select="logo['FR']/@path"/>
	</xsl:call-template>
</xsl:variable>
<xsl:variable name="defaultDisplay">
	<xsl:call-template name="default">
		<xsl:with-param name="text1" select="@display"/>
		<xsl:with-param name="text2" select="logo[@lang=/page/@lang]/@display"/>
	</xsl:call-template>
</xsl:variable>
n_button("<xsl:value-of select="@name"/>","<xsl:value-of select="@action"/>","<xsl:value-of select="$defaultLogo"/>","<xsl:value-of select="$defaultDisplay"/>","<xsl:value-of select="@length"/>");
</xsl:template>

<xsl:template match="combo">
n_select("<xsl:value-of select="@name"/>");<xsl:for-each select="item">n_selectOption("<xsl:value-of select="@value"/>","<xsl:value-of select="@name"/>","<xsl:value-of select="@selected"/>");</xsl:for-each>n_selectEnd();
</xsl:template>

<xsl:template match="warning">
<xsl:variable name="quoteWarning">
	<xsl:call-template name="replace-special">
		<xsl:with-param name="text" select="@value"/>
	</xsl:call-template>
</xsl:variable>
n_warning("<xsl:value-of select="@id"/>","<xsl:value-of select="$quoteWarning"/>");
</xsl:template>

<xsl:template name="default">
   	<xsl:param name="text1"/>
   	<xsl:param name="text2"/>
   	<xsl:param name="text3"/>
   	<xsl:choose>
		<xsl:when test="$text1!=''">
			<xsl:call-template name="replace-special">
				<xsl:with-param name="text" select="$text1"/>
			</xsl:call-template>
		</xsl:when>
		<xsl:when test="$text2!=''">
			<xsl:call-template name="replace-special">
				<xsl:with-param name="text" select="$text2"/>
			</xsl:call-template>
		</xsl:when>
		<xsl:otherwise>
			<xsl:call-template name="replace-special">
				<xsl:with-param name="text" select="$text3"/>
			</xsl:call-template>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template name="replace-special">
	<xsl:param name="text"/>
	<xsl:if test="string-length($text)>0">
		<xsl:variable name="trimText">
			<xsl:call-template name="right-trim">
				<xsl:with-param name="text" select="$text"/>
			</xsl:call-template>
		</xsl:variable>
		<xsl:call-template name="replace-quote">
			<xsl:with-param name="text" select="$trimText"/>
		</xsl:call-template>
	</xsl:if>	
</xsl:template>

<xsl:template name="right-trim">
	<xsl:param name="text"/>
	<xsl:choose>
		<xsl:when test="substring($text, string-length($text))=' '">
			<xsl:call-template name="right-trim">
				<xsl:with-param name="text" select="substring($text, 1, string-length($text) - 1)"/>
			</xsl:call-template>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="$text"/>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template name="replace-quote">
	<xsl:param name="text"/>
	<xsl:variable name="from">"</xsl:variable>
	<xsl:variable name="to">\"</xsl:variable>
	<xsl:choose>
		<xsl:when test="contains($text, $from)">
			<xsl:value-of select="substring-before($text, $from)"/>
			<xsl:text disable-output-escaping="yes"><xsl:value-of select="$to"/></xsl:text>
			<xsl:call-template name="replace-quote">
				<xsl:with-param name="text" select="substring-after($text, $from)"/>
			</xsl:call-template>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="$text"/>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

</xsl:stylesheet>