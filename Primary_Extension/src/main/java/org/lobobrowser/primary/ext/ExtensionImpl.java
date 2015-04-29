/*
 * GNU GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C)
 * 2014 - 2015 Lobo Evolution This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either verion 2 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this library; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net;
 * ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.ext;

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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.clientlet.ClientletException;
import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.clientlet.ComponentContent;
import org.lobobrowser.clientlet.JavaVersionException;
import org.lobobrowser.clientlet.NavigatorVersionException;
import org.lobobrowser.html.HtmlRendererContext;
import org.lobobrowser.html.gui.HtmlPanel;
import org.lobobrowser.html.w3c.HTMLDocument;
import org.lobobrowser.primary.clientlets.PrimaryClientletSelector;
import org.lobobrowser.primary.clientlets.html.HtmlContent;
import org.lobobrowser.primary.clientlets.html.HtmlRendererContextImpl;
import org.lobobrowser.request.UserAgentImpl;
import org.lobobrowser.ua.NavigationEntry;
import org.lobobrowser.ua.NavigatorErrorListener;
import org.lobobrowser.ua.NavigatorExceptionEvent;
import org.lobobrowser.ua.NavigatorExtension;
import org.lobobrowser.ua.NavigatorExtensionContext;
import org.lobobrowser.ua.NavigatorFrame;
import org.lobobrowser.ua.NavigatorWindow;
import org.lobobrowser.util.Html;
import org.lobobrowser.util.Strings;

/**
 * The Class ExtensionImpl.
 */
public class ExtensionImpl implements NavigatorExtension {

    /** The Constant logger. */
    private static final Logger logger = Logger.getLogger(ExtensionImpl.class
            .getName());

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.ua.NavigatorExtension#destroy()
     */
    @Override
    public void destroy() {
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.ua.NavigatorExtension#init(org.lobobrowser.ua.
     * NavigatorExtensionContext)
     */
    @Override
    public void init(NavigatorExtensionContext pcontext) {
        pcontext.addURLStreamHandlerFactory(new PrimaryStreamHandlerFactory());
        pcontext.addClientletSelector(new PrimaryClientletSelector());
        pcontext.addNavigatorErrorListener(new NavigatorErrorListener() {
            @Override
            public void errorOcurred(NavigatorExceptionEvent event) {
                showError(event.getNavigatorFrame(), event.getResponse(),
                        event.getException());
            }
        });
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.ua.NavigatorExtension#windowClosing(org.lobobrowser.ua.
     * NavigatorWindow)
     */
    @Override
    public void windowClosing(NavigatorWindow wcontext) {
        NavigationHistory.getInstance().save();
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.ua.NavigatorExtension#windowOpening(org.lobobrowser.ua.
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
        wcontext.addMenu("lobo.bookmarks", cs.getBookmarksMenu());
        wcontext.addMenu("lobo.page.services", cs.getPageServicesMenu());
        wcontext.addMenu("lobo.tools", cs.getToolsMenu());
        // wcontext.addMenu("lobo.extensions", cs.getExtensionsMenu());
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
    public static void showError(NavigatorFrame frame,
            ClientletResponse response, Throwable exception) {
        if (logger.isLoggable(Level.WARNING)) {
            logger.log(
                    Level.WARNING,
                    "showError(): An error occurred trying to process document "
                            + (response == null ? "[null]" : response
                                    .getResponseURL()), exception);
        }
        ComponentContent errorComponent = getErrorComponent(frame, response,
                exception);
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
    private static ComponentContent getErrorComponent(NavigatorFrame frame,
            ClientletResponse response, Throwable exception) {
        HtmlPanel panel = new HtmlPanel();
        HtmlRendererContext rcontext = HtmlRendererContextImpl
                .getHtmlRendererContext(frame);
        panel.setHtml(getErrorHtml(response, exception), "about:error",
                rcontext);
        String sourceCode = "[NOT AVAILABLE]";
        if (exception instanceof ClientletException) {
            ClientletException ce = (ClientletException) exception;
            String sc = ce.getSourceCode();
            if (sc != null) {
                sourceCode = sc;
            }
        }
        return new HtmlContent((HTMLDocument) panel.getRootNode(), panel,
                sourceCode);
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
    static String getErrorHtml(ClientletResponse response, Throwable exception) {
        URL url = response == null ? null : response.getResponseURL();
        String method = response == null ? null : response
                .getLastRequestMethod();
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
        writer.println("  <dd>" + getExceptionMeaning(url, exception) + "</dd>");
        writer.println("  <dt><strong>Message</strong>:</dt>");
        writer.println("  <dd>" + Html.textToHTML(exception.getMessage())
                + "</dd>");
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
        StringBuffer buf = new StringBuffer();
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
    private static String getExceptionMeaning(URL url, Throwable exception) {
        if (exception instanceof org.lobobrowser.clientlet.JavaVersionException) {
            JavaVersionException jve = (JavaVersionException) exception;
            return "This exception is thrown when the content expects the user's Java Virtual Machine "
            + "to be more up to date than it currently is. In this case the content is "
            + "expecting version "
            + jve.getExpectingVersion()
            + " whereas the version running "
            + "the browser is "
            + System.getProperty("java.version") + ".";
        } else if (exception instanceof NavigatorVersionException) {
            NavigatorVersionException nve = (NavigatorVersionException) exception;
            return "This exception is thrown when the content expects the browser version "
            + "to be more up to date than it currently is. In this case the content is "
            + "expecting version "
            + nve.getExpectingVersion()
            + " whereas the user agent "
            + "version is "
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
                return "The host named '"
                        + ((UnknownHostException) cause).getMessage()
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
            } else if ((cause instanceof NullPointerException)
                    || (cause instanceof ClassCastException)) {
                return "This is a common Java exception that generally occurs due to a programming error. "
                        + "The stack trace will show if the error is in browser code, an extension or the document itself.";
            } else if (cause instanceof ClientletException) {
                return "A "
                        + ClientletException.class.getSimpleName()
                        + " is thrown by extensions or documents typically to indicate an unexpected state has been encountered.";
            } else {
                return "Unknown.";
            }
        }
    }
}
