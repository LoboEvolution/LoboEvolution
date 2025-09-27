package com.jtattoo.plaf.aero.border;


import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;
import java.awt.*;

public class ButtonBorder implements Border, UIResource {

		private static final Insets insets = new Insets(4, 8, 4, 8);

		@Override
		public Insets getBorderInsets(final Component c) {
			return insets;
		}

		public Insets getBorderInsets(final Component c, final Insets borderInsets) {
			borderInsets.left = insets.left;
			borderInsets.top = insets.top;
			borderInsets.right = insets.right;
			borderInsets.bottom = insets.bottom;
			return borderInsets;
		}

		@Override
		public boolean isBorderOpaque() {
			return true;
		}

		@Override
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
			final Graphics2D g2D = (Graphics2D) g;
			final AbstractButton button = (AbstractButton) c;
			final ButtonModel model = button.getModel();
			if (model.isEnabled()) {
				g.setColor(AbstractLookAndFeel.getFrameColor());
			} else {
				g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getFrameColor(), 30));
			}
			g.drawRect(x, y, w - 2, h - 2);
			final Composite composite = g2D.getComposite();
			final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
			g2D.setComposite(alpha);
			g.setColor(Color.white);
			g.drawLine(x + w - 1, y + 1, x + w - 1, y + h);
			g.drawLine(x + 1, y + h - 1, x + w, y + h - 1);
			g2D.setComposite(composite);
		}

	} // class ButtonBorder