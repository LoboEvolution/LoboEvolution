/*
 * MIT License
 *
 * Copyright (c) 2014 - 2026 LoboEvolution
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

import org.loboevolution.html.node.ModelNode;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>TranslatedRenderable class.</p>
 */
public class TranslatedRenderable extends BaseBoundableRenderable implements RCollection {

    private final BoundableRenderable translatedChild;

    /**
     * <p>Constructor for TranslatedRenderable.</p>
     *
     * @param translatedChild a {@link org.loboevolution.html.renderer.BoundableRenderable} object.
     */
    public TranslatedRenderable(final BoundableRenderable translatedChild) {
        super(null, null);
        this.translatedChild = translatedChild;
    }

    /** {@inheritDoc} */
    @Override
    public void paint(final Graphics g) {
        translatedChild.paintTranslated(g);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isFixed() {
        return translatedChild.isFixed();
    }

    /** {@inheritDoc} */
    @Override
    public ModelNode getModelNode() {
        return translatedChild.getModelNode();
    }

    /** {@inheritDoc} */
    @Override
    public Rectangle getBounds() {
        return translatedChild.getBounds();
    }

    /** {@inheritDoc} */
    @Override
    public Rectangle getVisualBounds() {
        return translatedChild.getVisualBounds();
    }

    /** {@inheritDoc} */
    @Override
    public boolean contains(final int x, final int y) {
        return translatedChild.contains(x, y);
    }

    /** {@inheritDoc} */
    @Override
    public Dimension getSize() {
        return translatedChild.getSize();
    }

    /** {@inheritDoc} */
    @Override
    public Point getOrigin() {
        return translatedChild.getOrigin();
    }

    /** {@inheritDoc} */
    @Override
    public Point getOriginRelativeTo(final RCollection ancestor) {
        return translatedChild.getOriginRelativeTo(ancestor);
    }

    /** {@inheritDoc} */
    @Override
    public Point getOriginRelativeToNoScroll(final RCollection ancestor) {
        return translatedChild.getOriginRelativeToNoScroll(ancestor);
    }

    /** {@inheritDoc} */
    @Override
    public void setOriginalParent(final RCollection origParent) {
        translatedChild.setOriginalParent(origParent);
    }

    /** {@inheritDoc} */
    @Override
    public RCollection getOriginalParent() {
        return translatedChild.getOriginalParent();
    }

    /** {@inheritDoc} */
    @Override
    public RCollection getOriginalOrCurrentParent() {
        return translatedChild.getOriginalOrCurrentParent();
    }

    /** {@inheritDoc} */
    @Override
    public void setBounds(final int x, final int y, final int with, final int height) {
        translatedChild.setBounds(x, y, with, height);
    }

    /** {@inheritDoc} */
    @Override
    public void setOrigin(final int x, final int y) {
        translatedChild.setOrigin(x, y);
    }

    /** {@inheritDoc} */
    @Override
    public void setX(final int x) {
        translatedChild.setX(x);
    }

    /** {@inheritDoc} */
    @Override
    public void setY(final int y) {
        translatedChild.setY(y);
    }

    /** {@inheritDoc} */
    @Override
    public int getX() {
        return translatedChild.getX();
    }

    /** {@inheritDoc} */
    @Override
    public int getY() {
        return translatedChild.getY();
    }

    /** {@inheritDoc} */
    @Override
    public int getHeight() {
        return translatedChild.getHeight();
    }

    /** {@inheritDoc} */
    @Override
    public int getWidth() {
        return translatedChild.getWidth();
    }

    /** {@inheritDoc} */
    @Override
    public int getVisualHeight() {
        return translatedChild.getVisualHeight();
    }

    /** {@inheritDoc} */
    @Override
    public int getVisualWidth() {
        return translatedChild.getVisualWidth();
    }

    /** {@inheritDoc} */
    @Override
    public void setHeight(final int height) {
        translatedChild.setHeight(height);
    }

    /** {@inheritDoc} */
    @Override
    public void setWidth(final int width) {
        translatedChild.setWidth(width);
    }

    /** {@inheritDoc} */
    @Override
    public RenderableSpot getLowestRenderableSpot(final int x, final int y) {
        return translatedChild.getLowestRenderableSpot(x, y);
    }

    /** {@inheritDoc} */
    @Override
    public void repaint() {
        translatedChild.repaint();
    }

    /** {@inheritDoc} */
    @Override
    public boolean onMousePressed(final MouseEvent event, final int x, final int y) {
        return translatedChild.onMousePressed(event, x, y);
    }

    /** {@inheritDoc} */
    @Override
    public boolean onMouseReleased(final MouseEvent event, final int x, final int y) {
        return translatedChild.onMouseReleased(event, x, y);
    }

    /** {@inheritDoc} */
    @Override
    public boolean onMouseDisarmed(final MouseEvent event) {
        return translatedChild.onMouseDisarmed(event);
    }

    /** {@inheritDoc} */
    @Override
    public boolean onMouseClick(final MouseEvent event, final int x, final int y) {
        return translatedChild.onMouseClick(event, x, y);
    }

    /** {@inheritDoc} */
    @Override
    public boolean onDoubleClick(final MouseEvent event, final int x, final int y) {
        return translatedChild.onDoubleClick(event, x, y);
    }

    /** {@inheritDoc} */
    @Override
    public boolean onRightClick(final MouseEvent event, final int x, final int y) {
        return translatedChild.onRightClick(event, x, y);
    }

    /** {@inheritDoc} */
    @Override
    public void onMouseMoved(final MouseEvent event, final int x, final int y, final boolean triggerEvent, final ModelNode limit) {
        translatedChild.onMouseMoved(event, x, y, triggerEvent, limit);
    }

    /** {@inheritDoc} */
    @Override
    public void onMouseOut(final MouseEvent event, final int x, final int y, final ModelNode limit) {
        translatedChild.onMouseOut(event, x, y, limit);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isContainedByNode() {
        return translatedChild.isContainedByNode();
    }

    /** {@inheritDoc} */
    @Override
    public boolean paintSelection(final Graphics g, final boolean inSelection, final RenderableSpot startPoint, final RenderableSpot endPoint) {
        return translatedChild.paintSelection(g, inSelection, startPoint, endPoint);
    }

    /** {@inheritDoc} */
    @Override
    public boolean extractSelectionText(final StringBuilder buffer, final boolean inSelection, final RenderableSpot startPoint, final RenderableSpot endPoint) {
        return translatedChild.extractSelectionText(buffer, inSelection, startPoint, endPoint);
    }

    /** {@inheritDoc} */
    @Override
    public void repaint(final int x, final int y, final int width, final int height) {
        final Point or = translatedChild.getOriginRelativeTo(getParent());
        final Rectangle rect = new Rectangle(x, y, width, height);
        rect.translate(or.x, or.y);
        getParent().repaint(x + or.x, y + or.y, width, height);
    }

    /** {@inheritDoc} */
    @Override
    public void relayout() {
        translatedChild.relayout();
    }

    /** {@inheritDoc} */
    @Override
    public int getZIndex() {
        return translatedChild.getZIndex();
    }

    /** {@inheritDoc} */
    @Override
    protected void invalidateLayoutLocal() {
        // TODO
    }

    public Renderable getChild() {
        return translatedChild;
    }

    /** {@inheritDoc} */
    @Override
    public void updateWidgetBounds(final int guiX, final int guiY) {
        if (translatedChild instanceof RCollection tc) {
            tc.updateWidgetBounds(guiX, guiY);
        }

    }

    /** {@inheritDoc} */
    @Override
    public List<Renderable> getRenderables() {
        final List<Renderable> renderables = new ArrayList<>();
        renderables.add(translatedChild);
        return renderables;
    }

    /** {@inheritDoc} */
    @Override
    public void invalidateLayoutDeep() {
        if (translatedChild instanceof RCollection tc) {
            tc.invalidateLayoutDeep();
        }

    }

    /** {@inheritDoc} */
    @Override
    public void focus() {
        // TODO Auto-generated method stub
    }

    /** {@inheritDoc} */
    @Override
    public void blur() {
        // TODO Auto-generated method stub
    }

    /** {@inheritDoc} */
    @Override
    public BoundableRenderable getRenderable(final int x, final int y) {
        if (translatedChild instanceof RCollection tc) {
            return tc.getRenderable(x, y);
        }

        return null;
    }

    /** {@inheritDoc} */
    @Override
    public Rectangle getClipBounds() {
        if (translatedChild instanceof RCollection tc) {
            return tc.getClipBounds();
        }

        return null;
    }

    /** {@inheritDoc} */
    @Override
    public Rectangle getClipBoundsWithoutInsets() {
        // TODO: Stub
        return getClipBounds();
    }

    /** {@inheritDoc} */
    @Override
    public Point getOriginRelativeToAbs(final RCollection ancestor) {
        return translatedChild.getOriginRelativeToAbs(ancestor);
    }
}
