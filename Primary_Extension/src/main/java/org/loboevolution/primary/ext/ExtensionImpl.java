/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.primary.ext;

import java.awt.Component;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.clientlet.ClientletException;
import org.loboevolution.clientlet.ClientletResponse;
import org.loboevolution.clientlet.ComponentContent;
import org.loboevolution.clientlet.JavaVersionException;
import org.loboevolution.clientlet.NavigatorVersionException;
import org.loboevolution.html.HtmlRendererContext;
import org.loboevolution.html.gui.HtmlPanel;
import org.loboevolution.primary.clientlets.PrimaryClientletSelector;
import org.loboevolution.primary.clientlets.html.HtmlContent;
import org.loboevolution.primary.clientlets.html.HtmlRendererContextImpl;
import org.loboevolution.primary.ext.history.NavigationHistory;
import org.loboevolution.request.UserAgentImpl;
import org.loboevolution.ua.NavigationEntry;
import org.loboevolution.ua.NavigatorExtension;
import org.loboevolution.ua.NavigatorExtensionContext;
import org.loboevolution.ua.NavigatorFrame;
import org.loboevolution.ua.NavigatorWindow;
import org.loboevolution.util.Html;
import org.loboevolution.util.Strings;
import org.loboevolution.w3c.html.HTMLDocument;

/**
 * The Class ExtensionImpl.
 */
public class ExtensionImpl implements NavigatorExtension {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(ExtensionImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.ua.NavigatorExtension#destroy()
	 */
	@Override
	public void destroy() {
		// Method not implemented
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.ua.NavigatorExtension#init(org.loboevolution.ua.
	 * NavigatorExtensionContext)
	 */
	@Override
	public void init(NavigatorExtensionContext pcontext) {
		pcontext.addURLStreamHandlerFactory(new PrimaryStreamHandlerFactory());
		pcontext.addClientletSelector(new PrimaryClientletSelector());
		pcontext.addNavigatorErrorListener(event -> showError(event.getNavigatorFrame(), event.getResponse(), event.getException()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.ua.NavigatorExtension#windowClosing(org.loboevolution.ua.
	 * NavigatorWindow)
	 */
	@Override
	public void windowClosing(NavigatorWindow wcontext) {
		if (wcontext != null && wcontext.getCurrentNavigationEntry() != null) {
			URL url = wcontext.getCurrentNavigationEntry().getUrl();
			if (url != null)
				NavigationHistory.addAsRecent(wcontext.getCurrentNavigationEntry().getUrl());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.ua.NavigatorExtension#windowOpening(org.loboevolution.ua.
	 * NavigatorWindow)
	 */
	@Override
	public void windowOpening(NavigatorWindow wcontext) {
		ComponentSource cs = new ComponentSource(wcontext);
		Component[] abc = cs.getAddressBarComponents();
		for (Component c : abc) {
			wcontext.addAddressBarComponent(c);
		}
		Component[] sbc = cs.getStatusBarComponents();
		for (Component c : sbc) {
			wcontext.addStatusBarComponent(c);
		}
		wcontext.addMenu("lobo.file", cs.getFileMenu());
		wcontext.addMenu("lobo.edit", cs.getEditMenu());
		wcontext.addMenu("lobo.view", cs.getViewMenu());
		wcontext.addMenu("lobo.navigation", cs.getNavigationMenu());
		wcontext.addMenu("lobo.chronology", cs.getChronologyMenu());
		wcontext.addMenu("lobo.bookmarks", cs.getBookmarksMenu());
		wcontext.addMenu("lobo.tools", cs.getToolsMenu());
		wcontext.addMenu("lobo.help", cs.getHelpMenu());
		wcontext.addNavigatorWindowListener(cs);
		NavigationEntry firstEntry = wcontext.getCurrentNavigationEntry();
		cs.setNavigationEntry(firstEntry);
	}

	/**
	 * Show error.
	 *
	 * @param frame
	 *            the frame
	 * @param response
	 *            the response
	 * @param exception
	 *            the exception
	 */
	public static void showError(NavigatorFrame frame, ClientletResponse response, Throwable exception) {
		if (logger.isWarnEnabled()) {
			logger.error("showError(): An error occurred trying to process document "
					+ (response == null ? "[null]" : response.getResponseURL()), exception.getCause());
		}
		ComponentContent errorComponent = getErrorComponent(frame, response, exception);
		frame.replaceContent(response, errorComponent);
		// TODO: Get window or something, and ensure current URL is shown.
	}

	/**
	 * Gets the error component.
	 *
	 * @param frame
	 *            the frame
	 * @param response
	 *            the response
	 * @param exception
	 *            the exception
	 * @return the error component
	 */
	private static ComponentContent getErrorComponent(NavigatorFrame frame, ClientletResponse response,
			Throwable exception) {
		HtmlPanel panel = new HtmlPanel();
		HtmlRendererContext rcontext = HtmlRendererContextImpl.getHtmlRendererContext(frame);
		panel.setHtml(getErrorHtml(response, exception), "about:error", rcontext);
		String sourceCode = "[NOT AVAILABLE]";
		if (exception instanceof ClientletException) {
			ClientletException ce = (ClientletException) exception;
			String sc = ce.getSourceCode();
			if (sc != null) {
				sourceCode = sc;
			}
		}
		return new HtmlContent((HTMLDocument) panel.getRootNode(), panel, sourceCode);
	}

	/**
	 * Gets the root cause.
	 *
	 * @param t
	 *            the t
	 * @return the root cause
	 */
	private static Throwable getRootCause(Throwable t) {
		while (t.getCause() != null) {
			t = t.getCause();
		}
		return t;
	}

	/**
	 * Gets the error html.
	 *
	 * @param response
	 *            the response
	 * @param exception
	 *            the exception
	 * @return the error html
	 */
	private static String getErrorHtml(ClientletResponse response, Throwable exception) {
		URL url = response == null ? null : response.getResponseURL();
		String method = response == null ? null : response.getLastRequestMethod();
		Writer swriter = new StringWriter();
		PrintWriter writer = new PrintWriter(swriter);
		writer.println("<html><body>");
		writer.println("<dl style='background-color: #FFB0B0; border: solid red 2px; padding: 2px;'>");
		writer.println("  <big>An <strong>error</strong> occurred trying to process a request.</big>");
		writer.println("  <br>");
		if (url != null) {
			writer.println("  <dt><strong>URL</strong>:</dt>");
			writer.println("  <dd>" + getErrorUrlText(url, method) + "</dd>");
		}
		writer.println("  <dt><strong>Exception</strong>:</dt>");
		writer.println("  <dd>" + exception.getClass().getName() + "</dd>");
		writer.println("  <dt><strong>Meaning</strong>:</dt>");
		writer.println("  <dd>" + getExceptionMeaning(exception) + "</dd>");
		writer.println("  <dt><strong>Message</strong>:</dt>");
		writer.println("  <dd>" + Html.textToHTML(exception.getMessage()) + "</dd>");
		writer.println("</dl>");
		writer.println("<p></p>");
		writer.println("<table border='1' width='100%' style='background-color: #B0B0FF; bolder: solid red 2px;'>");
		writer.println("  <tr><th>");
		writer.println("  Details");
		writer.println("  </th></tr>");
		writer.println("  <tr><td>");

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		pw.flush();

		writer.println(Html.textToHTML(sw.toString()));

		if (exception.getCause() != null) {
			Throwable rootCause = getRootCause(exception);
			StringWriter sw2 = new StringWriter();
			PrintWriter pw2 = new PrintWriter(sw2);
			rootCause.printStackTrace(pw2);
			pw2.flush();
			writer.println("<p><strong>Root Cause</strong></p>");
			writer.println(Html.textToHTML(sw2.toString()));
		}

		writer.println("  </td></tr>");
		writer.println("</table>");
		writer.println("</body><html>");
		writer.flush();
		return swriter.toString();
	}

	/**
	 * Gets the error url text.
	 *
	 * @param url
	 *            the url
	 * @param method
	 *            the method
	 * @return the error url text
	 */
	private static String getErrorUrlText(URL url, String method) {
		StringBuilder buf = new StringBuilder();
		boolean isGet = "GET".equals(method);
		if (isGet) {
			buf.append("<a href=\"" + url.toExternalForm() + "\">");
		}
		buf.append(Strings.truncate(url.toExternalForm(), 80));
		if (isGet) {
			buf.append("</a>");
		}
		return buf.toString();
	}

	/**
	 * Gets the exception meaning.
	 *
	 * @param url
	 *            the url
	 * @param exception
	 *            the exception
	 * @return the exception meaning
	 */
	private static String getExceptionMeaning(Throwable exception) {
		if (exception instanceof JavaVersionException) {
			JavaVersionException jve = (JavaVersionException) exception;
			return "This exception is thrown when the content expects the user's Java Virtual Machine "
					+ "to be more up to date than it currently is. In this case the content is " + "expecting version "
					+ jve.getExpectingVersion() + " whereas the version running " + "the browser is "
					+ System.getProperty("java.version") + ".";
		} else if (exception instanceof NavigatorVersionException) {
			NavigatorVersionException nve = (NavigatorVersionException) exception;
			return "This exception is thrown when the content expects the browser version "
					+ "to be more up to date than it currently is. In this case the content is " + "expecting version "
					+ nve.getExpectingVersion() + " whereas the user agent " + "version is "
					+ UserAgentImpl.getInstance().getVersion() + ".";
		} else {
			Throwable cause = exception;
			if (exception instanceof ClientletException) {
				cause = ((ClientletException) exception).getCause();
				if (cause == null) {
					// oops
					cause = exception;
				}
			} else if (exception instanceof InvocationTargetException) {
				cause = ((InvocationTargetException) exception).getCause();
			}
			if (cause instanceof MalformedURLException) {
				return "A URL or URI was not formatted correctly.";
			} else if (cause instanceof UnknownHostException) {
				return "The host named '" + ((UnknownHostException) cause).getMessage()
						+ "' could not be found by the Domain Name Service (DNS).";
			} else if (cause instanceof SecurityException) {
				return "An attempted security violation has been detected and stopped.";
			} else if (cause instanceof ProtocolException) {
				return "Indicates there is an error in the underlying communications protocol.";
			} else if (cause instanceof SocketTimeoutException) {
				return "It means the server accepted the connection, but failed to respond after a long period of time. This is usually indicative of a server problem.";
			} else if (cause instanceof ConnectException) {
				return "It means a connection to the server could not be obtained. Typically, the server has refused the connection, i.e. it's not accepting connections on a given port.";
			} else if (cause instanceof SocketException) {
				return "Indicates there was an error in the underlying protocol, e.g. TCP/IP.";
			} else if (cause instanceof java.io.IOException) {
				return "Indicates an Input/Output error has occurred. This is typically due "
						+ "to a network connection that cannot be establised or one that has failed, "
						+ "but it can also mean that a file could not be accessed or found.";
			} else if (cause instanceof NullPointerException || cause instanceof ClassCastException) {
				return "This is a common Java exception that generally occurs due to a programming error. "
						+ "The stack trace will show if the error is in browser code, an extension or the document itself.";
			} else if (cause instanceof ClientletException) {
				return "A " + ClientletException.class.getSimpleName()
						+ " is thrown by extensions or documents typically to indicate an unexpected state has been encountered.";
			} else {
				return "Unknown.";
			}
		}
	}
}
