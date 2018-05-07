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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.loboevolution.html.control.RUIControl;
import org.loboevolution.html.dombl.ModelNode;
import org.loboevolution.html.renderer.BoundableRenderable;
import org.loboevolution.html.renderer.FloatingBounds;
import org.loboevolution.html.renderer.LineBreak;
import org.loboevolution.html.renderer.OverflowException;
import org.loboevolution.html.renderer.RFloatInfo;
import org.loboevolution.html.renderer.RLine;
import org.loboevolution.html.renderer.Renderable;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.util.Strings;

public class RBlockLine {
	
	
	/**
	 * Adds the line.
	 *
	 * @param startNode
	 *            the start node
	 * @param prevLine
	 *            the prev line
	 * @param newLineY
	 *            the new line y
	 * @return the r line
	 */
	protected RLine addLine(final ModelNode startNode, final RBlockViewport rBlockView, final RLine prevLine, final int newLineY) {
	    // lineDone must be called before we try to
	    // get float bounds.
	    lineDone(prevLine, rBlockView);
	    rBlockView.checkY(newLineY);
	    final int leftOffset = rBlockView.fetchLeftOffset(newLineY);
	    int newX = leftOffset;
	    int newMaxWidth = rBlockView.getDesiredWidth() - rBlockView.fetchRightOffset(newLineY) - leftOffset;
	    RLine rline;
	    boolean initialAllowOverflow;
	    if (prevLine == null) {
	      // Note: Assumes that prevLine == null means it's the first line.
	      final RenderState rs = rBlockView.getModelNode().getRenderState();
	      initialAllowOverflow = rs == null ? false : rs.getWhiteSpace() == RenderState.WS_NOWRAP;
	      // Text indentation only applies to the first line in the block.
	      final int textIndent = rs == null ? 0 : rs.getTextIndent(rBlockView.getAvailContentWidth());
	      if (textIndent != 0) {
	        newX += textIndent;
	        // Line width also changes!
	        newMaxWidth += (leftOffset - newX);
	      }
	    } else {
	      final int prevLineHeight = prevLine.getHeight();
	      if (prevLineHeight > 0) {
	        rBlockView.setCurrentCollapsibleMargin(0);
	      }
	      initialAllowOverflow = prevLine.isAllowOverflow();
	      if ((prevLine.x + prevLine.width) > rBlockView.getMaxX()) {
	        rBlockView.setMaxX(prevLine.x + prevLine.width);
	      }
	    }
	    rline = new RLine(startNode, rBlockView.getContainer(), newX, newLineY, newMaxWidth, 0, initialAllowOverflow);
	    rline.setParent(rBlockView);
	    List<BoundableRenderable> sr = rBlockView.getSeqRenderables();
	    if (sr == null) {
	      sr = new ArrayList<>(1);
	      rBlockView.setSeqRenderables(sr);
	    }
	    sr.add(rline);
	    rBlockView.setCurrentLine(rline);
	    return rline;
	}
	
	/**
	 * Line done.
	 *
	 * @param line
	 *            the line
	 */
	protected void lineDone(final RLine line, final RBlockViewport rBlockView) {
		int yAfterLine = line == null ? rBlockView.getPaddingInsets().top : line.y + line.height;
		Collection<RFloatInfo> pfs = rBlockView.getPendingFloats();
		if (pfs != null) {
			rBlockView.setPendingFloats(null);
			Iterator<RFloatInfo> i = pfs.iterator();
			while (i.hasNext()) {
				RFloatInfo pf = (RFloatInfo) i.next();
				rBlockView.placeFloat(pf.getRenderable(), yAfterLine, pf.isLeftFloat());
			}
		}
	}
	
	/**
	 * Adds the renderable to line.
	 *
	 * @param renderable
	 *            the renderable
	 */
	protected void addRenderableToLine(final Renderable renderable, final RBlockViewport rBlockView) {
		RLine line = rBlockView.getCurrentLine();
		int liney = line.y;
		boolean emptyLine = line.isEmpty();
		FloatingBounds floatBounds = rBlockView.getFloatBounds();
		int cleary;
		if (floatBounds != null) {
			cleary = floatBounds.getFirstClearY(liney);
		} else {
			cleary = liney + line.height;
		}
		try {
			line.add(renderable);
			// Check if the line goes into the float.
			if (floatBounds != null && cleary > liney) {
				int rightOffset = rBlockView.fetchRightOffset(liney);
				int topLineX = rBlockView.getDesiredWidth() - rightOffset;
				if (line.getX() + line.getWidth() > topLineX) {
					// Shift line down to clear area
					line.setY(cleary);
				}
			}
		} catch (OverflowException oe) {
			RBlockLine bLine = new RBlockLine();
			int nextY = emptyLine ? cleary : liney + line.height;
			bLine.addLine(renderable.getModelNode(), rBlockView, line, nextY);
			Collection<?> renderables = oe.getRenderables();
			Iterator<?> i = renderables.iterator();
			while (i.hasNext()) {
				Renderable r = (Renderable) i.next();
				addRenderableToLine(r,rBlockView);
			}
		}
		if (renderable instanceof RUIControl) {
			rBlockView.getContainer().addComponent(((RUIControl) renderable).getWidget().getComponent());
		}
	}
	
	/**
	 * Adds the line after block.
	 *
	 * @param block
	 *            the block
	 * @param informLineDone
	 *            the inform line done
	 */
	protected void addLineAfterBlock(final RBlock block, final RBlockViewport rBlockView, final boolean informLineDone) {
		List<BoundableRenderable> sr = rBlockView.getSeqRenderables();
		if (sr == null) {
			sr = new ArrayList<BoundableRenderable>(1);
			rBlockView.setSeqRenderables(sr);
		}
		RLine prevLine = rBlockView.getCurrentLine();
		boolean initialAllowOverflow;
		if (prevLine != null) {
			initialAllowOverflow = prevLine.isAllowOverflow();
			if (informLineDone) {
				RBlockLine bLine = new RBlockLine();
				bLine.lineDone(prevLine, rBlockView);
			}
			if (prevLine.x + prevLine.width > rBlockView.getMaxX()) {
				rBlockView.setMaxX(prevLine.x + prevLine.width);
			}
			// Check height only with floats.
		} else {
			initialAllowOverflow = false;
		}
		ModelNode lineNode = block.getModelNode().getParentModelNode();
		int newLineY = block.getY() + block.getHeight();
		rBlockView.checkY(newLineY);
		int leftOffset = rBlockView.fetchLeftOffset(newLineY);
		int newX = leftOffset;
		int newMaxWidth = rBlockView.getDesiredWidth() - rBlockView.fetchRightOffset(newLineY) - leftOffset;
		RLine newLine = new RLine(lineNode, rBlockView.getContainer(), newX, newLineY, newMaxWidth, 0, initialAllowOverflow);
		newLine.setParent(rBlockView);
		sr.add(newLine);
		rBlockView.setCurrentLine(newLine);
	}
	
	/**
	 * Adds the line break.
	 *
	 * @param startNode
	 *            the start node
	 * @param breakType
	 *            the break type
	 */
	public void addLineBreak(final ModelNode startNode, final RBlockViewport rBlockView, final int breakType) {
		RLine line = rBlockView.getCurrentLine();
		RenderState rs = startNode.getRenderState();
		if (line == null) {
			Insets insets = rBlockView.getPaddingInsets();
			addLine(startNode, rBlockView, null, insets.top);
			line = rBlockView.getCurrentLine();
		}
		
		if(!Strings.isBlank(rs.getlineHeight())) {
			Float f = Float.parseFloat(rs.getlineHeight());
			line.setHeight(f.intValue());
		}
		
		if (line.getHeight() == 0) {
			int fontHeight = rs.getFontMetrics().getHeight();
			line.setHeight(fontHeight);
		}
		
		line.setLineBreak(new LineBreak(breakType, startNode));
		int newLineY;
		FloatingBounds fb = rBlockView.getFloatBounds();
		if (breakType == LineBreak.NONE || fb == null) {
			newLineY = line == null ? rBlockView.getPaddingInsets().top : line.y + line.height;
		} else {
			int prevY = line == null ? rBlockView.getPaddingInsets().top : line.y + line.height;
			switch (breakType) {
			case LineBreak.LEFT:
				newLineY = fb.getLeftClearY(prevY);
				break;
			case LineBreak.RIGHT:
				newLineY = fb.getRightClearY(prevY);
				break;
			default:
				newLineY = fb.getClearY(prevY);
				break;
			}
		}
		rBlockView.setCurrentLine(addLine(startNode, rBlockView, line, newLineY));
	}

}
