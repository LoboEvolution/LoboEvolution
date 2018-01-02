/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

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
package org.lobobrowser.ua;

import java.awt.Component;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.lobobrowser.clientlet.ClientletResponse;
import org.lobobrowser.clientlet.ComponentContent;
import org.lobobrowser.http.HttpRequest;

/**
 * Represents a navigator frame. In many ways this interface parallels the
 * JavaScript "Window" object.
 */
public interface NavigatorFrame {

	/**
	 * Opens a URL in a separate window.
	 *
	 * @param urlOrPath
	 *            The absolute URL or file path to open.
	 * @return the navigator frame
	 * @throws MalformedURLException
	 *             the malformed url exception
	 */
	NavigatorFrame open(String urlOrPath) throws MalformedURLException;

	/**
	 * Opens a URL in a separate window.
	 *
	 * @param url
	 *            The URL to open.
	 * @return the navigator frame
	 */
	NavigatorFrame open(URL url);

	/**
	 * Opens a URL in a separate window using the properties provided.
	 *
	 * @param url
	 *            The URL to open.
	 * @param windowProperties
	 *            Window properties, following Javascript Window.open()
	 *            conventions.
	 * @return the navigator frame
	 * @throws MalformedURLException
	 *             the malformed url exception
	 */
	NavigatorFrame open(URL url, Properties windowProperties) throws MalformedURLException;

	/**
	 * Opens a URL in a separate window.
	 *
	 * @param url
	 *            The URL to open.
	 * @param method
	 *            The request method, e.g. GET.
	 * @param pinfo
	 *            The URL parameter information.
	 * @param windowId
	 *            the window id
	 * @param windowProperties
	 *            Window properties, following Javascript Window.open()
	 *            conventions.
	 * @return the navigator frame
	 */
	NavigatorFrame open(URL url, String method, ParameterInfo pinfo, String windowId, Properties windowProperties);

	/**
	 * Opens a URL in a separate window.
	 *
	 * @param url
	 *            The URL to open.
	 * @param method
	 *            The request method, e.g. GET.
	 * @param pinfo
	 *            The URL parameter information.
	 * @return the navigator frame
	 */
	NavigatorFrame open(URL url, String method, ParameterInfo pinfo);

	/**
	 * Navigates to a URL in the current frame.
	 *
	 * @param urlOrPath
	 *            An <i>absolute</i> URL or file path.
	 * @throws MalformedURLException
	 *             the malformed url exception
	 */
	void navigate(String urlOrPath) throws MalformedURLException;

	/**
	 * Navigates to a URL in the current frame.
	 *
	 * @param urlOrPath
	 *            An <i>absolute</i> URL or file path.
	 * @param requestType
	 *            The request type.
	 * @throws MalformedURLException
	 *             the malformed url exception
	 */
	void navigate(String urlOrPath, RequestType requestType) throws MalformedURLException;

	/**
	 * Navigates to a URL in the current frame.
	 *
	 * @param url
	 *            An absolute URL.
	 */
	void navigate(URL url);

	/**
	 * Navigates to a URL in the current frame.
	 *
	 * @param url
	 *            An absolute URL.
	 * @param requestType
	 *            The request type.
	 */
	void navigate(URL url, RequestType requestType);

	/**
	 * Navigates to a URL in the current frame.
	 *
	 * @param url
	 *            An absolute or relative URL.
	 * @param method
	 *            The request method.
	 * @param paramInfo
	 *            The request parameters.
	 * @param targetType
	 *            The frame target type.
	 * @param requestType
	 *            The request type.
	 */
	void navigate(URL url, String method, ParameterInfo paramInfo, TargetType targetType, RequestType requestType);

	/**
	 * Navigates to a URL in the current frame. This method should be used when
	 * the originating frame of the request differs from the target frame.
	 *
	 * @param url
	 *            An absolute or relative URL.
	 * @param method
	 *            The request method.
	 * @param paramInfo
	 *            The request parameters.
	 * @param targetType
	 *            The frame target type.
	 * @param requestType
	 *            The request type.
	 * @param originatingFrame
	 *            The frame where the request originated.
	 */
	void navigate(URL url, String method, ParameterInfo paramInfo, TargetType targetType, RequestType requestType,
			NavigatorFrame originatingFrame);

	/**
	 * Similar to
	 * {@link #navigate(URL, String, ParameterInfo, TargetType, RequestType)} ,
	 * except this method should be called when navigation is triggered by a
	 * user click.
	 *
	 * @param url
	 *            An absolute or relative URL.
	 * @param targetType
	 *            The frame target type.
	 * @param linkObject
	 *            An implementation-dependent object representing what was
	 *            clicked. For example, in HTML documents the
	 *            <code>linkObject</code> might be of type
	 *            <code>org.w3c.dom.html2.HTMLElement</code>.
	 */
	void linkClicked(URL url, TargetType targetType, Object linkObject);

	/**
	 * Closes the current window, if allowed.
	 */
	void closeWindow();

	/**
	 * Executes a task later in the event dispatch thread.
	 *
	 * @param runnable
	 *            the runnable
	 */
	void invokeLater(Runnable runnable);

	/**
	 * Sends the window of this clientlet context to the back and may cause it
	 * to lose focus.
	 */
	void windowToBack();

	/**
	 * Sends the window of this clientlet context to the front and may cause it
	 * to request focus.
	 */
	void windowToFront();

	/**
	 * Opens a Yes/No confirmation dialog.
	 *
	 * @param message
	 *            The question text.
	 * @return True only if Yes is selected.
	 */
	boolean confirm(String message);

	/**
	 * Opens a prompt dialog.
	 *
	 * @param message
	 *            The question text.
	 * @param inputDefault
	 *            The default prompt value.
	 * @return The text entered by the user.
	 */
	String prompt(String message, String inputDefault);

	/**
	 * Gets the component.
	 *
	 * @return the component
	 */
	Component getComponent();

	/**
	 * Opens an alert dialog.
	 *
	 * @param message
	 *            The message shown in the alert dialog.
	 */
	void alert(String message);

	/**
	 * Sets the progress event.
	 *
	 * @param event
	 *            the new progress event
	 */
	void setProgressEvent(NavigatorProgressEvent event);

	/**
	 * Gets the progress event.
	 *
	 * @return the progress event
	 */
	NavigatorProgressEvent getProgressEvent();

	/**
	 * Gets the parent frame.
	 *
	 * @return the parent frame
	 */
	NavigatorFrame getParentFrame();

	/**
	 * Gets the top frame.
	 *
	 * @return the top frame
	 */
	NavigatorFrame getTopFrame();

	// (commenting out - gives opportunity to retain objects)
	// void setItem(String name, Object value);
	// Object getItem(String name);

	/**
	 * Back.
	 *
	 * @return true, if successful
	 */
	boolean back();

	/**
	 * Forward.
	 *
	 * @return true, if successful
	 */
	boolean forward();

	/**
	 * Can forward.
	 *
	 * @return true, if successful
	 */
	boolean canForward();

	/**
	 * Can back.
	 *
	 * @return true, if successful
	 */
	boolean canBack();

	/**
	 * Creates the frame.
	 *
	 * @return the navigator frame
	 */
	NavigatorFrame createFrame();

	/**
	 * Gets the default status.
	 *
	 * @return the default status
	 */
	String getDefaultStatus();

	/**
	 * Sets the default status.
	 *
	 * @param value
	 *            the new default status
	 */
	void setDefaultStatus(String value);

	/**
	 * Gets the window id.
	 *
	 * @return the window id
	 */
	String getWindowId();

	/**
	 * Gets the opener frame.
	 *
	 * @return the opener frame
	 */
	NavigatorFrame getOpenerFrame();

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	String getStatus();

	/**
	 * Checks if is window closed.
	 *
	 * @return true, if is window closed
	 */
	boolean isWindowClosed();

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	void setStatus(String status);

	/**
	 * Reload.
	 */
	void reload();

	/**
	 * Replaces the content of the frame. Thrown when the caller does not have
	 * permission to replace the content of the frame.
	 *
	 * @param response
	 *            the response
	 * @param component
	 *            the component
	 * @see org.lobobrowser.clientlet.ClientletContext#setResultingContent(Component)
	 */
	void replaceContent(ClientletResponse response, ComponentContent component);

	/**
	 * A simple alternative to
	 * {@link #replaceContent(org.lobobrowser.clientlet.ClientletResponse, org.lobobrowser.clientlet.ComponentContent)}
	 * provided for convenience.
	 *
	 * @param component
	 *            A AWT or Swing component.
	 */
	void replaceContent(Component component);

	/**
	 * Gets the source code.
	 *
	 * @return the source code
	 */
	String getSourceCode();

	/**
	 * Creates a {@link HttpRequest} object that can be used to load data over
	 * HTTP and other network protocols.
	 *
	 * @return the network request
	 */
	HttpRequest createHttpRequest();

	/**
	 * Gets the component content.
	 *
	 * @return the component content
	 */
	ComponentContent getComponentContent();

	/**
	 * Resizes the browser window.
	 *
	 * @param width
	 *            The new window width.
	 * @param height
	 *            The new window height.
	 */
	void resizeWindowTo(int width, int height);

	/**
	 * Resizes the browser window.
	 *
	 * @param byWidth
	 *            The number of pixels to expand the width by.
	 * @param byHeight
	 *            The number of pixels to expand the height by.
	 */
	void resizeWindowBy(int byWidth, int byHeight);

	/**
	 * Gets the current navigation entry.
	 *
	 * @return the current navigation entry
	 */
	NavigationEntry getCurrentNavigationEntry();

	/**
	 * Gets the previous navigation entry.
	 *
	 * @return the previous navigation entry
	 */
	NavigationEntry getPreviousNavigationEntry();

	/**
	 * Gets the next navigation entry.
	 *
	 * @return the next navigation entry
	 */
	NavigationEntry getNextNavigationEntry();

	/**
	 * Switches to a new navigation entry in the frame's history, according to
	 * the given offset.
	 *
	 * @param offset
	 *            A positive or negative number, where -1 is equivalent to
	 *            {@link #back()} and +1 is equivalent to {@link #forward()}.
	 */
	void moveInHistory(int offset);

	/**
	 * Navigates to a URL that exists in the frame's history.
	 *
	 * @param absoluteURL
	 *            The target URL.
	 */
	void navigateInHistory(String absoluteURL);

	/**
	 * Gets the history length.
	 *
	 * @return the history length
	 */
	int getHistoryLength();

	/**
	 * Sets an implementation-dependent property of the underlying component
	 * currently rendered. For example, a Cobra-based HTML component accepts
	 * properties such as <code>defaultMarginInsets</code> (java.awt.Inset),
	 * <code>defaultOverflowX</code> and <code>defaultOverflowY</code>.
	 *
	 * @param name
	 *            The name of the property.
	 * @param value
	 *            The value of the property. The type of the value depends on
	 *            the property and the underlying implementation.
	 */
	void setProperty(String name, Object value);

}
