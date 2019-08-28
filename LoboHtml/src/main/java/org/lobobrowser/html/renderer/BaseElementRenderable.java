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

package org.lobobrowser.html.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import org.lobo.common.Strings;
import org.lobo.info.BackgroundInfo;
import org.lobo.info.BorderInfo;
import org.lobobrowser.html.dom.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.dom.domimpl.HTMLElementImpl;
import org.lobobrowser.html.dom.domimpl.ModelNode;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.style.AbstractCSSProperties;
import org.lobobrowser.html.style.HtmlInsets;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.http.HttpRequest;
import org.lobobrowser.http.UserAgentContext;
import org.lobo.common.GUITasks;
import org.w3c.dom.css.CSS3Properties;

public abstract class BaseElementRenderable extends BaseRCollection
		implements RElement, RenderableContainer, ImageObserver {
	protected static final Integer INVALID_SIZE = new Integer(Integer.MIN_VALUE);

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

	public BaseElementRenderable(RenderableContainer container, ModelNode modelNode, UserAgentContext ucontext) {
		super(container, modelNode);
		this.userAgentContext = ucontext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.renderer.RenderableContainer#add(java.awt.Component)
	 */
	@Override
	public Component addComponent(Component component) {
		// Expected to be called in GUI thread.
		// Adds only in local collection.
		// Does not remove from parent.
		// Sending components to parent is done
		// by sendGUIComponentsToParent().
		Collection<Component> gc = this.guiComponents;
		if (gc == null) {
			gc = new HashSet<Component>(1);
			this.guiComponents = gc;
		}
		gc.add(component);
		return component;
	}
	
    public Insets getBorderInsets() {
        return this.borderInsets == null ? RBlockViewport.ZERO_INSETS : this.borderInsets;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.renderer.RenderableContainer#add(java.awt.Component)
	 */
	@Override
	public void addDelayedPair(DelayedPair pair) {
		List<DelayedPair> gc = this.delayedPairs;
		if (gc == null) {
			gc = new LinkedList<DelayedPair>();
			this.delayedPairs = gc;
		}
		gc.add(pair);
	}

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
        this.backgroundColor = binfo == null ? null : binfo.backgroundColor;
		final URL backgroundImageUri = binfo == null ? null : binfo.backgroundImage;
		if (backgroundImageUri == null) {
			this.backgroundImage = null;
			this.lastBackgroundImageUri = null;
		} else if (!backgroundImageUri.equals(this.lastBackgroundImageUri)) {
			this.lastBackgroundImageUri = backgroundImageUri;
			loadBackgroundImage(backgroundImageUri);
		}
		final AbstractCSSProperties props = rootElement.getCurrentStyle();
		if (props == null) {
			clearStyle(isRootBlock);
		} else {
			final BorderInfo borderInfo = rs.getBorderInfo();
			this.borderInfo = borderInfo;
			final HtmlInsets binsets = borderInfo == null ? null : (HtmlInsets)borderInfo.insets;
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
				autoMarginX = (availWidth - declaredWidth.intValue()
						- (borderInsets == null ? 0 : borderInsets.left - borderInsets.right)
						- (paddingInsets == null ? 0 : paddingInsets.left - paddingInsets.right)) / 2;
			}
			if (declaredHeight != null) {
				autoMarginY = (availHeight - declaredHeight.intValue()
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
				this.borderTopColor = borderInfo.topColor;
				this.borderLeftColor = borderInfo.leftColor;
				this.borderBottomColor = borderInfo.bottomColor;
				this.borderRightColor = borderInfo.rightColor;
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

	@Override
	public final void clearDelayedPairs() {
		final Collection<DelayedPair> gc = this.delayedPairs;
		if (gc != null) {
			gc.clear();
		}
	}

	protected final void clearGUIComponents() {
		final Collection<Component> gc = this.guiComponents;
		if (gc != null) {
			gc.clear();
		}
	}

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

	protected abstract void doLayout(int availWidth, int availHeight, boolean sizeOnly);

	public float getAlignmentX() {
		return 0.0f;
	}

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

	protected Integer getDeclaredHeight(RenderState renderState, int actualAvailHeight) {
		Integer dh = this.declaredHeight;
		if (dh == INVALID_SIZE || actualAvailHeight != this.lastAvailHeightForDeclared) {
			this.lastAvailHeightForDeclared = actualAvailHeight;
			final int dhInt = getDeclaredHeightImpl(renderState, actualAvailHeight);
			dh = dhInt == -1 ? null : new Integer(dhInt);
			this.declaredHeight = dh;
		}
		return dh;
	}

	protected int getDeclaredHeightImpl(RenderState renderState, int availHeight) {
		final Object rootNode = this.modelNode;
		if (rootNode instanceof HTMLElementImpl) {
			final HTMLElementImpl element = (HTMLElementImpl) rootNode;
			final CSS3Properties props = element.getCurrentStyle();
			if (props == null) {
				return -1;
			}
			final String heightText = props.getHeight();
			if (heightText == null || "".equals(heightText)) {
				return -1;
			}
			return HtmlValues.getPixelSize(heightText, renderState, -1, availHeight);
		} else {
			return -1;
		}
	}

	protected Integer getDeclaredWidth(RenderState renderState, int actualAvailWidth) {
		Integer dw = this.declaredWidth;
		if (dw == INVALID_SIZE || actualAvailWidth != this.lastAvailWidthForDeclared) {
			this.lastAvailWidthForDeclared = actualAvailWidth;
			final int dwInt = getDeclaredWidthImpl(renderState, actualAvailWidth);
			dw = dwInt == -1 ? null : new Integer(dwInt);
			this.declaredWidth = dw;
		}
		return dw;
	}

	private final int getDeclaredWidthImpl(RenderState renderState, int availWidth) {
		final Object rootNode = this.modelNode;
		if (rootNode instanceof HTMLElementImpl) {
			final HTMLElementImpl element = (HTMLElementImpl) rootNode;
			final CSS3Properties props = element.getCurrentStyle();
			if (props == null) {
				return -1;
			}
			final String widthText = props.getWidth();
			if (widthText == null || "".equals(widthText)) {
				return -1;
			}
			return HtmlValues.getPixelSize(widthText, renderState, -1, availWidth);
		} else {
			return -1;
		}
	}

	@Override
	public final Collection<DelayedPair> getDelayedPairs() {
		return this.delayedPairs;
	}
	
	@Override
	public int getInnerWidth() {
		final Insets insets = getInsetsMarginBorder(false, false);
		return getWidth() - (insets.left + insets.right);
	}
	
	@Override
	public int getInnerHeight() {
		final Insets insets = getInsetsMarginBorder(false, false);
		return getHeight() - (insets.top + insets.bottom);
	}
	
	@Override
    public Insets getInsets(final boolean hscroll, final boolean vscroll) {
        return getInsets(hscroll, vscroll, true, true, true);
    }

	@Override
    public Insets getInsetsMarginBorder(final boolean hscroll, final boolean vscroll) {
        return getInsets(hscroll, vscroll, true, true, false);
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

	@Override
	public int getMarginBottom() {
		final Insets marginInsets = this.marginInsets;
		return marginInsets == null ? 0 : marginInsets.bottom;
	}

	@Override
	public int getMarginLeft() {
		final Insets marginInsets = this.marginInsets;
		return marginInsets == null ? 0 : marginInsets.left;
	}

	@Override
	public int getMarginRight() {
		final Insets marginInsets = this.marginInsets;
		return marginInsets == null ? 0 : marginInsets.right;
	}

	@Override
	public int getMarginTop() {
		final Insets marginInsets = this.marginInsets;
		return marginInsets == null ? 0 : marginInsets.top;
	}

	@Override
	public RenderableContainer getParentContainer() {
		return this.container;
	}

	@Override
	public int getZIndex() {
		return this.zIndex;
	}

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
	 * Invalidates this Renderable and all descendents. This is only used in special
	 * cases, such as when a new style sheet is added.
	 */
	@Override
	public final void invalidateLayoutDeep() {
		if (this.layoutDeepCanBeInvalidated) {
			this.layoutDeepCanBeInvalidated = false;
			invalidateLayoutLocal();
			final Iterator<?> i = getRenderables();
			if (i != null) {
				while (i.hasNext()) {
					final Object r = i.next();
					if (r instanceof RCollection) {
						((RCollection) r).invalidateLayoutDeep();
					}
				}
			}
		}
	}

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

	@Override
	public boolean isContainedByNode() {
		return true;
	}

	protected boolean isMarginBoundary() {
		return this.overflowY != RenderState.OVERFLOW_VISIBLE && this.overflowX != RenderState.OVERFLOW_NONE
				|| this.modelNode instanceof HTMLDocumentImpl;
	}

	/**
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

	protected void loadBackgroundImage(final URL imageURL) {
		final UserAgentContext ctx = this.userAgentContext;
		if (ctx != null) {
			final HttpRequest request = ctx.createHttpRequest();
			request.addReadyStateChangeListener(() -> {
				final int readyState = request.getReadyState();
				if (readyState == HttpRequest.STATE_COMPLETE) {
					final int status = request.getStatus();
					if (status == 200 || status == 0) {
						final Image img = request.getResponseImage();
						if (img != null) {
							BaseElementRenderable.this.backgroundImage = img;
							// Cause observer to be called
							final int w = img.getWidth(BaseElementRenderable.this);
							final int h = img.getHeight(BaseElementRenderable.this);
							// Maybe image already done...
							if (w != -1 && h != -1) {
								BaseElementRenderable.this.repaint();
							}
						}
					}
				}
			});
			final SecurityManager sm = System.getSecurityManager();
			if (sm == null) {
				try {
					request.open("GET", imageURL);
					request.send(null);
				} catch (Exception thrown) {
					logger.log(Level.WARNING, "loadBackgroundImage()", thrown);
				}
			} else {
				AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
					// Code might have restrictions on accessing
					// items from elsewhere.
					try {
						request.open("GET", imageURL);
						request.send(null);
					} catch (Exception thrown) {
						logger.log(Level.WARNING, "loadBackgroundImage()", thrown);
					}
					return null;
				});
			}
		}
	}
	
	@Override
	public void paint(Graphics g) {}

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
			            final int imageX = getImageX(totalHeight, binfo, h);

			            final int baseX = (bkgBounds.x / w) * w - (w - imageX);
			            final int baseY = (bkgBounds.y / h) * h - (h - imageY);
			            
			            final int topX = bkgBounds.x + bkgBounds.width;
			            final int topY = bkgBounds.y + bkgBounds.height;
			            
			            switch (binfo == null ? BackgroundInfo.BR_REPEAT : binfo.backgroundRepeat) {
						case BackgroundInfo.BR_NO_REPEAT:
							int _imageX;
							if (binfo.backgroundXPositionAbsolute) {
								_imageX = binfo.backgroundXPosition;
							} else {
								_imageX = binfo.backgroundXPosition * (totalWidth - w) / 100;
							}
							int _imageY;
							if (binfo.backgroundYPositionAbsolute) {
								_imageY = binfo.backgroundYPosition;
							} else {
								_imageY = binfo.backgroundYPosition * (totalHeight - h) / 100;
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
					final int borderStyle = borderInfo == null ? HtmlValues.BORDER_STYLE_SOLID : borderInfo.topStyle;
					for (int i = 0; i < btop; i++) {
						final int leftOffset = i * bleft / btop;
						final int rightOffset = i * bright / btop;
						if (borderStyle == HtmlValues.BORDER_STYLE_DASHED) {
							GUITasks.drawDashed(g, startX + leftOffset, startY + i,
									startX + totalWidth - rightOffset - 1, startY + i, 10 + btop, 6);
						} else {
							g.drawLine(startX + leftOffset, startY + i, startX + totalWidth - rightOffset - 1,
									startY + i);
						}
					}
				}
				if (bright > 0) {
					final int borderStyle = borderInfo == null ? HtmlValues.BORDER_STYLE_SOLID : borderInfo.rightStyle;
					g.setColor(getBorderRightColor());
					final int lastX = startX + totalWidth - 1;
					for (int i = 0; i < bright; i++) {
						final int topOffset = i * btop / bright;
						final int bottomOffset = i * bbottom / bright;
						if (borderStyle == HtmlValues.BORDER_STYLE_DASHED) {
							GUITasks.drawDashed(g, lastX - i, startY + topOffset, lastX - i,
									startY + totalHeight - bottomOffset - 1, 10 + bright, 6);
						} else {
							g.drawLine(lastX - i, startY + topOffset, lastX - i,
									startY + totalHeight - bottomOffset - 1);
						}
					}
				}
				if (bbottom > 0) {
					final int borderStyle = borderInfo == null ? HtmlValues.BORDER_STYLE_SOLID : borderInfo.bottomStyle;
					g.setColor(getBorderBottomColor());
					final int lastY = startY + totalHeight - 1;
					for (int i = 0; i < bbottom; i++) {
						final int leftOffset = i * bleft / bbottom;
						final int rightOffset = i * bright / bbottom;
						if (borderStyle == HtmlValues.BORDER_STYLE_DASHED) {
							GUITasks.drawDashed(g, startX + leftOffset, lastY - i,
									startX + totalWidth - rightOffset - 1, lastY - i, 10 + bbottom, 6);
						} else {
							g.drawLine(startX + leftOffset, lastY - i, startX + totalWidth - rightOffset - 1,
									lastY - i);
						}
					}
				}
				if (bleft > 0) {
					final int borderStyle = borderInfo == null ? HtmlValues.BORDER_STYLE_SOLID : borderInfo.leftStyle;
					g.setColor(getBorderLeftColor());
					for (int i = 0; i < bleft; i++) {
						final int topOffset = i * btop / bleft;
						final int bottomOffset = i * bbottom / bleft;
						if (borderStyle == HtmlValues.BORDER_STYLE_DASHED) {
							GUITasks.drawDashed(g, startX + i, startY + topOffset, startX + i,
									startY + totalHeight - bottomOffset - 1, 10 + bleft, 6);
						} else {
							g.drawLine(startX + i, startY + topOffset, startX + i,
									startY + totalHeight - bottomOffset - 1);
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

	protected final void sendDelayedPairsToParent() {
		// Ensures that parent has all the components
		// below this renderer node. (Parent expected to have removed them).
		final Collection<DelayedPair> gc = this.delayedPairs;
		if (gc != null) {
			final RenderableContainer rc = this.container;
			final Iterator<DelayedPair> i = gc.iterator();
			while (i.hasNext()) {
				final DelayedPair pair = (DelayedPair) i.next();
				if (pair.containingBlock != this) {
					rc.addDelayedPair(pair);
				}
			}
		}
	}

	protected final void sendGUIComponentsToParent() {
		// Ensures that parent has all the components
		// below this renderer node. (Parent expected to have removed them).
		final Collection<Component> gc = this.guiComponents;
		if (gc != null) {
			final RenderableContainer rc = this.container;
			final Iterator<Component> i = gc.iterator();
			while (i.hasNext()) {
				rc.addComponent((Component) i.next());
			}
		}
	}

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
			if (binfo.backgroundYPositionAbsolute) {
				return binfo.backgroundYPosition;
			} else {
				return (binfo.backgroundYPosition * (totalHeight - h)) / 100;
			}
		}
	}

	private static int getImageX(final int totalWidth, final BackgroundInfo binfo, final int w) {
		if (binfo == null) {
			return 0;
		} else {
			if (binfo.backgroundXPositionAbsolute) {
				return binfo.backgroundXPosition;
			} else {
				return (binfo.backgroundXPosition * (totalWidth - w)) / 100;
			}
		}
	}
}
