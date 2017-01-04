/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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

import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.style.HtmlInsets;

/**
 * The Class AbstractMarginRenderState.
 */
public abstract class AbstractMarginRenderState extends BlockRenderState {

    /**
     * Instantiates a new abstract margin render state.
     *
     * @param prevRenderState
     *            the prev render state
     * @param element
     *            the element
     */
    public AbstractMarginRenderState(RenderState prevRenderState,
            HTMLElementImpl element) {
        super(prevRenderState, element);
    }

    /** Gets the default margin insets.
	 *
	 * @return the default margin insets
	 */
    protected abstract HtmlInsets getDefaultMarginInsets();

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.renderstate.StyleSheetRenderState#getMarginInsets()
     */
    @Override
    public HtmlInsets getMarginInsets() {
        HtmlInsets insets = this.marginInsets;
        if (insets != INVALID_INSETS) {
            return insets;
        }
        insets = super.getMarginInsets();
        if (insets == null) {
            insets = this.getDefaultMarginInsets();
        }
        this.marginInsets = insets;
        return insets;
    }

}
