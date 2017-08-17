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

/**
 * The Class IFrameRenderState.
 */
public class IFrameRenderState extends StyleSheetRenderState {

	/**
	 * Instantiates a new i frame render state.
	 *
	 * @param prevRenderState
	 *            the prev render state
	 * @param element
	 *            the element
	 */
	public IFrameRenderState(RenderState prevRenderState, HTMLElementImpl element) {
		super(prevRenderState, element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderstate.StyleSheetRenderState#getOverflowX()
	 */
	@Override
	public int getOverflowX() {
		int overflow = this.overflowX;
		if (overflow != -1) {
			return overflow;
		}
		overflow = super.getOverflowX();
		if (overflow == OVERFLOW_NONE) {
			HTMLElementImpl element = this.element;
			if (element != null) {
				String scrolling = element.getAttribute(SCROLLING);
				if (scrolling != null) {
					scrolling = scrolling.trim().toLowerCase();
					if ("no".equals(scrolling)) {
						overflow = OVERFLOW_HIDDEN;
					} else if ("yes".equals(scrolling)) {
						overflow = OVERFLOW_SCROLL;
					} else if ("auto".equals(scrolling)) {
						overflow = OVERFLOW_AUTO;
					}
				}
			}
		}
		this.overflowX = overflow;
		return overflow;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderstate.StyleSheetRenderState#getOverflowY()
	 */
	@Override
	public int getOverflowY() {
		int overflow = this.overflowY;
		if (overflow != -1) {
			return overflow;
		}
		overflow = super.getOverflowY();
		if (overflow == OVERFLOW_NONE) {
			HTMLElementImpl element = this.element;
			if (element != null) {
				String scrolling = element.getAttribute(SCROLLING);
				if (scrolling != null) {
					scrolling = scrolling.trim().toLowerCase();
					if ("no".equals(scrolling)) {
						overflow = OVERFLOW_HIDDEN;
					} else if ("yes".equals(scrolling)) {
						overflow = OVERFLOW_SCROLL;
					} else if ("auto".equals(scrolling)) {
						overflow = OVERFLOW_AUTO;
					}
				}
			}
		}
		this.overflowY = overflow;
		return overflow;
	}
}
