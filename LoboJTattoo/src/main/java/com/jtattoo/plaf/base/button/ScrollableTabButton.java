package com.jtattoo.plaf.base.button;

import com.jtattoo.plaf.base.BaseTabbedPaneUI;
import com.jtattoo.plaf.base.action.ArrowButton;

import javax.swing.*;
import javax.swing.plaf.UIResource;
import java.io.Serial;

public class ScrollableTabButton extends ArrowButton implements UIResource, SwingConstants {

    @Serial
    private static final long serialVersionUID = 1L;

    public ScrollableTabButton(final BaseTabbedPaneUI ui, final int direction) {
        super(ui, direction);
    }

    public boolean scrollsForward() {
        return direction == EAST || direction == SOUTH;
    }
}