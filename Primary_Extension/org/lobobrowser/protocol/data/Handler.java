package org.lobobrowser.protocol.data;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLStreamHandler;

/**
 * The Class Handler.
 */
public class Handler extends URLStreamHandler {

	/* (non-Javadoc)
	 * @see java.net.URLStreamHandler#openConnection(java.net.URL)
	 */
	@Override
	protected HttpURLConnection openConnection(URL url) throws IOException {
		return new DataURLConnection(url);
	}

	/* (non-Javadoc)
	 * @see java.net.URLStreamHandler#parseURL(java.net.URL, java.lang.String, int, int)
	 */
	@Override
	protected void parseURL(URL u, String spec, int start, int limit) {
		String sub =  spec.substring(start, limit);
		setURL(u, "data", "", -1, "", "", sub, "", "");
	}
	
	/* (non-Javadoc)
	 * @see java.net.URLStreamHandler#toExternalForm(java.net.URL)
	 */
	protected String toExternalForm(URL u) {
		return u.getProtocol() + ":" + u.getPath();
	}

}
