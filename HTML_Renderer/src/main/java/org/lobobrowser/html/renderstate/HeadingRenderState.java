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
package org.lobobrowser.html.renderstate;

import java.awt.FontMetrics;

import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.style.HtmlInsets;

/**
 * The Class HeadingRenderState.
 */
public class HeadingRenderState extends AbstractMarginRenderState {

    /**
     * Instantiates a new heading render state.
     *
     * @param prevRenderState
     *            the prev render state
     * @param element
     *            the element
     */
    public HeadingRenderState(RenderState prevRenderState,
            HTMLElementImpl element) {
        super(prevRenderState, element);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.renderstate.AbstractMarginRenderState#getDefaultMarginInsets
     * ()
     */
    @Override
    protected HtmlInsets getDefaultMarginInsets() {
        HtmlInsets insets = new HtmlInsets();
        RenderState prevRS = this.getPreviousRenderState();
        FontMetrics fm = prevRS == null ? this.getFontMetrics() : prevRS
                .getFontMetrics();
        insets.top = fm.getHeight();
        insets.bottom = fm.getHeight();
        insets.topType = HtmlInsets.TYPE_PIXELS;
        insets.bottomType = HtmlInsets.TYPE_PIXELS;
        return insets;
    }

}
