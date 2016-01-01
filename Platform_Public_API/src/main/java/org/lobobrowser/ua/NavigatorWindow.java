/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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

import java.awt.Component;
import java.awt.Window;

import javax.swing.JMenu;

/**
 * This interface represents a navigator window.
 */
public interface NavigatorWindow {
    /**
     * Adds a top-level menu to the window.
     *
     * @param menuId
     *            A globally unique ID for the menu.
     * @param menu
     *            A JMenu instance.
     * @see #getMenu(String)
     */
    void addMenu(String menuId, JMenu menu);

    /**
     * Gets a menu previously added, typically by another extension with higher
     * priority.
     *
     * @param menuId
     *            The unique ID of the menu. The convention in Lobo is to use
     *            "lobo." followed by the name of the menu in lower case, with
     *            any spaces converted to dots. For example, the ID of the File
     *            menu should be "lobo.file". The ID of the Page Services menu
     *            should be "lobo.page.services".
     * @return A JMenu instance.
     */
    JMenu getMenu(String menuId);

    /**
     * Adds a "tool bar" component to the window. The preferred height of the
     * tool bar is used, whereas its width will be set roughly to the width of
     * the window.
     *
     * @param toolBar
     *            A AWT or Swing lightweight.
     */
    void addToolBar(Component toolBar);

    /**
     * Adds a component to the shared tool bar. The preferred width of the
     * component is used, whereas its height will be set roughly to the height
     * of the shared tool bar.
     *
     * @param toolBarComponent
     *            the tool bar component
     * @see #createGlueComponent(Component, boolean)
     */
    void addSharedToolBarComponent(Component toolBarComponent);

    /**
     * Adds a component to the status bar. The preferred width of the component
     * is used, whereas its height will be set roughly to the height of the
     * status bar.
     *
     * @param statusBarComponent
     *            the status bar component
     * @see #createGlueComponent(Component, boolean)
     */
    void addStatusBarComponent(java.awt.Component statusBarComponent);

    /**
     * Adds a component to the address bar. The preferred width of the component
     * is used, whereas its height will be set roughly to the height of the
     * status bar.
     *
     * @param addressBarComponent
     *            the address bar component
     * @see #createGlueComponent(Component, boolean)
     */
    void addAddressBarComponent(java.awt.Component addressBarComponent);

    /**
     * Adds a listener of window events.
     *
     * @param listener
     *            A listener instance.
     */
    void addNavigatorWindowListener(NavigatorWindowListener listener);

    /**
     * Removes a listener previously added with
     * {@link #addNavigatorWindowListener(NavigatorWindowListener)}.
     *
     * @param listener
     *            the listener
     */
    void removeNavigatorWindowListener(NavigatorWindowListener listener);

    /** Gets the top frame.
	 *
	 * @return the top frame
	 */
    NavigatorFrame getTopFrame();

    /**
     * Creates a component wrapper that expands to fill its parent's available
     * space. It only works if the parent uses a Swing <code>BoxLayout</code>.
     * Examples of components that are wrapped this way are the address combo
     * box and the status message component.
     *
     * @param wrappedComponent
     *            The component that is wrapped by the glue box.
     * @param usingMaxSize
     *            Whether the adjacent components have a maximum size that the
     *            container should try to use. If this argument is
     *            <code>false</code>, it is assumed that the adjacent components
     *            can be shrunk to their minimum sizes.
     * @return the component
     */
    Component createGlueComponent(Component wrappedComponent,
            boolean usingMaxSize);

    /**
     * Creates a gap component that should be placed between toolbar, address
     * bar or status bar components.
     *
     * @return the component
     */
    Component createGap();

    /**
     * Closes the window.
     */
    void dispose();

    /** Gets the user agent.
	 *
	 * @return the user agent
	 */
    UserAgent getUserAgent();

    /**
     * Can back.
     *
     * @return true, if successful
     */
    boolean canBack();

    /**
     * Can forward.
     *
     * @return true, if successful
     */
    boolean canForward();

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
     * Can reload.
     *
     * @return true, if successful
     */
    boolean canReload();

    /**
     * Reload.
     *
     * @return true, if successful
     */
    boolean reload();

    /**
     * Stop.
     *
     * @return true, if successful
     */
    boolean stop();

    /**
     * Can copy.
     *
     * @return true, if successful
     */
    boolean canCopy();

    /**
     * Copy.
     *
     * @return true, if successful
     */
    boolean copy();

    /**
     * Checks for source.
     *
     * @return true, if successful
     */
    boolean hasSource();

    /**
     * Navigates to a {@link NavigationEntry} belonging to navigation history in
     * the current session. without generating a new entry, in much the same way
     * that {@link #back()} and {@link #forward()} work.
     *
     * @param entry
     *            A existing <code>NavigationEntry</code>.
     * @return True if the operation succeeded.
     */
    boolean goTo(NavigationEntry entry);

    /** Gets the back navigation entries.
	 *
	 * @return the back navigation entries
	 */
    NavigationEntry[] getBackNavigationEntries();

    /** Gets the forward navigation entries.
	 *
	 * @return the forward navigation entries
	 */
    NavigationEntry[] getForwardNavigationEntries();

    /** Gets the current navigation entry.
	 *
	 * @return the current navigation entry
	 */
    NavigationEntry getCurrentNavigationEntry();

    /** Gets the awt window.
	 *
	 * @return the awt window
	 */
    Window getAwtWindow();
}
