package org.lobobrowser.protocol.data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import sun.misc.BASE64Decoder;

public class DataURLConnection extends HttpURLConnection {

	/** The header map. */
	private HashMap<String, String> headerMap = new HashMap<String, String>();

	/** The content. */
	private byte[] content = new byte[0];

	/**
	 * Instantiates a new data url connection.
	 *
	 * @param url
	 *            the url
	 */
	protected DataURLConnection(URL url) {
		super(url);
	}

	/*
	 * (non-Javadoc)
	 * @see java.net.URLConnection#connect()
	 */
	@Override
	public void connect() throws IOException {
		loadHeaderMap();
	}

	@Override
	public String getContentType() {
		String type = (String) headerMap.get("Content-Type");
		if (type == null) {
			return "Content-Type: text/plain; charset=US-ASCII";
		}
		return type;
	}

	/*
	 * (non-Javadoc)
	 * @see java.net.URLConnection#getContentLength()
	 */
	@Override
	public int getContentLength() {
		if (content != null) {
			return content.length;
		}
		
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * @see java.net.URLConnection#getHeaderField(int)
	 */
	@Override
	public String getHeaderField(int n) {
		return headerMap.get(headerMap.keySet().toArray()[n]);
	}

	/*
	 * (non-Javadoc)
	 * @see java.net.URLConnection#getHeaderField(java.lang.String)
	 */
	@Override
	public String getHeaderField(String name) {
		return headerMap.get(name);
	}

	/*
	 * (non-Javadoc)
	 * @see java.net.URLConnection#getInputStream()
	 */
	@Override
	public InputStream getInputStream() throws IOException {
		connect();
		if (content != null) {
			return new ByteArrayInputStream(content);
		}
		return new ByteArrayInputStream(new byte[] {});
	}
	
	/**
	 * Load header map.
	 */
	private void loadHeaderMap() {
		String UTF8 = "UTF-8";
		this.headerMap.clear();
		String path = getURL().getPath();
		int index2 = path.toLowerCase().indexOf(",");
		if (index2 == -1) {
			index2 = path.toLowerCase().lastIndexOf(";");
		}
		String mediatype = path.substring(0, index2).trim();
		boolean base64 = false;
		String[] split = mediatype.split("[;,]");
		String value = path.substring(index2 + 1).trim();
		if (split[0].equals("")) {
			split[0] = "text/plain";
		}

		this.headerMap.put("content-type", split[0]);

		try {
			for (int i = 1; i < split.length; i++) {
				if (split[i].contains("=")) {
					int index = split[i].indexOf("=");
					String attr = split[i].substring(0, index);
					String v = split[i].substring(index + 1);
					this.headerMap.put(attr,URLDecoder.decode(v, UTF8));
				} else if (split[i].equalsIgnoreCase("base64")) {
					base64 = true;
				}
			}
			String charset = this.getHeaderField("charset");
			if (charset == null) {
				charset = UTF8;
			}

			if (base64) {
				this.content = new BASE64Decoder().decodeBuffer(value);
			} else {
				value = URLDecoder.decode(value, charset);
				this.content = value.getBytes();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void disconnect() {}

	@Override
	public boolean usingProxy() {
		return false;
	}
}
