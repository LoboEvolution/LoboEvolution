/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

import org.loboevolution.common.ArrayUtilities;
import org.loboevolution.html.dom.nodeimpl.ModelNode;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Iterator;

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
    public void paint(final Graphics g) {
        translatedChild.paintTranslated(g);
    }

    /**
     * <p>isFixed.</p>
     *
     * @return a boolean.
     */
    public boolean isFixed() {
        return translatedChild.isFixed();
    }

    /**
     * <p>getModelNode.</p>
     *
     * @return a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
     */
    public ModelNode getModelNode() {
        return translatedChild.getModelNode();
    }

    /**
     * <p>getBounds.</p>
     *
     * @return a {@link java.awt.Rectangle} object.
     */
    public Rectangle getBounds() {
        return translatedChild.getBounds();
    }

    /**
     * <p>getVisualBounds.</p>
     *
     * @return a {@link java.awt.Rectangle} object.
     */
    public Rectangle getVisualBounds() {
        return translatedChild.getVisualBounds();
    }

    /** {@inheritDoc} */
    public boolean contains(final int x, final int y) {
        return translatedChild.contains(x, y);
    }

    /**
     * <p>getSize.</p>
     *
     * @return a {@link java.awt.Dimension} object.
     */
    public Dimension getSize() {
        return translatedChild.getSize();
    }

    /**
     * <p>getOrigin.</p>
     *
     * @return a {@link java.awt.Point} object.
     */
    public Point getOrigin() {
        return translatedChild.getOrigin();
    }

    /** {@inheritDoc} */
    public Point getOriginRelativeTo(final RCollection ancestor) {
        return translatedChild.getOriginRelativeTo(ancestor);
    }

    /** {@inheritDoc} */
    public Point getOriginRelativeToNoScroll(final RCollection ancestor) {
        return translatedChild.getOriginRelativeToNoScroll(ancestor);
    }

    /** {@inheritDoc} */
    public void setOriginalParent(final RCollection origParent) {
        translatedChild.setOriginalParent(origParent);
    }

    /**
     * <p>getOriginalParent.</p>
     *
     * @return a {@link org.loboevolution.html.renderer.RCollection} object.
     */
    public RCollection getOriginalParent() {
        return translatedChild.getOriginalParent();
    }

    /**
     * <p>getOriginalOrCurrentParent.</p>
     *
     * @return a {@link org.loboevolution.html.renderer.RCollection} object.
     */
    public RCollection getOriginalOrCurrentParent() {
        return translatedChild.getOriginalOrCurrentParent();
    }

    /** {@inheritDoc} */
    public void setBounds(final int x, final int y, final int with, final int height) {
        translatedChild.setBounds(x, y, with, height);
    }

    /** {@inheritDoc} */
    public void setOrigin(final int x, final int y) {
        translatedChild.setOrigin(x, y);
    }

    /** {@inheritDoc} */
    public void setX(final int x) {
        translatedChild.setX(x);
    }

    /** {@inheritDoc} */
    public void setY(final int y) {
        translatedChild.setY(y);
    }

    /**
     * <p>getX.</p>
     *
     * @return a int.
     */
    public int getX() {
        return translatedChild.getX();
    }

    /**
     * <p>getY.</p>
     *
     * @return a int.
     */
    public int getY() {
        return translatedChild.getY();
    }

    /**
     * <p>getHeight.</p>
     *
     * @return a int.
     */
    public int getHeight() {
        return translatedChild.getHeight();
    }

    /**
     * <p>getWidth.</p>
     *
     * @return a int.
     */
    public int getWidth() {
        return translatedChild.getWidth();
    }

    /**
     * <p>getVisualHeight.</p>
     *
     * @return a int.
     */
    public int getVisualHeight() {
        return translatedChild.getVisualHeight();
    }

    /**
     * <p>getVisualWidth.</p>
     *
     * @return a int.
     */
    public int getVisualWidth() {
        return translatedChild.getVisualWidth();
    }

    /** {@inheritDoc} */
    public void setHeight(final int height) {
        translatedChild.setHeight(height);
    }

    /** {@inheritDoc} */
    public void setWidth(final int width) {
        translatedChild.setWidth(width);
    }

    /** {@inheritDoc} */
    public RenderableSpot getLowestRenderableSpot(final int x, final int y) {
        return translatedChild.getLowestRenderableSpot(x, y);
    }

    /**
     * <p>repaint.</p>
     */
    public void repaint() {
        translatedChild.repaint();
    }

    /** {@inheritDoc} */
    public boolean onMousePressed(final MouseEvent event, final int x, final int y) {
        return translatedChild.onMousePressed(event, x, y);
    }

    /** {@inheritDoc} */
    public boolean onMouseReleased(final MouseEvent event, final int x, final int y) {
        return translatedChild.onMouseReleased(event, x, y);
    }

    /** {@inheritDoc} */
    public boolean onMouseDisarmed(final MouseEvent event) {
        return translatedChild.onMouseDisarmed(event);
    }

    /** {@inheritDoc} */
    public boolean onMouseClick(final MouseEvent event, final int x, final int y) {
        return translatedChild.onMouseClick(event, x, y);
    }

    /**
     * <p>onMiddleClick.</p>
     *
     * @param event a {@link java.awt.event.MouseEvent} object.
     * @param x a int.
     * @param y a int.
     * @return a boolean.
     */
    public boolean onMiddleClick(final MouseEvent event, final int x, final int y) {
        return false;
    }

    /** {@inheritDoc} */
    public boolean onDoubleClick(final MouseEvent event, final int x, final int y) {
        return translatedChild.onDoubleClick(event, x, y);
    }

    /** {@inheritDoc} */
    public boolean onRightClick(final MouseEvent event, final int x, final int y) {
        return translatedChild.onRightClick(event, x, y);
    }

    /** {@inheritDoc} */
    public void onMouseMoved(final MouseEvent event, final int x, final int y, final boolean triggerEvent, final ModelNode limit) {
        translatedChild.onMouseMoved(event, x, y, triggerEvent, limit);
    }

    /** {@inheritDoc} */
    public void onMouseOut(final MouseEvent event, final int x, final int y, final ModelNode limit) {
        translatedChild.onMouseOut(event, x, y, limit);
    }

    /**
     * <p>isContainedByNode.</p>
     *
     * @return a boolean.
     */
    public boolean isContainedByNode() {
        return translatedChild.isContainedByNode();
    }

    /** {@inheritDoc} */
    public boolean paintSelection(final Graphics g, final boolean inSelection, final RenderableSpot startPoint, final RenderableSpot endPoint) {
        return translatedChild.paintSelection(g, inSelection, startPoint, endPoint);
    }

    /** {@inheritDoc} */
    @Override
    public boolean extractSelectionText(StringBuilder buffer, boolean inSelection, RenderableSpot startPoint, RenderableSpot endPoint) {
        return translatedChild.extractSelectionText(buffer, inSelection, startPoint, endPoint);
    }

    /** {@inheritDoc} */
    public void repaint(final int x, final int y, final int width, final int height) {
        final Point or = translatedChild.getOriginRelativeTo(getParent());
        final Rectangle rect = new Rectangle(x, y, width, height);
        rect.translate(or.x, or.y);
        getParent().repaint(x + or.x, y + or.y, width, height);
    }

    /**
     * <p>relayout.</p>
     */
    public void relayout() {
        translatedChild.relayout();
    }

    /**
     * <p>getZIndex.</p>
     *
     * @return a int.
     */
    public int getZIndex() {
        return translatedChild.getZIndex();
    }

    /** {@inheritDoc} */
    @Override
    protected void invalidateLayoutLocal() {
        // TODO
    }

    /**
     * <p>getChild.</p>
     *
     * @return a {@link org.loboevolution.html.renderer.Renderable} object.
     */
    public Renderable getChild() {
        return translatedChild;
    }

    /** {@inheritDoc} */
    @Override
    public void updateWidgetBounds(int guiX, int guiY) {
        if (translatedChild instanceof RCollection) {
            final RCollection tc = (RCollection) translatedChild;
            tc.updateWidgetBounds(guiX, guiY);
        }

    }

    /** {@inheritDoc} */
    @Override
    public Iterator<Renderable> getRenderables() {
        return ArrayUtilities.singletonIterator(translatedChild);
    }

    /** {@inheritDoc} */
    @Override
    public void invalidateLayoutDeep() {
        if (translatedChild instanceof RCollection) {
            final RCollection tc = (RCollection) translatedChild;
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
    public BoundableRenderable getRenderable(int x, int y) {
        if (translatedChild instanceof RCollection) {
            final RCollection tc = (RCollection) translatedChild;
            return tc.getRenderable(x, y);
        }

        return null;
    }

    /** {@inheritDoc} */
    @Override
    public Rectangle getClipBounds() {
        if (translatedChild instanceof RCollection) {
            final RCollection tc = (RCollection) translatedChild;
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
    public Point getOriginRelativeToAbs(final RCollection ancestor) {
        return translatedChild.getOriginRelativeToAbs(ancestor);
    }
}
