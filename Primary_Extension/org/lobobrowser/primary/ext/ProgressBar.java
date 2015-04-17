/*
 * GNU GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C)
 * 2014 - 2015 Lobo Evolution This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either verion 2 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this library; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net;
 * ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.ext;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JProgressBar;

import org.lobobrowser.ua.ProgressType;

/**
 * The Class ProgressBar.
 */
public class ProgressBar extends JProgressBar {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The Constant logger. */
    private static final Logger logger = Logger.getLogger(ProgressBar.class
            .getName());

    /**
     * Instantiates a new progress bar.
     */
    public ProgressBar() {
        this.setStringPainted(true);
    }

    /**
     * Update progress.
     *
     * @param progressType
     *            the progress type
     * @param value
     *            the value
     * @param max
     *            the max
     */
    public void updateProgress(ProgressType progressType, int value, int max) {
        switch (progressType) {
        case NONE:
        case DONE:
            this.setString("");
            this.setIndeterminate(false);
            this.setValue(0);
            this.setMaximum(0);
            break;
        default:
            if (max == -1) {
                this.setIndeterminate(true);
                this.setString(getSizeText(value));
            } else {
                this.setIndeterminate(false);
                this.setValue(value);
                this.setMaximum(max);
                if (max == 0) {
                    this.setString("");
                } else {
                    this.setString(((value * 100) / max) + "%");
                }
            }
            break;
        }
    }

    /**
     * Round1.
     *
     * @param value
     *            the value
     * @return the double
     */
    private static double round1(double value) {
        return Math.round(value * 10.0) / 10.0;
    }

    /**
     * Gets the size text.
     *
     * @param numBytes
     *            the num bytes
     * @return the size text
     */
    private static String getSizeText(int numBytes) {
        if (numBytes == 0) {
            return "";
        } else if (numBytes < 1024) {
            return numBytes + " bytes";
        } else {
            double numK = numBytes / 1024.0;
            if (numK < 1024) {
                return round1(numK) + " Kb";
            } else {
                double numM = numK / 1024.0;
                if (numM < 1024) {
                    return round1(numM) + " Mb";
                } else {
                    double numG = numM / 1024.0;
                    return round1(numG) + " Gb";
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.JComponent#getMinimumSize()
     */
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(64, 18);
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.JComponent#getMaximumSize()
     */
    @Override
    public Dimension getMaximumSize() {
        return new Dimension(128, 100);
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(128, 18);
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
        try {
            super.paintComponent(g);
        } catch (Exception err) {
            logger.log(Level.SEVERE, "paintComponent(): Swing bug?", err);
        }
    }
}
