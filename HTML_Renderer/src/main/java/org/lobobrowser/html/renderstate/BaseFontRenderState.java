/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.renderstate;

import org.lobobrowser.html.style.RenderStateDelegator;

/**
 * The Class BaseFontRenderState.
 */
public class BaseFontRenderState extends RenderStateDelegator {

    /** The font base. */
    private final int fontBase;

    /**
     * Instantiates a new base font render state.
     *
     * @param prevRenderState
     *            the prev render state
     * @param fontBase
     *            the font base
     */
    public BaseFontRenderState(final RenderState prevRenderState, int fontBase) {
        super(prevRenderState);
        this.fontBase = fontBase;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.style.RenderStateDelegator#getFontBase()
     */
    @Override
    public int getFontBase() {
        return this.fontBase;
    }
}
