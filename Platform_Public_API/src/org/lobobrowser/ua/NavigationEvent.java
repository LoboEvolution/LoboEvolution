/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    version 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

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
	private final URL uRL;
	private final String method;
	private final ParameterInfo paramInfo;
	private final TargetType targetType;
	private final RequestType requestType;
	private final boolean fromClick;
	private final Object linkObject;
	private final NavigatorFrame originatingFrame;

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

	public NavigationEvent(Object source, URL url, String method,
			ParameterInfo paramInfo, TargetType targetType,
			RequestType requestType, NavigatorFrame originatingFrame) {
		this(source, url, method, paramInfo, targetType, requestType, false,
				null, originatingFrame);
	}

	public NavigationEvent(Object source, URL url, TargetType targetType,
			RequestType requestType, Object linkObject,
			NavigatorFrame originatingFrame) {
		this(source, url, "GET", null, targetType, requestType, true,
				linkObject, originatingFrame);
	}

	public NavigationEvent(Object source, URL url, String method,
			RequestType requestType, NavigatorFrame originatingFrame) {
		this(source, url, method, null, TargetType.SELF, requestType, false,
				null, originatingFrame);
	}

	public URL getURL() {
		return uRL;
	}

	public String getMethod() {
		return method;
	}

	public ParameterInfo getParamInfo() {
		return paramInfo;
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public boolean isFromClick() {
		return fromClick;
	}

	public Object getLinkObject() {
		return linkObject;
	}

	public NavigatorFrame getOriginatingFrame() {
		return originatingFrame;
	}

	public TargetType getTargetType() {
		return targetType;
	}
}
