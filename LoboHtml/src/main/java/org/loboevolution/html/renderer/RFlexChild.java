/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.html.renderer;

import org.loboevolution.common.Strings;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.renderstate.RenderState;

class RFlexChild {
	
	private final RenderState renderState;

	protected RFlexChild(RenderState renderState) {
		this.renderState = renderState;
	}

	protected boolean isInlineBlock() {
		final RenderState previous = renderState.getPreviousRenderState();
		final String flexDirText = previous.getFlexDirection();
		final String flexWrapText = previous.getFlexWrap();
		final String justText = previous.getJustifyContent();
		final String alignItems = previous.getAlignItems();
		final String alignContent = previous.getAlignContent();
		boolean isInlineBlock = false;

		// flex-direction
		CSSValues flex = CSSValues.get(flexDirText);
		if (CSSValues.ROW.equals(flex) || CSSValues.ROW_REVERSE.equals(flex)) {
			isInlineBlock = true;
		}

		// flex-wrap
		flex = CSSValues.get(flexWrapText);
		if (CSSValues.WRAP.equals(flex) || CSSValues.WRAP_REVERSE.equals(flex)) {
			isInlineBlock = true;
		}

		// justify-content
		flex = CSSValues.get(justText);
		if (Strings.isNotBlank(justText)) {
			isInlineBlock = true;
		}

		// align-items
		flex = CSSValues.get(alignItems);
		if (CSSValues.FLEX_START.equals(flex) || CSSValues.STRETCH.equals(flex)) {
			isInlineBlock = true;
		}

		// align-content
		flex = CSSValues.get(alignContent);
		if (Strings.isNotBlank(alignContent)) {
			isInlineBlock = true;
		}

		return isInlineBlock; 
	}
	
	protected void flexAlign(HTMLElementImpl markupElement) {
		final RenderState previous = renderState.getPreviousRenderState();
		final String justText = previous.getJustifyContent();
		CSSValues flex = CSSValues.get(justText);
		
		if (CSSValues.SPACE_AROUND.equals(flex)) {
			markupElement.getCurrentStyle().setTextAlign("center");
		}
		
		if (CSSValues.STRETCH.equals(flex)) {
			markupElement.getCurrentStyle().setHeight("100%");
		}
	}
}
