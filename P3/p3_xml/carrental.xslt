<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/carrental">
    <html>
        <head><title>RENTALS</title></head>
        <body>
            <xsl:apply-templates select="rental"/>
        </body>
    </html>
</xsl:template>
<xsl:template match="rental">
    <table border="0">
        MAKE=<xsl:value-of select="make"/><br />
        Modelo=<xsl:value-of select="model"/><br />
        Numero dias=<xsl:value-of select="nofdays"/><br />
        Numero vehiculos=<xsl:value-of select="nofunits"/><br />
        Descuento=<xsl:value-of select="discount"/><br />
 
				
    </table>  
 <br/><hr/><br/>
      
</xsl:template>
</xsl:stylesheet>

