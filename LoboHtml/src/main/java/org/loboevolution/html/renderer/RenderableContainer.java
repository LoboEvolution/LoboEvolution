/*
    GNU LESSER GENERAL PUBLIC LICENSE
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

package org.loboevolution.html.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.Point;
import java.util.Collection;

/**
 * A RenderableContainer is either usually a parent block or the root GUI
 * component. It's is a Renderable or GUI component whose layout may be
 * invalidated.
 */
public interface RenderableContainer {
	Component addComponent(Component component);

	void addDelayedPair(DelayedPair pair);

	void clearDelayedPairs();

	void focus();

	Collection getDelayedPairs();

	Color getPaintedBackgroundColor();

	RenderableContainer getParentContainer();

	void invalidateLayoutUpTree();

	void relayout();

	void repaint(int x, int y, int width, int height);

	void updateAllWidgetBounds();
	
    Insets getInsetsMarginBorder(final boolean hscroll, final boolean vscroll);
    
    Insets getInsets(final boolean hscroll, final boolean vscroll);
    
    int getY();
    
    default int getInnerWidth() {return 0;};

    default int getInnerHeight() {return 0;};
    
    Point getGUIPoint(int x, int y);
    
    Point translateDescendentPoint(BoundableRenderable descendent, int x, int y);
}
