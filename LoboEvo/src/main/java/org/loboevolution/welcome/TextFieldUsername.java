package org.loboevolution.welcome;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.jtattoo.plaf.lobo.LoboLookAndFeel;

/**
 * <p>TextFieldUsername class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class TextFieldUsername extends JTextField implements LoboLookAndFeel {

	private static final long serialVersionUID = 1L;
	
	private final Color COLOR_BACKGROUND = background();

	private final Color COLOR_INTERACTIVE = foreground();

	private final Color COLOR_OUTLINE = foreground();

	private Color borderColor = COLOR_INTERACTIVE;
	
	private final Font FONT_GENERAL_UI = new Font("Segoe UI", Font.PLAIN, 20);

	private final String PLACEHOLDER_TEXT_USERNAME = "Surf in the web! Surf in the web!";

	private final int ROUNDNESS = 8;

	/**
	 * <p>Constructor for TextFieldUsername.this.</p>
	 */
	public TextFieldUsername() {
		setOpaque(false);
		setBackground(COLOR_BACKGROUND);
		setForeground(COLOR_OUTLINE);
		setBorderColor(COLOR_OUTLINE);
		setCaretColor(Color.white);
		setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		setHorizontalAlignment(SwingConstants.CENTER);
		setFont(FONT_GENERAL_UI);

		setText(PLACEHOLDER_TEXT_USERNAME);
		addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (getText().equals(TextFieldUsername.this.PLACEHOLDER_TEXT_USERNAME)) {
					setText("");
				}
				setForeground(Color.white);
				setBorderColor(TextFieldUsername.this.COLOR_INTERACTIVE);
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (getText().isEmpty()) {
					setText(TextFieldUsername.this.PLACEHOLDER_TEXT_USERNAME);
					setForeground(TextFieldUsername.this.COLOR_OUTLINE);
					setBorderColor(TextFieldUsername.this.COLOR_OUTLINE);
				}

			}
		});
	}

	private Graphics2D get2dGraphics(Graphics g) {
		final Graphics2D g2 = (Graphics2D) g;
		g2.addRenderingHints(new HashMap<RenderingHints.Key, Object>() {
			private static final long serialVersionUID = 1L;
			{
				put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			}
		});
		return g2;
	}

	/** {@inheritDoc} */
	@Override
	protected void paintBorder(Graphics g) {
		final Graphics2D g2 = get2dGraphics(g);
		g2.setColor(borderColor);
		g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ROUNDNESS, ROUNDNESS);
	}

	/** {@inheritDoc} */
	@Override
	protected void paintComponent(Graphics g) {
		final Graphics2D g2 = get2dGraphics(g);
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ROUNDNESS, ROUNDNESS);
		super.paintComponent(g2);
	}

	/**
	 * <p>Setter for the field borderColor.</p>
	 *
	 * @param color a {@link java.awt.Color} object.
	 */
	protected void setBorderColor(Color color) {
		borderColor = color;
		repaint();
	}
}
