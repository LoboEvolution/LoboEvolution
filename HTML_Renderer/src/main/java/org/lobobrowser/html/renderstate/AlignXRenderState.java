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

import org.lobobrowser.html.style.RenderStateDelegator;

/**
 * The Class AlignXRenderState.
 */
public class AlignXRenderState extends RenderStateDelegator {

    /** The align x percent. */
    private final int alignXPercent;

    /**
     * Instantiates a new align x render state.
     *
     * @param prevRenderState
     *            the prev render state
     * @param alignXPercent
     *            the align x percent
     */
    public AlignXRenderState(final RenderState prevRenderState,
            int alignXPercent) {
        super(prevRenderState);
        this.alignXPercent = alignXPercent;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.style.RenderStateDelegator#getAlignXPercent()
     */
    @Override
    public int getAlignXPercent() {
        return this.alignXPercent;
    }
}
