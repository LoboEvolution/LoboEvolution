package org.loboevolution.pdfview.action;

import java.io.IOException;

import org.loboevolution.pdfview.PDFObject;

/*****************************************************************************
 * URI action, containing a web link
 *
 * @author  Katja Sondermann
 * @since 07.07.2009
 ****************************************************************************/
public class UriAction extends PDFAction {

	/** The URL this action links to */
	private final String uri;
	
	/*************************************************************************
	 * Constructor, reading the URL from the given action object
	 * @param obj
	 * @param root
	 * @throws IOException - in case the action can not be parsed
	 ************************************************************************/
	public UriAction(PDFObject obj, PDFObject root) throws IOException {
		super("URI");
		this.uri = PdfObjectParseUtil.parseStringFromDict("URI", obj, true);
	}
	
	/*************************************************************************
	 * Constructor
	 * @param uri
	 * @throws IOException 
	 ************************************************************************/
	public UriAction(String uri) throws IOException {
		super("URI");
		this.uri = uri;
	}

	/*************************************************************************
	 * Get the URI this action directs to
	 * @return String
	 ************************************************************************/
	public String getUri() {
		return this.uri;
	}
}
