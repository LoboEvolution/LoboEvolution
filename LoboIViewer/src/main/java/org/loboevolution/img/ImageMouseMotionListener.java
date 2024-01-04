/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.img;

import java.util.EventListener;

/**
 * Interface for receiving mouse motion events on an image.
 *
 * Author Kazo Csaba
 *
 */
public interface ImageMouseMotionListener extends EventListener {
    /**
     * Invoked when the mouse has entered a pixel of an image.
     *
     * @param e
     *            the event object containing attributes of the event
     */
    void mouseMoved(ImageMouseEvent e);

    /**
     * Invoked when the mouse has entered the area of an image.
     *
     * @param e
     *            the event object containing attributes of the event
     */
    void mouseEntered(ImageMouseEvent e);

    /**
     * Invoked when the mouse has left the area of an image.
     *
     * @param e
     *            the event object containing attributes of the event
     */
    void mouseExited(ImageMouseEvent e);

    /**
     * Invoked when the mouse is moved while a button is down. Note that the
     * coordinates for the event can be outside the image bounds.
     *
     * @param e
     *            the event object containing attributes of the event
     */
    void mouseDragged(ImageMouseEvent e);
}
