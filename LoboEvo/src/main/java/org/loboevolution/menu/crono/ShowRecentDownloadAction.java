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