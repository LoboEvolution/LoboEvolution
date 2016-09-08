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
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.style.HtmlInsets;

/**
 * The Class BlockQuoteRenderState.
 */
public class BlockQuoteRenderState extends AbstractMarginRenderState {

    /**
     * Instantiates a new block quote render state.
     *
     * @param prevRenderState
     *            the prev render state
     * @param element
     *            the element
     */
    public BlockQuoteRenderState(RenderState prevRenderState,
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
        int dpi = GraphicsEnvironment.isHeadless() ? 72 : Toolkit
                .getDefaultToolkit().getScreenResolution();
        insets.left = (int) Math.round((dpi * 30.0) / 72.0);
        insets.right = insets.left / 2;
        insets.leftType = HtmlInsets.TYPE_PIXELS;
        insets.rightType = HtmlInsets.TYPE_PIXELS;
        return insets;
    }
}
