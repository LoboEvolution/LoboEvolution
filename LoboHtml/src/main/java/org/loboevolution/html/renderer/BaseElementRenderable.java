/*  GNU LESSER GENERAL PUBLIC LICENSE
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

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import org.loboevolution.common.GUITasks;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.domimpl.ModelNode;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.BorderInsets;
import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.info.BackgroundInfo;
import org.loboevolution.info.BorderInfo;
import org.loboevolution.laf.ColorFactory;
import org.loboevolution.net.HttpNetwork;
import org.w3c.dom.css.CSS3Properties;

/**
 * <p>Abstract BaseElementRenderable class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public abstract class BaseElementRenderable extends BaseRCollection
		implements RElement, RenderableContainer, ImageObserver {
	/** Constant INVALID_SIZE */
	protected static final Integer INVALID_SIZE = Integer.MIN_VALUE;

	/** Constant SCROLL_BAR_THICKNESS=16 */
	protected static final int SCROLL_BAR_THICKNESS = 16;

	protected Color backgroundColor;

	protected volatile Image backgroundImage;
	protected Color borderBottomColor;
	protected BorderInfo borderInfo;
	protected Insets borderInsets;
	protected Color borderLeftColor;
	protected Color borderRightColor;
	protected Color borderTopColor;
	private Integer declaredHeight = INVALID_SIZE;
	private Integer declaredWidth = INVALID_SIZE;
	protected List<DelayedPair> delayedPairs = null;

	private Collection<Component> guiComponents = null;
	private int lastAvailHeightForDeclared = -1;
	private int lastAvailWidthForDeclared = -1;
	protected URL lastBackgroundImageUri;

	protected boolean layoutDeepCanBeInvalidated = false;

	protected Insets marginInsets;

	protected int overflowX;

	protected int overflowY;

	protected Insets paddingInsets;

	protected final UserAgentContext userAgentContext;

	protected int zIndex;
	
	private BackgroundInfo binfo;

	/**
	 * <p>Constructor for BaseElementRenderable.</p>
	 *
	 * @param container a {@link org.loboevolution.html.renderer.RenderableContainer} object.
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.ModelNode} object.
	 * @param ucontext a {@link org.loboevolution.http.UserAgentContext} object.
	 */
	public BaseElementRenderable(RenderableContainer container, ModelNode modelNode, UserAgentContext ucontext) {
		super(container, modelNode);
		this.userAgentContext = ucontext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.rendered.RenderableContainer#add(java.awt.Component)
	 */
	/** {@inheritDoc} */
	@Override
	public Component addComponent(Component component) {
		Collection<Component> gc = this.guiComponents;
		if (gc == null) {
			gc = new HashSet<Component>(1);
			this.guiComponents = gc;
		}
		gc.add(component);
		return component;
	}
	
    /**
     * <p>Getter for the field borderInsets.</p>
     *
     * @return a {@link java.awt.Insets} object.
     */
    public Insets getBorderInsets() {
        return this.borderInsets == null ? RBlockViewport.ZERO_INSETS : this.borderInsets;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.rendered.RenderableContainer#add(java.awt.Component)
	 */
	/** {@inheritDoc} */
	@Override
	public void addDelayedPair(DelayedPair pair) {
		List<DelayedPair> gc = this.delayedPairs;
		if (gc == null) {
			gc = new LinkedList<DelayedPair>();
			this.delayedPairs = gc;
		}
		gc.add(pair);
	}

	/**
	 * <p>applyStyle.</p>
	 *
	 * @param availWidth a int.
	 * @param availHeight a int.
	 */
	protected void applyStyle(int availWidth, int availHeight) {
		// TODO: Can be optimized if there's no style?
		// TODO: There's part of this that doesn't depend on availWidth
		// and availHeight, so it doesn't need to be redone on
		// every resize.
		// Note: Overridden by tables and lists.
		final Object rootNode = this.modelNode;
		HTMLElementImpl rootElement;
		boolean isRootBlock;
		if (rootNode instanceof HTMLDocumentImpl) {
			isRootBlock = true;
			final HTMLDocumentImpl doc = (HTMLDocumentImpl) rootNode;
			rootElement = (HTMLElementImpl) doc.getBody();
		} else {
			isRootBlock = false;
			rootElement = (HTMLElementImpl) rootNode;
		}
		if (rootElement == null) {
			clearStyle(isRootBlock);
			return;
		}
		final RenderState rs = rootElement.getRenderState();
		if (rs == null) {
			throw new IllegalStateException(
					"Element without render state: " + rootElement + "; parent=" + rootElement.getParentNode());
		}
		
		binfo = rs.getBackgroundInfo();	
        this.backgroundColor = binfo == null ? null : binfo.getBackgroundColor();
		final URL backgroundImageUri = binfo == null ? null : binfo.getBackgroundImage();		
		if (backgroundImageUri == null) {
			this.backgroundImage = null;
			this.lastBackgroundImageUri = null;
		} else if (!backgroundImageUri.equals(this.lastBackgroundImageUri)) {
			this.lastBackgroundImageUri = backgroundImageUri;
			backgroundImage = HttpNetwork.getImage(lastBackgroundImageUri.toString(), null);
			if (backgroundImage != null) {
				final int w = backgroundImage.getWidth(BaseElementRenderable.this);
				final int h = backgroundImage.getHeight(BaseElementRenderable.this);
				if (w != -1 && h != -1) {
					BaseElementRenderable.this.repaint();
				}
			}
		}
		final AbstractCSSProperties props = rootElement.getCurrentStyle();
		if (props == null) {
			clearStyle(isRootBlock);
		} else {
			final BorderInfo borderInfo = rs.getBorderInfo();
			this.borderInfo = borderInfo;
			final HtmlInsets binsets = borderInfo == null ? null : (HtmlInsets)borderInfo.getInsets();
			final HtmlInsets minsets = rs.getMarginInsets();
			final HtmlInsets pinsets = rs.getPaddingInsets();
			int dpleft = 0, dpright = 0, dptop = 0, dpbottom = 0;
			int dmleft = 0, dmright = 0, dmtop = 0, dmbottom = 0;

			Insets borderInsets = binsets == null ? null : binsets.getAWTInsets(0, 0, 0, 0, availWidth, availHeight, 0, 0);
			if (borderInsets == null) {
				borderInsets = RBlockViewport.ZERO_INSETS;
			}
			Insets paddingInsets = pinsets == null ? null : pinsets.getAWTInsets(dptop, dpleft, dpbottom, dpright, availWidth, availHeight, 0, 0);
			
			if (paddingInsets == null) {
				paddingInsets = RBlockViewport.ZERO_INSETS;
			}
	        Insets tentativeMarginInsets = minsets == null ? null : minsets.getAWTInsets(dmtop, dmleft, dmbottom, dmright, availWidth, availHeight, 0, 0);
			if (tentativeMarginInsets == null) {
				tentativeMarginInsets = RBlockViewport.ZERO_INSETS;
			}
			final int actualAvailWidth = availWidth - paddingInsets.left - paddingInsets.right - borderInsets.left
					- borderInsets.right - tentativeMarginInsets.left - tentativeMarginInsets.right;
			final int actualAvailHeight = availHeight - paddingInsets.top - paddingInsets.bottom - borderInsets.top
					- borderInsets.bottom - tentativeMarginInsets.top - tentativeMarginInsets.bottom;
			final Integer declaredWidth = getDeclaredWidth(rs, actualAvailWidth);
			final Integer declaredHeight = getDeclaredHeight(rs, actualAvailHeight);
			int autoMarginX = 0, autoMarginY = 0;
			if (declaredWidth != null) {
				autoMarginX = (availWidth - declaredWidth
						- (borderInsets == null ? 0 : borderInsets.left - borderInsets.right)
						- (paddingInsets == null ? 0 : paddingInsets.left - paddingInsets.right)) / 2;
			}
			if (declaredHeight != null) {
				autoMarginY = (availHeight - declaredHeight
						- (borderInsets == null ? 0 : borderInsets.top - borderInsets.bottom)
						- (paddingInsets == null ? 0 : paddingInsets.top - paddingInsets.bottom)) / 2;
			}
			this.borderInsets = borderInsets;
			if (isRootBlock) {
				// In the root block, the margin behaves like an extra padding.
				Insets regularMarginInsets = autoMarginX == 0 && autoMarginY == 0 ? tentativeMarginInsets
						: (minsets == null ? null : minsets.getAWTInsets(dmtop, dmleft, dmbottom, dmright, availWidth, availHeight, autoMarginX, autoMarginY));

				if (regularMarginInsets == null) {
					regularMarginInsets = RBlockViewport.ZERO_INSETS;
				}
				this.marginInsets = null;
				this.paddingInsets = new Insets(paddingInsets.top + regularMarginInsets.top,
						paddingInsets.left + regularMarginInsets.left,
						paddingInsets.bottom + regularMarginInsets.bottom,
						paddingInsets.right + regularMarginInsets.right);
			} else {
				this.paddingInsets = paddingInsets;
				this.marginInsets = ((autoMarginX == 0) && (autoMarginY == 0)) ? tentativeMarginInsets : (minsets == null ? null
			              : minsets.getAWTInsets(dmtop, dmleft, dmbottom, dmright, availWidth, availHeight, autoMarginX, autoMarginY));
			}
			if (borderInfo != null) {
				this.borderTopColor = borderInfo.getTopColor();
				this.borderLeftColor = borderInfo.getLeftColor();
				this.borderBottomColor = borderInfo.getBottomColor();
				this.borderRightColor = borderInfo.getRightColor();
			} else {
				this.borderTopColor = null;
				this.borderLeftColor = null;
				this.borderBottomColor = null;
				this.borderRightColor = null;
			}
			final String zIndex = props.getZIndex();
			if (zIndex != null) {
				try {
					this.zIndex = Integer.parseInt(zIndex);
				} catch (final NumberFormatException err) {
					logger.log(Level.WARNING,
							"Unable to parse z-index [" + zIndex + "] in element " + this.modelNode + ".", err);
					this.zIndex = 0;
				}
			} else {
				this.zIndex = 0;
			}
			this.overflowX = rs.getOverflowX();
			this.overflowY = rs.getOverflowY();
		}

		// Check if background image needs to be loaded
	}

	/** {@inheritDoc} */
	@Override
	public final void clearDelayedPairs() {
		final Collection<DelayedPair> gc = this.delayedPairs;
		if (gc != null) {
			gc.clear();
		}
	}

	/**
	 * <p>clearGUIComponents.</p>
	 */
	protected final void clearGUIComponents() {
		final Collection<Component> gc = this.guiComponents;
		if (gc != null) {
			gc.clear();
		}
	}

	/**
	 * <p>clearStyle.</p>
	 *
	 * @param isRootBlock a boolean.
	 */
	protected void clearStyle(boolean isRootBlock) {
		this.borderInfo = null;
		this.borderInsets = null;
		this.borderTopColor = null;
		this.borderLeftColor = null;
		this.borderBottomColor = null;
		this.borderRightColor = null;
		this.zIndex = 0;
		this.backgroundColor = null;
		this.backgroundImage = null;
		this.lastBackgroundImageUri = null;
		this.overflowX = RenderState.OVERFLOW_VISIBLE;
		this.overflowY = RenderState.OVERFLOW_VISIBLE;
		this.marginInsets = null;
		this.paddingInsets = null;
	}

	/**
	 * <p>doLayout.</p>
	 *
	 * @param availWidth a int.
	 * @param availHeight a int.
	 * @param sizeOnly a boolean.
	 */
	protected abstract void doLayout(int availWidth, int availHeight, boolean sizeOnly);

	/**
	 * <p>getAlignmentX.</p>
	 *
	 * @return a float.
	 */
	public float getAlignmentX() {
		return 0.0f;
	}

	/**
	 * <p>getAlignmentY.</p>
	 *
	 * @return a float.
	 */
	public float getAlignmentY() {
		return 0.0f;
	}

	private Color getBorderBottomColor() {
		final Color c = this.borderBottomColor;
		return c == null ? Color.black : c;
	}

	private Color getBorderLeftColor() {
		final Color c = this.borderLeftColor;
		return c == null ? Color.black : c;
	}

	private Color getBorderRightColor() {
		final Color c = this.borderRightColor;
		return c == null ? Color.black : c;
	}

	private Color getBorderTopColor() {
		final Color c = this.borderTopColor;
		return c == null ? Color.black : c;
	}

	/** {@inheritDoc} */
	@Override
	public Rectangle getBoundsRelativeToBlock() {
		RCollection parent = this;
		int x = 0, y = 0;
		while (parent != null) {
			x += parent.getX();
			y += parent.getY();
			parent = parent.getParent();
			if (parent instanceof RElement) {
				break;
			}
		}
		return new Rectangle(x, y, getWidth(), getHeight());
	}

	/** {@inheritDoc} */
	@Override
	public int getCollapsibleMarginBottom() {
		int cm;
		final Insets paddingInsets = this.paddingInsets;
		if (paddingInsets != null && paddingInsets.bottom > 0) {
			cm = 0;
		} else {
			final Insets borderInsets = this.borderInsets;
			if (borderInsets != null && borderInsets.bottom > 0) {
				cm = 0;
			} else {
				cm = getMarginBottom();
			}
		}
		if (isMarginBoundary()) {
			final RenderState rs = this.modelNode.getRenderState();
			if (rs != null) {
				final FontMetrics fm = rs.getFontMetrics();
				final int fontHeight = fm.getHeight();
				if (fontHeight > cm) {
					cm = fontHeight;
				}
			}
		}
		return cm;
	}

	/** {@inheritDoc} */
	@Override
	public int getCollapsibleMarginTop() {
		int cm;
		final Insets paddingInsets = this.paddingInsets;
		if (paddingInsets != null && paddingInsets.top > 0) {
			cm = 0;
		} else {
			final Insets borderInsets = this.borderInsets;
			if (borderInsets != null && borderInsets.top > 0) {
				cm = 0;
			} else {
				cm = getMarginTop();
			}
		}
		if (isMarginBoundary()) {
			final RenderState rs = this.modelNode.getRenderState();
			if (rs != null) {
				final FontMetrics fm = rs.getFontMetrics();
				final int fontHeight = fm.getHeight();
				if (fontHeight > cm) {
					cm = fontHeight;
				}
			}
		}
		return cm;
	}

	/**
	 * <p>Getter for the field declaredHeight.</p>
	 *
	 * @param renderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param actualAvailHeight a int.
	 * @return a {@link java.lang.Integer} object.
	 */
	protected Integer getDeclaredHeight(RenderState renderState, int actualAvailHeight) {
		Integer dh = this.declaredHeight;
		if (dh == INVALID_SIZE || actualAvailHeight != this.lastAvailHeightForDeclared) {
			this.lastAvailHeightForDeclared = actualAvailHeight;
			final int dhInt = getDeclaredHeightImpl(renderState, actualAvailHeight);
			dh = dhInt == -1 ? null : dhInt;
			this.declaredHeight = dh;
		}
		return dh;
	}

	/**
	 * <p>getDeclaredHeightImpl.</p>
	 *
	 * @param renderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param availHeight a int.
	 * @return a int.
	 */
	protected int getDeclaredHeightImpl(RenderState renderState, int availHeight) {
		Object rootNode = this.modelNode;
		if (rootNode instanceof HTMLElementImpl) {
			HTMLElementImpl element = (HTMLElementImpl) rootNode;
			CSS3Properties props = element.getCurrentStyle();
			if (props == null) {
				return -1;
			}
			String heightText = props.getHeight();

			if ("inherit".equalsIgnoreCase(heightText)) {
				heightText = element.getParentStyle().getHeight();
			} else if ("initial".equalsIgnoreCase(heightText)) {
				heightText = "100%";
			}

			int height = -1;

			if (heightText != null) {
				height = HtmlValues.getPixelSize(heightText, element.getRenderState(), -1, availHeight);
			}

			if (props.getMaxHeight() != null) {
				int maxHeight = HtmlValues.getPixelSize(props.getMaxHeight(), element.getRenderState(), -1, availHeight);

				if (height == 0 || height > maxHeight) {
					height = maxHeight;
				}
			}

			if (props.getMinHeight() != null) {
				int minHeight = HtmlValues.getPixelSize(props.getMinHeight(), element.getRenderState(), -1, availHeight);

				if (height == 0 || height < minHeight) {
					height = minHeight;
				}
			}

			return height;
		} else {
			return -1;
		}
	}

	/**
	 * <p>Getter for the field declaredWidth.</p>
	 *
	 * @param renderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param actualAvailWidth a int.
	 * @return a {@link java.lang.Integer} object.
	 */
	protected Integer getDeclaredWidth(RenderState renderState, int actualAvailWidth) {
		Integer dw = this.declaredWidth;
		if (dw == INVALID_SIZE || actualAvailWidth != this.lastAvailWidthForDeclared) {
			this.lastAvailWidthForDeclared = actualAvailWidth;
			final int dwInt = getDeclaredWidthImpl(renderState, actualAvailWidth);
			dw = dwInt == -1 ? null : dwInt;
			this.declaredWidth = dw;
		}
		return dw;
	}

	private final int getDeclaredWidthImpl(RenderState renderState, int availWidth) {
		Object rootNode = this.modelNode;
		if (rootNode instanceof HTMLElementImpl) {
			HTMLElementImpl element = (HTMLElementImpl) rootNode;
			CSS3Properties props = element.getCurrentStyle();
			if (props == null) {
				return -1;
			}
			String widthText = props.getWidth();

			if ("inherit".equalsIgnoreCase(widthText)) {
				widthText = element.getParentStyle().getWidth();
			} else if ("initial".equalsIgnoreCase(widthText)) {
				widthText = "100%";
			}

			int width = -1;

			if (widthText != null) {
				width = HtmlValues.getPixelSize(widthText, renderState, -1, availWidth);
			}

			if (props.getMaxWidth() != null) {
				int maxWidth = HtmlValues.getPixelSize(props.getMaxWidth(), renderState, -1, availWidth);

				if (width == -1 || width > maxWidth) {
					width = maxWidth;
				}
			}

			if (props.getMinWidth() != null) {
				int minWidth = HtmlValues.getPixelSize(props.getMinWidth(), element.getRenderState(), 0, availWidth);

				if (width == 0 || width < minWidth) {
					width = minWidth;
				}
			}

			return width;
		} else {
			return -1;
		}
	}

	/** {@inheritDoc} */
	@Override
	public final Collection<DelayedPair> getDelayedPairs() {
		return this.delayedPairs;
	}
	
	/** {@inheritDoc} */
	@Override
	public int getInnerWidth() {
		final Insets insets = getInsetsMarginBorder(false, false);
		return getWidth() - (insets.left + insets.right);
	}
	
	/** {@inheritDoc} */
	@Override
	public int getInnerHeight() {
		final Insets insets = getInsetsMarginBorder(false, false);
		return getHeight() - (insets.top + insets.bottom);
	}
	
	/** {@inheritDoc} */
	@Override
    public Insets getInsets(final boolean hscroll, final boolean vscroll) {
        return getInsets(hscroll, vscroll, true, true, true);
    }

	/** {@inheritDoc} */
	@Override
    public Insets getInsetsMarginBorder(final boolean hscroll, final boolean vscroll) {
        return getInsets(hscroll, vscroll, true, true, false);
    }

	/**
	 * <p>getInsetsPadding.</p>
	 *
	 * @param hscroll a boolean.
	 * @param vscroll a boolean.
	 * @return a {@link java.awt.Insets} object.
	 */
	public Insets getInsetsPadding(final boolean hscroll, final boolean vscroll) {
		return getInsets(hscroll, vscroll, false, false, true);
	}	 

    private Insets getInsets(final boolean hscroll, final boolean vscroll, final boolean includeMI,
            final boolean includeBI, final boolean includePI) {
		this.modelNode.getRenderState();
		final Insets mi = this.marginInsets;
		final Insets bi = this.borderInsets;
        final Insets pi = this.paddingInsets;
		int top = 0;
		int bottom = 0;
		int left = 0;
		int right = 0;
		
        if (includeMI && mi != null) {
			top += mi.top;
			left += mi.left;
			bottom += mi.bottom;
			right += mi.right;
		}
        
        if (includeBI && bi != null) {
			top += bi.top;
			left += bi.left;
			bottom += bi.bottom;
			right += bi.right;
		}
        
        if (includePI && pi != null) {
            top += pi.top;
            left += pi.left;
            bottom += pi.bottom;
            right += pi.right;
        }
        
		if (hscroll) {
			bottom += SCROLL_BAR_THICKNESS;
		}
		
		if (vscroll) {
			right += SCROLL_BAR_THICKNESS;
		}
		return new Insets(top, left, bottom, right);
	}

	/** {@inheritDoc} */
	@Override
	public int getMarginBottom() {
		final Insets marginInsets = this.marginInsets;
		return marginInsets == null ? 0 : marginInsets.bottom;
	}

	/** {@inheritDoc} */
	@Override
	public int getMarginLeft() {
		final Insets marginInsets = this.marginInsets;
		return marginInsets == null ? 0 : marginInsets.left;
	}

	/** {@inheritDoc} */
	@Override
	public int getMarginRight() {
		final Insets marginInsets = this.marginInsets;
		return marginInsets == null ? 0 : marginInsets.right;
	}

	/** {@inheritDoc} */
	@Override
	public int getMarginTop() {
		final Insets marginInsets = this.marginInsets;
		return marginInsets == null ? 0 : marginInsets.top;
	}

	/** {@inheritDoc} */
	@Override
	public RenderableContainer getParentContainer() {
		return this.container;
	}

	/** {@inheritDoc} */
	@Override
	public int getZIndex() {
		return this.zIndex;
	}

	/**
	 * <p>hasDeclaredWidth.</p>
	 *
	 * @return a boolean.
	 */
	public final boolean hasDeclaredWidth() {
		final Integer dw = this.declaredWidth;
		if (dw == INVALID_SIZE) {
			final Object rootNode = this.modelNode;
			if (rootNode instanceof HTMLElementImpl) {
				final HTMLElementImpl element = (HTMLElementImpl) rootNode;
				final CSS3Properties props = element.getCurrentStyle();
				if (props == null) {
					return false;
				}
				return !Strings.isBlank(props.getWidth());
			}
			return false;
		}
		return dw != null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int w, int h) {
		// This is so that a loading image doesn't cause
		// too many repaint events.
		if ((infoflags & ImageObserver.ALLBITS) != 0 || (infoflags & ImageObserver.FRAMEBITS) != 0) {
			this.repaint();
		}
		return true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Lays out children, and deals with "valid" state. Override doLayout method
	 * instead of this one.
	 */
	@Override
	public void layout(int availWidth, int availHeight, boolean sizeOnly) {
		// Must call doLayout regardless of validity state.
		try {
			doLayout(availWidth, availHeight, sizeOnly);
		} finally {
			this.layoutUpTreeCanBeInvalidated = true;
			this.layoutDeepCanBeInvalidated = true;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * Invalidates this Renderable and all descendents. This is only used in special
	 * cases, such as when a new style sheet is added.
	 */
	@Override
	public final void invalidateLayoutDeep() {
		if (this.layoutDeepCanBeInvalidated) {
			this.layoutDeepCanBeInvalidated = false;
			invalidateLayoutLocal();
			final Iterator<Renderable> i = getRenderables();
			if (i != null) {
				while (i.hasNext()) {
					final Renderable rn = i.next();
					final Renderable r = (rn instanceof PositionedRenderable) ? ((PositionedRenderable) rn).getRenderable() : rn;
					if (r instanceof RCollection) {
						((RCollection) r).invalidateLayoutDeep();
					}
				}
			}
		}
	}
	
	/** {@inheritDoc} */
	@Override
	  public Point translateDescendentPoint(BoundableRenderable descendent, int x, int y) {
	    final Point p = descendent.getOriginRelativeTo(this);
	    p.translate(x, y);
	    return p;
	  }

	/** {@inheritDoc} */
	@Override
	protected void invalidateLayoutLocal() {
		final RenderState rs = this.modelNode.getRenderState();
		if (rs != null) {
			rs.invalidate();
		}
		this.overflowX = RenderState.OVERFLOW_NONE;
		this.overflowY = RenderState.OVERFLOW_NONE;
		this.declaredWidth = INVALID_SIZE;
		this.declaredHeight = INVALID_SIZE;
		this.lastAvailHeightForDeclared = -1;
		this.lastAvailWidthForDeclared = -1;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isContainedByNode() {
		return true;
	}

	/**
	 * <p>isMarginBoundary.</p>
	 *
	 * @return a boolean.
	 */
	protected boolean isMarginBoundary() {
		return this.overflowY != RenderState.OVERFLOW_VISIBLE && this.overflowX != RenderState.OVERFLOW_NONE
				|| this.modelNode instanceof HTMLDocumentImpl;
	}
	
	/** {@inheritDoc} */
	@Override
	public void paint(Graphics g) {}

	 /** {@inheritDoc} */
	 @Override
	  public Rectangle getClipBounds() {
	    final Insets insets = this.getInsetsPadding(false, false);
	    final int hInset = insets.left + insets.right;
	    final int vInset = insets.top + insets.bottom;
	    if (((overflowX == RenderState.OVERFLOW_NONE) || (overflowX == RenderState.OVERFLOW_VISIBLE))
	        && ((overflowY == RenderState.OVERFLOW_NONE) || (overflowY == RenderState.OVERFLOW_VISIBLE))) {
			return null;
	    } else {
	      return new Rectangle(insets.left, insets.top, this.width - hInset, this.height - vInset);
	    }
	  }

	/**
	 * <p>prePaint.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 */
	protected void prePaint(Graphics g) {
		final int startWidth = this.width;
		final int startHeight = this.height;
		int totalWidth = startWidth;
		int totalHeight = startHeight;
		int startX = 0;
		int startY = 0;
		final ModelNode node = this.modelNode;
		
		final RenderState rs = node.getRenderState();
		final Insets marginInsets = this.marginInsets;
		if (marginInsets != null) {
			totalWidth -= marginInsets.left + marginInsets.right;
			totalHeight -= marginInsets.top + marginInsets.bottom;
			startX += marginInsets.left;
			startY += marginInsets.top;
		}
		final Insets borderInsets = this.borderInsets;

		final Graphics clientG = g.create(startX, startY, totalWidth, totalHeight);
		try {
			Rectangle bkgBounds = null;
			if (node != null) {
				final Color bkg = this.backgroundColor;
				if (bkg != null && bkg.getAlpha() > 0) {
					clientG.setColor(bkg);
					bkgBounds = clientG.getClipBounds();
					clientG.fillRect(bkgBounds.x, bkgBounds.y, bkgBounds.width, bkgBounds.height);
				}
				
				if(binfo == null) {
					binfo = rs == null ? null : rs.getBackgroundInfo();
				}				
				
				final Image image = this.backgroundImage;
				if (image != null) {
					if (bkgBounds == null) {
						bkgBounds = clientG.getClipBounds();
					}
					final int w = image.getWidth(this);
					final int h = image.getHeight(this);
					if (w != -1 && h != -1) {
						
			            final int imageY = getImageY(totalHeight, binfo, h);
			            final int imageX = getImageX(totalWidth, binfo, w);

			            final int baseX = (bkgBounds.x / w) * w - (w - imageX);
			            final int baseY = (bkgBounds.y / h) * h - (h - imageY);
			            
			            final int topX = bkgBounds.x + bkgBounds.width;
			            final int topY = bkgBounds.y + bkgBounds.height;
			            
			            switch (binfo == null ? BackgroundInfo.BR_REPEAT : binfo.getBackgroundRepeat()) {
						case BackgroundInfo.BR_NO_REPEAT:
							int _imageX;
							if (binfo.isBackgroundXPositionAbsolute()) {
								_imageX = binfo.getBackgroundXPosition();
							} else {
								_imageX = binfo.getBackgroundXPosition() * (totalWidth - w) / 100;
							}
							int _imageY;
							if (binfo.isBackgroundYPositionAbsolute()) {
								_imageY = binfo.getBackgroundYPosition();
							} else {
								_imageY = binfo.getBackgroundYPosition() * (totalHeight - h) / 100;
							}
							g.drawImage(image, _imageX, _imageY, w, h, this);
							break;
	
							case BackgroundInfo.BR_REPEAT_X:
	
								for (int x = baseX; x < topX; x += w) {
									clientG.drawImage(image, x, imageY, w, h, this);
								}
								break;
	
							case BackgroundInfo.BR_REPEAT_Y:
	
								for (int y = baseY; y < topY; y += h) {
									clientG.drawImage(image, imageX, y, w, h, this);
								}
								break;
	
							default: {
								for (int x = baseX; x < topX; x += w) {
									for (int y = baseY; y < topY; y += h) {
										clientG.drawImage(image, x, y, w, h, this);
									}
								}
								break;
							}
						}
					}
				}
			}
		} finally {
			clientG.dispose();
		}

		if (borderInsets != null) {
			final int btop = borderInsets.top;
			final int bleft = borderInsets.left;
			final int bright = borderInsets.right;
			final int bbottom = borderInsets.bottom;

			final int newTotalWidth = totalWidth - (bleft + bright);
			final int newTotalHeight = totalHeight - (btop + bbottom);
			final int newStartX = startX + bleft;
			final int newStartY = startY + btop;
			final Rectangle clientRegion = new Rectangle(newStartX, newStartY, newTotalWidth, newTotalHeight);

			// Paint borders if the clip bounds are not contained
			// by the content area.
			final Rectangle clipBounds = g.getClipBounds();
			if (!clientRegion.contains(clipBounds)) {
				final BorderInfo borderInfo = this.borderInfo;
				if (btop > 0) {
					g.setColor(getBorderTopColor());
					final int borderStyle = borderInfo == null ? BorderInsets.BORDER_STYLE_SOLID : borderInfo.getTopStyle();
					for (int i = 0; i < btop; i++) {
						final int leftOffset = i * bleft / btop;
						final int rightOffset = i * bright / btop;
						
						switch (borderStyle) {
						case BorderInsets.BORDER_STYLE_DASHED:
							GUITasks.drawDashed(g, startX + leftOffset, startY + i, startX + totalWidth - rightOffset - 1, startY + i, 10 + btop, 6);
							break;
						case BorderInsets.BORDER_STYLE_DOTTED:
							GUITasks.drawDotted(g, startX + leftOffset, startY + i, startX + totalWidth - rightOffset - 1, startY + i, btop);
							break;
						case BorderInsets.BORDER_STYLE_INSET:
							g.setColor(ColorFactory.getAdjustedColor(getBorderTopColor(), -0.3));	
							g.drawLine(startX + leftOffset, startY + i, startX + totalWidth - rightOffset - 1, startY + i);
							break;
						case BorderInsets.BORDER_STYLE_OUTSET:
							g.setColor(ColorFactory.getAdjustedColor(getBorderTopColor(), -0.3));	
							g.drawLine(startX + leftOffset, startY + i, startX + totalWidth - rightOffset - 1, startY + i);
							break;	
						case BorderInsets.BORDER_STYLE_SOLID:
						default:
							g.drawLine(startX + leftOffset, startY + i, startX + totalWidth - rightOffset - 1, startY + i);
							break;
						}
					}
				}
				if (bright > 0) {
					final int borderStyle = borderInfo == null ? BorderInsets.BORDER_STYLE_SOLID : borderInfo.getRightStyle();
					g.setColor(getBorderRightColor());
					final int lastX = startX + totalWidth - 1;
					for (int i = 0; i < bright; i++) {
						final int topOffset = i * btop / bright;
						final int bottomOffset = i * bbottom / bright;
						
						switch (borderStyle) {
						case BorderInsets.BORDER_STYLE_DASHED:
							GUITasks.drawDashed(g, lastX - i, startY + topOffset, lastX - i, startY + totalHeight - bottomOffset - 1, 10 + bright, 6);
							break;
						case BorderInsets.BORDER_STYLE_DOTTED:
							GUITasks.drawDotted(g, lastX - i, startY + topOffset, lastX - i, startY + totalHeight - bottomOffset - 1, bright);
							break;
						case BorderInsets.BORDER_STYLE_INSET:
							g.setColor(ColorFactory.getAdjustedColor(getBorderRightColor(), -0.3));	
							g.drawLine(lastX - i, startY + topOffset, lastX - i, startY + totalHeight - bottomOffset - 1);
							break;
						case BorderInsets.BORDER_STYLE_OUTSET:
							g.setColor(ColorFactory.getAdjustedColor(getBorderRightColor(), -0.3));	
							g.drawLine(lastX - i, startY + topOffset, lastX - i, startY + totalHeight - bottomOffset - 1);
							break;	
						case BorderInsets.BORDER_STYLE_SOLID:
						default:
							g.drawLine(lastX - i, startY + topOffset, lastX - i, startY + totalHeight - bottomOffset - 1);
							break;
						}
					}
				}
				if (bbottom > 0) {
					final int borderStyle = borderInfo == null ? BorderInsets.BORDER_STYLE_SOLID : borderInfo.getBottomStyle();
					g.setColor(getBorderBottomColor());
					final int lastY = startY + totalHeight - 1;
					for (int i = 0; i < bbottom; i++) {
						final int leftOffset = i * bleft / bbottom;
						final int rightOffset = i * bright / bbottom;
						
						switch (borderStyle) {
						case BorderInsets.BORDER_STYLE_DASHED:
							GUITasks.drawDashed(g, startX + leftOffset, lastY - i, startX + totalWidth - rightOffset - 1, lastY - i, 10 + bbottom, 6);
							break;
						case BorderInsets.BORDER_STYLE_DOTTED:
							GUITasks.drawDotted(g, startX + leftOffset, lastY - i, startX + totalWidth - rightOffset - 1, lastY - i, bbottom);
							break;
						case BorderInsets.BORDER_STYLE_INSET:
							g.setColor(ColorFactory.getAdjustedColor(getBorderBottomColor(), -0.3));	
							g.drawLine(startX + leftOffset, lastY - i, startX + totalWidth - rightOffset - 1, lastY - i);
							break;
						case BorderInsets.BORDER_STYLE_OUTSET:
							g.setColor(ColorFactory.getAdjustedColor(getBorderBottomColor(), -0.3));	
							g.drawLine(startX + leftOffset, lastY - i, startX + totalWidth - rightOffset - 1, lastY - i);
							break;	
						case BorderInsets.BORDER_STYLE_SOLID:
						default:
							g.drawLine(startX + leftOffset, lastY - i, startX + totalWidth - rightOffset - 1, lastY - i);
							break;
						}
					}
				}
				if (bleft > 0) {
					final int borderStyle = borderInfo == null ? BorderInsets.BORDER_STYLE_SOLID : borderInfo.getLeftStyle();
					g.setColor(getBorderLeftColor());
					for (int i = 0; i < bleft; i++) {
						final int topOffset = i * btop / bleft;
						final int bottomOffset = i * bbottom / bleft;
						
						switch (borderStyle) {
						case BorderInsets.BORDER_STYLE_DASHED:
							GUITasks.drawDashed(g, startX + i, startY + topOffset, startX + i, startY + totalHeight - bottomOffset - 1, 10 + bleft, 6);
							break;
						case BorderInsets.BORDER_STYLE_DOTTED:
							GUITasks.drawDotted(g, startX + i, startY + topOffset, startX + i, startY + totalHeight - bottomOffset - 1, bleft);
							break;
						case BorderInsets.BORDER_STYLE_INSET:
							g.setColor(ColorFactory.getAdjustedColor(getBorderLeftColor(), -0.3));	
							g.drawLine(startX + i, startY + topOffset, startX + i, startY + totalHeight - bottomOffset - 1);
							break;
						case BorderInsets.BORDER_STYLE_OUTSET:
							g.setColor(ColorFactory.getAdjustedColor(getBorderLeftColor(), -0.3));	
							g.drawLine(startX + i, startY + topOffset, startX + i, startY + totalHeight - bottomOffset - 1);
							break;	
						case BorderInsets.BORDER_STYLE_SOLID:
						default:
							g.drawLine(startX + i, startY + topOffset, startX + i, startY + totalHeight - bottomOffset - 1);
							break;
						}
					}
				}
			}

			// Adjust client area border
			totalWidth = newTotalWidth;
			totalHeight = newTotalHeight;
			startX = newStartX;
			startY = newStartY;

		}
	}

	/**
	 * <p>sendDelayedPairsToParent.</p>
	 */
	protected final void sendDelayedPairsToParent() {
		// Ensures that parent has all the components
		// below this renderer node. (Parent expected to have removed them).
		final Collection<DelayedPair> gc = this.delayedPairs;
		if (gc != null) {
			final RenderableContainer rc = this.container;
			for (DelayedPair pair : gc) {
				if (pair.getContainingBlock() != this) {
					rc.addDelayedPair(pair);
				}
			}
		}
	}

	/**
	 * <p>sendGUIComponentsToParent.</p>
	 */
	protected final void sendGUIComponentsToParent() {
		// Ensures that parent has all the components
		// below this renderer node. (Parent expected to have removed them).
		final Collection<Component> gc = this.guiComponents;
		if (gc != null) {
			final RenderableContainer rc = this.container;
			for (Component component : gc) {
				rc.addComponent(component);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void updateAllWidgetBounds() {
		this.container.updateAllWidgetBounds();
	}

	/**
	 * Updates widget bounds below this node only. Should not be called during
	 * general rendering.
	 */
	public void updateWidgetBounds() {
		final java.awt.Point guiPoint = getGUIPoint(0, 0);
		this.updateWidgetBounds(guiPoint.x, guiPoint.y);
	}
	
	private static int getImageY(final int totalHeight, final BackgroundInfo binfo, final int h) {
		if (binfo == null) {
			return 0;
		} else {
			if (binfo.isBackgroundYPositionAbsolute()) {
				return binfo.getBackgroundYPosition();
			} else {
				return (binfo.getBackgroundYPosition() * (totalHeight - h)) / 100;
			}
		}
	}

	private static int getImageX(final int totalWidth, final BackgroundInfo binfo, final int w) {
		if (binfo == null) {
			return 0;
		} else {
			if (binfo.isBackgroundXPositionAbsolute()) {
				return binfo.getBackgroundXPosition();
			} else {
				return (binfo.getBackgroundXPosition() * (totalWidth - w)) / 100;
			}
		}
	}
}
