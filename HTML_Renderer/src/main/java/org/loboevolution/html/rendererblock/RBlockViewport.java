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

import org.loboevolution.arraylist.ArrayUtilities;
import org.loboevolution.html.HtmlAttributeProperties;
import org.loboevolution.html.HtmlRendererContext;
import org.loboevolution.html.control.RUIControl;
import org.loboevolution.html.dombl.ModelNode;
import org.loboevolution.html.domimpl.DOMNodeImpl;
import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.html.info.FloatingInfo;
import org.loboevolution.html.renderer.BaseRCollection;
import org.loboevolution.html.renderer.BoundableRenderable;
import org.loboevolution.html.renderer.DelayedPair;
import org.loboevolution.html.renderer.ExportableFloat;
import org.loboevolution.html.renderer.FloatingBounds;
import org.loboevolution.html.renderer.FloatingBoundsSource;
import org.loboevolution.html.renderer.FloatingViewportBounds;
import org.loboevolution.html.renderer.FrameContext;
import org.loboevolution.html.renderer.MarkupUtilities;
import org.loboevolution.html.renderer.ParentFloatingBoundsSource;
import org.loboevolution.html.renderer.PositionedRenderable;
import org.loboevolution.html.renderer.RCollection;
import org.loboevolution.html.renderer.RElement;
import org.loboevolution.html.renderer.RFloatInfo;
import org.loboevolution.html.renderer.RLine;
import org.loboevolution.html.renderer.RRelative;
import org.loboevolution.html.renderer.RSpacing;
import org.loboevolution.html.renderer.Range;
import org.loboevolution.html.renderer.Renderable;
import org.loboevolution.html.renderer.RenderableContainer;
import org.loboevolution.html.renderer.RenderableSpot;
import org.loboevolution.html.renderer.SizeExceededException;
import org.loboevolution.html.renderer.ZIndexComparator;
import org.loboevolution.html.renderertable.RTable;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.util.Strings;

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
	private List<BoundableRenderable> seqRenderables = null;

	/** The exportable floats. */
	private List<ExportableFloat> exportableFloats = null;

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
		    this.setPaddingInsets(paddingInsets);
		    this.yLimit = yLimit;
		    this.desiredHeight = desiredHeight;
		    this.setDesiredWidth(desiredWidth);
		    this.setFloatBounds(floatBounds);
		    this.isFloatLimit = null;
		    this.setPendingFloats(null);
		    this.setSizeOnly(sizeOnly);
		    this.lastSeqBlock = null;
		    this.setCurrentCollapsibleMargin(0);

		    // maxX and maxY should not be reset by layoutPass.
		    this.setMaxX(paddingInsets.left);
		    this.maxY = paddingInsets.top;

		    int availw = desiredWidth - paddingInsets.left - paddingInsets.right;
		    if (availw < 0) {
		      availw = 0;
		    }
		    int availh = desiredHeight - paddingInsets.top - paddingInsets.bottom;
		    if (availh == 0) {
		      availh = 0;
		    }
		    this.setAvailContentHeight(availh);
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
		    final RLine lastLine = this.getCurrentLine();
		    final Rectangle lastBounds = lastLine.getBounds();
		    if ((lastBounds.height > 0) || (lastBounds.y > maxYWholeBlock)) {
		      final int lastTopX = lastBounds.x + lastBounds.width;
		      if (lastTopX > this.getMaxX()) {
		        this.setMaxX(lastTopX);
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
		        if ((br.getX() + br.getWidth()) > this.getMaxX()) {
		          this.setMaxX(br.getX() + br.getWidth());
		        }
		        if (isFloatLimit || !pr.isFloat()) {
		          if ((br.getY() + br.getHeight()) > maxY) {
		            this.maxY = maxY = br.getY() + br.getHeight();
		          }
		        }
		      }
		    }

		    this.width = paddingInsets.right + this.getMaxX();
		    this.height = paddingInsets.bottom + maxY;
		}

	/**
	 * Layout pass.
	 *
	 * @param rootNode
	 *            the root node
	 */
	private void layoutPass(DOMNodeImpl rootNode) {
		RBlockLine line = new RBlockLine();
		RblockLayout ly = new RblockLayout();
		RenderableContainer container = this.container;
		container.clearDelayedPairs();
		this.positionedOrdinal = 0;

		// Remove sequential renderables...
		this.setSeqRenderables(null);

		// Remove other renderables...
		this.positionedRenderables = null;

		// Remove exporatable floats...
		this.exportableFloats = null;

		// Call addLine after setting margins
		
		this.setCurrentLine(line.addLine(rootNode, this, null, this.getPaddingInsets().top));

		// Start laying out...
		// The parent is expected to have set the RenderState already.
		ly.layoutChildren(rootNode, this);

		// This adds last-line floats.
		line.lineDone(this.getCurrentLine(), this);
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
	      final List<BoundableRenderable> renderables = this.getSeqRenderables();
	      if (renderables != null) {
	        final Insets insets = this.getPaddingInsets();
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
	        final List<BoundableRenderable> rlist = this.getSeqRenderables();
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
	 * Layout markup.
	 *
	 * @param node
	 *            the node
	 */
	public void layoutMarkup(final DOMNodeImpl node) {
		RblockLayout ly = new RblockLayout();
	    final RenderState rs = node.getRenderState();
	    final HtmlInsets mi = rs.getMarginInsets();
	    final Insets marginInsets = mi == null ? null : mi.getSimpleAWTInsets(this.availContentWidth, this.getAvailContentHeight());
	    final HtmlInsets pi = rs.getPaddingInsets();
	    final Insets paddingInsets = pi == null ? null : pi.getSimpleAWTInsets(this.availContentWidth, this.getAvailContentHeight());

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
	      final RLine line = this.getCurrentLine();
	      line.addSpacing(new RSpacing(node, this.container, leftSpacing, line.height));
	    }
	    ly.layoutChildren(node, this);
	    if (rightSpacing > 0) {
	      final RLine line = this.getCurrentLine();
	      line.addSpacing(new RSpacing(node, this.container, rightSpacing, line.height));
	    }
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
			RblockLayout ly = new RblockLayout();
			if ("left".equalsIgnoreCase(align)) {
				ly.layoutFloat(renderable, this, layout, true);
				return true;
			} else if ("right".equalsIgnoreCase(align)) {
				ly.layoutFloat(renderable, this, layout, false);
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
	protected int getPosition(HTMLElementImpl element) {
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
	protected boolean addElsewhereIfPositioned(RElement renderable, HTMLElementImpl element, boolean usesAlignAttribute,
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
						Insets paddingInsets = this.getPaddingInsets();
						RLine line = this.getCurrentLine();
						RBlockLine bLine = new RBlockLine();
						bLine.lineDone(line, this);
						int newY = line == null ? paddingInsets.top : line.y + line.height;
						final int expectedWidth = this.availContentWidth;
						final int blockShiftRight = paddingInsets.right;
						final int newX = paddingInsets.left;
						FloatingBounds floatBounds = this.getFloatBounds();
						inheritedFloatBoundsSource = floatBounds == null ? null
								: new ParentFloatingBoundsSource(blockShiftRight, expectedWidth, newX, newY,
										floatBounds);
					}

					boolean floating = element.getRenderState().getFloat() != RenderState.FLOAT_NONE;
					boolean growHorizontally = relative && !floating;
					block.layout(this.availContentWidth, this.getAvailContentHeight(), growHorizontally, false,
							inheritedFloatBoundsSource, this.isSizeOnly());

				} else {
					renderable.layout(this.availContentWidth, this.getAvailContentHeight(), this.isSizeOnly());
				}
			}
			RenderState rs = element.getRenderState();
			String leftText = style.getLeft();
			String rightText = style.getRight();
			String bottomText = style.getBottom();
			String topText = style.getTop();
			RLine line = this.getCurrentLine();
			int lineBottomY = line == null ? 0 : line.getY() + line.getHeight();
			int newLeft;
			if (leftText != null) {
				newLeft = HtmlValues.getPixelSize(leftText, rs, 0, this.availContentWidth);
			} else {
				if (rightText != null) {
					int right = HtmlValues.getPixelSize(rightText, rs, 0, this.availContentWidth);
					newLeft = this.getDesiredWidth() - right - renderable.getWidth();
					// If right==0 and renderable.width is larger than the
					// parent's width,
					// the expected behavior is for newLeft to be negative.
				} else {
					newLeft = 0;
				}
			}
			int newTop = relative ? 0 : lineBottomY;
			if (topText != null) {
				newTop = HtmlValues.getPixelSize(topText, rs, newTop, this.getAvailContentHeight());
			} else {
				if (bottomText != null) {
					int bottom = HtmlValues.getPixelSize(bottomText, rs, 0, this.getAvailContentHeight());
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
						getCurrentLine().getY() + getCurrentLine().getHeight());
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
	public void addRenderableToLineCheckStyle(RElement renderable, HTMLElementImpl element, boolean usesAlignAttribute) {
		if (this.addElsewhereIfPositioned(renderable, element, usesAlignAttribute, true, true)) {
			return;
		}
		renderable.layout(this.availContentWidth, this.getAvailContentHeight(), this.isSizeOnly());
		RBlockLine line = new RBlockLine();
		line.addRenderableToLine(renderable, this);
		RblockLayout ly = new RblockLayout();
		ly.layoutRelative(element, renderable, this);
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
		int ccm = this.getCurrentCollapsibleMargin();
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
	protected void addAsSeqBlock(BoundableRenderable block, boolean obeysFloats, boolean informLineDone, boolean addLine,
			boolean centerBlock) {
		Insets insets = this.getPaddingInsets();
		int insetsl = insets.left;
		List<BoundableRenderable> sr = this.getSeqRenderables();
		if (sr == null) {
			sr = new ArrayList<BoundableRenderable>(1);
			this.setSeqRenderables(sr);
		}
		RLine prevLine = this.getCurrentLine();
		boolean initialAllowOverflow;
		if (prevLine != null) {
			initialAllowOverflow = prevLine.isAllowOverflow();
			if (informLineDone) {
				RBlockLine bLine = new RBlockLine();
				bLine.lineDone(prevLine, this);
			}
			if (prevLine.x + prevLine.width > this.getMaxX()) {
				this.setMaxX(prevLine.x + prevLine.width);
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
			FloatingBounds floatBounds = this.getFloatBounds();
			int actualAvailWidth;
			if (floatBounds != null) {
				int blockOffset = this.fetchLeftOffset(newLineY);
				blockX = blockOffset;
				int rightOffset = this.fetchRightOffset(newLineY);
				actualAvailWidth = this.getDesiredWidth() - rightOffset - blockOffset;
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
		if (blockX + blockWidth > this.getMaxX()) {
			this.setMaxX(blockX + blockWidth);
		}
		this.lastSeqBlock = block;
		this.setCurrentCollapsibleMargin(block instanceof RElement ? ((RElement) block).getMarginBottom() : 0);
		if (addLine) {
			newLineY = blockY + block.getHeight();
			this.checkY(newLineY);
			int leftOffset = this.fetchLeftOffset(newLineY);
			int newX = leftOffset;
			int newMaxWidth = this.getDesiredWidth() - this.fetchRightOffset(newLineY) - leftOffset;
			ModelNode lineNode = block.getModelNode().getParentModelNode();
			RLine newLine = new RLine(lineNode, this.container, newX, newLineY, newMaxWidth, 0, initialAllowOverflow);
			newLine.setParent(this);
			sr.add(newLine);
			this.setCurrentLine(newLine);
		}
	}
	
	private void populateZIndexGroups(List<BoundableRenderable> others,
			Iterator<BoundableRenderable> seqRenderablesIterator, List<BoundableRenderable> destination) {
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
			List<BoundableRenderable> sr = this.getSeqRenderables();
			return sr == null ? null : sr.iterator();
		} else {
			List<BoundableRenderable> sr = this.getSeqRenderables();
			List<BoundableRenderable> allRenderables = new ArrayList<BoundableRenderable>();
			List<BoundableRenderable> renderables = new ArrayList<BoundableRenderable>();
			
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
		List<BoundableRenderable> sr = this.getSeqRenderables();
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
			List<PositionedRenderable> matches = new ArrayList<PositionedRenderable>();
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
				List<BoundableRenderable> destination = new ArrayList<BoundableRenderable>();
				
				List<BoundableRenderable> renderables = new ArrayList<BoundableRenderable>();
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
		List<BoundableRenderable> sr = this.getSeqRenderables();
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
			RblockLayout ly = new RblockLayout();
			if ("left".equalsIgnoreCase(align)) {
				ly.layoutFloat(renderable, this, false, true);
			} else if ("right".equalsIgnoreCase(align)) {
				ly.layoutFloat(renderable, this, false, false);
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
	 * Gets offset from the left due to floats. It includes padding.
	 *
	 * @param newLineY
	 *            the new line y
	 * @return the int
	 */
	public final int fetchLeftOffset(int newLineY) {
		Insets paddingInsets = this.getPaddingInsets();
		FloatingBounds floatBounds = this.getFloatBounds();
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
		Insets paddingInsets = this.getPaddingInsets();
		FloatingBounds floatBounds = this.getFloatBounds();
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
		List<?> renderables = this.getSeqRenderables();
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
		List<?> renderables = this.getSeqRenderables();
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
						Insets paddingInsets = this.getPaddingInsets();
						return block.getFirstBaselineOffset() + insets.top
								+ (paddingInsets == null ? 0 : paddingInsets.top);
					}
				}
			}
		}
		return 0;
	}

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
		List<ExportableFloat> ep = this.exportableFloats;
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
	protected void placeFloat(BoundableRenderable element, int y, boolean leftFloat) {
		Insets insets = this.getPaddingInsets();
		int boxY = y;
		int boxWidth = element.getWidth();
		int boxHeight = element.getHeight();
		int desiredWidth = this.getDesiredWidth();
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
					relement.layout(availableBoxWidth, this.getAvailContentHeight(), this.isSizeOnly());
					if (relement.getWidth() < boxWidth) {
						if (relement.getWidth() > desiredWidth - rightOffset - leftOffset) {
							// Didn't work out. Put it back the way it was.
							relement.layout(this.availContentWidth, this.getAvailContentHeight(), this.isSizeOnly());
						} else {
							// Retry
							boxWidth = relement.getWidth();
							boxHeight = relement.getHeight();
							continue;
						}
					}
				}
			}
			FloatingBounds fb = this.getFloatBounds();
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
		this.setFloatBounds(new FloatingViewportBounds(this.getFloatBounds(), leftFloat, boxY, offsetFromBorder, boxHeight));
		// Add element to collection
		boolean isFloatLimit = this.isFloatLimitImpl();
		if (isFloatLimit) {
			this.addPositionedRenderable(element, true, true);
		} else {
			this.addExportableFloat(element, leftFloat, boxX, boxY);
		}
		// Adjust maxX based on float.
		if (boxX + boxWidth > this.getMaxX()) {
			this.setMaxX(boxX + boxWidth);
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
		List<ExportableFloat> ef = this.exportableFloats;
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
	protected void importFloatingInfo(FloatingInfo floatingInfo, BoundableRenderable block) {
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
		FloatingBounds prevBounds = this.getFloatBounds();
		int offsetFromBorder;
		boolean leftFloat = ef.isLeftFloat();
		if (leftFloat) {
			offsetFromBorder = newX + renderable.getWidth();
		} else {
			offsetFromBorder = this.getDesiredWidth() - newX;
		}
		this.setFloatBounds(new FloatingViewportBounds(prevBounds, leftFloat, newY, offsetFromBorder,
				renderable.getHeight()));
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
	 * Gets the avail content width.
	 *
	 * @return the avail content width
	 */
	public int getAvailContentWidth() {
		return this.availContentWidth;
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

	/**
	 * @return the overrideNoWrap
	 */
	public boolean isOverrideNoWrap() {
		return overrideNoWrap;
	}

	/**
	 * @param overrideNoWrap the overrideNoWrap to set
	 */
	public void setOverrideNoWrap(boolean overrideNoWrap) {
		this.overrideNoWrap = overrideNoWrap;
	}

	/**
	 * @return the currentLine
	 */
	public RLine getCurrentLine() {
		return currentLine;
	}

	/**
	 * @param currentLine the currentLine to set
	 */
	public void setCurrentLine(RLine currentLine) {
		this.currentLine = currentLine;
	}

	/**
	 * @return the floatBounds
	 */
	public FloatingBounds getFloatBounds() {
		return floatBounds;
	}

	/**
	 * @param floatBounds the floatBounds to set
	 */
	public void setFloatBounds(FloatingBounds floatBounds) {
		this.floatBounds = floatBounds;
	}

	/**
	 * @return the desiredWidth
	 */
	public int getDesiredWidth() {
		return desiredWidth;
	}

	/**
	 * @param desiredWidth the desiredWidth to set
	 */
	public void setDesiredWidth(int desiredWidth) {
		this.desiredWidth = desiredWidth;
	}

	/**
	 * @return the paddingInsets
	 */
	public Insets getPaddingInsets() {
		return paddingInsets;
	}

	/**
	 * @param paddingInsets the paddingInsets to set
	 */
	public void setPaddingInsets(Insets paddingInsets) {
		this.paddingInsets = paddingInsets;
	}

	public int getCurrentCollapsibleMargin() {
		return currentCollapsibleMargin;
	}

	public void setCurrentCollapsibleMargin(int currentCollapsibleMargin) {
		this.currentCollapsibleMargin = currentCollapsibleMargin;
	}

	public int getMaxX() {
		return maxX;
	}

	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}

	public List<BoundableRenderable> getSeqRenderables() {
		return seqRenderables;
	}

	public void setSeqRenderables(List<BoundableRenderable> seqRenderables) {
		this.seqRenderables = seqRenderables;
	}

	public Collection<RFloatInfo> getPendingFloats() {
		return pendingFloats;
	}

	public void setPendingFloats(Collection<RFloatInfo> pendingFloats) {
		this.pendingFloats = pendingFloats;
	}

	public boolean isSizeOnly() {
		return sizeOnly;
	}

	public void setSizeOnly(boolean sizeOnly) {
		this.sizeOnly = sizeOnly;
	}

	public int getAvailContentHeight() {
		return availContentHeight;
	}

	public void setAvailContentHeight(int availContentHeight) {
		this.availContentHeight = availContentHeight;
	}

	public int getListNesting() {
		return listNesting;
	}

	public void setListNesting(int listNesting) {
		this.listNesting = listNesting;
	}

}