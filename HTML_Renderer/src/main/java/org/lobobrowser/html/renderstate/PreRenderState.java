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

import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.style.AbstractCSS2Properties;

/**
 * The Class PreRenderState.
 */
public class PreRenderState extends BlockRenderState {

    /**
     * Instantiates a new pre render state.
     *
     * @param prevRenderState
     *            the prev render state
     * @param element
     *            the element
     */
    public PreRenderState(RenderState prevRenderState, HTMLElementImpl element) {
        super(prevRenderState, element);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.renderstate.StyleSheetRenderState#getWhiteSpace()
     */
    @Override
    public int getWhiteSpace() {
        Integer ws = this.iWhiteSpace;
        if (ws != null) {
            return ws.intValue();
        }
        AbstractCSS2Properties props = this.getCssProperties();
        String whiteSpaceText = props == null ? null : props.getWhiteSpace();
        int wsValue;
        if (whiteSpaceText == null) {
            wsValue = WS_PRE;
        } else {
            String whiteSpaceTextTL = whiteSpaceText.toLowerCase();
            if ("nowrap".equals(whiteSpaceTextTL)) {
                wsValue = WS_NOWRAP;
            } else if ("normal".equals(whiteSpaceTextTL)) {
                wsValue = WS_NORMAL;
            } else {
                wsValue = WS_PRE;
            }
        }
        this.iWhiteSpace = Integer.valueOf(wsValue);
        return wsValue;
    }
}
