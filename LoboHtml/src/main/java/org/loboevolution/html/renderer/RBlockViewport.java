/*    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
package org.loboevolution.html.renderer;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import org.loboevolution.common.ArrayUtilities;
import org.loboevolution.html.HTMLTag;
import org.loboevolution.html.control.HrControl;
import org.loboevolution.html.control.RUIControl;
import org.loboevolution.html.control.UIControl;
import org.loboevolution.html.dom.HTMLDocument;
import org.loboevolution.html.dom.domimpl.DocumentFragmentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLTableElementImpl;
import org.loboevolution.html.dom.domimpl.ModelNode;
import org.loboevolution.html.dom.domimpl.NodeImpl;
import org.loboevolution.html.dom.domimpl.UINode;
import org.loboevolution.html.renderer.RLayout.MiscLayout;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.info.FloatingInfo;
import org.w3c.dom.Node;

/**
 * A substantial portion of the HTML rendering logic of the package can be found
 * in this class. This class is in charge of laying out the DOM subtree of a
 * node, usually on behalf of an RBlock. It creates a renderer subtree
 * consisting of RLine's or RBlock's. RLine's in turn contain RWord's and so on.
 * This class also happens to be used as an RBlock scrollable viewport.
 *
 * @author J. H. S.
 * @version $Id: $Id
 */
public class RBlockViewport extends BaseRCollection {
	
	private static final Logger logger = Logger.getLogger(RBlockViewport.class.getName());
	
	private static final MarkupLayout miscLayout = new MiscLayout();
	
	private static final SizeExceededException SEE = new SizeExceededException();

	private BoundableRenderable armedRenderable;

	private int availContentHeight; // does not include insets

	private int availContentWidth; // does not include insets

	private int currentCollapsibleMargin;

	private RLine currentLine;

	private int desiredHeight; // includes insets

	private int desiredWidth; // includes insets

	private List<ExportableFloat> exportableFloats = null;

	private FloatingBounds floatBounds = null;

	private Boolean isFloatLimit = null;

	private BoundableRenderable lastSeqBlock;

	private final int listNesting;

	private int maxX;

	private int maxY;

	private boolean overrideNoWrap;

	private Insets paddingInsets;

	private Collection<RFloatInfo> pendingFloats = null;

	private int positionedOrdinal;

	private SortedSet<PositionedRenderable> positionedRenderables;

	private List<Renderable> seqRenderables = null;

	private boolean sizeOnly;
	
	private int yLimit;
	
	protected final HtmlRendererContext rendererContext;
	
	protected final UserAgentContext userAgentContext;
	
	protected final RenderableContainer container;
	
	protected final FrameContext frameContext;

	/** Constant ZERO_INSETS */
	public static final Insets ZERO_INSETS = new Insets(0, 0, 0, 0);
	
	/**
	 * Constructs an HtmlBlockLayout.
	 *
	 * @param container    This is usually going to be an RBlock.
	 * @param listNesting  The nesting level for lists. This is zero except inside a
	 *                     list.
	 * @param pcontext     The HTMLParserContext instance.
	 * @param frameContext This is usually going to be HtmlBlock, an object where
	 *                     text selections are contained.
	 * @param parent       This is usually going to be the parent of
	 *                     container.
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.ModelNode} object.
	 * @param rcontext a {@link org.loboevolution.http.HtmlRendererContext} object.
	 */
	public RBlockViewport(ModelNode modelNode, RenderableContainer container, int listNesting,
			UserAgentContext pcontext, HtmlRendererContext rcontext, FrameContext frameContext, RCollection parent) {
		super(container, modelNode);
		this.parent = parent;
		this.userAgentContext = pcontext;
		this.rendererContext = rcontext;
		this.frameContext = frameContext;
		this.container = container;
		this.listNesting = listNesting;
		// Layout here can always be "invalidated"
		this.layoutUpTreeCanBeInvalidated = true;
	}
	
	private static int getPosition(HTMLElementImpl element) {
		final RenderState rs = element.getRenderState();
		return rs == null ? RenderState.POSITION_STATIC : rs.getPosition();
	}

	private final void addAlignableAsBlock(HTMLElementImpl markupElement, RElement renderable) {
		boolean regularAdd = false;
		final String align = markupElement.getAttribute("align");
		if (align != null) {
			if ("left".equalsIgnoreCase(align)) {
				layoutFloat(renderable, false, true);
			} else if ("right".equalsIgnoreCase(align)) {
				layoutFloat(renderable, false, false);
			} else {
				regularAdd = true;
			}
		} else {
			regularAdd = true;
		}
		if (regularAdd) {
			this.addAsSeqBlock(renderable);
		}
	}

	private void addAsSeqBlock(BoundableRenderable block, boolean obeysFloats, boolean informLineDone, boolean addLine,
			boolean centerBlock) {
		final Insets insets = this.paddingInsets;
		final int insetsl = insets.left;
		List<Renderable> sr = this.seqRenderables;
		if (sr == null) {
			sr = new ArrayList<Renderable>();
			this.seqRenderables = sr;
		}
		final RLine prevLine = this.currentLine;
		boolean initialAllowOverflow;
		if (prevLine != null) {
			initialAllowOverflow = prevLine.isAllowOverflow();
			if (informLineDone) {
				lineDone(prevLine);
			}
			if (prevLine.x + prevLine.width > this.maxX) {
				this.maxX = prevLine.x + prevLine.width;
			}
			// Check height only with floats.
		} else {
			initialAllowOverflow = false;
		}
		final int prevLineHeight = prevLine == null ? 0 : prevLine.height;
		int newLineY = prevLine == null ? insets.top : prevLine.y + prevLineHeight;
		int blockX;
		int blockY = prevLineHeight == 0 ? getNewBlockY(block, newLineY) : newLineY;
		final int blockWidth = block.getWidth();
		if (obeysFloats) {
			// TODO: execution of fetchLeftOffset done twice with positionRElement.
			final FloatingBounds floatBounds = this.floatBounds;
			int actualAvailWidth;
			if (floatBounds != null) {
				final int blockOffset = fetchLeftOffset(newLineY);
				blockX = blockOffset;
				final int rightOffset = fetchRightOffset(newLineY);
				actualAvailWidth = this.desiredWidth - rightOffset - blockOffset;
				if (blockWidth > actualAvailWidth) {
					blockY = floatBounds.getClearY(newLineY);
				}
			} else {
				actualAvailWidth = this.availContentWidth;
				blockX = insetsl;
			}
			if (centerBlock) {
				final int roomX = actualAvailWidth - blockWidth;
				if (roomX > 0) {
					blockX += roomX / 2;
				}
			}
		} else {
			// Block does not obey alignment margins
			blockX = insetsl;
		}
		block.setOrigin(blockX, blockY);
		sr.add(block);
		block.setParent(this);
		if (blockX + blockWidth > this.maxX) {
			this.maxX = blockX + blockWidth;
		}
		this.lastSeqBlock = block;
		this.currentCollapsibleMargin = block instanceof RElement ? ((RElement) block).getMarginBottom() : 0;
		if (addLine) {
			newLineY = blockY + block.getHeight();
			checkY(newLineY);
			final int leftOffset = fetchLeftOffset(newLineY);
			final int newX = leftOffset;
			final int newMaxWidth = this.desiredWidth - fetchRightOffset(newLineY) - leftOffset;
			final ModelNode lineNode = block.getModelNode().getParentModelNode();
			final RLine newLine = new RLine(lineNode, this.container, newX, newLineY, newMaxWidth, 0,
					initialAllowOverflow);
			newLine.setParent(this);
			sr.add(newLine);
			this.currentLine = newLine;
		}
	}

	private void addAsSeqBlock(RElement block) {
		this.addAsSeqBlock(block, true, true, true, false);
	}

	private boolean addElsewhereIfFloat(BoundableRenderable renderable, HTMLElementImpl element,
			boolean usesAlignAttribute, AbstractCSSProperties style, boolean layout) {
		// "static" handled here
		String align = null;
		if (style != null) {
			align = style.getFloat();
			if (align != null && align.length() == 0) {
				align = null;
			}
		}
		if (align == null && usesAlignAttribute) {
			align = element.getAttribute("align");
		}
		if (align != null) {
			if ("left".equalsIgnoreCase(align)) {
				layoutFloat(renderable, layout, true);
				return true;
			} else if ("right".equalsIgnoreCase(align)) {
				layoutFloat(renderable, layout, false);
				return true;
			} else {
				// fall through
			}
		}
		return false;
	}

	/**
	 * Checks for position and float attributes.
	 * 
	 * @param renderable renderable
	 * @param element element
	 * @param usesAlignAttribute usesAlignAttribute
	 * @param layoutIfPositioned layoutIfPositioned
	 * @param obeysFloats obeysFloats
	 * @return True if it was added elsewhere.
	 */
	private boolean addElsewhereIfPositioned(RElement renderable, HTMLElementImpl element, boolean usesAlignAttribute,
			boolean layoutIfPositioned, boolean obeysFloats) {
		final AbstractCSSProperties style = element.getCurrentStyle();
		final int position = getPosition(element);
		final boolean absolute = position == RenderState.POSITION_ABSOLUTE;
		final boolean fixed = position == RenderState.POSITION_FIXED;
		if (absolute || fixed) {
			if (layoutIfPositioned) {
				if (renderable instanceof RBlock) {
					final RBlock block = (RBlock) renderable;
			          block.layout(this.availContentWidth, this.availContentHeight, false, false, null, this.sizeOnly);
				} else {
					renderable.layout(this.availContentWidth, this.availContentHeight, this.sizeOnly);
				}
			}
			final RenderState rs = element.getRenderState();
			this.scheduleAbsDelayedPair(renderable, style, rs, currentLine, absolute);
			return true;
		} else {
			if (addElsewhereIfFloat(renderable, element, usesAlignAttribute, style, layoutIfPositioned)) {
				return true;
			}
		}
		return false;
	}

	private void addExportableFloat(final BoundableRenderable element, final boolean leftFloat, final int origX, final int origY) {
		List<ExportableFloat> ep = this.exportableFloats;
		if (ep == null) {
			ep = new ArrayList<ExportableFloat>(1);
			this.exportableFloats = ep;
		}
		ExportableFloat ef = new ExportableFloat(element, leftFloat, origX, origY);
		ep.add(ef);
	}

	private RLine addLine(ModelNode startNode, RLine prevLine, int newLineY) {
		// lineDone must be called before we try to
		// get float bounds.
		lineDone(prevLine);
		checkY(newLineY);
		final int leftOffset = fetchLeftOffset(newLineY);
		int newX = leftOffset;
		int newMaxWidth = this.desiredWidth - fetchRightOffset(newLineY) - leftOffset;
		RLine rline;
		boolean initialAllowOverflow;
		if (prevLine == null) {
			// Note: Assumes that prevLine == null means it's the first line.
			final RenderState rs = this.modelNode.getRenderState();
			initialAllowOverflow = rs == null ? false : rs.getWhiteSpace() == RenderState.WS_NOWRAP;
			// Text indentation only applies to the first line in the block.
			final int textIndent = rs == null ? 0 : rs.getTextIndent(this.availContentWidth);
			if (textIndent != 0) {
				newX += textIndent;
				// Line width also changes!
				newMaxWidth += leftOffset - newX;
			}
		} else {
			final int prevLineHeight = prevLine.getHeight();
			if (prevLineHeight > 0) {
				this.currentCollapsibleMargin = 0;
			}
			initialAllowOverflow = prevLine.isAllowOverflow();
			if (prevLine.x + prevLine.width > this.maxX) {
				this.maxX = prevLine.x + prevLine.width;
			}
		}
		rline = new RLine(startNode, this.container, newX, newLineY, newMaxWidth, 0, initialAllowOverflow);
		rline.setParent(this);
		List<Renderable> sr = this.seqRenderables;
		if (sr == null) {
			sr = new ArrayList<Renderable>();
			this.seqRenderables = sr;
		}
		sr.add(rline);
		this.currentLine = rline;
		return rline;
	}

	private void addLineAfterBlock(RBlock block, boolean informLineDone) {
		List<Renderable> sr = this.seqRenderables;
		if (sr == null) {
			sr = new ArrayList<Renderable>(1);
			this.seqRenderables = sr;
		}
		final RLine prevLine = this.currentLine;
		boolean initialAllowOverflow;
		if (prevLine != null) {
			initialAllowOverflow = prevLine.isAllowOverflow();
			if (informLineDone) {
				lineDone(prevLine);
			}
			if (prevLine.x + prevLine.width > this.maxX) {
				this.maxX = prevLine.x + prevLine.width;
			}
			// Check height only with floats.
		} else {
			initialAllowOverflow = false;
		}
		final ModelNode lineNode = block.getModelNode().getParentModelNode();
		final int newLineY = block.getY() + block.getHeight();
		checkY(newLineY);
		final int leftOffset = fetchLeftOffset(newLineY);
		final int newX = leftOffset;
		final int newMaxWidth = this.desiredWidth - fetchRightOffset(newLineY) - leftOffset;
		final RLine newLine = new RLine(lineNode, this.container, newX, newLineY, newMaxWidth, 0, initialAllowOverflow);
		newLine.setParent(this);
		sr.add(newLine);
		this.currentLine = newLine;
	}

	/**
	 * <p>addLineBreak.</p>
	 *
	 * @param startNode a {@link org.loboevolution.html.dom.domimpl.ModelNode} object.
	 * @param breakType a int.
	 */
	protected void addLineBreak(ModelNode startNode, int breakType) {
		RLine line = this.currentLine;
		if (line == null) {
			final Insets insets = this.paddingInsets;
			addLine(startNode, null, insets.top);
			line = this.currentLine;
		}
		if (line.getHeight() == 0) {
			final RenderState rs = startNode.getRenderState();
			final int fontHeight = rs.getFontMetrics().getHeight();
			line.setHeight(fontHeight);
		}
		line.setLineBreak(new LineBreak(breakType, startNode));
		int newLineY;
		final FloatingBounds fb = this.floatBounds;
		if (breakType == LineBreak.NONE || fb == null) {
			newLineY = line == null ? this.paddingInsets.top : line.y + line.height;
		} else {
			final int prevY = line == null ? this.paddingInsets.top : line.y + line.height;
			switch (breakType) {
				case LineBreak.BOTH:
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
		this.currentLine = addLine(startNode, line, newLineY);
	}

	private final void addPositionedRenderable(final BoundableRenderable renderable, final boolean verticalAlignable,
			final boolean isFloat, final boolean isFixed) {
		SortedSet<PositionedRenderable> others = this.positionedRenderables;
		if (others == null) {
			others = new TreeSet<>(new ZIndexComparator());
			this.positionedRenderables = others;
		}
		
		final PositionedRenderable posRes = new PositionedRenderable();
		posRes.setRenderable(renderable);
		posRes.setVerticalAlignable(verticalAlignable);
		posRes.setOrdinal(positionedOrdinal++);
		posRes.setFloat(isFloat);
		posRes.setFixed(isFixed);
		others.add(posRes);
		renderable.setParent(this);
		if (renderable instanceof RUIControl) {
			this.container.addComponent(((RUIControl) renderable).widget.getComponent());
		}
	}

	private void addRenderableToLine(Renderable renderable) {
		renderable.getModelNode().getRenderState();
		final RLine line = this.currentLine;
		final int liney = line.y;
		final boolean emptyLine = line.isEmpty();
		final FloatingBounds floatBounds = this.floatBounds;
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
				final int rightOffset = fetchRightOffset(liney);
				final int topLineX = this.desiredWidth - rightOffset;
				if (line.getX() + line.getWidth() > topLineX) {
					// Shift line down to clear area
					line.setY(cleary);
				}
			}
		} catch (final OverflowException oe) {
			final int nextY = emptyLine ? cleary : liney + line.height;
			addLine(renderable.getModelNode(), line, nextY);
			final Collection<Renderable> renderables = oe.getRenderables();
			for (Renderable r : renderables) {
				addRenderableToLine(r);
			}
		}
		if (renderable instanceof RUIControl) {
			this.container.addComponent(((RUIControl) renderable).widget.getComponent());
		}
	}

	/**
	 * Checks property 'float' and in some cases attribute 'align'.
	 *
	 * @param renderable a {@link org.loboevolution.html.renderer.RElement} object.
	 * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 * @param usesAlignAttribute a boolean.
	 */
	protected void addRenderableToLineCheckStyle(RElement renderable, HTMLElementImpl element, boolean usesAlignAttribute) {
		if (addElsewhereIfPositioned(renderable, element, usesAlignAttribute, true, true)) {
			return;
		}
		renderable.layout(this.availContentWidth, this.availContentHeight, this.sizeOnly);
		addRenderableToLine(renderable);
	}

	private void addWordToLine(RWord renderable) {
		// this.skipLineBreakBefore = false;
		final RLine line = this.currentLine;
		final int liney = line.y;
		final boolean emptyLine = line.isEmpty();
		final FloatingBounds floatBounds = this.floatBounds;
		int cleary;
		if (floatBounds != null) {
			cleary = floatBounds.getFirstClearY(liney);
		} else {
			cleary = liney + line.height;
		}
		try {
			line.addWord(renderable);
			// Check if the line goes into the float.
			if (floatBounds != null && cleary > liney) {
				final int rightOffset = fetchRightOffset(liney);
				final int topLineX = this.desiredWidth - rightOffset;
				if (line.getX() + line.getWidth() > topLineX) {
					// Shift line down to clear area
					line.setY(cleary);
				}
			}
		} catch (final OverflowException oe) {
			final int nextY = emptyLine ? cleary : liney + line.height;
			addLine(renderable.getModelNode(), line, nextY);
			final Collection<Renderable> renderables = oe.getRenderables();
			for (Renderable r : renderables) {
				addRenderableToLine(r);
			}
		}
	}

	/**
	 * Applies any horizonal aLignment. It may adjust height if necessary.
	 *
	 * @param canvasWidth   The new width of the viewport. It could be different to
	 *                      the previously calculated width.
	 * @param paddingInsets a {@link java.awt.Insets} object.
	 * @param alignXPercent a int.
	 */
	public void alignX(int alignXPercent, int canvasWidth, Insets paddingInsets) {
		final int prevMaxY = this.maxY;
		// Horizontal alignment
		if (alignXPercent > 0) {
			final List<Renderable> renderables = this.seqRenderables;
			if (renderables != null) {
				final Insets insets = this.paddingInsets;
				for (final Object r : renderables) {
					if (r instanceof BoundableRenderable) {
						final BoundableRenderable seqRenderable = (BoundableRenderable) r;
						final int y = seqRenderable.getY();
						final boolean isVisibleBlock = seqRenderable instanceof RBlock && ((RBlock) seqRenderable).isOverflowVisibleX();
						final int leftOffset = isVisibleBlock ? insets.left : fetchLeftOffset(y);
						final int rightOffset = isVisibleBlock ? insets.right : fetchRightOffset(y);
						final int actualAvailWidth = canvasWidth - leftOffset - rightOffset;
						final int difference = actualAvailWidth - seqRenderable.getWidth();
						if (difference > 0) {
							final int shift = difference * alignXPercent / 100;
							if (!isVisibleBlock) {
								final int newX = leftOffset + shift;
								seqRenderable.setX(newX);
							}
						}
					}
				}
			}
		}
		if (prevMaxY != this.maxY) {
			this.height += this.maxY - prevMaxY;
		}
	}

	/**
	 * Applies vertical alignment.
	 *
	 * @param canvasHeight a int.
	 * @param paddingInsets a {@link java.awt.Insets} object.
	 * @param alignYPercent a int.
	 */
	public void alignY(int alignYPercent, int canvasHeight, Insets paddingInsets) {
		final int prevMaxY = this.maxY;
		if (alignYPercent > 0) {
			final int availContentHeight = canvasHeight - paddingInsets.top - paddingInsets.bottom;
			final int usedHeight = this.maxY - paddingInsets.top;
			final int difference = availContentHeight - usedHeight;
			if (difference > 0) {
				final int shift = difference * alignYPercent / 100;
				final List<Renderable> rlist = this.seqRenderables;
				if (rlist != null) {
					// Try sequential renderables first.
					for (Object r : rlist) {
						if (r instanceof BoundableRenderable) {
							final BoundableRenderable line = (BoundableRenderable) r;
							final int newY = line.getY() + shift;
							line.setY(newY);
							if (newY + line.getHeight() > this.maxY) {
								this.maxY = newY + line.getHeight();
							}
						}
					}
				}

				// Now other renderables, but only those that can be
				// vertically aligned
				final Set<PositionedRenderable> others = this.positionedRenderables;
				if (others != null) {
					for (PositionedRenderable pr : others) {
						if (pr.isVerticalAlignable()) {
							final BoundableRenderable br = pr.getRenderable();
							final int newY = br.getY() + shift;
							br.setY(newY);
							if (newY + br.getHeight() > this.maxY) {
								this.maxY = newY + br.getHeight();
							}
						}
					}
				}
			}
		}
		if (prevMaxY != this.maxY) {
			this.height += this.maxY - prevMaxY;
		}
	}

	private final void checkY(int y) {
		if (this.yLimit != -1 && y > this.yLimit) {
			throw SEE;
		}
	}

	/**
	 * Gets offset from the left due to floats. It includes padding.
	 */
	private final int fetchLeftOffset(int newLineY) {
		final Insets paddingInsets = this.paddingInsets;
		final FloatingBounds floatBounds = this.floatBounds;
		if (floatBounds == null) {
			return paddingInsets.left;
		}
		final int left = floatBounds.getLeft(newLineY);
		if (left < paddingInsets.left) {
			return paddingInsets.left;
		}
		return left;
	}

	/**
	 * Gets offset from the right due to floats. It includes padding.
	 */
	private final int fetchRightOffset(int newLineY) {
		final Insets paddingInsets = this.paddingInsets;
		final FloatingBounds floatBounds = this.floatBounds;
		if (floatBounds == null) {
			return paddingInsets.right;
		}
		final int right = floatBounds.getRight(newLineY);
		if (right < paddingInsets.right) {
			return paddingInsets.right;
		}
		return right;
	}

	/**
	 * <p>Getter for the field availContentWidth.</p>
	 *
	 * @return a int.
	 */
	public int getAvailContentWidth() {
		return this.availContentWidth;
	}

	private int getEffectiveBlockHeight(BoundableRenderable block) {
		// Assumes block is the last one in the sequence.
		if (!(block instanceof RElement)) {
			return block.getHeight();
		}
		final RCollection parent = getParent();
		if (!(parent instanceof RElement)) {
			return block.getHeight();
		}
		final int blockMarginBottom = ((RElement) block).getMarginBottom();
		final int parentMarginBottom = ((RElement) parent).getCollapsibleMarginBottom();
		return block.getHeight() - Math.min(blockMarginBottom, parentMarginBottom);
	}

	/**
	 * <p>getExportableFloatingInfo.</p>
	 *
	 * @return a {@link org.loboevolution.info.FloatingInfo} object.
	 */
	public FloatingInfo getExportableFloatingInfo() {
		final List<ExportableFloat> ef = this.exportableFloats;
		if (ef == null) {
			return null;
		}
		final ExportableFloat[] floats = (ExportableFloat[]) ef.toArray(ExportableFloat.EMPTY_ARRAY);
		final FloatingInfo fInfo = new FloatingInfo();
		fInfo.setShiftX(0);
		fInfo.setShiftY(0);
		fInfo.setFloats(floats);
		return fInfo;
	}

	/**
	 * <p>getFirstBaselineOffset.</p>
	 *
	 * @return a int.
	 */
	public int getFirstBaselineOffset() {
		final List<Renderable> renderables = this.seqRenderables;
		if (renderables != null) {
			for (Object r : renderables) {
				if (r instanceof RLine) {
					final int blo = ((RLine) r).getBaselineOffset();
					if (blo != 0) {
						return blo;
					}
				} else if (r instanceof RBlock) {
					final RBlock block = (RBlock) r;
					if (block.getHeight() > 0) {
						final Insets insets = block.getInsetsMarginBorder(false, false);
						final Insets paddingInsets = this.paddingInsets;
						return block.getFirstBaselineOffset() + insets.top + (paddingInsets == null ? 0 : paddingInsets.top);
					}
				}
			}
		}
		return 0;
	}

	/**
	 * <p>getFirstLineHeight.</p>
	 *
	 * @return a int.
	 */
	public int getFirstLineHeight() {
		final List<Renderable> renderables = this.seqRenderables;
		if (renderables != null) {
			final int size = renderables.size();
			if (size == 0) {
				return 0;
			}
			for (int i = 0; i < size; i++) {
				final BoundableRenderable br = (BoundableRenderable) renderables.get(0);
				final int height = br.getHeight();
				if (height != 0) {
					return height;
				}
			}
		}
		// Not found!!
		return 1;
	}

	/** {@inheritDoc} */
	@Override
	public RenderableSpot getLowestRenderableSpot(int x, int y) {
		final BoundableRenderable br = this.getRenderable(new Point(x, y));
		if (br != null) {
			return br.getLowestRenderableSpot(x - br.getX(), y - br.getY());
		} else {
			return new RenderableSpot(this, x, y);
		}
	}

	private int getNewBlockY(BoundableRenderable newBlock, int expectedY) {
		// Assumes the previous block is not a line with height > 0.
		if (!(newBlock instanceof RElement)) {
			return expectedY;
		}
		final RElement block = (RElement) newBlock;
		final int ccm = this.currentCollapsibleMargin;
		final int topMargin = block.getMarginTop();
		if (topMargin == 0 && ccm == 0) {
			return expectedY;
		}
		return expectedY - Math.min(topMargin, ccm);
	}

	final RBlockViewport getParentViewport() {
		// Use originalParent, which for one, is not going to be null during layout.
		RCollection parent = getOriginalOrCurrentParent();
		while (parent != null && !(parent instanceof RBlockViewport)) {
			parent = parent.getOriginalOrCurrentParent();
		}
		return (RBlockViewport) parent;
	}

	/** {@inheritDoc} */
	@Override
	public BoundableRenderable getRenderable(int x, int y) {
		final Iterator<Renderable> i = this.getRenderables(x, y);
		return i == null ? null : i.hasNext() ? (BoundableRenderable) i.next() : null;
	}

	/**
	 * <p>getRenderable.</p>
	 *
	 * @param point a {@link java.awt.Point} object.
	 * @return a {@link org.loboevolution.html.renderer.BoundableRenderable} object.
	 */
	public BoundableRenderable getRenderable(java.awt.Point point) {
		return this.getRenderable(point.x, point.y);
	}

	/** {@inheritDoc} */
	@Override
	public Iterator<Renderable> getRenderables() {
		final SortedSet<PositionedRenderable> others = this.positionedRenderables;
		if (others == null || others.size() == 0) {
			final List<Renderable> sr = this.seqRenderables;
			return sr == null ? null : sr.iterator();
		} else {
			final List<Renderable> allRenderables = new ArrayList<Renderable>();
			this.populateZIndexGroupsIterator(others, this.seqRenderables, allRenderables);
			return allRenderables.iterator();
		}
	}

	/**
	 * <p>getRenderables.</p>
	 *
	 * @param pointx a int.
	 * @param pointy a int.
	 * @return a {@link java.util.Iterator} object.
	 */
	public Iterator<Renderable> getRenderables(int pointx, int pointy) {
		if (!SwingUtilities.isEventDispatchThread() && logger.isLoggable(Level.INFO)) {
			logger.warning("getRenderable(): Invoked outside GUI dispatch thread.");
		}
		List<Renderable> result = null;
		final SortedSet<PositionedRenderable> others = this.positionedRenderables;
		final int size = others == null ? 0 : others.size();
		final PositionedRenderable[] otherArray = size == 0 ? null
				: (PositionedRenderable[]) others.toArray(PositionedRenderable.EMPTY_ARRAY);
		// Try to find in other renderables with z-index >= 0 first.
		int index = 0;
		if (otherArray != null) {
			final int px = pointx;
			final int py = pointy;
			// Must go in reverse order
			for (index = size; --index >= 0;) {
				final PositionedRenderable pr = otherArray[index];
				final BoundableRenderable br = pr.getRenderable();
				if (br.getZIndex() < 0) {
					break;
				}
				if (br.contains(pointx, pointy)) {
					if (result == null) {
						result = new LinkedList<Renderable>();
					}
					result.add(br);
				}
			}
		}

		final List<Renderable> sr = this.seqRenderables;
		if (sr != null) {
			final Renderable[] array = sr.toArray(Renderable.EMPTY_ARRAY);
			final List<BoundableRenderable> found = MarkupUtilities.findRenderables(array, pointx, pointy, true);
			if (found != null) {
				if (result == null) {
					result = new LinkedList<>();
				}
				result.addAll(found);
			}
		}

		// Finally, try to find it in renderables with z-index < 0.
		if (otherArray != null) {
			for (; index >= 0; index--) {
				final PositionedRenderable pr = otherArray[index];
				final BoundableRenderable br = pr.getRenderable();
				if (br.contains(pointx, pointy)) {
					if (result == null) {
						result = new LinkedList<Renderable>();
					}
					result.add(br);
				}
			}
		}
		return result == null ? null : result.iterator();
	}

	/**
	 * <p>getRenderables.</p>
	 *
	 * @param point a {@link java.awt.Point} object.
	 * @return a {@link java.util.Iterator} object.
	 */
	public Iterator<Renderable> getRenderables(java.awt.Point point) {
		return this.getRenderables(point.x, point.y);
	}

	private Iterator<Renderable> getRenderables(Rectangle clipBounds) {
		final List<Renderable> sr = this.seqRenderables;
		Iterator<Renderable> baseIterator = null;
		if (sr != null) {
			final Renderable[] array = (Renderable[]) sr.toArray(Renderable.EMPTY_ARRAY);
			final Range range = MarkupUtilities.findRenderables(array, clipBounds, true);
			baseIterator = ArrayUtilities.iterator(array, range.offset, range.length);
		}
		final SortedSet<PositionedRenderable> others = this.positionedRenderables;
		if (others == null || others.size() == 0) {
			return baseIterator;
		} else {
			final ArrayList<PositionedRenderable> matches = new ArrayList<>();
			// ArrayList "matches" keeps the order from "others".
			for (PositionedRenderable pr : others) {
				if (pr.isFixed() || clipBounds.intersects(pr.getRenderable().getVisualBounds())) {
					matches.add(pr);
				}
			}
			if (matches.size() == 0) {
				return baseIterator;
			} else {
				final List<Renderable> destination = new ArrayList<>();
				populateZIndexGroups(matches, baseIterator, destination);
				return destination.iterator();
			}
		}
	}

	/**
	 * <p>getRenderablesArray.</p>
	 *
	 * @return an array of {@link org.loboevolution.html.renderer.Renderable} objects.
	 */
	public Renderable[] getRenderablesArray() {
		final SortedSet<PositionedRenderable> others = this.positionedRenderables;
		final int othersSize = others == null ? 0 : others.size();
		if (othersSize == 0) {
			final List<Renderable> sr = this.seqRenderables;
			return sr == null ? Renderable.EMPTY_ARRAY : (Renderable[]) sr.toArray(Renderable.EMPTY_ARRAY);
		} else {
			final List<Renderable> allRenderables = new ArrayList<Renderable>();
			this.populateZIndexGroupsIterator(others, this.seqRenderables, allRenderables);
			return (Renderable[]) allRenderables.toArray(Renderable.EMPTY_ARRAY);
		}
	}

	void importDelayedPair(final DelayedPair pair) {
		pair.positionPairChild();
		final BoundableRenderable r = pair.getChild();
		this.addPositionedRenderable(r, false, false, pair.isFixed());
	}

	private void importFloat(ExportableFloat ef, int shiftX, int shiftY) {
		final BoundableRenderable renderable = ef.element;
		final int newX = ef.origX + shiftX;
		final int newY = ef.origY + shiftY;
	    renderable.setOrigin(newX, newY);
		final FloatingBounds prevBounds = this.floatBounds;
		int offsetFromBorder;
		final boolean leftFloat = ef.leftFloat;
		if (leftFloat) {
			offsetFromBorder = newX + renderable.getWidth();
		} else {
			offsetFromBorder = this.desiredWidth - newX;
		}
		this.floatBounds = new FloatingViewportBounds(prevBounds, leftFloat, newY, offsetFromBorder, renderable.getHeight());
		if (isFloatLimit()) {
			addPositionedRenderable(renderable, true, true, false);
		} else {
		      addExportableFloat(renderable, leftFloat, newX, newY);
		}
	}

	private void importFloatingInfo(FloatingInfo floatingInfo, BoundableRenderable block) {
		final int shiftX = floatingInfo.getShiftX() + block.getX();
		final int shiftY = floatingInfo.getShiftY() + block.getY();
		final ExportableFloat[] floats = (ExportableFloat[])floatingInfo.getFloats();
		for (final ExportableFloat ef : floats) {
			importFloat(ef, shiftX, shiftY);
		}
	}

	private int initCollapsibleMargin() {
		final Object parent = this.parent;
		if (!(parent instanceof RBlock)) {
			return 0;
		}
		final RBlock parentBlock = (RBlock) parent;
		return parentBlock.getCollapsibleMarginTop();
	}

	/** {@inheritDoc} */
	@Override
	public void invalidateLayoutLocal() {
		// Workaround for fact that RBlockViewport does not
		// get validated or invalidated.
		this.layoutUpTreeCanBeInvalidated = true;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isContainedByNode() {
		return false;
	}

	private boolean isFloatLimit() {
		Boolean fl = this.isFloatLimit;
		if (fl == null) {
			fl = isFloatLimitImpl();
			this.isFloatLimit = fl;
		}
		return fl;
	}

	private Boolean isFloatLimitImpl() {
		final Object parent = getOriginalOrCurrentParent();
		if (!(parent instanceof RBlock)) {
			return Boolean.TRUE;
		}
		final RBlock blockParent = (RBlock) parent;
		final Object grandParent = blockParent.getOriginalOrCurrentParent();
		if (!(grandParent instanceof RBlockViewport)) {
			// Could be contained in a table, or it could
			// be a list item, for example.
			return Boolean.TRUE;
		}
		final ModelNode node = this.modelNode;
		if (!(node instanceof HTMLElementImpl)) {
			// Can only be a document here.
			return Boolean.TRUE;
		}
		final HTMLElementImpl element = (HTMLElementImpl) node;
		final int position = getPosition(element);
		if (position == RenderState.POSITION_ABSOLUTE || position == RenderState.POSITION_FIXED) {
			return Boolean.TRUE;
		}
		element.getCurrentStyle();
		final RenderState rs = element.getRenderState();
		final int floatValue = rs == null ? RenderState.FLOAT_NONE : rs.getFloat();
		if (floatValue != RenderState.FLOAT_NONE) {
			return Boolean.TRUE;
		}
		final int overflowX = rs == null ? RenderState.OVERFLOW_NONE : rs.getOverflowX();
		final int overflowY = rs == null ? RenderState.OVERFLOW_NONE : rs.getOverflowY();
		if ((overflowX == RenderState.OVERFLOW_AUTO) || (overflowX == RenderState.OVERFLOW_SCROLL)
				|| (overflowY == RenderState.OVERFLOW_AUTO) || (overflowY == RenderState.OVERFLOW_SCROLL)) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	/**
	 * Builds the layout/renderer tree from scratch. Note: Returned dimension needs
	 * to be actual size needed for rendered content, not the available container
	 * size. This is relied upon by table layout.
	 *
	 * @param yLimit If other than -1, layout will throw
	 *               SizeExceededException in the event that the layout
	 *               goes beyond this y-coordinate point.
	 * @param desiredWidth a int.
	 * @param desiredHeight a int.
	 * @param paddingInsets a {@link java.awt.Insets} object.
	 * @param floatBounds a {@link org.loboevolution.html.renderer.FloatingBounds} object.
	 * @param sizeOnly a boolean.
	 */
	public void layout(int desiredWidth, int desiredHeight, Insets paddingInsets, int yLimit,
			FloatingBounds floatBounds, boolean sizeOnly) {
		// Expected in GUI thread. It's possible it may be invoked during pack()
		// outside of the GUI thread.
		if (!SwingUtilities.isEventDispatchThread() && logger.isLoggable(Level.INFO)) {
			logger.warning("layout(): Invoked outside GUI dispatch thread.");
		}
		final RenderableContainer container = this.container;
		this.paddingInsets = paddingInsets;
		this.yLimit = yLimit;
		this.desiredHeight = desiredHeight;
		this.desiredWidth = desiredWidth;
		this.floatBounds = floatBounds;
		this.isFloatLimit = null;
		this.pendingFloats = null;
		this.sizeOnly = sizeOnly;
		this.lastSeqBlock = null;
		this.currentCollapsibleMargin = initCollapsibleMargin();

		// maxX and maxY should not be reset by layoutPass.
		this.maxX = paddingInsets.left;
		this.maxY = paddingInsets.top;

		int availw = desiredWidth - paddingInsets.left - paddingInsets.right;
		if (availw < 0) {
			availw = 0;
		}
		int availh = desiredHeight - paddingInsets.top - paddingInsets.bottom;
		if (availh == 0) {
			availh = 0;
		}
		this.availContentHeight = availh;
		this.availContentWidth = availw;

		// New floating algorithm.
		layoutPass((NodeImpl) this.modelNode);

		final Collection<?> delayedPairs = container.getDelayedPairs();
		if (delayedPairs != null && delayedPairs.size() > 0) {
			// Add positioned renderables that belong here
			for (Object delayedPair : delayedPairs) {
				final DelayedPair pair = (DelayedPair) delayedPair;
				if (pair.getContainingBlock() == container) {
					importDelayedPair(pair);
				}
			}
		}

		// Compute maxY according to last block.
		int maxY = this.maxY;
		int maxYWholeBlock = maxY;
		final BoundableRenderable lastSeqBlock = this.lastSeqBlock;
		if (lastSeqBlock != null) {
			final int effBlockHeight = getEffectiveBlockHeight(lastSeqBlock);
			if (lastSeqBlock.getY() + effBlockHeight > maxY) {
				this.maxY = maxY = lastSeqBlock.getY() + effBlockHeight;
				maxYWholeBlock = lastSeqBlock.getY() + lastSeqBlock.getHeight();
			}
		}

		// See if line should increase maxY. Empty
		// lines shouldn't, except in cases where
		// there was a BR.
		final RLine lastLine = this.currentLine;
		final Rectangle lastBounds = lastLine.getBounds();
		if (lastBounds.height > 0 || lastBounds.y > maxYWholeBlock) {
			final int lastTopX = lastBounds.x + lastBounds.width;
			if (lastTopX > this.maxX) {
				this.maxX = lastTopX;
			}
			final int lastTopY = lastBounds.y + lastBounds.height;
			if (lastTopY > maxY) {
				this.maxY = maxY = lastTopY;
			}
		}

		// Check positioned renderables for maxX and maxY
		final SortedSet<PositionedRenderable> posRenderables = this.positionedRenderables;
		if (posRenderables != null) {
			final boolean isFloatLimit = isFloatLimit();
			for (PositionedRenderable pr : posRenderables) {
				final BoundableRenderable br = pr.getRenderable();
				if (br.getX() + br.getWidth() > this.maxX) {
					this.maxX = br.getX() + br.getWidth();
				}
				if (isFloatLimit || !pr.isFloat()) {
					if (br.getY() + br.getHeight() > maxY) {
						this.maxY = maxY = br.getY() + br.getHeight();
					}
				}
			}
		}

		this.width = paddingInsets.right + this.maxX;
		this.height = paddingInsets.bottom + maxY;
	}

	private void layoutChildren(NodeImpl node) {
		final NodeImpl[] childrenArray = node.getChildrenArray();
		if (childrenArray != null) {
			for (NodeImpl child : childrenArray) {
				final short nodeType = child.getNodeType();
				switch (nodeType) {
				case Node.TEXT_NODE:
					layoutText(child);
					break;
				case Node.ELEMENT_NODE:
					this.currentLine.addStyleChanger(new RStyleChanger(child));
					final String nodeName = child.getNodeName().toUpperCase();
					MarkupLayout ml = (MarkupLayout) RLayout.elementLayout.get(HTMLTag.get(nodeName));
					if (ml == null) {
						ml = miscLayout;
					}
					ml.layoutMarkup(this, (HTMLElementImpl) child);
					this.currentLine.addStyleChanger(new RStyleChanger(node));
					break;
				case Node.DOCUMENT_FRAGMENT_NODE:
					final DocumentFragmentImpl fragment = (DocumentFragmentImpl) child;
					for (final NodeImpl fragChild : fragment.getChildrenArray()) {
						layoutChildren(fragChild);
					}
					break;
				case Node.COMMENT_NODE:
				case Node.PROCESSING_INSTRUCTION_NODE:
				default:
					break;
				}
			}
		}
	}

	private final void layoutFloat(BoundableRenderable renderable, boolean layout, boolean leftFloat) {
		renderable.setOriginalParent(this);
		if (layout) {
			final int availWidth = this.availContentWidth;
			final int availHeight = this.availContentHeight;
			if (renderable instanceof RBlock) {
				final RBlock block = (RBlock) renderable;
				// Float boxes don't inherit float bounds?
				block.layout(availWidth, availHeight, false, false, null, this.sizeOnly);
			} else if (renderable instanceof RElement) {
				final RElement e = (RElement) renderable;
				e.layout(availWidth, availHeight, this.sizeOnly);
			}
		}
		final RFloatInfo floatInfo = new RFloatInfo(renderable.getModelNode(), renderable, leftFloat);
		this.currentLine.simplyAdd(floatInfo);
		scheduleFloat(floatInfo);
	}

	/**
	 * <p>layoutHr.</p>
	 *
	 * @param markupElement a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	protected final void layoutHr(HTMLElementImpl markupElement) {
		RElement renderable = (RElement) markupElement.getUINode();
		if (renderable == null) {
			renderable = setupNewUIControl(this.container, markupElement, new HrControl(markupElement));
		}
		renderable.layout(this.availContentWidth, this.availContentHeight, this.sizeOnly);
		addAlignableAsBlock(markupElement, renderable);
	}

	/**
	 * <p>layoutList.</p>
	 *
	 * @param markupElement a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	protected final void layoutList(HTMLElementImpl markupElement) {
		RList renderable = (RList) markupElement.getUINode();
		if (renderable == null) {
			renderable = new RList(markupElement, this.listNesting, this.userAgentContext, this.rendererContext, this.frameContext, this.container);
			markupElement.setUINode(renderable);
		}
		renderable.setOriginalParent(this);
		positionRBlock(markupElement, renderable);
	}

	/**
	 * <p>layoutListItem.</p>
	 *
	 * @param markupElement a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	protected final void layoutListItem(HTMLElementImpl markupElement) {
		RListItem renderable = (RListItem) markupElement.getUINode();
		if (renderable == null) {
			renderable = new RListItem(markupElement, this.listNesting, this.userAgentContext, this.rendererContext, this.frameContext, this.container);
			markupElement.setUINode(renderable);
		}
		renderable.setOriginalParent(this);
		positionRBlock(markupElement, renderable);
	}

	/**
	 * <p>layoutMarkup.</p>
	 *
	 * @param node a {@link org.loboevolution.html.dom.domimpl.NodeImpl} object.
	 */
	protected void layoutMarkup(NodeImpl node) {
		// This is the "inline" layout of an element.
		// The difference with layoutChildren is that this
		// method checks for padding and margin insets.
		final RenderState rs = node.getRenderState();
		Insets marginInsets = null;
		Insets paddingInsets = null;
		if (rs != null) {
			final HtmlInsets mi = rs.getMarginInsets();
			marginInsets = mi == null ? null : mi.getSimpleAWTInsets(this.availContentWidth, this.availContentHeight);
			final HtmlInsets pi = rs.getPaddingInsets();
			paddingInsets = pi == null ? null : pi.getSimpleAWTInsets(this.availContentWidth, this.availContentHeight);
		}
		int leftSpacing = 0;
		int rightSpacing = 0;
		if (marginInsets != null) {
			leftSpacing += marginInsets.left;
			rightSpacing += marginInsets.right;
		}
		if (paddingInsets != null) {
			leftSpacing += paddingInsets.left;
			rightSpacing += paddingInsets.right;
		}
		if (leftSpacing > 0) {
			final RLine line = this.currentLine;
			line.addSpacing(new RSpacing(node, this.container, leftSpacing, line.height));
		}
		layoutChildren(node);
		if (rightSpacing > 0) {
			final RLine line = this.currentLine;
			line.addSpacing(new RSpacing(node, this.container, rightSpacing, line.height));
		}
	}

	private void layoutPass(NodeImpl rootNode) {
		final RenderableContainer container = this.container;
		container.clearDelayedPairs();
		this.positionedOrdinal = 0;

		// Remove sequential renderables...
		this.seqRenderables = null;

		// Remove other renderables...
		this.positionedRenderables = null;

		// Remove exporatable floats...
		this.exportableFloats = null;

		// Call addLine after setting margins
		this.currentLine = addLine(rootNode, null, this.paddingInsets.top);

		// Start laying out...
		// The parent is expected to have set the RenderState already.
		layoutChildren(rootNode);

		// This adds last-line floats.
		lineDone(this.currentLine);
	}

	/**
	 * <p>layoutRBlock.</p>
	 *
	 * @param markupElement a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	protected final void layoutRBlock(HTMLElementImpl markupElement) {
		final UINode uiNode = markupElement.getUINode();
		RBlock renderable = null;
		if (uiNode instanceof RBlock) {
			renderable = (RBlock) markupElement.getUINode();
		}
		    
		if (renderable == null) {
			renderable = new RBlock(markupElement, this.listNesting, this.userAgentContext, this.rendererContext, this.frameContext, this.container);
			markupElement.setUINode(renderable);
		}
		renderable.setOriginalParent(this);
		positionRBlock(markupElement, renderable);
	}

	/**
	 * <p>layoutRTable.</p>
	 *
	 * @param markupElement a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	protected final void layoutRTable(HTMLElementImpl markupElement) {
		RElement renderable = (RElement) markupElement.getUINode();
		if (renderable == null) {
			renderable = new RTable(markupElement, this.userAgentContext, this.rendererContext, this.frameContext, this.container);
			markupElement.setUINode(renderable);
		}
		renderable.setOriginalParent(this);
		positionRElement(markupElement, renderable, markupElement instanceof HTMLTableElementImpl, true, true);
	}
	
	/**
	 * <p>layoutRInlineBlock.</p>
	 *
	 * @param markupElement a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public void layoutRInlineBlock(final HTMLElementImpl markupElement) {
		final UINode uINode = markupElement.getUINode();
		RInlineBlock inlineBlock = null;
		if (uINode instanceof RInlineBlock) {
			inlineBlock = (RInlineBlock) uINode;
		} else {
			final RInlineBlock newInlineBlock = new RInlineBlock(container, markupElement, userAgentContext, rendererContext, frameContext);
			markupElement.setUINode(newInlineBlock);
			inlineBlock = newInlineBlock;
		}
		inlineBlock.doLayout(availContentWidth, availContentHeight, sizeOnly);
		addRenderableToLine(inlineBlock);
	}
	
	/**
	 * <p>layoutRFlex.</p>
	 *
	 * @param markupElement a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public void layoutRFlex(final HTMLElementImpl markupElement) {
		final RenderState renderState = markupElement.getRenderState();
		RFlex flex = new RFlex(renderState);
		if(flex.isFlexTable()) {
			layoutRTable(markupElement);
		} else {
			flex.flexAlign(markupElement);
			layoutRBlock(markupElement);	
		}
	}

	/**
	 * <p>layoutChildFlex.</p>
	 *
	 * @param markupElement a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 */
	public void layoutChildFlex(final HTMLElementImpl markupElement) {
		final RenderState renderState = markupElement.getRenderState();
		RFlexChild flex = new RFlexChild(renderState);
		
		if (flex.isInlineBlock()) {
			layoutRInlineBlock(markupElement);
		} else {
			flex.flexAlign(markupElement);
			layoutRBlock(markupElement);
		}
	}

	private void layoutText(NodeImpl textNode) {
		final RenderState renderState = textNode.getRenderState();
		if (renderState == null) {
			throw new IllegalStateException(
					"RenderState is null for node " + textNode + " with parent " + textNode.getParentNode());
		}
		final FontMetrics fm = renderState.getFontMetrics();
		final int descent = fm.getDescent();
		final int ascentPlusLeading = fm.getAscent() + fm.getLeading();
		final int wordHeight = fm.getHeight();
		final int blankWidth = fm.charWidth(' ');
		final int whiteSpace = this.overrideNoWrap ? RenderState.WS_NOWRAP : renderState.getWhiteSpace();
		final int textTransform = renderState.getTextTransform();
		final String text = textNode.getNodeValue();
		if (whiteSpace != RenderState.WS_PRE) {
			final boolean prevAllowOverflow = this.currentLine.isAllowOverflow();
			final boolean allowOverflow = whiteSpace == RenderState.WS_NOWRAP;
			this.currentLine.setAllowOverflow(allowOverflow);
			try {
				final int length = text.length();
				boolean firstWord = true;
				final StringBuilder word = new StringBuilder(12);
				for (int i = 0; i < length; i++) {
					char ch = text.charAt(i);
					if (Character.isWhitespace(ch)) {
						if (firstWord) {
							firstWord = false;
						}
						final int wlen = word.length();
						if (wlen > 0) {
							final RWord rword = new RWord(textNode, word.toString(), this.container, fm, descent,
									ascentPlusLeading, wordHeight, textTransform);
							addWordToLine(rword);
							word.delete(0, wlen);
						}
						final RLine line = this.currentLine;
						if (line.width > 0) {
							final RBlank rblank = new RBlank(textNode, fm, this.container, ascentPlusLeading,
									blankWidth, wordHeight);
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
					final RWord rword = new RWord(textNode, word.toString(), this.container, fm, descent,
							ascentPlusLeading, wordHeight, textTransform);
					addWordToLine(rword);
				}
			} finally {
				this.currentLine.setAllowOverflow(prevAllowOverflow);
			}
		} else {
			final int length = text.length();
			boolean lastCharSlashR = false;
			final StringBuilder line = new StringBuilder();
			for (int i = 0; i < length; i++) {
				final char ch = text.charAt(i);
				switch (ch) {
				case '\r':
					lastCharSlashR = true;
					break;
				case '\n':
                    final RWord rword = new RWord(textNode, line.toString(), container, fm, descent, ascentPlusLeading, wordHeight, textTransform);
                    this.addWordToLine(rword);
                    line.delete(0, line.length());
					final RLine prevLine = this.currentLine;
					prevLine.setLineBreak(new LineBreak(LineBreak.NONE, textNode));
					addLine(textNode, prevLine, prevLine.y + prevLine.height);
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
				final RWord rword = new RWord(textNode, line.toString(), this.container, fm, descent, ascentPlusLeading,
						wordHeight, textTransform);
				addWordToLine(rword);
			}
		}
	}

	private void lineDone(RLine line) {
		final int yAfterLine = line == null ? this.paddingInsets.top : line.y + line.height;
		final Collection<RFloatInfo> pfs = this.pendingFloats;
		if (pfs != null) {
			this.pendingFloats = null;
			for (RFloatInfo pf : pfs) {
				placeFloat(pf.getRenderable(), yAfterLine, pf.isLeftFloat());
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean onDoubleClick(MouseEvent event, int x, int y) {
		final Iterator<Renderable> i = this.getRenderables(new Point(x, y));
		if (i != null) {
			while (i.hasNext()) {
				final BoundableRenderable br = (BoundableRenderable) i.next();
				if (br != null) {
					final Rectangle bounds = br.getVisualBounds();
					if (!br.onDoubleClick(event, x - bounds.x, y - bounds.y)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.rendered.BoundableRenderable#onMouseClick(java.awt.event.
	 * MouseEvent, int, int)
	 */
	/** {@inheritDoc} */
	@Override
	public boolean onMouseClick(MouseEvent event, int x, int y) {
		final Iterator<Renderable> i = this.getRenderables(new Point(x, y));
		if (i != null) {
			while (i.hasNext()) {
				final BoundableRenderable br = (BoundableRenderable) i.next();
				if (br != null) {
					final Rectangle bounds = br.getVisualBounds();
					if (!br.onMouseClick(event, x - bounds.x, y - bounds.y)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.rendered.BoundableRenderable#onMouseDisarmed(java.awt.event.
	 * MouseEvent)
	 */
	/** {@inheritDoc} */
	@Override
	public boolean onMouseDisarmed(MouseEvent event) {
		final BoundableRenderable br = this.armedRenderable;
		if (br != null) {
			try {
				return br.onMouseDisarmed(event);
			} finally {
				this.armedRenderable = null;
			}
		} else {
			return true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.rendered.BoundableRenderable#onMousePressed(java.awt.event.
	 * MouseEvent, int, int)
	 */
	/** {@inheritDoc} */
	@Override
	public boolean onMousePressed(MouseEvent event, int x, int y) {
		final Iterator<Renderable> i = this.getRenderables(new Point(x, y));
		if (i != null) {
			while (i.hasNext()) {
				final BoundableRenderable br = (BoundableRenderable) i.next();
				if (br != null) {
					final Rectangle bounds = br.getVisualBounds();
					if (!br.onMousePressed(event, x - bounds.x, y - bounds.y)) {
						this.armedRenderable = br;
						return false;
					}
				}
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.rendered.BoundableRenderable#onMouseReleased(java.awt.event.
	 * MouseEvent, int, int)
	 */
	/** {@inheritDoc} */
	@Override
	public boolean onMouseReleased(MouseEvent event, int x, int y) {
		final Iterator<Renderable> i = this.getRenderables(new Point(x, y));
		if (i != null) {
			while (i.hasNext()) {
				final BoundableRenderable br = (BoundableRenderable) i.next();
				if (br != null) {
					final Rectangle bounds = br.getVisualBounds();
					if (!br.onMouseReleased(event, x - bounds.x, y - bounds.y)) {
						final BoundableRenderable oldArmedRenderable = this.armedRenderable;
						if (oldArmedRenderable != null && br != oldArmedRenderable) {
							oldArmedRenderable.onMouseDisarmed(event);
							this.armedRenderable = null;
						}
						return false;
					}
				}
			}
		}
		final BoundableRenderable oldArmedRenderable = this.armedRenderable;
		if (oldArmedRenderable != null) {
			oldArmedRenderable.onMouseDisarmed(event);
			this.armedRenderable = null;
		}
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics gIn) {
		final boolean translationRequired = (x | y) != 0;
		final Graphics g = translationRequired ? gIn.create() : gIn;
		if (translationRequired) {
			g.translate(x, y);
		}
		try {
			final Rectangle clipBounds = g.getClipBounds();
			final Iterator<Renderable> i = this.getRenderables(clipBounds);
			if (i != null) {
				while (i.hasNext()) {
					final Renderable robj = i.next();
					if (robj instanceof BoundableRenderable) {
						final BoundableRenderable renderable = (BoundableRenderable) robj;
						renderable.paintTranslated(g);
					} else {
						final Graphics selectedG = robj.isFixed() ? gIn : g;
						if (getModelNode() instanceof HTMLDocument) {
							final Renderable htmlRenderable = robj.findHtmlRenderable(this);
							if (htmlRenderable != null) {
								final Rectangle htmlBounds = ((RBlock) htmlRenderable).getClipBoundsWithoutInsets();
								if (htmlBounds != null) {
									final Graphics clippedG = selectedG.create(0, 0, htmlBounds.width,
											htmlBounds.height);
									try {
										robj.paint(clippedG);
									} finally {
										clippedG.dispose();
									}
								} else {
									robj.paint(selectedG);
								}
							} else {
								robj.paint(selectedG);
							}
						} else {
							robj.paint(selectedG);
						}
					}
				}
			}
		} finally {
			if (translationRequired) {
				g.dispose();
			}
		}
	}

	/**
	 * 
	 * @param element
	 * @param y         The desired top position of the float element.
	 * @param leftFloat -1 (left) or +1 (right)
	 */
	private void placeFloat(BoundableRenderable element, int y, boolean leftFloat) {
		final Insets insets = this.paddingInsets;
		int boxY = y;
		int boxWidth = element.getWidth();
		int boxHeight = element.getHeight();
		final int desiredWidth = this.desiredWidth;
		int boxX;
		for (;;) {
			final int leftOffset = fetchLeftOffset(boxY);
			final int rightOffset = fetchRightOffset(boxY);
			boxX = leftFloat ? leftOffset : desiredWidth - rightOffset - boxWidth;
			if (leftOffset == insets.left && rightOffset == insets.right) {
				// Probably typical scenario. If it's overflowing to the left,
				// we need to correct.
				if (!leftFloat && boxX < leftOffset) {
					boxX = leftOffset;
				}
				break;
			}
            if ((desiredWidth <= 0) || boxWidth <= (desiredWidth - rightOffset - leftOffset)) {
				break;
			}
			// At this point the float doesn't fit at the current Y position.
			if (element instanceof RBlock) {
				// Try shrinking it.
				final RBlock relement = (RBlock) element;
				if (!relement.hasDeclaredWidth()) {
					final int availableBoxWidth = desiredWidth - rightOffset - leftOffset;
					relement.layout(availableBoxWidth, this.availContentHeight, this.sizeOnly);
					if (relement.getWidth() < boxWidth) {
						if (relement.getWidth() > desiredWidth - rightOffset - leftOffset) {
							// Didn't work out. Put it back the way it was.
							relement.layout(this.availContentWidth, this.availContentHeight, this.sizeOnly);
						} else {
							// Retry
							boxWidth = relement.getWidth();
							boxHeight = relement.getHeight();
							continue;
						}
					}
				}
			}
			final FloatingBounds fb = this.floatBounds;
			final int newY = fb == null ? boxY + boxHeight : fb.getFirstClearY(boxY);
			if (newY == boxY) {
				// Possible if prior box has height zero?
				break;
			}
			boxY = newY;
		}
		// Position element
		element.setOrigin(boxX, boxY);
		// Update float bounds accordingly
		final int offsetFromBorder = leftFloat ? boxX + boxWidth : desiredWidth - boxX;
		this.floatBounds = new FloatingViewportBounds(this.floatBounds, leftFloat, boxY, offsetFromBorder, boxHeight);
		// Add element to collection
		final boolean isFloatLimit = isFloatLimit();
		if (isFloatLimit) {
			addPositionedRenderable(element, true, true, false);
		} else {
			addExportableFloat(element, leftFloat, boxX, boxY);
		}
		// Adjust maxX based on float.
		if (boxX + boxWidth > this.maxX) {
			this.maxX = boxX + boxWidth;
		}
		// Adjust maxY based on float, but only if this viewport is the float limit.
		if (isFloatLimit()) {
			if (boxY + boxHeight > this.maxY) {
				this.maxY = boxY + boxHeight;
			}
		}
	}

	/**
	 *
	 * @param others         An ordered collection.
	 * @param seqRenderables
	 * @param destination
	 */
	private void populateZIndexGroupsIterator(final Collection<PositionedRenderable> others,
			final Collection<Renderable> seqRenderables, final List<Renderable> destination) {
		populateZIndexGroups(others, seqRenderables == null ? null : seqRenderables.iterator(), destination);
	}

	/**
	 *
	 * @param others                 An ordered collection.
	 * @param seqRenderablesIterator
	 * @param destination
	 */
	private static void populateZIndexGroups(final Collection<PositionedRenderable> others,
			final Iterator<Renderable> seqRenderablesIterator, final List<Renderable> destination) {
		// First, others with z-index < 0
		final Iterator<PositionedRenderable> i1 = others.iterator();
		Renderable pending = null;
		while (i1.hasNext()) {
			final PositionedRenderable pr = i1.next();
			final BoundableRenderable r = pr.getRenderable();
			if (r.getZIndex() >= 0) {
		        pending = pr;
				break;
			}
			destination.add(pr);
		}

		// Second, sequential renderables
		final Iterator<? extends Renderable> i2 = seqRenderablesIterator;
		if (i2 != null) {
			while (i2.hasNext()) {
				destination.add(i2.next());
			}
		}

		// Third, other renderables with z-index >= 0.
		if (pending != null) {
			destination.add(pending);
			while (i1.hasNext()) {
				final PositionedRenderable pr = i1.next();
				destination.add(pr);
			}
		}
	}

	private final void positionRBlock(HTMLElementImpl markupElement, RBlock renderable) {
		
		RenderState rs = renderable.getModelNode().getRenderState();
		int clear = rs.getClear();
		if (clear != LineBreak.NONE) {
			addLineBreak(renderable.getModelNode(), clear);
		}
		
		if (!addElsewhereIfPositioned(renderable, markupElement, false, true, false)) {
			final int availContentHeight = this.availContentHeight;
			final RLine line = this.currentLine;
			// Inform line done before layout so floats are considered.
			lineDone(line);
			final Insets paddingInsets = this.paddingInsets;
			final int newLineY = line == null ? paddingInsets.top : line.y + line.height;
			final int availContentWidth = this.availContentWidth;
			final int expectedWidth = availContentWidth;
			final int blockShiftRight = paddingInsets.right;
			final int newX = paddingInsets.left;
			final int newY = newLineY;
			final FloatingBounds floatBounds = this.floatBounds;
			final FloatingBoundsSource floatBoundsSource = floatBounds == null ? null
					: new ParentFloatingBoundsSource(blockShiftRight, expectedWidth, newX, newY, floatBounds);
			renderable.layout(availContentWidth, availContentHeight, true, false, floatBoundsSource, this.sizeOnly);
			this.addAsSeqBlock(renderable, false, false, false, false);
			// Calculate new floating bounds after block has been put in place.
			final FloatingInfo floatingInfo = renderable.getExportableFloatingInfo();
			if (floatingInfo != null) {
				importFloatingInfo(floatingInfo, renderable);
			}
			// Now add line, after float is set.
			addLineAfterBlock(renderable, false);
		}
	}

	/**
	 * <p>positionRElement.</p>
	 *
	 * @param markupElement a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 * @param renderable a {@link org.loboevolution.html.renderer.RElement} object.
	 * @param usesAlignAttribute a boolean.
	 * @param obeysFloats a boolean.
	 * @param alignCenterAttribute a boolean.
	 */
	protected final void positionRElement(HTMLElementImpl markupElement, RElement renderable, boolean usesAlignAttribute,
			boolean obeysFloats, boolean alignCenterAttribute) {
		if (!addElsewhereIfPositioned(renderable, markupElement, usesAlignAttribute, true, true)) {
			int availContentWidth = this.availContentWidth;
			final int availContentHeight = this.availContentHeight;
			final RLine line = this.currentLine;
			// Inform line done before layout so floats are considered.
			lineDone(line);
			if (obeysFloats) {
				final int newLineY = line == null ? this.paddingInsets.top : line.y + line.height;
				final int leftOffset = fetchLeftOffset(newLineY);
				final int rightOffset = fetchRightOffset(newLineY);
				availContentWidth = this.desiredWidth - leftOffset - rightOffset;
			}
			renderable.layout(availContentWidth, availContentHeight, this.sizeOnly);
			boolean centerBlock = false;
			if (alignCenterAttribute) {
				final String align = markupElement.getAttribute("align");
				centerBlock = align != null && align.equalsIgnoreCase("center");
			}
			addAsSeqBlock(renderable, obeysFloats, false, true, centerBlock);
		}
	}

	/**
	 * @param absolute if true, then position is absolute, else fixed
	 */
	private void scheduleAbsDelayedPair(final BoundableRenderable renderable, final AbstractCSSProperties style,
			final RenderState rs, final RLine line, final boolean absolute) {
		final RenderableContainer containingBlock = absolute ? getPositionedAncestor(this.container)  : getRootContainer(container);
		final DelayedPair pair = new DelayedPair();
		pair.setImmediateContainingBlock(container);
		pair.setContainingBlock(containingBlock);
		pair.setChild(renderable);
		pair.setLeft(style.getLeft());
		pair.setRight(style.getRight());
		pair.setTop(style.getTop());
		pair.setBottom(style.getBottom());
		pair.setWidth(style.getWidth());
		pair.setHeight(style.getHeight());
		pair.setRs(rs);
		pair.setInitY(currentLine.getY() + currentLine.getHeight());
		pair.setInitX(currentLine.getX());
		pair.setFixed(!absolute);
		this.container.addDelayedPair(pair);
	}

	private static RenderableContainer getRootContainer(final RenderableContainer container) {
		RenderableContainer c = container.getParentContainer();
		RenderableContainer prevC = container;
		for (;;) {
			if (c instanceof RenderableContainer) {
				final RenderableContainer newContainer = c.getParentContainer();
				if (newContainer == null) {
					break;
				}
				prevC = c;
				c = newContainer;
			} else {
				break;
			}
		}
		return prevC;
	}

	private static RenderableContainer getPositionedAncestor(RenderableContainer containingBlock) {
		for (;;) {
			if (containingBlock instanceof Renderable) {
				final ModelNode node = ((Renderable) containingBlock).getModelNode();
				if (node instanceof HTMLElementImpl) {
					final HTMLElementImpl element = (HTMLElementImpl) node;
					final int position = getPosition(element);
					if (position != RenderState.POSITION_STATIC) {
						break;
					}
					final RenderableContainer newContainer = containingBlock.getParentContainer();
					if (newContainer == null) {
						break;
					}
					containingBlock = newContainer;
				} else {
					break;
				}
			} else {
				break;
			}
		}
		return containingBlock;
	}

	private void scheduleFloat(RFloatInfo floatInfo) {
		final RLine line = this.currentLine;
		if (line == null) {
			final int y = line == null ? this.paddingInsets.top : line.getY();
			placeFloat(floatInfo.getRenderable(), y, floatInfo.isLeftFloat());
		} else if (line.getWidth() == 0) {
			final int y = line.getY();
			placeFloat(floatInfo.getRenderable(), y, floatInfo.isLeftFloat());
			final int leftOffset = fetchLeftOffset(y);
			final int rightOffset = fetchRightOffset(y);
			line.changeLimits(leftOffset, this.desiredWidth - leftOffset - rightOffset);
		} else {
			// These pending floats are positioned when
			// lineDone() is called.
			Collection<RFloatInfo> c = this.pendingFloats;
			if (c == null) {
				c = new LinkedList<RFloatInfo>();
				this.pendingFloats = c;
			}
			c.add(floatInfo);
		}
	}

	private RElement setupNewUIControl(RenderableContainer container, HTMLElementImpl element, UIControl control) {
		final RElement renderable = new RUIControl(element, control, container, this.frameContext,
				this.userAgentContext);
		element.setUINode(renderable);
		return renderable;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "RBlockViewport[node=" + this.modelNode + "]";
	}

	/** {@inheritDoc} */
	@Override
	public Rectangle getClipBounds() {
		return ((RBlock) container).getClipBounds();
	}

	/** {@inheritDoc} */
	@Override
	public int getVisualHeight() {
		double maxY = getHeight();
		final Iterator<? extends Renderable> renderables = getRenderables();
		if (renderables != null) {
			while (renderables.hasNext()) {
				final Renderable r = renderables.next();
				if (r instanceof BoundableRenderable) {
					final BoundableRenderable br = (BoundableRenderable) r;
					double brMaxY = br.getVisualBounds().getMaxY();
					if (brMaxY > maxY) {
						maxY = brMaxY;
					}
				} else if (r instanceof RenderableContainer) {
					final RenderableContainer rc = (RenderableContainer) r;
					double rcMaxY = rc.getVisualBounds().getMaxY();
					if (rcMaxY > maxY) {
						maxY = rcMaxY;
					}
				} else if (r instanceof PositionedRenderable) {
					final PositionedRenderable rc = (PositionedRenderable) r;
					double rcMaxY = rc.getRenderable().getVisualBounds().getMaxY();
					if (rcMaxY > maxY) {
						maxY = rcMaxY;
					}
				} else {
					System.err.println("Unhandled renderable: " + r);
					Thread.dumpStack();
				}
			}
		}
		return (int) maxY;
	}

	/** {@inheritDoc} */
	@Override
	public int getVisualWidth() {
		double maxX = getWidth();
		final Iterator<? extends Renderable> renderables = getRenderables();
		if (renderables != null) {
			while (renderables.hasNext()) {
				final Renderable r = renderables.next();
				if (r instanceof BoundableRenderable) {
					final BoundableRenderable br = (BoundableRenderable) r;
					double brMaxX = br.getVisualBounds().getMaxX();
					if (brMaxX > maxX) {
						maxX = brMaxX;
					}
				} else if (r instanceof RenderableContainer) {
					final RenderableContainer rc = (RenderableContainer) r;
					double rcMaxX = rc.getVisualBounds().getMaxX();
					if (rcMaxX > maxX) {
						maxX = rcMaxX;
					}
				} else if (r instanceof PositionedRenderable) {
					final PositionedRenderable rc = (PositionedRenderable) r;
					double rcMaxX = rc.getRenderable().getVisualBounds().getMaxX();
					if (rcMaxX > maxX) {
						maxX = rcMaxX;
					}
				} else {
					System.err.println("Unhandled renderable: " + r);
					Thread.dumpStack();
				}
			}
		}
		return (int) maxX;
	}
}
