/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.rendererblock;

import java.awt.Insets;

import org.loboevolution.html.HtmlAttributeProperties;
import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.html.info.FloatingInfo;
import org.loboevolution.html.renderer.FloatingBounds;
import org.loboevolution.html.renderer.FloatingBoundsSource;
import org.loboevolution.html.renderer.LineBreak;
import org.loboevolution.html.renderer.ParentFloatingBoundsSource;
import org.loboevolution.html.renderer.RElement;
import org.loboevolution.html.renderer.RLine;
import org.loboevolution.html.renderstate.RenderState;

public class RBlockPosition implements HtmlAttributeProperties{
	
	/**
	 * Position r block.
	 *
	 * @param markupElement
	 *            the markup element
	 * @param renderable
	 *            the renderable
	 */
	protected final void positionRBlock(final HTMLElementImpl markupElement, final RBlock renderable, final RBlockViewport rBlockView) {
		
		RenderState rs = renderable.getModelNode().getRenderState();
		int clear = rs.getClear();
		if (clear != LineBreak.NONE) {
			RBlockLine line = new RBlockLine();
			line.addLineBreak(renderable.getModelNode(), rBlockView, clear);
		}
		
		if (!rBlockView.addElsewhereIfPositioned(renderable, markupElement, false, true, false)) {
			int availContentHeight = rBlockView.getAvailContentHeight();
			RLine line = rBlockView.getCurrentLine();
			RBlockLine bLine = new RBlockLine();
			bLine.lineDone(line, rBlockView);
			Insets paddingInsets = rBlockView.getPaddingInsets();
			int newLineY = line == null ? paddingInsets.top : line.y + line.height;
			int availContentWidth = rBlockView.getAvailContentWidth();
			final int expectedWidth = availContentWidth;
			final int blockShiftRight = paddingInsets.right;
			final int newX = paddingInsets.left;
			final int newY = newLineY;
			FloatingBounds floatBounds = rBlockView.getFloatBounds();
			FloatingBoundsSource floatBoundsSource = floatBounds == null ? null
					: new ParentFloatingBoundsSource(blockShiftRight, expectedWidth, newX, newY, floatBounds);
			renderable.layout(availContentWidth, availContentHeight, true, false, floatBoundsSource, rBlockView.isSizeOnly());
			rBlockView.addAsSeqBlock(renderable, false, false, false, false);
			// Calculate Float.valueOfing bounds after block has been put in place.
			FloatingInfo floatingInfo = renderable.getExportableFloatingInfo();
			if (floatingInfo != null) {
				rBlockView.importFloatingInfo(floatingInfo, renderable);
			}
			// Now add line, after float is set.
			bLine.addLineAfterBlock(renderable, rBlockView, false);
			RblockLayout ly = new RblockLayout();
			ly.layoutRelative(markupElement, renderable, rBlockView);
		}
	}

	/**
	 * Position r element.
	 *
	 * @param markupElement
	 *            the markup element
	 * @param renderable
	 *            the renderable
	 * @param usesAlignAttribute
	 *            the uses align attribute
	 * @param obeysFloats
	 *            the obeys floats
	 * @param alignCenterAttribute
	 *            the align center attribute
	 */
	public final void positionRElement(final HTMLElementImpl markupElement, final RElement renderable, final RBlockViewport rBlockView, final boolean usesAlignAttribute,
			boolean obeysFloats, boolean alignCenterAttribute) {
		if (!rBlockView.addElsewhereIfPositioned(renderable, markupElement, usesAlignAttribute, true, true)) {
			int availContentWidth = rBlockView.getAvailContentWidth();
			int availContentHeight = rBlockView.getAvailContentHeight();
			RLine line = rBlockView.getCurrentLine();
			RBlockLine bLine = new RBlockLine();
			bLine.lineDone(line, rBlockView);
			if (obeysFloats) {
				int newLineY = line == null ? rBlockView.getPaddingInsets().top : line.y + line.height;
				int leftOffset = rBlockView.fetchLeftOffset(newLineY);
				int rightOffset = rBlockView.fetchRightOffset(newLineY);
				availContentWidth = rBlockView.getDesiredWidth() - leftOffset - rightOffset;
			}
			renderable.layout(availContentWidth, availContentHeight, rBlockView.isSizeOnly());
			boolean centerBlock = false;
			if (alignCenterAttribute) {
				String align = markupElement.getAttribute(ALIGN);
				centerBlock = "center".equalsIgnoreCase(align);
			}
			rBlockView.addAsSeqBlock(renderable, obeysFloats, false, true, centerBlock);
			RblockLayout ly = new RblockLayout();
			ly.layoutRelative(markupElement, renderable, rBlockView);
		}
	}

}
