/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
package org.lobobrowser.gui;

/**
 * This class allows trapping the creation of {@link FramePanel} instances. This
 * is useful, for example, if you need to add a listener to every
 * <code>FramePanel</code> that is created (e.g. for IFRAMEs).
 */
public class FramePanelFactorySource {

    /** The Constant instance. */
    private static final FramePanelFactorySource instance = new FramePanelFactorySource();

    /** The active factory. */
    private volatile FramePanelFactory activeFactory = new DefaultFramePanelFactory();

    /** Gets the Constant instance.
	 *
	 * @return the Constant instance
	 */
    public static FramePanelFactorySource getInstance() {
        return instance;
    }

    /** Gets the active factory.
	 *
	 * @return the active factory
	 */
    public FramePanelFactory getActiveFactory() {
        return activeFactory;
    }

    /** Sets the active factory.
	 *
	 * @param activeFactory
	 *            the new active factory
	 */
    public void setActiveFactory(FramePanelFactory activeFactory) {
        if (activeFactory == null) {
            throw new IllegalArgumentException("activeFactory==null");
        }
        this.activeFactory = activeFactory;
    }
}
