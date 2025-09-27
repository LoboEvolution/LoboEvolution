package com.jtattoo.plaf.base.button;

import com.jtattoo.plaf.base.BaseTabbedPaneUI;
import com.jtattoo.plaf.base.action.ArrowButton;

import javax.swing.*;
import javax.swing.plaf.UIResource;
import java.io.Serial;

public class ScrollablePopupMenuTabButton extends ArrowButton implements UIResource, SwingConstants {

    @Serial
    private static final long serialVersionUID = 1L;

    public ScrollablePopupMenuTabButton(final BaseTabbedPaneUI ui) {
        super(ui, SOUTH);
    }
}