/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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

/**
 * A listener of navigation events.
 *
 * @see NavigationEvent
 * @see NavigatorExtensionContext#addNavigationListener(NavigationListener)
 */
public interface NavigationListener extends java.util.EventListener {

    /** The Constant EMPTY_ARRAY. */
    NavigationListener[] EMPTY_ARRAY = new NavigationListener[0];

    /**
     * Called any time a navigation trigger originates in the event frame.
     *
     * @param event
     *            Contains navigation information.
     * @throws NavigationVetoException
     *             Thrown to cancel navigation.
     */
    void beforeNavigate(NavigationEvent event)
            throws NavigationVetoException;

    /**
     * Called any time a navigation trigger needs to be processed in the event
     * frame, but might have originated in a child frame.
     *
     * @param event
     *            Contains navigation information.
     * @throws NavigationVetoException
     *             Thrown to cancel navigation.
     */
    void beforeLocalNavigate(NavigationEvent event)
            throws NavigationVetoException;

    /**
     * Called any time a window open trigger originates in the event frame.
     *
     * @param event
     *            Contains navigation information.
     * @throws NavigationVetoException
     *             Thrown to cancel navigation.
     */
    public void beforeWindowOpen(NavigationEvent event)
            throws NavigationVetoException;
}
