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
package org.loboevolution.http;

/**
 * Based on: The HttpClient project's HttpStatus class.
 *
 * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html"> http://www.
 * w3.org/Protocols/rfc2616/rfc2616-sec10.html</a>
 *
 * @author rbair
 */
public enum StatusCode {

	/** The continue. */
	CONTINUE(100, "Continue"),
	/** The switching protocols. */
	SWITCHING_PROTOCOLS(101, "Switching Protocols"),
	/** The processing. */
	PROCESSING(102, "Processing"),
	/** The ok. */
	OK(200, "OK"),
	/** The created. */
	CREATED(201, "Created"),
	/** The accepted. */
	ACCEPTED(202, "Accepted"),
	/** The non authoritative information. */
	NON_AUTHORITATIVE_INFORMATION(203, "Non Authoritative Information"),
	/** The no content. */
	NO_CONTENT(204, "No Content"),
	/** The reset content. */
	RESET_CONTENT(205, "Reset Content"),
	/** The partial content. */
	PARTIAL_CONTENT(206, "Partial Content"),
	/** The multi status. */
	MULTI_STATUS(207, "Multi Status"),
	/** The multiple choices. */
	MULTIPLE_CHOICES(300, "Multiple Choices"),
	/** The moved permanently. */
	MOVED_PERMANENTLY(301, "Moved Permanently"),
	/** The moved temporarily. */
	MOVED_TEMPORARILY(302, "Moved Temporarily"),
	/** The see other. */
	SEE_OTHER(303, "See Other"),
	/** The not modified. */
	NOT_MODIFIED(304, "Not Modified"),
	/** The use proxy. */
	USE_PROXY(305, "Use Proxy"),
	/** The temporary redirect. */
	TEMPORARY_REDIRECT(307, "Temporary Redirect"),
	/** The bad request. */
	BAD_REQUEST(400, "Bad Request"),
	/** The unauthorized. */
	UNAUTHORIZED(401, "Unauthorized"),
	/** The payment required. */
	PAYMENT_REQUIRED(402, "Payment Required"),
	/** The forbidden. */
	FORBIDDEN(403, "Forbidden"),
	/** The not found. */
	NOT_FOUND(404, "Not Found"),
	/** The method not allowed. */
	METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
	/** The not acceptable. */
	NOT_ACCEPTABLE(406, "Not Acceptable"),
	/** The proxy authentication required. */
	PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),
	/** The request timeout. */
	REQUEST_TIMEOUT(408, "Request Timeout"),
	/** The conflict. */
	CONFLICT(409, "Conflict"),
	/** The gone. */
	GONE(410, "Gone"),
	/** The length required. */
	LENGTH_REQUIRED(411, "Length Required"),
	/** The precondition failed. */
	PRECONDITION_FAILED(412, "Precondition Failed"),
	/** The request too long. */
	REQUEST_TOO_LONG(413, "Request Too Long"),
	/** The request uri too long. */
	REQUEST_URI_TOO_LONG(414, "Request-uri Too Long"),
	/** The unsupported media type. */
	UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
	/** The requested range not satisfiable. */
	REQUESTED_RANGE_NOT_SATISFIABLE(416, "Requested Range Not Satisfiable"),
	/** The expectation failed. */
	EXPECTATION_FAILED(417, "Expectation Failed"),
	/** The unprocessable entity. */
	UNPROCESSABLE_ENTITY(418, "Unprocessable Entity"),
	/** The insufficient space on resource. */
	INSUFFICIENT_SPACE_ON_RESOURCE(419, "Insufficient Space On Resource"),
	/** The method failure. */
	METHOD_FAILURE(420, "Method Failure"),
	/** The locked. */
	LOCKED(423, "Locked"),
	/** The failed dependency. */
	FAILED_DEPENDENCY(424, "Failed Dependency"),
	/** The internal server error. */
	INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
	/** The not implemented. */
	NOT_IMPLEMENTED(501, "Not Implemented"),
	/** The bad gateway. */
	BAD_GATEWAY(502, "Bad Gateway"),
	/** The service unavailable. */
	SERVICE_UNAVAILABLE(503, "Service Unavailable"),
	/** The gateway timeout. */
	GATEWAY_TIMEOUT(504, "Gateway Timeout"),
	/** The http version not supported. */
	HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported"),
	/** The insufficient storage. */
	INSUFFICIENT_STORAGE(507, "Insufficient Storage");

	/** The code. */
	private int code;

	/** The description. */
	private String description;

	private StatusCode(int code, String description) {
		this.code = code;
		this.description = description;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
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
