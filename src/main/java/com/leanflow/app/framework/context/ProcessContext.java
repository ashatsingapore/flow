package com.leanflow.app.framework.context;

import org.xml.sax.Parser;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import java.io.File;

/**
 * Process context class to load the workflow from the configuration
 * Created by ash on 29/5/15.
 */
public class ProcessContext extends DefaultHandler {

    public ProcessContext() {
//        try
//        {
//            Parser parser = new SAXParser();
//
//            // (3) Set Handlers in the parser
//            parser.setDocumentHandler(sample);
//            parser.setEntityResolver(sample);
//            parser.setDTDHandler(sample);
//            parser.setErrorHandler(sample);
//
//            // (4) Convert file to URL and parse
//            try
//            {
//                parser.parse(fileToURL(new File(argv[0])).toString());
//            }
//            catch (SAXParseException e)
//            {
//                System.out.println(e.getMessage());
//            }
//            catch (SAXException e)
//            {
//                System.out.println(e.getMessage());
//            }
//        }
//        catch (Exception e)
//        {
//            System.out.println(e.toString());
//        }
    }
}
