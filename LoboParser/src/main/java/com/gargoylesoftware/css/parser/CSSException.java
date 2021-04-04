/*
 * Copyright (c) 2019-2020 Ronald Brill.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gargoylesoftware.css.parser;

/**
 * <p>CSSException class.</p>
 *
 * Author Ronald Brill
 *
 */
public class CSSException extends RuntimeException {

    /** Enum for error codes. */
    protected enum ErrorCode {
        /** Unspecified. */
        UNSPECIFIED_ERR,
        /** Not supported. */
        NOT_SUPPORTED_ERR,
        /** Syntax error. */
        SYNTAX_ERR,
    }

    private String message_;
    private ErrorCode code_;

    /**
     * Creates a new CSSException.
     */
    public CSSException() {
    }

    /**
     * Creates a new CSSException.
     *
     * @param message the message
     */
    public CSSException(final String message) {
        code_ = ErrorCode.UNSPECIFIED_ERR;
        message_ = message;
    }

    /**
     * Creates a new CSSException with an embeded exception.
     *
     * @param e the embeded exception.
     */
    public CSSException(final Exception e) {
        code_ = ErrorCode.UNSPECIFIED_ERR;
        initCause(e);
    }

    /**
     * Creates a new CSSException with a specific code.
     *
     * @param code a the embeded exception.
     */
    public CSSException(final ErrorCode code) {
        code_ = code;
    }

    /**
     * Creates a new CSSException with an embeded exception and a specified
     * message.
     *
     * @param code the specified code
     * @param message the message
     * @param e the embeded exception
     */
    public CSSException(final ErrorCode code, final String message, final Exception e) {
        code_ = code;
        message_ = message;
        initCause(e);
    }

    /**
     * {@inheritDoc}
     *
     * Returns the detail message of this throwable object.
     */
    @Override
    public String getMessage() {
        if (message_ != null) {
            return message_;
        }

        if (getCause() != null) {
            return getCause().getMessage();
        }

        switch (code_) {
            case UNSPECIFIED_ERR:
                return "unknown error";
            case NOT_SUPPORTED_ERR:
                return "not supported";
            case SYNTAX_ERR:
                return "syntax error";
            default:
                return null;
        }
    }

    /**
     * <p>getCode.</p>
     *
     * @return the error code for this exception.
     */
    public ErrorCode getCode() {
        return code_;
    }
}
