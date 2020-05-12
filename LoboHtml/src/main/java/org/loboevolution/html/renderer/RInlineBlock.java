package org.loboevolution.html.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import org.loboevolution.common.ArrayUtilities;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.domimpl.ModelNode;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.http.UserAgentContext;

/**
 * <p>RInlineBlock class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class RInlineBlock extends BaseElementRenderable {
	private final BaseElementRenderable child;

	/**
	 * <p>Constructor for RInlineBlock.</p>
	 *
	 * @param container a {@link org.loboevolution.html.renderer.RenderableContainer} object.
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 * @param uacontext a {@link org.loboevolution.http.UserAgentContext} object.
	 * @param rendererContext a {@link org.loboevolution.http.HtmlRendererContext} object.
	 * @param frameContext a {@link org.loboevolution.html.renderer.FrameContext} object.
	 */
	public RInlineBlock(final RenderableContainer container, final HTMLElementImpl modelNode,
			final UserAgentContext uacontext, final HtmlRendererContext rendererContext,
			final FrameContext frameContext) {
		super(container, modelNode, uacontext);
		final int display = modelNode.getRenderState().getDisplay();
		final BaseElementRenderable child = (display == RenderState.DISPLAY_INLINE_TABLE)
				? new RTable(modelNode, userAgentContext, rendererContext, frameContext, this)
				: new RBlock(modelNode, 0, userAgentContext, rendererContext, frameContext, this);
		child.setOriginalParent(this);
		child.setParent(this);
		this.child = child;
	}

	/**
	 * <p>assignDimension.</p>
	 */
	public void assignDimension() {
		this.width = child.getWidth();
		this.height = child.getHeight();
	}

	/**
	 * <p>getRenderables.</p>
	 *
	 * @return a {@link java.util.Iterator} object.
	 */
	public Iterator<Renderable> getRenderables() {
		return ArrayUtilities.singletonIterator((Renderable) this.child);
	}

	/** {@inheritDoc} */
	public RenderableSpot getLowestRenderableSpot(final int x, final int y) {
		return this.child.getLowestRenderableSpot(x, y);
	}

	/** {@inheritDoc} */
	public boolean onDoubleClick(final MouseEvent event, final int x, final int y) {
		return this.child.onDoubleClick(event, x, y);
	}

	/** {@inheritDoc} */
	public boolean onMouseClick(final MouseEvent event, final int x, final int y) {
		return this.child.onMouseClick(event, x, y);
	}

	/** {@inheritDoc} */
	public boolean onMouseDisarmed(final MouseEvent event) {
		return this.child.onMouseDisarmed(event);
	}

	/** {@inheritDoc} */
	public boolean onMousePressed(final MouseEvent event, final int x, final int y) {
		return this.child.onMousePressed(event, x, y);
	}

	/** {@inheritDoc} */
	public boolean onMouseReleased(final MouseEvent event, final int x, final int y) {
		return this.child.onMouseReleased(event, x, y);
	}

	/** {@inheritDoc} */
	public void paint(final Graphics g) {
		this.child.paint(g);
	}

	/** {@inheritDoc} */
	@Override
	public void repaint(final ModelNode modelNode) {
		this.child.repaint(modelNode);
	}

	/** {@inheritDoc} */
	@Override
	public Color getPaintedBackgroundColor() {
		return this.backgroundColor;
	}

	/** {@inheritDoc} */
	@Override
	protected void doLayout(final int availWidth, final int availHeight, final boolean sizeOnly) {
		this.child.layout(availWidth, availHeight, sizeOnly);
		assignDimension();
	}

    /** {@inheritDoc} */
    @Override
    public void layout(int availWidth, int availHeight, boolean sizeOnly) {
        this.doLayout(availWidth, availHeight, sizeOnly);
    }

	/** {@inheritDoc} */
	@Override
	public Component addComponent(final Component component) {
		this.container.addComponent(component);
		return super.addComponent(component);
	}
}
