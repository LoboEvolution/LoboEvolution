/*    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
/*
 * Created on Apr 16, 2005
 */
package org.lobobrowser.html.renderer;

import java.awt.EventQueue;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.HtmlProperties;
import org.lobobrowser.html.HtmlRendererContext;
import org.lobobrowser.html.UserAgentContext;
import org.lobobrowser.html.control.HrControl;
import org.lobobrowser.html.control.RUIControl;
import org.lobobrowser.html.dombl.ModelNode;
import org.lobobrowser.html.dombl.UINode;
import org.lobobrowser.html.domimpl.DOMNodeImpl;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.domimpl.HTMLTableElementImpl;
import org.lobobrowser.html.layout.AnchorLayout;
import org.lobobrowser.html.layout.BlockQuoteLayout;
import org.lobobrowser.html.layout.BrLayout;
import org.lobobrowser.html.layout.ButtonLayout;
import org.lobobrowser.html.layout.CanvasLayout;
import org.lobobrowser.html.layout.ChildrenLayout;
import org.lobobrowser.html.layout.CommonBlockLayout;
import org.lobobrowser.html.layout.DivLayout;
import org.lobobrowser.html.layout.EmLayout;
import org.lobobrowser.html.layout.HLayout;
import org.lobobrowser.html.layout.HrLayout;
import org.lobobrowser.html.layout.IFrameLayout;
import org.lobobrowser.html.layout.ImgLayout;
import org.lobobrowser.html.layout.InputLayout;
import org.lobobrowser.html.layout.ListItemLayout;
import org.lobobrowser.html.layout.MiscLayout;
import org.lobobrowser.html.layout.NoScriptLayout;
import org.lobobrowser.html.layout.NopLayout;
import org.lobobrowser.html.layout.ObjectLayout;
import org.lobobrowser.html.layout.PLayout;
import org.lobobrowser.html.layout.SelectLayout;
import org.lobobrowser.html.layout.SpanLayout;
import org.lobobrowser.html.layout.StrikeLayout;
import org.lobobrowser.html.layout.StrongLayout;
import org.lobobrowser.html.layout.TableLayout;
import org.lobobrowser.html.layout.TextAreaLayout;
import org.lobobrowser.html.layout.ULayout;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.html.style.HtmlInsets;
import org.lobobrowser.html.style.HtmlValues;
import org.w3c.dom.Node;

/**
 * A substantial portion of the HTML rendering logic of the package can be found
 * in this class. This class is in charge of laying out the DOM subtree of a
 * node, usually on behalf of an RBlock. It creates a renderer subtree
 * consisting of RLine's or RBlock's. RLine's in turn contain RWord's and so on.
 * This class also happens to be used as an RBlock scrollable viewport.
 * 
 * @author J. H. S.
 */
public class RBlockViewport extends BaseRCollection {
	// GENERAL NOTES
	// An RBlockViewport basically consists of two collections:
	// seqRenderables and positionedRenderables. The seqRenderables
	// collection is a sequential list of RLine's and RBlock's
	// that is amenable to a binary search by Y position. The
	// positionedRenderables collection is a z-index ordered
	// collection meant for blocks with position=absolute and such.
	//
	// HOW FLOATS WORK
	// Float boxes are scheduled to be added on the next available line.
	// Line layout is bounded by the current floatBounds.
	// When a float is placed with placeFloat(), an absolutely positioned
	// box is added. Whether the float height expands the RBlockViewport
	// height is determined by isFloatLimit().
	//
	// FloatingBounds are inherited by sub-boxes, but the bounds are
	// shifted.
	//
	// The RBlockViewport also publishes a collection of "exporatable
	// floating bounds." These are float boxes that go beyond the bounds
	// of the RBlockViewport, so ancestor blocks can obtain them to adjust
	// their own bounds.

	public static final Insets ZERO_INSETS = new Insets(0, 0, 0, 0);
	private static final Logger logger = Logger.getLogger(RBlockViewport.class.getName());

	public final RenderableContainer container;
	public final int listNesting;
	public final UserAgentContext userAgentContext;
	public final HtmlRendererContext rendererContext;
	public final FrameContext frameContext;
	private SortedSet<PositionedRenderable> positionedRenderables;
	private ArrayList<RLine> seqRenderables = null;
	private ArrayList<ExportableFloat> exportableFloats = null;
	private RLine currentLine;
	private int maxX;
	private int maxY;
	private int desiredWidth; // includes insets
	private int desiredHeight; // includes insets
	private int availContentHeight; // does not include insets
	private int availContentWidth; // does not include insets
	private int yLimit;
	private int positionedOrdinal;
	private int currentCollapsibleMargin;
	private Insets paddingInsets;
	private boolean overrideNoWrap;
	private FloatingBounds floatBounds = null;
	private boolean sizeOnly;
	private BoundableRenderable lastSeqBlock;

	private static final Map<String, Object> elementLayout = new HashMap<String, Object>();
	private static final MarkupLayout miscLayout = new MiscLayout();

	static {
		Map<String, Object> el = elementLayout;
		EmLayout em = new EmLayout();
		el.put(HtmlProperties.I, em);
		el.put(HtmlProperties.EM, em);
		el.put(HtmlProperties.CITE, em);
		el.put(HtmlProperties.H1, new HLayout(24));
		el.put(HtmlProperties.H2, new HLayout(18));
		el.put(HtmlProperties.H3, new HLayout(15));
		el.put(HtmlProperties.H4, new HLayout(12));
		el.put(HtmlProperties.H5, new HLayout(10));
		el.put(HtmlProperties.H6, new HLayout(8));
		StrongLayout strong = new StrongLayout();
		el.put(HtmlProperties.B, strong);
		el.put(HtmlProperties.STRONG, strong);
		el.put(HtmlProperties.TH, strong);
		el.put(HtmlProperties.U, new ULayout());
		el.put(HtmlProperties.STRIKE, new StrikeLayout());
		el.put(HtmlProperties.BR, new BrLayout());
		el.put(HtmlProperties.P, new PLayout());
		el.put(HtmlProperties.SECTION, new PLayout());
		el.put(HtmlProperties.NOSCRIPT, new NoScriptLayout());
		NopLayout nop = new NopLayout();
		el.put(HtmlProperties.SCRIPT, nop);
		el.put(HtmlProperties.HEAD, nop);
		el.put(HtmlProperties.TITLE, nop);
		el.put(HtmlProperties.META, nop);
		el.put(HtmlProperties.STYLE, nop);
		el.put(HtmlProperties.LINK, nop);
		el.put(HtmlProperties.IMG, new ImgLayout());
		el.put(HtmlProperties.TABLE, new TableLayout());
		ChildrenLayout children = new ChildrenLayout();
		el.put(HtmlProperties.HTML, children);
		AnchorLayout anchor = new AnchorLayout();
		el.put(HtmlProperties.A, anchor);
		el.put(HtmlProperties.ANCHOR, anchor);
		el.put(HtmlProperties.INPUT, new InputLayout());
		el.put(HtmlProperties.BUTTON, new ButtonLayout());
		el.put(HtmlProperties.TEXTAREA, new TextAreaLayout());
		el.put(HtmlProperties.SELECT, new SelectLayout());
		ListItemLayout list = new ListItemLayout();
		el.put(HtmlProperties.UL, list);
		el.put(HtmlProperties.OL, list);
		el.put(HtmlProperties.LI, list);
		CommonBlockLayout cbl = new CommonBlockLayout();
		el.put(HtmlProperties.PRE, cbl);
		el.put(HtmlProperties.CENTER, cbl);
		el.put(HtmlProperties.CAPTION, cbl);
		el.put(HtmlProperties.NAV, cbl);
		
		DivLayout div = new DivLayout();
		el.put(HtmlProperties.DIV, div);
		el.put(HtmlProperties.BODY, div);
		el.put(HtmlProperties.DL, div);
		el.put(HtmlProperties.DT, div);
		el.put(HtmlProperties.HEADER, div);
		el.put(HtmlProperties.FOOTER, div);
		el.put(HtmlProperties.ARTICLE, div);
		BlockQuoteLayout bq = new BlockQuoteLayout();
		el.put(HtmlProperties.BLOCKQUOTE, bq);
		el.put(HtmlProperties.DD, bq);
		el.put(HtmlProperties.HR, new HrLayout());
		el.put(HtmlProperties.SPAN, new SpanLayout());
		ObjectLayout ol = new ObjectLayout(false, true);
		el.put(HtmlProperties.OBJECT, new ObjectLayout(true, true));
		el.put(HtmlProperties.APPLET, ol);
		el.put(HtmlProperties.EMBED, ol);
		el.put(HtmlProperties.IFRAME, new IFrameLayout());
		el.put(HtmlProperties.CANVAS, new CanvasLayout());
		
	}

	/**
	 * Constructs an HtmlBlockLayout.
	 * 
	 * @param container
	 *            This is usually going to be an RBlock.
	 * @param listNesting
	 *            The nesting level for lists. This is zero except inside a
	 *            list.
	 * @param pcontext
	 *            The UserAgentContext instance.
	 * @param frameContext
	 *            This is usually going to be HtmlBlock, an object where text
	 *            selections are contained.
	 * @param parent
	 *            This is usually going to be the parent of
	 *            <code>container</code>.
	 */
	public RBlockViewport(ModelNode modelNode, RenderableContainer container,
			int listNesting, UserAgentContext pcontext,
			HtmlRendererContext rcontext, FrameContext frameContext,
			RCollection parent) {
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

	public void invalidateLayoutLocal() {
		// Workaround for fact that RBlockViewport does not
		// get validated or invalidated.
		this.layoutUpTreeCanBeInvalidated = true;
	}

	public int getAvailContentWidth() {
		return this.availContentWidth;
	}

	private int initCollapsibleMargin() {
		Object parent = this.parent;
		if (!(parent instanceof RBlock)) {
			return 0;
		}
		RBlock parentBlock = (RBlock) parent;
		return parentBlock.getCollapsibleMarginTop();
	}

	/**
	 * Builds the layout/renderer tree from scratch. Note: Returned dimension
	 * needs to be actual size needed for rendered content, not the available
	 * container size. This is relied upon by table layout.
	 * 
	 * @param yLimit
	 *            If other than -1, <code>layout</code> will throw
	 *            <code>SizeExceededException</code> in the event that the
	 *            layout goes beyond this y-coordinate point.
	 */
	public void layout(int desiredWidth, int desiredHeight,
			Insets paddingInsets, int yLimit, FloatingBounds floatBounds,
			boolean sizeOnly) {
		// Expected in GUI thread. It's possible it may be invoked during pack()
		// outside of the GUI thread.
		if (!EventQueue.isDispatchThread() && logger.isLoggable(Level.INFO)) {
			logger.warning("layout(): Invoked outside GUI dispatch thread.");
		}
		RenderableContainer container = this.container;
		this.paddingInsets = paddingInsets;
		this.yLimit = yLimit;
		this.desiredHeight = desiredHeight;
		this.desiredWidth = desiredWidth;
		this.floatBounds = floatBounds;
		this.isFloatLimit = null;
		this.pendingFloats = null;
		this.sizeOnly = sizeOnly;
		this.lastSeqBlock = null;
		this.currentCollapsibleMargin = this.initCollapsibleMargin();

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
		this.layoutPass((DOMNodeImpl) this.modelNode);

		Collection delayedPairs = container.getDelayedPairs();
		if (delayedPairs != null && delayedPairs.size() > 0) {
			// Add positioned renderables that belong here
			Iterator i = delayedPairs.iterator();
			while (i.hasNext()) {
				DelayedPair pair = (DelayedPair) i.next();
				if (pair.targetParent == container) {
					this.importDelayedPair(pair);
				}
			}
		}

		// Compute maxY according to last block.
		int maxY = this.maxY;
		int maxYWholeBlock = maxY;
		BoundableRenderable lastSeqBlock = this.lastSeqBlock;
		if (lastSeqBlock != null) {
			int effBlockHeight = this.getEffectiveBlockHeight(lastSeqBlock);
			if (lastSeqBlock.getY() + effBlockHeight > maxY) {
				this.maxY = maxY = lastSeqBlock.getY() + effBlockHeight;
				maxYWholeBlock = lastSeqBlock.getY() + lastSeqBlock.getHeight();
			}
		}

		// See if line should increase maxY. Empty
		// lines shouldn't, except in cases where
		// there was a BR.
		RLine lastLine = this.currentLine;
		Rectangle lastBounds = lastLine.getBounds();
		if (lastBounds.height > 0 || lastBounds.y > maxYWholeBlock) {
			int lastTopX = lastBounds.x + lastBounds.width;
			if (lastTopX > this.maxX) {
				this.maxX = lastTopX;
			}
			int lastTopY = lastBounds.y + lastBounds.height;
			if (lastTopY > maxY) {
				this.maxY = maxY = lastTopY;
			}
		}

		// Check positioned renderables for maxX and maxY
		SortedSet<PositionedRenderable> posRenderables = this.positionedRenderables;
		if (posRenderables != null) {
			boolean isFloatLimit = this.isFloatLimit();
			Iterator<PositionedRenderable> i = posRenderables.iterator();
			while (i.hasNext()) {
				PositionedRenderable pr = (PositionedRenderable) i.next();
				BoundableRenderable br = pr.renderable;
				if (br.getX() + br.getWidth() > this.maxX) {
					this.maxX = br.getX() + br.getWidth();
				}
				if (isFloatLimit || !pr.isFloat) {
					if (br.getY() + br.getHeight() > maxY) {
						this.maxY = maxY = br.getY() + br.getHeight();
					}
				}
			}
		}

		this.width = paddingInsets.right + this.maxX;
		this.height = paddingInsets.bottom + maxY;
	}

	private void layoutPass(DOMNodeImpl rootNode) {
		RenderableContainer container = this.container;
		container.clearDelayedPairs();
		this.positionedOrdinal = 0;

		// Remove sequential renderables...
		this.seqRenderables = null;

		// Remove other renderables...
		this.positionedRenderables = null;

		// Remove exporatable floats...
		this.exportableFloats = null;

		// Call addLine after setting margins
		this.currentLine = this.addLine(rootNode, null, this.paddingInsets.top);

		// Start laying out...
		// The parent is expected to have set the RenderState already.
		this.layoutChildren(rootNode);

		// This adds last-line floats.
		this.lineDone(this.currentLine);
	}

	/**
	 * Applies any horizonal aLignment. It may adjust height if necessary.
	 * 
	 * @param canvasWidth
	 *            The new width of the viewport. It could be different to the
	 *            previously calculated width.
	 * @param paddingInsets
	 */
	public void alignX(int alignXPercent, int canvasWidth, Insets paddingInsets) {
		int prevMaxY = this.maxY;
		// Horizontal alignment
		if (alignXPercent > 0) {
			ArrayList<RLine> renderables = this.seqRenderables;
			if (renderables != null) {
				Insets insets = this.paddingInsets;
				int numRenderables = renderables.size();
				int yoffset = 0; // This may get adjusted due to blocks and
									// floats.
				for (int i = 0; i < numRenderables; i++) {
					Object r = renderables.get(i);
					if (r instanceof BoundableRenderable) {
						BoundableRenderable seqRenderable = (BoundableRenderable) r;
						int y = seqRenderable.getY();
						int newY;
						if (yoffset > 0) {
							newY = y + yoffset;
							seqRenderable.setY(newY);
							if (newY + seqRenderable.getHeight() > this.maxY) {
								this.maxY = newY + seqRenderable.getHeight();
							}
						} else {
							newY = y;
						}
						boolean isVisibleBlock = seqRenderable instanceof RBlock
								&& ((RBlock) seqRenderable)
										.isOverflowVisibleX();
						int leftOffset = isVisibleBlock ? insets.left : this
								.fetchLeftOffset(y);
						int rightOffset = isVisibleBlock ? insets.right : this
								.fetchRightOffset(y);
						int actualAvailWidth = canvasWidth - leftOffset
								- rightOffset;
						int difference = actualAvailWidth
								- seqRenderable.getWidth();
						if (difference > 0) {
							// The difference check means that only
							// blocks with a declared width would get adjusted?
							int shift = (difference * alignXPercent) / 100;
							// if(floatBounds != null && isVisibleBlock) {
							// RBlock block = (RBlock) seqRenderable;
							// // Block needs to layed out again. Contents need
							// // to shift because of float.
							// final int expectedWidth = availContentWidth;
							// final int blockShiftRight = insets.right;
							// final int newX = leftOffset;
							// FloatingBoundsSource floatBoundsSource = new
							// ParentFloatingBoundsSource(blockShiftRight,
							// expectedWidth, newX, newY, floatBounds);
							// block.layout(actualAvailWidth,
							// this.availContentHeight, true, false,
							// floatBoundsSource);
							// }
							if (!isVisibleBlock) {
								int newX = leftOffset + shift;
								seqRenderable.setX(newX);
							}
						}
					}
				}
			}
		}
		if (prevMaxY != this.maxY) {
			this.height += (this.maxY - prevMaxY);
		}
	}

	/**
	 * Applies vertical alignment.
	 * 
	 * @param canvasHeight
	 * @param paddingInsets
	 */
	public void alignY(int alignYPercent, int canvasHeight, Insets paddingInsets) {
		int prevMaxY = this.maxY;
		if (alignYPercent > 0) {
			int availContentHeight = canvasHeight - paddingInsets.top
					- paddingInsets.bottom;
			int usedHeight = this.maxY - paddingInsets.top;
			int difference = availContentHeight - usedHeight;
			if (difference > 0) {
				int shift = (difference * alignYPercent) / 100;
				ArrayList<RLine> rlist = this.seqRenderables;
				if (rlist != null) {
					// Try sequential renderables first.
					Iterator renderables = rlist.iterator();
					while (renderables.hasNext()) {
						Object r = renderables.next();
						if (r instanceof BoundableRenderable) {
							BoundableRenderable line = (BoundableRenderable) r;
							int newY = line.getY() + shift;
							line.setY(newY);
							if (newY + line.getHeight() > this.maxY) {
								this.maxY = newY + line.getHeight();
							}
						}
					}
				}

				// Now other renderables, but only those that can be
				// vertically aligned
				Set<PositionedRenderable> others = this.positionedRenderables;
				if (others != null) {
					Iterator<PositionedRenderable> i2 = others.iterator();
					while (i2.hasNext()) {
						PositionedRenderable pr = (PositionedRenderable) i2
								.next();
						if (pr.verticalAlignable) {
							BoundableRenderable br = pr.renderable;
							int newY = br.getY() + shift;
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
			this.height += (this.maxY - prevMaxY);
		}
	}

	// /**
	// *
	// * @param block A block needing readjustment due to horizontal alignment.
	// * @return
	// */
	// private int readjustBlock(RBlock block, final int newX, final int newY,
	// final FloatingBounds floatBounds) {
	// final int rightInsets = this.paddingInsets.right;
	// final int expectedWidth = this.desiredWidth - rightInsets - newX;
	// final int blockShiftRight = rightInsets;
	// final int prevHeight = block.height;
	// FloatingBoundsSource floatBoundsSource = new FloatingBoundsSource() {
	// public FloatingBounds getChildBlockFloatingBounds(int apparentBlockWidth)
	// {
	// int actualRightShift = blockShiftRight + (expectedWidth -
	// apparentBlockWidth);
	// return new ShiftedFloatingBounds(floatBounds, -newX, -actualRightShift,
	// -newY);
	// }
	// };
	// block.adjust(expectedWidth, this.availContentHeight, true, false,
	// floatBoundsSource, true);
	// return block.height - prevHeight;
	// }
	//
	private RLine addLine(ModelNode startNode, RLine prevLine, int newLineY) {
		// lineDone must be called before we try to
		// get float bounds.
		this.lineDone(prevLine);
		this.checkY(newLineY);
		int leftOffset = this.fetchLeftOffset(newLineY);
		int newX = leftOffset;
		int newMaxWidth = this.desiredWidth - this.fetchRightOffset(newLineY)
				- leftOffset;
		RLine rline;
		boolean initialAllowOverflow;
		if (prevLine == null) {
			// Note: Assumes that prevLine == null means it's the first line.
			RenderState rs = this.modelNode.getRenderState();
			initialAllowOverflow = rs == null ? false
					: rs.getWhiteSpace() == RenderState.WS_NOWRAP;
			// Text indentation only applies to the first line in the block.
			int textIndent = rs == null ? 0 : rs
					.getTextIndent(this.availContentWidth);
			if (textIndent != 0) {
				newX += textIndent;
				// Line width also changes!
				newMaxWidth += (leftOffset - newX);
			}
		} else {
			int prevLineHeight = prevLine.getHeight();
			if (prevLineHeight > 0) {
				this.currentCollapsibleMargin = 0;
			}
			initialAllowOverflow = prevLine.isAllowOverflow();
			if (prevLine.x + prevLine.width > this.maxX) {
				this.maxX = prevLine.x + prevLine.width;
			}
		}
		rline = new RLine(startNode, this.container, newX, newLineY,
				newMaxWidth, 0, initialAllowOverflow);
		rline.setParent(this);
		ArrayList<RLine> sr = this.seqRenderables;
		if (sr == null) {
			sr = new ArrayList(1);
			this.seqRenderables = sr;
		}
		sr.add(rline);
		this.currentLine = rline;
		return rline;
	}

	public void layoutMarkup(DOMNodeImpl node) {
		// This is the "inline" layout of an element.
		// The difference with layoutChildren is that this
		// method checks for padding and margin insets.
		RenderState rs = node.getRenderState();
		Insets marginInsets = null;
		Insets paddingInsets = null;
		if (rs != null) {
			HtmlInsets mi = rs.getMarginInsets();
			marginInsets = mi == null ? null : mi.getSimpleAWTInsets(
					this.availContentWidth, this.availContentHeight);
			HtmlInsets pi = rs.getPaddingInsets();
			paddingInsets = pi == null ? null : pi.getSimpleAWTInsets(
					this.availContentWidth, this.availContentHeight);
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
			RLine line = this.currentLine;
			line.addSpacing(new RSpacing(node, this.container, leftSpacing,
					line.height));
		}
		this.layoutChildren(node);
		if (rightSpacing > 0) {
			RLine line = this.currentLine;
			line.addSpacing(new RSpacing(node, this.container, rightSpacing,
					line.height));
		}
	}

	public void layoutChildren(DOMNodeImpl node) {
		DOMNodeImpl[] childrenArray = node.getChildrenArray();
		if (childrenArray != null) {
			int length = childrenArray.length;
			for (int i = 0; i < length; i++) {
				DOMNodeImpl child = childrenArray[i];
				short nodeType = child.getNodeType();
				if (nodeType == Node.TEXT_NODE) {
					this.layoutText(child);
				} else if (nodeType == Node.ELEMENT_NODE) {
					// Note that scanning for node bounds (anchor location)
					// depends on there being a style changer for inline
					// elements.
					this.currentLine.addStyleChanger(new RStyleChanger(child));
					String nodeName = child.getNodeName().toUpperCase();
					MarkupLayout ml = (MarkupLayout) elementLayout
							.get(nodeName);
					if (ml == null) {
						ml = miscLayout;
					}
					ml.layoutMarkup(this, (HTMLElementImpl) child);
					this.currentLine.addStyleChanger(new RStyleChanger(node));
				} else if (nodeType == Node.COMMENT_NODE
						|| nodeType == Node.PROCESSING_INSTRUCTION_NODE) {
					// ignore
				} else {
					throw new IllegalStateException("Unknown node: " + child);
				}
			}
		}
	}

	public final void positionRBlock(HTMLElementImpl markupElement,
			RBlock renderable) {
		if (!this.addElsewhereIfPositioned(renderable, markupElement, false,
				true, false)) {
			int availContentHeight = this.availContentHeight;
			RLine line = this.currentLine;
			// Inform line done before layout so floats are considered.
			this.lineDone(line);
			Insets paddingInsets = this.paddingInsets;
			int newLineY = line == null ? paddingInsets.top : line.y
					+ line.height;
			// int leftOffset = this.fetchLeftOffset(newLineY);
			// int rightOffset = this.fetchRightOffset(newLineY);
			// Float offsets are ignored with block.
			int availContentWidth = this.availContentWidth;
			final int expectedWidth = availContentWidth;
			final int blockShiftRight = paddingInsets.right;
			final int newX = paddingInsets.left;
			final int newY = newLineY;
			FloatingBounds floatBounds = this.floatBounds;
			FloatingBoundsSource floatBoundsSource = floatBounds == null ? null
					: new ParentFloatingBoundsSource(blockShiftRight,
							expectedWidth, newX, newY, floatBounds);
			renderable.layout(availContentWidth, availContentHeight, true,
					false, floatBoundsSource, this.sizeOnly);
			this.addAsSeqBlock(renderable, false, false, false, false);
			// Calculate new floating bounds after block has been put in place.
			FloatingInfo floatingInfo = renderable.getExportableFloatingInfo();
			if (floatingInfo != null) {
				this.importFloatingInfo(floatingInfo, renderable);
			}
			// Now add line, after float is set.
			this.addLineAfterBlock(renderable, false);
		}
	}

	public final void positionRElement(HTMLElementImpl markupElement,
			RElement renderable, boolean usesAlignAttribute,
			boolean obeysFloats, boolean alignCenterAttribute) {
		if (!this.addElsewhereIfPositioned(renderable, markupElement,
				usesAlignAttribute, true, true)) {
			int availContentWidth = this.availContentWidth;
			int availContentHeight = this.availContentHeight;
			RLine line = this.currentLine;
			// Inform line done before layout so floats are considered.
			this.lineDone(line);
			if (obeysFloats) {
				int newLineY = line == null ? this.paddingInsets.top : line.y
						+ line.height;
				int leftOffset = this.fetchLeftOffset(newLineY);
				int rightOffset = this.fetchRightOffset(newLineY);
				availContentWidth = this.desiredWidth - leftOffset
						- rightOffset;
			}
			renderable.layout(availContentWidth, availContentHeight,
					this.sizeOnly);
			boolean centerBlock = false;
			if (alignCenterAttribute) {
				String align = markupElement.getAttribute(HtmlAttributeProperties.ALIGN);
				centerBlock = align != null && align.equalsIgnoreCase("center");
			}
			this.addAsSeqBlock(renderable, obeysFloats, false, true,
					centerBlock);
		}
	}

	public final void layoutRBlock(HTMLElementImpl markupElement) {
		RBlock renderable = (RBlock) markupElement.getUINode();
		if (renderable == null) {
			renderable = new RBlock(markupElement, this.listNesting,
					this.userAgentContext, this.rendererContext,
					this.frameContext, this.container);
			markupElement.setUINode(renderable);
		}
		renderable.setOriginalParent(this);
		this.positionRBlock(markupElement, renderable);
	}

	public final void layoutRTable(HTMLElementImpl markupElement) {
		RElement renderable = (RElement) markupElement.getUINode();
		if (renderable == null) {
			renderable = new RTable(markupElement, this.userAgentContext,
					this.rendererContext, this.frameContext, container);
			markupElement.setUINode((UINode) renderable);
		}
		renderable.setOriginalParent(this);
		this.positionRElement(markupElement, renderable,
				markupElement instanceof HTMLTableElementImpl, true, true);
	}

	public final void layoutListItem(HTMLElementImpl markupElement) {
		RListItem renderable = (RListItem) markupElement.getUINode();
		if (renderable == null) {
			renderable = new RListItem(markupElement, this.listNesting,
					this.userAgentContext, this.rendererContext,
					this.frameContext, this.container, null);
			markupElement.setUINode(renderable);
		}
		renderable.setOriginalParent(this);
		this.positionRBlock(markupElement, renderable);
	}

	public final void layoutList(HTMLElementImpl markupElement) {
		RList renderable = (RList) markupElement.getUINode();
		if (renderable == null) {
			renderable = new RList(markupElement, this.listNesting,
					this.userAgentContext, this.rendererContext,
					this.frameContext, this.container, null);
			markupElement.setUINode(renderable);
		}
		renderable.setOriginalParent(this);
		this.positionRBlock(markupElement, renderable);
	}

	public void addLineBreak(ModelNode startNode, int breakType) {
		RLine line = this.currentLine;
		if (line == null) {
			Insets insets = this.paddingInsets;
			this.addLine(startNode, null, insets.top);
			line = this.currentLine;
		}
		if (line.getHeight() == 0) {
			RenderState rs = startNode.getRenderState();
			int fontHeight = rs.getFontMetrics().getHeight();
			line.setHeight(fontHeight);
		}
		line.setLineBreak(new LineBreak(breakType, startNode));
		int newLineY;
		FloatingBounds fb = this.floatBounds;
		if (breakType == LineBreak.NONE || fb == null) {
			newLineY = line == null ? this.paddingInsets.top : line.y
					+ line.height;
		} else {
			int prevY = line == null ? this.paddingInsets.top : line.y
					+ line.height;
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
		this.currentLine = this.addLine(startNode, line, newLineY);
	}

	private boolean addElsewhereIfFloat(BoundableRenderable renderable,
			HTMLElementImpl element, boolean usesAlignAttribute,
			AbstractCSS2Properties style, boolean layout) {
		// "static" handled here
		String align = null;
		if (style != null) {
			align = style.getFloat();
			if (align != null && align.length() == 0) {
				align = null;
			}
		}
		if (align == null && usesAlignAttribute) {
			align = element.getAttribute(HtmlAttributeProperties.ALIGN);
		}
		if (align != null) {
			if ("left".equalsIgnoreCase(align)) {
				this.layoutFloat(renderable, layout, true);
				return true;
			} else if ("right".equalsIgnoreCase(align)) {
				this.layoutFloat(renderable, layout, false);
				return true;
			} else {
				// fall through
			}
		}
		return false;
	}


	final RBlockViewport getParentViewport() {
		// Use originalParent, which for one, is not going to be null during
		// layout.
		RCollection parent = this.getOriginalOrCurrentParent();
		while (parent != null && !(parent instanceof RBlockViewport)) {
			parent = parent.getOriginalOrCurrentParent();
		}
		return (RBlockViewport) parent;
	}

	private static int getPosition(HTMLElementImpl element) {
		RenderState rs = element.getRenderState();
		return rs == null ? RenderState.POSITION_STATIC : rs.getPosition();
	}

	/**
	 * Checks for position and float attributes.
	 * 
	 * @param container
	 * @param containerSize
	 * @param insets
	 * @param renderable
	 * @param element
	 * @param usesAlignAttribute
	 * @return True if it was added elsewhere.
	 */
	private boolean addElsewhereIfPositioned(RElement renderable,
			HTMLElementImpl element, boolean usesAlignAttribute,
			boolean layoutIfPositioned, boolean obeysFloats) {
		// At this point block already has bounds.
		AbstractCSS2Properties style = element.getCurrentStyle();
		int position = getPosition(element);
		boolean absolute = position == RenderState.POSITION_ABSOLUTE;
		boolean relative = position == RenderState.POSITION_RELATIVE;
		if (absolute || relative) {
			if (layoutIfPositioned) {
				// Presumes the method will return true.
				if (renderable instanceof RBlock) {
					RBlock block = (RBlock) renderable;
					FloatingBoundsSource inheritedFloatBoundsSource = null;
					if (relative) {
						Insets paddingInsets = this.paddingInsets;
						RLine line = this.currentLine;
						// Inform line done before layout so floats are
						// considered.
						this.lineDone(line);
						int newY = line == null ? paddingInsets.top : line.y
								+ line.height;
						final int expectedWidth = this.availContentWidth;
						final int blockShiftRight = paddingInsets.right;
						final int newX = paddingInsets.left;
						FloatingBounds floatBounds = this.floatBounds;
						inheritedFloatBoundsSource = floatBounds == null ? null
								: new ParentFloatingBoundsSource(
										blockShiftRight, expectedWidth, newX,
										newY, floatBounds);
					}
					block.layout(this.availContentWidth,
							this.availContentHeight, false, false,
							inheritedFloatBoundsSource, this.sizeOnly);
				} else {
					renderable.layout(this.availContentWidth,
							this.availContentHeight, this.sizeOnly);
				}
			}
			RenderState rs = element.getRenderState();
			String leftText = style.getLeft();
			RLine line = this.currentLine;
			int lineBottomY = line == null ? 0 : line.getY() + line.getHeight();
			int newLeft;
			if (leftText != null) {
				newLeft = HtmlValues.getPixelSize(leftText, rs, 0,
						this.availContentWidth);
			} else {
				String rightText = style.getRight();
				if (rightText != null) {
					int right = HtmlValues.getPixelSize(rightText, rs, 0,
							this.availContentWidth);
					newLeft = this.desiredWidth - right - renderable.getWidth();
					// If right==0 and renderable.width is larger than the
					// parent's width,
					// the expected behavior is for newLeft to be negative.
				} else {
					newLeft = 0;
				}
			}
			int newTop = relative ? 0 : lineBottomY;
			String topText = style.getTop();
			if (topText != null) {
				newTop = HtmlValues.getPixelSize(topText, rs, newTop,
						this.availContentHeight);
			} else {
				String bottomText = style.getBottom();
				if (bottomText != null) {
					int bottom = HtmlValues.getPixelSize(bottomText, rs, 0,
							this.availContentHeight);
					newTop = this.desiredHeight - bottom
							- renderable.getHeight();
					if (!relative && newTop < 0) {
						newTop = 0;
					}
				}
			}
			if (relative) {
				// First, try to add normally.
				RRelative rrel = new RRelative(this.container, element,
						renderable, newLeft, newTop);
				rrel.assignDimension();
				if (!this.addElsewhereIfFloat(rrel, element,
						usesAlignAttribute, style, true)) {
					boolean centerBlock = false;
					if (renderable instanceof RTable) {
						String align = element.getAttribute(HtmlAttributeProperties.ALIGN);
						centerBlock = align != null
								&& align.equalsIgnoreCase("center");
					}
					this.addAsSeqBlock(rrel, obeysFloats, true, true,
							centerBlock);
					// Need to import float boxes from relative, after
					// the box's origin has been set.
					FloatingInfo floatingInfo = rrel
							.getExportableFloatingInfo();
					if (floatingInfo != null) {
						this.importFloatingInfo(floatingInfo, rrel);
					}
				} else {
					// Adjust size of RRelative again - float might have been
					// adjusted.
					rrel.assignDimension();
				}
			} else {
				// Schedule as delayed pair. Will be positioned after
				// everything else.
				this.scheduleAbsDelayedPair(renderable, newLeft, newTop);
				// Does not affect bounds of this viewport yet.
				return true;
			}
			int newBottomY = renderable.getY() + renderable.getHeight();
			this.checkY(newBottomY);
			if (newBottomY > this.maxY) {
				this.maxY = newBottomY;
			}
			return true;
		} else {
			if (this.addElsewhereIfFloat(renderable, element,
					usesAlignAttribute, style, layoutIfPositioned)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks property 'float' and in some cases attribute 'align'.
	 */
	public void addRenderableToLineCheckStyle(RElement renderable,
			HTMLElementImpl element, boolean usesAlignAttribute) {
		if (this.addElsewhereIfPositioned(renderable, element,
				usesAlignAttribute, true, true)) {
			return;
		}
		renderable.layout(this.availContentWidth, this.availContentHeight,
				this.sizeOnly);
		this.addRenderableToLine(renderable);
	}

	private void addRenderableToLine(Renderable renderable) {
		renderable.getModelNode().getRenderState();
		RLine line = this.currentLine;
		int liney = line.y;
		boolean emptyLine = line.isEmpty();
		FloatingBounds floatBounds = this.floatBounds;
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
				int rightOffset = this.fetchRightOffset(liney);
				int topLineX = this.desiredWidth - rightOffset;
				if (line.getX() + line.getWidth() > topLineX) {
					// Shift line down to clear area
					line.setY(cleary);
				}
			}
		} catch (OverflowException oe) {
			int nextY = emptyLine ? cleary : liney + line.height;
			this.addLine(renderable.getModelNode(), line, nextY);
			Collection renderables = oe.getRenderables();
			Iterator i = renderables.iterator();
			while (i.hasNext()) {
				Renderable r = (Renderable) i.next();
				this.addRenderableToLine(r);
			}
		}
		if (renderable instanceof RUIControl) {
			this.container.addComponent(((RUIControl) renderable).widget
					.getComponent());
		}
	}

	private void addWordToLine(RWord renderable) {
		// this.skipLineBreakBefore = false;
		RLine line = this.currentLine;
		int liney = line.y;
		boolean emptyLine = line.isEmpty();
		FloatingBounds floatBounds = this.floatBounds;
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
				int rightOffset = this.fetchRightOffset(liney);
				int topLineX = this.desiredWidth - rightOffset;
				if (line.getX() + line.getWidth() > topLineX) {
					// Shift line down to clear area
					line.setY(cleary);
				}
			}
		} catch (OverflowException oe) {
			int nextY = emptyLine ? cleary : liney + line.height;
			this.addLine(renderable.getModelNode(), line, nextY);
			Collection renderables = oe.getRenderables();
			Iterator i = renderables.iterator();
			while (i.hasNext()) {
				Renderable r = (Renderable) i.next();
				this.addRenderableToLine(r);
			}
		}
	}

	private void addAsSeqBlockCheckStyle(RElement block,
			HTMLElementImpl element, boolean usesAlignAttribute) {
		if (this.addElsewhereIfPositioned(block, element, usesAlignAttribute,
				false, true)) {
			return;
		}
		this.addAsSeqBlock(block);
	}

	private void addAsSeqBlock(RElement block) {
		this.addAsSeqBlock(block, true, true, true, false);
	}

	private int getNewBlockY(BoundableRenderable newBlock, int expectedY) {
		// Assumes the previous block is not a line with height > 0.
		if (!(newBlock instanceof RElement)) {
			return expectedY;
		}
		RElement block = (RElement) newBlock;
		int ccm = this.currentCollapsibleMargin;
		int topMargin = block.getMarginTop();
		if (topMargin == 0 && ccm == 0) {
			return expectedY;
		}
		return expectedY - Math.min(topMargin, ccm);
	}

	private int getEffectiveBlockHeight(BoundableRenderable block) {
		// Assumes block is the last one in the sequence.
		if (!(block instanceof RElement)) {
			return block.getHeight();
		}
		RCollection parent = this.getParent();
		if (!(parent instanceof RElement)) {
			return block.getHeight();
		}
		int blockMarginBottom = ((RElement) block).getMarginBottom();
		int parentMarginBottom = ((RElement) parent)
				.getCollapsibleMarginBottom();
		return block.getHeight()
				- Math.min(blockMarginBottom, parentMarginBottom);
	}

	private void addAsSeqBlock(BoundableRenderable block, boolean obeysFloats,
			boolean informLineDone, boolean addLine, boolean centerBlock) {
		Insets insets = this.paddingInsets;
		int insetsl = insets.left;
		ArrayList sr = this.seqRenderables;
		if (sr == null) {
			sr = new ArrayList(1);
			this.seqRenderables = sr;
		}
		RLine prevLine = this.currentLine;
		boolean initialAllowOverflow;
		if (prevLine != null) {
			initialAllowOverflow = prevLine.isAllowOverflow();
			if (informLineDone) {
				this.lineDone(prevLine);
			}
			if (prevLine.x + prevLine.width > this.maxX) {
				this.maxX = prevLine.x + prevLine.width;
			}
			// Check height only with floats.
		} else {
			initialAllowOverflow = false;
		}
		int prevLineHeight = prevLine == null ? 0 : prevLine.height;
		int newLineY = prevLine == null ? insets.top : prevLine.y
				+ prevLineHeight;
		int blockX;
		int blockY = prevLineHeight == 0 ? this.getNewBlockY(block, newLineY)
				: newLineY;
		int blockWidth = block.getWidth();
		if (obeysFloats) {
			// TODO: execution of fetchLeftOffset done twice with
			// positionRElement.
			FloatingBounds floatBounds = this.floatBounds;
			int actualAvailWidth;
			if (floatBounds != null) {
				int blockOffset = this.fetchLeftOffset(newLineY);
				blockX = blockOffset;
				int rightOffset = this.fetchRightOffset(newLineY);
				actualAvailWidth = this.desiredWidth - rightOffset
						- blockOffset;
				if (blockWidth > actualAvailWidth) {
					blockY = floatBounds.getClearY(newLineY);
				}
			} else {
				actualAvailWidth = this.availContentWidth;
				blockX = insetsl;
			}
			if (centerBlock) {
				int roomX = actualAvailWidth - blockWidth;
				if (roomX > 0) {
					blockX += (roomX / 2);
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
		this.currentCollapsibleMargin = block instanceof RElement ? ((RElement) block)
				.getMarginBottom() : 0;
		if (addLine) {
			newLineY = blockY + block.getHeight();
			this.checkY(newLineY);
			int leftOffset = this.fetchLeftOffset(newLineY);
			int newX = leftOffset;
			int newMaxWidth = this.desiredWidth
					- this.fetchRightOffset(newLineY) - leftOffset;
			ModelNode lineNode = block.getModelNode().getParentModelNode();
			RLine newLine = new RLine(lineNode, this.container, newX, newLineY,
					newMaxWidth, 0, initialAllowOverflow);
			newLine.setParent(this);
			sr.add(newLine);
			this.currentLine = newLine;
		}
	}

	private void addLineAfterBlock(RBlock block, boolean informLineDone) {
		ArrayList sr = this.seqRenderables;
		if (sr == null) {
			sr = new ArrayList(1);
			this.seqRenderables = sr;
		}
		RLine prevLine = this.currentLine;
		boolean initialAllowOverflow;
		if (prevLine != null) {
			initialAllowOverflow = prevLine.isAllowOverflow();
			if (informLineDone) {
				this.lineDone(prevLine);
			}
			if (prevLine.x + prevLine.width > this.maxX) {
				this.maxX = prevLine.x + prevLine.width;
			}
			// Check height only with floats.
		} else {
			initialAllowOverflow = false;
		}
		ModelNode lineNode = block.getModelNode().getParentModelNode();
		int newLineY = block.getY() + block.getHeight();
		this.checkY(newLineY);
		int leftOffset = this.fetchLeftOffset(newLineY);
		int newX = leftOffset;
		int newMaxWidth = this.desiredWidth - this.fetchRightOffset(newLineY)
				- leftOffset;
		RLine newLine = new RLine(lineNode, this.container, newX, newLineY,
				newMaxWidth, 0, initialAllowOverflow);
		newLine.setParent(this);
		sr.add(newLine);
		this.currentLine = newLine;
	}

	private void layoutText(DOMNodeImpl textNode) {
		RenderState renderState = textNode.getRenderState();
		if (renderState == null) {
			throw new IllegalStateException("RenderState is null for node "
					+ textNode + " with parent " + textNode.getParentNode());
		}
		FontMetrics fm = renderState.getFontMetrics();
		int descent = fm.getDescent();
		int ascentPlusLeading = fm.getAscent() + fm.getLeading();
		int wordHeight = fm.getHeight();
		int blankWidth = fm.charWidth(' ');
		int whiteSpace = this.overrideNoWrap ? RenderState.WS_NOWRAP
				: renderState.getWhiteSpace();
		int textTransform = renderState.getTextTransform();
		String text = textNode.getNodeValue();
		if (whiteSpace != RenderState.WS_PRE) {
			boolean prevAllowOverflow = this.currentLine.isAllowOverflow();
			boolean allowOverflow = whiteSpace == RenderState.WS_NOWRAP;
			this.currentLine.setAllowOverflow(allowOverflow);
			try {
				int length = text.length();
				boolean firstWord = true;
				StringBuffer word = new StringBuffer(12);
				for (int i = 0; i < length; i++) {
					char ch = text.charAt(i);
					if (Character.isWhitespace(ch)) {
						if (firstWord) {
							firstWord = false;
						}
						int wlen = word.length();
						if (wlen > 0) {
							RWord rword = new RWord(textNode, word.toString(),
									container, fm, descent, ascentPlusLeading,
									wordHeight, textTransform);
							this.addWordToLine(rword);
							word.delete(0, wlen);
						}
						RLine line = this.currentLine;
						if (line.width > 0) {
							RBlank rblank = new RBlank(textNode, fm, container,
									ascentPlusLeading, blankWidth, wordHeight);
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
					RWord rword = new RWord(textNode, word.toString(),
							container, fm, descent, ascentPlusLeading,
							wordHeight, textTransform);
					this.addWordToLine(rword);
				}
			} finally {
				this.currentLine.setAllowOverflow(prevAllowOverflow);
			}
		} else {
			int length = text.length();
			boolean lastCharSlashR = false;
			StringBuffer line = new StringBuffer();
			for (int i = 0; i < length; i++) {
				char ch = text.charAt(i);
				switch (ch) {
				case '\r':
					lastCharSlashR = true;
					break;
				case '\n':
					int llen = line.length();
					if (llen > 0) {
						RWord rword = new RWord(textNode, line.toString(),
								container, fm, descent, ascentPlusLeading,
								wordHeight, textTransform);
						this.addWordToLine(rword);
						line.delete(0, line.length());
					}
					RLine prevLine = this.currentLine;
					prevLine.setLineBreak(new LineBreak(LineBreak.NONE,
							textNode));
					this.addLine(textNode, prevLine, prevLine.y
							+ prevLine.height);
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
				RWord rword = new RWord(textNode, line.toString(), container,
						fm, descent, ascentPlusLeading, wordHeight,
						textTransform);
				this.addWordToLine(rword);
			}
		}
	}

	/**
	 * 
	 * @param others
	 *            An ordered collection.
	 * @param seqRenderables
	 * @param destination
	 */
	private void populateZIndexGroups(Collection others,
			Collection seqRenderables, ArrayList destination) {
		this.populateZIndexGroups(others, seqRenderables == null ? null : seqRenderables.iterator(), destination);
	}

	/**
	 * 
	 * @param others
	 *            An ordered collection.
	 * @param seqRenderablesIterator
	 * @param destination
	 */
	private void populateZIndexGroups(Collection others,
			Iterator seqRenderablesIterator, ArrayList destination) {
		// First, others with z-index < 0
		Iterator i1 = others.iterator();
		Renderable pending = null;
		while (i1.hasNext()) {
			PositionedRenderable pr = (PositionedRenderable) i1.next();
			BoundableRenderable r = pr.renderable;
			if (r.getZIndex() >= 0) {
				pending = r;
				break;
			}
			destination.add(r);
		}

		// Second, sequential renderables
		Iterator i2 = seqRenderablesIterator;
		if (i2 != null) {
			while (i2.hasNext()) {
				destination.add(i2.next());
			}
		}

		// Third, other renderables with z-index >= 0.
		if (pending != null) {
			destination.add(pending);
			while (i1.hasNext()) {
				PositionedRenderable pr = (PositionedRenderable) i1.next();
				Renderable r = pr.renderable;
				destination.add(r);
			}
		}
	}

	public Renderable[] getRenderablesArray() {
		SortedSet<PositionedRenderable> others = this.positionedRenderables;
		int othersSize = others == null ? 0 : others.size();
		if (othersSize == 0) {
			ArrayList sr = this.seqRenderables;
			return sr == null ? Renderable.EMPTY_ARRAY : (Renderable[]) sr.toArray(Renderable.EMPTY_ARRAY);
		} else {
			ArrayList allRenderables = new ArrayList();
			this.populateZIndexGroups(others, this.seqRenderables, allRenderables);
			return (Renderable[]) allRenderables.toArray(Renderable.EMPTY_ARRAY);
		}
	}

	public Iterator getRenderables() {
		SortedSet others = this.positionedRenderables;
		if (others == null || others.size() == 0) {
			ArrayList sr = this.seqRenderables;
			return sr == null ? null : sr.iterator();
		} else {
			ArrayList allRenderables = new ArrayList();
			this.populateZIndexGroups(others, this.seqRenderables,
					allRenderables);
			return allRenderables.iterator();
		}
	}

	public Iterator getRenderables(Rectangle clipBounds) {
		if (!EventQueue.isDispatchThread() && logger.isLoggable(Level.INFO)) {
			logger.warning("getRenderables(): Invoked outside GUI dispatch thread.");
		}
		ArrayList sr = this.seqRenderables;
		Iterator baseIterator = null;
		if (sr != null) {
			Renderable[] array = (Renderable[]) sr.toArray(Renderable.EMPTY_ARRAY);
			Range range = MarkupUtilities.findRenderables(array, clipBounds,
					true);
			baseIterator = org.lobobrowser.util.ArrayUtilities.iterator(array,
					range.offset, range.length);
		}
		SortedSet others = this.positionedRenderables;
		if (others == null || others.size() == 0) {
			return baseIterator;
		} else {
			ArrayList<PositionedRenderable> matches = new ArrayList<PositionedRenderable>();
			// ArrayList "matches" keeps the order from "others".
			Iterator i = others.iterator();
			while (i.hasNext()) {
				PositionedRenderable pr = (PositionedRenderable) i.next();
				Object r = pr.renderable;
				if (r instanceof BoundableRenderable) {
					BoundableRenderable br = (BoundableRenderable) r;
					Rectangle rbounds = br.getBounds();
					if (clipBounds.intersects(rbounds)) {
						matches.add(pr);
					}
				}
			}
			if (matches.size() == 0) {
				return baseIterator;
			} else {
				ArrayList destination = new ArrayList();
				this.populateZIndexGroups(matches, baseIterator, destination);
				return destination.iterator();
			}
		}
	}

	public BoundableRenderable getRenderable(int x, int y) {
		Iterator i = this.getRenderables(x, y);
		return i == null ? null : (i.hasNext() ? (BoundableRenderable) i.next()
				: null);
	}

	public BoundableRenderable getRenderable(java.awt.Point point) {
		return this.getRenderable(point.x, point.y);
	}

	public Iterator getRenderables(java.awt.Point point) {
		return this.getRenderables(point.x, point.y);
	}

	public Iterator getRenderables(int pointx, int pointy) {
		if (!EventQueue.isDispatchThread() && logger.isLoggable(Level.INFO)) {
			logger.warning("getRenderable(): Invoked outside GUI dispatch thread.");
		}
		Collection result = null;
		SortedSet others = this.positionedRenderables;
		int size = others == null ? 0 : others.size();
		PositionedRenderable[] otherArray = size == 0 ? null : (PositionedRenderable[]) others.toArray(PositionedRenderable.EMPTY_ARRAY);
		// Try to find in other renderables with z-index >= 0 first.
		int index = 0;
		if (size != 0) {
			int px = pointx;
			int py = pointy;
			// Must go in reverse order
			for (index = size; --index >= 0;) {
				PositionedRenderable pr = otherArray[index];
				BoundableRenderable r = pr.renderable;
				if (r.getZIndex() < 0) {
					break;
				}
				if (r instanceof BoundableRenderable) {
					BoundableRenderable br = (BoundableRenderable) r;
					Rectangle rbounds = br.getBounds();
					if (rbounds.contains(px, py)) {
						if (result == null) {
							result = new LinkedList();
						}
						result.add(br);
					}
				}
			}
		}

		// Now do a "binary" search on sequential renderables.
		ArrayList sr = this.seqRenderables;
		if (sr != null) {
			Renderable[] array = (Renderable[]) sr.toArray(Renderable.EMPTY_ARRAY);
			BoundableRenderable found = MarkupUtilities.findRenderable(array,
					pointx, pointy, true);
			if (found != null) {
				if (result == null) {
					result = new LinkedList();
				}
				result.add(found);
			}
		}

		// Finally, try to find it in renderables with z-index < 0.
		if (size != 0) {
			int px = pointx;
			int py = pointy;
			// Must go in reverse order
			for (; index >= 0; index--) {
				PositionedRenderable pr = otherArray[index];
				Renderable r = pr.renderable;
				if (r instanceof BoundableRenderable) {
					BoundableRenderable br = (BoundableRenderable) r;
					Rectangle rbounds = br.getBounds();
					if (rbounds.contains(px, py)) {
						if (result == null) {
							result = new LinkedList();
						}
						result.add(br);
					}
				}
			}
		}
		return result == null ? null : result.iterator();
	}

	private RElement setupNewUIControl(RenderableContainer container,
			HTMLElementImpl element, UIControl control) {
		RElement renderable = new RUIControl(element, control, container,
				this.frameContext, this.userAgentContext);
		element.setUINode((UINode) renderable);
		return renderable;
	}

	public final void addAlignableAsBlock(HTMLElementImpl markupElement,
			RElement renderable) {
		// TODO: Get rid of this method?
		// At this point block already has bounds.
		boolean regularAdd = false;
		String align = markupElement.getAttribute(HtmlAttributeProperties.ALIGN);
		if (align != null) {
			if ("left".equalsIgnoreCase(align)) {
				this.layoutFloat(renderable, false, true);
			} else if ("right".equalsIgnoreCase(align)) {
				this.layoutFloat(renderable, false, false);
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

	public final void layoutHr(HTMLElementImpl markupElement) {
		RElement renderable = (RElement) markupElement.getUINode();
		if (renderable == null) {
			renderable = this.setupNewUIControl(container, markupElement,
					new HrControl(markupElement));
		}
		renderable.layout(this.availContentWidth, this.availContentHeight,
				this.sizeOnly);
		this.addAlignableAsBlock(markupElement, renderable);
	}

	/**
	 * Gets offset from the left due to floats. It includes padding.
	 */
	public final int fetchLeftOffset(int newLineY) {
		Insets paddingInsets = this.paddingInsets;
		FloatingBounds floatBounds = this.floatBounds;
		if (floatBounds == null) {
			return paddingInsets.left;
		}
		int left = floatBounds.getLeft(newLineY);
		if (left < paddingInsets.left) {
			return paddingInsets.left;
		}
		return left;
	}

	/**
	 * Gets offset from the right due to floats. It includes padding.
	 */
	public final int fetchRightOffset(int newLineY) {
		Insets paddingInsets = this.paddingInsets;
		FloatingBounds floatBounds = this.floatBounds;
		if (floatBounds == null) {
			return paddingInsets.right;
		}
		int right = floatBounds.getRight(newLineY);
		if (right < paddingInsets.right) {
			return paddingInsets.right;
		}
		return right;
	}

	private static final SizeExceededException SEE = new SizeExceededException();

	public final void checkY(int y) {
		if (this.yLimit != -1 && y > this.yLimit) {
			throw SEE;
		}
	}

	public final void layoutFloat(BoundableRenderable renderable,
			boolean layout, boolean leftFloat) {
		renderable.setOriginalParent(this);
		if (layout) {
			int availWidth = this.availContentWidth;
			int availHeight = this.availContentHeight;
			if (renderable instanceof RBlock) {
				RBlock block = (RBlock) renderable;
				// Float boxes don't inherit float bounds?
				block.layout(availWidth, availHeight, false, false, null,
						this.sizeOnly);
			} else if (renderable instanceof RElement) {
				RElement e = (RElement) renderable;
				e.layout(availWidth, availHeight, this.sizeOnly);
			}
		}
		RFloatInfo floatInfo = new RFloatInfo(renderable.getModelNode(),
				renderable, leftFloat);
		this.currentLine.simplyAdd(floatInfo);
		this.scheduleFloat(floatInfo);
	}

	private void scheduleAbsDelayedPair(BoundableRenderable renderable, int x,
			int y) {
		// It gets reimported in the local
		// viewport if it turns out it can't be exported up.
		RenderableContainer container = this.container;
		for (;;) {
			if (container instanceof Renderable) {
				Object node = ((Renderable) container).getModelNode();
				if (node instanceof HTMLElementImpl) {
					HTMLElementImpl element = (HTMLElementImpl) node;
					int position = getPosition(element);
					if (position != RenderState.POSITION_STATIC) {
						break;
					}
					RenderableContainer newContainer = container
							.getParentContainer();
					if (newContainer == null) {
						break;
					}
					container = newContainer;
				} else {
					break;
				}
			} else {
				break;
			}
		}
		DelayedPair pair = new DelayedPair(container, renderable, x, y);
		this.container.addDelayedPair(pair);
	}

	void importDelayedPair(DelayedPair pair) {
		BoundableRenderable r = pair.child;
		r.setOrigin(pair.x, pair.y);
		this.addPositionedRenderable(r, false, false);
		// Size of block does not change - it's
		// set in stone?
	}

	public final void addPositionedRenderable(BoundableRenderable renderable,
			boolean verticalAlignable, boolean isFloat) {
		// Expected to be called only in GUI thread.
		SortedSet others = this.positionedRenderables;
		if (others == null) {
			others = new TreeSet(new ZIndexComparator());
			this.positionedRenderables = others;
		}
		others.add(new PositionedRenderable(renderable, verticalAlignable,
				this.positionedOrdinal++, isFloat));
		renderable.setParent(this);
		if (renderable instanceof RUIControl) {
			this.container.addComponent(((RUIControl) renderable).widget
					.getComponent());
		}
	}

	public int getFirstLineHeight() {
		ArrayList renderables = this.seqRenderables;
		if (renderables != null) {
			int size = renderables.size();
			if (size == 0) {
				return 0;
			}
			for (int i = 0; i < size; i++) {
				BoundableRenderable br = (BoundableRenderable) renderables
						.get(0);
				int height = br.getHeight();
				if (height != 0) {
					return height;
				}
			}
		}
		// Not found!!
		return 1;
	}

	public int getFirstBaselineOffset() {
		ArrayList renderables = this.seqRenderables;
		if (renderables != null) {
			Iterator i = renderables.iterator();
			while (i.hasNext()) {
				Object r = i.next();
				if (r instanceof RLine) {
					int blo = ((RLine) r).getBaselineOffset();
					if (blo != 0) {
						return blo;
					}
				} else if (r instanceof RBlock) {
					RBlock block = (RBlock) r;
					if (block.getHeight() > 0) {
						Insets insets = block.getInsets(false, false);
						Insets paddingInsets = this.paddingInsets;
						return block.getFirstBaselineOffset()
								+ insets.top
								+ (paddingInsets == null ? 0
										: paddingInsets.top);
					}
				}
			}
		}
		return 0;
	}

	// ----------------------------------------------------------------

	public RenderableSpot getLowestRenderableSpot(int x, int y) {
		BoundableRenderable br = this.getRenderable(new Point(x, y));
		if (br != null) {
			return br.getLowestRenderableSpot(x - br.getX(), y - br.getY());
		} else {
			return new RenderableSpot(this, x, y);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.render.BoundableRenderable#onMouseClick(java.awt.event
	 * .MouseEvent, int, int)
	 */
	public boolean onMouseClick(MouseEvent event, int x, int y) {
		Iterator i = this.getRenderables(new Point(x, y));
		if (i != null) {
			while (i.hasNext()) {
				BoundableRenderable br = (BoundableRenderable) i.next();
				if (br != null) {
					Rectangle bounds = br.getBounds();
					if (!br.onMouseClick(event, x - bounds.x, y - bounds.y)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public boolean onDoubleClick(MouseEvent event, int x, int y) {
		Iterator i = this.getRenderables(new Point(x, y));
		if (i != null) {
			while (i.hasNext()) {
				BoundableRenderable br = (BoundableRenderable) i.next();
				if (br != null) {
					Rectangle bounds = br.getBounds();
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
	 * org.lobobrowser.html.render.BoundableRenderable#onMouseDisarmed(java.awt
	 * .event.MouseEvent)
	 */
	public boolean onMouseDisarmed(MouseEvent event) {
		BoundableRenderable br = this.armedRenderable;
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

	private BoundableRenderable armedRenderable;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.render.BoundableRenderable#onMousePressed(java.awt.
	 * event.MouseEvent, int, int)
	 */
	public boolean onMousePressed(MouseEvent event, int x, int y) {
		Iterator i = this.getRenderables(new Point(x, y));
		if (i != null) {
			while (i.hasNext()) {
				BoundableRenderable br = (BoundableRenderable) i.next();
				if (br != null) {
					Rectangle bounds = br.getBounds();
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
	 * org.lobobrowser.html.render.BoundableRenderable#onMouseReleased(java.awt
	 * .event.MouseEvent, int, int)
	 */
	public boolean onMouseReleased(MouseEvent event, int x, int y) {
		Iterator i = this.getRenderables(new Point(x, y));
		if (i != null) {
			while (i.hasNext()) {
				BoundableRenderable br = (BoundableRenderable) i.next();
				if (br != null) {
					Rectangle bounds = br.getBounds();
					if (!br.onMouseReleased(event, x - bounds.x, y - bounds.y)) {
						BoundableRenderable oldArmedRenderable = this.armedRenderable;
						if (oldArmedRenderable != null
								&& br != oldArmedRenderable) {
							oldArmedRenderable.onMouseDisarmed(event);
							this.armedRenderable = null;
						}
						return false;
					}
				}
			}
		}
		BoundableRenderable oldArmedRenderable = this.armedRenderable;
		if (oldArmedRenderable != null) {
			oldArmedRenderable.onMouseDisarmed(event);
			this.armedRenderable = null;
		}
		return true;
	}

	public void paint(Graphics g) {
		Rectangle clipBounds = g.getClipBounds();
		Iterator i = this.getRenderables(clipBounds);
		if (i != null) {
			while (i.hasNext()) {
				Object robj = i.next();
				// The expected behavior in HTML is for boxes
				// not to be clipped unless overflow=hidden.
				if (robj instanceof BoundableRenderable) {
					BoundableRenderable renderable = (BoundableRenderable) robj;
					renderable.paintTranslated(g);
					// numRenderables++;
				} else {
					((Renderable) robj).paint(g);
				}
			}
		}
	}

	public boolean isContainedByNode() {
		return false;
	}

	public String toString() {
		return "RBlockViewport[node=" + this.modelNode + "]";
	}

	private void scheduleFloat(RFloatInfo floatInfo) {
		RLine line = this.currentLine;
		if (line == null) {
			int y = line == null ? this.paddingInsets.top : line.getY();
			this.placeFloat(floatInfo.getRenderable(), y,
					floatInfo.isLeftFloat());
		} else if (line.getWidth() == 0) {
			int y = line.getY();
			this.placeFloat(floatInfo.getRenderable(), y,
					floatInfo.isLeftFloat());
			int leftOffset = this.fetchLeftOffset(y);
			int rightOffset = this.fetchRightOffset(y);
			line.changeLimits(leftOffset, this.desiredWidth - leftOffset
					- rightOffset);
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

	private Collection<RFloatInfo> pendingFloats = null;

	private void lineDone(RLine line) {
		int yAfterLine = line == null ? this.paddingInsets.top : line.y
				+ line.height;
		Collection<RFloatInfo> pfs = this.pendingFloats;
		if (pfs != null) {
			this.pendingFloats = null;
			Iterator i = pfs.iterator();
			while (i.hasNext()) {
				RFloatInfo pf = (RFloatInfo) i.next();
				this.placeFloat(pf.getRenderable(), yAfterLine,
						pf.isLeftFloat());
			}
		}
	}

	private void addExportableFloat(BoundableRenderable element,
			boolean leftFloat, int origX, int origY) {
		ArrayList<ExportableFloat> ep = this.exportableFloats;
		if (ep == null) {
			ep = new ArrayList<ExportableFloat>(1);
			this.exportableFloats = ep;
		}
		ep.add(new ExportableFloat(element, leftFloat, origX, origY));
	}

	/**
	 * 
	 * @param element
	 * @param y
	 *            The desired top position of the float element.
	 * @param floatType
	 *            -1 (left) or +1 (right)
	 */
	private void placeFloat(BoundableRenderable element, int y,
			boolean leftFloat) {
		Insets insets = this.paddingInsets;
		int boxY = y;
		int boxWidth = element.getWidth();
		int boxHeight = element.getHeight();
		int desiredWidth = this.desiredWidth;
		int boxX;
		for (;;) {
			int leftOffset = this.fetchLeftOffset(boxY);
			int rightOffset = this.fetchRightOffset(boxY);
			boxX = leftFloat ? leftOffset : desiredWidth - rightOffset
					- boxWidth;
			if (leftOffset == insets.left && rightOffset == insets.right) {
				// Probably typical scenario. If it's overflowing to the left,
				// we need to correct.
				if (!leftFloat && boxX < leftOffset) {
					boxX = leftOffset;
				}
				break;
			}
			if (boxWidth <= desiredWidth - rightOffset - leftOffset) {
				// Size is fine.
				break;
			}
			// At this point the float doesn't fit at the current Y position.
			if (element instanceof RBlock) {
				// Try shrinking it.
				RBlock relement = (RBlock) element;
				if (!relement.hasDeclaredWidth()) {
					int availableBoxWidth = desiredWidth - rightOffset
							- leftOffset;
					relement.layout(availableBoxWidth, this.availContentHeight,
							this.sizeOnly);
					if (relement.getWidth() < boxWidth) {
						if (relement.getWidth() > desiredWidth - rightOffset
								- leftOffset) {
							// Didn't work out. Put it back the way it was.
							relement.layout(this.availContentWidth,
									this.availContentHeight, this.sizeOnly);
						} else {
							// Retry
							boxWidth = relement.getWidth();
							boxHeight = relement.getHeight();
							continue;
						}
					}
				}
			}
			FloatingBounds fb = this.floatBounds;
			int newY = fb == null ? boxY + boxHeight : fb.getFirstClearY(boxY);
			if (newY == boxY) {
				// Possible if prior box has height zero?
				break;
			}
			boxY = newY;
		}
		// Position element
		element.setOrigin(boxX, boxY);
		// Update float bounds accordingly
		int offsetFromBorder = leftFloat ? boxX + boxWidth : desiredWidth
				- boxX;
		this.floatBounds = new FloatingViewportBounds(this.floatBounds,
				leftFloat, boxY, offsetFromBorder, boxHeight);
		// Add element to collection
		boolean isFloatLimit = this.isFloatLimit();
		if (isFloatLimit) {
			this.addPositionedRenderable(element, true, true);
		} else {
			this.addExportableFloat(element, leftFloat, boxX, boxY);
		}
		// Adjust maxX based on float.
		if (boxX + boxWidth > this.maxX) {
			this.maxX = boxX + boxWidth;
		}
		// Adjust maxY based on float, but only if this viewport is the float
		// limit.
		if (this.isFloatLimit()) {
			if (boxY + boxHeight > this.maxY) {
				this.maxY = boxY + boxHeight;
			}
		}
	}

	private Boolean isFloatLimit = null;

	private boolean isFloatLimit() {
		Boolean fl = this.isFloatLimit;
		if (fl == null) {
			fl = this.isFloatLimitImpl();
			this.isFloatLimit = fl;
		}
		return fl.booleanValue();
	}

	private Boolean isFloatLimitImpl() {
		Object parent = this.getOriginalOrCurrentParent();
		if (!(parent instanceof RBlock)) {
			return Boolean.TRUE;
		}
		RBlock blockParent = (RBlock) parent;
		Object grandParent = blockParent.getOriginalOrCurrentParent();
		if (!(grandParent instanceof RBlockViewport)) {
			// Could be contained in a table, or it could
			// be a list item, for example.
			return Boolean.TRUE;
		}
		ModelNode node = this.modelNode;
		if (!(node instanceof HTMLElementImpl)) {
			// Can only be a document here.
			return Boolean.TRUE;
		}
		HTMLElementImpl element = (HTMLElementImpl) node;
		int position = getPosition(element);
		if (position == RenderState.POSITION_ABSOLUTE
				|| position == RenderState.POSITION_FIXED) {
			return Boolean.TRUE;
		}
		element.getCurrentStyle();
		RenderState rs = element.getRenderState();
		int floatValue = rs == null ? RenderState.FLOAT_NONE : rs.getFloat();
		if (floatValue != RenderState.FLOAT_NONE) {
			return Boolean.TRUE;
		}
		int overflowX = rs == null ? RenderState.OVERFLOW_NONE : rs
				.getOverflowX();
		int overflowY = rs == null ? RenderState.OVERFLOW_NONE : rs
				.getOverflowY();
		if (overflowX == RenderState.OVERFLOW_AUTO
				|| overflowX == RenderState.OVERFLOW_SCROLL
				|| overflowY == RenderState.OVERFLOW_AUTO
				|| overflowY == RenderState.OVERFLOW_SCROLL) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	// /**
	// * Gets FloatingBounds from this viewport that should
	// * be considered by an ancestor block.
	// */
	// public FloatingBounds getExportableFloatingBounds() {
	// FloatingBounds floatBounds = this.floatBounds;
	// if(floatBounds == null) {
	// return null;
	// }
	// if(this.isFloatLimit()) {
	// return null;
	// }
	// int maxY = floatBounds.getMaxY();
	// if(maxY > this.height) {
	// return floatBounds;
	// }
	// return null;
	// }

	public FloatingInfo getExportableFloatingInfo() {
		ArrayList<ExportableFloat> ef = this.exportableFloats;
		if (ef == null) {
			return null;
		}
		ExportableFloat[] floats = (ExportableFloat[]) ef
				.toArray(ExportableFloat.EMPTY_ARRAY);
		return new FloatingInfo(0, 0, floats);
	}

	private void importFloatingInfo(FloatingInfo floatingInfo,
			BoundableRenderable block) {
		int shiftX = floatingInfo.shiftX + block.getX();
		int shiftY = floatingInfo.shiftY + block.getY();
		ExportableFloat[] floats = floatingInfo.floats;
		int length = floats.length;
		for (int i = 0; i < length; i++) {
			ExportableFloat ef = floats[i];
			this.importFloat(ef, shiftX, shiftY);
		}
	}

	private void importFloat(ExportableFloat ef, int shiftX, int shiftY) {
		BoundableRenderable renderable = ef.element;
		int newX = ef.origX + shiftX;
		int newY = ef.origY + shiftY;
		renderable.setOrigin(newX, newY);
		FloatingBounds prevBounds = this.floatBounds;
		int offsetFromBorder;
		boolean leftFloat = ef.leftFloat;
		if (leftFloat) {
			offsetFromBorder = newX + renderable.getWidth();
		} else {
			offsetFromBorder = this.desiredWidth - newX;
		}
		this.floatBounds = new FloatingViewportBounds(prevBounds, leftFloat,
				newY, offsetFromBorder, renderable.getHeight());
		if (this.isFloatLimit()) {
			this.addPositionedRenderable(renderable, true, true);
		} else {
			this.addExportableFloat(renderable, leftFloat, newX, newY);
		}
	}
	
	public void layoutRInlineBlock(final HTMLElementImpl markupElement) {
		final RInlineBlock inlineBlock = new RInlineBlock(container, markupElement, userAgentContext, rendererContext, frameContext);		
		inlineBlock.doLayout(availContentWidth, availContentHeight, sizeOnly);
		addRenderableToLine(inlineBlock);
		}
	
	public FrameContext getFrameContext() {
		return frameContext;
	}
	
	public RenderableContainer getContainer() {
		return container;
	}
	
	public UserAgentContext getUserAgentContext() {
		return userAgentContext;
	}
	
	public HtmlRendererContext getRendererContext() {
		return rendererContext;
	}
}
