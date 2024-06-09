/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Oct 22, 2005
 */
package org.loboevolution.component;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.config.HtmlRendererConfigImpl;
import org.loboevolution.gui.HtmlPanel;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.parser.DocumentBuilderImpl;
import org.loboevolution.html.parser.InputSourceImpl;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.net.HttpNetwork;
import org.loboevolution.net.UserAgent;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.net.ssl.SSLHandshakeException;
import java.awt.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * <p>NavigatorFrame class.</p>
 */
@Slf4j
public class NavigatorFrame {

    /**
     * <p>createHtmlPanel.</p>
     *
     * @param browserPanel a {@link org.loboevolution.component.IBrowserPanel} object.
     * @param uri a {@link java.lang.String} object.
     * @param connection a {@link java.net.URLConnection} object.
     * @return a {@link HtmlPanel} object.
     */
    public static HtmlPanel createHtmlPanel(final IBrowserPanel browserPanel, final String uri, final URLConnection connection) {
        try {
            return createPanel(browserPanel, connection, uri);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            return getErrorComponent(browserPanel, connection, uri, e);
        }
    }

    /**
     * <p>createHtmlPanel.</p>
     *
     * @param browserPanel a {@link org.loboevolution.component.IBrowserPanel} object.
     * @param uri a {@link java.lang.String} object.
     * @return a {@link HtmlPanel} object.
     */
    public static HtmlPanel createHtmlPanel(final IBrowserPanel browserPanel, final String uri) {
        URLConnection connection = null;
        try {
            final URL url = new URI(uri).toURL();
            connection = url.openConnection();
            connection.setRequestProperty("User-Agent", UserAgent.getUserAgent());
            connection.getHeaderField("Set-Cookie");
            connection.connect();
            return createPanel(browserPanel, connection, uri);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            return getErrorComponent(browserPanel, connection, uri, e);
        }
    }

    private static HtmlPanel createPanel(final IBrowserPanel browserPanel, final URLConnection connection, final String uri) throws Exception {
        final HtmlPanel panel = new HtmlPanel();
        panel.setBrowserPanel(browserPanel);
        try (final InputStream in = HttpNetwork.openConnectionCheckRedirects(connection);
             final Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {

            final InputSource is = new InputSourceImpl(reader, uri);
            final UserAgentContext ucontext = new UserAgentContext(new HtmlRendererConfigImpl());
            final HtmlRendererConfig config = new HtmlRendererConfigImpl();
            final HtmlRendererContext rendererContext = new HtmlRendererContextImpl(panel, ucontext, config);
            panel.setPreferredSize(new Dimension((int) config.getInitialWindowBounds().getWidth(), (int) config.getInitialWindowBounds().getHeight()));
            final DocumentBuilderImpl builder = new DocumentBuilderImpl(rendererContext.getUserAgentContext(),rendererContext, config);
            final Document document = builder.parse(is);
            panel.setDocument(document, rendererContext);
        } catch (final SocketTimeoutException e) {
            log.error("More time elapsed {}", connection.getConnectTimeout());
        }
        return panel;
    }

    /**
     * <p>createHtmlPanel.</p>
     *
     * @param browserPanel a {@link org.loboevolution.component.IBrowserPanel} object.
     * @param connection   a {@link java.net.URLConnection} object.
     * @param uri          a {@link java.lang.String} object.
     * @param exception    a {@link java.lang.Exception} object.
     * @return a {@link HtmlPanel} object.
     */
    public static HtmlPanel getErrorComponent(final IBrowserPanel browserPanel, final URLConnection connection, final String uri, final Exception exception) {
        final HtmlPanel panel = new HtmlPanel();
        panel.setBrowserPanel(browserPanel);
        panel.setName("Blu Screen Error");
        try (final InputStream in = new ByteArrayInputStream(getErrorHtml(connection, exception).getBytes());
             final Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {

            final InputSource is = new InputSourceImpl(reader, uri);
            final UserAgentContext ucontext = new UserAgentContext(new HtmlRendererConfigImpl());
            final HtmlRendererConfig config = new HtmlRendererConfigImpl();
            final HtmlRendererContext rendererContext = new HtmlRendererContextImpl(panel, ucontext, config);
            panel.setPreferredSize(new Dimension((int) config.getInitialWindowBounds().getWidth(), (int) config.getInitialWindowBounds().getHeight()));
            final DocumentBuilderImpl builder = new DocumentBuilderImpl(rendererContext.getUserAgentContext(), rendererContext, config);
            final Document document = builder.parse(is);
            panel.setDocument(document, rendererContext);
        } catch (final Exception ex) {
            throw new RuntimeException(ex);
        }
        return panel;
    }

    private static String getErrorHtml(final URLConnection connection, final Exception exception) throws IOException {
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
            final HttpURLConnection httpcon = (HttpURLConnection) connection;
            return switch (httpcon.getResponseCode()) {
                case 400 -> "The server could not understand the request due to invalid syntax.";
                case 401 ->
                        "Although the HTTP standard specifies \"unauthorized\", semantically this response means \"unauthenticated\". That is, the client must authenticate itself to get the requested response.";
                case 402 ->
                        "This response code is reserved for future use. The initial aim for creating this code was using it for digital payment systems, however this status code is used very rarely and no standard convention exists.";
                case 403 ->
                        "The client does not have access rights to the content; that is, it is unauthorized, so the server is refusing to give the requested resource. Unlike 401 Unauthorized, the client's identity is known to the server.";
                case 404 ->
                        "The server can not find the requested resource. In the browser, this means the URL is not recognized. In an API, this can also mean that the endpoint is valid but the resource itself does not exist. Servers may also send this response instead of 403 Forbidden to hide the existence of a resource from an unauthorized client. This response code is probably the most well known due to its frequent occurrence on the web.";
                case 405 ->
                        "The request method is known by the server but is not supported by the target resource. For example, an API may not allow calling DELETE to remove a resource.";
                case 406 ->
                        "This response is sent when the web server, after performing server-driven content negotiation, doesn't find any content that conforms to the criteria given by the user agent";
                case 407 -> "This is similar to 401 Unauthorized but authentication is needed to be done by a proxy.";
                case 408 ->
                        "This response is sent on an idle connection by some servers, even without any previous request by the client. It means that the server would like to shut down this unused connection. This response is used much more since some browsers, like Chrome, Firefox 27+, or IE9, use HTTP pre-connection mechanisms to speed up surfing. Also note that some servers merely shut down the connection without sending this message.";
                case 409 -> "This response is sent when a request conflicts with the current state of the server.";
                case 410 ->
                        "This response is sent when the requested content has been permanently deleted from server, with no forwarding address. Clients are expected to remove their caches and links to the resource. The HTTP specification intends this status code to be used for \"limited-time, promotional services\". APIs should not feel compelled to indicate resources that have been deleted with this status code.";
                case 411 ->
                        "Server rejected the request because the Content-Length header field is not defined and the server requires it.";
                case 412 -> "The client has indicated preconditions in its headers which the server does not meet.";
                case 413 ->
                        "Request entity is larger than limits defined by server. The server might close the connection or return an Retry-After header field.";
                case 414 -> "The URI requested by the client is longer than the server is willing to interpret.";
                case 415 ->
                        "The media format of the requested data is not supported by the server, so the server is rejecting the request.";
                case 416 ->
                        "The range specified by the Range header field in the request cannot be fulfilled. It's possible that the range is outside the size of the target URI's data.";
                case 417 ->
                        "This response code means the expectation indicated by the Expect request header field cannot be met by the server.";
                case 418 -> "The server refuses the attempt to brew coffee with a teapot.";
                case 421 ->
                        "The request was directed at a server that is not able to produce a response. This can be sent by a server that is not configured to produce responses for the combination of scheme and authority that are included in the request URI.";
                case 422 -> "The request was well-formed but was unable to be followed due to semantic errors.";
                case 426 ->
                        "The server refuses to perform the request using the current protocol but might be willing to do so after the client upgrades to a different protocol. The server sends an Upgrade header in a 426 response to indicate the required protocol(s).";
                case 428 ->
                        "The origin server requires the request to be conditional. This response is intended to prevent the 'lost update' problem, where a client GETs a resource's state, modifies it and PUTs it back to the server, when meanwhile a third party has modified the state on the server, leading to a conflict.";
                case 429 -> "The user has sent too many requests in a given amount of time (\"rate limiting\")";
                case 431 ->
                        "The server is unwilling to process the request because its header fields are too large. The request may be resubmitted after reducing the size of the request header fields.";
                case 451 ->
                        "The user agent requested a resource that cannot legally be provided, such as a web page censored by a government.";
                case 500 -> "A generic error message, given when no more specific message is suitable\n";
                case 501 ->
                        "The server either does not recognize the request method, or it lacks the ability to fulfill the request";
                case 502 ->
                        "The server was acting as a gateway or proxy and received an invalid response from the upstream server";
                case 503 -> "The server is currently unavailable (overloaded or down)";
                case 504 ->
                        "The server was acting as a gateway or proxy and did not receive a timely response from the upstream server";
                case 505 -> "The server does not support the HTTP protocol version used in the request";
                default -> "Indicates an Input/Output error has occurred. This is typically due "
                        + "to a network connection that cannot be establised or one that has failed, "
                        + "but it can also mean that a file could not be accessed or found.";
            };


        } else if ((cause instanceof NullPointerException) || (cause instanceof ClassCastException)) {
            return "This is a common Java exception that generally occurs due to a programming error. "
                    + "The stack trace will show if the error is in browser code, an extension or the document itself.";
        } else {
            return "Unknown.";
        }
    }
}
