/*
 * $Id: StatusCode.java 298 2008-04-16 20:01:52Z rbair $ Copyright 2004 Sun
 * Microsystems, Inc., 4150 Network Circle, Santa Clara, California 95054,
 * U.S.A. All rights reserved. This library is free software; you can
 * redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation; either version
 * 2.1 of the License, or (at your option) any later version. This library is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 */
package org.lobobrowser.http;

/**
 * Based on: The HttpClient project's HttpStatus class.
 *
 * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html"> http://www.
 * w3.org/Protocols/rfc2616/rfc2616-sec10.html</a>
 *
 * @author rbair
 */
public enum StatusCode {
    CONTINUE(100, "Continue"), SWITCHING_PROTOCOLS(101,
            "Switching Protocols"), PROCESSING(102, "Processing"), OK(200,
                    "OK"), CREATED(201, "Created"), ACCEPTED(202,
                            "Accepted"), NON_AUTHORITATIVE_INFORMATION(203,
                                    "Non Authoritative Information"), NO_CONTENT(
                                            204,
                                            "No Content"), RESET_CONTENT(205,
                                                    "Reset Content"), PARTIAL_CONTENT(
                                                            206,
                                                            "Partial Content"), MULTI_STATUS(
                                                                    207,
                                                                    "Multi Status"), MULTIPLE_CHOICES(
                                                                            300,
                                                                            "Multiple Choices"), MOVED_PERMANENTLY(
                                                                                    301,
                                                                                    "Moved Permanently"), MOVED_TEMPORARILY(
                                                                                            302,
                                                                                            "Moved Temporarily"), SEE_OTHER(
                                                                                                    303,
                                                                                                    "See Other"), NOT_MODIFIED(
                                                                                                            304,
                                                                                                            "Not Modified"), USE_PROXY(
                                                                                                                    305,
                                                                                                                    "Use Proxy"), TEMPORARY_REDIRECT(
                                                                                                                            307,
                                                                                                                            "Temporary Redirect"), BAD_REQUEST(
                                                                                                                                    400,
                                                                                                                                    "Bad Request"), UNAUTHORIZED(
                                                                                                                                            401,
                                                                                                                                            "Unauthorized"), PAYMENT_REQUIRED(
                                                                                                                                                    402,
                                                                                                                                                    "Payment Required"), FORBIDDEN(
                                                                                                                                                            403,
                                                                                                                                                            "Forbidden"), NOT_FOUND(
                                                                                                                                                                    404,
                                                                                                                                                                    "Not Found"), METHOD_NOT_ALLOWED(
                                                                                                                                                                            405,
                                                                                                                                                                            "Method Not Allowed"), NOT_ACCEPTABLE(
                                                                                                                                                                                    406,
                                                                                                                                                                                    "Not Acceptable"), PROXY_AUTHENTICATION_REQUIRED(
                                                                                                                                                                                            407,
                                                                                                                                                                                            "Proxy Authentication Required"), REQUEST_TIMEOUT(
                                                                                                                                                                                                    408,
                                                                                                                                                                                                    "Request Timeout"), CONFLICT(
                                                                                                                                                                                                            409,
                                                                                                                                                                                                            "Conflict"), GONE(
                                                                                                                                                                                                                    410,
                                                                                                                                                                                                                    "Gone"), LENGTH_REQUIRED(
                                                                                                                                                                                                                            411,
                                                                                                                                                                                                                            "Length Required"), PRECONDITION_FAILED(
                                                                                                                                                                                                                                    412,
                                                                                                                                                                                                                                    "Precondition Failed"), REQUEST_TOO_LONG(
                                                                                                                                                                                                                                            413,
                                                                                                                                                                                                                                            "Request Too Long"), REQUEST_URI_TOO_LONG(
                                                                                                                                                                                                                                                    414,
                                                                                                                                                                                                                                                    "Request-uri Too Long"), UNSUPPORTED_MEDIA_TYPE(
                                                                                                                                                                                                                                                            415,
                                                                                                                                                                                                                                                            "Unsupported Media Type"), REQUESTED_RANGE_NOT_SATISFIABLE(
                                                                                                                                                                                                                                                                    416,
                                                                                                                                                                                                                                                                    "Requested Range Not Satisfiable"), EXPECTATION_FAILED(
                                                                                                                                                                                                                                                                            417,
                                                                                                                                                                                                                                                                            "Expectation Failed"), UNPROCESSABLE_ENTITY(
                                                                                                                                                                                                                                                                                    418,
                                                                                                                                                                                                                                                                                    "Unprocessable Entity"), INSUFFICIENT_SPACE_ON_RESOURCE(
                                                                                                                                                                                                                                                                                            419,
                                                                                                                                                                                                                                                                                            "Insufficient Space On Resource"), METHOD_FAILURE(
                                                                                                                                                                                                                                                                                                    420,
                                                                                                                                                                                                                                                                                                    "Method Failure"), LOCKED(
                                                                                                                                                                                                                                                                                                            423,
                                                                                                                                                                                                                                                                                                            "Locked"), FAILED_DEPENDENCY(
                                                                                                                                                                                                                                                                                                                    424,
                                                                                                                                                                                                                                                                                                                    "Failed Dependency"), INTERNAL_SERVER_ERROR(
                                                                                                                                                                                                                                                                                                                            500,
                                                                                                                                                                                                                                                                                                                            "Internal Server Error"), NOT_IMPLEMENTED(
                                                                                                                                                                                                                                                                                                                                    501,
                                                                                                                                                                                                                                                                                                                                    "Not Implemented"), BAD_GATEWAY(
                                                                                                                                                                                                                                                                                                                                            502,
                                                                                                                                                                                                                                                                                                                                            "Bad Gateway"), SERVICE_UNAVAILABLE(
                                                                                                                                                                                                                                                                                                                                                    503,
                                                                                                                                                                                                                                                                                                                                                    "Service Unavailable"), GATEWAY_TIMEOUT(
                                                                                                                                                                                                                                                                                                                                                            504,
                                                                                                                                                                                                                                                                                                                                                            "Gateway Timeout"), HTTP_VERSION_NOT_SUPPORTED(
                                                                                                                                                                                                                                                                                                                                                                    505,
                                                                                                                                                                                                                                                                                                                                                                    "HTTP Version Not Supported"), INSUFFICIENT_STORAGE(
                                                                                                                                                                                                                                                                                                                                                                            507,
                                                                                                                                                                                                                                                                                                                                                                            "Insufficient Storage");
    private int code;
    private String description;

    private StatusCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "HTTP " + code + ": " + description;
    }

    public static StatusCode valueOf(int code) {
        for (StatusCode sc : StatusCode.values()) {
            if (sc.code == code) {
                return sc;
            }
        }
        return null;
    }
}
