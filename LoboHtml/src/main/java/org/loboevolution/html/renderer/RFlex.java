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

import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.renderstate.RenderState;

public class RFlex {

	private final RenderState renderState;

	public RFlex(RenderState renderState) {
		this.renderState = renderState;
	}

	public boolean isFlexTable() {
		final String flexWrapText = renderState.getFlexWrap();
		final String justText = renderState.getJustifyContent();
		final String alignItems = renderState.getAlignItems();
		final String alignContent = renderState.getAlignContent();
		boolean isTable = false;

		// flex-wrap
		CSSValues flex = CSSValues.get(flexWrapText);
		if (CSSValues.NOWRAP.equals(flex)) {
			isTable = true;
		}

		// justify-content
		flex = CSSValues.get(justText);
		if (CSSValues.SPACE_AROUND.equals(flex)) {
			isTable = true;
		}

		// align-items
		flex = CSSValues.get(alignItems);
		if (CSSValues.FLEX_END.equals(flex) || CSSValues.CENTER.equals(flex)) {
			isTable = true;
		}

		// align-content
		flex = CSSValues.get(alignContent);
		if (CSSValues.FLEX_END.equals(flex) || CSSValues.CENTER.equals(flex)) {
			isTable = true;
		}
		
		return isTable;
	}

	
	protected void flexAlign(HTMLElementImpl markupElement) {
		final String justText = renderState.getJustifyContent();
		CSSValues flex = CSSValues.get(justText);
		
		if (CSSValues.FLEX_END.equals(flex)) {
			markupElement.getCurrentStyle().setTextAlign("right");
		}
		
		if (CSSValues.CENTER.equals(flex)) {
			markupElement.getCurrentStyle().setTextAlign("center");
		}
		
		if (CSSValues.SPACE_BETWEEN.equals(flex)) {
			markupElement.getCurrentStyle().setTextAlign("justify");
		}
	}
}
