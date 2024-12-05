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

package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLMeterElement;
import org.loboevolution.html.node.NodeList;
import org.loboevolution.html.renderstate.DisplayRenderState;
import org.loboevolution.html.renderstate.RenderState;

public class HTMLMeterElementImpl extends HTMLElementImpl implements HTMLMeterElement {
    /**
     * <p>Constructor for HTMLElementImpl.</p>
     *
     * @param name a {@link String} object.
     */
    public HTMLMeterElementImpl(String name) {
        super(name);
    }

    @Override
    protected RenderState createRenderState(final RenderState prevRenderState) {
        return new DisplayRenderState(prevRenderState, this, RenderState.DISPLAY_INLINE_BLOCK);
    }

    @Override
    public double getHigh() {
        return 0;
    }

    @Override
    public void setHigh(double high) {

    }

    @Override
    public NodeList getLabels() {
        return null;
    }

    @Override
    public double getLow() {
        return 0;
    }

    @Override
    public void setLow(double low) {

    }

    @Override
    public double getMax() {
        return 0;
    }

    @Override
    public void setMax(double max) {

    }

    @Override
    public double getMin() {
        return 0;
    }

    @Override
    public void setMin(double min) {

    }

    @Override
    public double getOptimum() {
        return 0;
    }

    @Override
    public void setOptimum(double optimum) {

    }

    @Override
    public double getValue() {
        return 0;
    }

    @Override
    public void setValue(double value) {

    }

    @Override
    public Integer getClientWidth() {
        final int clientWidth = super.getClientWidth();
        return clientWidth == 0 ? 80 : clientWidth;
    }

    @Override
    public int getClientHeight() {
        final int clientHeight = super.getClientHeight();
        return clientHeight == 0 ? 16 : clientHeight;
    }

    @Override
    public Integer getOffsetWidth() {
        final int offsetWidth = super.getOffsetWidth();
        return offsetWidth == 0 ? 80 : offsetWidth;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "[object HTMLMeterElement]";
    }
}
