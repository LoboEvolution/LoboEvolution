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

import org.lobobrowser.clientlet.ClientletSelector;

/**
 * This interface must be implemented by a platform extension or plugin.
 */
public interface NavigatorExtension {
    /**
     * Invoked when the platform first loads the extension. At this point the
     * extension can register {@link ClientletSelector}s (extra content
     * handlers) by invoking
     * {@link NavigatorExtensionContext#addClientletSelector(ClientletSelector)}
     * .
     *
     * @param pcontext
     *            The extension context. It provides extensions with access to
     *            browser functionality.
     */
    void init(NavigatorExtensionContext pcontext);

    /**
     * Invoked right before the platform opens a new window. At this point the
     * extension can add custom widgets to the platform window.
     * <p>
     * Note that this method may not be invoked if the window does not require
     * any toolbars, status bars, address bars or menus.
     *
     * @param wcontext
     *            the wcontext
     */
    void windowOpening(NavigatorWindow wcontext);

    /**
     * Invoked when a window is about to close. At this point the extension can
     * perform cleanup operations that are window specific.
     *
     * @param wcontext
     *            the wcontext
     */
    void windowClosing(NavigatorWindow wcontext);

    /**
     * Invoked when the platform needs to unload the extension. This method
     * should release any resources used by the extension.
     */
    void destroy();
}
