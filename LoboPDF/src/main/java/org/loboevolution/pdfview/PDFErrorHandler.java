package org.loboevolution.pdfview;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simple class to handle exceptions - as default we just print the stack trace
 * but it's possible to inject another behaviour
 * @author xond
 *
 */
public class PDFErrorHandler {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(PDFErrorHandler.class.getName());

    public void publishException(Throwable e){
       logger.log(Level.SEVERE, e.getMessage(), e); 
    }
}
