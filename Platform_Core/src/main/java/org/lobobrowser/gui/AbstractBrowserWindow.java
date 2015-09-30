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
package org.lobobrowser.gui;

import javax.swing.JFrame;

/**
 * Browser windows should extend this class.
 */
public abstract class AbstractBrowserWindow extends JFrame implements
BrowserWindow {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Gets the root {@link FramePanel} of the window.
     *
     * @return the top frame panel
     */
    @Override
    public abstract FramePanel getTopFramePanel();

    /**
     * Gets a {@link WindowCallback} instance that receives navigation
     * notifications. This method may return <code>null</code>.
     *
     * @return the window callback
     */
    @Override
    public abstract WindowCallback getWindowCallback();

    /** The bounds assigned. */
    private boolean boundsAssigned;

    /** Checks if is bounds assigned.
	 *
	 * @return the bounds assigned
	 */
    public boolean isBoundsAssigned() {
        return boundsAssigned;
    }

    /** Sets the bounds assigned.
	 *
	 * @param boundsAssigned
	 *            the new bounds assigned
	 */
    public void setBoundsAssigned(boolean boundsAssigned) {
        this.boundsAssigned = boundsAssigned;
    }
}
