/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.html.renderer;

import org.loboevolution.common.ArrayUtilities;
import org.loboevolution.html.dom.domimpl.ModelNode;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Iterator;

public class TranslatedRenderable extends BaseBoundableRenderable implements RCollection {

    private final BoundableRenderable translatedChild;

    public TranslatedRenderable(final BoundableRenderable translatedChild) {
        super(null, null);
        this.translatedChild = translatedChild;
    }

    public void paint(final Graphics g) {
        translatedChild.paintTranslated(g);
    }

    public boolean isFixed() {
        return translatedChild.isFixed();
    }

    public ModelNode getModelNode() {
        return translatedChild.getModelNode();
    }

    public Rectangle getBounds() {
        return translatedChild.getBounds();
    }

    public Rectangle getVisualBounds() {
        return translatedChild.getVisualBounds();
    }

    public boolean contains(final int x, final int y) {
        return translatedChild.contains(x, y);
    }

    public Dimension getSize() {
        return translatedChild.getSize();
    }

    public Point getOrigin() {
        return translatedChild.getOrigin();
    }

    public Point getOriginRelativeTo(final RCollection ancestor) {
        return translatedChild.getOriginRelativeTo(ancestor);
    }

    public Point getOriginRelativeToNoScroll(final RCollection ancestor) {
        return translatedChild.getOriginRelativeToNoScroll(ancestor);
    }

    public void setOriginalParent(final RCollection origParent) {
        translatedChild.setOriginalParent(origParent);
    }

    public RCollection getOriginalParent() {
        return translatedChild.getOriginalParent();
    }

    public RCollection getOriginalOrCurrentParent() {
        return translatedChild.getOriginalOrCurrentParent();
    }

    public void setBounds(final int x, final int y, final int with, final int height) {
        translatedChild.setBounds(x, y, with, height);
    }

    public void setOrigin(final int x, final int y) {
        translatedChild.setOrigin(x, y);
    }

    public void setX(final int x) {
        translatedChild.setX(x);
    }

    public void setY(final int y) {
        translatedChild.setY(y);
    }

    public int getX() {
        return translatedChild.getX();
    }

    public int getY() {
        return translatedChild.getY();
    }

    public int getHeight() {
        return translatedChild.getHeight();
    }

    public int getWidth() {
        return translatedChild.getWidth();
    }

    public int getVisualHeight() {
        return translatedChild.getVisualHeight();
    }

    public int getVisualWidth() {
        return translatedChild.getVisualWidth();
    }

    public void setHeight(final int height) {
        translatedChild.setHeight(height);
    }

    public void setWidth(final int width) {
        translatedChild.setWidth(width);
    }

    public RenderableSpot getLowestRenderableSpot(final int x, final int y) {
        return translatedChild.getLowestRenderableSpot(x, y);
    }

    public void repaint() {
        translatedChild.repaint();
    }

    public boolean onMousePressed(final MouseEvent event, final int x, final int y) {
        return translatedChild.onMousePressed(event, x, y);
    }

    public boolean onMouseReleased(final MouseEvent event, final int x, final int y) {
        return translatedChild.onMouseReleased(event, x, y);
    }

    public boolean onMouseDisarmed(final MouseEvent event) {
        return translatedChild.onMouseDisarmed(event);
    }

    public boolean onMouseClick(final MouseEvent event, final int x, final int y) {
        return translatedChild.onMouseClick(event, x, y);
    }

    public boolean onMiddleClick(final MouseEvent event, final int x, final int y) {
        return false;
    }

    public boolean onDoubleClick(final MouseEvent event, final int x, final int y) {
        return translatedChild.onDoubleClick(event, x, y);
    }

    public boolean onRightClick(final MouseEvent event, final int x, final int y) {
        return translatedChild.onRightClick(event, x, y);
    }

    public void onMouseMoved(final MouseEvent event, final int x, final int y, final boolean triggerEvent, final ModelNode limit) {
        translatedChild.onMouseMoved(event, x, y, triggerEvent, limit);
    }

    public void onMouseOut(final MouseEvent event, final int x, final int y, final ModelNode limit) {
        translatedChild.onMouseOut(event, x, y, limit);
    }

    public boolean isContainedByNode() {
        return translatedChild.isContainedByNode();
    }

    public boolean paintSelection(final Graphics g, final boolean inSelection, final RenderableSpot startPoint, final RenderableSpot endPoint) {
        return translatedChild.paintSelection(g, inSelection, startPoint, endPoint);
    }

    @Override
    public boolean extractSelectionText(StringBuilder buffer, boolean inSelection, RenderableSpot startPoint, RenderableSpot endPoint) {
        return translatedChild.extractSelectionText(buffer, inSelection, startPoint, endPoint);
    }

    public void repaint(final int x, final int y, final int width, final int height) {
        final Point or = translatedChild.getOriginRelativeTo(getParent());
        final Rectangle rect = new Rectangle(x, y, width, height);
        rect.translate(or.x, or.y);
        getParent().repaint(x + or.x, y + or.y, width, height);
    }

    public void relayout() {
        translatedChild.relayout();
    }

    public int getZIndex() {
        return translatedChild.getZIndex();
    }

    @Override
    protected void invalidateLayoutLocal() {
        // TODO
    }

    public Renderable getChild() {
        return translatedChild;
    }

    @Override
    public void updateWidgetBounds(int guiX, int guiY) {
        if (translatedChild instanceof RCollection) {
            final RCollection tc = (RCollection) translatedChild;
            tc.updateWidgetBounds(guiX, guiY);
        }

    }

    @Override
    public Iterator<Renderable> getRenderables() {
        return ArrayUtilities.singletonIterator(translatedChild);
    }

    @Override
    public void invalidateLayoutDeep() {
        if (translatedChild instanceof RCollection) {
            final RCollection tc = (RCollection) translatedChild;
            tc.invalidateLayoutDeep();
        }

    }

    @Override
    public void focus() {
        // TODO Auto-generated method stub
    }

    @Override
    public void blur() {
        // TODO Auto-generated method stub
    }

    @Override
    public BoundableRenderable getRenderable(int x, int y) {
        if (translatedChild instanceof RCollection) {
            final RCollection tc = (RCollection) translatedChild;
            return tc.getRenderable(x, y);
        }

        return null;
    }

    @Override
    public Rectangle getClipBounds() {
        if (translatedChild instanceof RCollection) {
            final RCollection tc = (RCollection) translatedChild;
            return tc.getClipBounds();
        }

        return null;
    }

    @Override
    public Rectangle getClipBoundsWithoutInsets() {
        // TODO: Stub
        return getClipBounds();
    }

    public Point getOriginRelativeToAbs(final RCollection ancestor) {
        return translatedChild.getOriginRelativeToAbs(ancestor);
    }
}