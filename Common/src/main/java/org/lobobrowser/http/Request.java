/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.http;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lobobrowser.http.Header.Element;
import org.lobobrowser.xpath.XPathUtils;
import org.w3c.dom.Document;

/**
 * <p>
 * Represents an http request. A <code>Request</code> is constructed and then
 * passed to a Session for execution. The <code>Session</code> then returns a
 * Response after execution finishes.
 * </p>
 *
 * <p>
 * It is not possible to reuse <code>Request</code>s with content bodies because
 * those bodies are specified as <code>InputStream</code>s. This is done for
 * efficient handling of large files that may be used as content bodies.
 * </p>
 *
 * <p>
 * To help simplify reuse of <code>Request</code>, a copy constructor is
 * provided which will copy everything _except_ the content body from the source
 * <code>Request</code>
 * </p>
 * .
 *
 * <p>
 * A Request is composed of a URL and HTTP method and optionally Headers,
 * Parameters, and a body.
 * </p>
 *
 * <p>
 * The URL for convenience is specified as a String, not a java.net.URL. The
 * evaluation of the URL is done when the Request is executed, as opposed to
 * when it is first set. The HTTP {@link Method} must be non-null.
 * </p>
 *
 * <p>
 * HTTP headers are represented by the {@link Header} API. All HTTP headers that
 * will be sent as part of this request are represented with a Header in this
 * class. By default, all Request objects are created with an Accept-Encoding
 * header set to "gzip", and have a Content-Type header set to 'text/plain;
 * charset="UTF-8"'. If you send other data be sure to replace the value of the
 * content type header.
 * </p>
 *
 * <p>
 * According to the HTTP specification, HTTP headers are not case sensivite.
 * Therefore, this class will allow headers to be lookedup in a case insensitive
 * manner, unlike parameters which are case sensitive.
 * </p>
 *
 * <p>
 * For convenience, this class supports automatic header generation for basic
 * authentication when the <code>username</code> property is set. Whenever
 * <code>username</code> or <code>password</code> is set it will reset the
 * "Authentication" header. Be aware that manual modifications of this header
 * will be lost whenever the username/password is changed.
 * </p>
 *
 * <p>
 * Request also supports setting query parameters. A URL is composed of the
 * protocol part, path part, and optionally the query parameter part.
 * </p>
 *
 * <pre>
 * <code>
 *     http://www.example.com/foo.html?a=b;c=d
 *     |-----|------------------------|-------|
 *      proto  path portion of URI      params
 * </code>
 * </pre>
 *
 * <p>
 * Request supports the setting of query parameters either in the URL or
 * separately from it. In the next code snippet, the query parameters are set as
 * part of the URL. As you can see from the code snippet, the query parameters,
 * even though specified as part of the URL, are extracted from the URL and can
 * be read and/or modified via the parameter API:
 * </p>
 *
 * <pre>
 * <code>
 *      Request req = new Request("http://www.example.com/foo.html?a=b;c=d");
 *      System.out.println(req.getUrl()); // prints out http://www.example.com/foo.html
 *      System.out.println(req.getParameter("a")); // prints out a=b
 *      System.out.println(req.getParameter("c")); // prints out c=d
 * </code>
 * </pre>
 *
 * <p>
 * You may also specify the query parameters completely separately from the URL:
 * </p>
 *
 * <pre>
 * <code>
 *      Request req = new Request("http://www.example.com/foo.html");
 *      req.setParameter("a", "b");
 *      req.setParameter("c", "d");
 * </code>
 * </pre>
 *
 * <p>
 * HTTP parameters must be URL encoded prior to transmission. This task is not
 * handled by the Request, but by the Session. All parameter names and values
 * are not URL encoded.
 * </p>
 *
 * <p>
 * Some HTTP oriented APIs distinguish between "GET" parameters and "POST"
 * parameters. This one does not. All parameters in this Request class are "GET"
 * parameters, meaning that regardless of the HTTP method being used the
 * parameters are set on the query string in the URL, not the body of the
 * request. A subclass, FormRequest, handles "POST" parameters in a more
 * complete way by also supporting different encoding schemes for POST requests.
 * </p>
 *
 * @author rbair
 */
public class Request extends AbstractBean {
	/**
	 * Header keys are stored in a case insensitive manner.
	 */
	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(Request.class);

	/** The headers. */
	private Map<String, Header> headers = new HashMap<String, Header>();
	/** The params. */
	private Map<String, Parameter> params = new HashMap<String, Parameter>();
	/** The follow redirects. */
	private boolean followRedirects = true;
	/** The method. */
	private String method = Method.GET;
	/** The url. */
	private String url;
	/** The request body. */
	private InputStream requestBody;
	/** The username. */
	private String username;
	/** The password. */
	private char[] password;
	/**
	 * Used in the toString() method call only if the body was set as a String.
	 * If set as an InputStream or as bytes then this will be null.
	 */
	private String stringBody;

	/**
	 * Creates a new instance of Request. The following default values are used:
	 * <ul>
	 * <li>headers: Accept-Encoding = gzip</li>
	 * <li>parameters: empty set</li>
	 * <li>followRedirects: true</li>
	 * <li>method: GET</li>
	 * <li>url: null</li>
	 * <li>requestBody: null</li>
	 * </ul>
	 */
	public Request() {
		this(Method.GET, null);
	}

	/**
	 * Creaets a new instance of Request with the specified URL. Other default
	 * values are the same as for the default constructor.
	 *
	 * @param url
	 *            the url
	 */
	public Request(String url) {
		this(Method.GET, url);
	}

	/**
	 * Creates a new instance of Request with the specified HTTP method and url.
	 * All other default values are the same as for the default consturctor.
	 *
	 * @param method
	 *            The HTTP method. If null, Method.GET is used.
	 * @param url
	 *            The url. If non null, any query parameters are extracted and
	 *            set as params for this request.
	 */
	public Request(String method, String url) {
		this.method = method == null ? Method.GET : method;
		setHeader("Accept-Encoding", "gzip");
		setHeader("Content-Type", "text/plain; charset=UTF-8");
		if (url != null) {
			setUrlImpl(url);
		}
	}

	/**
	 * <p>
	 * Creates a new instance of Request, using <code>source</code> as the basis
	 * for all of the initial property values (except for requestBody, which is
	 * always null). This is a copy constructor.
	 * </p>
	 *
	 * @param source
	 *            The source Request to copy
	 */
	public Request(Request source) {
		if (source != null) {
			username = source.username;
			password = source.password;
			headers.putAll(source.headers);
			params.putAll(source.params);
			followRedirects = source.followRedirects;
			method = source.method;
			url = source.url;
		}
	}

	/**
	 * Returns the Header with the given name, or null if there is no such
	 * header. Header names are checked in a case insensitive manner.
	 *
	 * @param name
	 *            the name to look for. If null then a null value will be
	 *            returned
	 * @return the Header with the given name.
	 */
	public final Header getHeader(String name) {
		if (name == null) {
			return null;
		}
		return headers.get(name.toLowerCase());
	}

	/**
	 * Creates a new Header with the given name and value, and no elements and
	 * adds it to the set of headers.
	 *
	 * @param name
	 *            The name. Must not be null.
	 * @param value
	 *            The value. May be null.
	 */
	public final void setHeader(String name, String value) {
		if (name == null) {
			throw new IllegalArgumentException("Name cannot be null");
		}
		setHeader(new Header(name, value));
	}

	/**
	 * Creates a new Header with the given name, value, and elements and adds it
	 * to the set of headers.
	 *
	 * @param name
	 *            The name. Must not be null.
	 * @param value
	 *            The value. May be null.
	 * @param elements
	 *            The elements. May be null.
	 */
	public final void setHeader(String name, String value, Element... elements) {
		if (name == null) {
			throw new IllegalArgumentException("Name cannot be null");
		}
		setHeader(new Header(name, value, elements));
	}

	/**
	 * Sets the header.
	 *
	 * @param header
	 *            the new header
	 */
	public void setHeader(Header header) {
		if (header == null) {
			throw new IllegalArgumentException("header cannot be null");
		} else if (header.getName() == null) {
			throw new IllegalArgumentException("header name cannot be null");
		}
		headers.put(header.getName().toLowerCase(), header);
		// update the username/password if an auth header was just set
		if ("Authentication".equals(header.getName())) {
			try {
				String encoded = header.getValue().substring(6);
				String tmp = base64Decode(encoded);
				String u = tmp.substring(0, tmp.indexOf(":"));
				String p = tmp.substring(tmp.indexOf(":") + 1);
				String oldUsername = this.username;
				firePropertyChange("username", oldUsername, this.username = u);
				this.password = p.toCharArray();
			} catch (Exception e) { /* oh well */
			}
		}
	}

	/**
	 * Removes the given header from this Request.
	 *
	 * @param header
	 *            the Header to remove. If null, nothing happens. If the header
	 *            is not specified in this Request, nothing happens.
	 */
	public final void removeHeader(Header header) {
		if (header != null) {
			headers.remove(header.getName().toLowerCase());
		}
	}

	/**
	 * Removes the given named header from this Request. The header is
	 * case-insensitive.
	 *
	 * @param header
	 *            the name of the Header to remove. If null, nothing happens. If
	 *            the header is not specified in this Request, nothing happens.
	 *            Matches in a case-insensitive manner.
	 */
	public final void removeHeader(String header) {
		headers.remove(header.toLowerCase());
	}

	/**
	 * Gets the headers.
	 *
	 * @return the headers
	 */
	public final Header[] getHeaders() {
		return headers.values().toArray(new Header[0]);
	}

	/**
	 * Sets the headers.
	 *
	 * @param headers
	 *            the new headers
	 */
	public final void setHeaders(Header... headers) {
		this.headers.clear();
		if (headers != null) {
			for (Header h : headers) {
				setHeader(h);
			}
		}
	}

	/**
	 * Returns the Parameter with the given name, or null if there is no such
	 * Parameter.
	 *
	 * @param name
	 *            the name to look for. If null, null is returned.
	 * @return the Parameter with the given name.
	 */
	public final Parameter getParameter(String name) {
		if (name == null) {
			return null;
		}
		return params.get(name);
	}

	/**
	 * Creates a Parameter using the given name and value and then adds it to
	 * the set of parameters.
	 *
	 * @param name
	 *            must not be null
	 * @param value
	 *            the value
	 */
	public final void setParameter(String name, String value) {
		if (name == null) {
			throw new IllegalArgumentException("Parameter name cannot be null");
		}
		setParameter(new Parameter(name, value));
	}

	/**
	 * Sets the parameter.
	 *
	 * @param param
	 *            the new parameter
	 */
	public void setParameter(Parameter param) {
		if (param == null) {
			throw new IllegalArgumentException("param cannot be null");
		} else if (param.getName() == null) {
			throw new IllegalArgumentException("parameter name cannot be null");
		}
		params.put(param.getName(), param);
	}

	/**
	 * Gets the parameters.
	 *
	 * @return the parameters
	 */
	public final Parameter[] getParameters() {
		return params.values().toArray(new Parameter[0]);
	}

	/**
	 * Sets the parameters.
	 *
	 * @param params
	 *            the new parameters
	 */
	public final void setParameters(Parameter... params) {
		this.params.clear();
		if (params != null) {
			for (Parameter p : params) {
				setParameter(p);
			}
		}
	}

	/**
	 * Sets the follow redirects.
	 *
	 * @param b
	 *            the new follow redirects
	 */
	// TODO need to support a count of maximium redirects
	public void setFollowRedirects(boolean b) {
		boolean old = getFollowRedirects();
		this.followRedirects = b;
		firePropertyChange("followRedirects", old, this.followRedirects);
	}

	/**
	 * Gets the follow redirects.
	 *
	 * @return the follow redirects
	 */
	public final boolean getFollowRedirects() {
		return followRedirects;
	}

	/**
	 * Sets the method.
	 *
	 * @param method
	 *            the new method
	 */
	public void setMethod(String method) {
		String old = getMethod();
		this.method = method == null ? Method.GET : method;
		firePropertyChange("method", old, this.method);
	}

	/**
	 * Gets the method.
	 *
	 * @return the method
	 */
	public final String getMethod() {
		return method;
	}

	/**
	 * Sets the url.
	 *
	 * @param url
	 *            the new url
	 */
	public void setUrl(String url) throws IllegalArgumentException {
		String old = getUrl();
		setUrlImpl(url);
		firePropertyChange("url", old, this.url);
	}

	/**
	 * Sets the url impl.
	 *
	 * @param url
	 *            the new url impl
	 */
	private void setUrlImpl(String url) {
		this.url = url;
		if (url != null) {
			// if there is a ? in the url, then there are query params
			// If there are query params, then substring the url, decode the
			// params, etc.
			int index = url.indexOf("?");
			if (index >= 0) {
				this.url = url.substring(0, index);
				String[] parts = url.substring(index + 1).split("&");
				try {
					for (String part : parts) {
						String key = null;
						String value = null;
						index = part.indexOf("=");
						if (index < 0) {
							// no value, just a key
							key = part;
							key = URLDecoder.decode(key, "UTF-8");
						} else {
							key = part.substring(0, index);
							value = part.substring(index + 1);
							key = URLDecoder.decode(key, "UTF-8");
							value = URLDecoder.decode(value, "UTF-8");
						}
						setParameter(key, value);
					}
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		}
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public final String getUrl() {
		return url;
	}

	/**
	 * Sets the username.
	 *
	 * @param username
	 *            the new username
	 */
	public void setUsername(String username) {
		String old = this.username;
		this.username = username;
		resetAuthenticationHeader();
		firePropertyChange("username", old, this.username);
	}

	/**
	 * Reset authentication header.
	 */
	private void resetAuthenticationHeader() {
		try {
			if (username == null) {
				removeHeader("authentication");
			} else {
				headers.put("authentication",
						new Header("Authentication", "Basic " + base64Encode(username + ":" + getPassword())));
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public final String getUsername() {
		return username;
	}

	/**
	 * Sets the password.
	 *
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password == null ? new char[0] : password.toCharArray();
		resetAuthenticationHeader();
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	final String getPassword() {
		return password == null ? "" : new String(password);
	}

	/**
	 * Sets the body.
	 *
	 * @param body
	 *            the new body
	 */
	public void setBody(String body) {
		stringBody = body;
		setBody(body == null ? null : body.getBytes());
	}

	/**
	 * Sets the body.
	 *
	 * @param body
	 *            the new body
	 */
	public void setBody(byte[] body) {
		if (body == null || body.length == 0) {
			requestBody = null;
		} else {
			setBody(new ByteArrayInputStream(body));
		}
	}

	/**
	 * Sets the body.
	 *
	 * @param body
	 *            the new body
	 */
	public void setBody(Document body) {
		setBody(body == null ? null : XPathUtils.toXML(body));
	}

	/**
	 * Sets the body.
	 *
	 * @param body
	 *            the new body
	 */
	public void setBody(InputStream body) {
		this.requestBody = body;
	}

	/**
	 * Gets the body.
	 *
	 * @return the body
	 * @throws Exception
	 *             the exception
	 */
	protected InputStream getBody() throws Exception {
		return requestBody;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getMethod());
		buffer.append(" " + getUrl() + "\n");
		for (Header h : getHeaders()) {
			buffer.append("  ").append(h.getName()).append(": ").append(h.getValue());
			buffer.append("\n");
		}
		if (stringBody != null) {
			buffer.append(stringBody);
		} else {
			buffer.append("<<body content>>");
		}
		return buffer.toString();
	}

	/**
	 * Base64 encode.
	 *
	 * @param s
	 *            the s
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	private static String base64Encode(String s) throws Exception {
		return new String(Base64.getEncoder().encode(s.getBytes(StandardCharsets.UTF_8)));
	}

	/**
	 * Base64 decode.
	 *
	 * @param s
	 *            the s
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	private static String base64Decode(String s) throws Exception {
		byte[] asBytes = Base64.getDecoder().decode(s);
		return new String(new String(asBytes, "utf-8"));
	}
}
