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

import org.loboevolution.html.HTMLTag;
import org.loboevolution.html.HtmlObject;
import org.loboevolution.html.control.*;
import org.loboevolution.html.dom.domimpl.*;
import org.loboevolution.html.dom.rss.RSSElementImpl;
import org.loboevolution.svg.SVGSVGElementImpl;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.http.UserAgentContext;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>RLayout class.</p>
 */
public class RLayout {

	/** Constant elementLayout */
	protected static final Map<HTMLTag, MarkupLayout> elementLayout = new HashMap<>();

	static {
		final Map<HTMLTag, MarkupLayout> el = elementLayout;

		final InLineLayout inline = new InLineLayout();
		el.put(HTMLTag.A, inline);
		el.put(HTMLTag.ANCHOR, inline);
		el.put(HTMLTag.B, inline);
		el.put(HTMLTag.CITE, inline);
		el.put(HTMLTag.CODE, inline);
		el.put(HTMLTag.EM, inline);
		el.put(HTMLTag.I, inline);
		el.put(HTMLTag.KBD, inline);
		el.put(HTMLTag.SAMP, inline);
		el.put(HTMLTag.STRONG, inline);
		el.put(HTMLTag.STRIKE, inline);
		el.put(HTMLTag.SPAN, inline);
		el.put(HTMLTag.TH, inline);
		el.put(HTMLTag.U, inline);
		el.put(HTMLTag.VAR, inline);

		final BlockLayout block = new BlockLayout();
		el.put(HTMLTag.ARTICLE, block);
		el.put(HTMLTag.BODY, block);
		el.put(HTMLTag.BLOCKQUOTE, block);
		el.put(HTMLTag.CAPTION, block);
		el.put(HTMLTag.CENTER, block);
		el.put(HTMLTag.DD, block);
		el.put(HTMLTag.DIV, block);
		el.put(HTMLTag.ADDRESS, block);
		el.put(HTMLTag.DL, block);
		el.put(HTMLTag.DT, block);
		el.put(HTMLTag.H1, block);
		el.put(HTMLTag.H2, block);
		el.put(HTMLTag.H3, block);
		el.put(HTMLTag.H4, block);
		el.put(HTMLTag.H5, block);
		el.put(HTMLTag.H6, block);
		el.put(HTMLTag.HTML, block);
		el.put(HTMLTag.MAIN, block);
		el.put(HTMLTag.PRE, block);
		el.put(HTMLTag.DETAILS, block);
		el.put(HTMLTag.P, block);

		el.put(HTMLTag.BR,new BrLayout());

		el.put(HTMLTag.NOSCRIPT,new NoScriptLayout());
		final NopLayout nop = new NopLayout();
		el.put(HTMLTag.SCRIPT,nop);
		el.put(HTMLTag.HEAD,nop);
		el.put(HTMLTag.TITLE,nop);
		el.put(HTMLTag.META,nop);
		el.put(HTMLTag.STYLE,nop);
		el.put(HTMLTag.LINK,nop);
		el.put(HTMLTag.IMG,new ImgLayout());
		el.put(HTMLTag.TABLE,new TableLayout());
		el.put(HTMLTag.TR, new TableRowLayout());
		el.put(HTMLTag.TD, new TableCellLayout());
		el.put(HTMLTag.INPUT,new InputLayout());
		el.put(HTMLTag.TEXTAREA,new TextAreaLayout());
		el.put(HTMLTag.SELECT,new SelectLayout());
		el.put(HTMLTag.BUTTON, new ButtonLayout());
		final ListItemLayout list = new ListItemLayout();
		el.put(HTMLTag.UL,list);
		el.put(HTMLTag.OL,list);
		el.put(HTMLTag.LI,list);

		final ObjectLayout ol = new ObjectLayout(false);
		el.put(HTMLTag.OBJECT,new ObjectLayout(true));
		el.put(HTMLTag.APPLET,ol);
		el.put(HTMLTag.EMBED,ol);
		el.put(HTMLTag.CANVAS,new CanvasLayout());
		el.put(HTMLTag.SVG,new SVGLayout());
		el.put(HTMLTag.IFRAME,new IFrameLayout());
		el.put(HTMLTag.RSS,new RSSLayout());
	}

	protected static class InLineLayout extends CommonLayout {
		public InLineLayout() {
			super(RenderState.DISPLAY_INLINE);
		}
	}

	protected static class BlockLayout extends CommonLayout {
		public BlockLayout() {
			super(RenderState.DISPLAY_BLOCK);
		}
	}

	protected static class BrLayout implements MarkupLayout {

		@Override
		public void layoutMarkup(final RBlockViewport bodyLayout, final HTMLElementImpl markupElement) {
			final String clear = markupElement.getAttribute("clear");
			bodyLayout.addLineBreak(markupElement, LineBreak.getBreakType(clear));
		}
	}

	protected abstract static class CommonLayout implements MarkupLayout {

	    private final int display;

		public CommonLayout(final int defaultDisplay) {
			this.display = defaultDisplay;
		}

		@Override
		public void layoutMarkup(final RBlockViewport bodyLayout, final HTMLElementImpl markupElement) {
			final int display = calculateLayout(markupElement);

			switch (display) {
				case RenderState.DISPLAY_TABLE_COLUMN:
				case RenderState.DISPLAY_TABLE_COLUMN_GROUP:
				case RenderState.DISPLAY_NONE:
					final UINode node = markupElement.getUINode();
					if (node instanceof BaseBoundableRenderable) {
						((BaseBoundableRenderable) node).markLayoutValid();
					}
					break;
				case RenderState.DISPLAY_BLOCK:
				case RenderState.DISPLAY_TABLE_ROW:
					bodyLayout.layoutRBlock(markupElement);
					break;
				case RenderState.DISPLAY_LIST_ITEM:
					final String tagName = markupElement.getTagName();
					if ("UL".equalsIgnoreCase(tagName) || "OL".equalsIgnoreCase(tagName)) {
						bodyLayout.layoutList(markupElement);
					} else {
						bodyLayout.layoutListItem(markupElement);
					}
					break;
				case RenderState.DISPLAY_TABLE:
					bodyLayout.layoutRTable(markupElement);
					break;
				case RenderState.DISPLAY_INLINE_TABLE:
				case RenderState.DISPLAY_INLINE_BLOCK:
					bodyLayout.layoutRInlineBlock(markupElement);
					break;
				case RenderState.DISPLAY_FLEX_BOX:
					bodyLayout.layoutRFlex(markupElement);
					break;
				case RenderState.DISPLAY_FLEX_CHILD:
					bodyLayout.layoutChildFlex(markupElement);
					break;
				case RenderState.DISPLAY_TABLE_CELL:
				default:
					bodyLayout.layoutMarkup(markupElement);
					break;
			}
		}

		private int calculateLayout(final HTMLElementImpl markupElement) {
			final RenderState rs = markupElement.getRenderState();
			final boolean isHidden = markupElement.isHidden();
			final int defaultDispaly = rs == null ? this.display : rs.getDisplay();
			int display = isHidden ? RenderState.DISPLAY_NONE : defaultDispaly;

			if (display == RenderState.DISPLAY_INLINE || display == RenderState.DISPLAY_INLINE_BLOCK) {
	            final int position = rs == null ? RenderState.POSITION_STATIC : rs.getPosition();
	            if (position == RenderState.POSITION_ABSOLUTE || position == RenderState.POSITION_FIXED) {
	                display = RenderState.DISPLAY_BLOCK;
	            } else {
	                final int boxFloat = rs == null ? RenderState.FLOAT_NONE : rs.getFloat();
	                if (boxFloat != RenderState.FLOAT_NONE) {
	                    display = RenderState.DISPLAY_BLOCK;
	                }
	            }
	        }
			return display;
		}
	}

	/**
	 * This is layout common to elements that render themselves, except RBlock,
	 * RTable and RList.
	 */
	protected abstract static class CommonWidgetLayout implements MarkupLayout {
		protected static final int ADD_INLINE = 0;
	    protected static final int ADD_AS_BLOCK = 1;
	    protected static final int ADD_INLINE_BLOCK = 2;
	    private final int method;

	    public CommonWidgetLayout(final int method) {
	    	this.method = method;
	    }

	    protected abstract RElement createRenderable(final RBlockViewport bodyLayout, final HTMLElementImpl markupElement);

	    @Override
	    public void layoutMarkup(final RBlockViewport bodyLayout, final HTMLElementImpl markupElement) {
	        final UINode node = markupElement.getUINode();
	        final RElement renderable;
	        if (node == null) {
	            renderable = createRenderable(bodyLayout, markupElement);
	            if (renderable == null) {
	                return;
	            }
	            markupElement.setUINode(renderable);
	        } else {
	            renderable = (RElement) node;
	        }
	        renderable.setOriginalParent(bodyLayout);
	        switch (method) {
	        case ADD_INLINE:
	            bodyLayout.addRenderableToLineCheckStyle(renderable, markupElement);
	            break;
	        case ADD_AS_BLOCK:
	        case ADD_INLINE_BLOCK:
	            bodyLayout.positionRElement(markupElement, renderable, true, true, false);
	            break;
	        default:
				break;
	        }
	    }
	}

	protected static class CanvasLayout extends CommonWidgetLayout {
		public CanvasLayout() {
			super(ADD_AS_BLOCK);
		}

		@Override
		protected RElement createRenderable(final RBlockViewport bodyLayout, final HTMLElementImpl markupElement) {
			final UIControl control = new CanvasControl((HTMLCanvasElementImpl) markupElement);
			return new RUIControl(markupElement,control, bodyLayout.container, bodyLayout.userAgentContext);
		}
	}

	protected static class SVGLayout extends CommonWidgetLayout {
		public SVGLayout() {
			super(ADD_AS_BLOCK);
		}

		@Override
		protected RElement createRenderable(final RBlockViewport bodyLayout, final HTMLElementImpl markupElement) {
			final UIControl control = new SVGControl((SVGSVGElementImpl) markupElement);
			return new RUIControl(markupElement,control, bodyLayout.container, bodyLayout.userAgentContext);
		}
	}

	protected static class RSSLayout extends CommonWidgetLayout {
		public RSSLayout() {
			super(ADD_AS_BLOCK);
		}

		@Override
		protected RElement createRenderable(final RBlockViewport bodyLayout, final HTMLElementImpl markupElement) {
			final UIControl control = new RSSControl((RSSElementImpl) markupElement);
			return new RUIControl(markupElement,control, bodyLayout.container, bodyLayout.userAgentContext);
		}
	}

	protected static class ImgLayout extends CommonWidgetLayout {

		public ImgLayout() {
			super(ADD_INLINE);
		}

		@Override
		protected RElement createRenderable(final RBlockViewport bodyLayout, final HTMLElementImpl markupElement) {
			final UIControl control;
			final HTMLImageElementImpl image = (HTMLImageElementImpl) markupElement;
			if (image.getSrc() != null && image.getSrc().endsWith(".svg")) {
				control = new ImgSvgControl(image);
			} else {
				control = new ImgControl(image);
			}

			return new RImgControl(markupElement, control, bodyLayout.container, bodyLayout.userAgentContext);
		}
	}

	protected static class InputLayout extends CommonWidgetLayout {

		public InputLayout() {
			super(ADD_INLINE);
		}

		@Override
		protected RElement createRenderable(final RBlockViewport bodyLayout, final HTMLElementImpl markupElement) {
			final UIControl control = new InputControl((HTMLInputElementImpl) markupElement);
			return new RUIControl(markupElement,control, bodyLayout.container, bodyLayout.userAgentContext);
		}
	}

	protected static class IFrameLayout extends CommonWidgetLayout {
		public IFrameLayout() {
			super(ADD_INLINE);
		}

		@Override
		protected RElement createRenderable(final RBlockViewport bodyLayout, final HTMLElementImpl markupElement) {
			final UIControl control = new FrameControl((HTMLIFrameElementImpl)markupElement);
			return new RUIControl(markupElement, control, bodyLayout.container, bodyLayout.userAgentContext);
		}
	}

	protected static class ListItemLayout extends CommonLayout {
		public ListItemLayout() {
			super(RenderState.DISPLAY_LIST_ITEM);
		}
	}

	protected static class MiscLayout extends CommonLayout {
		public MiscLayout() {
			super(RenderState.DISPLAY_INLINE);
		}
	}

	protected static class NopLayout implements MarkupLayout {
		@Override
		public void layoutMarkup(final RBlockViewport bodyLayout, final HTMLElementImpl markupElement) {
		}
	}

	protected static class NoScriptLayout implements MarkupLayout {
		@Override
		public void layoutMarkup(final RBlockViewport bodyLayout, final HTMLElementImpl markupElement) {
			final UserAgentContext ucontext = bodyLayout.userAgentContext;
			if (!ucontext.isScriptingEnabled()) {
				bodyLayout.layoutMarkup(markupElement);
			}
		}
	}

	protected static class ObjectLayout extends CommonWidgetLayout {
		/**
		 * Must use this ThreadLocal because an ObjectLayout instance is shared across
		 * renderers.
		 */
		private final ThreadLocal<HtmlObject> htmlObject = new ThreadLocal<>();

		private final boolean tryToRenderContent;

		/**
		 * @param tryToRenderContent If the object is unknown, content is rendered as  HTML.
		 */
		public ObjectLayout(final boolean tryToRenderContent) {
			super(ADD_INLINE);
			this.tryToRenderContent = tryToRenderContent;
		}

		@Override
		protected RElement createRenderable(final RBlockViewport bodyLayout, final HTMLElementImpl markupElement) {
			final HtmlObject ho = this.htmlObject.get();
			final UIControl uiControl = new UIControlWrapper(ho);
			return new RUIControl(markupElement, uiControl, bodyLayout.container, bodyLayout.userAgentContext);
		}

		@Override
		public void layoutMarkup(final RBlockViewport bodyLayout, final HTMLElementImpl markupElement) {
			if (this.tryToRenderContent) {
				bodyLayout.layoutMarkup(markupElement);
			}
		}
	}

	protected static class SelectLayout extends CommonWidgetLayout {
		public SelectLayout() {
			super(ADD_INLINE);
		}

		@Override
		protected RElement createRenderable(final RBlockViewport bodyLayout, final HTMLElementImpl markupElement) {
			final UIControl control = new SelectControl((HTMLSelectElementImpl) markupElement);
			return new RUIControl(markupElement, control, bodyLayout.container, bodyLayout.userAgentContext);
		}
	}

	protected static class TableLayout extends CommonLayout {
		public TableLayout() {
			super(RenderState.DISPLAY_TABLE);
		}
	}

	protected static class TableRowLayout extends CommonLayout {
		public TableRowLayout() {
			super(RenderState.DISPLAY_TABLE_ROW);
		}
	}

	protected static class TableCellLayout extends CommonLayout {
		public TableCellLayout() {
			super(RenderState.DISPLAY_TABLE_CELL);
		}
	}

	protected static class TextAreaLayout extends CommonWidgetLayout {
		public TextAreaLayout() {
			super(ADD_INLINE);
		}

		@Override
		protected RElement createRenderable(final RBlockViewport bodyLayout, final HTMLElementImpl markupElement) {
			final UIControl control = new TextAreaControl((HTMLTextAreaElementImpl) markupElement);
			return new RUIControl(markupElement, control, bodyLayout.container, bodyLayout.userAgentContext);
		}
	}

	protected static class ButtonLayout extends CommonWidgetLayout {
		public ButtonLayout() {
			super(ADD_INLINE);
		}

		@Override
		protected RElement createRenderable(final RBlockViewport bodyLayout, final HTMLElementImpl markupElement) {
			final UIControl control = new ButtonControl((HTMLButtonElementImpl) markupElement);
			return new RUIControl(markupElement, control, bodyLayout.container, bodyLayout.userAgentContext);
		}
	}
}
