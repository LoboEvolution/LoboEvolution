package org.loboevolution.html.io;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;

import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.parser.HtmlParser;
import org.xml.sax.ErrorHandler;

public class LocalWritableLineReader extends WritableLineReader {
	
	private HTMLDocumentImpl doc;
	
	/**
	 * @param reader
	 */
	public LocalWritableLineReader(HTMLDocumentImpl doc, LineNumberReader reader) {
		super(reader);
		this.doc = doc;
	}

	/**
	 * @param reader
	 */
	public LocalWritableLineReader(Reader reader) {
		super(reader);
	}

	@Override
	public void write(String text) throws IOException {
		super.write(text);
		if ("".equals(text)) {
			openBufferChanged(text);
		}
	}
	
	private void openBufferChanged(String text) {
		// Assumed to execute in a lock
		// Assumed that text is not broken up HTML.
		final ErrorHandler errorHandler = new LocalErrorHandler();
		final HtmlParser parser = new HtmlParser(doc.getUcontext(), doc, errorHandler, true);
		final StringReader strReader = new StringReader(text);
		try {
			// This sets up another Javascript scope WindowImpl. Does it matter?
			parser.parse(strReader);
		} catch (final Exception err) {
			doc.warn("Unable to parse written HTML text. BaseURI=[" + doc.getBaseURI() + "].", err);
		}
	}
}