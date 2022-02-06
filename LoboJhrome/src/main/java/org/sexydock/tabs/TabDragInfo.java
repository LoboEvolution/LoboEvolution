package org.sexydock.tabs;

import java.awt.*;

public class TabDragInfo {

    public final IFloatingTabHandler floatingTabHandler;
    public final double grabX;
    public final Tab tab;
    public final Dimension sourceWindowSize;
    private Point grabPoint = null;
    public TabDragInfo(Tab tab, Point grabPoint, double grabX, IFloatingTabHandler floatingTabHandler, Dimension sourceWindowSize) {
        super();
        this.floatingTabHandler = floatingTabHandler;
        this.grabPoint = new Point(grabPoint);
        this.grabX = grabX;
        this.tab = tab;
        this.sourceWindowSize = sourceWindowSize;
    }

    public Point getGrabPoint() {
        return new Point(grabPoint);
    }

    public void setGrabPoint(Point p) {
        grabPoint = p == null ? null : new Point(p);
    }
}