/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.ua;

import java.net.URL;

/**
 * A navigation event.
 *
 * @see NavigationListener
 * @see NavigatorExtensionContext#addNavigationListener(NavigationListener)
 */
public class NavigationEvent extends java.util.EventObject {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The u rl. */
    private final URL uRL;

    /** The method. */
    private final String method;

    /** The param info. */
    private final ParameterInfo paramInfo;

    /** The target type. */
    private final TargetType targetType;

    /** The request type. */
    private final RequestType requestType;

    /** The from click. */
    private final boolean fromClick;

    /** The link object. */
    private final Object linkObject;

    /** The originating frame. */
    private final NavigatorFrame originatingFrame;

    /**
     * Instantiates a new navigation event.
     *
     * @param source
     *            the source
     * @param url
     *            the url
     * @param method
     *            the method
     * @param paramInfo
     *            the param info
     * @param targetType
     *            the target type
     * @param requestType
     *            the request type
     * @param fromClick
     *            the from click
     * @param linkObject
     *            the link object
     * @param originatingFrame
     *            the originating frame
     */
    public NavigationEvent(Object source, URL url, String method,
            ParameterInfo paramInfo, TargetType targetType,
            RequestType requestType, boolean fromClick, Object linkObject,
            NavigatorFrame originatingFrame) {
        super(source);
        this.uRL = url;
        this.method = method;
        this.paramInfo = paramInfo;
        this.targetType = targetType;
        this.requestType = requestType;
        this.fromClick = fromClick;
        this.linkObject = linkObject;
        this.originatingFrame = originatingFrame;
    }

    /**
     * Instantiates a new navigation event.
     *
     * @param source
     *            the source
     * @param url
     *            the url
     * @param method
     *            the method
     * @param paramInfo
     *            the param info
     * @param targetType
     *            the target type
     * @param requestType
     *            the request type
     * @param originatingFrame
     *            the originating frame
     */
    public NavigationEvent(Object source, URL url, String method,
            ParameterInfo paramInfo, TargetType targetType,
            RequestType requestType, NavigatorFrame originatingFrame) {
        this(source, url, method, paramInfo, targetType, requestType, false,
                null, originatingFrame);
    }

    /**
     * Instantiates a new navigation event.
     *
     * @param source
     *            the source
     * @param url
     *            the url
     * @param targetType
     *            the target type
     * @param requestType
     *            the request type
     * @param linkObject
     *            the link object
     * @param originatingFrame
     *            the originating frame
     */
    public NavigationEvent(Object source, URL url, TargetType targetType,
            RequestType requestType, Object linkObject,
            NavigatorFrame originatingFrame) {
        this(source, url, "GET", null, targetType, requestType, true,
                linkObject, originatingFrame);
    }

    /**
     * Instantiates a new navigation event.
     *
     * @param source
     *            the source
     * @param url
     *            the url
     * @param method
     *            the method
     * @param requestType
     *            the request type
     * @param originatingFrame
     *            the originating frame
     */
    public NavigationEvent(Object source, URL url, String method,
            RequestType requestType, NavigatorFrame originatingFrame) {
        this(source, url, method, null, TargetType.SELF, requestType, false,
                null, originatingFrame);
    }

    /** Gets the u rl.
	 *
	 * @return the u rl
	 */
    public URL getURL() {
        return uRL;
    }

    /** Gets the method.
	 *
	 * @return the method
	 */
    public String getMethod() {
        return method;
    }

    /** Gets the param info.
	 *
	 * @return the param info
	 */
    public ParameterInfo getParamInfo() {
        return paramInfo;
    }

    /** Gets the request type.
	 *
	 * @return the request type
	 */
    public RequestType getRequestType() {
        return requestType;
    }

    /** Checks if is from click.
	 *
	 * @return the from click
	 */
    public boolean isFromClick() {
        return fromClick;
    }

    /** Gets the link object.
	 *
	 * @return the link object
	 */
    public Object getLinkObject() {
        return linkObject;
    }

    /** Gets the originating frame.
	 *
	 * @return the originating frame
	 */
    public NavigatorFrame getOriginatingFrame() {
        return originatingFrame;
    }

    /** Gets the target type.
	 *
	 * @return the target type
	 */
    public TargetType getTargetType() {
        return targetType;
    }
}
