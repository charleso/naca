<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">

<xsl:template match="ItemCount">
<HTML>
<HEAD>
</HEAD>
<BODY bgcolor="#EFEFEF">
	<H1>ItemCount</H1>
		<xsl:apply-templates select="Category"/>
	<H1>Listings</H1>
		<xsl:apply-templates select="CopyForPrograms"/>
		<xsl:apply-templates select="ProgramUsingCopy"/>
		<xsl:apply-templates select="MissingCopy"/>
		<xsl:apply-templates select="SubProgramCalls"/>
		<xsl:apply-templates select="ProgramCalled"/>
		<xsl:apply-templates select="MissingCalls"/>
		<xsl:apply-templates select="ProgramsToRewrite"/>
</BODY>
</HTML>
</xsl:template>
		
		
<!-- ProgramCalled -->
<xsl:template match="ProgramCalled">
	<H2>Sub-Programs Called by programs</H2>
	<TABLE border="1">
		<TR>
			<TD>SUB-PROGRAM</TD>
			<TD>PROGRAM</TD>
		</TR>
	<xsl:for-each select="SubProgram">
		<xsl:apply-templates select="."/>
	</xsl:for-each>
	</TABLE>
</xsl:template>

<!-- Rewrite Program -->
<xsl:template match="ProgramsToRewrite">
	<H2>Programs to be rewritten</H2>
	<TABLE border="1">
		<TR>
			<TD>PROGRAM</TD>
			<TD>LINE</TD>
			<TD>REASON</TD>
		</TR>
	<xsl:for-each select="Program">
		<TR>
			<TD><xsl:value-of select="@Name"/></TD>
			<TD><xsl:value-of select="@Line"/></TD>
			<TD><xsl:value-of select="@Reason"/></TD>
		</TR>
	</xsl:for-each>
	</TABLE>
</xsl:template>

<!-- MissingCalls -->
<xsl:template match="MissingCalls">
	<H2>Missing Sub-Programs</H2>
	<TABLE border="1">
		<TR>
			<TD>SUB-PROGRAM</TD>
			<TD>PROGRAM</TD>
		</TR>
	<xsl:for-each select="SubProgram">
		<xsl:apply-templates select="."/>
	</xsl:for-each>
	</TABLE>
</xsl:template>

<!-- SubProgramCalls -->
<xsl:template match="SubProgramCalls">
	<H2>PROGRAM that calls SUB-PROGRAMS</H2>
	<TABLE border="1">
		<TR>
			<TD>PROGRAM</TD>
			<TD>SUB-PROGRAM</TD>
		</TR>
	<xsl:for-each select="Program">
		<xsl:apply-templates select="."/>
	</xsl:for-each>
	</TABLE>
</xsl:template>

<!-- MissingCopy -->
<xsl:template match="MissingCopy">
	<H2>missing COPY files</H2>
	<TABLE border="1">
		<TR>
			<TD>COPY</TD>
			<TD>PROGRAM</TD>
		</TR>
	<xsl:for-each select="Copy">
		<xsl:apply-templates select="."/>
	</xsl:for-each>
	</TABLE>
</xsl:template>

<!-- CopyForPrograms -->
<xsl:template match="CopyForPrograms">
	<H2>COPY files used by PROGRAMS</H2>
	<TABLE border="1">
		<TR>
			<TD>COPY</TD>
			<TD>PROGRAM</TD>
		</TR>
	<xsl:for-each select="Copy">
		<xsl:apply-templates select="."/>
	</xsl:for-each>
	</TABLE>
</xsl:template>

<!-- ProgramUsingCopy -->
<xsl:template match="ProgramUsingCopy">
	<H2>PROGRAMS using COPY files</H2>
	<TABLE border="1">
		<TR>
			<TD>PROGRAM</TD>
			<TD>COPY</TD>
		</TR>
	<xsl:for-each select="Program">
		<xsl:apply-templates select="."/>
	</xsl:for-each>
	</TABLE>
</xsl:template>


<!-- Category -->
<xsl:template match="Category">
	<H2><xsl:value-of select="@Name"/></H2>
	<TABLE border="1">
		<TR>
			<TD>Item</TD>
			<TD>SubItem</TD>
			<TD>Count</TD>
			<TD>Min</TD>
			<TD>Max</TD>
			<TD>Total</TD>
		</TR>
	<xsl:for-each select="Item">
		<xsl:apply-templates select="."/>
	</xsl:for-each>
	</TABLE>
</xsl:template>


<!-- COPY -->
<xsl:template match="Copy">
	<TR>
		<TD><xsl:value-of select="@Name"/></TD>
		<TD> - </TD>
	</TR>
	<xsl:for-each select="Program">
	<TR>
		<TD> - </TD>
		<TD><xsl:value-of select="@Name"/></TD>
	</TR>
	</xsl:for-each>
</xsl:template>

<!-- PROGRAM -->
<xsl:template match="Program">
	<TR>
		<TD><xsl:value-of select="@Name"/></TD>
		<TD> - </TD>
	</TR>
	<xsl:for-each select="Copy">
	<TR>
		<TD> - </TD>
		<TD><xsl:value-of select="@Name"/></TD>
	</TR>
	</xsl:for-each>
	<xsl:for-each select="SubProgram">
	<TR>
		<TD> - </TD>
		<TD><xsl:value-of select="@Name"/></TD>
	</TR>
	</xsl:for-each>
</xsl:template>

<!-- SubPROGRAM -->
<xsl:template match="SubProgram">
	<TR>
		<TD><xsl:value-of select="@Name"/></TD>
		<TD> - </TD>
	</TR>
	<xsl:for-each select="Program">
	<TR>
		<TD> - </TD>
		<TD><xsl:value-of select="@Name"/></TD>
	</TR>
	</xsl:for-each>
</xsl:template>


<!-- ITEM -->
<xsl:template match="Item">
	<TR>
		<TD><xsl:value-of select="@Name"/></TD>
		<TD> - </TD>
		<TD><xsl:value-of select="@Count"/></TD>
		<TD><xsl:value-of select="@Min"/></TD>
		<TD><xsl:value-of select="@Max"/></TD>
		<TD><xsl:value-of select="@Total"/></TD>
	</TR>
	<xsl:for-each select="SubItem">
		<xsl:apply-templates select="."/>
	</xsl:for-each>
</xsl:template>

<!-- SUB-ITEM -->
<xsl:template match="SubItem">
	<TR>
		<TD> - </TD>
		<TD><xsl:value-of select="@Name"/></TD>
		<TD><xsl:value-of select="@Count"/></TD>
		<TD></TD>
		<TD></TD>
		<TD></TD>
	</TR>
</xsl:template>

</xsl:stylesheet>