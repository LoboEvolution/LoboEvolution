package com.jtattoo.plaf.base.layout;

import javax.swing.*;
import javax.swing.plaf.UIResource;
import java.io.Serial;

public class ScrollableTabViewport extends JViewport implements UIResource {

    @Serial
    private static final long serialVersionUID = 1L;

    public ScrollableTabViewport() {
        setScrollMode(SIMPLE_SCROLL_MODE);
        setOpaque(false);
    }

}