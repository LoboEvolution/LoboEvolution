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

import java.awt.FontMetrics;
import java.util.Collection;
import java.util.Iterator;

import org.loboevolution.html.domimpl.DOMNodeImpl;
import org.loboevolution.html.renderer.FloatingBounds;
import org.loboevolution.html.renderer.LineBreak;
import org.loboevolution.html.renderer.OverflowException;
import org.loboevolution.html.renderer.RBlank;
import org.loboevolution.html.renderer.RLine;
import org.loboevolution.html.renderer.RWord;
import org.loboevolution.html.renderer.Renderable;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.util.Strings;

public class RBlockText {
	
	/**
	 * Layout text.
	 *
	 * @param textNode
	 *            the text node
	 */
	protected void layoutText(final DOMNodeImpl textNode, final RBlockViewport rBlockView) {
		RenderState renderState = textNode.getRenderState();
		RBlockLine bLine = new RBlockLine();
		
		if (renderState == null) {
			throw new IllegalStateException(
					"RenderState is null for node " + textNode + " with parent " + textNode.getParentNode());
		}
		FontMetrics fm = renderState.getFontMetrics();
		int descent = fm.getDescent();
		int ascentPlusLeading = fm.getAscent() + fm.getLeading();
		int wordHeight = fm.getHeight();
		int blankWidth = fm.charWidth(' ');
		int whiteSpace = rBlockView.isOverrideNoWrap() ? RenderState.WS_NOWRAP : renderState.getWhiteSpace();
		int textTransform = renderState.getTextTransform();
		String text = textNode.getNodeValue();
		if (whiteSpace != RenderState.WS_PRE) {
			boolean prevAllowOverflow = rBlockView.getCurrentLine().isAllowOverflow();
			boolean allowOverflow = whiteSpace == RenderState.WS_NOWRAP;
			rBlockView.getCurrentLine().setAllowOverflow(allowOverflow);
			try {
				int length = text.length();
				StringBuilder word = new StringBuilder(12);
				for (int i = 0; i < length; i++) {
					char ch = text.charAt(i);
					if (Character.isWhitespace(ch)) {
						int wlen = word.length();
						if (wlen > 0) {
							RWord rword = new RWord(textNode, word.toString(), rBlockView.getContainer(), fm, descent,
									ascentPlusLeading, wordHeight, textTransform);
							addWordToLine(rword, rBlockView);
							word.delete(0, wlen);
						}
						RLine line = rBlockView.getCurrentLine();
						if (line.width > 0) {
							RBlank rblank = new RBlank(textNode, fm, rBlockView.getContainer(), ascentPlusLeading, blankWidth,
									wordHeight);
							line.addBlank(rblank);
						}
						for (i++; i < length; i++) {
							ch = text.charAt(i);
							if (!Character.isWhitespace(ch)) {
								word.append(ch);
								break;
							}
						}
					} else {
						word.append(ch);
					}
				}
				if (word.length() > 0) {
					RWord rword = new RWord(textNode, word.toString(), rBlockView.getContainer(), fm, descent, ascentPlusLeading,
							wordHeight, textTransform);
					addWordToLine(rword, rBlockView);
					if (Strings.isNotBlank(renderState.getlineHeight())){
						bLine.addLineBreak(rBlockView.getModelNode(), rBlockView, 0);
					}
				}
			} finally {
				rBlockView.getCurrentLine().setAllowOverflow(prevAllowOverflow);
			}
		} else {
			boolean lastCharSlashR = false;
			StringBuilder line = new StringBuilder();
			char[] list = text.toCharArray();
			for (char ch : list) {
				switch (ch) {
				case '\r':
					lastCharSlashR = true;
					break;
				case '\n':
					int llen = line.length();
					if (llen > 0) {
						RWord rword = new RWord(textNode, line.toString(), rBlockView.getContainer(), fm, descent, ascentPlusLeading,
								wordHeight, textTransform);
						addWordToLine(rword, rBlockView);
						line.delete(0, line.length());
					}
					RLine prevLine = rBlockView.getCurrentLine();
					prevLine.setLineBreak(new LineBreak(LineBreak.NONE, textNode));
					bLine.addLine(textNode, rBlockView, prevLine, prevLine.y + prevLine.height);
					break;
				default:
					if (lastCharSlashR) {
						line.append('\r');
						lastCharSlashR = false;
					}
					line.append(ch);
					break;
				}
			}
			if (line.length() > 0) {
				RWord rword = new RWord(textNode, line.toString(), rBlockView.getContainer(), fm, descent, ascentPlusLeading,
						wordHeight, textTransform);
				addWordToLine(rword, rBlockView);
			}
		}
	}
	
	/**
	 * Adds the word to line.
	 *
	 * @param renderable
	 *            the renderable
	 */
	private void addWordToLine(final RWord renderable, final RBlockViewport rBlockView) {
		// this.skipLineBreakBefore = false;
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
			line.addWord(renderable);
			// Check if the line goes into the float.
			if (!line.isAllowOverflow() && floatBounds != null && cleary > liney) {
				int rightOffset = rBlockView.fetchRightOffset(liney);
				int topLineX = rBlockView.getDesiredWidth() - rightOffset;
				if (line.getX() + line.getWidth() > topLineX) {
					// Shift line down to clear area
					line.setY(cleary);
				}
			}
		} catch (OverflowException oe) {
			int nextY = emptyLine ? cleary : liney + line.height;
			RBlockLine bLine = new RBlockLine();
			bLine.addLine(renderable.getModelNode(), rBlockView, line, nextY);
			Collection<?> renderables = oe.getRenderables();
			Iterator<?> i = renderables.iterator();
			while (i.hasNext()) {
				Renderable r = (Renderable) i.next();
				RBlockLine rLine = new RBlockLine();
				rLine.addRenderableToLine(r, rBlockView);
			}
		}
	}

}
