package com.jtattoo.plaf.base.layout;

import com.jtattoo.plaf.base.BaseTabbedPaneUI;
import com.jtattoo.plaf.base.button.ScrollablePopupMenuTabButton;
import com.jtattoo.plaf.base.button.ScrollableTabButton;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

import static javax.swing.SwingConstants.*;

public class ScrollableTabSupport implements ChangeListener {

    public final ScrollableTabViewport viewport;
    public final ScrollableTabPanel tabPanel;
    public final ScrollableTabButton scrollForwardButton;
    public final ScrollableTabButton scrollBackwardButton;
    public final ScrollablePopupMenuTabButton popupMenuButton;
    public int leadingTabIndex;
    private final Point tabViewPosition = new Point(0, 0);
    private final JTabbedPane tabPane;
    private final Rectangle[] rects;
    private final BaseTabbedPaneUI ui;

    public ScrollableTabSupport(BaseTabbedPaneUI ui, final JTabbedPane tabPane, final Rectangle[] rects) {
        viewport = new ScrollableTabViewport();
        tabPanel = new ScrollableTabPanel(ui, tabPane);

        viewport.setView(tabPanel);
        viewport.addChangeListener(this);

        int tabPlacement = tabPane.getTabPlacement();

        if (tabPlacement == TOP || tabPlacement == BOTTOM) {
            scrollForwardButton = new ScrollableTabButton(ui, EAST);
            scrollBackwardButton = new ScrollableTabButton(ui, WEST);

        } else { // tabPlacement = LEFT || RIGHT
            scrollForwardButton = new ScrollableTabButton(ui, SOUTH);
            scrollBackwardButton = new ScrollableTabButton(ui, NORTH);
        }
        popupMenuButton = new ScrollablePopupMenuTabButton(ui);
        this.tabPane = tabPane;
        this.rects = rects;
        this.ui = ui;
    }

    public void scrollBackward(final int tabPlacement) {
        if (leadingTabIndex == 0) {
            return; // no room left to scroll
        }
        setLeadingTabIndex(tabPlacement, leadingTabIndex - 1);
        if (tabPane != null) {
            tabPane.doLayout();
        }
    }

    public void scrollForward(final int tabPlacement) {
        final Dimension viewSize = viewport.getViewSize();
        final Rectangle viewRect = viewport.getViewRect();

        if (tabPlacement == TOP || tabPlacement == BOTTOM) {
            if (viewRect.width >= viewSize.width - viewRect.x) {
                return; // no room left to scroll
            }
        } else {
            // tabPlacement == LEFT || tabPlacement == RIGHT
            if (viewRect.height >= viewSize.height - viewRect.y) {
                return;
            }
        }
        setLeadingTabIndex(tabPlacement, leadingTabIndex + 1);
        if (tabPane != null) {
            tabPane.doLayout();
        }
    }

    public void scrollTabToVisible(final int tabPlacement, final int index) {
        if (index <= leadingTabIndex) {
            setLeadingTabIndex(tabPlacement, index);
        } else {
            final Rectangle viewRect = viewport.getViewRect();
            switch (tabPlacement) {
                case TOP:
                case BOTTOM: {
                    int i = index;
                    int x = viewRect.width - rects[index].width;
                    while (i > 0 && x - rects[i - 1].width >= 0) {
                        i--;
                        x -= rects[i].width;
                    }
                    if (leadingTabIndex < i) {
                        setLeadingTabIndex(tabPlacement, i);
                    }
                    break;

                }
                case LEFT:
                case RIGHT: {
                    int i = index;
                    int y = viewRect.height - rects[index].height;
                    while (i > 0 && y - rects[i - 1].height > 0) {
                        i--;
                        y -= rects[i].height;
                    }
                    if (leadingTabIndex < i) {
                        setLeadingTabIndex(tabPlacement, i);
                    }
                    break;
                }
                default:
                    break;
            }
        }
    }

    public void setLeadingTabIndex(final int tabPlacement, final int index) {
        leadingTabIndex = index;
        final Dimension viewSize = viewport.getViewSize();
        final Rectangle viewRect = viewport.getViewRect();

        switch (tabPlacement) {
            case TOP:
            case BOTTOM:
                tabViewPosition.x = leadingTabIndex == 0 ? 0 : rects[leadingTabIndex].x;

                if (viewSize.width - tabViewPosition.x < viewRect.width) {
                    // We've scrolled to the end, so adjust the viewport size
                    // to ensure the view position remains aligned on a tab boundary
                    final Dimension extentSize = new Dimension(viewSize.width - tabViewPosition.x, viewRect.height);
                    viewport.setExtentSize(extentSize);
                }
                break;
            case LEFT:
            case RIGHT:
                tabViewPosition.y = leadingTabIndex == 0 ? 0 : rects[leadingTabIndex].y;

                if (viewSize.height - tabViewPosition.y < viewRect.height) {
                    // We've scrolled to the end, so adjust the viewport size
                    // to ensure the view position remains aligned on a tab boundary
                    final Dimension extentSize = new Dimension(viewRect.width, viewSize.height - tabViewPosition.y);
                    viewport.setExtentSize(extentSize);
                }
        }
        viewport.setViewPosition(tabViewPosition);
    }

    @Override
    public void stateChanged(final ChangeEvent e) {
        final JViewport vp = (JViewport) e.getSource();
        final int tabPlacement = tabPane.getTabPlacement();
        final int tc = tabPane.getTabCount();
        final Rectangle vpRect = vp.getBounds();
        final Dimension viewSize = vp.getViewSize();
        final Rectangle viewRect = vp.getViewRect();

        leadingTabIndex = ui.getClosestTab(viewRect.x, viewRect.y);
        if (leadingTabIndex >= rects.length) {
            return;
        }

        // If the tab isn't right aligned, adjust it.
        if (leadingTabIndex + 1 < tc) {
            switch (tabPlacement) {
                case TOP:
                case BOTTOM:
                    if (rects[leadingTabIndex].x < viewRect.x) {
                        leadingTabIndex++;
                    }
                    break;
                case LEFT:
                case RIGHT:
                    if (rects[leadingTabIndex].y < viewRect.y) {
                        leadingTabIndex++;
                    }
                    break;
            }
        }
        final Insets contentInsets = ui.getContentBorderInsets(tabPlacement);
        switch (tabPlacement) {
            case LEFT:
                tabPane.repaint(vpRect.x + vpRect.width, vpRect.y, contentInsets.left, vpRect.height);
                scrollBackwardButton.setEnabled(viewRect.y > 0);
                scrollForwardButton
                        .setEnabled(leadingTabIndex < tc - 1 && viewSize.height - viewRect.y > viewRect.height);
                break;
            case RIGHT:
                tabPane.repaint(vpRect.x - contentInsets.right, vpRect.y, contentInsets.right, vpRect.height);
                scrollBackwardButton.setEnabled(viewRect.y > 0);
                scrollForwardButton
                        .setEnabled(leadingTabIndex < tc - 1 && viewSize.height - viewRect.y > viewRect.height);
                break;
            case BOTTOM:
                tabPane.repaint(vpRect.x, vpRect.y - contentInsets.bottom, vpRect.width, contentInsets.bottom);
                scrollBackwardButton.setEnabled(viewRect.x > 0);
                scrollForwardButton
                        .setEnabled(leadingTabIndex < tc - 1 && viewSize.width - viewRect.x > viewRect.width);
                break;
            case TOP:
            default:
                tabPane.repaint(vpRect.x, vpRect.y + vpRect.height, vpRect.width, contentInsets.top);
                scrollBackwardButton.setEnabled(viewRect.x > 0);
                scrollForwardButton
                        .setEnabled(leadingTabIndex < tc - 1 && viewSize.width - viewRect.x > viewRect.width);
        }
    }

}
