/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.gui;

import org.loboevolution.component.IBrowserPanel;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.parser.DocumentBuilderImpl;
import org.loboevolution.html.parser.InputSourceImpl;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.http.UserAgentContext;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.net.ssl.SSLHandshakeException;
import java.awt.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * <p>HtmlErrorPanel class.</p>
 */
public class HtmlErrorPanel {

    /**
     * <p>createHtmlPanel.</p>
     *
     * @param browserPanel a {@link org.loboevolution.component.IBrowserPanel} object.
     * @param connection   a {@link java.net.URLConnection} object.
     * @param uri          a {@link java.lang.String} object.
     * @param exception    a {@link java.lang.Exception} object.
     * @return a {@link org.loboevolution.html.gui.HtmlPanel} object.
     */
    public HtmlPanel getErrorComponent(IBrowserPanel browserPanel, URLConnection connection, String uri, Exception exception) {
        final HtmlPanel panel = new HtmlPanel();
        panel.setBrowserPanel(browserPanel);
        panel.setName("Blu Screen Error");
        try (InputStream in = new ByteArrayInputStream(getErrorHtml(connection, exception).getBytes());
             Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {

            final InputSource is = new InputSourceImpl(reader, uri);
            final UserAgentContext ucontext = new UserAgentContext();
            final HtmlRendererContext rendererContext = new HtmlRendererContext(panel, ucontext);
            panel.setPreferredSize(new Dimension(800, 400));
            final DocumentBuilderImpl builder = new DocumentBuilderImpl(rendererContext.getUserAgentContext(), rendererContext);
            final Document document = builder.parse(is);
            panel.setDocument(document, rendererContext);
        } catch (IOException | SAXException ex) {
            throw new RuntimeException(ex);
        }
        return panel;
    }

    private String getErrorHtml(final URLConnection connection, final Exception exception) throws IOException {
        final URL url = connection.getURL();
        final Writer swriter = new StringWriter();
        final PrintWriter writer = new PrintWriter(swriter);
        writer.println("<html><style>");
        writer.println("body {background: #3973aa;height: 100vh;margin: 0;}");
        writer.println("#page {height: 100%;margin: 0 auto;margin-top: -10px;width: 70%;}");
        writer.println("#container {color: white;}");
        writer.println("</style><body>");
        writer.println("<div id=\"page\"><div id=\"container\">");
        writer.println("<h1>:(</h1><h2>Your PC ran into a problem and needs to restart. We're just collecting some error info, and then we'll restart for you.</h2>");
        writer.println("<div><h5>Url: " + getErrorUrlText(url));
        writer.println("<div><h5>If you call a support person, give them this info:");
        writer.println("<br/>Stop Code:" + exception.getMessage() + "</h5>");
        writer.println("<h4>" + getExceptionMeaning(exception, connection) + "</h4>");
        writer.println("</div></div></div></div>");
        writer.println("</body><html>");
        writer.flush();
        return swriter.toString();
    }

    private static String getErrorUrlText(final URL url) {
        return "<a style='color:white' href=\"" + url.toExternalForm() + "\">" +
                url.toExternalForm() +
                "</a>";
    }

    private static String getExceptionMeaning(final Exception exception, final URLConnection connection) throws IOException {
        Throwable cause = exception;
        if (exception instanceof InvocationTargetException) {
            cause = exception.getCause();
        }
        if (cause instanceof MalformedURLException) {
            return "A URL or URI was not formatted correctly.";
        } else if (cause instanceof SSLHandshakeException) {
            return "<p>This is most likely caused due to a JVM with crippled cipher suites.</p>" +
                    "<p>We are actively working on this. Please see https://github.com/LoboBrowser/LoboBrowser/wiki/SSL-Handshake-Failures</p>";
        } else if (cause instanceof UnknownHostException) {
            return "The host named '" + cause.getMessage()
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
        } else if (cause instanceof IOException) {
            HttpURLConnection httpcon = (HttpURLConnection) connection;
            switch (httpcon.getResponseCode()) {
                case 400:
                    return "The server could not understand the request due to invalid syntax.";
                case 401:
                    return "Although the HTTP standard specifies \"unauthorized\", semantically this response means \"unauthenticated\". That is, the client must authenticate itself to get the requested response.";
                case 402:
                    return "This response code is reserved for future use. The initial aim for creating this code was using it for digital payment systems, however this status code is used very rarely and no standard convention exists.";
                case 403:
                    return "The client does not have access rights to the content; that is, it is unauthorized, so the server is refusing to give the requested resource. Unlike 401 Unauthorized, the client's identity is known to the server.";
                case 404:
                    return "The server can not find the requested resource. In the browser, this means the URL is not recognized. In an API, this can also mean that the endpoint is valid but the resource itself does not exist. Servers may also send this response instead of 403 Forbidden to hide the existence of a resource from an unauthorized client. This response code is probably the most well known due to its frequent occurrence on the web.";
                case 405:
                    return "The request method is known by the server but is not supported by the target resource. For example, an API may not allow calling DELETE to remove a resource.";
                case 406:
                    return "This response is sent when the web server, after performing server-driven content negotiation, doesn't find any content that conforms to the criteria given by the user agent";
                case 407:
                    return "This is similar to 401 Unauthorized but authentication is needed to be done by a proxy.";
                case 408:
                    return "This response is sent on an idle connection by some servers, even without any previous request by the client. It means that the server would like to shut down this unused connection. This response is used much more since some browsers, like Chrome, Firefox 27+, or IE9, use HTTP pre-connection mechanisms to speed up surfing. Also note that some servers merely shut down the connection without sending this message.";
                case 409:
                    return "This response is sent when a request conflicts with the current state of the server.";
                case 410:
                    return "This response is sent when the requested content has been permanently deleted from server, with no forwarding address. Clients are expected to remove their caches and links to the resource. The HTTP specification intends this status code to be used for \"limited-time, promotional services\". APIs should not feel compelled to indicate resources that have been deleted with this status code.";
                case 411:
                    return "Server rejected the request because the Content-Length header field is not defined and the server requires it.";
                case 412:
                    return "The client has indicated preconditions in its headers which the server does not meet.";
                case 413:
                    return "Request entity is larger than limits defined by server. The server might close the connection or return an Retry-After header field.";
                case 414:
                    return "The URI requested by the client is longer than the server is willing to interpret.";
                case 415:
                    return "The media format of the requested data is not supported by the server, so the server is rejecting the request.";
                case 416:
                    return "The range specified by the Range header field in the request cannot be fulfilled. It's possible that the range is outside the size of the target URI's data.";
                case 417:
                    return "This response code means the expectation indicated by the Expect request header field cannot be met by the server.";
                case 418:
                    return "The server refuses the attempt to brew coffee with a teapot.";
                case 421:
                    return "The request was directed at a server that is not able to produce a response. This can be sent by a server that is not configured to produce responses for the combination of scheme and authority that are included in the request URI.";
                case 422:
                    return "The request was well-formed but was unable to be followed due to semantic errors.";
                case 426:
                    return "The server refuses to perform the request using the current protocol but might be willing to do so after the client upgrades to a different protocol. The server sends an Upgrade header in a 426 response to indicate the required protocol(s).";
                case 428:
                    return "The origin server requires the request to be conditional. This response is intended to prevent the 'lost update' problem, where a client GETs a resource's state, modifies it and PUTs it back to the server, when meanwhile a third party has modified the state on the server, leading to a conflict.";
                case 429:
                    return "The user has sent too many requests in a given amount of time (\"rate limiting\")";
                case 431:
                    return "The server is unwilling to process the request because its header fields are too large. The request may be resubmitted after reducing the size of the request header fields.";
                case 451:
                    return "The user agent requested a resource that cannot legally be provided, such as a web page censored by a government.";
                case 500:
                    return "A generic error message, given when no more specific message is suitable\n";
                case 501:
                    return "The server either does not recognize the request method, or it lacks the ability to fulfill the request";
                case 502:
                    return "The server was acting as a gateway or proxy and received an invalid response from the upstream server";
                case 503:
                    return "The server is currently unavailable (overloaded or down)";
                case 504:
                    return "The server was acting as a gateway or proxy and did not receive a timely response from the upstream server";
                case 505:
                    return "The server does not support the HTTP protocol version used in the request";
                default:
                    return "Indicates an Input/Output error has occurred. This is typically due "
                            + "to a network connection that cannot be establised or one that has failed, "
                            + "but it can also mean that a file could not be accessed or found.";
            }


        } else if ((cause instanceof NullPointerException) || (cause instanceof ClassCastException)) {
            return "This is a common Java exception that generally occurs due to a programming error. "
                    + "The stack trace will show if the error is in browser code, an extension or the document itself.";
        } else {
            return "Unknown.";
        }
    }
}
