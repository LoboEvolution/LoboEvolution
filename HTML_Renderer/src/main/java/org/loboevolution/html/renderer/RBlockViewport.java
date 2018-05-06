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
/*
 * Created on Apr 16, 2005
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
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.loboevolution.arraylist.ArrayUtilities;
import org.loboevolution.html.HtmlAttributeProperties;
import org.loboevolution.html.HtmlLayoutMapping;
import org.loboevolution.html.HtmlRendererContext;
import org.loboevolution.html.control.HrControl;
import org.loboevolution.html.control.RUIControl;
import org.loboevolution.html.dombl.ModelNode;
import org.loboevolution.html.dombl.UINode;
import org.loboevolution.html.domimpl.DOMFragmentImpl;
import org.loboevolution.html.domimpl.DOMNodeImpl;
import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.html.info.FloatingInfo;
import org.loboevolution.html.layout.MarkupLayout;
import org.loboevolution.html.layout.MiscLayout;
import org.loboevolution.html.renderertable.RTable;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.util.Strings;
import org.w3c.dom.Node;

/**
 * A substantial portion of the HTML rendering logic of the package can be found
 * in this class. This class is in charge of laying out the DOM subtree of a
 * node, usually on behalf of an RBlock. It creates a renderer subtree
 * consisting of RLine's or RBlock's. RLine's in turn contain RWord's and so on.
 * This class also happens to be used as an RBlock scrollable viewport. * An
 * RBlockViewport basically consists of two collections: seqRenderables and
 * positionedRenderables. The seqRenderables collection is a sequential list of
 * RLine's and RBlock's that is amenable to a binary search by Y position. The
 * positionedRenderables collection is a z-index ordered collection meant for
 * blocks with position=absolute and such.
 *
 * HOW FLOATS WORK Float boxes are scheduled to be added on the next available
 * line. Line layout is bounded by the current floatBounds. When a float is
 * placed with placeFloat(), an absolutely positioned box is added. Whether the
 * float height expands the RBlockViewport height is determined by
 * isFloatLimit().
 *
 * FloatingBounds are inherited by sub-boxes, but the bounds are shifted.
 *
 * The RBlockViewport also publishes a collection of "exporatable floating
 * bounds." These are float boxes that go beyond the bounds of the
 * RBlockViewport, so ancestor blocks can obtain them to adjust their own
 * bounds.
 *
 * @author J. H. S.
 */
public class RBlockViewport extends BaseRCollection implements HtmlAttributeProperties {

	/** The Constant ZERO_INSETS. */
	public static final Insets ZERO_INSETS = new Insets(0, 0, 0, 0);

	/** The container. */
	private RenderableContainer container;

	/** The list nesting. */
	private int listNesting;

	/** The user agent context. */
	private UserAgentContext userAgentContext;

	/** The renderer context. */
	private HtmlRendererContext rendererContext;

	/** The frame context. */
	private FrameContext frameContext;

	/** The positioned renderables. */
	private SortedSet<PositionedRenderable> positionedRenderables;

	/** The seq renderables. */
	private ArrayList<BoundableRenderable> seqRenderables = null;

	/** The exportable floats. */
	private ArrayList<ExportableFloat> exportableFloats = null;

	/** The current line. */
	private RLine currentLine;

	/** The max x. */
	private int maxX;

	/** The max y. */
	private int maxY;

	/** The desired width. */
	private int desiredWidth; // includes insets

	/** The desired height. */
	private int desiredHeight; // includes insets

	/** The avail content height. */
	private int availContentHeight; // does not include insets

	/** The avail content width. */
	private int availContentWidth; // does not include insets

	/** The y limit. */
	private int yLimit;

	/** The positioned ordinal. */
	private int positionedOrdinal;

	/** The current collapsible margin. */
	private int currentCollapsibleMargin;

	/** The padding insets. */
	private Insets paddingInsets;

	/** The override no wrap. */
	private boolean overrideNoWrap;

	/** The float bounds. */
	private FloatingBounds floatBounds = null;

	/** The size only. */
	private boolean sizeOnly;

	/** The last seq block. */
	private BoundableRenderable lastSeqBlock;

	/** The Constant elementLayout. */
	private static final Map<String, Object> elementLayout = HtmlLayoutMapping.layout();

	/** The Constant miscLayout. */
	private static final MarkupLayout miscLayout = new MiscLayout();
	
	/** The pending floats. */
	private Collection<RFloatInfo> pendingFloats = null;

	/** The is float limit. */
	private Boolean isFloatLimit = null;
	
	/** The Constant SEE. */
	private static final SizeExceededException SEE = new SizeExceededException();

	/** The armed renderable. */
	private BoundableRenderable armedRenderable;

	/**
	 * Constructs an HtmlBlockLayout.
	 *
	 * @param modelNode
	 *            the model node
	 * @param container
	 *            This is usually going to be an RBlock.
	 * @param listNesting
	 *            The nesting level for lists. This is zero except inside a
	 *            list.
	 * @param pcontext
	 *            The UserAgentContext instance.
	 * @param rcontext
	 *            the rcontext
	 * @param frameContext
	 *            This is usually going to be HtmlBlock, an object where text
	 *            selections are contained.
	 * @param parent
	 *            This is usually going to be the parent of
	 *            <code>container</code>.
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
		this.layoutUpTreeCanBeInvalidated = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderer.BaseBoundableRenderable#
	 * invalidateLayoutLocal()
	 */
	@Override
	public void invalidateLayoutLocal() {
		this.layoutUpTreeCanBeInvalidated = true;
	}

	/**
	 * Gets the avail content width.
	 *
	 * @return the avail content width
	 */
	public int getAvailContentWidth() {
		return this.availContentWidth;
	}

	/**
	 * Builds the layout/renderer tree from scratch. Note: Returned dimension
	 * needs to be actual size needed for rendered content, not the available
	 * container size. This is relied upon by table layout.
	 *
	 * @param desiredWidth
	 *            the desired width
	 * @param desiredHeight
	 *            the desired height
	 * @param paddingInsets
	 *            the padding insets
	 * @param yLimit
	 *            If other than -1, <code>layout</code> will throw
	 *            <code>SizeExceededException</code> in the event that the
	 *            layout goes beyond this y-coordinate point.
	 * @param floatBounds
	 *            the float bounds
	 * @param sizeOnly
	 *            the size only
	 */
	public void layout(final int desiredWidth, final int desiredHeight, final Insets paddingInsets, final int yLimit,
		      final FloatingBounds floatBounds, final boolean sizeOnly) {

		    // final RenderableContainer container = this.container;
		    this.paddingInsets = paddingInsets;
		    this.yLimit = yLimit;
		    this.desiredHeight = desiredHeight;
		    this.desiredWidth = desiredWidth;
		    this.floatBounds = floatBounds;
		    this.isFloatLimit = null;
		    this.pendingFloats = null;
		    this.sizeOnly = sizeOnly;
		    this.lastSeqBlock = null;
		    this.currentCollapsibleMargin = 0;

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

		    // Compute maxY according to last block.
		    int maxY = this.maxY;
		    int maxYWholeBlock = maxY;
		    final BoundableRenderable lastSeqBlock = this.lastSeqBlock;
		    if (lastSeqBlock != null) {
		      final int effBlockHeight = this.getEffectiveBlockHeight(lastSeqBlock);
		      if ((lastSeqBlock.getY() + effBlockHeight) > maxY) {
		        this.maxY = maxY = lastSeqBlock.getY() + effBlockHeight;
		        maxYWholeBlock = lastSeqBlock.getY() + lastSeqBlock.getHeight();
		      }
		    }

		    // See if line should increase maxY. Empty
		    // lines shouldn't, except in cases where
		    // there was a BR.
		    final RLine lastLine = this.currentLine;
		    final Rectangle lastBounds = lastLine.getBounds();
		    if ((lastBounds.height > 0) || (lastBounds.y > maxYWholeBlock)) {
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
		      final boolean isFloatLimit = this.isFloatLimitImpl();
		      final Iterator<PositionedRenderable> i = posRenderables.iterator();
		      while (i.hasNext()) {
		        final PositionedRenderable pr = i.next();
		        final BoundableRenderable br = pr.getRenderable();
		        if ((br.getX() + br.getWidth()) > this.maxX) {
		          this.maxX = br.getX() + br.getWidth();
		        }
		        if (isFloatLimit || !pr.isFloat()) {
		          if ((br.getY() + br.getHeight()) > maxY) {
		            this.maxY = maxY = br.getY() + br.getHeight();
		          }
		        }
		      }
		    }

		    this.width = paddingInsets.right + this.maxX;
		    this.height = paddingInsets.bottom + maxY;
		}

	/**
	 * Layout pass.
	 *
	 * @param rootNode
	 *            the root node
	 */
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
	 * @param alignXPercent
	 *            the align x percent
	 * @param canvasWidth
	 *            The new width of the viewport. It could be different to the
	 *            previously calculated width.
	 * @param paddingInsets
	 *            the padding insets
	 */
	public void alignX(final int alignXPercent, final int canvasWidth, final Insets paddingInsets) {
	    final int prevMaxY = this.maxY;
	    // Horizontal alignment
	    if (alignXPercent > 0) {
	      final ArrayList<BoundableRenderable> renderables = this.seqRenderables;
	      if (renderables != null) {
	        final Insets insets = this.paddingInsets;
	        // final FloatingBounds floatBounds = this.floatBounds;
	        final int numRenderables = renderables.size();
	        for (int i = 0; i < numRenderables; i++) {
	          final Object r = renderables.get(i);
	          if (r instanceof BoundableRenderable) {
	            final BoundableRenderable seqRenderable = (BoundableRenderable) r;
	            final int y = seqRenderable.getY();
	            final boolean isVisibleBlock = (seqRenderable instanceof RBlock) && ((RBlock) seqRenderable).isOverflowVisibleX();
	            final int leftOffset = isVisibleBlock ? insets.left : this.fetchLeftOffset(y);
	            final int rightOffset = isVisibleBlock ? insets.right : this.fetchRightOffset(y);
	            final int actualAvailWidth = canvasWidth - leftOffset - rightOffset;
	            final int difference = actualAvailWidth - seqRenderable.getWidth();
	            if (difference > 0) {
	              final int shift = (difference * alignXPercent) / 100;
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
	      this.height += (this.maxY - prevMaxY);
	    }
	}

	/**
	 * Applies vertical alignment.
	 *
	 * @param alignYPercent
	 *            the align y percent
	 * @param canvasHeight
	 *            the canvas height
	 * @param paddingInsets
	 *            the padding insets
	 */
	public void alignY(final int alignYPercent, final int canvasHeight, final Insets paddingInsets) {
	    final int prevMaxY = this.maxY;
	    if (alignYPercent > 0) {
	      final int availContentHeight = canvasHeight - paddingInsets.top - paddingInsets.bottom;
	      final int usedHeight = this.maxY - paddingInsets.top;
	      final int difference = availContentHeight - usedHeight;
	      if (difference > 0) {
	        final int shift = (difference * alignYPercent) / 100;
	        final ArrayList<BoundableRenderable> rlist = this.seqRenderables;
	        if (rlist != null) {
	          // Try sequential renderables first.
	          final Iterator<BoundableRenderable> renderables = rlist.iterator();
	          while (renderables.hasNext()) {
	            final Object r = renderables.next();
	            if (r instanceof BoundableRenderable) {
	              final BoundableRenderable line = (BoundableRenderable) r;
	              final int newY = line.getY() + shift;
	              line.setY(newY);
	              if ((newY + line.getHeight()) > this.maxY) {
	                this.maxY = newY + line.getHeight();
	              }
	            }
	          }
	        }

	        // Now other renderables, but only those that can be
	        // vertically aligned
	        final Set<PositionedRenderable> others = this.positionedRenderables;
	        if (others != null) {
	          final Iterator<PositionedRenderable> i2 = others.iterator();
	          while (i2.hasNext()) {
	            final PositionedRenderable pr = i2.next();
	            if (pr.isVerticalAlignable()) {
	              final BoundableRenderable br = pr.getRenderable();
	              final int newY = br.getY() + shift;
	              br.setY(newY);
	              if ((newY + br.getHeight()) > this.maxY) {
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
	private RLine addLine(final ModelNode startNode, final RLine prevLine, final int newLineY) {
	    // lineDone must be called before we try to
	    // get float bounds.
	    this.lineDone(prevLine);
	    this.checkY(newLineY);
	    final int leftOffset = this.fetchLeftOffset(newLineY);
	    int newX = leftOffset;
	    int newMaxWidth = this.desiredWidth - this.fetchRightOffset(newLineY) - leftOffset;
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
	        newMaxWidth += (leftOffset - newX);
	      }
	    } else {
	      final int prevLineHeight = prevLine.getHeight();
	      if (prevLineHeight > 0) {
	        this.currentCollapsibleMargin = 0;
	      }
	      initialAllowOverflow = prevLine.isAllowOverflow();
	      if ((prevLine.x + prevLine.width) > this.maxX) {
	        this.maxX = prevLine.x + prevLine.width;
	      }
	    }
	    rline = new RLine(startNode, this.container, newX, newLineY, newMaxWidth, 0, initialAllowOverflow);
	    rline.setParent(this);
	    ArrayList<BoundableRenderable> sr = this.seqRenderables;
	    if (sr == null) {
	      sr = new ArrayList<>(1);
	      this.seqRenderables = sr;
	    }
	    sr.add(rline);
	    this.currentLine = rline;
	    return rline;
	}

	/**
	 * Layout markup.
	 *
	 * @param node
	 *            the node
	 */
	public void layoutMarkup(final DOMNodeImpl node) {
	    final RenderState rs = node.getRenderState();
	    final HtmlInsets mi = rs.getMarginInsets();
	    final Insets marginInsets = mi == null ? null : mi.getSimpleAWTInsets(this.availContentWidth, this.availContentHeight);
	    final HtmlInsets pi = rs.getPaddingInsets();
	    final Insets paddingInsets = pi == null ? null : pi.getSimpleAWTInsets(this.availContentWidth, this.availContentHeight);

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
	    this.layoutChildren(node);
	    if (rightSpacing > 0) {
	      final RLine line = this.currentLine;
	      line.addSpacing(new RSpacing(node, this.container, rightSpacing, line.height));
	    }
	}

	/**
	 * Layout children.
	 *
	 * @param node
	 *            the node
	 */
	public void layoutChildren(DOMNodeImpl node) {
		DOMNodeImpl[] childrenArray = node.getChildrenArray();
		if (childrenArray != null) {
			for (DOMNodeImpl child : childrenArray) {
				short nodeType = child.getNodeType();
				switch (nodeType) {
				case Node.TEXT_NODE:
					this.layoutText(child);
					break;
				case Node.ELEMENT_NODE:
					this.currentLine.addStyleChanger(new RStyleChanger(child));
					String nodeName = child.getNodeName().toUpperCase();
					MarkupLayout ml = (MarkupLayout) elementLayout.get(nodeName);
					if (ml == null) {
						ml = miscLayout;
					}
					ml.layoutMarkup(this, (HTMLElementImpl) child);
					this.currentLine.addStyleChanger(new RStyleChanger(node));
					break;
				case Node.DOCUMENT_FRAGMENT_NODE:
					final DOMFragmentImpl fragment = (DOMFragmentImpl) child;
					for (final DOMNodeImpl fragChild : fragment.getChildrenArray()) {
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

	/**
	 * Position r block.
	 *
	 * @param markupElement
	 *            the markup element
	 * @param renderable
	 *            the renderable
	 */
	private final void positionRBlock(HTMLElementImpl markupElement, RBlock renderable) {
		
		RenderState rs = renderable.modelNode.getRenderState();
		int clear = rs.getClear();
		if (clear != LineBreak.NONE) {
			addLineBreak(renderable.modelNode, clear);
		}
		
		if (!this.addElsewhereIfPositioned(renderable, markupElement, false, true, false)) {
			int availContentHeight = this.availContentHeight;
			RLine line = this.currentLine;
			// Inform line done before layout so floats are considered.
			this.lineDone(line);
			Insets paddingInsets = this.paddingInsets;
			int newLineY = line == null ? paddingInsets.top : line.y + line.height;
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
					: new ParentFloatingBoundsSource(blockShiftRight, expectedWidth, newX, newY, floatBounds);
			renderable.layout(availContentWidth, availContentHeight, true, false, floatBoundsSource, this.sizeOnly);
			this.addAsSeqBlock(renderable, false, false, false, false);
			// Calculate Float.valueOfing bounds after block has been put in place.
			FloatingInfo floatingInfo = renderable.getExportableFloatingInfo();
			if (floatingInfo != null) {
				this.importFloatingInfo(floatingInfo, renderable);
			}
			// Now add line, after float is set.
			this.addLineAfterBlock(renderable, false);
			layoutRelative(markupElement, renderable);
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
	public final void positionRElement(HTMLElementImpl markupElement, RElement renderable, boolean usesAlignAttribute,
			boolean obeysFloats, boolean alignCenterAttribute) {
		if (!this.addElsewhereIfPositioned(renderable, markupElement, usesAlignAttribute, true, true)) {
			int availContentWidth = this.availContentWidth;
			int availContentHeight = this.availContentHeight;
			RLine line = this.currentLine;
			// Inform line done before layout so floats are considered.
			this.lineDone(line);
			if (obeysFloats) {
				int newLineY = line == null ? this.paddingInsets.top : line.y + line.height;
				int leftOffset = this.fetchLeftOffset(newLineY);
				int rightOffset = this.fetchRightOffset(newLineY);
				availContentWidth = this.desiredWidth - leftOffset - rightOffset;
			}
			renderable.layout(availContentWidth, availContentHeight, this.sizeOnly);
			boolean centerBlock = false;
			if (alignCenterAttribute) {
				String align = markupElement.getAttribute(ALIGN);
				centerBlock = "center".equalsIgnoreCase(align);
			}
			this.addAsSeqBlock(renderable, obeysFloats, false, true, centerBlock);
			layoutRelative(markupElement, renderable);
		}
	}

	/**
	 * Layout r block.
	 *
	 * @param markupElement
	 *            the markup element
	 */
	public final void layoutRBlock(HTMLElementImpl markupElement) {
		RBlock renderable = (RBlock) markupElement.getUINode();
		if (renderable == null) {
			renderable = new RBlock(markupElement, this.listNesting, this.userAgentContext, this.rendererContext,
					this.frameContext, this.container);
			markupElement.setUINode(renderable);
		}
		renderable.setOriginalParent(this);
		this.positionRBlock(markupElement, renderable);
	}

	/**
	 * Layout list item.
	 *
	 * @param markupElement
	 *            the markup element
	 */
	public final void layoutListItem(HTMLElementImpl markupElement) {
		RListItem renderable = (RListItem) markupElement.getUINode();
		if (renderable == null) {
			renderable = new RListItem(markupElement, this.listNesting, this.userAgentContext, this.rendererContext,
					this.frameContext, this.container);
			markupElement.setUINode(renderable);
		}
		renderable.setOriginalParent(this);
		this.positionRBlock(markupElement, renderable);
	}

	/**
	 * Layout list.
	 *
	 * @param markupElement
	 *            the markup element
	 */
	public final void layoutList(HTMLElementImpl markupElement) {
		RList renderable = (RList) markupElement.getUINode();
		if (renderable == null) {
			renderable = new RList(markupElement, this.listNesting, this.userAgentContext, this.rendererContext, this.frameContext, this.container);
			markupElement.setUINode(renderable);
		}
		renderable.setOriginalParent(this);
		this.positionRBlock(markupElement, renderable);
	}

	/**
	 * Adds the line break.
	 *
	 * @param startNode
	 *            the start node
	 * @param breakType
	 *            the break type
	 */
	public void addLineBreak(ModelNode startNode, int breakType) {
		RLine line = this.currentLine;
		RenderState rs = startNode.getRenderState();
		if (line == null) {
			Insets insets = this.paddingInsets;
			this.addLine(startNode, null, insets.top);
			line = this.currentLine;
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
		FloatingBounds fb = this.floatBounds;
		if (breakType == LineBreak.NONE || fb == null) {
			newLineY = line == null ? this.paddingInsets.top : line.y + line.height;
		} else {
			int prevY = line == null ? this.paddingInsets.top : line.y + line.height;
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

	/**
	 * Adds the elsewhere if float.
	 *
	 * @param renderable
	 *            the renderable
	 * @param element
	 *            the element
	 * @param usesAlignAttribute
	 *            the uses align attribute
	 * @param style
	 *            the style
	 * @param layout
	 *            the layout
	 * @return true, if successful
	 */
	private boolean addElsewhereIfFloat(BoundableRenderable renderable, HTMLElementImpl element,
			boolean usesAlignAttribute, AbstractCSSProperties style, boolean layout) {
		// "static" handled here
		String align = null;
		if (style != null) {
			align = style.getFloat();
			if (Strings.isBlank(align)) {
				align = null;
			}
		}
		if (align == null && usesAlignAttribute) {
			align = element.getAttribute(ALIGN);
		}
		if (align != null) {
			if ("left".equalsIgnoreCase(align)) {
				this.layoutFloat(renderable, layout, true);
				return true;
			} else if ("right".equalsIgnoreCase(align)) {
				this.layoutFloat(renderable, layout, false);
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the parent viewport.
	 *
	 * @return the parent viewport
	 */
	public final RBlockViewport getParentViewport() {
		// Use originalParent, which for one, is not going to be null during
		// layout.
		RCollection parent = this.getOriginalOrCurrentParent();
		while (parent != null && !(parent instanceof RBlockViewport)) {
			parent = parent.getOriginalOrCurrentParent();
		}
		return (RBlockViewport) parent;
	}

	/**
	 * Gets the position.
	 *
	 * @param element
	 *            the element
	 * @return the position
	 */
	private static int getPosition(HTMLElementImpl element) {
		RenderState rs = element.getRenderState();
		return rs == null ? RenderState.POSITION_STATIC : rs.getPosition();
	}

	/**
	 * Checks for position and float attributes.
	 *
	 * @param renderable
	 *            the renderable
	 * @param element
	 *            the element
	 * @param usesAlignAttribute
	 *            the uses align attribute
	 * @param layoutIfPositioned
	 *            the layout if positioned
	 * @param obeysFloats
	 *            the obeys floats
	 * @return True if it was added elsewhere.
	 */
	private boolean addElsewhereIfPositioned(RElement renderable, HTMLElementImpl element, boolean usesAlignAttribute,
			boolean layoutIfPositioned, boolean obeysFloats) {
		// At this point block already has bounds.
		AbstractCSSProperties style = element.getCurrentStyle();
		int position = getPosition(element);
		boolean absolute = position == RenderState.POSITION_ABSOLUTE;
		boolean relative = position == RenderState.POSITION_RELATIVE;
		boolean fixed = position == RenderState.POSITION_FIXED;
		if (absolute || relative || fixed) {
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
						int newY = line == null ? paddingInsets.top : line.y + line.height;
						final int expectedWidth = this.availContentWidth;
						final int blockShiftRight = paddingInsets.right;
						final int newX = paddingInsets.left;
						FloatingBounds floatBounds = this.floatBounds;
						inheritedFloatBoundsSource = floatBounds == null ? null
								: new ParentFloatingBoundsSource(blockShiftRight, expectedWidth, newX, newY,
										floatBounds);
					}

					boolean floating = element.getRenderState().getFloat() != RenderState.FLOAT_NONE;
					boolean growHorizontally = relative && !floating;
					block.layout(this.availContentWidth, this.availContentHeight, growHorizontally, false,
							inheritedFloatBoundsSource, this.sizeOnly);

				} else {
					renderable.layout(this.availContentWidth, this.availContentHeight, this.sizeOnly);
				}
			}
			RenderState rs = element.getRenderState();
			String leftText = style.getLeft();
			String rightText = style.getRight();
			String bottomText = style.getBottom();
			String topText = style.getTop();
			RLine line = this.currentLine;
			int lineBottomY = line == null ? 0 : line.getY() + line.getHeight();
			int newLeft;
			if (leftText != null) {
				newLeft = HtmlValues.getPixelSize(leftText, rs, 0, this.availContentWidth);
			} else {
				if (rightText != null) {
					int right = HtmlValues.getPixelSize(rightText, rs, 0, this.availContentWidth);
					newLeft = this.desiredWidth - right - renderable.getWidth();
					// If right==0 and renderable.width is larger than the
					// parent's width,
					// the expected behavior is for newLeft to be negative.
				} else {
					newLeft = 0;
				}
			}
			int newTop = relative ? 0 : lineBottomY;
			if (topText != null) {
				newTop = HtmlValues.getPixelSize(topText, rs, newTop, this.availContentHeight);
			} else {
				if (bottomText != null) {
					int bottom = HtmlValues.getPixelSize(bottomText, rs, 0, this.availContentHeight);
					if (relative) {
						newTop = -bottom;
					} else {
						newTop = Math.max(0, this.desiredHeight - bottom - renderable.getHeight());
					}
				}
			}
			if (relative) {
				// First, try to add normally.
				RRelative rrel = new RRelative(this.container, element, renderable, newLeft, newTop);
				rrel.assignDimension();
				if (!this.addElsewhereIfFloat(rrel, element, usesAlignAttribute, style, true)) {
					boolean centerBlock = false;
					if (renderable instanceof RTable) {
						String align = element.getAttribute(ALIGN);
						centerBlock = "center".equalsIgnoreCase(align);
					}
					this.addAsSeqBlock(rrel, obeysFloats, true, true, centerBlock);
					// Need to import float boxes from relative, after
					// the box's origin has been set.
					FloatingInfo floatingInfo = rrel.getExportableFloatingInfo();
					if (floatingInfo != null) {
						this.importFloatingInfo(floatingInfo, rrel);
					}
				} else {
					// Adjust size of RRelative again - float might have been
					// adjusted.
					rrel.assignDimension();
				}
			} else {
				// Schedule as delayed pair. Will be positioned after everything
				// else.
				this.scheduleAbsDelayedPair(renderable, leftText, rightText, topText, bottomText, rs,
						currentLine.getY() + currentLine.getHeight());
				return true;
			}
			int newBottomY = renderable.getY() + renderable.getHeight();
			this.checkY(newBottomY);
			if (newBottomY > this.maxY) {
				this.maxY = newBottomY;
			}
			return true;
		} else {
			if (this.addElsewhereIfFloat(renderable, element, usesAlignAttribute, style, layoutIfPositioned)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks property 'float' and in some cases attribute 'align'.
	 *
	 * @param renderable
	 *            the renderable
	 * @param element
	 *            the element
	 * @param usesAlignAttribute
	 *            the uses align attribute
	 */
	public void addRenderableToLineCheckStyle(RElement renderable, HTMLElementImpl element,
			boolean usesAlignAttribute) {
		if (this.addElsewhereIfPositioned(renderable, element, usesAlignAttribute, true, true)) {
			return;
		}
		renderable.layout(this.availContentWidth, this.availContentHeight, this.sizeOnly);
		this.addRenderableToLine(renderable);
		layoutRelative(element, renderable);
	}

	/**
	 * Adds the renderable to line.
	 *
	 * @param renderable
	 *            the renderable
	 */
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
			Collection<?> renderables = oe.getRenderables();
			Iterator<?> i = renderables.iterator();
			while (i.hasNext()) {
				Renderable r = (Renderable) i.next();
				this.addRenderableToLine(r);
			}
		}
		if (renderable instanceof RUIControl) {
			this.container.addComponent(((RUIControl) renderable).getWidget().getComponent());
		}
	}

	/**
	 * Adds the word to line.
	 *
	 * @param renderable
	 *            the renderable
	 */
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
			if (!line.isAllowOverflow() && floatBounds != null && cleary > liney) {
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
			Collection<?> renderables = oe.getRenderables();
			Iterator<?> i = renderables.iterator();
			while (i.hasNext()) {
				Renderable r = (Renderable) i.next();
				this.addRenderableToLine(r);
			}
		}
	}

	/**
	 * Adds the as seq block.
	 *
	 * @param block
	 *            the block
	 */
	private void addAsSeqBlock(RElement block) {
		this.addAsSeqBlock(block, true, true, true, false);
	}

	/**
	 * Gets the new block y.
	 *
	 * @param newBlock
	 *            the new block
	 * @param expectedY
	 *            the expected y
	 * @return the new block y
	 */
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

	/**
	 * Gets the effective block height.
	 *
	 * @param block
	 *            the block
	 * @return the effective block height
	 */
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
		int parentMarginBottom = ((RElement) parent).getCollapsibleMarginBottom();
		return block.getHeight() - Math.min(blockMarginBottom, parentMarginBottom);
	}

	/**
	 * Adds the as seq block.
	 *
	 * @param block
	 *            the block
	 * @param obeysFloats
	 *            the obeys floats
	 * @param informLineDone
	 *            the inform line done
	 * @param addLine
	 *            the add line
	 * @param centerBlock
	 *            the center block
	 */
	private void addAsSeqBlock(BoundableRenderable block, boolean obeysFloats, boolean informLineDone, boolean addLine,
			boolean centerBlock) {
		Insets insets = this.paddingInsets;
		int insetsl = insets.left;
		ArrayList<BoundableRenderable> sr = this.seqRenderables;
		if (sr == null) {
			sr = new ArrayList<BoundableRenderable>(1);
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
		int newLineY = prevLine == null ? insets.top : prevLine.y + prevLineHeight;
		int blockX;
		int blockY = prevLineHeight == 0 ? this.getNewBlockY(block, newLineY) : newLineY;
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
				actualAvailWidth = this.desiredWidth - rightOffset - blockOffset;
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
			this.checkY(newLineY);
			int leftOffset = this.fetchLeftOffset(newLineY);
			int newX = leftOffset;
			int newMaxWidth = this.desiredWidth - this.fetchRightOffset(newLineY) - leftOffset;
			ModelNode lineNode = block.getModelNode().getParentModelNode();
			RLine newLine = new RLine(lineNode, this.container, newX, newLineY, newMaxWidth, 0, initialAllowOverflow);
			newLine.setParent(this);
			sr.add(newLine);
			this.currentLine = newLine;
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
	private void addLineAfterBlock(RBlock block, boolean informLineDone) {
		ArrayList<BoundableRenderable> sr = this.seqRenderables;
		if (sr == null) {
			sr = new ArrayList<BoundableRenderable>(1);
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
		int newMaxWidth = this.desiredWidth - this.fetchRightOffset(newLineY) - leftOffset;
		RLine newLine = new RLine(lineNode, this.container, newX, newLineY, newMaxWidth, 0, initialAllowOverflow);
		newLine.setParent(this);
		sr.add(newLine);
		this.currentLine = newLine;
	}

	/**
	 * Layout text.
	 *
	 * @param textNode
	 *            the text node
	 */
	private void layoutText(DOMNodeImpl textNode) {
		RenderState renderState = textNode.getRenderState();
		if (renderState == null) {
			throw new IllegalStateException(
					"RenderState is null for node " + textNode + " with parent " + textNode.getParentNode());
		}
		FontMetrics fm = renderState.getFontMetrics();
		int descent = fm.getDescent();
		int ascentPlusLeading = fm.getAscent() + fm.getLeading();
		int wordHeight = fm.getHeight();
		int blankWidth = fm.charWidth(' ');
		int whiteSpace = this.overrideNoWrap ? RenderState.WS_NOWRAP : renderState.getWhiteSpace();
		int textTransform = renderState.getTextTransform();
		String text = textNode.getNodeValue();
		if (whiteSpace != RenderState.WS_PRE) {
			boolean prevAllowOverflow = this.currentLine.isAllowOverflow();
			boolean allowOverflow = whiteSpace == RenderState.WS_NOWRAP;
			this.currentLine.setAllowOverflow(allowOverflow);
			try {
				int length = text.length();
				StringBuilder word = new StringBuilder(12);
				for (int i = 0; i < length; i++) {
					char ch = text.charAt(i);
					if (Character.isWhitespace(ch)) {
						int wlen = word.length();
						if (wlen > 0) {
							RWord rword = new RWord(textNode, word.toString(), container, fm, descent,
									ascentPlusLeading, wordHeight, textTransform);
							this.addWordToLine(rword);
							word.delete(0, wlen);
						}
						RLine line = this.currentLine;
						if (line.width > 0) {
							RBlank rblank = new RBlank(textNode, fm, container, ascentPlusLeading, blankWidth,
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
					RWord rword = new RWord(textNode, word.toString(), container, fm, descent, ascentPlusLeading,
							wordHeight, textTransform);
					this.addWordToLine(rword);
					if (!Strings.isBlank(renderState.getlineHeight())){
						addLineBreak(this.modelNode, 0);
					}
				}
			} finally {
				this.currentLine.setAllowOverflow(prevAllowOverflow);
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
						RWord rword = new RWord(textNode, line.toString(), container, fm, descent, ascentPlusLeading,
								wordHeight, textTransform);
						this.addWordToLine(rword);
						line.delete(0, line.length());
					}
					RLine prevLine = this.currentLine;
					prevLine.setLineBreak(new LineBreak(LineBreak.NONE, textNode));
					this.addLine(textNode, prevLine, prevLine.y + prevLine.height);
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
				RWord rword = new RWord(textNode, line.toString(), container, fm, descent, ascentPlusLeading,
						wordHeight, textTransform);
				this.addWordToLine(rword);
			}
		}
	}
	
	private void populateZIndexGroups(ArrayList<BoundableRenderable> others,
			Iterator<BoundableRenderable> seqRenderablesIterator, ArrayList<BoundableRenderable> destination) {
		Iterator<BoundableRenderable> i1 = others.iterator();
		BoundableRenderable pending = null;
		while (i1.hasNext()) {
			BoundableRenderable r =  i1.next();
			if (r.getZIndex() >= 0) {
				pending = r;
				break;
			}
			destination.add(r);
		}

		// Second, sequential renderables
		Iterator<BoundableRenderable> i2 = seqRenderablesIterator;
		if (i2 != null) {
			while (i2.hasNext()) {
				destination.add(i2.next());
			}
		}

		// Third, other renderables with z-index >= 0.
		if (pending != null) {
			destination.add(pending);
			while (i1.hasNext()) {
				BoundableRenderable r =  i1.next();
				destination.add(r);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderer.RCollection#getRenderables()
	 */
	@Override
	public Iterator<BoundableRenderable> getRenderables() {
		SortedSet<PositionedRenderable> others = this.positionedRenderables;
		if (others == null || others.isEmpty()) {
			ArrayList<BoundableRenderable> sr = this.seqRenderables;
			return sr == null ? null : sr.iterator();
		} else {
			ArrayList<BoundableRenderable> sr = this.seqRenderables;
			ArrayList<BoundableRenderable> allRenderables = new ArrayList<BoundableRenderable>();
			ArrayList<BoundableRenderable> renderables = new ArrayList<BoundableRenderable>();
			
			for (PositionedRenderable s : others) {
				renderables.add(s.getRenderable());
			}
			
			this.populateZIndexGroups(renderables, sr.iterator(), allRenderables);
			return allRenderables.iterator();
		}
	}

	/**
	 * Gets the renderables.
	 *
	 * @param clipBounds
	 *            the clip bounds
	 * @return the renderables
	 */
	public Iterator<BoundableRenderable> getRenderables(Rectangle clipBounds) {
		ArrayList<BoundableRenderable> sr = this.seqRenderables;
		Iterator<BoundableRenderable> baseIterator = null;
		if (sr != null) {
			Renderable[] array = (Renderable[]) sr.toArray(Renderable.EMPTY_ARRAY);
			Range range = MarkupUtilities.findRenderables(array, clipBounds, true);
			baseIterator = ArrayUtilities.iterator(array, range.getOffset(), range.getLength());
		}
		SortedSet<?> others = this.positionedRenderables;
		if (others == null || others.isEmpty()) {
			return baseIterator;
		} else {
			ArrayList<PositionedRenderable> matches = new ArrayList<PositionedRenderable>();
			// ArrayList "matches" keeps the order from "others".
			Iterator<?> i = others.iterator();
			while (i.hasNext()) {
				PositionedRenderable pr = (PositionedRenderable) i.next();
				Object r = pr.getRenderable();
				if (r instanceof BoundableRenderable) {
					BoundableRenderable br = (BoundableRenderable) r;
					Rectangle rbounds = br.getBounds();
					if (clipBounds.intersects(rbounds)) {
						matches.add(pr);
					}
				}
			}
			if (matches.isEmpty()) {
				return baseIterator;
			} else {
				ArrayList<BoundableRenderable> destination = new ArrayList<BoundableRenderable>();
				
				ArrayList<BoundableRenderable> renderables = new ArrayList<BoundableRenderable>();
				for (PositionedRenderable s : matches) {
					renderables.add(s.getRenderable());
				}
				
				this.populateZIndexGroups(renderables, baseIterator, destination);
				return destination.iterator();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderer.BaseRCollection#getRenderable(int,
	 * int)
	 */
	@Override
	public BoundableRenderable getRenderable(int x, int y) {
		Iterator<?> i = this.getRenderables(x, y);
		return i == null ? null : i.hasNext() ? (BoundableRenderable) i.next() : null;
	}

	/**
	 * Gets the renderable.
	 *
	 * @param point
	 *            the point
	 * @return the renderable
	 */
	public BoundableRenderable getRenderable(Point point) {
		return this.getRenderable(point.x, point.y);
	}

	/**
	 * Gets the renderables.
	 *
	 * @param point
	 *            the point
	 * @return the renderables
	 */
	public Iterator<?> getRenderables(Point point) {
		return this.getRenderables(point.x, point.y);
	}

	/**
	 * Gets the renderables.
	 *
	 * @param pointx
	 *            the pointx
	 * @param pointy
	 *            the pointy
	 * @return the renderables
	 */
	public Iterator<?> getRenderables(int pointx, int pointy) {
		Collection<BoundableRenderable> result = null;
		SortedSet<?> others = this.positionedRenderables;
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
				BoundableRenderable r = pr.getRenderable();
				if (r.getZIndex() < 0) {
					break;
				}
				if (r instanceof BoundableRenderable) {
					BoundableRenderable br = r;
					Rectangle rbounds = br.getBounds();
					if (rbounds.contains(px, py)) {
						if (result == null) {
							result = new LinkedList<BoundableRenderable>();
						}
						result.add(br);
					}
				}
			}
		}

		// Now do a "binary" search on sequential renderables.
		ArrayList<BoundableRenderable> sr = this.seqRenderables;
		if (sr != null) {
			Renderable[] array = (Renderable[]) sr.toArray(Renderable.EMPTY_ARRAY);
			BoundableRenderable found = MarkupUtilities.findRenderable(array, pointx, pointy, true);
			if (found != null) {
				if (result == null) {
					result = new LinkedList<BoundableRenderable>();
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
				Renderable r = pr.getRenderable();
				if (r instanceof BoundableRenderable) {
					BoundableRenderable br = (BoundableRenderable) r;
					Rectangle rbounds = br.getBounds();
					if (rbounds.contains(px, py)) {
						if (result == null) {
							result = new LinkedList<BoundableRenderable>();
						}
						result.add(br);
					}
				}
			}
		}
		return result == null ? null : result.iterator();
	}

	/**
	 * Setup new ui control.
	 *
	 * @param container
	 *            the container
	 * @param element
	 *            the element
	 * @param control
	 *            the control
	 * @return the r element
	 */
	private RElement setupNewUIControl(RenderableContainer container, HTMLElementImpl element, UIControl control) {
		RElement renderable = new RUIControl(element, control, container, this.frameContext, this.userAgentContext);
		element.setUINode(renderable);
		return renderable;
	}

	/**
	 * Adds the alignable as block.
	 *
	 * @param markupElement
	 *            the markup element
	 * @param renderable
	 *            the renderable
	 */
	public final void addAlignableAsBlock(HTMLElementImpl markupElement, RElement renderable) {
		// TODO: Get rid of this method?
		// At this point block already has bounds.
		boolean regularAdd = false;
		String align = markupElement.getAttribute(ALIGN);
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

	/**
	 * Layout hr.
	 *
	 * @param markupElement
	 *            the markup element
	 */
	public final void layoutHr(HTMLElementImpl markupElement) {
		RElement renderable = (RElement) markupElement.getUINode();
		if (renderable == null) {
			renderable = this.setupNewUIControl(container, markupElement, new HrControl(markupElement));
		}
		renderable.layout(this.availContentWidth, this.availContentHeight, this.sizeOnly);
		this.addAlignableAsBlock(markupElement, renderable);
	}

	/**
	 * Gets offset from the left due to floats. It includes padding.
	 *
	 * @param newLineY
	 *            the new line y
	 * @return the int
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
	 *
	 * @param newLineY
	 *            the new line y
	 * @return the int
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

	/**
	 * Check y.
	 *
	 * @param y
	 *            the y
	 */
	public final void checkY(int y) {
		if (this.yLimit != -1 && y > this.yLimit) {
			throw SEE;
		}
	}

	/**
	 * Layout float.
	 *
	 * @param renderable
	 *            the renderable
	 * @param layout
	 *            the layout
	 * @param leftFloat
	 *            the left float
	 */
	public final void layoutFloat(BoundableRenderable renderable, boolean layout, boolean leftFloat) {
		renderable.setOriginalParent(this);
		if (layout) {
			int availWidth = this.availContentWidth;
			int availHeight = this.availContentHeight;
			if (renderable instanceof RBlock) {
				RBlock block = (RBlock) renderable;
				// Float boxes don't inherit float bounds?
				block.layout(availWidth, availHeight, false, false, null, this.sizeOnly);
			} else if (renderable instanceof RElement) {
				RElement e = (RElement) renderable;
				e.layout(availWidth, availHeight, this.sizeOnly);
			}
		}
		RFloatInfo floatInfo = new RFloatInfo(renderable.getModelNode(), renderable, leftFloat);
		this.currentLine.simplyAdd(floatInfo);
		this.scheduleFloat(floatInfo);
	}

	/**
	 * Schedule abs delayed pair.
	 *
	 * @param renderable
	 *            the renderable
	 */
	private void scheduleAbsDelayedPair(final BoundableRenderable renderable, String leftText, String rightText,
			String topText, String bottomText, RenderState rs, int currY) {
		// It gets reimported in the local
		// viewport if it turns out it can't be exported up.
		RenderableContainer container = this.container;
		while(true) {
			if (container instanceof Renderable) {
				Object node = ((Renderable) container).getModelNode();
				if (node instanceof HTMLElementImpl) {
					HTMLElementImpl element = (HTMLElementImpl) node;
					int position = getPosition(element);
					if (position != RenderState.POSITION_STATIC) {
						break;
					}
					RenderableContainer newContainer = container.getParentContainer();
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
		DelayedPair pair = new DelayedPair(this.container, container, renderable, leftText, rightText, topText, bottomText, rs, currY);
		this.container.addDelayedPair(pair);
	}

	/**
	 * Import delayed pair.
	 *
	 * @param pair
	 *            the pair
	 */
	private void importDelayedPair(DelayedPair pair) {
		pair.positionPairChild();
		BoundableRenderable r = pair.child;
		this.addPositionedRenderable(r, false, false);
	}

	/**
	 * Adds the positioned renderable.
	 *
	 * @param renderable
	 *            the renderable
	 * @param verticalAlignable
	 *            the vertical alignable
	 * @param isFloat
	 *            the is float
	 */
	public final void addPositionedRenderable(BoundableRenderable renderable, boolean verticalAlignable,
			boolean isFloat) {
		// Expected to be called only in GUI thread.
		SortedSet<PositionedRenderable> others = this.positionedRenderables;
		if (others == null) {
			others = new TreeSet<PositionedRenderable>(new ZIndexComparator());
			this.positionedRenderables = others;
		}
		others.add(new PositionedRenderable(renderable, verticalAlignable, this.positionedOrdinal++, isFloat));
		renderable.setParent(this);
		if (renderable instanceof RUIControl) {
			this.container.addComponent(((RUIControl) renderable).getWidget().getComponent());
		}
	}

	/**
	 * Gets the first line height.
	 *
	 * @return the first line height
	 */
	public int getFirstLineHeight() {
		ArrayList<?> renderables = this.seqRenderables;
		if (renderables != null) {
			int size = renderables.size();
			if (size == 0) {
				return 0;
			}
			for (int i = 0; i < size; i++) {
				BoundableRenderable br = (BoundableRenderable) renderables.get(0);
				int height = br.getHeight();
				if (height != 0) {
					return height;
				}
			}
		}
		// Not found!!
		return 1;
	}

	/**
	 * Gets the first baseline offset.
	 *
	 * @return the first baseline offset
	 */
	public int getFirstBaselineOffset() {
		ArrayList<?> renderables = this.seqRenderables;
		if (renderables != null) {
			Iterator<?> i = renderables.iterator();
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
						return block.getFirstBaselineOffset() + insets.top
								+ (paddingInsets == null ? 0 : paddingInsets.top);
					}
				}
			}
		}
		return 0;
	}

	// ----------------------------------------------------------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.renderer.BoundableRenderable#getLowestRenderableSpot
	 * (int, int)
	 */
	@Override
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
	 * org.loboevolution.html.render.BoundableRenderable#onMouseClick(java.awt.
	 * event .MouseEvent, int, int)
	 */
	@Override
	public boolean onMouseClick(MouseEvent event, int x, int y) {
		Iterator<?> i = this.getRenderables(new Point(x, y));
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.renderer.BoundableRenderable#onDoubleClick(java.awt.
	 * event.MouseEvent, int, int)
	 */
	@Override
	public boolean onDoubleClick(MouseEvent event, int x, int y) {
		Iterator<?> i = this.getRenderables(new Point(x, y));
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
	 * org.loboevolution.html.render.BoundableRenderable#onMouseDisarmed(java.awt
	 * .event.MouseEvent)
	 */
	@Override
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
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.render.BoundableRenderable#onMousePressed(java.awt.
	 * event.MouseEvent, int, int)
	 */
	@Override
	public boolean onMousePressed(MouseEvent event, int x, int y) {
		Iterator<?> i = this.getRenderables(new Point(x, y));
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
	 * org.loboevolution.html.render.BoundableRenderable#onMouseReleased(java.awt
	 * .event.MouseEvent, int, int)
	 */
	@Override
	public boolean onMouseReleased(MouseEvent event, int x, int y) {
		Iterator<?> i = this.getRenderables(new Point(x, y));
		if (i != null) {
			while (i.hasNext()) {
				BoundableRenderable br = (BoundableRenderable) i.next();
				if (br != null) {
					Rectangle bounds = br.getBounds();
					if (!br.onMouseReleased(event, x - bounds.x, y - bounds.y)) {
						BoundableRenderable oldArmedRenderable = this.armedRenderable;
						if (oldArmedRenderable != null && !oldArmedRenderable.equals(br)) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.renderer.Renderable#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		Rectangle clipBounds = g.getClipBounds();
		Iterator<?> i = this.getRenderables(clipBounds);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.renderer.BoundableRenderable#isContainedByNode()
	 */
	@Override
	public boolean isContainedByNode() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RBlockViewport[node=" + this.modelNode + "]";
	}

	/**
	 * Schedule float.
	 *
	 * @param floatInfo
	 *            the float info
	 */
	private void scheduleFloat(RFloatInfo floatInfo) {
		RLine line = this.currentLine;
		if (line == null) {
			int y = line == null ? this.paddingInsets.top : line.getY();
			this.placeFloat(floatInfo.getRenderable(), y, floatInfo.isLeftFloat());
		} else if (line.getWidth() == 0) {
			int y = line.getY();
			this.placeFloat(floatInfo.getRenderable(), y, floatInfo.isLeftFloat());
			int leftOffset = this.fetchLeftOffset(y);
			int rightOffset = this.fetchRightOffset(y);
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

	/**
	 * Line done.
	 *
	 * @param line
	 *            the line
	 */
	private void lineDone(RLine line) {
		int yAfterLine = line == null ? this.paddingInsets.top : line.y + line.height;
		Collection<RFloatInfo> pfs = this.pendingFloats;
		if (pfs != null) {
			this.pendingFloats = null;
			Iterator<RFloatInfo> i = pfs.iterator();
			while (i.hasNext()) {
				RFloatInfo pf = (RFloatInfo) i.next();
				this.placeFloat(pf.getRenderable(), yAfterLine, pf.isLeftFloat());
			}
		}
	}

	/**
	 * Adds the exportable float.
	 *
	 * @param element
	 *            the element
	 * @param leftFloat
	 *            the left float
	 * @param origX
	 *            the orig x
	 * @param origY
	 *            the orig y
	 */
	private void addExportableFloat(BoundableRenderable element, boolean leftFloat, int origX, int origY) {
		ArrayList<ExportableFloat> ep = this.exportableFloats;
		if (ep == null) {
			ep = new ArrayList<ExportableFloat>(1);
			this.exportableFloats = ep;
		}
		ep.add(new ExportableFloat(element, leftFloat, origX, origY));
	}

	/**
	 * Place float.
	 *
	 * @param element
	 *            the element
	 * @param y
	 *            The desired top position of the float element.
	 * @param leftFloat
	 *            the left float
	 */
	private void placeFloat(BoundableRenderable element, int y, boolean leftFloat) {
		Insets insets = this.paddingInsets;
		int boxY = y;
		int boxWidth = element.getWidth();
		int boxHeight = element.getHeight();
		int desiredWidth = this.desiredWidth;
		int boxX;
		while(true) {
			int leftOffset = this.fetchLeftOffset(boxY);
			int rightOffset = this.fetchRightOffset(boxY);
			boxX = leftFloat ? leftOffset : desiredWidth - rightOffset - boxWidth;
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
					int availableBoxWidth = desiredWidth - rightOffset - leftOffset;
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
		int offsetFromBorder = leftFloat ? boxX + boxWidth : desiredWidth - boxX;
		this.floatBounds = new FloatingViewportBounds(this.floatBounds, leftFloat, boxY, offsetFromBorder, boxHeight);
		// Add element to collection
		boolean isFloatLimit = this.isFloatLimitImpl();
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
		if (this.isFloatLimitImpl() && boxY + boxHeight > this.maxY) {
			this.maxY = boxY + boxHeight;
		}

	}
	
	/**
	 * Checks if is float limit impl.
	 *
	 * @return the boolean
	 */
	private Boolean isFloatLimitImpl() {
		Boolean fl = this.isFloatLimit;
		if (fl == null) {
			Object parent = this.getOriginalOrCurrentParent();
			if (!(parent instanceof RBlock)) {
				fl = Boolean.TRUE;
				this.isFloatLimit = Boolean.TRUE;
			}
			RBlock blockParent = (RBlock) parent;
			Object grandParent = blockParent.getOriginalOrCurrentParent();
			if (!(grandParent instanceof RBlockViewport)) {
				fl = Boolean.TRUE;
				this.isFloatLimit = Boolean.TRUE;
			}
			ModelNode node = this.modelNode;
			if (!(node instanceof HTMLElementImpl)) {
				fl = Boolean.TRUE;
				this.isFloatLimit = Boolean.TRUE;
			} else {
				HTMLElementImpl element = (HTMLElementImpl) node;
				int position = getPosition(element);
				if (position == RenderState.POSITION_ABSOLUTE || position == RenderState.POSITION_FIXED) {
					fl = Boolean.TRUE;
					this.isFloatLimit = Boolean.TRUE;
				}
				element.getCurrentStyle();
				RenderState rs = element.getRenderState();
				int floatValue = rs == null ? RenderState.FLOAT_NONE : rs.getFloat();
				if (floatValue != RenderState.FLOAT_NONE) {
					fl = Boolean.TRUE;
					this.isFloatLimit = Boolean.TRUE;
				}
				int overflowX = rs == null ? RenderState.OVERFLOW_NONE : rs.getOverflowX();
				int overflowY = rs == null ? RenderState.OVERFLOW_NONE : rs.getOverflowY();
				if (overflowX == RenderState.OVERFLOW_AUTO || overflowX == RenderState.OVERFLOW_SCROLL
						|| overflowY == RenderState.OVERFLOW_AUTO || overflowY == RenderState.OVERFLOW_SCROLL) {
					fl = Boolean.TRUE;
					this.isFloatLimit = Boolean.TRUE;
				}
				fl = Boolean.TRUE;
				this.isFloatLimit = Boolean.TRUE;
			}
		}
		return fl.booleanValue();
	}

	/**
	 * Gets the exportable floating info.
	 *
	 * @return the exportable floating info
	 */
	public FloatingInfo getExportableFloatingInfo() {
		ArrayList<ExportableFloat> ef = this.exportableFloats;
		if (ef == null) {
			return null;
		}
		ExportableFloat[] floats = ef.toArray(ExportableFloat.EMPTY_ARRAY);
		return new FloatingInfo(0, 0, floats);
	}

	/**
	 * Import floating info.
	 *
	 * @param floatingInfo
	 *            the floating info
	 * @param block
	 *            the block
	 */
	private void importFloatingInfo(FloatingInfo floatingInfo, BoundableRenderable block) {
		int shiftX = floatingInfo.getShiftX() + block.getX();
		int shiftY = floatingInfo.getShiftY() + block.getY();
		ExportableFloat[] floats = floatingInfo.getFloats();
		for (ExportableFloat ef : floats) {
			this.importFloat(ef, shiftX, shiftY);
		}
	}

	/**
	 * Import float.
	 *
	 * @param ef
	 *            the ef
	 * @param shiftX
	 *            the shift x
	 * @param shiftY
	 *            the shift y
	 */
	private void importFloat(ExportableFloat ef, int shiftX, int shiftY) {
		BoundableRenderable renderable = ef.getElement();
		int newX = ef.getOrigX() + shiftX;
		int newY = ef.getOrigY() + shiftY;
		renderable.setOrigin(newX, newY);
		FloatingBounds prevBounds = this.floatBounds;
		int offsetFromBorder;
		boolean leftFloat = ef.isLeftFloat();
		if (leftFloat) {
			offsetFromBorder = newX + renderable.getWidth();
		} else {
			offsetFromBorder = this.desiredWidth - newX;
		}
		this.floatBounds = new FloatingViewportBounds(prevBounds, leftFloat, newY, offsetFromBorder,
				renderable.getHeight());
		if (this.isFloatLimitImpl()) {
			this.addPositionedRenderable(renderable, true, true);
		} else {
			this.addExportableFloat(renderable, leftFloat, newX, newY);
		}
	}

	public void positionDelayed() {
		final Collection<DelayedPair> delayedPairs = container.getDelayedPairs();
		if (delayedPairs != null && !delayedPairs.isEmpty()) {
			// Add positioned renderables that belong here
			final Iterator<DelayedPair> i = delayedPairs.iterator();
			while (i.hasNext()) {
				final DelayedPair pair = i.next();
				if (pair.containingBlock == container) {
					this.importDelayedPair(pair);
				}
			}
		}
	}

	/**
	 * Layout r inline block.
	 *
	 * @param markupElement
	 *            the markup element
	 */
	public void layoutRInlineBlock(final HTMLElementImpl markupElement) {
		UINode uINode = markupElement.getUINode();
		RInlineBlock inlineBlock = null;
		if (uINode instanceof RInlineBlock) {
			inlineBlock = (RInlineBlock) uINode;
		} else {
			final RInlineBlock newInlineBlock = new RInlineBlock(container, markupElement, userAgentContext,
					rendererContext, frameContext);
			markupElement.setUINode(newInlineBlock);
			inlineBlock = newInlineBlock;
		}
		inlineBlock.doLayout(availContentWidth, availContentHeight, sizeOnly);
		addRenderableToLine(inlineBlock);
		inlineBlock.setOriginalParent(inlineBlock.getParent());
		layoutRelative(markupElement, inlineBlock);
	}

	/* This is used to bubble up relative elements (on the z-axis) */
	private boolean layoutRelative(final HTMLElementImpl markupElement, final RElement renderable) {
		int position = getPosition(markupElement);
		boolean isRelative = position == RenderState.POSITION_RELATIVE;
		if (isRelative) {
			RenderableContainer con = getPositionedAncestor(container);
			DelayedPair dp = new DelayedPair(container, con, renderable, null, null, null, null, null, position);
			container.addDelayedPair(dp);
			if (renderable instanceof RUIControl) {
				this.container.addComponent(((RUIControl) renderable).getWidget().getComponent());
			}
			return true;
		}

		return false;
	}

	/**
	 * Gets an ancestor which is "positioned" (that is whose position is not
	 * static). Stops searching when HTML element is encountered.
	 */
	private static RenderableContainer getPositionedAncestor(RenderableContainer containingBlock) {
		while(true) {
			if (containingBlock instanceof Renderable) {
				final ModelNode node = ((Renderable) containingBlock).getModelNode();
				if (node instanceof HTMLElementImpl) {
					HTMLElementImpl element = (HTMLElementImpl) node;
					int position = getPosition(element);
					// if (position != RenderState.POSITION_STATIC || (element
					// instanceof HTMLHtmlElement)) {
					if (position != RenderState.POSITION_STATIC) {
						break;
					}
					RenderableContainer newContainer = containingBlock.getParentContainer();
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

	/**
	 * Gets the frame context.
	 *
	 * @return the frame context
	 */
	public FrameContext getFrameContext() {
		return frameContext;
	}

	/**
	 * Gets the container.
	 *
	 * @return the container
	 */
	public RenderableContainer getContainer() {
		return container;
	}

	/**
	 * Gets the user agent context.
	 *
	 * @return the user agent context
	 */
	public UserAgentContext getUserAgentContext() {
		return userAgentContext;
	}

	/**
	 * Gets the renderer context.
	 *
	 * @return the renderer context
	 */
	public HtmlRendererContext getRendererContext() {
		return rendererContext;
	}

}