/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.menu.crono;

import org.loboevolution.component.BrowserFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ShowRecentDownloadAction extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private final BrowserFrame frame;

    /**
     * <p>Constructor for ShowRecentDownloadAction.</p>
     *
     * @param frame a {@link org.loboevolution.component.BrowserFrame} object.
     */
    public ShowRecentDownloadAction(BrowserFrame frame) {
        this.frame = frame;
    }

    /** {@inheritDoc} */
    @Override
    public void actionPerformed(ActionEvent e) {
        final ShowDowlaodWindow window = new ShowDowlaodWindow(this.frame);
        window.setLocationByPlatform(true);
        window.setResizable(false);
        window.setVisible(true);
    }
}