/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

package org.loboevolution.html.dom;

/**
 * <p>HTMLDialogElement interface.</p> *
 */
public interface HTMLDialogElement extends HTMLElement {

    
    /**
     * <p>isOpen.</p>
     *
     * @return a boolean.
     */
    boolean isOpen();

    
    /**
     * <p>setOpen.</p>
     *
     * @param open a boolean.
     */
    void setOpen(boolean open);

    
    /**
     * <p>getReturnValue.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getReturnValue();

    
    /**
     * <p>setReturnValue.</p>
     *
     * @param returnValue a {@link java.lang.String} object.
     */
    void setReturnValue(String returnValue);

    /**
     * <p>close.</p>
     *
     * @param returnValue a {@link java.lang.String} object.
     */
    void close(String returnValue);

    /**
     * <p>close.</p>
     */
    void close();

    /**
     * <p>show.</p>
     */
    void show();

    /**
     * <p>showModal.</p>
     */
    void showModal();

}
