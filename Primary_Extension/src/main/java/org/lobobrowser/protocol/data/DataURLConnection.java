package org.lobobrowser.protocol.data;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import sun.misc.BASE64Decoder;

/**
 * http://www.ietf.org/rfc/rfc2397.txt
 * 
 * 
 * dataurl := "data:" [ mediatype ] [ ";base64" ] "," data mediatype := [ type
 * "/" subtype ] *( ";" parameter ) data := *urlchar parameter := attribute "="
 * value
 * 
 * 
 * @author toenz
 *
 */
public class DataURLConnection extends URLConnection {

	private HashMap<String, String> headerMap = new HashMap<String, String>();
	private byte[] content = new byte[0];

	protected DataURLConnection(URL url) {
		super(url);
		loadHeaderMap();
	}

	@Override
	public void connect() throws IOException {
	}

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
					this.headerMap.put(attr,
							java.net.URLDecoder.decode(v, UTF8));
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
				value = java.net.URLDecoder.decode(value, charset);
				this.content = value.getBytes();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	

	@Override
	public int getContentLength() {
		return content.length;
	}

	@Override
	public String getHeaderField(int n) {
		return headerMap.get(headerMap.keySet().toArray()[n]);
	}

	@Override
	public String getHeaderField(String name) {
		return headerMap.get(name);
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(content);
	}

}
