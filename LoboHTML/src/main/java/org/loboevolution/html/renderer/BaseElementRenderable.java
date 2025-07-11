/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.renderer;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.common.GUITasks;
import org.loboevolution.common.Strings;
import org.loboevolution.css.CSS2Properties;
import org.loboevolution.css.CSS3Properties;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.HTMLTag;
import org.loboevolution.html.dom.HTMLDocument;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLImageElementImpl;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.ModelNode;
import org.loboevolution.gui.HtmlPanel;
import org.loboevolution.css.CSSStyleDeclaration;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.BorderInsets;
import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.info.BackgroundInfo;
import org.loboevolution.info.BorderInfo;
import org.loboevolution.info.TimingInfo;
import org.loboevolution.laf.ColorFactory;
import org.loboevolution.net.HttpNetwork;
import org.loboevolution.svg.dom.SVGElementImpl;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.util.List;
import java.util.*;
import java.util.function.Function;

/**
 * <p>Abstract BaseElementRenderable class.</p>
 */
@Slf4j
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
	 * @param modelNode a {@link ModelNode} object.
	 * @param ucontext a {@link org.loboevolution.http.UserAgentContext} object.
	 */
	public BaseElementRenderable(final RenderableContainer container, final ModelNode modelNode, final UserAgentContext ucontext) {
		super(container, modelNode);
		this.userAgentContext = ucontext;
	}

	/** {@inheritDoc} */
	@Override
	public Component addComponent(final Component component) {
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

	/** {@inheritDoc} */
	@Override
	public void addDelayedPair(final DelayedPair pair) {
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
	 * @param availWidth a {@link java.lang.Integer} object.
	 * @param availHeight a {@link java.lang.Integer} object.
	 */
	protected void applyStyle(final int availWidth, final int availHeight) {
		final ModelNode rootNode = this.modelNode;
		final Element rootElement;
		final boolean isRootBlock;

		if (rootNode instanceof HTMLDocument) {
			isRootBlock = true;
			final HTMLDocumentImpl doc = (HTMLDocumentImpl) rootNode;
			rootElement = (HTMLElementImpl) doc.getElementsByName(HTMLTag.HTML.getValue()).item(0);
		} else {
			isRootBlock = false;
			rootElement = (Element) rootNode;
		}


		if (rootElement == null) {
			clearStyle(isRootBlock);
			return;
		}
		final RenderState rs = (RenderState) rootElement.getRenderState();
		if (rs == null) {
			throw new IllegalStateException("Element without render state: " + rootElement + "; parent=" + rootElement.getParentNode());
		}

		backgroundApplyStyle(rs);

		CSSStyleDeclaration style;
		if (rootElement instanceof SVGElementImpl) {
			style = ((SVGElementImpl)rootElement).getCurrentStyle();
		} else{
			style = ((HTMLElementImpl)rootElement).getCurrentStyle();
		}
		if (style == null) {
			clearStyle(isRootBlock);
		} else {

			insetsApplyStyle(rs, availWidth, availHeight, isRootBlock);

			zIndexApplyStyle(style);

			this.overflowX = rs.getOverflowX();
			this.overflowY = rs.getOverflowY();
		}
	}

	private void backgroundApplyStyle(final RenderState rs) {
		binfo = rs.getBackgroundInfo();
		this.backgroundColor = binfo == null ? null : binfo.getBackgroundColor();
		final URL backgroundImageUri = binfo == null ? null : binfo.getBackgroundImage();
		if (backgroundImageUri == null) {
			this.backgroundImage = null;
			this.lastBackgroundImageUri = null;
		} else if (!Objects.equals(backgroundImageUri, this.lastBackgroundImageUri)) {
			this.lastBackgroundImageUri = backgroundImageUri;
			final HTMLImageElementImpl img = new HTMLImageElementImpl();
			final TimingInfo info = new TimingInfo();
			img.setSrc(lastBackgroundImageUri.toString());
			backgroundImage = HttpNetwork.getImage(img, info, false);
			if (backgroundImage != null) {
				final int w = backgroundImage.getWidth(BaseElementRenderable.this);
				final int h = backgroundImage.getHeight(BaseElementRenderable.this);
				if (w != -1 && h != -1) {
					BaseElementRenderable.this.repaint();
				}

				final HtmlRendererContext htmlRendererContext = img.getHtmlRendererContext();
				if (htmlRendererContext != null) {
					final HtmlPanel htmlPanel = htmlRendererContext.getHtmlPanel();
					htmlPanel.getBrowserPanel().getTimingList.add(info);
				}
			}
		}
	}

	private void insetsApplyStyle(final RenderState rs, final int availWidth, final int availHeight, final boolean isRootBlock) {
		borderInsets(rs, availWidth, availHeight);
		final Insets paddingInsets = paddingInsets(rs, availWidth, availHeight);
		final Insets tentativeMarginInsets = marginInsets(rs, availWidth, availHeight);

		if (isRootBlock) {
			final int top = paddingInsets.top + tentativeMarginInsets.top;
			final int left = paddingInsets.left + tentativeMarginInsets.left;
			final int bottom = paddingInsets.top + tentativeMarginInsets.bottom;
			final int right = paddingInsets.top + tentativeMarginInsets.right;

			this.paddingInsets = new Insets(top, left, bottom, right);
			this.marginInsets = null;
		} else {
			this.paddingInsets = paddingInsets;
			this.marginInsets = tentativeMarginInsets;
		}
	}

	private void borderInsets(final RenderState rs, final int availWidth, final int availHeight) {
		Insets ins = null;
		final BorderInfo borderInfo = rs.getBorderInfo();
		this.borderInfo = borderInfo;

		if (borderInfo != null) {
			final HtmlInsets html = (HtmlInsets) borderInfo.getInsets();
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
	}

	private Insets marginInsets(final RenderState rs, final int availWidth, final int availHeight) {
		final Insets ins;
		final HtmlInsets html = rs.getMarginInsets();
		if (html == null) {
			ins = RBlockViewport.ZERO_INSETS;
		} else {
			ins = html.getAWTInsets(availWidth, availHeight, 0, 0);
		}
		return ins;
	}

	private Insets paddingInsets(final RenderState rs, final int availWidth, final int availHeight) {
		final Insets ins;
		final HtmlInsets html = rs.getPaddingInsets();
		if (html == null) {
			ins = RBlockViewport.ZERO_INSETS;
		} else {
			ins = html.getAWTInsets(availWidth, availHeight, 0, 0);
		}
		return ins;
	}

	private void zIndexApplyStyle(final CSSStyleDeclaration props) {
		final String zIndex = props.getzIndex();
		if (Strings.isNotBlank(zIndex) && this.modelNode instanceof HTMLElementImpl element) {
            final HTMLDocumentImpl doc =  (HTMLDocumentImpl)element.getDocumentNode();
			try {
				this.zIndex =  HtmlValues.getPixelSize(zIndex, null, doc.getDefaultView(), 0);
			} catch (final NumberFormatException err) {
				log.warn("Unable to parse z-index {} in element {} ", zIndex, this.modelNode, err);
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
	protected void clearStyle(final boolean isRootBlock) {
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
		setupRelativePosition((RenderState)getModelNode().getRenderState(), container.getInnerWidth(), container.getInnerHeight());
	}

	private void setupRelativePosition(final RenderState rs, final int availWidth, final int availHeight) {
		if (rs.getPosition() == RenderState.POSITION_RELATIVE) {
			final HTMLElementImpl element = (HTMLElementImpl) this.modelNode;
			final HTMLDocumentImpl doc = (HTMLDocumentImpl) element.getDocumentNode();
			final String leftText = rs.getLeft();
			final String rightText = rs.getRight();
			int left = 0;
			if (leftText != null) {
				left = HtmlValues.getPixelSize(leftText, rs, doc.getDefaultView(), 0, availWidth);
			}

			if (rightText != null) {
				final int right = HtmlValues.getPixelSize(rightText, rs, doc.getDefaultView(), 0, availWidth);
				left = -right;
			}

			int top = 0;
			final String topText = rs.getTop();
			final String bottomText = rs.getBottom();
			if (topText != null) {
				top = HtmlValues.getPixelSize(topText, rs, doc.getDefaultView(), top, availHeight);
			}

			if (bottomText != null) {
				final int bottom = HtmlValues.getPixelSize(bottomText, rs, doc.getDefaultView(), 0, availHeight);
				top = -bottom;
			}

			this.relativeOffsetX = left;
			this.relativeOffsetY = top;
		}
	}

	/**
	 * <p>doLayout.</p>
	 *
	 * @param availWidth a {@link java.lang.Integer} object.
	 * @param availHeight a {@link java.lang.Integer} object.
	 * @param sizeOnly a boolean.
	 */
	protected abstract void doLayout(int availWidth, final int availHeight, boolean sizeOnly);

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
			final RenderState rs = (RenderState)this.modelNode.getRenderState();
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
			final RenderState rs = (RenderState)this.modelNode.getRenderState();
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
	 * @param actualAvailHeight a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	public Integer getDeclaredHeight(final int actualAvailHeight) {
		Integer dh = this.declaredHeight;
		if (INVALID_SIZE.equals(dh) || actualAvailHeight != this.lastAvailHeightForDeclared) {
			this.lastAvailHeightForDeclared = actualAvailHeight;
			if (modelNode instanceof HTMLElementImpl) {
				final int dhInt = getDeclaredHeightImpl((HTMLElementImpl) modelNode, actualAvailHeight);
				dh = dhInt == -1 ? null : dhInt;
			} else {
				dh= -1;
			}
			this.declaredHeight = dh;
		}
		return dh;
	}

	/**
	 * <p>Getter for the field declaredWidth.</p>
	 *
	 * @param actualAvailWidth a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	protected Integer getDeclaredWidth(final int actualAvailWidth) {
		Integer dw = this.declaredWidth;
		if (INVALID_SIZE.equals(dw) || actualAvailWidth != this.lastAvailWidthForDeclared) {
			this.lastAvailWidthForDeclared = actualAvailWidth;
			if (modelNode instanceof HTMLElementImpl) {
				final int dwInt = getDeclaredWidthImpl((HTMLElementImpl) modelNode, actualAvailWidth);
				dw = dwInt == -1 ? null : dwInt;
			} else {
				dw = -1;
			}
			this.declaredWidth = dw;
		}
		return dw;
	}

	protected Integer getDeclaredMaxWidth(final RenderState renderState, final int actualAvailWidth) {
		return getDeclaredHelper(renderState, actualAvailWidth, CSS2Properties::getMaxWidth, !isParentWidthDeclared());
	}

	protected Integer getDeclaredMinWidth(final RenderState renderState, final int actualAvailWidth) {
		return getDeclaredHelper(renderState, actualAvailWidth, CSS2Properties::getMinWidth, !isParentWidthDeclared());
	}

	protected Integer getDeclaredMaxHeight(final RenderState renderState, final int actualAvailHeight) {
		return getDeclaredHelper(renderState, actualAvailHeight, CSS2Properties::getMaxHeight, !isParentHeightDeclared());
	}

	protected Integer getDeclaredMinHeight(final RenderState renderState, final int actualAvailHeight) {
		return getDeclaredHelper(renderState, actualAvailHeight, CSS2Properties::getMinHeight, !isParentHeightDeclared());
	}

	private Integer getDeclaredHelper(final RenderState renderState, final int baseValue,
										final Function<CSS3Properties, String> propertyGetter, final boolean ignorePercentage) {
		final Object rootNode = this.modelNode;
		if (rootNode instanceof HTMLElementImpl element) {
            final HTMLDocumentImpl doc =  (HTMLDocumentImpl)element.getDocumentNode();
			final CSS3Properties props = element.getCurrentStyle();
			final String valueText = propertyGetter.apply(props);
			if (Strings.isBlank(valueText) || "none".equals(valueText) || (ignorePercentage && valueText.endsWith("%"))) {
				return null;
			}
			return HtmlValues.getPixelSize(valueText, renderState, doc.getDefaultView().getWindow(), -1, baseValue);
		} else {
			return null;
		}
	}

	private boolean isParentWidthDeclared() {
		final ModelNode parentNode = getModelNode().getParentModelNode();
		if (parentNode instanceof HTMLElementImpl element) {
            final CSS2Properties props = element.getCurrentStyle();
			final String decWidth = props.getWidth();
			return !(Strings.isBlank(decWidth) || "auto".equals(decWidth));
		}
		return false;
	}

	private boolean isParentHeightDeclared() {
		final ModelNode parentNode = getModelNode().getParentModelNode();
		if (parentNode instanceof HTMLElementImpl element) {
            final CSS2Properties props = element.getCurrentStyle();
			final String decHeight = props.getHeight();
			return !(Strings.isBlank(decHeight) || "auto".equals(decHeight));
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public final List<DelayedPair> getDelayedPairs() {
		return this.delayedPairs;
	}

	/** {@inheritDoc} */
	@Override
	public int getInnerWidth() {
		final Object rootNode = this.modelNode;
		if (rootNode instanceof HTMLDocument) {
			final HTMLDocumentImpl doc = (HTMLDocumentImpl) rootNode;
			return doc.getHtmlRendererContext().getInnerWidth();
		}

		if (rootNode instanceof HTMLElementImpl elem) {
            return elem.getHtmlRendererContext().getInnerWidth();
		}

		return getWidth();
	}

	/** {@inheritDoc} */
	@Override
	public int getInnerHeight() {
		final Object rootNode = this.modelNode;
		if (rootNode instanceof HTMLDocument) {
			final HTMLDocumentImpl doc = (HTMLDocumentImpl) rootNode;
			return doc.getHtmlRendererContext().getInnerHeight();
		}

		if (rootNode instanceof HTMLElementImpl elem) {
            return elem.getHtmlRendererContext().getInnerHeight();
		}

		return getHeight();
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
			if (rootNode instanceof HTMLElementImpl element) {
                final CSSStyleDeclaration props = element.getCurrentStyle();
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
	public boolean imageUpdate(final Image img, final int infoflags, final int x, final int y, final int w, final int h) {
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
	public void layout(final int availWidth, final int availHeight, final boolean sizeOnly) {
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
			final List<Renderable> renderables = getRenderables();
			if (renderables != null) {
				renderables.forEach(rn -> {
					final Renderable r = (rn instanceof PositionedRenderable) ? ((PositionedRenderable) rn).getRenderable() : rn;
					if (r instanceof RCollection) {
						((RCollection) r).invalidateLayoutDeep();
					}
				});
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public Point translateDescendentPoint(final BoundableRenderable descendent, final int x, final int y) {
		final Point p = descendent.getOriginRelativeTo(this);
		p.translate(x, y);
		return p;
	}

	/** {@inheritDoc} */
	@Override
	protected void invalidateLayoutLocal() {
		final RenderState rs = (RenderState)this.modelNode.getRenderState();
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
				|| this.modelNode instanceof HTMLDocument;
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g) {}

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
			return new Rectangle(insets.left, insets.top, this.getWidth() - hInset, this.getHeight() - vInset);
		}
	}

	/**
	 * <p>sendDelayedPairsToParent.</p>
	 */
	protected final void sendDelayedPairsToParent() {
		final Collection<DelayedPair> gc = this.delayedPairs;
		if (gc != null) {
            for (final DelayedPair pair : gc) {
				if (pair.getContainingBlock() != this) {
					this.container.addDelayedPair(pair);
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
            for (final Component component : gc) {
				this.container.addComponent(component);
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
	protected void prePaint(final Graphics g) {
		final int startWidth = this.getWidth();
		final int startHeight = this.getHeight();
		int totalWidth = startWidth;
		int totalHeight = startHeight;
		int startX = 0;
		int startY = 0;

		final Insets marginInsets = this.marginInsets;
		if (marginInsets != null) {
			final Object rootNode = this.modelNode;
			RenderState rs = null;
			if (rootNode instanceof HTMLElementImpl element) {
                rs = element.getRenderState();
			}

			if (rs == null || (RenderState.POSITION_ABSOLUTE != rs.getPosition() && RenderState.POSITION_FIXED != rs.getPosition())) {
				totalWidth -= marginInsets.left + marginInsets.right;
				totalHeight -= marginInsets.top + marginInsets.bottom;
				startX += marginInsets.left;
				startY += marginInsets.top;
			}
		}

		prePaintBackground(g, this.modelNode, totalWidth, totalHeight, startX, startY);

		prePaintBorder(g, totalWidth, totalHeight, startX, startY);

	}

	private void prePaintBackground(final Graphics g, final ModelNode node, final int totalWidth, final int totalHeight, final int startX, final int startY) {
		final RenderState rs = (RenderState) node.getRenderState();
		final Graphics clientG = g.create(startX, startY, totalWidth, totalHeight);
		try {
			Rectangle bkgBounds = null;
			final Color bkg = this.backgroundColor;
			if (bkg != null && bkg.getAlpha() > 0) {
				clientG.setColor(bkg);
				bkgBounds = clientG.getClipBounds();
				clientG.fillRect(bkgBounds.x, bkgBounds.y, bkgBounds.width, bkgBounds.height);
			}

			final Image image = this.backgroundImage;
			if (image != null) {
				if (bkgBounds == null) {
					bkgBounds = clientG.getClipBounds();
				}
				final int w = image.getWidth(this);
				final int h = image.getHeight(this);
				if (w != -1 && h != -1) {

					if (rs != null) {
						binfo = rs.getBackgroundImageInfo(w, h);
					}

					if(binfo != null && binfo.getBackgroundXPosition() == 0) {
						binfo.setBackgroundXPosition(startX);
					}

					if(binfo != null && binfo.getBackgroundYPosition() == 0) {
						binfo.setBackgroundYPosition(startY);
					}

					final int imageY = binfo == null ? 0 : binfo.getBackgroundYPosition();
					final int imageX = binfo == null ? 0 : binfo.getBackgroundXPosition();

					final int baseX = (bkgBounds.x / w) * w - (w - imageX);
					final int baseY = (bkgBounds.y / h) * h - (h - imageY);

					final int topX = bkgBounds.x + bkgBounds.width;
					final int topY = bkgBounds.y + bkgBounds.height;

					switch (binfo == null ? BackgroundInfo.BR_REPEAT : binfo.getBackgroundRepeat()) {
						case BackgroundInfo.BR_NO_REPEAT:
							g.drawImage(image, imageX, 	imageY, w, h, this);
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
		} finally {
			clientG.dispose();
		}
	}

	private void prePaintBorder(final Graphics g, final int totalWidth, final int totalHeight, final int startX, final int startY) {

		final Insets borderInsets = this.borderInsets;

		if (borderInsets != null && !(this.modelNode instanceof HTMLDocument)) {
			final int btop = borderInsets.top;
			final int bleft = borderInsets.left;
			final int bright = borderInsets.right;
			final int bbottom = borderInsets.bottom;

			final int newTotalWidth = totalWidth - (bleft + bright);
			final int newTotalHeight = totalHeight - (btop + bbottom);
			final int newStartX = startX + bleft;
			final int newStartY = startY + btop;
			final Rectangle clientRegion = new Rectangle(newStartX, newStartY, newTotalWidth, newTotalHeight);
			final Rectangle clipBounds = g.getClipBounds();
			if (clipBounds == null || !clientRegion.contains(clipBounds)) {
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

	private void paintBorder(final Graphics g, final int x1, final int y1, final int x2, final int y2, final int dashSize, final int width, final int borderStyle) {

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
}
