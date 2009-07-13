/*
 * NacaRT - Naca RunTime for Java Transcoded Cobol programs.
 *
 * Copyright (c) 2005, 2006, 2007, 2008 Publicitas SA.
 * Licensed under LGPL (LGPL-LICENSE.txt) license.
 */
/**
 * 
 */
package idea.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jlib.log.Log;

public abstract class BaseCheckServiceServlet extends HttpServlet
{
    public BaseCheckServiceServlet()
    {
    }

    protected abstract boolean getServiceStatus(HttpServletRequest httpservletrequest, StringBuffer stringbuffer);

    protected abstract String getServiceName();

    protected final void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        resp.setContentType("text/xml; charset=iso-8859-1");
        PrintWriter pw = resp.getWriter();
        StringBuffer errCode = new StringBuffer();
        long mtdeb = System.currentTimeMillis();
        boolean serviceStatus = getServiceStatus(req, errCode);
        long tt = System.currentTimeMillis() - mtdeb;
        String xmlRes = "<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>";
        xmlRes = xmlRes + "<?xml-stylesheet type=\"text/xsl\" href=\"/Monitoring/checkservice.xsl\"?>";
        xmlRes = xmlRes + "<checkService";
        xmlRes = xmlRes + " name=\"" + getServiceName() + "\"";
        xmlRes = xmlRes + " status=\"" + (serviceStatus ? "ok" : "nok") + "\"";
        if(errCode.length() > 0)
            xmlRes = xmlRes + " returnCode=\"" + errCode.toString() + "\"";
        xmlRes = xmlRes + " time_ms=\"" + String.valueOf(tt) + "\"";
        xmlRes = xmlRes + "/>";
        pw.print(xmlRes);
        info(getServiceName(), serviceStatus, errCode.toString(), tt);
    }

    protected final void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException
    {
        resp.setContentType("text/xml; charset=iso-8859-1");
        PrintWriter pw = resp.getWriter();
        StringBuffer errCode = new StringBuffer();
        long mtdeb = System.currentTimeMillis();
        boolean serviceStatus = getServiceStatus(req, errCode);
        long tt = System.currentTimeMillis() - mtdeb;
        String xmlRes = "<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>";
        xmlRes = xmlRes + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xdt=\"http://www.w3.org/2004/07/xpath-datatypes\" xmlns:fn=\"http://www.w3.org/2004/07/xpath-functions\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soapenv:Body><checkserviceResponse xmlns=\"http://publigroupe.com/checkservice\"><checkserviceReturn xmlns=\"\">";
        xmlRes = xmlRes + "<name xsi:type=\"xsd:string\">";
        xmlRes = xmlRes + getServiceName();
        xmlRes = xmlRes + "</name>";
        xmlRes = xmlRes + "<status xsi:type=\"xsd:string\">";
        xmlRes = xmlRes + (serviceStatus ? "ok" : "nok");
        xmlRes = xmlRes + "</status>";
        xmlRes = xmlRes + "<returnCode xsi:type=\"xsd:string\">";
        xmlRes = xmlRes + errCode.toString();
        xmlRes = xmlRes + "</returnCode>";
        xmlRes = xmlRes + "<time_ms xsi:type=\"xsd:string\">";
        xmlRes = xmlRes + String.valueOf(tt);
        xmlRes = xmlRes + "</time_ms>";
        xmlRes = xmlRes + "</checkserviceReturn></checkserviceResponse></soapenv:Body></soapenv:Envelope>";
        pw.print(xmlRes);
        info(getServiceName(), serviceStatus, errCode.toString(), tt);
    }

    private void info(String name, boolean status, String code, long tms)
    {
        if(status)
        	Log.logDebug(name + " OK, in " + String.valueOf(tms) + " ms");
        else
        	Log.logImportant(name + " NOK, Return Code : " + code + ", in " + String.valueOf(tms) + " ms");
    }

    private static final String mStatusOk = "ok";
    private static final String mStatusNok = "nok";
    private static final String mCharacterEncoding = "iso-8859-1";
//   
}
