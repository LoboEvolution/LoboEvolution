/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
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
import org.loboevolution.html.dom.nodeimpl.ModelNode;
import org.loboevolution.html.node.css.CSS3Properties;
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

/**
 * <p>Abstract BaseElementRenderable class.</p>
 *
 *
 *
 */
public abstract class BaseElementRenderable extends BaseRCollection implements RElement, RenderableContainer, ImageObserver {

	/** Constant INVALID_SIZE */
	protected static final Integer INVALID_SIZE = Integer.MIN_VALUE;

	/** Constant SCROLL_BAR_THICKNESS=16 */
	protected static final int SCROLL_BAR_THICKNESS = 16;

	protected final UserAgentContext userAgentContext;

	protected Color backgroundColor;

	protected Color borderLeftColor;

	protected Color borderRightColor;

	protected Color borderTopColor;

	protected Color borderBottomColor;

	protected volatile Image backgroundImage;

	protected BorderInfo borderInfo;

	private BackgroundInfo binfo;

	protected Insets borderInsets;

	protected Insets marginInsets;

	protected Insets paddingInsets;

	private Integer declaredHeight = INVALID_SIZE;

	private Integer declaredWidth = INVALID_SIZE;

	protected List<DelayedPair> delayedPairs = null;

	private Collection<Component> guiComponents = null;

	protected URL lastBackgroundImageUri;

	protected boolean layoutDeepCanBeInvalidated = false;

	protected int overflowX;

	protected int overflowY;

	protected int relativeOffsetX = 0;

	protected int relativeOffsetY = 0;

	protected int zIndex;

	private int lastAvailHeightForDeclared = -1;

	private int lastAvailWidthForDeclared = -1;

	/**
	 * <p>Constructor for BaseElementRenderable.</p>
	 *
	 * @param container a {@link org.loboevolution.html.renderer.RenderableContainer} object.
	 * @param modelNode a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
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
			gc = new HashSet<>(1);
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
			gc = new LinkedList<>();
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
			throw new IllegalStateException("Element without render state: " + rootElement + "; parent=" + rootElement.getParentNode());
		}

		backgroundApplyStyle(rs);

		final AbstractCSSProperties props = rootElement.getCurrentStyle();
		if (props == null) {
			clearStyle(isRootBlock);
		} else {

			insetsApplyStyle(rs, availWidth, availHeight, isRootBlock);

			zIndexApplyStyle(props);

			this.overflowX = rs.getOverflowX();
			this.overflowY = rs.getOverflowY();
		}
	}

	private void backgroundApplyStyle(RenderState rs) {
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
	}

	private void insetsApplyStyle(RenderState rs, int availWidth, int availHeight, boolean isRootBlock) {

		Insets borderInsets = borderInsets(rs,availWidth, availHeight);
		Insets paddingInsets = paddingInsets(rs,availWidth, availHeight);
		Insets tentativeMarginInsets = marginInsets(rs,availWidth, availHeight);

		final int paddingWidth = paddingInsets.left - paddingInsets.right;
		final int borderWidth = borderInsets.left - borderInsets.right;
		final int marginWidth = tentativeMarginInsets.left - tentativeMarginInsets.right;

		final int paddingHeight = paddingInsets.top - paddingInsets.bottom;
		final int borderHeight = borderInsets.top - borderInsets.bottom;
		final int marginHeight = tentativeMarginInsets.top - tentativeMarginInsets.bottom;

		final int actualAvailWidth = availWidth - paddingWidth - borderWidth - marginWidth;
		final int actualAvailHeight = availHeight - paddingHeight - borderHeight - marginHeight;
		final Integer declaredWidth = getDeclaredWidth(rs, actualAvailWidth);
		final Integer declaredHeight = getDeclaredHeight(rs, actualAvailHeight);

		int autoMarginX = 0;
		int autoMarginY = 0;

		if (declaredWidth != null) {
			final int borderx = borderInsets.left - borderInsets.right;
			final int paddingx = paddingInsets.left - paddingInsets.right;
			autoMarginX = (availWidth - declaredWidth - borderx  - paddingx) / 2;
		}
		if (declaredHeight != null) {
			final int bordery = borderInsets.top - borderInsets.bottom;
			final int paddingy = paddingInsets.top - paddingInsets.bottom;
			autoMarginY = (availHeight - declaredHeight - bordery - paddingy) / 2;
		}

		HtmlInsets minsets = rs.getMarginInsets();

		if (isRootBlock) {
			Insets regularMarginInsets = RBlockViewport.ZERO_INSETS;

			if (autoMarginX == 0 && autoMarginY == 0) {
				regularMarginInsets = tentativeMarginInsets;
			} else if (minsets != null) {
				regularMarginInsets = minsets.getAWTInsets(availWidth, availHeight, autoMarginX, autoMarginY);
			}

			final int top = paddingInsets.top + regularMarginInsets.top;
			final int left = paddingInsets.left + regularMarginInsets.left;
			final int bottom = paddingInsets.top + regularMarginInsets.bottom;
			final int right = paddingInsets.top + regularMarginInsets.right;

			this.paddingInsets = new Insets(top, left, bottom, right);
			this.marginInsets = null;
		} else {
			this.paddingInsets = paddingInsets;
			this.marginInsets = RBlockViewport.ZERO_INSETS;

			if (autoMarginX == 0 && autoMarginY == 0) {
				this.marginInsets = tentativeMarginInsets;
			} else if (minsets != null) {
				this.marginInsets = minsets.getAWTInsets(availWidth, availHeight, autoMarginX, autoMarginY);
			}
		}
	}

	private Insets borderInsets(RenderState rs, int availWidth, int availHeight) {
		Insets ins = null;
		final BorderInfo borderInfo = rs.getBorderInfo();
		this.borderInfo = borderInfo;

		if (borderInfo != null) {
			HtmlInsets html = (HtmlInsets) borderInfo.getInsets();
			if (html == null) {
				ins = RBlockViewport.ZERO_INSETS;
			} else {
				ins = html.getAWTInsets(availWidth, availHeight, 0, 0);
			}

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
		this.borderInsets = ins;
		return ins;
	}

	private Insets marginInsets(RenderState rs, int availWidth, int availHeight) {
		Insets ins;
		HtmlInsets html = rs.getMarginInsets();
		if (html == null) {
			ins = RBlockViewport.ZERO_INSETS;
		} else {
			ins = html.getAWTInsets(availWidth, availHeight, 0, 0);
		}
		return ins;
	}

	private Insets paddingInsets(RenderState rs, int availWidth, int availHeight) {
		Insets ins;
		HtmlInsets html = rs.getPaddingInsets();
		if (html == null) {
			ins = RBlockViewport.ZERO_INSETS;
		} else {
			ins = html.getAWTInsets(availWidth, availHeight, 0, 0);
		}
		return ins;
	}

	private void zIndexApplyStyle(AbstractCSSProperties props) {
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

	/** {@inheritDoc} */
	public void setupRelativePosition(final RenderableContainer container) {
		setupRelativePosition(getModelNode().getRenderState(), container.getInnerWidth(), container.getInnerHeight());
	}

	private void setupRelativePosition(final RenderState rs, final int availWidth, final int availHeight) {
		if (rs.getPosition() == RenderState.POSITION_RELATIVE) {
			HTMLElementImpl element = (HTMLElementImpl) this.modelNode;
			HTMLDocumentImpl doc =  (HTMLDocumentImpl)element.getDocumentNode();

			final String leftText = rs.getLeft();
			final String topText = rs.getTop();
			int left = 0;
			if (leftText != null) {
				left = HtmlValues.getPixelSize(leftText, rs, doc.getWindow(), 0, availWidth);
			} else {
				final String rightText = rs.getRight();
				if (rightText != null) {
					final int right = HtmlValues.getPixelSize(rightText, rs, doc.getWindow(),0, availWidth);
					left = -right;
				}
			}

			int top = 0;
			if (topText != null) {
				top = HtmlValues.getPixelSize(topText, rs, doc.getWindow(), top, availHeight);
			} else {
				final String bottomText = rs.getBottom();
				if (bottomText != null) {
					final int bottom = HtmlValues.getPixelSize(bottomText, rs, doc.getWindow(),0, availHeight);
					top = -bottom;
				}
			}

			this.relativeOffsetX = left;
			this.relativeOffsetY = top;
		}
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
		if (INVALID_SIZE.equals(dh) || actualAvailHeight != this.lastAvailHeightForDeclared) {
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
	private int getDeclaredHeightImpl(RenderState renderState, int availHeight) {
		Object rootNode = this.modelNode;
		if (rootNode instanceof HTMLElementImpl) {
			HTMLElementImpl element = (HTMLElementImpl) rootNode;
			CSS3Properties props = element.getCurrentStyle();
			if (props == null) {
				return -1;
			}
			HTMLDocumentImpl doc =  (HTMLDocumentImpl)element.getDocumentNode();
			String heightText = props.getHeight();

			if ("inherit".equalsIgnoreCase(heightText)) {
				heightText = element.getParentStyle().getHeight();
			} else if ("initial".equalsIgnoreCase(heightText)) {
				heightText = "100%";
			}

			int height = -1;

			if (heightText != null) {
				height = HtmlValues.getPixelSize(heightText, renderState, doc.getWindow(), -1, availHeight);
			}

			if (props.getMaxHeight() != null) {
				int maxHeight = HtmlValues.getPixelSize(props.getMaxHeight(), renderState, doc.getWindow(),-1, availHeight);

				if (height == 0 || height > maxHeight) {
					height = maxHeight;
				}
			}

			if (props.getMinHeight() != null) {
				int minHeight = HtmlValues.getPixelSize(props.getMinHeight(), renderState, doc.getWindow(),-1, availHeight);

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
		if (INVALID_SIZE.equals(dw) || actualAvailWidth != this.lastAvailWidthForDeclared) {
			this.lastAvailWidthForDeclared = actualAvailWidth;
			final int dwInt = getDeclaredWidthImpl(renderState, actualAvailWidth);
			dw = dwInt == -1 ? null : dwInt;
			this.declaredWidth = dw;
		}
		return dw;
	}

	private int getDeclaredWidthImpl(RenderState renderState, int availWidth) {
		Object rootNode = this.modelNode;
		if (rootNode instanceof HTMLElementImpl) {
			HTMLElementImpl element = (HTMLElementImpl) rootNode;
			HTMLDocumentImpl doc =  (HTMLDocumentImpl)element.getDocumentNode();
			CSS3Properties props = element.getCurrentStyle();
			if (props == null) {
				return -1;
			}
			String widthText = props.getWidth();
			final String textContent = element.getTextContent();

			if ("inherit".equalsIgnoreCase(widthText)) {
				widthText = element.getParentStyle().getWidth();
			} else if ("initial".equalsIgnoreCase(widthText)) {
				widthText = "100%";
			}

			int width = -1;

			if (widthText != null) {
				width = HtmlValues.getPixelSize(widthText, renderState, doc.getWindow(), -1, availWidth);
			}

			if(width == -1 && Strings.isNotBlank(textContent) && renderState.getDisplay() == RenderState.DISPLAY_INLINE_BLOCK) {
				HtmlInsets paddingInsets = renderState.getPaddingInsets();
				HtmlInsets marginInsets = renderState.getMarginInsets();
				int right = 0;
				int left = 0;

				if(paddingInsets != null) {
					right = right + paddingInsets.right;
					left = left + paddingInsets.left;
				}

				if(marginInsets != null) {
					right = right + marginInsets.right;
					left =  left + marginInsets.left;
				}

				final int multi = (right == 0 && left == 0) ? 12 : 4;

				width = (textContent.length() + right + left) * multi;
			}

			if (props.getMaxWidth() != null) {
				int maxWidth = HtmlValues.getPixelSize(props.getMaxWidth(), renderState, doc.getWindow(), -1, availWidth);

				if (width == -1 || width > maxWidth) {
					width = maxWidth;
				}
			}

			if (props.getMinWidth() != null) {
				int minWidth = HtmlValues.getPixelSize(props.getMinWidth(), element.getRenderState(), doc.getWindow(), 0, availWidth);

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
		if (INVALID_SIZE.equals(dw)) {
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
		return true;
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
		final Point guiPoint = getGUIPoint(0, 0);
		this.updateWidgetBounds(guiPoint.x, guiPoint.y);
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

		final Insets marginInsets = this.marginInsets;
		if (marginInsets != null) {
			totalWidth -= marginInsets.left + marginInsets.right;
			totalHeight -= marginInsets.top + marginInsets.bottom;
			startX += marginInsets.left;
			startY += marginInsets.top;
		}

		prePaintBackground(g, this.modelNode, totalWidth, totalHeight, startX, startY);

		prePaintBorder(g, totalWidth, totalHeight, startX, startY);

	}

	private void prePaintBackground(Graphics g, ModelNode node, int totalWidth, int totalHeight, int startX, int startY) {
		final RenderState rs = node.getRenderState();
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

				if (binfo == null) {
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
	}

	private void prePaintBorder(Graphics g, int totalWidth, int totalHeight, int startX, int startY) {

		final Insets borderInsets = this.borderInsets;

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
				int x1;
				int y1;
				int x2;
				int y2;
				int dashSize;

				if (btop > 0) {
					g.setColor(getBorderTopColor());
					final int borderStyle = borderInfo == null ? BorderInsets.BORDER_STYLE_SOLID : borderInfo.getTopStyle();
					for (int i = 0; i < btop; i++) {
						final int leftOffset = i * bleft / btop;
						final int rightOffset = i * bright / btop;
						x1 = startX + leftOffset;
						y1 = startY + i;
						x2 = startX + totalWidth - rightOffset - 1;
						y2 = startY + i;
						dashSize = 10 + btop;
						paintBorder(g, x1, y1, x2, y2, dashSize, btop, borderStyle);
					}
				}
				if (bright > 0) {
					final int borderStyle = borderInfo == null ? BorderInsets.BORDER_STYLE_SOLID : borderInfo.getRightStyle();
					g.setColor(getBorderRightColor());
					final int lastX = startX + totalWidth - 1;
					for (int i = 0; i < bright; i++) {
						final int topOffset = i * btop / bright;
						final int bottomOffset = i * bbottom / bright;
						x1 = lastX - i;
						y1 = startY + topOffset;
						x2 = lastX - i;
						y2 = startY + totalHeight - bottomOffset - 1;
						dashSize = 10 + bright;
						paintBorder(g, x1, y1, x2, y2, dashSize, bright, borderStyle);
					}
				}
				if (bbottom > 0) {
					final int borderStyle = borderInfo == null ? BorderInsets.BORDER_STYLE_SOLID : borderInfo.getBottomStyle();
					g.setColor(getBorderBottomColor());
					final int lastY = startY + totalHeight - 1;
					for (int i = 0; i < bbottom; i++) {
						final int leftOffset = i * bleft / bbottom;
						final int rightOffset = i * bright / bbottom;
						x1 = startX + leftOffset;
						y1 = lastY - i;
						x2 = startX + totalWidth - rightOffset - 1;
						y2 = lastY - i;
						dashSize = 10 + bbottom;
						paintBorder(g, x1, y1, x2, y2, dashSize, bbottom, borderStyle);
					}
				}
				if (bleft > 0) {
					final int borderStyle = borderInfo == null ? BorderInsets.BORDER_STYLE_SOLID : borderInfo.getLeftStyle();
					g.setColor(getBorderLeftColor());
					for (int i = 0; i < bleft; i++) {
						final int topOffset = i * btop / bleft;
						final int bottomOffset = i * bbottom / bleft;
						x1 = startX + i;
						y1 = startY + topOffset;
						x2 = startX + i;
						y2 = startY + totalHeight - bottomOffset - 1;
						dashSize = 10 + bleft;
						paintBorder(g, x1, y1, x2, y2, dashSize, bleft, borderStyle);
					}
				}
			}
		}
	}

	private void paintBorder(Graphics g, int x1, int y1, int x2, int y2, int dashSize, int width, int borderStyle) {

		switch (borderStyle) {
			case BorderInsets.BORDER_STYLE_DASHED:
				GUITasks.drawDashed(g, x1, y1, x2, y2, dashSize, 6);
				break;
			case BorderInsets.BORDER_STYLE_DOTTED:
				GUITasks.drawDotted(g, x1, y1, x2, y2, width);
				break;
			case BorderInsets.BORDER_STYLE_INSET:
			case BorderInsets.BORDER_STYLE_OUTSET:
				g.setColor(ColorFactory.getAdjustedColor(getBorderTopColor(), -0.3));
				g.drawLine(x1, y1, x2, y2);
				break;
			case BorderInsets.BORDER_STYLE_SOLID:
			default:
				g.drawLine(x1, y1, x2, y2);
				break;
		}
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
