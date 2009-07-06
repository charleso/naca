<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">	
	<xsl:template match="helpPage">
<html> 
	<head>
		<title>NACA - PUB 2000 Help</title>
		<meta HTTP-EQUIV="Pragma" CONTENT="no-cache"/>
		<link rel="StyleSheet" href="style.css" type="text/css"/>
		<script language="JavaScript" src="Naca.js"/>
		<script language="Javascript">
			function topEnter(key) 
			{
				if (key == 123) 
				{
					<xsl:if test="/helpPage/@field=''">
						var theopener = top.window.opener;
						if (theopener) 
						{
							topFocusForm(theopener.document.form);
						}
					</xsl:if>
					<xsl:if test="/helpPage/@field!=''">
						var theopener = top.window.opener;
						if (theopener) 
						{
							topFocus(theopener.document.form.<xsl:value-of select="/helpPage/@field"/>, theopener.document.form);
						}
					</xsl:if>
			    	window.close();
				}
			}
			
			document.onkeydown = function() {
				if (window.event.keyCode == 123) {
					topEnter(window.event.keyCode);
					return false;
				}
			}
			
			<xsl:if test="/helpPage/@field!=''">
				function setCode(selection) 
				{
					var theopener = top.window.opener;
					if (theopener) 
					{
						theopener.document.form.<xsl:value-of select="/helpPage/@field"/>.value = selection;
						theopener.document.form.<xsl:value-of select="/helpPage/@field"/>UPD.value = '1';
						topFocus(theopener.document.form.<xsl:value-of select="/helpPage/@field"/>, theopener.document.form);
						window.close();
					}
				}
			</xsl:if>
			
			function setPage(selection) 
			{
				var theopener = top.window.opener;
				if (theopener) 
				{
					theopener.document.location="naca.do?page="+selection;
					window.close();
				}
			}
		</script>
	</head>
	<body bgcolor="#E7E7EF" marginwidth="10" marginheight="10" leftmargin="10" topmargin="10">		
		<table border="0" cellspacing="0" cellpadding="0" width="100%">
			<tr>
				<td>
					<table border="0" cellspacing="0" cellpadding="0" width="100%">
						<xsl:for-each select="*">
							<xsl:apply-templates select="."/>
						</xsl:for-each>
					</table>
				</td>
			</tr>
		</table>
		</body>
	</html>
	</xsl:template>

	<!-- title -->
	<xsl:template match="title">
		<tr><td><table border="0" cellspacing="0" cellpadding="0" width="100%">
			<tr>
				<td class="helptit" width="80%"><xsl:value-of select="@text"/></td>
				<td width="5%">
					<a href="javascript:self.print();">
						<img src="images/ico_printscreen.gif" name="printscreen" border="0" alt="Print Screen"/>
					</a>
				</td>
				<td width="5%"></td>
				<td align="right">
					<a href="javascript:topEnter(123);" onMouseOver="topOver('butF12')" onMouseOut="topOut('butF12')">
						<img src="images/ico_exit_lo.gif" name="butF12" width="60" height="21" border="0" alt="F12"/>
					</a>
				</td>
			</tr>
			<tr>
				<td height="5"></td>
			</tr>
		</table></td></tr>
	</xsl:template>

	<!-- sub-title -->
	<xsl:template match="sub-title">
		<tr><td class="helpsubtit"><xsl:value-of select="@text"/></td></tr>
	</xsl:template>

	<!-- codeListLink -->
	<xsl:template match="codeListLink">
		<tr><td align="right">
			<xsl:element name="a">
				<xsl:attribute name="class">helplink</xsl:attribute>
				<xsl:attribute name="href">help.do?field=<xsl:value-of select="@field"/>&amp;currentPage=<xsl:value-of select="/helpPage/@currentPage"/></xsl:attribute>
				<xsl:value-of select="@text"/>
			</xsl:element>
		</td></tr>
	</xsl:template>

	<!-- codeInfoLink -->
	<xsl:template match="codeInfoLink">
		<tr><td align="right">
			<xsl:element name="a">
				<xsl:attribute name="class">helplink</xsl:attribute>
				<xsl:attribute name="href">help.do?field=<xsl:value-of select="@field"/>&amp;currentPage=<xsl:value-of select="/helpPage/@currentPage"/>&amp;codeinfo=Y</xsl:attribute>
				<xsl:value-of select="@text"/>
			</xsl:element>
		</td></tr>
	</xsl:template>
	
	<!-- line -->
	<xsl:template match="line">
		<tr><td height="10"></td></tr>
	</xsl:template>
	
	<!-- TitledLabel -->
	<xsl:template match="titledlabel">
		<tr><td><table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="helptxt" width="60">
					<xsl:value-of select="@title"/><xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
				</td>
				<td width="10"></td>
				<td class="helptxtbold">
					<xsl:value-of select="@text"/><xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
				</td>
			</tr>
		</table></td></tr>
	</xsl:template>
	
	<!-- label -->
	<xsl:template match="label">
		<tr><td class="helplabel">
			<xsl:call-template name="changeBlanks">
				<xsl:with-param name="text" select="@text"/>
			</xsl:call-template>
		</td></tr>
	</xsl:template>
	
	<!-- label -->
	<xsl:template match="labeluser">
		<tr><td class="helplabel"><i>
			<xsl:call-template name="changeBlanks">
				<xsl:with-param name="text" select="@text"/>
			</xsl:call-template>
		</i></td></tr>
	</xsl:template>

	<!-- table -->
	<xsl:template match="table">
		<tr><td><table border="0" cellspacing="0" cellpadding="0" width="100%">
			<xsl:for-each select="*">
				<xsl:apply-templates select="."/>
			</xsl:for-each>
		</table></td></tr>
	</xsl:template>
	
	<!-- tableItemCode -->
	<xsl:template match="tableItemCode">
		<tr>
			<td class="helptxtbold"><xsl:value-of select="@title"/></td>
			<td width="10"></td>
			<td width="450">
				<xsl:element name="a">
					<xsl:attribute name="class">helplink</xsl:attribute>
					<xsl:attribute name="href">javascript:setCode('<xsl:value-of select="@parameter"/>')</xsl:attribute>
					<xsl:value-of select="@caption"/>
				</xsl:element>
			</td>
			<td width="20" align="right">	
				<xsl:if test="@info!=''">
					<xsl:element name="a">
						<xsl:attribute name="class">helplink</xsl:attribute>
						<xsl:attribute name="href">help.do?field=<xsl:value-of select="/helpPage/@field"/>&amp;currentPage=<xsl:value-of select="/helpPage/@currentPage"/>&amp;info=yes</xsl:attribute>
						<xsl:value-of select="@info"/>
					</xsl:element>
				</xsl:if>
			</td>
		</tr>
		<xsl:if test="@complement!=''">
			<tr>
				<td colspan="2"></td>
				<td colspan="2" class="helptxt"><i><xsl:value-of select="@complement"/></i></td>
			</tr>
		</xsl:if>
		<xsl:for-each select="*">
			<xsl:apply-templates select="."/>
		</xsl:for-each>
	</xsl:template>
	
	<!-- tableItemCodeInfo -->
	<xsl:template match="tableItemCodeInfo">
		<tr>
			<td colspan="2"></td>
			<td colspan="2" class="helptxt"><i><xsl:value-of select="@info"/></i></td>
		</tr>
	</xsl:template>
	
	<!-- tableItemPage -->
	<xsl:template match="tableItemPage">
		<tr>
			<td class="helptxtbold"><xsl:value-of select="@title"/></td>
			<td width="10"></td>
			<td>
				<xsl:element name="a">
					<xsl:attribute name="class">helplink</xsl:attribute>
					<xsl:attribute name="href">javascript:setPage('<xsl:value-of select="@parameter"/>')</xsl:attribute>
					<xsl:value-of select="@caption"/>
				</xsl:element>
			</td>
		</tr>
	</xsl:template>
	
	<xsl:template name="changeBlanks">
		<xsl:param name="text"/>
		<xsl:if test="contains($text, ' ')">
			<xsl:value-of select="substring-before($text, ' ')"/>
			<xsl:text disable-output-escaping="yes">&amp;nbsp;</xsl:text>
			<xsl:call-template name="changeBlanks">
				<xsl:with-param name="text" select="substring-after($text, ' ')"/>
			</xsl:call-template>
		</xsl:if>
		<xsl:if test="not(contains($text, ' '))">
			<xsl:value-of select="$text"/>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>