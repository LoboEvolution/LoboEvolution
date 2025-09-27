package com.jtattoo.plaf.base.border;

import com.jtattoo.plaf.JTattooUtilities;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.UIResource;
import java.awt.*;
import java.io.Serial;

public class BaseInternalFrameBorder extends AbstractBorder implements UIResource {

    @Serial
    private static final long serialVersionUID = 1L;
    protected final int DW = 5;
    protected final int TRACK_WIDTH = 22;
    protected final Insets INSETS = new Insets(DW, DW, DW, DW);
    protected final Insets PALETTE_INSETS = new Insets(3, 3, 3, 3);

    public BaseInternalFrameBorder() {
    }

    @Override
    public Insets getBorderInsets(final Component c) {
        if (isResizable(c)) {
            return new Insets(INSETS.top, INSETS.left, INSETS.bottom, INSETS.right);
        } else {
            return new Insets(PALETTE_INSETS.top, PALETTE_INSETS.left, PALETTE_INSETS.bottom, PALETTE_INSETS.right);
        }
    }

    @Override
    public Insets getBorderInsets(final Component c, final Insets borderInsets) {
        final Insets ins = getBorderInsets(c);
        borderInsets.left = ins.left;
        borderInsets.top = ins.top;
        borderInsets.right = ins.right;
        borderInsets.bottom = ins.bottom;
        return borderInsets;
    }

    public int getTitleHeight(final Component c) {
        int th = 21;
        final int fh = getBorderInsets(c).top + getBorderInsets(c).bottom;
        if (c instanceof JDialog) {
            final JDialog dialog = (JDialog) c;
            th = dialog.getSize().height - dialog.getContentPane().getSize().height - fh - 1;
            if (dialog.getJMenuBar() != null) {
                th -= dialog.getJMenuBar().getSize().height;
            }
        } else if (c instanceof JInternalFrame) {
            final JInternalFrame frame = (JInternalFrame) c;
            th = frame.getSize().height - frame.getRootPane().getSize().height - fh - 1;
            if (frame.getJMenuBar() != null) {
                th -= frame.getJMenuBar().getSize().height;
            }
        } else if (c instanceof JRootPane) {
            final JRootPane jp = (JRootPane) c;
            if (jp.getParent() instanceof JFrame) {
                final JFrame frame = (JFrame) c.getParent();
                th = frame.getSize().height - frame.getContentPane().getSize().height - fh - 1;
                if (frame.getJMenuBar() != null) {
                    th -= frame.getJMenuBar().getSize().height;
                }
            } else if (jp.getParent() instanceof JDialog) {
                final JDialog dialog = (JDialog) c.getParent();
                th = dialog.getSize().height - dialog.getContentPane().getSize().height - fh - 1;
                if (dialog.getJMenuBar() != null) {
                    th -= dialog.getJMenuBar().getSize().height;
                }
            }
        }
        return th;
    }

    public boolean isActive(final Component c) {
        if (c instanceof JComponent) {
            return JTattooUtilities.isActive((JComponent) c);
        } else {
            return true;
        }
    }

    public boolean isResizable(final Component c) {
        boolean resizable = true;
        if (c instanceof JDialog) {
            final JDialog dialog = (JDialog) c;
            resizable = dialog.isResizable();
        } else if (c instanceof JInternalFrame) {
            final JInternalFrame frame = (JInternalFrame) c;
            resizable = frame.isResizable();
        } else if (c instanceof JRootPane) {
            final JRootPane jp = (JRootPane) c;
            if (jp.getParent() instanceof JFrame) {
                final JFrame frame = (JFrame) c.getParent();
                resizable = frame.isResizable();
            } else if (jp.getParent() instanceof JDialog) {
                final JDialog dialog = (JDialog) c.getParent();
                resizable = dialog.isResizable();
            }
        }
        return resizable;
    }

} // end of class BaseInternalFrameBorder
