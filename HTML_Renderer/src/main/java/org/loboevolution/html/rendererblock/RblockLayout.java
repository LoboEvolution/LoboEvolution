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
    along with rBlockView program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.rendererblock;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import org.loboevolution.html.HtmlLayoutMapping;
import org.loboevolution.html.HtmlRendererContext;
import org.loboevolution.html.control.HrControl;
import org.loboevolution.html.control.RUIControl;
import org.loboevolution.html.dombl.ModelNode;
import org.loboevolution.html.dombl.UINode;
import org.loboevolution.html.domimpl.DOMFragmentImpl;
import org.loboevolution.html.domimpl.DOMNodeImpl;
import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.html.layout.MarkupLayout;
import org.loboevolution.html.layout.MiscLayout;
import org.loboevolution.html.renderer.BoundableRenderable;
import org.loboevolution.html.renderer.DelayedPair;
import org.loboevolution.html.renderer.FrameContext;
import org.loboevolution.html.renderer.RElement;
import org.loboevolution.html.renderer.RFloatInfo;
import org.loboevolution.html.renderer.RLine;
import org.loboevolution.html.renderer.RList;
import org.loboevolution.html.renderer.RListItem;
import org.loboevolution.html.renderer.RStyleChanger;
import org.loboevolution.html.renderer.Renderable;
import org.loboevolution.html.renderer.RenderableContainer;
import org.loboevolution.html.renderer.UIControl;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.http.UserAgentContext;
import org.w3c.dom.Node;

public class RblockLayout {
	
	/** The Constant elementLayout. */
	private static final Map<String, Object> elementLayout = HtmlLayoutMapping.layout();

	/** The Constant miscLayout. */
	private static final MarkupLayout miscLayout = new MiscLayout();
	
	/**
	 * Layout r inline block.
	 *
	 * @param markupElement
	 *            the markup element
	 */
	public void layoutRInlineBlock(final HTMLElementImpl markupElement, final RBlockViewport rBlockView) {
		UINode uINode = markupElement.getUINode();
		RInlineBlock inlineBlock = null;
		RBlockLine line = new RBlockLine();
		if (uINode instanceof RInlineBlock) {
			inlineBlock = (RInlineBlock) uINode;
		} else {
			RenderableContainer container = rBlockView.getContainer();
			UserAgentContext userAgent = rBlockView.getUserAgentContext();
			HtmlRendererContext htmlContext = rBlockView.getRendererContext();
			FrameContext frameContext = rBlockView.getFrameContext();
			RInlineBlock newInlineBlock = new RInlineBlock(container, markupElement, userAgent, htmlContext, frameContext);
			markupElement.setUINode(newInlineBlock);
			inlineBlock = newInlineBlock;
		}
		inlineBlock.doLayout(rBlockView.getAvailContentWidth(), rBlockView.getAvailContentHeight(), rBlockView.isSizeOnly());
		line.addRenderableToLine(inlineBlock, rBlockView);
		inlineBlock.setOriginalParent(inlineBlock.getParent());
		layoutRelative(markupElement, inlineBlock, rBlockView);
	}
	
	/**
	 * Layout r block.
	 *
	 * @param markupElement
	 *            the markup element
	 */
	public void layoutRBlock(final HTMLElementImpl markupElement, final RBlockViewport rBlockView) {
		RBlock renderable = (RBlock) markupElement.getUINode();
		if (renderable == null) {
			RenderableContainer container = rBlockView.getContainer();
			UserAgentContext userAgentContext = rBlockView.getUserAgentContext();
			HtmlRendererContext rendererContext = rBlockView.getRendererContext();
			FrameContext frameContext = rBlockView.getFrameContext();
			int listNesting = rBlockView.getListNesting();
			renderable = new RBlock(markupElement, listNesting, userAgentContext, rendererContext, frameContext, container);
			markupElement.setUINode(renderable);
		}
		renderable.setOriginalParent(rBlockView);
		RBlockPosition ps = new RBlockPosition();
		ps.positionRBlock(markupElement, renderable, rBlockView);
	}

	/**
	 * Layout list item.
	 *
	 * @param markupElement
	 *            the markup element
	 */
	public void layoutListItem(final HTMLElementImpl markupElement, final RBlockViewport rBlockView) {
		RListItem renderable = (RListItem) markupElement.getUINode();
		if (renderable == null) {
			RenderableContainer container = rBlockView.getContainer();
			UserAgentContext userAgentContext = rBlockView.getUserAgentContext();
			HtmlRendererContext rendererContext = rBlockView.getRendererContext();
			FrameContext frameContext = rBlockView.getFrameContext();
			int listNesting = rBlockView.getListNesting();
			renderable = new RListItem(markupElement, listNesting, userAgentContext, rendererContext, frameContext, container);
			markupElement.setUINode(renderable);
		}
		renderable.setOriginalParent(rBlockView);
		RBlockPosition ps = new RBlockPosition();
		ps.positionRBlock(markupElement, renderable, rBlockView);
	}

	/**
	 * Layout list.
	 *
	 * @param markupElement
	 *            the markup element
	 */
	public void layoutList(final HTMLElementImpl markupElement, final RBlockViewport rBlockView) {
		RList renderable = (RList) markupElement.getUINode();
		if (renderable == null) {
			RenderableContainer container = rBlockView.getContainer();
			UserAgentContext userAgentContext = rBlockView.getUserAgentContext();
			HtmlRendererContext rendererContext = rBlockView.getRendererContext();
			FrameContext frameContext = rBlockView.getFrameContext();
			int listNesting = rBlockView.getListNesting();
			renderable = new RList(markupElement, listNesting, userAgentContext, rendererContext, frameContext, container);
			markupElement.setUINode(renderable);
		}
		renderable.setOriginalParent(rBlockView);
		RBlockPosition ps = new RBlockPosition();
		ps.positionRBlock(markupElement, renderable, rBlockView);
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
	protected void layoutFloat(final BoundableRenderable renderable, final RBlockViewport rBlockView, boolean layout, boolean leftFloat) {
		renderable.setOriginalParent(rBlockView);
		if (layout) {
			int availWidth = rBlockView.getAvailContentWidth();
			int availHeight = rBlockView.getAvailContentHeight();
			if (renderable instanceof RBlock) {
				RBlock block = (RBlock) renderable;
				// Float boxes don't inherit float bounds?
				block.layout(availWidth, availHeight, false, false, null, rBlockView.isSizeOnly());
			} else if (renderable instanceof RElement) {
				RElement e = (RElement) renderable;
				e.layout(availWidth, availHeight, rBlockView.isSizeOnly());
			}
		}
		RFloatInfo floatInfo = new RFloatInfo(renderable.getModelNode(), renderable, leftFloat);
		rBlockView.getCurrentLine().simplyAdd(floatInfo);
		scheduleFloat(floatInfo, rBlockView);
	}

	/**
	 * Layout hr.
	 *
	 * @param markupElement
	 *            the markup element
	 */
	public final void layoutHr(final HTMLElementImpl markupElement, final RBlockViewport rBlockView) {
		RElement renderable = (RElement) markupElement.getUINode();
		if (renderable == null) {
			renderable = setupNewUIControl(rBlockView.getContainer(), markupElement, new HrControl(markupElement), rBlockView);
		}
		renderable.layout(rBlockView.getAvailContentWidth(), rBlockView.getAvailContentHeight(), rBlockView.isSizeOnly());
		rBlockView.addAlignableAsBlock(markupElement, renderable);
	}
	
	/**
	 * Layout children.
	 *
	 * @param node
	 *            the node
	 */
	public void layoutChildren(final DOMNodeImpl node, final RBlockViewport rBlockView) {
		DOMNodeImpl[] childrenArray = node.getChildrenArray();
		if (childrenArray != null) {
			for (DOMNodeImpl child : childrenArray) {
				short nodeType = child.getNodeType();
				switch (nodeType) {
				case Node.TEXT_NODE:
					RBlockText txt = new RBlockText();
					txt.layoutText(child, rBlockView);
					break;
				case Node.ELEMENT_NODE:
					rBlockView.getCurrentLine().addStyleChanger(new RStyleChanger(child));
					String nodeName = child.getNodeName().toUpperCase();
					MarkupLayout ml = (MarkupLayout) elementLayout.get(nodeName);
					if (ml == null) {
						ml = miscLayout;
					}
					ml.layoutMarkup(rBlockView, (HTMLElementImpl) child);
					rBlockView.getCurrentLine().addStyleChanger(new RStyleChanger(node));
					break;
				case Node.DOCUMENT_FRAGMENT_NODE:
					final DOMFragmentImpl fragment = (DOMFragmentImpl) child;
					for (final DOMNodeImpl fragChild : fragment.getChildrenArray()) {
						layoutChildren(fragChild, rBlockView);
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


	/* This is used to bubble up relative elements (on the z-axis) */
	protected boolean layoutRelative(final HTMLElementImpl markupElement, final RElement renderable, final RBlockViewport rBlockView) {
		int position = rBlockView.getPosition(markupElement);
		boolean isRelative = position == RenderState.POSITION_RELATIVE;
		if (isRelative) {
			RenderableContainer con = getPositionedAncestor(rBlockView.getContainer(), rBlockView);
			DelayedPair dp = new DelayedPair(rBlockView.getContainer(), con, renderable, null, null, null, null, null, position);
			rBlockView.getContainer().addDelayedPair(dp);
			if (renderable instanceof RUIControl) {
				rBlockView.getContainer().addComponent(((RUIControl) renderable).getWidget().getComponent());
			}
			return true;
		}

		return false;
	}
	

	/**
	 * Schedule float.
	 *
	 * @param floatInfo
	 *            the float info
	 */
	private void scheduleFloat(final RFloatInfo floatInfo,final RBlockViewport rBlockView) {
		RLine line = rBlockView.getCurrentLine();
		if (line == null) {
			int y = line == null ? rBlockView.getPaddingInsets().top : line.getY();
			rBlockView.placeFloat(floatInfo.getRenderable(), y, floatInfo.isLeftFloat());
		} else if (line.getWidth() == 0) {
			int y = line.getY();
			rBlockView.placeFloat(floatInfo.getRenderable(), y, floatInfo.isLeftFloat());
			int leftOffset = rBlockView.fetchLeftOffset(y);
			int rightOffset = rBlockView.fetchRightOffset(y);
			line.changeLimits(leftOffset, rBlockView.getDesiredWidth() - leftOffset - rightOffset);
		} else {
			// These pending floats are positioned when
			// lineDone() is called.
			Collection<RFloatInfo> c = rBlockView.getPendingFloats();
			if (c == null) {
				c = new LinkedList<RFloatInfo>();
				rBlockView.setPendingFloats(c);
			}
			c.add(floatInfo);
		}
	}

	/**
	 * Gets an ancestor which is "positioned" (that is whose position is not
	 * static). Stops searching when HTML element is encountered.
	 */
	private static RenderableContainer getPositionedAncestor(RenderableContainer containingBlock, final RBlockViewport rBlockView) {
		while(true) {
			if (containingBlock instanceof Renderable) {
				final ModelNode node = ((Renderable) containingBlock).getModelNode();
				if (node instanceof HTMLElementImpl) {
					HTMLElementImpl element = (HTMLElementImpl) node;
					int position = rBlockView.getPosition(element);
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
	private RElement setupNewUIControl(final RenderableContainer container, final HTMLElementImpl element,final UIControl control, final RBlockViewport rBlockView) {
		RElement renderable = new RUIControl(element, control, container, rBlockView.getFrameContext(), rBlockView.getUserAgentContext());
		element.setUINode(renderable);
		return renderable;
	}

}
