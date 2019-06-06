package org.lobobrowser.html.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import org.lobo.common.ArrayUtilities;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.domimpl.ModelNode;
import org.lobobrowser.html.style.RenderState;
import org.lobobrowser.http.HtmlRendererContext;
import org.lobobrowser.http.UserAgentContext;

public class RInlineBlock extends BaseElementRenderable {
	private final BaseElementRenderable child;

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

	public void assignDimension() {
		this.width = child.getWidth();
		this.height = child.getHeight();
	}

	public Iterator<Renderable> getRenderables() {
		return ArrayUtilities.singletonIterator((Renderable) this.child);
	}

	public RenderableSpot getLowestRenderableSpot(final int x, final int y) {
		return this.child.getLowestRenderableSpot(x, y);
	}

	public boolean onDoubleClick(final MouseEvent event, final int x, final int y) {
		return this.child.onDoubleClick(event, x, y);
	}

	public boolean onMouseClick(final MouseEvent event, final int x, final int y) {
		return this.child.onMouseClick(event, x, y);
	}

	public boolean onMouseDisarmed(final MouseEvent event) {
		return this.child.onMouseDisarmed(event);
	}

	public boolean onMousePressed(final MouseEvent event, final int x, final int y) {
		return this.child.onMousePressed(event, x, y);
	}

	public boolean onMouseReleased(final MouseEvent event, final int x, final int y) {
		return this.child.onMouseReleased(event, x, y);
	}

	public void paint(final Graphics g) {
		this.child.paint(g);
	}

	@Override
	public int getVAlign() {
		// Not used
		return VALIGN_BASELINE;
	}

	@Override
	public void repaint(final ModelNode modelNode) {
		this.child.repaint(modelNode);
	}

	@Override
	public Color getPaintedBackgroundColor() {
		return this.backgroundColor;
	}

	@Override
	protected void doLayout(final int availWidth, final int availHeight, final boolean sizeOnly) {
		this.child.layout(availWidth, availHeight, sizeOnly);
		assignDimension();
	}

    @Override
    public void layout(int availWidth, int availHeight, boolean sizeOnly) {
        this.doLayout(availWidth, availHeight, sizeOnly);
    }

	@Override
	public Component addComponent(final Component component) {
		this.container.addComponent(component);
		return super.addComponent(component);
	}
}
